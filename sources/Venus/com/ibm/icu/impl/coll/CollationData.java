/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2_32;
import com.ibm.icu.impl.coll.Collation;
import com.ibm.icu.impl.coll.UVector32;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUException;

public final class CollationData {
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
    int[] jamoCE32s = new int[67];
    public Normalizer2Impl nfcImpl;
    long numericPrimary = 0x12000000L;
    public boolean[] compressibleBytes;
    UnicodeSet unsafeBackwardSet;
    public char[] fastLatinTable;
    char[] fastLatinTableHeader;
    int numScripts;
    char[] scriptsIndex;
    char[] scriptStarts;
    public long[] rootElements;
    static final boolean $assertionsDisabled;

    CollationData(Normalizer2Impl normalizer2Impl) {
        this.nfcImpl = normalizer2Impl;
    }

    public int getCE32(int n) {
        return this.trie.get(n);
    }

    int getCE32FromSupplementary(int n) {
        return this.trie.get(n);
    }

    boolean isDigit(int n) {
        return n < 1632 ? n <= 57 && 48 <= n : Collation.hasCE32Tag(this.getCE32(n), 10);
    }

    public boolean isUnsafeBackward(int n, boolean bl) {
        return this.unsafeBackwardSet.contains(n) || bl && this.isDigit(n);
    }

    public boolean isCompressibleLeadByte(int n) {
        return this.compressibleBytes[n];
    }

    public boolean isCompressiblePrimary(long l) {
        return this.isCompressibleLeadByte((int)l >>> 24);
    }

    int getCE32FromContexts(int n) {
        return this.contexts.charAt(n) << 16 | this.contexts.charAt(n + 1);
    }

    int getIndirectCE32(int n) {
        if (!$assertionsDisabled && !Collation.isSpecialCE32(n)) {
            throw new AssertionError();
        }
        int n2 = Collation.tagFromCE32(n);
        if (n2 == 10) {
            n = this.ce32s[Collation.indexFromCE32(n)];
        } else if (n2 == 13) {
            n = -1;
        } else if (n2 == 11) {
            n = this.ce32s[0];
        }
        return n;
    }

    int getFinalCE32(int n) {
        if (Collation.isSpecialCE32(n)) {
            n = this.getIndirectCE32(n);
        }
        return n;
    }

    long getCEFromOffsetCE32(int n, int n2) {
        long l = this.ces[Collation.indexFromCE32(n2)];
        return Collation.makeCE(Collation.getThreeBytePrimaryForOffsetData(n, l));
    }

    long getSingleCE(int n) {
        CollationData collationData;
        int n2 = this.getCE32(n);
        if (n2 == 192) {
            collationData = this.base;
            n2 = this.base.getCE32(n);
        } else {
            collationData = this;
        }
        while (Collation.isSpecialCE32(n2)) {
            switch (Collation.tagFromCE32(n2)) {
                case 4: 
                case 7: 
                case 8: 
                case 9: 
                case 12: 
                case 13: {
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", n, n2));
                }
                case 0: 
                case 3: {
                    throw new AssertionError((Object)String.format("unexpected CE32 tag for U+%04X (CE32 0x%08x)", n, n2));
                }
                case 1: {
                    return Collation.ceFromLongPrimaryCE32(n2);
                }
                case 2: {
                    return Collation.ceFromLongSecondaryCE32(n2);
                }
                case 5: {
                    if (Collation.lengthFromCE32(n2) == 1) {
                        n2 = collationData.ce32s[Collation.indexFromCE32(n2)];
                        break;
                    }
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", n, n2));
                }
                case 6: {
                    if (Collation.lengthFromCE32(n2) == 1) {
                        return collationData.ces[Collation.indexFromCE32(n2)];
                    }
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", n, n2));
                }
                case 10: {
                    n2 = collationData.ce32s[Collation.indexFromCE32(n2)];
                    break;
                }
                case 11: {
                    if (!$assertionsDisabled && n != 0) {
                        throw new AssertionError();
                    }
                    n2 = collationData.ce32s[0];
                    break;
                }
                case 14: {
                    return collationData.getCEFromOffsetCE32(n, n2);
                }
                case 15: {
                    return Collation.unassignedCEFromCodePoint(n);
                }
            }
        }
        return Collation.ceFromSimpleCE32(n2);
    }

