/*
 * Copyright (c) 2013 by Ivo Wolring (http://ivonet.nl)
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

import nl.ivonet.ebook.model.Epub;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ivo Woltring
 */
@Path("/ebook")
public class Ebook {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Epub get() {
        System.out.println("Hello World..!!.");
        final Epub epub = new Epub();
        epub.setTitle("Hello World");
        return epub;
    }

}
