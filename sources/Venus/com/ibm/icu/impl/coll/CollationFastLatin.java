/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationSettings;

public final class CollationFastLatin {
    public static final int VERSION = 2;
    public static final int LATIN_MAX = 383;
    public static final int LATIN_LIMIT = 384;
    static final int LATIN_MAX_UTF8_LEAD = 197;
    static final int PUNCT_START = 8192;
    static final int PUNCT_LIMIT = 8256;
    static final int NUM_FAST_CHARS = 448;
    static final int SHORT_PRIMARY_MASK = 64512;
    static final int INDEX_MASK = 1023;
    static final int SECONDARY_MASK = 992;
    static final int CASE_MASK = 24;
    static final int LONG_PRIMARY_MASK = 65528;
    static final int TERTIARY_MASK = 7;
    static final int CASE_AND_TERTIARY_MASK = 31;
    static final int TWO_SHORT_PRIMARIES_MASK = -67044352;
    static final int TWO_LONG_PRIMARIES_MASK = -458760;
    static final int TWO_SECONDARIES_MASK = 0x3E003E0;
    static final int TWO_CASES_MASK = 0x180018;
    static final int TWO_TERTIARIES_MASK = 458759;
    static final int CONTRACTION = 1024;
    static final int EXPANSION = 2048;
    static final int MIN_LONG = 3072;
    static final int LONG_INC = 8;
    static final int MAX_LONG = 4088;
    static final int MIN_SHORT = 4096;
    static final int SHORT_INC = 1024;
    static final int MAX_SHORT = 64512;
    static final int MIN_SEC_BEFORE = 0;
    static final int SEC_INC = 32;
    static final int MAX_SEC_BEFORE = 128;
    static final int COMMON_SEC = 160;
    static final int MIN_SEC_AFTER = 192;
    static final int MAX_SEC_AFTER = 352;
    static final int MIN_SEC_HIGH = 384;
    static final int MAX_SEC_HIGH = 992;
    static final int SEC_OFFSET = 32;
    static final int COMMON_SEC_PLUS_OFFSET = 192;
    static final int TWO_SEC_OFFSETS = 0x200020;
    static final int TWO_COMMON_SEC_PLUS_OFFSET = 0xC000C0;
    static final int LOWER_CASE = 8;
    static final int TWO_LOWER_CASES = 524296;
    static final int COMMON_TER = 0;
    static final int MAX_TER_AFTER = 7;
    static final int TER_OFFSET = 32;
    static final int COMMON_TER_PLUS_OFFSET = 32;
    static final int TWO_TER_OFFSETS = 0x200020;
    static final int TWO_COMMON_TER_PLUS_OFFSET = 0x200020;
    static final int MERGE_WEIGHT = 3;
    static final int EOS = 2;
    static final int BAIL_OUT = 1;
    static final int CONTR_CHAR_MASK = 511;
    static final int CONTR_LENGTH_SHIFT = 9;
    public static final int BAIL_OUT_RESULT = -2;
    static final boolean $assertionsDisabled = !CollationFastLatin.class.desiredAssertionStatus();

    static int getCharIndex(char c) {
        if (c <= '\u017f') {
            return c;
        }
        if ('\u2000' <= c && c < '\u2040') {
            return c - 7808;
        }
        return 1;
    }

