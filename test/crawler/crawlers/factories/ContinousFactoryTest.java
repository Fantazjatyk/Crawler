/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.crawlers.factories;

import crawler.configuration.CrawlerConfiguration;
import crawler.configuration.CrawlerParams;
import crawler.crawlers.continous.ContinousCrawler;
import crawler.crawlers.continous.FlexibleContinousCrawler;
import crawler.crawlers.continous.concurrent.FlexibleConcurrentCrawler;
import crawler.scrapping.collectors.ImagesCollector;
import crawler.scrapping.collectors.SentencesCollector;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class ContinousFactoryTest {

    public ContinousFactoryTest() {
    }

    int time = 5;

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of createSentencesCrawler method, of class ContinousFactory.
     */

    @Test
    public void testFlexibleCrawler() {
        FlexibleContinousCrawler crawler = new FlexibleContinousCrawler();
        SentencesCollector c = new SentencesCollector();
        c.setTarget("Learn");
        ImagesCollector i = new ImagesCollector();
        crawler.configure()
                .initUrl("https://www.w3schools.com/")
                .timeLimit(time)
                .addCollector(c)
                .addCollector(i);
        crawler.start();
        assertTrue(crawler.getResults().totalSize() > 1);
        assertTrue(c.getResults().size() > 0);
        assertTrue(i.getResults().size() > 0);
        assertTrue(crawler.getMovement().getCrawledAdresses().size() > 1);
    }

        @Test
    public void testFlexibleCrawler_withoutDuplicates() {
        FlexibleContinousCrawler crawler = new FlexibleContinousCrawler();
        SentencesCollector c = new SentencesCollector();
        c.setTarget("Learn");
        ImagesCollector i = new ImagesCollector();
        crawler.configure()
                .initUrl("https://www.w3schools.com/")
                .timeLimit(time)
                .addCollector(c)
                .addCollector(i);
        crawler.start();
        assertTrue(crawler.getResults().totalSize() > 1);
        assertTrue(c.getResultsWithoutDuplicates().size() > 0);
        assertTrue(i.getResults().size() > 0);
        assertTrue(crawler.getMovement().getCrawledAdresses().size() > 1);
    }


}
