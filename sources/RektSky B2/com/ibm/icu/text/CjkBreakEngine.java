package com.ibm.icu.text;

import java.io.*;
import java.text.*;
import com.ibm.icu.impl.*;

class CjkBreakEngine extends DictionaryBreakEngine
{
    private static final UnicodeSet fHangulWordSet;
    private static final UnicodeSet fHanWordSet;
    private static final UnicodeSet fKatakanaWordSet;
    private static final UnicodeSet fHiraganaWordSet;
    private DictionaryMatcher fDictionary;
    private static final int kMaxKatakanaLength = 8;
    private static final int kMaxKatakanaGroupLength = 20;
    private static final int maxSnlp = 255;
    private static final int kint32max = Integer.MAX_VALUE;
    
    public CjkBreakEngine(final boolean korean) throws IOException {
        this.fDictionary = null;
        this.fDictionary = DictionaryData.loadDictionaryFor("Hira");
        if (korean) {
            this.setCharacters(CjkBreakEngine.fHangulWordSet);
        }
        else {
            final UnicodeSet cjSet = new UnicodeSet();
            cjSet.addAll(CjkBreakEngine.fHanWordSet);
            cjSet.addAll(CjkBreakEngine.fKatakanaWordSet);
            cjSet.addAll(CjkBreakEngine.fHiraganaWordSet);
            cjSet.add(65392);
            cjSet.add(12540);
            this.setCharacters(cjSet);
        }
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CjkBreakEngine) {
            final CjkBreakEngine other = (CjkBreakEngine)obj;
            return this.fSet.equals(other.fSet);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
    
    private static int getKatakanaCost(final int wordlength) {
        final int[] katakanaCost = { 8192, 984, 408, 240, 204, 252, 300, 372, 480 };
        return (wordlength > 8) ? 8192 : katakanaCost[wordlength];
    }
    
    private static boolean isKatakana(final int value) {
        return (value >= 12449 && value <= 12542 && value != 12539) || (value >= 65382 && value <= 65439);
    }
    
    public int divideUpDictionaryRange(final CharacterIterator inText, final int startPos, final int endPos, final DequeI foundBreaks) {
        if (startPos >= endPos) {
            return 0;
        }
        inText.setIndex(startPos);
        final int inputLength = endPos - startPos;
        int[] charPositions = new int[inputLength + 1];
        final StringBuffer s = new StringBuffer("");
        inText.setIndex(startPos);
        while (inText.getIndex() < endPos) {
            s.append(inText.current());
            inText.next();
        }
        final String prenormstr = s.toString();
        final boolean isNormalized = Normalizer.quickCheck(prenormstr, Normalizer.NFKC) == Normalizer.YES || Normalizer.isNormalized(prenormstr, Normalizer.NFKC, 0);
        int numCodePts = 0;
        CharacterIterator text;
        if (isNormalized) {
            text = new StringCharacterIterator(prenormstr);
            int index = 0;
            charPositions[0] = 0;
            while (index < prenormstr.length()) {
                final int codepoint = prenormstr.codePointAt(index);
                index += Character.charCount(codepoint);
                ++numCodePts;
                charPositions[numCodePts] = index;
            }
        }
        else {
            final String normStr = Normalizer.normalize(prenormstr, Normalizer.NFKC);
            text = new StringCharacterIterator(normStr);
            charPositions = new int[normStr.length() + 1];
            final Normalizer normalizer = new Normalizer(prenormstr, Normalizer.NFKC, 0);
            int index2 = 0;
            charPositions[0] = 0;
            while (index2 < normalizer.endIndex()) {
                normalizer.next();
                ++numCodePts;
                index2 = normalizer.getIndex();
                charPositions[numCodePts] = index2;
            }
        }
        final int[] bestSnlp = new int[numCodePts + 1];
        bestSnlp[0] = 0;
        for (int i = 1; i <= numCodePts; ++i) {
            bestSnlp[i] = Integer.MAX_VALUE;
        }
        final int[] prev = new int[numCodePts + 1];
        for (int j = 0; j <= numCodePts; ++j) {
            prev[j] = -1;
        }
        final int maxWordSize = 20;
        final int[] values = new int[numCodePts];
        final int[] lengths = new int[numCodePts];
        int ix = 0;
        text.setIndex(ix);
        boolean is_prev_katakana = false;
        int k = 0;
        while (k < numCodePts) {
            ix = text.getIndex();
            if (bestSnlp[k] != Integer.MAX_VALUE) {
                final int maxSearchLength = (k + 20 < numCodePts) ? 20 : (numCodePts - k);
                final int[] count_ = { 0 };
                this.fDictionary.matches(text, maxSearchLength, lengths, count_, maxSearchLength, values);
                int count = count_[0];
                text.setIndex(ix);
                if ((count == 0 || lengths[0] != 1) && CharacterIteration.current32(text) != Integer.MAX_VALUE && !CjkBreakEngine.fHangulWordSet.contains(CharacterIteration.current32(text))) {
                    values[count] = 255;
                    lengths[count] = 1;
                    ++count;
                }
                for (int l = 0; l < count; ++l) {
                    final int newSnlp = bestSnlp[k] + values[l];
                    if (newSnlp < bestSnlp[lengths[l] + k]) {
                        bestSnlp[lengths[l] + k] = newSnlp;
                        prev[lengths[l] + k] = k;
                    }
                }
                final boolean is_katakana = isKatakana(CharacterIteration.current32(text));
                if (!is_prev_katakana && is_katakana) {
                    int m = k + 1;
                    CharacterIteration.next32(text);
                    while (m < numCodePts && m - k < 20 && isKatakana(CharacterIteration.current32(text))) {
                        CharacterIteration.next32(text);
                        ++m;
                    }
                    if (m - k < 20) {
                        final int newSnlp2 = bestSnlp[k] + getKatakanaCost(m - k);
                        if (newSnlp2 < bestSnlp[m]) {
                            bestSnlp[m] = newSnlp2;
                            prev[m] = k;
                        }
                    }
                }
                is_prev_katakana = is_katakana;
            }
            ++k;
            text.setIndex(ix);
            CharacterIteration.next32(text);
        }
        final int[] t_boundary = new int[numCodePts + 1];
        int numBreaks = 0;
        if (bestSnlp[numCodePts] == Integer.MAX_VALUE) {
            t_boundary[numBreaks] = numCodePts;
            ++numBreaks;
        }
        else {
            for (int i2 = numCodePts; i2 > 0; i2 = prev[i2]) {
                t_boundary[numBreaks] = i2;
                ++numBreaks;
            }
            Assert.assrt(prev[t_boundary[numBreaks - 1]] == 0);
        }
        if (foundBreaks.size() == 0 || foundBreaks.peek() < startPos) {
            t_boundary[numBreaks++] = 0;
        }
        int correctedNumBreaks = 0;
        for (int i3 = numBreaks - 1; i3 >= 0; --i3) {
            final int pos = charPositions[t_boundary[i3]] + startPos;
            if (!foundBreaks.contains(pos) && pos != startPos) {
                foundBreaks.push(charPositions[t_boundary[i3]] + startPos);
                ++correctedNumBreaks;
            }
        }
        if (!foundBreaks.isEmpty() && foundBreaks.peek() == endPos) {
            foundBreaks.pop();
            --correctedNumBreaks;
        }
        if (!foundBreaks.isEmpty()) {
            inText.setIndex(foundBreaks.peek());
        }
        return correctedNumBreaks;
    }
    
    static {
        fHangulWordSet = new UnicodeSet();
        fHanWordSet = new UnicodeSet();
        fKatakanaWordSet = new UnicodeSet();
        fHiraganaWordSet = new UnicodeSet();
        CjkBreakEngine.fHangulWordSet.applyPattern("[\\uac00-\\ud7a3]");
        CjkBreakEngine.fHanWordSet.applyPattern("[:Han:]");
        CjkBreakEngine.fKatakanaWordSet.applyPattern("[[:Katakana:]\\uff9e\\uff9f]");
        CjkBreakEngine.fHiraganaWordSet.applyPattern("[:Hiragana:]");
        CjkBreakEngine.fHangulWordSet.freeze();
        CjkBreakEngine.fHanWordSet.freeze();
        CjkBreakEngine.fKatakanaWordSet.freeze();
        CjkBreakEngine.fHiraganaWordSet.freeze();
    }
}
