/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatComparator;
import java.io.Serializable;
import java.util.Comparator;

public final class FloatComparators {
    public static final FloatComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final FloatComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private FloatComparators() {
    }

    public static FloatComparator oppositeComparator(FloatComparator floatComparator) {
        return new OppositeComparator(floatComparator);
    }

    public static FloatComparator asFloatComparator(Comparator<? super Float> comparator) {
        if (comparator == null || comparator instanceof FloatComparator) {
            return (FloatComparator)comparator;
        }
        return new FloatComparator(comparator){
            final Comparator val$c;
            {
                this.val$c = comparator;
            }

            @Override
            public int compare(float f, float f2) {
                return this.val$c.compare(Float.valueOf(f), Float.valueOf(f2));
            }

            @Override
            public int compare(Float f, Float f2) {
                return this.val$c.compare(f, f2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Float)object, (Float)object2);
            }
        };
    }

    protected static class OppositeComparator
    implements FloatComparator,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final FloatComparator comparator;

        protected OppositeComparator(FloatComparator floatComparator) {
            this.comparator = floatComparator;
        }

        @Override
        public final int compare(float f, float f2) {
            return this.comparator.compare(f2, f);
        }
    }

    protected static class OppositeImplicitComparator
    implements FloatComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(float f, float f2) {
            return -Float.compare(f, f2);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    implements FloatComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(float f, float f2) {
            return Float.compare(f, f2);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

