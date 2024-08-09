/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
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
public class LongArrayFrontCodedList
extends AbstractObjectList<long[]>
implements Serializable,
Cloneable,
RandomAccess {
    private static final long serialVersionUID = 1L;
    protected final int n;
    protected final int ratio;
    protected final long[][] array;
    protected transient long[] p;

    public LongArrayFrontCodedList(Iterator<long[]> iterator2, int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Illegal ratio (" + n + ")");
        }
        long[][] lArray = LongBigArrays.EMPTY_BIG_ARRAY;
        long[] lArray2 = LongArrays.EMPTY_ARRAY;
        long[][] lArrayArray = new long[2][];
        long l = 0L;
        int n2 = 0;
        int n3 = 0;
        while (iterator2.hasNext()) {
            lArrayArray[n3] = iterator2.next();
            int n4 = lArrayArray[n3].length;
            if (n2 % n == 0) {
                lArray2 = LongArrays.grow(lArray2, n2 / n + 1);
                lArray2[n2 / n] = l;
                lArray = LongBigArrays.grow(lArray, l + (long)LongArrayFrontCodedList.count(n4) + (long)n4, l);
                l += (long)LongArrayFrontCodedList.writeInt(lArray, n4, l);
                LongBigArrays.copyToBig(lArrayArray[n3], 0, lArray, l, n4);
                l += (long)n4;
            } else {
                int n5;
                int n6 = lArrayArray[1 - n3].length;
                if (n4 < n6) {
                    n6 = n4;
                }
                for (n5 = 0; n5 < n6 && lArrayArray[0][n5] == lArrayArray[5][n5]; ++n5) {
                }
                lArray = LongBigArrays.grow(lArray, l + (long)LongArrayFrontCodedList.count(n4 -= n5) + (long)LongArrayFrontCodedList.count(n5) + (long)n4, l);
                l += (long)LongArrayFrontCodedList.writeInt(lArray, n4, l);
                l += (long)LongArrayFrontCodedList.writeInt(lArray, n5, l);
                LongBigArrays.copyToBig(lArrayArray[n3], n5, lArray, l, n4);
                l += (long)n4;
            }
            n3 = 1 - n3;
            ++n2;
        }
        this.n = n2;
        this.ratio = n;
        this.array = LongBigArrays.trim(lArray, l);
        this.p = LongArrays.trim(lArray2, (n2 + n - 1) / n);
    }

    public LongArrayFrontCodedList(Collection<long[]> collection, int n) {
        this(collection.iterator(), n);
    }

    private static int readInt(long[][] lArray, long l) {
        return (int)LongBigArrays.get(lArray, l);
    }

    private static int count(int n) {
        return 0;
    }

    private static int writeInt(long[][] lArray, int n, long l) {
        LongBigArrays.set(lArray, l, n);
        return 0;
    }

    public int ratio() {
        return this.ratio;
    }

    private int length(int n) {
        long[][] lArray = this.array;
        int n2 = n % this.ratio;
        long l = this.p[n / this.ratio];
        int n3 = LongArrayFrontCodedList.readInt(lArray, l);
        if (n2 == 0) {
            return n3;
        }
        l += (long)(LongArrayFrontCodedList.count(n3) + n3);
        n3 = LongArrayFrontCodedList.readInt(lArray, l);
        int n4 = LongArrayFrontCodedList.readInt(lArray, l + (long)LongArrayFrontCodedList.count(n3));
        for (int i = 0; i < n2 - 1; ++i) {
            n3 = LongArrayFrontCodedList.readInt(lArray, l += (long)(LongArrayFrontCodedList.count(n3) + LongArrayFrontCodedList.count(n4) + n3));
            n4 = LongArrayFrontCodedList.readInt(lArray, l + (long)LongArrayFrontCodedList.count(n3));
        }
        return n3 + n4;
    }

    public int arrayLength(int n) {
        this.ensureRestrictedIndex(n);
        return this.length(n);
    }

    private int extract(int n, long[] lArray, int n2, int n3) {
        long l;
        int n4 = n % this.ratio;
        long l2 = l = this.p[n / this.ratio];
        int n5 = LongArrayFrontCodedList.readInt(this.array, l2);
        int n6 = 0;
        if (n4 == 0) {
            l2 = this.p[n / this.ratio] + (long)LongArrayFrontCodedList.count(n5);
            LongBigArrays.copyFromBig(this.array, l2, lArray, n2, Math.min(n3, n5));
            return n5;
        }
        int n7 = 0;
        for (int i = 0; i < n4; ++i) {
            long l3 = l2 + (long)LongArrayFrontCodedList.count(n5) + (long)(i != 0 ? LongArrayFrontCodedList.count(n7) : 0);
            n7 = LongArrayFrontCodedList.readInt(this.array, (l2 = l3 + (long)n5) + (long)LongArrayFrontCodedList.count(n5 = LongArrayFrontCodedList.readInt(this.array, l2)));
            int n8 = Math.min(n7, n3);
            if (n8 <= n6) {
                n6 = n8;
                continue;
            }
            LongBigArrays.copyFromBig(this.array, l3, lArray, n6 + n2, n8 - n6);
            n6 = n8;
        }
        if (n6 < n3) {
            LongBigArrays.copyFromBig(this.array, l2 + (long)LongArrayFrontCodedList.count(n5) + (long)LongArrayFrontCodedList.count(n7), lArray, n6 + n2, Math.min(n5, n3 - n6));
        }
        return n5 + n7;
    }

    @Override
    public long[] get(int n) {
        return this.getArray(n);
    }

    public long[] getArray(int n) {
        this.ensureRestrictedIndex(n);
        int n2 = this.length(n);
        long[] lArray = new long[n2];
        this.extract(n, lArray, 0, n2);
        return lArray;
    }

    public int get(int n, long[] lArray, int n2, int n3) {
        this.ensureRestrictedIndex(n);
        LongArrays.ensureOffsetLength(lArray, n2, n3);
        int n4 = this.extract(n, lArray, n2, n3);
        if (n3 >= n4) {
            return n4;
        }
        return n3 - n4;
    }

    public int get(int n, long[] lArray) {
        return this.get(n, lArray, 0, lArray.length);
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public ObjectListIterator<long[]> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<long[]>(this, n){
            long[] s;
            int i;
            long pos;
            boolean inSync;
            final int val$start;
            final LongArrayFrontCodedList this$0;
            {
                this.this$0 = longArrayFrontCodedList;
                this.val$start = n;
                this.s = LongArrays.EMPTY_ARRAY;
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
            public long[] next() {
                int n;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (this.i % this.this$0.ratio == 0) {
                    this.pos = this.this$0.p[this.i / this.this$0.ratio];
                    n = LongArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    this.s = LongArrays.ensureCapacity(this.s, n, 0);
                    LongBigArrays.copyFromBig(this.this$0.array, this.pos + (long)LongArrayFrontCodedList.access$100(n), this.s, 0, n);
                    this.pos += (long)(n + LongArrayFrontCodedList.access$100(n));
                    this.inSync = true;
                } else if (this.inSync) {
                    n = LongArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    int n2 = LongArrayFrontCodedList.access$000(this.this$0.array, this.pos + (long)LongArrayFrontCodedList.access$100(n));
                    this.s = LongArrays.ensureCapacity(this.s, n + n2, n2);
                    LongBigArrays.copyFromBig(this.this$0.array, this.pos + (long)LongArrayFrontCodedList.access$100(n) + (long)LongArrayFrontCodedList.access$100(n2), this.s, n2, n);
                    this.pos += (long)(LongArrayFrontCodedList.access$100(n) + LongArrayFrontCodedList.access$100(n2) + n);
                    n += n2;
                } else {
                    n = LongArrayFrontCodedList.access$200(this.this$0, this.i);
                    this.s = LongArrays.ensureCapacity(this.s, n, 0);
                    LongArrayFrontCodedList.access$300(this.this$0, this.i, this.s, 0, n);
                }
                ++this.i;
                return LongArrays.copy(this.s, 0, n);
            }

            @Override
            public long[] previous() {
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

    public LongArrayFrontCodedList clone() {
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
            stringBuffer.append(LongArrayList.wrap(this.getArray(i)).toString());
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    protected long[] rebuildPointerArray() {
        long[] lArray = new long[(this.n + this.ratio - 1) / this.ratio];
        long[][] lArray2 = this.array;
        long l = 0L;
        int n = 0;
        int n2 = this.ratio - 1;
        for (int i = 0; i < this.n; ++i) {
            int n3 = LongArrayFrontCodedList.readInt(lArray2, l);
            int n4 = LongArrayFrontCodedList.count(n3);
            if (++n2 == this.ratio) {
                n2 = 0;
                lArray[n++] = l;
                l += (long)(n4 + n3);
                continue;
            }
            l += (long)(n4 + LongArrayFrontCodedList.count(LongArrayFrontCodedList.readInt(lArray2, l + (long)n4)) + n3);
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

    static int access$000(long[][] lArray, long l) {
        return LongArrayFrontCodedList.readInt(lArray, l);
    }

    static int access$100(int n) {
        return LongArrayFrontCodedList.count(n);
    }

    static int access$200(LongArrayFrontCodedList longArrayFrontCodedList, int n) {
        return longArrayFrontCodedList.length(n);
    }

    static int access$300(LongArrayFrontCodedList longArrayFrontCodedList, int n, long[] lArray, int n2, int n3) {
        return longArrayFrontCodedList.extract(n, lArray, n2, n3);
    }
}

