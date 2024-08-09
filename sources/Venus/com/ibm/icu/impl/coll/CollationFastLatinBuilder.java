/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.coll.Collation;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationFastLatin;
import com.ibm.icu.impl.coll.UVector64;
import com.ibm.icu.util.CharsTrie;

final class CollationFastLatinBuilder {
    private static final int NUM_SPECIAL_GROUPS = 4;
    private static final long CONTRACTION_FLAG = 0x80000000L;
    private long ce0 = 0L;
    private long ce1 = 0L;
    private long[][] charCEs = new long[448][2];
    private UVector64 contractionCEs;
    private UVector64 uniqueCEs;
    private char[] miniCEs = null;
    long[] lastSpecialPrimaries = new long[4];
    private long firstDigitPrimary = 0L;
    private long firstLatinPrimary = 0L;
    private long lastLatinPrimary = 0L;
    private long firstShortPrimary = 0L;
    private boolean shortPrimaryOverflow = false;
    private StringBuilder result = new StringBuilder();
    private int headerLength = 0;
    static final boolean $assertionsDisabled = !CollationFastLatinBuilder.class.desiredAssertionStatus();

    private static final int compareInt64AsUnsigned(long l, long l2) {
        if ((l += Long.MIN_VALUE) < (l2 += Long.MIN_VALUE)) {
            return 1;
        }
        if (l > l2) {
            return 0;
        }
        return 1;
    }

    private static final int binarySearch(long[] lArray, int n, long l) {
        if (n == 0) {
            return 1;
        }
        int n2 = 0;
        int n3;
        int n4;
        while ((n4 = CollationFastLatinBuilder.compareInt64AsUnsigned(l, lArray[n3 = (int)(((long)n2 + (long)n) / 2L)])) != 0) {
            if (n4 < 0) {
                if (n3 == n2) {
                    return ~n2;
                }
                n = n3;
                continue;
            }
            if (n3 == n2) {
                return ~(n2 + 1);
            }
            n2 = n3;
        }
        return n3;
    }

    CollationFastLatinBuilder() {
        this.contractionCEs = new UVector64();
        this.uniqueCEs = new UVector64();
    }

    boolean forData(CollationData collationData) {
        boolean bl;
        if (this.result.length() != 0) {
            throw new IllegalStateException("attempt to reuse a CollationFastLatinBuilder");
        }
        if (!this.loadGroups(collationData)) {
            return true;
        }
        this.firstShortPrimary = this.firstDigitPrimary;
        this.getCEs(collationData);
        this.encodeUniqueCEs();
        if (this.shortPrimaryOverflow) {
            this.firstShortPrimary = this.firstLatinPrimary;
            this.resetCEs();
            this.getCEs(collationData);
            this.encodeUniqueCEs();
        }
        boolean bl2 = bl = !this.shortPrimaryOverflow;
        if (bl) {
            this.encodeCharCEs();
            this.encodeContractions();
        }
        this.contractionCEs.removeAllElements();
        this.uniqueCEs.removeAllElements();
        return bl;
    }

    char[] getHeader() {
        char[] cArray = new char[this.headerLength];
        this.result.getChars(0, this.headerLength, cArray, 0);
        return cArray;
    }

    char[] getTable() {
        char[] cArray = new char[this.result.length() - this.headerLength];
        this.result.getChars(this.headerLength, this.result.length(), cArray, 0);
        return cArray;
    }

    private boolean loadGroups(CollationData collationData) {
        this.headerLength = 5;
        int n = 0x200 | this.headerLength;
        this.result.append((char)n);
        for (int i = 0; i < 4; ++i) {
            this.lastSpecialPrimaries[i] = collationData.getLastPrimaryForGroup(4096 + i);
            if (this.lastSpecialPrimaries[i] == 0L) {
                return true;
            }
            this.result.append(0);
        }
        this.firstDigitPrimary = collationData.getFirstPrimaryForGroup(4100);
        this.firstLatinPrimary = collationData.getFirstPrimaryForGroup(25);
        this.lastLatinPrimary = collationData.getLastPrimaryForGroup(25);
        return this.firstDigitPrimary == 0L || this.firstLatinPrimary == 0L;
    }

