/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import crawler.scrapping.chain.ChainRequest;
import crawler.scrapping.chain.ChainResponse;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.filters.Filter;
import crawler.scrapping.filters.FilterMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com Every
 * Collector produces Collection, that also means, that every can be post
 * filtered.
 */
public abstract class PostFilterableCollector<Produces extends Collection, Expects> extends Collector<Produces, Expects> {

    private Collection<Filter> postFilters = new ArrayList();

    @Override
    protected Produces collect(Expects data, SearchRequest ctx) {
        Produces prod = null;
        Collection result = work(data, ctx);
        prod = (Produces) applyPostFilters(result);
        return prod;
    }

    protected abstract Produces work(Expects data, SearchRequest rq);

    public void addPostFilter(Filter f) {
        this.postFilters.add(f);
    }

    protected Collection applyPostFilters(Collection post) {
        return applyFilters(post, postFilters);
    }

    protected Collection applyFilters(Collection o, Collection<Filter> filters) {
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
