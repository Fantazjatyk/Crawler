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

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class PrePostFilterableCollector<Produces extends Collection, Expects extends Collection> extends PostFilterableCollector<Produces, Expects> {

    private Collection<Predicate> preFilters = new ArrayList();

    public void addPreFilter(Predicate f) {
        this.preFilters.add(f);
    }

    protected Collection applyPreFilters(Collection pre) {
        return applyFilters(pre, preFilters);
    }

    @Override
    public Produces collect(Expects data, SearchRequest ctx) {
        Collection input = data;
        Collection filteredInput = applyPreFilters(input);
        return super.collect((Expects) filteredInput, ctx);
    }

}
