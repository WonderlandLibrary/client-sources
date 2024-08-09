/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

final class ChainBuilder<E> {
    private final LinkedList<E> list = new LinkedList();
    private final Map<Class<?>, E> uniqueClasses = new HashMap();

    private void ensureUnique(E e) {
        E e2 = this.uniqueClasses.remove(e.getClass());
        if (e2 != null) {
            this.list.remove(e2);
        }
        this.uniqueClasses.put(e.getClass(), e);
    }

    public ChainBuilder<E> addFirst(E e) {
        if (e == null) {
            return this;
        }
        this.ensureUnique(e);
        this.list.addFirst(e);
        return this;
    }

    public ChainBuilder<E> addLast(E e) {
        if (e == null) {
            return this;
        }
        this.ensureUnique(e);
        this.list.addLast(e);
        return this;
    }

    public ChainBuilder<E> addAllFirst(Collection<E> collection) {
        if (collection == null) {
            return this;
        }
        for (E e : collection) {
            this.addFirst(e);
        }
        return this;
    }

    public ChainBuilder<E> addAllFirst(E ... EArray) {
        if (EArray == null) {
            return this;
        }
        for (E e : EArray) {
            this.addFirst(e);
        }
        return this;
    }

    public ChainBuilder<E> addAllLast(Collection<E> collection) {
        if (collection == null) {
            return this;
        }
        for (E e : collection) {
            this.addLast(e);
        }
        return this;
    }

    public ChainBuilder<E> addAllLast(E ... EArray) {
        if (EArray == null) {
            return this;
        }
        for (E e : EArray) {
            this.addLast(e);
        }
        return this;
    }

    public LinkedList<E> build() {
        return new LinkedList<E>(this.list);
    }
}

