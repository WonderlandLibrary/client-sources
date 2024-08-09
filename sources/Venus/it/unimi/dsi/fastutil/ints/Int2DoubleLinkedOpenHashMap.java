/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleMap;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleSortedMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
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
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Int2DoubleLinkedOpenHashMap
extends AbstractInt2DoubleSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
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
    protected transient Int2DoubleSortedMap.FastSortedEntrySet entries;
    protected transient IntSortedSet keys;
    protected transient DoubleCollection values;

    public Int2DoubleLinkedOpenHashMap(int n, float f) {
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
        this.value = new double[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Int2DoubleLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Int2DoubleLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Int2DoubleLinkedOpenHashMap(Map<? extends Integer, ? extends Double> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Int2DoubleLinkedOpenHashMap(Map<? extends Integer, ? extends Double> map) {
        this(map, 0.75f);
    }

    public Int2DoubleLinkedOpenHashMap(Int2DoubleMap int2DoubleMap, float f) {
        this(int2DoubleMap.size(), f);
        this.putAll(int2DoubleMap);
    }

    public Int2DoubleLinkedOpenHashMap(Int2DoubleMap int2DoubleMap) {
        this(int2DoubleMap, 0.75f);
    }

    public Int2DoubleLinkedOpenHashMap(int[] nArray, double[] dArray, float f) {
        this(nArray.length, f);
        if (nArray.length != dArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + nArray.length + " and " + dArray.length + ")");
        }
        for (int i = 0; i < nArray.length; ++i) {
            this.put(nArray[i], dArray[i]);
        }
    }

    public Int2DoubleLinkedOpenHashMap(int[] nArray, double[] dArray) {
        this(nArray, dArray, 0.75f);
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
    public void putAll(Map<? extends Integer, ? extends Double> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(int n) {
        if (n == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return -(n2 + 1);
        }
        if (n == n3) {
            return n2;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return -(n2 + 1);
        } while (n != n3);
        return n2;
    }

    private void insert(int n, int n2, double d) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = n2;
        this.value[n] = d;
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n3 = this.last;
            this.link[n3] = this.link[n3] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public double put(int n, double d) {
        int n2 = this.find(n);
        if (n2 < 0) {
            this.insert(-n2 - 1, n, d);
            return this.defRetValue;
        }
        double d2 = this.value[n2];
        this.value[n2] = d;
        return d2;
    }

    private double addToValue(int n, double d) {
        double d2 = this.value[n];
        this.value[n] = d2 + d;
        return d2;
    }

    public double addTo(int n, double d) {
        int n2;
        if (n == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, d);
            }
            n2 = this.n;
            this.containsNullKey = true;
        } else {
            int[] nArray = this.key;
            n2 = HashCommon.mix(n) & this.mask;
            int n3 = nArray[n2];
            if (n3 != 0) {
                if (n3 == n) {
                    return this.addToValue(n2, d);
                }
                while ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (n3 != n) continue;
                    return this.addToValue(n2, d);
                }
            }
        }
        this.key[n2] = n;
        this.value[n2] = this.defRetValue + d;
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n4 = this.last;
            this.link[n4] = this.link[n4] ^ (this.link[this.last] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n2] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
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
            this.value[n3] = this.value[n];
            this.fixPointers(n, n3);
        }
    }

    @Override
    public double remove(int n) {
        if (n == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return this.defRetValue;
        }
        if (n == n3) {
            return this.removeEntry(n2);
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (n != n3);
        return this.removeEntry(n2);
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

    public double getAndMoveToFirst(int n) {
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return this.defRetValue;
        }
        if (n == n3) {
            this.moveIndexToFirst(n2);
            return this.value[n2];
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (n != n3);
        this.moveIndexToFirst(n2);
        return this.value[n2];
    }

    public double getAndMoveToLast(int n) {
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return this.defRetValue;
        }
        if (n == n3) {
            this.moveIndexToLast(n2);
            return this.value[n2];
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (n != n3);
        this.moveIndexToLast(n2);
        return this.value[n2];
    }

    public double putAndMoveToFirst(int n, double d) {
        int n2;
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, d);
            }
            this.containsNullKey = true;
            n2 = this.n;
        } else {
            int[] nArray = this.key;
            n2 = HashCommon.mix(n) & this.mask;
            int n3 = nArray[n2];
            if (n3 != 0) {
                if (n3 == n) {
                    this.moveIndexToFirst(n2);
                    return this.setValue(n2, d);
                }
                while ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (n3 != n) continue;
                    this.moveIndexToFirst(n2);
                    return this.setValue(n2, d);
                }
            }
        }
        this.key[n2] = n;
        this.value[n2] = d;
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n4 = this.first;
            this.link[n4] = this.link[n4] ^ (this.link[this.first] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[n2] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
            this.first = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }

    public double putAndMoveToLast(int n, double d) {
        int n2;
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, d);
            }
            this.containsNullKey = true;
            n2 = this.n;
        } else {
            int[] nArray = this.key;
            n2 = HashCommon.mix(n) & this.mask;
            int n3 = nArray[n2];
            if (n3 != 0) {
                if (n3 == n) {
                    this.moveIndexToLast(n2);
                    return this.setValue(n2, d);
                }
                while ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (n3 != n) continue;
                    this.moveIndexToLast(n2);
                    return this.setValue(n2, d);
                }
            }
        }
        this.key[n2] = n;
        this.value[n2] = d;
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n4 = this.last;
            this.link[n4] = this.link[n4] ^ (this.link[this.last] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n2] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }

    @Override
    public double get(int n) {
        if (n == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return this.defRetValue;
        }
        if (n == n3) {
            return this.value[n2];
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (n != n3);
        return this.value[n2];
    }

    @Override
    public boolean containsKey(int n) {
        if (n == 0) {
            return this.containsNullKey;
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
    public boolean containsValue(double d) {
        double[] dArray = this.value;
        int[] nArray = this.key;
        if (this.containsNullKey && Double.doubleToLongBits(dArray[this.n]) == Double.doubleToLongBits(d)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (nArray[n] == 0 || Double.doubleToLongBits(dArray[n]) != Double.doubleToLongBits(d)) continue;
            return false;
        }
        return true;
    }

    @Override
    public double getOrDefault(int n, double d) {
        if (n == 0) {
            return this.containsNullKey ? this.value[this.n] : d;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return d;
        }
        if (n == n3) {
            return this.value[n2];
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return d;
        } while (n != n3);
        return this.value[n2];
    }

    @Override
    public double putIfAbsent(int n, double d) {
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        this.insert(-n2 - 1, n, d);
        return this.defRetValue;
    }

    @Override
    public boolean remove(int n, double d) {
        if (n == 0) {
            if (this.containsNullKey && Double.doubleToLongBits(d) == Double.doubleToLongBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return true;
        }
        if (n == n3 && Double.doubleToLongBits(d) == Double.doubleToLongBits(this.value[n2])) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (n != n3 || Double.doubleToLongBits(d) != Double.doubleToLongBits(this.value[n2]));
        this.removeEntry(n2);
        return false;
    }

    @Override
    public boolean replace(int n, double d, double d2) {
        int n2 = this.find(n);
        if (n2 < 0 || Double.doubleToLongBits(d) != Double.doubleToLongBits(this.value[n2])) {
            return true;
        }
        this.value[n2] = d2;
        return false;
    }

    @Override
    public double replace(int n, double d) {
        int n2 = this.find(n);
        if (n2 < 0) {
            return this.defRetValue;
        }
        double d2 = this.value[n2];
        this.value[n2] = d;
        return d2;
    }

    @Override
    public double computeIfAbsent(int n, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        double d = intToDoubleFunction.applyAsDouble(n);
        this.insert(-n2 - 1, n, d);
        return d;
    }

    @Override
    public double computeIfAbsentNullable(int n, IntFunction<? extends Double> intFunction) {
        Objects.requireNonNull(intFunction);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        Double d = intFunction.apply(n);
        if (d == null) {
            return this.defRetValue;
        }
        double d2 = d;
        this.insert(-n2 - 1, n, d2);
        return d2;
    }

    @Override
    public double computeIfPresent(int n, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        if (n2 < 0) {
            return this.defRetValue;
        }
        Double d = biFunction.apply((Integer)n, (Double)this.value[n2]);
        if (d == null) {
            if (n == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = d;
        return this.value[n2];
    }

    @Override
    public double compute(int n, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        Double d = biFunction.apply((Integer)n, n2 >= 0 ? Double.valueOf(this.value[n2]) : null);
        if (d == null) {
            if (n2 >= 0) {
                if (n == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n2);
                }
            }
            return this.defRetValue;
        }
        double d2 = d;
        if (n2 < 0) {
            this.insert(-n2 - 1, n, d2);
            return d2;
        }
        this.value[n2] = d2;
        return this.value[n2];
    }

    @Override
    public double merge(int n, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        if (n2 < 0) {
            this.insert(-n2 - 1, n, d);
            return d;
        }
        Double d2 = biFunction.apply((Double)this.value[n2], (Double)d);
        if (d2 == null) {
            if (n == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = d2;
        return this.value[n2];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0);
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
    public int firstIntKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public int lastIntKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public Int2DoubleSortedMap tailMap(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Int2DoubleSortedMap headMap(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Int2DoubleSortedMap subMap(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IntComparator comparator() {
        return null;
    }

    @Override
    public Int2DoubleSortedMap.FastSortedEntrySet int2DoubleEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public IntSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public DoubleCollection values() {
        if (this.values == null) {
            this.values = new AbstractDoubleCollection(this){
                final Int2DoubleLinkedOpenHashMap this$0;
                {
                    this.this$0 = int2DoubleLinkedOpenHashMap;
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
                        if (this.this$0.key[n] == 0) continue;
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
        int[] nArray = this.key;
        double[] dArray = this.value;
        int n2 = n - 1;
        int[] nArray2 = new int[n + 1];
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
            if (nArray[n3] == 0) {
                n7 = n;
            } else {
                n7 = HashCommon.mix(nArray[n3]) & n2;
                while (nArray2[n7] != 0) {
                    n7 = n7 + 1 & n2;
                }
            }
            nArray2[n7] = nArray[n3];
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
        this.key = nArray2;
        this.value = dArray2;
    }

    public Int2DoubleLinkedOpenHashMap clone() {
        Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap;
        try {
            int2DoubleLinkedOpenHashMap = (Int2DoubleLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2DoubleLinkedOpenHashMap.keys = null;
        int2DoubleLinkedOpenHashMap.values = null;
        int2DoubleLinkedOpenHashMap.entries = null;
        int2DoubleLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        int2DoubleLinkedOpenHashMap.key = (int[])this.key.clone();
        int2DoubleLinkedOpenHashMap.value = (double[])this.value.clone();
        int2DoubleLinkedOpenHashMap.link = (long[])this.link.clone();
        return int2DoubleLinkedOpenHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == 0) {
                ++n3;
            }
            n4 = this.key[n3];
            n += (n4 ^= HashCommon.double2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.double2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int[] nArray = this.key;
        double[] dArray = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeInt(nArray[n2]);
            objectOutputStream.writeDouble(dArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new int[this.n + 1];
        int[] nArray = this.key;
        this.value = new double[this.n + 1];
        double[] dArray = this.value;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            int n4 = objectInputStream.readInt();
            double d = objectInputStream.readDouble();
            if (n4 == 0) {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = HashCommon.mix(n4) & this.mask;
                while (nArray[n3] != 0) {
                    n3 = n3 + 1 & this.mask;
                }
            }
            nArray[n3] = n4;
            dArray[n3] = d;
            if (this.first != -1) {
                int n5 = n;
                lArray[n5] = lArray[n5] ^ (lArray[n] ^ (long)n3 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n6 = n3;
                lArray[n6] = lArray[n6] ^ (lArray[n3] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n = n3;
                continue;
            }
            n = this.first = n3;
            int n7 = n3;
            lArray[n7] = lArray[n7] | 0xFFFFFFFF00000000L;
        }
        this.last = n;
        if (n != -1) {
            int n8 = n;
            lArray[n8] = lArray[n8] | 0xFFFFFFFFL;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSortedSet int2DoubleEntrySet() {
        return this.int2DoubleEntrySet();
    }

    @Override
    public IntSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet int2DoubleEntrySet() {
        return this.int2DoubleEntrySet();
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

    static double access$100(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
        return int2DoubleLinkedOpenHashMap.removeNullEntry();
    }

    static double access$200(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, int n) {
        return int2DoubleLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements DoubleListIterator {
        final Int2DoubleLinkedOpenHashMap this$0;

        @Override
        public double previousDouble() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            super(int2DoubleLinkedOpenHashMap);
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
    extends AbstractIntSortedSet {
        final Int2DoubleLinkedOpenHashMap this$0;

        private KeySet(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
        }

        @Override
        public IntListIterator iterator(int n) {
            return new KeyIterator(this.this$0, n);
        }

        @Override
        public IntListIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                int n2 = this.this$0.key[n];
                if (n2 == 0) continue;
                intConsumer.accept(n2);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(int n) {
            return this.this$0.containsKey(n);
        }

        @Override
        public boolean remove(int n) {
            int n2 = this.this$0.size;
            this.this$0.remove(n);
            return this.this$0.size != n2;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public int firstInt() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }

        @Override
        public int lastInt() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }

        @Override
        public IntComparator comparator() {
            return null;
        }

        @Override
        public IntSortedSet tailSet(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IntSortedSet headSet(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IntSortedSet subSet(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public IntBidirectionalIterator iterator(int n) {
            return this.iterator(n);
        }

        @Override
        public IntIterator iterator() {
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

        KeySet(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, 1 var2_2) {
            this(int2DoubleLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements IntListIterator {
        final Int2DoubleLinkedOpenHashMap this$0;

        public KeyIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, int n) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            super(int2DoubleLinkedOpenHashMap, n, null);
        }

        @Override
        public int previousInt() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            super(int2DoubleLinkedOpenHashMap);
        }

        @Override
        public int nextInt() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class MapEntrySet
    extends AbstractObjectSortedSet<Int2DoubleMap.Entry>
    implements Int2DoubleSortedMap.FastSortedEntrySet {
        final Int2DoubleLinkedOpenHashMap this$0;

        private MapEntrySet(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Int2DoubleMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Int2DoubleMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Int2DoubleMap.Entry> subSet(Int2DoubleMap.Entry entry, Int2DoubleMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Int2DoubleMap.Entry> headSet(Int2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Int2DoubleMap.Entry> tailSet(Int2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Int2DoubleMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Int2DoubleMap.Entry last() {
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
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            double d = (Double)entry.getValue();
            if (n == 0) {
                return this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d);
            }
            int[] nArray = this.this$0.key;
            int n2 = HashCommon.mix(n) & this.this$0.mask;
            int n3 = nArray[n2];
            if (n3 == 0) {
                return true;
            }
            if (n == n3) {
                return Double.doubleToLongBits(this.this$0.value[n2]) == Double.doubleToLongBits(d);
            }
            do {
                if ((n3 = nArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (n != n3);
            return Double.doubleToLongBits(this.this$0.value[n2]) == Double.doubleToLongBits(d);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            double d = (Double)entry.getValue();
            if (n == 0) {
                if (this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d)) {
                    Int2DoubleLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            int[] nArray = this.this$0.key;
            int n2 = HashCommon.mix(n) & this.this$0.mask;
            int n3 = nArray[n2];
            if (n3 == 0) {
                return true;
            }
            if (n3 == n) {
                if (Double.doubleToLongBits(this.this$0.value[n2]) == Double.doubleToLongBits(d)) {
                    Int2DoubleLinkedOpenHashMap.access$200(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((n3 = nArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (n3 != n || Double.doubleToLongBits(this.this$0.value[n2]) != Double.doubleToLongBits(d));
            Int2DoubleLinkedOpenHashMap.access$200(this.this$0, n2);
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
        public ObjectListIterator<Int2DoubleMap.Entry> iterator(Int2DoubleMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getIntKey());
        }

        @Override
        public ObjectListIterator<Int2DoubleMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Int2DoubleMap.Entry> fastIterator(Int2DoubleMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getIntKey());
        }

        @Override
        public void forEach(Consumer<? super Int2DoubleMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractInt2DoubleMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Int2DoubleMap.Entry> consumer) {
            AbstractInt2DoubleMap.BasicEntry basicEntry = new AbstractInt2DoubleMap.BasicEntry();
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
            return this.tailSet((Int2DoubleMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Int2DoubleMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Int2DoubleMap.Entry)object, (Int2DoubleMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Int2DoubleMap.Entry)object);
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
            return this.tailSet((Int2DoubleMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Int2DoubleMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Int2DoubleMap.Entry)object, (Int2DoubleMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Int2DoubleMap.Entry entry) {
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

        MapEntrySet(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, 1 var2_2) {
            this(int2DoubleLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Int2DoubleMap.Entry> {
        final MapEntry entry;
        final Int2DoubleLinkedOpenHashMap this$0;

        public FastEntryIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            super(int2DoubleLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, int n) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            super(int2DoubleLinkedOpenHashMap, n, null);
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
            super.add((Int2DoubleMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Int2DoubleMap.Entry)object);
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
    implements ObjectListIterator<Int2DoubleMap.Entry> {
        private MapEntry entry;
        final Int2DoubleLinkedOpenHashMap this$0;

        public EntryIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            super(int2DoubleLinkedOpenHashMap);
        }

        public EntryIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, int n) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            super(int2DoubleLinkedOpenHashMap, n, null);
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
            super.add((Int2DoubleMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Int2DoubleMap.Entry)object);
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
        final Int2DoubleLinkedOpenHashMap this$0;

        protected MapIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = int2DoubleLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, int n) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (n == 0) {
                if (int2DoubleLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)int2DoubleLinkedOpenHashMap.link[int2DoubleLinkedOpenHashMap.n];
                    this.prev = int2DoubleLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + n + " does not belong to this map.");
            }
            if (int2DoubleLinkedOpenHashMap.key[int2DoubleLinkedOpenHashMap.last] == n) {
                this.prev = int2DoubleLinkedOpenHashMap.last;
                this.index = int2DoubleLinkedOpenHashMap.size;
                return;
            }
            int n2 = HashCommon.mix(n) & int2DoubleLinkedOpenHashMap.mask;
            while (int2DoubleLinkedOpenHashMap.key[n2] != 0) {
                if (int2DoubleLinkedOpenHashMap.key[n2] == n) {
                    this.next = (int)int2DoubleLinkedOpenHashMap.link[n2];
                    this.prev = n2;
                    return;
                }
                n2 = n2 + 1 & int2DoubleLinkedOpenHashMap.mask;
            }
            throw new NoSuchElementException("The key " + n + " does not belong to this map.");
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
                    nArray[n3] = n2;
                    this.this$0.value[n3] = this.this$0.value[n];
                    if (this.next == n) {
                        this.next = n3;
                    }
                    if (this.prev == n) {
                        this.prev = n3;
                    }
                    this.this$0.fixPointers(n, n3);
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

        public void set(Int2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Int2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, int n, 1 var3_3) {
            this(int2DoubleLinkedOpenHashMap, n);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Int2DoubleMap.Entry,
    Map.Entry<Integer, Double> {
        int index;
        final Int2DoubleLinkedOpenHashMap this$0;

        MapEntry(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap, int n) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Int2DoubleLinkedOpenHashMap int2DoubleLinkedOpenHashMap) {
            this.this$0 = int2DoubleLinkedOpenHashMap;
        }

        @Override
        public int getIntKey() {
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
        public Integer getKey() {
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
            return this.this$0.key[this.index] == (Integer)entry.getKey() && Double.doubleToLongBits(this.this$0.value[this.index]) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public int hashCode() {
            return this.this$0.key[this.index] ^ HashCommon.double2int(this.this$0.value[this.index]);
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

