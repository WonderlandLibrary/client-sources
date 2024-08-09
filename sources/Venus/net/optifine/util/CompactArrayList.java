/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.ArrayList;

public class CompactArrayList {
    private ArrayList list = null;
    private int initialCapacity = 0;
    private float loadFactor = 1.0f;
    private int countValid = 0;

    public CompactArrayList() {
        this(10, 0.75f);
    }

    public CompactArrayList(int n) {
        this(n, 0.75f);
    }

    public CompactArrayList(int n, float f) {
        this.list = new ArrayList(n);
        this.initialCapacity = n;
        this.loadFactor = f;
    }

    public void add(int n, Object object) {
        if (object != null) {
            ++this.countValid;
        }
        this.list.add(n, object);
    }

    public boolean add(Object object) {
        if (object != null) {
            ++this.countValid;
        }
        return this.list.add(object);
    }

    public Object set(int n, Object object) {
        Object object2 = this.list.set(n, object);
        if (object != object2) {
            if (object2 == null) {
                ++this.countValid;
            }
            if (object == null) {
                --this.countValid;
            }
        }
        return object2;
    }

    public Object remove(int n) {
        Object e = this.list.remove(n);
        if (e != null) {
            --this.countValid;
        }
        return e;
    }

    public void clear() {
        this.list.clear();
        this.countValid = 0;
    }

    public void compact() {
        float f;
        if (this.countValid <= 0 && this.list.size() <= 0) {
            this.clear();
        } else if (this.list.size() > this.initialCapacity && !((f = (float)this.countValid * 1.0f / (float)this.list.size()) > this.loadFactor)) {
            int n;
            int n2 = 0;
            for (n = 0; n < this.list.size(); ++n) {
                Object e = this.list.get(n);
                if (e == null) continue;
                if (n != n2) {
                    this.list.set(n2, e);
                }
                ++n2;
            }
            for (n = this.list.size() - 1; n >= n2; --n) {
                this.list.remove(n);
            }
        }
    }

    public boolean contains(Object object) {
        return this.list.contains(object);
    }

    public Object get(int n) {
        return this.list.get(n);
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

