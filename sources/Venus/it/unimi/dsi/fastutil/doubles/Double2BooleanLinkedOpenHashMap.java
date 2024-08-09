/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanSortedMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Double2BooleanLinkedOpenHashMap
extends AbstractDouble2BooleanSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient double[] key;
    protected transient boolean[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Double2BooleanSortedMap.FastSortedEntrySet entries;
    protected transient DoubleSortedSet keys;
    protected transient BooleanCollection values;

    public Double2BooleanLinkedOpenHashMap(int n, float f) {
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
        this.value = new boolean[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Double2BooleanLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Double2BooleanLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Double2BooleanLinkedOpenHashMap(Map<? extends Double, ? extends Boolean> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Double2BooleanLinkedOpenHashMap(Map<? extends Double, ? extends Boolean> map) {
        this(map, 0.75f);
    }

    public Double2BooleanLinkedOpenHashMap(Double2BooleanMap double2BooleanMap, float f) {
        this(double2BooleanMap.size(), f);
        this.putAll(double2BooleanMap);
    }

    public Double2BooleanLinkedOpenHashMap(Double2BooleanMap double2BooleanMap) {
        this(double2BooleanMap, 0.75f);
    }

    public Double2BooleanLinkedOpenHashMap(double[] dArray, boolean[] blArray, float f) {
        this(dArray.length, f);
        if (dArray.length != blArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + dArray.length + " and " + blArray.length + ")");
        }
        for (int i = 0; i < dArray.length; ++i) {
            this.put(dArray[i], blArray[i]);
        }
    }

    public Double2BooleanLinkedOpenHashMap(double[] dArray, boolean[] blArray) {
        this(dArray, blArray, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
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

    private boolean removeEntry(int n) {
        boolean bl = this.value[n];
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return bl;
    }

    private boolean removeNullEntry() {
        this.containsNullKey = false;
        boolean bl = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return bl;
    }

    @Override
    public void putAll(Map<? extends Double, ? extends Boolean> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return -(n + 1);
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return n;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return -(n + 1);
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return n;
    }

    private void insert(int n, double d, boolean bl) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = d;
        this.value[n] = bl;
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
    }

    @Override
    public boolean put(double d, boolean bl) {
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, bl);
            return this.defRetValue;
        }
        boolean bl2 = this.value[n];
        this.value[n] = bl;
        return bl2;
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
            this.value[n2] = this.value[n];
            this.fixPointers(n, n2);
        }
    }

    @Override
    public boolean remove(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return this.defRetValue;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return this.removeEntry(n);
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return this.removeEntry(n);
    }

    private boolean setValue(int n, boolean bl) {
        boolean bl2 = this.value[n];
        this.value[n] = bl;
        return bl2;
    }

    public boolean removeFirstBoolean() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.first;
        this.first = (int)this.link[n];
        if (0 <= this.first) {
            int n2 = this.first;
            this.link[n2] = this.link[n2] | 0xFFFFFFFF00000000L;
        }
        --this.size;
        boolean bl = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return bl;
    }

    public boolean removeLastBoolean() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.last;
        this.last = (int)(this.link[n] >>> 32);
        if (0 <= this.last) {
            int n2 = this.last;
            this.link[n2] = this.link[n2] | 0xFFFFFFFFL;
        }
        --this.size;
        boolean bl = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return bl;
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

    public boolean getAndMoveToFirst(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return this.defRetValue;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            this.moveIndexToFirst(n);
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        this.moveIndexToFirst(n);
        return this.value[n];
    }

    public boolean getAndMoveToLast(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return this.defRetValue;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            this.moveIndexToLast(n);
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        this.moveIndexToLast(n);
        return this.value[n];
    }

    public boolean putAndMoveToFirst(double d, boolean bl) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, bl);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) != 0L) {
                if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(d)) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, bl);
                }
                while (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) {
                    if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d)) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, bl);
                }
            }
        }
        this.key[n] = d;
        this.value[n] = bl;
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
        return this.defRetValue;
    }

    public boolean putAndMoveToLast(double d, boolean bl) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, bl);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) != 0L) {
                if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(d)) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, bl);
                }
                while (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) {
                    if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d)) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, bl);
                }
            }
        }
        this.key[n] = d;
        this.value[n] = bl;
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
        return this.defRetValue;
    }

    @Override
    public boolean get(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return this.defRetValue;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey;
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

    @Override
    public boolean containsValue(boolean bl) {
        boolean[] blArray = this.value;
        double[] dArray = this.key;
        if (this.containsNullKey && blArray[this.n] == bl) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray[n]) == 0L || blArray[n] != bl) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean getOrDefault(double d, boolean bl) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey ? this.value[this.n] : bl;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return bl;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return bl;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return this.value[n];
    }

    @Override
    public boolean putIfAbsent(double d, boolean bl) {
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, d, bl);
        return this.defRetValue;
    }

    @Override
    public boolean remove(double d, boolean bl) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey && bl == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return true;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2) && bl == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2) || bl != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(double d, boolean bl, boolean bl2) {
        int n = this.find(d);
        if (n < 0 || bl != this.value[n]) {
            return true;
        }
        this.value[n] = bl2;
        return false;
    }

    @Override
    public boolean replace(double d, boolean bl) {
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        boolean bl2 = this.value[n];
        this.value[n] = bl;
        return bl2;
    }

    @Override
    public boolean computeIfAbsent(double d, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        boolean bl = doublePredicate.test(d);
        this.insert(-n - 1, d, bl);
        return bl;
    }

    @Override
    public boolean computeIfAbsentNullable(double d, DoubleFunction<? extends Boolean> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        Boolean bl = doubleFunction.apply(d);
        if (bl == null) {
            return this.defRetValue;
        }
        boolean bl2 = bl;
        this.insert(-n - 1, d, bl2);
        return bl2;
    }

    @Override
    public boolean computeIfPresent(double d, BiFunction<? super Double, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        Boolean bl = biFunction.apply((Double)d, (Boolean)this.value[n]);
        if (bl == null) {
            if (Double.doubleToLongBits(d) == 0L) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = bl;
        return this.value[n];
    }

    @Override
    public boolean compute(double d, BiFunction<? super Double, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        Boolean bl = biFunction.apply((Double)d, n >= 0 ? Boolean.valueOf(this.value[n]) : null);
        if (bl == null) {
            if (n >= 0) {
                if (Double.doubleToLongBits(d) == 0L) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        boolean bl2 = bl;
        if (n < 0) {
            this.insert(-n - 1, d, bl2);
            return bl2;
        }
        this.value[n] = bl2;
        return this.value[n];
    }

    @Override
    public boolean merge(double d, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, bl);
            return bl;
        }
        Boolean bl2 = biFunction.apply((Boolean)this.value[n], (Boolean)bl);
        if (bl2 == null) {
            if (Double.doubleToLongBits(d) == 0L) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = bl2;
        return this.value[n];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
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
    public double firstDoubleKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public double lastDoubleKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public Double2BooleanSortedMap tailMap(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double2BooleanSortedMap headMap(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double2BooleanSortedMap subMap(double d, double d2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleComparator comparator() {
        return null;
    }

    @Override
    public Double2BooleanSortedMap.FastSortedEntrySet double2BooleanEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public DoubleSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public BooleanCollection values() {
        if (this.values == null) {
            this.values = new AbstractBooleanCollection(this){
                final Double2BooleanLinkedOpenHashMap this$0;
                {
                    this.this$0 = double2BooleanLinkedOpenHashMap;
                }

                @Override
                public BooleanIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(boolean bl) {
                    return this.this$0.containsValue(bl);
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public void forEach(BooleanConsumer booleanConsumer) {
                    if (this.this$0.containsNullKey) {
                        booleanConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (Double.doubleToLongBits(this.this$0.key[n]) == 0L) continue;
                        booleanConsumer.accept(this.this$0.value[n]);
                    }
                }

                @Override
                public Iterator iterator() {
                    return this.iterator();
                }
            };
        }
        return this.values;
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
        boolean[] blArray = this.value;
        int n2 = n - 1;
        double[] dArray2 = new double[n + 1];
        boolean[] blArray2 = new boolean[n + 1];
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
            blArray2[n7] = blArray[n3];
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
        this.value = blArray2;
    }

    public Double2BooleanLinkedOpenHashMap clone() {
        Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap;
        try {
            double2BooleanLinkedOpenHashMap = (Double2BooleanLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2BooleanLinkedOpenHashMap.keys = null;
        double2BooleanLinkedOpenHashMap.values = null;
        double2BooleanLinkedOpenHashMap.entries = null;
        double2BooleanLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        double2BooleanLinkedOpenHashMap.key = (double[])this.key.clone();
        double2BooleanLinkedOpenHashMap.value = (boolean[])this.value.clone();
        double2BooleanLinkedOpenHashMap.link = (long[])this.link.clone();
        return double2BooleanLinkedOpenHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (Double.doubleToLongBits(this.key[n3]) == 0L) {
                ++n3;
            }
            n4 = HashCommon.double2int(this.key[n3]);
            n += (n4 ^= this.value[n3] ? 1231 : 1237);
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n] ? 1231 : 1237;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        double[] dArray = this.key;
        boolean[] blArray = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeDouble(dArray[n2]);
            objectOutputStream.writeBoolean(blArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new double[this.n + 1];
        double[] dArray = this.key;
        this.value = new boolean[this.n + 1];
        boolean[] blArray = this.value;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            double d = objectInputStream.readDouble();
            boolean bl = objectInputStream.readBoolean();
            if (Double.doubleToLongBits(d) == 0L) {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
                while (Double.doubleToLongBits(dArray[n3]) != 0L) {
                    n3 = n3 + 1 & this.mask;
                }
            }
            dArray[n3] = d;
            blArray[n3] = bl;
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
    public ObjectSortedSet double2BooleanEntrySet() {
        return this.double2BooleanEntrySet();
    }

    @Override
    public DoubleSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet double2BooleanEntrySet() {
        return this.double2BooleanEntrySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static boolean access$100(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
        return double2BooleanLinkedOpenHashMap.removeNullEntry();
    }

    static boolean access$200(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, int n) {
        return double2BooleanLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements BooleanListIterator {
        final Double2BooleanLinkedOpenHashMap this$0;

        @Override
        public boolean previousBoolean() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            super(double2BooleanLinkedOpenHashMap);
        }

        @Override
        public boolean nextBoolean() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractDoubleSortedSet {
        final Double2BooleanLinkedOpenHashMap this$0;

        private KeySet(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
        }

        @Override
        public DoubleListIterator iterator(double d) {
            return new KeyIterator(this.this$0, d);
        }

        @Override
        public DoubleListIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(DoubleConsumer doubleConsumer) {
            if (this.this$0.containsNullKey) {
                doubleConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                double d = this.this$0.key[n];
                if (Double.doubleToLongBits(d) == 0L) continue;
                doubleConsumer.accept(d);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(double d) {
            return this.this$0.containsKey(d);
        }

        @Override
        public boolean remove(double d) {
            int n = this.this$0.size;
            this.this$0.remove(d);
            return this.this$0.size != n;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public double firstDouble() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }

        @Override
        public double lastDouble() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }

        @Override
        public DoubleComparator comparator() {
            return null;
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

        KeySet(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, 1 var2_2) {
            this(double2BooleanLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements DoubleListIterator {
        final Double2BooleanLinkedOpenHashMap this$0;

        public KeyIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, double d) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            super(double2BooleanLinkedOpenHashMap, d, null);
        }

        @Override
        public double previousDouble() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            super(double2BooleanLinkedOpenHashMap);
        }

        @Override
        public double nextDouble() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class MapEntrySet
    extends AbstractObjectSortedSet<Double2BooleanMap.Entry>
    implements Double2BooleanSortedMap.FastSortedEntrySet {
        final Double2BooleanLinkedOpenHashMap this$0;

        private MapEntrySet(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Double2BooleanMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Double2BooleanMap.Entry> subSet(Double2BooleanMap.Entry entry, Double2BooleanMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Double2BooleanMap.Entry> headSet(Double2BooleanMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Double2BooleanMap.Entry> tailSet(Double2BooleanMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Double2BooleanMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Double2BooleanMap.Entry last() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.last);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Double)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            double d = (Double)entry.getKey();
            boolean bl = (Boolean)entry.getValue();
            if (Double.doubleToLongBits(d) == 0L) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == bl;
            }
            double[] dArray = this.this$0.key;
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) == 0L) {
                return true;
            }
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
                return this.this$0.value[n] == bl;
            }
            do {
                if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
            return this.this$0.value[n] == bl;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Double)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            double d = (Double)entry.getKey();
            boolean bl = (Boolean)entry.getValue();
            if (Double.doubleToLongBits(d) == 0L) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == bl) {
                    Double2BooleanLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            double[] dArray = this.this$0.key;
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) == 0L) {
                return true;
            }
            if (Double.doubleToLongBits(d2) == Double.doubleToLongBits(d)) {
                if (this.this$0.value[n] == bl) {
                    Double2BooleanLinkedOpenHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d) || this.this$0.value[n] != bl);
            Double2BooleanLinkedOpenHashMap.access$200(this.this$0, n);
            return false;
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public ObjectListIterator<Double2BooleanMap.Entry> iterator(Double2BooleanMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getDoubleKey());
        }

        @Override
        public ObjectListIterator<Double2BooleanMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Double2BooleanMap.Entry> fastIterator(Double2BooleanMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getDoubleKey());
        }

        @Override
        public void forEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractDouble2BooleanMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
            AbstractDouble2BooleanMap.BasicEntry basicEntry = new AbstractDouble2BooleanMap.BasicEntry();
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                basicEntry.key = this.this$0.key[n3];
                basicEntry.value = this.this$0.value[n3];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public ObjectSortedSet tailSet(Object object) {
            return this.tailSet((Double2BooleanMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Double2BooleanMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Double2BooleanMap.Entry)object, (Double2BooleanMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Double2BooleanMap.Entry)object);
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public Object last() {
            return this.last();
        }

        @Override
        public Object first() {
            return this.first();
        }

        @Override
        public SortedSet tailSet(Object object) {
            return this.tailSet((Double2BooleanMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Double2BooleanMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Double2BooleanMap.Entry)object, (Double2BooleanMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Double2BooleanMap.Entry entry) {
            return this.fastIterator(entry);
        }

        @Override
        public ObjectBidirectionalIterator fastIterator() {
            return this.fastIterator();
        }

        @Override
        public ObjectIterator fastIterator() {
            return this.fastIterator();
        }

        MapEntrySet(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, 1 var2_2) {
            this(double2BooleanLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Double2BooleanMap.Entry> {
        final MapEntry entry;
        final Double2BooleanLinkedOpenHashMap this$0;

        public FastEntryIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            super(double2BooleanLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, double d) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            super(double2BooleanLinkedOpenHashMap, d, null);
            this.entry = new MapEntry(this.this$0);
        }

        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }

        @Override
        public MapEntry previous() {
            this.entry.index = this.previousEntry();
            return this.entry;
        }

        @Override
        public void add(Object object) {
            super.add((Double2BooleanMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Double2BooleanMap.Entry)object);
        }

        @Override
        public Object next() {
            return this.next();
        }

        @Override
        public Object previous() {
            return this.previous();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectListIterator<Double2BooleanMap.Entry> {
        private MapEntry entry;
        final Double2BooleanLinkedOpenHashMap this$0;

        public EntryIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            super(double2BooleanLinkedOpenHashMap);
        }

        public EntryIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, double d) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            super(double2BooleanLinkedOpenHashMap, d, null);
        }

        @Override
        public MapEntry next() {
            this.entry = new MapEntry(this.this$0, this.nextEntry());
            return this.entry;
        }

        @Override
        public MapEntry previous() {
            this.entry = new MapEntry(this.this$0, this.previousEntry());
            return this.entry;
        }

        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }

        @Override
        public void add(Object object) {
            super.add((Double2BooleanMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Double2BooleanMap.Entry)object);
        }

        @Override
        public Object next() {
            return this.next();
        }

        @Override
        public Object previous() {
            return this.previous();
        }
    }

    private class MapIterator {
        int prev;
        int next;
        int curr;
        int index;
        final Double2BooleanLinkedOpenHashMap this$0;

        protected MapIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = double2BooleanLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, double d) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (Double.doubleToLongBits(d) == 0L) {
                if (double2BooleanLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)double2BooleanLinkedOpenHashMap.link[double2BooleanLinkedOpenHashMap.n];
                    this.prev = double2BooleanLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + d + " does not belong to this map.");
            }
            if (Double.doubleToLongBits(double2BooleanLinkedOpenHashMap.key[double2BooleanLinkedOpenHashMap.last]) == Double.doubleToLongBits(d)) {
                this.prev = double2BooleanLinkedOpenHashMap.last;
                this.index = double2BooleanLinkedOpenHashMap.size;
                return;
            }
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & double2BooleanLinkedOpenHashMap.mask;
            while (Double.doubleToLongBits(double2BooleanLinkedOpenHashMap.key[n]) != 0L) {
                if (Double.doubleToLongBits(double2BooleanLinkedOpenHashMap.key[n]) == Double.doubleToLongBits(d)) {
                    this.next = (int)double2BooleanLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & double2BooleanLinkedOpenHashMap.mask;
            }
            throw new NoSuchElementException("The key " + d + " does not belong to this map.");
        }

        public boolean hasNext() {
            return this.next != -1;
        }

        public boolean hasPrevious() {
            return this.prev != -1;
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

        public int nextIndex() {
            this.ensureIndexKnown();
            return this.index;
        }

        public int previousIndex() {
            this.ensureIndexKnown();
            return this.index - 1;
        }

        public int nextEntry() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int)this.this$0.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                ++this.index;
            }
            return this.curr;
        }

        public int previousEntry() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                --this.index;
            }
            return this.curr;
        }

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
                    this.this$0.value[n2] = this.this$0.value[n];
                    if (this.next == n) {
                        this.next = n2;
                    }
                    if (this.prev == n) {
                        this.prev = n2;
                    }
                    this.this$0.fixPointers(n, n2);
                }
            }
            this.this$0.containsNullKey = false;
        }

        public int skip(int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - n2 - 1;
        }

        public int back(int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasPrevious()) {
                this.previousEntry();
            }
            return n - n2 - 1;
        }

        public void set(Double2BooleanMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Double2BooleanMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, double d, 1 var4_3) {
            this(double2BooleanLinkedOpenHashMap, d);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Double2BooleanMap.Entry,
    Map.Entry<Double, Boolean> {
        int index;
        final Double2BooleanLinkedOpenHashMap this$0;

        MapEntry(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap, int n) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Double2BooleanLinkedOpenHashMap double2BooleanLinkedOpenHashMap) {
            this.this$0 = double2BooleanLinkedOpenHashMap;
        }

        @Override
        public double getDoubleKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public boolean getBooleanValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public boolean setValue(boolean bl) {
            boolean bl2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = bl;
            return bl2;
        }

        @Override
        @Deprecated
        public Double getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Boolean getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Boolean setValue(Boolean bl) {
            return this.setValue((boolean)bl);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Double.doubleToLongBits(this.this$0.key[this.index]) == Double.doubleToLongBits((Double)entry.getKey()) && this.this$0.value[this.index] == (Boolean)entry.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.this$0.key[this.index]) ^ (this.this$0.value[this.index] ? 1231 : 1237);
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Boolean)object);
        }

        @Override
        @Deprecated
        public Object getValue() {
            return this.getValue();
        }

        @Override
        @Deprecated
        public Object getKey() {
            return this.getKey();
        }
    }
}

