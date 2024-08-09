/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ShortMap;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ShortSortedMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2ShortMap;
import it.unimi.dsi.fastutil.floats.Float2ShortSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
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
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Float2ShortLinkedOpenHashMap
extends AbstractFloat2ShortSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[] key;
    protected transient short[] value;
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
    protected transient Float2ShortSortedMap.FastSortedEntrySet entries;
    protected transient FloatSortedSet keys;
    protected transient ShortCollection values;

    public Float2ShortLinkedOpenHashMap(int n, float f) {
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
        this.value = new short[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Float2ShortLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Float2ShortLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Float2ShortLinkedOpenHashMap(Map<? extends Float, ? extends Short> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Float2ShortLinkedOpenHashMap(Map<? extends Float, ? extends Short> map) {
        this(map, 0.75f);
    }

    public Float2ShortLinkedOpenHashMap(Float2ShortMap float2ShortMap, float f) {
        this(float2ShortMap.size(), f);
        this.putAll(float2ShortMap);
    }

    public Float2ShortLinkedOpenHashMap(Float2ShortMap float2ShortMap) {
        this(float2ShortMap, 0.75f);
    }

    public Float2ShortLinkedOpenHashMap(float[] fArray, short[] sArray, float f) {
        this(fArray.length, f);
        if (fArray.length != sArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + fArray.length + " and " + sArray.length + ")");
        }
        for (int i = 0; i < fArray.length; ++i) {
            this.put(fArray[i], sArray[i]);
        }
    }

    public Float2ShortLinkedOpenHashMap(float[] fArray, short[] sArray) {
        this(fArray, sArray, 0.75f);
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

    private short removeEntry(int n) {
        short s = this.value[n];
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return s;
    }

    private short removeNullEntry() {
        this.containsNullKey = false;
        short s = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return s;
    }

    @Override
    public void putAll(Map<? extends Float, ? extends Short> map) {
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

    private void insert(int n, float f, short s) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = f;
        this.value[n] = s;
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
    public short put(float f, short s) {
        int n = this.find(f);
        if (n < 0) {
            this.insert(-n - 1, f, s);
            return this.defRetValue;
        }
        short s2 = this.value[n];
        this.value[n] = s;
        return s2;
    }

    private short addToValue(int n, short s) {
        short s2 = this.value[n];
        this.value[n] = (short)(s2 + s);
        return s2;
    }

    public short addTo(float f, short s) {
        int n;
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, s);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            float[] fArray = this.key;
            n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) != 0) {
                if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
                    return this.addToValue(n, s);
                }
                while (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(f2) != Float.floatToIntBits(f)) continue;
                    return this.addToValue(n, s);
                }
            }
        }
        this.key[n] = f;
        this.value[n] = (short)(this.defRetValue + s);
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
    public short remove(float f) {
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

    private short setValue(int n, short s) {
        short s2 = this.value[n];
        this.value[n] = s;
        return s2;
    }

    public short removeFirstShort() {
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
        short s = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return s;
    }

    public short removeLastShort() {
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
        short s = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return s;
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

    public short getAndMoveToFirst(float f) {
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

    public short getAndMoveToLast(float f) {
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

    public short putAndMoveToFirst(float f, short s) {
        int n;
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, s);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            float[] fArray = this.key;
            n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) != 0) {
                if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, s);
                }
                while (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(f2) != Float.floatToIntBits(f)) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, s);
                }
            }
        }
        this.key[n] = f;
        this.value[n] = s;
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

    public short putAndMoveToLast(float f, short s) {
        int n;
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, s);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            float[] fArray = this.key;
            n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) != 0) {
                if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, s);
                }
                while (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(f2) != Float.floatToIntBits(f)) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, s);
                }
            }
        }
        this.key[n] = f;
        this.value[n] = s;
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
    public short get(float f) {
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
    public boolean containsValue(short s) {
        short[] sArray = this.value;
        float[] fArray = this.key;
        if (this.containsNullKey && sArray[this.n] == s) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) == 0 || sArray[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public short getOrDefault(float f, short s) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey ? this.value[this.n] : s;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return s;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return s;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return this.value[n];
    }

    @Override
    public short putIfAbsent(float f, short s) {
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, f, s);
        return this.defRetValue;
    }

    @Override
    public boolean remove(float f, short s) {
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey && s == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2) && s == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2) || s != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(float f, short s, short s2) {
        int n = this.find(f);
        if (n < 0 || s != this.value[n]) {
            return true;
        }
        this.value[n] = s2;
        return false;
    }

    @Override
    public short replace(float f, short s) {
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        short s2 = this.value[n];
        this.value[n] = s;
        return s2;
    }

    @Override
    public short computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        short s = SafeMath.safeIntToShort(doubleToIntFunction.applyAsInt(f));
        this.insert(-n - 1, f, s);
        return s;
    }

    @Override
    public short computeIfAbsentNullable(float f, DoubleFunction<? extends Short> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        Short s = doubleFunction.apply(f);
        if (s == null) {
            return this.defRetValue;
        }
        short s2 = s;
        this.insert(-n - 1, f, s2);
        return s2;
    }

    @Override
    public short computeIfPresent(float f, BiFunction<? super Float, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        Short s = biFunction.apply(Float.valueOf(f), (Short)this.value[n]);
        if (s == null) {
            if (Float.floatToIntBits(f) == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = s;
        return this.value[n];
    }

    @Override
    public short compute(float f, BiFunction<? super Float, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        Short s = biFunction.apply(Float.valueOf(f), n >= 0 ? Short.valueOf(this.value[n]) : null);
        if (s == null) {
            if (n >= 0) {
                if (Float.floatToIntBits(f) == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        short s2 = s;
        if (n < 0) {
            this.insert(-n - 1, f, s2);
            return s2;
        }
        this.value[n] = s2;
        return this.value[n];
    }

    @Override
    public short merge(float f, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            this.insert(-n - 1, f, s);
            return s;
        }
        Short s2 = biFunction.apply((Short)this.value[n], (Short)s);
        if (s2 == null) {
            if (Float.floatToIntBits(f) == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = s2;
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
    public Float2ShortSortedMap tailMap(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float2ShortSortedMap headMap(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float2ShortSortedMap subMap(float f, float f2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FloatComparator comparator() {
        return null;
    }

    @Override
    public Float2ShortSortedMap.FastSortedEntrySet float2ShortEntrySet() {
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
    public ShortCollection values() {
        if (this.values == null) {
            this.values = new AbstractShortCollection(this){
                final Float2ShortLinkedOpenHashMap this$0;
                {
                    this.this$0 = float2ShortLinkedOpenHashMap;
                }

                @Override
                public ShortIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(short s) {
                    return this.this$0.containsValue(s);
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public void forEach(IntConsumer intConsumer) {
                    if (this.this$0.containsNullKey) {
                        intConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (Float.floatToIntBits(this.this$0.key[n]) == 0) continue;
                        intConsumer.accept(this.this$0.value[n]);
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
        short[] sArray = this.value;
        int n2 = n - 1;
        float[] fArray2 = new float[n + 1];
        short[] sArray2 = new short[n + 1];
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
                while (Float.floatToIntBits(fArray2[n7]) != 0) {
                    n7 = n7 + 1 & n2;
                }
            }
            fArray2[n7] = fArray[n3];
            sArray2[n7] = sArray[n3];
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
        this.key = fArray2;
        this.value = sArray2;
    }

    public Float2ShortLinkedOpenHashMap clone() {
        Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap;
        try {
            float2ShortLinkedOpenHashMap = (Float2ShortLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2ShortLinkedOpenHashMap.keys = null;
        float2ShortLinkedOpenHashMap.values = null;
        float2ShortLinkedOpenHashMap.entries = null;
        float2ShortLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        float2ShortLinkedOpenHashMap.key = (float[])this.key.clone();
        float2ShortLinkedOpenHashMap.value = (short[])this.value.clone();
        float2ShortLinkedOpenHashMap.link = (long[])this.link.clone();
        return float2ShortLinkedOpenHashMap;
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
            n += (n4 ^= this.value[n3]);
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n];
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        float[] fArray = this.key;
        short[] sArray = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeFloat(fArray[n2]);
            objectOutputStream.writeShort(sArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new float[this.n + 1];
        float[] fArray = this.key;
        this.value = new short[this.n + 1];
        short[] sArray = this.value;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            float f = objectInputStream.readFloat();
            short s = objectInputStream.readShort();
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
            sArray[n3] = s;
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
    public ObjectSortedSet float2ShortEntrySet() {
        return this.float2ShortEntrySet();
    }

    @Override
    public FloatSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet float2ShortEntrySet() {
        return this.float2ShortEntrySet();
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

    static short access$100(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
        return float2ShortLinkedOpenHashMap.removeNullEntry();
    }

    static short access$200(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, int n) {
        return float2ShortLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ShortListIterator {
        final Float2ShortLinkedOpenHashMap this$0;

        @Override
        public short previousShort() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            super(float2ShortLinkedOpenHashMap);
        }

        @Override
        public short nextShort() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractFloatSortedSet {
        final Float2ShortLinkedOpenHashMap this$0;

        private KeySet(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
            this.this$0 = float2ShortLinkedOpenHashMap;
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

        KeySet(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, 1 var2_2) {
            this(float2ShortLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements FloatListIterator {
        final Float2ShortLinkedOpenHashMap this$0;

        public KeyIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, float f) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            super(float2ShortLinkedOpenHashMap, f, null);
        }

        @Override
        public float previousFloat() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            super(float2ShortLinkedOpenHashMap);
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
    extends AbstractObjectSortedSet<Float2ShortMap.Entry>
    implements Float2ShortSortedMap.FastSortedEntrySet {
        final Float2ShortLinkedOpenHashMap this$0;

        private MapEntrySet(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
            this.this$0 = float2ShortLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Float2ShortMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Float2ShortMap.Entry> subSet(Float2ShortMap.Entry entry, Float2ShortMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Float2ShortMap.Entry> headSet(Float2ShortMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Float2ShortMap.Entry> tailSet(Float2ShortMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Float2ShortMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Float2ShortMap.Entry last() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            short s = (Short)entry.getValue();
            if (Float.floatToIntBits(f) == 0) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s;
            }
            float[] fArray = this.this$0.key;
            int n = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) == 0) {
                return true;
            }
            if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
                return this.this$0.value[n] == s;
            }
            do {
                if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
            return this.this$0.value[n] == s;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            short s = (Short)entry.getValue();
            if (Float.floatToIntBits(f) == 0) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s) {
                    Float2ShortLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            float[] fArray = this.this$0.key;
            int n = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) == 0) {
                return true;
            }
            if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
                if (this.this$0.value[n] == s) {
                    Float2ShortLinkedOpenHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || this.this$0.value[n] != s);
            Float2ShortLinkedOpenHashMap.access$200(this.this$0, n);
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
        public ObjectListIterator<Float2ShortMap.Entry> iterator(Float2ShortMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getFloatKey());
        }

        @Override
        public ObjectListIterator<Float2ShortMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Float2ShortMap.Entry> fastIterator(Float2ShortMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getFloatKey());
        }

        @Override
        public void forEach(Consumer<? super Float2ShortMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractFloat2ShortMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Float2ShortMap.Entry> consumer) {
            AbstractFloat2ShortMap.BasicEntry basicEntry = new AbstractFloat2ShortMap.BasicEntry();
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
            return this.tailSet((Float2ShortMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Float2ShortMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Float2ShortMap.Entry)object, (Float2ShortMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Float2ShortMap.Entry)object);
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
            return this.tailSet((Float2ShortMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Float2ShortMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Float2ShortMap.Entry)object, (Float2ShortMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Float2ShortMap.Entry entry) {
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

        MapEntrySet(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, 1 var2_2) {
            this(float2ShortLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Float2ShortMap.Entry> {
        final MapEntry entry;
        final Float2ShortLinkedOpenHashMap this$0;

        public FastEntryIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            super(float2ShortLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, float f) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            super(float2ShortLinkedOpenHashMap, f, null);
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
            super.add((Float2ShortMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Float2ShortMap.Entry)object);
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
    implements ObjectListIterator<Float2ShortMap.Entry> {
        private MapEntry entry;
        final Float2ShortLinkedOpenHashMap this$0;

        public EntryIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            super(float2ShortLinkedOpenHashMap);
        }

        public EntryIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, float f) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            super(float2ShortLinkedOpenHashMap, f, null);
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
            super.add((Float2ShortMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Float2ShortMap.Entry)object);
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
        final Float2ShortLinkedOpenHashMap this$0;

        protected MapIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = float2ShortLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, float f) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (Float.floatToIntBits(f) == 0) {
                if (float2ShortLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)float2ShortLinkedOpenHashMap.link[float2ShortLinkedOpenHashMap.n];
                    this.prev = float2ShortLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + f + " does not belong to this map.");
            }
            if (Float.floatToIntBits(float2ShortLinkedOpenHashMap.key[float2ShortLinkedOpenHashMap.last]) == Float.floatToIntBits(f)) {
                this.prev = float2ShortLinkedOpenHashMap.last;
                this.index = float2ShortLinkedOpenHashMap.size;
                return;
            }
            int n = HashCommon.mix(HashCommon.float2int(f)) & float2ShortLinkedOpenHashMap.mask;
            while (Float.floatToIntBits(float2ShortLinkedOpenHashMap.key[n]) != 0) {
                if (Float.floatToIntBits(float2ShortLinkedOpenHashMap.key[n]) == Float.floatToIntBits(f)) {
                    this.next = (int)float2ShortLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & float2ShortLinkedOpenHashMap.mask;
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

        public void set(Float2ShortMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Float2ShortMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, float f, 1 var3_3) {
            this(float2ShortLinkedOpenHashMap, f);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Float2ShortMap.Entry,
    Map.Entry<Float, Short> {
        int index;
        final Float2ShortLinkedOpenHashMap this$0;

        MapEntry(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap, int n) {
            this.this$0 = float2ShortLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Float2ShortLinkedOpenHashMap float2ShortLinkedOpenHashMap) {
            this.this$0 = float2ShortLinkedOpenHashMap;
        }

        @Override
        public float getFloatKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public short getShortValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public short setValue(short s) {
            short s2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = s;
            return s2;
        }

        @Override
        @Deprecated
        public Float getKey() {
            return Float.valueOf(this.this$0.key[this.index]);
        }

        @Override
        @Deprecated
        public Short getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Short setValue(Short s) {
            return this.setValue((short)s);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Float.floatToIntBits(this.this$0.key[this.index]) == Float.floatToIntBits(((Float)entry.getKey()).floatValue()) && this.this$0.value[this.index] == (Short)entry.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.this$0.key[this.index]) ^ this.this$0.value[this.index];
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Short)object);
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