    public static int getOptions(CollationData collationData, CollationSettings collationSettings, char[] cArray) {
        int n;
        int n2;
        int n3;
        char[] cArray2 = collationData.fastLatinTableHeader;
        if (cArray2 == null) {
            return 1;
        }
        if (!$assertionsDisabled && cArray2[0] >> 8 != 2) {
            throw new AssertionError();
        }
        if (cArray.length != 384) {
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            return 1;
        }
        if ((collationSettings.options & 0xC) == 0) {
            n3 = 3071;
        } else {
            n2 = cArray2[0] & 0xFF;
            int n4 = 1 + collationSettings.getMaxVariable();
            if (n4 >= n2) {
                return 1;
            }
            n3 = cArray2[n4];
        }
        n2 = 0;
        if (collationSettings.hasReordering()) {
            long l = 0L;
            long l2 = 0L;
            long l3 = 0L;
            long l4 = 0L;
            for (int i = 4096; i < 4104; ++i) {
                long l5 = collationData.getFirstPrimaryForGroup(i);
                l5 = collationSettings.reorder(l5);
                if (i == 4100) {
                    l2 = l;
                    l3 = l5;
                    continue;
                }
                if (l5 == 0L) continue;
                if (l5 < l) {
                    return 1;
                }
                if (l3 != 0L && l4 == 0L && l == l2) {
                    l4 = l5;
                }
                l = l5;
            }
            long l6 = collationData.getFirstPrimaryForGroup(25);
            if ((l6 = collationSettings.reorder(l6)) < l) {
                return 1;
            }
            if (l4 == 0L) {
                l4 = l6;
            }
            if (l2 >= l3 || l3 >= l4) {
                n2 = 1;
            }
        }
        char[] cArray3 = collationData.fastLatinTable;
        for (n = 0; n < 384; ++n) {
            int n5 = cArray3[n];
            n5 = n5 >= 4096 ? (n5 &= 0xFC00) : (n5 > n3 ? (n5 &= 0xFFF8) : 0);
            cArray[n] = (char)n5;
        }
        if (n2 != 0 || (collationSettings.options & 2) != 0) {
            for (n = 48; n <= 57; ++n) {
                cArray[n] = '\u0000';
            }
        }
        return n3 << 16 | collationSettings.options;
    }

