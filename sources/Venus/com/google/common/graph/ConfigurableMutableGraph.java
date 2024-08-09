/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.ConfigurableMutableValueGraph;
import com.google.common.graph.ForwardingGraph;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphConstants;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;

final class ConfigurableMutableGraph<N>
extends ForwardingGraph<N>
implements MutableGraph<N> {
    private final MutableValueGraph<N, GraphConstants.Presence> backingValueGraph;

    ConfigurableMutableGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this.backingValueGraph = new ConfigurableMutableValueGraph<N, GraphConstants.Presence>(abstractGraphBuilder);
    }

    @Override
    protected Graph<N> delegate() {
        return this.backingValueGraph;
    }

    @Override
    public boolean addNode(N n) {
        return this.backingValueGraph.addNode(n);
    }

    @Override
    public boolean putEdge(N n, N n2) {
        return this.backingValueGraph.putEdgeValue(n, n2, GraphConstants.Presence.EDGE_EXISTS) == null;
    }

    @Override
    public boolean removeNode(Object object) {
        return this.backingValueGraph.removeNode(object);
    }

    @Override
    public boolean removeEdge(Object object, Object object2) {
        return this.backingValueGraph.removeEdge(object, object2) != null;
    }
}

