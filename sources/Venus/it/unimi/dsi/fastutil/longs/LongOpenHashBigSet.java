/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
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
public class LongOpenHashBigSet
extends AbstractLongSet
implements Serializable,
Cloneable,
Hash,
Size64 {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[][] key;
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

    public LongOpenHashBigSet(long l, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (this.n < 0L) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.bigArraySize(l, f);
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = LongBigArrays.newBigArray(this.n);
        this.initMasks();
    }

    public LongOpenHashBigSet(long l) {
        this(l, 0.75f);
    }

    public LongOpenHashBigSet() {
        this(16L, 0.75f);
    }

    public LongOpenHashBigSet(Collection<? extends Long> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public LongOpenHashBigSet(Collection<? extends Long> collection) {
        this(collection, 0.75f);
    }

    public LongOpenHashBigSet(LongCollection longCollection, float f) {
        this(longCollection.size(), f);
        this.addAll(longCollection);
    }

    public LongOpenHashBigSet(LongCollection longCollection) {
        this(longCollection, 0.75f);
    }

    public LongOpenHashBigSet(LongIterator longIterator, float f) {
        this(16L, f);
        while (longIterator.hasNext()) {
            this.add(longIterator.nextLong());
        }
    }

    public LongOpenHashBigSet(LongIterator longIterator) {
        this(longIterator, 0.75f);
    }

    public LongOpenHashBigSet(Iterator<?> iterator2, float f) {
        this(LongIterators.asLongIterator(iterator2), f);
    }

    public LongOpenHashBigSet(Iterator<?> iterator2) {
        this(LongIterators.asLongIterator(iterator2));
    }

    public LongOpenHashBigSet(long[] lArray, int n, int n2, float f) {
        this(n2 < 0 ? 0L : (long)n2, f);
        LongArrays.ensureOffsetLength(lArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(lArray[n + i]);
        }
    }

    public LongOpenHashBigSet(long[] lArray, int n, int n2) {
        this(lArray, n, n2, 0.75f);
    }

    public LongOpenHashBigSet(long[] lArray, float f) {
        this(lArray, 0, lArray.length, f);
    }

    public LongOpenHashBigSet(long[] lArray) {
        this(lArray, 0.75f);
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
    public boolean addAll(Collection<? extends Long> collection) {
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
    public boolean addAll(LongCollection longCollection) {
        long l;
        long l2 = l = longCollection instanceof Size64 ? ((Size64)((Object)longCollection)).size64() : (long)longCollection.size();
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(l);
        } else {
            this.ensureCapacity(this.size64() + l);
        }
        return super.addAll(longCollection);
    }

    @Override
    public boolean add(long l) {
        if (l == 0L) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            int n;
            long[][] lArray = this.key;
            long l2 = HashCommon.mix(l);
            int n2 = (int)((l2 & this.mask) >>> 27);
            long l3 = lArray[n2][n = (int)(l2 & (long)this.segmentMask)];
            if (l3 != 0L) {
                if (l3 == l) {
                    return true;
                }
                while ((l3 = lArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0L) {
                    if (l3 != l) continue;
                    return true;
                }
            }
            lArray[n2][n] = l;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(2L * this.n);
        }
        return false;
    }

    protected final void shiftKeys(long l) {
        long[][] lArray = this.key;
        while (true) {
            long l2 = l;
            l = l2 + 1L & this.mask;
            while (true) {
                if (LongBigArrays.get(lArray, l) == 0L) {
                    LongBigArrays.set(lArray, l2, 0L);
                    return;
                }
                long l3 = HashCommon.mix(LongBigArrays.get(lArray, l)) & this.mask;
                if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                l = l + 1L & this.mask;
            }
            LongBigArrays.set(lArray, l2, LongBigArrays.get(lArray, l));
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
    public boolean remove(long l) {
        int n;
        if (l == 0L) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        long[][] lArray = this.key;
        long l2 = HashCommon.mix(l);
        int n2 = (int)((l2 & this.mask) >>> 27);
        long l3 = lArray[n2][n = (int)(l2 & (long)this.segmentMask)];
        if (l3 == 0L) {
            return true;
        }
        if (l3 == l) {
            return this.removeEntry(n2, n);
        }
        do {
            if ((l3 = lArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0L) continue;
            return true;
        } while (l3 != l);
        return this.removeEntry(n2, n);
    }

    @Override
    public boolean contains(long l) {
        int n;
        if (l == 0L) {
            return this.containsNull;
        }
        long[][] lArray = this.key;
        long l2 = HashCommon.mix(l);
        int n2 = (int)((l2 & this.mask) >>> 27);
        long l3 = lArray[n2][n = (int)(l2 & (long)this.segmentMask)];
        if (l3 == 0L) {
            return true;
        }
        if (l3 == l) {
            return false;
        }
        do {
            if ((l3 = lArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0L) continue;
            return true;
        } while (l3 != l);
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0L) {
            return;
        }
        this.size = 0L;
        this.containsNull = false;
        LongBigArrays.fill(this.key, 0L);
    }

    @Override
    public LongIterator iterator() {
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
        long[][] lArray = this.key;
        long[][] lArray2 = LongBigArrays.newBigArray(l);
        long l2 = l - 1L;
        int n = lArray2[0].length - 1;
        int n2 = lArray2.length - 1;
        int n3 = 0;
        int n4 = 0;
        long l3 = this.realSize();
        while (l3-- != 0L) {
            int n5;
            while (lArray[n3][n4] == 0L) {
                n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            long l4 = lArray[n3][n4];
            long l5 = HashCommon.mix(l4);
            int n6 = (int)((l5 & l2) >>> 27);
            if (lArray2[n6][n5 = (int)(l5 & (long)n)] != 0L) {
                while (lArray2[n6 = n6 + ((n5 = n5 + 1 & n) == 0 ? 1 : 0) & n2][n5] != 0L) {
                }
            }
            lArray2[n6][n5] = l4;
            n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        this.n = l;
        this.key = lArray2;
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

    public LongOpenHashBigSet clone() {
        LongOpenHashBigSet longOpenHashBigSet;
        try {
            longOpenHashBigSet = (LongOpenHashBigSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        longOpenHashBigSet.key = LongBigArrays.copy(this.key);
        longOpenHashBigSet.containsNull = this.containsNull;
        return longOpenHashBigSet;
    }

    @Override
    public int hashCode() {
        long[][] lArray = this.key;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l = this.realSize();
        while (l-- != 0L) {
            while (lArray[n2][n3] == 0L) {
                n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            n += HashCommon.long2int(lArray[n2][n3]);
            n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        LongIterator longIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        long l = this.size;
        while (l-- != 0L) {
            objectOutputStream.writeLong(longIterator.nextLong());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.bigArraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = LongBigArrays.newBigArray(this.n);
        long[][] lArray = this.key;
        this.initMasks();
        long l = this.size;
        while (l-- != 0L) {
            int n;
            long l2 = objectInputStream.readLong();
            if (l2 == 0L) {
                this.containsNull = true;
                continue;
            }
            long l3 = HashCommon.mix(l2);
            int n2 = (int)((l3 & this.mask) >>> 27);
            if (lArray[n2][n = (int)(l3 & (long)this.segmentMask)] != 0L) {
                while (lArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n] != 0L) {
                }
            }
            lArray[n2][n] = l2;
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
    implements LongIterator {
        int base;
        int displ;
        long last;
        long c;
        boolean mustReturnNull;
        LongArrayList wrapped;
        final LongOpenHashBigSet this$0;

        private SetIterator(LongOpenHashBigSet longOpenHashBigSet) {
            this.this$0 = longOpenHashBigSet;
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
        public long nextLong() {
            long l;
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return 0L;
            }
            long[][] lArray = this.this$0.key;
            do {
                if (this.displ == 0 && this.base <= 0) {
                    this.last = Long.MIN_VALUE;
                    return this.wrapped.getLong(-(--this.base) - 1);
                }
                if (this.displ-- != 0) continue;
                this.displ = lArray[--this.base].length - 1;
            } while ((l = lArray[this.base][this.displ]) == 0L);
            this.last = (long)this.base * 0x8000000L + (long)this.displ;
            return l;
        }

        private final void shiftKeys(long l) {
            long[][] lArray = this.this$0.key;
            while (true) {
                long l2;
                long l3 = l;
                l = l3 + 1L & this.this$0.mask;
                while (true) {
                    if ((l2 = LongBigArrays.get(lArray, l)) == 0L) {
                        LongBigArrays.set(lArray, l3, 0L);
                        return;
                    }
                    long l4 = HashCommon.mix(l2) & this.this$0.mask;
                    if (l3 <= l ? l3 >= l4 || l4 > l : l3 >= l4 && l4 > l) break;
                    l = l + 1L & this.this$0.mask;
                }
                if (l < l3) {
                    if (this.wrapped == null) {
                        this.wrapped = new LongArrayList();
                    }
                    this.wrapped.add(LongBigArrays.get(lArray, l));
                }
                LongBigArrays.set(lArray, l3, l2);
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
                this.this$0.remove(this.wrapped.getLong(-this.base - 1));
                this.last = -1L;
                return;
            }
            --this.this$0.size;
            this.last = -1L;
        }

        SetIterator(LongOpenHashBigSet longOpenHashBigSet, 1 var2_2) {
            this(longOpenHashBigSet);
        }
    }
}

