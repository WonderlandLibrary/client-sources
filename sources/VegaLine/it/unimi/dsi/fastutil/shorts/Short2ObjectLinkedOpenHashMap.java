/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ObjectSortedMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Short2ObjectLinkedOpenHashMap<V>
extends AbstractShort2ObjectSortedMap<V>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient short[] key;
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
    protected transient Short2ObjectSortedMap.FastSortedEntrySet<V> entries;
    protected transient ShortSortedSet keys;
    protected transient ObjectCollection<V> values;

    public Short2ObjectLinkedOpenHashMap(int expected, float f) {
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
        this.key = new short[this.n + 1];
        this.value = new Object[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Short2ObjectLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Short2ObjectLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Short2ObjectLinkedOpenHashMap(Map<? extends Short, ? extends V> m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Short2ObjectLinkedOpenHashMap(Map<? extends Short, ? extends V> m) {
        this(m, 0.75f);
    }

    public Short2ObjectLinkedOpenHashMap(Short2ObjectMap<V> m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Short2ObjectLinkedOpenHashMap(Short2ObjectMap<V> m) {
        this(m, 0.75f);
    }

    public Short2ObjectLinkedOpenHashMap(short[] k, V[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Short2ObjectLinkedOpenHashMap(short[] k, V[] v) {
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
    public void putAll(Map<? extends Short, ? extends V> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(short k, V v) {
        int pos;
        if (k == 0) {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            short[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            short curr = key[pos];
            if (curr != 0) {
                if (curr == k) {
                    return pos;
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
    public V put(short k, V v) {
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
    public V put(Short ok, V ov) {
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
        short[] key = this.key;
        while (true) {
            short curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if ((curr = key[pos]) == 0) {
                    key[last] = 0;
                    this.value[last] = null;
                    return;
                }
                int slot = HashCommon.mix(curr) & this.mask;
                if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                pos = pos + 1 & this.mask;
            }
            key[last] = curr;
            this.value[last] = this.value[pos];
            this.fixPointers(pos, last);
        }
    }

    @Override
    public V remove(short k) {
        if (k == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public V remove(Object ok) {
        short k = (Short)ok;
        if (k == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return (V)this.defRetValue;
        }
        if (curr == k) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
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

    public V getAndMoveToFirst(short k) {
        if (k == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return (V)this.defRetValue;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        this.moveIndexToFirst(pos);
        return this.value[pos];
    }

    public V getAndMoveToLast(short k) {
        if (k == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return (V)this.defRetValue;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            this.moveIndexToLast(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        this.moveIndexToLast(pos);
        return this.value[pos];
    }

    public V putAndMoveToFirst(short k, V v) {
        int pos;
        if (k == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            short[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            short curr = key[pos];
            if (curr != 0) {
                if (curr == k) {
                    this.moveIndexToFirst(pos);
                    return this.setValue(pos, v);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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

    public V putAndMoveToLast(short k, V v) {
        int pos;
        if (k == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            short[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            short curr = key[pos];
            if (curr != 0) {
                if (curr == k) {
                    this.moveIndexToLast(pos);
                    return this.setValue(pos, v);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
    public V get(Short ok) {
        if (ok == null) {
            return null;
        }
        short k = ok;
        if (k == 0) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        short[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        return this.value[pos];
    }

    @Override
    public V get(short k) {
        if (k == 0) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        short[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return (V)this.defRetValue;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (k != curr);
        return this.value[pos];
    }

    @Override
    public boolean containsKey(short k) {
        if (k == 0) {
            return this.containsNullKey;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return false;
        }
        if (k == curr) {
            return true;
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return false;
        } while (k != curr);
        return true;
    }

    @Override
    public boolean containsValue(Object v) {
        V[] value = this.value;
        short[] key = this.key;
        if (this.containsNullKey && (value[this.n] == null ? v == null : value[this.n].equals(v))) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (key[i] == 0 || !(value[i] == null ? v == null : value[i].equals(v))) continue;
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
        Arrays.fill(this.key, (short)0);
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
    public short firstShortKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public short lastShortKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public ShortComparator comparator() {
        return null;
    }

    @Override
    public Short2ObjectSortedMap<V> tailMap(short from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Short2ObjectSortedMap<V> headMap(short to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Short2ObjectSortedMap<V> subMap(short from, short to) {
        throw new UnsupportedOperationException();
    }

    public Short2ObjectSortedMap.FastSortedEntrySet<V> short2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public ShortSortedSet keySet() {
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
                    return Short2ObjectLinkedOpenHashMap.this.size;
                }

                @Override
                public boolean contains(Object v) {
                    return Short2ObjectLinkedOpenHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Short2ObjectLinkedOpenHashMap.this.clear();
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
        short[] key = this.key;
        V[] value = this.value;
        int mask = newN - 1;
        short[] newKey = new short[newN + 1];
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
            if (key[i] == 0) {
                pos = newN;
            } else {
                pos = HashCommon.mix(key[i]) & mask;
                while (newKey[pos] != 0) {
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

    public Short2ObjectLinkedOpenHashMap<V> clone() {
        Short2ObjectLinkedOpenHashMap c;
        try {
            c = (Short2ObjectLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (short[])this.key.clone();
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
            while (this.key[i] == 0) {
                ++i;
            }
            t = this.key[i];
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
        short[] key = this.key;
        V[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeShort(key[e]);
            s.writeObject(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new short[this.n + 1];
        short[] key = this.key;
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
            short k = s.readShort();
            Object v = s.readObject();
            if (k == 0) {
                pos = this.n;
                this.containsNullKey = true;
            } else {
                pos = HashCommon.mix(k) & this.mask;
                while (key[pos] != 0) {
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
            return Short2ObjectLinkedOpenHashMap.this.value[this.previousEntry()];
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
            return Short2ObjectLinkedOpenHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractShortSortedSet {
        private KeySet() {
        }

        @Override
        public ShortListIterator iterator(short from) {
            return new KeyIterator(from);
        }

        @Override
        public ShortListIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Short2ObjectLinkedOpenHashMap.this.size;
        }

        @Override
        public boolean contains(short k) {
            return Short2ObjectLinkedOpenHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(short k) {
            int oldSize = Short2ObjectLinkedOpenHashMap.this.size;
            Short2ObjectLinkedOpenHashMap.this.remove(k);
            return Short2ObjectLinkedOpenHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Short2ObjectLinkedOpenHashMap.this.clear();
        }

        @Override
        public short firstShort() {
            if (Short2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Short2ObjectLinkedOpenHashMap.this.key[Short2ObjectLinkedOpenHashMap.this.first];
        }

        @Override
        public short lastShort() {
            if (Short2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Short2ObjectLinkedOpenHashMap.this.key[Short2ObjectLinkedOpenHashMap.this.last];
        }

        @Override
        public ShortComparator comparator() {
            return null;
        }

        @Override
        public final ShortSortedSet tailSet(short from) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final ShortSortedSet headSet(short to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final ShortSortedSet subSet(short from, short to) {
            throw new UnsupportedOperationException();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ShortListIterator {
        public KeyIterator(short k) {
            super(k);
        }

        @Override
        public short previousShort() {
            return Short2ObjectLinkedOpenHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short previous() {
            return Short2ObjectLinkedOpenHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(Short ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Short ok) {
            throw new UnsupportedOperationException();
        }

        public KeyIterator() {
        }

        @Override
        public short nextShort() {
            return Short2ObjectLinkedOpenHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Short next() {
            return Short2ObjectLinkedOpenHashMap.this.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSortedSet<Short2ObjectMap.Entry<V>>
    implements Short2ObjectSortedMap.FastSortedEntrySet<V> {
        private MapEntrySet() {
        }

        @Override
        public ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public Comparator<? super Short2ObjectMap.Entry<V>> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Short2ObjectMap.Entry<V>> subSet(Short2ObjectMap.Entry<V> fromElement, Short2ObjectMap.Entry<V> toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Short2ObjectMap.Entry<V>> headSet(Short2ObjectMap.Entry<V> toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Short2ObjectMap.Entry<V>> tailSet(Short2ObjectMap.Entry<V> fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short2ObjectMap.Entry<V> first() {
            if (Short2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Short2ObjectLinkedOpenHashMap.this.first);
        }

        @Override
        public Short2ObjectMap.Entry<V> last() {
            if (Short2ObjectLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Short2ObjectLinkedOpenHashMap.this.last);
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Short)) {
                return false;
            }
            short k = (Short)e.getKey();
            Object v = e.getValue();
            if (k == 0) {
                return Short2ObjectLinkedOpenHashMap.this.containsNullKey && (Short2ObjectLinkedOpenHashMap.this.value[Short2ObjectLinkedOpenHashMap.this.n] == null ? v == null : Short2ObjectLinkedOpenHashMap.this.value[Short2ObjectLinkedOpenHashMap.this.n].equals(v));
            }
            short[] key = Short2ObjectLinkedOpenHashMap.this.key;
            int pos = HashCommon.mix(k) & Short2ObjectLinkedOpenHashMap.this.mask;
            short curr = key[pos];
            if (curr == 0) {
                return false;
            }
            if (k == curr) {
                return Short2ObjectLinkedOpenHashMap.this.value[pos] == null ? v == null : Short2ObjectLinkedOpenHashMap.this.value[pos].equals(v);
            }
            do {
                if ((curr = key[pos = pos + 1 & Short2ObjectLinkedOpenHashMap.this.mask]) != 0) continue;
                return false;
            } while (k != curr);
            return Short2ObjectLinkedOpenHashMap.this.value[pos] == null ? v == null : Short2ObjectLinkedOpenHashMap.this.value[pos].equals(v);
        }

        @Override
        public boolean rem(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Short)) {
                return false;
            }
            short k = (Short)e.getKey();
            Object v = e.getValue();
            if (k == 0) {
                if (Short2ObjectLinkedOpenHashMap.this.containsNullKey && (Short2ObjectLinkedOpenHashMap.this.value[Short2ObjectLinkedOpenHashMap.this.n] == null ? v == null : Short2ObjectLinkedOpenHashMap.this.value[Short2ObjectLinkedOpenHashMap.this.n].equals(v))) {
                    Short2ObjectLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            short[] key = Short2ObjectLinkedOpenHashMap.this.key;
            int pos = HashCommon.mix(k) & Short2ObjectLinkedOpenHashMap.this.mask;
            short curr = key[pos];
            if (curr == 0) {
                return false;
            }
            if (curr == k) {
                if (Short2ObjectLinkedOpenHashMap.this.value[pos] == null ? v == null : Short2ObjectLinkedOpenHashMap.this.value[pos].equals(v)) {
                    Short2ObjectLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if ((curr = key[pos = pos + 1 & Short2ObjectLinkedOpenHashMap.this.mask]) != 0) continue;
                return false;
            } while (curr != k || !(Short2ObjectLinkedOpenHashMap.this.value[pos] == null ? v == null : Short2ObjectLinkedOpenHashMap.this.value[pos].equals(v)));
            Short2ObjectLinkedOpenHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Short2ObjectLinkedOpenHashMap.this.size;
        }

        @Override
        public void clear() {
            Short2ObjectLinkedOpenHashMap.this.clear();
        }

        @Override
        public ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> iterator(Short2ObjectMap.Entry<V> from) {
            return new EntryIterator(from.getShortKey());
        }

        @Override
        public ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override
        public ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> fastIterator(Short2ObjectMap.Entry<V> from) {
            return new FastEntryIterator(from.getShortKey());
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Short2ObjectMap.Entry<V>> {
        final MapEntry entry;

        public FastEntryIterator() {
            this.entry = new MapEntry();
        }

        public FastEntryIterator(short from) {
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
        public void set(Short2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Short2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class EntryIterator
    extends MapIterator
    implements ObjectListIterator<Short2ObjectMap.Entry<V>> {
        private MapEntry entry;

        public EntryIterator() {
        }

        public EntryIterator(short from) {
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
        public void set(Short2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Short2ObjectMap.Entry<V> ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class MapIterator {
        int prev = -1;
        int next = -1;
        int curr = -1;
        int index = -1;

        private MapIterator() {
            this.next = Short2ObjectLinkedOpenHashMap.this.first;
            this.index = 0;
        }

        private MapIterator(short from) {
            if (from == 0) {
                if (Short2ObjectLinkedOpenHashMap.this.containsNullKey) {
                    this.next = (int)Short2ObjectLinkedOpenHashMap.this.link[Short2ObjectLinkedOpenHashMap.this.n];
                    this.prev = Short2ObjectLinkedOpenHashMap.this.n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
            if (Short2ObjectLinkedOpenHashMap.this.key[Short2ObjectLinkedOpenHashMap.this.last] == from) {
                this.prev = Short2ObjectLinkedOpenHashMap.this.last;
                this.index = Short2ObjectLinkedOpenHashMap.this.size;
                return;
            }
            int pos = HashCommon.mix(from) & Short2ObjectLinkedOpenHashMap.this.mask;
            while (Short2ObjectLinkedOpenHashMap.this.key[pos] != 0) {
                if (Short2ObjectLinkedOpenHashMap.this.key[pos] == from) {
                    this.next = (int)Short2ObjectLinkedOpenHashMap.this.link[pos];
                    this.prev = pos;
                    return;
                }
                pos = pos + 1 & Short2ObjectLinkedOpenHashMap.this.mask;
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
                this.index = Short2ObjectLinkedOpenHashMap.this.size;
                return;
            }
            int pos = Short2ObjectLinkedOpenHashMap.this.first;
            this.index = 1;
            while (pos != this.prev) {
                pos = (int)Short2ObjectLinkedOpenHashMap.this.link[pos];
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
            this.next = (int)Short2ObjectLinkedOpenHashMap.this.link[this.curr];
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
            this.prev = (int)(Short2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
                this.prev = (int)(Short2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
                this.next = (int)Short2ObjectLinkedOpenHashMap.this.link[this.curr];
            }
            --Short2ObjectLinkedOpenHashMap.this.size;
            if (this.prev == -1) {
                Short2ObjectLinkedOpenHashMap.this.first = this.next;
            } else {
                int n = this.prev;
                Short2ObjectLinkedOpenHashMap.this.link[n] = Short2ObjectLinkedOpenHashMap.this.link[n] ^ (Short2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            if (this.next == -1) {
                Short2ObjectLinkedOpenHashMap.this.last = this.prev;
            } else {
                int n = this.next;
                Short2ObjectLinkedOpenHashMap.this.link[n] = Short2ObjectLinkedOpenHashMap.this.link[n] ^ (Short2ObjectLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos != Short2ObjectLinkedOpenHashMap.this.n) {
                short[] key = Short2ObjectLinkedOpenHashMap.this.key;
                while (true) {
                    short curr;
                    int last = pos;
                    pos = last + 1 & Short2ObjectLinkedOpenHashMap.this.mask;
                    while (true) {
                        if ((curr = key[pos]) == 0) {
                            key[last] = 0;
                            Short2ObjectLinkedOpenHashMap.this.value[last] = null;
                            return;
                        }
                        int slot = HashCommon.mix(curr) & Short2ObjectLinkedOpenHashMap.this.mask;
                        if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                        pos = pos + 1 & Short2ObjectLinkedOpenHashMap.this.mask;
                    }
                    key[last] = curr;
                    Short2ObjectLinkedOpenHashMap.this.value[last] = Short2ObjectLinkedOpenHashMap.this.value[pos];
                    if (this.next == pos) {
                        this.next = last;
                    }
                    if (this.prev == pos) {
                        this.prev = last;
                    }
                    Short2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
                }
            }
            Short2ObjectLinkedOpenHashMap.this.containsNullKey = false;
            Short2ObjectLinkedOpenHashMap.this.value[Short2ObjectLinkedOpenHashMap.this.n] = null;
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
    implements Short2ObjectMap.Entry<V>,
    Map.Entry<Short, V> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Short getKey() {
            return Short2ObjectLinkedOpenHashMap.this.key[this.index];
        }

        @Override
        public short getShortKey() {
            return Short2ObjectLinkedOpenHashMap.this.key[this.index];
        }

        @Override
        public V getValue() {
            return Short2ObjectLinkedOpenHashMap.this.value[this.index];
        }

        @Override
        public V setValue(V v) {
            Object oldValue = Short2ObjectLinkedOpenHashMap.this.value[this.index];
            Short2ObjectLinkedOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Short2ObjectLinkedOpenHashMap.this.key[this.index] == (Short)e.getKey() && (Short2ObjectLinkedOpenHashMap.this.value[this.index] == null ? e.getValue() == null : Short2ObjectLinkedOpenHashMap.this.value[this.index].equals(e.getValue()));
        }

        @Override
        public int hashCode() {
            return Short2ObjectLinkedOpenHashMap.this.key[this.index] ^ (Short2ObjectLinkedOpenHashMap.this.value[this.index] == null ? 0 : Short2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Short2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Short2ObjectLinkedOpenHashMap.this.value[this.index];
        }
    }
}

