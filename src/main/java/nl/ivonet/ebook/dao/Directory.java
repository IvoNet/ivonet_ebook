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

package nl.ivonet.ebook.dao;

import nl.ivonet.cdi_properties.Property;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Ivo Woltring
 */
public class Directory {

    @Inject
    @Property
    private String baseFolder;

    public Folders folders(final String path) {
        final DirectoryStream.Filter<Path> directoryFilter =
                new DirectoryStream.Filter<Path>() {
                    @Override
                    public boolean accept(final Path path) throws IOException {
                        try {
                            return (Files.isDirectory(path));
                        } catch (Exception e) {
                            return false;
                        }
                    }
                };
        final Path dir = Paths.get(baseFolder + path);
        final Folders folders = new Folders(path);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, directoryFilter)) {
            for (final Path entry : stream) {
                folders.add(entry.getFileName().toString());
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return folders;
    }

    @PostConstruct
    public void postConstruct() {
        if (!baseFolder.endsWith(File.separator)) {
            baseFolder += "/";
        }
    }

}
