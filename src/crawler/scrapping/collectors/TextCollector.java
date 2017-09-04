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

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import crawler.data.Text;
import crawler.scrapping.chain.SearchRequest;
import crawler.utils.ClassSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class TextCollector extends DomCollector<Collection> {

    String[] excludedTags = new String[]{"title"};

    @Override
    protected Collection collectUsingJsoup(Document o, SearchRequest ctx) {
        Elements e = o.getAllElements();
        LinkedBlockingQueue found = new LinkedBlockingQueue();
        LinkedBlockingQueue result = new LinkedBlockingQueue();

        e.parallelStream()
                .filter((el) -> isElementPassable(el.ownText(), el.tag().getName()) && !found.contains(el.ownText()))
                .forEach((el) -> {
                    result.add(new Text(el.ownText()));
                    found.add(el.ownText());
                });

        return new ArrayList(result);
    }

    private boolean isElementPassable(String text, String tagName) {
        return text != null && !text.isEmpty() && !Arrays.asList(excludedTags).contains(tagName);
    }

    @Override
    protected Collection collectUsingHtmlUnit(HtmlPage o, SearchRequest ctx) {
        List<DomNode> e = o.getByXPath("//child::text()");

        LinkedBlockingQueue found = new LinkedBlockingQueue();
        LinkedBlockingQueue result = new LinkedBlockingQueue();

        e.parallelStream()
                .filter((el) -> isElementPassable(el.getTextContent(), el.getNodeName()) && !found.contains(el.getTextContent()))
                .peek((el) -> {
                    String text = el.getTextContent();

                    result.add(new Text(text));
                    found.add(text);

                });
        return result;
    }

    @Override
    public ClassSet produces() {
        return new ClassSet(Text.class);
    }

}
