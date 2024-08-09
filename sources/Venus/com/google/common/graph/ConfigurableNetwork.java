/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.AbstractNetwork;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MapIteratorCache;
import com.google.common.graph.MapRetrievalCache;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.NetworkConnections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.Nullable;

class ConfigurableNetwork<N, E>
extends AbstractNetwork<N, E> {
    private final boolean isDirected;
    private final boolean allowsParallelEdges;
    private final boolean allowsSelfLoops;
    private final ElementOrder<N> nodeOrder;
    private final ElementOrder<E> edgeOrder;
    protected final MapIteratorCache<N, NetworkConnections<N, E>> nodeConnections;
    protected final MapIteratorCache<E, N> edgeToReferenceNode;

    ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        this(networkBuilder, networkBuilder.nodeOrder.createMap(networkBuilder.expectedNodeCount.or(10)), networkBuilder.edgeOrder.createMap(networkBuilder.expectedEdgeCount.or(20)));
    }

    ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder, Map<N, NetworkConnections<N, E>> map, Map<E, N> map2) {
        this.isDirected = networkBuilder.directed;
        this.allowsParallelEdges = networkBuilder.allowsParallelEdges;
        this.allowsSelfLoops = networkBuilder.allowsSelfLoops;
        this.nodeOrder = networkBuilder.nodeOrder.cast();
        this.edgeOrder = networkBuilder.edgeOrder.cast();
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<N, NetworkConnections<N, E>>(map) : new MapIteratorCache<N, NetworkConnections<N, E>>(map);
        this.edgeToReferenceNode = new MapIteratorCache<E, N>(map2);
    }

    @Override
    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    @Override
    public Set<E> edges() {
        return this.edgeToReferenceNode.unmodifiableKeySet();
    }

    @Override
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override
    public boolean allowsParallelEdges() {
        return this.allowsParallelEdges;
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
    public ElementOrder<E> edgeOrder() {
        return this.edgeOrder;
    }

    @Override
    public Set<E> incidentEdges(Object object) {
        return this.checkedConnections(object).incidentEdges();
    }

    @Override
    public EndpointPair<N> incidentNodes(Object object) {
        N n = this.checkedReferenceNode(object);
        N n2 = this.nodeConnections.get(n).oppositeNode(object);
        return EndpointPair.of(this, n, n2);
    }

    @Override
    public Set<N> adjacentNodes(Object object) {
        return this.checkedConnections(object).adjacentNodes();
    }

    @Override
    public Set<E> edgesConnecting(Object object, Object object2) {
        NetworkConnections<N, E> networkConnections = this.checkedConnections(object);
        if (!this.allowsSelfLoops && object == object2) {
            return ImmutableSet.of();
        }
        Preconditions.checkArgument(this.containsNode(object2), "Node %s is not an element of this graph.", object2);
        return networkConnections.edgesConnecting(object2);
    }

    @Override
    public Set<E> inEdges(Object object) {
        return this.checkedConnections(object).inEdges();
    }

    @Override
    public Set<E> outEdges(Object object) {
        return this.checkedConnections(object).outEdges();
    }

    @Override
    public Set<N> predecessors(Object object) {
        return this.checkedConnections(object).predecessors();
    }

    @Override
    public Set<N> successors(Object object) {
        return this.checkedConnections(object).successors();
    }

    protected final NetworkConnections<N, E> checkedConnections(Object object) {
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(object);
        if (networkConnections == null) {
            Preconditions.checkNotNull(object);
            throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", object));
        }
        return networkConnections;
    }

    protected final N checkedReferenceNode(Object object) {
        N n = this.edgeToReferenceNode.get(object);
        if (n == null) {
            Preconditions.checkNotNull(object);
            throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", object));
        }
        return n;
    }

    protected final boolean containsNode(@Nullable Object object) {
        return this.nodeConnections.containsKey(object);
    }

    protected final boolean containsEdge(@Nullable Object object) {
        return this.edgeToReferenceNode.containsKey(object);
    }
}

