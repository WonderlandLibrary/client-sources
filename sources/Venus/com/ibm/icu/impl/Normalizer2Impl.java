/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.CodePointTrie;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.MutableCodePointTrie;
import com.ibm.icu.util.VersionInfo;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public final class Normalizer2Impl {
    private static final IsAcceptable IS_ACCEPTABLE;
    private static final int DATA_FORMAT = 1316121906;
    private static final CodePointMap.ValueFilter segmentStarterMapper;
    public static final int MIN_YES_YES_WITH_CC = 65026;
    public static final int JAMO_VT = 65024;
    public static final int MIN_NORMAL_MAYBE_YES = 64512;
    public static final int JAMO_L = 2;
    public static final int INERT = 1;
    public static final int HAS_COMP_BOUNDARY_AFTER = 1;
    public static final int OFFSET_SHIFT = 1;
    public static final int DELTA_TCCC_0 = 0;
    public static final int DELTA_TCCC_1 = 2;
    public static final int DELTA_TCCC_GT_1 = 4;
    public static final int DELTA_TCCC_MASK = 6;
    public static final int DELTA_SHIFT = 3;
    public static final int MAX_DELTA = 64;
    public static final int IX_NORM_TRIE_OFFSET = 0;
    public static final int IX_EXTRA_DATA_OFFSET = 1;
    public static final int IX_SMALL_FCD_OFFSET = 2;
    public static final int IX_RESERVED3_OFFSET = 3;
    public static final int IX_TOTAL_SIZE = 7;
    public static final int IX_MIN_DECOMP_NO_CP = 8;
    public static final int IX_MIN_COMP_NO_MAYBE_CP = 9;
    public static final int IX_MIN_YES_NO = 10;
    public static final int IX_MIN_NO_NO = 11;
    public static final int IX_LIMIT_NO_NO = 12;
    public static final int IX_MIN_MAYBE_YES = 13;
    public static final int IX_MIN_YES_NO_MAPPINGS_ONLY = 14;
    public static final int IX_MIN_NO_NO_COMP_BOUNDARY_BEFORE = 15;
    public static final int IX_MIN_NO_NO_COMP_NO_MAYBE_CC = 16;
    public static final int IX_MIN_NO_NO_EMPTY = 17;
    public static final int IX_MIN_LCCC_CP = 18;
    public static final int IX_COUNT = 20;
    public static final int MAPPING_HAS_CCC_LCCC_WORD = 128;
    public static final int MAPPING_HAS_RAW_MAPPING = 64;
    public static final int MAPPING_LENGTH_MASK = 31;
    public static final int COMP_1_LAST_TUPLE = 32768;
    public static final int COMP_1_TRIPLE = 1;
    public static final int COMP_1_TRAIL_LIMIT = 13312;
    public static final int COMP_1_TRAIL_MASK = 32766;
    public static final int COMP_1_TRAIL_SHIFT = 9;
    public static final int COMP_2_TRAIL_SHIFT = 6;
    public static final int COMP_2_TRAIL_MASK = 65472;
    private VersionInfo dataVersion;
    private int minDecompNoCP;
    private int minCompNoMaybeCP;
    private int minLcccCP;
    private int minYesNo;
    private int minYesNoMappingsOnly;
    private int minNoNo;
    private int minNoNoCompBoundaryBefore;
    private int minNoNoCompNoMaybeCC;
    private int minNoNoEmpty;
    private int limitNoNo;
    private int centerNoNoDelta;
    private int minMaybeYes;
    private CodePointTrie.Fast16 normTrie;
    private String maybeYesCompositions;
    private String extraData;
    private byte[] smallFCD;
    private CodePointTrie canonIterData;
    private ArrayList<UnicodeSet> canonStartSets;
    private static final int CANON_NOT_SEGMENT_STARTER = Integer.MIN_VALUE;
    private static final int CANON_HAS_COMPOSITIONS = 0x40000000;
    private static final int CANON_HAS_SET = 0x200000;
    private static final int CANON_VALUE_MASK = 0x1FFFFF;
    static final boolean $assertionsDisabled;

    public Normalizer2Impl load(ByteBuffer byteBuffer) {
        try {
            int n;
            this.dataVersion = ICUBinary.readHeaderAndDataVersion(byteBuffer, 1316121906, IS_ACCEPTABLE);
            int n2 = byteBuffer.getInt() / 4;
            if (n2 <= 18) {
                throw new ICUUncheckedIOException("Normalizer2 data: not enough indexes");
            }
            int[] nArray = new int[n2];
            nArray[0] = n2 * 4;
            for (n = 1; n < n2; ++n) {
                nArray[n] = byteBuffer.getInt();
            }
            this.minDecompNoCP = nArray[8];
            this.minCompNoMaybeCP = nArray[9];
            this.minLcccCP = nArray[18];
            this.minYesNo = nArray[10];
            this.minYesNoMappingsOnly = nArray[14];
            this.minNoNo = nArray[11];
            this.minNoNoCompBoundaryBefore = nArray[15];
            this.minNoNoCompNoMaybeCC = nArray[16];
            this.minNoNoEmpty = nArray[17];
            this.limitNoNo = nArray[12];
            this.minMaybeYes = nArray[13];
            if (!$assertionsDisabled && (this.minMaybeYes & 7) != 0) {
                throw new AssertionError();
            }
            this.centerNoNoDelta = (this.minMaybeYes >> 3) - 64 - 1;
            n = nArray[0];
            int n3 = nArray[1];
            int n4 = byteBuffer.position();
            this.normTrie = CodePointTrie.Fast16.fromBinary(byteBuffer);
            int n5 = byteBuffer.position() - n4;
            if (n5 > n3 - n) {
                throw new ICUUncheckedIOException("Normalizer2 data: not enough bytes for normTrie");
            }
            ICUBinary.skipBytes(byteBuffer, n3 - n - n5);
            n = n3;
            n3 = nArray[2];
            int n6 = (n3 - n) / 2;
            if (n6 != 0) {
                this.maybeYesCompositions = ICUBinary.getString(byteBuffer, n6, 0);
                this.extraData = this.maybeYesCompositions.substring(64512 - this.minMaybeYes >> 1);
            }
            n = n3;
            this.smallFCD = new byte[256];
            byteBuffer.get(this.smallFCD);
            return this;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    public Normalizer2Impl load(String string) {
        return this.load(ICUBinary.getRequiredData(string));
    }

    public void addLcccChars(UnicodeSet unicodeSet) {
        int n = 0;
        CodePointMap.Range range = new CodePointMap.Range();
        while (this.normTrie.getRange(n, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, range)) {
            int n2;
            int n3 = range.getEnd();
            int n4 = range.getValue();
            if (n4 > 64512 && n4 != 65024) {
                unicodeSet.add(n, n3);
            } else if (this.minNoNoCompNoMaybeCC <= n4 && n4 < this.limitNoNo && (n2 = this.getFCD16(n)) > 255) {
                unicodeSet.add(n, n3);
            }
            n = n3 + 1;
        }
    }

    public void addPropertyStarts(UnicodeSet unicodeSet) {
        int n;
        int n2 = 0;
        CodePointMap.Range range = new CodePointMap.Range();
        while (this.normTrie.getRange(n2, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, range)) {
            n = range.getEnd();
            int n3 = range.getValue();
            unicodeSet.add(n2);
            if (n2 != n && this.isAlgorithmicNoNo(n3) && (n3 & 6) > 2) {
                int n4 = this.getFCD16(n2);
                while (++n2 <= n) {
                    int n5 = this.getFCD16(n2);
                    if (n5 == n4) continue;
                    unicodeSet.add(n2);
                    n4 = n5;
                }
            }
            n2 = n + 1;
        }
        for (n = 44032; n < 55204; n += 28) {
            unicodeSet.add(n);
            unicodeSet.add(n + 1);
        }
        unicodeSet.add(55204);
    }

    public void addCanonIterPropertyStarts(UnicodeSet unicodeSet) {
        this.ensureCanonIterData();
        int n = 0;
        CodePointMap.Range range = new CodePointMap.Range();
        while (this.canonIterData.getRange(n, segmentStarterMapper, range)) {
            unicodeSet.add(n);
            n = range.getEnd() + 1;
        }
    }

    public synchronized Normalizer2Impl ensureCanonIterData() {
        if (this.canonIterData == null) {
            MutableCodePointTrie mutableCodePointTrie = new MutableCodePointTrie(0, 0);
            this.canonStartSets = new ArrayList();
            int n = 0;
            CodePointMap.Range range = new CodePointMap.Range();
            while (this.normTrie.getRange(n, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, range)) {
                int n2 = range.getEnd();
                int n3 = range.getValue();
                if (Normalizer2Impl.isInert(n3) || this.minYesNo <= n3 && n3 < this.minNoNo) {
                    n = n2 + 1;
                    continue;
                }
                for (int i = n; i <= n2; ++i) {
                    int n4;
                    int n5 = n4 = mutableCodePointTrie.get(i);
                    if (this.isMaybeOrNonZeroCC(n3)) {
                        n5 |= Integer.MIN_VALUE;
                        if (n3 < 64512) {
                            n5 |= 0x40000000;
                        }
                    } else if (n3 < this.minYesNo) {
                        n5 |= 0x40000000;
                    } else {
                        int n6 = i;
                        int n7 = n3;
                        if (this.isDecompNoAlgorithmic(n7)) {
                            n6 = this.mapAlgorithmic(n6, n7);
                            n7 = this.getRawNorm16(n6);
                            if (!$assertionsDisabled && (this.isHangulLV(n7) || this.isHangulLVT(n7))) {
                                throw new AssertionError();
                            }
                        }
                        if (n7 > this.minYesNo) {
                            int n8 = n7 >> 1;
                            char c = this.extraData.charAt(n8);
                            int n9 = c & 0x1F;
                            if ((c & 0x80) != 0 && i == n6 && (this.extraData.charAt(n8 - 1) & 0xFF) != 0) {
                                n5 |= Integer.MIN_VALUE;
                            }
                            if (n9 != 0) {
                                int n10 = ++n8 + n9;
                                n6 = this.extraData.codePointAt(n8);
                                this.addToStartSet(mutableCodePointTrie, i, n6);
                                if (n7 >= this.minNoNo) {
                                    while ((n8 += Character.charCount(n6)) < n10) {
                                        n6 = this.extraData.codePointAt(n8);
                                        int n11 = mutableCodePointTrie.get(n6);
                                        if ((n11 & Integer.MIN_VALUE) != 0) continue;
                                        mutableCodePointTrie.set(n6, n11 | Integer.MIN_VALUE);
                                    }
                                }
                            }
                        } else {
                            this.addToStartSet(mutableCodePointTrie, i, n6);
                        }
                    }
                    if (n5 == n4) continue;
                    mutableCodePointTrie.set(i, n5);
                }
                n = n2 + 1;
            }
            this.canonIterData = mutableCodePointTrie.buildImmutable(CodePointTrie.Type.SMALL, CodePointTrie.ValueWidth.BITS_32);
        }
        return this;
    }

    public int getNorm16(int n) {
        return UTF16Plus.isLeadSurrogate(n) ? 1 : this.normTrie.get(n);
    }

    public int getRawNorm16(int n) {
        return this.normTrie.get(n);
    }

    public int getCompQuickCheck(int n) {
        if (n < this.minNoNo || 65026 <= n) {
            return 0;
        }
        if (this.minMaybeYes <= n) {
            return 1;
        }
        return 1;
    }

    public boolean isAlgorithmicNoNo(int n) {
        return this.limitNoNo <= n && n < this.minMaybeYes;
    }

    public boolean isCompNo(int n) {
        return this.minNoNo <= n && n < this.minMaybeYes;
    }

    public boolean isDecompYes(int n) {
        return n < this.minYesNo || this.minMaybeYes <= n;
    }

    public int getCC(int n) {
        if (n >= 64512) {
            return Normalizer2Impl.getCCFromNormalYesOrMaybe(n);
        }
        if (n < this.minNoNo || this.limitNoNo <= n) {
            return 1;
        }
        return this.getCCFromNoNo(n);
    }

    public static int getCCFromNormalYesOrMaybe(int n) {
        return n >> 1 & 0xFF;
    }

    public static int getCCFromYesOrMaybe(int n) {
        return n >= 64512 ? Normalizer2Impl.getCCFromNormalYesOrMaybe(n) : 0;
    }

    public int getCCFromYesOrMaybeCP(int n) {
        if (n < this.minCompNoMaybeCP) {
            return 1;
        }
        return Normalizer2Impl.getCCFromYesOrMaybe(this.getNorm16(n));
    }

    public int getFCD16(int n) {
        if (n < this.minDecompNoCP) {
            return 1;
        }
        if (n <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(n)) {
            return 1;
        }
        return this.getFCD16FromNormData(n);
    }

    public boolean singleLeadMightHaveNonZeroFCD16(int n) {
        byte by = this.smallFCD[n >> 8];
        if (by == 0) {
            return true;
        }
        return (by >> (n >> 5 & 7) & 1) != 0;
    }

    public int getFCD16FromNormData(int n) {
        int n2;
        int n3 = this.getNorm16(n);
        if (n3 >= this.limitNoNo) {
            if (n3 >= 64512) {
                n3 = Normalizer2Impl.getCCFromNormalYesOrMaybe(n3);
                return n3 | n3 << 8;
            }
            if (n3 >= this.minMaybeYes) {
                return 1;
            }
            n2 = n3 & 6;
            if (n2 <= 2) {
                return n2 >> 1;
            }
            n = this.mapAlgorithmic(n, n3);
            n3 = this.getRawNorm16(n);
        }
        if (n3 <= this.minYesNo || this.isHangulLVT(n3)) {
            return 1;
        }
        n2 = n3 >> 1;
        char c = this.extraData.charAt(n2);
        int n4 = c >> 8;
        if ((c & 0x80) != 0) {
            n4 |= this.extraData.charAt(n2 - 1) & 0xFF00;
        }
        return n4;
    }

    public String getDecomposition(int n) {
        int n2;
        if (n < this.minDecompNoCP || this.isMaybeOrNonZeroCC(n2 = this.getNorm16(n))) {
            return null;
        }
        int n3 = -1;
        if (this.isDecompNoAlgorithmic(n2)) {
            n3 = n = this.mapAlgorithmic(n, n2);
            n2 = this.getRawNorm16(n);
        }
        if (n2 < this.minYesNo) {
            if (n3 < 0) {
                return null;
            }
            return UTF16.valueOf(n3);
        }
        if (this.isHangulLV(n2) || this.isHangulLVT(n2)) {
            StringBuilder stringBuilder = new StringBuilder();
            Hangul.decompose(n, stringBuilder);
            return stringBuilder.toString();
        }
        int n4 = n2 >> 1;
        int n5 = this.extraData.charAt(n4++) & 0x1F;
        return this.extraData.substring(n4, n4 + n5);
    }

    public String getRawDecomposition(int n) {
        int n2;
        if (n < this.minDecompNoCP || this.isDecompYes(n2 = this.getNorm16(n))) {
            return null;
        }
        if (this.isHangulLV(n2) || this.isHangulLVT(n2)) {
            StringBuilder stringBuilder = new StringBuilder();
            Hangul.getRawDecomposition(n, stringBuilder);
            return stringBuilder.toString();
        }
        if (this.isDecompNoAlgorithmic(n2)) {
            return UTF16.valueOf(this.mapAlgorithmic(n, n2));
        }
        int n3 = n2 >> 1;
        char c = this.extraData.charAt(n3);
        int n4 = c & 0x1F;
        if ((c & 0x40) != 0) {
            int n5 = n3 - (c >> 7 & 1) - 1;
            char c2 = this.extraData.charAt(n5);
            if (c2 <= '\u001f') {
                return this.extraData.substring(n5 - c2, n5);
            }
            StringBuilder stringBuilder = new StringBuilder(n4 - 1).append(c2);
            return stringBuilder.append(this.extraData, n3 += 3, n3 + n4 - 2).toString();
        }
        return this.extraData.substring(++n3, n3 + n4);
    }

    public boolean isCanonSegmentStarter(int n) {
        return this.canonIterData.get(n) >= 0;
    }

    public boolean getCanonStartSet(int n, UnicodeSet unicodeSet) {
        int n2 = this.canonIterData.get(n) & Integer.MAX_VALUE;
        if (n2 == 0) {
            return true;
        }
        unicodeSet.clear();
        int n3 = n2 & 0x1FFFFF;
        if ((n2 & 0x200000) != 0) {
            unicodeSet.addAll(this.canonStartSets.get(n3));
        } else if (n3 != 0) {
            unicodeSet.add(n3);
        }
        if ((n2 & 0x40000000) != 0) {
            int n4 = this.getRawNorm16(n);
            if (n4 == 2) {
                int n5 = 44032 + (n - 4352) * 588;
                unicodeSet.add(n5, n5 + 588 - 1);
            } else {
                this.addComposites(this.getCompositionsList(n4), unicodeSet);
            }
        }
        return false;
    }

    public Appendable decompose(CharSequence charSequence, StringBuilder stringBuilder) {
        this.decompose(charSequence, 0, charSequence.length(), stringBuilder, charSequence.length());
        return stringBuilder;
    }

    public void decompose(CharSequence charSequence, int n, int n2, StringBuilder stringBuilder, int n3) {
        if (n3 < 0) {
            n3 = n2 - n;
        }
        stringBuilder.setLength(0);
        ReorderingBuffer reorderingBuffer = new ReorderingBuffer(this, stringBuilder, n3);
        this.decompose(charSequence, n, n2, reorderingBuffer);
    }

    public int decompose(CharSequence charSequence, int n, int n2, ReorderingBuffer reorderingBuffer) {
        block9: {
            int n3 = this.minDecompNoCP;
            int n4 = 0;
            int n5 = 0;
            int n6 = n;
            char c = '\u0000';
            while (true) {
                char c2;
                int n7 = n;
                while (n != n2) {
                    n4 = charSequence.charAt(n);
                    if (n4 < n3 || this.isMostDecompYesAndZeroCC(n5 = this.normTrie.bmpGet(n4))) {
                        ++n;
                        continue;
                    }
                    if (!UTF16Plus.isLeadSurrogate(n4)) break;
                    if (n + 1 != n2) {
                        char c3 = charSequence.charAt(n + 1);
                        c2 = c3;
                        if (Character.isLowSurrogate(c3)) {
                            n5 = this.normTrie.suppGet(n4 = Character.toCodePoint((char)n4, c2));
                            if (!this.isMostDecompYesAndZeroCC(n5)) break;
                            n += 2;
                            continue;
                        }
                    }
                    ++n;
                }
                if (n != n7) {
                    if (reorderingBuffer != null) {
                        reorderingBuffer.flushAndAppendZeroCC(charSequence, n7, n);
                    } else {
                        c = '\u0000';
                        n6 = n;
                    }
                }
                if (n == n2) break block9;
                n += Character.charCount(n4);
                if (reorderingBuffer != null) {
                    this.decompose(n4, n5, reorderingBuffer);
                    continue;
                }
                if (!this.isDecompYes(n5) || c > (c2 = Normalizer2Impl.getCCFromYesOrMaybe(n5)) && c2 != '\u0000') break;
                c = c2;
                if (c2 > '\u0001') continue;
                n6 = n;
            }
            return n6;
        }
        return n;
    }

    public void decomposeAndAppend(CharSequence charSequence, boolean bl, ReorderingBuffer reorderingBuffer) {
        int n;
        int n2 = charSequence.length();
        if (n2 == 0) {
            return;
        }
        if (bl) {
            this.decompose(charSequence, 0, n2, reorderingBuffer);
            return;
        }
        int n3 = Character.codePointAt(charSequence, 0);
        int n4 = 0;
        int n5 = n = this.getCC(this.getNorm16(n3));
        int n6 = n;
        while (n != 0) {
            n5 = n;
            if ((n4 += Character.charCount(n3)) >= n2) break;
            n3 = Character.codePointAt(charSequence, n4);
            n = this.getCC(this.getNorm16(n3));
        }
        reorderingBuffer.append(charSequence, 0, n4, false, n6, n5);
        reorderingBuffer.append(charSequence, n4, n2);
    }

    public boolean compose(CharSequence charSequence, int n, int n2, boolean bl, boolean bl2, ReorderingBuffer reorderingBuffer) {
        int n3 = n;
        int n4 = this.minCompNoMaybeCP;
        while (true) {
            int n5;
            int n6;
            int n7;
            int n8;
            int n9 = 0;
            int n10 = 0;
            while (true) {
                if (n == n2) {
                    if (n3 != n2 && bl2) {
                        reorderingBuffer.append(charSequence, n3, n2);
                    }
                    return false;
                }
                n9 = charSequence.charAt(n);
                if (n9 < n4 || this.isCompYesAndZeroCC(n10 = this.normTrie.bmpGet(n9))) {
                    ++n;
                    continue;
                }
                n8 = n++;
                if (!UTF16Plus.isLeadSurrogate(n9)) break;
                if (n == n2 || !Character.isLowSurrogate((char)(n7 = charSequence.charAt(n)))) continue;
                ++n;
                n10 = this.normTrie.suppGet(n9 = Character.toCodePoint((char)n9, (char)n7));
                if (!this.isCompYesAndZeroCC(n10)) break;
            }
            if (!this.isMaybeOrNonZeroCC(n10)) {
                if (!bl2) {
                    return true;
                }
                if (this.isDecompNoAlgorithmic(n10)) {
                    if (this.norm16HasCompBoundaryAfter(n10, bl) || this.hasCompBoundaryBefore(charSequence, n, n2)) {
                        if (n3 != n8) {
                            reorderingBuffer.append(charSequence, n3, n8);
                        }
                        reorderingBuffer.append(this.mapAlgorithmic(n9, n10), 0);
                        n3 = n;
                        continue;
                    }
                } else if (n10 < this.minNoNoCompBoundaryBefore) {
                    if (this.norm16HasCompBoundaryAfter(n10, bl) || this.hasCompBoundaryBefore(charSequence, n, n2)) {
                        if (n3 != n8) {
                            reorderingBuffer.append(charSequence, n3, n8);
                        }
                        n7 = n10 >> 1;
                        n6 = this.extraData.charAt(n7++) & 0x1F;
                        reorderingBuffer.append(this.extraData, n7, n7 + n6);
                        n3 = n;
                        continue;
                    }
                } else if (n10 >= this.minNoNoEmpty && (this.hasCompBoundaryBefore(charSequence, n, n2) || this.hasCompBoundaryAfter(charSequence, n3, n8, bl))) {
                    if (n3 != n8) {
                        reorderingBuffer.append(charSequence, n3, n8);
                    }
                    n3 = n;
                    continue;
                }
            } else if (Normalizer2Impl.isJamoVT(n10) && n3 != n8) {
                n7 = charSequence.charAt(n8 - 1);
                if (n9 < 4519) {
                    n6 = n7 - 4352;
                    if (n6 < 19) {
                        if (!bl2) {
                            return true;
                        }
                        if (n != n2 && 0 < (n5 = charSequence.charAt(n) - 4519) && n5 < 28) {
                            ++n;
                        } else {
                            n5 = this.hasCompBoundaryBefore(charSequence, n, n2) ? 0 : -1;
                        }
                        if (n5 >= 0) {
                            int n11 = 44032 + (n6 * 21 + (n9 - 4449)) * 28 + n5;
                            if (n3 != --n8) {
                                reorderingBuffer.append(charSequence, n3, n8);
                            }
                            reorderingBuffer.append((char)n11);
                            n3 = n;
                            continue;
                        }
                    }
                } else if (Hangul.isHangulLV(n7)) {
                    if (!bl2) {
                        return true;
                    }
                    n6 = n7 + n9 - 4519;
                    if (n3 != --n8) {
                        reorderingBuffer.append(charSequence, n3, n8);
                    }
                    reorderingBuffer.append((char)n6);
                    n3 = n;
                    continue;
                }
            } else if (n10 > 65024) {
                n7 = Normalizer2Impl.getCCFromNormalYesOrMaybe(n10);
                if (bl && this.getPreviousTrailCC(charSequence, n3, n8) > n7) {
                    if (!bl2) {
                        return true;
                    }
                } else {
                    while (true) {
                        if (n == n2) {
                            if (bl2) {
                                reorderingBuffer.append(charSequence, n3, n2);
                            }
                            return false;
                        }
                        n5 = n7;
                        n9 = Character.codePointAt(charSequence, n);
                        n6 = this.normTrie.get(n9);
                        if (n6 < 65026) break;
                        n7 = Normalizer2Impl.getCCFromNormalYesOrMaybe(n6);
                        if (n5 > n7) {
                            if (bl2) break;
                            return true;
                        }
                        n += Character.charCount(n9);
                    }
                    if (this.norm16HasCompBoundaryBefore(n6)) {
                        if (!this.isCompYesAndZeroCC(n6)) continue;
                        n += Character.charCount(n9);
                        continue;
                    }
                }
            }
            if (n3 != n8 && !this.norm16HasCompBoundaryBefore(n10) && !this.norm16HasCompBoundaryAfter(n10 = this.normTrie.get(n9 = Character.codePointBefore(charSequence, n8)), bl)) {
                n8 -= Character.charCount(n9);
            }
            if (bl2 && n3 != n8) {
                reorderingBuffer.append(charSequence, n3, n8);
            }
            n7 = reorderingBuffer.length();
            this.decomposeShort(charSequence, n8, n, false, bl, reorderingBuffer);
            n = this.decomposeShort(charSequence, n, n2, true, bl, reorderingBuffer);
            this.recompose(reorderingBuffer, n7, bl);
            if (!bl2) {
                if (!reorderingBuffer.equals(charSequence, n8, n)) {
                    return true;
                }
                reorderingBuffer.remove();
            }
            n3 = n;
        }
    }

    public int composeQuickCheck(CharSequence charSequence, int n, int n2, boolean bl, boolean bl2) {
        int n3 = 0;
        int n4 = n;
        int n5 = this.minCompNoMaybeCP;
        while (true) {
            int n6;
            int n7;
            int n8;
            int n9 = 0;
            int n10 = 0;
            while (true) {
                if (n == n2) {
                    return n << 1 | n3;
                }
                n9 = charSequence.charAt(n);
                if (n9 < n5 || this.isCompYesAndZeroCC(n10 = this.normTrie.bmpGet(n9))) {
                    ++n;
                    continue;
                }
                n8 = n++;
                if (!UTF16Plus.isLeadSurrogate(n9)) break;
                if (n == n2 || !Character.isLowSurrogate((char)(n7 = charSequence.charAt(n)))) continue;
                ++n;
                n10 = this.normTrie.suppGet(n9 = Character.toCodePoint((char)n9, (char)n7));
                if (!this.isCompYesAndZeroCC(n10)) break;
            }
            n7 = 1;
            if (n4 != n8) {
                n4 = n8;
                if (!this.norm16HasCompBoundaryBefore(n10) && !this.norm16HasCompBoundaryAfter(n6 = this.getNorm16(n9 = Character.codePointBefore(charSequence, n8)), bl)) {
                    n4 -= Character.charCount(n9);
                    n7 = n6;
                }
            }
            if (!this.isMaybeOrNonZeroCC(n10)) break;
            n6 = Normalizer2Impl.getCCFromYesOrMaybe(n10);
            if (bl && n6 != 0 && this.getTrailCCFromCompYesAndZeroCC(n7) > n6) break;
            while (true) {
                if (n10 < 65026) {
                    if (!bl2) {
                        n3 = 1;
                    } else {
                        return n4 << 1;
                    }
                }
                if (n == n2) {
                    return n << 1 | n3;
                }
                int n11 = n6;
                n9 = Character.codePointAt(charSequence, n);
                n10 = this.getNorm16(n9);
                if (!this.isMaybeOrNonZeroCC(n10) || n11 > (n6 = Normalizer2Impl.getCCFromYesOrMaybe(n10)) && n6 != 0) break;
                n += Character.charCount(n9);
            }
            if (!this.isCompYesAndZeroCC(n10)) break;
            n4 = n;
            n += Character.charCount(n9);
        }
        return n4 << 1;
    }

    public void composeAndAppend(CharSequence charSequence, boolean bl, boolean bl2, ReorderingBuffer reorderingBuffer) {
        int n;
        int n2 = 0;
        int n3 = charSequence.length();
        if (!reorderingBuffer.isEmpty() && 0 != (n = this.findNextCompBoundary(charSequence, 0, n3, bl2))) {
            int n4 = this.findPreviousCompBoundary(reorderingBuffer.getStringBuilder(), reorderingBuffer.length(), bl2);
            StringBuilder stringBuilder = new StringBuilder(reorderingBuffer.length() - n4 + n + 16);
            stringBuilder.append(reorderingBuffer.getStringBuilder(), n4, reorderingBuffer.length());
            reorderingBuffer.removeSuffix(reorderingBuffer.length() - n4);
            stringBuilder.append(charSequence, 0, n);
            this.compose(stringBuilder, 0, stringBuilder.length(), bl2, true, reorderingBuffer);
            n2 = n;
        }
        if (bl) {
            this.compose(charSequence, n2, n3, bl2, true, reorderingBuffer);
        } else {
            reorderingBuffer.append(charSequence, n2, n3);
        }
    }

    public int makeFCD(CharSequence charSequence, int n, int n2, ReorderingBuffer reorderingBuffer) {
        int n3 = n;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        while (true) {
            int n7;
            int n8 = n;
            while (n != n2) {
                n4 = charSequence.charAt(n);
                if (n4 < this.minLcccCP) {
                    n5 = ~n4;
                    ++n;
                    continue;
                }
                if (!this.singleLeadMightHaveNonZeroFCD16(n4)) {
                    n5 = 0;
                    ++n;
                    continue;
                }
                if (UTF16Plus.isLeadSurrogate(n4) && n + 1 != n2) {
                    char c = charSequence.charAt(n + 1);
                    n7 = c;
                    if (Character.isLowSurrogate(c)) {
                        n4 = Character.toCodePoint((char)n4, (char)n7);
                    }
                }
                if ((n6 = this.getFCD16FromNormData(n4)) > 255) break;
                n5 = n6;
                n += Character.charCount(n4);
            }
            if (n != n8) {
                if (n == n2) {
                    if (reorderingBuffer == null) break;
                    reorderingBuffer.flushAndAppendZeroCC(charSequence, n8, n);
                    break;
                }
                n3 = n;
                if (n5 < 0) {
                    n7 = ~n5;
                    if (n7 < this.minDecompNoCP) {
                        n5 = 0;
                    } else {
                        n5 = this.getFCD16FromNormData(n7);
                        if (n5 > 1) {
                            --n3;
                        }
                    }
                } else {
                    n7 = n - 1;
                    if (Character.isLowSurrogate(charSequence.charAt(n7)) && n8 < n7 && Character.isHighSurrogate(charSequence.charAt(n7 - 1))) {
                        n5 = this.getFCD16FromNormData(Character.toCodePoint(charSequence.charAt(--n7), charSequence.charAt(n7 + '\u0001')));
                    }
                    if (n5 > 1) {
                        n3 = n7;
                    }
                }
                if (reorderingBuffer != null) {
                    reorderingBuffer.flushAndAppendZeroCC(charSequence, n8, n3);
                    reorderingBuffer.append(charSequence, n3, n);
                }
                n8 = n;
            } else if (n == n2) break;
            n += Character.charCount(n4);
            if ((n5 & 0xFF) <= n6 >> 8) {
                if ((n6 & 0xFF) <= 1) {
                    n3 = n;
                }
                if (reorderingBuffer != null) {
                    reorderingBuffer.appendZeroCC(n4);
                }
                n5 = n6;
                continue;
            }
            if (reorderingBuffer == null) {
                return n3;
            }
            reorderingBuffer.removeSuffix(n8 - n3);
            n = this.findNextFCDBoundary(charSequence, n, n2);
            this.decomposeShort(charSequence, n3, n, false, false, reorderingBuffer);
            n3 = n;
            n5 = 0;
        }
        return n;
    }

    public void makeFCDAndAppend(CharSequence charSequence, boolean bl, ReorderingBuffer reorderingBuffer) {
        int n;
        int n2 = 0;
        int n3 = charSequence.length();
        if (!reorderingBuffer.isEmpty() && 0 != (n = this.findNextFCDBoundary(charSequence, 0, n3))) {
            int n4 = this.findPreviousFCDBoundary(reorderingBuffer.getStringBuilder(), reorderingBuffer.length());
            StringBuilder stringBuilder = new StringBuilder(reorderingBuffer.length() - n4 + n + 16);
            stringBuilder.append(reorderingBuffer.getStringBuilder(), n4, reorderingBuffer.length());
            reorderingBuffer.removeSuffix(reorderingBuffer.length() - n4);
            stringBuilder.append(charSequence, 0, n);
            this.makeFCD(stringBuilder, 0, stringBuilder.length(), reorderingBuffer);
            n2 = n;
        }
        if (bl) {
            this.makeFCD(charSequence, n2, n3, reorderingBuffer);
        } else {
            reorderingBuffer.append(charSequence, n2, n3);
        }
    }

    public boolean hasDecompBoundaryBefore(int n) {
        return n < this.minLcccCP || n <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(n) || this.norm16HasDecompBoundaryBefore(this.getNorm16(n));
    }

    public boolean norm16HasDecompBoundaryBefore(int n) {
        if (n < this.minNoNoCompNoMaybeCC) {
            return false;
        }
        if (n >= this.limitNoNo) {
            return n <= 64512 || n == 65024;
        }
        int n2 = n >> 1;
        char c = this.extraData.charAt(n2);
        return (c & 0x80) == 0 || (this.extraData.charAt(n2 - 1) & 0xFF00) == 0;
    }

    public boolean hasDecompBoundaryAfter(int n) {
        if (n < this.minDecompNoCP) {
            return false;
        }
        if (n <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(n)) {
            return false;
        }
        return this.norm16HasDecompBoundaryAfter(this.getNorm16(n));
    }

    public boolean norm16HasDecompBoundaryAfter(int n) {
        if (n <= this.minYesNo || this.isHangulLVT(n)) {
            return false;
        }
        if (n >= this.limitNoNo) {
            if (this.isMaybeOrNonZeroCC(n)) {
                return n <= 64512 || n == 65024;
            }
            return (n & 6) <= 2;
        }
        int n2 = n >> 1;
        char c = this.extraData.charAt(n2);
        if (c > '\u01ff') {
            return true;
        }
        if (c <= '\u00ff') {
            return false;
        }
        return (c & 0x80) == 0 || (this.extraData.charAt(n2 - 1) & 0xFF00) == 0;
    }

    public boolean isDecompInert(int n) {
        return this.isDecompYesAndZeroCC(this.getNorm16(n));
    }

    public boolean hasCompBoundaryBefore(int n) {
        return n < this.minCompNoMaybeCP || this.norm16HasCompBoundaryBefore(this.getNorm16(n));
    }

    public boolean hasCompBoundaryAfter(int n, boolean bl) {
        return this.norm16HasCompBoundaryAfter(this.getNorm16(n), bl);
    }

    public boolean isCompInert(int n, boolean bl) {
        int n2 = this.getNorm16(n);
        return this.isCompYesAndZeroCC(n2) && (n2 & 1) != 0 && (!bl || Normalizer2Impl.isInert(n2) || this.extraData.charAt(n2 >> 1) <= '\u01ff');
    }

    public boolean hasFCDBoundaryBefore(int n) {
        return this.hasDecompBoundaryBefore(n);
    }

    public boolean hasFCDBoundaryAfter(int n) {
        return this.hasDecompBoundaryAfter(n);
    }

    public boolean isFCDInert(int n) {
        return this.getFCD16(n) <= 1;
    }

    private boolean isMaybe(int n) {
        return this.minMaybeYes <= n && n <= 65024;
    }

    private boolean isMaybeOrNonZeroCC(int n) {
        return n >= this.minMaybeYes;
    }

    private static boolean isInert(int n) {
        return n == 1;
    }

    private static boolean isJamoL(int n) {
        return n == 2;
    }

    private static boolean isJamoVT(int n) {
        return n == 65024;
    }

    private int hangulLVT() {
        return this.minYesNoMappingsOnly | 1;
    }

    private boolean isHangulLV(int n) {
        return n == this.minYesNo;
    }

    private boolean isHangulLVT(int n) {
        return n == this.hangulLVT();
    }

    private boolean isCompYesAndZeroCC(int n) {
        return n < this.minNoNo;
    }

    private boolean isDecompYesAndZeroCC(int n) {
        return n < this.minYesNo || n == 65024 || this.minMaybeYes <= n && n <= 64512;
    }

    private boolean isMostDecompYesAndZeroCC(int n) {
        return n < this.minYesNo || n == 64512 || n == 65024;
    }

    private boolean isDecompNoAlgorithmic(int n) {
        return n >= this.limitNoNo;
    }

    private int getCCFromNoNo(int n) {
        int n2 = n >> 1;
        if ((this.extraData.charAt(n2) & 0x80) != 0) {
            return this.extraData.charAt(n2 - 1) & 0xFF;
        }
        return 1;
    }

    int getTrailCCFromCompYesAndZeroCC(int n) {
        if (n <= this.minYesNo) {
            return 1;
        }
        return this.extraData.charAt(n >> 1) >> 8;
    }

    private int mapAlgorithmic(int n, int n2) {
        return n + (n2 >> 3) - this.centerNoNoDelta;
    }

    private int getCompositionsListForDecompYes(int n) {
        if (n < 2 || 64512 <= n) {
            return 1;
        }
        if ((n -= this.minMaybeYes) < 0) {
            n += 64512;
        }
        return n >> 1;
    }

    private int getCompositionsListForComposite(int n) {
        int n2 = 64512 - this.minMaybeYes + n >> 1;
        char c = this.maybeYesCompositions.charAt(n2);
        return n2 + 1 + (c & 0x1F);
    }

    private int getCompositionsListForMaybe(int n) {
        return n - this.minMaybeYes >> 1;
    }

    private int getCompositionsList(int n) {
        return this.isDecompYes(n) ? this.getCompositionsListForDecompYes(n) : this.getCompositionsListForComposite(n);
    }

    private int decomposeShort(CharSequence charSequence, int n, int n2, boolean bl, boolean bl2, ReorderingBuffer reorderingBuffer) {
        while (n < n2) {
            int n3 = Character.codePointAt(charSequence, n);
            if (bl && n3 < this.minCompNoMaybeCP) {
                return n;
            }
            int n4 = this.getNorm16(n3);
            if (bl && this.norm16HasCompBoundaryBefore(n4)) {
                return n;
            }
            n += Character.charCount(n3);
            this.decompose(n3, n4, reorderingBuffer);
            if (!bl || !this.norm16HasCompBoundaryAfter(n4, bl2)) continue;
            return n;
        }
        return n;
    }

    private void decompose(int n, int n2, ReorderingBuffer reorderingBuffer) {
        if (n2 >= this.limitNoNo) {
            if (this.isMaybeOrNonZeroCC(n2)) {
                reorderingBuffer.append(n, Normalizer2Impl.getCCFromYesOrMaybe(n2));
                return;
            }
            n = this.mapAlgorithmic(n, n2);
            n2 = this.getRawNorm16(n);
        }
        if (n2 < this.minYesNo) {
            reorderingBuffer.append(n, 0);
        } else if (this.isHangulLV(n2) || this.isHangulLVT(n2)) {
            Hangul.decompose(n, reorderingBuffer);
        } else {
            int n3 = n2 >> 1;
            char c = this.extraData.charAt(n3);
            int n4 = c & 0x1F;
            int n5 = c >> 8;
            int n6 = (c & 0x80) != 0 ? this.extraData.charAt(n3 - 1) >> 8 : 0;
            reorderingBuffer.append(this.extraData, ++n3, n3 + n4, true, n6, n5);
        }
    }

    private static int combine(String string, int n, int n2) {
        block9: {
            if (n2 < 13312) {
                char c;
                int n3 = n2 << 1;
                while (n3 > (c = string.charAt(n))) {
                    n += 2 + (c & '\u0001');
                }
                if (n3 == (c & 0x7FFE)) {
                    if ((c & '\u0001') != 0) {
                        return string.charAt(n + 1) << 16 | string.charAt(n + 2);
                    }
                    return string.charAt(n + 1);
                }
            } else {
                char c;
                int n4 = 13312 + (n2 >> 9 & 0xFFFFFFFE);
                int n5 = n2 << 6 & 0xFFFF;
                while (true) {
                    char c2;
                    if (n4 > (c2 = string.charAt(n))) {
                        n += 2 + (c2 & '\u0001');
                        continue;
                    }
                    if (n4 != (c2 & 0x7FFE)) break block9;
                    c = string.charAt(n + 1);
                    if (n5 <= c) break;
                    if ((c2 & 0x8000) == 0) {
                        n += 3;
                        continue;
                    }
                    break block9;
                    break;
                }
                if (n5 == (c & 0xFFC0)) {
                    return (c & 0xFFFF003F) << 16 | string.charAt(n + 2);
                }
            }
        }
        return 1;
    }

    private void addComposites(int n, UnicodeSet unicodeSet) {
        char c;
        do {
            int n2;
            if (((c = this.maybeYesCompositions.charAt(n)) & '\u0001') == 0) {
                n2 = this.maybeYesCompositions.charAt(n + 1);
                n += 2;
            } else {
                n2 = (this.maybeYesCompositions.charAt(n + 1) & 0xFFFF003F) << 16 | this.maybeYesCompositions.charAt(n + 2);
                n += 3;
            }
            int n3 = n2 >> 1;
            if ((n2 & '\u0001') != 0) {
                this.addComposites(this.getCompositionsListForComposite(this.getRawNorm16(n3)), unicodeSet);
            }
            unicodeSet.add(n3);
        } while ((c & 0x8000) == 0);
    }

    private void recompose(ReorderingBuffer reorderingBuffer, int n, boolean bl) {
        int n2 = n;
        StringBuilder stringBuilder = reorderingBuffer.getStringBuilder();
        if (n2 == stringBuilder.length()) {
            return;
        }
        int n3 = -1;
        int n4 = -1;
        boolean bl2 = false;
        int n5 = 0;
        while (true) {
            int n6 = stringBuilder.codePointAt(n2);
            n2 += Character.charCount(n6);
            int n7 = this.getNorm16(n6);
            int n8 = Normalizer2Impl.getCCFromYesOrMaybe(n7);
            if (this.isMaybe(n7) && n3 >= 0 && (n5 < n8 || n5 == 0)) {
                int n9;
                int n10;
                if (Normalizer2Impl.isJamoVT(n7)) {
                    if (n6 < 4519 && (n10 = (int)(stringBuilder.charAt(n4) - 4352)) < 19) {
                        char c;
                        n9 = n2 - 1;
                        char c2 = (char)(44032 + (n10 * 21 + (n6 - 4449)) * 28);
                        if (n2 != stringBuilder.length() && (c = (char)(stringBuilder.charAt(n2) - 4519)) < '\u001c') {
                            ++n2;
                            c2 = (char)(c2 + c);
                        }
                        stringBuilder.setCharAt(n4, c2);
                        stringBuilder.delete(n9, n2);
                        n2 = n9;
                    }
                    if (n2 == stringBuilder.length()) break;
                    n3 = -1;
                    continue;
                }
                int n11 = Normalizer2Impl.combine(this.maybeYesCompositions, n3, n6);
                if (n11 >= 0) {
                    n10 = n11 >> 1;
                    n9 = n2 - Character.charCount(n6);
                    stringBuilder.delete(n9, n2);
                    n2 = n9;
                    if (bl2) {
                        if (n10 > 65535) {
                            stringBuilder.setCharAt(n4, UTF16.getLeadSurrogate(n10));
                            stringBuilder.setCharAt(n4 + 1, UTF16.getTrailSurrogate(n10));
                        } else {
                            stringBuilder.setCharAt(n4, (char)n6);
                            stringBuilder.deleteCharAt(n4 + 1);
                            bl2 = false;
                            --n2;
                        }
                    } else if (n10 > 65535) {
                        bl2 = true;
                        stringBuilder.setCharAt(n4, UTF16.getLeadSurrogate(n10));
                        stringBuilder.insert(n4 + 1, UTF16.getTrailSurrogate(n10));
                        ++n2;
                    } else {
                        stringBuilder.setCharAt(n4, (char)n10);
                    }
                    if (n2 == stringBuilder.length()) break;
                    if ((n11 & 1) != 0) {
                        n3 = this.getCompositionsListForComposite(this.getRawNorm16(n10));
                        continue;
                    }
                    n3 = -1;
                    continue;
                }
            }
            n5 = n8;
            if (n2 == stringBuilder.length()) break;
            if (n8 == 0) {
                n3 = this.getCompositionsListForDecompYes(n7);
                if (n3 < 0) continue;
                if (n6 <= 65535) {
                    bl2 = false;
                    n4 = n2 - 1;
                    continue;
                }
                bl2 = true;
                n4 = n2 - 2;
                continue;
            }
            if (!bl) continue;
            n3 = -1;
        }
        reorderingBuffer.flush();
    }

    public int composePair(int n, int n2) {
        int n3;
        int n4 = this.getNorm16(n);
        if (Normalizer2Impl.isInert(n4)) {
            return 1;
        }
        if (n4 < this.minYesNoMappingsOnly) {
            if (Normalizer2Impl.isJamoL(n4)) {
                if (0 <= (n2 -= 4449) && n2 < 21) {
                    return 44032 + ((n - 4352) * 21 + n2) * 28;
                }
                return 1;
            }
            if (this.isHangulLV(n4)) {
                if (0 < (n2 -= 4519) && n2 < 28) {
                    return n + n2;
                }
                return 1;
            }
            n3 = 64512 - this.minMaybeYes + n4 >> 1;
            if (n4 > this.minYesNo) {
                n3 += 1 + (this.maybeYesCompositions.charAt(n3) & 0x1F);
            }
        } else {
            if (n4 < this.minMaybeYes || 64512 <= n4) {
                return 1;
            }
            n3 = this.getCompositionsListForMaybe(n4);
        }
        if (n2 < 0 || 0x10FFFF < n2) {
            return 1;
        }
        return Normalizer2Impl.combine(this.maybeYesCompositions, n3, n2) >> 1;
    }

    private boolean hasCompBoundaryBefore(int n, int n2) {
        return n < this.minCompNoMaybeCP || this.norm16HasCompBoundaryBefore(n2);
    }

    private boolean norm16HasCompBoundaryBefore(int n) {
        return n < this.minNoNoCompNoMaybeCC || this.isAlgorithmicNoNo(n);
    }

    private boolean hasCompBoundaryBefore(CharSequence charSequence, int n, int n2) {
        return n == n2 || this.hasCompBoundaryBefore(Character.codePointAt(charSequence, n));
    }

    private boolean norm16HasCompBoundaryAfter(int n, boolean bl) {
        return (n & 1) != 0 && (!bl || this.isTrailCC01ForCompBoundaryAfter(n));
    }

    private boolean hasCompBoundaryAfter(CharSequence charSequence, int n, int n2, boolean bl) {
        return n == n2 || this.hasCompBoundaryAfter(Character.codePointBefore(charSequence, n2), bl);
    }

    private boolean isTrailCC01ForCompBoundaryAfter(int n) {
        return Normalizer2Impl.isInert(n) || (this.isDecompNoAlgorithmic(n) ? (n & 6) <= 2 : this.extraData.charAt(n >> 1) <= '\u01ff');
    }

    private int findPreviousCompBoundary(CharSequence charSequence, int n, boolean bl) {
        int n2;
        int n3;
        while (n > 0 && !this.norm16HasCompBoundaryAfter(n3 = this.getNorm16(n2 = Character.codePointBefore(charSequence, n)), bl)) {
            n -= Character.charCount(n2);
            if (!this.hasCompBoundaryBefore(n2, n3)) continue;
            break;
        }
        return n;
    }

    private int findNextCompBoundary(CharSequence charSequence, int n, int n2, boolean bl) {
        int n3;
        int n4;
        while (n < n2 && !this.hasCompBoundaryBefore(n4 = Character.codePointAt(charSequence, n), n3 = this.normTrie.get(n4))) {
            n += Character.charCount(n4);
            if (!this.norm16HasCompBoundaryAfter(n3, bl)) continue;
            break;
        }
        return n;
    }

    private int findPreviousFCDBoundary(CharSequence charSequence, int n) {
        int n2;
        int n3;
        while (n > 0 && (n3 = Character.codePointBefore(charSequence, n)) >= this.minDecompNoCP && !this.norm16HasDecompBoundaryAfter(n2 = this.getNorm16(n3))) {
            n -= Character.charCount(n3);
            if (!this.norm16HasDecompBoundaryBefore(n2)) continue;
            break;
        }
        return n;
    }

    private int findNextFCDBoundary(CharSequence charSequence, int n, int n2) {
        int n3;
        int n4;
        while (n < n2 && (n4 = Character.codePointAt(charSequence, n)) >= this.minLcccCP && !this.norm16HasDecompBoundaryBefore(n3 = this.getNorm16(n4))) {
            n += Character.charCount(n4);
            if (!this.norm16HasDecompBoundaryAfter(n3)) continue;
            break;
        }
        return n;
    }

    private int getPreviousTrailCC(CharSequence charSequence, int n, int n2) {
        if (n == n2) {
            return 1;
        }
        return this.getFCD16(Character.codePointBefore(charSequence, n2));
    }

    private void addToStartSet(MutableCodePointTrie mutableCodePointTrie, int n, int n2) {
        int n3 = mutableCodePointTrie.get(n2);
        if ((n3 & 0x3FFFFF) == 0 && n != 0) {
            mutableCodePointTrie.set(n2, n3 | n);
        } else {
            UnicodeSet unicodeSet;
            if ((n3 & 0x200000) == 0) {
                int n4 = n3 & 0x1FFFFF;
                n3 = n3 & 0xFFE00000 | 0x200000 | this.canonStartSets.size();
                mutableCodePointTrie.set(n2, n3);
                unicodeSet = new UnicodeSet();
                this.canonStartSets.add(unicodeSet);
                if (n4 != 0) {
                    unicodeSet.add(n4);
                }
            } else {
                unicodeSet = this.canonStartSets.get(n3 & 0x1FFFFF);
            }
            unicodeSet.add(n);
        }
    }

    static {
        $assertionsDisabled = !Normalizer2Impl.class.desiredAssertionStatus();
        IS_ACCEPTABLE = new IsAcceptable(null);
        segmentStarterMapper = new CodePointMap.ValueFilter(){

            @Override
            public int apply(int n) {
                return n & Integer.MIN_VALUE;
            }
        };
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] byArray) {
            return byArray[0] == 4;
        }

        IsAcceptable(1 var1_1) {
            this();
        }
    }

    public static final class UTF16Plus {
        public static boolean isLeadSurrogate(int n) {
            return (n & 0xFFFFFC00) == 55296;
        }

        public static boolean isTrailSurrogate(int n) {
            return (n & 0xFFFFFC00) == 56320;
        }

        public static boolean isSurrogate(int n) {
            return (n & 0xFFFFF800) == 55296;
        }

        public static boolean isSurrogateLead(int n) {
            return (n & 0x400) == 0;
        }

        public static boolean equal(CharSequence charSequence, CharSequence charSequence2) {
            if (charSequence == charSequence2) {
                return false;
            }
            int n = charSequence.length();
            if (n != charSequence2.length()) {
                return true;
            }
            for (int i = 0; i < n; ++i) {
                if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
                return true;
            }
            return false;
        }

        public static boolean equal(CharSequence charSequence, int n, int n2, CharSequence charSequence2, int n3, int n4) {
            if (n2 - n != n4 - n3) {
                return true;
            }
            if (charSequence == charSequence2 && n == n3) {
                return false;
            }
            while (n < n2) {
                if (charSequence.charAt(n++) == charSequence2.charAt(n3++)) continue;
                return true;
            }
            return false;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class ReorderingBuffer
    implements Appendable {
        private final Normalizer2Impl impl;
        private final Appendable app;
        private final StringBuilder str;
        private final boolean appIsStringBuilder;
        private int reorderStart;
        private int lastCC;
        private int codePointStart;
        private int codePointLimit;

        public ReorderingBuffer(Normalizer2Impl normalizer2Impl, Appendable appendable, int n) {
            this.impl = normalizer2Impl;
            this.app = appendable;
            if (this.app instanceof StringBuilder) {
                this.appIsStringBuilder = true;
                this.str = (StringBuilder)appendable;
                this.str.ensureCapacity(n);
                this.reorderStart = 0;
                if (this.str.length() == 0) {
                    this.lastCC = 0;
                } else {
                    this.setIterator();
                    this.lastCC = this.previousCC();
                    if (this.lastCC > 1) {
                        while (this.previousCC() > 1) {
                        }
                    }
                    this.reorderStart = this.codePointLimit;
                }
            } else {
                this.appIsStringBuilder = false;
                this.str = new StringBuilder();
                this.reorderStart = 0;
                this.lastCC = 0;
            }
        }

        public boolean isEmpty() {
            return this.str.length() == 0;
        }

        public int length() {
            return this.str.length();
        }

        public int getLastCC() {
            return this.lastCC;
        }

        public StringBuilder getStringBuilder() {
            return this.str;
        }

        public boolean equals(CharSequence charSequence, int n, int n2) {
            return UTF16Plus.equal(this.str, 0, this.str.length(), charSequence, n, n2);
        }

        public void append(int n, int n2) {
            if (this.lastCC <= n2 || n2 == 0) {
                this.str.appendCodePoint(n);
                this.lastCC = n2;
                if (n2 <= 1) {
                    this.reorderStart = this.str.length();
                }
            } else {
                this.insert(n, n2);
            }
        }

        public void append(CharSequence charSequence, int n, int n2, boolean bl, int n3, int n4) {
            if (n == n2) {
                return;
            }
            if (this.lastCC <= n3 || n3 == 0) {
                if (n4 <= 1) {
                    this.reorderStart = this.str.length() + (n2 - n);
                } else if (n3 <= 1) {
                    this.reorderStart = this.str.length() + 1;
                }
                this.str.append(charSequence, n, n2);
                this.lastCC = n4;
            } else {
                int n5 = Character.codePointAt(charSequence, n);
                n += Character.charCount(n5);
                this.insert(n5, n3);
                while (n < n2) {
                    n3 = (n += Character.charCount(n5 = Character.codePointAt(charSequence, n))) < n2 ? (bl ? Normalizer2Impl.getCCFromYesOrMaybe(this.impl.getNorm16(n5)) : this.impl.getCC(this.impl.getNorm16(n5))) : n4;
                    this.append(n5, n3);
                }
            }
        }

        @Override
        public ReorderingBuffer append(char c) {
            this.str.append(c);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
            return this;
        }

        public void appendZeroCC(int n) {
            this.str.appendCodePoint(n);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }

        @Override
        public ReorderingBuffer append(CharSequence charSequence) {
            if (charSequence.length() != 0) {
                this.str.append(charSequence);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }

        @Override
        public ReorderingBuffer append(CharSequence charSequence, int n, int n2) {
            if (n != n2) {
                this.str.append(charSequence, n, n2);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }

        public void flush() {
            if (this.appIsStringBuilder) {
                this.reorderStart = this.str.length();
            } else {
                try {
                    this.app.append(this.str);
                    this.str.setLength(0);
                    this.reorderStart = 0;
                } catch (IOException iOException) {
                    throw new ICUUncheckedIOException(iOException);
                }
            }
            this.lastCC = 0;
        }

        public ReorderingBuffer flushAndAppendZeroCC(CharSequence charSequence, int n, int n2) {
            if (this.appIsStringBuilder) {
                this.str.append(charSequence, n, n2);
                this.reorderStart = this.str.length();
            } else {
                try {
                    this.app.append(this.str).append(charSequence, n, n2);
                    this.str.setLength(0);
                    this.reorderStart = 0;
                } catch (IOException iOException) {
                    throw new ICUUncheckedIOException(iOException);
                }
            }
            this.lastCC = 0;
            return this;
        }

        public void remove() {
            this.str.setLength(0);
            this.lastCC = 0;
            this.reorderStart = 0;
        }

        public void removeSuffix(int n) {
            int n2 = this.str.length();
            this.str.delete(n2 - n, n2);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }

        private void insert(int n, int n2) {
            this.setIterator();
            this.skipPrevious();
            while (this.previousCC() > n2) {
            }
            if (n <= 65535) {
                this.str.insert(this.codePointLimit, (char)n);
                if (n2 <= 1) {
                    this.reorderStart = this.codePointLimit + 1;
                }
            } else {
                this.str.insert(this.codePointLimit, Character.toChars(n));
                if (n2 <= 1) {
                    this.reorderStart = this.codePointLimit + 2;
                }
            }
        }

        private void setIterator() {
            this.codePointStart = this.str.length();
        }

        private void skipPrevious() {
            this.codePointLimit = this.codePointStart;
            this.codePointStart = this.str.offsetByCodePoints(this.codePointStart, -1);
        }

        private int previousCC() {
            this.codePointLimit = this.codePointStart;
            if (this.reorderStart >= this.codePointStart) {
                return 1;
            }
            int n = this.str.codePointBefore(this.codePointStart);
            this.codePointStart -= Character.charCount(n);
            return this.impl.getCCFromYesOrMaybeCP(n);
        }

        @Override
        public Appendable append(char c) throws IOException {
            return this.append(c);
        }

        @Override
        public Appendable append(CharSequence charSequence, int n, int n2) throws IOException {
            return this.append(charSequence, n, n2);
        }

        @Override
        public Appendable append(CharSequence charSequence) throws IOException {
            return this.append(charSequence);
        }
    }

    public static final class Hangul {
        public static final int JAMO_L_BASE = 4352;
        public static final int JAMO_L_END = 4370;
        public static final int JAMO_V_BASE = 4449;
        public static final int JAMO_V_END = 4469;
        public static final int JAMO_T_BASE = 4519;
        public static final int JAMO_T_END = 4546;
        public static final int HANGUL_BASE = 44032;
        public static final int HANGUL_END = 55203;
        public static final int JAMO_L_COUNT = 19;
        public static final int JAMO_V_COUNT = 21;
        public static final int JAMO_T_COUNT = 28;
        public static final int JAMO_L_LIMIT = 4371;
        public static final int JAMO_V_LIMIT = 4470;
        public static final int JAMO_VT_COUNT = 588;
        public static final int HANGUL_COUNT = 11172;
        public static final int HANGUL_LIMIT = 55204;

        public static boolean isHangul(int n) {
            return 44032 <= n && n < 55204;
        }

        public static boolean isHangulLV(int n) {
            return 0 <= (n -= 44032) && n < 11172 && n % 28 == 0;
        }

        public static boolean isJamoL(int n) {
            return 4352 <= n && n < 4371;
        }

        public static boolean isJamoV(int n) {
            return 4449 <= n && n < 4470;
        }

        public static boolean isJamoT(int n) {
            int n2 = n - 4519;
            return 0 < n2 && n2 < 28;
        }

        public static boolean isJamo(int n) {
            return 4352 <= n && n <= 4546 && (n <= 4370 || 4449 <= n && n <= 4469 || 4519 < n);
        }

        public static int decompose(int n, Appendable appendable) {
            try {
                int n2 = (n -= 44032) % 28;
                appendable.append((char)(4352 + (n /= 28) / 21));
                appendable.append((char)(4449 + n % 21));
                if (n2 == 0) {
                    return 2;
                }
                appendable.append((char)(4519 + n2));
                return 3;
            } catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }

        public static void getRawDecomposition(int n, Appendable appendable) {
            try {
                int n2 = n;
                int n3 = (n -= 44032) % 28;
                if (n3 == 0) {
                    appendable.append((char)(4352 + (n /= 28) / 21));
                    appendable.append((char)(4449 + n % 21));
                } else {
                    appendable.append((char)(n2 - n3));
                    appendable.append((char)(4519 + n3));
                }
            } catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }
    }
}

