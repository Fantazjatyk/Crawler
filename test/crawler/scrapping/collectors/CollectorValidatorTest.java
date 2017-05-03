/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
