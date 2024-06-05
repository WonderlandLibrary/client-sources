package com.ibm.icu.text;

import java.io.*;
import com.ibm.icu.lang.*;
import java.text.*;

class KhmerBreakEngine extends DictionaryBreakEngine
{
    private static final byte KHMER_LOOKAHEAD = 3;
    private static final byte KHMER_ROOT_COMBINE_THRESHOLD = 3;
    private static final byte KHMER_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte KHMER_MIN_WORD = 2;
    private static final byte KHMER_MIN_WORD_SPAN = 4;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fKhmerWordSet;
    private static UnicodeSet fEndWordSet;
    private static UnicodeSet fBeginWordSet;
    private static UnicodeSet fMarkSet;
    
    public KhmerBreakEngine() throws IOException {
        this.setCharacters(KhmerBreakEngine.fKhmerWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Khmr");
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof KhmerBreakEngine;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
    
    @Override
    public boolean handles(final int c) {
        final int script = UCharacter.getIntPropertyValue(c, 4106);
        return script == 23;
    }
    
    public int divideUpDictionaryRange(final CharacterIterator fIter, final int rangeStart, final int rangeEnd, final DequeI foundBreaks) {
        if (rangeEnd - rangeStart < 4) {
            return 0;
        }
        int wordsFound = 0;
        final PossibleWord[] words = new PossibleWord[3];
        for (int i = 0; i < 3; ++i) {
            words[i] = new PossibleWord();
        }
        fIter.setIndex(rangeStart);
        int current;
        while ((current = fIter.getIndex()) < rangeEnd) {
            int wordLength = 0;
            final int candidates = words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd);
            if (candidates == 1) {
                wordLength = words[wordsFound % 3].acceptMarked(fIter);
                ++wordsFound;
            }
            else if (candidates > 1) {
                boolean foundBest = false;
                if (fIter.getIndex() < rangeEnd) {
                Label_0240:
                    do {
                        int wordsMatched = 1;
                        if (words[(wordsFound + 1) % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                            if (wordsMatched < 2) {
                                words[wordsFound % 3].markCurrent();
                                wordsMatched = 2;
                            }
                            if (fIter.getIndex() >= rangeEnd) {
                                break;
                            }
                            while (words[(wordsFound + 2) % 3].candidates(fIter, this.fDictionary, rangeEnd) <= 0) {
                                if (!words[(wordsFound + 1) % 3].backUp(fIter)) {
                                    continue Label_0240;
                                }
                            }
                            words[wordsFound % 3].markCurrent();
                            foundBest = true;
                        }
                    } while (words[wordsFound % 3].backUp(fIter) && !foundBest);
                }
                wordLength = words[wordsFound % 3].acceptMarked(fIter);
                ++wordsFound;
            }
            if (fIter.getIndex() < rangeEnd && wordLength < 3) {
                if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) <= 0 && (wordLength == 0 || words[wordsFound % 3].longestPrefix() < 3)) {
                    int remaining = rangeEnd - (current + wordLength);
                    int pc = fIter.current();
                    int chars = 0;
                    while (true) {
                        fIter.next();
                        final int uc = fIter.current();
                        ++chars;
                        if (--remaining <= 0) {
                            break;
                        }
                        if (KhmerBreakEngine.fEndWordSet.contains(pc) && KhmerBreakEngine.fBeginWordSet.contains(uc)) {
                            final int candidate = words[(wordsFound + 1) % 3].candidates(fIter, this.fDictionary, rangeEnd);
                            fIter.setIndex(current + wordLength + chars);
                            if (candidate > 0) {
                                break;
                            }
                        }
                        pc = uc;
                    }
                    if (wordLength <= 0) {
                        ++wordsFound;
                    }
                    wordLength += chars;
                }
                else {
                    fIter.setIndex(current + wordLength);
                }
            }
            int currPos;
            while ((currPos = fIter.getIndex()) < rangeEnd && KhmerBreakEngine.fMarkSet.contains(fIter.current())) {
                fIter.next();
                wordLength += fIter.getIndex() - currPos;
            }
            if (wordLength > 0) {
                foundBreaks.push(current + wordLength);
            }
        }
        if (foundBreaks.peek() >= rangeEnd) {
            foundBreaks.pop();
            --wordsFound;
        }
        return wordsFound;
    }
    
    static {
        KhmerBreakEngine.fKhmerWordSet = new UnicodeSet();
        KhmerBreakEngine.fMarkSet = new UnicodeSet();
        KhmerBreakEngine.fBeginWordSet = new UnicodeSet();
        KhmerBreakEngine.fKhmerWordSet.applyPattern("[[:Khmer:]&[:LineBreak=SA:]]");
        KhmerBreakEngine.fKhmerWordSet.compact();
        KhmerBreakEngine.fMarkSet.applyPattern("[[:Khmer:]&[:LineBreak=SA:]&[:M:]]");
        KhmerBreakEngine.fMarkSet.add(32);
        KhmerBreakEngine.fEndWordSet = new UnicodeSet(KhmerBreakEngine.fKhmerWordSet);
        KhmerBreakEngine.fBeginWordSet.add(6016, 6067);
        KhmerBreakEngine.fEndWordSet.remove(6098);
        KhmerBreakEngine.fMarkSet.compact();
        KhmerBreakEngine.fEndWordSet.compact();
        KhmerBreakEngine.fBeginWordSet.compact();
        KhmerBreakEngine.fKhmerWordSet.freeze();
        KhmerBreakEngine.fMarkSet.freeze();
        KhmerBreakEngine.fEndWordSet.freeze();
        KhmerBreakEngine.fBeginWordSet.freeze();
    }
}
