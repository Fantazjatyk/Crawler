/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import crawler.configuration.CrawlerConfiguration;
import crawler.configuration.CrawlerParams;
import crawler.data.Adress;
import crawler.scrapping.chain.ChainRequest;
import crawler.scrapping.chain.context.SearchContext;
import crawler.scrapping.chain.context.SearchRuntimeContext;
import crawler.scrapping.collectors.URLCollector;
import crawler.scrapping.parsers.JsoupParser;
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
    public void testCollect(){
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
                ChainRequest rq = spy(ChainRequest.class);
        CrawlerConfiguration conf = new CrawlerConfiguration();
        SearchRuntimeContext rctx = spy(SearchRuntimeContext.class);
        when(rctx.getDomAdress()).thenReturn(new Adress("https://www.w3schools.com/"));
        conf.put(CrawlerParams.URL, "https://www.w3schools.com/");
        SearchContext sc = new SearchContext();
        sc.setConfiguration(conf);
        sc.setRuntimeContext(rctx);
        rq.setContext(sc);

        assumeTrue(document != null);

        Collection result = (Collection) urlCollector.collect(document, sc);
        assertTrue(result.size() > 0);
    }
    /**
     * Test of produces method, of class URLCollector.
     */


}
