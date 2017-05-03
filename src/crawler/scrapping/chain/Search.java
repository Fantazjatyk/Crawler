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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class Search extends AutowireChain<SearchRequestAwareLink> {

    public ChainResponse start(Object root, SearchRequest rq) {
        ChainResponse rs = super.start(root, rq);
        body(root, rq, rs);
        return rs;
    }

    protected void each(Object expecting, SearchRequestAwareLink link, ChainRequest rq, ChainResponse rs) throws IllegalInputException {
        try {
            link.handle(rq, rs);
        } catch (NullPointerException e) {
            Logger.getLogger(this.getClass().getName()).warning(e.getStackTrace()[0].toString());
        }
    }

    protected void body(Object expecting, ChainRequest rq, ChainResponse rs) {
        onStart();
        while (!links.isEmpty()) {
            SearchRequestAwareLink link = null;
            try {
                link = links.peek();
                links.remove();

                if (!link.accepts().contains(expecting.getClass())) {
                    continue;
                }

                each(expecting, link, rq, rs);
            } catch (IllegalInputException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        }
        onEnd();
    }

    protected void onEnd() {
    }

    protected void onStart() {
    }

}
