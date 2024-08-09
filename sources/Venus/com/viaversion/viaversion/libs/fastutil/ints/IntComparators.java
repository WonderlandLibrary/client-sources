/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import java.io.Serializable;
import java.util.Comparator;

public final class IntComparators {
    public static final IntComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final IntComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private IntComparators() {
    }

    public static IntComparator oppositeComparator(IntComparator intComparator) {
        if (intComparator instanceof OppositeComparator) {
            return ((OppositeComparator)intComparator).comparator;
        }
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected static class OppositeComparator
    implements IntComparator,
    Serializable {
        private static final long serialVersionUID = 1L;
        final IntComparator comparator;

        protected OppositeComparator(IntComparator intComparator) {
            this.comparator = intComparator;
        }

        @Override
        public final int compare(int n, int n2) {
            return this.comparator.compare(n2, n);
        }

        @Override
        public final IntComparator reversed() {
            return this.comparator;
        }

        @Override
        public Comparator reversed() {
            return this.reversed();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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

        @Override
        public IntComparator reversed() {
            return OPPOSITE_COMPARATOR;
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }

        @Override
        public Comparator reversed() {
            return this.reversed();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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

        @Override
        public IntComparator reversed() {
            return NATURAL_COMPARATOR;
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }

        @Override
        public Comparator reversed() {
            return this.reversed();
        }
    }
}