    private boolean inSameGroup(long l, long l2) {
        if (l >= this.firstShortPrimary) {
            return l2 >= this.firstShortPrimary;
        }
        if (l2 >= this.firstShortPrimary) {
            return true;
        }
        long l3 = this.lastSpecialPrimaries[3];
        if (l > l3) {
            return l2 > l3;
        }
        if (l2 > l3) {
            return true;
        }
        if (!($assertionsDisabled || l != 0L && l2 != 0L)) {
            throw new AssertionError();
        }
        int n = 0;
        long l4;
        while (l > (l4 = this.lastSpecialPrimaries[n])) {
            if (l2 <= l4) {
                return true;
            }
            ++n;
        }
        return l2 <= l4;
    }

    private void resetCEs() {
        this.contractionCEs.removeAllElements();
        this.uniqueCEs.removeAllElements();
        this.shortPrimaryOverflow = false;
        this.result.setLength(this.headerLength);
    }

    private void getCEs(CollationData collationData) {
        int n = 0;
        int n2 = 0;
        while (true) {
            CollationData collationData2;
            if (n2 == 384) {
                n2 = 8192;
            } else if (n2 == 8256) break;
            int n3 = collationData.getCE32(n2);
            if (n3 == 192) {
                collationData2 = collationData.base;
                n3 = collationData2.getCE32(n2);
            } else {
                collationData2 = collationData;
            }
            if (this.getCEsFromCE32(collationData2, n2, n3)) {
                this.charCEs[n][0] = this.ce0;
                this.charCEs[n][1] = this.ce1;
                this.addUniqueCE(this.ce0);
                this.addUniqueCE(this.ce1);
            } else {
                this.ce0 = 0x101000100L;
                this.charCEs[n][0] = 0x101000100L;
                this.ce1 = 0L;
                this.charCEs[n][1] = 0L;
            }
            if (n2 == 0 && !CollationFastLatinBuilder.isContractionCharCE(this.ce0)) {
                if (!$assertionsDisabled && !this.contractionCEs.isEmpty()) {
                    throw new AssertionError();
                }
                this.addContractionEntry(511, this.ce0, this.ce1);
                this.charCEs[0][0] = 0x180000000L;
                this.charCEs[0][1] = 0L;
            }
            ++n;
            n2 = (char)(n2 + 1);
        }
        this.contractionCEs.addElement(511L);
    }

