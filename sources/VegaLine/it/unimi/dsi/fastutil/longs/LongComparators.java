/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongComparator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import java.io.Serializable;

public class LongComparators {
    public static final LongComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final LongComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private LongComparators() {
    }

    public static LongComparator oppositeComparator(LongComparator c) {
        return new OppositeComparator(c);
    }

    protected static class OppositeComparator
    extends AbstractLongComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final LongComparator comparator;

        protected OppositeComparator(LongComparator c) {
            this.comparator = c;
        }

        @Override
        public final int compare(long a, long b) {
            return this.comparator.compare(b, a);
        }
    }

    protected static class OppositeImplicitComparator
    extends AbstractLongComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(long a, long b) {
            return -Long.compare(a, b);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    extends AbstractLongComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(long a, long b) {
            return Long.compare(a, b);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

