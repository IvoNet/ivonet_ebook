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

import nl.ivonet.ebook.dao.Directory;
import nl.ivonet.ebook.dao.Folders;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ivo Woltring
 */
@Path("/ebook")
@RequestScoped
public class Ebook {
    //TODO move this to a property file
    private static final String PATH = "/Volumes/Data/Books/ebook/IvoNetLibrary/";
    @Inject Directory directory;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Folders get() {
        final Folders folders = this.directory.folders(PATH);
        System.out.println("!!!!!!!!!!!!" + folders); //TODO Remove me
        return folders;
    }

    @Path("{folder}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Folders getFolderContent(@PathParam("folder") final String path) {
        final Folders folders = this.directory.folders(PATH + path);
        System.out.println("!!!!!!!!!!!!" + folders); //TODO Remove me
        return folders;

    }

}
