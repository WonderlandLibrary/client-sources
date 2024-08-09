/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharComparator;
import java.io.Serializable;
import java.util.Comparator;

public final class CharComparators {
    public static final CharComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
    public static final CharComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();

    private CharComparators() {
    }

    public static CharComparator oppositeComparator(CharComparator charComparator) {
        return new OppositeComparator(charComparator);
    }

    public static CharComparator asCharComparator(Comparator<? super Character> comparator) {
        if (comparator == null || comparator instanceof CharComparator) {
            return (CharComparator)comparator;
        }
        return new CharComparator(comparator){
            final Comparator val$c;
            {
                this.val$c = comparator;
            }

            @Override
            public int compare(char c, char c2) {
                return this.val$c.compare(Character.valueOf(c), Character.valueOf(c2));
            }

            @Override
            public int compare(Character c, Character c2) {
                return this.val$c.compare(c, c2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Character)object, (Character)object2);
            }
        };
    }

    protected static class OppositeComparator
    implements CharComparator,
    Serializable {
        private static final long serialVersionUID = 1L;
        private final CharComparator comparator;

        protected OppositeComparator(CharComparator charComparator) {
            this.comparator = charComparator;
        }

        @Override
        public final int compare(char c, char c2) {
            return this.comparator.compare(c2, c);
        }
    }

    protected static class OppositeImplicitComparator
    implements CharComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected OppositeImplicitComparator() {
        }

        @Override
        public final int compare(char c, char c2) {
            return -Character.compare(c, c2);
        }

        private Object readResolve() {
            return OPPOSITE_COMPARATOR;
        }
    }

    protected static class NaturalImplicitComparator
    implements CharComparator,
    Serializable {
        private static final long serialVersionUID = 1L;

        protected NaturalImplicitComparator() {
        }

        @Override
        public final int compare(char c, char c2) {
            return Character.compare(c, c2);
        }

        private Object readResolve() {
            return NATURAL_COMPARATOR;
        }
    }
}

