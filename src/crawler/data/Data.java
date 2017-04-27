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
import java.util.List;
import java.util.Objects;

/**
 *
@author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com.
 */
public class Data<T> {


    T content;
    List<Source> sources = new ArrayList();

    public Data(T content, Source source){
        this.content = content;
        this.sources.add(source);
    }

    public Data(T content, List<Source> sources){
        this.content = content;
        this.sources.addAll(sources);
    }

    public Data(T content){
        this.content = content;
    }

    public Data(){

    }
    public T get(){
        return this.content;
    }

    public List<Source> getSources(){
        return this.sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }



    @Override
    public boolean equals(Object obj) {
        Data s = (Data) obj;
        return s!=null && s instanceof Data && this.content != null?
                this.content.equals(s.content) : false;
        // NullPointerException
    }


    @Override
    public int hashCode() {
            int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.content);
        return hash;
    }

}
