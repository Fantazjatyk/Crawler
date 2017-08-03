/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
