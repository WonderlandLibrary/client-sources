/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanSet;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterators;
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
public class BooleanOpenHashSet
extends AbstractBooleanSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient boolean[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public BooleanOpenHashSet(int n, float f) {
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
        this.key = new boolean[this.n + 1];
    }

    public BooleanOpenHashSet(int n) {
        this(n, 0.75f);
    }

    public BooleanOpenHashSet() {
        this(16, 0.75f);
    }

    public BooleanOpenHashSet(Collection<? extends Boolean> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public BooleanOpenHashSet(Collection<? extends Boolean> collection) {
        this(collection, 0.75f);
    }

    public BooleanOpenHashSet(BooleanCollection booleanCollection, float f) {
        this(booleanCollection.size(), f);
        this.addAll(booleanCollection);
    }

    public BooleanOpenHashSet(BooleanCollection booleanCollection) {
        this(booleanCollection, 0.75f);
    }

    public BooleanOpenHashSet(BooleanIterator booleanIterator, float f) {
        this(16, f);
        while (booleanIterator.hasNext()) {
            this.add(booleanIterator.nextBoolean());
        }
    }

    public BooleanOpenHashSet(BooleanIterator booleanIterator) {
        this(booleanIterator, 0.75f);
    }

    public BooleanOpenHashSet(Iterator<?> iterator2, float f) {
        this(BooleanIterators.asBooleanIterator(iterator2), f);
    }

    public BooleanOpenHashSet(Iterator<?> iterator2) {
        this(BooleanIterators.asBooleanIterator(iterator2));
    }

    public BooleanOpenHashSet(boolean[] blArray, int n, int n2, float f) {
        this(n2 < 0 ? 0 : n2, f);
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(blArray[n + i]);
        }
    }

    public BooleanOpenHashSet(boolean[] blArray, int n, int n2) {
        this(blArray, n, n2, 0.75f);
    }

    public BooleanOpenHashSet(boolean[] blArray, float f) {
        this(blArray, 0, blArray.length, f);
    }

    public BooleanOpenHashSet(boolean[] blArray) {
        this(blArray, 0.75f);
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
    public boolean addAll(BooleanCollection booleanCollection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(booleanCollection.size());
        } else {
            this.tryCapacity(this.size() + booleanCollection.size());
        }
        return super.addAll(booleanCollection);
    }

    @Override
    public boolean addAll(Collection<? extends Boolean> collection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        } else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(boolean bl) {
        if (!bl) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            boolean[] blArray = this.key;
            int n = (bl ? 262886248 : -878682501) & this.mask;
            boolean bl2 = blArray[n];
            if (bl2) {
                if (bl2 == bl) {
                    return true;
                }
                while (bl2 = blArray[n = n + 1 & this.mask]) {
                    if (bl2 != bl) continue;
                    return true;
                }
            }
            blArray[n] = bl;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return false;
    }

    protected final void shiftKeys(int n) {
        boolean[] blArray = this.key;
        while (true) {
            boolean bl;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if (!(bl = blArray[n])) {
                    blArray[n2] = false;
                    return;
                }
                int n3 = (bl ? 262886248 : -878682501) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            blArray[n2] = bl;
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
        this.key[this.n] = false;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    @Override
    public boolean remove(boolean bl) {
        if (!bl) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        boolean[] blArray = this.key;
        int n = (bl ? 262886248 : -878682501) & this.mask;
        boolean bl2 = blArray[n];
        if (!bl2) {
            return true;
        }
        if (bl == bl2) {
            return this.removeEntry(n);
        }
        do {
            if (bl2 = blArray[n = n + 1 & this.mask]) continue;
            return true;
        } while (bl != bl2);
        return this.removeEntry(n);
    }

    @Override
    public boolean contains(boolean bl) {
        if (!bl) {
            return this.containsNull;
        }
        boolean[] blArray = this.key;
        int n = (bl ? 262886248 : -878682501) & this.mask;
        boolean bl2 = blArray[n];
        if (!bl2) {
            return true;
        }
        if (bl == bl2) {
            return false;
        }
        do {
            if (bl2 = blArray[n = n + 1 & this.mask]) continue;
            return true;
        } while (bl != bl2);
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, false);
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
    public BooleanIterator iterator() {
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
        boolean[] blArray = this.key;
        int n2 = n - 1;
        boolean[] blArray2 = new boolean[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (!blArray[--n3]) {
            }
            int n5 = (blArray[n3] ? 262886248 : -878682501) & n2;
            if (blArray2[n5]) {
                while (blArray2[n5 = n5 + 1 & n2]) {
                }
            }
            blArray2[n5] = blArray[n3];
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = blArray2;
    }

    public BooleanOpenHashSet clone() {
        BooleanOpenHashSet booleanOpenHashSet;
        try {
            booleanOpenHashSet = (BooleanOpenHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        booleanOpenHashSet.key = (boolean[])this.key.clone();
        booleanOpenHashSet.containsNull = this.containsNull;
        return booleanOpenHashSet;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        while (n2-- != 0) {
            while (!this.key[n3]) {
                ++n3;
            }
            n += this.key[n3] ? 1231 : 1237;
            ++n3;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        BooleanIterator booleanIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            objectOutputStream.writeBoolean(booleanIterator.nextBoolean());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new boolean[this.n + 1];
        boolean[] blArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            boolean bl = objectInputStream.readBoolean();
            if (!bl) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = (bl ? 262886248 : -878682501) & this.mask;
                if (blArray[n2]) {
                    while (blArray[n2 = n2 + 1 & this.mask]) {
                    }
                }
            }
            blArray[n2] = bl;
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
    implements BooleanIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        BooleanArrayList wrapped;
        final BooleanOpenHashSet this$0;

        private SetIterator(BooleanOpenHashSet booleanOpenHashSet) {
            this.this$0 = booleanOpenHashSet;
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
        public boolean nextBoolean() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            boolean[] blArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                return this.wrapped.getBoolean(-this.pos - 1);
            } while (!blArray[this.pos]);
            this.last = this.pos;
            return blArray[this.last];
        }

        private final void shiftKeys(int n) {
            boolean[] blArray = this.this$0.key;
            while (true) {
                boolean bl;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if (!(bl = blArray[n])) {
                        blArray[n2] = false;
                        return;
                    }
                    int n3 = (bl ? 262886248 : -878682501) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new BooleanArrayList(2);
                    }
                    this.wrapped.add(blArray[n]);
                }
                blArray[n2] = bl;
            }
        }

        @Override
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = false;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.getBoolean(-this.pos - 1));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        SetIterator(BooleanOpenHashSet booleanOpenHashSet, 1 var2_2) {
            this(booleanOpenHashSet);
        }
    }
}

