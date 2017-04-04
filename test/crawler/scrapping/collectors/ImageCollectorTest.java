/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import crawler.configuration.CrawlerParams;
import crawler.data.ImageSource;
import crawler.scrapping.chain.context.SearchContext;
import crawler.scrapping.filters.FilterMode;
import crawler.scrapping.filters.ImageFormatFilter;
import crawler.scrapping.parsers.JsoupParser;
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
    public void testCollect() {
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchContext ctx = spy(SearchContext.class);

        ctx.getCrawlerConfiguration().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getRuntimeConfiguration().put(CrawlerParams.URL, "https://www.w3schools.com/");

        assumeTrue(document != null);

        Collection result = (Collection) collector.collect(document, ctx);
        assertTrue(result.size() > 0);
    }

    @Test
    public void testWithFilter() {

        collector.addFilter(new ImageFormatFilter(FilterMode.POST, "png"));
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchContext ctx = spy(SearchContext.class);

        ctx.getCrawlerConfiguration().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getRuntimeConfiguration().put(CrawlerParams.URL, "https://www.w3schools.com/");

        assumeTrue(document != null);

        Collection<ImageSource> result = (Collection) collector.collect(document, ctx);
        assertTrue(!result.isEmpty());
        assertTrue(result.parallelStream().filter((el) -> el.getQuessFormat() != null && !el.getQuessFormat().equals("png")).count() == 0);

    }

    @Test
    public void testIfFilterActuallyWorking() {
        int nonFilteredSize;
        int filteredSize;

        //filtered
        collector.addFilter(new ImageFormatFilter(FilterMode.POST, "png"));
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchContext ctx = spy(SearchContext.class);

        ctx.getCrawlerConfiguration().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getRuntimeConfiguration().put(CrawlerParams.URL, "https://www.w3schools.com/");

        assumeTrue(document != null);

        Collection<ImageSource> resultFiltered = (Collection) collector.collect(document, ctx);
        filteredSize = resultFiltered.size();

        setup();

        //non filtered
         Collection<ImageSource> resultNonFiltered = (Collection) collector.collect(document, ctx);
         nonFilteredSize = resultNonFiltered.size();

         assumeTrue(resultFiltered.size() > 0);
         assertTrue(!resultNonFiltered.isEmpty());
         assertTrue(nonFilteredSize > filteredSize);
    }

}