    public static int compareUTF16(char[] cArray, char[] cArray2, int n, CharSequence charSequence, CharSequence charSequence2, int n2) {
        int n3;
        int n4 = n >> 16;
        n &= 0xFFFF;
        int n5 = n2;
        int n6 = n2;
        int n7 = 0;
        int n8 = 0;
        while (true) {
            block80: {
                block83: {
                    block82: {
                        block81: {
                            if (n7 != 0) break block80;
                            if (n5 != charSequence.length()) break block81;
                            n7 = 2;
                            break block80;
                        }
                        if ((n3 = charSequence.charAt(n5++)) > 383) break block82;
                        n7 = cArray2[n3];
                        if (n7 != 0) break block80;
                        if (n3 <= 57 && n3 >= 48 && (n & 2) != 0) {
                            return 1;
                        }
                        n7 = cArray[n3];
                        break block83;
                    }
                    n7 = 8192 <= n3 && n3 < 8256 ? cArray[n3 - 8192 + 384] : CollationFastLatin.lookup(cArray, n3);
                }
                if (n7 >= 4096) {
                    n7 &= 0xFC00;
                } else if (n7 > n4) {
                    n7 &= 0xFFF8;
                } else {
                    long l = CollationFastLatin.nextPair(cArray, n3, n7, charSequence, n5);
                    if (l < 0L) {
                        ++n5;
                        l ^= 0xFFFFFFFFFFFFFFFFL;
                    }
                    if ((n7 = (int)l) == 1) {
                        return 1;
                    }
                    n7 = CollationFastLatin.getPrimaries(n4, n7);
                    continue;
                }
            }
            while (n8 == 0) {
                if (n6 == charSequence2.length()) {
                    n8 = 2;
                    break;
                }
                if ((n3 = (int)charSequence2.charAt(n6++)) <= 383) {
                    n8 = cArray2[n3];
                    if (n8 != 0) break;
                    if (n3 <= 57 && n3 >= 48 && (n & 2) != 0) {
                        return 1;
                    }
                    n8 = cArray[n3];
                } else {
                    n8 = 8192 <= n3 && n3 < 8256 ? cArray[n3 - 8192 + 384] : CollationFastLatin.lookup(cArray, n3);
                }
                if (n8 >= 4096) {
                    n8 &= 0xFC00;
                    break;
                }
                if (n8 > n4) {
                    n8 &= 0xFFF8;
                    break;
                }
                long l = CollationFastLatin.nextPair(cArray, n3, n8, charSequence2, n6);
                if (l < 0L) {
                    ++n6;
                    l ^= 0xFFFFFFFFFFFFFFFFL;
                }
                if ((n8 = (int)l) == 1) {
                    return 1;
                }
                n8 = CollationFastLatin.getPrimaries(n4, n8);
            }
            if (n7 == n8) {
                if (n7 == 2) break;
                n8 = 0;
                n7 = 0;
                continue;
            }
            n3 = n7 & 0xFFFF;
            int n9 = n8 & 0xFFFF;
            if (n3 != n9) {
                return n3 < n9 ? -1 : 1;
            }
            if (n7 == 2) break;
            n7 >>>= 16;
            n8 >>>= 16;
        }
        if (CollationSettings.getStrength(n) >= 1) {
            n5 = n6 = n2;
            n8 = 0;
            n7 = 0;
            while (true) {
                if (n7 == 0) {
                    if (n5 == charSequence.length()) {
                        n7 = 2;
                    } else if ((n7 = (n3 = (int)charSequence.charAt(n5++)) <= 383 ? cArray[n3] : (8192 <= n3 && n3 < 8256 ? cArray[n3 - 8192 + 384] : CollationFastLatin.lookup(cArray, n3))) >= 4096) {
                        n7 = CollationFastLatin.getSecondariesFromOneShortCE(n7);
                    } else if (n7 > n4) {
                        n7 = 192;
                    } else {
                        long l = CollationFastLatin.nextPair(cArray, n3, n7, charSequence, n5);
                        if (l < 0L) {
                            ++n5;
                            l ^= 0xFFFFFFFFFFFFFFFFL;
                        }
                        n7 = CollationFastLatin.getSecondaries(n4, (int)l);
                        continue;
                    }
                }
                while (n8 == 0) {
                    if (n6 == charSequence2.length()) {
                        n8 = 2;
                        break;
                    }
                    if ((n8 = (n3 = (int)charSequence2.charAt(n6++)) <= 383 ? cArray[n3] : (8192 <= n3 && n3 < 8256 ? cArray[n3 - 8192 + 384] : CollationFastLatin.lookup(cArray, n3))) >= 4096) {
                        n8 = CollationFastLatin.getSecondariesFromOneShortCE(n8);
                        break;
                    }
                    if (n8 > n4) {
                        n8 = 192;
                        break;
                    }
                    long l = CollationFastLatin.nextPair(cArray, n3, n8, charSequence2, n6);
                    if (l < 0L) {
                        ++n6;
                        l ^= 0xFFFFFFFFFFFFFFFFL;
                    }
                    n8 = CollationFastLatin.getSecondaries(n4, (int)l);
                }
                if (n7 == n8) {
                    if (n7 == 2) break;
                    n8 = 0;
                    n7 = 0;
                    continue;
                }
                n3 = n7 & 0xFFFF;
                int n10 = n8 & 0xFFFF;
                if (n3 != n10) {
                    if ((n & 0x800) != 0) {
                        return 1;
                    }
                    return n3 < n10 ? -1 : 1;
                }
                if (n7 == 2) break;
                n7 >>>= 16;
                n8 >>>= 16;
            }
        }
        if ((n & 0x400) != 0) {
            n3 = CollationSettings.getStrength(n) == 0 ? 1 : 0;
            n5 = n6 = n2;
            n8 = 0;
            n7 = 0;
            while (true) {
                int n11;
                if (n7 == 0) {
                    if (n5 == charSequence.length()) {
                        n7 = 2;
                    } else {
                        int n12 = n7 = (n11 = charSequence.charAt(n5++)) <= 383 ? cArray[n11] : CollationFastLatin.lookup(cArray, n11);
                        if (n7 < 3072) {
                            long l = CollationFastLatin.nextPair(cArray, n11, n7, charSequence, n5);
                            if (l < 0L) {
                                ++n5;
                                l ^= 0xFFFFFFFFFFFFFFFFL;
                            }
                            n7 = (int)l;
                        }
                        n7 = CollationFastLatin.getCases(n4, n3 != 0, n7);
                        continue;
                    }
                }
                while (n8 == 0) {
                    if (n6 == charSequence2.length()) {
                        n8 = 2;
                        break;
                    }
                    int n13 = n8 = (n11 = (int)charSequence2.charAt(n6++)) <= 383 ? cArray[n11] : CollationFastLatin.lookup(cArray, n11);
                    if (n8 < 3072) {
                        long l = CollationFastLatin.nextPair(cArray, n11, n8, charSequence2, n6);
                        if (l < 0L) {
                            ++n6;
                            l ^= 0xFFFFFFFFFFFFFFFFL;
                        }
                        n8 = (int)l;
                    }
                    n8 = CollationFastLatin.getCases(n4, n3 != 0, n8);
                }
                if (n7 == n8) {
                    if (n7 == 2) break;
                    n8 = 0;
                    n7 = 0;
                    continue;
                }
                n11 = n7 & 0xFFFF;
                int n14 = n8 & 0xFFFF;
                if (n11 != n14) {
                    if ((n & 0x100) == 0) {
                        return n11 < n14 ? -1 : 1;
                    }
                    return n11 < n14 ? 1 : -1;
                }
                if (n7 == 2) break;
                n7 >>>= 16;
                n8 >>>= 16;
            }
        }
        if (CollationSettings.getStrength(n) <= 1) {
            return 1;
        }
        n3 = CollationSettings.isTertiaryWithCaseBits(n) ? 1 : 0;
        n5 = n6 = n2;
        n8 = 0;
        n7 = 0;
        while (true) {
            int n15;
            if (n7 == 0) {
                if (n5 == charSequence.length()) {
                    n7 = 2;
                } else {
                    int n16 = n7 = (n15 = charSequence.charAt(n5++)) <= 383 ? cArray[n15] : CollationFastLatin.lookup(cArray, n15);
                    if (n7 < 3072) {
                        long l = CollationFastLatin.nextPair(cArray, n15, n7, charSequence, n5);
                        if (l < 0L) {
                            ++n5;
                            l ^= 0xFFFFFFFFFFFFFFFFL;
                        }
                        n7 = (int)l;
                    }
                    n7 = CollationFastLatin.getTertiaries(n4, n3 != 0, n7);
                    continue;
                }
            }
            while (n8 == 0) {
                if (n6 == charSequence2.length()) {
                    n8 = 2;
                    break;
                }
                int n17 = n8 = (n15 = (int)charSequence2.charAt(n6++)) <= 383 ? cArray[n15] : CollationFastLatin.lookup(cArray, n15);
                if (n8 < 3072) {
                    long l = CollationFastLatin.nextPair(cArray, n15, n8, charSequence2, n6);
                    if (l < 0L) {
                        ++n6;
                        l ^= 0xFFFFFFFFFFFFFFFFL;
                    }
                    n8 = (int)l;
                }
                n8 = CollationFastLatin.getTertiaries(n4, n3 != 0, n8);
            }
            if (n7 == n8) {
                if (n7 == 2) break;
                n8 = 0;
                n7 = 0;
                continue;
            }
            n15 = n7 & 0xFFFF;
            int n18 = n8 & 0xFFFF;
            if (n15 != n18) {
                if (CollationSettings.sortsTertiaryUpperCaseFirst(n)) {
                    if (n15 > 3) {
                        n15 ^= 0x18;
                    }
                    if (n18 > 3) {
                        n18 ^= 0x18;
                    }
                }
                return n15 < n18 ? -1 : 1;
            }
            if (n7 == 2) break;
            n7 >>>= 16;
            n8 >>>= 16;
        }
        if (CollationSettings.getStrength(n) <= 2) {
            return 1;
        }
        n5 = n6 = n2;
        n8 = 0;
        n7 = 0;
        while (true) {
            int n19;
            if (n7 == 0) {
                if (n5 == charSequence.length()) {
                    n7 = 2;
                } else {
                    int n20 = n7 = (n19 = charSequence.charAt(n5++)) <= 383 ? cArray[n19] : CollationFastLatin.lookup(cArray, n19);
                    if (n7 < 3072) {
                        long l = CollationFastLatin.nextPair(cArray, n19, n7, charSequence, n5);
                        if (l < 0L) {
                            ++n5;
                            l ^= 0xFFFFFFFFFFFFFFFFL;
                        }
                        n7 = (int)l;
                    }
                    n7 = CollationFastLatin.getQuaternaries(n4, n7);
                    continue;
                }
            }
            while (n8 == 0) {
                if (n6 == charSequence2.length()) {
                    n8 = 2;
                    break;
                }
                int n21 = n8 = (n19 = (int)charSequence2.charAt(n6++)) <= 383 ? cArray[n19] : CollationFastLatin.lookup(cArray, n19);
                if (n8 < 3072) {
                    long l = CollationFastLatin.nextPair(cArray, n19, n8, charSequence2, n6);
                    if (l < 0L) {
                        ++n6;
                        l ^= 0xFFFFFFFFFFFFFFFFL;
                    }
                    n8 = (int)l;
                }
                n8 = CollationFastLatin.getQuaternaries(n4, n8);
            }
            if (n7 == n8) {
                if (n7 == 2) break;
                n8 = 0;
                n7 = 0;
                continue;
            }
            n19 = n7 & 0xFFFF;
            int n22 = n8 & 0xFFFF;
            if (n19 != n22) {
                return n19 < n22 ? -1 : 1;
            }
            if (n7 == 2) break;
            n7 >>>= 16;
            n8 >>>= 16;
        }
        return 1;
    }

