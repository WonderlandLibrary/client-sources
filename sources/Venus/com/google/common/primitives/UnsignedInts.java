/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.ParseRequest;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Comparator;

@Beta
@GwtCompatible
public final class UnsignedInts {
    static final long INT_MASK = 0xFFFFFFFFL;

    private UnsignedInts() {
    }

    static int flip(int n) {
        return n ^ Integer.MIN_VALUE;
    }

    public static int compare(int n, int n2) {
        return Ints.compare(UnsignedInts.flip(n), UnsignedInts.flip(n2));
    }

    public static long toLong(int n) {
        return (long)n & 0xFFFFFFFFL;
    }

    public static int checkedCast(long l) {
        Preconditions.checkArgument(l >> 32 == 0L, "out of range: %s", l);
        return (int)l;
    }

    public static int saturatedCast(long l) {
        if (l <= 0L) {
            return 1;
        }
        if (l >= 0x100000000L) {
            return 1;
        }
        return (int)l;
    }

    public static int min(int ... nArray) {
        Preconditions.checkArgument(nArray.length > 0);
        int n = UnsignedInts.flip(nArray[0]);
        for (int i = 1; i < nArray.length; ++i) {
            int n2 = UnsignedInts.flip(nArray[i]);
            if (n2 >= n) continue;
            n = n2;
        }
        return UnsignedInts.flip(n);
    }

    public static int max(int ... nArray) {
        Preconditions.checkArgument(nArray.length > 0);
        int n = UnsignedInts.flip(nArray[0]);
        for (int i = 1; i < nArray.length; ++i) {
            int n2 = UnsignedInts.flip(nArray[i]);
            if (n2 <= n) continue;
            n = n2;
        }
        return UnsignedInts.flip(n);
    }

    public static String join(String string, int ... nArray) {
        Preconditions.checkNotNull(string);
        if (nArray.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(nArray.length * 5);
        stringBuilder.append(UnsignedInts.toString(nArray[0]));
        for (int i = 1; i < nArray.length; ++i) {
            stringBuilder.append(string).append(UnsignedInts.toString(nArray[i]));
        }
        return stringBuilder.toString();
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static int divide(int n, int n2) {
        return (int)(UnsignedInts.toLong(n) / UnsignedInts.toLong(n2));
    }

    public static int remainder(int n, int n2) {
        return (int)(UnsignedInts.toLong(n) % UnsignedInts.toLong(n2));
    }

    @CanIgnoreReturnValue
    public static int decode(String string) {
        ParseRequest parseRequest = ParseRequest.fromString(string);
        try {
            return UnsignedInts.parseUnsignedInt(parseRequest.rawValue, parseRequest.radix);
        } catch (NumberFormatException numberFormatException) {
            NumberFormatException numberFormatException2 = new NumberFormatException("Error parsing value: " + string);
            numberFormatException2.initCause(numberFormatException);
            throw numberFormatException2;
        }
    }

    @CanIgnoreReturnValue
    public static int parseUnsignedInt(String string) {
        return UnsignedInts.parseUnsignedInt(string, 10);
    }

    @CanIgnoreReturnValue
    public static int parseUnsignedInt(String string, int n) {
        Preconditions.checkNotNull(string);
        long l = Long.parseLong(string, n);
        if ((l & 0xFFFFFFFFL) != l) {
            throw new NumberFormatException("Input " + string + " in base " + n + " is not in the range of an unsigned integer");
        }
        return (int)l;
    }

    public static String toString(int n) {
        return UnsignedInts.toString(n, 10);
    }

    public static String toString(int n, int n2) {
        long l = (long)n & 0xFFFFFFFFL;
        return Long.toString(l, n2);
    }

    static enum LexicographicalComparator implements Comparator<int[]>
    {
        INSTANCE;


        @Override
        public int compare(int[] nArray, int[] nArray2) {
            int n = Math.min(nArray.length, nArray2.length);
            for (int i = 0; i < n; ++i) {
                if (nArray[i] == nArray2[i]) continue;
                return UnsignedInts.compare(nArray[i], nArray2[i]);
            }
            return nArray.length - nArray2.length;
        }

        public String toString() {
            return "UnsignedInts.lexicographicalComparator()";
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((int[])object, (int[])object2);
        }
    }
}

