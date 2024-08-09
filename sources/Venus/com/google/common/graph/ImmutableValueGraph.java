/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.graph.DirectedGraphConnections;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.UndirectedGraphConnections;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import javax.annotation.Nullable;

@Beta
public final class ImmutableValueGraph<N, V>
extends ImmutableGraph.ValueBackedImpl<N, V>
implements ValueGraph<N, V> {
    private ImmutableValueGraph(ValueGraph<N, V> valueGraph) {
        super(ValueGraphBuilder.from(valueGraph), ImmutableValueGraph.getNodeConnections(valueGraph), valueGraph.edges().size());
    }

    public static <N, V> ImmutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        return valueGraph instanceof ImmutableValueGraph ? (ImmutableValueGraph<N, V>)valueGraph : new ImmutableValueGraph<N, V>(valueGraph);
    }

    @Deprecated
    public static <N, V> ImmutableValueGraph<N, V> copyOf(ImmutableValueGraph<N, V> immutableValueGraph) {
        return Preconditions.checkNotNull(immutableValueGraph);
    }

    private static <N, V> ImmutableMap<N, GraphConnections<N, V>> getNodeConnections(ValueGraph<N, V> valueGraph) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (Object n : valueGraph.nodes()) {
            builder.put(n, ImmutableValueGraph.connectionsOf(valueGraph, n));
        }
        return builder.build();
    }

    private static <N, V> GraphConnections<N, V> connectionsOf(ValueGraph<N, V> valueGraph, N n) {
        Function function = new Function<N, V>(valueGraph, n){
            final ValueGraph val$graph;
            final Object val$node;
            {
                this.val$graph = valueGraph;
                this.val$node = object;
            }

            @Override
            public V apply(N n) {
                return this.val$graph.edgeValue(this.val$node, n);
            }
        };
        return valueGraph.isDirected() ? DirectedGraphConnections.ofImmutable(valueGraph.predecessors(n), Maps.asMap(valueGraph.successors(n), function)) : UndirectedGraphConnections.ofImmutable(Maps.asMap(valueGraph.adjacentNodes(n), function));
    }

    @Override
    public V edgeValue(Object object, Object object2) {
        return this.backingValueGraph.edgeValue(object, object2);
    }

    @Override
    public V edgeValueOrDefault(Object object, Object object2, @Nullable V v) {
        return this.backingValueGraph.edgeValueOrDefault(object, object2, v);
    }

    @Override
    public String toString() {
        return this.backingValueGraph.toString();
    }
}

