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

/**
 *
 * @author Ivo Woltring
 */
public class Epub {
    private final String filename;
    private String cover;
    private String title;
    private String description;

    public Epub(final String filename) {
        this.filename = filename;
    }

    public String getCover() {
        return "data:image/jpg;base64, " + cover;
    }

    public void setCover(final String cover) {
        this.cover = cover;
    }

    public String getFilename() {
        return filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
