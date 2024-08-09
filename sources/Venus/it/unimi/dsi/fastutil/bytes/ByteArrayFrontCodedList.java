/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
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
public class ByteArrayFrontCodedList
extends AbstractObjectList<byte[]>
implements Serializable,
Cloneable,
RandomAccess {
    private static final long serialVersionUID = 1L;
    protected final int n;
    protected final int ratio;
    protected final byte[][] array;
    protected transient long[] p;

    public ByteArrayFrontCodedList(Iterator<byte[]> iterator2, int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Illegal ratio (" + n + ")");
        }
        byte[][] byArray = ByteBigArrays.EMPTY_BIG_ARRAY;
        long[] lArray = LongArrays.EMPTY_ARRAY;
        byte[][] byArrayArray = new byte[2][];
        long l = 0L;
        int n2 = 0;
        int n3 = 0;
        while (iterator2.hasNext()) {
            byArrayArray[n3] = iterator2.next();
            int n4 = byArrayArray[n3].length;
            if (n2 % n == 0) {
                lArray = LongArrays.grow(lArray, n2 / n + 1);
                lArray[n2 / n] = l;
                byArray = ByteBigArrays.grow(byArray, l + (long)ByteArrayFrontCodedList.count(n4) + (long)n4, l);
                l += (long)ByteArrayFrontCodedList.writeInt(byArray, n4, l);
                ByteBigArrays.copyToBig(byArrayArray[n3], 0, byArray, l, n4);
                l += (long)n4;
            } else {
                int n5;
                int n6 = byArrayArray[1 - n3].length;
                if (n4 < n6) {
                    n6 = n4;
                }
                for (n5 = 0; n5 < n6 && byArrayArray[0][n5] == byArrayArray[5][n5]; ++n5) {
                }
                byArray = ByteBigArrays.grow(byArray, l + (long)ByteArrayFrontCodedList.count(n4 -= n5) + (long)ByteArrayFrontCodedList.count(n5) + (long)n4, l);
                l += (long)ByteArrayFrontCodedList.writeInt(byArray, n4, l);
                l += (long)ByteArrayFrontCodedList.writeInt(byArray, n5, l);
                ByteBigArrays.copyToBig(byArrayArray[n3], n5, byArray, l, n4);
                l += (long)n4;
            }
            n3 = 1 - n3;
            ++n2;
        }
        this.n = n2;
        this.ratio = n;
        this.array = ByteBigArrays.trim(byArray, l);
        this.p = LongArrays.trim(lArray, (n2 + n - 1) / n);
    }

    public ByteArrayFrontCodedList(Collection<byte[]> collection, int n) {
        this(collection.iterator(), n);
    }

    private static int readInt(byte[][] byArray, long l) {
        byte by = ByteBigArrays.get(byArray, l);
        if (by >= 0) {
            return by;
        }
        byte by2 = ByteBigArrays.get(byArray, l + 1L);
        if (by2 >= 0) {
            return -by - 1 << 7 | by2;
        }
        byte by3 = ByteBigArrays.get(byArray, l + 2L);
        if (by3 >= 0) {
            return -by - 1 << 14 | -by2 - 1 << 7 | by3;
        }
        byte by4 = ByteBigArrays.get(byArray, l + 3L);
        if (by4 >= 0) {
            return -by - 1 << 21 | -by2 - 1 << 14 | -by3 - 1 << 7 | by4;
        }
        return -by - 1 << 28 | -by2 - 1 << 21 | -by3 - 1 << 14 | -by4 - 1 << 7 | ByteBigArrays.get(byArray, l + 4L);
    }

    private static int count(int n) {
        if (n < 128) {
            return 0;
        }
        if (n < 16384) {
            return 1;
        }
        if (n < 0x200000) {
            return 0;
        }
        if (n < 0x10000000) {
            return 1;
        }
        return 0;
    }

    private static int writeInt(byte[][] byArray, int n, long l) {
        int n2 = ByteArrayFrontCodedList.count(n);
        ByteBigArrays.set(byArray, l + (long)n2 - 1L, (byte)(n & 0x7F));
        if (n2 != 1) {
            int n3 = n2 - 1;
            while (n3-- != 0) {
                ByteBigArrays.set(byArray, l + (long)n3, (byte)(-((n >>>= 7) & 0x7F) - 1));
            }
        }
        return n2;
    }

    public int ratio() {
        return this.ratio;
    }

    private int length(int n) {
        byte[][] byArray = this.array;
        int n2 = n % this.ratio;
        long l = this.p[n / this.ratio];
        int n3 = ByteArrayFrontCodedList.readInt(byArray, l);
        if (n2 == 0) {
            return n3;
        }
        l += (long)(ByteArrayFrontCodedList.count(n3) + n3);
        n3 = ByteArrayFrontCodedList.readInt(byArray, l);
        int n4 = ByteArrayFrontCodedList.readInt(byArray, l + (long)ByteArrayFrontCodedList.count(n3));
        for (int i = 0; i < n2 - 1; ++i) {
            n3 = ByteArrayFrontCodedList.readInt(byArray, l += (long)(ByteArrayFrontCodedList.count(n3) + ByteArrayFrontCodedList.count(n4) + n3));
            n4 = ByteArrayFrontCodedList.readInt(byArray, l + (long)ByteArrayFrontCodedList.count(n3));
        }
        return n3 + n4;
    }

    public int arrayLength(int n) {
        this.ensureRestrictedIndex(n);
        return this.length(n);
    }

    private int extract(int n, byte[] byArray, int n2, int n3) {
        long l;
        int n4 = n % this.ratio;
        long l2 = l = this.p[n / this.ratio];
        int n5 = ByteArrayFrontCodedList.readInt(this.array, l2);
        int n6 = 0;
        if (n4 == 0) {
            l2 = this.p[n / this.ratio] + (long)ByteArrayFrontCodedList.count(n5);
            ByteBigArrays.copyFromBig(this.array, l2, byArray, n2, Math.min(n3, n5));
            return n5;
        }
        int n7 = 0;
        for (int i = 0; i < n4; ++i) {
            long l3 = l2 + (long)ByteArrayFrontCodedList.count(n5) + (long)(i != 0 ? ByteArrayFrontCodedList.count(n7) : 0);
            n7 = ByteArrayFrontCodedList.readInt(this.array, (l2 = l3 + (long)n5) + (long)ByteArrayFrontCodedList.count(n5 = ByteArrayFrontCodedList.readInt(this.array, l2)));
            int n8 = Math.min(n7, n3);
            if (n8 <= n6) {
                n6 = n8;
                continue;
            }
            ByteBigArrays.copyFromBig(this.array, l3, byArray, n6 + n2, n8 - n6);
            n6 = n8;
        }
        if (n6 < n3) {
            ByteBigArrays.copyFromBig(this.array, l2 + (long)ByteArrayFrontCodedList.count(n5) + (long)ByteArrayFrontCodedList.count(n7), byArray, n6 + n2, Math.min(n5, n3 - n6));
        }
        return n5 + n7;
    }

    @Override
    public byte[] get(int n) {
        return this.getArray(n);
    }

    public byte[] getArray(int n) {
        this.ensureRestrictedIndex(n);
        int n2 = this.length(n);
        byte[] byArray = new byte[n2];
        this.extract(n, byArray, 0, n2);
        return byArray;
    }

    public int get(int n, byte[] byArray, int n2, int n3) {
        this.ensureRestrictedIndex(n);
        ByteArrays.ensureOffsetLength(byArray, n2, n3);
        int n4 = this.extract(n, byArray, n2, n3);
        if (n3 >= n4) {
            return n4;
        }
        return n3 - n4;
    }

    public int get(int n, byte[] byArray) {
        return this.get(n, byArray, 0, byArray.length);
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public ObjectListIterator<byte[]> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<byte[]>(this, n){
            byte[] s;
            int i;
            long pos;
            boolean inSync;
            final int val$start;
            final ByteArrayFrontCodedList this$0;
            {
                this.this$0 = byteArrayFrontCodedList;
                this.val$start = n;
                this.s = ByteArrays.EMPTY_ARRAY;
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
            public byte[] next() {
                int n;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (this.i % this.this$0.ratio == 0) {
                    this.pos = this.this$0.p[this.i / this.this$0.ratio];
                    n = ByteArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    this.s = ByteArrays.ensureCapacity(this.s, n, 0);
                    ByteBigArrays.copyFromBig(this.this$0.array, this.pos + (long)ByteArrayFrontCodedList.access$100(n), this.s, 0, n);
                    this.pos += (long)(n + ByteArrayFrontCodedList.access$100(n));
                    this.inSync = true;
                } else if (this.inSync) {
                    n = ByteArrayFrontCodedList.access$000(this.this$0.array, this.pos);
                    int n2 = ByteArrayFrontCodedList.access$000(this.this$0.array, this.pos + (long)ByteArrayFrontCodedList.access$100(n));
                    this.s = ByteArrays.ensureCapacity(this.s, n + n2, n2);
                    ByteBigArrays.copyFromBig(this.this$0.array, this.pos + (long)ByteArrayFrontCodedList.access$100(n) + (long)ByteArrayFrontCodedList.access$100(n2), this.s, n2, n);
                    this.pos += (long)(ByteArrayFrontCodedList.access$100(n) + ByteArrayFrontCodedList.access$100(n2) + n);
                    n += n2;
                } else {
                    n = ByteArrayFrontCodedList.access$200(this.this$0, this.i);
                    this.s = ByteArrays.ensureCapacity(this.s, n, 0);
                    ByteArrayFrontCodedList.access$300(this.this$0, this.i, this.s, 0, n);
                }
                ++this.i;
                return ByteArrays.copy(this.s, 0, n);
            }

            @Override
            public byte[] previous() {
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

    public ByteArrayFrontCodedList clone() {
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
            stringBuffer.append(ByteArrayList.wrap(this.getArray(i)).toString());
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    protected long[] rebuildPointerArray() {
        long[] lArray = new long[(this.n + this.ratio - 1) / this.ratio];
        byte[][] byArray = this.array;
        long l = 0L;
        int n = 0;
        int n2 = this.ratio - 1;
        for (int i = 0; i < this.n; ++i) {
            int n3 = ByteArrayFrontCodedList.readInt(byArray, l);
            int n4 = ByteArrayFrontCodedList.count(n3);
            if (++n2 == this.ratio) {
                n2 = 0;
                lArray[n++] = l;
                l += (long)(n4 + n3);
                continue;
            }
            l += (long)(n4 + ByteArrayFrontCodedList.count(ByteArrayFrontCodedList.readInt(byArray, l + (long)n4)) + n3);
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

    static int access$000(byte[][] byArray, long l) {
        return ByteArrayFrontCodedList.readInt(byArray, l);
    }

    static int access$100(int n) {
        return ByteArrayFrontCodedList.count(n);
    }

    static int access$200(ByteArrayFrontCodedList byteArrayFrontCodedList, int n) {
        return byteArrayFrontCodedList.length(n);
    }

    static int access$300(ByteArrayFrontCodedList byteArrayFrontCodedList, int n, byte[] byArray, int n2, int n3) {
        return byteArrayFrontCodedList.extract(n, byArray, n2, n3);
    }
}

