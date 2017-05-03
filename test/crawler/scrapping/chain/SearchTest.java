/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.chain;

import crawler.scrapping.collectors.ImageCollector;
import crawler.scrapping.collectors.SentenceCollector;
import crawler.scrapping.collectors.TextCollector;
import crawler.scrapping.collectors.URLCollector;
import crawler.scrapping.parsers.JsoupParser;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchTest {

    @Spy
    Search search;

    public SearchTest() {
    }

    /**
     * Test of sortLinks method, of class Search.
     */
    @Test
    public void testSortLinks() {
        URLCollector url = spy(new URLCollector());
        TextCollector text = spy(new TextCollector());
        SentenceCollector sentences = spy(new SentenceCollector(new String[]{"spy"}));
        ImageCollector images = spy(new ImageCollector());

        search.getLinks().add(url);
        search.getLinks().add(text);
        search.getLinks().add(sentences);
        search.getLinks().add(images);
        search.sortLinks();

        assertSame(text.getSuccesor().get(), sentences);
        assertFalse(url.getSuccesor().isPresent());
        assertFalse(sentences.getSuccesor().isPresent());
        assertFalse(images.getSuccesor().isPresent());

    }

    @Test
    public void testIsEveryoneCalledInChain() throws IOException {
        URLCollector url = spy(new URLCollector());
        TextCollector text = spy(new TextCollector());
        SentenceCollector sentences = spy(new SentenceCollector(new String[]{}));
        ImageCollector images = spy(new ImageCollector());

        search.getLinks().add(url);
        search.getLinks().add(text);
        search.getLinks().add(sentences);
        search.getLinks().add(images);

        Document document = new JsoupParser().parse("https://www.w3schools.com/");

        assumeTrue(document != null);
        SearchRequest rq = new SearchRequest();

        try {
            search.start(document, rq);
        } catch (NullPointerException e) {

        }

        verify(url, times(1)).handle(any(), any());
        verify(text, times(1)).handle(any(), any());
        verify(sentences, times(1)).handle(any(), any());
        verify(images, times(1)).handle(any(), any());
    }


    @Test
    public void testAreCompatibile() {
        TextCollector text = spy(new TextCollector());
        SentenceCollector sentences = spy(new SentenceCollector(new String[]{}));
        ImageCollector images = spy(new ImageCollector());

        assertTrue(search.areCompatibile(text, sentences));
        assertFalse(search.areCompatibile(images, sentences));

    }

}
