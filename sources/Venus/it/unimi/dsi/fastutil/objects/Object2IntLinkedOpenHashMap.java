/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntSortedMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
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
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Object2IntLinkedOpenHashMap<K>
extends AbstractObject2IntSortedMap<K>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient int[] value;
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
    protected transient Object2IntSortedMap.FastSortedEntrySet<K> entries;
    protected transient ObjectSortedSet<K> keys;
    protected transient IntCollection values;

    public Object2IntLinkedOpenHashMap(int n, float f) {
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
        this.value = new int[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Object2IntLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Object2IntLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2IntLinkedOpenHashMap(Map<? extends K, ? extends Integer> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Object2IntLinkedOpenHashMap(Map<? extends K, ? extends Integer> map) {
        this(map, 0.75f);
    }

    public Object2IntLinkedOpenHashMap(Object2IntMap<K> object2IntMap, float f) {
        this(object2IntMap.size(), f);
        this.putAll(object2IntMap);
    }

    public Object2IntLinkedOpenHashMap(Object2IntMap<K> object2IntMap) {
        this(object2IntMap, 0.75f);
    }

    public Object2IntLinkedOpenHashMap(K[] KArray, int[] nArray, float f) {
        this(KArray.length, f);
        if (KArray.length != nArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + nArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], nArray[i]);
        }
    }

    public Object2IntLinkedOpenHashMap(K[] KArray, int[] nArray) {
        this(KArray, nArray, 0.75f);
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

    private int removeEntry(int n) {
        int n2 = this.value[n];
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n2;
    }

    private int removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.n] = null;
        int n = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n;
    }

    @Override
    public void putAll(Map<? extends K, ? extends Integer> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(K k) {
        if (k == null) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(k.hashCode()) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return -(n + 1);
        }
        if (k.equals(k2)) {
            return n;
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return -(n + 1);
        } while (!k.equals(k2));
        return n;
    }

    private void insert(int n, K k, int n2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = k;
        this.value[n] = n2;
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
    public int put(K k, int n) {
        int n2 = this.find(k);
        if (n2 < 0) {
            this.insert(-n2 - 1, k, n);
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        this.value[n2] = n;
        return n3;
    }

    private int addToValue(int n, int n2) {
        int n3 = this.value[n];
        this.value[n] = n3 + n2;
        return n3;
    }

    public int addTo(K k, int n) {
        int n2;
        if (k == null) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, n);
            }
            n2 = this.n;
            this.containsNullKey = true;
        } else {
            K[] KArray = this.key;
            n2 = HashCommon.mix(k.hashCode()) & this.mask;
            K k2 = KArray[n2];
            if (k2 != null) {
                if (k2.equals(k)) {
                    return this.addToValue(n2, n);
                }
                while ((k2 = KArray[n2 = n2 + 1 & this.mask]) != null) {
                    if (!k2.equals(k)) continue;
                    return this.addToValue(n2, n);
                }
            }
        }
        this.key[n2] = k;
        this.value[n2] = this.defRetValue + n;
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n3 = this.last;
            this.link[n3] = this.link[n3] ^ (this.link[this.last] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n2] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n2;
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
                int n3 = HashCommon.mix(k.hashCode()) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            KArray[n2] = k;
            this.value[n2] = this.value[n];
            this.fixPointers(n, n2);
        }
    }

    @Override
    public int removeInt(Object object) {
        if (object == null) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object.equals(k)) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!object.equals(k));
        return this.removeEntry(n);
    }

    private int setValue(int n, int n2) {
        int n3 = this.value[n];
        this.value[n] = n2;
        return n3;
    }

    public int removeFirstInt() {
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
        int n3 = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n3;
    }

    public int removeLastInt() {
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
        int n3 = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n3;
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

    public int getAndMoveToFirst(K k) {
        if (k == null) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(k.hashCode()) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (k.equals(k2)) {
            this.moveIndexToFirst(n);
            return this.value[n];
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!k.equals(k2));
        this.moveIndexToFirst(n);
        return this.value[n];
    }

    public int getAndMoveToLast(K k) {
        if (k == null) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(k.hashCode()) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (k.equals(k2)) {
            this.moveIndexToLast(n);
            return this.value[n];
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!k.equals(k2));
        this.moveIndexToLast(n);
        return this.value[n];
    }

    public int putAndMoveToFirst(K k, int n) {
        int n2;
        if (k == null) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, n);
            }
            this.containsNullKey = true;
            n2 = this.n;
        } else {
            K[] KArray = this.key;
            n2 = HashCommon.mix(k.hashCode()) & this.mask;
            K k2 = KArray[n2];
            if (k2 != null) {
                if (k2.equals(k)) {
                    this.moveIndexToFirst(n2);
                    return this.setValue(n2, n);
                }
                while ((k2 = KArray[n2 = n2 + 1 & this.mask]) != null) {
                    if (!k2.equals(k)) continue;
                    this.moveIndexToFirst(n2);
                    return this.setValue(n2, n);
                }
            }
        }
        this.key[n2] = k;
        this.value[n2] = n;
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n3 = this.first;
            this.link[n3] = this.link[n3] ^ (this.link[this.first] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[n2] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
            this.first = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }

    public int putAndMoveToLast(K k, int n) {
        int n2;
        if (k == null) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, n);
            }
            this.containsNullKey = true;
            n2 = this.n;
        } else {
            K[] KArray = this.key;
            n2 = HashCommon.mix(k.hashCode()) & this.mask;
            K k2 = KArray[n2];
            if (k2 != null) {
                if (k2.equals(k)) {
                    this.moveIndexToLast(n2);
                    return this.setValue(n2, n);
                }
                while ((k2 = KArray[n2 = n2 + 1 & this.mask]) != null) {
                    if (!k2.equals(k)) continue;
                    this.moveIndexToLast(n2);
                    return this.setValue(n2, n);
                }
            }
        }
        this.key[n2] = k;
        this.value[n2] = n;
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n3 = this.last;
            this.link[n3] = this.link[n3] ^ (this.link[this.last] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n2] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }

    @Override
    public int getInt(Object object) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object.equals(k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!object.equals(k));
        return this.value[n];
    }

    @Override
    public boolean containsKey(Object object) {
        if (object == null) {
            return this.containsNullKey;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object.equals(k)) {
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k));
        return false;
    }

    @Override
    public boolean containsValue(int n) {
        int[] nArray = this.value;
        K[] KArray = this.key;
        if (this.containsNullKey && nArray[this.n] == n) {
            return false;
        }
        int n2 = this.n;
        while (n2-- != 0) {
            if (KArray[n2] == null || nArray[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getOrDefault(Object object, int n) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : n;
        }
        K[] KArray = this.key;
        int n2 = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n2];
        if (k == null) {
            return n;
        }
        if (object.equals(k)) {
            return this.value[n2];
        }
        do {
            if ((k = KArray[n2 = n2 + 1 & this.mask]) != null) continue;
            return n;
        } while (!object.equals(k));
        return this.value[n2];
    }

    @Override
    public int putIfAbsent(K k, int n) {
        int n2 = this.find(k);
        if (n2 >= 0) {
            return this.value[n2];
        }
        this.insert(-n2 - 1, k, n);
        return this.defRetValue;
    }

    @Override
    public boolean remove(Object object, int n) {
        if (object == null) {
            if (this.containsNullKey && n == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        K[] KArray = this.key;
        int n2 = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n2];
        if (k == null) {
            return true;
        }
        if (object.equals(k) && n == this.value[n2]) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if ((k = KArray[n2 = n2 + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k) || n != this.value[n2]);
        this.removeEntry(n2);
        return false;
    }

    @Override
    public boolean replace(K k, int n, int n2) {
        int n3 = this.find(k);
        if (n3 < 0 || n != this.value[n3]) {
            return true;
        }
        this.value[n3] = n2;
        return false;
    }

    @Override
    public int replace(K k, int n) {
        int n2 = this.find(k);
        if (n2 < 0) {
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        this.value[n2] = n;
        return n3;
    }

    @Override
    public int computeIntIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        int n2 = toIntFunction.applyAsInt(k);
        this.insert(-n - 1, k, n2);
        return n2;
    }

    @Override
    public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            return this.defRetValue;
        }
        Integer n2 = biFunction.apply(k, this.value[n]);
        if (n2 == null) {
            if (k == null) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = n2;
        return this.value[n];
    }

    @Override
    public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        Integer n2 = biFunction.apply(k, n >= 0 ? Integer.valueOf(this.value[n]) : null);
        if (n2 == null) {
            if (n >= 0) {
                if (k == null) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        int n3 = n2;
        if (n < 0) {
            this.insert(-n - 1, k, n3);
            return n3;
        }
        this.value[n] = n3;
        return this.value[n];
    }

    @Override
    public int mergeInt(K k, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(k);
        if (n2 < 0) {
            this.insert(-n2 - 1, k, n);
            return n;
        }
        Integer n3 = biFunction.apply((Integer)this.value[n2], (Integer)n);
        if (n3 == null) {
            if (k == null) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = n3;
        return this.value[n2];
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
    public Object2IntSortedMap<K> tailMap(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object2IntSortedMap<K> headMap(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object2IntSortedMap<K> subMap(K k, K k2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparator<? super K> comparator() {
        return null;
    }

    @Override
    public Object2IntSortedMap.FastSortedEntrySet<K> object2IntEntrySet() {
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
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(this){
                final Object2IntLinkedOpenHashMap this$0;
                {
                    this.this$0 = object2IntLinkedOpenHashMap;
                }

                @Override
                public IntIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(int n) {
                    return this.this$0.containsValue(n);
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
        int[] nArray = this.value;
        int n2 = n - 1;
        Object[] objectArray = new Object[n + 1];
        int[] nArray2 = new int[n + 1];
        int n3 = this.first;
        int n4 = -1;
        int n5 = -1;
        long[] lArray = this.link;
        long[] lArray2 = new long[n + 1];
        this.first = -1;
        int n6 = this.size;
        while (n6-- != 0) {
            int n7;
            if (KArray[n3] == null) {
                n7 = n;
            } else {
                n7 = HashCommon.mix(KArray[n3].hashCode()) & n2;
                while (objectArray[n7] != null) {
                    n7 = n7 + 1 & n2;
                }
            }
            objectArray[n7] = KArray[n3];
            nArray2[n7] = nArray[n3];
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
        this.value = nArray2;
    }

    public Object2IntLinkedOpenHashMap<K> clone() {
        Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap;
        try {
            object2IntLinkedOpenHashMap = (Object2IntLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2IntLinkedOpenHashMap.keys = null;
        object2IntLinkedOpenHashMap.values = null;
        object2IntLinkedOpenHashMap.entries = null;
        object2IntLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        object2IntLinkedOpenHashMap.key = (Object[])this.key.clone();
        object2IntLinkedOpenHashMap.value = (int[])this.value.clone();
        object2IntLinkedOpenHashMap.link = (long[])this.link.clone();
        return object2IntLinkedOpenHashMap;
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
                n4 = this.key[n3].hashCode();
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
        int[] nArray = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeObject(KArray[n2]);
            objectOutputStream.writeInt(nArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] objectArray = this.key;
        this.value = new int[this.n + 1];
        int[] nArray = this.value;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            Object object = objectInputStream.readObject();
            int n4 = objectInputStream.readInt();
            if (object == null) {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = HashCommon.mix(object.hashCode()) & this.mask;
                while (objectArray[n3] != null) {
                    n3 = n3 + 1 & this.mask;
                }
            }
            objectArray[n3] = object;
            nArray[n3] = n4;
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
    public ObjectSortedSet object2IntEntrySet() {
        return this.object2IntEntrySet();
    }

    @Override
    public ObjectSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet object2IntEntrySet() {
        return this.object2IntEntrySet();
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

    static int access$100(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
        return object2IntLinkedOpenHashMap.removeNullEntry();
    }

    static int access$200(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, int n) {
        return object2IntLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements IntListIterator {
        final Object2IntLinkedOpenHashMap this$0;

        @Override
        public int previousInt() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
            this.this$0 = object2IntLinkedOpenHashMap;
            super(object2IntLinkedOpenHashMap);
        }

        @Override
        public int nextInt() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractObjectSortedSet<K> {
        final Object2IntLinkedOpenHashMap this$0;

        private KeySet(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
            this.this$0 = object2IntLinkedOpenHashMap;
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
            this.this$0.removeInt(object);
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

        KeySet(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, 1 var2_2) {
            this(object2IntLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ObjectListIterator<K> {
        final Object2IntLinkedOpenHashMap this$0;

        public KeyIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, K k) {
            this.this$0 = object2IntLinkedOpenHashMap;
            super(object2IntLinkedOpenHashMap, k, null);
        }

        @Override
        public K previous() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
            this.this$0 = object2IntLinkedOpenHashMap;
            super(object2IntLinkedOpenHashMap);
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
    extends AbstractObjectSortedSet<Object2IntMap.Entry<K>>
    implements Object2IntSortedMap.FastSortedEntrySet<K> {
        final Object2IntLinkedOpenHashMap this$0;

        private MapEntrySet(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
            this.this$0 = object2IntLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Object2IntMap.Entry<K>> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> entry, Object2IntMap.Entry<K> entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object2IntMap.Entry<K> first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Object2IntMap.Entry<K> last() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            Object k = entry.getKey();
            int n = (Integer)entry.getValue();
            if (k == null) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n;
            }
            K[] KArray = this.this$0.key;
            int n2 = HashCommon.mix(k.hashCode()) & this.this$0.mask;
            Object k2 = KArray[n2];
            if (k2 == null) {
                return true;
            }
            if (k.equals(k2)) {
                return this.this$0.value[n2] == n;
            }
            do {
                if ((k2 = KArray[n2 = n2 + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!k.equals(k2));
            return this.this$0.value[n2] == n;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            Object k = entry.getKey();
            int n = (Integer)entry.getValue();
            if (k == null) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n) {
                    Object2IntLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            K[] KArray = this.this$0.key;
            int n2 = HashCommon.mix(k.hashCode()) & this.this$0.mask;
            Object k2 = KArray[n2];
            if (k2 == null) {
                return true;
            }
            if (k2.equals(k)) {
                if (this.this$0.value[n2] == n) {
                    Object2IntLinkedOpenHashMap.access$200(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((k2 = KArray[n2 = n2 + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!k2.equals(k) || this.this$0.value[n2] != n);
            Object2IntLinkedOpenHashMap.access$200(this.this$0, n2);
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
        public ObjectListIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> entry) {
            return new EntryIterator(this.this$0, entry.getKey());
        }

        @Override
        public ObjectListIterator<Object2IntMap.Entry<K>> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        @Override
        public ObjectListIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap.Entry<K> entry) {
            return new FastEntryIterator(this.this$0, entry.getKey());
        }

        @Override
        public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractObject2IntMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
            AbstractObject2IntMap.BasicEntry basicEntry = new AbstractObject2IntMap.BasicEntry();
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
            return this.tailSet((Object2IntMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Object2IntMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Object2IntMap.Entry)object, (Object2IntMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Object2IntMap.Entry)object);
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
            return this.tailSet((Object2IntMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Object2IntMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Object2IntMap.Entry)object, (Object2IntMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator fastIterator(Object2IntMap.Entry entry) {
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

        MapEntrySet(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, 1 var2_2) {
            this(object2IntLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Object2IntMap.Entry<K>> {
        final MapEntry entry;
        final Object2IntLinkedOpenHashMap this$0;

        public FastEntryIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
            this.this$0 = object2IntLinkedOpenHashMap;
            super(object2IntLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, K k) {
            this.this$0 = object2IntLinkedOpenHashMap;
            super(object2IntLinkedOpenHashMap, k, null);
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
            super.add((Object2IntMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Object2IntMap.Entry)object);
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
    implements ObjectListIterator<Object2IntMap.Entry<K>> {
        private MapEntry entry;
        final Object2IntLinkedOpenHashMap this$0;

        public EntryIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
            this.this$0 = object2IntLinkedOpenHashMap;
            super(object2IntLinkedOpenHashMap);
        }

        public EntryIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, K k) {
            this.this$0 = object2IntLinkedOpenHashMap;
            super(object2IntLinkedOpenHashMap, k, null);
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
            super.add((Object2IntMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Object2IntMap.Entry)object);
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
        final Object2IntLinkedOpenHashMap this$0;

        protected MapIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
            this.this$0 = object2IntLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = object2IntLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, K k) {
            this.this$0 = object2IntLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (k == null) {
                if (object2IntLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)object2IntLinkedOpenHashMap.link[object2IntLinkedOpenHashMap.n];
                    this.prev = object2IntLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + k + " does not belong to this map.");
            }
            if (Objects.equals(object2IntLinkedOpenHashMap.key[object2IntLinkedOpenHashMap.last], k)) {
                this.prev = object2IntLinkedOpenHashMap.last;
                this.index = object2IntLinkedOpenHashMap.size;
                return;
            }
            int n = HashCommon.mix(k.hashCode()) & object2IntLinkedOpenHashMap.mask;
            while (object2IntLinkedOpenHashMap.key[n] != null) {
                if (object2IntLinkedOpenHashMap.key[n].equals(k)) {
                    this.next = (int)object2IntLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & object2IntLinkedOpenHashMap.mask;
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
                        int n3 = HashCommon.mix(k.hashCode()) & this.this$0.mask;
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

        public void set(Object2IntMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Object2IntMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, Object object, 1 var3_3) {
            this(object2IntLinkedOpenHashMap, object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Object2IntMap.Entry<K>,
    Map.Entry<K, Integer> {
        int index;
        final Object2IntLinkedOpenHashMap this$0;

        MapEntry(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, int n) {
            this.this$0 = object2IntLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
            this.this$0 = object2IntLinkedOpenHashMap;
        }

        @Override
        public K getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public int getIntValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public int setValue(int n) {
            int n2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = n;
            return n2;
        }

        @Override
        @Deprecated
        public Integer getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Integer setValue(Integer n) {
            return this.setValue((int)n);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Objects.equals(this.this$0.key[this.index], entry.getKey()) && this.this$0.value[this.index] == (Integer)entry.getValue();
        }

        @Override
        public int hashCode() {
            return (this.this$0.key[this.index] == null ? 0 : this.this$0.key[this.index].hashCode()) ^ this.this$0.value[this.index];
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Integer)object);
        }

        @Override
        @Deprecated
        public Object getValue() {
            return this.getValue();
        }
    }
}

