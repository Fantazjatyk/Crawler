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
import crawler.data.Sentence;
import crawler.data.Source;
import crawler.data.Text;
import crawler.scrapping.chain.SearchRequest;
import crawler.utils.ClassSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import michal.szymanski.util.Strings;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class SentencesCollector extends PrePostFilterableCollector<Collection, Collection<Text>> {

    private List<String> filteringSentences = new ArrayList();
    private boolean ignoreCase = false;

    public List<String> getFilteringSentences() {
        return filteringSentences;
    }

    @Override
    protected Collection work(Collection<Text> o, SearchRequest ctx) {
        List found = new LinkedList();

        o.stream()
                .filter((input) -> filteringSentences.stream().anyMatch((filter) -> input.get().toLowerCase().contains(filter.toLowerCase())))
                .forEach((input) -> {
                    filteringSentences.stream().forEach((filter) -> {

                        String[] occurences;
                        if (ignoreCase) {
                            occurences = Strings.cutMatchingFragmentIgnoreCase(input.get(), filter);
                        } else {
                            occurences = Strings.cutMatchingFragments(input.get(), filter);
                        }
                        Stream.of(occurences).forEach((occurence) -> {
                            Sentence sentence = new Sentence(occurence, new Source(ctx.getInitParams().get(CrawlerParams.CURRENT_URL)));
                            found.add(sentence);
                        });
                    });
                });
        return found;
    }

    public void setTarget(String... sentences) {
        this.filteringSentences = Arrays.asList(sentences);
    }

    public void setIgnoreCase(boolean bol) {
        this.ignoreCase = bol;
    }

    @Override
    public ClassSet accepts() {
        return new ClassSet(Text.class);
    }

    @Override
    public ClassSet produces() {
        return new ClassSet(Sentence.class);
    }

}
