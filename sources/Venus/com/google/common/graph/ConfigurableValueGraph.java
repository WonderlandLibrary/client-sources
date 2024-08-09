/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.AbstractValueGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.Graphs;
import com.google.common.graph.MapIteratorCache;
import com.google.common.graph.MapRetrievalCache;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

class ConfigurableValueGraph<N, V>
extends AbstractValueGraph<N, V> {
    private final boolean isDirected;
    private final boolean allowsSelfLoops;
    private final ElementOrder<N> nodeOrder;
    protected final MapIteratorCache<N, GraphConnections<N, V>> nodeConnections;
    protected long edgeCount;

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this(abstractGraphBuilder, abstractGraphBuilder.nodeOrder.createMap(abstractGraphBuilder.expectedNodeCount.or(10)), 0L);
    }

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder, Map<N, GraphConnections<N, V>> map, long l) {
        this.isDirected = abstractGraphBuilder.directed;
        this.allowsSelfLoops = abstractGraphBuilder.allowsSelfLoops;
        this.nodeOrder = abstractGraphBuilder.nodeOrder.cast();
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<N, GraphConnections<N, V>>(map) : new MapIteratorCache<N, GraphConnections<N, V>>(map);
        this.edgeCount = Graphs.checkNonNegative(l);
    }

    @Override
    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    @Override
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    @Override
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override
    public Set<N> adjacentNodes(Object object) {
        return this.checkedConnections(object).adjacentNodes();
    }

    @Override
    public Set<N> predecessors(Object object) {
        return this.checkedConnections(object).predecessors();
    }

    @Override
    public Set<N> successors(Object object) {
        return this.checkedConnections(object).successors();
    }

    @Override
    public V edgeValueOrDefault(Object object, Object object2, @Nullable V v) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(object);
        if (graphConnections == null) {
            return v;
        }
        V v2 = graphConnections.value(object2);
        if (v2 == null) {
            return v;
        }
        return v2;
    }

    @Override
    protected long edgeCount() {
        return this.edgeCount;
    }

    protected final GraphConnections<N, V> checkedConnections(Object object) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(object);
        if (graphConnections == null) {
            Preconditions.checkNotNull(object);
            throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", object));
        }
        return graphConnections;
    }

    protected final boolean containsNode(@Nullable Object object) {
        return this.nodeConnections.containsKey(object);
    }
}

