/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.graph.ConfigurableNetwork;
import com.google.common.graph.DirectedMultiNetworkConnections;
import com.google.common.graph.DirectedNetworkConnections;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.NetworkConnections;
import com.google.common.graph.UndirectedMultiNetworkConnections;
import com.google.common.graph.UndirectedNetworkConnections;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

final class ConfigurableMutableNetwork<N, E>
extends ConfigurableNetwork<N, E>
implements MutableNetwork<N, E> {
    ConfigurableMutableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        super(networkBuilder);
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
    private NetworkConnections<N, E> addNodeInternal(N n) {
        NetworkConnections<N, E> networkConnections = this.newConnections();
        Preconditions.checkState(this.nodeConnections.put(n, networkConnections) == null);
        return networkConnections;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addEdge(N n, N n2, E e) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(e, "edge");
        if (this.containsEdge(e)) {
            EndpointPair endpointPair = this.incidentNodes(e);
            EndpointPair<N> endpointPair2 = EndpointPair.of(this, n, n2);
            Preconditions.checkArgument(endpointPair.equals(endpointPair2), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", e, endpointPair, endpointPair2);
            return true;
        }
        NetworkConnections<N, E> networkConnections = (NetworkConnections<N, E>)this.nodeConnections.get(n);
        if (!this.allowsParallelEdges()) {
            Preconditions.checkArgument(networkConnections == null || !networkConnections.successors().contains(n2), "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", n, n2);
        }
        boolean bl = n.equals(n2);
        if (!this.allowsSelfLoops()) {
            Preconditions.checkArgument(!bl, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n);
        }
        if (networkConnections == null) {
            networkConnections = this.addNodeInternal(n);
        }
        networkConnections.addOutEdge(e, n2);
        NetworkConnections<N, E> networkConnections2 = (NetworkConnections<N, E>)this.nodeConnections.get(n2);
        if (networkConnections2 == null) {
            networkConnections2 = this.addNodeInternal(n2);
        }
        networkConnections2.addInEdge(e, n, bl);
        this.edgeToReferenceNode.put(e, n);
        return false;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeNode(Object object) {
        Preconditions.checkNotNull(object, "node");
        NetworkConnections networkConnections = (NetworkConnections)this.nodeConnections.get(object);
        if (networkConnections == null) {
            return true;
        }
        for (Object e : ImmutableList.copyOf(networkConnections.incidentEdges())) {
            this.removeEdge(e);
        }
        this.nodeConnections.remove(object);
        return false;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeEdge(Object object) {
        Preconditions.checkNotNull(object, "edge");
        Object v = this.edgeToReferenceNode.get(object);
        if (v == null) {
            return true;
        }
        NetworkConnections networkConnections = (NetworkConnections)this.nodeConnections.get(v);
        Object n = networkConnections.oppositeNode(object);
        NetworkConnections networkConnections2 = (NetworkConnections)this.nodeConnections.get(n);
        networkConnections.removeOutEdge(object);
        networkConnections2.removeInEdge(object, this.allowsSelfLoops() && v.equals(n));
        this.edgeToReferenceNode.remove(object);
        return false;
    }

    private NetworkConnections<N, E> newConnections() {
        return this.isDirected() ? (this.allowsParallelEdges() ? DirectedMultiNetworkConnections.of() : DirectedNetworkConnections.of()) : (this.allowsParallelEdges() ? UndirectedMultiNetworkConnections.of() : UndirectedNetworkConnections.of());
    }
}

