/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.crawlers.factories;

import crawler.configuration.CrawlerConfiguration;
import crawler.configuration.CrawlerParams;
import crawler.crawlers.continous.ContinousCrawler;
import crawler.crawlers.continous.ContinousSentencesCrawler;
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
    public void testCreateSentencesCrawler() {
        ContinousCrawler crawler = (ContinousCrawler) new ContinousFactory().createSentencesCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        conf.put(CrawlerParams.SENTENCES, new String[]{"javascript"});
        conf.put(CrawlerParams.TIME_LIMIT, time);
        crawler.start(conf);
        assertTrue(crawler.getResults().totalSize() > 1);
        assertTrue(((ContinousSentencesCrawler)crawler).getFoundSentences().size() > 0);
        assertTrue(crawler.getMovement().getCrawledAdresses().size() > 1);
    }

    /**
     * Test of createImagesCrawler method, of class ContinousFactory.
     */

    @Test
    public void testCreateImagesCrawler() {
        ContinousCrawler crawler = (ContinousCrawler) new ContinousFactory().createImagesCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
         conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        conf.put(CrawlerParams.TIME_LIMIT, time);
        crawler.start(conf);
        assertTrue(crawler.getResults().totalSize() > 1);
        assertTrue(crawler.getMovement().getCrawledAdresses().size() > 1);
    }

    /**
     * Test of createGenericCrawler method, of class ContinousFactory.
     */

    @Test
    public void testCreateGenericCrawler() {
        ContinousCrawler crawler = (ContinousCrawler) new ContinousFactory().createGenericCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        conf.put(CrawlerParams.TIME_LIMIT, 20);
        crawler.start(conf);
        assertTrue(crawler.getResults().totalSize() > 1);
        assertTrue(crawler.getMovement().getCrawledAdresses().size() > 1);
    }

    /**
     * Test of createCustomCrawler method, of class ContinousFactory.
     */

    public void testCreateCustomCrawler() {

    }

}
