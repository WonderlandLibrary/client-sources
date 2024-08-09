/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.TransliterationRuleSet;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UnicodeFilter;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.text.UnicodeReplacer;
import com.ibm.icu.text.UnicodeSet;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class RuleBasedTransliterator
extends Transliterator {
    private final Data data;

    RuleBasedTransliterator(String string, Data data, UnicodeFilter unicodeFilter) {
        super(string, unicodeFilter);
        this.data = data;
        this.setMaximumContextLength(data.ruleSet.getMaximumContextLength());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Deprecated
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        Data data = this.data;
        synchronized (data) {
            int n = 0;
            int n2 = position.limit - position.start << 4;
            if (n2 < 0) {
                n2 = Integer.MAX_VALUE;
            }
            while (position.start < position.limit && n <= n2 && this.data.ruleSet.transliterate(replaceable, position, bl)) {
                ++n;
            }
        }
    }

    @Override
    @Deprecated
    public String toRules(boolean bl) {
        return this.data.ruleSet.toRules(bl);
    }

    @Override
    @Deprecated
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        this.data.ruleSet.addSourceTargetSet(unicodeSet, unicodeSet2, unicodeSet3);
    }

    @Deprecated
    public Transliterator safeClone() {
        UnicodeFilter unicodeFilter = this.getFilter();
        if (unicodeFilter != null && unicodeFilter instanceof UnicodeSet) {
            unicodeFilter = new UnicodeSet((UnicodeSet)unicodeFilter);
        }
        return new RuleBasedTransliterator(this.getID(), this.data, unicodeFilter);
    }

    static class Data {
        public TransliterationRuleSet ruleSet;
        Map<String, char[]> variableNames = new HashMap<String, char[]>();
        Object[] variables;
        char variablesBase;

        public Data() {
            this.ruleSet = new TransliterationRuleSet();
        }

        public UnicodeMatcher lookupMatcher(int n) {
            int n2 = n - this.variablesBase;
            return n2 >= 0 && n2 < this.variables.length ? (UnicodeMatcher)this.variables[n2] : null;
        }

        public UnicodeReplacer lookupReplacer(int n) {
            int n2 = n - this.variablesBase;
            return n2 >= 0 && n2 < this.variables.length ? (UnicodeReplacer)this.variables[n2] : null;
        }
    }
}

