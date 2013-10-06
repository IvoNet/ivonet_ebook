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

package nl.ivonet.ebook.model;

/**
 *
 * @author Ivo Woltring
 */

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "", propOrder = {
//    "title"
//})

//@XmlRootElement(name = "Epub", namespace = "http://things.ivonet.nl")
public class Epub {
    //    @XmlElement(namespace = "http://things.ivonet.nl")
    private String title;

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
