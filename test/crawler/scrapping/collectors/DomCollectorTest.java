/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
