/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.common.graph.AbstractUndirectedNetworkConnections;
import com.google.common.graph.MultiEdgesConnecting;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

final class UndirectedMultiNetworkConnections<N, E>
extends AbstractUndirectedNetworkConnections<N, E> {
    @LazyInit
    private transient Reference<Multiset<N>> adjacentNodesReference;

    private UndirectedMultiNetworkConnections(Map<E, N> map) {
        super(map);
    }

    static <N, E> UndirectedMultiNetworkConnections<N, E> of() {
        return new UndirectedMultiNetworkConnections(new HashMap(2, 1.0f));
    }

    static <N, E> UndirectedMultiNetworkConnections<N, E> ofImmutable(Map<E, N> map) {
        return new UndirectedMultiNetworkConnections<N, E>(ImmutableMap.copyOf(map));
    }

    @Override
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodesMultiset().elementSet());
    }

    private Multiset<N> adjacentNodesMultiset() {
        Multiset<N> multiset = UndirectedMultiNetworkConnections.getReference(this.adjacentNodesReference);
        if (multiset == null) {
            multiset = HashMultiset.create(this.incidentEdgeMap.values());
            this.adjacentNodesReference = new SoftReference<Multiset<N>>(multiset);
        }
        return multiset;
    }

    @Override
    public Set<E> edgesConnecting(Object object) {
        return new MultiEdgesConnecting<E>(this, this.incidentEdgeMap, object, object){
            final Object val$node;
            final UndirectedMultiNetworkConnections this$0;
            {
                this.this$0 = undirectedMultiNetworkConnections;
                this.val$node = object2;
                super(map, object);
            }

            @Override
            public int size() {
                return UndirectedMultiNetworkConnections.access$000(this.this$0).count(this.val$node);
            }
        };
    }

    @Override
    public N removeInEdge(Object object, boolean bl) {
        if (!bl) {
            return this.removeOutEdge(object);
        }
        return null;
    }

    @Override
    public N removeOutEdge(Object object) {
        Object n = super.removeOutEdge(object);
        Multiset<N> multiset = UndirectedMultiNetworkConnections.getReference(this.adjacentNodesReference);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(n));
        }
        return n;
    }

    @Override
    public void addInEdge(E e, N n, boolean bl) {
        if (!bl) {
            this.addOutEdge(e, n);
        }
    }

    @Override
    public void addOutEdge(E e, N n) {
        super.addOutEdge(e, n);
        Multiset<N> multiset = UndirectedMultiNetworkConnections.getReference(this.adjacentNodesReference);
        if (multiset != null) {
            Preconditions.checkState(multiset.add(n));
        }
    }

    @Nullable
    private static <T> T getReference(@Nullable Reference<T> reference) {
        return reference == null ? null : (T)reference.get();
    }

    static Multiset access$000(UndirectedMultiNetworkConnections undirectedMultiNetworkConnections) {
        return undirectedMultiNetworkConnections.adjacentNodesMultiset();
    }
}

