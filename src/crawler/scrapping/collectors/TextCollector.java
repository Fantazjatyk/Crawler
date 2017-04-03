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
import crawler.scrapping.chain.ChainRequest;
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
public class TextCollector extends DomCollector{
  String[] excludedTags = new String[]{"title"};
    @Override
    protected Object usingJsoup(Document o, ChainRequest cr) {
         Elements e = o.getAllElements();
        LinkedBlockingQueue found = new LinkedBlockingQueue();
        LinkedBlockingQueue result = new LinkedBlockingQueue();

        e.parallelStream().forEach((el) -> {
            String text = el.ownText();

            if (text != null && !text.isEmpty() && !found.contains(text) && !Arrays.asList(excludedTags).contains(el.tag())) {
                result.add(new Text(text));
                found.add(text);
            }
        });
        return new ArrayList(result);
    }

    @Override
    protected Object usingHtmlUnit(HtmlPage o, ChainRequest cr) {
         List<DomNode> e = o.getByXPath("//child::text()");

        LinkedBlockingQueue found = new LinkedBlockingQueue();
        LinkedBlockingQueue result = new LinkedBlockingQueue();

        e.parallelStream().forEach((el) -> {
            String text = el.getTextContent();

            if (text != null && !text.isEmpty() && !found.contains(text) && !Arrays.asList(excludedTags).contains(el.getNodeName())) {
                result.add(new Text(text));
                found.add(text);
            }
        });
        return new ArrayList(result);
    }


    @Override
    public Class[] produces() {
        return new Class[]{Text.class};
    }

}
