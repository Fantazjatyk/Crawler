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
package crawler.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class Adress extends Data<String> {

    boolean isBelongsToDomain;
    URL url;
    String quessFormat;

    public Adress(String content, Source source) {
        super(content, source);
        assign(content);
    }

    public Adress(String content) {
        super(content);
        assign(content);

    }

    public Adress(String content, List<Source> sources) {
        super(content, sources);
        assign(content);
    }


    public Adress(){

    }
    public void assign(String content) {
        this.url = createURL(content);
        assignQuess(url != null && url.getPath() !=null && !url.getPath().isEmpty() ? url.getPath() : content);
    }

    public String getQuessFormat() {
        return this.quessFormat;
    }

    private URL createURL(String s) {
        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException ex) {

        }
        return url;
    }

    private void assignQuess(String content) {
        if (content.contains(".")) {
            int lastDotIndex = content.lastIndexOf(".");
            //assumes that this is file format (this not a rule).
            quessFormat = content.substring(lastDotIndex + 1, content.length());
        }
    }

    public URL getURL() {
        return this.url;
    }

    public void markAsBelongsToDomain() {
        this.isBelongsToDomain = true;
    }

    public boolean isBelongsToDomain() {
        return this.isBelongsToDomain;
    }

}
