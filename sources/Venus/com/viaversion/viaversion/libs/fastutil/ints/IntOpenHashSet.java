/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntOpenHashSet
extends AbstractIntSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public IntOpenHashSet(int n, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
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

    public IntOpenHashSet(int n) {
        this(n, 0.75f);
    }

    public IntOpenHashSet() {
        this(16, 0.75f);
    }

    public IntOpenHashSet(Collection<? extends Integer> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public IntOpenHashSet(Collection<? extends Integer> collection) {
        this(collection, 0.75f);
    }

    public IntOpenHashSet(IntCollection intCollection, float f) {
        this(intCollection.size(), f);
        this.addAll(intCollection);
    }

    public IntOpenHashSet(IntCollection intCollection) {
        this(intCollection, 0.75f);
    }

    public IntOpenHashSet(IntIterator intIterator, float f) {
        this(16, f);
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }

    public IntOpenHashSet(IntIterator intIterator) {
        this(intIterator, 0.75f);
    }

    public IntOpenHashSet(Iterator<?> iterator2, float f) {
        this(IntIterators.asIntIterator(iterator2), f);
    }

    public IntOpenHashSet(Iterator<?> iterator2) {
        this(IntIterators.asIntIterator(iterator2));
    }

    public IntOpenHashSet(int[] nArray, int n, int n2, float f) {
        this(n2 < 0 ? 0 : n2, f);
        IntArrays.ensureOffsetLength(nArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(nArray[n + i]);
        }
    }

    public IntOpenHashSet(int[] nArray, int n, int n2) {
        this(nArray, n, n2, 0.75f);
    }

    public IntOpenHashSet(int[] nArray, float f) {
        this(nArray, 0, nArray.length, f);
    }

    public IntOpenHashSet(int[] nArray) {
        this(nArray, 0.75f);
    }

    public static IntOpenHashSet of() {
        return new IntOpenHashSet();
    }

    public static IntOpenHashSet of(int n) {
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet(1, 0.75f);
        intOpenHashSet.add(n);
        return intOpenHashSet;
    }

    public static IntOpenHashSet of(int n, int n2) {
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet(2, 0.75f);
        intOpenHashSet.add(n);
        if (!intOpenHashSet.add(n2)) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        return intOpenHashSet;
    }

    public static IntOpenHashSet of(int n, int n2, int n3) {
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet(3, 0.75f);
        intOpenHashSet.add(n);
        if (!intOpenHashSet.add(n2)) {
            throw new IllegalArgumentException("Duplicate element: " + n2);
        }
        if (!intOpenHashSet.add(n3)) {
            throw new IllegalArgumentException("Duplicate element: " + n3);
        }
        return intOpenHashSet;
    }

    public static IntOpenHashSet of(int ... nArray) {
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet(nArray.length, 0.75f);
        for (int n : nArray) {
            if (intOpenHashSet.add(n)) continue;
            throw new IllegalArgumentException("Duplicate element " + n);
        }
        return intOpenHashSet;
    }

    public static IntOpenHashSet toSet(IntStream intStream) {
        return intStream.collect(IntOpenHashSet::new, IntOpenHashSet::add, IntOpenHashSet::addAll);
    }

    public static IntOpenHashSet toSetWithExpectedSize(IntStream intStream, int n) {
        if (n <= 16) {
            return IntOpenHashSet.toSet(intStream);
        }
        return intStream.collect(new IntCollections.SizeDecreasingSupplier<IntOpenHashSet>(n, IntOpenHashSet::lambda$toSetWithExpectedSize$0), IntOpenHashSet::add, IntOpenHashSet::addAll);
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    public void ensureCapacity(int n) {
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
        if (n == 0) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            int[] nArray = this.key;
            int n2 = HashCommon.mix(n) & this.mask;
            int n3 = nArray[n2];
            if (n3 != 0) {
                if (n3 == n) {
                    return true;
                }
                while ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (n3 != n) continue;
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
                int n4 = HashCommon.mix(n2) & this.mask;
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
        if (n == 0) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return true;
        }
        if (n == n3) {
            return this.removeEntry(n2);
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (n != n3);
        return this.removeEntry(n2);
    }

    @Override
    public boolean contains(int n) {
        if (n == 0) {
            return this.containsNull;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return true;
        }
        if (n == n3) {
            return false;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (n != n3);
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

    @Override
    public IntSpliterator spliterator() {
        return new SetSpliterator(this);
    }

    @Override
    public void forEach(IntConsumer intConsumer) {
        if (this.containsNull) {
            intConsumer.accept(this.key[this.n]);
        }
        int[] nArray = this.key;
        int n = this.n;
        while (n-- != 0) {
            if (nArray[n] == 0) continue;
            intConsumer.accept(nArray[n]);
        }
    }

    public boolean trim() {
        return this.trim(this.size);
    }

    public boolean trim(int n) {
        int n2 = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.f));
        if (n2 >= this.n || this.size > HashCommon.maxFill(n2, this.f)) {
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
            int n5 = HashCommon.mix(nArray[n3]) & n2;
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

    public IntOpenHashSet clone() {
        IntOpenHashSet intOpenHashSet;
        try {
            intOpenHashSet = (IntOpenHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        intOpenHashSet.key = (int[])this.key.clone();
        intOpenHashSet.containsNull = this.containsNull;
        return intOpenHashSet;
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
            n += this.key[n3];
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
            if (n3 == 0) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = HashCommon.mix(n3) & this.mask;
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
    public Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private static IntOpenHashSet lambda$toSetWithExpectedSize$0(int n) {
        return n <= 16 ? new IntOpenHashSet() : new IntOpenHashSet(n);
    }

    static int access$100(IntOpenHashSet intOpenHashSet) {
        return intOpenHashSet.realSize();
    }

    private final class SetIterator
    implements IntIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        IntArrayList wrapped;
        final IntOpenHashSet this$0;

        private SetIterator(IntOpenHashSet intOpenHashSet) {
            this.this$0 = intOpenHashSet;
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
                    int n4 = HashCommon.mix(n2) & this.this$0.mask;
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

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            int[] nArray = this.this$0.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                intConsumer.accept(nArray[this.this$0.n]);
                --this.c;
            }
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    intConsumer.accept(this.wrapped.getInt(-this.pos - 1));
                    --this.c;
                    continue;
                }
                if (nArray[this.pos] == 0) continue;
                this.last = this.pos;
                intConsumer.accept(nArray[this.last]);
                --this.c;
            }
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((IntConsumer)object);
        }

        SetIterator(IntOpenHashSet intOpenHashSet, 1 var2_2) {
            this(intOpenHashSet);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class SetSpliterator
    implements IntSpliterator {
        private static final int POST_SPLIT_CHARACTERISTICS = 257;
        int pos;
        int max;
        int c;
        boolean mustReturnNull;
        boolean hasSplit;
        final IntOpenHashSet this$0;

        SetSpliterator(IntOpenHashSet intOpenHashSet) {
            this.this$0 = intOpenHashSet;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNull;
            this.hasSplit = false;
        }

        SetSpliterator(IntOpenHashSet intOpenHashSet, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = intOpenHashSet;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNull;
            this.hasSplit = false;
            this.pos = n;
            this.max = n2;
            this.mustReturnNull = bl;
            this.hasSplit = bl2;
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                intConsumer.accept(this.this$0.key[this.this$0.n]);
                return false;
            }
            int[] nArray = this.this$0.key;
            while (this.pos < this.max) {
                if (nArray[this.pos] != 0) {
                    ++this.c;
                    intConsumer.accept(nArray[this.pos++]);
                    return false;
                }
                ++this.pos;
            }
            return true;
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            int[] nArray = this.this$0.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                intConsumer.accept(nArray[this.this$0.n]);
                ++this.c;
            }
            while (this.pos < this.max) {
                if (nArray[this.pos] != 0) {
                    intConsumer.accept(nArray[this.pos]);
                    ++this.c;
                }
                ++this.pos;
            }
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 257 : 321;
        }

        @Override
        public long estimateSize() {
            if (!this.hasSplit) {
                return this.this$0.size - this.c;
            }
            return Math.min((long)(this.this$0.size - this.c), (long)((double)IntOpenHashSet.access$100(this.this$0) / (double)this.this$0.n * (double)(this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
        }

        @Override
        public SetSpliterator trySplit() {
            if (this.pos >= this.max - 1) {
                return null;
            }
            int n = this.max - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            int n2 = this.pos + n;
            int n3 = this.pos;
            int n4 = n2;
            SetSpliterator setSpliterator = new SetSpliterator(this.this$0, n3, n4, this.mustReturnNull, true);
            this.pos = n2;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return setSpliterator;
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (l == 0L) {
                return 0L;
            }
            long l2 = 0L;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++l2;
                --l;
            }
            int[] nArray = this.this$0.key;
            while (this.pos < this.max && l > 0L) {
                if (nArray[this.pos++] == 0) continue;
                ++l2;
                --l;
            }
            return l2;
        }

        @Override
        public IntSpliterator trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
}

