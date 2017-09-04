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
package crawler.utils;

import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class HumanFaker {

    public static int getRandomMilis(int minMilis, int maxMilis) {
        int result = new Random().nextInt(maxMilis);
        if (result < minMilis) {
            result = minMilis + maxMilis;
        }
    return result ;
}
public static Object pollRandomElement(Collection c){
        Object result;

        if(c.isEmpty()){
            return null;
        }
        if(c instanceof List){
        result = getRandomListElement((List) c);
    }
        else if(c instanceof Deque){
        result = getRandomDequeElement((Deque) c);
    }
        else{
           result = c.iterator().next();
        }
        return result;
    }

    private static Object getRandomListElement(List c){
        int index = new Random().nextInt(c.size());
        Object result = c.get(index);
        c.remove(index);
        return result;
    }

    private static Object getRandomDequeElement(Deque d){
        boolean headOrTail = new Random().nextBoolean();
        return headOrTail ? d.pollFirst() : d.pollLast();
    }
}
