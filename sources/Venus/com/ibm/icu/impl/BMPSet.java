/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.OutputInt;

public final class BMPSet {
    public static int U16_SURROGATE_OFFSET;
    private boolean[] latin1Contains;
    private int[] table7FF;
    private int[] bmpBlockBits;
    private int[] list4kStarts;
    private final int[] list;
    private final int listLength;
    static final boolean $assertionsDisabled;

    public BMPSet(int[] nArray, int n) {
        this.list = nArray;
        this.listLength = n;
        this.latin1Contains = new boolean[256];
        this.table7FF = new int[64];
        this.bmpBlockBits = new int[64];
        this.list4kStarts = new int[18];
        this.list4kStarts[0] = this.findCodePoint(2048, 0, this.listLength - 1);
        for (int i = 1; i <= 16; ++i) {
            this.list4kStarts[i] = this.findCodePoint(i << 12, this.list4kStarts[i - 1], this.listLength - 1);
        }
        this.list4kStarts[17] = this.listLength - 1;
        this.initBits();
    }

    public BMPSet(BMPSet bMPSet, int[] nArray, int n) {
        this.list = nArray;
        this.listLength = n;
        this.latin1Contains = (boolean[])bMPSet.latin1Contains.clone();
        this.table7FF = (int[])bMPSet.table7FF.clone();
        this.bmpBlockBits = (int[])bMPSet.bmpBlockBits.clone();
        this.list4kStarts = (int[])bMPSet.list4kStarts.clone();
    }

    public boolean contains(int n) {
        if (n <= 255) {
            return this.latin1Contains[n];
        }
        if (n <= 2047) {
            return (this.table7FF[n & 0x3F] & 1 << (n >> 6)) != 0;
        }
        if (n < 55296 || n >= 57344 && n <= 65535) {
            int n2 = n >> 12;
            int n3 = this.bmpBlockBits[n >> 6 & 0x3F] >> n2 & 0x10001;
            if (n3 <= 1) {
                return 0 != n3;
            }
            return this.containsSlow(n, this.list4kStarts[n2], this.list4kStarts[n2 + 1]);
        }
        if (n <= 0x10FFFF) {
            return this.containsSlow(n, this.list4kStarts[13], this.list4kStarts[17]);
        }
        return true;
    }

    public final int span(CharSequence charSequence, int n, UnicodeSet.SpanCondition spanCondition, OutputInt outputInt) {
        int n2;
        int n3;
        int n4 = charSequence.length();
        int n5 = 0;
        if (UnicodeSet.SpanCondition.NOT_CONTAINED != spanCondition) {
            for (n3 = n; n3 < n4; ++n3) {
                char c;
                char c2 = charSequence.charAt(n3);
                if (c2 <= '\u00ff') {
                    if (this.latin1Contains[c2]) continue;
                } else if (c2 <= '\u07ff') {
                    if ((this.table7FF[c2 & 0x3F] & 1 << (c2 >> 6)) != 0) continue;
                } else if (c2 < '\ud800' || c2 >= '\udc00' || n3 + 1 == n4 || (c = charSequence.charAt(n3 + 1)) < '\udc00' || c >= '\ue000') {
                    n2 = c2 >> 12;
                    int n6 = this.bmpBlockBits[c2 >> 6 & 0x3F] >> n2 & 0x10001;
                    if (!(n6 <= 1 ? n6 == 0 : !this.containsSlow(c2, this.list4kStarts[n2], this.list4kStarts[n2 + 1]))) continue;
                } else {
                    n2 = Character.toCodePoint(c2, c);
                    if (this.containsSlow(n2, this.list4kStarts[16], this.list4kStarts[17])) {
                        ++n5;
                        ++n3;
                        continue;
                    }
                }
                break;
            }
        } else {
            while (n3 < n4) {
                char c;
                char c3 = charSequence.charAt(n3);
                if (c3 <= '\u00ff') {
                    if (this.latin1Contains[c3]) {
                        break;
                    }
                } else if (c3 <= '\u07ff') {
                    if ((this.table7FF[c3 & 0x3F] & 1 << (c3 >> 6)) != 0) {
                        break;
                    }
                } else if (c3 < '\ud800' || c3 >= '\udc00' || n3 + 1 == n4 || (c = charSequence.charAt(n3 + 1)) < '\udc00' || c >= '\ue000') {
                    n2 = c3 >> 12;
                    int n7 = this.bmpBlockBits[c3 >> 6 & 0x3F] >> n2 & 0x10001;
                    if (n7 <= 1 ? n7 != 0 : this.containsSlow(c3, this.list4kStarts[n2], this.list4kStarts[n2 + 1])) {
                        break;
                    }
                } else {
                    n2 = Character.toCodePoint(c3, c);
                    if (this.containsSlow(n2, this.list4kStarts[16], this.list4kStarts[17])) break;
                    ++n5;
                    ++n3;
                }
                ++n3;
            }
        }
        if (outputInt != null) {
            n2 = n3 - n;
            outputInt.value = n2 - n5;
        }
        return n3;
    }

