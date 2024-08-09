/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.SharedObject;
import com.ibm.icu.impl.coll.UVector32;
import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class CollationSettings
extends SharedObject {
    public static final int CHECK_FCD = 1;
    public static final int NUMERIC = 2;
    static final int SHIFTED = 4;
    static final int ALTERNATE_MASK = 12;
    static final int MAX_VARIABLE_SHIFT = 4;
    static final int MAX_VARIABLE_MASK = 112;
    static final int UPPER_FIRST = 256;
    public static final int CASE_FIRST = 512;
    public static final int CASE_FIRST_AND_UPPER_MASK = 768;
    public static final int CASE_LEVEL = 1024;
    public static final int BACKWARD_SECONDARY = 2048;
    static final int STRENGTH_SHIFT = 12;
    static final int STRENGTH_MASK = 61440;
    static final int MAX_VAR_SPACE = 0;
    static final int MAX_VAR_PUNCT = 1;
    static final int MAX_VAR_SYMBOL = 2;
    static final int MAX_VAR_CURRENCY = 3;
    public int options = 8208;
    public long variableTop;
    public byte[] reorderTable;
    long minHighNoReorder;
    long[] reorderRanges;
    public int[] reorderCodes = EMPTY_INT_ARRAY;
    private static final int[] EMPTY_INT_ARRAY;
    public int fastLatinOptions = -1;
    public char[] fastLatinPrimaries = new char[384];
    static final boolean $assertionsDisabled;

    CollationSettings() {
    }

    @Override
    public CollationSettings clone() {
        CollationSettings collationSettings = (CollationSettings)super.clone();
        collationSettings.fastLatinPrimaries = (char[])this.fastLatinPrimaries.clone();
        return collationSettings;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (!this.getClass().equals(object.getClass())) {
            return true;
        }
        CollationSettings collationSettings = (CollationSettings)object;
        if (this.options != collationSettings.options) {
            return true;
        }
        if ((this.options & 0xC) != 0 && this.variableTop != collationSettings.variableTop) {
            return true;
        }
        return !Arrays.equals(this.reorderCodes, collationSettings.reorderCodes);
    }

    public int hashCode() {
        int n = this.options << 8;
        if ((this.options & 0xC) != 0) {
            n = (int)((long)n ^ this.variableTop);
        }
        n ^= this.reorderCodes.length;
        for (int i = 0; i < this.reorderCodes.length; ++i) {
            n ^= this.reorderCodes[i] << i;
        }
        return n;
    }

    public void resetReordering() {
        this.reorderTable = null;
        this.minHighNoReorder = 0L;
        this.reorderRanges = null;
        this.reorderCodes = EMPTY_INT_ARRAY;
    }

    void aliasReordering(CollationData collationData, int[] nArray, int n, byte[] byArray) {
        int[] nArray2 = n == nArray.length ? nArray : Arrays.copyOf(nArray, n);
        int n2 = n;
        int n3 = nArray.length;
        int n4 = n3 - n2;
        if (byArray != null && (n4 == 0 ? !CollationSettings.reorderTableHasSplitBytes(byArray) : n4 >= 2 && (nArray[n2] & 0xFFFF) == 0 && (nArray[n3 - 1] & 0xFFFF) != 0)) {
            int n5;
            this.reorderTable = byArray;
            this.reorderCodes = nArray2;
            for (n5 = n2; n5 < n3 && (nArray[n5] & 0xFF0000) == 0; ++n5) {
            }
            if (n5 == n3) {
                if (!$assertionsDisabled && CollationSettings.reorderTableHasSplitBytes(byArray)) {
                    throw new AssertionError();
                }
                this.minHighNoReorder = 0L;
                this.reorderRanges = null;
            } else {
                if (!$assertionsDisabled && byArray[nArray[n5] >>> 24] != 0) {
                    throw new AssertionError();
                }
                this.minHighNoReorder = (long)nArray[n3 - 1] & 0xFFFF0000L;
                this.setReorderRanges(nArray, n5, n3 - n5);
            }
            return;
        }
        this.setReordering(collationData, nArray2);
    }

    public void setReordering(CollationData collationData, int[] nArray) {
        int n;
        if (nArray.length == 0 || nArray.length == 1 && nArray[0] == 103) {
            this.resetReordering();
            return;
        }
        UVector32 uVector32 = new UVector32();
        collationData.makeReorderRanges(nArray, uVector32);
        int n2 = uVector32.size();
        if (n2 == 0) {
            this.resetReordering();
            return;
        }
        int[] nArray2 = uVector32.getBuffer();
        if (!$assertionsDisabled && n2 < 2) {
            throw new AssertionError();
        }
        if (!($assertionsDisabled || (nArray2[0] & 0xFFFF) == 0 && (nArray2[n2 - 1] & 0xFFFF) != 0)) {
            throw new AssertionError();
        }
        this.minHighNoReorder = (long)nArray2[n2 - 1] & 0xFFFF0000L;
        byte[] byArray = new byte[256];
        int n3 = 0;
        int n4 = -1;
        for (n = 0; n < n2; ++n) {
            int n5 = nArray2[n];
            int n6 = n5 >>> 24;
            while (n3 < n6) {
                byArray[n3] = (byte)(n3 + n5);
                ++n3;
            }
            if ((n5 & 0xFF0000) == 0) continue;
            byArray[n6] = 0;
            n3 = n6 + 1;
            if (n4 >= 0) continue;
            n4 = n;
        }
        while (n3 <= 255) {
            byArray[n3] = (byte)n3;
            ++n3;
        }
        if (n4 < 0) {
            n2 = 0;
            n = 0;
        } else {
            n = n4;
            n2 -= n4;
        }
        this.setReorderArrays(nArray, nArray2, n, n2, byArray);
    }

    private void setReorderArrays(int[] nArray, int[] nArray2, int n, int n2, byte[] byArray) {
        if (nArray == null) {
            nArray = EMPTY_INT_ARRAY;
        }
        if (!$assertionsDisabled && nArray.length == 0 != (byArray == null)) {
            throw new AssertionError();
        }
        this.reorderTable = byArray;
        this.reorderCodes = nArray;
        this.setReorderRanges(nArray2, n, n2);
    }

    private void setReorderRanges(int[] nArray, int n, int n2) {
        if (n2 == 0) {
            this.reorderRanges = null;
        } else {
            this.reorderRanges = new long[n2];
            int n3 = 0;
            do {
                this.reorderRanges[n3++] = (long)nArray[n++] & 0xFFFFFFFFL;
            } while (n3 < n2);
        }
    }

    public void copyReorderingFrom(CollationSettings collationSettings) {
        if (!collationSettings.hasReordering()) {
            this.resetReordering();
            return;
        }
        this.minHighNoReorder = collationSettings.minHighNoReorder;
        this.reorderTable = collationSettings.reorderTable;
        this.reorderRanges = collationSettings.reorderRanges;
        this.reorderCodes = collationSettings.reorderCodes;
    }

    public boolean hasReordering() {
        return this.reorderTable != null;
    }

    private static boolean reorderTableHasSplitBytes(byte[] byArray) {
        if (!$assertionsDisabled && byArray[0] != 0) {
            throw new AssertionError();
        }
        for (int i = 1; i < 256; ++i) {
            if (byArray[i] != 0) continue;
            return false;
        }
        return true;
    }

    public long reorder(long l) {
        byte by = this.reorderTable[(int)l >>> 24];
        if (by != 0 || l <= 1L) {
            return ((long)by & 0xFFL) << 24 | l & 0xFFFFFFL;
        }
        return this.reorderEx(l);
    }

    private long reorderEx(long l) {
        long l2;
        if (!$assertionsDisabled && this.minHighNoReorder <= 0L) {
            throw new AssertionError();
        }
        if (l >= this.minHighNoReorder) {
            return l;
        }
        long l3 = l | 0xFFFFL;
        int n = 0;
        while (l3 >= (l2 = this.reorderRanges[n])) {
            ++n;
        }
        return l + ((long)((short)l2) << 24);
    }

    public void setStrength(int n) {
        int n2 = this.options & 0xFFFF0FFF;
        switch (n) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 15: {
                this.options = n2 | n << 12;
                break;
            }
            default: {
                throw new IllegalArgumentException("illegal strength value " + n);
            }
        }
    }

    public void setStrengthDefault(int n) {
        int n2 = this.options & 0xFFFF0FFF;
        this.options = n2 | n & 0xF000;
    }

    static int getStrength(int n) {
        return n >> 12;
    }

    public int getStrength() {
        return CollationSettings.getStrength(this.options);
    }

    public void setFlag(int n, boolean bl) {
        this.options = bl ? (this.options |= n) : (this.options &= ~n);
    }

    public void setFlagDefault(int n, int n2) {
        this.options = this.options & ~n | n2 & n;
    }

    public boolean getFlag(int n) {
        return (this.options & n) != 0;
    }

    public void setCaseFirst(int n) {
        if (!$assertionsDisabled && n != 0 && n != 512 && n != 768) {
            throw new AssertionError();
        }
        int n2 = this.options & 0xFFFFFCFF;
        this.options = n2 | n;
    }

    public void setCaseFirstDefault(int n) {
        int n2 = this.options & 0xFFFFFCFF;
        this.options = n2 | n & 0x300;
    }

    public int getCaseFirst() {
        return this.options & 0x300;
    }

    public void setAlternateHandlingShifted(boolean bl) {
        int n = this.options & 0xFFFFFFF3;
        this.options = bl ? n | 4 : n;
    }

    public void setAlternateHandlingDefault(int n) {
        int n2 = this.options & 0xFFFFFFF3;
        this.options = n2 | n & 0xC;
    }

    public boolean getAlternateHandling() {
        return (this.options & 0xC) != 0;
    }

    public void setMaxVariable(int n, int n2) {
        int n3 = this.options & 0xFFFFFF8F;
        switch (n) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                this.options = n3 | n << 4;
                break;
            }
            case -1: {
                this.options = n3 | n2 & 0x70;
                break;
            }
            default: {
                throw new IllegalArgumentException("illegal maxVariable value " + n);
            }
        }
    }

    public int getMaxVariable() {
        return (this.options & 0x70) >> 4;
    }

    static boolean isTertiaryWithCaseBits(int n) {
        return (n & 0x600) == 512;
    }

    static int getTertiaryMask(int n) {
        return CollationSettings.isTertiaryWithCaseBits(n) ? 65343 : 16191;
    }

    static boolean sortsTertiaryUpperCaseFirst(int n) {
        return (n & 0x700) == 768;
    }

    public boolean dontCheckFCD() {
        return (this.options & 1) == 0;
    }

    boolean hasBackwardSecondary() {
        return (this.options & 0x800) != 0;
    }

    public boolean isNumeric() {
        return (this.options & 2) != 0;
    }

    @Override
    public SharedObject clone() {
        return this.clone();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static {
        $assertionsDisabled = !CollationSettings.class.desiredAssertionStatus();
        EMPTY_INT_ARRAY = new int[0];
    }
}

