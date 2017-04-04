/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.scrapping.collectors;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import crawler.data.Adress;
import crawler.data.Data;
import crawler.scrapping.chain.context.SearchContext;
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
        protected Object work(Object o, SearchContext ctx) {
            return null;
        }

        @Override
        public Class[] accepts() {
            return null;
        }

        @Override
        public Class[] produces() {
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
        protected Object work(Object o, SearchContext ctx) {
            return null;
        }

        @Override
        public Class[] accepts() {
            return new Class[]{Elements.class};
        }

        @Override
        public Class[] produces() {
            return new Class[]{Data.class};
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
        protected Object work(Object o, SearchContext ctx) {
            return null;
        }

        @Override
        public Class[] accepts() {
            return new Class[]{Adress.class};
        }

        @Override
        public Class[] produces() {
            return new Class[]{Data.class};
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
        Object collectUsingJsoup(Document o, SearchContext ctx) {
            return null;
        }

        @Override
        Object collectUsingHtmlUnit(HtmlPage o, SearchContext ctx) {
            return null;
        }

        @Override
        public Class[] produces() {
            return new Class[]{Data.class};
        }

    }

}
