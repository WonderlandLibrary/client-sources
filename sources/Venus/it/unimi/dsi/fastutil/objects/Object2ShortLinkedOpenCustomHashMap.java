/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap;
import it.unimi.dsi.fastutil.objects.AbstractObject2ShortSortedMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortSortedMap;
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
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Object2ShortLinkedOpenCustomHashMap<K>
extends AbstractObject2ShortSortedMap<K>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient short[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected Hash.Strategy<K> strategy;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Object2ShortSortedMap.FastSortedEntrySet<K> entries;
    protected transient ObjectSortedSet<K> keys;
    protected transient ShortCollection values;

    public Object2ShortLinkedOpenCustomHashMap(int n, float f, Hash.Strategy<K> strategy) {
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
        this.key = new Object[this.n + 1];
        this.value = new short[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Object2ShortLinkedOpenCustomHashMap(int n, Hash.Strategy<K> strategy) {
        this(n, 0.75f, strategy);
    }

    public Object2ShortLinkedOpenCustomHashMap(Hash.Strategy<K> strategy) {
        this(16, 0.75f, strategy);
    }

    public Object2ShortLinkedOpenCustomHashMap(Map<? extends K, ? extends Short> map, float f, Hash.Strategy<K> strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Object2ShortLinkedOpenCustomHashMap(Map<? extends K, ? extends Short> map, Hash.Strategy<K> strategy) {
        this(map, 0.75f, strategy);
    }

    public Object2ShortLinkedOpenCustomHashMap(Object2ShortMap<K> object2ShortMap, float f, Hash.Strategy<K> strategy) {
        this(object2ShortMap.size(), f, strategy);
        this.putAll(object2ShortMap);
    }

    public Object2ShortLinkedOpenCustomHashMap(Object2ShortMap<K> object2ShortMap, Hash.Strategy<K> strategy) {
        this(object2ShortMap, 0.75f, strategy);
    }

    public Object2ShortLinkedOpenCustomHashMap(K[] KArray, short[] sArray, float f, Hash.Strategy<K> strategy) {
        this(KArray.length, f, strategy);
        if (KArray.length != sArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + sArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], sArray[i]);
        }
    }

    public Object2ShortLinkedOpenCustomHashMap(K[] KArray, short[] sArray, Hash.Strategy<K> strategy) {
        this(KArray, sArray, 0.75f, strategy);
    }

    public Hash.Strategy<K> strategy() {
        return this.strategy;
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
        this.key[this.n] = null;
        short s = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return s;
    }

    @Override
    public void putAll(Map<? extends K, ? extends Short> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(K k) {
        if (this.strategy.equals(k, null)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return -(n + 1);
        }
        if (this.strategy.equals(k, k2)) {
            return n;
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return -(n + 1);
        } while (!this.strategy.equals(k, k2));
        return n;
    }

    private void insert(int n, K k, short s) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = k;
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
    public short put(K k, short s) {
        int n = this.find(k);
        if (n < 0) {
            this.insert(-n - 1, k, s);
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

    public short addTo(K k, short s) {
        int n;
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, s);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            K[] KArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (this.strategy.equals(k2, k)) {
                    return this.addToValue(n, s);
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (!this.strategy.equals(k2, k)) continue;
                    return this.addToValue(n, s);
                }
            }
        }
        this.key[n] = k;
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
        K[] KArray = this.key;
        while (true) {
            K k;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((k = KArray[n]) == null) {
                    KArray[n2] = null;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            KArray[n2] = k;
            this.value[n2] = this.value[n];
            this.fixPointers(n, n2);
        }
    }

    @Override
    public short removeShort(Object object) {
        if (this.strategy.equals(object, null)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (this.strategy.equals(object, k)) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(object, k));
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
            this.key[this.n] = null;
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
            this.key[this.n] = null;
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

    public short getAndMoveToFirst(K k) {
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, k2)) {
            this.moveIndexToFirst(n);
            return this.value[n];
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, k2));
        this.moveIndexToFirst(n);
        return this.value[n];
    }

    public short getAndMoveToLast(K k) {
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, k2)) {
            this.moveIndexToLast(n);
            return this.value[n];
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, k2));
        this.moveIndexToLast(n);
        return this.value[n];
    }

    public short putAndMoveToFirst(K k, short s) {
        int n;
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, s);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            K[] KArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (this.strategy.equals(k2, k)) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, s);
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (!this.strategy.equals(k2, k)) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, s);
                }
            }
        }
        this.key[n] = k;
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

    public short putAndMoveToLast(K k, short s) {
        int n;
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, s);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            K[] KArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (this.strategy.equals(k2, k)) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, s);
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (!this.strategy.equals(k2, k)) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, s);
                }
            }
        }
        this.key[n] = k;
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
    public short getShort(Object object) {
        if (this.strategy.equals(object, null)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (this.strategy.equals(object, k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(object, k));
        return this.value[n];
    }

    @Override
    public boolean containsKey(Object object) {
        if (this.strategy.equals(object, null)) {
            return this.containsNullKey;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (this.strategy.equals(object, k)) {
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!this.strategy.equals(object, k));
        return false;
    }

    @Override
    public boolean containsValue(short s) {
        short[] sArray = this.value;
        K[] KArray = this.key;
        if (this.containsNullKey && sArray[this.n] == s) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (KArray[n] == null || sArray[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public short getOrDefault(Object object, short s) {
        if (this.strategy.equals(object, null)) {
            return this.containsNullKey ? this.value[this.n] : s;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return s;
        }
        if (this.strategy.equals(object, k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return s;
        } while (!this.strategy.equals(object, k));
        return this.value[n];
    }

    @Override
    public short putIfAbsent(K k, short s) {
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, k, s);
        return this.defRetValue;
    }

    @Override
    public boolean remove(Object object, short s) {
        if (this.strategy.equals(object, null)) {
            if (this.containsNullKey && s == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (this.strategy.equals(object, k) && s == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!this.strategy.equals(object, k) || s != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(K k, short s, short s2) {
        int n = this.find(k);
        if (n < 0 || s != this.value[n]) {
            return true;
        }
        this.value[n] = s2;
        return false;
    }

    @Override
    public short replace(K k, short s) {
        int n = this.find(k);
        if (n < 0) {
            return this.defRetValue;
        }
        short s2 = this.value[n];
        this.value[n] = s;
        return s2;
    }

    @Override
    public short computeShortIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        short s = SafeMath.safeIntToShort(toIntFunction.applyAsInt(k));
        this.insert(-n - 1, k, s);
        return s;
    }

    @Override
    public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            return this.defRetValue;
        }
        Short s = biFunction.apply(k, this.value[n]);
        if (s == null) {
            if (this.strategy.equals(k, null)) {
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
    public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        Short s = biFunction.apply(k, n >= 0 ? Short.valueOf(this.value[n]) : null);
        if (s == null) {
            if (n >= 0) {
                if (this.strategy.equals(k, null)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        short s2 = s;
        if (n < 0) {
            this.insert(-n - 1, k, s2);
            return s2;
        }
        this.value[n] = s2;
        return this.value[n];
    }

    @Override
    public short mergeShort(K k, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            this.insert(-n - 1, k, s);
            return s;
        }
        Short s2 = biFunction.apply((Short)this.value[n], (Short)s);
        if (s2 == null) {
            if (this.strategy.equals(k, null)) {
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
        Arrays.fill(this.key, null);
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
    public K firstKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public K lastKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public Object2ShortSortedMap<K> tailMap(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object2ShortSortedMap<K> headMap(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object2ShortSortedMap<K> subMap(K k, K k2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparator<? super K> comparator() {
        return null;
    }

    @Override
    public Object2ShortSortedMap.FastSortedEntrySet<K> object2ShortEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ShortCollection values() {
        if (this.values == null) {
            this.values = new AbstractShortCollection(this){
                final Object2ShortLinkedOpenCustomHashMap this$0;
                {
                    this.this$0 = object2ShortLinkedOpenCustomHashMap;
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
                        if (this.this$0.key[n] == null) continue;
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
        K[] KArray = this.key;
        short[] sArray = this.value;
        int n2 = n - 1;
        Object[] objectArray = new Object[n + 1];
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
            if (this.strategy.equals(KArray[n3], null)) {
                n7 = n;
            } else {
                n7 = HashCommon.mix(this.strategy.hashCode(KArray[n3])) & n2;
                while (objectArray[n7] != null) {
                    n7 = n7 + 1 & n2;
                }
            }
            objectArray[n7] = KArray[n3];
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
        this.key = objectArray;
        this.value = sArray2;
    }

    public Object2ShortLinkedOpenCustomHashMap<K> clone() {
        Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap;
        try {
            object2ShortLinkedOpenCustomHashMap = (Object2ShortLinkedOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2ShortLinkedOpenCustomHashMap.keys = null;
        object2ShortLinkedOpenCustomHashMap.values = null;
        object2ShortLinkedOpenCustomHashMap.entries = null;
        object2ShortLinkedOpenCustomHashMap.containsNullKey = this.containsNullKey;
        object2ShortLinkedOpenCustomHashMap.key = (Object[])this.key.clone();
        object2ShortLinkedOpenCustomHashMap.value = (short[])this.value.clone();
        object2ShortLinkedOpenCustomHashMap.link = (long[])this.link.clone();
        object2ShortLinkedOpenCustomHashMap.strategy = this.strategy;
        return object2ShortLinkedOpenCustomHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == null) {
                ++n3;
            }
            if (this != this.key[n3]) {
                n4 = this.strategy.hashCode(this.key[n3]);
            }
            n += (n4 ^= this.value[n3]);
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n];
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        K[] KArray = this.key;
        short[] sArray = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeObject(KArray[n2]);
            objectOutputStream.writeShort(sArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] objectArray = this.key;
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
            Object object = objectInputStream.readObject();
            short s = objectInputStream.readShort();
            if (this.strategy.equals(object, null)) {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = HashCommon.mix(this.strategy.hashCode(object)) & this.mask;
                while (objectArray[n3] != null) {
                    n3 = n3 + 1 & this.mask;
                }
            }
            objectArray[n3] = object;
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
    public ObjectSortedSet object2ShortEntrySet() {
        return this.object2ShortEntrySet();
    }

    @Override
    public ObjectSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet object2ShortEntrySet() {
        return this.object2ShortEntrySet();
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
    public SortedMap tailMap(Object object) {
        return this.tailMap(object);
    }

    @Override
    public SortedMap headMap(Object object) {
        return this.headMap(object);
    }

    @Override
    public SortedMap subMap(Object object, Object object2) {
        return this.subMap(object, object2);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static short access$100(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
        return object2ShortLinkedOpenCustomHashMap.removeNullEntry();
    }

    static short access$200(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, int n) {
        return object2ShortLinkedOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ShortListIterator {
        final Object2ShortLinkedOpenCustomHashMap this$0;

        @Override
        public short previousShort() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            super(object2ShortLinkedOpenCustomHashMap);
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
    extends AbstractObjectSortedSet<K> {
        final Object2ShortLinkedOpenCustomHashMap this$0;

        private KeySet(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
        }

        @Override
        public ObjectListIterator<K> iterator(K k) {
            return new KeyIterator(this.this$0, k);
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(Consumer<? super K> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                Object k = this.this$0.key[n];
                if (k == null) continue;
                consumer.accept(k);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public boolean remove(Object object) {
            int n = this.this$0.size;
            this.this$0.removeShort(object);
            return this.this$0.size != n;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public K first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }

        @Override
        public K last() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }

        @Override
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<K> tailSet(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<K> headSet(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<K> subSet(K k, K k2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator(object);
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
        public SortedSet tailSet(Object object) {
            return this.tailSet(object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet(object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet(object, object2);
        }

        KeySet(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, 1 var2_2) {
            this(object2ShortLinkedOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ObjectListIterator<K> {
        final Object2ShortLinkedOpenCustomHashMap this$0;

        public KeyIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, K k) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            super(object2ShortLinkedOpenCustomHashMap, k, null);
        }

        @Override
        public K previous() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            super(object2ShortLinkedOpenCustomHashMap);
        }

        @Override
        public K next() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class MapEntrySet
    extends AbstractObjectSortedSet<Object2ShortMap.Entry<K>>
    implements Object2ShortSortedMap.FastSortedEntrySet<K> {
        final Object2ShortLinkedOpenCustomHashMap this$0;

        private MapEntrySet(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Object2ShortMap.Entry<K>> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Object2ShortMap.Entry<K>> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Object2ShortMap.Entry<K>> subSet(Object2ShortMap.Entry<K> entry, Object2ShortMap.Entry<K> entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Object2ShortMap.Entry<K>> headSet(Object2ShortMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Object2ShortMap.Entry<K>> tailSet(Object2ShortMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object2ShortMap.Entry<K> first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Object2ShortMap.Entry<K> last() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            Object k = entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(k, null)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s;
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(k)) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (this.this$0.strategy.equals(k, k2)) {
                return this.this$0.value[n] == s;
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!this.this$0.strategy.equals(k, k2));
            return this.this$0.value[n] == s;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            Object k = entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(k, null)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s) {
                    Object2ShortLinkedOpenCustomHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(k)) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (this.this$0.strategy.equals(k2, k)) {
                if (this.this$0.value[n] == s) {
                    Object2ShortLinkedOpenCustomHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!this.this$0.strategy.equals(k2, k) || this.this$0.value[n] != s);
            Object2ShortLinkedOpenCustomHashMap.access$200(this.this$0, n);
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
        public ObjectListIterator<Object2ShortMap.Entry<K>> iterator(Object2ShortMap.Entry<K> entry) {
            return new EntryIterator(this.this$0, entry.getKey());
        }

        @Override
        public ObjectListIterator<Object2ShortMap.Entry<K>> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        @Override
        public ObjectListIterator<Object2ShortMap.Entry<K>> fastIterator(Object2ShortMap.Entry<K> entry) {
            return new FastEntryIterator(this.this$0, entry.getKey());
        }

        @Override
        public void forEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractObject2ShortMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
            AbstractObject2ShortMap.BasicEntry basicEntry = new AbstractObject2ShortMap.BasicEntry();
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
            return this.tailSet((Object2ShortMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Object2ShortMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Object2ShortMap.Entry)object, (Object2ShortMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Object2ShortMap.Entry)object);
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
            return this.tailSet((Object2ShortMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Object2ShortMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Object2ShortMap.Entry)object, (Object2ShortMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator fastIterator(Object2ShortMap.Entry entry) {
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

        MapEntrySet(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, 1 var2_2) {
            this(object2ShortLinkedOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Object2ShortMap.Entry<K>> {
        final MapEntry entry;
        final Object2ShortLinkedOpenCustomHashMap this$0;

        public FastEntryIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            super(object2ShortLinkedOpenCustomHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, K k) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            super(object2ShortLinkedOpenCustomHashMap, k, null);
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
            super.add((Object2ShortMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Object2ShortMap.Entry)object);
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
    implements ObjectListIterator<Object2ShortMap.Entry<K>> {
        private MapEntry entry;
        final Object2ShortLinkedOpenCustomHashMap this$0;

        public EntryIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            super(object2ShortLinkedOpenCustomHashMap);
        }

        public EntryIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, K k) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            super(object2ShortLinkedOpenCustomHashMap, k, null);
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
            super.add((Object2ShortMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Object2ShortMap.Entry)object);
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
        final Object2ShortLinkedOpenCustomHashMap this$0;

        protected MapIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = object2ShortLinkedOpenCustomHashMap.first;
            this.index = 0;
        }

        private MapIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, K k) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (object2ShortLinkedOpenCustomHashMap.strategy.equals(k, null)) {
                if (object2ShortLinkedOpenCustomHashMap.containsNullKey) {
                    this.next = (int)object2ShortLinkedOpenCustomHashMap.link[object2ShortLinkedOpenCustomHashMap.n];
                    this.prev = object2ShortLinkedOpenCustomHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + k + " does not belong to this map.");
            }
            if (object2ShortLinkedOpenCustomHashMap.strategy.equals(object2ShortLinkedOpenCustomHashMap.key[object2ShortLinkedOpenCustomHashMap.last], k)) {
                this.prev = object2ShortLinkedOpenCustomHashMap.last;
                this.index = object2ShortLinkedOpenCustomHashMap.size;
                return;
            }
            int n = HashCommon.mix(object2ShortLinkedOpenCustomHashMap.strategy.hashCode(k)) & object2ShortLinkedOpenCustomHashMap.mask;
            while (object2ShortLinkedOpenCustomHashMap.key[n] != null) {
                if (object2ShortLinkedOpenCustomHashMap.strategy.equals(object2ShortLinkedOpenCustomHashMap.key[n], k)) {
                    this.next = (int)object2ShortLinkedOpenCustomHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & object2ShortLinkedOpenCustomHashMap.mask;
            }
            throw new NoSuchElementException("The key " + k + " does not belong to this map.");
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
                K[] KArray = this.this$0.key;
                while (true) {
                    Object k;
                    int n2 = n;
                    n = n2 + 1 & this.this$0.mask;
                    while (true) {
                        if ((k = KArray[n]) == null) {
                            KArray[n2] = null;
                            return;
                        }
                        int n3 = HashCommon.mix(this.this$0.strategy.hashCode(k)) & this.this$0.mask;
                        if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                        n = n + 1 & this.this$0.mask;
                    }
                    KArray[n2] = k;
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
            this.this$0.key[this.this$0.n] = null;
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

        public void set(Object2ShortMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Object2ShortMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, Object object, 1 var3_3) {
            this(object2ShortLinkedOpenCustomHashMap, object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Object2ShortMap.Entry<K>,
    Map.Entry<K, Short> {
        int index;
        final Object2ShortLinkedOpenCustomHashMap this$0;

        MapEntry(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap, int n) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Object2ShortLinkedOpenCustomHashMap object2ShortLinkedOpenCustomHashMap) {
            this.this$0 = object2ShortLinkedOpenCustomHashMap;
        }

        @Override
        public K getKey() {
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
            return this.this$0.strategy.equals(this.this$0.key[this.index], entry.getKey()) && this.this$0.value[this.index] == (Short)entry.getValue();
        }

        @Override
        public int hashCode() {
            return this.this$0.strategy.hashCode(this.this$0.key[this.index]) ^ this.this$0.value[this.index];
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
    }
}

