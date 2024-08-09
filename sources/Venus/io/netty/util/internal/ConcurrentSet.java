/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.PlatformDependent;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

public final class ConcurrentSet<E>
extends AbstractSet<E>
implements Serializable {
    private static final long serialVersionUID = -6761513279741915432L;
    private final ConcurrentMap<E, Boolean> map = PlatformDependent.newConcurrentHashMap();

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean contains(Object object) {
        return this.map.containsKey(object);
    }

    @Override
    public boolean add(E e) {
        return this.map.putIfAbsent(e, Boolean.TRUE) == null;
    }

    @Override
    public boolean remove(Object object) {
        return this.map.remove(object) != null;
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }
}

