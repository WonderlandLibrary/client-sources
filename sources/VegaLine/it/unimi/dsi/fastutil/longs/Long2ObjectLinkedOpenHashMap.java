/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLong2ObjectSortedMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectSortedMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Long2ObjectLinkedOpenHashMap<V>
extends AbstractLong2ObjectSortedMap<V>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Long2ObjectSortedMap.FastSortedEntrySet<V> entries;
    protected transient LongSortedSet keys;
    protected transient ObjectCollection<V> values;

    public Long2ObjectLinkedOpenHashMap(int expected, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.n = HashCommon.arraySize(expected, f);
        this.mask = this.n - 1;
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = new long[this.n + 1];
        this.value = new Object[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Long2ObjectLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Long2ObjectLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Long2ObjectLinkedOpenHashMap(Map<? extends Long, ? extends V> m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Long2ObjectLinkedOpenHashMap(Map<? extends Long, ? extends V> m) {
        this(m, 0.75f);
    }

    public Long2ObjectLinkedOpenHashMap(Long2ObjectMap<V> m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Long2ObjectLinkedOpenHashMap(Long2ObjectMap<V> m) {
        this(m, 0.75f);
    }

    public Long2ObjectLinkedOpenHashMap(long[] k, V[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Long2ObjectLinkedOpenHashMap(long[] k, V[] v) {
        this(k, v, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f);
        if (needed > this.n) {
            this.rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int)Math.min(0x40000000L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)capacity / this.f))));
        if (needed > this.n) {
            this.rehash(needed);
        }
    }

    private V removeEntry(int pos) {
        V oldValue = this.value[pos];
        this.value[pos] = null;
        --this.size;
        this.fixPointers(pos);
        this.shiftKeys(pos);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    private V removeNullEntry() {
        this.containsNullKey = false;
        V oldValue = this.value[this.n];
        this.value[this.n] = null;
        --this.size;
        this.fixPointers(this.n);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Long, ? extends V> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(long k, V v) {
        int pos;
        if (k == 0L) {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            long[] key = this.key;
            pos = (int)HashCommon.mix(k) & this.mask;
            long curr = key[pos];
            if (curr != 0L) {
                if (curr == k) {
                    return pos;
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
                    if (curr != k) continue;
                    return pos;
                }
            }
        }
        this.key[pos] = k;
        this.value[pos] = v;
        if (this.size == 0) {
            this.first = this.last = pos;
            this.link[pos] = -1L;
        } else {
            int n = this.last;
            this.link[n] = this.link[n] ^ (this.link[this.last] ^ (long)pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[pos] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = pos;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return -1;
    }

    @Override
    public V put(long k, V v) {
        int pos = this.insert(k, v);
        if (pos < 0) {
            return (V)this.defRetValue;
        }
        V oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override
    @Deprecated
    public V put(Long ok, V ov) {
        V v = ov;
        int pos = this.insert(ok, v);
        if (pos < 0) {
            return (V)this.defRetValue;
        }
        V oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    protected final void shiftKeys(int pos) {
        long[] key = this.key;
        while (true) {
            long curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if ((curr = key[pos]) == 0L) {
                    key[last] = 0L;
                    this.value[last] = null;
                    return;
                }
                int slot = (int)HashCommon.mix(curr) & this.mask;
                if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                pos = pos + 1 & this.mask;
            }
            key[last] = curr;
            this.value[last] = this.value[pos];
            this.fixPointers(pos, last);
        }
    }

    @Override
    public V remove(long k) {
        if (k == 0L) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public V remove(Object ok) {
        long k = (Long)ok;
        if (k == 0L) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return (V)this.defRetValue;
        }
        if (curr == k) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return (V)this.defRetValue;
        } while (curr != k);
        return this.removeEntry(pos);
    }

    private V setValue(int pos, V v) {
        V oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    public V removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = this.first;
        this.first = (int)this.link[pos];
        if (0 <= this.first) {
            int n = this.first;
            this.link[n] = this.link[n] | 0xFFFFFFFF00000000L;
        }
        --this.size;
        V v = this.value[pos];
        if (pos == this.n) {
            this.containsNullKey = false;
            this.value[this.n] = null;
        } else {
            this.shiftKeys(pos);
        }
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    public V removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = this.last;
        this.last = (int)(this.link[pos] >>> 32);
        if (0 <= this.last) {
            int n = this.last;
            this.link[n] = this.link[n] | 0xFFFFFFFFL;
        }
        --this.size;
        V v = this.value[pos];
        if (pos == this.n) {
            this.containsNullKey = false;
            this.value[this.n] = null;
        } else {
            this.shiftKeys(pos);
        }
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    private void moveIndexToFirst(int i) {
        if (this.size == 1 || this.first == i) {
            return;
        }
        if (this.last == i) {
            int n = this.last = (int)(this.link[i] >>> 32);
            this.link[n] = this.link[n] | 0xFFFFFFFFL;
        } else {
            long linki = this.link[i];
            int prev = (int)(linki >>> 32);
            int next = (int)linki;
            int n = prev;
            this.link[n] = this.link[n] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            int n2 = next;
            this.link[n2] = this.link[n2] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
        }
        int n = this.first;
        this.link[n] = this.link[n] ^ (this.link[this.first] ^ ((long)i & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
        this.link[i] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
        this.first = i;
    }

    private void moveIndexToLast(int i) {
        if (this.size == 1 || this.last == i) {
            return;
        }
        if (this.first == i) {
            int n = this.first = (int)this.link[i];
            this.link[n] = this.link[n] | 0xFFFFFFFF00000000L;
        } else {
            long linki = this.link[i];
            int prev = (int)(linki >>> 32);
            int next = (int)linki;
            int n = prev;
            this.link[n] = this.link[n] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            int n2 = next;
            this.link[n2] = this.link[n2] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
        }
        int n = this.last;
        this.link[n] = this.link[n] ^ (this.link[this.last] ^ (long)i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        this.link[i] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
        this.last = i;
    }

    public V getAndMoveToFirst(long k) {
        if (k == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return (V)this.defRetValue;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        this.moveIndexToFirst(pos);
        return this.value[pos];
    }

    public V getAndMoveToLast(long k) {
        if (k == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return (V)this.defRetValue;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            this.moveIndexToLast(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        this.moveIndexToLast(pos);
        return this.value[pos];
    }

    public V putAndMoveToFirst(long k, V v) {
        int pos;
        if (k == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            long[] key = this.key;
            pos = (int)HashCommon.mix(k) & this.mask;
            long curr = key[pos];
            if (curr != 0L) {
                if (curr == k) {
                    this.moveIndexToFirst(pos);
                    return this.setValue(pos, v);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
                    if (curr != k) continue;
                    this.moveIndexToFirst(pos);
                    return this.setValue(pos, v);
                }
            }
        }
        this.key[pos] = k;
        this.value[pos] = v;
        if (this.size == 0) {
            this.first = this.last = pos;
            this.link[pos] = -1L;
        } else {
            int n = this.first;
            this.link[n] = this.link[n] ^ (this.link[this.first] ^ ((long)pos & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[pos] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
            this.first = pos;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return (V)this.defRetValue;
    }

    public V putAndMoveToLast(long k, V v) {
        int pos;
        if (k == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            long[] key = this.key;
            pos = (int)HashCommon.mix(k) & this.mask;
            long curr = key[pos];
            if (curr != 0L) {
                if (curr == k) {
                    this.moveIndexToLast(pos);
                    return this.setValue(pos, v);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
                    if (curr != k) continue;
                    this.moveIndexToLast(pos);
                    return this.setValue(pos, v);
                }
            }
        }
        this.key[pos] = k;
        this.value[pos] = v;
        if (this.size == 0) {
            this.first = this.last = pos;
            this.link[pos] = -1L;
        } else {
            int n = this.last;
            this.link[n] = this.link[n] ^ (this.link[this.last] ^ (long)pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[pos] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = pos;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return (V)this.defRetValue;
    }

    @Deprecated
    public V get(Long ok) {
        if (ok == null) {
            return null;
        }
        long k = ok;
        if (k == 0L) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        return this.value[pos];
    }

    @Override
    public V get(long k) {
        if (k == 0L) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        return this.value[pos];
    }

    @Override
    public boolean containsKey(long k) {
        if (k == 0L) {
            return this.containsNullKey;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return false;
        }
        if (k == curr) {
            return true;
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return false;
        } while (k != curr);
        return true;
    }

    @Override
    public boolean containsValue(Object v) {
        V[] value = this.value;
        long[] key = this.key;
        if (this.containsNullKey && (value[this.n] == null ? v == null : value[this.n].equals(v))) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (key[i] == 0L || !(value[i] == null ? v == null : value[i].equals(v))) continue;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0L);
        Arrays.fill(this.value, null);
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

    @Deprecated
    public void growthFactor(int growthFactor) {
    }

    @Deprecated
    public int growthFactor() {
        return 16;
    }

    protected void fixPointers(int i) {
        if (this.size == 0) {
            this.last = -1;
            this.first = -1;
            return;
        }
        if (this.first == i) {
            this.first = (int)this.link[i];
            if (0 <= this.first) {
                int n = this.first;
                this.link[n] = this.link[n] | 0xFFFFFFFF00000000L;
            }
            return;
        }
        if (this.last == i) {
            this.last = (int)(this.link[i] >>> 32);
            if (0 <= this.last) {
                int n = this.last;
                this.link[n] = this.link[n] | 0xFFFFFFFFL;
            }
            return;
        }
        long linki = this.link[i];
        int prev = (int)(linki >>> 32);
        int next = (int)linki;
        int n = prev;
        this.link[n] = this.link[n] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        int n2 = next;
        this.link[n2] = this.link[n2] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
    }

    protected void fixPointers(int s, int d) {
        if (this.size == 1) {
            this.first = this.last = d;
            this.link[d] = -1L;
            return;
        }
        if (this.first == s) {
            this.first = d;
            int n = (int)this.link[s];
            this.link[n] = this.link[n] ^ (this.link[(int)this.link[s]] ^ ((long)d & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[d] = this.link[s];
            return;
        }
        if (this.last == s) {
            this.last = d;
            int n = (int)(this.link[s] >>> 32);
            this.link[n] = this.link[n] ^ (this.link[(int)(this.link[s] >>> 32)] ^ (long)d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[d] = this.link[s];
            return;
        }
        long links = this.link[s];
        int prev = (int)(links >>> 32);
        int next = (int)links;
        int n = prev;
        this.link[n] = this.link[n] ^ (this.link[prev] ^ (long)d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        int n2 = next;
        this.link[n2] = this.link[n2] ^ (this.link[next] ^ ((long)d & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
        this.link[d] = links;
    }

    @Override
    public long firstLongKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public long lastLongKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public LongComparator comparator() {
        return null;
    }

    @Override
    public Long2ObjectSortedMap<V> tailMap(long from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long2ObjectSortedMap<V> headMap(long to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long2ObjectSortedMap<V> subMap(long from, long to) {
        throw new UnsupportedOperationException();
    }

    public Long2ObjectSortedMap.FastSortedEntrySet<V> long2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public LongSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>(){

                @Override
                public ObjectIterator<V> iterator() {
                    return new ValueIterator();
                }

                @Override
                public int size() {
                    return Long2ObjectLinkedOpenHashMap.this.size;
                }

                @Override
                public boolean contains(Object v) {
                    return Long2ObjectLinkedOpenHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Long2ObjectLinkedOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    @Deprecated
    public boolean rehash() {
        return true;
    }

    public boolean trim() {
        int l = HashCommon.arraySize(this.size, this.f);
        if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) {
            return true;
        }
        try {
            this.rehash(l);
        } catch (OutOfMemoryError cantDoIt) {
            return false;
        }
        return true;
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.f));
        if (l >= n || this.size > HashCommon.maxFill(l, this.f)) {
            return true;
        }
        try {
            this.rehash(l);
        } catch (OutOfMemoryError cantDoIt) {
            return false;
        }
        return true;
    }

    protected void rehash(int newN) {
        long[] key = this.key;
        V[] value = this.value;
        int mask = newN - 1;
        long[] newKey = new long[newN + 1];
        Object[] newValue = new Object[newN + 1];
        int i = this.first;
        int prev = -1;
        int newPrev = -1;
        long[] link = this.link;
        long[] newLink = new long[newN + 1];
        this.first = -1;
        int j = this.size;
        while (j-- != 0) {
            int pos;
            if (key[i] == 0L) {
                pos = newN;
            } else {
                pos = (int)HashCommon.mix(key[i]) & mask;
                while (newKey[pos] != 0L) {
                    pos = pos + 1 & mask;
                }
            }
            newKey[pos] = key[i];
            newValue[pos] = value[i];
            if (prev != -1) {
                int n = newPrev;
                newLink[n] = newLink[n] ^ (newLink[newPrev] ^ (long)pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n2 = pos;
                newLink[n2] = newLink[n2] ^ (newLink[pos] ^ ((long)newPrev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                newPrev = pos;
            } else {
                newPrev = this.first = pos;
                newLink[pos] = -1L;
            }
            int t = i;
            i = (int)link[i];
            prev = t;
        }
        this.link = newLink;
        this.last = newPrev;
        if (newPrev != -1) {
            int n = newPrev;
            newLink[n] = newLink[n] | 0xFFFFFFFFL;
        }
        this.n = newN;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = newKey;
        this.value = newValue;
    }

    public Long2ObjectLinkedOpenHashMap<V> clone() {
        Long2ObjectLinkedOpenHashMap c;
        try {
            c = (Long2ObjectLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (long[])this.key.clone();
        c.value = (Object[])this.value.clone();
        c.link = (long[])this.link.clone();
        return c;
    }

    @Override
    public int hashCode() {
        int h = 0;
        int j = this.realSize();
        int i = 0;
        int t = 0;
        while (j-- != 0) {
            while (this.key[i] == 0L) {
                ++i;
            }
            t = HashCommon.long2int(this.key[i]);
            if (this != this.value[i]) {
                t ^= this.value[i] == null ? 0 : this.value[i].hashCode();
            }
            h += t;
            ++i;
        }
        if (this.containsNullKey) {
            h += this.value[this.n] == null ? 0 : this.value[this.n].hashCode();
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        long[] key = this.key;
        V[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeLong(key[e]);
            s.writeObject(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new long[this.n + 1];
        long[] key = this.key;
        this.value = new Object[this.n + 1];
        Object[] value = this.value;
        this.link = new long[this.n + 1];
        long[] link = this.link;
        int prev = -1;
        this.last = -1;
        this.first = -1;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            long k = s.readLong();
            Object v = s.readObject();
            if (k == 0L) {
                pos = this.n;
                this.containsNullKey = true;
            } else {
                pos = (int)HashCommon.mix(k) & this.mask;
                while (key[pos] != 0L) {
                    pos = pos + 1 & this.mask;
                }
            }
            key[pos] = k;
            value[pos] = v;
            if (this.first != -1) {
                int n = prev;
                link[n] = link[n] ^ (link[prev] ^ (long)pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n2 = pos;
                link[n2] = link[n2] ^ (link[pos] ^ ((long)prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                prev = pos;
                continue;
            }
            prev = this.first = pos;
            int n = pos;
            link[n] = link[n] | 0xFFFFFFFF00000000L;
        }
        this.last = prev;
        if (prev != -1) {
            int n = prev;
            link[n] = link[n] | 0xFFFFFFFFL;
        }
    }

    private void checkTable() {
    }

    private final class ValueIterator
    extends MapIterator
    implements ObjectListIterator<V> {
        @Override
        public V previous() {
            return Long2ObjectLinkedOpenHashMap.this.value[this.previousEntry()];
        }

        @Override
        public void set(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V next() {
            return Long2ObjectLinkedOpenHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractLongSortedSet {
        private KeySet() {
        }

        @Override
        public LongListIterator iterator(long from) {
            return new KeyIterator(from);
        }

        @Override
        public LongListIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Long2ObjectLinkedOpenHashMap.this.size;
        }

        @Override
        public boolean contains(long k) {
            return Long2ObjectLinkedOpenHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(long k) {
            int oldSize = Long2ObjectLinkedOpenHashMap.this.size;
            Long2ObjectLinkedOpenHashMap.this.remove(k);
            return Long2ObjectLinkedOpenHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Long2ObjectLinkedOpenHashMap.this.clear();
        }

        @Override
        public long firstLong() {
            if (Long2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.first];
        }

        @Override
        public long lastLong() {
            if (Long2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.last];
        }

        @Override
        public LongComparator comparator() {
            return null;
        }

        @Override
        public final LongSortedSet tailSet(long from) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final LongSortedSet headSet(long to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final LongSortedSet subSet(long from, long to) {
            throw new UnsupportedOperationException();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongListIterator {
        public KeyIterator(long k) {
            super(k);
        }

        @Override
        public long previousLong() {
            return Long2ObjectLinkedOpenHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long previous() {
            return Long2ObjectLinkedOpenHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(Long ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Long ok) {
            throw new UnsupportedOperationException();
        }

        public KeyIterator() {
        }

        @Override
        public long nextLong() {
            return Long2ObjectLinkedOpenHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Long next() {
            return Long2ObjectLinkedOpenHashMap.this.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSortedSet<Long2ObjectMap.Entry<V>>
    implements Long2ObjectSortedMap.FastSortedEntrySet<V> {
        private MapEntrySet() {
        }

        @Override
        public ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public Comparator<? super Long2ObjectMap.Entry<V>> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Long2ObjectMap.Entry<V>> subSet(Long2ObjectMap.Entry<V> fromElement, Long2ObjectMap.Entry<V> toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Long2ObjectMap.Entry<V>> headSet(Long2ObjectMap.Entry<V> toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Long2ObjectMap.Entry<V>> tailSet(Long2ObjectMap.Entry<V> fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long2ObjectMap.Entry<V> first() {
            if (Long2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Long2ObjectLinkedOpenHashMap.this.first);
        }

        @Override
        public Long2ObjectMap.Entry<V> last() {
            if (Long2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Long2ObjectLinkedOpenHashMap.this.last);
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Long)) {
                return false;
            }
            long k = (Long)e.getKey();
            Object v = e.getValue();
            if (k == 0L) {
                return Long2ObjectLinkedOpenHashMap.this.containsNullKey && (Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n] == null ? v == null : Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n].equals(v));
            }
            long[] key = Long2ObjectLinkedOpenHashMap.this.key;
            int pos = (int)HashCommon.mix(k) & Long2ObjectLinkedOpenHashMap.this.mask;
            long curr = key[pos];
            if (curr == 0L) {
                return false;
            }
            if (k == curr) {
                return Long2ObjectLinkedOpenHashMap.this.value[pos] == null ? v == null : Long2ObjectLinkedOpenHashMap.this.value[pos].equals(v);
            }
            do {
                if ((curr = key[pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask]) != 0L) continue;
                return false;
            } while (k != curr);
            return Long2ObjectLinkedOpenHashMap.this.value[pos] == null ? v == null : Long2ObjectLinkedOpenHashMap.this.value[pos].equals(v);
        }

        @Override
        public boolean rem(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Long)) {
                return false;
            }
            long k = (Long)e.getKey();
            Object v = e.getValue();
            if (k == 0L) {
                if (Long2ObjectLinkedOpenHashMap.this.containsNullKey && (Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n] == null ? v == null : Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n].equals(v))) {
                    Long2ObjectLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            long[] key = Long2ObjectLinkedOpenHashMap.this.key;
            int pos = (int)HashCommon.mix(k) & Long2ObjectLinkedOpenHashMap.this.mask;
            long curr = key[pos];
            if (curr == 0L) {
                return false;
            }
            if (curr == k) {
                if (Long2ObjectLinkedOpenHashMap.this.value[pos] == null ? v == null : Long2ObjectLinkedOpenHashMap.this.value[pos].equals(v)) {
                    Long2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if ((curr = key[pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask]) != 0L) continue;
                return false;
            } while (curr != k || !(Long2ObjectLinkedOpenHashMap.this.value[pos] == null ? v == null : Long2ObjectLinkedOpenHashMap.this.value[pos].equals(v)));
            Long2ObjectLinkedOpenHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Long2ObjectLinkedOpenHashMap.this.size;
        }

        @Override
        public void clear() {
            Long2ObjectLinkedOpenHashMap.this.clear();
        }

        @Override
        public ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> iterator(Long2ObjectMap.Entry<V> from) {
            return new EntryIterator(from.getLongKey());
        }

        @Override
        public ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override
        public ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> fastIterator(Long2ObjectMap.Entry<V> from) {
            return new FastEntryIterator(from.getLongKey());
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Long2ObjectMap.Entry<V>> {
        final MapEntry entry;

        public FastEntryIterator() {
            this.entry = new MapEntry();
        }

        public FastEntryIterator(long from) {
            super(from);
            this.entry = new MapEntry();
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
        public void set(Long2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Long2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class EntryIterator
    extends MapIterator
    implements ObjectListIterator<Long2ObjectMap.Entry<V>> {
        private MapEntry entry;

        public EntryIterator() {
        }

        public EntryIterator(long from) {
            super(from);
        }

        @Override
        public MapEntry next() {
            this.entry = new MapEntry(this.nextEntry());
            return this.entry;
        }

        @Override
        public MapEntry previous() {
            this.entry = new MapEntry(this.previousEntry());
            return this.entry;
        }

        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }

        @Override
        public void set(Long2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Long2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class MapIterator {
        int prev = -1;
        int next = -1;
        int curr = -1;
        int index = -1;

        private MapIterator() {
            this.next = Long2ObjectLinkedOpenHashMap.this.first;
            this.index = 0;
        }

        private MapIterator(long from) {
            if (from == 0L) {
                if (Long2ObjectLinkedOpenHashMap.this.containsNullKey) {
                    this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[Long2ObjectLinkedOpenHashMap.this.n];
                    this.prev = Long2ObjectLinkedOpenHashMap.this.n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
            if (Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.last] == from) {
                this.prev = Long2ObjectLinkedOpenHashMap.this.last;
                this.index = Long2ObjectLinkedOpenHashMap.this.size;
                return;
            }
            int pos = (int)HashCommon.mix(from) & Long2ObjectLinkedOpenHashMap.this.mask;
            while (Long2ObjectLinkedOpenHashMap.this.key[pos] != 0L) {
                if (Long2ObjectLinkedOpenHashMap.this.key[pos] == from) {
                    this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[pos];
                    this.prev = pos;
                    return;
                }
                pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
            }
            throw new NoSuchElementException("The key " + from + " does not belong to this map.");
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
                this.index = Long2ObjectLinkedOpenHashMap.this.size;
                return;
            }
            int pos = Long2ObjectLinkedOpenHashMap.this.first;
            this.index = 1;
            while (pos != this.prev) {
                pos = (int)Long2ObjectLinkedOpenHashMap.this.link[pos];
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
            this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[this.curr];
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
            this.prev = (int)(Long2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
                this.prev = (int)(Long2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
                this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[this.curr];
            }
            --Long2ObjectLinkedOpenHashMap.this.size;
            if (this.prev == -1) {
                Long2ObjectLinkedOpenHashMap.this.first = this.next;
            } else {
                int n = this.prev;
                Long2ObjectLinkedOpenHashMap.this.link[n] = Long2ObjectLinkedOpenHashMap.this.link[n] ^ (Long2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            if (this.next == -1) {
                Long2ObjectLinkedOpenHashMap.this.last = this.prev;
            } else {
                int n = this.next;
                Long2ObjectLinkedOpenHashMap.this.link[n] = Long2ObjectLinkedOpenHashMap.this.link[n] ^ (Long2ObjectLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos != Long2ObjectLinkedOpenHashMap.this.n) {
                long[] key = Long2ObjectLinkedOpenHashMap.this.key;
                while (true) {
                    long curr;
                    int last = pos;
                    pos = last + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
                    while (true) {
                        if ((curr = key[pos]) == 0L) {
                            key[last] = 0L;
                            Long2ObjectLinkedOpenHashMap.this.value[last] = null;
                            return;
                        }
                        int slot = (int)HashCommon.mix(curr) & Long2ObjectLinkedOpenHashMap.this.mask;
                        if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                        pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
                    }
                    key[last] = curr;
                    Long2ObjectLinkedOpenHashMap.this.value[last] = Long2ObjectLinkedOpenHashMap.this.value[pos];
                    if (this.next == pos) {
                        this.next = last;
                    }
                    if (this.prev == pos) {
                        this.prev = last;
                    }
                    Long2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
                }
            }
            Long2ObjectLinkedOpenHashMap.this.containsNullKey = false;
            Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n] = null;
        }

        public int skip(int n) {
            int i = n;
            while (i-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - i - 1;
        }

        public int back(int n) {
            int i = n;
            while (i-- != 0 && this.hasPrevious()) {
                this.previousEntry();
            }
            return n - i - 1;
        }
    }

    final class MapEntry
    implements Long2ObjectMap.Entry<V>,
    Map.Entry<Long, V> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Long getKey() {
            return Long2ObjectLinkedOpenHashMap.this.key[this.index];
        }

        @Override
        public long getLongKey() {
            return Long2ObjectLinkedOpenHashMap.this.key[this.index];
        }

        @Override
        public V getValue() {
            return Long2ObjectLinkedOpenHashMap.this.value[this.index];
        }

        @Override
        public V setValue(V v) {
            Object oldValue = Long2ObjectLinkedOpenHashMap.this.value[this.index];
            Long2ObjectLinkedOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Long2ObjectLinkedOpenHashMap.this.key[this.index] == (Long)e.getKey() && (Long2ObjectLinkedOpenHashMap.this.value[this.index] == null ? e.getValue() == null : Long2ObjectLinkedOpenHashMap.this.value[this.index].equals(e.getValue()));
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(Long2ObjectLinkedOpenHashMap.this.key[this.index]) ^ (Long2ObjectLinkedOpenHashMap.this.value[this.index] == null ? 0 : Long2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Long2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Long2ObjectLinkedOpenHashMap.this.value[this.index];
        }
    }
}

