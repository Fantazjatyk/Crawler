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
import crawler.scrapping.chain.context.SearchContext;
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

    SentenceCollector collector;

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
        collector = new SentenceCollector(new String[]{"ala"});
        List input = new ArrayList() {
            {
                add(new Text("ala ma kota a kot ma psa", new Source("")));
                add(new Text("ala ma kota a pies ma kota", new Source("")));
            }
        };
        SearchRequest rq = new SearchRequest();
        rq.getInitParams().put(CrawlerParams.URL, "bla bla");
        rq.getInitParams().put(CrawlerParams.CURRENT_URL, "sdsd");
        Collection<Sentence> result = (Collection) collector.collect(input, rq);
        assertEquals(2, result.size());

    }

    /**
     * Test of produces method, of class SentenceCollector.
     */
    @Test
    public void testProduces() {
    }

}
