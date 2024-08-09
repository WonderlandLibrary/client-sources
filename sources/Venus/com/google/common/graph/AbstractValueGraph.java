/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.graph.AbstractGraph;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;
import java.util.Map;

@Beta
public abstract class AbstractValueGraph<N, V>
extends AbstractGraph<N>
implements ValueGraph<N, V> {
    @Override
    public V edgeValue(Object object, Object object2) {
        V v = this.edgeValueOrDefault(object, object2, null);
        if (v == null) {
            Preconditions.checkArgument(this.nodes().contains(object), "Node %s is not an element of this graph.", object);
            Preconditions.checkArgument(this.nodes().contains(object2), "Node %s is not an element of this graph.", object2);
            throw new IllegalArgumentException(String.format("Edge connecting %s to %s is not present in this graph.", object, object2));
        }
        return v;
    }

    @Override
    public String toString() {
        String string = String.format("isDirected: %s, allowsSelfLoops: %s", this.isDirected(), this.allowsSelfLoops());
        return String.format("%s, nodes: %s, edges: %s", string, this.nodes(), this.edgeValueMap());
    }

    private Map<EndpointPair<N>, V> edgeValueMap() {
        Function function = new Function<EndpointPair<N>, V>(this){
            final AbstractValueGraph this$0;
            {
                this.this$0 = abstractValueGraph;
            }

            @Override
            public V apply(EndpointPair<N> endpointPair) {
                return this.this$0.edgeValue(endpointPair.nodeU(), endpointPair.nodeV());
            }

            @Override
            public Object apply(Object object) {
                return this.apply((EndpointPair)object);
            }
        };
        return Maps.asMap(this.edges(), function);
    }
}

