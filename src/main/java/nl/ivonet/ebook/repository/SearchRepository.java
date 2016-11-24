package nl.ivonet.ebook.repository;

import com.google.gson.JsonElement;
import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.aliases.*;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.params.Parameters;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import nl.ivonet.ebook.config.Property;
import nl.ivonet.ebook.controller.SearchController;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;
import static javaslang.Tuple.of;

public class SearchRepository {

    private static final String INDEX = "books";
    private static final String EPUB = ".epub";
    private static final int WINDOW_SIZE = 1000;
    private final String elasticsearchUrl;
    private final String baseFolder;
    private final JestClient client;
    private final EpubReader epubReader;

    @Inject
    public SearchRepository(@Property("elasticsearchUrl") String elasticsearchUrl, @Property("baseFolder") String baseFolder, EpubReader epubReader) {
        this.elasticsearchUrl = elasticsearchUrl;
        this.baseFolder = baseFolder;
        this.epubReader = epubReader;

        HttpClientConfig clientConfig = new HttpClientConfig.Builder(elasticsearchUrl)
            .multiThreaded(true).build();
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(clientConfig);
        client = factory.getObject();
    }

    public List<SearchableBook> search(String query) {
        return List.ofAll(
            executeRequest(new Search.Builder(format("{ \"query\": { \"query_string\": { \"query\": \"%s\" } } }", query))
                .setParameter(Parameters.SIZE, 100)
                .addIndex(INDEX).build())
                .getHits(SearchableBook.class)
                .stream().map(hit -> hit.source).collect(toList())
        );
    }

    public URI recreateIndex() throws URISyntaxException, IOException {
        String newIndex = format("books-%s", now().format(ofPattern("yyyy-MM-dd-HH-mm-ss")));

        executeRequest(new CreateIndex.Builder(newIndex).settings(fileToString("/elasticsearch/settings.json")).build());
        executeRequest(new PutMapping.Builder(newIndex, INDEX, fileToString("/elasticsearch/mapping.json")).build());

        List.ofAll(
            Files.walk(Paths.get(baseFolder))
                .limit(500)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(EPUB))
                .map(Path::toFile)
                .map(file -> of(file, parseEpub(file)))
                .map(SearchableBook::new)
                .map(searchableBook -> new Index.Builder(searchableBook).build())
                .collect(toList()))
            .sliding(WINDOW_SIZE, WINDOW_SIZE)
            .forEach(indices ->
                executeRequest(
                    new Bulk.Builder()
                        .defaultIndex(newIndex)
                        .defaultType(INDEX)
                        .addAction(indices.toJavaList())
                        .build()
                ));

        HashSet<AliasMapping> map = retrieveAliasesEntrySet()
            .filter(entry -> entry.getValue().toString().contains(INDEX))
            .map(Map.Entry::getKey)
            .map(key -> new RemoveAliasMapping.Builder(key, INDEX).build());

        executeRequest(new ModifyAliases.Builder(new AddAliasMapping.Builder(newIndex, INDEX).build())
            .addAlias(map.toJavaList())
            .build());

        return new URI(elasticsearchUrl + "/" + newIndex);
    }

    private HashSet<Map.Entry<String, JsonElement>> retrieveAliasesEntrySet() {
        return HashSet.ofAll(executeRequest(new GetAliases.Builder().build()).getJsonObject().entrySet());
    }

    private String fileToString(String filePath) {
        InputStream resourceAsStream = SearchController.class.getResourceAsStream(filePath);
        Scanner s = new Scanner(resourceAsStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private Book parseEpub(File file) {
        try {
            return epubReader.readEpub(new FileInputStream(file.getAbsolutePath()));
        } catch (IOException e) {
            throw new IllegalArgumentException("This is not a valid file to parse");
        }
    }

    private <T extends JestResult> T executeRequest(Action<T> request) {
        try {
            return client.execute(request);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
