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
package crawler.scrapping.chain;

import crawler.scrapping.exceptions.IllegalInputException;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class SearchRequestAwareChain extends AutowireChain {

    abstract protected Collection each(Object expecting, SearchRequestAwareLink link, ChainRequest rq) throws IllegalInputException;
    protected LinkedBlockingDeque<SearchRequestAwareLink> links = new LinkedBlockingDeque();
    private Object root;

    public Class getRootType() {
        return root.getClass();
    }

    abstract protected void body(Object expecting, ChainRequest rq);

    abstract protected void onEnd();

    abstract protected void onStart();

    public LinkedBlockingDeque<SearchRequestAwareLink> getLinks() {
        return links;
    }

    public void start(Object root, ChainRequest rq) {
        this.root = root;
        sortLinks();
        body(root, rq);
    }
}
