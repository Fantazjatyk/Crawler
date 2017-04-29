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
import crawler.data.Text;
import crawler.scrapping.chain.ChainRequest;
import crawler.scrapping.chain.ChainResponse;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.filters.Filter;
import crawler.scrapping.filters.FilterMode;
import crawler.scrapping.chain.SearchRequestAwareLink;
import crawler.scrapping.chain.context.SearchContext;
import crawler.utils.ClassSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jsoup.select.Elements;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Valid
public abstract class Collector<Produces extends Collection, Expects> extends SearchRequestAwareLink {

    public static Class[] SECURED_TYPES = new Class[]{Elements.class, HtmlPage.class};

    @Override
    protected void doChain(ChainRequest rq, ChainResponse rs) {

        Optional expected = rs.getResults().getOneOrMany(accepts());
        if (!expected.isPresent()) {
            return;
        }

        Expects o = (Expects) expected.get();
        Produces prod = null;
        SearchRequest srq = (SearchRequest) rq;
        /*
        Zmienna expected zwrrapowana przez Optional może być kolekcją albo pojedyńczym obiektem.
        Klasy potomne po Collector akceptują tylko kolekcje, ale z kolei klasa potomna DomCollector akceptuje tylko pojedyńczy obiekt.
        Czasami prowadzi to do wyrzucenia wyjątku ClassCastException.
         */
        try {
            prod = collect(o, srq);
        } catch (ClassCastException e) {
            catchClassCastException(o, srq);
        }
        if (prod instanceof Collection) {
            rs.getResults().addAll(prod);
        } else {
            rs.getResults().add(prod);
        }
    }

    public Produces catchClassCastException(Expects data, SearchRequest rq) {
        Produces prod = null;
        Collection collection;
        try {
            if (data instanceof Collection) {
                collection = (Collection) data;
                prod = collect((Expects) collection.stream().findAny().get(), rq);
            } else {
                collection = new ArrayList();
                collection.add(data);
                prod = collect((Expects) collection, rq);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            // it's over.
        }
        return prod;
    }

    protected abstract Produces collect(Expects data, SearchRequest ctx);

}
