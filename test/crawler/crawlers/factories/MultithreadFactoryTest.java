/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.crawlers.factories;

import crawler.configuration.CrawlerConfiguration;
import crawler.configuration.CrawlerParams;
import crawler.crawlers.continous.concurrent.ConcurrentCrawler;
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
public class MultithreadFactoryTest {

    public MultithreadFactoryTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    int time = 5;

    /**
     * Test of createSentencesCrawler method, of class MultithreadFactory.
     */
    // zwracanie wyników itp trwa 20-30% początkowego limitu czasu.
    //@Test(timeout = 5000)


    @Test
    public void testFlexibleCrawler() {
        FlexibleConcurrentCrawler crawler = new FlexibleConcurrentCrawler();
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

}
