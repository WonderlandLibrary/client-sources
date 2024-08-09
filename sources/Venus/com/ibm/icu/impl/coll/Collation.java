/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

public final class Collation {
    public static final int SENTINEL_CP = -1;
    public static final int LESS = -1;
    public static final int EQUAL = 0;
    public static final int GREATER = 1;
    public static final int TERMINATOR_BYTE = 0;
    public static final int LEVEL_SEPARATOR_BYTE = 1;
    static final int BEFORE_WEIGHT16 = 256;
    public static final int MERGE_SEPARATOR_BYTE = 2;
    public static final long MERGE_SEPARATOR_PRIMARY = 0x2000000L;
    static final int MERGE_SEPARATOR_CE32 = 0x2000505;
    public static final int PRIMARY_COMPRESSION_LOW_BYTE = 3;
    public static final int PRIMARY_COMPRESSION_HIGH_BYTE = 255;
    static final int COMMON_BYTE = 5;
    public static final int COMMON_WEIGHT16 = 1280;
    static final int COMMON_SECONDARY_CE = 0x5000000;
    static final int COMMON_TERTIARY_CE = 1280;
    public static final int COMMON_SEC_AND_TER_CE = 0x5000500;
    static final int SECONDARY_MASK = -65536;
    public static final int CASE_MASK = 49152;
    static final int SECONDARY_AND_CASE_MASK = -16384;
    public static final int ONLY_TERTIARY_MASK = 16191;
    static final int ONLY_SEC_TER_MASK = -49345;
    static final int CASE_AND_TERTIARY_MASK = 65343;
    public static final int QUATERNARY_MASK = 192;
    public static final int CASE_AND_QUATERNARY_MASK = 49344;
    static final int UNASSIGNED_IMPLICIT_BYTE = 254;
    static final long FIRST_UNASSIGNED_PRIMARY = 4261675520L;
    static final int TRAIL_WEIGHT_BYTE = 255;
    static final long FIRST_TRAILING_PRIMARY = 0xFF020200L;
    public static final long MAX_PRIMARY = 0xFFFF0000L;
    static final int MAX_REGULAR_CE32 = -64251;
    public static final long FFFD_PRIMARY = 0xFFFD0000L;
    static final int FFFD_CE32 = -195323;
    static final int SPECIAL_CE32_LOW_BYTE = 192;
    static final int FALLBACK_CE32 = 192;
    static final int LONG_PRIMARY_CE32_LOW_BYTE = 193;
    static final int UNASSIGNED_CE32 = -1;
    static final int NO_CE32 = 1;
    static final long NO_CE_PRIMARY = 1L;
    static final int NO_CE_WEIGHT16 = 256;
    public static final long NO_CE = 0x101000100L;
    public static final int NO_LEVEL = 0;
    public static final int PRIMARY_LEVEL = 1;
    public static final int SECONDARY_LEVEL = 2;
    public static final int CASE_LEVEL = 3;
    public static final int TERTIARY_LEVEL = 4;
    public static final int QUATERNARY_LEVEL = 5;
    public static final int IDENTICAL_LEVEL = 6;
    public static final int ZERO_LEVEL = 7;
    static final int NO_LEVEL_FLAG = 1;
    static final int PRIMARY_LEVEL_FLAG = 2;
    static final int SECONDARY_LEVEL_FLAG = 4;
    static final int CASE_LEVEL_FLAG = 8;
    static final int TERTIARY_LEVEL_FLAG = 16;
    static final int QUATERNARY_LEVEL_FLAG = 32;
    static final int IDENTICAL_LEVEL_FLAG = 64;
    static final int ZERO_LEVEL_FLAG = 128;
    static final int FALLBACK_TAG = 0;
    static final int LONG_PRIMARY_TAG = 1;
    static final int LONG_SECONDARY_TAG = 2;
    static final int RESERVED_TAG_3 = 3;
    static final int LATIN_EXPANSION_TAG = 4;
    static final int EXPANSION32_TAG = 5;
    static final int EXPANSION_TAG = 6;
    static final int BUILDER_DATA_TAG = 7;
    static final int PREFIX_TAG = 8;
    static final int CONTRACTION_TAG = 9;
    static final int DIGIT_TAG = 10;
    static final int U0000_TAG = 11;
    static final int HANGUL_TAG = 12;
    static final int LEAD_SURROGATE_TAG = 13;
    static final int OFFSET_TAG = 14;
    static final int IMPLICIT_TAG = 15;
    static final int MAX_EXPANSION_LENGTH = 31;
    static final int MAX_INDEX = 524287;
    static final int CONTRACT_SINGLE_CP_NO_MATCH = 256;
    static final int CONTRACT_NEXT_CCC = 512;
    static final int CONTRACT_TRAILING_CCC = 1024;
    static final int HANGUL_NO_SPECIAL_JAMO = 256;
    static final int LEAD_ALL_UNASSIGNED = 0;
    static final int LEAD_ALL_FALLBACK = 256;
    static final int LEAD_MIXED = 512;
    static final int LEAD_TYPE_MASK = 768;
    static final boolean $assertionsDisabled = !Collation.class.desiredAssertionStatus();

