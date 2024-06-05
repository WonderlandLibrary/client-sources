package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;
import com.ibm.icu.util.*;

public final class CollationData
{
    static final int REORDER_RESERVED_BEFORE_LATIN = 4110;
    static final int REORDER_RESERVED_AFTER_LATIN = 4111;
    static final int MAX_NUM_SPECIAL_REORDER_CODES = 8;
    private static final int[] EMPTY_INT_ARRAY;
    static final int JAMO_CE32S_LENGTH = 67;
    Trie2_32 trie;
    int[] ce32s;
    long[] ces;
    String contexts;
    public CollationData base;
    int[] jamoCE32s;
    public Normalizer2Impl nfcImpl;
    long numericPrimary;
    public boolean[] compressibleBytes;
    UnicodeSet unsafeBackwardSet;
    public char[] fastLatinTable;
    char[] fastLatinTableHeader;
    int numScripts;
    char[] scriptsIndex;
    char[] scriptStarts;
    public long[] rootElements;
    
    CollationData(final Normalizer2Impl nfc) {
        this.jamoCE32s = new int[67];
        this.numericPrimary = 301989888L;
        this.nfcImpl = nfc;
    }
    
    public int getCE32(final int c) {
        return this.trie.get(c);
    }
    
    int getCE32FromSupplementary(final int c) {
        return this.trie.get(c);
    }
    
    boolean isDigit(final int c) {
        return (c < 1632) ? (c <= 57 && 48 <= c) : Collation.hasCE32Tag(this.getCE32(c), 10);
    }
    
    public boolean isUnsafeBackward(final int c, final boolean numeric) {
        return this.unsafeBackwardSet.contains(c) || (numeric && this.isDigit(c));
    }
    
    public boolean isCompressibleLeadByte(final int b) {
        return this.compressibleBytes[b];
    }
    
    public boolean isCompressiblePrimary(final long p) {
        return this.isCompressibleLeadByte((int)p >>> 24);
    }
    
    int getCE32FromContexts(final int index) {
        return this.contexts.charAt(index) << 16 | this.contexts.charAt(index + 1);
    }
    
    int getIndirectCE32(int ce32) {
        assert Collation.isSpecialCE32(ce32);
        final int tag = Collation.tagFromCE32(ce32);
        if (tag == 10) {
            ce32 = this.ce32s[Collation.indexFromCE32(ce32)];
        }
        else if (tag == 13) {
            ce32 = -1;
        }
        else if (tag == 11) {
            ce32 = this.ce32s[0];
        }
        return ce32;
    }
    
    int getFinalCE32(int ce32) {
        if (Collation.isSpecialCE32(ce32)) {
            ce32 = this.getIndirectCE32(ce32);
        }
        return ce32;
    }
    
    long getCEFromOffsetCE32(final int c, final int ce32) {
        final long dataCE = this.ces[Collation.indexFromCE32(ce32)];
        return Collation.makeCE(Collation.getThreeBytePrimaryForOffsetData(c, dataCE));
    }
    
    long getSingleCE(final int c) {
        int ce32 = this.getCE32(c);
        CollationData d;
        if (ce32 == 192) {
            d = this.base;
            ce32 = this.base.getCE32(c);
        }
        else {
            d = this;
        }
        while (Collation.isSpecialCE32(ce32)) {
            switch (Collation.tagFromCE32(ce32)) {
                case 4:
                case 7:
                case 8:
                case 9:
                case 12:
                case 13: {
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", c, ce32));
                }
                case 0:
                case 3: {
                    throw new AssertionError((Object)String.format("unexpected CE32 tag for U+%04X (CE32 0x%08x)", c, ce32));
                }
                case 1: {
                    return Collation.ceFromLongPrimaryCE32(ce32);
                }
                case 2: {
                    return Collation.ceFromLongSecondaryCE32(ce32);
                }
                case 5: {
                    if (Collation.lengthFromCE32(ce32) == 1) {
                        ce32 = d.ce32s[Collation.indexFromCE32(ce32)];
                        continue;
                    }
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", c, ce32));
                }
                case 6: {
                    if (Collation.lengthFromCE32(ce32) == 1) {
                        return d.ces[Collation.indexFromCE32(ce32)];
                    }
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", c, ce32));
                }
                case 10: {
                    ce32 = d.ce32s[Collation.indexFromCE32(ce32)];
                    continue;
                }
                case 11: {
                    assert c == 0;
                    ce32 = d.ce32s[0];
                    continue;
                }
                case 14: {
                    return d.getCEFromOffsetCE32(c, ce32);
                }
                case 15: {
                    return Collation.unassignedCEFromCodePoint(c);
                }
            }
        }
        return Collation.ceFromSimpleCE32(ce32);
    }
    
