/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
