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
public class LongOpenHashSet
extends AbstractLongSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public LongOpenHashSet(int n, float f) {
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

    public LongOpenHashSet(int n) {
        this(n, 0.75f);
    }

    public LongOpenHashSet() {
        this(16, 0.75f);
    }

    public LongOpenHashSet(Collection<? extends Long> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public LongOpenHashSet(Collection<? extends Long> collection) {
        this(collection, 0.75f);
    }

    public LongOpenHashSet(LongCollection longCollection, float f) {
        this(longCollection.size(), f);
        this.addAll(longCollection);
    }

    public LongOpenHashSet(LongCollection longCollection) {
        this(longCollection, 0.75f);
    }

    public LongOpenHashSet(LongIterator longIterator, float f) {
        this(16, f);
        while (longIterator.hasNext()) {
            this.add(longIterator.nextLong());
        }
    }

    public LongOpenHashSet(LongIterator longIterator) {
        this(longIterator, 0.75f);
    }

    public LongOpenHashSet(Iterator<?> iterator2, float f) {
        this(LongIterators.asLongIterator(iterator2), f);
    }

    public LongOpenHashSet(Iterator<?> iterator2) {
        this(LongIterators.asLongIterator(iterator2));
    }

    public LongOpenHashSet(long[] lArray, int n, int n2, float f) {
        this(n2 < 0 ? 0 : n2, f);
        LongArrays.ensureOffsetLength(lArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(lArray[n + i]);
        }
    }

    public LongOpenHashSet(long[] lArray, int n, int n2) {
        this(lArray, n, n2, 0.75f);
    }

    public LongOpenHashSet(long[] lArray, float f) {
        this(lArray, 0, lArray.length, f);
    }

    public LongOpenHashSet(long[] lArray) {
        this(lArray, 0.75f);
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
        if (l == 0L) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            long[] lArray = this.key;
            int n = (int)HashCommon.mix(l) & this.mask;
            long l2 = lArray[n];
            if (l2 != 0L) {
                if (l2 == l) {
                    return true;
                }
                while ((l2 = lArray[n = n + 1 & this.mask]) != 0L) {
                    if (l2 != l) continue;
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
                int n3 = (int)HashCommon.mix(l) & this.mask;
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
        if (l == 0L) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        long[] lArray = this.key;
        int n = (int)HashCommon.mix(l) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (l == l2) {
            return this.removeEntry(n);
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (l != l2);
        return this.removeEntry(n);
    }

    @Override
    public boolean contains(long l) {
        if (l == 0L) {
            return this.containsNull;
        }
        long[] lArray = this.key;
        int n = (int)HashCommon.mix(l) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (l == l2) {
            return false;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (l != l2);
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
            int n5 = (int)HashCommon.mix(lArray[n3]) & n2;
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

    public LongOpenHashSet clone() {
        LongOpenHashSet longOpenHashSet;
        try {
            longOpenHashSet = (LongOpenHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        longOpenHashSet.key = (long[])this.key.clone();
        longOpenHashSet.containsNull = this.containsNull;
        return longOpenHashSet;
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
            n += HashCommon.long2int(this.key[n3]);
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
            if (l == 0L) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = (int)HashCommon.mix(l) & this.mask;
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
        final LongOpenHashSet this$0;

        private SetIterator(LongOpenHashSet longOpenHashSet) {
            this.this$0 = longOpenHashSet;
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
                    int n3 = (int)HashCommon.mix(l) & this.this$0.mask;
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

        SetIterator(LongOpenHashSet longOpenHashSet, 1 var2_2) {
            this(longOpenHashSet);
        }
    }
}