    int getFCD16(final int c) {
        return this.nfcImpl.getFCD16(c);
    }
    
    long getFirstPrimaryForGroup(final int script) {
        final int index = this.getScriptIndex(script);
        return (index == 0) ? 0L : ((long)this.scriptStarts[index] << 16);
    }
    
    public long getLastPrimaryForGroup(final int script) {
        final int index = this.getScriptIndex(script);
        if (index == 0) {
            return 0L;
        }
        final long limit = this.scriptStarts[index + 1];
        return (limit << 16) - 1L;
    }
    
    public int getGroupForPrimary(long p) {
        p >>= 16;
        if (p < this.scriptStarts[1] || this.scriptStarts[this.scriptStarts.length - 1] <= p) {
            return -1;
        }
        int index;
        for (index = 1; p >= this.scriptStarts[index + 1]; ++index) {}
        for (int i = 0; i < this.numScripts; ++i) {
            if (this.scriptsIndex[i] == index) {
                return i;
            }
        }
        for (int i = 0; i < 8; ++i) {
            if (this.scriptsIndex[this.numScripts + i] == index) {
                return 4096 + i;
            }
        }
        return -1;
    }
    
    private int getScriptIndex(int script) {
        if (script < 0) {
            return 0;
        }
        if (script < this.numScripts) {
            return this.scriptsIndex[script];
        }
        if (script < 4096) {
            return 0;
        }
        script -= 4096;
        if (script < 8) {
            return this.scriptsIndex[this.numScripts + script];
        }
        return 0;
    }
    
    public int[] getEquivalentScripts(final int script) {
        final int index = this.getScriptIndex(script);
        if (index == 0) {
            return CollationData.EMPTY_INT_ARRAY;
        }
        if (script >= 4096) {
            return new int[] { script };
        }
        int length = 0;
        for (int i = 0; i < this.numScripts; ++i) {
            if (this.scriptsIndex[i] == index) {
                ++length;
            }
        }
        final int[] dest = new int[length];
        if (length == 1) {
            dest[0] = script;
            return dest;
        }
        length = 0;
        for (int j = 0; j < this.numScripts; ++j) {
            if (this.scriptsIndex[j] == index) {
                dest[length++] = j;
            }
        }
        return dest;
    }
    
    void makeReorderRanges(final int[] reorder, final UVector32 ranges) {
        this.makeReorderRanges(reorder, false, ranges);
    }
    
