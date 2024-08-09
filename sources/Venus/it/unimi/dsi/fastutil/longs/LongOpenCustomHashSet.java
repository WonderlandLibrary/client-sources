/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongHash;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
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
public class LongOpenCustomHashSet
extends AbstractLongSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected LongHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public LongOpenCustomHashSet(int n, float f, LongHash.Strategy strategy) {
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
        this.key = new long[this.n + 1];
    }

    public LongOpenCustomHashSet(int n, LongHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public LongOpenCustomHashSet(LongHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public LongOpenCustomHashSet(Collection<? extends Long> collection, float f, LongHash.Strategy strategy) {
        this(collection.size(), f, strategy);
        this.addAll(collection);
    }

    public LongOpenCustomHashSet(Collection<? extends Long> collection, LongHash.Strategy strategy) {
        this(collection, 0.75f, strategy);
    }

    public LongOpenCustomHashSet(LongCollection longCollection, float f, LongHash.Strategy strategy) {
        this(longCollection.size(), f, strategy);
        this.addAll(longCollection);
    }

    public LongOpenCustomHashSet(LongCollection longCollection, LongHash.Strategy strategy) {
        this(longCollection, 0.75f, strategy);
    }

    public LongOpenCustomHashSet(LongIterator longIterator, float f, LongHash.Strategy strategy) {
        this(16, f, strategy);
        while (longIterator.hasNext()) {
            this.add(longIterator.nextLong());
        }
    }

    public LongOpenCustomHashSet(LongIterator longIterator, LongHash.Strategy strategy) {
        this(longIterator, 0.75f, strategy);
    }

    public LongOpenCustomHashSet(Iterator<?> iterator2, float f, LongHash.Strategy strategy) {
        this(LongIterators.asLongIterator(iterator2), f, strategy);
    }

    public LongOpenCustomHashSet(Iterator<?> iterator2, LongHash.Strategy strategy) {
        this(LongIterators.asLongIterator(iterator2), strategy);
    }

    public LongOpenCustomHashSet(long[] lArray, int n, int n2, float f, LongHash.Strategy strategy) {
        this(n2 < 0 ? 0 : n2, f, strategy);
        LongArrays.ensureOffsetLength(lArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(lArray[n + i]);
        }
    }

    public LongOpenCustomHashSet(long[] lArray, int n, int n2, LongHash.Strategy strategy) {
        this(lArray, n, n2, 0.75f, strategy);
    }

    public LongOpenCustomHashSet(long[] lArray, float f, LongHash.Strategy strategy) {
        this(lArray, 0, lArray.length, f, strategy);
    }

    public LongOpenCustomHashSet(long[] lArray, LongHash.Strategy strategy) {
        this(lArray, 0.75f, strategy);
    }

    public LongHash.Strategy strategy() {
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
    public boolean addAll(LongCollection longCollection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(longCollection.size());
        } else {
            this.tryCapacity(this.size() + longCollection.size());
        }
        return super.addAll(longCollection);
    }

    @Override
    public boolean addAll(Collection<? extends Long> collection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        } else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(long l) {
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
            this.key[this.n] = l;
        } else {
            long[] lArray = this.key;
            int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
            long l2 = lArray[n];
            if (l2 != 0L) {
                if (this.strategy.equals(l2, l)) {
                    return true;
                }
                while ((l2 = lArray[n = n + 1 & this.mask]) != 0L) {
                    if (!this.strategy.equals(l2, l)) continue;
                    return true;
                }
            }
            lArray[n] = l;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return false;
    }

    protected final void shiftKeys(int n) {
        long[] lArray = this.key;
        while (true) {
            long l;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((l = lArray[n]) == 0L) {
                    lArray[n2] = 0L;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            lArray[n2] = l;
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
        this.key[this.n] = 0L;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    @Override
    public boolean remove(long l) {
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (this.strategy.equals(l, l2)) {
            return this.removeEntry(n);
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(l, l2));
        return this.removeEntry(n);
    }

    @Override
    public boolean contains(long l) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNull;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (this.strategy.equals(l, l2)) {
            return false;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(l, l2));
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0L);
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
    public LongIterator iterator() {
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
        long[] lArray = this.key;
        int n2 = n - 1;
        long[] lArray2 = new long[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (lArray[--n3] == 0L) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(lArray[n3])) & n2;
            if (lArray2[n5] != 0L) {
                while (lArray2[n5 = n5 + 1 & n2] != 0L) {
                }
            }
            lArray2[n5] = lArray[n3];
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = lArray2;
    }

    public LongOpenCustomHashSet clone() {
        LongOpenCustomHashSet longOpenCustomHashSet;
        try {
            longOpenCustomHashSet = (LongOpenCustomHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        longOpenCustomHashSet.key = (long[])this.key.clone();
        longOpenCustomHashSet.containsNull = this.containsNull;
        longOpenCustomHashSet.strategy = this.strategy;
        return longOpenCustomHashSet;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == 0L) {
                ++n3;
            }
            n += this.strategy.hashCode(this.key[n3]);
            ++n3;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        LongIterator longIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            objectOutputStream.writeLong(longIterator.nextLong());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new long[this.n + 1];
        long[] lArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            long l = objectInputStream.readLong();
            if (this.strategy.equals(l, 0L)) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
                if (lArray[n2] != 0L) {
                    while (lArray[n2 = n2 + 1 & this.mask] != 0L) {
                    }
                }
            }
            lArray[n2] = l;
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
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        LongArrayList wrapped;
        final LongOpenCustomHashSet this$0;

        private SetIterator(LongOpenCustomHashSet longOpenCustomHashSet) {
            this.this$0 = longOpenCustomHashSet;
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
        public long nextLong() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            long[] lArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                return this.wrapped.getLong(-this.pos - 1);
            } while (lArray[this.pos] == 0L);
            this.last = this.pos;
            return lArray[this.last];
        }

        private final void shiftKeys(int n) {
            long[] lArray = this.this$0.key;
            while (true) {
                long l;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((l = lArray[n]) == 0L) {
                        lArray[n2] = 0L;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new LongArrayList(2);
                    }
                    this.wrapped.add(lArray[n]);
                }
                lArray[n2] = l;
            }
        }

        @Override
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = 0L;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.getLong(-this.pos - 1));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        SetIterator(LongOpenCustomHashSet longOpenCustomHashSet, 1 var2_2) {
            this(longOpenCustomHashSet);
        }
    }
}

