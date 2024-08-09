/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UnicodeReplacer;
import com.ibm.icu.text.UnicodeSet;

class FunctionReplacer
implements UnicodeReplacer {
    private Transliterator translit;
    private UnicodeReplacer replacer;

    public FunctionReplacer(Transliterator transliterator, UnicodeReplacer unicodeReplacer) {
        this.translit = transliterator;
        this.replacer = unicodeReplacer;
    }

    @Override
    public int replace(Replaceable replaceable, int n, int n2, int[] nArray) {
        int n3 = this.replacer.replace(replaceable, n, n2, nArray);
        n2 = n + n3;
        n2 = this.translit.transliterate(replaceable, n, n2);
        return n2 - n;
    }

    @Override
    public String toReplacerPattern(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("&");
        stringBuilder.append(this.translit.getID());
        stringBuilder.append("( ");
        stringBuilder.append(this.replacer.toReplacerPattern(bl));
        stringBuilder.append(" )");
        return stringBuilder.toString();
    }

    @Override
    public void addReplacementSetTo(UnicodeSet unicodeSet) {
        unicodeSet.addAll(this.translit.getTargetSet());
    }
}

