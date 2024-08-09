/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.graph.AbstractGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import java.util.Set;

abstract class ForwardingGraph<N>
extends AbstractGraph<N> {
    ForwardingGraph() {
    }

    protected abstract Graph<N> delegate();

    @Override
    public Set<N> nodes() {
        return this.delegate().nodes();
    }

    @Override
    public Set<EndpointPair<N>> edges() {
        return this.delegate().edges();
    }

    @Override
    public boolean isDirected() {
        return this.delegate().isDirected();
    }

    @Override
    public boolean allowsSelfLoops() {
        return this.delegate().allowsSelfLoops();
    }

    @Override
    public ElementOrder<N> nodeOrder() {
        return this.delegate().nodeOrder();
    }

    @Override
    public Set<N> adjacentNodes(Object object) {
        return this.delegate().adjacentNodes(object);
    }

    @Override
    public Set<N> predecessors(Object object) {
        return this.delegate().predecessors(object);
    }

    @Override
    public Set<N> successors(Object object) {
        return this.delegate().successors(object);
    }

    @Override
    public int degree(Object object) {
        return this.delegate().degree(object);
    }

    @Override
    public int inDegree(Object object) {
        return this.delegate().inDegree(object);
    }

    @Override
    public int outDegree(Object object) {
        return this.delegate().outDegree(object);
    }
}

