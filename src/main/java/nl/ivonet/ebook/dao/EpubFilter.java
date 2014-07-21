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

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

/**
 *
 * @author Ivo Woltring
 */
public class EpubFilter implements DirectoryStream.Filter<Path> {

    private static final String EPUB = ".epub";

    @Override
    public boolean accept(final Path entry) throws IOException {
        return Files.isRegularFile(entry) && isEpub(entry);
    }

    private boolean isEpub(final Path entry) {
        return entry.getFileName().toString().toLowerCase(Locale.US).endsWith(EPUB);
    }
}