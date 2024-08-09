/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.coll.CollationIterator;
import com.ibm.icu.impl.coll.CollationSettings;

public final class CollationKeys {
    public static final LevelCallback SIMPLE_LEVEL_FALLBACK;
    private static final int SEC_COMMON_LOW = 5;
    private static final int SEC_COMMON_MIDDLE = 37;
    static final int SEC_COMMON_HIGH = 69;
    private static final int SEC_COMMON_MAX_COUNT = 33;
    private static final int CASE_LOWER_FIRST_COMMON_LOW = 1;
    private static final int CASE_LOWER_FIRST_COMMON_MIDDLE = 7;
    private static final int CASE_LOWER_FIRST_COMMON_HIGH = 13;
    private static final int CASE_LOWER_FIRST_COMMON_MAX_COUNT = 7;
    private static final int CASE_UPPER_FIRST_COMMON_LOW = 3;
    private static final int CASE_UPPER_FIRST_COMMON_HIGH = 15;
    private static final int CASE_UPPER_FIRST_COMMON_MAX_COUNT = 13;
    private static final int TER_ONLY_COMMON_LOW = 5;
    private static final int TER_ONLY_COMMON_MIDDLE = 101;
    private static final int TER_ONLY_COMMON_HIGH = 197;
    private static final int TER_ONLY_COMMON_MAX_COUNT = 97;
    private static final int TER_LOWER_FIRST_COMMON_LOW = 5;
    private static final int TER_LOWER_FIRST_COMMON_MIDDLE = 37;
    private static final int TER_LOWER_FIRST_COMMON_HIGH = 69;
    private static final int TER_LOWER_FIRST_COMMON_MAX_COUNT = 33;
    private static final int TER_UPPER_FIRST_COMMON_LOW = 133;
    private static final int TER_UPPER_FIRST_COMMON_MIDDLE = 165;
    private static final int TER_UPPER_FIRST_COMMON_HIGH = 197;
    private static final int TER_UPPER_FIRST_COMMON_MAX_COUNT = 33;
    private static final int QUAT_COMMON_LOW = 28;
    private static final int QUAT_COMMON_MIDDLE = 140;
    private static final int QUAT_COMMON_HIGH = 252;
    private static final int QUAT_COMMON_MAX_COUNT = 113;
    private static final int QUAT_SHIFTED_LIMIT_BYTE = 27;
    private static final int[] levelMasks;
    static final boolean $assertionsDisabled;

    private static SortKeyLevel getSortKeyLevel(int n, int n2) {
        return (n & n2) != 0 ? new SortKeyLevel() : null;
    }

    private CollationKeys() {
    }

