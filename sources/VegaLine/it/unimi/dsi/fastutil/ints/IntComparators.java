/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntComparator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import java.io.Serializable;

public class IntComparators {
    public static final IntComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final IntComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private IntComparators() {
    }

    public static IntComparator oppositeComparator(IntComparator c) {
        return new OppositeComparator(c);
    }

    protected static class OppositeComparator
    extends AbstractIntComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final IntComparator comparator;

        protected OppositeComparator(IntComparator c) {
            this.comparator = c;
        }

        @Override
        public final int compare(int a, int b) {
            return this.comparator.compare(b, a);
        }
    }

    protected static class OppositeImplicitComparator
    extends AbstractIntComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(int a, int b) {
            return -Integer.compare(a, b);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    extends AbstractIntComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(int a, int b) {
            return Integer.compare(a, b);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

