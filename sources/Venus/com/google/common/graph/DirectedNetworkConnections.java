/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.graph.AbstractDirectedNetworkConnections;
import com.google.common.graph.EdgesConnecting;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

final class DirectedNetworkConnections<N, E>
extends AbstractDirectedNetworkConnections<N, E> {
    protected DirectedNetworkConnections(Map<E, N> map, Map<E, N> map2, int n) {
        super(map, map2, n);
    }

    static <N, E> DirectedNetworkConnections<N, E> of() {
        return new DirectedNetworkConnections(HashBiMap.create(2), HashBiMap.create(2), 0);
    }

    static <N, E> DirectedNetworkConnections<N, E> ofImmutable(Map<E, N> map, Map<E, N> map2, int n) {
        return new DirectedNetworkConnections<N, E>(ImmutableBiMap.copyOf(map), ImmutableBiMap.copyOf(map2), n);
    }

    @Override
    public Set<N> predecessors() {
        return Collections.unmodifiableSet(((BiMap)this.inEdgeMap).values());
    }

    @Override
    public Set<N> successors() {
        return Collections.unmodifiableSet(((BiMap)this.outEdgeMap).values());
    }

    @Override
    public Set<E> edgesConnecting(Object object) {
        return new EdgesConnecting(((BiMap)this.outEdgeMap).inverse(), object);
    }
}

