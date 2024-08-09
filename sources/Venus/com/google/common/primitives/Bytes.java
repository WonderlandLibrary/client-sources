/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible
public final class Bytes {
    private Bytes() {
    }

    public static int hashCode(byte by) {
        return by;
    }

    public static boolean contains(byte[] byArray, byte by) {
        for (byte by2 : byArray) {
            if (by2 != by) continue;
            return false;
        }
        return true;
    }

    public static int indexOf(byte[] byArray, byte by) {
        return Bytes.indexOf(byArray, by, 0, byArray.length);
    }

    private static int indexOf(byte[] byArray, byte by, int n, int n2) {
        for (int i = n; i < n2; ++i) {
            if (byArray[i] != by) continue;
            return i;
        }
        return 1;
    }

    public static int indexOf(byte[] byArray, byte[] byArray2) {
        Preconditions.checkNotNull(byArray, "array");
        Preconditions.checkNotNull(byArray2, "target");
        if (byArray2.length == 0) {
            return 1;
        }
        block0: for (int i = 0; i < byArray.length - byArray2.length + 1; ++i) {
            for (int j = 0; j < byArray2.length; ++j) {
                if (byArray[i + j] != byArray2[j]) continue block0;
            }
            return i;
        }
        return 1;
    }

    public static int lastIndexOf(byte[] byArray, byte by) {
        return Bytes.lastIndexOf(byArray, by, 0, byArray.length);
    }

    private static int lastIndexOf(byte[] byArray, byte by, int n, int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (byArray[i] != by) continue;
            return i;
        }
        return 1;
    }

    public static byte[] concat(byte[] ... byArray) {
        int n = 0;
        for (byte[] byArray2 : byArray) {
            n += byArray2.length;
        }
        byte[] byArray3 = new byte[n];
        int n2 = 0;
        for (byte[] byArray4 : byArray) {
            System.arraycopy(byArray4, 0, byArray3, n2, byArray4.length);
            n2 += byArray4.length;
        }
        return byArray3;
    }

    public static byte[] ensureCapacity(byte[] byArray, int n, int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return byArray.length < n ? Arrays.copyOf(byArray, n + n2) : byArray;
    }

    public static byte[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof ByteArrayAsList) {
            return ((ByteArrayAsList)collection).toByteArray();
        }
        Object[] objectArray = collection.toArray();
        int n = objectArray.length;
        byte[] byArray = new byte[n];
        for (int i = 0; i < n; ++i) {
            byArray[i] = ((Number)Preconditions.checkNotNull(objectArray[i])).byteValue();
        }
        return byArray;
    }

    public static List<Byte> asList(byte ... byArray) {
        if (byArray.length == 0) {
            return Collections.emptyList();
        }
        return new ByteArrayAsList(byArray);
    }

    static int access$000(byte[] byArray, byte by, int n, int n2) {
        return Bytes.indexOf(byArray, by, n, n2);
    }

    static int access$100(byte[] byArray, byte by, int n, int n2) {
        return Bytes.lastIndexOf(byArray, by, n, n2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtCompatible
    private static class ByteArrayAsList
    extends AbstractList<Byte>
    implements RandomAccess,
    Serializable {
        final byte[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;

        ByteArrayAsList(byte[] byArray) {
            this(byArray, 0, byArray.length);
        }

        ByteArrayAsList(byte[] byArray, int n, int n2) {
            this.array = byArray;
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
        public Byte get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Byte && Bytes.access$000(this.array, (Byte)object, this.start, this.end) != -1;
        }

        @Override
        public int indexOf(Object object) {
            int n;
            if (object instanceof Byte && (n = Bytes.access$000(this.array, (Byte)object, this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public int lastIndexOf(Object object) {
            int n;
            if (object instanceof Byte && (n = Bytes.access$100(this.array, (Byte)object, this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public Byte set(int n, Byte by) {
            Preconditions.checkElementIndex(n, this.size());
            byte by2 = this.array[this.start + n];
            this.array[this.start + n] = Preconditions.checkNotNull(by);
            return by2;
        }

        @Override
        public List<Byte> subList(int n, int n2) {
            int n3 = this.size();
            Preconditions.checkPositionIndexes(n, n2, n3);
            if (n == n2) {
                return Collections.emptyList();
            }
            return new ByteArrayAsList(this.array, this.start + n, this.start + n2);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return false;
            }
            if (object instanceof ByteArrayAsList) {
                ByteArrayAsList byteArrayAsList = (ByteArrayAsList)object;
                int n = this.size();
                if (byteArrayAsList.size() != n) {
                    return true;
                }
                for (int i = 0; i < n; ++i) {
                    if (this.array[this.start + i] == byteArrayAsList.array[byteArrayAsList.start + i]) continue;
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
                n = 31 * n + Bytes.hashCode(this.array[i]);
            }
            return n;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 5);
            stringBuilder.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                stringBuilder.append(", ").append(this.array[i]);
            }
            return stringBuilder.append(']').toString();
        }

        byte[] toByteArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public Object set(int n, Object object) {
            return this.set(n, (Byte)object);
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }
}

