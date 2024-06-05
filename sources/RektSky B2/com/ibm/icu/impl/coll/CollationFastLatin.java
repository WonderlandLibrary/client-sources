package com.ibm.icu.impl.coll;

public final class CollationFastLatin
{
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
    static final int TWO_SECONDARIES_MASK = 65012704;
    static final int TWO_CASES_MASK = 1572888;
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
    static final int TWO_SEC_OFFSETS = 2097184;
    static final int TWO_COMMON_SEC_PLUS_OFFSET = 12583104;
    static final int LOWER_CASE = 8;
    static final int TWO_LOWER_CASES = 524296;
    static final int COMMON_TER = 0;
    static final int MAX_TER_AFTER = 7;
    static final int TER_OFFSET = 32;
    static final int COMMON_TER_PLUS_OFFSET = 32;
    static final int TWO_TER_OFFSETS = 2097184;
    static final int TWO_COMMON_TER_PLUS_OFFSET = 2097184;
    static final int MERGE_WEIGHT = 3;
    static final int EOS = 2;
    static final int BAIL_OUT = 1;
    static final int CONTR_CHAR_MASK = 511;
    static final int CONTR_LENGTH_SHIFT = 9;
    public static final int BAIL_OUT_RESULT = -2;
    
    static int getCharIndex(final char c) {
        if (c <= '\u017f') {
            return c;
        }
        if ('\u2000' <= c && c < '\u2040') {
            return c - '\u1e80';
        }
        return -1;
    }
    
    public static int getOptions(final CollationData data, final CollationSettings settings, final char[] primaries) {
        final char[] header = data.fastLatinTableHeader;
        if (header == null) {
            return -1;
        }
        assert header[0] >> 8 == 2;
        if (primaries.length == 384) {
            int miniVarTop;
            if ((settings.options & 0xC) == 0x0) {
                miniVarTop = 3071;
            }
            else {
                final int headerLength = header[0] & '\u00ff';
                final int i = 1 + settings.getMaxVariable();
                if (i >= headerLength) {
                    return -1;
                }
                miniVarTop = header[i];
            }
            boolean digitsAreReordered = false;
            if (settings.hasReordering()) {
                long prevStart = 0L;
                long beforeDigitStart = 0L;
                long digitStart = 0L;
                long afterDigitStart = 0L;
                for (int group = 4096; group < 4104; ++group) {
                    long start = data.getFirstPrimaryForGroup(group);
                    start = settings.reorder(start);
                    if (group == 4100) {
                        beforeDigitStart = prevStart;
                        digitStart = start;
                    }
                    else if (start != 0L) {
                        if (start < prevStart) {
                            return -1;
                        }
                        if (digitStart != 0L && afterDigitStart == 0L && prevStart == beforeDigitStart) {
                            afterDigitStart = start;
                        }
                        prevStart = start;
                    }
                }
                long latinStart = data.getFirstPrimaryForGroup(25);
                latinStart = settings.reorder(latinStart);
                if (latinStart < prevStart) {
                    return -1;
                }
                if (afterDigitStart == 0L) {
                    afterDigitStart = latinStart;
                }
                if (beforeDigitStart >= digitStart || digitStart >= afterDigitStart) {
                    digitsAreReordered = true;
                }
            }
            final char[] table = data.fastLatinTable;
            for (int c = 0; c < 384; ++c) {
                int p = table[c];
                if (p >= 4096) {
                    p &= 0xFC00;
                }
                else if (p > miniVarTop) {
                    p &= 0xFFF8;
                }
                else {
                    p = 0;
                }
                primaries[c] = (char)p;
            }
            if (digitsAreReordered || (settings.options & 0x2) != 0x0) {
                for (int c = 48; c <= 57; ++c) {
                    primaries[c] = '\0';
                }
            }
            return miniVarTop << 16 | settings.options;
        }
        assert false;
        return -1;
    }
    
