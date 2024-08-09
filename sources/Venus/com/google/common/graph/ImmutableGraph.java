/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.ConfigurableValueGraph;
import com.google.common.graph.DirectedGraphConnections;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.ForwardingGraph;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.GraphConstants;
import com.google.common.graph.UndirectedGraphConnections;
import com.google.common.graph.ValueGraph;
import java.util.Set;

@Beta
public abstract class ImmutableGraph<N>
extends ForwardingGraph<N> {
    ImmutableGraph() {
    }

    public static <N> ImmutableGraph<N> copyOf(Graph<N> graph) {
        return graph instanceof ImmutableGraph ? (ImmutableGraph<N>)graph : new ValueBackedImpl<N, GraphConstants.Presence>(GraphBuilder.from(graph), ImmutableGraph.getNodeConnections(graph), graph.edges().size());
    }

    @Deprecated
    public static <N> ImmutableGraph<N> copyOf(ImmutableGraph<N> immutableGraph) {
        return Preconditions.checkNotNull(immutableGraph);
    }

    private static <N> ImmutableMap<N, GraphConnections<N, GraphConstants.Presence>> getNodeConnections(Graph<N> graph) {
        ImmutableMap.Builder<N, GraphConnections<N, GraphConstants.Presence>> builder = ImmutableMap.builder();
        for (N n : graph.nodes()) {
            builder.put(n, ImmutableGraph.connectionsOf(graph, n));
        }
        return builder.build();
    }

    private static <N> GraphConnections<N, GraphConstants.Presence> connectionsOf(Graph<N> graph, N n) {
        Function<Object, GraphConstants.Presence> function = Functions.constant(GraphConstants.Presence.EDGE_EXISTS);
        return graph.isDirected() ? DirectedGraphConnections.ofImmutable(graph.predecessors(n), Maps.asMap(graph.successors(n), function)) : UndirectedGraphConnections.ofImmutable(Maps.asMap(graph.adjacentNodes(n), function));
    }

    @Override
    public int outDegree(Object object) {
        return super.outDegree(object);
    }

    @Override
    public int inDegree(Object object) {
        return super.inDegree(object);
    }

    @Override
    public int degree(Object object) {
        return super.degree(object);
    }

    @Override
    public Set successors(Object object) {
        return super.successors(object);
    }

    @Override
    public Set predecessors(Object object) {
        return super.predecessors(object);
    }

    @Override
    public Set adjacentNodes(Object object) {
        return super.adjacentNodes(object);
    }

    @Override
    public ElementOrder nodeOrder() {
        return super.nodeOrder();
    }

    @Override
    public boolean allowsSelfLoops() {
        return super.allowsSelfLoops();
    }

    @Override
    public boolean isDirected() {
        return super.isDirected();
    }

    @Override
    public Set edges() {
        return super.edges();
    }

    @Override
    public Set nodes() {
        return super.nodes();
    }

    static class ValueBackedImpl<N, V>
    extends ImmutableGraph<N> {
        protected final ValueGraph<N, V> backingValueGraph;

        ValueBackedImpl(AbstractGraphBuilder<? super N> abstractGraphBuilder, ImmutableMap<N, GraphConnections<N, V>> immutableMap, long l) {
            this.backingValueGraph = new ConfigurableValueGraph<N, V>(abstractGraphBuilder, immutableMap, l);
        }

        @Override
        protected Graph<N> delegate() {
            return this.backingValueGraph;
        }
    }
}