    private static int lookup(char[] cArray, int n) {
        if (!$assertionsDisabled && n <= 383) {
            throw new AssertionError();
        }
        if (8192 <= n && n < 8256) {
            return cArray[n - 8192 + 384];
        }
        if (n == 65534) {
            return 0;
        }
        if (n == 65535) {
            return 1;
        }
        return 0;
    }

    private static long nextPair(char[] cArray, int n, int n2, CharSequence charSequence, int n3) {
        int n4;
        if (n2 >= 3072 || n2 < 1024) {
            return n2;
        }
        if (n2 >= 2048) {
            int n5 = 448 + (n2 & 0x3FF);
            return (long)cArray[n5 + 1] << 16 | (long)cArray[n5];
        }
        int n6 = 448 + (n2 & 0x3FF);
        boolean bl = false;
        if (n3 != charSequence.length()) {
            int n7;
            int n8 = n3;
            if ((n4 = (int)charSequence.charAt(n8++)) > 383) {
                if (8192 <= n4 && n4 < 8256) {
                    n4 = n4 - 8192 + 384;
                } else if (n4 == 65534 || n4 == 65535) {
                    n4 = -1;
                } else {
                    return 1L;
                }
            }
            int n9 = n6;
            char c = cArray[n9];
            while ((n7 = (c = cArray[n9 += c >> 9]) & 0x1FF) < n4) {
            }
            if (n7 == n4) {
                n6 = n9;
                bl = true;
            }
        }
        if ((n4 = cArray[n6] >> 9) == 1) {
            return 1L;
        }
        n2 = cArray[n6 + 1];
        long l = n4 == 2 ? (long)n2 : (long)cArray[n6 + 2] << 16 | (long)n2;
        return bl ? l ^ 0xFFFFFFFFFFFFFFFFL : l;
    }

