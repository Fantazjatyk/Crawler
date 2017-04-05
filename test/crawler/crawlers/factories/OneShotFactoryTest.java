/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.crawlers.factories;

import crawler.configuration.CrawlerConfiguration;
import crawler.configuration.CrawlerParams;
import crawler.crawlers.oneshot.OneShotCrawler;
import java.util.List;
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
    public void testCreateSentencesCrawler() {
        OneShotCrawler crawler = (OneShotCrawler) new OneShotFactory().createSentencesCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        List results = (List) crawler.crawl(conf);
        assertTrue(results.size() > 1);
    }

    /**
     * Test of createImagesCrawler method, of class OneShotFactory.
     */
    @Test
    public void testCreateImagesCrawler() {
        OneShotCrawler crawler = (OneShotCrawler) new OneShotFactory().createImagesCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        List results = (List) crawler.crawl(conf);
        assertTrue(results.size() > 1);
    }

    /**
     * Test of createGenericCrawler method, of class OneShotFactory.
     */
    @Test
    public void testCreateGenericCrawler() {
        OneShotCrawler crawler = (OneShotCrawler) new OneShotFactory().createGenericCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        List results = (List) crawler.crawl(conf);
        assertTrue(results.size() > 1);
    }

    /**
     * Test of createCustomCrawler method, of class OneShotFactory.
     */
    public void testCreateCustomCrawler() {
        OneShotCrawler crawler = (OneShotCrawler) new OneShotFactory().createCustomCrawler();
        CrawlerConfiguration conf = new CrawlerConfiguration();
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        List results = (List) crawler.crawl(conf);
        assertTrue(results.size() > 1);
    }

}
