/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import java.io.Serializable;
import java.util.Comparator;

public final class DoubleComparators {
    public static final DoubleComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final DoubleComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private DoubleComparators() {
    }

    public static DoubleComparator oppositeComparator(DoubleComparator doubleComparator) {
        return new OppositeComparator(doubleComparator);
    }

    public static DoubleComparator asDoubleComparator(Comparator<? super Double> comparator) {
        if (comparator == null || comparator instanceof DoubleComparator) {
            return (DoubleComparator)comparator;
        }
        return new DoubleComparator(comparator){
            final Comparator val$c;
            {
                this.val$c = comparator;
            }

            @Override
            public int compare(double d, double d2) {
                return this.val$c.compare(d, d2);
            }

            @Override
            public int compare(Double d, Double d2) {
                return this.val$c.compare(d, d2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Double)object, (Double)object2);
            }
        };
    }

    protected static class OppositeComparator
    implements DoubleComparator,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final DoubleComparator comparator;

        protected OppositeComparator(DoubleComparator doubleComparator) {
            this.comparator = doubleComparator;
        }

        @Override
        public final int compare(double d, double d2) {
            return this.comparator.compare(d2, d);
        }
    }

    protected static class OppositeImplicitComparator
    implements DoubleComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(double d, double d2) {
            return -Double.compare(d, d2);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    implements DoubleComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(double d, double d2) {
            return Double.compare(d, d2);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

