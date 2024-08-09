/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;

public final class ObjectComparators {
    public static final Comparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final Comparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private ObjectComparators() {
    }

    public static <K> Comparator<K> oppositeComparator(Comparator<K> comparator) {
        return new OppositeComparator<K>(comparator);
    }

    protected static class OppositeComparator<K>
    implements Comparator<K>,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final Comparator<K> comparator;

        protected OppositeComparator(Comparator<K> comparator) {
            this.comparator = comparator;
        }

        @Override
        public final int compare(K k, K k2) {
            return this.comparator.compare(k2, k);
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

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
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

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

