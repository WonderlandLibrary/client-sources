/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event;

import java.util.Iterator;

public class FlexibleArray<T>
implements Iterable<T> {
    private T[] elements;

    public FlexibleArray(T[] array) {
        this.elements = array;
    }

    public FlexibleArray() {
        this.elements = new Object[0];
    }

    public void add(T t) {
        if (t != null) {
            Object[] array = new Object[this.size() + 1];
            for (int i = 0; i < array.length; ++i) {
                array[i] = i < this.size() ? this.get(i) : t;
            }
            this.set(array);
        }
    }

    public void remove(T t) {
        if (this.contains(t)) {
            Object[] array = new Object[this.size() - 1];
            boolean b = true;
            for (int i = 0; i < this.size(); ++i) {
                if (b && this.get(i).equals(t)) {
                    b = false;
                    continue;
                }
                array[b != false ? i : i - 1] = this.get(i);
            }
            this.set(array);
        }
    }

    public boolean contains(T t) {
        for (T entry : this.array()) {
            if (!entry.equals(t)) continue;
            return true;
        }
        return false;
    }

    private void set(T[] array) {
        this.elements = array;
    }

    public void clear() {
        this.elements = new Object[0];
    }

    public T get(int index) {
        return this.array()[index];
    }

    public int size() {
        return this.array().length;
    }

    public T[] array() {
        return this.elements;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < FlexibleArray.this.size() && FlexibleArray.this.get(this.index) != null;
            }

            @Override
            public T next() {
                return FlexibleArray.this.get(this.index++);
            }

            @Override
            public void remove() {
                FlexibleArray.this.remove(FlexibleArray.this.get(this.index));
            }
        };
    }

}