    int getFCD16(int n) {
        return this.nfcImpl.getFCD16(n);
    }

    long getFirstPrimaryForGroup(int n) {
        int n2 = this.getScriptIndex(n);
        return n2 == 0 ? 0L : (long)this.scriptStarts[n2] << 16;
    }

    public long getLastPrimaryForGroup(int n) {
        int n2 = this.getScriptIndex(n);
        if (n2 == 0) {
            return 0L;
        }
        long l = this.scriptStarts[n2 + 1];
        return (l << 16) - 1L;
    }

    public int getGroupForPrimary(long l) {
        int n;
        if ((l >>= 16) < (long)this.scriptStarts[1] || (long)this.scriptStarts[this.scriptStarts.length - 1] <= l) {
            return 1;
        }
        char c = '\u0001';
        while (l >= (long)this.scriptStarts[c + 1]) {
            ++c;
        }
        for (n = 0; n < this.numScripts; ++n) {
            if (this.scriptsIndex[n] != c) continue;
            return n;
        }
        for (n = 0; n < 8; ++n) {
            if (this.scriptsIndex[this.numScripts + n] != c) continue;
            return 4096 + n;
        }
        return 1;
    }

    private int getScriptIndex(int n) {
        if (n < 0) {
            return 1;
        }
        if (n < this.numScripts) {
            return this.scriptsIndex[n];
        }
        if (n < 4096) {
            return 1;
        }
        if ((n -= 4096) < 8) {
            return this.scriptsIndex[this.numScripts + n];
        }
        return 1;
    }

    public int[] getEquivalentScripts(int n) {
        int n2 = this.getScriptIndex(n);
        if (n2 == 0) {
            return EMPTY_INT_ARRAY;
        }
        if (n >= 4096) {
            return new int[]{n};
        }
        int n3 = 0;
        for (int i = 0; i < this.numScripts; ++i) {
            if (this.scriptsIndex[i] != n2) continue;
            ++n3;
        }
        int[] nArray = new int[n3];
        if (n3 == 1) {
            nArray[0] = n;
            return nArray;
        }
        n3 = 0;
        for (int i = 0; i < this.numScripts; ++i) {
            if (this.scriptsIndex[i] != n2) continue;
            nArray[n3++] = i;
        }
        return nArray;
    }

    void makeReorderRanges(int[] nArray, UVector32 uVector32) {
        this.makeReorderRanges(nArray, false, uVector32);
    }

