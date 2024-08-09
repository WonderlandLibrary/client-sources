/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

final class EdgesConnecting<E>
extends AbstractSet<E> {
    private final Map<?, E> nodeToOutEdge;
    private final Object targetNode;

    EdgesConnecting(Map<?, E> map, Object object) {
        this.nodeToOutEdge = Preconditions.checkNotNull(map);
        this.targetNode = Preconditions.checkNotNull(object);
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        E e = this.getConnectingEdge();
        return e == null ? ImmutableSet.of().iterator() : Iterators.singletonIterator(e);
    }

    @Override
    public int size() {
        return this.getConnectingEdge() == null ? 0 : 1;
    }

    @Override
    public boolean contains(@Nullable Object object) {
        E e = this.getConnectingEdge();
        return e != null && e.equals(object);
    }

    @Nullable
    private E getConnectingEdge() {
        return this.nodeToOutEdge.get(this.targetNode);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

