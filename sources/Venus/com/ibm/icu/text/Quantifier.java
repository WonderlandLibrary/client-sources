/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.text.UnicodeSet;

class Quantifier
implements UnicodeMatcher {
    private UnicodeMatcher matcher;
    private int minCount;
    private int maxCount;
    public static final int MAX = Integer.MAX_VALUE;

    public Quantifier(UnicodeMatcher unicodeMatcher, int n, int n2) {
        if (unicodeMatcher == null || n < 0 || n2 < 0 || n > n2) {
            throw new IllegalArgumentException();
        }
        this.matcher = unicodeMatcher;
        this.minCount = n;
        this.maxCount = n2;
    }

    @Override
    public int matches(Replaceable replaceable, int[] nArray, int n, boolean bl) {
        int n2;
        int n3 = nArray[0];
        for (n2 = 0; n2 < this.maxCount; ++n2) {
            int n4 = nArray[0];
            int n5 = this.matcher.matches(replaceable, nArray, n, bl);
            if (n5 == 2) {
                if (n4 != nArray[0]) continue;
                break;
            }
            if (!bl || n5 != 1) break;
            return 0;
        }
        if (bl && nArray[0] == n) {
            return 0;
        }
        if (n2 >= this.minCount) {
            return 1;
        }
        nArray[0] = n3;
        return 1;
    }

    @Override
    public String toPattern(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.matcher.toPattern(bl));
        if (this.minCount == 0) {
            if (this.maxCount == 1) {
                return stringBuilder.append('?').toString();
            }
            if (this.maxCount == Integer.MAX_VALUE) {
                return stringBuilder.append('*').toString();
            }
        } else if (this.minCount == 1 && this.maxCount == Integer.MAX_VALUE) {
            return stringBuilder.append('+').toString();
        }
        stringBuilder.append('{');
        stringBuilder.append(Utility.hex(this.minCount, 1));
        stringBuilder.append(',');
        if (this.maxCount != Integer.MAX_VALUE) {
            stringBuilder.append(Utility.hex(this.maxCount, 1));
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public boolean matchesIndexValue(int n) {
        return this.minCount == 0 || this.matcher.matchesIndexValue(n);
    }

    @Override
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        if (this.maxCount > 0) {
            this.matcher.addMatchSetTo(unicodeSet);
        }
    }
}

