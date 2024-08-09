/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatMap;
import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatSortedMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
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
public class Float2FloatLinkedOpenHashMap
extends AbstractFloat2FloatSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[] key;
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
    protected transient Float2FloatSortedMap.FastSortedEntrySet entries;
    protected transient FloatSortedSet keys;
    protected transient FloatCollection values;

    public Float2FloatLinkedOpenHashMap(int n, float f) {
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
        this.key = new float[this.n + 1];
        this.value = new float[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Float2FloatLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Float2FloatLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Float2FloatLinkedOpenHashMap(Map<? extends Float, ? extends Float> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Float2FloatLinkedOpenHashMap(Map<? extends Float, ? extends Float> map) {
        this(map, 0.75f);
    }

    public Float2FloatLinkedOpenHashMap(Float2FloatMap float2FloatMap, float f) {
        this(float2FloatMap.size(), f);
        this.putAll(float2FloatMap);
    }

    public Float2FloatLinkedOpenHashMap(Float2FloatMap float2FloatMap) {
        this(float2FloatMap, 0.75f);
    }

    public Float2FloatLinkedOpenHashMap(float[] fArray, float[] fArray2, float f) {
        this(fArray.length, f);
        if (fArray.length != fArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + fArray.length + " and " + fArray2.length + ")");
        }
        for (int i = 0; i < fArray.length; ++i) {
            this.put(fArray[i], fArray2[i]);
        }
    }

    public Float2FloatLinkedOpenHashMap(float[] fArray, float[] fArray2) {
        this(fArray, fArray2, 0.75f);
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
    public void putAll(Map<? extends Float, ? extends Float> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(float f) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return -(n + 1);
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return n;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return -(n + 1);
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return n;
    }

    private void insert(int n, float f, float f2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = f;
        this.value[n] = f2;
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
    public float put(float f, float f2) {
        int n = this.find(f);
        if (n < 0) {
            this.insert(-n - 1, f, f2);
            return this.defRetValue;
        }
        float f3 = this.value[n];
        this.value[n] = f2;
        return f3;
    }

    private float addToValue(int n, float f) {
        float f2 = this.value[n];
        this.value[n] = f2 + f;
        return f2;
    }

    public float addTo(float f, float f2) {
        int n;
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, f2);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            float[] fArray = this.key;
            n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
            float f3 = fArray[n];
            if (Float.floatToIntBits(f3) != 0) {
                if (Float.floatToIntBits(f3) == Float.floatToIntBits(f)) {
                    return this.addToValue(n, f2);
                }
                while (Float.floatToIntBits(f3 = fArray[n = n + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(f3) != Float.floatToIntBits(f)) continue;
                    return this.addToValue(n, f2);
                }
            }
        }
        this.key[n] = f;
        this.value[n] = this.defRetValue + f2;
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
        float[] fArray = this.key;
        while (true) {
            float f;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if (Float.floatToIntBits(f = fArray[n]) == 0) {
                    fArray[n2] = 0.0f;
                    return;
                }
                int n3 = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            fArray[n2] = f;
            this.value[n2] = this.value[n];
            this.fixPointers(n, n2);
        }
    }

    @Override
    public float remove(float f) {
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return this.removeEntry(n);
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
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

    public float getAndMoveToFirst(float f) {
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            this.moveIndexToFirst(n);
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        this.moveIndexToFirst(n);
        return this.value[n];
    }

    public float getAndMoveToLast(float f) {
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            this.moveIndexToLast(n);
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        this.moveIndexToLast(n);
        return this.value[n];
    }

    public float putAndMoveToFirst(float f, float f2) {
        int n;
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, f2);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            float[] fArray = this.key;
            n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
            float f3 = fArray[n];
            if (Float.floatToIntBits(f3) != 0) {
                if (Float.floatToIntBits(f3) == Float.floatToIntBits(f)) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, f2);
                }
                while (Float.floatToIntBits(f3 = fArray[n = n + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(f3) != Float.floatToIntBits(f)) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, f2);
                }
            }
        }
        this.key[n] = f;
        this.value[n] = f2;
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

    public float putAndMoveToLast(float f, float f2) {
        int n;
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, f2);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            float[] fArray = this.key;
            n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
            float f3 = fArray[n];
            if (Float.floatToIntBits(f3) != 0) {
                if (Float.floatToIntBits(f3) == Float.floatToIntBits(f)) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, f2);
                }
                while (Float.floatToIntBits(f3 = fArray[n = n + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(f3) != Float.floatToIntBits(f)) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, f2);
                }
            }
        }
        this.key[n] = f;
        this.value[n] = f2;
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
    public float get(float f) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(float f) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return false;
    }

    @Override
    public boolean containsValue(float f) {
        float[] fArray = this.value;
        float[] fArray2 = this.key;
        if (this.containsNullKey && Float.floatToIntBits(fArray[this.n]) == Float.floatToIntBits(f)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray2[n]) == 0 || Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public float getOrDefault(float f, float f2) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey ? this.value[this.n] : f2;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f3 = fArray[n];
        if (Float.floatToIntBits(f3) == 0) {
            return f2;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f3)) {
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f3 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return f2;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f3));
        return this.value[n];
    }

    @Override
    public float putIfAbsent(float f, float f2) {
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, f, f2);
        return this.defRetValue;
    }

    @Override
    public boolean remove(float f, float f2) {
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey && Float.floatToIntBits(f2) == Float.floatToIntBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f3 = fArray[n];
        if (Float.floatToIntBits(f3) == 0) {
            return true;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f3) && Float.floatToIntBits(f2) == Float.floatToIntBits(this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Float.floatToIntBits(f3 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f3) || Float.floatToIntBits(f2) != Float.floatToIntBits(this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(float f, float f2, float f3) {
        int n = this.find(f);
        if (n < 0 || Float.floatToIntBits(f2) != Float.floatToIntBits(this.value[n])) {
            return true;
        }
        this.value[n] = f3;
        return false;
    }

    @Override
    public float replace(float f, float f2) {
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        float f3 = this.value[n];
        this.value[n] = f2;
        return f3;
    }

    @Override
    public float computeIfAbsent(float f, DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        float f2 = SafeMath.safeDoubleToFloat(doubleUnaryOperator.applyAsDouble(f));
        this.insert(-n - 1, f, f2);
        return f2;
    }

    @Override
    public float computeIfAbsentNullable(float f, DoubleFunction<? extends Float> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        Float f2 = doubleFunction.apply(f);
        if (f2 == null) {
            return this.defRetValue;
        }
        float f3 = f2.floatValue();
        this.insert(-n - 1, f, f3);
        return f3;
    }

    @Override
    public float computeIfPresent(float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        Float f2 = biFunction.apply(Float.valueOf(f), Float.valueOf(this.value[n]));
        if (f2 == null) {
            if (Float.floatToIntBits(f) == 0) {
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
    public float compute(float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        Float f2 = biFunction.apply(Float.valueOf(f), n >= 0 ? Float.valueOf(this.value[n]) : null);
        if (f2 == null) {
            if (n >= 0) {
                if (Float.floatToIntBits(f) == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        float f3 = f2.floatValue();
        if (n < 0) {
            this.insert(-n - 1, f, f3);
            return f3;
        }
        this.value[n] = f3;
        return this.value[n];
    }

    @Override
    public float merge(float f, float f2, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            this.insert(-n - 1, f, f2);
            return f2;
        }
        Float f3 = biFunction.apply(Float.valueOf(this.value[n]), Float.valueOf(f2));
        if (f3 == null) {
            if (Float.floatToIntBits(f) == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = f3.floatValue();
        return this.value[n];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0.0f);
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
    public float firstFloatKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public float lastFloatKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public Float2FloatSortedMap tailMap(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float2FloatSortedMap headMap(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float2FloatSortedMap subMap(float f, float f2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FloatComparator comparator() {
        return null;
    }

    @Override
    public Float2FloatSortedMap.FastSortedEntrySet float2FloatEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public FloatSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public FloatCollection values() {
        if (this.values == null) {
            this.values = new AbstractFloatCollection(this){
                final Float2FloatLinkedOpenHashMap this$0;
                {
                    this.this$0 = float2FloatLinkedOpenHashMap;
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
                        if (Float.floatToIntBits(this.this$0.key[n]) == 0) continue;
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
        float[] fArray = this.key;
        float[] fArray2 = this.value;
        int n2 = n - 1;
        float[] fArray3 = new float[n + 1];
        float[] fArray4 = new float[n + 1];
        int n3 = this.first;
        int n4 = -1;
        int n5 = -1;
        long[] lArray = this.link;
        long[] lArray2 = new long[n + 1];
        this.first = -1;
        int n6 = this.size;
        while (n6-- != 0) {
            int n7;
            if (Float.floatToIntBits(fArray[n3]) == 0) {
                n7 = n;
            } else {
                n7 = HashCommon.mix(HashCommon.float2int(fArray[n3])) & n2;
                while (Float.floatToIntBits(fArray3[n7]) != 0) {
                    n7 = n7 + 1 & n2;
                }
            }
            fArray3[n7] = fArray[n3];
            fArray4[n7] = fArray2[n3];
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
        this.key = fArray3;
        this.value = fArray4;
    }

    public Float2FloatLinkedOpenHashMap clone() {
        Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap;
        try {
            float2FloatLinkedOpenHashMap = (Float2FloatLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2FloatLinkedOpenHashMap.keys = null;
        float2FloatLinkedOpenHashMap.values = null;
        float2FloatLinkedOpenHashMap.entries = null;
        float2FloatLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        float2FloatLinkedOpenHashMap.key = (float[])this.key.clone();
        float2FloatLinkedOpenHashMap.value = (float[])this.value.clone();
        float2FloatLinkedOpenHashMap.link = (long[])this.link.clone();
        return float2FloatLinkedOpenHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (Float.floatToIntBits(this.key[n3]) == 0) {
                ++n3;
            }
            n4 = HashCommon.float2int(this.key[n3]);
            n += (n4 ^= HashCommon.float2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.float2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        float[] fArray = this.key;
        float[] fArray2 = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeFloat(fArray[n2]);
            objectOutputStream.writeFloat(fArray2[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new float[this.n + 1];
        float[] fArray = this.key;
        this.value = new float[this.n + 1];
        float[] fArray2 = this.value;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            float f = objectInputStream.readFloat();
            float f2 = objectInputStream.readFloat();
            if (Float.floatToIntBits(f) == 0) {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
                while (Float.floatToIntBits(fArray[n3]) != 0) {
                    n3 = n3 + 1 & this.mask;
                }
            }
            fArray[n3] = f;
            fArray2[n3] = f2;
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
    public ObjectSortedSet float2FloatEntrySet() {
        return this.float2FloatEntrySet();
    }

    @Override
    public FloatSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet float2FloatEntrySet() {
        return this.float2FloatEntrySet();
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

    static float access$100(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
        return float2FloatLinkedOpenHashMap.removeNullEntry();
    }

    static float access$200(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, int n) {
        return float2FloatLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements FloatListIterator {
        final Float2FloatLinkedOpenHashMap this$0;

        @Override
        public float previousFloat() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            super(float2FloatLinkedOpenHashMap);
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
    extends AbstractFloatSortedSet {
        final Float2FloatLinkedOpenHashMap this$0;

        private KeySet(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
            this.this$0 = float2FloatLinkedOpenHashMap;
        }

        @Override
        public FloatListIterator iterator(float f) {
            return new KeyIterator(this.this$0, f);
        }

        @Override
        public FloatListIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(DoubleConsumer doubleConsumer) {
            if (this.this$0.containsNullKey) {
                doubleConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                float f = this.this$0.key[n];
                if (Float.floatToIntBits(f) == 0) continue;
                doubleConsumer.accept(f);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(float f) {
            return this.this$0.containsKey(f);
        }

        @Override
        public boolean remove(float f) {
            int n = this.this$0.size;
            this.this$0.remove(f);
            return this.this$0.size != n;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public float firstFloat() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }

        @Override
        public float lastFloat() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }

        @Override
        public FloatComparator comparator() {
            return null;
        }

        @Override
        public FloatSortedSet tailSet(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public FloatSortedSet headSet(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public FloatSortedSet subSet(float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public FloatBidirectionalIterator iterator(float f) {
            return this.iterator(f);
        }

        @Override
        public FloatIterator iterator() {
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

        KeySet(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, 1 var2_2) {
            this(float2FloatLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements FloatListIterator {
        final Float2FloatLinkedOpenHashMap this$0;

        public KeyIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, float f) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            super(float2FloatLinkedOpenHashMap, f, null);
        }

        @Override
        public float previousFloat() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            super(float2FloatLinkedOpenHashMap);
        }

        @Override
        public float nextFloat() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class MapEntrySet
    extends AbstractObjectSortedSet<Float2FloatMap.Entry>
    implements Float2FloatSortedMap.FastSortedEntrySet {
        final Float2FloatLinkedOpenHashMap this$0;

        private MapEntrySet(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
            this.this$0 = float2FloatLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Float2FloatMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Float2FloatMap.Entry> subSet(Float2FloatMap.Entry entry, Float2FloatMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Float2FloatMap.Entry> headSet(Float2FloatMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Float2FloatMap.Entry> tailSet(Float2FloatMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Float2FloatMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Float2FloatMap.Entry last() {
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
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            float f2 = ((Float)entry.getValue()).floatValue();
            if (Float.floatToIntBits(f) == 0) {
                return this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f2);
            }
            float[] fArray = this.this$0.key;
            int n = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
            float f3 = fArray[n];
            if (Float.floatToIntBits(f3) == 0) {
                return true;
            }
            if (Float.floatToIntBits(f) == Float.floatToIntBits(f3)) {
                return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f2);
            }
            do {
                if (Float.floatToIntBits(f3 = fArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (Float.floatToIntBits(f) != Float.floatToIntBits(f3));
            return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f2);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            float f2 = ((Float)entry.getValue()).floatValue();
            if (Float.floatToIntBits(f) == 0) {
                if (this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f2)) {
                    Float2FloatLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            float[] fArray = this.this$0.key;
            int n = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
            float f3 = fArray[n];
            if (Float.floatToIntBits(f3) == 0) {
                return true;
            }
            if (Float.floatToIntBits(f3) == Float.floatToIntBits(f)) {
                if (Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f2)) {
                    Float2FloatLinkedOpenHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Float.floatToIntBits(f3 = fArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (Float.floatToIntBits(f3) != Float.floatToIntBits(f) || Float.floatToIntBits(this.this$0.value[n]) != Float.floatToIntBits(f2));
            Float2FloatLinkedOpenHashMap.access$200(this.this$0, n);
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
        public ObjectListIterator<Float2FloatMap.Entry> iterator(Float2FloatMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getFloatKey());
        }

        @Override
        public ObjectListIterator<Float2FloatMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Float2FloatMap.Entry> fastIterator(Float2FloatMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getFloatKey());
        }

        @Override
        public void forEach(Consumer<? super Float2FloatMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractFloat2FloatMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Float2FloatMap.Entry> consumer) {
            AbstractFloat2FloatMap.BasicEntry basicEntry = new AbstractFloat2FloatMap.BasicEntry();
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
            return this.tailSet((Float2FloatMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Float2FloatMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Float2FloatMap.Entry)object, (Float2FloatMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Float2FloatMap.Entry)object);
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
            return this.tailSet((Float2FloatMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Float2FloatMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Float2FloatMap.Entry)object, (Float2FloatMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Float2FloatMap.Entry entry) {
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

        MapEntrySet(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, 1 var2_2) {
            this(float2FloatLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Float2FloatMap.Entry> {
        final MapEntry entry;
        final Float2FloatLinkedOpenHashMap this$0;

        public FastEntryIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            super(float2FloatLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, float f) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            super(float2FloatLinkedOpenHashMap, f, null);
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
            super.add((Float2FloatMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Float2FloatMap.Entry)object);
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
    implements ObjectListIterator<Float2FloatMap.Entry> {
        private MapEntry entry;
        final Float2FloatLinkedOpenHashMap this$0;

        public EntryIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            super(float2FloatLinkedOpenHashMap);
        }

        public EntryIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, float f) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            super(float2FloatLinkedOpenHashMap, f, null);
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
            super.add((Float2FloatMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Float2FloatMap.Entry)object);
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
        final Float2FloatLinkedOpenHashMap this$0;

        protected MapIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = float2FloatLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, float f) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (Float.floatToIntBits(f) == 0) {
                if (float2FloatLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)float2FloatLinkedOpenHashMap.link[float2FloatLinkedOpenHashMap.n];
                    this.prev = float2FloatLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + f + " does not belong to this map.");
            }
            if (Float.floatToIntBits(float2FloatLinkedOpenHashMap.key[float2FloatLinkedOpenHashMap.last]) == Float.floatToIntBits(f)) {
                this.prev = float2FloatLinkedOpenHashMap.last;
                this.index = float2FloatLinkedOpenHashMap.size;
                return;
            }
            int n = HashCommon.mix(HashCommon.float2int(f)) & float2FloatLinkedOpenHashMap.mask;
            while (Float.floatToIntBits(float2FloatLinkedOpenHashMap.key[n]) != 0) {
                if (Float.floatToIntBits(float2FloatLinkedOpenHashMap.key[n]) == Float.floatToIntBits(f)) {
                    this.next = (int)float2FloatLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & float2FloatLinkedOpenHashMap.mask;
            }
            throw new NoSuchElementException("The key " + f + " does not belong to this map.");
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
                float[] fArray = this.this$0.key;
                while (true) {
                    float f;
                    int n2 = n;
                    n = n2 + 1 & this.this$0.mask;
                    while (true) {
                        if (Float.floatToIntBits(f = fArray[n]) == 0) {
                            fArray[n2] = 0.0f;
                            return;
                        }
                        int n3 = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
                        if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                        n = n + 1 & this.this$0.mask;
                    }
                    fArray[n2] = f;
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

        public void set(Float2FloatMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Float2FloatMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, float f, 1 var3_3) {
            this(float2FloatLinkedOpenHashMap, f);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Float2FloatMap.Entry,
    Map.Entry<Float, Float> {
        int index;
        final Float2FloatLinkedOpenHashMap this$0;

        MapEntry(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap, int n) {
            this.this$0 = float2FloatLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Float2FloatLinkedOpenHashMap float2FloatLinkedOpenHashMap) {
            this.this$0 = float2FloatLinkedOpenHashMap;
        }

        @Override
        public float getFloatKey() {
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
        public Float getKey() {
            return Float.valueOf(this.this$0.key[this.index]);
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
            return Float.floatToIntBits(this.this$0.key[this.index]) == Float.floatToIntBits(((Float)entry.getKey()).floatValue()) && Float.floatToIntBits(this.this$0.value[this.index]) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.this$0.key[this.index]) ^ HashCommon.float2int(this.this$0.value[this.index]);
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

