/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortComparator;
import java.io.Serializable;
import java.util.Comparator;

public final class ShortComparators {
    public static final ShortComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final ShortComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private ShortComparators() {
    }

    public static ShortComparator oppositeComparator(ShortComparator shortComparator) {
        return new OppositeComparator(shortComparator);
    }

    public static ShortComparator asShortComparator(Comparator<? super Short> comparator) {
        if (comparator == null || comparator instanceof ShortComparator) {
            return (ShortComparator)comparator;
        }
        return new ShortComparator(comparator){
            final Comparator val$c;
            {
                this.val$c = comparator;
            }

            @Override
            public int compare(short s, short s2) {
                return this.val$c.compare(s, s2);
            }

            @Override
            public int compare(Short s, Short s2) {
                return this.val$c.compare(s, s2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Short)object, (Short)object2);
            }
        };
    }

    protected static class OppositeComparator
    implements ShortComparator,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final ShortComparator comparator;

        protected OppositeComparator(ShortComparator shortComparator) {
            this.comparator = shortComparator;
        }

        @Override
        public final int compare(short s, short s2) {
            return this.comparator.compare(s2, s);
        }
    }

    protected static class OppositeImplicitComparator
    implements ShortComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(short s, short s2) {
            return -Short.compare(s, s2);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    implements ShortComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(short s, short s2) {
            return Short.compare(s, s2);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

