package net.optifine.util;

import lombok.Getter;

import java.util.ArrayList;

public class CompactArrayList {
    private ArrayList<Object> list = null;
    private int initialCapacity = 0;
    private float loadFactor = 1.0F;
    @Getter
    private int countValid = 0;

    public CompactArrayList() {
        this(10, 0.75F);
    }

    public CompactArrayList(int initialCapacity) {
        this(initialCapacity, 0.75F);
    }

    public CompactArrayList(int initialCapacity, float loadFactor) {
        this.list = new ArrayList<>(initialCapacity);
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
        Object object = this.list.set(index, element);

        if (element != object) {
            if (object == null) {
                ++this.countValid;
            }

            if (element == null) {
                --this.countValid;
            }
        }

        return object;
    }

    public Object remove(int index) {
        Object object = this.list.remove(index);

        if (object != null) {
            --this.countValid;
        }

        return object;
    }

    public void clear() {
        this.list.clear();
        this.countValid = 0;
    }

    public void compact() {
        if (this.countValid <= 0 && this.list.size() <= 0) {
            this.clear();
        } else if (this.list.size() > this.initialCapacity) {
            float f = (float) this.countValid / (float) this.list.size();

            if (!(f > this.loadFactor)) {
                int i = 0;

                for (int j = 0; j < this.list.size(); ++j) {
                    Object object = this.list.get(j);

                    if (object != null) {
                        if (j != i) {
                            this.list.set(i, object);
                        }

                        ++i;
                    }
                }

                if (this.list.size() > i) {
                    this.list.subList(i, this.list.size()).clear();
                }
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

}
