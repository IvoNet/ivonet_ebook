package nl.ivonet.ebook.controler;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/search")
@RequestScoped
public class SearchController {

    @GET
    @Path("{query}")
    public String downloadEpub(@PathParam("query") String query) {
        return query;
    }
}
