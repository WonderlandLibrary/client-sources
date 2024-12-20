/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.util.ArrayList;

public class CompactArrayList {
    private ArrayList list = null;
    private int initialCapacity = 0;
    private float loadFactor = 1.0f;
    private int countValid = 0;

    public CompactArrayList() {
        this(10, 0.75f);
    }

    public CompactArrayList(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public CompactArrayList(int initialCapacity, float loadFactor) {
        this.list = new ArrayList(initialCapacity);
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
    }

    public void add(int index, Object element) {
        if (element != null) {
            ++this.countValid;
        }
        this.list.add(index, element);
    }

    public boolean add(Object element) {
        if (element != null) {
            ++this.countValid;
        }
        return this.list.add(element);
    }

    public Object set(int index, Object element) {
        Object oldElement = this.list.set(index, element);
        if (element != oldElement) {
            if (oldElement == null) {
                ++this.countValid;
            }
            if (element == null) {
                --this.countValid;
            }
        }
        return oldElement;
    }

    public Object remove(int index) {
        Object oldElement = this.list.remove(index);
        if (oldElement != null) {
            --this.countValid;
        }
        return oldElement;
    }

    public void clear() {
        this.list.clear();
        this.countValid = 0;
    }

    public void compact() {
        float currentLoadFactor;
        if (this.countValid <= 0 && this.list.size() <= 0) {
            this.clear();
        } else if (this.list.size() > this.initialCapacity && (currentLoadFactor = (float)this.countValid * 1.0f / (float)this.list.size()) <= this.loadFactor) {
            int i2;
            int dstIndex = 0;
            for (i2 = 0; i2 < this.list.size(); ++i2) {
                Object wr2 = this.list.get(i2);
                if (wr2 == null) continue;
                if (i2 != dstIndex) {
                    this.list.set(dstIndex, wr2);
                }
                ++dstIndex;
            }
            for (i2 = this.list.size() - 1; i2 >= dstIndex; --i2) {
                this.list.remove(i2);
            }
        }
    }

    public boolean contains(Object elem) {
        return this.list.contains(elem);
    }

    public Object get(int index) {
        return this.list.get(index);
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public int size() {
        return this.list.size();
    }

    public int getCountValid() {
        return this.countValid;
    }
}

