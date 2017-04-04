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

import crawler.configuration.CrawlerParams;
import crawler.data.Data;
import crawler.data.Sentence;
import crawler.data.Source;
import crawler.data.Text;
import crawler.scrapping.chain.context.SearchContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import utils.Strings;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class SentenceCollector extends Collector{

    List<String> filteringSentences = new ArrayList();

      public SentenceCollector(String[] sentences) {

        if(sentences!= null){
        this.filteringSentences = Arrays.asList(sentences);
    }
    }

      public SentenceCollector(){

      }

    public List<String> getFilteringSentences() {
        return filteringSentences;
    }

    @Override
    public Object work(Object o, SearchContext ctx) {
        List found = new LinkedList();


        ((Collection<Data>)(o)).parallelStream().forEach((el) -> {
            if(el == null){
                return;
            }
            String text = ((String)(el.get())).toLowerCase();


            filteringSentences.parallelStream().filter((el1)->el1 != null).forEach((el2) -> {

                String s = el2;
                if (text.contains(s.toLowerCase())) {

                    String[] occurences = Strings.cutMatchingFragments(text, s, true);

                    for (String match : occurences) {
                        Sentence sentence = new Sentence(match, new Source(ctx.getRuntimeConfiguration().get(CrawlerParams.URL)));
                        found.add(sentence);
                    }
                }

            });
        });
        return found;
    }

    @Override
    public Class[] accepts() {
        return new Class[]{Text.class};
    }

    @Override
    public Class[] produces() {
        return new Class[]{Sentence.class};
    }

}
