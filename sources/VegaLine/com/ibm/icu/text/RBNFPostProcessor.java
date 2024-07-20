/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.RuleBasedNumberFormat;

interface RBNFPostProcessor {
    public void init(RuleBasedNumberFormat var1, String var2);

    public void process(StringBuffer var1, NFRuleSet var2);
}

