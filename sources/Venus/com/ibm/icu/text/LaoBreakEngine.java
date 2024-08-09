/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.DictionaryBreakEngine;
import com.ibm.icu.text.DictionaryData;
import com.ibm.icu.text.DictionaryMatcher;
import com.ibm.icu.text.UnicodeSet;
import java.io.IOException;
import java.text.CharacterIterator;

class LaoBreakEngine
extends DictionaryBreakEngine {
    private static final byte LAO_LOOKAHEAD = 3;
    private static final byte LAO_ROOT_COMBINE_THRESHOLD = 3;
    private static final byte LAO_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte LAO_MIN_WORD = 2;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fLaoWordSet = new UnicodeSet();
    private static UnicodeSet fEndWordSet;
    private static UnicodeSet fBeginWordSet;
    private static UnicodeSet fMarkSet;

    public LaoBreakEngine() throws IOException {
        this.setCharacters(fLaoWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Laoo");
    }

    public boolean equals(Object object) {
        return object instanceof LaoBreakEngine;
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public boolean handles(int n) {
        int n2 = UCharacter.getIntPropertyValue(n, 4106);
        return n2 == 24;
    }

    @Override
    public int divideUpDictionaryRange(CharacterIterator characterIterator, int n, int n2, DictionaryBreakEngine.DequeI dequeI) {
        int n3;
        int n4;
        if (n2 - n < 2) {
            return 1;
        }
        int n5 = 0;
        DictionaryBreakEngine.PossibleWord[] possibleWordArray = new DictionaryBreakEngine.PossibleWord[3];
        for (n4 = 0; n4 < 3; ++n4) {
            possibleWordArray[n4] = new DictionaryBreakEngine.PossibleWord();
        }
        characterIterator.setIndex(n);
        while ((n3 = characterIterator.getIndex()) < n2) {
            int n6;
            int n7;
            int n8 = 0;
            int n9 = possibleWordArray[n5 % 3].candidates(characterIterator, this.fDictionary, n2);
            if (n9 == 1) {
                n8 = possibleWordArray[n5 % 3].acceptMarked(characterIterator);
                ++n5;
            } else if (n9 > 1) {
                n7 = 0;
                if (characterIterator.getIndex() < n2) {
                    block2: do {
                        n6 = 1;
                        if (possibleWordArray[(n5 + 1) % 3].candidates(characterIterator, this.fDictionary, n2) <= 0) continue;
                        if (n6 < 2) {
                            possibleWordArray[n5 % 3].markCurrent();
                            n6 = 2;
                        }
                        if (characterIterator.getIndex() >= n2) break;
                        do {
                            if (possibleWordArray[(n5 + 2) % 3].candidates(characterIterator, this.fDictionary, n2) <= 0) continue;
                            possibleWordArray[n5 % 3].markCurrent();
                            n7 = 1;
                            continue block2;
                        } while (possibleWordArray[(n5 + 1) % 3].backUp(characterIterator));
                    } while (possibleWordArray[n5 % 3].backUp(characterIterator) && n7 == 0);
                }
                n8 = possibleWordArray[n5 % 3].acceptMarked(characterIterator);
                ++n5;
            }
            if (characterIterator.getIndex() < n2 && n8 < 3) {
                if (possibleWordArray[n5 % 3].candidates(characterIterator, this.fDictionary, n2) <= 0 && (n8 == 0 || possibleWordArray[n5 % 3].longestPrefix() < 3)) {
                    n7 = n2 - (n3 + n8);
                    n6 = characterIterator.current();
                    int n10 = 0;
                    while (true) {
                        characterIterator.next();
                        n4 = characterIterator.current();
                        ++n10;
                        if (--n7 <= 0) break;
                        if (fEndWordSet.contains(n6) && fBeginWordSet.contains(n4)) {
                            int n11 = possibleWordArray[(n5 + 1) % 3].candidates(characterIterator, this.fDictionary, n2);
                            characterIterator.setIndex(n3 + n8 + n10);
                            if (n11 > 0) break;
                        }
                        n6 = n4;
                    }
                    if (n8 <= 0) {
                        ++n5;
                    }
                    n8 += n10;
                } else {
                    characterIterator.setIndex(n3 + n8);
                }
            }
            while ((n7 = characterIterator.getIndex()) < n2 && fMarkSet.contains(characterIterator.current())) {
                characterIterator.next();
                n8 += characterIterator.getIndex() - n7;
            }
            if (n8 <= 0) continue;
            dequeI.push(n3 + n8);
        }
        if (dequeI.peek() >= n2) {
            dequeI.pop();
            --n5;
        }
        return n5;
    }

    static {
        fMarkSet = new UnicodeSet();
        fBeginWordSet = new UnicodeSet();
        fLaoWordSet.applyPattern("[[:Laoo:]&[:LineBreak=SA:]]");
        fLaoWordSet.compact();
        fMarkSet.applyPattern("[[:Laoo:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet = new UnicodeSet(fLaoWordSet);
        fEndWordSet.remove(3776, 3780);
        fBeginWordSet.add(3713, 3758);
        fBeginWordSet.add(3804, 3805);
        fBeginWordSet.add(3776, 3780);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fLaoWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
    }
}

