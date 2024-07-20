/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import java.io.Serializable;

public class DoubleComparators {
    public static final DoubleComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final DoubleComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private DoubleComparators() {
    }

    public static DoubleComparator oppositeComparator(DoubleComparator c) {
        return new OppositeComparator(c);
    }

    protected static class OppositeComparator
    extends AbstractDoubleComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final DoubleComparator comparator;

        protected OppositeComparator(DoubleComparator c) {
            this.comparator = c;
        }

        @Override
        public final int compare(double a, double b) {
            return this.comparator.compare(b, a);
        }
    }

    protected static class OppositeImplicitComparator
    extends AbstractDoubleComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(double a, double b) {
            return -Double.compare(a, b);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    extends AbstractDoubleComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(double a, double b) {
            return Double.compare(a, b);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