    public static int compareUTF16(final char[] table, final char[] primaries, int options, final CharSequence left, final CharSequence right, final int startIndex) {
        final int variableTop = options >> 16;
        options &= 0xFFFF;
        int leftIndex = startIndex;
        int rightIndex = startIndex;
        int leftPair = 0;
        int rightPair = 0;
        while (true) {
            Label_0247: {
                if (leftPair == 0) {
                    if (leftIndex == left.length()) {
                        leftPair = 2;
                    }
                    else {
                        final int c = left.charAt(leftIndex++);
                        if (c <= 383) {
                            leftPair = primaries[c];
                            if (leftPair != 0) {
                                break Label_0247;
                            }
                            if (c <= 57 && c >= 48 && (options & 0x2) != 0x0) {
                                return -2;
                            }
                            leftPair = table[c];
                        }
                        else if (8192 <= c && c < 8256) {
                            leftPair = table[c - 8192 + 384];
                        }
                        else {
                            leftPair = lookup(table, c);
                        }
                        if (leftPair >= 4096) {
                            leftPair &= 0xFC00;
                        }
                        else if (leftPair > variableTop) {
                            leftPair &= 0xFFF8;
                        }
                        else {
                            long pairAndInc = nextPair(table, c, leftPair, left, leftIndex);
                            if (pairAndInc < 0L) {
                                ++leftIndex;
                                pairAndInc ^= -1L;
                            }
                            leftPair = (int)pairAndInc;
                            if (leftPair == 1) {
                                return -2;
                            }
                            leftPair = getPrimaries(variableTop, leftPair);
                            continue;
                        }
                    }
                }
            }
            while (rightPair == 0) {
                if (rightIndex == right.length()) {
                    rightPair = 2;
                    break;
                }
                final int c = right.charAt(rightIndex++);
                if (c <= 383) {
                    rightPair = primaries[c];
                    if (rightPair != 0) {
                        break;
                    }
                    if (c <= 57 && c >= 48 && (options & 0x2) != 0x0) {
                        return -2;
                    }
                    rightPair = table[c];
                }
                else if (8192 <= c && c < 8256) {
                    rightPair = table[c - 8192 + 384];
                }
                else {
                    rightPair = lookup(table, c);
                }
                if (rightPair >= 4096) {
                    rightPair &= 0xFC00;
                    break;
                }
                if (rightPair > variableTop) {
                    rightPair &= 0xFFF8;
                    break;
                }
                long pairAndInc = nextPair(table, c, rightPair, right, rightIndex);
                if (pairAndInc < 0L) {
                    ++rightIndex;
                    pairAndInc ^= -1L;
                }
                rightPair = (int)pairAndInc;
                if (rightPair == 1) {
                    return -2;
                }
                rightPair = getPrimaries(variableTop, rightPair);
            }
            if (leftPair == rightPair) {
                if (leftPair == 2) {
                    break;
                }
                rightPair = (leftPair = 0);
            }
            else {
                final int leftPrimary = leftPair & 0xFFFF;
                final int rightPrimary = rightPair & 0xFFFF;
                if (leftPrimary != rightPrimary) {
                    return (leftPrimary < rightPrimary) ? -1 : 1;
                }
                if (leftPair == 2) {
                    break;
                }
                leftPair >>>= 16;
                rightPair >>>= 16;
            }
        }
        if (CollationSettings.getStrength(options) >= 1) {
            rightIndex = startIndex;
            leftIndex = startIndex;
            rightPair = (leftPair = 0);
            while (true) {
                if (leftPair == 0) {
                    if (leftIndex == left.length()) {
                        leftPair = 2;
                    }
                    else {
                        final int c = left.charAt(leftIndex++);
                        if (c <= 383) {
                            leftPair = table[c];
                        }
                        else if (8192 <= c && c < 8256) {
                            leftPair = table[c - 8192 + 384];
                        }
                        else {
                            leftPair = lookup(table, c);
                        }
                        if (leftPair >= 4096) {
                            leftPair = getSecondariesFromOneShortCE(leftPair);
                        }
                        else {
                            if (leftPair <= variableTop) {
                                long pairAndInc = nextPair(table, c, leftPair, left, leftIndex);
                                if (pairAndInc < 0L) {
                                    ++leftIndex;
                                    pairAndInc ^= -1L;
                                }
                                leftPair = getSecondaries(variableTop, (int)pairAndInc);
                                continue;
                            }
                            leftPair = 192;
                        }
                    }
                }
                while (rightPair == 0) {
                    if (rightIndex == right.length()) {
                        rightPair = 2;
                        break;
                    }
                    final int c = right.charAt(rightIndex++);
                    if (c <= 383) {
                        rightPair = table[c];
                    }
                    else if (8192 <= c && c < 8256) {
                        rightPair = table[c - 8192 + 384];
                    }
                    else {
                        rightPair = lookup(table, c);
                    }
                    if (rightPair >= 4096) {
                        rightPair = getSecondariesFromOneShortCE(rightPair);
                        break;
                    }
                    if (rightPair > variableTop) {
                        rightPair = 192;
                        break;
                    }
                    long pairAndInc = nextPair(table, c, rightPair, right, rightIndex);
                    if (pairAndInc < 0L) {
                        ++rightIndex;
                        pairAndInc ^= -1L;
                    }
                    rightPair = getSecondaries(variableTop, (int)pairAndInc);
                }
                if (leftPair == rightPair) {
                    if (leftPair == 2) {
                        break;
                    }
                    rightPair = (leftPair = 0);
                }
                else {
                    final int leftSecondary = leftPair & 0xFFFF;
                    final int rightSecondary = rightPair & 0xFFFF;
                    if (leftSecondary != rightSecondary) {
                        if ((options & 0x800) != 0x0) {
                            return -2;
                        }
                        return (leftSecondary < rightSecondary) ? -1 : 1;
                    }
                    else {
                        if (leftPair == 2) {
                            break;
                        }
                        leftPair >>>= 16;
                        rightPair >>>= 16;
                    }
                }
            }
        }
        if ((options & 0x400) != 0x0) {
            final boolean strengthIsPrimary = CollationSettings.getStrength(options) == 0;
            rightIndex = startIndex;
            leftIndex = startIndex;
            rightPair = (leftPair = 0);
            while (true) {
                if (leftPair == 0) {
                    if (leftIndex != left.length()) {
                        final int c2 = left.charAt(leftIndex++);
                        leftPair = ((c2 <= 383) ? table[c2] : lookup(table, c2));
                        if (leftPair < 3072) {
                            long pairAndInc2 = nextPair(table, c2, leftPair, left, leftIndex);
                            if (pairAndInc2 < 0L) {
                                ++leftIndex;
                                pairAndInc2 ^= -1L;
                            }
                            leftPair = (int)pairAndInc2;
                        }
                        leftPair = getCases(variableTop, strengthIsPrimary, leftPair);
                        continue;
                    }
                    leftPair = 2;
                }
                while (rightPair == 0) {
                    if (rightIndex == right.length()) {
                        rightPair = 2;
                        break;
                    }
                    final int c2 = right.charAt(rightIndex++);
                    rightPair = ((c2 <= 383) ? table[c2] : lookup(table, c2));
                    if (rightPair < 3072) {
                        long pairAndInc2 = nextPair(table, c2, rightPair, right, rightIndex);
                        if (pairAndInc2 < 0L) {
                            ++rightIndex;
                            pairAndInc2 ^= -1L;
                        }
                        rightPair = (int)pairAndInc2;
                    }
                    rightPair = getCases(variableTop, strengthIsPrimary, rightPair);
                }
                if (leftPair == rightPair) {
                    if (leftPair == 2) {
                        break;
                    }
                    rightPair = (leftPair = 0);
                }
                else {
                    final int leftCase = leftPair & 0xFFFF;
                    final int rightCase = rightPair & 0xFFFF;
                    if (leftCase != rightCase) {
                        if ((options & 0x100) == 0x0) {
                            return (leftCase < rightCase) ? -1 : 1;
                        }
                        return (leftCase < rightCase) ? 1 : -1;
                    }
                    else {
                        if (leftPair == 2) {
                            break;
                        }
                        leftPair >>>= 16;
                        rightPair >>>= 16;
                    }
                }
            }
        }
        if (CollationSettings.getStrength(options) <= 1) {
            return 0;
        }
        final boolean withCaseBits = CollationSettings.isTertiaryWithCaseBits(options);
        rightIndex = startIndex;
        leftIndex = startIndex;
        rightPair = (leftPair = 0);
        while (true) {
            if (leftPair == 0) {
                if (leftIndex != left.length()) {
                    final int c2 = left.charAt(leftIndex++);
                    leftPair = ((c2 <= 383) ? table[c2] : lookup(table, c2));
                    if (leftPair < 3072) {
                        long pairAndInc2 = nextPair(table, c2, leftPair, left, leftIndex);
                        if (pairAndInc2 < 0L) {
                            ++leftIndex;
                            pairAndInc2 ^= -1L;
                        }
                        leftPair = (int)pairAndInc2;
                    }
                    leftPair = getTertiaries(variableTop, withCaseBits, leftPair);
                    continue;
                }
                leftPair = 2;
            }
            while (rightPair == 0) {
                if (rightIndex == right.length()) {
                    rightPair = 2;
                    break;
                }
                final int c2 = right.charAt(rightIndex++);
                rightPair = ((c2 <= 383) ? table[c2] : lookup(table, c2));
                if (rightPair < 3072) {
                    long pairAndInc2 = nextPair(table, c2, rightPair, right, rightIndex);
                    if (pairAndInc2 < 0L) {
                        ++rightIndex;
                        pairAndInc2 ^= -1L;
                    }
                    rightPair = (int)pairAndInc2;
                }
                rightPair = getTertiaries(variableTop, withCaseBits, rightPair);
            }
            if (leftPair == rightPair) {
                if (leftPair == 2) {
                    break;
                }
                rightPair = (leftPair = 0);
            }
            else {
                int leftTertiary = leftPair & 0xFFFF;
                int rightTertiary = rightPair & 0xFFFF;
                if (leftTertiary != rightTertiary) {
                    if (CollationSettings.sortsTertiaryUpperCaseFirst(options)) {
                        if (leftTertiary > 3) {
                            leftTertiary ^= 0x18;
                        }
                        if (rightTertiary > 3) {
                            rightTertiary ^= 0x18;
                        }
                    }
                    return (leftTertiary < rightTertiary) ? -1 : 1;
                }
                if (leftPair == 2) {
                    break;
                }
                leftPair >>>= 16;
                rightPair >>>= 16;
            }
        }
        if (CollationSettings.getStrength(options) <= 2) {
            return 0;
        }
        rightIndex = startIndex;
        leftIndex = startIndex;
        rightPair = (leftPair = 0);
        while (true) {
            if (leftPair == 0) {
                if (leftIndex != left.length()) {
                    final int c2 = left.charAt(leftIndex++);
                    leftPair = ((c2 <= 383) ? table[c2] : lookup(table, c2));
                    if (leftPair < 3072) {
                        long pairAndInc2 = nextPair(table, c2, leftPair, left, leftIndex);
                        if (pairAndInc2 < 0L) {
                            ++leftIndex;
                            pairAndInc2 ^= -1L;
                        }
                        leftPair = (int)pairAndInc2;
                    }
                    leftPair = getQuaternaries(variableTop, leftPair);
                    continue;
                }
                leftPair = 2;
            }
            while (rightPair == 0) {
                if (rightIndex == right.length()) {
                    rightPair = 2;
                    break;
                }
                final int c2 = right.charAt(rightIndex++);
                rightPair = ((c2 <= 383) ? table[c2] : lookup(table, c2));
                if (rightPair < 3072) {
                    long pairAndInc2 = nextPair(table, c2, rightPair, right, rightIndex);
                    if (pairAndInc2 < 0L) {
                        ++rightIndex;
                        pairAndInc2 ^= -1L;
                    }
                    rightPair = (int)pairAndInc2;
                }
                rightPair = getQuaternaries(variableTop, rightPair);
            }
            if (leftPair == rightPair) {
                if (leftPair == 2) {
                    break;
                }
                rightPair = (leftPair = 0);
            }
            else {
                final int leftQuaternary = leftPair & 0xFFFF;
                final int rightQuaternary = rightPair & 0xFFFF;
                if (leftQuaternary != rightQuaternary) {
                    return (leftQuaternary < rightQuaternary) ? -1 : 1;
                }
                if (leftPair == 2) {
                    break;
                }
                leftPair >>>= 16;
                rightPair >>>= 16;
            }
        }
        return 0;
    }
    
