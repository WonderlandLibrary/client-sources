/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeFilter;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;

final class BreakTransliterator
extends Transliterator {
    private BreakIterator bi;
    private String insertion;
    private int[] boundaries = new int[50];
    private int boundaryCount = 0;
    static final int LETTER_OR_MARK_MASK = 510;

    public BreakTransliterator(String string, UnicodeFilter unicodeFilter, BreakIterator breakIterator, String string2) {
        super(string, unicodeFilter);
        this.bi = breakIterator;
        this.insertion = string2;
    }

    public BreakTransliterator(String string, UnicodeFilter unicodeFilter) {
        this(string, unicodeFilter, null, " ");
    }

    public String getInsertion() {
        return this.insertion;
    }

    public void setInsertion(String string) {
        this.insertion = string;
    }

    public BreakIterator getBreakIterator() {
        if (this.bi == null) {
            this.bi = BreakIterator.getWordInstance(new ULocale("th_TH"));
        }
        return this.bi;
    }

    public void setBreakIterator(BreakIterator breakIterator) {
        this.bi = breakIterator;
    }

    @Override
    protected synchronized void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        int n2;
        this.boundaryCount = 0;
        int n3 = 0;
        this.getBreakIterator();
        this.bi.setText(new ReplaceableCharacterIterator(replaceable, position.start, position.limit, position.start));
        n3 = this.bi.first();
        while (n3 != -1 && n3 < position.limit) {
            if (n3 != 0 && (1 << (n2 = UCharacter.getType(n = UTF16.charAt(replaceable, n3 - 1))) & 0x1FE) != 0 && (1 << (n2 = UCharacter.getType(n = UTF16.charAt(replaceable, n3))) & 0x1FE) != 0) {
                if (this.boundaryCount >= this.boundaries.length) {
                    int[] nArray = new int[this.boundaries.length * 2];
                    System.arraycopy(this.boundaries, 0, nArray, 0, this.boundaries.length);
                    this.boundaries = nArray;
                }
                this.boundaries[this.boundaryCount++] = n3;
            }
            n3 = this.bi.next();
        }
        n = 0;
        n2 = 0;
        if (this.boundaryCount != 0) {
            n = this.boundaryCount * this.insertion.length();
            n2 = this.boundaries[this.boundaryCount - 1];
            while (this.boundaryCount > 0) {
                n3 = this.boundaries[--this.boundaryCount];
                replaceable.replace(n3, n3, this.insertion);
            }
        }
        position.contextLimit += n;
        position.limit += n;
        position.start = bl ? n2 + n : position.limit;
    }

    static void register() {
        BreakTransliterator breakTransliterator = new BreakTransliterator("Any-BreakInternal", null);
        Transliterator.registerInstance(breakTransliterator, false);
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = this.getFilterAsUnicodeSet(unicodeSet);
        if (unicodeSet4.size() != 0) {
            unicodeSet3.addAll(this.insertion);
        }
    }

    static final class ReplaceableCharacterIterator
    implements CharacterIterator {
        private Replaceable text;
        private int begin;
        private int end;
        private int pos;

        public ReplaceableCharacterIterator(Replaceable replaceable, int n, int n2, int n3) {
            if (replaceable == null) {
                throw new NullPointerException();
            }
            this.text = replaceable;
            if (n < 0 || n > n2 || n2 > replaceable.length()) {
                throw new IllegalArgumentException("Invalid substring range");
            }
            if (n3 < n || n3 > n2) {
                throw new IllegalArgumentException("Invalid position");
            }
            this.begin = n;
            this.end = n2;
            this.pos = n3;
        }

        public void setText(Replaceable replaceable) {
            if (replaceable == null) {
                throw new NullPointerException();
            }
            this.text = replaceable;
            this.begin = 0;
            this.end = replaceable.length();
            this.pos = 0;
        }

        @Override
        public char first() {
            this.pos = this.begin;
            return this.current();
        }

        @Override
        public char last() {
            this.pos = this.end != this.begin ? this.end - 1 : this.end;
            return this.current();
        }

        @Override
        public char setIndex(int n) {
            if (n < this.begin || n > this.end) {
                throw new IllegalArgumentException("Invalid index");
            }
            this.pos = n;
            return this.current();
        }

        @Override
        public char current() {
            if (this.pos >= this.begin && this.pos < this.end) {
                return this.text.charAt(this.pos);
            }
            return '\u0000';
        }

        @Override
        public char next() {
            if (this.pos < this.end - 1) {
                ++this.pos;
                return this.text.charAt(this.pos);
            }
            this.pos = this.end;
            return '\u0000';
        }

        @Override
        public char previous() {
            if (this.pos > this.begin) {
                --this.pos;
                return this.text.charAt(this.pos);
            }
            return '\u0000';
        }

        @Override
        public int getBeginIndex() {
            return this.begin;
        }

        @Override
        public int getEndIndex() {
            return this.end;
        }

        @Override
        public int getIndex() {
            return this.pos;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof ReplaceableCharacterIterator)) {
                return true;
            }
            ReplaceableCharacterIterator replaceableCharacterIterator = (ReplaceableCharacterIterator)object;
            if (this.hashCode() != replaceableCharacterIterator.hashCode()) {
                return true;
            }
            if (!this.text.equals(replaceableCharacterIterator.text)) {
                return true;
            }
            return this.pos != replaceableCharacterIterator.pos || this.begin != replaceableCharacterIterator.begin || this.end != replaceableCharacterIterator.end;
        }

        public int hashCode() {
            return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
        }

        @Override
        public Object clone() {
            try {
                ReplaceableCharacterIterator replaceableCharacterIterator = (ReplaceableCharacterIterator)super.clone();
                return replaceableCharacterIterator;
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new ICUCloneNotSupportedException();
            }
        }
    }
}

