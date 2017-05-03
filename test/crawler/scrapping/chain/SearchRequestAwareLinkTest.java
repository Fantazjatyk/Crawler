/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.chain;

import crawler.scrapping.collectors.TextCollector;
import crawler.scrapping.collectors.URLCollector;
import crawler.utils.ClassSet;
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

        ChainRequest request = spy(SearchRequest.class);
        ChainResponse response = spy(ChainResponse.class);
        collector.setSuccesor(collectorSuccesor);
        try {
            collector.foward(request, response);
        } catch (NullPointerException e) {

        }

        verify(collectorSuccesor, times(1)).handle(any(), any());
    }

    public class Link extends SearchRequestAwareLink {

        @Override
        protected void doChain(ChainRequest rq, ChainResponse rs) {

        }

        @Override
        public ClassSet produces() {
            return null;
        }

        @Override
        public ClassSet accepts() {
            return null;
        }



    }

}
