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

import crawler.scrapping.exceptions.IllegalInputException;
import crawler.utils.ClassSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class Search extends SearchRequestAwareChain {

    @Override
    protected Collection each(Object expecting, SearchRequestAwareLink link, ChainRequest rq, ChainResponse rs) throws IllegalInputException {
        Collection c = new ArrayList();
        Object result = new Object();
        try {
            link.handle(rq, rs);
        } catch (NullPointerException e) {
            Logger.getLogger(this.getClass().getName()).warning(e.getStackTrace()[0].toString());
        }
        if (result instanceof Collection) {
            c.addAll((Collection) result);
        } else {
            c.add(result);
        }
        return c;
    }

    @Override
    protected void body(Object expecting, ChainRequest rq, ChainResponse rs) {
        onStart();
        while (!links.isEmpty()) {
            SearchRequestAwareLink link = null;
            try {
                link = links.peek();

                links.remove();
                if(!link.accepts().contains(expecting.getClass()))
                    continue;

                each(expecting, link, rq, rs);
            } catch (IllegalInputException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        }
        onEnd();
    }

    @Override
    protected void onEnd() {
    }

    @Override
    protected void onStart() {
    }

    @Override
    public void sortLinks() {
        ConcurrentHashMap<Class[], SearchRequestAwareLink> producers = new ConcurrentHashMap(super.getLinks().parallelStream().collect(Collectors.toMap((el) -> el.produces(), (el2) -> el2)));

        producers.entrySet().parallelStream().forEach((entry) -> {
            producers.entrySet().stream().forEach((entry2) -> {
                if (areCompatibile(entry2.getValue(), entry.getValue())) {
                    wire(entry2.getValue(), entry.getValue());
                }
            });
        }
        );
        super.getLinks().clear();;
        super.getLinks().addAll(producers.values());
    }

    private void wire(SearchRequestAwareLink produce, SearchRequestAwareLink accept) {
        produce.setSuccesor(accept);
    }

    protected final boolean areCompatibile(SearchRequestAwareLink produce, SearchRequestAwareLink accept) {
        ClassSet produces = produce.produces();
        ClassSet accepts = accept.accepts();

        return Arrays.equals(produces.toArray(), accepts.toArray());
    }

}
