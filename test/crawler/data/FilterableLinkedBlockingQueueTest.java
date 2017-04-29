/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.data;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class FilterableLinkedBlockingQueueTest {

    FilterableLinkedBlockingQueue queue;
    public FilterableLinkedBlockingQueueTest() {
    }


    @Before
    public void setup(){
       queue = new FilterableLinkedBlockingQueue();
    }

    /**
     * Test of getAllDistinctOf method, of class FilterableLinkedBlockingQueue.
     */
    @Test
    public void testGetAllDistinctOf() {
        queue.add(new Data());
        queue.add(new Sentence());
        queue.add(new Sentence());
        queue.add(new Sentence());
        queue.add(new Adress());
        queue.add(new Adress());

        assertEquals(3, queue.getAllOf(Sentence.class).size());
        assertEquals(2, queue.getAllOf(Adress.class).size());
        assertEquals(1, queue.getAllOf(Data.class).size());

 }

}
