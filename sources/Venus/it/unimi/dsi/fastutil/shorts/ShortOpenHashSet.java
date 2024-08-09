/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterators;
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
public class ShortOpenHashSet
extends AbstractShortSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient short[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public ShortOpenHashSet(int n, float f) {
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
        this.key = new short[this.n + 1];
    }

    public ShortOpenHashSet(int n) {
        this(n, 0.75f);
    }

    public ShortOpenHashSet() {
        this(16, 0.75f);
    }

    public ShortOpenHashSet(Collection<? extends Short> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public ShortOpenHashSet(Collection<? extends Short> collection) {
        this(collection, 0.75f);
    }

    public ShortOpenHashSet(ShortCollection shortCollection, float f) {
        this(shortCollection.size(), f);
        this.addAll(shortCollection);
    }

    public ShortOpenHashSet(ShortCollection shortCollection) {
        this(shortCollection, 0.75f);
    }

    public ShortOpenHashSet(ShortIterator shortIterator, float f) {
        this(16, f);
        while (shortIterator.hasNext()) {
            this.add(shortIterator.nextShort());
        }
    }

    public ShortOpenHashSet(ShortIterator shortIterator) {
        this(shortIterator, 0.75f);
    }

    public ShortOpenHashSet(Iterator<?> iterator2, float f) {
        this(ShortIterators.asShortIterator(iterator2), f);
    }

    public ShortOpenHashSet(Iterator<?> iterator2) {
        this(ShortIterators.asShortIterator(iterator2));
    }

    public ShortOpenHashSet(short[] sArray, int n, int n2, float f) {
        this(n2 < 0 ? 0 : n2, f);
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(sArray[n + i]);
        }
    }

    public ShortOpenHashSet(short[] sArray, int n, int n2) {
        this(sArray, n, n2, 0.75f);
    }

    public ShortOpenHashSet(short[] sArray, float f) {
        this(sArray, 0, sArray.length, f);
    }

    public ShortOpenHashSet(short[] sArray) {
        this(sArray, 0.75f);
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
    public boolean addAll(ShortCollection shortCollection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(shortCollection.size());
        } else {
            this.tryCapacity(this.size() + shortCollection.size());
        }
        return super.addAll(shortCollection);
    }

    @Override
    public boolean addAll(Collection<? extends Short> collection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        } else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(short s) {
        if (s == 0) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            short[] sArray = this.key;
            int n = HashCommon.mix(s) & this.mask;
            short s2 = sArray[n];
            if (s2 != 0) {
                if (s2 == s) {
                    return true;
                }
                while ((s2 = sArray[n = n + 1 & this.mask]) != 0) {
                    if (s2 != s) continue;
                    return true;
                }
            }
            sArray[n] = s;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return false;
    }

    protected final void shiftKeys(int n) {
        short[] sArray = this.key;
        while (true) {
            short s;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((s = sArray[n]) == 0) {
                    sArray[n2] = 0;
                    return;
                }
                int n3 = HashCommon.mix(s) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            sArray[n2] = s;
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
    public boolean remove(short s) {
        if (s == 0) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return true;
        }
        if (s == s2) {
            return this.removeEntry(n);
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (s != s2);
        return this.removeEntry(n);
    }

    @Override
    public boolean contains(short s) {
        if (s == 0) {
            return this.containsNull;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return true;
        }
        if (s == s2) {
            return false;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (s != s2);
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, (short)0);
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
    public ShortIterator iterator() {
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
        short[] sArray = this.key;
        int n2 = n - 1;
        short[] sArray2 = new short[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (sArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(sArray[n3]) & n2;
            if (sArray2[n5] != 0) {
                while (sArray2[n5 = n5 + 1 & n2] != 0) {
                }
            }
            sArray2[n5] = sArray[n3];
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = sArray2;
    }

    public ShortOpenHashSet clone() {
        ShortOpenHashSet shortOpenHashSet;
        try {
            shortOpenHashSet = (ShortOpenHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        shortOpenHashSet.key = (short[])this.key.clone();
        shortOpenHashSet.containsNull = this.containsNull;
        return shortOpenHashSet;
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
        ShortIterator shortIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            objectOutputStream.writeShort(shortIterator.nextShort());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new short[this.n + 1];
        short[] sArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            short s = objectInputStream.readShort();
            if (s == 0) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = HashCommon.mix(s) & this.mask;
                if (sArray[n2] != 0) {
                    while (sArray[n2 = n2 + 1 & this.mask] != 0) {
                    }
                }
            }
            sArray[n2] = s;
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
    implements ShortIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        ShortArrayList wrapped;
        final ShortOpenHashSet this$0;

        private SetIterator(ShortOpenHashSet shortOpenHashSet) {
            this.this$0 = shortOpenHashSet;
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
        public short nextShort() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            short[] sArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                return this.wrapped.getShort(-this.pos - 1);
            } while (sArray[this.pos] == 0);
            this.last = this.pos;
            return sArray[this.last];
        }

        private final void shiftKeys(int n) {
            short[] sArray = this.this$0.key;
            while (true) {
                short s;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((s = sArray[n]) == 0) {
                        sArray[n2] = 0;
                        return;
                    }
                    int n3 = HashCommon.mix(s) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ShortArrayList(2);
                    }
                    this.wrapped.add(sArray[n]);
                }
                sArray[n2] = s;
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
                this.this$0.remove(this.wrapped.getShort(-this.pos - 1));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        SetIterator(ShortOpenHashSet shortOpenHashSet, 1 var2_2) {
            this(shortOpenHashSet);
        }
    }
}

