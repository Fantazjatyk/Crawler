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
package crawler.scrapping.parsers;

import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import crawler.logging.CrawlerLogger;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com.
 */
public class JsoupParser implements Parser<Document> {

    String url;

    public Document parse(String url) {
        this.url = url;
        return getHTMLElements();
    }

    private Document getSourceCode() {

        Document sourceCode = null;

        try {
            sourceCode = Jsoup.connect(url).get();
            CrawlerLogger.getLogger(this).log("PARSED (URL: " + url + ")");
        } catch (Exception ex) {
            Logger.getGlobal().warning(ex.getLocalizedMessage());
            CrawlerLogger.getLogger(this).log("CONNECTION ERROR (URL: " + url + "): " + ex.getMessage());
        }

        return sourceCode;
    }

    private Document getHTMLElements() {
        Document document = getSourceCode();
        return document;
    }

}
