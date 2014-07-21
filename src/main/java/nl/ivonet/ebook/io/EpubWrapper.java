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

package nl.ivonet.ebook.io;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EpubWrapper {

    private File file;
    private Book epubBook;

    public EpubWrapper(final String file) {
        initialize(new File(file));
    }

    public EpubWrapper(final File file) {
        initialize(file);
    }

    private void initialize(final File file) {
        try {
            final EpubReader epubReader = new EpubReader();
            this.epubBook = epubReader.readEpub(new FileInputStream(file));
            this.file = file;
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize using file " + file, e);
        }
    }

    public Book getEpubBook() {
        return epubBook;
    }

    public Metadata getMetadata() {
        return epubBook.getMetadata();
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EpubWrapper{");
        sb.append("epubBook=")
          .append(epubBook);
        sb.append(", file=")
          .append(file);
        sb.append('}');
        return sb.toString();
    }
}