    public final int spanBack(CharSequence charSequence, int n, UnicodeSet.SpanCondition spanCondition) {
        block10: {
            if (UnicodeSet.SpanCondition.NOT_CONTAINED != spanCondition) {
                do {
                    int n2;
                    char c;
                    char c2;
                    if ((c2 = charSequence.charAt(--n)) <= '\u00ff') {
                        if (this.latin1Contains[c2]) continue;
                        break block10;
                    }
                    if (c2 <= '\u07ff') {
                        if ((this.table7FF[c2 & 0x3F] & 1 << (c2 >> 6)) != 0) continue;
                        break block10;
                    }
                    if (c2 < '\ud800' || c2 < '\udc00' || 0 == n || (c = charSequence.charAt(n - 1)) < '\ud800' || c >= '\udc00') {
                        n2 = c2 >> 12;
                        int n3 = this.bmpBlockBits[c2 >> 6 & 0x3F] >> n2 & 0x10001;
                        if (!(n3 <= 1 ? n3 == 0 : !this.containsSlow(c2, this.list4kStarts[n2], this.list4kStarts[n2 + 1]))) continue;
                        break block10;
                    }
                    n2 = Character.toCodePoint(c, c2);
                    if (!this.containsSlow(n2, this.list4kStarts[16], this.list4kStarts[17])) break block10;
                    --n;
                } while (0 != n);
                return 1;
            }
            do {
                int n4;
                char c;
                char c3;
                if ((c3 = charSequence.charAt(--n)) <= '\u00ff') {
                    if (!this.latin1Contains[c3]) continue;
                    break block10;
                }
                if (c3 <= '\u07ff') {
                    if ((this.table7FF[c3 & 0x3F] & 1 << (c3 >> 6)) == 0) continue;
                    break block10;
                }
                if (c3 < '\ud800' || c3 < '\udc00' || 0 == n || (c = charSequence.charAt(n - 1)) < '\ud800' || c >= '\udc00') {
                    n4 = c3 >> 12;
                    int n5 = this.bmpBlockBits[c3 >> 6 & 0x3F] >> n4 & 0x10001;
                    if (!(n5 <= 1 ? n5 != 0 : this.containsSlow(c3, this.list4kStarts[n4], this.list4kStarts[n4 + 1]))) continue;
                    break block10;
                }
                n4 = Character.toCodePoint(c, c3);
                if (this.containsSlow(n4, this.list4kStarts[16], this.list4kStarts[17])) break block10;
                --n;
            } while (0 != n);
            return 1;
        }
        return n + 1;
    }

