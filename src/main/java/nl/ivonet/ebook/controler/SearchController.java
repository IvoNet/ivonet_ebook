package nl.ivonet.ebook.controler;

import nl.ivonet.ebook.repository.SearchRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/search")
public class SearchController {

    @Inject private SearchRepository repo;

    @GET
    @Path("q/{query}")
    public int searchEbook(@PathParam("query") String query) throws IOException {
        return repo.getTotalResults(query);
    }

    @GET
    @Path("/reindex")
    public URI reindex() throws URISyntaxException {
        return repo.recreateIndex();
    }
}
