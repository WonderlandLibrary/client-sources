/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Chars {
    public static final int BYTES = 2;

    private Chars() {
    }

    public static int hashCode(char c) {
        return c;
    }

    public static char checkedCast(long l) {
        char c = (char)l;
        Preconditions.checkArgument((long)c == l, "Out of range: %s", l);
        return c;
    }

    public static char saturatedCast(long l) {
        if (l > 65535L) {
            return '\u0000';
        }
        if (l < 0L) {
            return '\u0001';
        }
        return (char)l;
    }

    public static int compare(char c, char c2) {
        return c - c2;
    }

    public static boolean contains(char[] cArray, char c) {
        for (char c2 : cArray) {
            if (c2 != c) continue;
            return false;
        }
        return true;
    }

    public static int indexOf(char[] cArray, char c) {
        return Chars.indexOf(cArray, c, 0, cArray.length);
    }

    private static int indexOf(char[] cArray, char c, int n, int n2) {
        for (int i = n; i < n2; ++i) {
            if (cArray[i] != c) continue;
            return i;
        }
        return 1;
    }

    public static int indexOf(char[] cArray, char[] cArray2) {
        Preconditions.checkNotNull(cArray, "array");
        Preconditions.checkNotNull(cArray2, "target");
        if (cArray2.length == 0) {
            return 1;
        }
        block0: for (int i = 0; i < cArray.length - cArray2.length + 1; ++i) {
            for (int j = 0; j < cArray2.length; ++j) {
                if (cArray[i + j] != cArray2[j]) continue block0;
            }
            return i;
        }
        return 1;
    }

    public static int lastIndexOf(char[] cArray, char c) {
        return Chars.lastIndexOf(cArray, c, 0, cArray.length);
    }

    private static int lastIndexOf(char[] cArray, char c, int n, int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (cArray[i] != c) continue;
            return i;
        }
        return 1;
    }

    public static char min(char ... cArray) {
        Preconditions.checkArgument(cArray.length > 0);
        char c = cArray[0];
        for (int i = 1; i < cArray.length; ++i) {
            if (cArray[i] >= c) continue;
            c = cArray[i];
        }
        return c;
    }

    public static char max(char ... cArray) {
        Preconditions.checkArgument(cArray.length > 0);
        char c = cArray[0];
        for (int i = 1; i < cArray.length; ++i) {
            if (cArray[i] <= c) continue;
            c = cArray[i];
        }
        return c;
    }

    @Beta
    public static char constrainToRange(char c, char c2, char c3) {
        Preconditions.checkArgument(c2 <= c3, "min (%s) must be less than or equal to max (%s)", c2, c3);
        return c < c2 ? c2 : (c < c3 ? c : c3);
    }

    public static char[] concat(char[] ... cArray) {
        int n = 0;
        for (char[] cArray2 : cArray) {
            n += cArray2.length;
        }
        char[] cArray3 = new char[n];
        int n2 = 0;
        for (char[] cArray4 : cArray) {
            System.arraycopy(cArray4, 0, cArray3, n2, cArray4.length);
            n2 += cArray4.length;
        }
        return cArray3;
    }

    @GwtIncompatible
    public static byte[] toByteArray(char c) {
        return new byte[]{(byte)(c >> 8), (byte)c};
    }

    @GwtIncompatible
    public static char fromByteArray(byte[] byArray) {
        Preconditions.checkArgument(byArray.length >= 2, "array too small: %s < %s", byArray.length, 2);
        return Chars.fromBytes(byArray[0], byArray[1]);
    }

    @GwtIncompatible
    public static char fromBytes(byte by, byte by2) {
        return (char)(by << 8 | by2 & 0xFF);
    }

    public static char[] ensureCapacity(char[] cArray, int n, int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return cArray.length < n ? Arrays.copyOf(cArray, n + n2) : cArray;
    }

    public static String join(String string, char ... cArray) {
        Preconditions.checkNotNull(string);
        int n = cArray.length;
        if (n == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(n + string.length() * (n - 1));
        stringBuilder.append(cArray[0]);
        for (int i = 1; i < n; ++i) {
            stringBuilder.append(string).append(cArray[i]);
        }
        return stringBuilder.toString();
    }

    public static Comparator<char[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static char[] toArray(Collection<Character> collection) {
        if (collection instanceof CharArrayAsList) {
            return ((CharArrayAsList)collection).toCharArray();
        }
        Object[] objectArray = collection.toArray();
        int n = objectArray.length;
        char[] cArray = new char[n];
        for (int i = 0; i < n; ++i) {
            cArray[i] = ((Character)Preconditions.checkNotNull(objectArray[i])).charValue();
        }
        return cArray;
    }

    public static List<Character> asList(char ... cArray) {
        if (cArray.length == 0) {
            return Collections.emptyList();
        }
        return new CharArrayAsList(cArray);
    }

    static int access$000(char[] cArray, char c, int n, int n2) {
        return Chars.indexOf(cArray, c, n, n2);
    }

    static int access$100(char[] cArray, char c, int n, int n2) {
        return Chars.lastIndexOf(cArray, c, n, n2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtCompatible
    private static class CharArrayAsList
    extends AbstractList<Character>
    implements RandomAccess,
    Serializable {
        final char[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;

        CharArrayAsList(char[] cArray) {
            this(cArray, 0, cArray.length);
        }

        CharArrayAsList(char[] cArray, int n, int n2) {
            this.array = cArray;
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
        public Character get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return Character.valueOf(this.array[this.start + n]);
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Character && Chars.access$000(this.array, ((Character)object).charValue(), this.start, this.end) != -1;
        }

        @Override
        public int indexOf(Object object) {
            int n;
            if (object instanceof Character && (n = Chars.access$000(this.array, ((Character)object).charValue(), this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public int lastIndexOf(Object object) {
            int n;
            if (object instanceof Character && (n = Chars.access$100(this.array, ((Character)object).charValue(), this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public Character set(int n, Character c) {
            Preconditions.checkElementIndex(n, this.size());
            char c2 = this.array[this.start + n];
            this.array[this.start + n] = Preconditions.checkNotNull(c).charValue();
            return Character.valueOf(c2);
        }

        @Override
        public List<Character> subList(int n, int n2) {
            int n3 = this.size();
            Preconditions.checkPositionIndexes(n, n2, n3);
            if (n == n2) {
                return Collections.emptyList();
            }
            return new CharArrayAsList(this.array, this.start + n, this.start + n2);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return false;
            }
            if (object instanceof CharArrayAsList) {
                CharArrayAsList charArrayAsList = (CharArrayAsList)object;
                int n = this.size();
                if (charArrayAsList.size() != n) {
                    return true;
                }
                for (int i = 0; i < n; ++i) {
                    if (this.array[this.start + i] == charArrayAsList.array[charArrayAsList.start + i]) continue;
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
                n = 31 * n + Chars.hashCode(this.array[i]);
            }
            return n;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 3);
            stringBuilder.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                stringBuilder.append(", ").append(this.array[i]);
            }
            return stringBuilder.append(']').toString();
        }

        char[] toCharArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public Object set(int n, Object object) {
            return this.set(n, (Character)object);
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    private static enum LexicographicalComparator implements Comparator<char[]>
    {
        INSTANCE;


        @Override
        public int compare(char[] cArray, char[] cArray2) {
            int n = Math.min(cArray.length, cArray2.length);
            for (int i = 0; i < n; ++i) {
                int n2 = Chars.compare(cArray[i], cArray2[i]);
                if (n2 == 0) continue;
                return n2;
            }
            return cArray.length - cArray2.length;
        }

        public String toString() {
            return "Chars.lexicographicalComparator()";
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((char[])object, (char[])object2);
        }
    }
}

