/* 
 * The MIT License
 *
 * Copyright 2017 Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package crawler.scrapping.filters;

import crawler.data.ImageSource;
import crawler.data.Source;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class ImageFormatFilterTest {
 ImageFormatFilter filter;
    public ImageFormatFilterTest() {
    }

    @Before
    public void setup(){
       filter  = new ImageFormatFilter("png");
    }
    /**
     * Test of test method, of class ImageFormatFilter.
     */
    @Test
    public void testImageFormatFilter_Test_EmptyWrapper() {
        assertFalse(filter.test(new ImageSource("", new Source(""))));
    }

        @Test
    public void testImageFormatFilter_Test_Null() {
        assertFalse(filter.test(null));
    }

    @Test
    public void testTestOptimistic(){
        filter  = new ImageFormatFilter("jpg");
        assertTrue(filter.test(new ImageSource("https://www.w3schools.com/css/trolltunga.jpg", new Source("https://www.w3schools.com/css/trolltunga.jpg"))));
    }

    @Test
    public void testTestPesimistic(){
        filter  = new ImageFormatFilter("png");
        assertFalse(filter.test(new ImageSource("https://www.w3schools.com/css/trolltunga.jpg", new Source("https://www.w3schools.com/css/trolltunga.jpg"))));
    }

}
