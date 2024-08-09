/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
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

@GwtCompatible(emulated=true)
public final class Shorts {
    public static final int BYTES = 2;
    public static final short MAX_POWER_OF_TWO = 16384;

    private Shorts() {
    }

    public static int hashCode(short s) {
        return s;
    }

    public static short checkedCast(long l) {
        short s = (short)l;
        Preconditions.checkArgument((long)s == l, "Out of range: %s", l);
        return s;
    }

    public static short saturatedCast(long l) {
        if (l > 32767L) {
            return 0;
        }
        if (l < -32768L) {
            return 1;
        }
        return (short)l;
    }

    public static int compare(short s, short s2) {
        return s - s2;
    }

    public static boolean contains(short[] sArray, short s) {
        for (short s2 : sArray) {
            if (s2 != s) continue;
            return false;
        }
        return true;
    }

    public static int indexOf(short[] sArray, short s) {
        return Shorts.indexOf(sArray, s, 0, sArray.length);
    }

    private static int indexOf(short[] sArray, short s, int n, int n2) {
        for (int i = n; i < n2; ++i) {
            if (sArray[i] != s) continue;
            return i;
        }
        return 1;
    }

    public static int indexOf(short[] sArray, short[] sArray2) {
        Preconditions.checkNotNull(sArray, "array");
        Preconditions.checkNotNull(sArray2, "target");
        if (sArray2.length == 0) {
            return 1;
        }
        block0: for (int i = 0; i < sArray.length - sArray2.length + 1; ++i) {
            for (int j = 0; j < sArray2.length; ++j) {
                if (sArray[i + j] != sArray2[j]) continue block0;
            }
            return i;
        }
        return 1;
    }

    public static int lastIndexOf(short[] sArray, short s) {
        return Shorts.lastIndexOf(sArray, s, 0, sArray.length);
    }

