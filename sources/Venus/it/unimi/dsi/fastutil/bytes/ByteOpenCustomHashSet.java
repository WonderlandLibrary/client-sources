/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteHash;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
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
public class ByteOpenCustomHashSet
extends AbstractByteSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient byte[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected ByteHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public ByteOpenCustomHashSet(int n, float f, ByteHash.Strategy strategy) {
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
        this.key = new byte[this.n + 1];
    }

    public ByteOpenCustomHashSet(int n, ByteHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public ByteOpenCustomHashSet(ByteHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public ByteOpenCustomHashSet(Collection<? extends Byte> collection, float f, ByteHash.Strategy strategy) {
        this(collection.size(), f, strategy);
        this.addAll(collection);
    }

    public ByteOpenCustomHashSet(Collection<? extends Byte> collection, ByteHash.Strategy strategy) {
        this(collection, 0.75f, strategy);
    }

    public ByteOpenCustomHashSet(ByteCollection byteCollection, float f, ByteHash.Strategy strategy) {
        this(byteCollection.size(), f, strategy);
        this.addAll(byteCollection);
    }

    public ByteOpenCustomHashSet(ByteCollection byteCollection, ByteHash.Strategy strategy) {
        this(byteCollection, 0.75f, strategy);
    }

    public ByteOpenCustomHashSet(ByteIterator byteIterator, float f, ByteHash.Strategy strategy) {
        this(16, f, strategy);
        while (byteIterator.hasNext()) {
            this.add(byteIterator.nextByte());
        }
    }

    public ByteOpenCustomHashSet(ByteIterator byteIterator, ByteHash.Strategy strategy) {
        this(byteIterator, 0.75f, strategy);
    }

    public ByteOpenCustomHashSet(Iterator<?> iterator2, float f, ByteHash.Strategy strategy) {
        this(ByteIterators.asByteIterator(iterator2), f, strategy);
    }

    public ByteOpenCustomHashSet(Iterator<?> iterator2, ByteHash.Strategy strategy) {
        this(ByteIterators.asByteIterator(iterator2), strategy);
    }

    public ByteOpenCustomHashSet(byte[] byArray, int n, int n2, float f, ByteHash.Strategy strategy) {
        this(n2 < 0 ? 0 : n2, f, strategy);
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(byArray[n + i]);
        }
    }

    public ByteOpenCustomHashSet(byte[] byArray, int n, int n2, ByteHash.Strategy strategy) {
        this(byArray, n, n2, 0.75f, strategy);
    }

    public ByteOpenCustomHashSet(byte[] byArray, float f, ByteHash.Strategy strategy) {
        this(byArray, 0, byArray.length, f, strategy);
    }

    public ByteOpenCustomHashSet(byte[] byArray, ByteHash.Strategy strategy) {
        this(byArray, 0.75f, strategy);
    }

    public ByteHash.Strategy strategy() {
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
    public boolean addAll(ByteCollection byteCollection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(byteCollection.size());
        } else {
            this.tryCapacity(this.size() + byteCollection.size());
        }
        return super.addAll(byteCollection);
    }

    @Override
    public boolean addAll(Collection<? extends Byte> collection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        } else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(byte by) {
        if (this.strategy.equals(by, (byte)0)) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
            this.key[this.n] = by;
        } else {
            byte[] byArray = this.key;
            int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
            byte by2 = byArray[n];
            if (by2 != 0) {
                if (this.strategy.equals(by2, by)) {
                    return true;
                }
                while ((by2 = byArray[n = n + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(by2, by)) continue;
                    return true;
                }
            }
            byArray[n] = by;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return false;
    }

    protected final void shiftKeys(int n) {
        byte[] byArray = this.key;
        while (true) {
            byte by;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((by = byArray[n]) == 0) {
                    byArray[n2] = 0;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            byArray[n2] = by;
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
    public boolean remove(byte by) {
        if (this.strategy.equals(by, (byte)0)) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return true;
        }
        if (this.strategy.equals(by, by2)) {
            return this.removeEntry(n);
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(by, by2));
        return this.removeEntry(n);
    }

    @Override
    public boolean contains(byte by) {
        if (this.strategy.equals(by, (byte)0)) {
            return this.containsNull;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return true;
        }
        if (this.strategy.equals(by, by2)) {
            return false;
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(by, by2));
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, (byte)0);
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
    public ByteIterator iterator() {
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
        byte[] byArray = this.key;
        int n2 = n - 1;
        byte[] byArray2 = new byte[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (byArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(byArray[n3])) & n2;
            if (byArray2[n5] != 0) {
                while (byArray2[n5 = n5 + 1 & n2] != 0) {
                }
            }
            byArray2[n5] = byArray[n3];
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = byArray2;
    }

    public ByteOpenCustomHashSet clone() {
        ByteOpenCustomHashSet byteOpenCustomHashSet;
        try {
            byteOpenCustomHashSet = (ByteOpenCustomHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byteOpenCustomHashSet.key = (byte[])this.key.clone();
        byteOpenCustomHashSet.containsNull = this.containsNull;
        byteOpenCustomHashSet.strategy = this.strategy;
        return byteOpenCustomHashSet;
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
        ByteIterator byteIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            objectOutputStream.writeByte(byteIterator.nextByte());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new byte[this.n + 1];
        byte[] byArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            byte by = objectInputStream.readByte();
            if (this.strategy.equals(by, (byte)0)) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
                if (byArray[n2] != 0) {
                    while (byArray[n2 = n2 + 1 & this.mask] != 0) {
                    }
                }
            }
            byArray[n2] = by;
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
    implements ByteIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        ByteArrayList wrapped;
        final ByteOpenCustomHashSet this$0;

        private SetIterator(ByteOpenCustomHashSet byteOpenCustomHashSet) {
            this.this$0 = byteOpenCustomHashSet;
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
        public byte nextByte() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            byte[] byArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                return this.wrapped.getByte(-this.pos - 1);
            } while (byArray[this.pos] == 0);
            this.last = this.pos;
            return byArray[this.last];
        }

        private final void shiftKeys(int n) {
            byte[] byArray = this.this$0.key;
            while (true) {
                byte by;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((by = byArray[n]) == 0) {
                        byArray[n2] = 0;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(by)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ByteArrayList(2);
                    }
                    this.wrapped.add(byArray[n]);
                }
                byArray[n2] = by;
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
                this.this$0.remove(this.wrapped.getByte(-this.pos - 1));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        SetIterator(ByteOpenCustomHashSet byteOpenCustomHashSet, 1 var2_2) {
            this(byteOpenCustomHashSet);
        }
    }
}

