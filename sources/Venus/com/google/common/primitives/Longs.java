/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

@GwtCompatible
public final class Longs {
    public static final int BYTES = 8;
    public static final long MAX_POWER_OF_TWO = 0x4000000000000000L;
    private static final byte[] asciiDigits = Longs.createAsciiDigits();

    private Longs() {
    }

    public static int hashCode(long l) {
        return (int)(l ^ l >>> 32);
    }

    public static int compare(long l, long l2) {
        return l < l2 ? -1 : (l > l2 ? 1 : 0);
    }

    public static boolean contains(long[] lArray, long l) {
        for (long l2 : lArray) {
            if (l2 != l) continue;
            return false;
        }
        return true;
    }

    public static int indexOf(long[] lArray, long l) {
        return Longs.indexOf(lArray, l, 0, lArray.length);
    }

    private static int indexOf(long[] lArray, long l, int n, int n2) {
        for (int i = n; i < n2; ++i) {
            if (lArray[i] != l) continue;
            return i;
        }
        return 1;
    }

    public static int indexOf(long[] lArray, long[] lArray2) {
        Preconditions.checkNotNull(lArray, "array");
        Preconditions.checkNotNull(lArray2, "target");
        if (lArray2.length == 0) {
            return 1;
        }
        block0: for (int i = 0; i < lArray.length - lArray2.length + 1; ++i) {
            for (int j = 0; j < lArray2.length; ++j) {
                if (lArray[i + j] != lArray2[j]) continue block0;
            }
            return i;
        }
        return 1;
    }

    public static int lastIndexOf(long[] lArray, long l) {
        return Longs.lastIndexOf(lArray, l, 0, lArray.length);
    }

