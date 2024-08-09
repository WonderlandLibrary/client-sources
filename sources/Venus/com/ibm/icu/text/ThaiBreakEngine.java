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

class ThaiBreakEngine
extends DictionaryBreakEngine {
    private static final byte THAI_LOOKAHEAD = 3;
    private static final byte THAI_ROOT_COMBINE_THRESHOLD = 3;
    private static final byte THAI_PREFIX_COMBINE_THRESHOLD = 3;
    private static final char THAI_PAIYANNOI = '\u0e2f';
    private static final char THAI_MAIYAMOK = '\u0e46';
    private static final byte THAI_MIN_WORD = 2;
    private static final byte THAI_MIN_WORD_SPAN = 4;
    private DictionaryMatcher fDictionary;
    private static UnicodeSet fThaiWordSet = new UnicodeSet();
    private static UnicodeSet fEndWordSet;
    private static UnicodeSet fBeginWordSet;
    private static UnicodeSet fSuffixSet;
    private static UnicodeSet fMarkSet;

    public ThaiBreakEngine() throws IOException {
        this.setCharacters(fThaiWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Thai");
    }

    public boolean equals(Object object) {
        return object instanceof ThaiBreakEngine;
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public boolean handles(int n) {
        int n2 = UCharacter.getIntPropertyValue(n, 4106);
        return n2 == 38;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public int divideUpDictionaryRange(CharacterIterator var1_1, int var2_2, int var3_3, DictionaryBreakEngine.DequeI var4_4) {
        if (var3_3 - var2_2 < 4) {
            return 1;
        }
        var5_5 = 0;
        var7_6 = new DictionaryBreakEngine.PossibleWord[3];
        for (var8_7 = 0; var8_7 < 3; ++var8_7) {
            var7_6[var8_7] = new DictionaryBreakEngine.PossibleWord();
        }
        var1_1.setIndex(var2_2);
        while ((var9_9 = var1_1.getIndex()) < var3_3) {
            block26: {
                var6_8 = 0;
                var10_10 = var7_6[var5_5 % 3].candidates(var1_1, this.fDictionary, var3_3);
                if (var10_10 == 1) {
                    var6_8 = var7_6[var5_5 % 3].acceptMarked(var1_1);
                    ++var5_5;
                } else if (var10_10 > 1) {
                    if (var1_1.getIndex() < var3_3) {
                        block2: do {
                            var11_11 = 1;
                            if (var7_6[(var5_5 + 1) % 3].candidates(var1_1, this.fDictionary, var3_3) <= 0) continue;
                            if (var11_11 < 2) {
                                var7_6[var5_5 % 3].markCurrent();
                                var11_11 = 2;
                            }
                            if (var1_1.getIndex() >= var3_3) break;
                            do {
                                if (var7_6[(var5_5 + 2) % 3].candidates(var1_1, this.fDictionary, var3_3) <= 0) continue;
                                var7_6[var5_5 % 3].markCurrent();
                                break block2;
                            } while (var7_6[(var5_5 + 1) % 3].backUp(var1_1));
                        } while (var7_6[var5_5 % 3].backUp(var1_1));
                    }
                    var6_8 = var7_6[var5_5 % 3].acceptMarked(var1_1);
                    ++var5_5;
                }
                if (var1_1.getIndex() < var3_3 && var6_8 < 3) {
                    if (var7_6[var5_5 % 3].candidates(var1_1, this.fDictionary, var3_3) <= 0 && (var6_8 == 0 || var7_6[var5_5 % 3].longestPrefix() < 3)) {
                        var11_11 = var3_3 - (var9_9 + var6_8);
                        var12_12 = var1_1.current();
                        var13_13 = 0;
                        while (true) {
                            var1_1.next();
                            var8_7 = var1_1.current();
                            ++var13_13;
                            if (--var11_11 <= 0) break;
                            if (ThaiBreakEngine.fEndWordSet.contains(var12_12) && ThaiBreakEngine.fBeginWordSet.contains(var8_7)) {
                                var14_14 = var7_6[(var5_5 + 1) % 3].candidates(var1_1, this.fDictionary, var3_3);
                                var1_1.setIndex(var9_9 + var6_8 + var13_13);
                                if (var14_14 > 0) break;
                            }
                            var12_12 = var8_7;
                        }
                        if (var6_8 <= 0) {
                            ++var5_5;
                        }
                        var6_8 += var13_13;
                    } else {
                        var1_1.setIndex(var9_9 + var6_8);
                    }
                }
                while ((var11_11 = var1_1.getIndex()) < var3_3 && ThaiBreakEngine.fMarkSet.contains(var1_1.current())) {
                    var1_1.next();
                    var6_8 += var1_1.getIndex() - var11_11;
                }
                if (var1_1.getIndex() >= var3_3 || var6_8 <= 0) break block26;
                if (var7_6[var5_5 % 3].candidates(var1_1, this.fDictionary, var3_3) > 0) ** GOTO lbl-1000
                v0 = var1_1.current();
                var8_7 = v0;
                if (ThaiBreakEngine.fSuffixSet.contains(v0)) {
                    if (var8_7 == 3631) {
                        if (!ThaiBreakEngine.fSuffixSet.contains(var1_1.previous())) {
                            var1_1.next();
                            var1_1.next();
                            ++var6_8;
                            var8_7 = var1_1.current();
                        } else {
                            var1_1.next();
                        }
                    }
                    if (var8_7 == 3654) {
                        if (var1_1.previous() != '\u0e46') {
                            var1_1.next();
                            var1_1.next();
                            ++var6_8;
                        } else {
                            var1_1.next();
                        }
                    }
                } else lbl-1000:
                // 2 sources

                {
                    var1_1.setIndex(var9_9 + var6_8);
                }
            }
            if (var6_8 <= 0) continue;
            var4_4.push(var9_9 + var6_8);
        }
        if (var4_4.peek() >= var3_3) {
            var4_4.pop();
            --var5_5;
        }
        return var5_5;
    }

    static {
        fMarkSet = new UnicodeSet();
        fBeginWordSet = new UnicodeSet();
        fSuffixSet = new UnicodeSet();
        fThaiWordSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]]");
        fThaiWordSet.compact();
        fMarkSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet = new UnicodeSet(fThaiWordSet);
        fEndWordSet.remove(3633);
        fEndWordSet.remove(3648, 3652);
        fBeginWordSet.add(3585, 3630);
        fBeginWordSet.add(3648, 3652);
        fSuffixSet.add(3631);
        fSuffixSet.add(3654);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fSuffixSet.compact();
        fThaiWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
        fSuffixSet.freeze();
    }
}

