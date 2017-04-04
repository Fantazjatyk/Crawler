/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.chain;

import crawler.scrapping.collectors.TextCollector;
import crawler.scrapping.collectors.URLCollector;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class SearchRequestAwareLinkTest {

    public SearchRequestAwareLinkTest() {
    }

    @Test
    public void testFowardingTaskToSuccesor() {
        TextCollector collector = spy(new TextCollector());
        URLCollector collectorSuccesor = spy(new URLCollector());

        ChainRequest request = spy(ChainRequest.class);
        collector.setSuccesor(collectorSuccesor);
        try {
            collector.foward();
        } catch (NullPointerException e) {

        }

        verify(collectorSuccesor, times(1)).handle(any(), any());
    }

    public class Link extends SearchRequestAwareLink {

        @Override
        protected Object process(Object o, ChainRequest cr) {
            return null;
        }

        @Override
        public Class[] accepts() {
            return null;
        }

        @Override
        public Class[] produces() {
            return null;
        }

    }

}