    private static int getPrimaries(int n, int n2) {
        int n3 = n2 & 0xFFFF;
        if (n3 >= 4096) {
            return n2 & 0xFC00FC00;
        }
        if (n3 > n) {
            return n2 & 0xFFF8FFF8;
        }
        if (n3 >= 3072) {
            return 1;
        }
        return n2;
    }

    private static int getSecondariesFromOneShortCE(int n) {
        if ((n &= 0x3E0) < 384) {
            return n + 32;
        }
        return n + 32 << 16 | 0xC0;
    }

    private static int getSecondaries(int n, int n2) {
        if (n2 <= 65535) {
            if (n2 >= 4096) {
                n2 = CollationFastLatin.getSecondariesFromOneShortCE(n2);
            } else if (n2 > n) {
                n2 = 192;
            } else if (n2 >= 3072) {
                n2 = 0;
            }
        } else {
            int n3 = n2 & 0xFFFF;
            if (n3 >= 4096) {
                n2 = (n2 & 0x3E003E0) + 0x200020;
            } else if (n3 > n) {
                n2 = 0xC000C0;
            } else {
                if (!$assertionsDisabled && n3 < 3072) {
                    throw new AssertionError();
                }
                n2 = 0;
            }
        }
        return n2;
    }

    private static int getCases(int n, boolean bl, int n2) {
        if (n2 <= 65535) {
            if (n2 >= 4096) {
                int n3 = n2;
                n2 &= 0x18;
                if (!bl && (n3 & 0x3E0) >= 384) {
                    n2 |= 0x80000;
                }
            } else if (n2 > n) {
                n2 = 8;
            } else if (n2 >= 3072) {
                n2 = 0;
            }
        } else {
            int n4 = n2 & 0xFFFF;
            if (n4 >= 4096) {
                n2 = bl && (n2 & 0xFC000000) == 0 ? (n2 &= 0x18) : (n2 &= 0x180018);
            } else if (n4 > n) {
                n2 = 524296;
            } else {
                if (!$assertionsDisabled && n4 < 3072) {
                    throw new AssertionError();
                }
                n2 = 0;
            }
        }
        return n2;
    }

