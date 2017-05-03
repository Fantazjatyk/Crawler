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

import crawler.utils.ClassSet;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class AutowireChain<LinkType extends SearchRequestAwareLink> extends Chain<LinkType> {

    @Override
    public ChainResponse start(Object root, SearchRequest rq) {
        ChainResponse response = super.start(root, rq);
        sortLinks();
        return response;
    }

    public void sortLinks() {

        final ConcurrentHashMap<Class[], LinkType> producers = (ConcurrentHashMap) super.getLinks().parallelStream().collect(Collectors.toConcurrentMap((el) -> el.produces(), (el2) -> el2));

        producers.entrySet().parallelStream()
                .forEach((entry) -> {
                    producers.entrySet()
                            .stream()
                            .filter((entry2) -> areCompatibile(entry2.getValue(), entry.getValue()))
                            .forEach((entry2) -> {
                                wire(entry2.getValue(), entry.getValue());
                            });

                }
                );
        super.getLinks().clear();
        super.getLinks().addAll(producers.values());
    }

    protected final boolean areCompatibile(SearchRequestAwareLink produce, SearchRequestAwareLink accept) {
        ClassSet produces = produce.produces();
        ClassSet accepts = accept.accepts();

        return Arrays.equals(produces.toArray(), accepts.toArray());
    }

    private void wire(SearchRequestAwareLink produce, SearchRequestAwareLink accept) {
        produce.setSuccesor(accept);
    }

}
