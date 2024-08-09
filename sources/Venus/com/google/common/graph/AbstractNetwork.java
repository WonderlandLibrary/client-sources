/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.graph.AbstractGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.Network;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public abstract class AbstractNetwork<N, E>
implements Network<N, E> {
    @Override
    public Graph<N> asGraph() {
        return new AbstractGraph<N>(this){
            final AbstractNetwork this$0;
            {
                this.this$0 = abstractNetwork;
            }

            @Override
            public Set<N> nodes() {
                return this.this$0.nodes();
            }

            @Override
            public Set<EndpointPair<N>> edges() {
                if (this.this$0.allowsParallelEdges()) {
                    return super.edges();
                }
                return new AbstractSet<EndpointPair<N>>(this){
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                    }

                    @Override
                    public Iterator<EndpointPair<N>> iterator() {
                        return Iterators.transform(this.this$1.this$0.edges().iterator(), new Function<E, EndpointPair<N>>(this){
                            final 1 this$2;
                            {
                                this.this$2 = var1_1;
                            }

                            @Override
                            public EndpointPair<N> apply(E e) {
                                return this.this$2.this$1.this$0.incidentNodes(e);
                            }

                            @Override
                            public Object apply(Object object) {
                                return this.apply(object);
                            }
                        });
                    }

                    @Override
                    public int size() {
                        return this.this$1.this$0.edges().size();
                    }

                    @Override
                    public boolean contains(@Nullable Object object) {
                        if (!(object instanceof EndpointPair)) {
                            return true;
                        }
                        EndpointPair endpointPair = (EndpointPair)object;
                        return this.this$1.isDirected() == endpointPair.isOrdered() && this.this$1.nodes().contains(endpointPair.nodeU()) && this.this$1.successors(endpointPair.nodeU()).contains(endpointPair.nodeV());
                    }
                };
            }

            @Override
            public ElementOrder<N> nodeOrder() {
                return this.this$0.nodeOrder();
            }

            @Override
            public boolean isDirected() {
                return this.this$0.isDirected();
            }

            @Override
            public boolean allowsSelfLoops() {
                return this.this$0.allowsSelfLoops();
            }

            @Override
            public Set<N> adjacentNodes(Object object) {
                return this.this$0.adjacentNodes(object);
            }

            @Override
            public Set<N> predecessors(Object object) {
                return this.this$0.predecessors(object);
            }

            @Override
            public Set<N> successors(Object object) {
                return this.this$0.successors(object);
            }
        };
    }

    @Override
    public int degree(Object object) {
        if (this.isDirected()) {
            return IntMath.saturatedAdd(this.inEdges(object).size(), this.outEdges(object).size());
        }
        return IntMath.saturatedAdd(this.incidentEdges(object).size(), this.edgesConnecting(object, object).size());
    }

    @Override
    public int inDegree(Object object) {
        return this.isDirected() ? this.inEdges(object).size() : this.degree(object);
    }

    @Override
    public int outDegree(Object object) {
        return this.isDirected() ? this.outEdges(object).size() : this.degree(object);
    }

    @Override
    public Set<E> adjacentEdges(Object object) {
        EndpointPair endpointPair = this.incidentNodes(object);
        Sets.SetView setView = Sets.union(this.incidentEdges(endpointPair.nodeU()), this.incidentEdges(endpointPair.nodeV()));
        return Sets.difference(setView, ImmutableSet.of(object));
    }

    public String toString() {
        String string = String.format("isDirected: %s, allowsParallelEdges: %s, allowsSelfLoops: %s", this.isDirected(), this.allowsParallelEdges(), this.allowsSelfLoops());
        return String.format("%s, nodes: %s, edges: %s", string, this.nodes(), this.edgeIncidentNodesMap());
    }

    private Map<E, EndpointPair<N>> edgeIncidentNodesMap() {
        Function function = new Function<E, EndpointPair<N>>(this){
            final AbstractNetwork this$0;
            {
                this.this$0 = abstractNetwork;
            }

            @Override
            public EndpointPair<N> apply(E e) {
                return this.this$0.incidentNodes(e);
            }

            @Override
            public Object apply(Object object) {
                return this.apply(object);
            }
        };
        return Maps.asMap(this.edges(), function);
    }
}