    public static void writeSortKeyUpToQuaternary(CollationIterator collationIterator, boolean[] blArray, CollationSettings collationSettings, SortKeyByteSink sortKeyByteSink, int n, LevelCallback levelCallback, boolean bl) {
        int n2 = collationSettings.options;
        int n3 = levelMasks[CollationSettings.getStrength(n2)];
        if ((n2 & 0x400) != 0) {
            n3 |= 8;
        }
        if ((n3 &= ~((1 << n) - 1)) == 0) {
            return;
        }
        long l = (n2 & 0xC) == 0 ? 0L : collationSettings.variableTop + 1L;
        int n4 = CollationSettings.getTertiaryMask(n2);
        byte[] byArray = new byte[3];
        SortKeyLevel sortKeyLevel = CollationKeys.getSortKeyLevel(n3, 8);
        SortKeyLevel sortKeyLevel2 = CollationKeys.getSortKeyLevel(n3, 4);
        SortKeyLevel sortKeyLevel3 = CollationKeys.getSortKeyLevel(n3, 16);
        SortKeyLevel sortKeyLevel4 = CollationKeys.getSortKeyLevel(n3, 32);
        long l2 = 0L;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        while (true) {
            int n11;
            int n12;
            int n13;
            collationIterator.clearCEsIfNoneRemaining();
            long l3 = collationIterator.nextCE();
            long l4 = l3 >>> 32;
            if (l4 < l && l4 > 0x2000000L) {
                if (n8 != 0) {
                    --n8;
                    while (n8 >= 113) {
                        sortKeyLevel4.appendByte(140);
                        n8 -= 113;
                    }
                    sortKeyLevel4.appendByte(28 + n8);
                    n8 = 0;
                }
                do {
                    if ((n3 & 0x20) != 0) {
                        if (collationSettings.hasReordering()) {
                            l4 = collationSettings.reorder(l4);
                        }
                        if ((int)l4 >>> 24 >= 27) {
                            sortKeyLevel4.appendByte(27);
                        }
                        sortKeyLevel4.appendWeight32(l4);
                    }
                    while ((l4 = (l3 = collationIterator.nextCE()) >>> 32) == 0L) {
                    }
                } while (l4 < l && l4 > 0x2000000L);
            }
            if (l4 > 1L && (n3 & 2) != 0) {
                n13 = blArray[(int)l4 >>> 24];
                if (collationSettings.hasReordering()) {
                    l4 = collationSettings.reorder(l4);
                }
                n12 = (int)l4 >>> 24;
                if (n13 == 0 || n12 != (int)l2 >>> 24) {
                    if (l2 != 0L) {
                        if (l4 < l2) {
                            if (n12 > 2) {
                                sortKeyByteSink.Append(3);
                            }
                        } else {
                            sortKeyByteSink.Append(255);
                        }
                    }
                    sortKeyByteSink.Append(n12);
                    l2 = n13 != 0 ? l4 : 0L;
                }
                if ((n11 = (int)((byte)(l4 >>> 16))) != 0) {
                    byArray[0] = n11;
                    byArray[1] = (byte)(l4 >>> 8);
                    byArray[2] = (byte)l4;
                    sortKeyByteSink.Append(byArray, byArray[1] == 0 ? 1 : (byArray[2] == 0 ? 2 : 3));
                }
                if (!bl && sortKeyByteSink.Overflowed()) {
                    return;
                }
            }
            if ((n13 = (int)l3) == 0) continue;
            if ((n3 & 4) != 0 && (n12 = n13 >>> 16) != 0) {
                if (n12 == 1280 && ((n2 & 0x800) == 0 || l4 != 0x2000000L)) {
                    ++n6;
                } else if ((n2 & 0x800) == 0) {
                    if (n6 != 0) {
                        --n6;
                        while (n6 >= 33) {
                            sortKeyLevel2.appendByte(37);
                            n6 -= 33;
                        }
                        n11 = n12 < 1280 ? 5 + n6 : 69 - n6;
                        sortKeyLevel2.appendByte(n11);
                        n6 = 0;
                    }
                    sortKeyLevel2.appendWeight16(n12);
                } else {
                    int n14;
                    if (n6 != 0) {
                        n11 = --n6 % 33;
                        n14 = n9 < 1280 ? 5 + n11 : 69 - n11;
                        sortKeyLevel2.appendByte(n14);
                        n6 -= n11;
                        while (n6 > 0) {
                            sortKeyLevel2.appendByte(37);
                            n6 -= 33;
                        }
                    }
                    if (0L < l4 && l4 <= 0x2000000L) {
                        byte[] byArray2 = sortKeyLevel2.data();
                        n14 = sortKeyLevel2.length() - 1;
                        while (n10 < n14) {
                            byte by = byArray2[n10];
                            byArray2[n10++] = byArray2[n14];
                            byArray2[n14--] = by;
                        }
                        sortKeyLevel2.appendByte(l4 == 1L ? 1 : 2);
                        n9 = 0;
                        n10 = sortKeyLevel2.length();
                    } else {
                        sortKeyLevel2.appendReverseWeight16(n12);
                        n9 = n12;
                    }
                }
            }
            if ((n3 & 8) != 0 && !(CollationSettings.getStrength(n2) == 0 ? l4 == 0L : n13 >>> 16 == 0)) {
                n12 = n13 >>> 8 & 0xFF;
                if (!$assertionsDisabled && (n12 & 0xC0) == 192) {
                    throw new AssertionError();
                }
                if ((n12 & 0xC0) == 0 && n12 > 1) {
                    ++n5;
                } else {
                    if ((n2 & 0x100) == 0) {
                        if (!(n5 == 0 || n12 <= 1 && sortKeyLevel.isEmpty())) {
                            --n5;
                            while (n5 >= 7) {
                                sortKeyLevel.appendByte(112);
                                n5 -= 7;
                            }
                            int n15 = n12 <= 1 ? 1 + n5 : 13 - n5;
                            sortKeyLevel.appendByte(n15 << 4);
                            n5 = 0;
                        }
                        if (n12 > 1) {
                            n12 = 13 + (n12 >>> 6) << 4;
                        }
                    } else {
                        if (n5 != 0) {
                            --n5;
                            while (n5 >= 13) {
                                sortKeyLevel.appendByte(48);
                                n5 -= 13;
                            }
                            sortKeyLevel.appendByte(3 + n5 << 4);
                            n5 = 0;
                        }
                        if (n12 > 1) {
                            n12 = 3 - (n12 >>> 6) << 4;
                        }
                    }
                    sortKeyLevel.appendByte(n12);
                }
            }
            if ((n3 & 0x10) != 0) {
                n12 = n13 & n4;
                if (!$assertionsDisabled && (n13 & 0xC000) == 49152) {
                    throw new AssertionError();
                }
                if (n12 == 1280) {
                    ++n7;
                } else if ((n4 & 0x8000) == 0) {
                    if (n7 != 0) {
                        --n7;
                        while (n7 >= 97) {
                            sortKeyLevel3.appendByte(101);
                            n7 -= 97;
                        }
                        int n16 = n12 < 1280 ? 5 + n7 : 197 - n7;
                        sortKeyLevel3.appendByte(n16);
                        n7 = 0;
                    }
                    if (n12 > 1280) {
                        n12 += 49152;
                    }
                    sortKeyLevel3.appendWeight16(n12);
                } else if ((n2 & 0x100) == 0) {
                    if (n7 != 0) {
                        --n7;
                        while (n7 >= 33) {
                            sortKeyLevel3.appendByte(37);
                            n7 -= 33;
                        }
                        int n17 = n12 < 1280 ? 5 + n7 : 69 - n7;
                        sortKeyLevel3.appendByte(n17);
                        n7 = 0;
                    }
                    if (n12 > 1280) {
                        n12 += 16384;
                    }
                    sortKeyLevel3.appendWeight16(n12);
                } else {
                    if (n12 > 256) {
                        if (n13 >>> 16 != 0) {
                            if ((n12 ^= 0xC000) < 50432) {
                                n12 -= 16384;
                            }
                        } else {
                            if (!($assertionsDisabled || 34304 <= n12 && n12 <= 49151)) {
                                throw new AssertionError();
                            }
                            n12 += 16384;
                        }
                    }
                    if (n7 != 0) {
                        --n7;
                        while (n7 >= 33) {
                            sortKeyLevel3.appendByte(165);
                            n7 -= 33;
                        }
                        int n18 = n12 < 34048 ? 133 + n7 : 197 - n7;
                        sortKeyLevel3.appendByte(n18);
                        n7 = 0;
                    }
                    sortKeyLevel3.appendWeight16(n12);
                }
            }
            if ((n3 & 0x20) != 0) {
                n12 = n13 & 0xFFFF;
                if ((n12 & 0xC0) == 0 && n12 > 256) {
                    ++n8;
                } else if (n12 == 256 && (n2 & 0xC) == 0 && sortKeyLevel4.isEmpty()) {
                    sortKeyLevel4.appendByte(1);
                } else {
                    n12 = n12 == 256 ? 1 : 252 + (n12 >>> 6 & 3);
                    if (n8 != 0) {
                        --n8;
                        while (n8 >= 113) {
                            sortKeyLevel4.appendByte(140);
                            n8 -= 113;
                        }
                        int n19 = n12 < 28 ? 28 + n8 : 252 - n8;
                        sortKeyLevel4.appendByte(n19);
                        n8 = 0;
                    }
                    sortKeyLevel4.appendByte(n12);
                }
            }
            if (n13 >>> 24 == 1) break;
        }
        if ((n3 & 4) != 0) {
            if (!levelCallback.needToWrite(1)) {
                return;
            }
            sortKeyByteSink.Append(1);
            sortKeyLevel2.appendTo(sortKeyByteSink);
        }
        if ((n3 & 8) != 0) {
            if (!levelCallback.needToWrite(0)) {
                return;
            }
            sortKeyByteSink.Append(1);
            int n20 = sortKeyLevel.length() - 1;
            int n21 = 0;
            for (int i = 0; i < n20; ++i) {
                byte by = sortKeyLevel.getAt(i);
                if (!($assertionsDisabled || (by & 0xF) == 0 && by != 0)) {
                    throw new AssertionError();
                }
                if (n21 == 0) {
                    n21 = by;
                    continue;
                }
                sortKeyByteSink.Append(n21 | by >> 4 & 0xF);
                n21 = 0;
            }
            if (n21 != 0) {
                sortKeyByteSink.Append(n21);
            }
        }
        if ((n3 & 0x10) != 0) {
            if (!levelCallback.needToWrite(1)) {
                return;
            }
            sortKeyByteSink.Append(1);
            sortKeyLevel3.appendTo(sortKeyByteSink);
        }
        if ((n3 & 0x20) != 0) {
            if (!levelCallback.needToWrite(0)) {
                return;
            }
            sortKeyByteSink.Append(1);
            sortKeyLevel4.appendTo(sortKeyByteSink);
        }
    }

