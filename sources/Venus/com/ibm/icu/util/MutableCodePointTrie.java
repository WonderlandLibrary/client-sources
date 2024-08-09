/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.CodePointTrie;
import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class MutableCodePointTrie
extends CodePointMap
implements Cloneable {
    private static final int MAX_UNICODE = 0x10FFFF;
    private static final int UNICODE_LIMIT = 0x110000;
    private static final int BMP_LIMIT = 65536;
    private static final int ASCII_LIMIT = 128;
    private static final int I_LIMIT = 69632;
    private static final int BMP_I_LIMIT = 4096;
    private static final int ASCII_I_LIMIT = 8;
    private static final int SMALL_DATA_BLOCKS_PER_BMP_BLOCK = 4;
    private static final byte ALL_SAME = 0;
    private static final byte MIXED = 1;
    private static final byte SAME_AS = 2;
    private static final int INITIAL_DATA_LENGTH = 16384;
    private static final int MEDIUM_DATA_LENGTH = 131072;
    private static final int MAX_DATA_LENGTH = 0x110000;
    private static final byte I3_NULL = 0;
    private static final byte I3_BMP = 1;
    private static final byte I3_16 = 2;
    private static final byte I3_18 = 3;
    private static final int INDEX_3_18BIT_BLOCK_LENGTH = 36;
    private int[] index;
    private int index3NullOffset = -1;
    private int[] data;
    private int dataLength;
    private int dataNullOffset = -1;
    private int origInitialValue;
    private int initialValue;
    private int errorValue;
    private int highStart;
    private int highValue;
    private char[] index16;
    private byte[] flags = new byte[69632];
    static final boolean $assertionsDisabled = !MutableCodePointTrie.class.desiredAssertionStatus();

    public MutableCodePointTrie(int n, int n2) {
        this.index = new int[4096];
        this.data = new int[16384];
        this.origInitialValue = n;
        this.initialValue = n;
        this.errorValue = n2;
        this.highValue = n;
    }

    public MutableCodePointTrie clone() {
        try {
            MutableCodePointTrie mutableCodePointTrie = (MutableCodePointTrie)super.clone();
            int n = this.highStart <= 65536 ? 4096 : 69632;
            mutableCodePointTrie.index = new int[n];
            mutableCodePointTrie.flags = new byte[69632];
            int n2 = this.highStart >> 4;
            for (int i = 0; i < n2; ++i) {
                mutableCodePointTrie.index[i] = this.index[i];
                mutableCodePointTrie.flags[i] = this.flags[i];
            }
            mutableCodePointTrie.index3NullOffset = this.index3NullOffset;
            mutableCodePointTrie.data = (int[])this.data.clone();
            mutableCodePointTrie.dataLength = this.dataLength;
            mutableCodePointTrie.dataNullOffset = this.dataNullOffset;
            mutableCodePointTrie.origInitialValue = this.origInitialValue;
            mutableCodePointTrie.initialValue = this.initialValue;
            mutableCodePointTrie.errorValue = this.errorValue;
            mutableCodePointTrie.highStart = this.highStart;
            mutableCodePointTrie.highValue = this.highValue;
            if (!$assertionsDisabled && this.index16 != null) {
                throw new AssertionError();
            }
            return mutableCodePointTrie;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public static MutableCodePointTrie fromCodePointMap(CodePointMap codePointMap) {
        int n = codePointMap.get(-1);
        int n2 = codePointMap.get(0x10FFFF);
        MutableCodePointTrie mutableCodePointTrie = new MutableCodePointTrie(n2, n);
        CodePointMap.Range range = new CodePointMap.Range();
        int n3 = 0;
        while (codePointMap.getRange(n3, null, range)) {
            int n4 = range.getEnd();
            int n5 = range.getValue();
            if (n5 != n2) {
                if (n3 == n4) {
                    mutableCodePointTrie.set(n3, n5);
                } else {
                    mutableCodePointTrie.setRange(n3, n4, n5);
                }
            }
            n3 = n4 + 1;
        }
        return mutableCodePointTrie;
    }

    private void clear() {
        this.dataNullOffset = -1;
        this.index3NullOffset = -1;
        this.dataLength = 0;
        this.highValue = this.initialValue = this.origInitialValue;
        this.highStart = 0;
        this.index16 = null;
    }

    @Override
    public int get(int n) {
        if (n < 0 || 0x10FFFF < n) {
            return this.errorValue;
        }
        if (n >= this.highStart) {
            return this.highValue;
        }
        int n2 = n >> 4;
        if (this.flags[n2] == 0) {
            return this.index[n2];
        }
        return this.data[this.index[n2] + (n & 0xF)];
    }

    private static final int maybeFilterValue(int n, int n2, int n3, CodePointMap.ValueFilter valueFilter) {
        if (n == n2) {
            n = n3;
        } else if (valueFilter != null) {
            n = valueFilter.apply(n);
        }
        return n;
    }

    @Override
    public boolean getRange(int n, CodePointMap.ValueFilter valueFilter, CodePointMap.Range range) {
        if (n < 0 || 0x10FFFF < n) {
            return true;
        }
        if (n >= this.highStart) {
            int n2 = this.highValue;
            if (valueFilter != null) {
                n2 = valueFilter.apply(n2);
            }
            range.set(n, 0x10FFFF, n2);
            return false;
        }
        int n3 = this.initialValue;
        if (valueFilter != null) {
            n3 = valueFilter.apply(n3);
        }
        int n4 = n;
        int n5 = 0;
        int n6 = 0;
        boolean bl = false;
        int n7 = n4 >> 4;
        do {
            int n8;
            if (this.flags[n7] == 0) {
                n8 = this.index[n7];
                if (bl) {
                    if (n8 != n5) {
                        if (valueFilter == null || MutableCodePointTrie.maybeFilterValue(n8, this.initialValue, n3, valueFilter) != n6) {
                            range.set(n, n4 - 1, n6);
                            return false;
                        }
                        n5 = n8;
                    }
                } else {
                    n5 = n8;
                    n6 = MutableCodePointTrie.maybeFilterValue(n8, this.initialValue, n3, valueFilter);
                    bl = true;
                }
                n4 = n4 + 16 & 0xFFFFFFF0;
            } else {
                n8 = this.index[n7] + (n4 & 0xF);
                int n9 = this.data[n8];
                if (bl) {
                    if (n9 != n5) {
                        if (valueFilter == null || MutableCodePointTrie.maybeFilterValue(n9, this.initialValue, n3, valueFilter) != n6) {
                            range.set(n, n4 - 1, n6);
                            return false;
                        }
                        n5 = n9;
                    }
                } else {
                    n5 = n9;
                    n6 = MutableCodePointTrie.maybeFilterValue(n9, this.initialValue, n3, valueFilter);
                    bl = true;
                }
                while ((++n4 & 0xF) != 0) {
                    if ((n9 = this.data[++n8]) == n5) continue;
                    if (valueFilter == null || MutableCodePointTrie.maybeFilterValue(n9, this.initialValue, n3, valueFilter) != n6) {
                        range.set(n, n4 - 1, n6);
                        return false;
                    }
                    n5 = n9;
                }
            }
            ++n7;
        } while (n4 < this.highStart);
        if (!$assertionsDisabled && !bl) {
            throw new AssertionError();
        }
        if (MutableCodePointTrie.maybeFilterValue(this.highValue, this.initialValue, n3, valueFilter) != n6) {
            range.set(n, n4 - 1, n6);
        } else {
            range.set(n, 0x10FFFF, n6);
        }
        return false;
    }

    private void writeBlock(int n, int n2) {
        int n3 = n + 16;
        Arrays.fill(this.data, n, n3, n2);
    }

    public void set(int n, int n2) {
        if (n < 0 || 0x10FFFF < n) {
            throw new IllegalArgumentException("invalid code point");
        }
        this.ensureHighStart(n);
        int n3 = this.getDataBlock(n >> 4);
        this.data[n3 + (n & 0xF)] = n2;
    }

    private void fillBlock(int n, int n2, int n3, int n4) {
        Arrays.fill(this.data, n + n2, n + n3, n4);
    }

    public void setRange(int n, int n2, int n3) {
        int n4;
        int n5;
        if (n < 0 || 0x10FFFF < n || n2 < 0 || 0x10FFFF < n2 || n > n2) {
            throw new IllegalArgumentException("invalid code point range");
        }
        this.ensureHighStart(n2);
        int n6 = n2 + 1;
        if ((n & 0xF) != 0) {
            n5 = this.getDataBlock(n >> 4);
            n4 = n + 15 & 0xFFFFFFF0;
            if (n4 <= n6) {
                this.fillBlock(n5, n & 0xF, 16, n3);
                n = n4;
            } else {
                this.fillBlock(n5, n & 0xF, n6 & 0xF, n3);
                return;
            }
        }
        n5 = n6 & 0xF;
        n6 &= 0xFFFFFFF0;
        while (n < n6) {
            n4 = n >> 4;
            if (this.flags[n4] == 0) {
                this.index[n4] = n3;
            } else {
                this.fillBlock(this.index[n4], 0, 16, n3);
            }
            n += 16;
        }
        if (n5 > 0) {
            n4 = this.getDataBlock(n >> 4);
            this.fillBlock(n4, 0, n5, n3);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CodePointTrie buildImmutable(CodePointTrie.Type type, CodePointTrie.ValueWidth valueWidth) {
        if (type == null || valueWidth == null) {
            throw new IllegalArgumentException("The type and valueWidth must be specified.");
        }
        try {
            CodePointTrie codePointTrie = this.build(type, valueWidth);
            return codePointTrie;
        } finally {
            this.clear();
        }
    }

    private void ensureHighStart(int n) {
        if (n >= this.highStart) {
            n = n + 512 & 0xFFFFFE00;
            int n2 = this.highStart >> 4;
            int n3 = n >> 4;
            if (n3 > this.index.length) {
                int[] nArray = new int[69632];
                for (int i = 0; i < n2; ++i) {
                    nArray[i] = this.index[i];
                }
                this.index = nArray;
            }
            do {
                this.flags[n2] = 0;
                this.index[n2] = this.initialValue;
            } while (++n2 < n3);
            this.highStart = n;
        }
    }

    private int allocDataBlock(int n) {
        int n2 = this.dataLength;
        int n3 = n2 + n;
        if (n3 > this.data.length) {
            int n4;
            if (this.data.length < 131072) {
                n4 = 131072;
            } else if (this.data.length < 0x110000) {
                n4 = 0x110000;
            } else {
                throw new AssertionError();
            }
            int[] nArray = new int[n4];
            for (int i = 0; i < this.dataLength; ++i) {
                nArray[i] = this.data[i];
            }
            this.data = nArray;
        }
        this.dataLength = n3;
        return n2;
    }

    private int getDataBlock(int n) {
        if (this.flags[n] == 1) {
            return this.index[n];
        }
        if (n < 4096) {
            int n2 = this.allocDataBlock(64);
            int n3 = n & 0xFFFFFFFC;
            int n4 = n3 + 4;
            do {
                if (!$assertionsDisabled && this.flags[n3] != 0) {
                    throw new AssertionError();
                }
                this.writeBlock(n2, this.index[n3]);
                this.flags[n3] = 1;
                this.index[n3++] = n2;
                n2 += 16;
            } while (n3 < n4);
            return this.index[n];
        }
        int n5 = this.allocDataBlock(16);
        if (n5 < 0) {
            return n5;
        }
        this.writeBlock(n5, this.index[n]);
        this.flags[n] = 1;
        this.index[n] = n5;
        return n5;
    }

    private void maskValues(int n) {
        int n2;
        this.initialValue &= n;
        this.errorValue &= n;
        this.highValue &= n;
        int n3 = this.highStart >> 4;
        for (n2 = 0; n2 < n3; ++n2) {
            if (this.flags[n2] != 0) continue;
            int n4 = n2;
            this.index[n4] = this.index[n4] & n;
        }
        n2 = 0;
        while (n2 < this.dataLength) {
            int n5 = n2++;
            this.data[n5] = this.data[n5] & n;
        }
    }

    private static boolean equalBlocks(int[] nArray, int n, int[] nArray2, int n2, int n3) {
        while (n3 > 0 && nArray[n] == nArray2[n2]) {
            ++n;
            ++n2;
            --n3;
        }
        return n3 == 0;
    }

    private static boolean equalBlocks(char[] cArray, int n, int[] nArray, int n2, int n3) {
        while (n3 > 0 && cArray[n] == nArray[n2]) {
            ++n;
            ++n2;
            --n3;
        }
        return n3 == 0;
    }

    private static boolean equalBlocks(char[] cArray, int n, char[] cArray2, int n2, int n3) {
        while (n3 > 0 && cArray[n] == cArray2[n2]) {
            ++n;
            ++n2;
            --n3;
        }
        return n3 == 0;
    }

    private static boolean allValuesSameAs(int[] nArray, int n, int n2, int n3) {
        int n4 = n + n2;
        while (n < n4 && nArray[n] == n3) {
            ++n;
        }
        return n == n4;
    }

    private static int findSameBlock(char[] cArray, int n, int n2, char[] cArray2, int n3, int n4) {
        n2 -= n4;
        while (n <= n2) {
            if (MutableCodePointTrie.equalBlocks(cArray, n, cArray2, n3, n4)) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    private static int findAllSameBlock(int[] nArray, int n, int n2, int n3, int n4) {
        n2 -= n4;
        block0: for (int i = n; i <= n2; ++i) {
            if (nArray[i] != n3) continue;
            int n5 = 1;
            while (n5 != n4) {
                if (nArray[i + n5] != n3) {
                    i += n5;
                    continue block0;
                }
                ++n5;
            }
            return i;
        }
        return 1;
    }

    private static int getOverlap(int[] nArray, int n, int[] nArray2, int n2, int n3) {
        int n4;
        if (!$assertionsDisabled && n4 > n) {
            throw new AssertionError();
        }
        for (n4 = n3 - 1; n4 > 0 && !MutableCodePointTrie.equalBlocks(nArray, n - n4, nArray2, n2, n4); --n4) {
        }
        return n4;
    }

    private static int getOverlap(char[] cArray, int n, int[] nArray, int n2, int n3) {
        int n4;
        if (!$assertionsDisabled && n4 > n) {
            throw new AssertionError();
        }
        for (n4 = n3 - 1; n4 > 0 && !MutableCodePointTrie.equalBlocks(cArray, n - n4, nArray, n2, n4); --n4) {
        }
        return n4;
    }

    private static int getOverlap(char[] cArray, int n, char[] cArray2, int n2, int n3) {
        int n4;
        if (!$assertionsDisabled && n4 > n) {
            throw new AssertionError();
        }
        for (n4 = n3 - 1; n4 > 0 && !MutableCodePointTrie.equalBlocks(cArray, n - n4, cArray2, n2, n4); --n4) {
        }
        return n4;
    }

    private static int getAllSameOverlap(int[] nArray, int n, int n2, int n3) {
        int n4;
        int n5 = n - (n3 - 1);
        for (n4 = n; n5 < n4 && nArray[n4 - 1] == n2; --n4) {
        }
        return n - n4;
    }

    private static boolean isStartOfSomeFastBlock(int n, int[] nArray, int n2) {
        for (int i = 0; i < n2; i += 4) {
            if (nArray[i] != n) continue;
            return false;
        }
        return true;
    }

    private int findHighStart() {
        int n = this.highStart >> 4;
        while (n > 0) {
            boolean bl;
            if (this.flags[--n] == 0) {
                bl = this.index[n] == this.highValue;
            } else {
                int n2 = this.index[n];
                int n3 = 0;
                while (true) {
                    if (n3 == 16) {
                        bl = true;
                        break;
                    }
                    if (this.data[n2 + n3] != this.highValue) {
                        bl = false;
                        break;
                    }
                    ++n3;
                }
            }
            if (bl) continue;
            return n + 1 << 4;
        }
        return 1;
    }

    /*
     * Unable to fully structure code
     */
    private int compactWholeDataBlocks(int var1_1, AllSameBlocks var2_2) {
        var3_3 = 128;
        var3_3 += 16;
        var3_3 += 4;
        var4_4 = this.highStart >> 4;
        var5_5 = 64;
        var6_6 = 4;
        for (var7_7 = 0; var7_7 < var4_4; var7_7 += var6_6) {
            if (var7_7 == var1_1) {
                var5_5 = 16;
                var6_6 = 1;
            }
            var8_8 = this.index[var7_7];
            if (this.flags[var7_7] != 1) ** GOTO lbl20
            var9_9 = var8_8;
            if (MutableCodePointTrie.allValuesSameAs(this.data, var9_9 + 1, var5_5 - 1, var8_8 = this.data[var9_9])) {
                this.flags[var7_7] = 0;
                this.index[var7_7] = var8_8;
            } else {
                var3_3 += var5_5;
                continue;
lbl20:
                // 1 sources

                if (!MutableCodePointTrie.$assertionsDisabled && this.flags[var7_7] != 0) {
                    throw new AssertionError();
                }
                if (var6_6 > 1) {
                    var9_9 = 1;
                    var10_10 = var7_7 + var6_6;
                    for (var11_11 = var7_7 + 1; var11_11 < var10_10; ++var11_11) {
                        if (!MutableCodePointTrie.$assertionsDisabled && this.flags[var11_11] != 0) {
                            throw new AssertionError();
                        }
                        if (this.index[var11_11] == var8_8) continue;
                        var9_9 = 0;
                        break;
                    }
                    if (var9_9 == 0) {
                        if (this.getDataBlock(var7_7) < 0) {
                            return 1;
                        }
                        var3_3 += var5_5;
                        continue;
                    }
                }
            }
            if ((var9_9 = var2_2.findOrAdd(var7_7, var6_6, var8_8)) == -2) {
                var10_10 = 4;
                var11_11 = 0;
                while (true) {
                    if (var11_11 == var7_7) {
                        var2_2.add(var7_7, var6_6, var8_8);
                        break;
                    }
                    if (var11_11 == var1_1) {
                        var10_10 = 1;
                    }
                    if (this.flags[var11_11] == 0 && this.index[var11_11] == var8_8) {
                        var2_2.add(var11_11, var10_10 + var6_6, var8_8);
                        var9_9 = var11_11;
                        break;
                    }
                    var11_11 += var10_10;
                }
            }
            if (var9_9 >= 0) {
                this.flags[var7_7] = 2;
                this.index[var7_7] = var9_9;
                continue;
            }
            var3_3 += var5_5;
        }
        return var3_3;
    }

    private int compactData(int n, int[] nArray, int n2, MixedBlocks mixedBlocks) {
        int n3 = 0;
        int n4 = 0;
        while (n3 < 128) {
            this.index[n4] = n3;
            n3 += 64;
            n4 += 4;
        }
        n4 = 64;
        mixedBlocks.init(nArray.length, n4);
        mixedBlocks.extend(nArray, 0, 0, n3);
        int n5 = this.highStart >> 4;
        int n6 = 4;
        int n7 = 0;
        for (int i = 8; i < n5; i += n6) {
            int n8;
            int n9;
            int n10;
            if (i == n) {
                n4 = 16;
                n6 = 1;
                n7 = n3;
                mixedBlocks.init(nArray.length, n4);
                mixedBlocks.extend(nArray, 0, 0, n3);
            }
            if (this.flags[i] == 0) {
                n10 = this.index[i];
                n9 = mixedBlocks.findAllSameBlock(nArray, n10);
                while (n9 >= 0 && i == n2 && i >= n && n9 < n7 && MutableCodePointTrie.isStartOfSomeFastBlock(n9, this.index, n)) {
                    n9 = MutableCodePointTrie.findAllSameBlock(nArray, n9 + 1, n3, n10, n4);
                }
                if (n9 >= 0) {
                    this.index[i] = n9;
                    continue;
                }
                this.index[i] = n3 - n9;
                n8 = n3;
                for (n9 = MutableCodePointTrie.getAllSameOverlap(nArray, n3, n10, n4); n9 < n4; ++n9) {
                    nArray[n3++] = n10;
                }
                mixedBlocks.extend(nArray, 0, n8, n3);
                continue;
            }
            if (this.flags[i] == 1) {
                n10 = this.index[i];
                n9 = mixedBlocks.findBlock(nArray, this.data, n10);
                if (n9 >= 0) {
                    this.index[i] = n9;
                    continue;
                }
                n9 = MutableCodePointTrie.getOverlap(nArray, n3, this.data, n10, n4);
                this.index[i] = n3 - n9;
                n8 = n3;
                while (n9 < n4) {
                    nArray[n3++] = this.data[n10 + n9++];
                }
                mixedBlocks.extend(nArray, 0, n8, n3);
                continue;
            }
            n10 = this.index[i];
            this.index[i] = this.index[n10];
        }
        return n3;
    }

    private int compactIndex(int n, MixedBlocks mixedBlocks) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14 = n >> 2;
        if (this.highStart >> 6 <= n14) {
            this.index3NullOffset = Short.MAX_VALUE;
            return n14;
        }
        char[] cArray = new char[n14];
        int n15 = -1;
        int n16 = 0;
        int n17 = 0;
        while (n16 < n) {
            n13 = this.index[n16];
            cArray[n17] = (char)n13;
            if (n13 == this.dataNullOffset) {
                if (n15 < 0) {
                    n15 = n17;
                } else if (this.index3NullOffset < 0 && n17 - n15 + 1 == 32) {
                    this.index3NullOffset = n15;
                }
            } else {
                n15 = -1;
            }
            n12 = n16 + 4;
            while (++n16 < n12) {
                this.index[n16] = n13 += 16;
            }
            ++n17;
        }
        mixedBlocks.init(n14, 32);
        mixedBlocks.extend(cArray, 0, 0, n14);
        n16 = 0;
        n15 = this.index3NullOffset;
        n17 = 0;
        n13 = n < 4096 ? 0 : 4096;
        n12 = this.highStart >> 4;
        int n18 = n13;
        while (n18 < n12) {
            n11 = n18;
            n10 = n18 + 32;
            int n19 = 0;
            boolean bl = true;
            do {
                n9 = this.index[n11];
                n19 |= n9;
                if (n9 == this.dataNullOffset) continue;
                bl = false;
            } while (++n11 < n10);
            if (bl) {
                this.flags[n18] = 0;
                if (n15 < 0) {
                    if (n19 <= 65535) {
                        n16 += 32;
                    } else {
                        n16 += 36;
                        n17 = 1;
                    }
                    n15 = 0;
                }
            } else if (n19 <= 65535) {
                n9 = mixedBlocks.findBlock(cArray, this.index, n18);
                if (n9 >= 0) {
                    this.flags[n18] = 1;
                    this.index[n18] = n9;
                } else {
                    this.flags[n18] = 2;
                    n16 += 32;
                }
            } else {
                this.flags[n18] = 3;
                n16 += 36;
                n17 = 1;
            }
            n18 = n11;
        }
        n18 = n12 - n13 >> 5;
        n11 = n18 + 31 >> 5;
        n10 = n14 + n11 + n16 + n18 + 1;
        this.index16 = Arrays.copyOf(cArray, n10);
        mixedBlocks.init(n10, 32);
        MixedBlocks mixedBlocks2 = null;
        if (n17 != 0) {
            mixedBlocks2 = new MixedBlocks(null);
            mixedBlocks2.init(n10, 36);
        }
        char[] cArray2 = new char[n18];
        n9 = 0;
        n15 = this.index3NullOffset;
        int n20 = n8 = n14 + n11;
        for (n7 = n13; n7 < n12; n7 += 32) {
            n6 = this.flags[n7];
            if (n6 == 0 && n15 < 0) {
                n6 = this.dataNullOffset <= 65535 ? 2 : 3;
                n15 = 0;
            }
            if (n6 == 0) {
                n5 = this.index3NullOffset;
            } else if (n6 == 1) {
                n5 = this.index[n7];
            } else if (n6 == 2) {
                n4 = mixedBlocks.findBlock(this.index16, this.index, n7);
                if (n4 >= 0) {
                    n5 = n4;
                } else {
                    n4 = n20 == n8 ? 0 : MutableCodePointTrie.getOverlap(this.index16, n20, this.index, n7, 32);
                    n5 = n20 - n4;
                    n3 = n20;
                    while (n4 < 32) {
                        this.index16[n20++] = (char)this.index[n7 + n4++];
                    }
                    mixedBlocks.extend(this.index16, n8, n3, n20);
                    if (n17 != 0) {
                        mixedBlocks2.extend(this.index16, n8, n3, n20);
                    }
                }
            } else {
                int n21;
                int n22;
                if (!$assertionsDisabled && n6 != 3) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && n17 == 0) {
                    throw new AssertionError();
                }
                n4 = n7;
                n3 = n7 + 32;
                n2 = n20;
                do {
                    n22 = this.index[n4++];
                    n21 = (n22 & 0x30000) >> 2;
                    int n23 = ++n2;
                    this.index16[n23] = (char)n22;
                    n22 = this.index[n4++];
                    n21 |= (n22 & 0x30000) >> 4;
                    int n24 = ++n2;
                    this.index16[n24] = (char)n22;
                    n22 = this.index[n4++];
                    n21 |= (n22 & 0x30000) >> 6;
                    int n25 = ++n2;
                    this.index16[n25] = (char)n22;
                    n22 = this.index[n4++];
                    n21 |= (n22 & 0x30000) >> 8;
                    int n26 = ++n2;
                    this.index16[n26] = (char)n22;
                    n22 = this.index[n4++];
                    n21 |= (n22 & 0x30000) >> 10;
                    int n27 = ++n2;
                    this.index16[n27] = (char)n22;
                    n22 = this.index[n4++];
                    n21 |= (n22 & 0x30000) >> 12;
                    int n28 = ++n2;
                    this.index16[n28] = (char)n22;
                    n22 = this.index[n4++];
                    n21 |= (n22 & 0x30000) >> 14;
                    int n29 = ++n2;
                    this.index16[n29] = (char)n22;
                    n22 = this.index[n4++];
                    int n30 = ++n2;
                    this.index16[n30] = (char)n22;
                    this.index16[++n2 - 9] = (char)(n21 |= (n22 & 0x30000) >> 16);
                } while (n4 < n3);
                n22 = mixedBlocks2.findBlock(this.index16, this.index16, n20);
                if (n22 >= 0) {
                    n5 = n22 | 0x8000;
                } else {
                    n22 = n20 == n8 ? 0 : MutableCodePointTrie.getOverlap(this.index16, n20, this.index16, n20, 36);
                    n5 = n20 - n22 | 0x8000;
                    n21 = n20;
                    if (n22 > 0) {
                        int n31 = n20;
                        while (n22 < 36) {
                            this.index16[n20++] = this.index16[n31 + n22++];
                        }
                    } else {
                        n20 += 36;
                    }
                    mixedBlocks.extend(this.index16, n8, n21, n20);
                    if (n17 != 0) {
                        mixedBlocks2.extend(this.index16, n8, n21, n20);
                    }
                }
            }
            if (this.index3NullOffset < 0 && n15 >= 0) {
                this.index3NullOffset = n5;
            }
            cArray2[n9++] = (char)n5;
        }
        if (!$assertionsDisabled && n9 != n18) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n20 > n8 + n16) {
            throw new AssertionError();
        }
        if (this.index3NullOffset < 0) {
            this.index3NullOffset = Short.MAX_VALUE;
        }
        if (n20 >= 32799) {
            throw new IndexOutOfBoundsException("The trie data exceeds limitations of the data structure.");
        }
        n7 = 32;
        n5 = n14;
        for (n6 = 0; n6 < n9; n6 += n7) {
            if (n9 - n6 >= n7) {
                if (!$assertionsDisabled && n7 != 32) {
                    throw new AssertionError();
                }
                n4 = mixedBlocks.findBlock(this.index16, cArray2, n6);
            } else {
                n7 = n9 - n6;
                n4 = MutableCodePointTrie.findSameBlock(this.index16, n8, n20, cArray2, n6, n7);
            }
            if (n4 >= 0) {
                n3 = n4;
            } else {
                n4 = n20 == n8 ? 0 : MutableCodePointTrie.getOverlap(this.index16, n20, cArray2, n6, n7);
                n3 = n20 - n4;
                n2 = n20;
                while (n4 < n7) {
                    this.index16[n20++] = cArray2[n6 + n4++];
                }
                mixedBlocks.extend(this.index16, n8, n2, n20);
            }
            this.index16[n5++] = (char)n3;
        }
        if (!$assertionsDisabled && n5 != n8) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n20 > n10) {
            throw new AssertionError();
        }
        return n20;
    }

    private int compactTrie(int n) {
        int n2;
        if (!$assertionsDisabled && (this.highStart & 0x1FF) != 0) {
            throw new AssertionError();
        }
        this.highValue = this.get(0x10FFFF);
        int n3 = this.findHighStart();
        if ((n3 = n3 + 511 & 0xFFFFFE00) == 0x110000) {
            this.highValue = this.initialValue;
        }
        if (n3 < (n2 = n << 4)) {
            for (int i = n3 >> 4; i < n; ++i) {
                this.flags[i] = 0;
                this.index[i] = this.highValue;
            }
            this.highStart = n2;
        } else {
            this.highStart = n3;
        }
        int[] nArray = new int[128];
        for (int i = 0; i < 128; ++i) {
            nArray[i] = this.get(i);
        }
        AllSameBlocks allSameBlocks = new AllSameBlocks();
        int n4 = this.compactWholeDataBlocks(n, allSameBlocks);
        int[] nArray2 = Arrays.copyOf(nArray, n4);
        int n5 = allSameBlocks.findMostUsed();
        MixedBlocks mixedBlocks = new MixedBlocks(null);
        int n6 = this.compactData(n, nArray2, n5, mixedBlocks);
        if (!$assertionsDisabled && n6 > n4) {
            throw new AssertionError();
        }
        this.data = nArray2;
        this.dataLength = n6;
        if (this.dataLength > 262159) {
            throw new IndexOutOfBoundsException("The trie data exceeds limitations of the data structure.");
        }
        if (n5 >= 0) {
            this.dataNullOffset = this.index[n5];
            this.initialValue = this.data[this.dataNullOffset];
        } else {
            this.dataNullOffset = 1048575;
        }
        int n7 = this.compactIndex(n, mixedBlocks);
        this.highStart = n3;
        return n7;
    }

    private CodePointTrie build(CodePointTrie.Type type, CodePointTrie.ValueWidth valueWidth) {
        int n;
        char[] cArray;
        switch (1.$SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth[valueWidth.ordinal()]) {
            case 1: {
                break;
            }
            case 2: {
                this.maskValues(65535);
                break;
            }
            case 3: {
                this.maskValues(255);
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        int n2 = type == CodePointTrie.Type.FAST ? 65536 : 4096;
        int n3 = this.compactTrie(n2 >> 4);
        if (valueWidth == CodePointTrie.ValueWidth.BITS_32 && (n3 & 1) != 0) {
            this.index16[n3++] = 65518;
        }
        int n4 = n3 * 2;
        if (valueWidth == CodePointTrie.ValueWidth.BITS_16) {
            if (((n3 ^ this.dataLength) & 1) != 0) {
                this.data[this.dataLength++] = this.errorValue;
            }
            if (this.data[this.dataLength - 1] != this.errorValue || this.data[this.dataLength - 2] != this.highValue) {
                this.data[this.dataLength++] = this.highValue;
                this.data[this.dataLength++] = this.errorValue;
            }
            n4 += this.dataLength * 2;
        } else if (valueWidth == CodePointTrie.ValueWidth.BITS_32) {
            if (this.data[this.dataLength - 1] != this.errorValue || this.data[this.dataLength - 2] != this.highValue) {
                if (this.data[this.dataLength - 1] != this.highValue) {
                    this.data[this.dataLength++] = this.highValue;
                }
                this.data[this.dataLength++] = this.errorValue;
            }
            n4 += this.dataLength * 4;
        } else {
            int n5 = n4 + this.dataLength & 3;
            if (n5 != 0 || this.data[this.dataLength - 1] != this.errorValue || this.data[this.dataLength - 2] != this.highValue) {
                if (n5 == 3 && this.data[this.dataLength - 1] == this.highValue) {
                    this.data[this.dataLength++] = this.errorValue;
                } else {
                    while (n5 != 2) {
                        this.data[this.dataLength++] = this.highValue;
                        n5 = n5 + 1 & 3;
                    }
                    this.data[this.dataLength++] = this.highValue;
                    this.data[this.dataLength++] = this.errorValue;
                }
            }
            n4 += this.dataLength;
        }
        if (!$assertionsDisabled && (n4 & 3) != 0) {
            throw new AssertionError();
        }
        if (this.highStart <= n2) {
            cArray = new char[n3];
            int n6 = 0;
            for (n = 0; n < n3; ++n) {
                cArray[n] = (char)this.index[n6];
                n6 += 4;
            }
        } else if (n3 == this.index16.length) {
            cArray = this.index16;
            this.index16 = null;
        } else {
            cArray = Arrays.copyOf(this.index16, n3);
        }
        switch (1.$SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth[valueWidth.ordinal()]) {
            case 2: {
                char[] cArray2 = new char[this.dataLength];
                for (n = 0; n < this.dataLength; ++n) {
                    cArray2[n] = (char)this.data[n];
                }
                return type == CodePointTrie.Type.FAST ? new CodePointTrie.Fast16(cArray, cArray2, this.highStart, this.index3NullOffset, this.dataNullOffset) : new CodePointTrie.Small16(cArray, cArray2, this.highStart, this.index3NullOffset, this.dataNullOffset);
            }
            case 1: {
                int[] nArray = Arrays.copyOf(this.data, this.dataLength);
                return type == CodePointTrie.Type.FAST ? new CodePointTrie.Fast32(cArray, nArray, this.highStart, this.index3NullOffset, this.dataNullOffset) : new CodePointTrie.Small32(cArray, nArray, this.highStart, this.index3NullOffset, this.dataNullOffset);
            }
            case 3: {
                byte[] byArray = new byte[this.dataLength];
                for (n = 0; n < this.dataLength; ++n) {
                    byArray[n] = (byte)this.data[n];
                }
                return type == CodePointTrie.Type.FAST ? new CodePointTrie.Fast8(cArray, byArray, this.highStart, this.index3NullOffset, this.dataNullOffset) : new CodePointTrie.Small8(cArray, byArray, this.highStart, this.index3NullOffset, this.dataNullOffset);
            }
        }
        throw new IllegalArgumentException();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static boolean access$000(int[] nArray, int n, int[] nArray2, int n2, int n3) {
        return MutableCodePointTrie.equalBlocks(nArray, n, nArray2, n2, n3);
    }

    static boolean access$100(char[] cArray, int n, int[] nArray, int n2, int n3) {
        return MutableCodePointTrie.equalBlocks(cArray, n, nArray, n2, n3);
    }

    static boolean access$200(char[] cArray, int n, char[] cArray2, int n2, int n3) {
        return MutableCodePointTrie.equalBlocks(cArray, n, cArray2, n2, n3);
    }

    static boolean access$300(int[] nArray, int n, int n2, int n3) {
        return MutableCodePointTrie.allValuesSameAs(nArray, n, n2, n3);
    }

    private static final class MixedBlocks {
        private int[] table;
        private int length;
        private int shift;
        private int mask;
        private int blockLength;
        static final boolean $assertionsDisabled = !MutableCodePointTrie.class.desiredAssertionStatus();

        private MixedBlocks() {
        }

        void init(int n, int n2) {
            int n3;
            int n4 = n - n2 + 1;
            if (n4 <= 4095) {
                n3 = 6007;
                this.shift = 12;
                this.mask = 4095;
            } else if (n4 <= Short.MAX_VALUE) {
                n3 = 50021;
                this.shift = 15;
                this.mask = Short.MAX_VALUE;
            } else if (n4 <= 131071) {
                n3 = 200003;
                this.shift = 17;
                this.mask = 131071;
            } else {
                n3 = 1500007;
                this.shift = 21;
                this.mask = 0x1FFFFF;
            }
            if (this.table == null || n3 > this.table.length) {
                this.table = new int[n3];
            } else {
                Arrays.fill(this.table, 0, n3, 0);
            }
            this.length = n3;
            this.blockLength = n2;
        }

        void extend(int[] nArray, int n, int n2, int n3) {
            int n4 = n2 - this.blockLength;
            n4 = n4 >= n ? ++n4 : n;
            int n5 = n3 - this.blockLength;
            while (n4 <= n5) {
                int n6 = this.makeHashCode(nArray, n4);
                this.addEntry(nArray, null, n4, n6, n4);
                ++n4;
            }
        }

        void extend(char[] cArray, int n, int n2, int n3) {
            int n4 = n2 - this.blockLength;
            n4 = n4 >= n ? ++n4 : n;
            int n5 = n3 - this.blockLength;
            while (n4 <= n5) {
                int n6 = this.makeHashCode(cArray, n4);
                this.addEntry(null, cArray, n4, n6, n4);
                ++n4;
            }
        }

        int findBlock(int[] nArray, int[] nArray2, int n) {
            int n2 = this.makeHashCode(nArray2, n);
            int n3 = this.findEntry(nArray, null, nArray2, null, n, n2);
            if (n3 >= 0) {
                return (this.table[n3] & this.mask) - 1;
            }
            return 1;
        }

        int findBlock(char[] cArray, int[] nArray, int n) {
            int n2 = this.makeHashCode(nArray, n);
            int n3 = this.findEntry(null, cArray, nArray, null, n, n2);
            if (n3 >= 0) {
                return (this.table[n3] & this.mask) - 1;
            }
            return 1;
        }

        int findBlock(char[] cArray, char[] cArray2, int n) {
            int n2 = this.makeHashCode(cArray2, n);
            int n3 = this.findEntry(null, cArray, null, cArray2, n, n2);
            if (n3 >= 0) {
                return (this.table[n3] & this.mask) - 1;
            }
            return 1;
        }

        int findAllSameBlock(int[] nArray, int n) {
            int n2 = this.makeHashCode(n);
            int n3 = this.findEntry(nArray, n, n2);
            if (n3 >= 0) {
                return (this.table[n3] & this.mask) - 1;
            }
            return 1;
        }

        private int makeHashCode(int[] nArray, int n) {
            int n2 = n + this.blockLength;
            int n3 = nArray[n++];
            do {
                n3 = 37 * n3 + nArray[n++];
            } while (n < n2);
            return n3;
        }

        private int makeHashCode(char[] cArray, int n) {
            int n2 = n + this.blockLength;
            int n3 = cArray[n++];
            do {
                n3 = 37 * n3 + cArray[n++];
            } while (n < n2);
            return n3;
        }

        private int makeHashCode(int n) {
            int n2 = n;
            for (int i = 1; i < this.blockLength; ++i) {
                n2 = 37 * n2 + n;
            }
            return n2;
        }

        private void addEntry(int[] nArray, char[] cArray, int n, int n2, int n3) {
            if (!($assertionsDisabled || 0 <= n3 && n3 < this.mask)) {
                throw new AssertionError();
            }
            int n4 = this.findEntry(nArray, cArray, nArray, cArray, n, n2);
            if (n4 < 0) {
                this.table[n4 ^ 0xFFFFFFFF] = n2 << this.shift | n3 + 1;
            }
        }

        private int findEntry(int[] nArray, char[] cArray, int[] nArray2, char[] cArray2, int n, int n2) {
            int n3;
            int n4 = n2 << this.shift;
            int n5 = n3 = this.modulo(n2, this.length - 1) + 1;
            int n6;
            while ((n6 = this.table[n5]) != 0) {
                if ((n6 & ~this.mask) == n4) {
                    int n7 = (n6 & this.mask) - 1;
                    if (nArray != null ? MutableCodePointTrie.access$000(nArray, n7, nArray2, n, this.blockLength) : (nArray2 != null ? MutableCodePointTrie.access$100(cArray, n7, nArray2, n, this.blockLength) : MutableCodePointTrie.access$200(cArray, n7, cArray2, n, this.blockLength))) {
                        return n5;
                    }
                }
                n5 = this.nextIndex(n3, n5);
            }
            return ~n5;
        }

        private int findEntry(int[] nArray, int n, int n2) {
            int n3;
            int n4 = n2 << this.shift;
            int n5 = n3 = this.modulo(n2, this.length - 1) + 1;
            int n6;
            while ((n6 = this.table[n5]) != 0) {
                int n7;
                if ((n6 & ~this.mask) == n4 && MutableCodePointTrie.access$300(nArray, n7 = (n6 & this.mask) - 1, this.blockLength, n)) {
                    return n5;
                }
                n5 = this.nextIndex(n3, n5);
            }
            return ~n5;
        }

        private int nextIndex(int n, int n2) {
            return (n2 + n) % this.length;
        }

        private int modulo(int n, int n2) {
            int n3 = n % n2;
            if (n3 < 0) {
                n3 += n2;
            }
            return n3;
        }

        MixedBlocks(1 var1_1) {
            this();
        }
    }

    private static final class AllSameBlocks {
        static final int NEW_UNIQUE = -1;
        static final int OVERFLOW = -2;
        private static final int CAPACITY = 32;
        private int length;
        private int mostRecent = -1;
        private int[] indexes = new int[32];
        private int[] values = new int[32];
        private int[] refCounts = new int[32];
        static final boolean $assertionsDisabled = !MutableCodePointTrie.class.desiredAssertionStatus();

        AllSameBlocks() {
        }

        int findOrAdd(int n, int n2, int n3) {
            if (this.mostRecent >= 0 && this.values[this.mostRecent] == n3) {
                int n4 = this.mostRecent;
                this.refCounts[n4] = this.refCounts[n4] + n2;
                return this.indexes[this.mostRecent];
            }
            for (int i = 0; i < this.length; ++i) {
                if (this.values[i] != n3) continue;
                this.mostRecent = i;
                int n5 = i;
                this.refCounts[n5] = this.refCounts[n5] + n2;
                return this.indexes[i];
            }
            if (this.length == 32) {
                return 1;
            }
            this.mostRecent = this.length;
            this.indexes[this.length] = n;
            this.values[this.length] = n3;
            this.refCounts[this.length++] = n2;
            return 1;
        }

        void add(int n, int n2, int n3) {
            if (!$assertionsDisabled && this.length != 32) {
                throw new AssertionError();
            }
            int n4 = -1;
            int n5 = 69632;
            for (int i = 0; i < this.length; ++i) {
                if (!$assertionsDisabled && this.values[i] == n3) {
                    throw new AssertionError();
                }
                if (this.refCounts[i] >= n5) continue;
                n4 = i;
                n5 = this.refCounts[i];
            }
            if (!$assertionsDisabled && n4 < 0) {
                throw new AssertionError();
            }
            this.mostRecent = n4;
            this.indexes[n4] = n;
            this.values[n4] = n3;
            this.refCounts[n4] = n2;
        }

        int findMostUsed() {
            if (this.length == 0) {
                return 1;
            }
            int n = -1;
            int n2 = 0;
            for (int i = 0; i < this.length; ++i) {
                if (this.refCounts[i] <= n2) continue;
                n = i;
                n2 = this.refCounts[i];
            }
            return this.indexes[n];
        }
    }
}

