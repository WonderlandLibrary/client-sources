package com.ibm.icu.impl.coll;

import java.util.*;

public final class CollationSettings extends SharedObject
{
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
    public int options;
    public long variableTop;
    public byte[] reorderTable;
    long minHighNoReorder;
    long[] reorderRanges;
    public int[] reorderCodes;
    private static final int[] EMPTY_INT_ARRAY;
    public int fastLatinOptions;
    public char[] fastLatinPrimaries;
    
    CollationSettings() {
        this.options = 8208;
        this.reorderCodes = CollationSettings.EMPTY_INT_ARRAY;
        this.fastLatinOptions = -1;
        this.fastLatinPrimaries = new char[384];
    }
    
    @Override
    public CollationSettings clone() {
        final CollationSettings newSettings = (CollationSettings)super.clone();
        newSettings.fastLatinPrimaries = this.fastLatinPrimaries.clone();
        return newSettings;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (!this.getClass().equals(other.getClass())) {
            return false;
        }
        final CollationSettings o = (CollationSettings)other;
        return this.options == o.options && ((this.options & 0xC) == 0x0 || this.variableTop == o.variableTop) && Arrays.equals(this.reorderCodes, o.reorderCodes);
    }
    
    @Override
    public int hashCode() {
        int h = this.options << 8;
        if ((this.options & 0xC) != 0x0) {
            h = (int)((long)h ^ this.variableTop);
        }
        h ^= this.reorderCodes.length;
        for (int i = 0; i < this.reorderCodes.length; ++i) {
            h ^= this.reorderCodes[i] << i;
        }
        return h;
    }
    
    public void resetReordering() {
        this.reorderTable = null;
        this.minHighNoReorder = 0L;
        this.reorderRanges = null;
        this.reorderCodes = CollationSettings.EMPTY_INT_ARRAY;
    }
    
    void aliasReordering(final CollationData data, final int[] codesAndRanges, final int codesLength, final byte[] table) {
        int[] codes;
        if (codesLength == codesAndRanges.length) {
            codes = codesAndRanges;
        }
        else {
            codes = new int[codesLength];
            System.arraycopy(codesAndRanges, 0, codes, 0, codesLength);
        }
        final int rangesStart = codesLength;
        final int rangesLimit = codesAndRanges.length;
        final int rangesLength = rangesLimit - rangesStart;
        Label_0225: {
            if (table != null) {
                if (rangesLength == 0) {
                    if (reorderTableHasSplitBytes(table)) {
                        break Label_0225;
                    }
                }
                else if (rangesLength < 2 || (codesAndRanges[rangesStart] & 0xFFFF) != 0x0 || (codesAndRanges[rangesLimit - 1] & 0xFFFF) == 0x0) {
                    break Label_0225;
                }
                this.reorderTable = table;
                this.reorderCodes = codes;
                int firstSplitByteRangeIndex;
                for (firstSplitByteRangeIndex = rangesStart; firstSplitByteRangeIndex < rangesLimit && (codesAndRanges[firstSplitByteRangeIndex] & 0xFF0000) == 0x0; ++firstSplitByteRangeIndex) {}
                if (firstSplitByteRangeIndex == rangesLimit) {
                    assert !reorderTableHasSplitBytes(table);
                    this.minHighNoReorder = 0L;
                    this.reorderRanges = null;
                }
                else {
                    assert table[codesAndRanges[firstSplitByteRangeIndex] >>> 24] == 0;
                    this.minHighNoReorder = ((long)codesAndRanges[rangesLimit - 1] & 0xFFFF0000L);
                    this.setReorderRanges(codesAndRanges, firstSplitByteRangeIndex, rangesLimit - firstSplitByteRangeIndex);
                }
                return;
            }
        }
        this.setReordering(data, codes);
    }
    
    public void setReordering(final CollationData data, final int[] codes) {
        if (codes.length == 0 || (codes.length == 1 && codes[0] == 103)) {
            this.resetReordering();
            return;
        }
        final UVector32 rangesList = new UVector32();
        data.makeReorderRanges(codes, rangesList);
        int rangesLength = rangesList.size();
        if (rangesLength == 0) {
            this.resetReordering();
            return;
        }
        final int[] ranges = rangesList.getBuffer();
        assert rangesLength >= 2;
        assert (ranges[0] & 0xFFFF) == 0x0 && (ranges[rangesLength - 1] & 0xFFFF) != 0x0;
        this.minHighNoReorder = ((long)ranges[rangesLength - 1] & 0xFFFF0000L);
        final byte[] table = new byte[256];
        int b = 0;
        int firstSplitByteRangeIndex = -1;
        for (int i = 0; i < rangesLength; ++i) {
            final int pair = ranges[i];
            int limit1;
            for (limit1 = pair >>> 24; b < limit1; ++b) {
                table[b] = (byte)(b + pair);
            }
            if ((pair & 0xFF0000) != 0x0) {
                table[limit1] = 0;
                b = limit1 + 1;
                if (firstSplitByteRangeIndex < 0) {
                    firstSplitByteRangeIndex = i;
                }
            }
        }
        while (b <= 255) {
            table[b] = (byte)b;
            ++b;
        }
        int rangesStart;
        if (firstSplitByteRangeIndex < 0) {
            rangesLength = (rangesStart = 0);
        }
        else {
            rangesStart = firstSplitByteRangeIndex;
            rangesLength -= firstSplitByteRangeIndex;
        }
        this.setReorderArrays(codes, ranges, rangesStart, rangesLength, table);
    }
    
