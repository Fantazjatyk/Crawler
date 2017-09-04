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

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import crawler.scrapping.chain.SearchRequest;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class DomCollectorTest {

    URLCollector domCollector;

    public DomCollectorTest() {
    }

    @Before
    public void setup() {
        domCollector = new URLCollector();
    }

    /**
     * Test of collect method, of class DomCollector.
     */


    @Test
    public void testCollect(){
        Document document = mock(Document.class);
        when(document.getAllElements()).thenReturn(new Elements());
        SearchRequest ctx = spy(SearchRequest.class);
        assertNotNull(domCollector.collect(document, ctx));
    }

    /**
     * Test of accepts method, of class DomCollector.
     */
        @Test
    public void testPassJsoupDocumentToDedicatedMethod() {
        Document document = mock(Document.class);
        SearchRequest ctx = spy(SearchRequest.class);
        when(document.getAllElements()).thenReturn(new Elements());
        domCollector = spy(URLCollector.class);
        domCollector.collect(document, ctx);

       verify(domCollector, atLeastOnce()).collectUsingJsoup(any(), any());

    }

        @Test
    public void testPassHtmlUnitHtmlPageToDedicatedMethod() {
        HtmlPage page = mock(HtmlPage.class);
        SearchRequest ctx = spy(SearchRequest.class);
        when(page.getByXPath(any())).thenReturn(new ArrayList());
        domCollector = spy(URLCollector.class);
        domCollector.collect(page, ctx);

       verify(domCollector, atLeastOnce()).collectUsingHtmlUnit(any(), any());

    }

    @Test
    public void testAccepts() {
    }

}