    private static int getTertiaries(int n, boolean bl, int n2) {
        if (n2 <= 65535) {
            if (n2 >= 4096) {
                int n3 = n2;
                if (bl) {
                    n2 = (n2 & 0x1F) + 32;
                    if ((n3 & 0x3E0) >= 384) {
                        n2 |= 0x280000;
                    }
                } else {
                    n2 = (n2 & 7) + 32;
                    if ((n3 & 0x3E0) >= 384) {
                        n2 |= 0x200000;
                    }
                }
            } else if (n2 > n) {
                n2 = (n2 & 7) + 32;
                if (bl) {
                    n2 |= 8;
                }
            } else if (n2 >= 3072) {
                n2 = 0;
            }
        } else {
            int n4 = n2 & 0xFFFF;
            if (n4 >= 4096) {
                n2 = bl ? (n2 &= 0x1F001F) : (n2 &= 0x70007);
                n2 += 0x200020;
            } else if (n4 > n) {
                n2 = (n2 & 0x70007) + 0x200020;
                if (bl) {
                    n2 |= 0x80008;
                }
            } else {
                if (!$assertionsDisabled && n4 < 3072) {
                    throw new AssertionError();
                }
                n2 = 0;
            }
        }
        return n2;
    }

    private static int getQuaternaries(int n, int n2) {
        if (n2 <= 65535) {
            if (n2 >= 4096) {
                n2 = (n2 & 0x3E0) >= 384 ? -67044352 : 64512;
            } else if (n2 > n) {
                n2 = 64512;
            } else if (n2 >= 3072) {
                n2 &= 0xFFF8;
            }
        } else {
            int n3 = n2 & 0xFFFF;
            if (n3 > n) {
                n2 = -67044352;
            } else {
                if (!$assertionsDisabled && n3 < 3072) {
                    throw new AssertionError();
                }
                n2 &= 0xFFF8FFF8;
            }
        }
        return n2;
    }

    private CollationFastLatin() {
    }
}

