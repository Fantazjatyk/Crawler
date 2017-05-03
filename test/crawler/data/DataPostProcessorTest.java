/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class DataPostProcessorTest {

    DataPostProcessor processor;

    public DataPostProcessorTest() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        processor = new DataPostProcessor();
    }

    /**
     * Test of mergeDatas method, of class DataPostProcessor.
     */

    @Test
    public void testMergeData_Optimistic() {
        Sentence sentence1 = new Sentence("Ala", new Source("z miasta"));
        Sentence sentence2 = new Sentence("ma", new Source("ndryt"));
        Sentence result = (Sentence) processor.mergeData(sentence1, sentence2);
        assertNotNull(result);
        assertEquals(2, result.getSources().size());
    }

    @Test
    public void testMergeDatas() {
        Sentence sentence = new Sentence("Ala", new Source("a"));
        Sentence sentence2 = new Sentence("ma", new Source("b"));
        Sentence sentence3 = new Sentence("Ala", new Source("a"));

        Collection<Sentence> sentences = new ArrayList() {
            {
                add(sentence);
                add(sentence2);
                add(sentence3);
            }
        };

        List<Data> result = (List) processor.mergeDatas(sentences);
        assertEquals(2, result.size());
        assertEquals(2, ((Source) (result.get(result.indexOf(sentence)).getSources().iterator().next())).getTimes());
    }

    @Test
    public void testMergeDatas_RemoveNulls() {
        Sentence sentence = new Sentence("Ala", new Source("a"));
        Sentence sentence2 = new Sentence("ma", new Source("b"));
        Sentence sentence3 = null;

        Collection<Sentence> sentences = new ArrayList() {
            {
                add(sentence);
                add(sentence2);
                add(sentence3);
            }
        };

        List result = (List) processor.mergeDatas(sentences);
        assertEquals(2, result.size());
    }

    @Test
    public void testMergeDatas_NoSources() {
        Sentence sentence = new Sentence("Ala", new Source("a"));
        Sentence sentence4 = new Sentence("Ala", new Source("a"));
        Sentence sentence2 = new Sentence("ma", new Source("b"));
        Sentence sentence3 = new Sentence("ma");

        Collection<Sentence> sentences = new ArrayList() {
            {
                add(sentence);
                add(sentence2);
                add(sentence3);
                add(sentence4);
            }
        };

        List<Data> result = (List) processor.mergeDatas(sentences);

        assertEquals(2, result.size());
        assertEquals(1, ((Source) (result.get(result.indexOf(sentence2)).getSources().iterator().next())).getTimes());

    }

    @Test
    public void testMergeDatas_DiffrentTypes() {
        Sentence sentence = new Sentence("Ala", new Source("a"));
        Sentence sentence2 = new Sentence("ma", new Source("b"));
        Adress adress = new Adress("google.pl", new Source("http://google.pl"));

        Collection<Data> sentences = new ArrayList() {
            {
                add(sentence);
                add(sentence2);
                add(adress);
            }
        };

        List<Data> result = (List) processor.mergeDatas(sentences);
        assertEquals(3, result.size());
    }
}