    static {
        $assertionsDisabled = !CollationKeys.class.desiredAssertionStatus();
        SIMPLE_LEVEL_FALLBACK = new LevelCallback();
        levelMasks = new int[]{2, 6, 22, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 54};
    }

    private static final class SortKeyLevel {
        private static final int INITIAL_CAPACITY = 40;
        byte[] buffer = new byte[40];
        int len = 0;
        static final boolean $assertionsDisabled = !CollationKeys.class.desiredAssertionStatus();

        SortKeyLevel() {
        }

        boolean isEmpty() {
            return this.len == 0;
        }

        int length() {
            return this.len;
        }

        byte getAt(int n) {
            return this.buffer[n];
        }

        byte[] data() {
            return this.buffer;
        }

        void appendByte(int n) {
            if (this.len < this.buffer.length || this.ensureCapacity(1)) {
                this.buffer[this.len++] = (byte)n;
            }
        }

        void appendWeight16(int n) {
            int n2;
            if (!$assertionsDisabled && (n & 0xFFFF) == 0) {
                throw new AssertionError();
            }
            byte by = (byte)(n >>> 8);
            byte by2 = (byte)n;
            int n3 = n2 = by2 == 0 ? 1 : 2;
            if (this.len + n2 <= this.buffer.length || this.ensureCapacity(n2)) {
                this.buffer[this.len++] = by;
                if (by2 != 0) {
                    this.buffer[this.len++] = by2;
                }
            }
        }

