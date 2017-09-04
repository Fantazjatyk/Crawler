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

import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.filters.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com Every
 * Collector produces Collection, that also means, that every can be post
 * filtered.
 */
public abstract class PostFilterableCollector<Produces extends Collection, Expects> extends DataHolderCollector<Produces, Expects> {

    private Collection<Predicate> postFilters = new ArrayList();

    @Override
    public Produces gather(Expects data, SearchRequest ctx) {
        Collection result = work(data, ctx);
        return (Produces) applyPostFilters(result);
    }

    protected abstract Produces work(Expects data, SearchRequest rq);

    protected void addPostFilter(Predicate p) {
        this.postFilters.add(p);
    }

    protected Collection applyPostFilters(Collection post) {
        return applyFilters(post, postFilters);
    }

    protected Collection applyFilters(Collection o, Collection<Predicate> filters) {
        if (filters.isEmpty()) {
            return o;
        }
        Collection result = new ArrayList();
        filters.parallelStream().forEach((el) -> {
            result.addAll((Collection) o.parallelStream().filter(el).collect(Collectors.toList()));
        });
        o.clear();
        o.addAll(result);
        return o;
    }
}
