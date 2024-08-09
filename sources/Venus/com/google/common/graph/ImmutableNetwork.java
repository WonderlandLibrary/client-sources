/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.graph.ConfigurableNetwork;
import com.google.common.graph.DirectedMultiNetworkConnections;
import com.google.common.graph.DirectedNetworkConnections;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.Network;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.NetworkConnections;
import com.google.common.graph.UndirectedMultiNetworkConnections;
import com.google.common.graph.UndirectedNetworkConnections;
import java.util.Map;
import java.util.Set;

@Beta
public final class ImmutableNetwork<N, E>
extends ConfigurableNetwork<N, E> {
    private ImmutableNetwork(Network<N, E> network) {
        super(NetworkBuilder.from(network), ImmutableNetwork.getNodeConnections(network), ImmutableNetwork.getEdgeToReferenceNode(network));
    }

    public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> network) {
        return network instanceof ImmutableNetwork ? (ImmutableNetwork<N, E>)network : new ImmutableNetwork<N, E>(network);
    }

    @Deprecated
    public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> immutableNetwork) {
        return Preconditions.checkNotNull(immutableNetwork);
    }

    @Override
    public ImmutableGraph<N> asGraph() {
        Graph graph = super.asGraph();
        return new ImmutableGraph<N>(this, graph){
            final Graph val$asGraph;
            final ImmutableNetwork this$0;
            {
                this.this$0 = immutableNetwork;
                this.val$asGraph = graph;
            }

            @Override
            protected Graph<N> delegate() {
                return this.val$asGraph;
            }
        };
    }

    private static <N, E> Map<N, NetworkConnections<N, E>> getNodeConnections(Network<N, E> network) {
        ImmutableMap.Builder<N, NetworkConnections<N, E>> builder = ImmutableMap.builder();
        for (N n : network.nodes()) {
            builder.put(n, ImmutableNetwork.connectionsOf(network, n));
        }
        return builder.build();
    }

    private static <N, E> Map<E, N> getEdgeToReferenceNode(Network<N, E> network) {
        ImmutableMap.Builder<E, N> builder = ImmutableMap.builder();
        for (E e : network.edges()) {
            builder.put(e, network.incidentNodes(e).nodeU());
        }
        return builder.build();
    }

    private static <N, E> NetworkConnections<N, E> connectionsOf(Network<N, E> network, N n) {
        if (network.isDirected()) {
            Map<E, N> map = Maps.asMap(network.inEdges(n), ImmutableNetwork.sourceNodeFn(network));
            Map<E, N> map2 = Maps.asMap(network.outEdges(n), ImmutableNetwork.targetNodeFn(network));
            int n2 = network.edgesConnecting(n, n).size();
            return network.allowsParallelEdges() ? DirectedMultiNetworkConnections.ofImmutable(map, map2, n2) : DirectedNetworkConnections.ofImmutable(map, map2, n2);
        }
        Map<E, N> map = Maps.asMap(network.incidentEdges(n), ImmutableNetwork.adjacentNodeFn(network, n));
        return network.allowsParallelEdges() ? UndirectedMultiNetworkConnections.ofImmutable(map) : UndirectedNetworkConnections.ofImmutable(map);
    }

    private static <N, E> Function<E, N> sourceNodeFn(Network<N, E> network) {
        return new Function<E, N>(network){
            final Network val$network;
            {
                this.val$network = network;
            }

            @Override
            public N apply(E e) {
                return this.val$network.incidentNodes(e).source();
            }
        };
    }

    private static <N, E> Function<E, N> targetNodeFn(Network<N, E> network) {
        return new Function<E, N>(network){
            final Network val$network;
            {
                this.val$network = network;
            }

            @Override
            public N apply(E e) {
                return this.val$network.incidentNodes(e).target();
            }
        };
    }

    private static <N, E> Function<E, N> adjacentNodeFn(Network<N, E> network, N n) {
        return new Function<E, N>(network, n){
            final Network val$network;
            final Object val$node;
            {
                this.val$network = network;
                this.val$node = object;
            }

            @Override
            public N apply(E e) {
                return this.val$network.incidentNodes(e).adjacentNode(this.val$node);
            }
        };
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
    public Set outEdges(Object object) {
        return super.outEdges(object);
    }

    @Override
    public Set inEdges(Object object) {
        return super.inEdges(object);
    }

    @Override
    public Set edgesConnecting(Object object, Object object2) {
        return super.edgesConnecting(object, object2);
    }

    @Override
    public Set adjacentNodes(Object object) {
        return super.adjacentNodes(object);
    }

    @Override
    public EndpointPair incidentNodes(Object object) {
        return super.incidentNodes(object);
    }

    @Override
    public Set incidentEdges(Object object) {
        return super.incidentEdges(object);
    }

    @Override
    public ElementOrder edgeOrder() {
        return super.edgeOrder();
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
    public boolean allowsParallelEdges() {
        return super.allowsParallelEdges();
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

    @Override
    public Graph asGraph() {
        return this.asGraph();
    }
}

