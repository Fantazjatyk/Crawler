/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.filters.Filter;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class PrePostFilterableCollector<Produces extends Collection, Expects extends Collection> extends PostFilterableCollector<Produces, Expects> {

    private Collection<Filter> preFilters = new ArrayList();

    public void addPreFilter(Filter f) {
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
