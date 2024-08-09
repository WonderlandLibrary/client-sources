package ez.h.event;

import java.util.*;

public class ArrayHelper<T> implements Iterable<T>
{
    private T[] elements;
    
    public void clear() {
        this.elements = (T[])new Object[0];
    }
    
    public void set(final T[] elements) {
        this.elements = elements;
    }
    
    public T get(final int n) {
        return this.array()[n];
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index;
            
            @Override
            public boolean hasNext() {
                return this.index < ArrayHelper.this.size() && ArrayHelper.this.get(this.index) != null;
            }
            
            @Override
            public T next() {
                return ArrayHelper.this.get(this.index++);
            }
            
            @Override
            public void remove() {
                ArrayHelper.this.remove(ArrayHelper.this.get(this.index));
            }
        };
    }
    
    public void remove(final T t) {
        if (this.contains(t)) {
            final Object[] array = new Object[this.size() - 1];
            int n = 1;
            for (int i = 0; i < this.size(); ++i) {
                if (n != 0 && this.get(i).equals(t)) {
                    n = 0;
                }
                else {
                    array[(n != 0) ? i : (i - 1)] = this.get(i);
                }
            }
            this.set((T[])array);
        }
    }
    
    public ArrayHelper(final T[] elements) {
        this.elements = elements;
    }
    
    public boolean contains(final T t) {
        Object[] array;
        for (int length = (array = this.array()).length, i = 0; i < length; ++i) {
            if (array[i].equals(t)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public int size() {
        return this.array().length;
    }
    
    public T[] array() {
        return this.elements;
    }
    
    public void add(final T t) {
        if (t != null) {
            final Object[] array = new Object[this.size() + 1];
            for (int i = 0; i < ((T[])array).length; ++i) {
                if (i < this.size()) {
                    array[i] = this.get(i);
                }
                else {
                    array[i] = t;
                }
            }
            this.set((T[])array);
        }
    }
    
    public ArrayHelper() {
        this.elements = (T[])new Object[0];
    }
}
