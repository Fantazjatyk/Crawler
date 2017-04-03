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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import crawler.data.Data;
import crawler.data.Source;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */

public class DataPostProcessor {

    private void mergeData(Data data1, Data data2) {
        List<Source> dateSources = data1.getSources();
        List<Source> dateNewSources = data2.getSources();

        dateNewSources.forEach((el) -> {
            if (!dateSources.contains(el)) {
                dateSources.add(el);
            }
        });
    }

    public Collection<? extends Data> mergeDatas(Collection<? extends Data> collection) {
        List<Data> result = new LinkedList();


        collection.forEach((el) -> {

            if (result.contains(el)) {
                mergeData(result.get(result.indexOf(el)), el);
            } else {
                result.add(el);
            }
        });

        result.stream().forEach((el) -> el.setSources(getMergedSources(el.getSources())));

        return result;
    }

    private List<Source> getMergedSources(List<Source> sources) {
        List<Source> list = new ArrayList() {
            {
                addAll(sources);
            }
        };
        Map<Object, Source> sourcesMap = new HashMap();
        list.stream().forEach((el) -> {

            if (sourcesMap.containsKey(el.getSource())) {
                sourcesMap.get(el.getSource()).duplicate();
            } else {
                sourcesMap.put(el.getSource(), el);
            }
        });
        list.clear();
        list.addAll(sourcesMap.values());
        return list;
    }

}
