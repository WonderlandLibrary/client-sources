/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.Graphs;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

final class DirectedGraphConnections<N, V>
implements GraphConnections<N, V> {
    private static final Object PRED = new Object();
    private final Map<N, Object> adjacentNodeValues;
    private int predecessorCount;
    private int successorCount;

    private DirectedGraphConnections(Map<N, Object> map, int n, int n2) {
        this.adjacentNodeValues = Preconditions.checkNotNull(map);
        this.predecessorCount = Graphs.checkNonNegative(n);
        this.successorCount = Graphs.checkNonNegative(n2);
        Preconditions.checkState(n <= map.size() && n2 <= map.size());
    }

    static <N, V> DirectedGraphConnections<N, V> of() {
        int n = 4;
        return new DirectedGraphConnections(new HashMap(n, 1.0f), 0, 0);
    }

    static <N, V> DirectedGraphConnections<N, V> ofImmutable(Set<N> set, Map<N, V> map) {
        HashMap<N, Object> hashMap = new HashMap<N, Object>();
        hashMap.putAll(map);
        for (N n : set) {
            Object object = hashMap.put(n, PRED);
            if (object == null) continue;
            hashMap.put(n, new PredAndSucc(object));
        }
        return new DirectedGraphConnections(ImmutableMap.copyOf(hashMap), set.size(), map.size());
    }

    @Override
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    @Override
    public Set<N> predecessors() {
        return new AbstractSet<N>(this){
            final DirectedGraphConnections this$0;
            {
                this.this$0 = directedGraphConnections;
            }

            @Override
            public UnmodifiableIterator<N> iterator() {
                Iterator iterator2 = DirectedGraphConnections.access$000(this.this$0).entrySet().iterator();
                return new AbstractIterator<N>(this, iterator2){
                    final Iterator val$entries;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$entries = iterator2;
                    }

                    @Override
                    protected N computeNext() {
                        while (this.val$entries.hasNext()) {
                            Map.Entry entry = (Map.Entry)this.val$entries.next();
                            if (!DirectedGraphConnections.access$100(entry.getValue())) continue;
                            return entry.getKey();
                        }
                        return this.endOfData();
                    }
                };
            }

            @Override
            public int size() {
                return DirectedGraphConnections.access$200(this.this$0);
            }

            @Override
            public boolean contains(@Nullable Object object) {
                return DirectedGraphConnections.access$100(DirectedGraphConnections.access$000(this.this$0).get(object));
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public Set<N> successors() {
        return new AbstractSet<N>(this){
            final DirectedGraphConnections this$0;
            {
                this.this$0 = directedGraphConnections;
            }

            @Override
            public UnmodifiableIterator<N> iterator() {
                Iterator iterator2 = DirectedGraphConnections.access$000(this.this$0).entrySet().iterator();
                return new AbstractIterator<N>(this, iterator2){
                    final Iterator val$entries;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$entries = iterator2;
                    }

                    @Override
                    protected N computeNext() {
                        while (this.val$entries.hasNext()) {
                            Map.Entry entry = (Map.Entry)this.val$entries.next();
                            if (!DirectedGraphConnections.access$300(entry.getValue())) continue;
                            return entry.getKey();
                        }
                        return this.endOfData();
                    }
                };
            }

            @Override
            public int size() {
                return DirectedGraphConnections.access$400(this.this$0);
            }

            @Override
            public boolean contains(@Nullable Object object) {
                return DirectedGraphConnections.access$300(DirectedGraphConnections.access$000(this.this$0).get(object));
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public V value(Object object) {
        Object object2 = this.adjacentNodeValues.get(object);
        if (object2 == PRED) {
            return null;
        }
        if (object2 instanceof PredAndSucc) {
            return (V)PredAndSucc.access$500((PredAndSucc)object2);
        }
        return (V)object2;
    }

    @Override
    public void removePredecessor(Object object) {
        Object object2 = this.adjacentNodeValues.get(object);
        if (object2 == PRED) {
            this.adjacentNodeValues.remove(object);
            Graphs.checkNonNegative(--this.predecessorCount);
        } else if (object2 instanceof PredAndSucc) {
            this.adjacentNodeValues.put(object, PredAndSucc.access$500((PredAndSucc)object2));
            Graphs.checkNonNegative(--this.predecessorCount);
        }
    }

    @Override
    public V removeSuccessor(Object object) {
        Object object2 = this.adjacentNodeValues.get(object);
        if (object2 == null || object2 == PRED) {
            return null;
        }
        if (object2 instanceof PredAndSucc) {
            this.adjacentNodeValues.put(object, PRED);
            Graphs.checkNonNegative(--this.successorCount);
            return (V)PredAndSucc.access$500((PredAndSucc)object2);
        }
        this.adjacentNodeValues.remove(object);
        Graphs.checkNonNegative(--this.successorCount);
        return (V)object2;
    }

    @Override
    public void addPredecessor(N n, V v) {
        Object object = this.adjacentNodeValues.put(n, PRED);
        if (object == null) {
            Graphs.checkPositive(++this.predecessorCount);
        } else if (object instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n, object);
        } else if (object != PRED) {
            this.adjacentNodeValues.put(n, new PredAndSucc(object));
            Graphs.checkPositive(++this.predecessorCount);
        }
    }

    @Override
    public V addSuccessor(N n, V v) {
        Object object = this.adjacentNodeValues.put(n, v);
        if (object == null) {
            Graphs.checkPositive(++this.successorCount);
            return null;
        }
        if (object instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n, new PredAndSucc(v));
            return (V)PredAndSucc.access$500((PredAndSucc)object);
        }
        if (object == PRED) {
            this.adjacentNodeValues.put(n, new PredAndSucc(v));
            Graphs.checkPositive(++this.successorCount);
            return null;
        }
        return (V)object;
    }

    private static boolean isPredecessor(@Nullable Object object) {
        return object == PRED || object instanceof PredAndSucc;
    }

    private static boolean isSuccessor(@Nullable Object object) {
        return object != PRED && object != null;
    }

    static Map access$000(DirectedGraphConnections directedGraphConnections) {
        return directedGraphConnections.adjacentNodeValues;
    }

    static boolean access$100(Object object) {
        return DirectedGraphConnections.isPredecessor(object);
    }

    static int access$200(DirectedGraphConnections directedGraphConnections) {
        return directedGraphConnections.predecessorCount;
    }

    static boolean access$300(Object object) {
        return DirectedGraphConnections.isSuccessor(object);
    }

    static int access$400(DirectedGraphConnections directedGraphConnections) {
        return directedGraphConnections.successorCount;
    }

    private static final class PredAndSucc {
        private final Object successorValue;

        PredAndSucc(Object object) {
            this.successorValue = object;
        }

        static Object access$500(PredAndSucc predAndSucc) {
            return predAndSucc.successorValue;
        }
    }
}

