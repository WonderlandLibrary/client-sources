/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.ConfigurableValueGraph;
import com.google.common.graph.DirectedGraphConnections;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.UndirectedGraphConnections;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

final class ConfigurableMutableValueGraph<N, V>
extends ConfigurableValueGraph<N, V>
implements MutableValueGraph<N, V> {
    ConfigurableMutableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        super(abstractGraphBuilder);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addNode(N n) {
        Preconditions.checkNotNull(n, "node");
        if (this.containsNode(n)) {
            return true;
        }
        this.addNodeInternal(n);
        return false;
    }

    @CanIgnoreReturnValue
    private GraphConnections<N, V> addNodeInternal(N n) {
        GraphConnections<N, V> graphConnections = this.newConnections();
        Preconditions.checkState(this.nodeConnections.put(n, graphConnections) == null);
        return graphConnections;
    }

    @Override
    @CanIgnoreReturnValue
    public V putEdgeValue(N n, N n2, V v) {
        GraphConnections<N, V> graphConnections;
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(v, "value");
        if (!this.allowsSelfLoops()) {
            Preconditions.checkArgument(!n.equals(n2), "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n);
        }
        if ((graphConnections = (GraphConnections<N, V>)this.nodeConnections.get(n)) == null) {
            graphConnections = this.addNodeInternal(n);
        }
        V v2 = graphConnections.addSuccessor(n2, v);
        GraphConnections<N, V> graphConnections2 = (GraphConnections<N, V>)this.nodeConnections.get(n2);
        if (graphConnections2 == null) {
            graphConnections2 = this.addNodeInternal(n2);
        }
        graphConnections2.addPredecessor(n, v);
        if (v2 == null) {
            Graphs.checkPositive(++this.edgeCount);
        }
        return v2;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeNode(Object object) {
        Preconditions.checkNotNull(object, "node");
        GraphConnections graphConnections = (GraphConnections)this.nodeConnections.get(object);
        if (graphConnections == null) {
            return true;
        }
        if (this.allowsSelfLoops() && graphConnections.removeSuccessor(object) != null) {
            graphConnections.removePredecessor(object);
            --this.edgeCount;
        }
        for (Object n : graphConnections.successors()) {
            ((GraphConnections)this.nodeConnections.getWithoutCaching(n)).removePredecessor(object);
            --this.edgeCount;
        }
        if (this.isDirected()) {
            for (Object n : graphConnections.predecessors()) {
                Preconditions.checkState(((GraphConnections)this.nodeConnections.getWithoutCaching(n)).removeSuccessor(object) != null);
                --this.edgeCount;
            }
        }
        this.nodeConnections.remove(object);
        Graphs.checkNonNegative(this.edgeCount);
        return false;
    }

    @Override
    @CanIgnoreReturnValue
    public V removeEdge(Object object, Object object2) {
        Preconditions.checkNotNull(object, "nodeU");
        Preconditions.checkNotNull(object2, "nodeV");
        GraphConnections graphConnections = (GraphConnections)this.nodeConnections.get(object);
        GraphConnections graphConnections2 = (GraphConnections)this.nodeConnections.get(object2);
        if (graphConnections == null || graphConnections2 == null) {
            return null;
        }
        Object v = graphConnections.removeSuccessor(object2);
        if (v != null) {
            graphConnections2.removePredecessor(object);
            Graphs.checkNonNegative(--this.edgeCount);
        }
        return v;
    }

    private GraphConnections<N, V> newConnections() {
        return this.isDirected() ? DirectedGraphConnections.of() : UndirectedGraphConnections.of();
    }
}