    private boolean getCEsFromCE32(CollationData collationData, int n, int n2) {
        int n3;
        n2 = collationData.getFinalCE32(n2);
        this.ce1 = 0L;
        if (Collation.isSimpleOrLongCE32(n2)) {
            this.ce0 = Collation.ceFromCE32(n2);
        } else {
            switch (Collation.tagFromCE32(n2)) {
                case 4: {
                    this.ce0 = Collation.latinCE0FromCE32(n2);
                    this.ce1 = Collation.latinCE1FromCE32(n2);
                    break;
                }
                case 5: {
                    int n4 = Collation.indexFromCE32(n2);
                    int n5 = Collation.lengthFromCE32(n2);
                    if (n5 <= 2) {
                        this.ce0 = Collation.ceFromCE32(collationData.ce32s[n4]);
                        if (n5 != 2) break;
                        this.ce1 = Collation.ceFromCE32(collationData.ce32s[n4 + 1]);
                        break;
                    }
                    return true;
                }
                case 6: {
                    int n4 = Collation.indexFromCE32(n2);
                    int n6 = Collation.lengthFromCE32(n2);
                    if (n6 <= 2) {
                        this.ce0 = collationData.ces[n4];
                        if (n6 != 2) break;
                        this.ce1 = collationData.ces[n4 + 1];
                        break;
                    }
                    return true;
                }
                case 9: {
                    if (!$assertionsDisabled && n < 0) {
                        throw new AssertionError();
                    }
                    return this.getCEsFromContractionCE32(collationData, n2);
                }
                case 14: {
                    if (!$assertionsDisabled && n < 0) {
                        throw new AssertionError();
                    }
                    this.ce0 = collationData.getCEFromOffsetCE32(n, n2);
                    break;
                }
                default: {
                    return true;
                }
            }
        }
        if (this.ce0 == 0L) {
            return this.ce1 == 0L;
        }
        long l = this.ce0 >>> 32;
        if (l == 0L) {
            return true;
        }
        if (l > this.lastLatinPrimary) {
            return true;
        }
        int n7 = (int)this.ce0;
        if (l < this.firstShortPrimary && (n3 = n7 & 0xFFFFC000) != 0x5000000) {
            return true;
        }
        if ((n7 & 0x3F3F) < 1280) {
            return true;
        }
        if (this.ce1 != 0L) {
            int n8;
            long l2 = this.ce1 >>> 32;
            if (l2 == 0L ? l < this.firstShortPrimary : !this.inSameGroup(l, l2)) {
                return true;
            }
            int n9 = (int)this.ce1;
            if (n9 >>> 16 == 0) {
                return true;
            }
            if (l2 != 0L && l2 < this.firstShortPrimary && (n8 = n9 & 0xFFFFC000) != 0x5000000) {
                return true;
            }
            if ((n7 & 0x3F3F) < 1280) {
                return true;
            }
        }
        return ((this.ce0 | this.ce1) & 0xC0L) != 0L;
    }

    private boolean getCEsFromContractionCE32(CollationData collationData, int n) {
        int n2 = Collation.indexFromCE32(n);
        n = collationData.getCE32FromContexts(n2);
        if (!$assertionsDisabled && Collation.isContractionCE32(n)) {
            throw new AssertionError();
        }
        int n3 = this.contractionCEs.size();
        if (this.getCEsFromCE32(collationData, -1, n)) {
            this.addContractionEntry(511, this.ce0, this.ce1);
        } else {
            this.addContractionEntry(511, 0x101000100L, 0L);
        }
        int n4 = -1;
        boolean bl = false;
        CharsTrie.Iterator iterator2 = CharsTrie.iterator(collationData.contexts, n2 + 2, 0);
        while (iterator2.hasNext()) {
            CharsTrie.Entry entry = iterator2.next();
            CharSequence charSequence = entry.chars;
            int n5 = CollationFastLatin.getCharIndex(charSequence.charAt(0));
            if (n5 < 0) continue;
            if (n5 == n4) {
                if (!bl) continue;
                this.addContractionEntry(n5, 0x101000100L, 0L);
                bl = false;
                continue;
            }
            if (bl) {
                this.addContractionEntry(n4, this.ce0, this.ce1);
            }
            n = entry.value;
            if (charSequence.length() == 1 && this.getCEsFromCE32(collationData, -1, n)) {
                bl = true;
            } else {
                this.addContractionEntry(n5, 0x101000100L, 0L);
                bl = false;
            }
            n4 = n5;
        }
        if (bl) {
            this.addContractionEntry(n4, this.ce0, this.ce1);
        }
        this.ce0 = 0x180000000L | (long)n3;
        this.ce1 = 0L;
        return false;
    }

    private void addContractionEntry(int n, long l, long l2) {
        this.contractionCEs.addElement(n);
        this.contractionCEs.addElement(l);
        this.contractionCEs.addElement(l2);
        this.addUniqueCE(l);
        this.addUniqueCE(l2);
    }

