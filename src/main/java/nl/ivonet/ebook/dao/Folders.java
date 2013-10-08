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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivo Woltring
 */
@SuppressWarnings("UnusedDeclaration")
public class Folders {
    private final List<String> folders;
    private String path;

    public Folders() {
        this.folders = new ArrayList<>();
    }

    public void add(final String name) {
        this.folders.add(name);
    }

    public List<String> getFolders() {
        return this.folders;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Folders{");
        sb.append("folders=").append(this.folders);
        sb.append('}');
        return sb.toString();
    }
}
