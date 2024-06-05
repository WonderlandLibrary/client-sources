package com.ibm.icu.text;

import java.io.*;
import com.ibm.icu.lang.*;
import java.text.*;

class ThaiBreakEngine extends DictionaryBreakEngine
{
    private static final byte THAI_LOOKAHEAD = 3;
    private static final byte THAI_ROOT_COMBINE_THRESHOLD = 3;
    private static final byte THAI_PREFIX_COMBINE_THRESHOLD = 3;
    private static final char THAI_PAIYANNOI = '\u0e2f';
    private static final char THAI_MAIYAMOK = '\u0e46';
    private static final byte THAI_MIN_WORD = 2;
    private static final byte THAI_MIN_WORD_SPAN = 4;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fThaiWordSet;
    private static UnicodeSet fEndWordSet;
    private static UnicodeSet fBeginWordSet;
    private static UnicodeSet fSuffixSet;
    private static UnicodeSet fMarkSet;
    
    public ThaiBreakEngine() throws IOException {
        this.setCharacters(ThaiBreakEngine.fThaiWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Thai");
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ThaiBreakEngine;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
    
    @Override
    public boolean handles(final int c) {
        final int script = UCharacter.getIntPropertyValue(c, 4106);
        return script == 38;
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
                if (fIter.getIndex() < rangeEnd) {
                Label_0234:
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
                                    continue Label_0234;
                                }
                            }
                            words[wordsFound % 3].markCurrent();
                            break;
                        }
                    } while (words[wordsFound % 3].backUp(fIter));
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
                        if (ThaiBreakEngine.fEndWordSet.contains(pc) && ThaiBreakEngine.fBeginWordSet.contains(uc)) {
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
            while ((currPos = fIter.getIndex()) < rangeEnd && ThaiBreakEngine.fMarkSet.contains(fIter.current())) {
                fIter.next();
                wordLength += fIter.getIndex() - currPos;
            }
            if (fIter.getIndex() < rangeEnd && wordLength > 0) {
                int uc;
                if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) <= 0 && ThaiBreakEngine.fSuffixSet.contains(uc = fIter.current())) {
                    if (uc == 3631) {
                        if (!ThaiBreakEngine.fSuffixSet.contains(fIter.previous())) {
                            fIter.next();
                            fIter.next();
                            ++wordLength;
                            uc = fIter.current();
                        }
                        else {
                            fIter.next();
                        }
                    }
                    if (uc == 3654) {
                        if (fIter.previous() != '\u0e46') {
                            fIter.next();
                            fIter.next();
                            ++wordLength;
                        }
                        else {
                            fIter.next();
                        }
                    }
                }
                else {
                    fIter.setIndex(current + wordLength);
                }
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
        ThaiBreakEngine.fThaiWordSet = new UnicodeSet();
        ThaiBreakEngine.fMarkSet = new UnicodeSet();
        ThaiBreakEngine.fBeginWordSet = new UnicodeSet();
        ThaiBreakEngine.fSuffixSet = new UnicodeSet();
        ThaiBreakEngine.fThaiWordSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]]");
        ThaiBreakEngine.fThaiWordSet.compact();
        ThaiBreakEngine.fMarkSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]&[:M:]]");
        ThaiBreakEngine.fMarkSet.add(32);
        (ThaiBreakEngine.fEndWordSet = new UnicodeSet(ThaiBreakEngine.fThaiWordSet)).remove(3633);
        ThaiBreakEngine.fEndWordSet.remove(3648, 3652);
        ThaiBreakEngine.fBeginWordSet.add(3585, 3630);
        ThaiBreakEngine.fBeginWordSet.add(3648, 3652);
        ThaiBreakEngine.fSuffixSet.add(3631);
        ThaiBreakEngine.fSuffixSet.add(3654);
        ThaiBreakEngine.fMarkSet.compact();
        ThaiBreakEngine.fEndWordSet.compact();
        ThaiBreakEngine.fBeginWordSet.compact();
        ThaiBreakEngine.fSuffixSet.compact();
        ThaiBreakEngine.fThaiWordSet.freeze();
        ThaiBreakEngine.fMarkSet.freeze();
        ThaiBreakEngine.fEndWordSet.freeze();
        ThaiBreakEngine.fBeginWordSet.freeze();
        ThaiBreakEngine.fSuffixSet.freeze();
    }
}
