package nl.ivonet.ebook.repository;

import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import nl.ivonet.ebook.config.Property;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class SearchRepository {

    private static final String INDEX = "books";
    private static final String EPUB = ".epub";
    private static final int WINDOW_SIZE = 1000;
    private String elasticsearchUrl;
    private String baseFolder;

    private JestClient client;

    @Inject
    public SearchRepository(@Property("elasticsearchUrl") String elasticsearchUrl, @Property("baseFolder") String baseFolder) {
        this.elasticsearchUrl = elasticsearchUrl;
        this.baseFolder = baseFolder;

        HttpClientConfig clientConfig = new HttpClientConfig.Builder(elasticsearchUrl)
            .multiThreaded(true).build();
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(clientConfig);
        client = factory.getObject();
    }

    public URI recreateIndex() throws URISyntaxException, IOException {
        String newIndex = format("books-%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")));

        executeRequest(new CreateIndex.Builder(newIndex).build());

        List<Index> addActions = Files.walk(Paths.get(baseFolder))
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(EPUB))
            .map(Path::toFile)
            .map(File::getName)
            .map(SearchableBook::new)
            .map(searchableBook -> new Index.Builder(searchableBook).build())
            .collect(toList());

        window(addActions, WINDOW_SIZE).forEach(
            searchableBookBatch ->
                executeRequest(new Bulk.Builder()
                    .defaultIndex(newIndex)
                    .defaultType(INDEX)
                    .addAction(searchableBookBatch).build())
        );

        return new URI(elasticsearchUrl + "/" + newIndex);
    }

    private <T extends JestResult> T executeRequest(Action<T> request) {
        try {
            return client.execute(request);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static <T> Stream<List<T>> window(List<T> list, int windowSize) {
        return IntStream.range(0, list.size() / windowSize + 1)
            .mapToObj(value -> {
                int nextWindow = (value + 1) * windowSize;
                if (nextWindow > list.size()) {
                    nextWindow = list.size();
                }
                return list.subList(value * windowSize, nextWindow);
            });
    }
}