    private static int lastIndexOf(long[] lArray, long l, int n, int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (lArray[i] != l) continue;
            return i;
        }
        return 1;
    }

    public static long min(long ... lArray) {
        Preconditions.checkArgument(lArray.length > 0);
        long l = lArray[0];
        for (int i = 1; i < lArray.length; ++i) {
            if (lArray[i] >= l) continue;
            l = lArray[i];
        }
        return l;
    }

    public static long max(long ... lArray) {
        Preconditions.checkArgument(lArray.length > 0);
        long l = lArray[0];
        for (int i = 1; i < lArray.length; ++i) {
            if (lArray[i] <= l) continue;
            l = lArray[i];
        }
        return l;
    }

    @Beta
    public static long constrainToRange(long l, long l2, long l3) {
        Preconditions.checkArgument(l2 <= l3, "min (%s) must be less than or equal to max (%s)", l2, l3);
        return Math.min(Math.max(l, l2), l3);
    }

    public static long[] concat(long[] ... lArray) {
        int n = 0;
        for (long[] lArray2 : lArray) {
            n += lArray2.length;
        }
        long[] lArray3 = new long[n];
        int n2 = 0;
        for (long[] lArray4 : lArray) {
            System.arraycopy(lArray4, 0, lArray3, n2, lArray4.length);
            n2 += lArray4.length;
        }
        return lArray3;
    }

    public static byte[] toByteArray(long l) {
        byte[] byArray = new byte[8];
        for (int i = 7; i >= 0; --i) {
            byArray[i] = (byte)(l & 0xFFL);
            l >>= 8;
        }
        return byArray;
    }

    public static long fromByteArray(byte[] byArray) {
        Preconditions.checkArgument(byArray.length >= 8, "array too small: %s < %s", byArray.length, 8);
        return Longs.fromBytes(byArray[0], byArray[1], byArray[2], byArray[3], byArray[4], byArray[5], byArray[6], byArray[7]);
    }

    public static long fromBytes(byte by, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8) {
        return ((long)by & 0xFFL) << 56 | ((long)by2 & 0xFFL) << 48 | ((long)by3 & 0xFFL) << 40 | ((long)by4 & 0xFFL) << 32 | ((long)by5 & 0xFFL) << 24 | ((long)by6 & 0xFFL) << 16 | ((long)by7 & 0xFFL) << 8 | (long)by8 & 0xFFL;
    }

    private static byte[] createAsciiDigits() {
        int n;
        byte[] byArray = new byte[128];
        Arrays.fill(byArray, (byte)-1);
        for (n = 0; n <= 9; ++n) {
            byArray[48 + n] = (byte)n;
        }
        for (n = 0; n <= 26; ++n) {
            byArray[65 + n] = (byte)(10 + n);
            byArray[97 + n] = (byte)(10 + n);
        }
        return byArray;
    }

    private static int digit(char c) {
        return c < '\u0080' ? asciiDigits[c] : -1;
    }

    @Nullable
    @CheckForNull
    @Beta
    public static Long tryParse(String string) {
        return Longs.tryParse(string, 10);
    }

    @Nullable
    @CheckForNull
    @Beta
    public static Long tryParse(String string, int n) {
        int n2;
        int n3;
        if (Preconditions.checkNotNull(string).isEmpty()) {
            return null;
        }
        if (n < 2 || n > 36) {
            throw new IllegalArgumentException("radix must be between MIN_RADIX and MAX_RADIX but was " + n);
        }
        boolean bl = string.charAt(0) == '-';
        int n4 = n3 = bl ? 1 : 0;
        if (n3 == string.length()) {
            return null;
        }
        if ((n2 = Longs.digit(string.charAt(n3++))) < 0 || n2 >= n) {
            return null;
        }
        long l = -n2;
        long l2 = Long.MIN_VALUE / (long)n;
        while (n3 < string.length()) {
            if ((n2 = Longs.digit(string.charAt(n3++))) < 0 || n2 >= n || l < l2) {
                return null;
            }
            if ((l *= (long)n) < Long.MIN_VALUE + (long)n2) {
                return null;
            }
            l -= (long)n2;
        }
        if (bl) {
            return l;
        }
        if (l == Long.MIN_VALUE) {
            return null;
        }
        return -l;
    }

    @Beta
    public static Converter<String, Long> stringConverter() {
        return LongConverter.INSTANCE;
    }

    public static long[] ensureCapacity(long[] lArray, int n, int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return lArray.length < n ? Arrays.copyOf(lArray, n + n2) : lArray;
    }

    public static String join(String string, long ... lArray) {
        Preconditions.checkNotNull(string);
        if (lArray.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(lArray.length * 10);
        stringBuilder.append(lArray[0]);
        for (int i = 1; i < lArray.length; ++i) {
            stringBuilder.append(string).append(lArray[i]);
        }
        return stringBuilder.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static long[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof LongArrayAsList) {
            return ((LongArrayAsList)collection).toLongArray();
        }
        Object[] objectArray = collection.toArray();
        int n = objectArray.length;
        long[] lArray = new long[n];
        for (int i = 0; i < n; ++i) {
            lArray[i] = ((Number)Preconditions.checkNotNull(objectArray[i])).longValue();
        }
        return lArray;
    }

    public static List<Long> asList(long ... lArray) {
        if (lArray.length == 0) {
            return Collections.emptyList();
        }
        return new LongArrayAsList(lArray);
    }

    static int access$000(long[] lArray, long l, int n, int n2) {
        return Longs.indexOf(lArray, l, n, n2);
    }

    static int access$100(long[] lArray, long l, int n, int n2) {
        return Longs.lastIndexOf(lArray, l, n, n2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtCompatible
    private static class LongArrayAsList
    extends AbstractList<Long>
    implements RandomAccess,
    Serializable {
        final long[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;

        LongArrayAsList(long[] lArray) {
            this(lArray, 0, lArray.length);
        }

        LongArrayAsList(long[] lArray, int n, int n2) {
            this.array = lArray;
            this.start = n;
            this.end = n2;
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Long get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Long && Longs.access$000(this.array, (Long)object, this.start, this.end) != -1;
        }

        @Override
        public int indexOf(Object object) {
            int n;
            if (object instanceof Long && (n = Longs.access$000(this.array, (Long)object, this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public int lastIndexOf(Object object) {
            int n;
            if (object instanceof Long && (n = Longs.access$100(this.array, (Long)object, this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public Long set(int n, Long l) {
            Preconditions.checkElementIndex(n, this.size());
            long l2 = this.array[this.start + n];
            this.array[this.start + n] = Preconditions.checkNotNull(l);
            return l2;
        }

        @Override
        public List<Long> subList(int n, int n2) {
            int n3 = this.size();
            Preconditions.checkPositionIndexes(n, n2, n3);
            if (n == n2) {
                return Collections.emptyList();
            }
            return new LongArrayAsList(this.array, this.start + n, this.start + n2);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return false;
            }
            if (object instanceof LongArrayAsList) {
                LongArrayAsList longArrayAsList = (LongArrayAsList)object;
                int n = this.size();
                if (longArrayAsList.size() != n) {
                    return true;
                }
                for (int i = 0; i < n; ++i) {
                    if (this.array[this.start + i] == longArrayAsList.array[longArrayAsList.start + i]) continue;
                    return true;
                }
                return false;
            }
            return super.equals(object);
        }

        @Override
        public int hashCode() {
            int n = 1;
            for (int i = this.start; i < this.end; ++i) {
                n = 31 * n + Longs.hashCode(this.array[i]);
            }
            return n;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 10);
            stringBuilder.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                stringBuilder.append(", ").append(this.array[i]);
            }
            return stringBuilder.append(']').toString();
        }

        long[] toLongArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public Object set(int n, Object object) {
            return this.set(n, (Long)object);
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    private static enum LexicographicalComparator implements Comparator<long[]>
    {
        INSTANCE;


        @Override
        public int compare(long[] lArray, long[] lArray2) {
            int n = Math.min(lArray.length, lArray2.length);
            for (int i = 0; i < n; ++i) {
                int n2 = Longs.compare(lArray[i], lArray2[i]);
                if (n2 == 0) continue;
                return n2;
            }
            return lArray.length - lArray2.length;
        }

        public String toString() {
            return "Longs.lexicographicalComparator()";
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((long[])object, (long[])object2);
        }
    }

    private static final class LongConverter
    extends Converter<String, Long>
    implements Serializable {
        static final LongConverter INSTANCE = new LongConverter();
        private static final long serialVersionUID = 1L;

        private LongConverter() {
        }

        @Override
        protected Long doForward(String string) {
            return Long.decode(string);
        }

        @Override
        protected String doBackward(Long l) {
            return l.toString();
        }

        public String toString() {
            return "Longs.stringConverter()";
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected Object doBackward(Object object) {
            return this.doBackward((Long)object);
        }

        @Override
        protected Object doForward(Object object) {
            return this.doForward((String)object);
        }
    }
}