    private void setReorderArrays(int[] codes, final int[] ranges, final int rangesStart, final int rangesLength, final byte[] table) {
        if (codes == null) {
            codes = CollationSettings.EMPTY_INT_ARRAY;
        }
        assert codes.length == 0 == (table == null);
        this.reorderTable = table;
        this.reorderCodes = codes;
        this.setReorderRanges(ranges, rangesStart, rangesLength);
    }
    
    private void setReorderRanges(final int[] ranges, int rangesStart, final int rangesLength) {
        if (rangesLength == 0) {
            this.reorderRanges = null;
        }
        else {
            this.reorderRanges = new long[rangesLength];
            int i = 0;
            do {
                this.reorderRanges[i++] = ((long)ranges[rangesStart++] & 0xFFFFFFFFL);
            } while (i < rangesLength);
        }
    }
    
    public void copyReorderingFrom(final CollationSettings other) {
        if (!other.hasReordering()) {
            this.resetReordering();
            return;
        }
        this.minHighNoReorder = other.minHighNoReorder;
        this.reorderTable = other.reorderTable;
        this.reorderRanges = other.reorderRanges;
        this.reorderCodes = other.reorderCodes;
    }
    
    public boolean hasReordering() {
        return this.reorderTable != null;
    }
    
    private static boolean reorderTableHasSplitBytes(final byte[] table) {
        assert table[0] == 0;
        for (int i = 1; i < 256; ++i) {
            if (table[i] == 0) {
                return true;
            }
        }
        return false;
    }
    
    public long reorder(final long p) {
        final byte b = this.reorderTable[(int)p >>> 24];
        if (b != 0 || p <= 1L) {
            return ((long)b & 0xFFL) << 24 | (p & 0xFFFFFFL);
        }
        return this.reorderEx(p);
    }
    
    private long reorderEx(final long p) {
        assert this.minHighNoReorder > 0L;
        if (p >= this.minHighNoReorder) {
            return p;
        }
        final long q = p | 0xFFFFL;
        long r;
        for (int i = 0; q >= (r = this.reorderRanges[i]); ++i) {}
        return p + ((long)(short)r << 24);
    }
    
    public void setStrength(final int value) {
        final int noStrength = this.options & 0xFFFF0FFF;
        switch (value) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 15: {
                this.options = (noStrength | value << 12);
            }
            default: {
                throw new IllegalArgumentException("illegal strength value " + value);
            }
        }
    }
    
    public void setStrengthDefault(final int defaultOptions) {
        final int noStrength = this.options & 0xFFFF0FFF;
        this.options = (noStrength | (defaultOptions & 0xF000));
    }
    
    static int getStrength(final int options) {
        return options >> 12;
    }
    
    public int getStrength() {
        return getStrength(this.options);
    }
    
    public void setFlag(final int bit, final boolean value) {
        if (value) {
            this.options |= bit;
        }
        else {
            this.options &= ~bit;
        }
    }
    
    public void setFlagDefault(final int bit, final int defaultOptions) {
        this.options = ((this.options & ~bit) | (defaultOptions & bit));
    }
    
    public boolean getFlag(final int bit) {
        return (this.options & bit) != 0x0;
    }
    
    public void setCaseFirst(final int value) {
        assert value == 768;
        final int noCaseFirst = this.options & 0xFFFFFCFF;
        this.options = (noCaseFirst | value);
    }
    
    public void setCaseFirstDefault(final int defaultOptions) {
        final int noCaseFirst = this.options & 0xFFFFFCFF;
        this.options = (noCaseFirst | (defaultOptions & 0x300));
    }
    
    public int getCaseFirst() {
        return this.options & 0x300;
    }
    
    public void setAlternateHandlingShifted(final boolean value) {
        final int noAlternate = this.options & 0xFFFFFFF3;
        if (value) {
            this.options = (noAlternate | 0x4);
        }
        else {
            this.options = noAlternate;
        }
    }
    
    public void setAlternateHandlingDefault(final int defaultOptions) {
        final int noAlternate = this.options & 0xFFFFFFF3;
        this.options = (noAlternate | (defaultOptions & 0xC));
    }
    
    public boolean getAlternateHandling() {
        return (this.options & 0xC) != 0x0;
    }
    
    public void setMaxVariable(final int value, final int defaultOptions) {
        final int noMax = this.options & 0xFFFFFF8F;
        switch (value) {
            case 0:
            case 1:
            case 2:
            case 3: {
                this.options = (noMax | value << 4);
                break;
            }
            case -1: {
                this.options = (noMax | (defaultOptions & 0x70));
                break;
            }
            default: {
                throw new IllegalArgumentException("illegal maxVariable value " + value);
            }
        }
    }
    
    public int getMaxVariable() {
        return (this.options & 0x70) >> 4;
    }
    
    static boolean isTertiaryWithCaseBits(final int options) {
        return (options & 0x600) == 0x200;
    }
    
    static int getTertiaryMask(final int options) {
        return isTertiaryWithCaseBits(options) ? 65343 : 16191;
    }
    
    static boolean sortsTertiaryUpperCaseFirst(final int options) {
        return (options & 0x700) == 0x300;
    }
    
    public boolean dontCheckFCD() {
        return (this.options & 0x1) == 0x0;
    }
    
    boolean hasBackwardSecondary() {
        return (this.options & 0x800) != 0x0;
    }
    
    public boolean isNumeric() {
        return (this.options & 0x2) != 0x0;
    }
    
    static {
        EMPTY_INT_ARRAY = new int[0];
    }
}
