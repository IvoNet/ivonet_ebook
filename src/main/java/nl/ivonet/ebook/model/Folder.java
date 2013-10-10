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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivo Woltring
 */
@SuppressWarnings("UnusedDeclaration")
public class Folder {
    private final List<String> folders;
    private final List<String> files;
    private String path;

    public Folder(final String path) {
        this.folders = new ArrayList<>();
        this.files = new ArrayList<>();
        this.path = path;
        if (path != null && !path.isEmpty()) {
            folders.add("/");
            folders.add("..");
        }
    }

    public void addFolder(final String name) {
        this.folders.add(name);
    }

    public void addFile(final String name) {
        files.add(name);
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

    public List<String> getFiles() {
        return files;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Folder{");
        sb.append("files=").append(files);
        sb.append(", folders=").append(folders);
        sb.append(", path='").append(path).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
