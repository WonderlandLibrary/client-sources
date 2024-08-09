/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntBigArrays;
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
public class IntArrayFrontCodedList
extends AbstractObjectList<int[]>
implements Serializable,
Cloneable,
RandomAccess {
    private static final long serialVersionUID = 1L;
    protected final int n;
    protected final int ratio;
    protected final int[][] array;
    protected transient long[] p;

    public IntArrayFrontCodedList(Iterator<int[]> iterator2, int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Illegal ratio (" + n + ")");
        }
        int[][] nArray = IntBigArrays.EMPTY_BIG_ARRAY;
        long[] lArray = LongArrays.EMPTY_ARRAY;
        int[][] nArrayArray = new int[2][];
        long l = 0L;
        int n2 = 0;
        int n3 = 0;
        while (iterator2.hasNext()) {
            nArrayArray[n3] = iterator2.next();
            int n4 = nArrayArray[n3].length;
            if (n2 % n == 0) {
                lArray = LongArrays.grow(lArray, n2 / n + 1);
                lArray[n2 / n] = l;
                nArray = IntBigArrays.grow(nArray, l + (long)IntArrayFrontCodedList.count(n4) + (long)n4, l);
                l += (long)IntArrayFrontCodedList.writeInt(nArray, n4, l);
                IntBigArrays.copyToBig(nArrayArray[n3], 0, nArray, l, n4);
                l += (long)n4;
            } else {
                int n5;
                int n6 = nArrayArray[1 - n3].length;
                if (n4 < n6) {
                    n6 = n4;
                }
                for (n5 = 0; n5 < n6 && nArrayArray[0][n5] == nArrayArray[5][n5]; ++n5) {
                }
                nArray = IntBigArrays.grow(nArray, l + (long)IntArrayFrontCodedList.count(n4 -= n5) + (long)IntArrayFrontCodedList.count(n5) + (long)n4, l);
                l += (long)IntArrayFrontCodedList.writeInt(nArray, n4, l);
                l += (long)IntArrayFrontCodedList.writeInt(nArray, n5, l);
                IntBigArrays.copyToBig(nArrayArray[n3], n5, nArray, l, n4);
                l += (long)n4;
            }
            n3 = 1 - n3;
            ++n2;
        }
        this.n = n2;
        this.ratio = n;
        this.array = IntBigArrays.trim(nArray, l);
        this.p = LongArrays.trim(lArray, (n2 + n - 1) / n);
    }

    public IntArrayFrontCodedList(Collection<int[]> collection, int n) {
        this(collection.iterator(), n);
    }

    private static int readInt(int[][] nArray, long l) {
        return IntBigArrays.get(nArray, l);
    }

    private static int count(int n) {
        return 0;
    }

    private static int writeInt(int[][] nArray, int n, long l) {
        IntBigArrays.set(nArray, l, n);
        return 0;
    }

    public int ratio() {
        return this.ratio;
    }

    private int length(int n) {
        int[][] nArray = this.array;
        int n2 = n % this.ratio;
        long l = this.p[n / this.ratio];
        int n3 = IntArrayFrontCodedList.readInt(nArray, l);
        if (n2 == 0) {
            return n3;
        }
        l += (long)(IntArrayFrontCodedList.count(n3) + n3);
        n3 = IntArrayFrontCodedList.readInt(nArray, l);
        int n4 = IntArrayFrontCodedList.readInt(nArray, l + (long)IntArrayFrontCodedList.count(n3));
        for (int i = 0; i < n2 - 1; ++i) {
            n3 = IntArrayFrontCodedList.readInt(nArray, l += (long)(IntArrayFrontCodedList.count(n3) + IntArrayFrontCodedList.count(n4) + n3));
            n4 = IntArrayFrontCodedList.readInt(nArray, l + (long)IntArrayFrontCodedList.count(n3));
        }
        return n3 + n4;
    }

    public int arrayLength(int n) {
        this.ensureRestrictedIndex(n);
        return this.length(n);
    }

    private int extract(int n, int[] nArray, int n2, int n3) {
        long l;
        int n4 = n % this.ratio;
        long l2 = l = this.p[n / this.ratio];
        int n5 = IntArrayFrontCodedList.readInt(this.array, l2);
        int n6 = 0;
        if (n4 == 0) {
            l2 = this.p[n / this.ratio] + (long)IntArrayFrontCodedList.count(n5);
            IntBigArrays.copyFromBig(this.array, l2, nArray, n2, Math.min(n3, n5));
            return n5;
        }
        int n7 = 0;
        for (int i = 0; i < n4; ++i) {
            long l3 = l2 + (long)IntArrayFrontCodedList.count(n5) + (long)(i != 0 ? IntArrayFrontCodedList.count(n7) : 0);
            n7 = IntArrayFrontCodedList.readInt(this.array, (l2 = l3 + (long)n5) + (long)IntArrayFrontCodedList.count(n5 = IntArrayFrontCodedList.readInt(this.array, l2)));
            int n8 = Math.min(n7, n3);
            if (n8 <= n6) {
                n6 = n8;
                continue;
            }
            IntBigArrays.copyFromBig(this.array, l3, nArray, n6 + n2, n8 - n6);
            n6 = n8;
        }
        if (n6 < n3) {
            IntBigArrays.copyFromBig(this.array, l2 + (long)IntArrayFrontCodedList.count(n5) + (long)IntArrayFrontCodedList.count(n7), nArray, n6 + n2, Math.min(n5, n3 - n6));
        }
        return n5 + n7;
    }

    @Override
    public int[] get(int n) {
        return this.getArray(n);
    }

    public int[] getArray(int n) {
        this.ensureRestrictedIndex(n);
        int n2 = this.length(n);
        int[] nArray = new int[n2];
        this.extract(n, nArray, 0, n2);
        return nArray;
    }

    public int get(int n, int[] nArray, int n2, int n3) {
        this.ensureRestrictedIndex(n);
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        int n4 = this.extract(n, nArray, n2, n3);
        if (n3 >= n4) {
            return n4;
        }
        return n3 - n4;
    }

    public int get(int n, int[] nArray) {
        return this.get(n, nArray, 0, nArray.length);
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public ObjectListIterator<int[]> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<int[]>(this, n){
            int[] s;
            int i;
            long pos;
            boolean inSync;
            final int val$start;
            final IntArrayFrontCodedList this$0;
            {
                this.this$0 = intArrayFrontCodedList;
                this.val$start = n;
                this.s = IntArrays.EMPTY_ARRAY;
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
            public int[] next() {
                int n;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (this.i % this.this$0.ratio == 0) {
                    this.pos = this.this$0.p[this.i / this.this$0.ratio];
                    n = IntArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    this.s = IntArrays.ensureCapacity(this.s, n, 0);
                    IntBigArrays.copyFromBig(this.this$0.array, this.pos + (long)IntArrayFrontCodedList.access$100(n), this.s, 0, n);
                    this.pos += (long)(n + IntArrayFrontCodedList.access$100(n));
                    this.inSync = true;
                } else if (this.inSync) {
                    n = IntArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    int n2 = IntArrayFrontCodedList.access$000(this.this$0.array, this.pos + (long)IntArrayFrontCodedList.access$100(n));
                    this.s = IntArrays.ensureCapacity(this.s, n + n2, n2);
                    IntBigArrays.copyFromBig(this.this$0.array, this.pos + (long)IntArrayFrontCodedList.access$100(n) + (long)IntArrayFrontCodedList.access$100(n2), this.s, n2, n);
                    this.pos += (long)(IntArrayFrontCodedList.access$100(n) + IntArrayFrontCodedList.access$100(n2) + n);
                    n += n2;
                } else {
                    n = IntArrayFrontCodedList.access$200(this.this$0, this.i);
                    this.s = IntArrays.ensureCapacity(this.s, n, 0);
                    IntArrayFrontCodedList.access$300(this.this$0, this.i, this.s, 0, n);
                }
                ++this.i;
                return IntArrays.copy(this.s, 0, n);
            }

            @Override
            public int[] previous() {
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

    public IntArrayFrontCodedList clone() {
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
            stringBuffer.append(IntArrayList.wrap(this.getArray(i)).toString());
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    protected long[] rebuildPointerArray() {
        long[] lArray = new long[(this.n + this.ratio - 1) / this.ratio];
        int[][] nArray = this.array;
        long l = 0L;
        int n = 0;
        int n2 = this.ratio - 1;
        for (int i = 0; i < this.n; ++i) {
            int n3 = IntArrayFrontCodedList.readInt(nArray, l);
            int n4 = IntArrayFrontCodedList.count(n3);
            if (++n2 == this.ratio) {
                n2 = 0;
                lArray[n++] = l;
                l += (long)(n4 + n3);
                continue;
            }
            l += (long)(n4 + IntArrayFrontCodedList.count(IntArrayFrontCodedList.readInt(nArray, l + (long)n4)) + n3);
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

    static int access$000(int[][] nArray, long l) {
        return IntArrayFrontCodedList.readInt(nArray, l);
    }

    static int access$100(int n) {
        return IntArrayFrontCodedList.count(n);
    }

    static int access$200(IntArrayFrontCodedList intArrayFrontCodedList, int n) {
        return intArrayFrontCodedList.length(n);
    }

    static int access$300(IntArrayFrontCodedList intArrayFrontCodedList, int n, int[] nArray, int n2, int n3) {
        return intArrayFrontCodedList.extract(n, nArray, n2, n3);
    }
}

