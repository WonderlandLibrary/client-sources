/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeMatcher;

public abstract class UnicodeFilter
implements UnicodeMatcher {
    public abstract boolean contains(int var1);

    @Override
    public int matches(Replaceable replaceable, int[] nArray, int n, boolean bl) {
        int n2;
        if (nArray[0] < n && this.contains(n2 = replaceable.char32At(nArray[0]))) {
            nArray[0] = nArray[0] + UTF16.getCharCount(n2);
            return 1;
        }
        if (nArray[0] > n && this.contains(replaceable.char32At(nArray[0]))) {
            nArray[0] = nArray[0] - 1;
            if (nArray[0] >= 0) {
                nArray[0] = nArray[0] - (UTF16.getCharCount(replaceable.char32At(nArray[0])) - 1);
            }
            return 1;
        }
        if (bl && nArray[0] == n) {
            return 0;
        }
        return 1;
    }

    @Deprecated
    protected UnicodeFilter() {
    }
}

