/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleMap;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleSortedMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMap;
import it.unimi.dsi.fastutil.doubles.Double2DoubleSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
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
import java.util.function.DoubleUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Double2DoubleLinkedOpenHashMap
extends AbstractDouble2DoubleSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient double[] key;
    protected transient double[] value;
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
    protected transient Double2DoubleSortedMap.FastSortedEntrySet entries;
    protected transient DoubleSortedSet keys;
    protected transient DoubleCollection values;

    public Double2DoubleLinkedOpenHashMap(int n, float f) {
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
        this.value = new double[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Double2DoubleLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Double2DoubleLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Double2DoubleLinkedOpenHashMap(Map<? extends Double, ? extends Double> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Double2DoubleLinkedOpenHashMap(Map<? extends Double, ? extends Double> map) {
        this(map, 0.75f);
    }

    public Double2DoubleLinkedOpenHashMap(Double2DoubleMap double2DoubleMap, float f) {
        this(double2DoubleMap.size(), f);
        this.putAll(double2DoubleMap);
    }

    public Double2DoubleLinkedOpenHashMap(Double2DoubleMap double2DoubleMap) {
        this(double2DoubleMap, 0.75f);
    }

    public Double2DoubleLinkedOpenHashMap(double[] dArray, double[] dArray2, float f) {
        this(dArray.length, f);
        if (dArray.length != dArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + dArray.length + " and " + dArray2.length + ")");
        }
        for (int i = 0; i < dArray.length; ++i) {
            this.put(dArray[i], dArray2[i]);
        }
    }

    public Double2DoubleLinkedOpenHashMap(double[] dArray, double[] dArray2) {
        this(dArray, dArray2, 0.75f);
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

    private double removeEntry(int n) {
        double d = this.value[n];
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    private double removeNullEntry() {
        this.containsNullKey = false;
        double d = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    @Override
    public void putAll(Map<? extends Double, ? extends Double> map) {
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

    private void insert(int n, double d, double d2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = d;
        this.value[n] = d2;
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
    public double put(double d, double d2) {
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, d2);
            return this.defRetValue;
        }
        double d3 = this.value[n];
        this.value[n] = d2;
        return d3;
    }

    private double addToValue(int n, double d) {
        double d2 = this.value[n];
        this.value[n] = d2 + d;
        return d2;
    }

    public double addTo(double d, double d2) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, d2);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            double d3 = dArray[n];
            if (Double.doubleToLongBits(d3) != 0L) {
                if (Double.doubleToLongBits(d3) == Double.doubleToLongBits(d)) {
                    return this.addToValue(n, d2);
                }
                while (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.mask]) != 0L) {
                    if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d)) continue;
                    return this.addToValue(n, d2);
                }
            }
        }
        this.key[n] = d;
        this.value[n] = this.defRetValue + d2;
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
        return this.defRetValue;
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
    public double remove(double d) {
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

    private double setValue(int n, double d) {
        double d2 = this.value[n];
        this.value[n] = d;
        return d2;
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
        --this.size;
        double d = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
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
        --this.size;
        double d = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
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

    public double getAndMoveToFirst(double d) {
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

    public double getAndMoveToLast(double d) {
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

    public double putAndMoveToFirst(double d, double d2) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, d2);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            double d3 = dArray[n];
            if (Double.doubleToLongBits(d3) != 0L) {
                if (Double.doubleToLongBits(d3) == Double.doubleToLongBits(d)) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, d2);
                }
                while (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.mask]) != 0L) {
                    if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d)) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, d2);
                }
            }
        }
        this.key[n] = d;
        this.value[n] = d2;
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

    public double putAndMoveToLast(double d, double d2) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, d2);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            double d3 = dArray[n];
            if (Double.doubleToLongBits(d3) != 0L) {
                if (Double.doubleToLongBits(d3) == Double.doubleToLongBits(d)) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, d2);
                }
                while (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.mask]) != 0L) {
                    if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d)) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, d2);
                }
            }
        }
        this.key[n] = d;
        this.value[n] = d2;
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
    public double get(double d) {
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
    public boolean containsValue(double d) {
        double[] dArray = this.value;
        double[] dArray2 = this.key;
        if (this.containsNullKey && Double.doubleToLongBits(dArray[this.n]) == Double.doubleToLongBits(d)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray2[n]) == 0L || Double.doubleToLongBits(dArray[n]) != Double.doubleToLongBits(d)) continue;
            return false;
        }
        return true;
    }

    @Override
    public double getOrDefault(double d, double d2) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey ? this.value[this.n] : d2;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d3 = dArray[n];
        if (Double.doubleToLongBits(d3) == 0L) {
            return d2;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3)) {
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return d2;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d3));
        return this.value[n];
    }

    @Override
    public double putIfAbsent(double d, double d2) {
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, d, d2);
        return this.defRetValue;
    }

    @Override
    public boolean remove(double d, double d2) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey && Double.doubleToLongBits(d2) == Double.doubleToLongBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d3 = dArray[n];
        if (Double.doubleToLongBits(d3) == 0L) {
            return true;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3) && Double.doubleToLongBits(d2) == Double.doubleToLongBits(this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d3) || Double.doubleToLongBits(d2) != Double.doubleToLongBits(this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(double d, double d2, double d3) {
        int n = this.find(d);
        if (n < 0 || Double.doubleToLongBits(d2) != Double.doubleToLongBits(this.value[n])) {
            return true;
        }
        this.value[n] = d3;
        return false;
    }

    @Override
    public double replace(double d, double d2) {
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        double d3 = this.value[n];
        this.value[n] = d2;
        return d3;
    }

    @Override
    public double computeIfAbsent(double d, DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        double d2 = doubleUnaryOperator.applyAsDouble(d);
        this.insert(-n - 1, d, d2);
        return d2;
    }

    @Override
    public double computeIfAbsentNullable(double d, DoubleFunction<? extends Double> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        Double d2 = doubleFunction.apply(d);
        if (d2 == null) {
            return this.defRetValue;
        }
        double d3 = d2;
        this.insert(-n - 1, d, d3);
        return d3;
    }

    @Override
    public double computeIfPresent(double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        Double d2 = biFunction.apply((Double)d, (Double)this.value[n]);
        if (d2 == null) {
            if (Double.doubleToLongBits(d) == 0L) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = d2;
        return this.value[n];
    }

    @Override
    public double compute(double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        Double d2 = biFunction.apply((Double)d, n >= 0 ? Double.valueOf(this.value[n]) : null);
        if (d2 == null) {
            if (n >= 0) {
                if (Double.doubleToLongBits(d) == 0L) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        double d3 = d2;
        if (n < 0) {
            this.insert(-n - 1, d, d3);
            return d3;
        }
        this.value[n] = d3;
        return this.value[n];
    }

    @Override
    public double merge(double d, double d2, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, d2);
            return d2;
        }
        Double d3 = biFunction.apply((Double)this.value[n], (Double)d2);
        if (d3 == null) {
            if (Double.doubleToLongBits(d) == 0L) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = d3;
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
    public Double2DoubleSortedMap tailMap(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double2DoubleSortedMap headMap(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double2DoubleSortedMap subMap(double d, double d2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleComparator comparator() {
        return null;
    }

    @Override
    public Double2DoubleSortedMap.FastSortedEntrySet double2DoubleEntrySet() {
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
    public DoubleCollection values() {
        if (this.values == null) {
            this.values = new AbstractDoubleCollection(this){
                final Double2DoubleLinkedOpenHashMap this$0;
                {
                    this.this$0 = double2DoubleLinkedOpenHashMap;
                }

                @Override
                public DoubleIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(double d) {
                    return this.this$0.containsValue(d);
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public void forEach(DoubleConsumer doubleConsumer) {
                    if (this.this$0.containsNullKey) {
                        doubleConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (Double.doubleToLongBits(this.this$0.key[n]) == 0L) continue;
                        doubleConsumer.accept(this.this$0.value[n]);
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
        double[] dArray2 = this.value;
        int n2 = n - 1;
        double[] dArray3 = new double[n + 1];
        double[] dArray4 = new double[n + 1];
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
                while (Double.doubleToLongBits(dArray3[n7]) != 0L) {
                    n7 = n7 + 1 & n2;
                }
            }
            dArray3[n7] = dArray[n3];
            dArray4[n7] = dArray2[n3];
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
        this.key = dArray3;
        this.value = dArray4;
    }

    public Double2DoubleLinkedOpenHashMap clone() {
        Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap;
        try {
            double2DoubleLinkedOpenHashMap = (Double2DoubleLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2DoubleLinkedOpenHashMap.keys = null;
        double2DoubleLinkedOpenHashMap.values = null;
        double2DoubleLinkedOpenHashMap.entries = null;
        double2DoubleLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        double2DoubleLinkedOpenHashMap.key = (double[])this.key.clone();
        double2DoubleLinkedOpenHashMap.value = (double[])this.value.clone();
        double2DoubleLinkedOpenHashMap.link = (long[])this.link.clone();
        return double2DoubleLinkedOpenHashMap;
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
            n += (n4 ^= HashCommon.double2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.double2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        double[] dArray = this.key;
        double[] dArray2 = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeDouble(dArray[n2]);
            objectOutputStream.writeDouble(dArray2[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new double[this.n + 1];
        double[] dArray = this.key;
        this.value = new double[this.n + 1];
        double[] dArray2 = this.value;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            double d = objectInputStream.readDouble();
            double d2 = objectInputStream.readDouble();
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
            dArray2[n3] = d2;
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
    public ObjectSortedSet double2DoubleEntrySet() {
        return this.double2DoubleEntrySet();
    }

    @Override
    public DoubleSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet double2DoubleEntrySet() {
        return this.double2DoubleEntrySet();
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

    static double access$100(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
        return double2DoubleLinkedOpenHashMap.removeNullEntry();
    }

    static double access$200(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, int n) {
        return double2DoubleLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements DoubleListIterator {
        final Double2DoubleLinkedOpenHashMap this$0;

        @Override
        public double previousDouble() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            super(double2DoubleLinkedOpenHashMap);
        }

        @Override
        public double nextDouble() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractDoubleSortedSet {
        final Double2DoubleLinkedOpenHashMap this$0;

        private KeySet(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
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

        KeySet(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, 1 var2_2) {
            this(double2DoubleLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements DoubleListIterator {
        final Double2DoubleLinkedOpenHashMap this$0;

        public KeyIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, double d) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            super(double2DoubleLinkedOpenHashMap, d, null);
        }

        @Override
        public double previousDouble() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            super(double2DoubleLinkedOpenHashMap);
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
    extends AbstractObjectSortedSet<Double2DoubleMap.Entry>
    implements Double2DoubleSortedMap.FastSortedEntrySet {
        final Double2DoubleLinkedOpenHashMap this$0;

        private MapEntrySet(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Double2DoubleMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Double2DoubleMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Double2DoubleMap.Entry> subSet(Double2DoubleMap.Entry entry, Double2DoubleMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Double2DoubleMap.Entry> headSet(Double2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Double2DoubleMap.Entry> tailSet(Double2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Double2DoubleMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Double2DoubleMap.Entry last() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            double d = (Double)entry.getKey();
            double d2 = (Double)entry.getValue();
            if (Double.doubleToLongBits(d) == 0L) {
                return this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d2);
            }
            double[] dArray = this.this$0.key;
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
            double d3 = dArray[n];
            if (Double.doubleToLongBits(d3) == 0L) {
                return true;
            }
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3)) {
                return Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d2);
            }
            do {
                if (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d3));
            return Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d2);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            double d = (Double)entry.getKey();
            double d2 = (Double)entry.getValue();
            if (Double.doubleToLongBits(d) == 0L) {
                if (this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d2)) {
                    Double2DoubleLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            double[] dArray = this.this$0.key;
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
            double d3 = dArray[n];
            if (Double.doubleToLongBits(d3) == 0L) {
                return true;
            }
            if (Double.doubleToLongBits(d3) == Double.doubleToLongBits(d)) {
                if (Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d2)) {
                    Double2DoubleLinkedOpenHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || Double.doubleToLongBits(this.this$0.value[n]) != Double.doubleToLongBits(d2));
            Double2DoubleLinkedOpenHashMap.access$200(this.this$0, n);
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
        public ObjectListIterator<Double2DoubleMap.Entry> iterator(Double2DoubleMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getDoubleKey());
        }

        @Override
        public ObjectListIterator<Double2DoubleMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Double2DoubleMap.Entry> fastIterator(Double2DoubleMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getDoubleKey());
        }

        @Override
        public void forEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractDouble2DoubleMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
            AbstractDouble2DoubleMap.BasicEntry basicEntry = new AbstractDouble2DoubleMap.BasicEntry();
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
            return this.tailSet((Double2DoubleMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Double2DoubleMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Double2DoubleMap.Entry)object, (Double2DoubleMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Double2DoubleMap.Entry)object);
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
            return this.tailSet((Double2DoubleMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Double2DoubleMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Double2DoubleMap.Entry)object, (Double2DoubleMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Double2DoubleMap.Entry entry) {
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

        MapEntrySet(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, 1 var2_2) {
            this(double2DoubleLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Double2DoubleMap.Entry> {
        final MapEntry entry;
        final Double2DoubleLinkedOpenHashMap this$0;

        public FastEntryIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            super(double2DoubleLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, double d) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            super(double2DoubleLinkedOpenHashMap, d, null);
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
            super.add((Double2DoubleMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Double2DoubleMap.Entry)object);
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
    implements ObjectListIterator<Double2DoubleMap.Entry> {
        private MapEntry entry;
        final Double2DoubleLinkedOpenHashMap this$0;

        public EntryIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            super(double2DoubleLinkedOpenHashMap);
        }

        public EntryIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, double d) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            super(double2DoubleLinkedOpenHashMap, d, null);
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
            super.add((Double2DoubleMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Double2DoubleMap.Entry)object);
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
        final Double2DoubleLinkedOpenHashMap this$0;

        protected MapIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = double2DoubleLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, double d) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (Double.doubleToLongBits(d) == 0L) {
                if (double2DoubleLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)double2DoubleLinkedOpenHashMap.link[double2DoubleLinkedOpenHashMap.n];
                    this.prev = double2DoubleLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + d + " does not belong to this map.");
            }
            if (Double.doubleToLongBits(double2DoubleLinkedOpenHashMap.key[double2DoubleLinkedOpenHashMap.last]) == Double.doubleToLongBits(d)) {
                this.prev = double2DoubleLinkedOpenHashMap.last;
                this.index = double2DoubleLinkedOpenHashMap.size;
                return;
            }
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & double2DoubleLinkedOpenHashMap.mask;
            while (Double.doubleToLongBits(double2DoubleLinkedOpenHashMap.key[n]) != 0L) {
                if (Double.doubleToLongBits(double2DoubleLinkedOpenHashMap.key[n]) == Double.doubleToLongBits(d)) {
                    this.next = (int)double2DoubleLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & double2DoubleLinkedOpenHashMap.mask;
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

        public void set(Double2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Double2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, double d, 1 var4_3) {
            this(double2DoubleLinkedOpenHashMap, d);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Double2DoubleMap.Entry,
    Map.Entry<Double, Double> {
        int index;
        final Double2DoubleLinkedOpenHashMap this$0;

        MapEntry(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap, int n) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Double2DoubleLinkedOpenHashMap double2DoubleLinkedOpenHashMap) {
            this.this$0 = double2DoubleLinkedOpenHashMap;
        }

        @Override
        public double getDoubleKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public double getDoubleValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public double setValue(double d) {
            double d2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = d;
            return d2;
        }

        @Override
        @Deprecated
        public Double getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Double getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Double setValue(Double d) {
            return this.setValue((double)d);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Double.doubleToLongBits(this.this$0.key[this.index]) == Double.doubleToLongBits((Double)entry.getKey()) && Double.doubleToLongBits(this.this$0.value[this.index]) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.this$0.key[this.index]) ^ HashCommon.double2int(this.this$0.value[this.index]);
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Double)object);
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

