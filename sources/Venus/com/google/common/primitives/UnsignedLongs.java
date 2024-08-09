/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import com.google.common.primitives.ParseRequest;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.util.Comparator;

@Beta
@GwtCompatible
public final class UnsignedLongs {
    public static final long MAX_VALUE = -1L;
    private static final long[] maxValueDivs = new long[37];
    private static final int[] maxValueMods = new int[37];
    private static final int[] maxSafeDigits = new int[37];

    private UnsignedLongs() {
    }

    private static long flip(long l) {
        return l ^ Long.MIN_VALUE;
    }

    public static int compare(long l, long l2) {
        return Longs.compare(UnsignedLongs.flip(l), UnsignedLongs.flip(l2));
    }

    public static long min(long ... lArray) {
        Preconditions.checkArgument(lArray.length > 0);
        long l = UnsignedLongs.flip(lArray[0]);
        for (int i = 1; i < lArray.length; ++i) {
            long l2 = UnsignedLongs.flip(lArray[i]);
            if (l2 >= l) continue;
            l = l2;
        }
        return UnsignedLongs.flip(l);
    }

    public static long max(long ... lArray) {
        Preconditions.checkArgument(lArray.length > 0);
        long l = UnsignedLongs.flip(lArray[0]);
        for (int i = 1; i < lArray.length; ++i) {
            long l2 = UnsignedLongs.flip(lArray[i]);
            if (l2 <= l) continue;
            l = l2;
        }
        return UnsignedLongs.flip(l);
    }

    public static String join(String string, long ... lArray) {
        Preconditions.checkNotNull(string);
        if (lArray.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(lArray.length * 5);
        stringBuilder.append(UnsignedLongs.toString(lArray[0]));
        for (int i = 1; i < lArray.length; ++i) {
            stringBuilder.append(string).append(UnsignedLongs.toString(lArray[i]));
        }
        return stringBuilder.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static long divide(long l, long l2) {
        long l3;
        if (l2 < 0L) {
            if (UnsignedLongs.compare(l, l2) < 0) {
                return 0L;
            }
            return 1L;
        }
        if (l >= 0L) {
            return l / l2;
        }
        long l4 = l - (l3 = (l >>> 1) / l2 << 1) * l2;
        return l3 + (long)(UnsignedLongs.compare(l4, l2) >= 0 ? 1 : 0);
    }

    public static long remainder(long l, long l2) {
        long l3;
        if (l2 < 0L) {
            if (UnsignedLongs.compare(l, l2) < 0) {
                return l;
            }
            return l - l2;
        }
        if (l >= 0L) {
            return l % l2;
        }
        long l4 = (l >>> 1) / l2 << 1;
        return l3 - (UnsignedLongs.compare(l3 = l - l4 * l2, l2) >= 0 ? l2 : 0L);
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String string) {
        return UnsignedLongs.parseUnsignedLong(string, 10);
    }

    @CanIgnoreReturnValue
    public static long decode(String string) {
        ParseRequest parseRequest = ParseRequest.fromString(string);
        try {
            return UnsignedLongs.parseUnsignedLong(parseRequest.rawValue, parseRequest.radix);
        } catch (NumberFormatException numberFormatException) {
            NumberFormatException numberFormatException2 = new NumberFormatException("Error parsing value: " + string);
            numberFormatException2.initCause(numberFormatException);
            throw numberFormatException2;
        }
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String string, int n) {
        Preconditions.checkNotNull(string);
        if (string.length() == 0) {
            throw new NumberFormatException("empty string");
        }
        if (n < 2 || n > 36) {
            throw new NumberFormatException("illegal radix: " + n);
        }
        int n2 = maxSafeDigits[n] - 1;
        long l = 0L;
        for (int i = 0; i < string.length(); ++i) {
            int n3 = Character.digit(string.charAt(i), n);
            if (n3 == -1) {
                throw new NumberFormatException(string);
            }
            if (i > n2 && UnsignedLongs.overflowInParse(l, n3, n)) {
                throw new NumberFormatException("Too large for unsigned long: " + string);
            }
            l = l * (long)n + (long)n3;
        }
        return l;
    }

    private static boolean overflowInParse(long l, int n, int n2) {
        if (l >= 0L) {
            if (l < maxValueDivs[n2]) {
                return true;
            }
            if (l > maxValueDivs[n2]) {
                return false;
            }
            return n > maxValueMods[n2];
        }
        return false;
    }

    public static String toString(long l) {
        return UnsignedLongs.toString(l, 10);
    }

    public static String toString(long l, int n) {
        Preconditions.checkArgument(n >= 2 && n <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", n);
        if (l == 0L) {
            return "0";
        }
        if (l > 0L) {
            return Long.toString(l, n);
        }
        char[] cArray = new char[64];
        int n2 = cArray.length;
        if ((n & n - 1) == 0) {
            int n3 = Integer.numberOfTrailingZeros(n);
            int n4 = n - 1;
            do {
                cArray[--n2] = Character.forDigit((int)l & n4, n);
            } while ((l >>>= n3) != 0L);
        } else {
            long l2 = (n & 1) == 0 ? (l >>> 1) / (long)(n >>> 1) : UnsignedLongs.divide(l, n);
            long l3 = l - l2 * (long)n;
            cArray[--n2] = Character.forDigit((int)l3, n);
            for (l = l2; l > 0L; l /= (long)n) {
                cArray[--n2] = Character.forDigit((int)(l % (long)n), n);
            }
        }
        return new String(cArray, n2, cArray.length - n2);
    }

    static {
        BigInteger bigInteger = new BigInteger("10000000000000000", 16);
        for (int i = 2; i <= 36; ++i) {
            UnsignedLongs.maxValueDivs[i] = UnsignedLongs.divide(-1L, i);
            UnsignedLongs.maxValueMods[i] = (int)UnsignedLongs.remainder(-1L, i);
            UnsignedLongs.maxSafeDigits[i] = bigInteger.toString(i).length() - 1;
        }
    }

    static enum LexicographicalComparator implements Comparator<long[]>
    {
        INSTANCE;


        @Override
        public int compare(long[] lArray, long[] lArray2) {
            int n = Math.min(lArray.length, lArray2.length);
            for (int i = 0; i < n; ++i) {
                if (lArray[i] == lArray2[i]) continue;
                return UnsignedLongs.compare(lArray[i], lArray2[i]);
            }
            return lArray.length - lArray2.length;
        }

        public String toString() {
            return "UnsignedLongs.lexicographicalComparator()";
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((long[])object, (long[])object2);
        }
    }
}

