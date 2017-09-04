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

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import crawler.data.Adress;
import crawler.data.Data;
import crawler.scrapping.chain.SearchRequest;
import crawler.utils.ClassSet;
import java.util.Collection;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class CollectorValidatorTest {

    Validator validator;

    public CollectorValidatorTest() {
    }

    /**
     * Test of initialize method, of class CollectorValidator.
     */
    @Test
    public void testInitialize() {
    }

    @Before
    public void setup() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * Test of isValid method, of class CollectorValidator.
     */
    @Test
    public void testIsValid_NullProducesOrNullAccepts() {
        CollectorWithNullProducesOrAndAccepts collector = new CollectorWithNullProducesOrAndAccepts();
        Set errors = validator.validate(collector);
        assertFalse(errors.isEmpty());
    }

    public class CollectorWithNullProducesOrAndAccepts extends Collector {

        @Override
        public ClassSet accepts() {
            return null;
        }

        @Override
        public ClassSet produces() {
            return null;
        }

        @Override
        protected Collection collect(Object data, SearchRequest ctx) {
            return null;
        }



    }

    @Test
    public void testIsValid_InvalidProducesOrInvalidAccepts() {
        CollectorWithInvalidAcceptsOrProduces collector = new CollectorWithInvalidAcceptsOrProduces();
        Set errors = validator.validate(collector);
        assertFalse(errors.isEmpty());
    }

    public class CollectorWithInvalidAcceptsOrProduces extends Collector {

        @Override
        public ClassSet accepts() {
            return new ClassSet(Elements.class);
        }

        @Override
        public ClassSet produces() {
            return new ClassSet(Data.class);
        }

        @Override
        protected Collection collect(Object data,SearchRequest ctx) {
            return null;
        }


    }

    @Test
    public void testIsValid_Valid() {
        ValidCollector collector = new ValidCollector();
        Set errors = validator.validate(collector);
        assertTrue(errors.isEmpty());
    }

    public class ValidCollector extends Collector {

        @Override
        public ClassSet accepts() {
            return new ClassSet(Adress.class);
        }

        @Override
        public ClassSet produces() {
            return new ClassSet(Data.class);
        }

        @Override
        protected Collection collect(Object data, SearchRequest ctx) {
            return null;
        }


    }

    @Test
    public void testIsValid_ValidDomCollector() {
        DomCollectorImpl collector = new DomCollectorImpl();
        Set errors = validator.validate(collector);
        assertTrue(errors.isEmpty());
    }

    public class DomCollectorImpl extends DomCollector {

        @Override
        Collection collectUsingJsoup(Document o, SearchRequest ctx) {
            return null;
        }

        @Override
        Collection collectUsingHtmlUnit(HtmlPage o, SearchRequest ctx) {
            return null;
        }

        @Override
        public ClassSet produces() {
            return new ClassSet(Data.class);
        }

    }

}
