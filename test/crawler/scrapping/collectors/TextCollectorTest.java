/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import crawler.configuration.CrawlerParams;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.chain.context.SearchContext;
import crawler.scrapping.parsers.JsoupParser;
import java.io.IOException;
import java.util.Collection;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class TextCollectorTest {

    TextCollector collector = new TextCollector();

    public TextCollectorTest() {
    }

    /**
     * Test of produces method, of class TextCollector.
     */
    @Test
    public void testProduces() throws IOException {
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        collector = spy(TextCollector.class);
        SearchRequest ctx = spy(SearchRequest.class);
        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        assumeTrue(document != null);
        Collection result = (Collection) collector.collect(document, ctx);
        assertTrue(result.size() > 1);
    }

}
