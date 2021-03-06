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
package crawler.core;

import crawler.data.Adress;
import crawler.scrapping.SearchEngine;
import java.util.Collection;
import java.util.Optional;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;
import michal.szymanski.util.collection.ClassGroupingMap;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class AbstractCrawlerMovement extends TimeLimitedContinousProces {

    protected SearchEngine searchEngine = new SearchEngine();
    protected ClassGroupingMap results = new ClassGroupingMap();
    protected LinkedBlockingDeque<Adress> adresses = new LinkedBlockingDeque();
    private LinkedBlockingDeque<Adress> crawledAdresses = new LinkedBlockingDeque();
    protected String initURL;

    @Override
    public void iteration() {
        Optional<Adress> adress = findNextAdress();

        if (!adress.isPresent()) {
            return;
        }

        doMove(adress.get());
        commitMove(adress.get());
    }

    protected void doMove(Adress adress) {
        results.putAll(searchEngine.start(adress).toCollection());
        Collection group = results.getGroup(Adress.class);
        if (!group.isEmpty()) {
            adresses.addAll((Collection) group.stream().filter((el) -> ((Adress) (el)).isBelongsToDomain()).collect(Collectors.toList()));
        }

    }

    public ClassGroupingMap getResults() {
        return results;
    }

    public SearchEngine getSearchEngine() {
        return searchEngine;
    }

    public LinkedBlockingDeque<Adress> getAdresses() {
        return adresses;
    }

    public LinkedBlockingDeque<Adress> getCrawledAdresses() {
        return crawledAdresses;
    }

    private final void commitMove(Adress adress) {
        this.crawledAdresses.push(adress);
    }

    abstract Optional<Adress> findNextAdress();

}
