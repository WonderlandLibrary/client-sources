/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntComparator;
import java.io.Serializable;
import java.util.Comparator;

public final class IntComparators {
    public static final IntComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final IntComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private IntComparators() {
    }

    public static IntComparator oppositeComparator(IntComparator intComparator) {
        return new OppositeComparator(intComparator);
    }

    public static IntComparator asIntComparator(Comparator<? super Integer> comparator) {
        if (comparator == null || comparator instanceof IntComparator) {
            return (IntComparator)comparator;
        }
        return new IntComparator(comparator){
            final Comparator val$c;
            {
                this.val$c = comparator;
            }

            @Override
            public int compare(int n, int n2) {
                return this.val$c.compare(n, n2);
            }

            @Override
            public int compare(Integer n, Integer n2) {
                return this.val$c.compare(n, n2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Integer)object, (Integer)object2);
            }
        };
    }

    protected static class OppositeComparator
    implements IntComparator,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final IntComparator comparator;

        protected OppositeComparator(IntComparator intComparator) {
            this.comparator = intComparator;
        }

        @Override
        public final int compare(int n, int n2) {
            return this.comparator.compare(n2, n);
        }
    }

    protected static class OppositeImplicitComparator
    implements IntComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(int n, int n2) {
            return -Integer.compare(n, n2);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    implements IntComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(int n, int n2) {
            return Integer.compare(n, n2);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

