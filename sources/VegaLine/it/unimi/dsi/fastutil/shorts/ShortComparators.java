/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import java.io.Serializable;

public class ShortComparators {
    public static final ShortComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final ShortComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private ShortComparators() {
    }

    public static ShortComparator oppositeComparator(ShortComparator c) {
        return new OppositeComparator(c);
    }

    protected static class OppositeComparator
    extends AbstractShortComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final ShortComparator comparator;

        protected OppositeComparator(ShortComparator c) {
            this.comparator = c;
        }

        @Override
        public final int compare(short a, short b) {
            return this.comparator.compare(b, a);
        }
    }

    protected static class OppositeImplicitComparator
    extends AbstractShortComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(short a, short b) {
            return -Short.compare(a, b);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    extends AbstractShortComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(short a, short b) {
            return Short.compare(a, b);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

