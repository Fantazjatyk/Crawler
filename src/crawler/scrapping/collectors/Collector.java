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
import crawler.data.DataPostProcessor;
import crawler.scrapping.chain.ChainRequest;
import crawler.scrapping.chain.ChainResponse;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.chain.SearchRequestAwareLink;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.select.Elements;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Valid
public abstract class Collector<Produces extends Collection, Expects> extends SearchRequestAwareLink {

    public static final Class[] SECURED_TYPES = new Class[]{Elements.class, HtmlPage.class};

    @Override
    protected void doChain(ChainRequest rq, ChainResponse rs) {

        Collection expected = rs.getResults().getMergedGroup(accepts());

        if (expected.isEmpty()) {
            return;
        }

        Expects o = expected.size() == 1 ? (Expects) expected.stream().findAny().get() : (Expects) expected;
        Produces prod = null;
        SearchRequest srq = (SearchRequest) rq;

        try {
            prod = collect(o, srq);
        } catch (ClassCastException e) {
            prod = catchClassCastException(o, srq);
        }

        if (prod != null) {
            rs.getResults().putAll(prod);
        }

    }

    public Produces catchClassCastException(Expects data, SearchRequest rq) {
        Produces prod = null;
        Collection collection = new ArrayList();
        try {
            if (data instanceof Collection && !collection.isEmpty()) {
                collection = (Collection) data;
                prod = collect((Expects) collection.stream().findAny().get(), rq);
            } else {
                collection.add(data);
                prod = collect((Expects) collection, rq);
            }
        } catch (ClassCastException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, e);
            // it's over.
        }
        return prod;
    }

    protected abstract Produces collect(Expects data, SearchRequest ctx);

}
