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

import nl.ivonet.ebook.model.Folder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Ivo Woltring
 */
public class DirectoryTest {
    private String dir;
    private Directory directory;

    @Before
    public void setUp() throws Exception {
        this.dir = "/Volumes/Data/Books/ebook/IvoNetLibrary/";
        this.directory = new Directory();
    }

    @Test
    @Ignore
    public void testFolders() throws Exception {
        final Folder folder = this.directory.folder(this.dir);
        System.out.println("folder = " + folder);
    }
}
