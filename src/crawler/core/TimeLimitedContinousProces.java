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


import com.google.common.base.Stopwatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public abstract class TimeLimitedContinousProces extends ContinousProcess {

    private Stopwatch stopwatch;
    private volatile int timeLimit;

    public int getRemainingTime(){
        return (int) (this.timeLimit - stopwatch.elapsed(TimeUnit.SECONDS));
    }

    @Override
    final void loopBody() {
        makeCycle();
        sleep();
    }

    abstract void makeCycle();

    public void start(int timeLimit){
        this.timeLimit = timeLimit;
        super.start();
    }

    private final void initIndependendLimiter(){
                  Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{ while(!isDone()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CrawlerMovement.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        kill();
        });
    }

    void before(){

    }

    void after(){

    }
    @Override
    final void onStart() {
        stopwatch = Stopwatch.createUnstarted();
        stopwatch.start();
        initIndependendLimiter();
        before();
    }

    @Override
    final boolean isDone() {
        return stopwatch.elapsed(TimeUnit.SECONDS) > timeLimit;
    }

    @Override
    final void onEnd() {
        after();
    }

}