    static boolean isAssignedCE32(int n) {
        return n != 192 && n != -1;
    }

    static int makeLongPrimaryCE32(long l) {
        return (int)(l | 0xC1L);
    }

    static long primaryFromLongPrimaryCE32(int n) {
        return (long)n & 0xFFFFFF00L;
    }

    static long ceFromLongPrimaryCE32(int n) {
        return (long)(n & 0xFFFFFF00) << 32 | 0x5000500L;
    }

    static int makeLongSecondaryCE32(int n) {
        return n | 0xC0 | 2;
    }

    static long ceFromLongSecondaryCE32(int n) {
        return (long)n & 0xFFFFFF00L;
    }

    static int makeCE32FromTagIndexAndLength(int n, int n2, int n3) {
        return n2 << 13 | n3 << 8 | 0xC0 | n;
    }

    static int makeCE32FromTagAndIndex(int n, int n2) {
        return n2 << 13 | 0xC0 | n;
    }

    static boolean isSpecialCE32(int n) {
        return (n & 0xFF) >= 192;
    }

    static int tagFromCE32(int n) {
        return n & 0xF;
    }

    static boolean hasCE32Tag(int n, int n2) {
        return Collation.isSpecialCE32(n) && Collation.tagFromCE32(n) == n2;
    }

    static boolean isLongPrimaryCE32(int n) {
        return Collation.hasCE32Tag(n, 1);
    }

    static boolean isSimpleOrLongCE32(int n) {
        return !Collation.isSpecialCE32(n) || Collation.tagFromCE32(n) == 1 || Collation.tagFromCE32(n) == 2;
    }

    static boolean isSelfContainedCE32(int n) {
        return !Collation.isSpecialCE32(n) || Collation.tagFromCE32(n) == 1 || Collation.tagFromCE32(n) == 2 || Collation.tagFromCE32(n) == 4;
    }

    static boolean isPrefixCE32(int n) {
        return Collation.hasCE32Tag(n, 8);
    }

    static boolean isContractionCE32(int n) {
        return Collation.hasCE32Tag(n, 9);
    }

    static boolean ce32HasContext(int n) {
        return Collation.isSpecialCE32(n) && (Collation.tagFromCE32(n) == 8 || Collation.tagFromCE32(n) == 9);
    }

    static long latinCE0FromCE32(int n) {
        return (long)(n & 0xFF000000) << 32 | 0x5000000L | (long)((n & 0xFF0000) >> 8);
    }

    static long latinCE1FromCE32(int n) {
        return ((long)n & 0xFF00L) << 16 | 0x500L;
    }

    static int indexFromCE32(int n) {
        return n >>> 13;
    }

    static int lengthFromCE32(int n) {
        return n >> 8 & 0x1F;
    }

    static char digitFromCE32(int n) {
        return (char)(n >> 8 & 0xF);
    }

    static long ceFromSimpleCE32(int n) {
        if (!$assertionsDisabled && (n & 0xFF) >= 192) {
            throw new AssertionError();
        }
        return (long)(n & 0xFFFF0000) << 32 | (long)(n & 0xFF00) << 16 | (long)((n & 0xFF) << 8);
    }