    private static int lookup(final char[] table, final int c) {
        assert c > 383;
        if (8192 <= c && c < 8256) {
            return table[c - 8192 + 384];
        }
        if (c == 65534) {
            return 3;
        }
        if (c == 65535) {
            return 64680;
        }
        return 1;
    }
    
    private static long nextPair(final char[] table, final int c, int ce, final CharSequence s16, final int sIndex) {
        if (ce >= 3072 || ce < 1024) {
            return ce;
        }
        if (ce >= 2048) {
            final int index = 448 + (ce & 0x3FF);
            return (long)table[index + 1] << 16 | (long)table[index];
        }
        int index = 448 + (ce & 0x3FF);
        boolean inc = false;
        if (sIndex != s16.length()) {
            int nextIndex = sIndex;
            int c2 = s16.charAt(nextIndex++);
            if (c2 > 383) {
                if (8192 <= c2 && c2 < 8256) {
                    c2 = c2 - 8192 + 384;
                }
                else {
                    if (c2 != 65534 && c2 != 65535) {
                        return 1L;
                    }
                    c2 = -1;
                }
            }
            int i = index;
            int head = table[i];
            int x;
            do {
                i += head >> 9;
                head = table[i];
                x = (head & 0x1FF);
            } while (x < c2);
            if (x == c2) {
                index = i;
                inc = true;
            }
        }
        final int length = table[index] >> 9;
        if (length == 1) {
            return 1L;
        }
        ce = table[index + 1];
        long result;
        if (length == 2) {
            result = ce;
        }
        else {
            result = ((long)table[index + 2] << 16 | (long)ce);
        }
        return inc ? (~result) : result;
    }
    
