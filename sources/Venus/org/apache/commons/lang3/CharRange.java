/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class CharRange
implements Iterable<Character>,
Serializable {
    private static final long serialVersionUID = 8270183163158333422L;
    private final char start;
    private final char end;
    private final boolean negated;
    private transient String iToString;

    private CharRange(char c, char c2, boolean bl) {
        if (c > c2) {
            char c3 = c;
            c = c2;
            c2 = c3;
        }
        this.start = c;
        this.end = c2;
        this.negated = bl;
    }

    public static CharRange is(char c) {
        return new CharRange(c, c, false);
    }

    public static CharRange isNot(char c) {
        return new CharRange(c, c, true);
    }

    public static CharRange isIn(char c, char c2) {
        return new CharRange(c, c2, false);
    }

    public static CharRange isNotIn(char c, char c2) {
        return new CharRange(c, c2, true);
    }

    public char getStart() {
        return this.start;
    }

    public char getEnd() {
        return this.end;
    }

    public boolean isNegated() {
        return this.negated;
    }

    public boolean contains(char c) {
        return (c >= this.start && c <= this.end) != this.negated;
    }

    public boolean contains(CharRange charRange) {
        if (charRange == null) {
            throw new IllegalArgumentException("The Range must not be null");
        }
        if (this.negated) {
            if (charRange.negated) {
                return this.start >= charRange.start && this.end <= charRange.end;
            }
            return charRange.end < this.start || charRange.start > this.end;
        }
        if (charRange.negated) {
            return this.start == '\u0000' && this.end == '\uffff';
        }
        return this.start <= charRange.start && this.end >= charRange.end;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof CharRange)) {
            return true;
        }
        CharRange charRange = (CharRange)object;
        return this.start == charRange.start && this.end == charRange.end && this.negated == charRange.negated;
    }

    public int hashCode() {
        return 83 + this.start + 7 * this.end + (this.negated ? 1 : 0);
    }

    public String toString() {
        if (this.iToString == null) {
            StringBuilder stringBuilder = new StringBuilder(4);
            if (this.isNegated()) {
                stringBuilder.append('^');
            }
            stringBuilder.append(this.start);
            if (this.start != this.end) {
                stringBuilder.append('-');
                stringBuilder.append(this.end);
            }
            this.iToString = stringBuilder.toString();
        }
        return this.iToString;
    }

    @Override
    public Iterator<Character> iterator() {
        return new CharacterIterator(this, null);
    }

    static boolean access$100(CharRange charRange) {
        return charRange.negated;
    }

    static char access$200(CharRange charRange) {
        return charRange.start;
    }

    static char access$300(CharRange charRange) {
        return charRange.end;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class CharacterIterator
    implements Iterator<Character> {
        private char current;
        private final CharRange range;
        private boolean hasNext;

        private CharacterIterator(CharRange charRange) {
            this.range = charRange;
            this.hasNext = true;
            if (CharRange.access$100(this.range)) {
                if (CharRange.access$200(this.range) == '\u0000') {
                    if (CharRange.access$300(this.range) == '\uffff') {
                        this.hasNext = false;
                    } else {
                        this.current = (char)(CharRange.access$300(this.range) + '\u0001');
                    }
                } else {
                    this.current = '\u0000';
                }
            } else {
                this.current = CharRange.access$200(this.range);
            }
        }

        private void prepareNext() {
            if (CharRange.access$100(this.range)) {
                if (this.current == '\uffff') {
                    this.hasNext = false;
                } else if (this.current + '\u0001' == CharRange.access$200(this.range)) {
                    if (CharRange.access$300(this.range) == '\uffff') {
                        this.hasNext = false;
                    } else {
                        this.current = (char)(CharRange.access$300(this.range) + '\u0001');
                    }
                } else {
                    this.current = (char)(this.current + '\u0001');
                }
            } else if (this.current < CharRange.access$300(this.range)) {
                this.current = (char)(this.current + '\u0001');
            } else {
                this.hasNext = false;
            }
        }

        @Override
        public boolean hasNext() {
            return this.hasNext;
        }

        @Override
        public Character next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            char c = this.current;
            this.prepareNext();
            return Character.valueOf(c);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object next() {
            return this.next();
        }

        CharacterIterator(CharRange charRange, 1 var2_2) {
            this(charRange);
        }
    }
}

