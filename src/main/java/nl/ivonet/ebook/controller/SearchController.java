package nl.ivonet.ebook.controller;

import nl.ivonet.ebook.repository.SearchRepository;
import nl.ivonet.ebook.repository.SearchableBook;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Path("/search")
public class SearchController {

    @Inject private SearchRepository repo;

    @GET
    @Path("q/{query}")
    @Produces(APPLICATION_JSON)
    public List<SearchableBook> searchEbook(@PathParam("query") String query) throws IOException {
        return repo.search(query);
    }

    @GET
    @Path("/reindex")
    public URI reindex() throws URISyntaxException, IOException {
        return repo.recreateIndex();
    }
}