    private static int getPrimaries(final int variableTop, final int pair) {
        final int ce = pair & 0xFFFF;
        if (ce >= 4096) {
            return pair & 0xFC00FC00;
        }
        if (ce > variableTop) {
            return pair & 0xFFF8FFF8;
        }
        if (ce >= 3072) {
            return 0;
        }
        return pair;
    }
    
    private static int getSecondariesFromOneShortCE(int ce) {
        ce &= 0x3E0;
        if (ce < 384) {
            return ce + 32;
        }
        return ce + 32 << 16 | 0xC0;
    }
    
    private static int getSecondaries(final int variableTop, int pair) {
        if (pair <= 65535) {
            if (pair >= 4096) {
                pair = getSecondariesFromOneShortCE(pair);
            }
            else if (pair > variableTop) {
                pair = 192;
            }
            else if (pair >= 3072) {
                pair = 0;
            }
        }
        else {
            final int ce = pair & 0xFFFF;
            if (ce >= 4096) {
                pair = (pair & 0x3E003E0) + 2097184;
            }
            else if (ce > variableTop) {
                pair = 12583104;
            }
            else {
                assert ce >= 3072;
                pair = 0;
            }
        }
        return pair;
    }
    
    private static int getCases(final int variableTop, final boolean strengthIsPrimary, int pair) {
        if (pair <= 65535) {
            if (pair >= 4096) {
                final int ce = pair;
                pair &= 0x18;
                if (!strengthIsPrimary && (ce & 0x3E0) >= 384) {
                    pair |= 0x80000;
                }
            }
            else if (pair > variableTop) {
                pair = 8;
            }
            else if (pair >= 3072) {
                pair = 0;
            }
        }
        else {
            final int ce = pair & 0xFFFF;
            if (ce >= 4096) {
                if (strengthIsPrimary && (pair & 0xFC000000) == 0x0) {
                    pair &= 0x18;
                }
                else {
                    pair &= 0x180018;
                }
            }
            else if (ce > variableTop) {
                pair = 524296;
            }
            else {
                assert ce >= 3072;
                pair = 0;
            }
        }
        return pair;
    }
    
