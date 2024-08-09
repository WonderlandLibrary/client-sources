/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.objects.AbstractObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
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
public class ShortArrayFrontCodedList
extends AbstractObjectList<short[]>
implements Serializable,
Cloneable,
RandomAccess {
    private static final long serialVersionUID = 1L;
    protected final int n;
    protected final int ratio;
    protected final short[][] array;
    protected transient long[] p;

    public ShortArrayFrontCodedList(Iterator<short[]> iterator2, int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Illegal ratio (" + n + ")");
        }
        short[][] sArray = ShortBigArrays.EMPTY_BIG_ARRAY;
        long[] lArray = LongArrays.EMPTY_ARRAY;
        short[][] sArrayArray = new short[2][];
        long l = 0L;
        int n2 = 0;
        int n3 = 0;
        while (iterator2.hasNext()) {
            sArrayArray[n3] = iterator2.next();
            int n4 = sArrayArray[n3].length;
            if (n2 % n == 0) {
                lArray = LongArrays.grow(lArray, n2 / n + 1);
                lArray[n2 / n] = l;
                sArray = ShortBigArrays.grow(sArray, l + (long)ShortArrayFrontCodedList.count(n4) + (long)n4, l);
                l += (long)ShortArrayFrontCodedList.writeInt(sArray, n4, l);
                ShortBigArrays.copyToBig(sArrayArray[n3], 0, sArray, l, n4);
                l += (long)n4;
            } else {
                int n5;
                int n6 = sArrayArray[1 - n3].length;
                if (n4 < n6) {
                    n6 = n4;
                }
                for (n5 = 0; n5 < n6 && sArrayArray[0][n5] == sArrayArray[5][n5]; ++n5) {
                }
                sArray = ShortBigArrays.grow(sArray, l + (long)ShortArrayFrontCodedList.count(n4 -= n5) + (long)ShortArrayFrontCodedList.count(n5) + (long)n4, l);
                l += (long)ShortArrayFrontCodedList.writeInt(sArray, n4, l);
                l += (long)ShortArrayFrontCodedList.writeInt(sArray, n5, l);
                ShortBigArrays.copyToBig(sArrayArray[n3], n5, sArray, l, n4);
                l += (long)n4;
            }
            n3 = 1 - n3;
            ++n2;
        }
        this.n = n2;
        this.ratio = n;
        this.array = ShortBigArrays.trim(sArray, l);
        this.p = LongArrays.trim(lArray, (n2 + n - 1) / n);
    }

    public ShortArrayFrontCodedList(Collection<short[]> collection, int n) {
        this(collection.iterator(), n);
    }

    private static int readInt(short[][] sArray, long l) {
        int n = ShortBigArrays.get(sArray, l);
        return n >= 0 ? n : n << 16 | ShortBigArrays.get(sArray, l + 1L) & 0xFFFF;
    }

    private static int count(int n) {
        return n < 32768 ? 1 : 2;
    }

    private static int writeInt(short[][] sArray, int n, long l) {
        if (n < 32768) {
            ShortBigArrays.set(sArray, l, (short)n);
            return 0;
        }
        ShortBigArrays.set(sArray, l++, (short)(-(n >>> 16) - 1));
        ShortBigArrays.set(sArray, l, (short)(n & 0xFFFF));
        return 1;
    }

    public int ratio() {
        return this.ratio;
    }

    private int length(int n) {
        short[][] sArray = this.array;
        int n2 = n % this.ratio;
        long l = this.p[n / this.ratio];
        int n3 = ShortArrayFrontCodedList.readInt(sArray, l);
        if (n2 == 0) {
            return n3;
        }
        l += (long)(ShortArrayFrontCodedList.count(n3) + n3);
        n3 = ShortArrayFrontCodedList.readInt(sArray, l);
        int n4 = ShortArrayFrontCodedList.readInt(sArray, l + (long)ShortArrayFrontCodedList.count(n3));
        for (int i = 0; i < n2 - 1; ++i) {
            n3 = ShortArrayFrontCodedList.readInt(sArray, l += (long)(ShortArrayFrontCodedList.count(n3) + ShortArrayFrontCodedList.count(n4) + n3));
            n4 = ShortArrayFrontCodedList.readInt(sArray, l + (long)ShortArrayFrontCodedList.count(n3));
        }
        return n3 + n4;
    }

    public int arrayLength(int n) {
        this.ensureRestrictedIndex(n);
        return this.length(n);
    }

    private int extract(int n, short[] sArray, int n2, int n3) {
        long l;
        int n4 = n % this.ratio;
        long l2 = l = this.p[n / this.ratio];
        int n5 = ShortArrayFrontCodedList.readInt(this.array, l2);
        int n6 = 0;
        if (n4 == 0) {
            l2 = this.p[n / this.ratio] + (long)ShortArrayFrontCodedList.count(n5);
            ShortBigArrays.copyFromBig(this.array, l2, sArray, n2, Math.min(n3, n5));
            return n5;
        }
        int n7 = 0;
        for (int i = 0; i < n4; ++i) {
            long l3 = l2 + (long)ShortArrayFrontCodedList.count(n5) + (long)(i != 0 ? ShortArrayFrontCodedList.count(n7) : 0);
            n7 = ShortArrayFrontCodedList.readInt(this.array, (l2 = l3 + (long)n5) + (long)ShortArrayFrontCodedList.count(n5 = ShortArrayFrontCodedList.readInt(this.array, l2)));
            int n8 = Math.min(n7, n3);
            if (n8 <= n6) {
                n6 = n8;
                continue;
            }
            ShortBigArrays.copyFromBig(this.array, l3, sArray, n6 + n2, n8 - n6);
            n6 = n8;
        }
        if (n6 < n3) {
            ShortBigArrays.copyFromBig(this.array, l2 + (long)ShortArrayFrontCodedList.count(n5) + (long)ShortArrayFrontCodedList.count(n7), sArray, n6 + n2, Math.min(n5, n3 - n6));
        }
        return n5 + n7;
    }

    @Override
    public short[] get(int n) {
        return this.getArray(n);
    }

    public short[] getArray(int n) {
        this.ensureRestrictedIndex(n);
        int n2 = this.length(n);
        short[] sArray = new short[n2];
        this.extract(n, sArray, 0, n2);
        return sArray;
    }

    public int get(int n, short[] sArray, int n2, int n3) {
        this.ensureRestrictedIndex(n);
        ShortArrays.ensureOffsetLength(sArray, n2, n3);
        int n4 = this.extract(n, sArray, n2, n3);
        if (n3 >= n4) {
            return n4;
        }
        return n3 - n4;
    }

    public int get(int n, short[] sArray) {
        return this.get(n, sArray, 0, sArray.length);
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public ObjectListIterator<short[]> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<short[]>(this, n){
            short[] s;
            int i;
            long pos;
            boolean inSync;
            final int val$start;
            final ShortArrayFrontCodedList this$0;
            {
                this.this$0 = shortArrayFrontCodedList;
                this.val$start = n;
                this.s = ShortArrays.EMPTY_ARRAY;
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
            public short[] next() {
                int n;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (this.i % this.this$0.ratio == 0) {
                    this.pos = this.this$0.p[this.i / this.this$0.ratio];
                    n = ShortArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    this.s = ShortArrays.ensureCapacity(this.s, n, 0);
                    ShortBigArrays.copyFromBig(this.this$0.array, this.pos + (long)ShortArrayFrontCodedList.access$100(n), this.s, 0, n);
                    this.pos += (long)(n + ShortArrayFrontCodedList.access$100(n));
                    this.inSync = true;
                } else if (this.inSync) {
                    n = ShortArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    int n2 = ShortArrayFrontCodedList.access$000(this.this$0.array, this.pos + (long)ShortArrayFrontCodedList.access$100(n));
                    this.s = ShortArrays.ensureCapacity(this.s, n + n2, n2);
                    ShortBigArrays.copyFromBig(this.this$0.array, this.pos + (long)ShortArrayFrontCodedList.access$100(n) + (long)ShortArrayFrontCodedList.access$100(n2), this.s, n2, n);
                    this.pos += (long)(ShortArrayFrontCodedList.access$100(n) + ShortArrayFrontCodedList.access$100(n2) + n);
                    n += n2;
                } else {
                    n = ShortArrayFrontCodedList.access$200(this.this$0, this.i);
                    this.s = ShortArrays.ensureCapacity(this.s, n, 0);
                    ShortArrayFrontCodedList.access$300(this.this$0, this.i, this.s, 0, n);
                }
                ++this.i;
                return ShortArrays.copy(this.s, 0, n);
            }

            @Override
            public short[] previous() {
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

    public ShortArrayFrontCodedList clone() {
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
            stringBuffer.append(ShortArrayList.wrap(this.getArray(i)).toString());
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    protected long[] rebuildPointerArray() {
        long[] lArray = new long[(this.n + this.ratio - 1) / this.ratio];
        short[][] sArray = this.array;
        long l = 0L;
        int n = 0;
        int n2 = this.ratio - 1;
        for (int i = 0; i < this.n; ++i) {
            int n3 = ShortArrayFrontCodedList.readInt(sArray, l);
            int n4 = ShortArrayFrontCodedList.count(n3);
            if (++n2 == this.ratio) {
                n2 = 0;
                lArray[n++] = l;
                l += (long)(n4 + n3);
                continue;
            }
            l += (long)(n4 + ShortArrayFrontCodedList.count(ShortArrayFrontCodedList.readInt(sArray, l + (long)n4)) + n3);
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

    static int access$000(short[][] sArray, long l) {
        return ShortArrayFrontCodedList.readInt(sArray, l);
    }

    static int access$100(int n) {
        return ShortArrayFrontCodedList.count(n);
    }

    static int access$200(ShortArrayFrontCodedList shortArrayFrontCodedList, int n) {
        return shortArrayFrontCodedList.length(n);
    }

    static int access$300(ShortArrayFrontCodedList shortArrayFrontCodedList, int n, short[] sArray, int n2, int n3) {
        return shortArrayFrontCodedList.extract(n, sArray, n2, n3);
    }
}

