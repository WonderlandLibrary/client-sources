/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.graph.AbstractGraph;
import com.google.common.graph.AbstractNetwork;
import com.google.common.graph.AbstractValueGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.Network;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public final class Graphs {
    private Graphs() {
    }

    public static boolean hasCycle(Graph<?> graph) {
        int n = graph.edges().size();
        if (n == 0) {
            return true;
        }
        if (!graph.isDirected() && n >= graph.nodes().size()) {
            return false;
        }
        HashMap<Object, NodeVisitState> hashMap = Maps.newHashMapWithExpectedSize(graph.nodes().size());
        for (Object obj : graph.nodes()) {
            if (!Graphs.subgraphHasCycle(graph, hashMap, obj, null)) continue;
            return false;
        }
        return true;
    }

    public static boolean hasCycle(Network<?, ?> network) {
        if (!network.isDirected() && network.allowsParallelEdges() && network.edges().size() > network.asGraph().edges().size()) {
            return false;
        }
        return Graphs.hasCycle(network.asGraph());
    }

    private static boolean subgraphHasCycle(Graph<?> graph, Map<Object, NodeVisitState> map, Object object, @Nullable Object object2) {
        NodeVisitState nodeVisitState = map.get(object);
        if (nodeVisitState == NodeVisitState.COMPLETE) {
            return true;
        }
        if (nodeVisitState == NodeVisitState.PENDING) {
            return false;
        }
        map.put(object, NodeVisitState.PENDING);
        for (Object obj : graph.successors(object)) {
            if (!Graphs.canTraverseWithoutReusingEdge(graph, obj, object2) || !Graphs.subgraphHasCycle(graph, map, obj, object)) continue;
            return false;
        }
        map.put(object, NodeVisitState.COMPLETE);
        return true;
    }

    private static boolean canTraverseWithoutReusingEdge(Graph<?> graph, Object object, @Nullable Object object2) {
        return !graph.isDirected() && Objects.equal(object2, object);
    }

    public static <N> Graph<N> transitiveClosure(Graph<N> graph) {
        MutableGraph mutableGraph = GraphBuilder.from(graph).allowsSelfLoops(false).build();
        if (graph.isDirected()) {
            for (N n : graph.nodes()) {
                for (N n2 : Graphs.reachableNodes(graph, n)) {
                    mutableGraph.putEdge(n, n2);
                }
            }
        } else {
            HashSet<N> hashSet = new HashSet<N>();
            for (N n : graph.nodes()) {
                if (hashSet.contains(n)) continue;
                Set<N> set = Graphs.reachableNodes(graph, n);
                hashSet.addAll(set);
                int n3 = 1;
                for (N n4 : set) {
                    for (N n5 : Iterables.limit(set, n3++)) {
                        mutableGraph.putEdge(n4, n5);
                    }
                }
            }
        }
        return mutableGraph;
    }

    public static <N> Set<N> reachableNodes(Graph<N> graph, Object object) {
        Preconditions.checkArgument(graph.nodes().contains(object), "Node %s is not an element of this graph.", object);
        LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<Object>();
        ArrayDeque<Object> arrayDeque = new ArrayDeque<Object>();
        linkedHashSet.add(object);
        arrayDeque.add(object);
        while (!arrayDeque.isEmpty()) {
            Object e = arrayDeque.remove();
            for (N n : graph.successors(e)) {
                if (!linkedHashSet.add(n)) continue;
                arrayDeque.add(n);
            }
        }
        return Collections.unmodifiableSet(linkedHashSet);
    }

    public static boolean equivalent(@Nullable Graph<?> graph, @Nullable Graph<?> graph2) {
        if (graph == graph2) {
            return false;
        }
        if (graph == null || graph2 == null) {
            return true;
        }
        return graph.isDirected() == graph2.isDirected() && graph.nodes().equals(graph2.nodes()) && graph.edges().equals(graph2.edges());
    }

    public static boolean equivalent(@Nullable ValueGraph<?, ?> valueGraph, @Nullable ValueGraph<?, ?> valueGraph2) {
        if (valueGraph == valueGraph2) {
            return false;
        }
        if (valueGraph == null || valueGraph2 == null) {
            return true;
        }
        if (valueGraph.isDirected() != valueGraph2.isDirected() || !valueGraph.nodes().equals(valueGraph2.nodes()) || !valueGraph.edges().equals(valueGraph2.edges())) {
            return true;
        }
        for (EndpointPair endpointPair : valueGraph.edges()) {
            if (valueGraph.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()).equals(valueGraph2.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()))) continue;
            return true;
        }
        return false;
    }

    public static boolean equivalent(@Nullable Network<?, ?> network, @Nullable Network<?, ?> network2) {
        if (network == network2) {
            return false;
        }
        if (network == null || network2 == null) {
            return true;
        }
        if (network.isDirected() != network2.isDirected() || !network.nodes().equals(network2.nodes()) || !network.edges().equals(network2.edges())) {
            return true;
        }
        for (Object obj : network.edges()) {
            if (network.incidentNodes(obj).equals(network2.incidentNodes(obj))) continue;
            return true;
        }
        return false;
    }

    public static <N> Graph<N> transpose(Graph<N> graph) {
        if (!graph.isDirected()) {
            return graph;
        }
        if (graph instanceof TransposedGraph) {
            return TransposedGraph.access$000((TransposedGraph)graph);
        }
        return new TransposedGraph<N>(graph);
    }

    public static <N, V> ValueGraph<N, V> transpose(ValueGraph<N, V> valueGraph) {
        if (!valueGraph.isDirected()) {
            return valueGraph;
        }
        if (valueGraph instanceof TransposedValueGraph) {
            return TransposedValueGraph.access$100((TransposedValueGraph)valueGraph);
        }
        return new TransposedValueGraph<N, V>(valueGraph);
    }

    public static <N, E> Network<N, E> transpose(Network<N, E> network) {
        if (!network.isDirected()) {
            return network;
        }
        if (network instanceof TransposedNetwork) {
            return TransposedNetwork.access$200((TransposedNetwork)network);
        }
        return new TransposedNetwork<N, E>(network);
    }

    public static <N> MutableGraph<N> inducedSubgraph(Graph<N> graph, Iterable<? extends N> iterable) {
        MutableGraph mutableGraph = GraphBuilder.from(graph).build();
        for (Object object : iterable) {
            mutableGraph.addNode(object);
        }
        for (Object object : mutableGraph.nodes()) {
            for (N n : graph.successors(object)) {
                if (!mutableGraph.nodes().contains(n)) continue;
                mutableGraph.putEdge(object, n);
            }
        }
        return mutableGraph;
    }

    public static <N, V> MutableValueGraph<N, V> inducedSubgraph(ValueGraph<N, V> valueGraph, Iterable<? extends N> iterable) {
        MutableValueGraph mutableValueGraph = ValueGraphBuilder.from(valueGraph).build();
        for (Object object : iterable) {
            mutableValueGraph.addNode(object);
        }
        for (Object object : mutableValueGraph.nodes()) {
            for (Object n : valueGraph.successors(object)) {
                if (!mutableValueGraph.nodes().contains(n)) continue;
                mutableValueGraph.putEdgeValue(object, n, valueGraph.edgeValue(object, n));
            }
        }
        return mutableValueGraph;
    }

    public static <N, E> MutableNetwork<N, E> inducedSubgraph(Network<N, E> network, Iterable<? extends N> iterable) {
        MutableNetwork mutableNetwork = NetworkBuilder.from(network).build();
        for (Object object : iterable) {
            mutableNetwork.addNode(object);
        }
        for (Object object : mutableNetwork.nodes()) {
            for (E e : network.outEdges(object)) {
                N n = network.incidentNodes(e).adjacentNode(object);
                if (!mutableNetwork.nodes().contains(n)) continue;
                mutableNetwork.addEdge(object, n, e);
            }
        }
        return mutableNetwork;
    }

    public static <N> MutableGraph<N> copyOf(Graph<N> graph) {
        MutableGraph mutableGraph = GraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
        for (N object : graph.nodes()) {
            mutableGraph.addNode(object);
        }
        for (EndpointPair endpointPair : graph.edges()) {
            mutableGraph.putEdge(endpointPair.nodeU(), endpointPair.nodeV());
        }
        return mutableGraph;
    }

    public static <N, V> MutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        MutableValueGraph mutableValueGraph = ValueGraphBuilder.from(valueGraph).expectedNodeCount(valueGraph.nodes().size()).build();
        for (Object object : valueGraph.nodes()) {
            mutableValueGraph.addNode(object);
        }
        for (EndpointPair endpointPair : valueGraph.edges()) {
            mutableValueGraph.putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), valueGraph.edgeValue(endpointPair.nodeU(), endpointPair.nodeV()));
        }
        return mutableValueGraph;
    }

    public static <N, E> MutableNetwork<N, E> copyOf(Network<N, E> network) {
        MutableNetwork mutableNetwork = NetworkBuilder.from(network).expectedNodeCount(network.nodes().size()).expectedEdgeCount(network.edges().size()).build();
        for (Object object : network.nodes()) {
            mutableNetwork.addNode(object);
        }
        for (Object object : network.edges()) {
            EndpointPair<N> endpointPair = network.incidentNodes(object);
            mutableNetwork.addEdge(endpointPair.nodeU(), endpointPair.nodeV(), object);
        }
        return mutableNetwork;
    }

    @CanIgnoreReturnValue
    static int checkNonNegative(int n) {
        Preconditions.checkArgument(n >= 0, "Not true that %s is non-negative.", n);
        return n;
    }

    @CanIgnoreReturnValue
    static int checkPositive(int n) {
        Preconditions.checkArgument(n > 0, "Not true that %s is positive.", n);
        return n;
    }

    @CanIgnoreReturnValue
    static long checkNonNegative(long l) {
        Preconditions.checkArgument(l >= 0L, "Not true that %s is non-negative.", l);
        return l;
    }

    @CanIgnoreReturnValue
    static long checkPositive(long l) {
        Preconditions.checkArgument(l > 0L, "Not true that %s is positive.", l);
        return l;
    }

    private static enum NodeVisitState {
        PENDING,
        COMPLETE;

    }

    private static class TransposedNetwork<N, E>
    extends AbstractNetwork<N, E> {
        private final Network<N, E> network;

        TransposedNetwork(Network<N, E> network) {
            this.network = network;
        }

        @Override
        public Set<N> nodes() {
            return this.network.nodes();
        }

        @Override
        public Set<E> edges() {
            return this.network.edges();
        }

        @Override
        public boolean isDirected() {
            return this.network.isDirected();
        }

        @Override
        public boolean allowsParallelEdges() {
            return this.network.allowsParallelEdges();
        }

        @Override
        public boolean allowsSelfLoops() {
            return this.network.allowsSelfLoops();
        }

        @Override
        public ElementOrder<N> nodeOrder() {
            return this.network.nodeOrder();
        }

        @Override
        public ElementOrder<E> edgeOrder() {
            return this.network.edgeOrder();
        }

        @Override
        public Set<N> adjacentNodes(Object object) {
            return this.network.adjacentNodes(object);
        }

        @Override
        public Set<N> predecessors(Object object) {
            return this.network.successors(object);
        }

        @Override
        public Set<N> successors(Object object) {
            return this.network.predecessors(object);
        }

        @Override
        public Set<E> incidentEdges(Object object) {
            return this.network.incidentEdges(object);
        }

        @Override
        public Set<E> inEdges(Object object) {
            return this.network.outEdges(object);
        }

        @Override
        public Set<E> outEdges(Object object) {
            return this.network.inEdges(object);
        }

        @Override
        public EndpointPair<N> incidentNodes(Object object) {
            EndpointPair<N> endpointPair = this.network.incidentNodes(object);
            return EndpointPair.of(this.network, endpointPair.nodeV(), endpointPair.nodeU());
        }

        @Override
        public Set<E> adjacentEdges(Object object) {
            return this.network.adjacentEdges(object);
        }

        @Override
        public Set<E> edgesConnecting(Object object, Object object2) {
            return this.network.edgesConnecting(object2, object);
        }

        static Network access$200(TransposedNetwork transposedNetwork) {
            return transposedNetwork.network;
        }
    }

    private static class TransposedValueGraph<N, V>
    extends AbstractValueGraph<N, V> {
        private final ValueGraph<N, V> graph;

        TransposedValueGraph(ValueGraph<N, V> valueGraph) {
            this.graph = valueGraph;
        }

        @Override
        public Set<N> nodes() {
            return this.graph.nodes();
        }

        @Override
        protected long edgeCount() {
            return this.graph.edges().size();
        }

        @Override
        public boolean isDirected() {
            return this.graph.isDirected();
        }

        @Override
        public boolean allowsSelfLoops() {
            return this.graph.allowsSelfLoops();
        }

        @Override
        public ElementOrder<N> nodeOrder() {
            return this.graph.nodeOrder();
        }

        @Override
        public Set<N> adjacentNodes(Object object) {
            return this.graph.adjacentNodes(object);
        }

        @Override
        public Set<N> predecessors(Object object) {
            return this.graph.successors(object);
        }

        @Override
        public Set<N> successors(Object object) {
            return this.graph.predecessors(object);
        }

        @Override
        public V edgeValue(Object object, Object object2) {
            return this.graph.edgeValue(object2, object);
        }

        @Override
        public V edgeValueOrDefault(Object object, Object object2, @Nullable V v) {
            return this.graph.edgeValueOrDefault(object2, object, v);
        }

        static ValueGraph access$100(TransposedValueGraph transposedValueGraph) {
            return transposedValueGraph.graph;
        }
    }

    private static class TransposedGraph<N>
    extends AbstractGraph<N> {
        private final Graph<N> graph;

        TransposedGraph(Graph<N> graph) {
            this.graph = graph;
        }

        @Override
        public Set<N> nodes() {
            return this.graph.nodes();
        }

        @Override
        protected long edgeCount() {
            return this.graph.edges().size();
        }

        @Override
        public boolean isDirected() {
            return this.graph.isDirected();
        }

        @Override
        public boolean allowsSelfLoops() {
            return this.graph.allowsSelfLoops();
        }

        @Override
        public ElementOrder<N> nodeOrder() {
            return this.graph.nodeOrder();
        }

        @Override
        public Set<N> adjacentNodes(Object object) {
            return this.graph.adjacentNodes(object);
        }

        @Override
        public Set<N> predecessors(Object object) {
            return this.graph.successors(object);
        }

        @Override
        public Set<N> successors(Object object) {
            return this.graph.predecessors(object);
        }

        static Graph access$000(TransposedGraph transposedGraph) {
            return transposedGraph.graph;
        }
    }
}