    private static int lastIndexOf(short[] sArray, short s, int n, int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (sArray[i] != s) continue;
            return i;
        }
        return 1;
    }

    public static short min(short ... sArray) {
        Preconditions.checkArgument(sArray.length > 0);
        short s = sArray[0];
        for (int i = 1; i < sArray.length; ++i) {
            if (sArray[i] >= s) continue;
            s = sArray[i];
        }
        return s;
    }

    public static short max(short ... sArray) {
        Preconditions.checkArgument(sArray.length > 0);
        short s = sArray[0];
        for (int i = 1; i < sArray.length; ++i) {
            if (sArray[i] <= s) continue;
            s = sArray[i];
        }
        return s;
    }

    @Beta
    public static short constrainToRange(short s, short s2, short s3) {
        Preconditions.checkArgument(s2 <= s3, "min (%s) must be less than or equal to max (%s)", (int)s2, (int)s3);
        return s < s2 ? s2 : (s < s3 ? s : s3);
    }

    public static short[] concat(short[] ... sArray) {
        int n = 0;
        for (short[] sArray2 : sArray) {
            n += sArray2.length;
        }
        short[] sArray3 = new short[n];
        int n2 = 0;
        for (short[] sArray4 : sArray) {
            System.arraycopy(sArray4, 0, sArray3, n2, sArray4.length);
            n2 += sArray4.length;
        }
        return sArray3;
    }

    @GwtIncompatible
    public static byte[] toByteArray(short s) {
        return new byte[]{(byte)(s >> 8), (byte)s};
    }

    @GwtIncompatible
    public static short fromByteArray(byte[] byArray) {
        Preconditions.checkArgument(byArray.length >= 2, "array too small: %s < %s", byArray.length, 2);
        return Shorts.fromBytes(byArray[0], byArray[1]);
    }

    @GwtIncompatible
    public static short fromBytes(byte by, byte by2) {
        return (short)(by << 8 | by2 & 0xFF);
    }

    @Beta
    public static Converter<String, Short> stringConverter() {
        return ShortConverter.INSTANCE;
    }

    public static short[] ensureCapacity(short[] sArray, int n, int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return sArray.length < n ? Arrays.copyOf(sArray, n + n2) : sArray;
    }

    public static String join(String string, short ... sArray) {
        Preconditions.checkNotNull(string);
        if (sArray.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(sArray.length * 6);
        stringBuilder.append(sArray[0]);
        for (int i = 1; i < sArray.length; ++i) {
            stringBuilder.append(string).append(sArray[i]);
        }
        return stringBuilder.toString();
    }

    public static Comparator<short[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static short[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof ShortArrayAsList) {
            return ((ShortArrayAsList)collection).toShortArray();
        }
        Object[] objectArray = collection.toArray();
        int n = objectArray.length;
        short[] sArray = new short[n];
        for (int i = 0; i < n; ++i) {
            sArray[i] = ((Number)Preconditions.checkNotNull(objectArray[i])).shortValue();
        }
        return sArray;
    }

    public static List<Short> asList(short ... sArray) {
        if (sArray.length == 0) {
            return Collections.emptyList();
        }
        return new ShortArrayAsList(sArray);
    }

    static int access$000(short[] sArray, short s, int n, int n2) {
        return Shorts.indexOf(sArray, s, n, n2);
    }

    static int access$100(short[] sArray, short s, int n, int n2) {
        return Shorts.lastIndexOf(sArray, s, n, n2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtCompatible
    private static class ShortArrayAsList
    extends AbstractList<Short>
    implements RandomAccess,
    Serializable {
        final short[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;

        ShortArrayAsList(short[] sArray) {
            this(sArray, 0, sArray.length);
        }

        ShortArrayAsList(short[] sArray, int n, int n2) {
            this.array = sArray;
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
        public Short get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Short && Shorts.access$000(this.array, (Short)object, this.start, this.end) != -1;
        }

        @Override
        public int indexOf(Object object) {
            int n;
            if (object instanceof Short && (n = Shorts.access$000(this.array, (Short)object, this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public int lastIndexOf(Object object) {
            int n;
            if (object instanceof Short && (n = Shorts.access$100(this.array, (Short)object, this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public Short set(int n, Short s) {
            Preconditions.checkElementIndex(n, this.size());
            short s2 = this.array[this.start + n];
            this.array[this.start + n] = Preconditions.checkNotNull(s);
            return s2;
        }

        @Override
        public List<Short> subList(int n, int n2) {
            int n3 = this.size();
            Preconditions.checkPositionIndexes(n, n2, n3);
            if (n == n2) {
                return Collections.emptyList();
            }
            return new ShortArrayAsList(this.array, this.start + n, this.start + n2);
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object instanceof ShortArrayAsList) {
                ShortArrayAsList shortArrayAsList = (ShortArrayAsList)object;
                int n = this.size();
                if (shortArrayAsList.size() != n) {
                    return true;
                }
                for (int i = 0; i < n; ++i) {
                    if (this.array[this.start + i] == shortArrayAsList.array[shortArrayAsList.start + i]) continue;
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
                n = 31 * n + Shorts.hashCode(this.array[i]);
            }
            return n;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 6);
            stringBuilder.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                stringBuilder.append(", ").append(this.array[i]);
            }
            return stringBuilder.append(']').toString();
        }

        short[] toShortArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public Object set(int n, Object object) {
            return this.set(n, (Short)object);
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    private static enum LexicographicalComparator implements Comparator<short[]>
    {
        INSTANCE;


        @Override
        public int compare(short[] sArray, short[] sArray2) {
            int n = Math.min(sArray.length, sArray2.length);
            for (int i = 0; i < n; ++i) {
                int n2 = Shorts.compare(sArray[i], sArray2[i]);
                if (n2 == 0) continue;
                return n2;
            }
            return sArray.length - sArray2.length;
        }

        public String toString() {
            return "Shorts.lexicographicalComparator()";
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((short[])object, (short[])object2);
        }
    }

    private static final class ShortConverter
    extends Converter<String, Short>
    implements Serializable {
        static final ShortConverter INSTANCE = new ShortConverter();
        private static final long serialVersionUID = 1L;

        private ShortConverter() {
        }

        @Override
        protected Short doForward(String string) {
            return Short.decode(string);
        }

        @Override
        protected String doBackward(Short s) {
            return s.toString();
        }

        public String toString() {
            return "Shorts.stringConverter()";
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected Object doBackward(Object object) {
            return this.doBackward((Short)object);
        }

        @Override
        protected Object doForward(Object object) {
            return this.doForward((String)object);
        }
    }
}

