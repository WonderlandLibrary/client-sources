/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import java.io.Serializable;

public class ByteComparators {
    public static final ByteComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final ByteComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private ByteComparators() {
    }

    public static ByteComparator oppositeComparator(ByteComparator c) {
        return new OppositeComparator(c);
    }

    protected static class OppositeComparator
    extends AbstractByteComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final ByteComparator comparator;

        protected OppositeComparator(ByteComparator c) {
            this.comparator = c;
        }

        @Override
        public final int compare(byte a, byte b) {
            return this.comparator.compare(b, a);
        }
    }

    protected static class OppositeImplicitComparator
    extends AbstractByteComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(byte a, byte b) {
            return -Byte.compare(a, b);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    extends AbstractByteComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(byte a, byte b) {
            return Byte.compare(a, b);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

