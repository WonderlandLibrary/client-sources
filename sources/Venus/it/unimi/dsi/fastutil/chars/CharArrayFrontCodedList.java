/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharBigArrays;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.objects.AbstractObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CharArrayFrontCodedList
extends AbstractObjectList<char[]>
implements Serializable,
Cloneable,
RandomAccess {
    private static final long serialVersionUID = 1L;
    protected final int n;
    protected final int ratio;
    protected final char[][] array;
    protected transient long[] p;

    public CharArrayFrontCodedList(Iterator<char[]> iterator2, int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Illegal ratio (" + n + ")");
        }
        char[][] cArray = CharBigArrays.EMPTY_BIG_ARRAY;
        long[] lArray = LongArrays.EMPTY_ARRAY;
        char[][] cArrayArray = new char[2][];
        long l = 0L;
        int n2 = 0;
        int n3 = 0;
        while (iterator2.hasNext()) {
            cArrayArray[n3] = iterator2.next();
            int n4 = cArrayArray[n3].length;
            if (n2 % n == 0) {
                lArray = LongArrays.grow(lArray, n2 / n + 1);
                lArray[n2 / n] = l;
                cArray = CharBigArrays.grow(cArray, l + (long)CharArrayFrontCodedList.count(n4) + (long)n4, l);
                l += (long)CharArrayFrontCodedList.writeInt(cArray, n4, l);
                CharBigArrays.copyToBig(cArrayArray[n3], 0, cArray, l, n4);
                l += (long)n4;
            } else {
                int n5;
                int n6 = cArrayArray[1 - n3].length;
                if (n4 < n6) {
                    n6 = n4;
                }
                for (n5 = 0; n5 < n6 && cArrayArray[0][n5] == cArrayArray[5][n5]; ++n5) {
                }
                cArray = CharBigArrays.grow(cArray, l + (long)CharArrayFrontCodedList.count(n4 -= n5) + (long)CharArrayFrontCodedList.count(n5) + (long)n4, l);
                l += (long)CharArrayFrontCodedList.writeInt(cArray, n4, l);
                l += (long)CharArrayFrontCodedList.writeInt(cArray, n5, l);
                CharBigArrays.copyToBig(cArrayArray[n3], n5, cArray, l, n4);
                l += (long)n4;
            }
            n3 = 1 - n3;
            ++n2;
        }
        this.n = n2;
        this.ratio = n;
        this.array = CharBigArrays.trim(cArray, l);
        this.p = LongArrays.trim(lArray, (n2 + n - 1) / n);
    }

    public CharArrayFrontCodedList(Collection<char[]> collection, int n) {
        this(collection.iterator(), n);
    }

    private static int readInt(char[][] cArray, long l) {
        int n = CharBigArrays.get(cArray, l);
        return n < 32768 ? n : (n & Short.MAX_VALUE) << 16 | CharBigArrays.get(cArray, l + 1L);
    }

    private static int count(int n) {
        return n < 32768 ? 1 : 2;
    }

    private static int writeInt(char[][] cArray, int n, long l) {
        if (n < 32768) {
            CharBigArrays.set(cArray, l, (char)n);
            return 0;
        }
        CharBigArrays.set(cArray, l++, (char)(n >>> 16 | 0x8000));
        CharBigArrays.set(cArray, l, (char)(n & 0xFFFF));
        return 1;
    }

    public int ratio() {
        return this.ratio;
    }

    private int length(int n) {
        char[][] cArray = this.array;
        int n2 = n % this.ratio;
        long l = this.p[n / this.ratio];
        int n3 = CharArrayFrontCodedList.readInt(cArray, l);
        if (n2 == 0) {
            return n3;
        }
        l += (long)(CharArrayFrontCodedList.count(n3) + n3);
        n3 = CharArrayFrontCodedList.readInt(cArray, l);
        int n4 = CharArrayFrontCodedList.readInt(cArray, l + (long)CharArrayFrontCodedList.count(n3));
        for (int i = 0; i < n2 - 1; ++i) {
            n3 = CharArrayFrontCodedList.readInt(cArray, l += (long)(CharArrayFrontCodedList.count(n3) + CharArrayFrontCodedList.count(n4) + n3));
            n4 = CharArrayFrontCodedList.readInt(cArray, l + (long)CharArrayFrontCodedList.count(n3));
        }
        return n3 + n4;
    }

    public int arrayLength(int n) {
        this.ensureRestrictedIndex(n);
        return this.length(n);
    }

    private int extract(int n, char[] cArray, int n2, int n3) {
        long l;
        int n4 = n % this.ratio;
        long l2 = l = this.p[n / this.ratio];
        int n5 = CharArrayFrontCodedList.readInt(this.array, l2);
        int n6 = 0;
        if (n4 == 0) {
            l2 = this.p[n / this.ratio] + (long)CharArrayFrontCodedList.count(n5);
            CharBigArrays.copyFromBig(this.array, l2, cArray, n2, Math.min(n3, n5));
            return n5;
        }
        int n7 = 0;
        for (int i = 0; i < n4; ++i) {
            long l3 = l2 + (long)CharArrayFrontCodedList.count(n5) + (long)(i != 0 ? CharArrayFrontCodedList.count(n7) : 0);
            n7 = CharArrayFrontCodedList.readInt(this.array, (l2 = l3 + (long)n5) + (long)CharArrayFrontCodedList.count(n5 = CharArrayFrontCodedList.readInt(this.array, l2)));
            int n8 = Math.min(n7, n3);
            if (n8 <= n6) {
                n6 = n8;
                continue;
            }
            CharBigArrays.copyFromBig(this.array, l3, cArray, n6 + n2, n8 - n6);
            n6 = n8;
        }
        if (n6 < n3) {
            CharBigArrays.copyFromBig(this.array, l2 + (long)CharArrayFrontCodedList.count(n5) + (long)CharArrayFrontCodedList.count(n7), cArray, n6 + n2, Math.min(n5, n3 - n6));
        }
        return n5 + n7;
    }

    @Override
    public char[] get(int n) {
        return this.getArray(n);
    }

    public char[] getArray(int n) {
        this.ensureRestrictedIndex(n);
        int n2 = this.length(n);
        char[] cArray = new char[n2];
        this.extract(n, cArray, 0, n2);
        return cArray;
    }

    public int get(int n, char[] cArray, int n2, int n3) {
        this.ensureRestrictedIndex(n);
        CharArrays.ensureOffsetLength(cArray, n2, n3);
        int n4 = this.extract(n, cArray, n2, n3);
        if (n3 >= n4) {
            return n4;
        }
        return n3 - n4;
    }

    public int get(int n, char[] cArray) {
        return this.get(n, cArray, 0, cArray.length);
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public ObjectListIterator<char[]> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<char[]>(this, n){
            char[] s;
            int i;
            long pos;
            boolean inSync;
            final int val$start;
            final CharArrayFrontCodedList this$0;
            {
                this.this$0 = charArrayFrontCodedList;
                this.val$start = n;
                this.s = CharArrays.EMPTY_ARRAY;
                this.i = 0;
                this.pos = 0L;
                if (this.val$start != 0) {
                    if (this.val$start == this.this$0.n) {
                        this.i = this.val$start;
                    } else {
                        this.pos = this.this$0.p[this.val$start / this.this$0.ratio];
                        int n2 = this.val$start % this.this$0.ratio;
                        this.i = this.val$start - n2;
                        while (n2-- != 0) {
                            this.next();
                        }
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return this.i < this.this$0.n;
            }

            @Override
            public boolean hasPrevious() {
                return this.i > 0;
            }

            @Override
            public int previousIndex() {
                return this.i - 1;
            }

            @Override
            public int nextIndex() {
                return this.i;
            }

            @Override
            public char[] next() {
                int n;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (this.i % this.this$0.ratio == 0) {
                    this.pos = this.this$0.p[this.i / this.this$0.ratio];
                    n = CharArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    this.s = CharArrays.ensureCapacity(this.s, n, 0);
                    CharBigArrays.copyFromBig(this.this$0.array, this.pos + (long)CharArrayFrontCodedList.access$100(n), this.s, 0, n);
                    this.pos += (long)(n + CharArrayFrontCodedList.access$100(n));
                    this.inSync = true;
                } else if (this.inSync) {
                    n = CharArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    int n2 = CharArrayFrontCodedList.access$000(this.this$0.array, this.pos + (long)CharArrayFrontCodedList.access$100(n));
                    this.s = CharArrays.ensureCapacity(this.s, n + n2, n2);
                    CharBigArrays.copyFromBig(this.this$0.array, this.pos + (long)CharArrayFrontCodedList.access$100(n) + (long)CharArrayFrontCodedList.access$100(n2), this.s, n2, n);
                    this.pos += (long)(CharArrayFrontCodedList.access$100(n) + CharArrayFrontCodedList.access$100(n2) + n);
                    n += n2;
                } else {
                    n = CharArrayFrontCodedList.access$200(this.this$0, this.i);
                    this.s = CharArrays.ensureCapacity(this.s, n, 0);
                    CharArrayFrontCodedList.access$300(this.this$0, this.i, this.s, 0, n);
                }
                ++this.i;
                return CharArrays.copy(this.s, 0, n);
            }

            @Override
            public char[] previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.inSync = false;
                return this.this$0.getArray(--this.i);
            }

            @Override
            public Object next() {
                return this.next();
            }

            @Override
            public Object previous() {
                return this.previous();
            }
        };
    }

    public CharArrayFrontCodedList clone() {
        return this;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < this.n; ++i) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(CharArrayList.wrap(this.getArray(i)).toString());
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    protected long[] rebuildPointerArray() {
        long[] lArray = new long[(this.n + this.ratio - 1) / this.ratio];
        char[][] cArray = this.array;
        long l = 0L;
        int n = 0;
        int n2 = this.ratio - 1;
        for (int i = 0; i < this.n; ++i) {
            int n3 = CharArrayFrontCodedList.readInt(cArray, l);
            int n4 = CharArrayFrontCodedList.count(n3);
            if (++n2 == this.ratio) {
                n2 = 0;
                lArray[n++] = l;
                l += (long)(n4 + n3);
                continue;
            }
            l += (long)(n4 + CharArrayFrontCodedList.count(CharArrayFrontCodedList.readInt(cArray, l + (long)n4)) + n3);
        }
        return lArray;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.p = this.rebuildPointerArray();
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    public Object get(int n) {
        return this.get(n);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(char[][] cArray, long l) {
        return CharArrayFrontCodedList.readInt(cArray, l);
    }

    static int access$100(int n) {
        return CharArrayFrontCodedList.count(n);
    }

    static int access$200(CharArrayFrontCodedList charArrayFrontCodedList, int n) {
        return charArrayFrontCodedList.length(n);
    }

    static int access$300(CharArrayFrontCodedList charArrayFrontCodedList, int n, char[] cArray, int n2, int n3) {
        return charArrayFrontCodedList.extract(n, cArray, n2, n3);
    }
}

