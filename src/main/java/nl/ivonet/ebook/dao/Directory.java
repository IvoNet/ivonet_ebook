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
import nl.ivonet.ebook.io.ImageBase64;
import nl.ivonet.ebook.model.Epub;
import nl.ivonet.ebook.model.Folder;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
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
    @Inject
    private DirectoryFilter directoryFilter;
    @Inject
    private EpubFilter epubFilter;
    @Inject
    private ImageBase64 imageBase64;

    @Inject private EpubReader epubReader;

    public Folder folder(final String path) {
        final Path dir = Paths.get(baseFolder + path.replace("+", "/"));
        final Folder folder = new Folder(path);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, directoryFilter)) {
            for (final Path entry : stream) {
                folder.addFolder(entry.getFileName().toString());
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, epubFilter)) {
            for (final Path entry : stream) {
                final String filename = dir + "/" + entry.getFileName().toString();
                folder.addBook(parseEpub(filename));
            }
        } catch (IOException x) {
            System.err.println(x);
        }


        return folder;
    }

    private Epub parseEpub(final String filename) throws IOException {
        final Book book = epubReader.readEpub(new FileInputStream(filename));

        final Epub epub = new Epub(filename);
        final Resource coverImage = book.getCoverImage();
        final String defaultExtension;
        final String jpg;
        if (coverImage == null) {
            jpg = imageBase64.defaultImage();
        } else {
            defaultExtension = coverImage.getMediaType()
                    .getDefaultExtension().substring(1);
            System.out.println("defaultExtension = " + defaultExtension);
            jpg = imageBase64.encodeToString(coverImage.getInputStream(), defaultExtension);
        }
        epub.setCover(jpg);
        return epub;
    }

    @PostConstruct
    public void postConstruct() {
        if (!baseFolder.endsWith(File.separator)) {
            baseFolder += "/";
        }
    }

}
