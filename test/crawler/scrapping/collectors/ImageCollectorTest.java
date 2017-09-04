/* 
 * The MIT License
 *
 * Copyright 2017 Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package crawler.scrapping.collectors;

import crawler.configuration.CrawlerParams;
import crawler.data.ImageSource;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.filters.ImageFormatFilter;
import crawler.scrapping.parsers.JsoupParser;
import java.io.IOException;
import java.util.Collection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class ImageCollectorTest {

    ImagesCollector collector;

    public ImageCollectorTest() {
    }

    @Before
    public void setup() {
        this.collector = new ImagesCollector();
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

        collector.addPostFilter(new ImageFormatFilter("png"));
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchRequest ctx = spy(SearchRequest.class);

        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

        assumeTrue(document != null);

        Collection<ImageSource> result = (Collection) collector.collect(document, ctx);
        assertTrue(!result.isEmpty());
        assertTrue(result.parallelStream().filter((el) -> el.getQuessFormat() != null && !el.getQuessFormat().equals("png")).count() == 0);

    }

    @Test
    public void testWithoutDuplicates() {
        String html = "<img src=\"https://www.w3schools.com/css/img_fjords.jpg\"/>"
                + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/280px-PNG_transparency_demonstration_1.png\"/>"
                + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/8/87/Google_Chrome_icon_%282011%29.png\"/>"
                + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/8/87/Google_Chrome_icon_%282011%29.png\"/>"
                + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/8/87/Google_Chrome_icon_%282011%29.png\"/>";

        Document doc = Jsoup.parse(html);

        SearchRequest ctx = Mockito.spy(SearchRequest.class);
        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

        assumeTrue(doc != null);
        collector.collect(doc, ctx);
        Collection<ImageSource> result = collector.getResultsWithoutDuplicates();
        assertTrue(!result.isEmpty());
        assertEquals(3, result.size());
        assertTrue(result.stream().filter(el->el.getSources().stream().findAny().get().getTimes() == 3).count() == 1);
    }

    @Test
    public void testWithFormats_all() {
        String html = "<img src=\"https://www.w3schools.com/css/img_fjords.jpg\"/>"
                + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/280px-PNG_transparency_demonstration_1.png\"/>"
                + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/8/87/Google_Chrome_icon_%282011%29.png\"/>";

        Document doc = Jsoup.parse(html);

        SearchRequest ctx = Mockito.spy(SearchRequest.class);
        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

        assumeTrue(doc != null);
        Collection<ImageSource> result = (Collection) collector.collect(doc, ctx);
        assertTrue(!result.isEmpty());
        assertEquals(3, result.size());
    }

    @Test
    public void testWithFormats_filtered() {
        String html = "<img src=\"https://www.w3schools.com/css/img_fjords.jpg\"/>"
                + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/280px-PNG_transparency_demonstration_1.png\"/>"
                + "<img src=\"https://upload.wikimedia.org/wikipedia/commons/8/87/Google_Chrome_icon_%282011%29.png\"/>";

        Document doc = Jsoup.parse(html);

        SearchRequest ctx = Mockito.spy(SearchRequest.class);
        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

        assumeTrue(doc != null);

        collector.setFormats("png");
        Collection<ImageSource> result = (Collection) collector.collect(doc, ctx);
        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    public void testIfFilterActuallyWorking() throws IOException {
        int nonFilteredSize;
        int filteredSize;

        //filtered
        collector.addPostFilter(new ImageFormatFilter("png"));
        Document document = new JsoupParser().parse("https://www.w3schools.com/");
        SearchRequest ctx = spy(SearchRequest.class);

        ctx.getInitParams().put(CrawlerParams.URL, "https://www.w3schools.com/");
        ctx.getInitParams().put(CrawlerParams.CURRENT_URL, "https://www.w3schools.com/");

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
