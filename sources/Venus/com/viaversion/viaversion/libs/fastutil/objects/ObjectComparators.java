/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;

public final class ObjectComparators {
    public static final Comparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final Comparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private ObjectComparators() {
    }

    public static <K> Comparator<K> oppositeComparator(Comparator<K> comparator) {
        if (comparator instanceof OppositeComparator) {
            return ((OppositeComparator)comparator).comparator;
        }
        return new OppositeComparator<K>(comparator);
    }

    public static <K> Comparator<K> asObjectComparator(Comparator<K> comparator) {
        return comparator;
    }

    protected static class OppositeComparator<K>
    implements Comparator<K>,
    Serializable {
        private static final long serialVersionUID = 1L;
        final Comparator<K> comparator;

        protected OppositeComparator(Comparator<K> comparator) {
            this.comparator = comparator;
        }

        @Override
        public final int compare(K k, K k2) {
            return this.comparator.compare(k2, k);
        }

        @Override
        public final Comparator<K> reversed() {
            return this.comparator;
        }
    }

    protected static class NaturalImplicitComparator
    implements Comparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        public final int compare(Object object, Object object2) {
            return ((Comparable)object).compareTo(object2);
        }

        public Comparator reversed() {
            return OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }

    protected static class OppositeImplicitComparator
    implements Comparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        public final int compare(Object object, Object object2) {
            return ((Comparable)object2).compareTo(object);
        }

        public Comparator reversed() {
            return NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }
}

