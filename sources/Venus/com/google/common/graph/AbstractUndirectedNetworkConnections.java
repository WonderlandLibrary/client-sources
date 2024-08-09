/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.graph.NetworkConnections;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

abstract class AbstractUndirectedNetworkConnections<N, E>
implements NetworkConnections<N, E> {
    protected final Map<E, N> incidentEdgeMap;

    protected AbstractUndirectedNetworkConnections(Map<E, N> map) {
        this.incidentEdgeMap = Preconditions.checkNotNull(map);
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
    public Set<E> incidentEdges() {
        return Collections.unmodifiableSet(this.incidentEdgeMap.keySet());
    }

    @Override
    public Set<E> inEdges() {
        return this.incidentEdges();
    }

    @Override
    public Set<E> outEdges() {
        return this.incidentEdges();
    }

    @Override
    public N oppositeNode(Object object) {
        return Preconditions.checkNotNull(this.incidentEdgeMap.get(object));
    }

    @Override
    public N removeInEdge(Object object, boolean bl) {
        if (!bl) {
            return this.removeOutEdge(object);
        }
        return null;
    }

    @Override
    public N removeOutEdge(Object object) {
        N n = this.incidentEdgeMap.remove(object);
        return Preconditions.checkNotNull(n);
    }

    @Override
    public void addInEdge(E e, N n, boolean bl) {
        if (!bl) {
            this.addOutEdge(e, n);
        }
    }

    @Override
    public void addOutEdge(E e, N n) {
        N n2 = this.incidentEdgeMap.put(e, n);
        Preconditions.checkState(n2 == null);
    }
}