        void appendWeight32(long l) {
            int n;
            if (!$assertionsDisabled && l == 0L) {
                throw new AssertionError();
            }
            byte[] byArray = new byte[]{(byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)l};
            int n2 = byArray[1] == 0 ? 1 : (byArray[2] == 0 ? 2 : (n = byArray[3] == 0 ? 3 : 4));
            if (this.len + n <= this.buffer.length || this.ensureCapacity(n)) {
                this.buffer[this.len++] = byArray[0];
                if (byArray[1] != 0) {
                    this.buffer[this.len++] = byArray[1];
                    if (byArray[2] != 0) {
                        this.buffer[this.len++] = byArray[2];
                        if (byArray[3] != 0) {
                            this.buffer[this.len++] = byArray[3];
                        }
                    }
                }
            }
        }

        void appendReverseWeight16(int n) {
            int n2;
            if (!$assertionsDisabled && (n & 0xFFFF) == 0) {
                throw new AssertionError();
            }
            byte by = (byte)(n >>> 8);
            byte by2 = (byte)n;
            int n3 = n2 = by2 == 0 ? 1 : 2;
            if (this.len + n2 <= this.buffer.length || this.ensureCapacity(n2)) {
                if (by2 == 0) {
                    this.buffer[this.len++] = by;
                } else {
                    this.buffer[this.len] = by2;
                    this.buffer[this.len + 1] = by;
                    this.len += 2;
                }
            }
        }

        void appendTo(SortKeyByteSink sortKeyByteSink) {
            if (!($assertionsDisabled || this.len > 0 && this.buffer[this.len - 1] == 1)) {
                throw new AssertionError();
            }
            sortKeyByteSink.Append(this.buffer, this.len - 1);
        }

        private boolean ensureCapacity(int n) {
            int n2 = 2 * this.buffer.length;
            int n3 = this.len + 2 * n;
            if (n2 < n3) {
                n2 = n3;
            }
            if (n2 < 200) {
                n2 = 200;
            }
            byte[] byArray = new byte[n2];
            System.arraycopy(this.buffer, 0, byArray, 0, this.len);
            this.buffer = byArray;
            return false;
        }
    }

    public static class LevelCallback {
        boolean needToWrite(int n) {
            return false;
        }
    }

    public static abstract class SortKeyByteSink {
        protected byte[] buffer_;
        private int appended_ = 0;

        public SortKeyByteSink(byte[] byArray) {
            this.buffer_ = byArray;
        }

        public void setBufferAndAppended(byte[] byArray, int n) {
            this.buffer_ = byArray;
            this.appended_ = n;
        }

        public void Append(byte[] byArray, int n) {
            if (n <= 0 || byArray == null) {
                return;
            }
            int n2 = this.appended_;
            this.appended_ += n;
            int n3 = this.buffer_.length - n2;
            if (n <= n3) {
                System.arraycopy(byArray, 0, this.buffer_, n2, n);
            } else {
                this.AppendBeyondCapacity(byArray, 0, n, n2);
            }
        }

        public void Append(int n) {
            if (this.appended_ < this.buffer_.length || this.Resize(1, this.appended_)) {
                this.buffer_[this.appended_] = (byte)n;
            }
            ++this.appended_;
        }

        public int NumberOfBytesAppended() {
            return this.appended_;
        }

        public int GetRemainingCapacity() {
            return this.buffer_.length - this.appended_;
        }

        public boolean Overflowed() {
            return this.appended_ > this.buffer_.length;
        }

        protected abstract void AppendBeyondCapacity(byte[] var1, int var2, int var3, int var4);

        protected abstract boolean Resize(int var1, int var2);
    }
}