    private void makeReorderRanges(final int[] reorder, final boolean latinMustMove, final UVector32 ranges) {
        ranges.removeAllElements();
        int length = reorder.length;
        if (length == 0 || (length == 1 && reorder[0] == 103)) {
            return;
        }
        final short[] table = new short[this.scriptStarts.length - 1];
        int index = this.scriptsIndex[this.numScripts + 4110 - 4096];
        if (index != 0) {
            table[index] = 255;
        }
        index = this.scriptsIndex[this.numScripts + 4111 - 4096];
        if (index != 0) {
            table[index] = 255;
        }
        assert this.scriptStarts.length >= 2;
        assert this.scriptStarts[0] == '\0';
        int lowStart = this.scriptStarts[1];
        assert lowStart == 768;
        int highLimit = this.scriptStarts[this.scriptStarts.length - 1];
        assert highLimit == 65280;
        int specials = 0;
        for (int i = 0; i < length; ++i) {
            final int reorderCode = reorder[i] - 4096;
            if (0 <= reorderCode && reorderCode < 8) {
                specials |= 1 << reorderCode;
            }
        }
        for (int i = 0; i < 8; ++i) {
            final int index2 = this.scriptsIndex[this.numScripts + i];
            if (index2 != 0 && (specials & 1 << i) == 0x0) {
                lowStart = this.addLowScriptRange(table, index2, lowStart);
            }
        }
        int skippedReserved = 0;
        if (specials == 0 && reorder[0] == 25 && !latinMustMove) {
            final int index2 = this.scriptsIndex[25];
            assert index2 != 0;
            final int start = this.scriptStarts[index2];
            assert lowStart <= start;
            skippedReserved = start - lowStart;
            lowStart = start;
        }
        boolean hasReorderToEnd = false;
        int j = 0;
        while (j < length) {
            int script = reorder[j++];
            if (script == 103) {
                hasReorderToEnd = true;
                while (j < length) {
                    script = reorder[--length];
                    if (script == 103) {
                        throw new IllegalArgumentException("setReorderCodes(): duplicate UScript.UNKNOWN");
                    }
                    if (script == -1) {
                        throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
                    }
                    final int index3 = this.getScriptIndex(script);
                    if (index3 == 0) {
                        continue;
                    }
                    if (table[index3] != 0) {
                        throw new IllegalArgumentException("setReorderCodes(): duplicate or equivalent script " + scriptCodeString(script));
                    }
                    highLimit = this.addHighScriptRange(table, index3, highLimit);
                }
                break;
            }
            if (script == -1) {
                throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
            }
            final int index3 = this.getScriptIndex(script);
            if (index3 == 0) {
                continue;
            }
            if (table[index3] != 0) {
                throw new IllegalArgumentException("setReorderCodes(): duplicate or equivalent script " + scriptCodeString(script));
            }
            lowStart = this.addLowScriptRange(table, index3, lowStart);
        }
        for (j = 1; j < this.scriptStarts.length - 1; ++j) {
            final int leadByte = table[j];
            if (leadByte == 0) {
                final int start2 = this.scriptStarts[j];
                if (!hasReorderToEnd && start2 > lowStart) {
                    lowStart = start2;
                }
                lowStart = this.addLowScriptRange(table, j, lowStart);
            }
        }
        if (lowStart <= highLimit) {
            int offset = 0;
            int k = 1;
            while (true) {
                int nextOffset = offset;
                while (k < this.scriptStarts.length - 1) {
                    final int newLeadByte = table[k];
                    if (newLeadByte != 255) {
                        nextOffset = newLeadByte - (this.scriptStarts[k] >> 8);
                        if (nextOffset != offset) {
                            break;
                        }
                    }
                    ++k;
                }
                if (offset != 0 || k < this.scriptStarts.length - 1) {
                    ranges.addElement(this.scriptStarts[k] << 16 | (offset & 0xFFFF));
                }
                if (k == this.scriptStarts.length - 1) {
                    break;
                }
                offset = nextOffset;
                ++k;
            }
            return;
        }
        if (lowStart - (skippedReserved & 0xFF00) <= highLimit) {
            this.makeReorderRanges(reorder, true, ranges);
            return;
        }
        throw new ICUException("setReorderCodes(): reordering too many partial-primary-lead-byte scripts");
    }
    
    private int addLowScriptRange(final short[] table, final int index, int lowStart) {
        final int start = this.scriptStarts[index];
        if ((start & 0xFF) < (lowStart & 0xFF)) {
            lowStart += 256;
        }
        table[index] = (short)(lowStart >> 8);
        final int limit = this.scriptStarts[index + 1];
        lowStart = ((lowStart & 0xFF00) + ((limit & 0xFF00) - (start & 0xFF00)) | (limit & 0xFF));
        return lowStart;
    }
    
    private int addHighScriptRange(final short[] table, final int index, int highLimit) {
        final int limit = this.scriptStarts[index + 1];
        if ((limit & 0xFF) > (highLimit & 0xFF)) {
            highLimit -= 256;
        }
        final int start = this.scriptStarts[index];
        highLimit = ((highLimit & 0xFF00) - ((limit & 0xFF00) - (start & 0xFF00)) | (start & 0xFF));
        table[index] = (short)(highLimit >> 8);
        return highLimit;
    }
    
    private static String scriptCodeString(final int script) {
        return (script < 4096) ? Integer.toString(script) : ("0x" + Integer.toHexString(script));
    }
    
    static {
        EMPTY_INT_ARRAY = new int[0];
    }
}
