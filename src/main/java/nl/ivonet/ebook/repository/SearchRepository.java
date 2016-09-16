package nl.ivonet.ebook.repository;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import nl.ivonet.ebook.config.Property;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class SearchRepository {

    private static final String INDEX = "books";
    private String elasticsearchUrl;

    private JestClient client;

    @Inject
    public SearchRepository(@Property("elasticsearchUrl") String elasticsearchUrl) {
        this.elasticsearchUrl = elasticsearchUrl;

        HttpClientConfig clientConfig = new HttpClientConfig.Builder(elasticsearchUrl)
            .multiThreaded(true).build();
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(clientConfig);
        client = factory.getObject();
    }

    public int getTotalResults(String query) throws IOException {
        return client.execute(new Search.Builder(format("{\"query\": {\"querystring\": \"%s\"}", query)).addIndex(INDEX).build()).getTotal();
    }

    public URI recreateIndex() throws URISyntaxException {
        String newIndex = format("books-%s", LocalDateTime.now().format(ISO_LOCAL_DATE));
        return new URI(elasticsearchUrl + "/" + newIndex);
    }
}
