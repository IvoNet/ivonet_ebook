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

package nl.ivonet.ebook.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivo Woltring
 */
@SuppressWarnings("UnusedDeclaration")
public class Folder {
    private final List<String> folders;
    private final List<Epub> epubs;
    private String path;

    public Folder(final String path) {
        this.folders = new ArrayList<>();
        this.epubs = new ArrayList<>();
        this.path = path;
        if (StringUtils.isNotEmpty(path)) {
            folders.add("/");
            folders.add("..");
        }
    }

    public void addFolder(final String name) {
        this.folders.add(name);
    }

    public void addBook(final Epub epub) {
        epubs.add(epub);
    }

    public List<String> getFolders() {
        return this.folders;
    }

    public String getPath() {
        return this.path.replace("/", "+");
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public List<Epub> getEpubs() {
        return epubs;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Folder{");
        sb.append("files=").append(epubs);
        sb.append(", folder=").append(folders);
        sb.append(", path='").append(path).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
