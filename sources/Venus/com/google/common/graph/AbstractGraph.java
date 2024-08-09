/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.EndpointPairIterator;
import com.google.common.graph.Graph;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public abstract class AbstractGraph<N>
implements Graph<N> {
    protected long edgeCount() {
        long l = 0L;
        for (Object n : this.nodes()) {
            l += (long)this.degree(n);
        }
        Preconditions.checkState((l & 1L) == 0L);
        return l >>> 1;
    }

    @Override
    public Set<EndpointPair<N>> edges() {
        return new AbstractSet<EndpointPair<N>>(this){
            final AbstractGraph this$0;
            {
                this.this$0 = abstractGraph;
            }

            @Override
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return EndpointPairIterator.of(this.this$0);
            }

            @Override
            public int size() {
                return Ints.saturatedCast(this.this$0.edgeCount());
            }

            @Override
            public boolean contains(@Nullable Object object) {
                if (!(object instanceof EndpointPair)) {
                    return true;
                }
                EndpointPair endpointPair = (EndpointPair)object;
                return this.this$0.isDirected() == endpointPair.isOrdered() && this.this$0.nodes().contains(endpointPair.nodeU()) && this.this$0.successors(endpointPair.nodeU()).contains(endpointPair.nodeV());
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public int degree(Object object) {
        if (this.isDirected()) {
            return IntMath.saturatedAdd(this.predecessors(object).size(), this.successors(object).size());
        }
        Set set = this.adjacentNodes(object);
        int n = this.allowsSelfLoops() && set.contains(object) ? 1 : 0;
        return IntMath.saturatedAdd(set.size(), n);
    }

    @Override
    public int inDegree(Object object) {
        return this.isDirected() ? this.predecessors(object).size() : this.degree(object);
    }

    @Override
    public int outDegree(Object object) {
        return this.isDirected() ? this.successors(object).size() : this.degree(object);
    }

    public String toString() {
        String string = String.format("isDirected: %s, allowsSelfLoops: %s", this.isDirected(), this.allowsSelfLoops());
        return String.format("%s, nodes: %s, edges: %s", string, this.nodes(), this.edges());
    }
}

