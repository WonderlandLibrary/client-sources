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
import it.unimi.dsi.fastutil.objects.AbstractObject2IntSortedMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
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

public class Object2IntLinkedOpenCustomHashMap<K>
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
    protected Hash.Strategy<K> strategy;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Object2IntSortedMap.FastSortedEntrySet<K> entries;
    protected transient ObjectSortedSet<K> keys;
    protected transient IntCollection values;

    public Object2IntLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<K> strategy) {
        this.strategy = strategy;
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
        this.key = new Object[this.n + 1];
        this.value = new int[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Object2IntLinkedOpenCustomHashMap(int expected, Hash.Strategy<K> strategy) {
        this(expected, 0.75f, strategy);
    }

    public Object2IntLinkedOpenCustomHashMap(Hash.Strategy<K> strategy) {
        this(16, 0.75f, strategy);
    }

    public Object2IntLinkedOpenCustomHashMap(Map<? extends K, ? extends Integer> m, float f, Hash.Strategy<K> strategy) {
        this(m.size(), f, strategy);
        this.putAll(m);
    }

    public Object2IntLinkedOpenCustomHashMap(Map<? extends K, ? extends Integer> m, Hash.Strategy<K> strategy) {
        this(m, 0.75f, strategy);
    }

    public Object2IntLinkedOpenCustomHashMap(Object2IntMap<K> m, float f, Hash.Strategy<K> strategy) {
        this(m.size(), f, strategy);
        this.putAll(m);
    }

    public Object2IntLinkedOpenCustomHashMap(Object2IntMap<K> m, Hash.Strategy<K> strategy) {
        this(m, 0.75f, strategy);
    }

    public Object2IntLinkedOpenCustomHashMap(K[] k, int[] v, float f, Hash.Strategy<K> strategy) {
        this(k.length, f, strategy);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Object2IntLinkedOpenCustomHashMap(K[] k, int[] v, Hash.Strategy<K> strategy) {
        this(k, v, 0.75f, strategy);
    }

    public Hash.Strategy<K> strategy() {
        return this.strategy;
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

    private int removeEntry(int pos) {
        int oldValue = this.value[pos];
        --this.size;
        this.fixPointers(pos);
        this.shiftKeys(pos);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    private int removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.n] = null;
        int oldValue = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends Integer> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(K k, int v) {
        int pos;
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            K[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            K curr = key[pos];
            if (curr != null) {
                if (this.strategy.equals(curr, k)) {
                    return pos;
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != null) {
                    if (!this.strategy.equals(curr, k)) continue;
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
    public int put(K k, int v) {
        int pos = this.insert(k, v);
        if (pos < 0) {
            return this.defRetValue;
        }
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override
    @Deprecated
    public Integer put(K ok, Integer ov) {
        int v = ov;
        int pos = this.insert(ok, v);
        if (pos < 0) {
            return null;
        }
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    private int addToValue(int pos, int incr) {
        int oldValue = this.value[pos];
        this.value[pos] = oldValue + incr;
        return oldValue;
    }

    public int addTo(K k, int incr) {
        int pos;
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, incr);
            }
            pos = this.n;
            this.containsNullKey = true;
        } else {
            K[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            K curr = key[pos];
            if (curr != null) {
                if (this.strategy.equals(curr, k)) {
                    return this.addToValue(pos, incr);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != null) {
                    if (!this.strategy.equals(curr, k)) continue;
                    return this.addToValue(pos, incr);
                }
            }
        }
        this.key[pos] = k;
        this.value[pos] = this.defRetValue + incr;
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
        return this.defRetValue;
    }

    protected final void shiftKeys(int pos) {
        K[] key = this.key;
        while (true) {
            K curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if ((curr = key[pos]) == null) {
                    key[last] = null;
                    return;
                }
                int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
                if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                pos = pos + 1 & this.mask;
            }
            key[last] = curr;
            this.value[last] = this.value[pos];
            this.fixPointers(pos, last);
        }
    }

    @Override
    public int removeInt(Object k) {
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        K[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public Integer remove(Object ok) {
        Object k = ok;
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return null;
        }
        K[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return null;
        }
        if (this.strategy.equals(curr, k)) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return null;
        } while (!this.strategy.equals(curr, k));
        return this.removeEntry(pos);
    }

    private int setValue(int pos, int v) {
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    public int removeFirstInt() {
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
        int v = this.value[pos];
        if (pos == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
        } else {
            this.shiftKeys(pos);
        }
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    public int removeLastInt() {
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
        int v = this.value[pos];
        if (pos == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
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

    public int getAndMoveToFirst(K k) {
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        K[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        this.moveIndexToFirst(pos);
        return this.value[pos];
    }

    public int getAndMoveToLast(K k) {
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        K[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            this.moveIndexToLast(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        this.moveIndexToLast(pos);
        return this.value[pos];
    }

    public int putAndMoveToFirst(K k, int v) {
        int pos;
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            K[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            K curr = key[pos];
            if (curr != null) {
                if (this.strategy.equals(curr, k)) {
                    this.moveIndexToFirst(pos);
                    return this.setValue(pos, v);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != null) {
                    if (!this.strategy.equals(curr, k)) continue;
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
        return this.defRetValue;
    }

    public int putAndMoveToLast(K k, int v) {
        int pos;
        if (this.strategy.equals(k, null)) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            K[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            K curr = key[pos];
            if (curr != null) {
                if (this.strategy.equals(curr, k)) {
                    this.moveIndexToLast(pos);
                    return this.setValue(pos, v);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != null) {
                    if (!this.strategy.equals(curr, k)) continue;
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
        return this.defRetValue;
    }

    @Override
    public int getInt(Object k) {
        if (this.strategy.equals(k, null)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.value[pos];
    }

    @Override
    public boolean containsKey(Object k) {
        if (this.strategy.equals(k, null)) {
            return this.containsNullKey;
        }
        K[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return false;
        }
        if (this.strategy.equals(k, curr)) {
            return true;
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return false;
        } while (!this.strategy.equals(k, curr));
        return true;
    }

    @Override
    public boolean containsValue(int v) {
        int[] value = this.value;
        K[] key = this.key;
        if (this.containsNullKey && value[this.n] == v) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (key[i] == null || value[i] != v) continue;
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
    public Comparator<? super K> comparator() {
        return null;
    }

    @Override
    public Object2IntSortedMap<K> tailMap(K from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object2IntSortedMap<K> headMap(K to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object2IntSortedMap<K> subMap(K from, K to) {
        throw new UnsupportedOperationException();
    }

    public Object2IntSortedMap.FastSortedEntrySet<K> object2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(){

                @Override
                public IntIterator iterator() {
                    return new ValueIterator();
                }

                @Override
                public int size() {
                    return Object2IntLinkedOpenCustomHashMap.this.size;
                }

                @Override
                public boolean contains(int v) {
                    return Object2IntLinkedOpenCustomHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Object2IntLinkedOpenCustomHashMap.this.clear();
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
        K[] key = this.key;
        int[] value = this.value;
        int mask = newN - 1;
        Object[] newKey = new Object[newN + 1];
        int[] newValue = new int[newN + 1];
        int i = this.first;
        int prev = -1;
        int newPrev = -1;
        long[] link = this.link;
        long[] newLink = new long[newN + 1];
        this.first = -1;
        int j = this.size;
        while (j-- != 0) {
            int pos;
            if (this.strategy.equals(key[i], null)) {
                pos = newN;
            } else {
                pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
                while (newKey[pos] != null) {
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

    public Object2IntLinkedOpenCustomHashMap<K> clone() {
        Object2IntLinkedOpenCustomHashMap c;
        try {
            c = (Object2IntLinkedOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (Object[])this.key.clone();
        c.value = (int[])this.value.clone();
        c.link = (long[])this.link.clone();
        c.strategy = this.strategy;
        return c;
    }

    @Override
    public int hashCode() {
        int h = 0;
        int j = this.realSize();
        int i = 0;
        int t = 0;
        while (j-- != 0) {
            while (this.key[i] == null) {
                ++i;
            }
            if (this != this.key[i]) {
                t = this.strategy.hashCode(this.key[i]);
            }
            h += (t ^= this.value[i]);
            ++i;
        }
        if (this.containsNullKey) {
            h += this.value[this.n];
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        K[] key = this.key;
        int[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeObject(key[e]);
            s.writeInt(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] key = this.key;
        this.value = new int[this.n + 1];
        int[] value = this.value;
        this.link = new long[this.n + 1];
        long[] link = this.link;
        int prev = -1;
        this.last = -1;
        this.first = -1;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            Object k = s.readObject();
            int v = s.readInt();
            if (this.strategy.equals(k, null)) {
                pos = this.n;
                this.containsNullKey = true;
            } else {
                pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
                while (key[pos] != null) {
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
    implements IntListIterator {
        @Override
        public int previousInt() {
            return Object2IntLinkedOpenCustomHashMap.this.value[this.previousEntry()];
        }

        @Override
        public Integer previous() {
            return Object2IntLinkedOpenCustomHashMap.this.value[this.previousEntry()];
        }

        @Override
        public void set(Integer ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Integer ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(int v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextInt() {
            return Object2IntLinkedOpenCustomHashMap.this.value[this.nextEntry()];
        }

        @Override
        @Deprecated
        public Integer next() {
            return Object2IntLinkedOpenCustomHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractObjectSortedSet<K> {
        private KeySet() {
        }

        @Override
        public ObjectListIterator<K> iterator(K from) {
            return new KeyIterator(from);
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Object2IntLinkedOpenCustomHashMap.this.size;
        }

        @Override
        public boolean contains(Object k) {
            return Object2IntLinkedOpenCustomHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(Object k) {
            int oldSize = Object2IntLinkedOpenCustomHashMap.this.size;
            Object2IntLinkedOpenCustomHashMap.this.remove(k);
            return Object2IntLinkedOpenCustomHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Object2IntLinkedOpenCustomHashMap.this.clear();
        }

        @Override
        public K first() {
            if (Object2IntLinkedOpenCustomHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Object2IntLinkedOpenCustomHashMap.this.key[Object2IntLinkedOpenCustomHashMap.this.first];
        }

        @Override
        public K last() {
            if (Object2IntLinkedOpenCustomHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Object2IntLinkedOpenCustomHashMap.this.key[Object2IntLinkedOpenCustomHashMap.this.last];
        }

        @Override
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override
        public final ObjectSortedSet<K> tailSet(K from) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final ObjectSortedSet<K> headSet(K to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final ObjectSortedSet<K> subSet(K from, K to) {
            throw new UnsupportedOperationException();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ObjectListIterator<K> {
        public KeyIterator(K k) {
            super(k);
        }

        @Override
        public K previous() {
            return Object2IntLinkedOpenCustomHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(K k) {
            throw new UnsupportedOperationException();
        }

        public KeyIterator() {
        }

        @Override
        public K next() {
            return Object2IntLinkedOpenCustomHashMap.this.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSortedSet<Object2IntMap.Entry<K>>
    implements Object2IntSortedMap.FastSortedEntrySet<K> {
        private MapEntrySet() {
        }

        @Override
        public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
            return new EntryIterator();
        }

        @Override
        public Comparator<? super Object2IntMap.Entry<K>> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> fromElement, Object2IntMap.Entry<K> toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object2IntMap.Entry<K> first() {
            if (Object2IntLinkedOpenCustomHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Object2IntLinkedOpenCustomHashMap.this.first);
        }

        @Override
        public Object2IntMap.Entry<K> last() {
            if (Object2IntLinkedOpenCustomHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Object2IntLinkedOpenCustomHashMap.this.last);
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object k = e.getKey();
            int v = (Integer)e.getValue();
            if (Object2IntLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
                return Object2IntLinkedOpenCustomHashMap.this.containsNullKey && Object2IntLinkedOpenCustomHashMap.this.value[Object2IntLinkedOpenCustomHashMap.this.n] == v;
            }
            K[] key = Object2IntLinkedOpenCustomHashMap.this.key;
            int pos = HashCommon.mix(Object2IntLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2IntLinkedOpenCustomHashMap.this.mask;
            Object curr = key[pos];
            if (curr == null) {
                return false;
            }
            if (Object2IntLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
                return Object2IntLinkedOpenCustomHashMap.this.value[pos] == v;
            }
            do {
                if ((curr = key[pos = pos + 1 & Object2IntLinkedOpenCustomHashMap.this.mask]) != null) continue;
                return false;
            } while (!Object2IntLinkedOpenCustomHashMap.this.strategy.equals(k, curr));
            return Object2IntLinkedOpenCustomHashMap.this.value[pos] == v;
        }

        @Override
        public boolean rem(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object k = e.getKey();
            int v = (Integer)e.getValue();
            if (Object2IntLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
                if (Object2IntLinkedOpenCustomHashMap.this.containsNullKey && Object2IntLinkedOpenCustomHashMap.this.value[Object2IntLinkedOpenCustomHashMap.this.n] == v) {
                    Object2IntLinkedOpenCustomHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            K[] key = Object2IntLinkedOpenCustomHashMap.this.key;
            int pos = HashCommon.mix(Object2IntLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2IntLinkedOpenCustomHashMap.this.mask;
            Object curr = key[pos];
            if (curr == null) {
                return false;
            }
            if (Object2IntLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
                if (Object2IntLinkedOpenCustomHashMap.this.value[pos] == v) {
                    Object2IntLinkedOpenCustomHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if ((curr = key[pos = pos + 1 & Object2IntLinkedOpenCustomHashMap.this.mask]) != null) continue;
                return false;
            } while (!Object2IntLinkedOpenCustomHashMap.this.strategy.equals(curr, k) || Object2IntLinkedOpenCustomHashMap.this.value[pos] != v);
            Object2IntLinkedOpenCustomHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Object2IntLinkedOpenCustomHashMap.this.size;
        }

        @Override
        public void clear() {
            Object2IntLinkedOpenCustomHashMap.this.clear();
        }

        @Override
        public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
            return new EntryIterator(from.getKey());
        }

        @Override
        public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override
        public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap.Entry<K> from) {
            return new FastEntryIterator(from.getKey());
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Object2IntMap.Entry<K>> {
        final MapEntry entry;

        public FastEntryIterator() {
            this.entry = new MapEntry();
        }

        public FastEntryIterator(K from) {
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
        public void set(Object2IntMap.Entry<K> ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Object2IntMap.Entry<K> ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class EntryIterator
    extends MapIterator
    implements ObjectListIterator<Object2IntMap.Entry<K>> {
        private MapEntry entry;

        public EntryIterator() {
        }

        public EntryIterator(K from) {
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
        public void set(Object2IntMap.Entry<K> ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Object2IntMap.Entry<K> ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class MapIterator {
        int prev = -1;
        int next = -1;
        int curr = -1;
        int index = -1;

        private MapIterator() {
            this.next = Object2IntLinkedOpenCustomHashMap.this.first;
            this.index = 0;
        }

        private MapIterator(K from) {
            if (Object2IntLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
                if (Object2IntLinkedOpenCustomHashMap.this.containsNullKey) {
                    this.next = (int)Object2IntLinkedOpenCustomHashMap.this.link[Object2IntLinkedOpenCustomHashMap.this.n];
                    this.prev = Object2IntLinkedOpenCustomHashMap.this.n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
            if (Object2IntLinkedOpenCustomHashMap.this.strategy.equals(Object2IntLinkedOpenCustomHashMap.this.key[Object2IntLinkedOpenCustomHashMap.this.last], from)) {
                this.prev = Object2IntLinkedOpenCustomHashMap.this.last;
                this.index = Object2IntLinkedOpenCustomHashMap.this.size;
                return;
            }
            int pos = HashCommon.mix(Object2IntLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2IntLinkedOpenCustomHashMap.this.mask;
            while (Object2IntLinkedOpenCustomHashMap.this.key[pos] != null) {
                if (Object2IntLinkedOpenCustomHashMap.this.strategy.equals(Object2IntLinkedOpenCustomHashMap.this.key[pos], from)) {
                    this.next = (int)Object2IntLinkedOpenCustomHashMap.this.link[pos];
                    this.prev = pos;
                    return;
                }
                pos = pos + 1 & Object2IntLinkedOpenCustomHashMap.this.mask;
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
                this.index = Object2IntLinkedOpenCustomHashMap.this.size;
                return;
            }
            int pos = Object2IntLinkedOpenCustomHashMap.this.first;
            this.index = 1;
            while (pos != this.prev) {
                pos = (int)Object2IntLinkedOpenCustomHashMap.this.link[pos];
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
            this.next = (int)Object2IntLinkedOpenCustomHashMap.this.link[this.curr];
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
            this.prev = (int)(Object2IntLinkedOpenCustomHashMap.this.link[this.curr] >>> 32);
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
                this.prev = (int)(Object2IntLinkedOpenCustomHashMap.this.link[this.curr] >>> 32);
            } else {
                this.next = (int)Object2IntLinkedOpenCustomHashMap.this.link[this.curr];
            }
            --Object2IntLinkedOpenCustomHashMap.this.size;
            if (this.prev == -1) {
                Object2IntLinkedOpenCustomHashMap.this.first = this.next;
            } else {
                int n = this.prev;
                Object2IntLinkedOpenCustomHashMap.this.link[n] = Object2IntLinkedOpenCustomHashMap.this.link[n] ^ (Object2IntLinkedOpenCustomHashMap.this.link[this.prev] ^ (long)this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            if (this.next == -1) {
                Object2IntLinkedOpenCustomHashMap.this.last = this.prev;
            } else {
                int n = this.next;
                Object2IntLinkedOpenCustomHashMap.this.link[n] = Object2IntLinkedOpenCustomHashMap.this.link[n] ^ (Object2IntLinkedOpenCustomHashMap.this.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos != Object2IntLinkedOpenCustomHashMap.this.n) {
                K[] key = Object2IntLinkedOpenCustomHashMap.this.key;
                while (true) {
                    Object curr;
                    int last = pos;
                    pos = last + 1 & Object2IntLinkedOpenCustomHashMap.this.mask;
                    while (true) {
                        if ((curr = key[pos]) == null) {
                            key[last] = null;
                            return;
                        }
                        int slot = HashCommon.mix(Object2IntLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2IntLinkedOpenCustomHashMap.this.mask;
                        if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                        pos = pos + 1 & Object2IntLinkedOpenCustomHashMap.this.mask;
                    }
                    key[last] = curr;
                    Object2IntLinkedOpenCustomHashMap.this.value[last] = Object2IntLinkedOpenCustomHashMap.this.value[pos];
                    if (this.next == pos) {
                        this.next = last;
                    }
                    if (this.prev == pos) {
                        this.prev = last;
                    }
                    Object2IntLinkedOpenCustomHashMap.this.fixPointers(pos, last);
                }
            }
            Object2IntLinkedOpenCustomHashMap.this.containsNullKey = false;
            Object2IntLinkedOpenCustomHashMap.this.key[Object2IntLinkedOpenCustomHashMap.this.n] = null;
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
    implements Object2IntMap.Entry<K>,
    Map.Entry<K, Integer> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        public K getKey() {
            return Object2IntLinkedOpenCustomHashMap.this.key[this.index];
        }

        @Override
        @Deprecated
        public Integer getValue() {
            return Object2IntLinkedOpenCustomHashMap.this.value[this.index];
        }

        @Override
        public int getIntValue() {
            return Object2IntLinkedOpenCustomHashMap.this.value[this.index];
        }

        @Override
        public int setValue(int v) {
            int oldValue = Object2IntLinkedOpenCustomHashMap.this.value[this.index];
            Object2IntLinkedOpenCustomHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public Integer setValue(Integer v) {
            return this.setValue((int)v);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Object2IntLinkedOpenCustomHashMap.this.strategy.equals(Object2IntLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2IntLinkedOpenCustomHashMap.this.value[this.index] == (Integer)e.getValue();
        }

        @Override
        public int hashCode() {
            return Object2IntLinkedOpenCustomHashMap.this.strategy.hashCode(Object2IntLinkedOpenCustomHashMap.this.key[this.index]) ^ Object2IntLinkedOpenCustomHashMap.this.value[this.index];
        }

        public String toString() {
            return Object2IntLinkedOpenCustomHashMap.this.key[this.index] + "=>" + Object2IntLinkedOpenCustomHashMap.this.value[this.index];
        }
    }
}