    private void addUniqueCE(long l) {
        if (l == 0L || l >>> 32 == 1L) {
            return;
        }
        int n = CollationFastLatinBuilder.binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), l &= 0xFFFFFFFFFFFF3FFFL);
        if (n < 0) {
            this.uniqueCEs.insertElementAt(l, ~n);
        }
    }

    private int getMiniCE(long l) {
        int n = CollationFastLatinBuilder.binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), l &= 0xFFFFFFFFFFFF3FFFL);
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        return this.miniCEs[n];
    }

    /*
     * Unable to fully structure code
     */
    private void encodeUniqueCEs() {
        this.miniCEs = new char[this.uniqueCEs.size()];
        var1_1 = 0;
        var2_2 = this.lastSpecialPrimaries[var1_1];
        if (!CollationFastLatinBuilder.$assertionsDisabled && (int)this.uniqueCEs.elementAti(0) >>> 16 == 0) {
            throw new AssertionError();
        }
        var4_3 = 0L;
        var6_4 = 0;
        var7_5 = 0;
        var8_6 = 0;
        var9_7 = 0;
        for (var10_8 = 0; var10_8 < this.uniqueCEs.size(); ++var10_8) {
            block34: {
                block36: {
                    block35: {
                        block33: {
                            var11_9 = this.uniqueCEs.elementAti(var10_8);
                            var13_10 = var11_9 >>> 32;
                            if (var13_10 == var4_3) break block33;
                            while (var13_10 > var2_2) {
                                if (!CollationFastLatinBuilder.$assertionsDisabled && var7_5 > 4088) {
                                    throw new AssertionError();
                                }
                                this.result.setCharAt(1 + var1_1, (char)var7_5);
                                if (++var1_1 < 4) {
                                    var2_2 = this.lastSpecialPrimaries[var1_1];
                                    continue;
                                }
                                var2_2 = 0xFFFFFFFFL;
                                break;
                            }
                            if (var13_10 >= this.firstShortPrimary) ** GOTO lbl33
                            if (var7_5 == 0) {
                                var7_5 = 3072;
                            } else if (var7_5 < 4088) {
                                var7_5 += 8;
                            } else {
                                this.miniCEs[var10_8] = '\u0001';
                                continue;
lbl33:
                                // 1 sources

                                if (var7_5 < 4096) {
                                    var7_5 = 4096;
                                } else if (var7_5 < 63488) {
                                    var7_5 += 1024;
                                } else {
                                    this.shortPrimaryOverflow = true;
                                    this.miniCEs[var10_8] = '\u0001';
                                    continue;
                                }
                            }
                            var4_3 = var13_10;
                            var6_4 = 1280;
                            var8_6 = 160;
                            var9_7 = 0;
                        }
                        if ((var16_12 = (var15_11 = (int)var11_9) >>> 16) == var6_4) break block34;
                        if (var7_5 != 0) break block35;
                        if (var8_6 == 0) {
                            var8_6 = 384;
                        } else if (var8_6 < 992) {
                            var8_6 += 32;
                        } else {
                            this.miniCEs[var10_8] = '\u0001';
                            continue;
                        }
                        var6_4 = var16_12;
                        var9_7 = 0;
                        break block36;
                    }
                    if (var16_12 >= 1280) ** GOTO lbl70
                    if (var8_6 == 160) {
                        var8_6 = 0;
                    } else if (var8_6 < 128) {
                        var8_6 += 32;
                    } else {
                        this.miniCEs[var10_8] = '\u0001';
                        continue;
lbl70:
                        // 1 sources

                        if (var16_12 == 1280) {
                            var8_6 = 160;
                        } else if (var8_6 < 192) {
                            var8_6 = 192;
                        } else if (var8_6 < 352) {
                            var8_6 += 32;
                        } else {
                            this.miniCEs[var10_8] = '\u0001';
                            continue;
                        }
                    }
                }
                var6_4 = var16_12;
                var9_7 = 0;
            }
            if (!CollationFastLatinBuilder.$assertionsDisabled && (var15_11 & 49152) != 0) {
                throw new AssertionError();
            }
            var17_13 = var15_11 & 16191;
            if (var17_13 > 1280) {
                if (var9_7 < 7) {
                    ++var9_7;
                } else {
                    this.miniCEs[var10_8] = '\u0001';
                    continue;
                }
            }
            if (3072 <= var7_5 && var7_5 <= 4088) {
                if (!CollationFastLatinBuilder.$assertionsDisabled && var8_6 != 160) {
                    throw new AssertionError();
                }
                this.miniCEs[var10_8] = (char)(var7_5 | var9_7);
                continue;
            }
            this.miniCEs[var10_8] = (char)(var7_5 | var8_6 | var9_7);
        }
    }

    private void encodeCharCEs() {
        int n;
        int n2 = this.result.length();
        for (n = 0; n < 448; ++n) {
            this.result.append(0);
        }
        n = this.result.length();
        for (int i = 0; i < 448; ++i) {
            long l = this.charCEs[i][0];
            if (CollationFastLatinBuilder.isContractionCharCE(l)) continue;
            int n3 = this.encodeTwoCEs(l, this.charCEs[i][1]);
            if (n3 >>> 16 > 0) {
                int n4 = this.result.length() - n;
                if (n4 > 1023) {
                    n3 = 1;
                } else {
                    this.result.append((char)(n3 >> 16)).append((char)n3);
                    n3 = 0x800 | n4;
                }
            }
            this.result.setCharAt(n2 + i, (char)n3);
        }
    }

    private void encodeContractions() {
        int n = this.headerLength + 448;
        int n2 = this.result.length();
        for (int i = 0; i < 448; ++i) {
            long l;
            long l2 = this.charCEs[i][0];
            if (!CollationFastLatinBuilder.isContractionCharCE(l2)) continue;
            int n3 = this.result.length() - n;
            if (n3 > 1023) {
                this.result.setCharAt(this.headerLength + i, '\u0001');
                continue;
            }
            boolean bl = true;
            int n4 = (int)l2 & Integer.MAX_VALUE;
            while ((l = this.contractionCEs.elementAti(n4)) != 511L || bl) {
                long l3;
                long l4 = this.contractionCEs.elementAti(n4 + 1);
                int n5 = this.encodeTwoCEs(l4, l3 = this.contractionCEs.elementAti(n4 + 2));
                if (n5 == 1) {
                    this.result.append((char)(l | 0x200L));
                } else if (n5 >>> 16 == 0) {
                    this.result.append((char)(l | 0x400L));
                    this.result.append((char)n5);
                } else {
                    this.result.append((char)(l | 0x600L));
                    this.result.append((char)(n5 >> 16)).append((char)n5);
                }
                bl = false;
                n4 += 3;
            }
            this.result.setCharAt(this.headerLength + i, (char)(0x400 | n3));
        }
        if (this.result.length() > n2) {
            this.result.append('\u01ff');
        }
    }

    private int encodeTwoCEs(long l, long l2) {
        int n;
        if (l == 0L) {
            return 1;
        }
        if (l == 0x101000100L) {
            return 0;
        }
        if (!$assertionsDisabled && l >>> 32 == 1L) {
            throw new AssertionError();
        }
        int n2 = this.getMiniCE(l);
        if (n2 == 1) {
            return n2;
        }
        if (n2 >= 4096) {
            n = ((int)l & 0xC000) >> 11;
            n2 |= (n += 8);
        }
        if (l2 == 0L) {
            return n2;
        }
        n = this.getMiniCE(l2);
        if (n == 1) {
            return n;
        }
        int n3 = (int)l2 & 0xC000;
        if (n2 >= 4096 && (n2 & 0x3E0) == 160) {
            int n4 = n & 0x3E0;
            int n5 = n & 7;
            if (n4 >= 384 && n3 == 0 && n5 == 0) {
                return n2 & 0xFFFFFC1F | n4;
            }
        }
        if (n <= 992 || 4096 <= n) {
            n3 = (n3 >> 11) + 8;
            n |= n3;
        }
        return n2 << 16 | n;
    }

    private static boolean isContractionCharCE(long l) {
        return l >>> 32 == 1L && l != 0x101000100L;
    }
}

