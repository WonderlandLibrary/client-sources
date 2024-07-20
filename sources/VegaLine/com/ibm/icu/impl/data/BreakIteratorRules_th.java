/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.data;

import com.ibm.icu.impl.ICUData;
import java.util.ListResourceBundle;

public class BreakIteratorRules_th
extends ListResourceBundle {
    private static final String DATA_NAME = "data/th.brk";

    public Object[][] getContents() {
        boolean exists = ICUData.exists(DATA_NAME);
        if (!exists) {
            return new Object[0][0];
        }
        return new Object[][]{{"BreakIteratorClasses", new String[]{"RuleBasedBreakIterator", "DictionaryBasedBreakIterator", "DictionaryBasedBreakIterator", "RuleBasedBreakIterator"}}, {"WordBreakDictionary", DATA_NAME}, {"LineBreakDictionary", DATA_NAME}};
    }
}

