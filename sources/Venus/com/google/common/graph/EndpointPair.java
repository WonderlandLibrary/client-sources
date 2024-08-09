/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.Graph;
import com.google.common.graph.Network;
import java.util.Iterator;
import javax.annotation.Nullable;

@Beta
public abstract class EndpointPair<N>
implements Iterable<N> {
    private final N nodeU;
    private final N nodeV;

    private EndpointPair(N n, N n2) {
        this.nodeU = Preconditions.checkNotNull(n);
        this.nodeV = Preconditions.checkNotNull(n2);
    }

    public static <N> EndpointPair<N> ordered(N n, N n2) {
        return new Ordered(n, n2, null);
    }

    public static <N> EndpointPair<N> unordered(N n, N n2) {
        return new Unordered(n2, n, null);
    }

    static <N> EndpointPair<N> of(Graph<?> graph, N n, N n2) {
        return graph.isDirected() ? EndpointPair.ordered(n, n2) : EndpointPair.unordered(n, n2);
    }

    static <N> EndpointPair<N> of(Network<?, ?> network, N n, N n2) {
        return network.isDirected() ? EndpointPair.ordered(n, n2) : EndpointPair.unordered(n, n2);
    }

    public abstract N source();

    public abstract N target();

    public final N nodeU() {
        return this.nodeU;
    }

    public final N nodeV() {
        return this.nodeV;
    }

    public final N adjacentNode(Object object) {
        if (object.equals(this.nodeU)) {
            return this.nodeV;
        }
        if (object.equals(this.nodeV)) {
            return this.nodeU;
        }
        throw new IllegalArgumentException(String.format("EndpointPair %s does not contain node %s", this, object));
    }

    public abstract boolean isOrdered();

    @Override
    public final UnmodifiableIterator<N> iterator() {
        return Iterators.forArray(this.nodeU, this.nodeV);
    }

    public abstract boolean equals(@Nullable Object var1);

    public abstract int hashCode();

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    EndpointPair(Object object, Object object2, 1 var3_3) {
        this(object, object2);
    }

    private static final class Unordered<N>
    extends EndpointPair<N> {
        private Unordered(N n, N n2) {
            super(n, n2, null);
        }

        @Override
        public N source() {
            throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
        }

        @Override
        public N target() {
            throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
        }

        @Override
        public boolean isOrdered() {
            return true;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof EndpointPair)) {
                return true;
            }
            EndpointPair endpointPair = (EndpointPair)object;
            if (this.isOrdered() != endpointPair.isOrdered()) {
                return true;
            }
            if (this.nodeU().equals(endpointPair.nodeU())) {
                return this.nodeV().equals(endpointPair.nodeV());
            }
            return this.nodeU().equals(endpointPair.nodeV()) && this.nodeV().equals(endpointPair.nodeU());
        }

        @Override
        public int hashCode() {
            return this.nodeU().hashCode() + this.nodeV().hashCode();
        }

        public String toString() {
            return String.format("[%s, %s]", this.nodeU(), this.nodeV());
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        Unordered(Object object, Object object2, 1 var3_3) {
            this(object, object2);
        }
    }

    private static final class Ordered<N>
    extends EndpointPair<N> {
        private Ordered(N n, N n2) {
            super(n, n2, null);
        }

        @Override
        public N source() {
            return this.nodeU();
        }

        @Override
        public N target() {
            return this.nodeV();
        }

        @Override
        public boolean isOrdered() {
            return false;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof EndpointPair)) {
                return true;
            }
            EndpointPair endpointPair = (EndpointPair)object;
            if (this.isOrdered() != endpointPair.isOrdered()) {
                return true;
            }
            return this.source().equals(endpointPair.source()) && this.target().equals(endpointPair.target());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.source(), this.target());
        }

        public String toString() {
            return String.format("<%s -> %s>", this.source(), this.target());
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        Ordered(Object object, Object object2, 1 var3_3) {
            this(object, object2);
        }
    }
}

