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

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import crawler.configuration.CrawlerParams;
import crawler.data.Data;
import crawler.data.ImageSource;
import crawler.data.Source;
import crawler.scrapping.chain.context.SearchContext;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class ImageCollector extends DomCollector{

    @Override
    protected Object collectUsingJsoup(Document o, SearchContext ctx) {
           Elements e = o.getAllElements();
        List<Data> result = e.parallelStream()
                .filter((el) -> el.tagName().equals("img") && el.hasAttr("abs:src") && !el.attr("abs:src").isEmpty())
                .map((el2)
                        -> new ImageSource(el2.attr("abs:src"), new Source(ctx.getRuntimeConfiguration().get(CrawlerParams.URL)))).collect(Collectors.toList());

        return result;
    }

    @Override
    protected Object collectUsingHtmlUnit(HtmlPage o, SearchContext ctx) {
        List<DomElement> list = o.getElementsByTagName("img");
        List<Data> result = list.parallelStream().filter((el)-> el.hasAttribute("src") && !el.getAttribute("src").isEmpty()).map((el2)
                        -> new ImageSource(getHtmlUnitAbsUrl(el2.getAttribute("src"), o), new Source(ctx.getRuntimeConfiguration().get(CrawlerParams.URL)))).collect(Collectors.toList());
        return result;
    }

    @Override
    public Class[] produces() {
        return new Class[]{ImageSource.class};
    }

    private String getHtmlUnitAbsUrl(String s, HtmlPage p){
        String result = null;
        try {
            result = p.getFullyQualifiedUrl(s).toExternalForm();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ImageCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
