/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import crawler.configuration.CrawlerParams;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.chain.context.SearchContext;

import crawler.scrapping.collectors.URLCollector;
import crawler.scrapping.parsers.JsoupParser;
import java.io.IOException;
import java.util.Collection;
import org.jsoup.nodes.Document;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class URLCollectorTest {

    URLCollector urlCollector = new URLCollector();

    public URLCollectorTest() {
    }

    /**
     * Test of isBelongsToSearchedDomain method, of class URLCollector.
     */
    @Test
    public void testIsBelongsToSearchedDomain() {
    }

    @Test
    public void testCollect() throws IOException{
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchRequest ctx = spy(SearchRequest.class);

        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

        assumeTrue(document != null);

        Collection result = (Collection) urlCollector.collect(document, ctx);
        assertTrue(result.size() > 0);
    }
    /**
     * Test of produces method, of class URLCollector.
     */


}
