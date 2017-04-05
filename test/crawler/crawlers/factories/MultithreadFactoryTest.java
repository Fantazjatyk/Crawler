/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.crawlers.factories;

import crawler.configuration.CrawlerConfiguration;
import crawler.configuration.CrawlerParams;
import crawler.crawlers.continous.concurrent.ConcurrentCrawler;
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

    /**
     * Test of createSentencesCrawler method, of class MultithreadFactory.
     */

        // zwracanie wyników itp trwa 20-30% początkowego limitu czasu.
    @Test(timeout = 10000)
    public void testCreateSentencesCrawler() {
        ConcurrentCrawler crawler = (ConcurrentCrawler) new MultithreadFactory().createSentencesCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        conf.put(CrawlerParams.TIME_LIMIT, 5);
        crawler.crawl(conf);
        assertTrue(crawler.getResults().size() > 1);
        assertTrue(crawler.getMovement().getCrawledAdresses().size() > 1);
    }

    /**
     * Test of createImagesCrawler method, of class MultithreadFactory.
     */
    @Test(timeout = 10000)
    public void testCreateImagesCrawler() {
        ConcurrentCrawler crawler = (ConcurrentCrawler) new MultithreadFactory().createImagesCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        conf.put(CrawlerParams.TIME_LIMIT, 5);
        crawler.crawl(conf);
        assertTrue(crawler.getResults().size() > 1);
        assertTrue(crawler.getMovement().getCrawledAdresses().size() > 1);
    }

    /**
     * Test of createGenericCrawler method, of class MultithreadFactory.
     */
    @Test(timeout = 10000)
    public void testCreateGenericCrawler() {
        ConcurrentCrawler crawler = (ConcurrentCrawler) new MultithreadFactory().createGenericCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        conf.put(CrawlerParams.TIME_LIMIT, 5);
        crawler.crawl(conf);
        assertTrue(crawler.getResults().size() > 1);
        assertTrue(crawler.getMovement().getCrawledAdresses().size() > 1);
    }

    /**
     * Test of createCustomCrawler method, of class MultithreadFactory.
     */
    public void testCreateCustomCrawler() {
        ConcurrentCrawler crawler = (ConcurrentCrawler) new MultithreadFactory().createCustomCrawler();

    }

}
