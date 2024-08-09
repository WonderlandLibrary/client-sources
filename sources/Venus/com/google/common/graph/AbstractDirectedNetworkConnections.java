/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.Graphs;
import com.google.common.graph.NetworkConnections;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

abstract class AbstractDirectedNetworkConnections<N, E>
implements NetworkConnections<N, E> {
    protected final Map<E, N> inEdgeMap;
    protected final Map<E, N> outEdgeMap;
    private int selfLoopCount;

    protected AbstractDirectedNetworkConnections(Map<E, N> map, Map<E, N> map2, int n) {
        this.inEdgeMap = Preconditions.checkNotNull(map);
        this.outEdgeMap = Preconditions.checkNotNull(map2);
        this.selfLoopCount = Graphs.checkNonNegative(n);
        Preconditions.checkState(n <= map.size() && n <= map2.size());
    }

    @Override
    public Set<N> adjacentNodes() {
        return Sets.union(this.predecessors(), this.successors());
    }

    @Override
    public Set<E> incidentEdges() {
        return new AbstractSet<E>(this){
            final AbstractDirectedNetworkConnections this$0;
            {
                this.this$0 = abstractDirectedNetworkConnections;
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                Iterable iterable = AbstractDirectedNetworkConnections.access$000(this.this$0) == 0 ? Iterables.concat(this.this$0.inEdgeMap.keySet(), this.this$0.outEdgeMap.keySet()) : Sets.union(this.this$0.inEdgeMap.keySet(), this.this$0.outEdgeMap.keySet());
                return Iterators.unmodifiableIterator(iterable.iterator());
            }

            @Override
            public int size() {
                return IntMath.saturatedAdd(this.this$0.inEdgeMap.size(), this.this$0.outEdgeMap.size() - AbstractDirectedNetworkConnections.access$000(this.this$0));
            }

            @Override
            public boolean contains(@Nullable Object object) {
                return this.this$0.inEdgeMap.containsKey(object) || this.this$0.outEdgeMap.containsKey(object);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public Set<E> inEdges() {
        return Collections.unmodifiableSet(this.inEdgeMap.keySet());
    }

    @Override
    public Set<E> outEdges() {
        return Collections.unmodifiableSet(this.outEdgeMap.keySet());
    }

    @Override
    public N oppositeNode(Object object) {
        return Preconditions.checkNotNull(this.outEdgeMap.get(object));
    }

    @Override
    public N removeInEdge(Object object, boolean bl) {
        if (bl) {
            Graphs.checkNonNegative(--this.selfLoopCount);
        }
        N n = this.inEdgeMap.remove(object);
        return Preconditions.checkNotNull(n);
    }

    @Override
    public N removeOutEdge(Object object) {
        N n = this.outEdgeMap.remove(object);
        return Preconditions.checkNotNull(n);
    }

    @Override
    public void addInEdge(E e, N n, boolean bl) {
        N n2;
        if (bl) {
            Graphs.checkPositive(++this.selfLoopCount);
        }
        Preconditions.checkState((n2 = this.inEdgeMap.put(e, n)) == null);
    }

    @Override
    public void addOutEdge(E e, N n) {
        N n2 = this.outEdgeMap.put(e, n);
        Preconditions.checkState(n2 == null);
    }

    static int access$000(AbstractDirectedNetworkConnections abstractDirectedNetworkConnections) {
        return abstractDirectedNetworkConnections.selfLoopCount;
    }
}

