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
import crawler.scrapping.chain.ChainRequest;
import crawler.scrapping.filters.Filter;
import crawler.scrapping.filters.FilterMode;
import crawler.scrapping.chain.SearchRequestAwareLink;
import crawler.scrapping.chain.context.SearchContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.select.Elements;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Valid
public abstract class Collector extends SearchRequestAwareLink {

    public static Class[] SECURED_TYPES = new Class[]{Elements.class, HtmlPage.class};
    private List<Filter> preFilters = new ArrayList();
    private List<Filter> postFilters = new ArrayList();

    @Override
    protected final Object process(Object o, ChainRequest cr) {
        Object filteredPost = new ArrayList();
        if (o instanceof Collection) {
            filteredPost = applyPreFilters((Collection) o);
        } else {
            filteredPost = o;
        }
        Collection collected = (Collection) collect(filteredPost, (SearchContext) cr.getContext());
        Collection filteredCollected = applyPostFilters(collected);
        return filteredCollected;
    }

    public abstract Object collect(Object o, SearchContext ctx);

    public void addFilter(Filter f) {
        FilterMode mode = f.getMode();

        if (mode == FilterMode.POST) {
            addPostFilter(f);
        } else if (mode == FilterMode.PRE) {
            addPreFilter(f);
        }
    }

    private void addPostFilter(Filter f) {
        this.postFilters.add(f);
    }

    private void addPreFilter(Filter f) {
        this.preFilters.add(f);
    }

    protected Collection applyPostFilters(Collection post) {
        return applyFilters(post, postFilters);
    }

    protected Collection applyPreFilters(Collection pre) {
        return applyFilters(pre, preFilters);
    }

    protected Collection applyFilters(Collection o, Collection<Filter> filters) {
        if (filters.isEmpty()) {
            return o;
        }
        List result = new ArrayList();
        filters.parallelStream().forEach((el) -> {
            result.addAll((Collection) o.parallelStream().filter(el).collect(Collectors.toList()));
        });
        o.clear();
        o.addAll(result);
        return o;
    }
}
