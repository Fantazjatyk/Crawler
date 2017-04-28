/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import crawler.configuration.CrawlerParams;
import crawler.data.ImageSource;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.chain.context.SearchContext;
import crawler.scrapping.filters.FilterMode;
import crawler.scrapping.filters.ImageFormatFilter;
import crawler.scrapping.parsers.JsoupParser;
import java.io.IOException;
import java.util.Collection;
import org.jsoup.nodes.Document;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.spy;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class ImageCollectorTest {

    ImageCollector collector;

    public ImageCollectorTest() {
    }

    @Before
    public void setup() {
        this.collector = new ImageCollector();
    }

    /**
     * Test of produces method, of class ImageCollector.
     */
    @Test
    public void testCollect() throws IOException {
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchRequest ctx = spy(SearchRequest.class);

        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

        assumeTrue(document != null);

        Collection result = collector.collect(document, ctx);
        assertTrue(result.size() > 0);
    }

    @Test
    public void testWithFilter() throws IOException {

        collector.addFilter(new ImageFormatFilter(FilterMode.POST, "png"));
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchRequest ctx = spy(SearchRequest.class);

        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

        assumeTrue(document != null);

        Collection<ImageSource> result = (Collection) collector.collectAndFilter(document, ctx);
        assertTrue(!result.isEmpty());
        assertTrue(result.parallelStream().filter((el) -> el.getQuessFormat() != null && !el.getQuessFormat().equals("png")).count() == 0);

    }

    @Test
    public void testIfFilterActuallyWorking() throws IOException {
        int nonFilteredSize;
        int filteredSize;

        //filtered
        collector.addFilter(new ImageFormatFilter(FilterMode.POST, "png"));
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchRequest ctx = spy(SearchRequest.class);

        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

        assumeTrue(document != null);

        Collection<ImageSource> resultFiltered = (Collection) collector.collectAndFilter(document, ctx);
        filteredSize = resultFiltered.size();

        setup();

        //non filtered
         Collection<ImageSource> resultNonFiltered = (Collection) collector.collectAndFilter(document, ctx);
         nonFilteredSize = resultNonFiltered.size();

         assumeTrue(resultFiltered.size() > 0);
         assertTrue(!resultNonFiltered.isEmpty());
         assertTrue(nonFilteredSize > filteredSize);
    }

}
