/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatComparator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import java.io.Serializable;

public class FloatComparators {
    public static final FloatComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final FloatComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private FloatComparators() {
    }

    public static FloatComparator oppositeComparator(FloatComparator c) {
        return new OppositeComparator(c);
    }

    protected static class OppositeComparator
    extends AbstractFloatComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final FloatComparator comparator;

        protected OppositeComparator(FloatComparator c) {
            this.comparator = c;
        }

        @Override
        public final int compare(float a, float b) {
            return this.comparator.compare(b, a);
        }
    }

    protected static class OppositeImplicitComparator
    extends AbstractFloatComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(float a, float b) {
            return -Float.compare(a, b);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    extends AbstractFloatComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(float a, float b) {
            return Float.compare(a, b);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

