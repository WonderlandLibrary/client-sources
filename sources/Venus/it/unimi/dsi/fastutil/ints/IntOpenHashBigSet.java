/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntBigArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntIterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntOpenHashBigSet
extends AbstractIntSet
implements Serializable,
Cloneable,
Hash,
Size64 {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[][] key;
    protected transient long mask;
    protected transient int segmentMask;
    protected transient int baseMask;
    protected transient boolean containsNull;
    protected transient long n;
    protected transient long maxFill;
    protected final transient long minN;
    protected final float f;
    protected long size;

    private void initMasks() {
        this.mask = this.n - 1L;
        this.segmentMask = this.key[0].length - 1;
        this.baseMask = this.key.length - 1;
    }

    public IntOpenHashBigSet(long l, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (this.n < 0L) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.bigArraySize(l, f);
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = IntBigArrays.newBigArray(this.n);
        this.initMasks();
    }

    public IntOpenHashBigSet(long l) {
        this(l, 0.75f);
    }

    public IntOpenHashBigSet() {
        this(16L, 0.75f);
    }

    public IntOpenHashBigSet(Collection<? extends Integer> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public IntOpenHashBigSet(Collection<? extends Integer> collection) {
        this(collection, 0.75f);
    }

    public IntOpenHashBigSet(IntCollection intCollection, float f) {
        this(intCollection.size(), f);
        this.addAll(intCollection);
    }

    public IntOpenHashBigSet(IntCollection intCollection) {
        this(intCollection, 0.75f);
    }

    public IntOpenHashBigSet(IntIterator intIterator, float f) {
        this(16L, f);
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }

    public IntOpenHashBigSet(IntIterator intIterator) {
        this(intIterator, 0.75f);
    }

    public IntOpenHashBigSet(Iterator<?> iterator2, float f) {
        this(IntIterators.asIntIterator(iterator2), f);
    }

    public IntOpenHashBigSet(Iterator<?> iterator2) {
        this(IntIterators.asIntIterator(iterator2));
    }

    public IntOpenHashBigSet(int[] nArray, int n, int n2, float f) {
        this(n2 < 0 ? 0L : (long)n2, f);
        IntArrays.ensureOffsetLength(nArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(nArray[n + i]);
        }
    }

    public IntOpenHashBigSet(int[] nArray, int n, int n2) {
        this(nArray, n, n2, 0.75f);
    }

    public IntOpenHashBigSet(int[] nArray, float f) {
        this(nArray, 0, nArray.length, f);
    }

    public IntOpenHashBigSet(int[] nArray) {
        this(nArray, 0.75f);
    }

    private long realSize() {
        return this.containsNull ? this.size - 1L : this.size;
    }

    private void ensureCapacity(long l) {
        long l2 = HashCommon.bigArraySize(l, this.f);
        if (l2 > this.n) {
            this.rehash(l2);
        }
    }

    @Override
    public boolean addAll(Collection<? extends Integer> collection) {
        long l;
        long l2 = l = collection instanceof Size64 ? ((Size64)((Object)collection)).size64() : (long)collection.size();
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(l);
        } else {
            this.ensureCapacity(this.size64() + l);
        }
        return super.addAll(collection);
    }

    @Override
    public boolean addAll(IntCollection intCollection) {
        long l;
        long l2 = l = intCollection instanceof Size64 ? ((Size64)((Object)intCollection)).size64() : (long)intCollection.size();
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(l);
        } else {
            this.ensureCapacity(this.size64() + l);
        }
        return super.addAll(intCollection);
    }

    @Override
    public boolean add(int n) {
        if (n == 0) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            int n2;
            int[][] nArray = this.key;
            long l = HashCommon.mix((long)n);
            int n3 = (int)((l & this.mask) >>> 27);
            int n4 = nArray[n3][n2 = (int)(l & (long)this.segmentMask)];
            if (n4 != 0) {
                if (n4 == n) {
                    return true;
                }
                while ((n4 = nArray[n3 = n3 + ((n2 = n2 + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n2]) != 0) {
                    if (n4 != n) continue;
                    return true;
                }
            }
            nArray[n3][n2] = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(2L * this.n);
        }
        return false;
    }

    protected final void shiftKeys(long l) {
        int[][] nArray = this.key;
        while (true) {
            long l2 = l;
            l = l2 + 1L & this.mask;
            while (true) {
                if (IntBigArrays.get(nArray, l) == 0) {
                    IntBigArrays.set(nArray, l2, 0);
                    return;
                }
                long l3 = HashCommon.mix((long)IntBigArrays.get(nArray, l)) & this.mask;
                if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                l = l + 1L & this.mask;
            }
            IntBigArrays.set(nArray, l2, IntBigArrays.get(nArray, l));
        }
    }

    private boolean removeEntry(int n, int n2) {
        --this.size;
        this.shiftKeys((long)n * 0x8000000L + (long)n2);
        if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) {
            this.rehash(this.n / 2L);
        }
        return false;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) {
            this.rehash(this.n / 2L);
        }
        return false;
    }

    @Override
    public boolean remove(int n) {
        int n2;
        if (n == 0) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        int[][] nArray = this.key;
        long l = HashCommon.mix((long)n);
        int n3 = (int)((l & this.mask) >>> 27);
        int n4 = nArray[n3][n2 = (int)(l & (long)this.segmentMask)];
        if (n4 == 0) {
            return true;
        }
        if (n4 == n) {
            return this.removeEntry(n3, n2);
        }
        do {
            if ((n4 = nArray[n3 = n3 + ((n2 = n2 + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n2]) != 0) continue;
            return true;
        } while (n4 != n);
        return this.removeEntry(n3, n2);
    }

    @Override
    public boolean contains(int n) {
        int n2;
        if (n == 0) {
            return this.containsNull;
        }
        int[][] nArray = this.key;
        long l = HashCommon.mix((long)n);
        int n3 = (int)((l & this.mask) >>> 27);
        int n4 = nArray[n3][n2 = (int)(l & (long)this.segmentMask)];
        if (n4 == 0) {
            return true;
        }
        if (n4 == n) {
            return false;
        }
        do {
            if ((n4 = nArray[n3 = n3 + ((n2 = n2 + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n2]) != 0) continue;
            return true;
        } while (n4 != n);
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0L) {
            return;
        }
        this.size = 0L;
        this.containsNull = false;
        IntBigArrays.fill(this.key, 0);
    }

    @Override
    public IntIterator iterator() {
        return new SetIterator(this, null);
    }

    public boolean trim() {
        long l = HashCommon.bigArraySize(this.size, this.f);
        if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) {
            return false;
        }
        try {
            this.rehash(l);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    public boolean trim(long l) {
        long l2 = HashCommon.bigArraySize(l, this.f);
        if (this.n <= l2) {
            return false;
        }
        try {
            this.rehash(l2);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    protected void rehash(long l) {
        int[][] nArray = this.key;
        int[][] nArray2 = IntBigArrays.newBigArray(l);
        long l2 = l - 1L;
        int n = nArray2[0].length - 1;
        int n2 = nArray2.length - 1;
        int n3 = 0;
        int n4 = 0;
        long l3 = this.realSize();
        while (l3-- != 0L) {
            int n5;
            while (nArray[n3][n4] == 0) {
                n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            int n6 = nArray[n3][n4];
            long l4 = HashCommon.mix((long)n6);
            int n7 = (int)((l4 & l2) >>> 27);
            if (nArray2[n7][n5 = (int)(l4 & (long)n)] != 0) {
                while (nArray2[n7 = n7 + ((n5 = n5 + 1 & n) == 0 ? 1 : 0) & n2][n5] != 0) {
                }
            }
            nArray2[n7][n5] = n6;
            n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        this.n = l;
        this.key = nArray2;
        this.initMasks();
        this.maxFill = HashCommon.maxFill(this.n, this.f);
    }

    @Override
    @Deprecated
    public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size);
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0L;
    }

    public IntOpenHashBigSet clone() {
        IntOpenHashBigSet intOpenHashBigSet;
        try {
            intOpenHashBigSet = (IntOpenHashBigSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        intOpenHashBigSet.key = IntBigArrays.copy(this.key);
        intOpenHashBigSet.containsNull = this.containsNull;
        return intOpenHashBigSet;
    }

    @Override
    public int hashCode() {
        int[][] nArray = this.key;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l = this.realSize();
        while (l-- != 0L) {
            while (nArray[n2][n3] == 0) {
                n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            n += nArray[n2][n3];
            n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        IntIterator intIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        long l = this.size;
        while (l-- != 0L) {
            objectOutputStream.writeInt(intIterator.nextInt());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.bigArraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = IntBigArrays.newBigArray(this.n);
        int[][] nArray = this.key;
        this.initMasks();
        long l = this.size;
        while (l-- != 0L) {
            int n;
            int n2 = objectInputStream.readInt();
            if (n2 == 0) {
                this.containsNull = true;
                continue;
            }
            long l2 = HashCommon.mix((long)n2);
            int n3 = (int)((l2 & this.mask) >>> 27);
            if (nArray[n3][n = (int)(l2 & (long)this.segmentMask)] != 0) {
                while (nArray[n3 = n3 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n] != 0) {
                }
            }
            nArray[n3][n] = n2;
        }
    }

    private void checkTable() {
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private class SetIterator
    implements IntIterator {
        int base;
        int displ;
        long last;
        long c;
        boolean mustReturnNull;
        IntArrayList wrapped;
        final IntOpenHashBigSet this$0;

        private SetIterator(IntOpenHashBigSet intOpenHashBigSet) {
            this.this$0 = intOpenHashBigSet;
            this.base = this.this$0.key.length;
            this.last = -1L;
            this.c = this.this$0.size;
            this.mustReturnNull = this.this$0.containsNull;
        }

        @Override
        public boolean hasNext() {
            return this.c != 0L;
        }

        @Override
        public int nextInt() {
            int n;
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return 1;
            }
            int[][] nArray = this.this$0.key;
            do {
                if (this.displ == 0 && this.base <= 0) {
                    this.last = Long.MIN_VALUE;
                    return this.wrapped.getInt(-(--this.base) - 1);
                }
                if (this.displ-- != 0) continue;
                this.displ = nArray[--this.base].length - 1;
            } while ((n = nArray[this.base][this.displ]) == 0);
            this.last = (long)this.base * 0x8000000L + (long)this.displ;
            return n;
        }

        private final void shiftKeys(long l) {
            int[][] nArray = this.this$0.key;
            while (true) {
                int n;
                long l2 = l;
                l = l2 + 1L & this.this$0.mask;
                while (true) {
                    if ((n = IntBigArrays.get(nArray, l)) == 0) {
                        IntBigArrays.set(nArray, l2, 0);
                        return;
                    }
                    long l3 = HashCommon.mix((long)n) & this.this$0.mask;
                    if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                    l = l + 1L & this.this$0.mask;
                }
                if (l < l2) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList();
                    }
                    this.wrapped.add(IntBigArrays.get(nArray, l));
                }
                IntBigArrays.set(nArray, l2, n);
            }
        }

        @Override
        public void remove() {
            if (this.last == -1L) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
            } else if (this.base >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.getInt(-this.base - 1));
                this.last = -1L;
                return;
            }
            --this.this$0.size;
            this.last = -1L;
        }

        SetIterator(IntOpenHashBigSet intOpenHashBigSet, 1 var2_2) {
            this(intOpenHashBigSet);
        }
    }
}

