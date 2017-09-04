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
import crawler.scrapping.chain.SearchRequest;
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
