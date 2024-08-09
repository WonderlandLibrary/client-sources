/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.OutputInt;
import java.util.ArrayList;

public class UnicodeSetStringSpan {
    public static final int WITH_COUNT = 64;
    public static final int FWD = 32;
    public static final int BACK = 16;
    public static final int CONTAINED = 2;
    public static final int NOT_CONTAINED = 1;
    public static final int ALL = 127;
    public static final int FWD_UTF16_CONTAINED = 34;
    public static final int FWD_UTF16_NOT_CONTAINED = 33;
    public static final int BACK_UTF16_CONTAINED = 18;
    public static final int BACK_UTF16_NOT_CONTAINED = 17;
    static final short ALL_CP_CONTAINED = 255;
    static final short LONG_SPAN = 254;
    private UnicodeSet spanSet;
    private UnicodeSet spanNotSet;
    private ArrayList<String> strings;
    private short[] spanLengths;
    private final int maxLength16;
    private boolean someRelevant;
    private boolean all;
    private OffsetList offsets;

    public UnicodeSetStringSpan(UnicodeSet unicodeSet, ArrayList<String> arrayList, int n) {
        int n2;
        int n3;
        int n4;
        this.spanSet = new UnicodeSet(0, 0x10FFFF);
        this.strings = arrayList;
        this.all = n == 127;
        this.spanSet.retainAll(unicodeSet);
        if (0 != (n & 1)) {
            this.spanNotSet = this.spanSet;
        }
        this.offsets = new OffsetList();
        int n5 = this.strings.size();
        int n6 = 0;
        this.someRelevant = false;
        for (n4 = 0; n4 < n5; ++n4) {
            String string = this.strings.get(n4);
            n3 = string.length();
            n2 = this.spanSet.span(string, UnicodeSet.SpanCondition.CONTAINED);
            if (n2 < n3) {
                this.someRelevant = true;
            }
            if (n3 <= n6) continue;
            n6 = n3;
        }
        this.maxLength16 = n6;
        if (!this.someRelevant && (n & 0x40) == 0) {
            return;
        }
        if (this.all) {
            this.spanSet.freeze();
        }
        n3 = this.all ? n5 * 2 : n5;
        this.spanLengths = new short[n3];
        int n7 = this.all ? n5 : 0;
        for (n4 = 0; n4 < n5; ++n4) {
            String string = this.strings.get(n4);
            int n8 = string.length();
            n2 = this.spanSet.span(string, UnicodeSet.SpanCondition.CONTAINED);
            if (n2 < n8) {
                int n9;
                if (0 != (n & 2)) {
                    if (0 != (n & 0x20)) {
                        this.spanLengths[n4] = UnicodeSetStringSpan.makeSpanLengthByte(n2);
                    }
                    if (0 != (n & 0x10)) {
                        n2 = n8 - this.spanSet.spanBack(string, n8, UnicodeSet.SpanCondition.CONTAINED);
                        this.spanLengths[n7 + n4] = UnicodeSetStringSpan.makeSpanLengthByte(n2);
                    }
                } else {
                    this.spanLengths[n7 + n4] = 0;
                    this.spanLengths[n4] = 0;
                }
                if (0 == (n & 1)) continue;
                if (0 != (n & 0x20)) {
                    n9 = string.codePointAt(0);
                    this.addToSpanNotSet(n9);
                }
                if (0 == (n & 0x10)) continue;
                n9 = string.codePointBefore(n8);
                this.addToSpanNotSet(n9);
                continue;
            }
            if (this.all) {
                this.spanLengths[n7 + n4] = 255;
                this.spanLengths[n4] = 255;
                continue;
            }
            this.spanLengths[n4] = 255;
        }
        if (this.all) {
            this.spanNotSet.freeze();
        }
    }

    public UnicodeSetStringSpan(UnicodeSetStringSpan unicodeSetStringSpan, ArrayList<String> arrayList) {
        this.spanSet = unicodeSetStringSpan.spanSet;
        this.strings = arrayList;
        this.maxLength16 = unicodeSetStringSpan.maxLength16;
        this.someRelevant = unicodeSetStringSpan.someRelevant;
        this.all = true;
        this.spanNotSet = Utility.sameObjects(unicodeSetStringSpan.spanNotSet, unicodeSetStringSpan.spanSet) ? this.spanSet : (UnicodeSet)unicodeSetStringSpan.spanNotSet.clone();
        this.offsets = new OffsetList();
        this.spanLengths = (short[])unicodeSetStringSpan.spanLengths.clone();
    }

