/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Interner;
import com.google.common.collect.MapMaker;
import com.google.common.collect.MapMakerInternalMap;
import java.util.concurrent.ConcurrentMap;

@Beta
@GwtIncompatible
public final class Interners {
    private Interners() {
    }

    public static InternerBuilder newBuilder() {
        return new InternerBuilder(null);
    }

    public static <E> Interner<E> newStrongInterner() {
        return Interners.newBuilder().strong().build();
    }

    @GwtIncompatible(value="java.lang.ref.WeakReference")
    public static <E> Interner<E> newWeakInterner() {
        return Interners.newBuilder().weak().build();
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new InternerFunction<E>(Preconditions.checkNotNull(interner));
    }

    private static class InternerFunction<E>
    implements Function<E, E> {
        private final Interner<E> interner;

        public InternerFunction(Interner<E> interner) {
            this.interner = interner;
        }

        @Override
        public E apply(E e) {
            return this.interner.intern(e);
        }

        public int hashCode() {
            return this.interner.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof InternerFunction) {
                InternerFunction internerFunction = (InternerFunction)object;
                return this.interner.equals(internerFunction.interner);
            }
            return true;
        }
    }

    @VisibleForTesting
    static final class WeakInterner<E>
    implements Interner<E> {
        @VisibleForTesting
        final MapMakerInternalMap<E, Dummy, ?, ?> map;

        private WeakInterner(MapMaker mapMaker) {
            this.map = mapMaker.weakKeys().keyEquivalence(Equivalence.equals()).makeCustomMap();
        }

        @Override
        public E intern(E e) {
            Object object;
            do {
                Object obj;
                if ((obj = this.map.getEntry(e)) == null || (object = obj.getKey()) == null) continue;
                return (E)object;
            } while ((object = this.map.putIfAbsent(e, Dummy.VALUE)) != null);
            return e;
        }

        WeakInterner(MapMaker mapMaker, 1 var2_2) {
            this(mapMaker);
        }

        private static enum Dummy {
            VALUE;

        }
    }

    @VisibleForTesting
    static final class StrongInterner<E>
    implements Interner<E> {
        @VisibleForTesting
        final ConcurrentMap<E, E> map;

        private StrongInterner(MapMaker mapMaker) {
            this.map = mapMaker.makeMap();
        }

        @Override
        public E intern(E e) {
            E e2 = this.map.putIfAbsent(Preconditions.checkNotNull(e), e);
            return e2 == null ? e : e2;
        }

        StrongInterner(MapMaker mapMaker, 1 var2_2) {
            this(mapMaker);
        }
    }

    public static class InternerBuilder {
        private final MapMaker mapMaker = new MapMaker();
        private boolean strong = true;

        private InternerBuilder() {
        }

        public InternerBuilder strong() {
            this.strong = true;
            return this;
        }

        @GwtIncompatible(value="java.lang.ref.WeakReference")
        public InternerBuilder weak() {
            this.strong = false;
            return this;
        }

        public InternerBuilder concurrencyLevel(int n) {
            this.mapMaker.concurrencyLevel(n);
            return this;
        }

        public <E> Interner<E> build() {
            return this.strong ? new StrongInterner(this.mapMaker, null) : new WeakInterner(this.mapMaker, null);
        }

        InternerBuilder(1 var1_1) {
            this();
        }
    }
}

