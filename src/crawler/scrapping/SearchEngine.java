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
package crawler.scrapping;

import crawler.configuration.CrawlerConfiguration;
import crawler.configuration.CrawlerParams;
import crawler.data.Adress;
import crawler.scrapping.chain.ChainResponse;
import crawler.scrapping.chain.ChainResults;
import crawler.scrapping.chain.Search;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.collectors.Collector;
import crawler.scrapping.collectors.TextCollector;
import crawler.scrapping.collectors.URLCollector;
import crawler.scrapping.exceptions.InnvalidCollectorException;
import crawler.scrapping.parsers.JsoupParser;
import crawler.scrapping.parsers.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class SearchEngine {

    private Parser parser = new JsoupParser();
    protected Search search = new Search();
    private CrawlerConfiguration conf;
    private List<Collector> collectors = new ArrayList();

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    protected ChainResponse init(Adress adress, SearchRequest rq) {
        ChainResponse rs = null;
        rq.getInitParams().put(CrawlerParams.URL, adress.get());
        try {
            rs = runRootChains(adress, rq);
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.toString());
        } finally {
            if (rs == null) {
                rs = new ChainResponse();
            }
        }
        return rs;
    }

    public void setConfiguration(CrawlerConfiguration conf) {
        this.conf = conf;
    }

    public ChainResults start(Adress adress) {
        SearchRequest rq = new SearchRequest();
        rq.getInitParams().putAll(conf);
        rq.getInitParams().put(CrawlerParams.CURRENT_URL, adress.get());
        ChainResponse rs = init(adress, rq);
        return rs.getResults();
    }

    public ChainResponse runRootChains(Adress adress, SearchRequest rq) throws InterruptedException, IOException {
        Object parsed = parser.parse(adress.get());
        search.getLinks().add(new URLCollector());
        search.getLinks().add(new TextCollector());
        search.getLinks().addAll(collectors);
        return search.start(parsed, rq);
    }

    public void addCollectors(Collection<Collector> links) {
        links.forEach((el) -> {
            if (isCollectorValid(el)) {
                collectors.add(el);
            } else {
                throw new InnvalidCollectorException();
            }
        });
    }

    public boolean isCollectorValid(Collector c) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set set = validator.validate(c);

        return set.isEmpty();
    }

    public void addCollector(Collector collector) {
        if (isCollectorValid(collector)) {
            collectors.add(collector);
        } else {
            throw new InnvalidCollectorException();
        }
    }

}
