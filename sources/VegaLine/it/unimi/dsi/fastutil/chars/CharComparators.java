/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharComparator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import java.io.Serializable;

public class CharComparators {
    public static final CharComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final CharComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private CharComparators() {
    }

    public static CharComparator oppositeComparator(CharComparator c) {
        return new OppositeComparator(c);
    }

    protected static class OppositeComparator
    extends AbstractCharComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final CharComparator comparator;

        protected OppositeComparator(CharComparator c) {
            this.comparator = c;
        }

        @Override
        public final int compare(char a, char b) {
            return this.comparator.compare(b, a);
        }
    }

    protected static class OppositeImplicitComparator
    extends AbstractCharComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(char a, char b) {
            return -Character.compare(a, b);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    extends AbstractCharComparator
    implements Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(char a, char b) {
            return Character.compare(a, b);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

