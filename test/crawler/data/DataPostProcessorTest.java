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
