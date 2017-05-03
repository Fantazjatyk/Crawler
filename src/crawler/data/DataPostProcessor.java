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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class DataPostProcessor {

    public Data mergeData(Data data1, Data data2) {
        Collection<Source> dateSources = data1.getSources();
        Collection<Source> dateNewSources = data2.getSources();

        dateNewSources.stream().filter((el) -> dateSources.contains(el) == false).forEach((el) -> dateSources.add(el));

        return data1;
    }

    public Collection<? extends Data> mergeDatas(Collection collection) {
        List<Data> result = new LinkedList();

        Collection<Data> copy = (Collection<Data>) collection.stream().filter((el)
                -> (el instanceof Data)
        ).collect(Collectors.toList());
        copy.removeIf(Objects::isNull);
        copy.forEach((data2) -> {

            if (result.contains(data2)) {
                Data data1 = result.get(result.indexOf(data2));

                if (data1 != null && data2 != null
                        && data1.getClass().equals(data2.getClass())) {
                    mergeData(data1, data2);

                    data2 = null;
                }
            } else {
                result.add(data2);
            }
        });

        result.stream().forEach((el) -> el.setSources(getMergedSources(el.getSources())));

        return result;
    }

    private Collection<Source> getMergedSources(Collection<Source> sources) {
        List<Source> list = new ArrayList();
        list.addAll(sources);

        Map<Object, Source> sourcesMap = new HashMap();
        list.stream().forEach((el) -> {

            if (sourcesMap.containsKey(el.getContent())) {
                sourcesMap.get(el.getContent()).duplicate();
            } else {
                sourcesMap.put(el.getContent(), el);
            }
        });
        return sourcesMap.values();
    }

}
