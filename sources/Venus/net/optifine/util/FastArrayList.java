/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

public class FastArrayList<E> {
    private E[] array;
    private int size;

    public FastArrayList(int n) {
        this.array = new Object[n];
    }

    public void add(E e) {
        this.array[this.size] = e;
        ++this.size;
    }

    public E get(int n) {
        return this.array[n];
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        this.size = 0;
    }
}