    static long ceFromCE32(int n) {
        int n2 = n & 0xFF;
        if (n2 < 192) {
            return (long)(n & 0xFFFF0000) << 32 | (long)(n & 0xFF00) << 16 | (long)(n2 << 8);
        }
        n -= n2;
        if ((n2 & 0xF) == 1) {
            return (long)n << 32 | 0x5000500L;
        }
        if (!$assertionsDisabled && (n2 & 0xF) != 2) {
            throw new AssertionError();
        }
        return (long)n & 0xFFFFFFFFL;
    }

    public static long makeCE(long l) {
        return l << 32 | 0x5000500L;
    }

    static long makeCE(long l, int n, int n2, int n3) {
        return l << 32 | (long)n << 16 | (long)n2 | (long)(n3 << 6);
    }

    public static long incTwoBytePrimaryByOffset(long l, boolean bl, int n) {
        long l2;
        if (bl) {
            l2 = (n += ((int)(l >> 16) & 0xFF) - 4) % 251 + 4 << 16;
            n /= 251;
        } else {
            l2 = (n += ((int)(l >> 16) & 0xFF) - 2) % 254 + 2 << 16;
            n /= 254;
        }
        return l2 | (l & 0xFF000000L) + ((long)n << 24);
    }

    public static long incThreeBytePrimaryByOffset(long l, boolean bl, int n) {
        long l2 = (n += ((int)(l >> 8) & 0xFF) - 2) % 254 + 2 << 8;
        n /= 254;
        if (bl) {
            l2 |= (long)((n += ((int)(l >> 16) & 0xFF) - 4) % 251 + 4 << 16);
            n /= 251;
        } else {
            l2 |= (long)((n += ((int)(l >> 16) & 0xFF) - 2) % 254 + 2 << 16);
            n /= 254;
        }
        return l2 | (l & 0xFF000000L) + ((long)n << 24);
    }

    static long decTwoBytePrimaryByOneStep(long l, boolean bl, int n) {
        if (!($assertionsDisabled || 0 < n && n <= 127)) {
            throw new AssertionError();
        }
        int n2 = ((int)(l >> 16) & 0xFF) - n;
        if (bl) {
            if (n2 < 4) {
                n2 += 251;
                l -= 0x1000000L;
            }
        } else if (n2 < 2) {
            n2 += 254;
            l -= 0x1000000L;
        }
        return l & 0xFF000000L | (long)(n2 << 16);
    }

    static long decThreeBytePrimaryByOneStep(long l, boolean bl, int n) {
        if (!($assertionsDisabled || 0 < n && n <= 127)) {
            throw new AssertionError();
        }
        int n2 = ((int)(l >> 8) & 0xFF) - n;
        if (n2 >= 2) {
            return l & 0xFFFF0000L | (long)(n2 << 8);
        }
        n2 += 254;
        int n3 = ((int)(l >> 16) & 0xFF) - 1;
        if (bl) {
            if (n3 < 4) {
                n3 = 254;
                l -= 0x1000000L;
            }
        } else if (n3 < 2) {
            n3 = 255;
            l -= 0x1000000L;
        }
        return l & 0xFF000000L | (long)(n3 << 16) | (long)(n2 << 8);
    }

    static long getThreeBytePrimaryForOffsetData(int n, long l) {
        long l2 = l >>> 32;
        int n2 = (int)l;
        int n3 = (n - (n2 >> 8)) * (n2 & 0x7F);
        boolean bl = (n2 & 0x80) != 0;
        return Collation.incThreeBytePrimaryByOffset(l2, bl, n3);
    }

    static long unassignedPrimaryFromCodePoint(int n) {
        long l = 2 + ++n % 18 * 14;
        l |= (long)(2 + (n /= 18) % 254 << 8);
        return (l |= (long)(4 + (n /= 254) % 251 << 16)) | 0xFE000000L;
    }

    static long unassignedCEFromCodePoint(int n) {
        return Collation.makeCE(Collation.unassignedPrimaryFromCodePoint(n));
    }
}