    private void makeReorderRanges(int[] nArray, boolean bl, UVector32 uVector32) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        uVector32.removeAllElements();
        int n6 = nArray.length;
        if (n6 == 0 || n6 == 1 && nArray[0] == 103) {
            return;
        }
        short[] sArray = new short[this.scriptStarts.length - 1];
        int n7 = this.scriptsIndex[this.numScripts + 4110 - 4096];
        if (n7 != 0) {
            sArray[n7] = 255;
        }
        if ((n7 = this.scriptsIndex[this.numScripts + 4111 - 4096]) != 0) {
            sArray[n7] = 255;
        }
        if (!$assertionsDisabled && this.scriptStarts.length < 2) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.scriptStarts[0] != '\u0000') {
            throw new AssertionError();
        }
        n7 = this.scriptStarts[1];
        if (!$assertionsDisabled && n7 != 768) {
            throw new AssertionError();
        }
        int n8 = this.scriptStarts[this.scriptStarts.length - 1];
        if (!$assertionsDisabled && n8 != 65280) {
            throw new AssertionError();
        }
        int n9 = 0;
        for (n5 = 0; n5 < n6; ++n5) {
            n4 = nArray[n5] - 4096;
            if (0 > n4 || n4 >= 8) continue;
            n9 |= 1 << n4;
        }
        for (n5 = 0; n5 < 8; ++n5) {
            n4 = this.scriptsIndex[this.numScripts + n5];
            if (n4 == 0 || (n9 & 1 << n5) != 0) continue;
            n7 = this.addLowScriptRange(sArray, n4, n7);
        }
        n5 = 0;
        if (n9 == 0 && nArray[0] == 25 && !bl) {
            n4 = this.scriptsIndex[25];
            if (!$assertionsDisabled && n4 == 0) {
                throw new AssertionError();
            }
            n3 = this.scriptStarts[n4];
            if (!$assertionsDisabled && n7 > n3) {
                throw new AssertionError();
            }
            n5 = n3 - n7;
            n7 = n3;
        }
        n4 = 0;
        n3 = 0;
        while (n3 < n6) {
            if ((n2 = nArray[n3++]) == 103) {
                n4 = 1;
                while (n3 < n6) {
                    if ((n2 = nArray[--n6]) == 103) {
                        throw new IllegalArgumentException("setReorderCodes(): duplicate UScript.UNKNOWN");
                    }
                    if (n2 == -1) {
                        throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
                    }
                    n = this.getScriptIndex(n2);
                    if (n == 0) continue;
                    if (sArray[n] != 0) {
                        throw new IllegalArgumentException("setReorderCodes(): duplicate or equivalent script " + CollationData.scriptCodeString(n2));
                    }
                    n8 = this.addHighScriptRange(sArray, n, n8);
                }
                break;
            }
            if (n2 == -1) {
                throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
            }
            n = this.getScriptIndex(n2);
            if (n == 0) continue;
            if (sArray[n] != 0) {
                throw new IllegalArgumentException("setReorderCodes(): duplicate or equivalent script " + CollationData.scriptCodeString(n2));
            }
            n7 = this.addLowScriptRange(sArray, n, n7);
        }
        for (n3 = 1; n3 < this.scriptStarts.length - 1; ++n3) {
            n2 = sArray[n3];
            if (n2 != 0) continue;
            n = this.scriptStarts[n3];
            if (n4 == 0 && n > n7) {
                n7 = n;
            }
            n7 = this.addLowScriptRange(sArray, n3, n7);
        }
        if (n7 > n8) {
            if (n7 - (n5 & 0xFF00) <= n8) {
                this.makeReorderRanges(nArray, true, uVector32);
                return;
            }
            throw new ICUException("setReorderCodes(): reordering too many partial-primary-lead-byte scripts");
        }
        n3 = 0;
        n2 = 1;
        while (true) {
            short s;
            n = n3;
            while (n2 < this.scriptStarts.length - 1 && ((s = sArray[n2]) == 255 || (n = s - (this.scriptStarts[n2] >> 8)) == n3)) {
                ++n2;
            }
            if (n3 != 0 || n2 < this.scriptStarts.length - 1) {
                uVector32.addElement(this.scriptStarts[n2] << 16 | n3 & 0xFFFF);
            }
            if (n2 == this.scriptStarts.length - 1) break;
            n3 = n;
            ++n2;
        }
    }

    private int addLowScriptRange(short[] sArray, int n, int n2) {
        char c = this.scriptStarts[n];
        if ((c & 0xFF) < (n2 & 0xFF)) {
            n2 += 256;
        }
        sArray[n] = (short)(n2 >> 8);
        char c2 = this.scriptStarts[n + 1];
        n2 = (n2 & 0xFF00) + ((c2 & 0xFF00) - (c & 0xFF00)) | c2 & 0xFF;
        return n2;
    }

    private int addHighScriptRange(short[] sArray, int n, int n2) {
        char c = this.scriptStarts[n + 1];
        if ((c & 0xFF) > (n2 & 0xFF)) {
            n2 -= 256;
        }
        char c2 = this.scriptStarts[n];
        n2 = (n2 & 0xFF00) - ((c & 0xFF00) - (c2 & 0xFF00)) | c2 & 0xFF;
        sArray[n] = (short)(n2 >> 8);
        return n2;
    }

    private static String scriptCodeString(int n) {
        return n < 4096 ? Integer.toString(n) : "0x" + Integer.toHexString(n);
    }

    static {
        $assertionsDisabled = !CollationData.class.desiredAssertionStatus();
        EMPTY_INT_ARRAY = new int[0];
    }
}

