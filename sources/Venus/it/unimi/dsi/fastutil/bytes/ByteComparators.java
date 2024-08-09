/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteComparator;
import java.io.Serializable;
import java.util.Comparator;

public final class ByteComparators {
    public static final ByteComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final ByteComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private ByteComparators() {
    }

    public static ByteComparator oppositeComparator(ByteComparator byteComparator) {
        return new OppositeComparator(byteComparator);
    }

    public static ByteComparator asByteComparator(Comparator<? super Byte> comparator) {
        if (comparator == null || comparator instanceof ByteComparator) {
            return (ByteComparator)comparator;
        }
        return new ByteComparator(comparator){
            final Comparator val$c;
            {
                this.val$c = comparator;
            }

            @Override
            public int compare(byte by, byte by2) {
                return this.val$c.compare(by, by2);
            }

            @Override
            public int compare(Byte by, Byte by2) {
                return this.val$c.compare(by, by2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Byte)object, (Byte)object2);
            }
        };
    }

    protected static class OppositeComparator
    implements ByteComparator,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final ByteComparator comparator;

        protected OppositeComparator(ByteComparator byteComparator) {
            this.comparator = byteComparator;
        }

        @Override
        public final int compare(byte by, byte by2) {
            return this.comparator.compare(by2, by);
        }
    }

    protected static class OppositeImplicitComparator
    implements ByteComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(byte by, byte by2) {
            return -Byte.compare(by, by2);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    implements ByteComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(byte by, byte by2) {
            return Byte.compare(by, by2);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

