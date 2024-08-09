/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
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
public class DoubleOpenHashBigSet
extends AbstractDoubleSet
implements Serializable,
Cloneable,
Hash,
Size64 {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient double[][] key;
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

    public DoubleOpenHashBigSet(long l, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (this.n < 0L) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.bigArraySize(l, f);
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = DoubleBigArrays.newBigArray(this.n);
        this.initMasks();
    }

    public DoubleOpenHashBigSet(long l) {
        this(l, 0.75f);
    }

    public DoubleOpenHashBigSet() {
        this(16L, 0.75f);
    }

    public DoubleOpenHashBigSet(Collection<? extends Double> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public DoubleOpenHashBigSet(Collection<? extends Double> collection) {
        this(collection, 0.75f);
    }

    public DoubleOpenHashBigSet(DoubleCollection doubleCollection, float f) {
        this(doubleCollection.size(), f);
        this.addAll(doubleCollection);
    }

    public DoubleOpenHashBigSet(DoubleCollection doubleCollection) {
        this(doubleCollection, 0.75f);
    }

    public DoubleOpenHashBigSet(DoubleIterator doubleIterator, float f) {
        this(16L, f);
        while (doubleIterator.hasNext()) {
            this.add(doubleIterator.nextDouble());
        }
    }

    public DoubleOpenHashBigSet(DoubleIterator doubleIterator) {
        this(doubleIterator, 0.75f);
    }

    public DoubleOpenHashBigSet(Iterator<?> iterator2, float f) {
        this(DoubleIterators.asDoubleIterator(iterator2), f);
    }

    public DoubleOpenHashBigSet(Iterator<?> iterator2) {
        this(DoubleIterators.asDoubleIterator(iterator2));
    }

    public DoubleOpenHashBigSet(double[] dArray, int n, int n2, float f) {
        this(n2 < 0 ? 0L : (long)n2, f);
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(dArray[n + i]);
        }
    }

    public DoubleOpenHashBigSet(double[] dArray, int n, int n2) {
        this(dArray, n, n2, 0.75f);
    }

    public DoubleOpenHashBigSet(double[] dArray, float f) {
        this(dArray, 0, dArray.length, f);
    }

    public DoubleOpenHashBigSet(double[] dArray) {
        this(dArray, 0.75f);
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
    public boolean addAll(Collection<? extends Double> collection) {
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
    public boolean addAll(DoubleCollection doubleCollection) {
        long l;
        long l2 = l = doubleCollection instanceof Size64 ? ((Size64)((Object)doubleCollection)).size64() : (long)doubleCollection.size();
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(l);
        } else {
            this.ensureCapacity(this.size64() + l);
        }
        return super.addAll(doubleCollection);
    }

    @Override
    public boolean add(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            int n;
            double[][] dArray = this.key;
            long l = HashCommon.mix(Double.doubleToRawLongBits(d));
            int n2 = (int)((l & this.mask) >>> 27);
            double d2 = dArray[n2][n = (int)(l & (long)this.segmentMask)];
            if (Double.doubleToLongBits(d2) != 0L) {
                if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(d)) {
                    return true;
                }
                while (Double.doubleToLongBits(d2 = dArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0L) {
                    if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d)) continue;
                    return true;
                }
            }
            dArray[n2][n] = d;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(2L * this.n);
        }
        return false;
    }

    protected final void shiftKeys(long l) {
        double[][] dArray = this.key;
        while (true) {
            long l2 = l;
            l = l2 + 1L & this.mask;
            while (true) {
                if (Double.doubleToLongBits(DoubleBigArrays.get(dArray, l)) == 0L) {
                    DoubleBigArrays.set(dArray, l2, 0.0);
                    return;
                }
                long l3 = HashCommon.mix(Double.doubleToRawLongBits(DoubleBigArrays.get(dArray, l))) & this.mask;
                if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                l = l + 1L & this.mask;
            }
            DoubleBigArrays.set(dArray, l2, DoubleBigArrays.get(dArray, l));
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
    public boolean remove(double d) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        double[][] dArray = this.key;
        long l = HashCommon.mix(Double.doubleToRawLongBits(d));
        int n2 = (int)((l & this.mask) >>> 27);
        double d2 = dArray[n2][n = (int)(l & (long)this.segmentMask)];
        if (Double.doubleToLongBits(d2) == 0L) {
            return true;
        }
        if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(d)) {
            return this.removeEntry(n2, n);
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0L) continue;
            return true;
        } while (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d));
        return this.removeEntry(n2, n);
    }

    @Override
    public boolean contains(double d) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNull;
        }
        double[][] dArray = this.key;
        long l = HashCommon.mix(Double.doubleToRawLongBits(d));
        int n2 = (int)((l & this.mask) >>> 27);
        double d2 = dArray[n2][n = (int)(l & (long)this.segmentMask)];
        if (Double.doubleToLongBits(d2) == 0L) {
            return true;
        }
        if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(d)) {
            return false;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0L) continue;
            return true;
        } while (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d));
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0L) {
            return;
        }
        this.size = 0L;
        this.containsNull = false;
        DoubleBigArrays.fill(this.key, 0.0);
    }

    @Override
    public DoubleIterator iterator() {
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
        double[][] dArray = this.key;
        double[][] dArray2 = DoubleBigArrays.newBigArray(l);
        long l2 = l - 1L;
        int n = dArray2[0].length - 1;
        int n2 = dArray2.length - 1;
        int n3 = 0;
        int n4 = 0;
        long l3 = this.realSize();
        while (l3-- != 0L) {
            int n5;
            while (Double.doubleToLongBits(dArray[n3][n4]) == 0L) {
                n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            double d = dArray[n3][n4];
            long l4 = HashCommon.mix(Double.doubleToRawLongBits(d));
            int n6 = (int)((l4 & l2) >>> 27);
            if (Double.doubleToLongBits(dArray2[n6][n5 = (int)(l4 & (long)n)]) != 0L) {
                while (Double.doubleToLongBits(dArray2[n6 = n6 + ((n5 = n5 + 1 & n) == 0 ? 1 : 0) & n2][n5]) != 0L) {
                }
            }
            dArray2[n6][n5] = d;
            n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        this.n = l;
        this.key = dArray2;
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

    public DoubleOpenHashBigSet clone() {
        DoubleOpenHashBigSet doubleOpenHashBigSet;
        try {
            doubleOpenHashBigSet = (DoubleOpenHashBigSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        doubleOpenHashBigSet.key = DoubleBigArrays.copy(this.key);
        doubleOpenHashBigSet.containsNull = this.containsNull;
        return doubleOpenHashBigSet;
    }

    @Override
    public int hashCode() {
        double[][] dArray = this.key;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l = this.realSize();
        while (l-- != 0L) {
            while (Double.doubleToLongBits(dArray[n2][n3]) == 0L) {
                n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            n += HashCommon.double2int(dArray[n2][n3]);
            n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        DoubleIterator doubleIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        long l = this.size;
        while (l-- != 0L) {
            objectOutputStream.writeDouble(doubleIterator.nextDouble());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.bigArraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = DoubleBigArrays.newBigArray(this.n);
        double[][] dArray = this.key;
        this.initMasks();
        long l = this.size;
        while (l-- != 0L) {
            int n;
            double d = objectInputStream.readDouble();
            if (Double.doubleToLongBits(d) == 0L) {
                this.containsNull = true;
                continue;
            }
            long l2 = HashCommon.mix(Double.doubleToRawLongBits(d));
            int n2 = (int)((l2 & this.mask) >>> 27);
            if (Double.doubleToLongBits(dArray[n2][n = (int)(l2 & (long)this.segmentMask)]) != 0L) {
                while (Double.doubleToLongBits(dArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0L) {
                }
            }
            dArray[n2][n] = d;
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
    implements DoubleIterator {
        int base;
        int displ;
        long last;
        long c;
        boolean mustReturnNull;
        DoubleArrayList wrapped;
        final DoubleOpenHashBigSet this$0;

        private SetIterator(DoubleOpenHashBigSet doubleOpenHashBigSet) {
            this.this$0 = doubleOpenHashBigSet;
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
        public double nextDouble() {
            double d;
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return 0.0;
            }
            double[][] dArray = this.this$0.key;
            do {
                if (this.displ == 0 && this.base <= 0) {
                    this.last = Long.MIN_VALUE;
                    return this.wrapped.getDouble(-(--this.base) - 1);
                }
                if (this.displ-- != 0) continue;
                this.displ = dArray[--this.base].length - 1;
            } while (Double.doubleToLongBits(d = dArray[this.base][this.displ]) == 0L);
            this.last = (long)this.base * 0x8000000L + (long)this.displ;
            return d;
        }

        private final void shiftKeys(long l) {
            double[][] dArray = this.this$0.key;
            while (true) {
                double d;
                long l2 = l;
                l = l2 + 1L & this.this$0.mask;
                while (true) {
                    if (Double.doubleToLongBits(d = DoubleBigArrays.get(dArray, l)) == 0L) {
                        DoubleBigArrays.set(dArray, l2, 0.0);
                        return;
                    }
                    long l3 = HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
                    if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                    l = l + 1L & this.this$0.mask;
                }
                if (l < l2) {
                    if (this.wrapped == null) {
                        this.wrapped = new DoubleArrayList();
                    }
                    this.wrapped.add(DoubleBigArrays.get(dArray, l));
                }
                DoubleBigArrays.set(dArray, l2, d);
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
                this.this$0.remove(this.wrapped.getDouble(-this.base - 1));
                this.last = -1L;
                return;
            }
            --this.this$0.size;
            this.last = -1L;
        }

        SetIterator(DoubleOpenHashBigSet doubleOpenHashBigSet, 1 var2_2) {
            this(doubleOpenHashBigSet);
        }
    }
}

