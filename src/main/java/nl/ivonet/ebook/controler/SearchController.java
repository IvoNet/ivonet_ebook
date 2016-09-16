package nl.ivonet.ebook.controler;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import nl.ivonet.ebook.config.Property;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;

import static java.lang.String.format;

@Path("/search")
@RequestScoped
public class SearchController {

    private static final String INDEX = "books";

    @Inject @Property private String elasticsearchUrl;

    private JestClient client;

    public SearchController() {
        new HttpClientConfig.Builder(elasticsearchUrl).build();
        JestClientFactory jestClientFactory = new JestClientFactory();
        jestClientFactory.setHttpClientConfig(new HttpClientConfig.Builder(elasticsearchUrl).build());
        client = jestClientFactory.getObject();
    }

    @GET
    @Path("q/{query}")
    public int searchEbook(@PathParam("query") String query) throws IOException {
        Search builder = new Search.Builder(format("{\"query\": {\"querystring\": \"%s\"}", query)).addIndex(INDEX).build();
        return client.execute(builder).getTotal();
    }

    @GET
    @Path("/reindex")
    public boolean reindex() {
        return true;
    }
}
