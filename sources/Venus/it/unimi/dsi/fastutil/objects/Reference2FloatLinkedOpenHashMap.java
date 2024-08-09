/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatMap;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatSortedMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
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
import java.util.function.DoubleConsumer;
import java.util.function.ToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Reference2FloatLinkedOpenHashMap<K>
extends AbstractReference2FloatSortedMap<K>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
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
    protected transient Reference2FloatSortedMap.FastSortedEntrySet<K> entries;
    protected transient ReferenceSortedSet<K> keys;
    protected transient FloatCollection values;

    public Reference2FloatLinkedOpenHashMap(int n, float f) {
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
        this.value = new float[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Reference2FloatLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Reference2FloatLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Reference2FloatLinkedOpenHashMap(Map<? extends K, ? extends Float> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Reference2FloatLinkedOpenHashMap(Map<? extends K, ? extends Float> map) {
        this(map, 0.75f);
    }

    public Reference2FloatLinkedOpenHashMap(Reference2FloatMap<K> reference2FloatMap, float f) {
        this(reference2FloatMap.size(), f);
        this.putAll(reference2FloatMap);
    }

    public Reference2FloatLinkedOpenHashMap(Reference2FloatMap<K> reference2FloatMap) {
        this(reference2FloatMap, 0.75f);
    }

    public Reference2FloatLinkedOpenHashMap(K[] KArray, float[] fArray, float f) {
        this(KArray.length, f);
        if (KArray.length != fArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + fArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], fArray[i]);
        }
    }

    public Reference2FloatLinkedOpenHashMap(K[] KArray, float[] fArray) {
        this(KArray, fArray, 0.75f);
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
        this.key[this.n] = null;
        float f = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return f;
    }

    @Override
    public void putAll(Map<? extends K, ? extends Float> map) {
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
        int n = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return -(n + 1);
        }
        if (k == k2) {
            return n;
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return -(n + 1);
        } while (k != k2);
        return n;
    }

    private void insert(int n, K k, float f) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = k;
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
    public float put(K k, float f) {
        int n = this.find(k);
        if (n < 0) {
            this.insert(-n - 1, k, f);
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

    public float addTo(K k, float f) {
        int n;
        if (k == null) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, f);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            K[] KArray = this.key;
            n = HashCommon.mix(System.identityHashCode(k)) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (k2 == k) {
                    return this.addToValue(n, f);
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (k2 != k) continue;
                    return this.addToValue(n, f);
                }
            }
        }
        this.key[n] = k;
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
                int n3 = HashCommon.mix(System.identityHashCode(k)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            KArray[n2] = k;
            this.value[n2] = this.value[n];
            this.fixPointers(n, n2);
        }
    }

    @Override
    public float removeFloat(Object object) {
        if (object == null) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object == k) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (object != k);
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
            this.key[this.n] = null;
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
            this.key[this.n] = null;
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

    public float getAndMoveToFirst(K k) {
        if (k == null) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (k == k2) {
            this.moveIndexToFirst(n);
            return this.value[n];
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (k != k2);
        this.moveIndexToFirst(n);
        return this.value[n];
    }

    public float getAndMoveToLast(K k) {
        if (k == null) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(k)) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return this.defRetValue;
        }
        if (k == k2) {
            this.moveIndexToLast(n);
            return this.value[n];
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (k != k2);
        this.moveIndexToLast(n);
        return this.value[n];
    }

    public float putAndMoveToFirst(K k, float f) {
        int n;
        if (k == null) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, f);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            K[] KArray = this.key;
            n = HashCommon.mix(System.identityHashCode(k)) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (k2 == k) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, f);
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (k2 != k) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, f);
                }
            }
        }
        this.key[n] = k;
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

    public float putAndMoveToLast(K k, float f) {
        int n;
        if (k == null) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, f);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            K[] KArray = this.key;
            n = HashCommon.mix(System.identityHashCode(k)) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (k2 == k) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, f);
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (k2 != k) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, f);
                }
            }
        }
        this.key[n] = k;
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
    public float getFloat(Object object) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object == k) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (object != k);
        return this.value[n];
    }

    @Override
    public boolean containsKey(Object object) {
        if (object == null) {
            return this.containsNullKey;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object == k) {
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (object != k);
        return false;
    }

    @Override
    public boolean containsValue(float f) {
        float[] fArray = this.value;
        K[] KArray = this.key;
        if (this.containsNullKey && Float.floatToIntBits(fArray[this.n]) == Float.floatToIntBits(f)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (KArray[n] == null || Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public float getOrDefault(Object object, float f) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : f;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return f;
        }
        if (object == k) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return f;
        } while (object != k);
        return this.value[n];
    }

    @Override
    public float putIfAbsent(K k, float f) {
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, k, f);
        return this.defRetValue;
    }

    @Override
    public boolean remove(Object object, float f) {
        if (object == null) {
            if (this.containsNullKey && Float.floatToIntBits(f) == Float.floatToIntBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(System.identityHashCode(object)) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object == k && Float.floatToIntBits(f) == Float.floatToIntBits(this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (object != k || Float.floatToIntBits(f) != Float.floatToIntBits(this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(K k, float f, float f2) {
        int n = this.find(k);
        if (n < 0 || Float.floatToIntBits(f) != Float.floatToIntBits(this.value[n])) {
            return true;
        }
        this.value[n] = f2;
        return false;
    }

    @Override
    public float replace(K k, float f) {
        int n = this.find(k);
        if (n < 0) {
            return this.defRetValue;
        }
        float f2 = this.value[n];
        this.value[n] = f;
        return f2;
    }

    @Override
    public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        float f = SafeMath.safeDoubleToFloat(toDoubleFunction.applyAsDouble(k));
        this.insert(-n - 1, k, f);
        return f;
    }

    @Override
    public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            return this.defRetValue;
        }
        Float f = biFunction.apply(k, Float.valueOf(this.value[n]));
        if (f == null) {
            if (k == null) {
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
    public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        Float f = biFunction.apply(k, n >= 0 ? Float.valueOf(this.value[n]) : null);
        if (f == null) {
            if (n >= 0) {
                if (k == null) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        float f2 = f.floatValue();
        if (n < 0) {
            this.insert(-n - 1, k, f2);
            return f2;
        }
        this.value[n] = f2;
        return this.value[n];
    }

    @Override
    public float mergeFloat(K k, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            this.insert(-n - 1, k, f);
            return f;
        }
        Float f2 = biFunction.apply(Float.valueOf(this.value[n]), Float.valueOf(f));
        if (f2 == null) {
            if (k == null) {
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
    public Reference2FloatSortedMap<K> tailMap(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reference2FloatSortedMap<K> headMap(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reference2FloatSortedMap<K> subMap(K k, K k2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparator<? super K> comparator() {
        return null;
    }

    @Override
    public Reference2FloatSortedMap.FastSortedEntrySet<K> reference2FloatEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ReferenceSortedSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public FloatCollection values() {
        if (this.values == null) {
            this.values = new AbstractFloatCollection(this){
                final Reference2FloatLinkedOpenHashMap this$0;
                {
                    this.this$0 = reference2FloatLinkedOpenHashMap;
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
                        if (this.this$0.key[n] == null) continue;
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
        K[] KArray = this.key;
        float[] fArray = this.value;
        int n2 = n - 1;
        Object[] objectArray = new Object[n + 1];
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
            if (KArray[n3] == null) {
                n7 = n;
            } else {
                n7 = HashCommon.mix(System.identityHashCode(KArray[n3])) & n2;
                while (objectArray[n7] != null) {
                    n7 = n7 + 1 & n2;
                }
            }
            objectArray[n7] = KArray[n3];
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
        this.key = objectArray;
        this.value = fArray2;
    }

    public Reference2FloatLinkedOpenHashMap<K> clone() {
        Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap;
        try {
            reference2FloatLinkedOpenHashMap = (Reference2FloatLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2FloatLinkedOpenHashMap.keys = null;
        reference2FloatLinkedOpenHashMap.values = null;
        reference2FloatLinkedOpenHashMap.entries = null;
        reference2FloatLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        reference2FloatLinkedOpenHashMap.key = (Object[])this.key.clone();
        reference2FloatLinkedOpenHashMap.value = (float[])this.value.clone();
        reference2FloatLinkedOpenHashMap.link = (long[])this.link.clone();
        return reference2FloatLinkedOpenHashMap;
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
                n4 = System.identityHashCode(this.key[n3]);
            }
            n += (n4 ^= HashCommon.float2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.float2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        K[] KArray = this.key;
        float[] fArray = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeObject(KArray[n2]);
            objectOutputStream.writeFloat(fArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] objectArray = this.key;
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
            Object object = objectInputStream.readObject();
            float f = objectInputStream.readFloat();
            if (object == null) {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = HashCommon.mix(System.identityHashCode(object)) & this.mask;
                while (objectArray[n3] != null) {
                    n3 = n3 + 1 & this.mask;
                }
            }
            objectArray[n3] = object;
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
    public ObjectSortedSet reference2FloatEntrySet() {
        return this.reference2FloatEntrySet();
    }

    @Override
    public ReferenceSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet reference2FloatEntrySet() {
        return this.reference2FloatEntrySet();
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

    static float access$100(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
        return reference2FloatLinkedOpenHashMap.removeNullEntry();
    }

    static float access$200(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, int n) {
        return reference2FloatLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements FloatListIterator {
        final Reference2FloatLinkedOpenHashMap this$0;

        @Override
        public float previousFloat() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            super(reference2FloatLinkedOpenHashMap);
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
    extends AbstractReferenceSortedSet<K> {
        final Reference2FloatLinkedOpenHashMap this$0;

        private KeySet(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
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
            this.this$0.removeFloat(object);
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
        public ReferenceSortedSet<K> tailSet(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceSortedSet<K> headSet(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceSortedSet<K> subSet(K k, K k2) {
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

        KeySet(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, 1 var2_2) {
            this(reference2FloatLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ObjectListIterator<K> {
        final Reference2FloatLinkedOpenHashMap this$0;

        public KeyIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, K k) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            super(reference2FloatLinkedOpenHashMap, k, null);
        }

        @Override
        public K previous() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            super(reference2FloatLinkedOpenHashMap);
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
    extends AbstractObjectSortedSet<Reference2FloatMap.Entry<K>>
    implements Reference2FloatSortedMap.FastSortedEntrySet<K> {
        final Reference2FloatLinkedOpenHashMap this$0;

        private MapEntrySet(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Reference2FloatMap.Entry<K>> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Reference2FloatMap.Entry<K>> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Reference2FloatMap.Entry<K>> subSet(Reference2FloatMap.Entry<K> entry, Reference2FloatMap.Entry<K> entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Reference2FloatMap.Entry<K>> headSet(Reference2FloatMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Reference2FloatMap.Entry<K>> tailSet(Reference2FloatMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Reference2FloatMap.Entry<K> first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Reference2FloatMap.Entry<K> last() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            Object k = entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            if (k == null) {
                return this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f);
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(System.identityHashCode(k)) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (k == k2) {
                return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f);
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (k != k2);
            return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            Object k = entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            if (k == null) {
                if (this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f)) {
                    Reference2FloatLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(System.identityHashCode(k)) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (k2 == k) {
                if (Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f)) {
                    Reference2FloatLinkedOpenHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (k2 != k || Float.floatToIntBits(this.this$0.value[n]) != Float.floatToIntBits(f));
            Reference2FloatLinkedOpenHashMap.access$200(this.this$0, n);
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
        public ObjectListIterator<Reference2FloatMap.Entry<K>> iterator(Reference2FloatMap.Entry<K> entry) {
            return new EntryIterator(this.this$0, entry.getKey());
        }

        @Override
        public ObjectListIterator<Reference2FloatMap.Entry<K>> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        @Override
        public ObjectListIterator<Reference2FloatMap.Entry<K>> fastIterator(Reference2FloatMap.Entry<K> entry) {
            return new FastEntryIterator(this.this$0, entry.getKey());
        }

        @Override
        public void forEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractReference2FloatMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
            AbstractReference2FloatMap.BasicEntry basicEntry = new AbstractReference2FloatMap.BasicEntry();
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
            return this.tailSet((Reference2FloatMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Reference2FloatMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Reference2FloatMap.Entry)object, (Reference2FloatMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Reference2FloatMap.Entry)object);
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
            return this.tailSet((Reference2FloatMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Reference2FloatMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Reference2FloatMap.Entry)object, (Reference2FloatMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator fastIterator(Reference2FloatMap.Entry entry) {
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

        MapEntrySet(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, 1 var2_2) {
            this(reference2FloatLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Reference2FloatMap.Entry<K>> {
        final MapEntry entry;
        final Reference2FloatLinkedOpenHashMap this$0;

        public FastEntryIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            super(reference2FloatLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, K k) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            super(reference2FloatLinkedOpenHashMap, k, null);
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
            super.add((Reference2FloatMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Reference2FloatMap.Entry)object);
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
    implements ObjectListIterator<Reference2FloatMap.Entry<K>> {
        private MapEntry entry;
        final Reference2FloatLinkedOpenHashMap this$0;

        public EntryIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            super(reference2FloatLinkedOpenHashMap);
        }

        public EntryIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, K k) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            super(reference2FloatLinkedOpenHashMap, k, null);
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
            super.add((Reference2FloatMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Reference2FloatMap.Entry)object);
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
        final Reference2FloatLinkedOpenHashMap this$0;

        protected MapIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = reference2FloatLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, K k) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (k == null) {
                if (reference2FloatLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)reference2FloatLinkedOpenHashMap.link[reference2FloatLinkedOpenHashMap.n];
                    this.prev = reference2FloatLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + k + " does not belong to this map.");
            }
            if (reference2FloatLinkedOpenHashMap.key[reference2FloatLinkedOpenHashMap.last] == k) {
                this.prev = reference2FloatLinkedOpenHashMap.last;
                this.index = reference2FloatLinkedOpenHashMap.size;
                return;
            }
            int n = HashCommon.mix(System.identityHashCode(k)) & reference2FloatLinkedOpenHashMap.mask;
            while (reference2FloatLinkedOpenHashMap.key[n] != null) {
                if (reference2FloatLinkedOpenHashMap.key[n] == k) {
                    this.next = (int)reference2FloatLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & reference2FloatLinkedOpenHashMap.mask;
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
                        int n3 = HashCommon.mix(System.identityHashCode(k)) & this.this$0.mask;
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

        public void set(Reference2FloatMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Reference2FloatMap.Entry<K> entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, Object object, 1 var3_3) {
            this(reference2FloatLinkedOpenHashMap, object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Reference2FloatMap.Entry<K>,
    Map.Entry<K, Float> {
        int index;
        final Reference2FloatLinkedOpenHashMap this$0;

        MapEntry(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap, int n) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Reference2FloatLinkedOpenHashMap reference2FloatLinkedOpenHashMap) {
            this.this$0 = reference2FloatLinkedOpenHashMap;
        }

        @Override
        public K getKey() {
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
            return this.this$0.key[this.index] == entry.getKey() && Float.floatToIntBits(this.this$0.value[this.index]) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.this$0.key[this.index]) ^ HashCommon.float2int(this.this$0.value[this.index]);
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
    }
}

