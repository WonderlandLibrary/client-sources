/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoubleLinkedOpenHashSet
extends AbstractDoubleSortedSet
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient double[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;

    public DoubleLinkedOpenHashSet(int n, float f) {
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
        this.key = new double[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public DoubleLinkedOpenHashSet(int n) {
        this(n, 0.75f);
    }

    public DoubleLinkedOpenHashSet() {
        this(16, 0.75f);
    }

    public DoubleLinkedOpenHashSet(Collection<? extends Double> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public DoubleLinkedOpenHashSet(Collection<? extends Double> collection) {
        this(collection, 0.75f);
    }

    public DoubleLinkedOpenHashSet(DoubleCollection doubleCollection, float f) {
        this(doubleCollection.size(), f);
        this.addAll(doubleCollection);
    }

    public DoubleLinkedOpenHashSet(DoubleCollection doubleCollection) {
        this(doubleCollection, 0.75f);
    }

    public DoubleLinkedOpenHashSet(DoubleIterator doubleIterator, float f) {
        this(16, f);
        while (doubleIterator.hasNext()) {
            this.add(doubleIterator.nextDouble());
        }
    }

    public DoubleLinkedOpenHashSet(DoubleIterator doubleIterator) {
        this(doubleIterator, 0.75f);
    }

    public DoubleLinkedOpenHashSet(Iterator<?> iterator2, float f) {
        this(DoubleIterators.asDoubleIterator(iterator2), f);
    }

    public DoubleLinkedOpenHashSet(Iterator<?> iterator2) {
        this(DoubleIterators.asDoubleIterator(iterator2));
    }

    public DoubleLinkedOpenHashSet(double[] dArray, int n, int n2, float f) {
        this(n2 < 0 ? 0 : n2, f);
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(dArray[n + i]);
        }
    }

    public DoubleLinkedOpenHashSet(double[] dArray, int n, int n2) {
        this(dArray, n, n2, 0.75f);
    }

    public DoubleLinkedOpenHashSet(double[] dArray, float f) {
        this(dArray, 0, dArray.length, f);
    }

    public DoubleLinkedOpenHashSet(double[] dArray) {
        this(dArray, 0.75f);
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
    public boolean addAll(DoubleCollection doubleCollection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(doubleCollection.size());
        } else {
            this.tryCapacity(this.size() + doubleCollection.size());
        }
        return super.addAll(doubleCollection);
    }

    @Override
    public boolean addAll(Collection<? extends Double> collection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        } else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(double d) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNull) {
                return true;
            }
            n = this.n;
            this.containsNull = true;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) != 0L) {
                if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(d)) {
                    return true;
                }
                while (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) {
                    if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d)) continue;
                    return true;
                }
            }
            dArray[n] = d;
        }
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n2 = this.last;
            this.link[n2] = this.link[n2] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return false;
    }

    protected final void shiftKeys(int n) {
        double[] dArray = this.key;
        while (true) {
            double d;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if (Double.doubleToLongBits(d = dArray[n]) == 0L) {
                    dArray[n2] = 0.0;
                    return;
                }
                int n3 = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            dArray[n2] = d;
            this.fixPointers(n, n2);
        }
    }

    private boolean removeEntry(int n) {
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.n] = 0.0;
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    @Override
    public boolean remove(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return true;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return this.removeEntry(n);
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return this.removeEntry(n);
    }

    @Override
    public boolean contains(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNull;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return true;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return false;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return false;
    }

    public double removeFirstDouble() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.first;
        this.first = (int)this.link[n];
        if (0 <= this.first) {
            int n2 = this.first;
            this.link[n2] = this.link[n2] | 0xFFFFFFFF00000000L;
        }
        double d = this.key[n];
        --this.size;
        if (Double.doubleToLongBits(d) == 0L) {
            this.containsNull = false;
            this.key[this.n] = 0.0;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    public double removeLastDouble() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.last;
        this.last = (int)(this.link[n] >>> 32);
        if (0 <= this.last) {
            int n2 = this.last;
            this.link[n2] = this.link[n2] | 0xFFFFFFFFL;
        }
        double d = this.key[n];
        --this.size;
        if (Double.doubleToLongBits(d) == 0L) {
            this.containsNull = false;
            this.key[this.n] = 0.0;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    private void moveIndexToFirst(int n) {
        if (this.size == 1 || this.first == n) {
            return;
        }
        if (this.last == n) {
            int n2 = this.last = (int)(this.link[n] >>> 32);
            this.link[n2] = this.link[n2] | 0xFFFFFFFFL;
        } else {
            long l = this.link[n];
            int n3 = (int)(l >>> 32);
            int n4 = (int)l;
            int n5 = n3;
            this.link[n5] = this.link[n5] ^ (this.link[n3] ^ l & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            int n6 = n4;
            this.link[n6] = this.link[n6] ^ (this.link[n4] ^ l & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
        }
        int n7 = this.first;
        this.link[n7] = this.link[n7] ^ (this.link[this.first] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
        this.link[n] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
        this.first = n;
    }

    private void moveIndexToLast(int n) {
        if (this.size == 1 || this.last == n) {
            return;
        }
        if (this.first == n) {
            int n2 = this.first = (int)this.link[n];
            this.link[n2] = this.link[n2] | 0xFFFFFFFF00000000L;
        } else {
            long l = this.link[n];
            int n3 = (int)(l >>> 32);
            int n4 = (int)l;
            int n5 = n3;
            this.link[n5] = this.link[n5] ^ (this.link[n3] ^ l & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            int n6 = n4;
            this.link[n6] = this.link[n6] ^ (this.link[n4] ^ l & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
        }
        int n7 = this.last;
        this.link[n7] = this.link[n7] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
        this.last = n;
    }

    public boolean addAndMoveToFirst(double d) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNull) {
                this.moveIndexToFirst(this.n);
                return true;
            }
            this.containsNull = true;
            n = this.n;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            while (Double.doubleToLongBits(dArray[n]) != 0L) {
                if (Double.doubleToLongBits(d) == Double.doubleToLongBits(dArray[n])) {
                    this.moveIndexToFirst(n);
                    return true;
                }
                n = n + 1 & this.mask;
            }
        }
        this.key[n] = d;
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n2 = this.first;
            this.link[n2] = this.link[n2] ^ (this.link[this.first] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[n] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
            this.first = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return false;
    }

    public boolean addAndMoveToLast(double d) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNull) {
                this.moveIndexToLast(this.n);
                return true;
            }
            this.containsNull = true;
            n = this.n;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            while (Double.doubleToLongBits(dArray[n]) != 0L) {
                if (Double.doubleToLongBits(d) == Double.doubleToLongBits(dArray[n])) {
                    this.moveIndexToLast(n);
                    return true;
                }
                n = n + 1 & this.mask;
            }
        }
        this.key[n] = d;
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n2 = this.last;
            this.link[n2] = this.link[n2] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0.0);
        this.last = -1;
        this.first = -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    protected void fixPointers(int n) {
        if (this.size == 0) {
            this.last = -1;
            this.first = -1;
            return;
        }
        if (this.first == n) {
            this.first = (int)this.link[n];
            if (0 <= this.first) {
                int n2 = this.first;
                this.link[n2] = this.link[n2] | 0xFFFFFFFF00000000L;
            }
            return;
        }
        if (this.last == n) {
            this.last = (int)(this.link[n] >>> 32);
            if (0 <= this.last) {
                int n3 = this.last;
                this.link[n3] = this.link[n3] | 0xFFFFFFFFL;
            }
            return;
        }
        long l = this.link[n];
        int n4 = (int)(l >>> 32);
        int n5 = (int)l;
        int n6 = n4;
        this.link[n6] = this.link[n6] ^ (this.link[n4] ^ l & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        int n7 = n5;
        this.link[n7] = this.link[n7] ^ (this.link[n5] ^ l & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
    }

    protected void fixPointers(int n, int n2) {
        if (this.size == 1) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
            return;
        }
        if (this.first == n) {
            this.first = n2;
            int n3 = (int)this.link[n];
            this.link[n3] = this.link[n3] ^ (this.link[(int)this.link[n]] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[n2] = this.link[n];
            return;
        }
        if (this.last == n) {
            this.last = n2;
            int n4 = (int)(this.link[n] >>> 32);
            this.link[n4] = this.link[n4] ^ (this.link[(int)(this.link[n] >>> 32)] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n2] = this.link[n];
            return;
        }
        long l = this.link[n];
        int n5 = (int)(l >>> 32);
        int n6 = (int)l;
        int n7 = n5;
        this.link[n7] = this.link[n7] ^ (this.link[n5] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        int n8 = n6;
        this.link[n8] = this.link[n8] ^ (this.link[n6] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
        this.link[n2] = l;
    }

    @Override
    public double firstDouble() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public double lastDouble() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public DoubleSortedSet tailSet(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleSortedSet headSet(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleSortedSet subSet(double d, double d2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleComparator comparator() {
        return null;
    }

    @Override
    public DoubleListIterator iterator(double d) {
        return new SetIterator(this, d);
    }

    @Override
    public DoubleListIterator iterator() {
        return new SetIterator(this);
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
        double[] dArray = this.key;
        int n2 = n - 1;
        double[] dArray2 = new double[n + 1];
        int n3 = this.first;
        int n4 = -1;
        int n5 = -1;
        long[] lArray = this.link;
        long[] lArray2 = new long[n + 1];
        this.first = -1;
        int n6 = this.size;
        while (n6-- != 0) {
            int n7;
            if (Double.doubleToLongBits(dArray[n3]) == 0L) {
                n7 = n;
            } else {
                n7 = (int)HashCommon.mix(Double.doubleToRawLongBits(dArray[n3])) & n2;
                while (Double.doubleToLongBits(dArray2[n7]) != 0L) {
                    n7 = n7 + 1 & n2;
                }
            }
            dArray2[n7] = dArray[n3];
            if (n4 != -1) {
                int n8 = n5;
                lArray2[n8] = lArray2[n8] ^ (lArray2[n5] ^ (long)n7 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n9 = n7;
                lArray2[n9] = lArray2[n9] ^ (lArray2[n7] ^ ((long)n5 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n5 = n7;
            } else {
                n5 = this.first = n7;
                lArray2[n7] = -1L;
            }
            int n10 = n3;
            n3 = (int)lArray[n3];
            n4 = n10;
        }
        this.link = lArray2;
        this.last = n5;
        if (n5 != -1) {
            int n11 = n5;
            lArray2[n11] = lArray2[n11] | 0xFFFFFFFFL;
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = dArray2;
    }

    public DoubleLinkedOpenHashSet clone() {
        DoubleLinkedOpenHashSet doubleLinkedOpenHashSet;
        try {
            doubleLinkedOpenHashSet = (DoubleLinkedOpenHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        doubleLinkedOpenHashSet.key = (double[])this.key.clone();
        doubleLinkedOpenHashSet.containsNull = this.containsNull;
        doubleLinkedOpenHashSet.link = (long[])this.link.clone();
        return doubleLinkedOpenHashSet;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        while (n2-- != 0) {
            while (Double.doubleToLongBits(this.key[n3]) == 0L) {
                ++n3;
            }
            n += HashCommon.double2int(this.key[n3]);
            ++n3;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        DoubleListIterator doubleListIterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            objectOutputStream.writeDouble(doubleListIterator.nextDouble());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new double[this.n + 1];
        double[] dArray = this.key;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            double d = objectInputStream.readDouble();
            if (Double.doubleToLongBits(d) == 0L) {
                n3 = this.n;
                this.containsNull = true;
            } else {
                n3 = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
                if (Double.doubleToLongBits(dArray[n3]) != 0L) {
                    while (Double.doubleToLongBits(dArray[n3 = n3 + 1 & this.mask]) != 0L) {
                    }
                }
            }
            dArray[n3] = d;
            if (this.first != -1) {
                int n4 = n;
                lArray[n4] = lArray[n4] ^ (lArray[n] ^ (long)n3 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n5 = n3;
                lArray[n5] = lArray[n5] ^ (lArray[n3] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n = n3;
                continue;
            }
            n = this.first = n3;
            int n6 = n3;
            lArray[n6] = lArray[n6] | 0xFFFFFFFF00000000L;
        }
        this.last = n;
        if (n != -1) {
            int n7 = n;
            lArray[n7] = lArray[n7] | 0xFFFFFFFFL;
        }
    }

    private void checkTable() {
    }

    @Override
    public DoubleBidirectionalIterator iterator() {
        return this.iterator();
    }

    @Override
    public DoubleBidirectionalIterator iterator(double d) {
        return this.iterator(d);
    }

    @Override
    public DoubleIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private class SetIterator
    implements DoubleListIterator {
        int prev;
        int next;
        int curr;
        int index;
        final DoubleLinkedOpenHashSet this$0;

        SetIterator(DoubleLinkedOpenHashSet doubleLinkedOpenHashSet) {
            this.this$0 = doubleLinkedOpenHashSet;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = doubleLinkedOpenHashSet.first;
            this.index = 0;
        }

        SetIterator(DoubleLinkedOpenHashSet doubleLinkedOpenHashSet, double d) {
            this.this$0 = doubleLinkedOpenHashSet;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (Double.doubleToLongBits(d) == 0L) {
                if (doubleLinkedOpenHashSet.containsNull) {
                    this.next = (int)doubleLinkedOpenHashSet.link[doubleLinkedOpenHashSet.n];
                    this.prev = doubleLinkedOpenHashSet.n;
                    return;
                }
                throw new NoSuchElementException("The key " + d + " does not belong to this set.");
            }
            if (Double.doubleToLongBits(doubleLinkedOpenHashSet.key[doubleLinkedOpenHashSet.last]) == Double.doubleToLongBits(d)) {
                this.prev = doubleLinkedOpenHashSet.last;
                this.index = doubleLinkedOpenHashSet.size;
                return;
            }
            double[] dArray = doubleLinkedOpenHashSet.key;
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & doubleLinkedOpenHashSet.mask;
            while (Double.doubleToLongBits(dArray[n]) != 0L) {
                if (Double.doubleToLongBits(dArray[n]) == Double.doubleToLongBits(d)) {
                    this.next = (int)doubleLinkedOpenHashSet.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & doubleLinkedOpenHashSet.mask;
            }
            throw new NoSuchElementException("The key " + d + " does not belong to this set.");
        }

        @Override
        public boolean hasNext() {
            return this.next != -1;
        }

        @Override
        public boolean hasPrevious() {
            return this.prev != -1;
        }

        @Override
        public double nextDouble() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int)this.this$0.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                ++this.index;
            }
            return this.this$0.key[this.curr];
        }

        @Override
        public double previousDouble() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                --this.index;
            }
            return this.this$0.key[this.curr];
        }

        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
                return;
            }
            if (this.next == -1) {
                this.index = this.this$0.size;
                return;
            }
            int n = this.this$0.first;
            this.index = 1;
            while (n != this.prev) {
                n = (int)this.this$0.link[n];
                ++this.index;
            }
        }

        @Override
        public int nextIndex() {
            this.ensureIndexKnown();
            return this.index;
        }

        @Override
        public int previousIndex() {
            this.ensureIndexKnown();
            return this.index - 1;
        }

        @Override
        public void remove() {
            this.ensureIndexKnown();
            if (this.curr == -1) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                --this.index;
                this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            } else {
                this.next = (int)this.this$0.link[this.curr];
            }
            --this.this$0.size;
            if (this.prev == -1) {
                this.this$0.first = this.next;
            } else {
                int n = this.prev;
                this.this$0.link[n] = this.this$0.link[n] ^ (this.this$0.link[this.prev] ^ (long)this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            if (this.next == -1) {
                this.this$0.last = this.prev;
            } else {
                int n = this.next;
                this.this$0.link[n] = this.this$0.link[n] ^ (this.this$0.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            }
            int n = this.curr;
            this.curr = -1;
            if (n != this.this$0.n) {
                double[] dArray = this.this$0.key;
                while (true) {
                    double d;
                    int n2 = n;
                    n = n2 + 1 & this.this$0.mask;
                    while (true) {
                        if (Double.doubleToLongBits(d = dArray[n]) == 0L) {
                            dArray[n2] = 0.0;
                            return;
                        }
                        int n3 = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
                        if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                        n = n + 1 & this.this$0.mask;
                    }
                    dArray[n2] = d;
                    if (this.next == n) {
                        this.next = n2;
                    }
                    if (this.prev == n) {
                        this.prev = n2;
                    }
                    this.this$0.fixPointers(n, n2);
                }
            }
            this.this$0.containsNull = false;
            this.this$0.key[this.this$0.n] = 0.0;
        }
    }
}

