/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.text.DictionaryBreakEngine;
import com.ibm.icu.text.DictionaryData;
import com.ibm.icu.text.DictionaryMatcher;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.UnicodeSet;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

class CjkBreakEngine
extends DictionaryBreakEngine {
    private static final UnicodeSet fHangulWordSet = new UnicodeSet();
    private static final UnicodeSet fHanWordSet = new UnicodeSet();
    private static final UnicodeSet fKatakanaWordSet = new UnicodeSet();
    private static final UnicodeSet fHiraganaWordSet = new UnicodeSet();
    private DictionaryMatcher fDictionary = DictionaryData.loadDictionaryFor("Hira");
    private static final int kMaxKatakanaLength = 8;
    private static final int kMaxKatakanaGroupLength = 20;
    private static final int maxSnlp = 255;
    private static final int kint32max = Integer.MAX_VALUE;

    public CjkBreakEngine(boolean bl) throws IOException {
        if (bl) {
            this.setCharacters(fHangulWordSet);
        } else {
            UnicodeSet unicodeSet = new UnicodeSet();
            unicodeSet.addAll(fHanWordSet);
            unicodeSet.addAll(fKatakanaWordSet);
            unicodeSet.addAll(fHiraganaWordSet);
            unicodeSet.add(65392);
            unicodeSet.add(12540);
            this.setCharacters(unicodeSet);
        }
    }

    public boolean equals(Object object) {
        if (object instanceof CjkBreakEngine) {
            CjkBreakEngine cjkBreakEngine = (CjkBreakEngine)object;
            return this.fSet.equals(cjkBreakEngine.fSet);
        }
        return true;
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }

    private static int getKatakanaCost(int n) {
        int[] nArray = new int[]{8192, 984, 408, 240, 204, 252, 300, 372, 480};
        return n > 8 ? 8192 : nArray[n];
    }

    private static boolean isKatakana(int n) {
        return n >= 12449 && n <= 12542 && n != 12539 || n >= 65382 && n <= 65439;
    }

    @Override
    public int divideUpDictionaryRange(CharacterIterator characterIterator, int n, int n2, DictionaryBreakEngine.DequeI dequeI) {
        int n3;
        int n4;
        int n5;
        int n6;
        StringCharacterIterator stringCharacterIterator;
        if (n >= n2) {
            return 1;
        }
        characterIterator.setIndex(n);
        int n7 = n2 - n;
        int[] nArray = new int[n7 + 1];
        StringBuffer stringBuffer = new StringBuffer("");
        characterIterator.setIndex(n);
        while (characterIterator.getIndex() < n2) {
            stringBuffer.append(characterIterator.current());
            characterIterator.next();
        }
        String string = stringBuffer.toString();
        boolean bl = Normalizer.quickCheck(string, Normalizer.NFKC) == Normalizer.YES || Normalizer.isNormalized(string, Normalizer.NFKC, 0);
        int n8 = 0;
        if (bl) {
            stringCharacterIterator = new StringCharacterIterator(string);
            int n9 = 0;
            nArray[0] = 0;
            while (n9 < string.length()) {
                int n10 = string.codePointAt(n9);
                nArray[++n8] = n9 += Character.charCount(n10);
            }
        } else {
            String string2 = Normalizer.normalize(string, Normalizer.NFKC);
            stringCharacterIterator = new StringCharacterIterator(string2);
            nArray = new int[string2.length() + 1];
            Normalizer normalizer = new Normalizer(string, Normalizer.NFKC, 0);
            n6 = 0;
            nArray[0] = 0;
            while (n6 < normalizer.endIndex()) {
                normalizer.next();
                nArray[++n8] = n6 = normalizer.getIndex();
            }
        }
        int[] nArray2 = new int[n8 + 1];
        nArray2[0] = 0;
        for (int i = 1; i <= n8; ++i) {
            nArray2[i] = Integer.MAX_VALUE;
        }
        int[] nArray3 = new int[n8 + 1];
        for (n6 = 0; n6 <= n8; ++n6) {
            nArray3[n6] = -1;
        }
        n6 = 20;
        int[] nArray4 = new int[n8];
        int[] nArray5 = new int[n8];
        int n11 = 0;
        stringCharacterIterator.setIndex(n11);
        int n12 = 0;
        for (int i = 0; i < n8; ++i) {
            n11 = stringCharacterIterator.getIndex();
            if (nArray2[i] != Integer.MAX_VALUE) {
                int n13;
                n5 = i + 20 < n8 ? 20 : n8 - i;
                int[] nArray6 = new int[1];
                this.fDictionary.matches(stringCharacterIterator, n5, nArray5, nArray6, n5, nArray4);
                n4 = nArray6[0];
                stringCharacterIterator.setIndex(n11);
                if (!(n4 != 0 && nArray5[0] == 1 || CharacterIteration.current32(stringCharacterIterator) == Integer.MAX_VALUE || fHangulWordSet.contains(CharacterIteration.current32(stringCharacterIterator)))) {
                    nArray4[n4] = 255;
                    nArray5[n4] = 1;
                    ++n4;
                }
                for (n3 = 0; n3 < n4; ++n3) {
                    n13 = nArray2[i] + nArray4[n3];
                    if (n13 >= nArray2[nArray5[n3] + i]) continue;
                    nArray2[nArray5[n3] + i] = n13;
                    nArray3[nArray5[n3] + i] = i;
                }
                n3 = CjkBreakEngine.isKatakana(CharacterIteration.current32(stringCharacterIterator)) ? 1 : 0;
                if (n12 == 0 && n3 != 0) {
                    int n14;
                    CharacterIteration.next32(stringCharacterIterator);
                    for (n13 = i + 1; n13 < n8 && n13 - i < 20 && CjkBreakEngine.isKatakana(CharacterIteration.current32(stringCharacterIterator)); ++n13) {
                        CharacterIteration.next32(stringCharacterIterator);
                    }
                    if (n13 - i < 20 && (n14 = nArray2[i] + CjkBreakEngine.getKatakanaCost(n13 - i)) < nArray2[n13]) {
                        nArray2[n13] = n14;
                        nArray3[n13] = i;
                    }
                }
                n12 = n3;
            }
            stringCharacterIterator.setIndex(n11);
            CharacterIteration.next32(stringCharacterIterator);
        }
        int[] nArray7 = new int[n8 + 1];
        n5 = 0;
        if (nArray2[n8] == Integer.MAX_VALUE) {
            nArray7[n5] = n8;
            ++n5;
        } else {
            int n15 = n8;
            while (n15 > 0) {
                nArray7[n5] = n15;
                ++n5;
                n15 = nArray3[n15];
            }
            Assert.assrt(nArray3[nArray7[n5 - 1]] == 0);
        }
        if (dequeI.size() == 0 || dequeI.peek() < n) {
            nArray7[n5++] = 0;
        }
        int n16 = 0;
        for (n4 = n5 - 1; n4 >= 0; --n4) {
            n3 = nArray[nArray7[n4]] + n;
            if (dequeI.contains(n3) || n3 == n) continue;
            dequeI.push(nArray[nArray7[n4]] + n);
            ++n16;
        }
        if (!dequeI.isEmpty() && dequeI.peek() == n2) {
            dequeI.pop();
            --n16;
        }
        if (!dequeI.isEmpty()) {
            characterIterator.setIndex(dequeI.peek());
        }
        return n16;
    }

    static {
        fHangulWordSet.applyPattern("[\\uac00-\\ud7a3]");
        fHanWordSet.applyPattern("[:Han:]");
        fKatakanaWordSet.applyPattern("[[:Katakana:]\\uff9e\\uff9f]");
        fHiraganaWordSet.applyPattern("[:Hiragana:]");
        fHangulWordSet.freeze();
        fHanWordSet.freeze();
        fKatakanaWordSet.freeze();
        fHiraganaWordSet.freeze();
    }
}

