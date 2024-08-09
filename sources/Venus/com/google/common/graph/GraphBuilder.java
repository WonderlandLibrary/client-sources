/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.ConfigurableMutableGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

@Beta
public final class GraphBuilder<N>
extends AbstractGraphBuilder<N> {
    private GraphBuilder(boolean bl) {
        super(bl);
    }

    public static GraphBuilder<Object> directed() {
        return new GraphBuilder<Object>(true);
    }

    public static GraphBuilder<Object> undirected() {
        return new GraphBuilder<Object>(false);
    }

    public static <N> GraphBuilder<N> from(Graph<N> graph) {
        return new GraphBuilder<N>(graph.isDirected()).allowsSelfLoops(graph.allowsSelfLoops()).nodeOrder(graph.nodeOrder());
    }

    public GraphBuilder<N> allowsSelfLoops(boolean bl) {
        this.allowsSelfLoops = bl;
        return this;
    }

    public GraphBuilder<N> expectedNodeCount(int n) {
        this.expectedNodeCount = Optional.of(Graphs.checkNonNegative(n));
        return this;
    }

    public <N1 extends N> GraphBuilder<N1> nodeOrder(ElementOrder<N1> elementOrder) {
        GraphBuilder<N1> graphBuilder = this.cast();
        graphBuilder.nodeOrder = Preconditions.checkNotNull(elementOrder);
        return graphBuilder;
    }

    public <N1 extends N> MutableGraph<N1> build() {
        return new ConfigurableMutableGraph(this);
    }

    private <N1 extends N> GraphBuilder<N1> cast() {
        return this;
    }
}

