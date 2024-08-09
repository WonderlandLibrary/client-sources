/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongComparator;
import java.io.Serializable;
import java.util.Comparator;

public final class LongComparators {
    public static final LongComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final LongComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private LongComparators() {
    }

    public static LongComparator oppositeComparator(LongComparator longComparator) {
        return new OppositeComparator(longComparator);
    }

    public static LongComparator asLongComparator(Comparator<? super Long> comparator) {
        if (comparator == null || comparator instanceof LongComparator) {
            return (LongComparator)comparator;
        }
        return new LongComparator(comparator){
            final Comparator val$c;
            {
                this.val$c = comparator;
            }

            @Override
            public int compare(long l, long l2) {
                return this.val$c.compare(l, l2);
            }

            @Override
            public int compare(Long l, Long l2) {
                return this.val$c.compare(l, l2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Long)object, (Long)object2);
            }
        };
    }

    protected static class OppositeComparator
    implements LongComparator,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final LongComparator comparator;

        protected OppositeComparator(LongComparator longComparator) {
            this.comparator = longComparator;
        }

        @Override
        public final int compare(long l, long l2) {
            return this.comparator.compare(l2, l);
        }
    }

    protected static class OppositeImplicitComparator
    implements LongComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(long l, long l2) {
            return -Long.compare(l, l2);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    implements LongComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(long l, long l2) {
            return Long.compare(l, l2);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

