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
package crawler.crawlers.continous;

import crawler.core.CrawlerMovement;
import crawler.crawlers.Crawler;
import crawler.crawlers.ContinousableCrawler;
import crawler.configuration.CrawlerConfiguration;
import crawler.core.AbstractCrawlerMovement;
import crawler.data.Adress;
import crawler.data.FilterableArrayList;
import java.util.Collection;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class ContinousCrawler extends Crawler implements ContinousableCrawler {

    protected AbstractCrawlerMovement movement = new CrawlerMovement();

    public AbstractCrawlerMovement getMovement() {
        return movement;
    }

    @Override
    public Collection crawl(CrawlerConfiguration conf) {
        movement.getSearchEngine().setConfiguration(conf);
        appendFilters(movement.getSearchEngine(), conf);
        movement.getAdresses().add(new Adress(conf.getInitURL()));
        return startMovement(conf.getTimeLimit());
    }

    private FilterableArrayList startMovement(int timeLimit) {
        this.movement.start(timeLimit);
        return movement.getResults();
    }

    @Override
    public void interrupt() {
        movement.kill();
    }

}
