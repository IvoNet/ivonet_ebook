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

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ivo Woltring
 */
public class ImageBase64Test {
    private ImageBase64 imageBase64;

    @Before
    public void setUp() throws Exception {
        imageBase64 = new ImageBase64();
    }

    @Test
    public void testEncodeToString() throws Exception {
        final String jpg = imageBase64
                .encodeToString("/Users/ivonet/dev/ebook/ivonet-ebook/src/main/resources/ebook.jpg", "jpg");
        System.out.println(jpg);
    }
}
