/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class ClassSet extends LinkedHashSet<Class> {

    public ClassSet(Class... classes) {
        for (Class c : classes) {
            this.add(c);
        }

    }

    public boolean containsClass(Class c) {
        HashSet set = this;

        Iterator i = set.iterator();
        while (i.hasNext()) {

            if (i.next().equals(c)) {
                return true;
            }
        }
        return false;
    }

}