    private static void set32x64Bits(int[] nArray, int n, int n2) {
        if (!$assertionsDisabled && 64 != nArray.length) {
            throw new AssertionError();
        }
        int n3 = n >> 6;
        int n4 = n & 0x3F;
        int n5 = 1 << n3;
        if (n + 1 == n2) {
            int n6 = n4;
            nArray[n6] = nArray[n6] | n5;
            return;
        }
        int n7 = n2 >> 6;
        int n8 = n2 & 0x3F;
        if (n3 == n7) {
            while (n4 < n8) {
                int n9 = n4++;
                nArray[n9] = nArray[n9] | n5;
            }
        } else {
            if (n4 > 0) {
                do {
                    int n10 = n4++;
                    nArray[n10] = nArray[n10] | n5;
                } while (n4 < 64);
                ++n3;
            }
            if (n3 < n7) {
                n5 = ~((1 << n3) - 1);
                if (n7 < 32) {
                    n5 &= (1 << n7) - 1;
                }
                n4 = 0;
                while (n4 < 64) {
                    int n11 = n4++;
                    nArray[n11] = nArray[n11] | n5;
                }
            }
            n5 = 1 << n7;
            n4 = 0;
            while (n4 < n8) {
                int n12 = n4++;
                nArray[n12] = nArray[n12] | n5;
            }
        }
    }

    private void initBits() {
        int n;
        int n2;
        int n3 = 0;
        do {
            n = this.list[n3++];
            n2 = n3 < this.listLength ? this.list[n3++] : 0x110000;
            if (n >= 256) break;
            do {
                this.latin1Contains[n++] = true;
            } while (n < n2 && n < 256);
        } while (n2 <= 256);
        while (n < 2048) {
            BMPSet.set32x64Bits(this.table7FF, n, n2 <= 2048 ? n2 : 2048);
            if (n2 > 2048) {
                n = 2048;
                break;
            }
            n = this.list[n3++];
            if (n3 < this.listLength) {
                n2 = this.list[n3++];
                continue;
            }
            n2 = 0x110000;
        }
        int n4 = 2048;
        while (n < 65536) {
            if (n2 > 65536) {
                n2 = 65536;
            }
            if (n < n4) {
                n = n4;
            }
            if (n < n2) {
                if (0 != (n & 0x3F)) {
                    int n5 = (n >>= 6) & 0x3F;
                    this.bmpBlockBits[n5] = this.bmpBlockBits[n5] | 65537 << (n >> 6);
                    n4 = n = n + 1 << 6;
                }
                if (n < n2) {
                    if (n < (n2 & 0xFFFFFFC0)) {
                        BMPSet.set32x64Bits(this.bmpBlockBits, n >> 6, n2 >> 6);
                    }
                    if (0 != (n2 & 0x3F)) {
                        int n6 = (n2 >>= 6) & 0x3F;
                        this.bmpBlockBits[n6] = this.bmpBlockBits[n6] | 65537 << (n2 >> 6);
                        n4 = n2 = n2 + 1 << 6;
                    }
                }
            }
            if (n2 == 65536) break;
            n = this.list[n3++];
            if (n3 < this.listLength) {
                n2 = this.list[n3++];
                continue;
            }
            n2 = 0x110000;
        }
    }

    private int findCodePoint(int n, int n2, int n3) {
        int n4;
        if (n < this.list[n2]) {
            return n2;
        }
        if (n2 >= n3 || n >= this.list[n3 - 1]) {
            return n3;
        }
        while ((n4 = n2 + n3 >>> 1) != n2) {
            if (n < this.list[n4]) {
                n3 = n4;
                continue;
            }
            n2 = n4;
        }
        return n3;
    }

    private final boolean containsSlow(int n, int n2, int n3) {
        return 0 != (this.findCodePoint(n, n2, n3) & 1);
    }

    static {
        $assertionsDisabled = !BMPSet.class.desiredAssertionStatus();
        U16_SURROGATE_OFFSET = 56613888;
    }
}