    public boolean needsStringSpanUTF16() {
        return this.someRelevant;
    }

    public boolean contains(int n) {
        return this.spanSet.contains(n);
    }

    private void addToSpanNotSet(int n) {
        if (Utility.sameObjects(this.spanNotSet, null) || Utility.sameObjects(this.spanNotSet, this.spanSet)) {
            if (this.spanSet.contains(n)) {
                return;
            }
            this.spanNotSet = this.spanSet.cloneAsThawed();
        }
        this.spanNotSet.add(n);
    }

    public int span(CharSequence charSequence, int n, UnicodeSet.SpanCondition spanCondition) {
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNot(charSequence, n, null);
        }
        int n2 = this.spanSet.span(charSequence, n, UnicodeSet.SpanCondition.CONTAINED);
        if (n2 == charSequence.length()) {
            return n2;
        }
        return this.spanWithStrings(charSequence, n, n2, spanCondition);
    }

    private synchronized int spanWithStrings(CharSequence charSequence, int n, int n2, UnicodeSet.SpanCondition spanCondition) {
        int n3 = 0;
        if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
            n3 = this.maxLength16;
        }
        this.offsets.setMaxLength(n3);
        int n4 = charSequence.length();
        int n5 = n2;
        int n6 = n4 - n2;
        int n7 = n2 - n;
        int n8 = this.strings.size();
        while (true) {
            int n9;
            int n10;
            int n11;
            if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
                block1: for (n11 = 0; n11 < n8; ++n11) {
                    n10 = this.spanLengths[n11];
                    if (n10 == 255) continue;
                    String string = this.strings.get(n11);
                    n9 = string.length();
                    if (n10 >= 254) {
                        n10 = n9;
                        n10 = string.offsetByCodePoints(n10, -1);
                    }
                    if (n10 > n7) {
                        n10 = n7;
                    }
                    for (int i = n9 - n10; i <= n6; ++i) {
                        if (!this.offsets.containsOffset(i) && UnicodeSetStringSpan.matches16CPB(charSequence, n5 - n10, n4, string, n9)) {
                            if (i == n6) {
                                return n4;
                            }
                            this.offsets.addOffset(i);
                        }
                        if (n10 == 0) continue block1;
                        --n10;
                    }
                }
            } else {
                n10 = 0;
                int n12 = 0;
                block3: for (n11 = 0; n11 < n8; ++n11) {
                    n9 = this.spanLengths[n11];
                    String string = this.strings.get(n11);
                    int n13 = string.length();
                    if (n9 >= 254) {
                        n9 = n13;
                    }
                    if (n9 > n7) {
                        n9 = n7;
                    }
                    for (int i = n13 - n9; i <= n6 && n9 >= n12; --n9, ++i) {
                        if (n9 <= n12 && i <= n10 || !UnicodeSetStringSpan.matches16CPB(charSequence, n5 - n9, n4, string, n13)) continue;
                        n10 = i;
                        n12 = n9;
                        continue block3;
                    }
                }
                if (n10 != 0 || n12 != 0) {
                    n5 += n10;
                    if ((n6 -= n10) == 0) {
                        return n4;
                    }
                    n7 = 0;
                    continue;
                }
            }
            if (n7 != 0 || n5 == 0) {
                if (this.offsets.isEmpty()) {
                    return n5;
                }
            } else {
                if (this.offsets.isEmpty()) {
                    n2 = this.spanSet.span(charSequence, n5, UnicodeSet.SpanCondition.CONTAINED);
                    n7 = n2 - n5;
                    if (n7 == n6 || n7 == 0) {
                        return n2;
                    }
                    n5 += n7;
                    n6 -= n7;
                    continue;
                }
                n7 = UnicodeSetStringSpan.spanOne(this.spanSet, charSequence, n5, n6);
                if (n7 > 0) {
                    if (n7 == n6) {
                        return n4;
                    }
                    n5 += n7;
                    n6 -= n7;
                    this.offsets.shift(n7);
                    n7 = 0;
                    continue;
                }
            }
            n10 = this.offsets.popMinimum(null);
            n5 += n10;
            n6 -= n10;
            n7 = 0;
        }
    }

    public int spanAndCount(CharSequence charSequence, int n, UnicodeSet.SpanCondition spanCondition, OutputInt outputInt) {
        int n2;
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNot(charSequence, n, outputInt);
        }
        if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
            return this.spanContainedAndCount(charSequence, n, outputInt);
        }
        int n3 = this.strings.size();
        int n4 = charSequence.length();
        int n5 = n;
        int n6 = 0;
        for (int i = n4 - n; i != 0; i -= n2) {
            int n7 = UnicodeSetStringSpan.spanOne(this.spanSet, charSequence, n5, i);
            n2 = n7 > 0 ? n7 : 0;
            for (int j = 0; j < n3; ++j) {
                String string = this.strings.get(j);
                int n8 = string.length();
                if (n2 >= n8 || n8 > i || !UnicodeSetStringSpan.matches16CPB(charSequence, n5, n4, string, n8)) continue;
                n2 = n8;
            }
            if (n2 == 0) {
                outputInt.value = n6;
                return n5;
            }
            ++n6;
            n5 += n2;
        }
        outputInt.value = n6;
        return n5;
    }

    private synchronized int spanContainedAndCount(CharSequence charSequence, int n, OutputInt outputInt) {
        int n2;
        this.offsets.setMaxLength(this.maxLength16);
        int n3 = this.strings.size();
        int n4 = charSequence.length();
        int n5 = n;
        int n6 = 0;
        for (int i = n4 - n; i != 0; i -= n2) {
            int n7 = UnicodeSetStringSpan.spanOne(this.spanSet, charSequence, n5, i);
            if (n7 > 0) {
                this.offsets.addOffsetAndCount(n7, n6 + 1);
            }
            for (n2 = 0; n2 < n3; ++n2) {
                String string = this.strings.get(n2);
                int n8 = string.length();
                if (n8 > i || this.offsets.hasCountAtOffset(n8, n6 + 1) || !UnicodeSetStringSpan.matches16CPB(charSequence, n5, n4, string, n8)) continue;
                this.offsets.addOffsetAndCount(n8, n6 + 1);
            }
            if (this.offsets.isEmpty()) {
                outputInt.value = n6;
                return n5;
            }
            n2 = this.offsets.popMinimum(outputInt);
            n6 = outputInt.value;
            n5 += n2;
        }
        outputInt.value = n6;
        return n5;
    }

    public synchronized int spanBack(CharSequence charSequence, int n, UnicodeSet.SpanCondition spanCondition) {
        if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
            return this.spanNotBack(charSequence, n);
        }
        int n2 = this.spanSet.spanBack(charSequence, n, UnicodeSet.SpanCondition.CONTAINED);
        if (n2 == 0) {
            return 1;
        }
        int n3 = n - n2;
        int n4 = 0;
        if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
            n4 = this.maxLength16;
        }
        this.offsets.setMaxLength(n4);
        int n5 = this.strings.size();
        int n6 = 0;
        if (this.all) {
            n6 = n5;
        }
        while (true) {
            int n7;
            int n8;
            int n9;
            if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
                block1: for (n9 = 0; n9 < n5; ++n9) {
                    int n10;
                    n8 = this.spanLengths[n6 + n9];
                    if (n8 == 255) continue;
                    String string = this.strings.get(n9);
                    n7 = string.length();
                    if (n8 >= 254) {
                        n8 = n7;
                        n10 = 0;
                        n10 = string.offsetByCodePoints(0, 1);
                        n8 -= n10;
                    }
                    if (n8 > n3) {
                        n8 = n3;
                    }
                    for (n10 = n7 - n8; n10 <= n2; ++n10) {
                        if (!this.offsets.containsOffset(n10) && UnicodeSetStringSpan.matches16CPB(charSequence, n2 - n10, n, string, n7)) {
                            if (n10 == n2) {
                                return 1;
                            }
                            this.offsets.addOffset(n10);
                        }
                        if (n8 == 0) continue block1;
                        --n8;
                    }
                }
            } else {
                n8 = 0;
                int n11 = 0;
                block3: for (n9 = 0; n9 < n5; ++n9) {
                    n7 = this.spanLengths[n6 + n9];
                    String string = this.strings.get(n9);
                    int n12 = string.length();
                    if (n7 >= 254) {
                        n7 = n12;
                    }
                    if (n7 > n3) {
                        n7 = n3;
                    }
                    for (int i = n12 - n7; i <= n2 && n7 >= n11; --n7, ++i) {
                        if (n7 <= n11 && i <= n8 || !UnicodeSetStringSpan.matches16CPB(charSequence, n2 - i, n, string, n12)) continue;
                        n8 = i;
                        n11 = n7;
                        continue block3;
                    }
                }
                if (n8 != 0 || n11 != 0) {
                    if ((n2 -= n8) == 0) {
                        return 1;
                    }
                    n3 = 0;
                    continue;
                }
            }
            if (n3 != 0 || n2 == n) {
                if (this.offsets.isEmpty()) {
                    return n2;
                }
            } else {
                if (this.offsets.isEmpty()) {
                    n8 = n2;
                    n2 = this.spanSet.spanBack(charSequence, n8, UnicodeSet.SpanCondition.CONTAINED);
                    n3 = n8 - n2;
                    if (n2 != 0 && n3 != 0) continue;
                    return n2;
                }
                n3 = UnicodeSetStringSpan.spanOneBack(this.spanSet, charSequence, n2);
                if (n3 > 0) {
                    if (n3 == n2) {
                        return 1;
                    }
                    n2 -= n3;
                    this.offsets.shift(n3);
                    n3 = 0;
                    continue;
                }
            }
            n2 -= this.offsets.popMinimum(null);
            n3 = 0;
        }
    }

    private int spanNot(CharSequence charSequence, int n, OutputInt outputInt) {
        int n2;
        int n3 = charSequence.length();
        int n4 = n;
        int n5 = n3 - n;
        int n6 = this.strings.size();
        int n7 = 0;
        do {
            int n8;
            if (outputInt == null) {
                n8 = this.spanNotSet.span(charSequence, n4, UnicodeSet.SpanCondition.NOT_CONTAINED);
            } else {
                n8 = this.spanNotSet.spanAndCount(charSequence, n4, UnicodeSet.SpanCondition.NOT_CONTAINED, outputInt);
                outputInt.value = n7 += outputInt.value;
            }
            if (n8 == n3) {
                return n3;
            }
            n4 = n8;
            n5 = n3 - n8;
            n2 = UnicodeSetStringSpan.spanOne(this.spanSet, charSequence, n4, n5);
            if (n2 > 0) {
                return n4;
            }
            for (int i = 0; i < n6; ++i) {
                String string;
                int n9;
                if (this.spanLengths[i] == 255 || (n9 = (string = this.strings.get(i)).length()) > n5 || !UnicodeSetStringSpan.matches16CPB(charSequence, n4, n3, string, n9)) continue;
                return n4;
            }
            n4 -= n2;
            ++n7;
        } while ((n5 += n2) != 0);
        if (outputInt != null) {
            outputInt.value = n7;
        }
        return n3;
    }

    private int spanNotBack(CharSequence charSequence, int n) {
        int n2;
        int n3 = n;
        int n4 = this.strings.size();
        do {
            if ((n3 = this.spanNotSet.spanBack(charSequence, n3, UnicodeSet.SpanCondition.NOT_CONTAINED)) == 0) {
                return 1;
            }
            n2 = UnicodeSetStringSpan.spanOneBack(this.spanSet, charSequence, n3);
            if (n2 > 0) {
                return n3;
            }
            for (int i = 0; i < n4; ++i) {
                String string;
                int n5;
                if (this.spanLengths[i] == 255 || (n5 = (string = this.strings.get(i)).length()) > n3 || !UnicodeSetStringSpan.matches16CPB(charSequence, n3 - n5, n, string, n5)) continue;
                return n3;
            }
        } while ((n3 += n2) != 0);
        return 1;
    }

    static short makeSpanLengthByte(int n) {
        return (short)(n < 254 ? (int)n : 254);
    }

    private static boolean matches16(CharSequence charSequence, int n, String string, int n2) {
        int n3 = n + n2;
        while (n2-- > 0) {
            if (charSequence.charAt(--n3) == string.charAt(n2)) continue;
            return true;
        }
        return false;
    }

    static boolean matches16CPB(CharSequence charSequence, int n, int n2, String string, int n3) {
        return !(!UnicodeSetStringSpan.matches16(charSequence, n, string, n3) || 0 < n && Character.isHighSurrogate(charSequence.charAt(n - 1)) && Character.isLowSurrogate(charSequence.charAt(n)) || n + n3 < n2 && Character.isHighSurrogate(charSequence.charAt(n + n3 - 1)) && Character.isLowSurrogate(charSequence.charAt(n + n3)));
    }

    static int spanOne(UnicodeSet unicodeSet, CharSequence charSequence, int n, int n2) {
        char c;
        char c2 = charSequence.charAt(n);
        if (c2 >= '\ud800' && c2 <= '\udbff' && n2 >= 2 && UTF16.isTrailSurrogate(c = charSequence.charAt(n + 1))) {
            int n3 = Character.toCodePoint(c2, c);
            return unicodeSet.contains(n3) ? 2 : -2;
        }
        return unicodeSet.contains(c2) ? 1 : -1;
    }

    static int spanOneBack(UnicodeSet unicodeSet, CharSequence charSequence, int n) {
        char c;
        char c2 = charSequence.charAt(n - 1);
        if (c2 >= '\udc00' && c2 <= '\udfff' && n >= 2 && UTF16.isLeadSurrogate(c = charSequence.charAt(n - 2))) {
            int n2 = Character.toCodePoint(c, c2);
            return unicodeSet.contains(n2) ? 2 : -2;
        }
        return unicodeSet.contains(c2) ? 1 : -1;
    }

    private static final class OffsetList {
        private int[] list = new int[16];
        private int length;
        private int start;
        static final boolean $assertionsDisabled = !UnicodeSetStringSpan.class.desiredAssertionStatus();

        public void setMaxLength(int n) {
            if (n > this.list.length) {
                this.list = new int[n];
            }
            this.clear();
        }

        public void clear() {
            int n = this.list.length;
            while (n-- > 0) {
                this.list[n] = 0;
            }
            this.length = 0;
            this.start = 0;
        }

        public boolean isEmpty() {
            return this.length == 0;
        }

        public void shift(int n) {
            int n2 = this.start + n;
            if (n2 >= this.list.length) {
                n2 -= this.list.length;
            }
            if (this.list[n2] != 0) {
                this.list[n2] = 0;
                --this.length;
            }
            this.start = n2;
        }

        public void addOffset(int n) {
            int n2 = this.start + n;
            if (n2 >= this.list.length) {
                n2 -= this.list.length;
            }
            if (!$assertionsDisabled && this.list[n2] != 0) {
                throw new AssertionError();
            }
            this.list[n2] = 1;
            ++this.length;
        }

        public void addOffsetAndCount(int n, int n2) {
            if (!$assertionsDisabled && n2 <= 0) {
                throw new AssertionError();
            }
            int n3 = this.start + n;
            if (n3 >= this.list.length) {
                n3 -= this.list.length;
            }
            if (this.list[n3] == 0) {
                this.list[n3] = n2;
                ++this.length;
            } else if (n2 < this.list[n3]) {
                this.list[n3] = n2;
            }
        }

        public boolean containsOffset(int n) {
            int n2 = this.start + n;
            if (n2 >= this.list.length) {
                n2 -= this.list.length;
            }
            return this.list[n2] != 0;
        }

        public boolean hasCountAtOffset(int n, int n2) {
            int n3;
            int n4 = this.start + n;
            if (n4 >= this.list.length) {
                n4 -= this.list.length;
            }
            return (n3 = this.list[n4]) != 0 && n3 <= n2;
        }

        public int popMinimum(OutputInt outputInt) {
            int n;
            int n2 = this.start;
            while (++n2 < this.list.length) {
                n = this.list[n2];
                if (n == 0) continue;
                this.list[n2] = 0;
                --this.length;
                int n3 = n2 - this.start;
                this.start = n2;
                if (outputInt != null) {
                    outputInt.value = n;
                }
                return n3;
            }
            int n4 = this.list.length - this.start;
            n2 = 0;
            while ((n = this.list[n2]) == 0) {
                ++n2;
            }
            this.list[n2] = 0;
            --this.length;
            this.start = n2;
            if (outputInt != null) {
                outputInt.value = n;
            }
            return n4 + n2;
        }
    }
}

