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

import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class Chain<LinkType> {

    protected LinkedBlockingDeque<LinkType> links = new LinkedBlockingDeque();
    private SearchRequest rq;
    private Object root;

    public Class getRootType() {
        return root.getClass();
    }

    public SearchRequest getChainRequest() {
        return rq;
    }

    public ChainResponse start(Object root, SearchRequest rq) {
        if (root == null || rq == null) {
            throw new NullPointerException();
        }
        this.root = root;
        this.rq = rq;

        ChainResponse rs = new ChainResponse();
        rs.getResults().put(root);
        return rs;
    }

    public LinkedBlockingDeque<LinkType> getLinks() {
        return links;
    }

}
