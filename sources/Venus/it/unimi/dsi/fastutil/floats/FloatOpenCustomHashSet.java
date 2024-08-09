/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatHash;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatOpenCustomHashSet
extends AbstractFloatSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected FloatHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public FloatOpenCustomHashSet(int n, float f, FloatHash.Strategy strategy) {
        this.strategy = strategy;
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (n < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.arraySize(n, f);
        this.mask = this.n - 1;
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = new float[this.n + 1];
    }

    public FloatOpenCustomHashSet(int n, FloatHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public FloatOpenCustomHashSet(FloatHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public FloatOpenCustomHashSet(Collection<? extends Float> collection, float f, FloatHash.Strategy strategy) {
        this(collection.size(), f, strategy);
        this.addAll(collection);
    }

    public FloatOpenCustomHashSet(Collection<? extends Float> collection, FloatHash.Strategy strategy) {
        this(collection, 0.75f, strategy);
    }

    public FloatOpenCustomHashSet(FloatCollection floatCollection, float f, FloatHash.Strategy strategy) {
        this(floatCollection.size(), f, strategy);
        this.addAll(floatCollection);
    }

    public FloatOpenCustomHashSet(FloatCollection floatCollection, FloatHash.Strategy strategy) {
        this(floatCollection, 0.75f, strategy);
    }

    public FloatOpenCustomHashSet(FloatIterator floatIterator, float f, FloatHash.Strategy strategy) {
        this(16, f, strategy);
        while (floatIterator.hasNext()) {
            this.add(floatIterator.nextFloat());
        }
    }

    public FloatOpenCustomHashSet(FloatIterator floatIterator, FloatHash.Strategy strategy) {
        this(floatIterator, 0.75f, strategy);
    }

    public FloatOpenCustomHashSet(Iterator<?> iterator2, float f, FloatHash.Strategy strategy) {
        this(FloatIterators.asFloatIterator(iterator2), f, strategy);
    }

    public FloatOpenCustomHashSet(Iterator<?> iterator2, FloatHash.Strategy strategy) {
        this(FloatIterators.asFloatIterator(iterator2), strategy);
    }

    public FloatOpenCustomHashSet(float[] fArray, int n, int n2, float f, FloatHash.Strategy strategy) {
        this(n2 < 0 ? 0 : n2, f, strategy);
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(fArray[n + i]);
        }
    }

    public FloatOpenCustomHashSet(float[] fArray, int n, int n2, FloatHash.Strategy strategy) {
        this(fArray, n, n2, 0.75f, strategy);
    }

    public FloatOpenCustomHashSet(float[] fArray, float f, FloatHash.Strategy strategy) {
        this(fArray, 0, fArray.length, f, strategy);
    }

    public FloatOpenCustomHashSet(float[] fArray, FloatHash.Strategy strategy) {
        this(fArray, 0.75f, strategy);
    }

    public FloatHash.Strategy strategy() {
        return this.strategy;
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int n) {
        int n2 = HashCommon.arraySize(n, this.f);
        if (n2 > this.n) {
            this.rehash(n2);
        }
    }

    private void tryCapacity(long l) {
        int n = (int)Math.min(0x40000000L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)l / this.f))));
        if (n > this.n) {
            this.rehash(n);
        }
    }

    @Override
    public boolean addAll(FloatCollection floatCollection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(floatCollection.size());
        } else {
            this.tryCapacity(this.size() + floatCollection.size());
        }
        return super.addAll(floatCollection);
    }

    @Override
    public boolean addAll(Collection<? extends Float> collection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        } else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(float f) {
        if (this.strategy.equals(f, 0.0f)) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
            this.key[this.n] = f;
        } else {
            float[] fArray = this.key;
            int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) != 0) {
                if (this.strategy.equals(f2, f)) {
                    return true;
                }
                while (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(f2, f)) continue;
                    return true;
                }
            }
            fArray[n] = f;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return false;
    }

    protected final void shiftKeys(int n) {
        float[] fArray = this.key;
        while (true) {
            float f;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if (Float.floatToIntBits(f = fArray[n]) == 0) {
                    fArray[n2] = 0.0f;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            fArray[n2] = f;
        }
    }

    private boolean removeEntry(int n) {
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.n] = 0.0f;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    @Override
    public boolean remove(float f) {
        if (this.strategy.equals(f, 0.0f)) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (this.strategy.equals(f, f2)) {
            return this.removeEntry(n);
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(f, f2));
        return this.removeEntry(n);
    }

    @Override
    public boolean contains(float f) {
        if (this.strategy.equals(f, 0.0f)) {
            return this.containsNull;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (this.strategy.equals(f, f2)) {
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(f, f2));
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0.0f);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public FloatIterator iterator() {
        return new SetIterator(this, null);
    }

    public boolean trim() {
        int n = HashCommon.arraySize(this.size, this.f);
        if (n >= this.n || this.size > HashCommon.maxFill(n, this.f)) {
            return false;
        }
        try {
            this.rehash(n);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    public boolean trim(int n) {
        int n2 = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.f));
        if (n2 >= n || this.size > HashCommon.maxFill(n2, this.f)) {
            return false;
        }
        try {
            this.rehash(n2);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    protected void rehash(int n) {
        float[] fArray = this.key;
        int n2 = n - 1;
        float[] fArray2 = new float[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (Float.floatToIntBits(fArray[--n3]) == 0) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(fArray[n3])) & n2;
            if (Float.floatToIntBits(fArray2[n5]) != 0) {
                while (Float.floatToIntBits(fArray2[n5 = n5 + 1 & n2]) != 0) {
                }
            }
            fArray2[n5] = fArray[n3];
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = fArray2;
    }

    public FloatOpenCustomHashSet clone() {
        FloatOpenCustomHashSet floatOpenCustomHashSet;
        try {
            floatOpenCustomHashSet = (FloatOpenCustomHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        floatOpenCustomHashSet.key = (float[])this.key.clone();
        floatOpenCustomHashSet.containsNull = this.containsNull;
        floatOpenCustomHashSet.strategy = this.strategy;
        return floatOpenCustomHashSet;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        while (n2-- != 0) {
            while (Float.floatToIntBits(this.key[n3]) == 0) {
                ++n3;
            }
            n += this.strategy.hashCode(this.key[n3]);
            ++n3;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        FloatIterator floatIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            objectOutputStream.writeFloat(floatIterator.nextFloat());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new float[this.n + 1];
        float[] fArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            float f = objectInputStream.readFloat();
            if (this.strategy.equals(f, 0.0f)) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
                if (Float.floatToIntBits(fArray[n2]) != 0) {
                    while (Float.floatToIntBits(fArray[n2 = n2 + 1 & this.mask]) != 0) {
                    }
                }
            }
            fArray[n2] = f;
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
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        FloatArrayList wrapped;
        final FloatOpenCustomHashSet this$0;

        private SetIterator(FloatOpenCustomHashSet floatOpenCustomHashSet) {
            this.this$0 = floatOpenCustomHashSet;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNull = this.this$0.containsNull;
        }

        @Override
        public boolean hasNext() {
            return this.c != 0;
        }

        @Override
        public float nextFloat() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            float[] fArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                return this.wrapped.getFloat(-this.pos - 1);
            } while (Float.floatToIntBits(fArray[this.pos]) == 0);
            this.last = this.pos;
            return fArray[this.last];
        }

        private final void shiftKeys(int n) {
            float[] fArray = this.this$0.key;
            while (true) {
                float f;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if (Float.floatToIntBits(f = fArray[n]) == 0) {
                        fArray[n2] = 0.0f;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(f)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new FloatArrayList(2);
                    }
                    this.wrapped.add(fArray[n]);
                }
                fArray[n2] = f;
            }
        }

        @Override
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = 0.0f;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.getFloat(-this.pos - 1));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        SetIterator(FloatOpenCustomHashSet floatOpenCustomHashSet, 1 var2_2) {
            this(floatOpenCustomHashSet);
        }
    }
}

