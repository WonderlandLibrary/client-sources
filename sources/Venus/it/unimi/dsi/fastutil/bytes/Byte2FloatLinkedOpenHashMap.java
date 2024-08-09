/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.AbstractByte2FloatMap;
import it.unimi.dsi.fastutil.bytes.AbstractByte2FloatSortedMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2FloatMap;
import it.unimi.dsi.fastutil.bytes.Byte2FloatSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
public class Byte2FloatLinkedOpenHashMap
extends AbstractByte2FloatSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient byte[] key;
    protected transient float[] value;
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
    protected transient Byte2FloatSortedMap.FastSortedEntrySet entries;
    protected transient ByteSortedSet keys;
    protected transient FloatCollection values;

    public Byte2FloatLinkedOpenHashMap(int n, float f) {
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
        this.value = new float[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Byte2FloatLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Byte2FloatLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Byte2FloatLinkedOpenHashMap(Map<? extends Byte, ? extends Float> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Byte2FloatLinkedOpenHashMap(Map<? extends Byte, ? extends Float> map) {
        this(map, 0.75f);
    }

    public Byte2FloatLinkedOpenHashMap(Byte2FloatMap byte2FloatMap, float f) {
        this(byte2FloatMap.size(), f);
        this.putAll(byte2FloatMap);
    }

    public Byte2FloatLinkedOpenHashMap(Byte2FloatMap byte2FloatMap) {
        this(byte2FloatMap, 0.75f);
    }

    public Byte2FloatLinkedOpenHashMap(byte[] byArray, float[] fArray, float f) {
        this(byArray.length, f);
        if (byArray.length != fArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + byArray.length + " and " + fArray.length + ")");
        }
        for (int i = 0; i < byArray.length; ++i) {
            this.put(byArray[i], fArray[i]);
        }
    }

    public Byte2FloatLinkedOpenHashMap(byte[] byArray, float[] fArray) {
        this(byArray, fArray, 0.75f);
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

    private float removeEntry(int n) {
        float f = this.value[n];
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return f;
    }

    private float removeNullEntry() {
        this.containsNullKey = false;
        float f = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return f;
    }

    @Override
    public void putAll(Map<? extends Byte, ? extends Float> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(byte by) {
        if (by == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(by) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return -(n + 1);
        }
        if (by == by2) {
            return n;
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return -(n + 1);
        } while (by != by2);
        return n;
    }

    private void insert(int n, byte by, float f) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = by;
        this.value[n] = f;
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
    public float put(byte by, float f) {
        int n = this.find(by);
        if (n < 0) {
            this.insert(-n - 1, by, f);
            return this.defRetValue;
        }
        float f2 = this.value[n];
        this.value[n] = f;
        return f2;
    }

    private float addToValue(int n, float f) {
        float f2 = this.value[n];
        this.value[n] = f2 + f;
        return f2;
    }

    public float addTo(byte by, float f) {
        int n;
        if (by == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, f);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            byte[] byArray = this.key;
            n = HashCommon.mix(by) & this.mask;
            byte by2 = byArray[n];
            if (by2 != 0) {
                if (by2 == by) {
                    return this.addToValue(n, f);
                }
                while ((by2 = byArray[n = n + 1 & this.mask]) != 0) {
                    if (by2 != by) continue;
                    return this.addToValue(n, f);
                }
            }
        }
        this.key[n] = by;
        this.value[n] = this.defRetValue + f;
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
                int n3 = HashCommon.mix(by) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            byArray[n2] = by;
            this.value[n2] = this.value[n];
            this.fixPointers(n, n2);
        }
    }

    @Override
    public float remove(byte by) {
        if (by == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(by) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return this.defRetValue;
        }
        if (by == by2) {
            return this.removeEntry(n);
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (by != by2);
        return this.removeEntry(n);
    }

    private float setValue(int n, float f) {
        float f2 = this.value[n];
        this.value[n] = f;
        return f2;
    }

    public float removeFirstFloat() {
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
        float f = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return f;
    }

    public float removeLastFloat() {
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
        float f = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return f;
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

    public float getAndMoveToFirst(byte by) {
        if (by == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(by) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return this.defRetValue;
        }
        if (by == by2) {
            this.moveIndexToFirst(n);
            return this.value[n];
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (by != by2);
        this.moveIndexToFirst(n);
        return this.value[n];
    }

    public float getAndMoveToLast(byte by) {
        if (by == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(by) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return this.defRetValue;
        }
        if (by == by2) {
            this.moveIndexToLast(n);
            return this.value[n];
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (by != by2);
        this.moveIndexToLast(n);
        return this.value[n];
    }

    public float putAndMoveToFirst(byte by, float f) {
        int n;
        if (by == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, f);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            byte[] byArray = this.key;
            n = HashCommon.mix(by) & this.mask;
            byte by2 = byArray[n];
            if (by2 != 0) {
                if (by2 == by) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, f);
                }
                while ((by2 = byArray[n = n + 1 & this.mask]) != 0) {
                    if (by2 != by) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, f);
                }
            }
        }
        this.key[n] = by;
        this.value[n] = f;
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

    public float putAndMoveToLast(byte by, float f) {
        int n;
        if (by == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, f);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            byte[] byArray = this.key;
            n = HashCommon.mix(by) & this.mask;
            byte by2 = byArray[n];
            if (by2 != 0) {
                if (by2 == by) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, f);
                }
                while ((by2 = byArray[n = n + 1 & this.mask]) != 0) {
                    if (by2 != by) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, f);
                }
            }
        }
        this.key[n] = by;
        this.value[n] = f;
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
    public float get(byte by) {
        if (by == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(by) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return this.defRetValue;
        }
        if (by == by2) {
            return this.value[n];
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (by != by2);
        return this.value[n];
    }

    @Override
    public boolean containsKey(byte by) {
        if (by == 0) {
            return this.containsNullKey;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(by) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return true;
        }
        if (by == by2) {
            return false;
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (by != by2);
        return false;
    }

    @Override
    public boolean containsValue(float f) {
        float[] fArray = this.value;
        byte[] byArray = this.key;
        if (this.containsNullKey && Float.floatToIntBits(fArray[this.n]) == Float.floatToIntBits(f)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (byArray[n] == 0 || Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public float getOrDefault(byte by, float f) {
        if (by == 0) {
            return this.containsNullKey ? this.value[this.n] : f;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(by) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return f;
        }
        if (by == by2) {
            return this.value[n];
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return f;
        } while (by != by2);
        return this.value[n];
    }

    @Override
    public float putIfAbsent(byte by, float f) {
        int n = this.find(by);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, by, f);
        return this.defRetValue;
    }

    @Override
    public boolean remove(byte by, float f) {
        if (by == 0) {
            if (this.containsNullKey && Float.floatToIntBits(f) == Float.floatToIntBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(by) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return true;
        }
        if (by == by2 && Float.floatToIntBits(f) == Float.floatToIntBits(this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (by != by2 || Float.floatToIntBits(f) != Float.floatToIntBits(this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(byte by, float f, float f2) {
        int n = this.find(by);
        if (n < 0 || Float.floatToIntBits(f) != Float.floatToIntBits(this.value[n])) {
            return true;
        }
        this.value[n] = f2;
        return false;
    }

    @Override
    public float replace(byte by, float f) {
        int n = this.find(by);
        if (n < 0) {
            return this.defRetValue;
        }
        float f2 = this.value[n];
        this.value[n] = f;
        return f2;
    }

    @Override
    public float computeIfAbsent(byte by, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        int n = this.find(by);
        if (n >= 0) {
            return this.value[n];
        }
        float f = SafeMath.safeDoubleToFloat(intToDoubleFunction.applyAsDouble(by));
        this.insert(-n - 1, by, f);
        return f;
    }

    @Override
    public float computeIfAbsentNullable(byte by, IntFunction<? extends Float> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(by);
        if (n >= 0) {
            return this.value[n];
        }
        Float f = intFunction.apply(by);
        if (f == null) {
            return this.defRetValue;
        }
        float f2 = f.floatValue();
        this.insert(-n - 1, by, f2);
        return f2;
    }

    @Override
    public float computeIfPresent(byte by, BiFunction<? super Byte, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(by);
        if (n < 0) {
            return this.defRetValue;
        }
        Float f = biFunction.apply((Byte)by, Float.valueOf(this.value[n]));
        if (f == null) {
            if (by == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = f.floatValue();
        return this.value[n];
    }

    @Override
    public float compute(byte by, BiFunction<? super Byte, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(by);
        Float f = biFunction.apply((Byte)by, n >= 0 ? Float.valueOf(this.value[n]) : null);
        if (f == null) {
            if (n >= 0) {
                if (by == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        float f2 = f.floatValue();
        if (n < 0) {
            this.insert(-n - 1, by, f2);
            return f2;
        }
        this.value[n] = f2;
        return this.value[n];
    }

    @Override
    public float merge(byte by, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(by);
        if (n < 0) {
            this.insert(-n - 1, by, f);
            return f;
        }
        Float f2 = biFunction.apply(Float.valueOf(this.value[n]), Float.valueOf(f));
        if (f2 == null) {
            if (by == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = f2.floatValue();
        return this.value[n];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, (byte)0);
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
    public byte firstByteKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public byte lastByteKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public Byte2FloatSortedMap tailMap(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Byte2FloatSortedMap headMap(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Byte2FloatSortedMap subMap(byte by, byte by2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ByteComparator comparator() {
        return null;
    }

    @Override
    public Byte2FloatSortedMap.FastSortedEntrySet byte2FloatEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ByteSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public FloatCollection values() {
        if (this.values == null) {
            this.values = new AbstractFloatCollection(this){
                final Byte2FloatLinkedOpenHashMap this$0;
                {
                    this.this$0 = byte2FloatLinkedOpenHashMap;
                }

                @Override
                public FloatIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(float f) {
                    return this.this$0.containsValue(f);
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
        byte[] byArray = this.key;
        float[] fArray = this.value;
        int n2 = n - 1;
        byte[] byArray2 = new byte[n + 1];
        float[] fArray2 = new float[n + 1];
        int n3 = this.first;
        int n4 = -1;
        int n5 = -1;
        long[] lArray = this.link;
        long[] lArray2 = new long[n + 1];
        this.first = -1;
        int n6 = this.size;
        while (n6-- != 0) {
            int n7;
            if (byArray[n3] == 0) {
                n7 = n;
            } else {
                n7 = HashCommon.mix(byArray[n3]) & n2;
                while (byArray2[n7] != 0) {
                    n7 = n7 + 1 & n2;
                }
            }
            byArray2[n7] = byArray[n3];
            fArray2[n7] = fArray[n3];
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
        this.key = byArray2;
        this.value = fArray2;
    }

    public Byte2FloatLinkedOpenHashMap clone() {
        Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap;
        try {
            byte2FloatLinkedOpenHashMap = (Byte2FloatLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2FloatLinkedOpenHashMap.keys = null;
        byte2FloatLinkedOpenHashMap.values = null;
        byte2FloatLinkedOpenHashMap.entries = null;
        byte2FloatLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        byte2FloatLinkedOpenHashMap.key = (byte[])this.key.clone();
        byte2FloatLinkedOpenHashMap.value = (float[])this.value.clone();
        byte2FloatLinkedOpenHashMap.link = (long[])this.link.clone();
        return byte2FloatLinkedOpenHashMap;
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
            n += (n4 ^= HashCommon.float2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.float2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        byte[] byArray = this.key;
        float[] fArray = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeByte(byArray[n2]);
            objectOutputStream.writeFloat(fArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new byte[this.n + 1];
        byte[] byArray = this.key;
        this.value = new float[this.n + 1];
        float[] fArray = this.value;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            byte by = objectInputStream.readByte();
            float f = objectInputStream.readFloat();
            if (by == 0) {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = HashCommon.mix(by) & this.mask;
                while (byArray[n3] != 0) {
                    n3 = n3 + 1 & this.mask;
                }
            }
            byArray[n3] = by;
            fArray[n3] = f;
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
    public ObjectSortedSet byte2FloatEntrySet() {
        return this.byte2FloatEntrySet();
    }

    @Override
    public ByteSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet byte2FloatEntrySet() {
        return this.byte2FloatEntrySet();
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

    static float access$100(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
        return byte2FloatLinkedOpenHashMap.removeNullEntry();
    }

    static float access$200(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, int n) {
        return byte2FloatLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements FloatListIterator {
        final Byte2FloatLinkedOpenHashMap this$0;

        @Override
        public float previousFloat() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            super(byte2FloatLinkedOpenHashMap);
        }

        @Override
        public float nextFloat() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractByteSortedSet {
        final Byte2FloatLinkedOpenHashMap this$0;

        private KeySet(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
        }

        @Override
        public ByteListIterator iterator(byte by) {
            return new KeyIterator(this.this$0, by);
        }

        @Override
        public ByteListIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                byte by = this.this$0.key[n];
                if (by == 0) continue;
                intConsumer.accept(by);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(byte by) {
            return this.this$0.containsKey(by);
        }

        @Override
        public boolean remove(byte by) {
            int n = this.this$0.size;
            this.this$0.remove(by);
            return this.this$0.size != n;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public byte firstByte() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }

        @Override
        public byte lastByte() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }

        @Override
        public ByteComparator comparator() {
            return null;
        }

        @Override
        public ByteSortedSet tailSet(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ByteSortedSet headSet(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ByteSortedSet subSet(byte by, byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte by) {
            return this.iterator(by);
        }

        @Override
        public ByteIterator iterator() {
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

        KeySet(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, 1 var2_2) {
            this(byte2FloatLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ByteListIterator {
        final Byte2FloatLinkedOpenHashMap this$0;

        public KeyIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, byte by) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            super(byte2FloatLinkedOpenHashMap, by, null);
        }

        @Override
        public byte previousByte() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            super(byte2FloatLinkedOpenHashMap);
        }

        @Override
        public byte nextByte() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class MapEntrySet
    extends AbstractObjectSortedSet<Byte2FloatMap.Entry>
    implements Byte2FloatSortedMap.FastSortedEntrySet {
        final Byte2FloatLinkedOpenHashMap this$0;

        private MapEntrySet(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Byte2FloatMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Byte2FloatMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Byte2FloatMap.Entry> subSet(Byte2FloatMap.Entry entry, Byte2FloatMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Byte2FloatMap.Entry> headSet(Byte2FloatMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Byte2FloatMap.Entry> tailSet(Byte2FloatMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte2FloatMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Byte2FloatMap.Entry last() {
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
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            if (by == 0) {
                return this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f);
            }
            byte[] byArray = this.this$0.key;
            int n = HashCommon.mix(by) & this.this$0.mask;
            byte by2 = byArray[n];
            if (by2 == 0) {
                return true;
            }
            if (by == by2) {
                return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f);
            }
            do {
                if ((by2 = byArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (by != by2);
            return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            if (by == 0) {
                if (this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f)) {
                    Byte2FloatLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            byte[] byArray = this.this$0.key;
            int n = HashCommon.mix(by) & this.this$0.mask;
            byte by2 = byArray[n];
            if (by2 == 0) {
                return true;
            }
            if (by2 == by) {
                if (Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f)) {
                    Byte2FloatLinkedOpenHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((by2 = byArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (by2 != by || Float.floatToIntBits(this.this$0.value[n]) != Float.floatToIntBits(f));
            Byte2FloatLinkedOpenHashMap.access$200(this.this$0, n);
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
        public ObjectListIterator<Byte2FloatMap.Entry> iterator(Byte2FloatMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getByteKey());
        }

        @Override
        public ObjectListIterator<Byte2FloatMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Byte2FloatMap.Entry> fastIterator(Byte2FloatMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getByteKey());
        }

        @Override
        public void forEach(Consumer<? super Byte2FloatMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractByte2FloatMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Byte2FloatMap.Entry> consumer) {
            AbstractByte2FloatMap.BasicEntry basicEntry = new AbstractByte2FloatMap.BasicEntry();
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
            return this.tailSet((Byte2FloatMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Byte2FloatMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Byte2FloatMap.Entry)object, (Byte2FloatMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Byte2FloatMap.Entry)object);
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
            return this.tailSet((Byte2FloatMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Byte2FloatMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Byte2FloatMap.Entry)object, (Byte2FloatMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Byte2FloatMap.Entry entry) {
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

        MapEntrySet(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, 1 var2_2) {
            this(byte2FloatLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Byte2FloatMap.Entry> {
        final MapEntry entry;
        final Byte2FloatLinkedOpenHashMap this$0;

        public FastEntryIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            super(byte2FloatLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, byte by) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            super(byte2FloatLinkedOpenHashMap, by, null);
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
            super.add((Byte2FloatMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Byte2FloatMap.Entry)object);
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
    implements ObjectListIterator<Byte2FloatMap.Entry> {
        private MapEntry entry;
        final Byte2FloatLinkedOpenHashMap this$0;

        public EntryIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            super(byte2FloatLinkedOpenHashMap);
        }

        public EntryIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, byte by) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            super(byte2FloatLinkedOpenHashMap, by, null);
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
            super.add((Byte2FloatMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Byte2FloatMap.Entry)object);
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
        final Byte2FloatLinkedOpenHashMap this$0;

        protected MapIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = byte2FloatLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, byte by) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (by == 0) {
                if (byte2FloatLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)byte2FloatLinkedOpenHashMap.link[byte2FloatLinkedOpenHashMap.n];
                    this.prev = byte2FloatLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + by + " does not belong to this map.");
            }
            if (byte2FloatLinkedOpenHashMap.key[byte2FloatLinkedOpenHashMap.last] == by) {
                this.prev = byte2FloatLinkedOpenHashMap.last;
                this.index = byte2FloatLinkedOpenHashMap.size;
                return;
            }
            int n = HashCommon.mix(by) & byte2FloatLinkedOpenHashMap.mask;
            while (byte2FloatLinkedOpenHashMap.key[n] != 0) {
                if (byte2FloatLinkedOpenHashMap.key[n] == by) {
                    this.next = (int)byte2FloatLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & byte2FloatLinkedOpenHashMap.mask;
            }
            throw new NoSuchElementException("The key " + by + " does not belong to this map.");
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
                        int n3 = HashCommon.mix(by) & this.this$0.mask;
                        if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                        n = n + 1 & this.this$0.mask;
                    }
                    byArray[n2] = by;
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

        public void set(Byte2FloatMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Byte2FloatMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, byte by, 1 var3_3) {
            this(byte2FloatLinkedOpenHashMap, by);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Byte2FloatMap.Entry,
    Map.Entry<Byte, Float> {
        int index;
        final Byte2FloatLinkedOpenHashMap this$0;

        MapEntry(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap, int n) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Byte2FloatLinkedOpenHashMap byte2FloatLinkedOpenHashMap) {
            this.this$0 = byte2FloatLinkedOpenHashMap;
        }

        @Override
        public byte getByteKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public float getFloatValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public float setValue(float f) {
            float f2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = f;
            return f2;
        }

        @Override
        @Deprecated
        public Byte getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Float getValue() {
            return Float.valueOf(this.this$0.value[this.index]);
        }

        @Override
        @Deprecated
        public Float setValue(Float f) {
            return Float.valueOf(this.setValue(f.floatValue()));
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.key[this.index] == (Byte)entry.getKey() && Float.floatToIntBits(this.this$0.value[this.index]) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
        }

        @Override
        public int hashCode() {
            return this.this$0.key[this.index] ^ HashCommon.float2int(this.this$0.value[this.index]);
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Float)object);
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

