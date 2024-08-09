/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.graph.GraphConnections;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class UndirectedGraphConnections<N, V>
implements GraphConnections<N, V> {
    private final Map<N, V> adjacentNodeValues;

    private UndirectedGraphConnections(Map<N, V> map) {
        this.adjacentNodeValues = Preconditions.checkNotNull(map);
    }

    static <N, V> UndirectedGraphConnections<N, V> of() {
        return new UndirectedGraphConnections(new HashMap(2, 1.0f));
    }

    static <N, V> UndirectedGraphConnections<N, V> ofImmutable(Map<N, V> map) {
        return new UndirectedGraphConnections<N, V>(ImmutableMap.copyOf(map));
    }

    @Override
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    @Override
    public Set<N> predecessors() {
        return this.adjacentNodes();
    }

    @Override
    public Set<N> successors() {
        return this.adjacentNodes();
    }

    @Override
    public V value(Object object) {
        return this.adjacentNodeValues.get(object);
    }

    @Override
    public void removePredecessor(Object object) {
        V v = this.removeSuccessor(object);
    }

    @Override
    public V removeSuccessor(Object object) {
        return this.adjacentNodeValues.remove(object);
    }

    @Override
    public void addPredecessor(N n, V v) {
        V v2 = this.addSuccessor(n, v);
    }

    @Override
    public V addSuccessor(N n, V v) {
        return this.adjacentNodeValues.put(n, v);
    }
}

