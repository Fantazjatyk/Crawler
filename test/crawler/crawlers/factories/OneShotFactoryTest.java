/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