    private static int getTertiaries(final int variableTop, final boolean withCaseBits, int pair) {
        if (pair <= 65535) {
            if (pair >= 4096) {
                final int ce = pair;
                if (withCaseBits) {
                    pair = (pair & 0x1F) + 32;
                    if ((ce & 0x3E0) >= 384) {
                        pair |= 0x280000;
                    }
                }
                else {
                    pair = (pair & 0x7) + 32;
                    if ((ce & 0x3E0) >= 384) {
                        pair |= 0x200000;
                    }
                }
            }
            else if (pair > variableTop) {
                pair = (pair & 0x7) + 32;
                if (withCaseBits) {
                    pair |= 0x8;
                }
            }
            else if (pair >= 3072) {
                pair = 0;
            }
        }
        else {
            final int ce = pair & 0xFFFF;
            if (ce >= 4096) {
                if (withCaseBits) {
                    pair &= 0x1F001F;
                }
                else {
                    pair &= 0x70007;
                }
                pair += 2097184;
            }
            else if (ce > variableTop) {
                pair = (pair & 0x70007) + 2097184;
                if (withCaseBits) {
                    pair |= 0x80008;
                }
            }
            else {
                assert ce >= 3072;
                pair = 0;
            }
        }
        return pair;
    }
    
    private static int getQuaternaries(final int variableTop, int pair) {
        if (pair <= 65535) {
            if (pair >= 4096) {
                if ((pair & 0x3E0) >= 384) {
                    pair = -67044352;
                }
                else {
                    pair = 64512;
                }
            }
            else if (pair > variableTop) {
                pair = 64512;
            }
            else if (pair >= 3072) {
                pair &= 0xFFF8;
            }
        }
        else {
            final int ce = pair & 0xFFFF;
            if (ce > variableTop) {
                pair = -67044352;
            }
            else {
                assert ce >= 3072;
                pair &= 0xFFF8FFF8;
            }
        }
        return pair;
    }
    
    private CollationFastLatin() {
    }
}
