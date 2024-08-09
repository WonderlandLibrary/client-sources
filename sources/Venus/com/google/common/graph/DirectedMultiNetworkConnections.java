/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.common.graph.AbstractDirectedNetworkConnections;
import com.google.common.graph.MultiEdgesConnecting;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

final class DirectedMultiNetworkConnections<N, E>
extends AbstractDirectedNetworkConnections<N, E> {
    @LazyInit
    private transient Reference<Multiset<N>> predecessorsReference;
    @LazyInit
    private transient Reference<Multiset<N>> successorsReference;

    private DirectedMultiNetworkConnections(Map<E, N> map, Map<E, N> map2, int n) {
        super(map, map2, n);
    }

    static <N, E> DirectedMultiNetworkConnections<N, E> of() {
        return new DirectedMultiNetworkConnections(new HashMap(2, 1.0f), new HashMap(2, 1.0f), 0);
    }

    static <N, E> DirectedMultiNetworkConnections<N, E> ofImmutable(Map<E, N> map, Map<E, N> map2, int n) {
        return new DirectedMultiNetworkConnections<N, E>(ImmutableMap.copyOf(map), ImmutableMap.copyOf(map2), n);
    }

    @Override
    public Set<N> predecessors() {
        return Collections.unmodifiableSet(this.predecessorsMultiset().elementSet());
    }

    private Multiset<N> predecessorsMultiset() {
        Multiset<N> multiset = DirectedMultiNetworkConnections.getReference(this.predecessorsReference);
        if (multiset == null) {
            multiset = HashMultiset.create(this.inEdgeMap.values());
            this.predecessorsReference = new SoftReference<Multiset<N>>(multiset);
        }
        return multiset;
    }

    @Override
    public Set<N> successors() {
        return Collections.unmodifiableSet(this.successorsMultiset().elementSet());
    }

    private Multiset<N> successorsMultiset() {
        Multiset<N> multiset = DirectedMultiNetworkConnections.getReference(this.successorsReference);
        if (multiset == null) {
            multiset = HashMultiset.create(this.outEdgeMap.values());
            this.successorsReference = new SoftReference<Multiset<N>>(multiset);
        }
        return multiset;
    }

    @Override
    public Set<E> edgesConnecting(Object object) {
        return new MultiEdgesConnecting<E>(this, this.outEdgeMap, object, object){
            final Object val$node;
            final DirectedMultiNetworkConnections this$0;
            {
                this.this$0 = directedMultiNetworkConnections;
                this.val$node = object2;
                super(map, object);
            }

            @Override
            public int size() {
                return DirectedMultiNetworkConnections.access$000(this.this$0).count(this.val$node);
            }
        };
    }

    @Override
    public N removeInEdge(Object object, boolean bl) {
        Object n = super.removeInEdge(object, bl);
        Multiset<N> multiset = DirectedMultiNetworkConnections.getReference(this.predecessorsReference);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(n));
        }
        return n;
    }

    @Override
    public N removeOutEdge(Object object) {
        Object n = super.removeOutEdge(object);
        Multiset<N> multiset = DirectedMultiNetworkConnections.getReference(this.successorsReference);
        if (multiset != null) {
            Preconditions.checkState(multiset.remove(n));
        }
        return n;
    }

    @Override
    public void addInEdge(E e, N n, boolean bl) {
        super.addInEdge(e, n, bl);
        Multiset<N> multiset = DirectedMultiNetworkConnections.getReference(this.predecessorsReference);
        if (multiset != null) {
            Preconditions.checkState(multiset.add(n));
        }
    }

    @Override
    public void addOutEdge(E e, N n) {
        super.addOutEdge(e, n);
        Multiset<N> multiset = DirectedMultiNetworkConnections.getReference(this.successorsReference);
        if (multiset != null) {
            Preconditions.checkState(multiset.add(n));
        }
    }

    @Nullable
    private static <T> T getReference(@Nullable Reference<T> reference) {
        return reference == null ? null : (T)reference.get();
    }

    static Multiset access$000(DirectedMultiNetworkConnections directedMultiNetworkConnections) {
        return directedMultiNetworkConnections.successorsMultiset();
    }
}

