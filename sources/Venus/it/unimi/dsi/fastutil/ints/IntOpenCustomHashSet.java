/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntHash;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntIterators;
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
public class IntOpenCustomHashSet
extends AbstractIntSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected IntHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public IntOpenCustomHashSet(int n, float f, IntHash.Strategy strategy) {
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
        this.key = new int[this.n + 1];
    }

    public IntOpenCustomHashSet(int n, IntHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public IntOpenCustomHashSet(IntHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public IntOpenCustomHashSet(Collection<? extends Integer> collection, float f, IntHash.Strategy strategy) {
        this(collection.size(), f, strategy);
        this.addAll(collection);
    }

    public IntOpenCustomHashSet(Collection<? extends Integer> collection, IntHash.Strategy strategy) {
        this(collection, 0.75f, strategy);
    }

    public IntOpenCustomHashSet(IntCollection intCollection, float f, IntHash.Strategy strategy) {
        this(intCollection.size(), f, strategy);
        this.addAll(intCollection);
    }

    public IntOpenCustomHashSet(IntCollection intCollection, IntHash.Strategy strategy) {
        this(intCollection, 0.75f, strategy);
    }

    public IntOpenCustomHashSet(IntIterator intIterator, float f, IntHash.Strategy strategy) {
        this(16, f, strategy);
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }

    public IntOpenCustomHashSet(IntIterator intIterator, IntHash.Strategy strategy) {
        this(intIterator, 0.75f, strategy);
    }

    public IntOpenCustomHashSet(Iterator<?> iterator2, float f, IntHash.Strategy strategy) {
        this(IntIterators.asIntIterator(iterator2), f, strategy);
    }

    public IntOpenCustomHashSet(Iterator<?> iterator2, IntHash.Strategy strategy) {
        this(IntIterators.asIntIterator(iterator2), strategy);
    }

    public IntOpenCustomHashSet(int[] nArray, int n, int n2, float f, IntHash.Strategy strategy) {
        this(n2 < 0 ? 0 : n2, f, strategy);
        IntArrays.ensureOffsetLength(nArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(nArray[n + i]);
        }
    }

    public IntOpenCustomHashSet(int[] nArray, int n, int n2, IntHash.Strategy strategy) {
        this(nArray, n, n2, 0.75f, strategy);
    }

    public IntOpenCustomHashSet(int[] nArray, float f, IntHash.Strategy strategy) {
        this(nArray, 0, nArray.length, f, strategy);
    }

    public IntOpenCustomHashSet(int[] nArray, IntHash.Strategy strategy) {
        this(nArray, 0.75f, strategy);
    }

    public IntHash.Strategy strategy() {
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
    public boolean addAll(IntCollection intCollection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(intCollection.size());
        } else {
            this.tryCapacity(this.size() + intCollection.size());
        }
        return super.addAll(intCollection);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> collection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        } else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(int n) {
        if (this.strategy.equals(n, 0)) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
            this.key[this.n] = n;
        } else {
            int[] nArray = this.key;
            int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
            int n3 = nArray[n2];
            if (n3 != 0) {
                if (this.strategy.equals(n3, n)) {
                    return true;
                }
                while ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(n3, n)) continue;
                    return true;
                }
            }
            nArray[n2] = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return false;
    }

    protected final void shiftKeys(int n) {
        int[] nArray = this.key;
        while (true) {
            int n2;
            int n3 = n;
            n = n3 + 1 & this.mask;
            while (true) {
                if ((n2 = nArray[n]) == 0) {
                    nArray[n3] = 0;
                    return;
                }
                int n4 = HashCommon.mix(this.strategy.hashCode(n2)) & this.mask;
                if (n3 <= n ? n3 >= n4 || n4 > n : n3 >= n4 && n4 > n) break;
                n = n + 1 & this.mask;
            }
            nArray[n3] = n2;
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
        this.key[this.n] = 0;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    @Override
    public boolean remove(int n) {
        if (this.strategy.equals(n, 0)) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return true;
        }
        if (this.strategy.equals(n, n3)) {
            return this.removeEntry(n2);
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(n, n3));
        return this.removeEntry(n2);
    }

    @Override
    public boolean contains(int n) {
        if (this.strategy.equals(n, 0)) {
            return this.containsNull;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return true;
        }
        if (this.strategy.equals(n, n3)) {
            return false;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(n, n3));
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0);
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
    public IntIterator iterator() {
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
        int[] nArray = this.key;
        int n2 = n - 1;
        int[] nArray2 = new int[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (nArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(nArray[n3])) & n2;
            if (nArray2[n5] != 0) {
                while (nArray2[n5 = n5 + 1 & n2] != 0) {
                }
            }
            nArray2[n5] = nArray[n3];
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = nArray2;
    }

    public IntOpenCustomHashSet clone() {
        IntOpenCustomHashSet intOpenCustomHashSet;
        try {
            intOpenCustomHashSet = (IntOpenCustomHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        intOpenCustomHashSet.key = (int[])this.key.clone();
        intOpenCustomHashSet.containsNull = this.containsNull;
        intOpenCustomHashSet.strategy = this.strategy;
        return intOpenCustomHashSet;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == 0) {
                ++n3;
            }
            n += this.strategy.hashCode(this.key[n3]);
            ++n3;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        IntIterator intIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            objectOutputStream.writeInt(intIterator.nextInt());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new int[this.n + 1];
        int[] nArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            int n3 = objectInputStream.readInt();
            if (this.strategy.equals(n3, 0)) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(n3)) & this.mask;
                if (nArray[n2] != 0) {
                    while (nArray[n2 = n2 + 1 & this.mask] != 0) {
                    }
                }
            }
            nArray[n2] = n3;
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
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        IntArrayList wrapped;
        final IntOpenCustomHashSet this$0;

        private SetIterator(IntOpenCustomHashSet intOpenCustomHashSet) {
            this.this$0 = intOpenCustomHashSet;
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
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            int[] nArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                return this.wrapped.getInt(-this.pos - 1);
            } while (nArray[this.pos] == 0);
            this.last = this.pos;
            return nArray[this.last];
        }

        private final void shiftKeys(int n) {
            int[] nArray = this.this$0.key;
            while (true) {
                int n2;
                int n3 = n;
                n = n3 + 1 & this.this$0.mask;
                while (true) {
                    if ((n2 = nArray[n]) == 0) {
                        nArray[n3] = 0;
                        return;
                    }
                    int n4 = HashCommon.mix(this.this$0.strategy.hashCode(n2)) & this.this$0.mask;
                    if (n3 <= n ? n3 >= n4 || n4 > n : n3 >= n4 && n4 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n3) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(nArray[n]);
                }
                nArray[n3] = n2;
            }
        }

        @Override
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = 0;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.getInt(-this.pos - 1));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        SetIterator(IntOpenCustomHashSet intOpenCustomHashSet, 1 var2_2) {
            this(intOpenCustomHashSet);
        }
    }
}

