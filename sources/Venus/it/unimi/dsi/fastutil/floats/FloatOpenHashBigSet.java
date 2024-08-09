/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatBigArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
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
public class FloatOpenHashBigSet
extends AbstractFloatSet
implements Serializable,
Cloneable,
Hash,
Size64 {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[][] key;
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

    public FloatOpenHashBigSet(long l, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (this.n < 0L) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.bigArraySize(l, f);
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = FloatBigArrays.newBigArray(this.n);
        this.initMasks();
    }

    public FloatOpenHashBigSet(long l) {
        this(l, 0.75f);
    }

    public FloatOpenHashBigSet() {
        this(16L, 0.75f);
    }

    public FloatOpenHashBigSet(Collection<? extends Float> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public FloatOpenHashBigSet(Collection<? extends Float> collection) {
        this(collection, 0.75f);
    }

    public FloatOpenHashBigSet(FloatCollection floatCollection, float f) {
        this(floatCollection.size(), f);
        this.addAll(floatCollection);
    }

    public FloatOpenHashBigSet(FloatCollection floatCollection) {
        this(floatCollection, 0.75f);
    }

    public FloatOpenHashBigSet(FloatIterator floatIterator, float f) {
        this(16L, f);
        while (floatIterator.hasNext()) {
            this.add(floatIterator.nextFloat());
        }
    }

    public FloatOpenHashBigSet(FloatIterator floatIterator) {
        this(floatIterator, 0.75f);
    }

    public FloatOpenHashBigSet(Iterator<?> iterator2, float f) {
        this(FloatIterators.asFloatIterator(iterator2), f);
    }

    public FloatOpenHashBigSet(Iterator<?> iterator2) {
        this(FloatIterators.asFloatIterator(iterator2));
    }

    public FloatOpenHashBigSet(float[] fArray, int n, int n2, float f) {
        this(n2 < 0 ? 0L : (long)n2, f);
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(fArray[n + i]);
        }
    }

    public FloatOpenHashBigSet(float[] fArray, int n, int n2) {
        this(fArray, n, n2, 0.75f);
    }

    public FloatOpenHashBigSet(float[] fArray, float f) {
        this(fArray, 0, fArray.length, f);
    }

    public FloatOpenHashBigSet(float[] fArray) {
        this(fArray, 0.75f);
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
    public boolean addAll(Collection<? extends Float> collection) {
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
    public boolean addAll(FloatCollection floatCollection) {
        long l;
        long l2 = l = floatCollection instanceof Size64 ? ((Size64)((Object)floatCollection)).size64() : (long)floatCollection.size();
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(l);
        } else {
            this.ensureCapacity(this.size64() + l);
        }
        return super.addAll(floatCollection);
    }

    @Override
    public boolean add(float f) {
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            int n;
            float[][] fArray = this.key;
            long l = HashCommon.mix((long)HashCommon.float2int(f));
            int n2 = (int)((l & this.mask) >>> 27);
            float f2 = fArray[n2][n = (int)(l & (long)this.segmentMask)];
            if (Float.floatToIntBits(f2) != 0) {
                if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
                    return true;
                }
                while (Float.floatToIntBits(f2 = fArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0) {
                    if (Float.floatToIntBits(f2) != Float.floatToIntBits(f)) continue;
                    return true;
                }
            }
            fArray[n2][n] = f;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(2L * this.n);
        }
        return false;
    }

    protected final void shiftKeys(long l) {
        float[][] fArray = this.key;
        while (true) {
            long l2 = l;
            l = l2 + 1L & this.mask;
            while (true) {
                if (Float.floatToIntBits(FloatBigArrays.get(fArray, l)) == 0) {
                    FloatBigArrays.set(fArray, l2, 0.0f);
                    return;
                }
                long l3 = HashCommon.mix((long)HashCommon.float2int(FloatBigArrays.get(fArray, l))) & this.mask;
                if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                l = l + 1L & this.mask;
            }
            FloatBigArrays.set(fArray, l2, FloatBigArrays.get(fArray, l));
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
    public boolean remove(float f) {
        int n;
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        float[][] fArray = this.key;
        long l = HashCommon.mix((long)HashCommon.float2int(f));
        int n2 = (int)((l & this.mask) >>> 27);
        float f2 = fArray[n2][n = (int)(l & (long)this.segmentMask)];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
            return this.removeEntry(n2, n);
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0) continue;
            return true;
        } while (Float.floatToIntBits(f2) != Float.floatToIntBits(f));
        return this.removeEntry(n2, n);
    }

    @Override
    public boolean contains(float f) {
        int n;
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNull;
        }
        float[][] fArray = this.key;
        long l = HashCommon.mix((long)HashCommon.float2int(f));
        int n2 = (int)((l & this.mask) >>> 27);
        float f2 = fArray[n2][n = (int)(l & (long)this.segmentMask)];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0) continue;
            return true;
        } while (Float.floatToIntBits(f2) != Float.floatToIntBits(f));
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0L) {
            return;
        }
        this.size = 0L;
        this.containsNull = false;
        FloatBigArrays.fill(this.key, 0.0f);
    }

    @Override
    public FloatIterator iterator() {
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
        float[][] fArray = this.key;
        float[][] fArray2 = FloatBigArrays.newBigArray(l);
        long l2 = l - 1L;
        int n = fArray2[0].length - 1;
        int n2 = fArray2.length - 1;
        int n3 = 0;
        int n4 = 0;
        long l3 = this.realSize();
        while (l3-- != 0L) {
            int n5;
            while (Float.floatToIntBits(fArray[n3][n4]) == 0) {
                n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            float f = fArray[n3][n4];
            long l4 = HashCommon.mix((long)HashCommon.float2int(f));
            int n6 = (int)((l4 & l2) >>> 27);
            if (Float.floatToIntBits(fArray2[n6][n5 = (int)(l4 & (long)n)]) != 0) {
                while (Float.floatToIntBits(fArray2[n6 = n6 + ((n5 = n5 + 1 & n) == 0 ? 1 : 0) & n2][n5]) != 0) {
                }
            }
            fArray2[n6][n5] = f;
            n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        this.n = l;
        this.key = fArray2;
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

    public FloatOpenHashBigSet clone() {
        FloatOpenHashBigSet floatOpenHashBigSet;
        try {
            floatOpenHashBigSet = (FloatOpenHashBigSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        floatOpenHashBigSet.key = FloatBigArrays.copy(this.key);
        floatOpenHashBigSet.containsNull = this.containsNull;
        return floatOpenHashBigSet;
    }

    @Override
    public int hashCode() {
        float[][] fArray = this.key;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l = this.realSize();
        while (l-- != 0L) {
            while (Float.floatToIntBits(fArray[n2][n3]) == 0) {
                n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            n += HashCommon.float2int(fArray[n2][n3]);
            n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        FloatIterator floatIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        long l = this.size;
        while (l-- != 0L) {
            objectOutputStream.writeFloat(floatIterator.nextFloat());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.bigArraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = FloatBigArrays.newBigArray(this.n);
        float[][] fArray = this.key;
        this.initMasks();
        long l = this.size;
        while (l-- != 0L) {
            int n;
            float f = objectInputStream.readFloat();
            if (Float.floatToIntBits(f) == 0) {
                this.containsNull = true;
                continue;
            }
            long l2 = HashCommon.mix((long)HashCommon.float2int(f));
            int n2 = (int)((l2 & this.mask) >>> 27);
            if (Float.floatToIntBits(fArray[n2][n = (int)(l2 & (long)this.segmentMask)]) != 0) {
                while (Float.floatToIntBits(fArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != 0) {
                }
            }
            fArray[n2][n] = f;
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
    implements FloatIterator {
        int base;
        int displ;
        long last;
        long c;
        boolean mustReturnNull;
        FloatArrayList wrapped;
        final FloatOpenHashBigSet this$0;

        private SetIterator(FloatOpenHashBigSet floatOpenHashBigSet) {
            this.this$0 = floatOpenHashBigSet;
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
        public float nextFloat() {
            float f;
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return 0.0f;
            }
            float[][] fArray = this.this$0.key;
            do {
                if (this.displ == 0 && this.base <= 0) {
                    this.last = Long.MIN_VALUE;
                    return this.wrapped.getFloat(-(--this.base) - 1);
                }
                if (this.displ-- != 0) continue;
                this.displ = fArray[--this.base].length - 1;
            } while (Float.floatToIntBits(f = fArray[this.base][this.displ]) == 0);
            this.last = (long)this.base * 0x8000000L + (long)this.displ;
            return f;
        }

        private final void shiftKeys(long l) {
            float[][] fArray = this.this$0.key;
            while (true) {
                float f;
                long l2 = l;
                l = l2 + 1L & this.this$0.mask;
                while (true) {
                    if (Float.floatToIntBits(f = FloatBigArrays.get(fArray, l)) == 0) {
                        FloatBigArrays.set(fArray, l2, 0.0f);
                        return;
                    }
                    long l3 = HashCommon.mix((long)HashCommon.float2int(f)) & this.this$0.mask;
                    if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                    l = l + 1L & this.this$0.mask;
                }
                if (l < l2) {
                    if (this.wrapped == null) {
                        this.wrapped = new FloatArrayList();
                    }
                    this.wrapped.add(FloatBigArrays.get(fArray, l));
                }
                FloatBigArrays.set(fArray, l2, f);
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
                this.this$0.remove(this.wrapped.getFloat(-this.base - 1));
                this.last = -1L;
                return;
            }
            --this.this$0.size;
            this.last = -1L;
        }

        SetIterator(FloatOpenHashBigSet floatOpenHashBigSet, 1 var2_2) {
            this(floatOpenHashBigSet);
        }
    }
}

