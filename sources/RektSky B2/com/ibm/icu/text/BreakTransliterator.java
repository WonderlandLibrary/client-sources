package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.util.*;

final class BreakTransliterator extends Transliterator
{
    private BreakIterator bi;
    private String insertion;
    private int[] boundaries;
    private int boundaryCount;
    static final int LETTER_OR_MARK_MASK = 510;
    
    public BreakTransliterator(final String ID, final UnicodeFilter filter, final BreakIterator bi, final String insertion) {
        super(ID, filter);
        this.boundaries = new int[50];
        this.boundaryCount = 0;
        this.bi = bi;
        this.insertion = insertion;
    }
    
    public BreakTransliterator(final String ID, final UnicodeFilter filter) {
        this(ID, filter, null, " ");
    }
    
    public String getInsertion() {
        return this.insertion;
    }
    
    public void setInsertion(final String insertion) {
        this.insertion = insertion;
    }
    
    public BreakIterator getBreakIterator() {
        if (this.bi == null) {
            this.bi = BreakIterator.getWordInstance(new ULocale("th_TH"));
        }
        return this.bi;
    }
    
    public void setBreakIterator(final BreakIterator bi) {
        this.bi = bi;
    }
    
    @Override
    protected synchronized void handleTransliterate(final Replaceable text, final Position pos, final boolean incremental) {
        this.boundaryCount = 0;
        int boundary = 0;
        this.getBreakIterator();
        this.bi.setText(new ReplaceableCharacterIterator(text, pos.start, pos.limit, pos.start));
        for (boundary = this.bi.first(); boundary != -1 && boundary < pos.limit; boundary = this.bi.next()) {
            if (boundary != 0) {
                int cp = UTF16.charAt(text, boundary - 1);
                int type = UCharacter.getType(cp);
                if ((1 << type & 0x1FE) != 0x0) {
                    cp = UTF16.charAt(text, boundary);
                    type = UCharacter.getType(cp);
                    if ((1 << type & 0x1FE) != 0x0) {
                        if (this.boundaryCount >= this.boundaries.length) {
                            final int[] temp = new int[this.boundaries.length * 2];
                            System.arraycopy(this.boundaries, 0, temp, 0, this.boundaries.length);
                            this.boundaries = temp;
                        }
                        this.boundaries[this.boundaryCount++] = boundary;
                    }
                }
            }
        }
        int delta = 0;
        int lastBoundary = 0;
        if (this.boundaryCount != 0) {
            delta = this.boundaryCount * this.insertion.length();
            lastBoundary = this.boundaries[this.boundaryCount - 1];
            while (this.boundaryCount > 0) {
                final int[] boundaries = this.boundaries;
                final int boundaryCount = this.boundaryCount - 1;
                this.boundaryCount = boundaryCount;
                boundary = boundaries[boundaryCount];
                text.replace(boundary, boundary, this.insertion);
            }
        }
        pos.contextLimit += delta;
        pos.limit += delta;
        pos.start = (incremental ? (lastBoundary + delta) : pos.limit);
    }
    
    static void register() {
        final Transliterator trans = new BreakTransliterator("Any-BreakInternal", null);
        Transliterator.registerInstance(trans, false);
    }
    
    @Override
    public void addSourceTargetSet(final UnicodeSet inputFilter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
        if (myFilter.size() != 0) {
            targetSet.addAll(this.insertion);
        }
    }
    
    static final class ReplaceableCharacterIterator implements CharacterIterator
    {
        private Replaceable text;
        private int begin;
        private int end;
        private int pos;
        
        public ReplaceableCharacterIterator(final Replaceable text, final int begin, final int end, final int pos) {
            if (text == null) {
                throw new NullPointerException();
            }
            this.text = text;
            if (begin < 0 || begin > end || end > text.length()) {
                throw new IllegalArgumentException("Invalid substring range");
            }
            if (pos < begin || pos > end) {
                throw new IllegalArgumentException("Invalid position");
            }
            this.begin = begin;
            this.end = end;
            this.pos = pos;
        }
        
        public void setText(final Replaceable text) {
            if (text == null) {
                throw new NullPointerException();
            }
            this.text = text;
            this.begin = 0;
            this.end = text.length();
            this.pos = 0;
        }
        
        @Override
        public char first() {
            this.pos = this.begin;
            return this.current();
        }
        
        @Override
        public char last() {
            if (this.end != this.begin) {
                this.pos = this.end - 1;
            }
            else {
                this.pos = this.end;
            }
            return this.current();
        }
        
        @Override
        public char setIndex(final int p) {
            if (p < this.begin || p > this.end) {
                throw new IllegalArgumentException("Invalid index");
            }
            this.pos = p;
            return this.current();
        }
        
        @Override
        public char current() {
            if (this.pos >= this.begin && this.pos < this.end) {
                return this.text.charAt(this.pos);
            }
            return '\uffff';
        }
        
        @Override
        public char next() {
            if (this.pos < this.end - 1) {
                ++this.pos;
                return this.text.charAt(this.pos);
            }
            this.pos = this.end;
            return '\uffff';
        }
        
        @Override
        public char previous() {
            if (this.pos > this.begin) {
                --this.pos;
                return this.text.charAt(this.pos);
            }
            return '\uffff';
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
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ReplaceableCharacterIterator)) {
                return false;
            }
            final ReplaceableCharacterIterator that = (ReplaceableCharacterIterator)obj;
            return this.hashCode() == that.hashCode() && this.text.equals(that.text) && this.pos == that.pos && this.begin == that.begin && this.end == that.end;
        }
        
        @Override
        public int hashCode() {
            return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
        }
        
        @Override
        public Object clone() {
            try {
                final ReplaceableCharacterIterator other = (ReplaceableCharacterIterator)super.clone();
                return other;
            }
            catch (CloneNotSupportedException e) {
                throw new ICUCloneNotSupportedException();
            }
        }
    }
}
