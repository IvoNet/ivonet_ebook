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

package nl.ivonet.ebook.controller;

import nl.ivonet.ebook.dao.Directory;
import nl.ivonet.ebook.io.ImageBase64;
import nl.ivonet.ebook.model.Folder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Ivo Woltring
 */
@Path("/ebook")
@RequestScoped
public class EbookController {
    @Inject private Directory directory;
    @Inject private ImageBase64 imageBase64;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Folder get() {
        return this.directory.folder("");
    }

    @Path("{folder}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Folder getFolderContent(@PathParam("folder") final String path) {
        return this.directory.folder(path);
    }
}
