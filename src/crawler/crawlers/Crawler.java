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
package crawler.crawlers;

import crawler.configuration.CrawlerConfiguration;
import crawler.data.DataPostProcessor;
import crawler.logging.CrawlerFinishInfo;
import crawler.logging.CrawlerInitInfo;
import crawler.scrapping.SearchEngine;
import java.util.Collection;
import michal.szymanski.util.collection.ClassGroupingMap;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class Crawler implements IBasicCrawler {

    private CrawlerConfiguration conf = new CrawlerConfiguration();
    private ClassGroupingMap sr = new ClassGroupingMap();
    private CrawlerTime time = new CrawlerTime();

    public double getElapsedTime() {
        return time.getElapsedTime();
    }

    public final ClassGroupingMap getResults() {
        return sr;
    }

    public final CrawlerConfiguration getConf() {
        return conf;
    }

    public final void start() {
        time.start();
        CrawlerInitInfo.printInitInfo(this, conf);
        Collection results = crawl(conf).toCollection();
        results = new DataPostProcessor().mergeDatas(results);
        sr.putAll(results);
        time.end();
        CrawlerFinishInfo.printtCrawlerFinishInfo(this);
    }

    public CrawlerConfiguration configure() {
        return this.conf;
    }

    protected abstract ClassGroupingMap crawl(CrawlerConfiguration conf);

    public abstract void interrupt();

    public abstract void appendFilters(SearchEngine se, CrawlerConfiguration conf);
}
