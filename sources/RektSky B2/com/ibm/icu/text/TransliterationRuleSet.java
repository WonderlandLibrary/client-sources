package com.ibm.icu.text;

import java.util.*;

class TransliterationRuleSet
{
    private List<TransliterationRule> ruleVector;
    private int maxContextLength;
    private TransliterationRule[] rules;
    private int[] index;
    
    public TransliterationRuleSet() {
        this.ruleVector = new ArrayList<TransliterationRule>();
        this.maxContextLength = 0;
    }
    
    public int getMaximumContextLength() {
        return this.maxContextLength;
    }
    
    public void addRule(final TransliterationRule rule) {
        this.ruleVector.add(rule);
        final int len;
        if ((len = rule.getAnteContextLength()) > this.maxContextLength) {
            this.maxContextLength = len;
        }
        this.rules = null;
    }
    
    public void freeze() {
        final int n = this.ruleVector.size();
        this.index = new int[257];
        final List<TransliterationRule> v = new ArrayList<TransliterationRule>(2 * n);
        final int[] indexValue = new int[n];
        for (int j = 0; j < n; ++j) {
            final TransliterationRule r = this.ruleVector.get(j);
            indexValue[j] = r.getIndexValue();
        }
        for (int x = 0; x < 256; ++x) {
            this.index[x] = v.size();
            for (int i = 0; i < n; ++i) {
                if (indexValue[i] >= 0) {
                    if (indexValue[i] == x) {
                        v.add(this.ruleVector.get(i));
                    }
                }
                else {
                    final TransliterationRule r2 = this.ruleVector.get(i);
                    if (r2.matchesIndexValue(x)) {
                        v.add(r2);
                    }
                }
            }
        }
        this.index[256] = v.size();
        v.toArray(this.rules = new TransliterationRule[v.size()]);
        StringBuilder errors = null;
        for (int x2 = 0; x2 < 256; ++x2) {
            for (int k = this.index[x2]; k < this.index[x2 + 1] - 1; ++k) {
                final TransliterationRule r3 = this.rules[k];
                for (int l = k + 1; l < this.index[x2 + 1]; ++l) {
                    final TransliterationRule r4 = this.rules[l];
                    if (r3.masks(r4)) {
                        if (errors == null) {
                            errors = new StringBuilder();
                        }
                        else {
                            errors.append("\n");
                        }
                        errors.append("Rule " + r3 + " masks " + r4);
                    }
                }
            }
        }
        if (errors != null) {
            throw new IllegalArgumentException(errors.toString());
        }
    }
    
    public boolean transliterate(final Replaceable text, final Transliterator.Position pos, final boolean incremental) {
        final int indexByte = text.char32At(pos.start) & 0xFF;
        int i = this.index[indexByte];
        while (i < this.index[indexByte + 1]) {
            final int m = this.rules[i].matchAndReplace(text, pos, incremental);
            switch (m) {
                case 2: {
                    return true;
                }
                case 1: {
                    return false;
                }
                default: {
                    ++i;
                    continue;
                }
            }
        }
        pos.start += UTF16.getCharCount(text.char32At(pos.start));
        return true;
    }
    
    String toRules(final boolean escapeUnprintable) {
        final int count = this.ruleVector.size();
        final StringBuilder ruleSource = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            if (i != 0) {
                ruleSource.append('\n');
            }
            final TransliterationRule r = this.ruleVector.get(i);
            ruleSource.append(r.toRule(escapeUnprintable));
        }
        return ruleSource.toString();
    }
    
    void addSourceTargetSet(final UnicodeSet filter, final UnicodeSet sourceSet, final UnicodeSet targetSet) {
        final UnicodeSet currentFilter = new UnicodeSet(filter);
        final UnicodeSet revisiting = new UnicodeSet();
        for (int count = this.ruleVector.size(), i = 0; i < count; ++i) {
            final TransliterationRule r = this.ruleVector.get(i);
            r.addSourceTargetSet(currentFilter, sourceSet, targetSet, revisiting.clear());
            currentFilter.addAll(revisiting);
        }
    }
}
