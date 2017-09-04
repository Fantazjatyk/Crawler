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
import crawler.data.Sentence;
import crawler.data.Source;
import crawler.data.Text;
import crawler.scrapping.chain.SearchRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class SentenceCollectorTest {

    SentencesCollector collector;

    public SentenceCollectorTest() {
    }

    /**
     * Test of getFilteringSentences method, of class SentenceCollector.
     */
    @Test
    public void testGetFilteringSentences() {
    }

    /**
     * Test of collect method, of class SentenceCollector.
     */
    @Test
    public void testCollectTargetGiven() {
        collector = new SentencesCollector();
        collector.setTarget("ala", "Szwecji", "Aha");
        List input = new ArrayList() {
            {
                add(new Text("ala ma kota a kot ma psa. Aha. Aha ta Ala.", new Source("")));
                add(new Text("ala ma kota a pies ma kota", new Source("")));
                add(new Text("No to cóż, że ze Szwecji... szwecji", new Source("")));
            }
        };
        SearchRequest rq = new SearchRequest();
        rq.getInitParams().put(CrawlerParams.URL, "bla bla");
        rq.getInitParams().put(CrawlerParams.CURRENT_URL, "sdsd");
        Collection<Sentence> result = (Collection) collector.collect(input, rq);
        assertEquals(5, result.size());

    }

    @Test
    public void testCollectTargetIgnoreCase() {
        collector = new SentencesCollector();
        collector.setIgnoreCase(true);
        collector.setTarget("ala", "Szwecji", "Aha");
        List input = new ArrayList() {
            {
                add(new Text("ala ma kota a kot ma psa. Aha. Aha ta Ala.", new Source("")));
                add(new Text("ala ma kota a pies ma kota", new Source("")));
                add(new Text("No to cóż, że ze Szwecji... szwecji", new Source("")));
            }
        };
        SearchRequest rq = new SearchRequest();
        rq.getInitParams().put(CrawlerParams.URL, "bla bla");
        rq.getInitParams().put(CrawlerParams.CURRENT_URL, "sdsd");
        Collection<Sentence> result = (Collection) collector.collect(input, rq);
        assertEquals(7, result.size());
    }

    @Test
    public void testCollectTargetWithoutDuplicates() {
        SentencesCollector collector = new SentencesCollector();
        collector.setTarget("ala", "Szwecji", "Aha");
        List input = new ArrayList() {
            {
                add(new Text("ala ma kota a kot ma psa. Aha. Aha ta Ala, Aha...", new Source("")));
                add(new Text("ala ma kota a pies ma kota", new Source("")));
                add(new Text("No to cóż, że ze Szwecji... szwecji", new Source("")));
                add(new Text("No to cóż, że ze Szwecji... szwecji", new Source("")));
            }
        };
        SearchRequest rq = new SearchRequest();
        rq.getInitParams().put(CrawlerParams.URL, "bla bla");
        rq.getInitParams().put(CrawlerParams.CURRENT_URL, "sdsd");
        collector.collect(input, rq);
        Collection<Sentence> result = collector.getResultsWithoutDuplicates();
        assertEquals(3, result.size());
        assertTrue(result.stream().filter(el -> el.getSources().stream().findAny().get().getTimes() == 2).count() == 2);

    }

    /**
     * Test of produces method, of class SentenceCollector.
     */
    @Test
    public void testProduces() {
    }

}
