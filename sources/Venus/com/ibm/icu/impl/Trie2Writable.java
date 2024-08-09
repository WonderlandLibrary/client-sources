/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.Trie2_16;
import com.ibm.icu.impl.Trie2_32;

public class Trie2Writable
extends Trie2 {
    private static final int UTRIE2_MAX_INDEX_LENGTH = 65535;
    private static final int UTRIE2_MAX_DATA_LENGTH = 262140;
    private static final int UNEWTRIE2_INITIAL_DATA_LENGTH = 16384;
    private static final int UNEWTRIE2_MEDIUM_DATA_LENGTH = 131072;
    private static final int UNEWTRIE2_INDEX_2_NULL_OFFSET = 2656;
    private static final int UNEWTRIE2_INDEX_2_START_OFFSET = 2720;
    private static final int UNEWTRIE2_DATA_NULL_OFFSET = 192;
    private static final int UNEWTRIE2_DATA_START_OFFSET = 256;
    private static final int UNEWTRIE2_DATA_0800_OFFSET = 2176;
    private int[] index1 = new int[544];
    private int[] index2 = new int[35488];
    private int[] data;
    private int index2Length;
    private int dataCapacity;
    private int firstFreeBlock;
    private int index2NullOffset;
    private boolean isCompacted;
    private int[] map = new int[34852];
    private boolean UTRIE2_DEBUG = false;
    static final boolean $assertionsDisabled = !Trie2Writable.class.desiredAssertionStatus();

    public Trie2Writable(int n, int n2) {
        this.init(n, n2);
    }

    private void init(int n, int n2) {
        int n3;
        int n4;
        this.initialValue = n;
        this.errorValue = n2;
        this.highStart = 0x110000;
        this.data = new int[16384];
        this.dataCapacity = 16384;
        this.initialValue = n;
        this.errorValue = n2;
        this.highStart = 0x110000;
        this.firstFreeBlock = 0;
        this.isCompacted = false;
        for (n4 = 0; n4 < 128; ++n4) {
            this.data[n4] = this.initialValue;
        }
        while (n4 < 192) {
            this.data[n4] = this.errorValue;
            ++n4;
        }
        for (n4 = 192; n4 < 256; ++n4) {
            this.data[n4] = this.initialValue;
        }
        this.dataNullOffset = 192;
        this.dataLength = 256;
        n4 = 0;
        for (n3 = 0; n3 < 128; n3 += 32) {
            this.index2[n4] = n3;
            this.map[n4] = 1;
            ++n4;
        }
        while (n3 < 192) {
            this.map[n4] = 0;
            ++n4;
            n3 += 32;
        }
        this.map[n4++] = 34845;
        n3 += 32;
        while (n3 < 256) {
            this.map[n4] = 0;
            ++n4;
            n3 += 32;
        }
        for (n4 = 4; n4 < 2080; ++n4) {
            this.index2[n4] = 192;
        }
        for (n4 = 0; n4 < 576; ++n4) {
            this.index2[2080 + n4] = -1;
        }
        for (n4 = 0; n4 < 64; ++n4) {
            this.index2[2656 + n4] = 192;
        }
        this.index2NullOffset = 2656;
        this.index2Length = 2720;
        n4 = 0;
        n3 = 0;
        while (n4 < 32) {
            this.index1[n4] = n3;
            ++n4;
            n3 += 64;
        }
        while (n4 < 544) {
            this.index1[n4] = 2656;
            ++n4;
        }
        for (n4 = 128; n4 < 2048; n4 += 32) {
            this.set(n4, this.initialValue);
        }
    }

    public Trie2Writable(Trie2 trie2) {
        this.init(trie2.initialValue, trie2.errorValue);
        for (Trie2.Range range : trie2) {
            this.setRange(range, false);
        }
    }

    private boolean isInNullBlock(int n, boolean bl) {
        int n2 = Character.isHighSurrogate((char)n) && bl ? 320 + (n >> 5) : this.index1[n >> 11] + (n >> 5 & 0x3F);
        int n3 = this.index2[n2];
        return n3 == this.dataNullOffset;
    }

    private int allocIndex2Block() {
        int n = this.index2Length;
        int n2 = n + 64;
        if (n2 > this.index2.length) {
            throw new IllegalStateException("Internal error in Trie2 creation.");
        }
        this.index2Length = n2;
        System.arraycopy(this.index2, this.index2NullOffset, this.index2, n, 64);
        return n;
    }

    private int getIndex2Block(int n, boolean bl) {
        if (n >= 55296 && n < 56320 && bl) {
            return 1;
        }
        int n2 = n >> 11;
        int n3 = this.index1[n2];
        if (n3 == this.index2NullOffset) {
            this.index1[n2] = n3 = this.allocIndex2Block();
        }
        return n3;
    }

    private int allocDataBlock(int n) {
        int n2;
        if (this.firstFreeBlock != 0) {
            n2 = this.firstFreeBlock;
            this.firstFreeBlock = -this.map[n2 >> 5];
        } else {
            n2 = this.dataLength;
            int n3 = n2 + 32;
            if (n3 > this.dataCapacity) {
                int n4;
                if (this.dataCapacity < 131072) {
                    n4 = 131072;
                } else if (this.dataCapacity < 1115264) {
                    n4 = 1115264;
                } else {
                    throw new IllegalStateException("Internal error in Trie2 creation.");
                }
                int[] nArray = new int[n4];
                System.arraycopy(this.data, 0, nArray, 0, this.dataLength);
                this.data = nArray;
                this.dataCapacity = n4;
            }
            this.dataLength = n3;
        }
        System.arraycopy(this.data, n, this.data, n2, 32);
        this.map[n2 >> 5] = 0;
        return n2;
    }

    private void releaseDataBlock(int n) {
        this.map[n >> 5] = -this.firstFreeBlock;
        this.firstFreeBlock = n;
    }

    private boolean isWritableBlock(int n) {
        return n != this.dataNullOffset && 1 == this.map[n >> 5];
    }

    private void setIndex2Entry(int n, int n2) {
        int n3 = n2 >> 5;
        this.map[n3] = this.map[n3] + 1;
        int n4 = this.index2[n];
        int n5 = n4 >> 5;
        this.map[n5] = this.map[n5] - 1;
        if (0 == this.map[n5]) {
            this.releaseDataBlock(n4);
        }
        this.index2[n] = n2;
    }

    private int getDataBlock(int n, boolean bl) {
        int n2 = this.getIndex2Block(n, bl);
        int n3 = this.index2[n2 += n >> 5 & 0x3F];
        if (this.isWritableBlock(n3)) {
            return n3;
        }
        int n4 = this.allocDataBlock(n3);
        this.setIndex2Entry(n2, n4);
        return n4;
    }

    public Trie2Writable set(int n, int n2) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Invalid code point.");
        }
        this.set(n, true, n2);
        this.fHash = 0;
        return this;
    }

    private Trie2Writable set(int n, boolean bl, int n2) {
        if (this.isCompacted) {
            this.uncompact();
        }
        int n3 = this.getDataBlock(n, bl);
        this.data[n3 + (n & 0x1F)] = n2;
        return this;
    }

    private void uncompact() {
        Trie2Writable trie2Writable = new Trie2Writable(this);
        this.index1 = trie2Writable.index1;
        this.index2 = trie2Writable.index2;
        this.data = trie2Writable.data;
        this.index2Length = trie2Writable.index2Length;
        this.dataCapacity = trie2Writable.dataCapacity;
        this.isCompacted = trie2Writable.isCompacted;
        this.header = trie2Writable.header;
        this.index = trie2Writable.index;
        this.data16 = trie2Writable.data16;
        this.data32 = trie2Writable.data32;
        this.indexLength = trie2Writable.indexLength;
        this.dataLength = trie2Writable.dataLength;
        this.index2NullOffset = trie2Writable.index2NullOffset;
        this.initialValue = trie2Writable.initialValue;
        this.errorValue = trie2Writable.errorValue;
        this.highStart = trie2Writable.highStart;
        this.highValueIndex = trie2Writable.highValueIndex;
        this.dataNullOffset = trie2Writable.dataNullOffset;
    }

    private void writeBlock(int n, int n2) {
        int n3 = n + 32;
        while (n < n3) {
            this.data[n++] = n2;
        }
    }

    private void fillBlock(int n, int n2, int n3, int n4, int n5, boolean bl) {
        int n6 = n + n3;
        if (bl) {
            for (int i = n + n2; i < n6; ++i) {
                this.data[i] = n4;
            }
        } else {
            for (int i = n + n2; i < n6; ++i) {
                if (this.data[i] != n5) continue;
                this.data[i] = n4;
            }
        }
    }

    public Trie2Writable setRange(int n, int n2, int n3, boolean bl) {
        int n4;
        int n5;
        if (n > 0x10FFFF || n < 0 || n2 > 0x10FFFF || n2 < 0 || n > n2) {
            throw new IllegalArgumentException("Invalid code point range.");
        }
        if (!bl && n3 == this.initialValue) {
            return this;
        }
        this.fHash = 0;
        if (this.isCompacted) {
            this.uncompact();
        }
        int n6 = n2 + 1;
        if ((n & 0x1F) != 0) {
            n5 = this.getDataBlock(n, true);
            n4 = n + 32 & 0xFFFFFFE0;
            if (n4 <= n6) {
                this.fillBlock(n5, n & 0x1F, 32, n3, this.initialValue, bl);
                n = n4;
            } else {
                this.fillBlock(n5, n & 0x1F, n6 & 0x1F, n3, this.initialValue, bl);
                return this;
            }
        }
        int n7 = n6 & 0x1F;
        n6 &= 0xFFFFFFE0;
        int n8 = n3 == this.initialValue ? this.dataNullOffset : -1;
        while (n < n6) {
            boolean bl2 = false;
            if (n3 == this.initialValue && this.isInNullBlock(n, true)) {
                n += 32;
                continue;
            }
            n4 = this.getIndex2Block(n, true);
            n5 = this.index2[n4 += n >> 5 & 0x3F];
            if (this.isWritableBlock(n5)) {
                if (bl && n5 >= 2176) {
                    bl2 = true;
                } else {
                    this.fillBlock(n5, 0, 32, n3, this.initialValue, bl);
                }
            } else if (this.data[n5] != n3 && (bl || n5 == this.dataNullOffset)) {
                bl2 = true;
            }
            if (bl2) {
                if (n8 >= 0) {
                    this.setIndex2Entry(n4, n8);
                } else {
                    n8 = this.getDataBlock(n, true);
                    this.writeBlock(n8, n3);
                }
            }
            n += 32;
        }
        if (n7 > 0) {
            n5 = this.getDataBlock(n, true);
            this.fillBlock(n5, 0, n7, n3, this.initialValue, bl);
        }
        return this;
    }

    public Trie2Writable setRange(Trie2.Range range, boolean bl) {
        this.fHash = 0;
        if (range.leadSurrogate) {
            for (int i = range.startCodePoint; i <= range.endCodePoint; ++i) {
                if (!bl && this.getFromU16SingleLead((char)i) != this.initialValue) continue;
                this.setForLeadSurrogateCodeUnit((char)i, range.value);
            }
        } else {
            this.setRange(range.startCodePoint, range.endCodePoint, range.value, bl);
        }
        return this;
    }

    public Trie2Writable setForLeadSurrogateCodeUnit(char c, int n) {
        this.fHash = 0;
        this.set(c, false, n);
        return this;
    }

    @Override
    public int get(int n) {
        if (n < 0 || n > 0x10FFFF) {
            return this.errorValue;
        }
        return this.get(n, true);
    }

    private int get(int n, boolean bl) {
        if (n >= this.highStart && (n < 55296 || n >= 56320 || bl)) {
            return this.data[this.dataLength - 4];
        }
        int n2 = n >= 55296 && n < 56320 && bl ? 320 + (n >> 5) : this.index1[n >> 11] + (n >> 5 & 0x3F);
        int n3 = this.index2[n2];
        return this.data[n3 + (n & 0x1F)];
    }

    @Override
    public int getFromU16SingleLead(char c) {
        return this.get(c, false);
    }

    private boolean equal_int(int[] nArray, int n, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            if (nArray[n + i] == nArray[n2 + i]) continue;
            return true;
        }
        return false;
    }

    private int findSameIndex2Block(int n, int n2) {
        n -= 64;
        for (int i = 0; i <= n; ++i) {
            if (!this.equal_int(this.index2, i, n2, 64)) continue;
            return i;
        }
        return 1;
    }

    private int findSameDataBlock(int n, int n2, int n3) {
        n -= n3;
        for (int i = 0; i <= n; i += 4) {
            if (!this.equal_int(this.data, i, n2, n3)) continue;
            return i;
        }
        return 1;
    }

    private int findHighStart(int n) {
        int n2;
        int n3;
        if (n == this.initialValue) {
            n3 = this.index2NullOffset;
            n2 = this.dataNullOffset;
        } else {
            n3 = -1;
            n2 = -1;
        }
        int n4 = 0x110000;
        int n5 = 544;
        int n6 = n4;
        while (n6 > 0) {
            int n7;
            if ((n7 = this.index1[--n5]) == n3) {
                n6 -= 2048;
                continue;
            }
            n3 = n7;
            if (n7 == this.index2NullOffset) {
                if (n != this.initialValue) {
                    return n6;
                }
                n6 -= 2048;
                continue;
            }
            int n8 = 64;
            while (n8 > 0) {
                int n9;
                if ((n9 = this.index2[n7 + --n8]) == n2) {
                    n6 -= 32;
                    continue;
                }
                n2 = n9;
                if (n9 == this.dataNullOffset) {
                    if (n != this.initialValue) {
                        return n6;
                    }
                    n6 -= 32;
                    continue;
                }
                int n10 = 32;
                while (n10 > 0) {
                    int n11;
                    if ((n11 = this.data[n9 + --n10]) != n) {
                        return n6;
                    }
                    --n6;
                }
            }
        }
        return 1;
    }

    private void compactData() {
        int n = 192;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            this.map[n3] = n2;
            n2 += 32;
            ++n3;
        }
        int n4 = 64;
        int n5 = n4 >> 5;
        n2 = n;
        while (n2 < this.dataLength) {
            int n6;
            int n7;
            if (n2 == 2176) {
                n4 = 32;
                n5 = 1;
            }
            if (this.map[n2 >> 5] <= 0) {
                n2 += n4;
                continue;
            }
            int n8 = this.findSameDataBlock(n, n2, n4);
            if (n8 >= 0) {
                n7 = n2 >> 5;
                for (n3 = n5; n3 > 0; --n3) {
                    this.map[n7++] = n8;
                    n8 += 32;
                }
                n2 += n4;
                continue;
            }
            for (n6 = n4 - 4; n6 > 0 && !this.equal_int(this.data, n - n6, n2, n6); n6 -= 4) {
            }
            if (n6 > 0 || n < n2) {
                n8 = n - n6;
                n7 = n2 >> 5;
                for (n3 = n5; n3 > 0; --n3) {
                    this.map[n7++] = n8;
                    n8 += 32;
                }
                n2 += n6;
                for (n3 = n4 - n6; n3 > 0; --n3) {
                    this.data[n++] = this.data[n2++];
                }
                continue;
            }
            n7 = n2 >> 5;
            for (n3 = n5; n3 > 0; --n3) {
                this.map[n7++] = n2;
                n2 += 32;
            }
            n = n2;
        }
        for (n3 = 0; n3 < this.index2Length; ++n3) {
            if (n3 == 2080) {
                n3 += 576;
            }
            this.index2[n3] = this.map[this.index2[n3] >> 5];
        }
        this.dataNullOffset = this.map[this.dataNullOffset >> 5];
        while ((n & 3) != 0) {
            this.data[n++] = this.initialValue;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.printf("compacting UTrie2: count of 32-bit data words %d->%d%n", this.dataLength, n);
        }
        this.dataLength = n;
    }

    private void compactIndex2() {
        int n = 2080;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            this.map[n3] = n2;
            n2 += 64;
            ++n3;
        }
        n += 32 + (this.highStart - 65536 >> 11);
        n2 = 2656;
        while (n2 < this.index2Length) {
            int n4;
            int n5 = this.findSameIndex2Block(n, n2);
            if (n5 >= 0) {
                this.map[n2 >> 6] = n5;
                n2 += 64;
                continue;
            }
            for (n4 = 63; n4 > 0 && !this.equal_int(this.index2, n - n4, n2, n4); --n4) {
            }
            if (n4 > 0 || n < n2) {
                this.map[n2 >> 6] = n - n4;
                n2 += n4;
                for (n3 = 64 - n4; n3 > 0; --n3) {
                    this.index2[n++] = this.index2[n2++];
                }
                continue;
            }
            this.map[n2 >> 6] = n2;
            n = n2 += 64;
        }
        for (n3 = 0; n3 < 544; ++n3) {
            this.index1[n3] = this.map[this.index1[n3] >> 6];
        }
        this.index2NullOffset = this.map[this.index2NullOffset >> 6];
        while ((n & 3) != 0) {
            this.index2[n++] = 262140;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.printf("compacting UTrie2: count of 16-bit index-2 words %d->%d%n", this.index2Length, n);
        }
        this.index2Length = n;
    }

    private void compactTrie() {
        int n = this.get(0x10FFFF);
        int n2 = this.findHighStart(n);
        if ((n2 = n2 + 2047 & 0xFFFFF800) == 0x110000) {
            n = this.errorValue;
        }
        this.highStart = n2;
        if (this.UTRIE2_DEBUG) {
            System.out.printf("UTrie2: highStart U+%04x  highValue 0x%x  initialValue 0x%x%n", this.highStart, n, this.initialValue);
        }
        if (this.highStart < 0x110000) {
            int n3 = this.highStart <= 65536 ? 65536 : this.highStart;
            this.setRange(n3, 0x10FFFF, this.initialValue, false);
        }
        this.compactData();
        if (this.highStart > 65536) {
            this.compactIndex2();
        } else if (this.UTRIE2_DEBUG) {
            System.out.printf("UTrie2: highStart U+%04x  count of 16-bit index-2 words %d->%d%n", this.highStart, this.index2Length, 2112);
        }
        this.data[this.dataLength++] = n;
        while ((this.dataLength & 3) != 0) {
            this.data[this.dataLength++] = this.initialValue;
        }
        this.isCompacted = true;
    }

    public Trie2_16 toTrie2_16() {
        Trie2_16 trie2_16 = new Trie2_16();
        this.freeze(trie2_16, Trie2.ValueWidth.BITS_16);
        return trie2_16;
    }

    public Trie2_32 toTrie2_32() {
        Trie2_32 trie2_32 = new Trie2_32();
        this.freeze(trie2_32, Trie2.ValueWidth.BITS_32);
        return trie2_32;
    }

    private void freeze(Trie2 trie2, Trie2.ValueWidth valueWidth) {
        int n;
        if (!this.isCompacted) {
            this.compactTrie();
        }
        int n2 = this.highStart <= 65536 ? 2112 : this.index2Length;
        int n3 = valueWidth == Trie2.ValueWidth.BITS_16 ? n2 : 0;
        if (n2 > 65535 || n3 + this.dataNullOffset > 65535 || n3 + 2176 > 65535 || n3 + this.dataLength > 262140) {
            throw new UnsupportedOperationException("Trie2 data is too large.");
        }
        int n4 = n2;
        if (valueWidth == Trie2.ValueWidth.BITS_16) {
            n4 += this.dataLength;
        } else {
            trie2.data32 = new int[this.dataLength];
        }
        trie2.index = new char[n4];
        trie2.indexLength = n2;
        trie2.dataLength = this.dataLength;
        trie2.index2NullOffset = this.highStart <= 65536 ? 65535 : 0 + this.index2NullOffset;
        trie2.initialValue = this.initialValue;
        trie2.errorValue = this.errorValue;
        trie2.highStart = this.highStart;
        trie2.highValueIndex = n3 + this.dataLength - 4;
        trie2.dataNullOffset = n3 + this.dataNullOffset;
        trie2.header = new Trie2.UTrie2Header();
        trie2.header.signature = 1416784178;
        trie2.header.options = valueWidth == Trie2.ValueWidth.BITS_16 ? 0 : 1;
        trie2.header.indexLength = trie2.indexLength;
        trie2.header.shiftedDataLength = trie2.dataLength >> 2;
        trie2.header.index2NullOffset = trie2.index2NullOffset;
        trie2.header.dataNullOffset = trie2.dataNullOffset;
        trie2.header.shiftedHighStart = trie2.highStart >> 11;
        int n5 = 0;
        for (n = 0; n < 2080; ++n) {
            trie2.index[n5++] = (char)(this.index2[n] + n3 >> 2);
        }
        if (this.UTRIE2_DEBUG) {
            System.out.println("\n\nIndex2 for BMP limit is " + Integer.toHexString(n5));
        }
        for (n = 0; n < 2; ++n) {
            trie2.index[n5++] = (char)(n3 + 128);
        }
        while (n < 32) {
            trie2.index[n5++] = (char)(n3 + this.index2[n << 1]);
            ++n;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.println("Index2 for UTF-8 2byte values limit is " + Integer.toHexString(n5));
        }
        if (this.highStart > 65536) {
            int n6 = this.highStart - 65536 >> 11;
            int n7 = 2112 + n6;
            for (n = 0; n < n6; ++n) {
                trie2.index[n5++] = (char)(0 + this.index1[n + 32]);
            }
            if (this.UTRIE2_DEBUG) {
                System.out.println("Index 1 for supplementals, limit is " + Integer.toHexString(n5));
            }
            for (n = 0; n < this.index2Length - n7; ++n) {
                trie2.index[n5++] = (char)(n3 + this.index2[n7 + n] >> 2);
            }
            if (this.UTRIE2_DEBUG) {
                System.out.println("Index 2 for supplementals, limit is " + Integer.toHexString(n5));
            }
        }
        switch (1.$SwitchMap$com$ibm$icu$impl$Trie2$ValueWidth[valueWidth.ordinal()]) {
            case 1: {
                if (!$assertionsDisabled && n5 != n3) {
                    throw new AssertionError();
                }
                trie2.data16 = n5;
                for (n = 0; n < this.dataLength; ++n) {
                    trie2.index[n5++] = (char)this.data[n];
                }
                break;
            }
            case 2: {
                for (n = 0; n < this.dataLength; ++n) {
                    trie2.data32[n] = this.data[n];
                }
                break;
            }
        }
    }
}

