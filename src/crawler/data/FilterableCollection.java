/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.data;

import crawler.utils.ClassSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public interface FilterableCollection {

    static DataPostProcessor dataProcessor = new DataPostProcessor();

    public default Optional get(ClassSet c) {
        Collection self = (Collection) this;

        List found = (List) self.stream().filter((el) -> c.containsClass(el.getClass())).collect(Collectors.toList());

        long elementsCount = found.size();
        Optional result = elementsCount > 1 ? Optional.of(found) : found.stream().findAny();
        return result;
    }

    public default List getAllDistinctOf(ClassSet c) {
        List found = new ArrayList();
        Optional result = get(c);

        if(result.isPresent() && result.get() instanceof Collection){
            Optional.of(dataProcessor.mergeDatas((Collection) result.get())).ifPresent((el)->found.addAll(el));
        }
        else if(result.isPresent()){
            found.add(result.get());
        }
        return found;
    }

    public default List getAllDistinctOf(Class c){
        return getAllDistinctOf(new ClassSet(c));
    }
}
