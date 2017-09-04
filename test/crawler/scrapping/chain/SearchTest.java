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
package crawler.scrapping.chain;

import crawler.scrapping.collectors.ImagesCollector;
import crawler.scrapping.collectors.SentencesCollector;
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
        SentencesCollector sentences = spy(SentencesCollector.class);
        sentences.setTarget("spy");
        ImagesCollector images = spy(new ImagesCollector());

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
        SentencesCollector sentences = spy(new SentencesCollector());
        ImagesCollector images = spy(new ImagesCollector());

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
        SentencesCollector sentences = spy(new SentencesCollector());
        ImagesCollector images = spy(new ImagesCollector());

        assertTrue(search.areCompatibile(text, sentences));
        assertFalse(search.areCompatibile(images, sentences));

    }

}
