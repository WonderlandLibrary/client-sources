/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.text.DictionaryMatcher;
import com.ibm.icu.text.LanguageBreakEngine;
import com.ibm.icu.text.UnicodeSet;
import java.text.CharacterIterator;

abstract class DictionaryBreakEngine
implements LanguageBreakEngine {
    UnicodeSet fSet = new UnicodeSet();

    @Override
    public boolean handles(int n) {
        return this.fSet.contains(n);
    }

    @Override
    public int findBreaks(CharacterIterator characterIterator, int n, int n2, DequeI dequeI) {
        int n3;
        int n4 = 0;
        int n5 = characterIterator.getIndex();
        int n6 = CharacterIteration.current32(characterIterator);
        while ((n3 = characterIterator.getIndex()) < n2 && this.fSet.contains(n6)) {
            CharacterIteration.next32(characterIterator);
            n6 = CharacterIteration.current32(characterIterator);
        }
        int n7 = n5;
        int n8 = n3;
        n4 = this.divideUpDictionaryRange(characterIterator, n7, n8, dequeI);
        characterIterator.setIndex(n3);
        return n4;
    }

    void setCharacters(UnicodeSet unicodeSet) {
        this.fSet = new UnicodeSet(unicodeSet);
        this.fSet.compact();
    }

    abstract int divideUpDictionaryRange(CharacterIterator var1, int var2, int var3, DequeI var4);

    static class DequeI
    implements Cloneable {
        private int[] data = new int[50];
        private int lastIdx = 4;
        private int firstIdx = 4;
        static final boolean $assertionsDisabled = !DictionaryBreakEngine.class.desiredAssertionStatus();

        DequeI() {
        }

        public Object clone() throws CloneNotSupportedException {
            DequeI dequeI = (DequeI)super.clone();
            dequeI.data = (int[])this.data.clone();
            return dequeI;
        }

        int size() {
            return this.firstIdx - this.lastIdx;
        }

        boolean isEmpty() {
            return this.size() == 0;
        }

        private void grow() {
            int[] nArray = new int[this.data.length * 2];
            System.arraycopy(this.data, 0, nArray, 0, this.data.length);
            this.data = nArray;
        }

        void offer(int n) {
            if (!$assertionsDisabled && this.lastIdx <= 0) {
                throw new AssertionError();
            }
            this.data[--this.lastIdx] = n;
        }

        void push(int n) {
            if (this.firstIdx >= this.data.length) {
                this.grow();
            }
            this.data[this.firstIdx++] = n;
        }

        int pop() {
            if (!$assertionsDisabled && this.size() <= 0) {
                throw new AssertionError();
            }
            return this.data[--this.firstIdx];
        }

        int peek() {
            if (!$assertionsDisabled && this.size() <= 0) {
                throw new AssertionError();
            }
            return this.data[this.firstIdx - 1];
        }

        int peekLast() {
            if (!$assertionsDisabled && this.size() <= 0) {
                throw new AssertionError();
            }
            return this.data[this.lastIdx];
        }

        int pollLast() {
            if (!$assertionsDisabled && this.size() <= 0) {
                throw new AssertionError();
            }
            return this.data[this.lastIdx++];
        }

        boolean contains(int n) {
            for (int i = this.lastIdx; i < this.firstIdx; ++i) {
                if (this.data[i] != n) continue;
                return false;
            }
            return true;
        }

        int elementAt(int n) {
            if (!$assertionsDisabled && n >= this.size()) {
                throw new AssertionError();
            }
            return this.data[this.lastIdx + n];
        }

        void removeAllElements() {
            this.firstIdx = 4;
            this.lastIdx = 4;
        }
    }

    static class PossibleWord {
        private static final int POSSIBLE_WORD_LIST_MAX = 20;
        private int[] lengths = new int[20];
        private int[] count = new int[1];
        private int prefix;
        private int offset = -1;
        private int mark;
        private int current;

        public int candidates(CharacterIterator characterIterator, DictionaryMatcher dictionaryMatcher, int n) {
            int n2 = characterIterator.getIndex();
            if (n2 != this.offset) {
                this.offset = n2;
                this.prefix = dictionaryMatcher.matches(characterIterator, n - n2, this.lengths, this.count, this.lengths.length);
                if (this.count[0] <= 0) {
                    characterIterator.setIndex(n2);
                }
            }
            if (this.count[0] > 0) {
                characterIterator.setIndex(n2 + this.lengths[this.count[0] - 1]);
            }
            this.mark = this.current = this.count[0] - 1;
            return this.count[0];
        }

        public int acceptMarked(CharacterIterator characterIterator) {
            characterIterator.setIndex(this.offset + this.lengths[this.mark]);
            return this.lengths[this.mark];
        }

        public boolean backUp(CharacterIterator characterIterator) {
            if (this.current > 0) {
                characterIterator.setIndex(this.offset + this.lengths[--this.current]);
                return false;
            }
            return true;
        }

        public int longestPrefix() {
            return this.prefix;
        }

        public void markCurrent() {
            this.mark = this.current;
        }
    }
}

