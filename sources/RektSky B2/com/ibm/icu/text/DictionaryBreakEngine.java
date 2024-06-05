package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.impl.*;

abstract class DictionaryBreakEngine implements LanguageBreakEngine
{
    UnicodeSet fSet;
    
    public DictionaryBreakEngine() {
        this.fSet = new UnicodeSet();
    }
    
    @Override
    public boolean handles(final int c) {
        return this.fSet.contains(c);
    }
    
    @Override
    public int findBreaks(final CharacterIterator text, final int startPos, final int endPos, final DequeI foundBreaks) {
        int result = 0;
        final int start = text.getIndex();
        int current;
        for (int c = CharacterIteration.current32(text); (current = text.getIndex()) < endPos && this.fSet.contains(c); c = CharacterIteration.current32(text)) {
            CharacterIteration.next32(text);
        }
        final int rangeStart = start;
        final int rangeEnd = current;
        result = this.divideUpDictionaryRange(text, rangeStart, rangeEnd, foundBreaks);
        text.setIndex(current);
        return result;
    }
    
    void setCharacters(final UnicodeSet set) {
        (this.fSet = new UnicodeSet(set)).compact();
    }
    
    abstract int divideUpDictionaryRange(final CharacterIterator p0, final int p1, final int p2, final DequeI p3);
    
    static class PossibleWord
    {
        private static final int POSSIBLE_WORD_LIST_MAX = 20;
        private int[] lengths;
        private int[] count;
        private int prefix;
        private int offset;
        private int mark;
        private int current;
        
        public PossibleWord() {
            this.lengths = new int[20];
            this.count = new int[1];
            this.offset = -1;
        }
        
        public int candidates(final CharacterIterator fIter, final DictionaryMatcher dict, final int rangeEnd) {
            final int start = fIter.getIndex();
            if (start != this.offset) {
                this.offset = start;
                this.prefix = dict.matches(fIter, rangeEnd - start, this.lengths, this.count, this.lengths.length);
                if (this.count[0] <= 0) {
                    fIter.setIndex(start);
                }
            }
            if (this.count[0] > 0) {
                fIter.setIndex(start + this.lengths[this.count[0] - 1]);
            }
            this.current = this.count[0] - 1;
            this.mark = this.current;
            return this.count[0];
        }
        
        public int acceptMarked(final CharacterIterator fIter) {
            fIter.setIndex(this.offset + this.lengths[this.mark]);
            return this.lengths[this.mark];
        }
        
        public boolean backUp(final CharacterIterator fIter) {
            if (this.current > 0) {
                final int offset = this.offset;
                final int[] lengths = this.lengths;
                final int current = this.current - 1;
                this.current = current;
                fIter.setIndex(offset + lengths[current]);
                return true;
            }
            return false;
        }
        
        public int longestPrefix() {
            return this.prefix;
        }
        
        public void markCurrent() {
            this.mark = this.current;
        }
    }
    
    static class DequeI implements Cloneable
    {
        private int[] data;
        private int lastIdx;
        private int firstIdx;
        
        DequeI() {
            this.data = new int[50];
            this.lastIdx = 4;
            this.firstIdx = 4;
        }
        
        public Object clone() throws CloneNotSupportedException {
            final DequeI result = (DequeI)super.clone();
            result.data = this.data.clone();
            return result;
        }
        
        int size() {
            return this.firstIdx - this.lastIdx;
        }
        
        boolean isEmpty() {
            return this.size() == 0;
        }
        
        private void grow() {
            final int[] newData = new int[this.data.length * 2];
            System.arraycopy(this.data, 0, newData, 0, this.data.length);
            this.data = newData;
        }
        
        void offer(final int v) {
            assert this.lastIdx > 0;
            this.data[--this.lastIdx] = v;
        }
        
        void push(final int v) {
            if (this.firstIdx >= this.data.length) {
                this.grow();
            }
            this.data[this.firstIdx++] = v;
        }
        
        int pop() {
            assert this.size() > 0;
            final int[] data = this.data;
            final int firstIdx = this.firstIdx - 1;
            this.firstIdx = firstIdx;
            return data[firstIdx];
        }
        
        int peek() {
            assert this.size() > 0;
            return this.data[this.firstIdx - 1];
        }
        
        int peekLast() {
            assert this.size() > 0;
            return this.data[this.lastIdx];
        }
        
        int pollLast() {
            assert this.size() > 0;
            return this.data[this.lastIdx++];
        }
        
        boolean contains(final int v) {
            for (int i = this.lastIdx; i < this.firstIdx; ++i) {
                if (this.data[i] == v) {
                    return true;
                }
            }
            return false;
        }
        
        int elementAt(final int i) {
            assert i < this.size();
            return this.data[this.lastIdx + i];
        }
        
        void removeAllElements() {
            final int n = 4;
            this.firstIdx = n;
            this.lastIdx = n;
        }
    }
}
