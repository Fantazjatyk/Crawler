/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public interface FilterableCollection {
    static DataPostProcessor dataProcessor = new DataPostProcessor();

    public default List getAllOf(Class c) {
        Collection self = (Collection) this;
          Class c1 = c;
        List result = new ArrayList();
            result = (List) self.parallelStream().filter((el) -> el != null && el.getClass().equals(c1)).collect(Collectors.toList());
        return result;
    }


    public default List getAllDistinctOf(Class c) {
        List result = getAllOf(c);
        return (List<Data>) dataProcessor.mergeDatas(result);
    }
}
