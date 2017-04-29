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
package crawler.scrapping.collectors;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import crawler.configuration.CrawlerParams;
import crawler.data.Adress;
import crawler.data.Source;
import crawler.logging.CrawlerLogger;
import crawler.scrapping.chain.SearchRequest;
import crawler.scrapping.chain.context.SearchContext;
import crawler.utils.ClassSet;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import michal.szymanski.util.URLs;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class URLCollector extends DomCollector<Collection> {

    private String[] htmlAttributes = new String[]{"abs:href", "abs:ref", "abs:src"};

    @Override
    public Collection collectUsingJsoup(Document o, SearchRequest ctx) {
        LinkedBlockingQueue result = new LinkedBlockingQueue();

        for (String s : htmlAttributes) {
            Elements input = o.getElementsByAttribute(s);
            if (input == null || input.isEmpty()) {
                continue;
            }

            input = input.parallelStream()
                    .filter((el) -> el != null && el.attr(s) != null)
                    .peek((el) -> {
                        Adress adress = new Adress(el.attr(s), new Source(ctx.getInitParams().get(CrawlerParams.CURRENT_URL)));
                        if (URLs.isBelongsToDomain((String) ctx.getInitParams().get(CrawlerParams.URL), adress.get())) {
                            adress.markAsBelongsToDomain();
                        }

                        result.add(adress);
                    })
                    .collect(Collectors.toCollection(() -> new Elements()));

        }

        return result;
    }

    @Override
    public Collection collectUsingHtmlUnit(HtmlPage o, SearchRequest ctx) {
        Collection<DomAttr> e = (Collection) o.getByXPath("//@href");
        List result = new ArrayList();

        e.parallelStream()
                .forEach((el) -> {

                    Adress adress = new Adress(el.getNodeValue(), new Source(ctx.getInitParams().get(CrawlerParams.CURRENT_URL)));
                    if (URLs.isBelongsToDomain((String) ctx.getInitParams().get(CrawlerParams.URL), adress.get())) {
                        adress.markAsBelongsToDomain();
                    }

                    result.add(adress);

                });
        return result;
    }

    @Override
    public ClassSet produces() {
        return new ClassSet(Adress.class);
    }

}
