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

import org.junit.Before;

/**
 *
 * @author Ivo Woltring
 */
public class FoldersTest {

    private Folders folders;

    @Before
    public void setUp() throws Exception {
        this.folders = new Folders("path");
        this.folders.add("foo");
        this.folders.add("bar");
        this.folders.add("hello");
    }

}
