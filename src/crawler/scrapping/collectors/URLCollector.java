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
import crawler.data.Adress;
import crawler.data.Source;
import crawler.logging.CrawlerLogger;
import crawler.scrapping.chain.ChainRequest;
import crawler.scrapping.chain.context.SearchContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class URLCollector extends DomCollector {

    private String[] htmlAttributes = new String[]{"abs:href", "abs:ref", "abs:src"};

    @Override
    protected Object usingJsoup(Document o, ChainRequest cr) {
        Elements e = o.getAllElements();
        SearchContext ctx = (SearchContext) cr.getContext();
        LinkedBlockingQueue result = new LinkedBlockingQueue();
        LinkedBlockingQueue<String> htmlSearchTags = new LinkedBlockingQueue(Arrays.asList(htmlAttributes));

        e.stream().parallel().forEach((el) -> {

            htmlSearchTags.parallelStream().forEach((el2) -> {
                if (el.hasAttr(el2)) {

                    if (!el.attr(el2).isEmpty()) {
                        Adress adress = new Adress(el.attr(el2), new Source(ctx.getRuntimeContext().getDomAdress().get()));
                        if (isBelongsToSearchedDomain(ctx.getRuntimeConfiguration().getInitURL(), adress.get())) {
                            adress.markAsBelongsToDomain();
                        }

                        result.add(adress);
                    }
                }
            });

        });
        return result;
    }

    @Override
    protected Object usingHtmlUnit(HtmlPage o, ChainRequest cr) {
        Iterable<DomAttr> e = o.getByXPath("//@href");
        SearchContext ctx = (SearchContext) cr.getContext();
        List result = new ArrayList();

        e.forEach((el) -> {

            Adress adress = new Adress(el.getNodeValue(), new Source(ctx.getRuntimeContext().getDomAdress().get()));
            if (isBelongsToSearchedDomain(ctx.getRuntimeConfiguration().getInitURL(), adress.get())) {
                adress.markAsBelongsToDomain();
            }

            result.add(adress);

        });
        return result;
    }

    public boolean isBelongsToSearchedDomain(String initURL, String url) {
        boolean result = false;
        URL testUrl = null;
        try {
            testUrl = new URL(url);

            if (new URL(initURL).getHost().equalsIgnoreCase(testUrl.getHost())) {
                result = true;
            } else {

            }
        } catch (MalformedURLException ex) {
            CrawlerLogger.getLogger(this).log("BAD ADRESS " + url);
        }

        return result;
    }

    @Override
    public Class[] produces() {
        return new Class[]{Adress.class};
    }

}
