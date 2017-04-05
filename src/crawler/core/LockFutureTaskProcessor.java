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
package crawler.core;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class LockFutureTaskProcessor<T> extends FutureTaskProcessor<T> {

    private ConcurrentSkipListMap<Lock, FutureTask> collection = new ConcurrentSkipListMap();
    private ConcurrentSkipListMap<Lock, Object> results = new ConcurrentSkipListMap();

    @Override
    void makeCycle() {

        Entry<Lock, FutureTask> entry = entry = collection.lastEntry();
        if (entry == null) {
            return;
        }
        FutureTask futureTask = null;
        futureTask = entry.getValue();

        if (futureTask != null && futureTask.isDone()) {
            try {
                T result = (T) futureTask.get();
                if (result != null) {
                    entry.getKey().deactivate();
                    results.put(entry.getKey(), result);
                    collection.remove(entry.getKey());
                }
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, e);
            }

        }

    }

    public void put(Lock lock, FutureTask task) {
        this.collection.put(lock, task);
    }

    public Entry<Lock, Object> pollLastResult() {
        return results.pollLastEntry();
    }

    @Override
    public void cancelAllFutureTasks() {
        collection.forEach((lock, el) -> {
            el.cancel(true);
            lock.deactivate();
        });
        collection.clear();
    }

    @Override
    void after() {
    }

}
