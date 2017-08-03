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
