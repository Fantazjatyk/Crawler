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
package crawler.crawlers.factories;

import crawler.configuration.CrawlerConfiguration;
import crawler.configuration.CrawlerParams;
import crawler.crawlers.continous.concurrent.FlexibleConcurrentCrawler;
import crawler.crawlers.oneshot.FlexibleOneShotCrawler;
import crawler.crawlers.oneshot.OneShotCrawler;
import crawler.scrapping.collectors.ImagesCollector;
import crawler.scrapping.collectors.SentencesCollector;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class OneShotFactoryTest {

    public OneShotFactoryTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of createSentencesCrawler method, of class OneShotFactory.
     */
    @Test
    public void testFlexibleCrawler() {
        FlexibleOneShotCrawler crawler = new FlexibleOneShotCrawler();
        SentencesCollector c = new SentencesCollector();
        c.setTarget("Learn");
        ImagesCollector i = new ImagesCollector();
        

        crawler.configure()
                .initUrl("https://www.w3schools.com/")
                .addCollector(c)
                .addCollector(i);
        crawler.start();
        assertTrue(crawler.getResults().totalSize() > 1);
        assertTrue(c.getResults().size() > 0);
        assertTrue(i.getResults().size() > 0);
    }

}
