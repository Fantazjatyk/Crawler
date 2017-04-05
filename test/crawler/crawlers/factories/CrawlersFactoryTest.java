/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.crawlers.factories;

import crawler.crawlers.continous.ContinousGenericCrawler;
import crawler.crawlers.continous.ContinousImagesCrawler;
import crawler.crawlers.continous.ContinousSentencesCrawler;
import crawler.crawlers.continous.concurrent.ConcurrentGenericCrawler;
import crawler.crawlers.continous.concurrent.ConcurrentImagesCrawler;
import crawler.crawlers.continous.concurrent.ConcurrentSentencesCrawler;
import crawler.crawlers.oneshot.OneShotGenericCrawler;
import crawler.crawlers.oneshot.OneShotImagesCrawler;
import crawler.crawlers.oneshot.OneShotSentencesCrawler;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class CrawlersFactoryTest {

    public CrawlersFactoryTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of createSpecialCrawler method, of class CrawlersFactory.
     */
    @Test
    public void testCreateSpecialCrawler() {

        assertEquals(OneShotImagesCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.OneShot, CrawlerTarget.Images).getClass());
        assertEquals(ContinousImagesCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.Continous, CrawlerTarget.Images).getClass());
        assertEquals(ConcurrentImagesCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.Concurrent, CrawlerTarget.Images).getClass());

        assertEquals(OneShotSentencesCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.OneShot, CrawlerTarget.Sentences).getClass());
        assertEquals(ContinousSentencesCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.Continous, CrawlerTarget.Sentences).getClass());
        assertEquals(ConcurrentSentencesCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.Concurrent, CrawlerTarget.Sentences).getClass());

        assertEquals(OneShotGenericCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.OneShot, CrawlerTarget.Generic).getClass());
        assertEquals(ContinousGenericCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.Continous, CrawlerTarget.Generic).getClass());
        assertEquals(ConcurrentGenericCrawler.class, CrawlersFactory.createSpecialCrawler(CrawlerMode.Concurrent, CrawlerTarget.Generic).getClass());


    }

}
