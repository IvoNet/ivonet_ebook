/*
 * Copyright (c) 2013 by Ivo Woltring (http://ivonet.nl)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package nl.ivonet.ebook.controler;


import nl.ivonet.ebook.config.Property;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.nio.file.Paths;

/**
 * @author Ivo Woltring
 */
@Path("/download")
@RequestScoped
public class DownloadController {
    private static final String APPLICATION_EPUB_ZIP = "application/epub+zip";
    @Inject
    @Property
    private String baseFolder;

    @GET
    @Path("{path}")
    @Produces(APPLICATION_EPUB_ZIP)
    public Response downloadEpub(@PathParam("path") final String path) {
        return Response.ok(Paths.get(baseFolder, path)
                                .toFile(), APPLICATION_EPUB_ZIP)
                       .build();
    }
}
