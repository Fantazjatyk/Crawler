/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import crawler.data.DataPostProcessor;
import crawler.scrapping.chain.SearchRequest;
import crawler.utils.ClassSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class DataHolderCollector<Produces extends Collection, Expects> extends Collector<Produces, Expects> {

    private Collection result = new ArrayList();

    @Override
    protected Produces collect(Expects data, SearchRequest ctx) {
        Produces result = gather(data, ctx);
        this.result.addAll(result);
        return result;
    }

    protected abstract Produces gather(Expects data, SearchRequest ctx);

    public Collection getResults() {
        return this.result;
    }

    public Collection getResultsWithoutDuplicates() {
        return new DataPostProcessor().mergeDatas(this.result);
    }

}
