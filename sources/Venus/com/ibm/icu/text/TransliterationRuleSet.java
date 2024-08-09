/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.TransliterationRule;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import java.util.ArrayList;
import java.util.List;

class TransliterationRuleSet {
    private List<TransliterationRule> ruleVector = new ArrayList<TransliterationRule>();
    private int maxContextLength = 0;
    private TransliterationRule[] rules;
    private int[] index;

    public int getMaximumContextLength() {
        return this.maxContextLength;
    }

    public void addRule(TransliterationRule transliterationRule) {
        this.ruleVector.add(transliterationRule);
        int n = transliterationRule.getAnteContextLength();
        if (n > this.maxContextLength) {
            this.maxContextLength = n;
        }
        this.rules = null;
    }

    public void freeze() {
        int n;
        int n2 = this.ruleVector.size();
        this.index = new int[257];
        ArrayList<TransliterationRule> arrayList = new ArrayList<TransliterationRule>(2 * n2);
        int[] nArray = new int[n2];
        for (n = 0; n < n2; ++n) {
            TransliterationRule transliterationRule = this.ruleVector.get(n);
            nArray[n] = transliterationRule.getIndexValue();
        }
        for (n = 0; n < 256; ++n) {
            this.index[n] = arrayList.size();
            for (int i = 0; i < n2; ++i) {
                if (nArray[i] >= 0) {
                    if (nArray[i] != n) continue;
                    arrayList.add(this.ruleVector.get(i));
                    continue;
                }
                TransliterationRule transliterationRule = this.ruleVector.get(i);
                if (!transliterationRule.matchesIndexValue(n)) continue;
                arrayList.add(transliterationRule);
            }
        }
        this.index[256] = arrayList.size();
        this.rules = new TransliterationRule[arrayList.size()];
        arrayList.toArray(this.rules);
        StringBuilder stringBuilder = null;
        for (int i = 0; i < 256; ++i) {
            for (int j = this.index[i]; j < this.index[i + 1] - 1; ++j) {
                TransliterationRule transliterationRule = this.rules[j];
                for (int k = j + 1; k < this.index[i + 1]; ++k) {
                    TransliterationRule transliterationRule2 = this.rules[k];
                    if (!transliterationRule.masks(transliterationRule2)) continue;
                    if (stringBuilder == null) {
                        stringBuilder = new StringBuilder();
                    } else {
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append("Rule " + transliterationRule + " masks " + transliterationRule2);
                }
            }
        }
        if (stringBuilder != null) {
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public boolean transliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = replaceable.char32At(position.start) & 0xFF;
        for (int i = this.index[n]; i < this.index[n + 1]; ++i) {
            int n2 = this.rules[i].matchAndReplace(replaceable, position, bl);
            switch (n2) {
                case 2: {
                    return false;
                }
                case 1: {
                    return true;
                }
            }
        }
        position.start += UTF16.getCharCount(replaceable.char32At(position.start));
        return false;
    }

    String toRules(boolean bl) {
        int n = this.ruleVector.size();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append('\n');
            }
            TransliterationRule transliterationRule = this.ruleVector.get(i);
            stringBuilder.append(transliterationRule.toRule(bl));
        }
        return stringBuilder.toString();
    }

    void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = new UnicodeSet(unicodeSet);
        UnicodeSet unicodeSet5 = new UnicodeSet();
        int n = this.ruleVector.size();
        for (int i = 0; i < n; ++i) {
            TransliterationRule transliterationRule = this.ruleVector.get(i);
            transliterationRule.addSourceTargetSet(unicodeSet4, unicodeSet2, unicodeSet3, unicodeSet5.clear());
            unicodeSet4.addAll(unicodeSet5);
        }
    }
}

