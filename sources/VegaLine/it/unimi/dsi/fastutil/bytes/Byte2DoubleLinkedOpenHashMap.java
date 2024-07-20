/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleSortedMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
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

public class Byte2DoubleLinkedOpenHashMap
extends AbstractByte2DoubleSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient byte[] key;
    protected transient double[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Byte2DoubleSortedMap.FastSortedEntrySet entries;
    protected transient ByteSortedSet keys;
    protected transient DoubleCollection values;

    public Byte2DoubleLinkedOpenHashMap(int expected, float f) {
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
        this.key = new byte[this.n + 1];
        this.value = new double[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Byte2DoubleLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Byte2DoubleLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Byte2DoubleLinkedOpenHashMap(Map<? extends Byte, ? extends Double> m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Byte2DoubleLinkedOpenHashMap(Map<? extends Byte, ? extends Double> m) {
        this(m, 0.75f);
    }

    public Byte2DoubleLinkedOpenHashMap(Byte2DoubleMap m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Byte2DoubleLinkedOpenHashMap(Byte2DoubleMap m) {
        this(m, 0.75f);
    }

    public Byte2DoubleLinkedOpenHashMap(byte[] k, double[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Byte2DoubleLinkedOpenHashMap(byte[] k, double[] v) {
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

    private double removeEntry(int pos) {
        double oldValue = this.value[pos];
        --this.size;
        this.fixPointers(pos);
        this.shiftKeys(pos);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    private double removeNullEntry() {
        this.containsNullKey = false;
        double oldValue = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Byte, ? extends Double> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(byte k, double v) {
        int pos;
        if (k == 0) {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            byte[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            byte curr = key[pos];
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
    public double put(byte k, double v) {
        int pos = this.insert(k, v);
        if (pos < 0) {
            return this.defRetValue;
        }
        double oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override
    @Deprecated
    public Double put(Byte ok, Double ov) {
        double v = ov;
        int pos = this.insert(ok, v);
        if (pos < 0) {
            return null;
        }
        double oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    private double addToValue(int pos, double incr) {
        double oldValue = this.value[pos];
        this.value[pos] = oldValue + incr;
        return oldValue;
    }

    public double addTo(byte k, double incr) {
        int pos;
        if (k == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, incr);
            }
            pos = this.n;
            this.containsNullKey = true;
        } else {
            byte[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            byte curr = key[pos];
            if (curr != 0) {
                if (curr == k) {
                    return this.addToValue(pos, incr);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
                    if (curr != k) continue;
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
        byte[] key = this.key;
        while (true) {
            byte curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if ((curr = key[pos]) == 0) {
                    key[last] = 0;
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
    public double remove(byte k) {
        if (k == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        byte[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        byte curr = key[pos];
        if (curr == 0) {
            return this.defRetValue;
        }
        if (k == curr) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (k != curr);
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public Double remove(Object ok) {
        byte k = (Byte)ok;
        if (k == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return null;
        }
        byte[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        byte curr = key[pos];
        if (curr == 0) {
            return null;
        }
        if (curr == k) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return null;
        } while (curr != k);
        return this.removeEntry(pos);
    }

    private double setValue(int pos, double v) {
        double oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    public double removeFirstDouble() {
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
        double v = this.value[pos];
        if (pos == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(pos);
        }
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    public double removeLastDouble() {
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
        double v = this.value[pos];
        if (pos == this.n) {
            this.containsNullKey = false;
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

    public double getAndMoveToFirst(byte k) {
        if (k == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        byte[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        byte curr = key[pos];
        if (curr == 0) {
            return this.defRetValue;
        }
        if (k == curr) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (k != curr);
        this.moveIndexToFirst(pos);
        return this.value[pos];
    }

    public double getAndMoveToLast(byte k) {
        if (k == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        byte[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        byte curr = key[pos];
        if (curr == 0) {
            return this.defRetValue;
        }
        if (k == curr) {
            this.moveIndexToLast(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (k != curr);
        this.moveIndexToLast(pos);
        return this.value[pos];
    }

    public double putAndMoveToFirst(byte k, double v) {
        int pos;
        if (k == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            byte[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            byte curr = key[pos];
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
        return this.defRetValue;
    }

    public double putAndMoveToLast(byte k, double v) {
        int pos;
        if (k == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            byte[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            byte curr = key[pos];
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
        return this.defRetValue;
    }

    @Deprecated
    public Double get(Byte ok) {
        if (ok == null) {
            return null;
        }
        byte k = ok;
        if (k == 0) {
            return this.containsNullKey ? Double.valueOf(this.value[this.n]) : null;
        }
        byte[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        byte curr = key[pos];
        if (curr == 0) {
            return null;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return null;
        } while (k != curr);
        return this.value[pos];
    }

    @Override
    public double get(byte k) {
        if (k == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        byte[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        byte curr = key[pos];
        if (curr == 0) {
            return this.defRetValue;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (k != curr);
        return this.value[pos];
    }

    @Override
    public boolean containsKey(byte k) {
        if (k == 0) {
            return this.containsNullKey;
        }
        byte[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        byte curr = key[pos];
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
    public boolean containsValue(double v) {
        double[] value = this.value;
        byte[] key = this.key;
        if (this.containsNullKey && value[this.n] == v) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (key[i] == 0 || value[i] != v) continue;
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
    public ByteComparator comparator() {
        return null;
    }

    @Override
    public Byte2DoubleSortedMap tailMap(byte from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Byte2DoubleSortedMap headMap(byte to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Byte2DoubleSortedMap subMap(byte from, byte to) {
        throw new UnsupportedOperationException();
    }

    public Byte2DoubleSortedMap.FastSortedEntrySet byte2DoubleEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public ByteSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public DoubleCollection values() {
        if (this.values == null) {
            this.values = new AbstractDoubleCollection(){

                @Override
                public DoubleIterator iterator() {
                    return new ValueIterator();
                }

                @Override
                public int size() {
                    return Byte2DoubleLinkedOpenHashMap.this.size;
                }

                @Override
                public boolean contains(double v) {
                    return Byte2DoubleLinkedOpenHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Byte2DoubleLinkedOpenHashMap.this.clear();
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
        byte[] key = this.key;
        double[] value = this.value;
        int mask = newN - 1;
        byte[] newKey = new byte[newN + 1];
        double[] newValue = new double[newN + 1];
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

    public Byte2DoubleLinkedOpenHashMap clone() {
        Byte2DoubleLinkedOpenHashMap c;
        try {
            c = (Byte2DoubleLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (byte[])this.key.clone();
        c.value = (double[])this.value.clone();
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
            h += (t ^= HashCommon.double2int(this.value[i]));
            ++i;
        }
        if (this.containsNullKey) {
            h += HashCommon.double2int(this.value[this.n]);
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        byte[] key = this.key;
        double[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeByte(key[e]);
            s.writeDouble(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new byte[this.n + 1];
        byte[] key = this.key;
        this.value = new double[this.n + 1];
        double[] value = this.value;
        this.link = new long[this.n + 1];
        long[] link = this.link;
        int prev = -1;
        this.last = -1;
        this.first = -1;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            byte k = s.readByte();
            double v = s.readDouble();
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
    implements DoubleListIterator {
        @Override
        public double previousDouble() {
            return Byte2DoubleLinkedOpenHashMap.this.value[this.previousEntry()];
        }

        @Override
        public Double previous() {
            return Byte2DoubleLinkedOpenHashMap.this.value[this.previousEntry()];
        }

        @Override
        public void set(Double ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Double ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(double v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(double v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double nextDouble() {
            return Byte2DoubleLinkedOpenHashMap.this.value[this.nextEntry()];
        }

        @Override
        @Deprecated
        public Double next() {
            return Byte2DoubleLinkedOpenHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractByteSortedSet {
        private KeySet() {
        }

        @Override
        public ByteListIterator iterator(byte from) {
            return new KeyIterator(from);
        }

        @Override
        public ByteListIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Byte2DoubleLinkedOpenHashMap.this.size;
        }

        @Override
        public boolean contains(byte k) {
            return Byte2DoubleLinkedOpenHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(byte k) {
            int oldSize = Byte2DoubleLinkedOpenHashMap.this.size;
            Byte2DoubleLinkedOpenHashMap.this.remove(k);
            return Byte2DoubleLinkedOpenHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Byte2DoubleLinkedOpenHashMap.this.clear();
        }

        @Override
        public byte firstByte() {
            if (Byte2DoubleLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Byte2DoubleLinkedOpenHashMap.this.key[Byte2DoubleLinkedOpenHashMap.this.first];
        }

        @Override
        public byte lastByte() {
            if (Byte2DoubleLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Byte2DoubleLinkedOpenHashMap.this.key[Byte2DoubleLinkedOpenHashMap.this.last];
        }

        @Override
        public ByteComparator comparator() {
            return null;
        }

        @Override
        public final ByteSortedSet tailSet(byte from) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final ByteSortedSet headSet(byte to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final ByteSortedSet subSet(byte from, byte to) {
            throw new UnsupportedOperationException();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ByteListIterator {
        public KeyIterator(byte k) {
            super(k);
        }

        @Override
        public byte previousByte() {
            return Byte2DoubleLinkedOpenHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(byte k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte previous() {
            return Byte2DoubleLinkedOpenHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(Byte ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Byte ok) {
            throw new UnsupportedOperationException();
        }

        public KeyIterator() {
        }

        @Override
        public byte nextByte() {
            return Byte2DoubleLinkedOpenHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Byte next() {
            return Byte2DoubleLinkedOpenHashMap.this.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSortedSet<Byte2DoubleMap.Entry>
    implements Byte2DoubleSortedMap.FastSortedEntrySet {
        private MapEntrySet() {
        }

        @Override
        public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override
        public Comparator<? super Byte2DoubleMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Byte2DoubleMap.Entry> subSet(Byte2DoubleMap.Entry fromElement, Byte2DoubleMap.Entry toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Byte2DoubleMap.Entry> headSet(Byte2DoubleMap.Entry toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Byte2DoubleMap.Entry> tailSet(Byte2DoubleMap.Entry fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Byte2DoubleMap.Entry first() {
            if (Byte2DoubleLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Byte2DoubleLinkedOpenHashMap.this.first);
        }

        @Override
        public Byte2DoubleMap.Entry last() {
            if (Byte2DoubleLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Byte2DoubleLinkedOpenHashMap.this.last);
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Double)) {
                return false;
            }
            byte k = (Byte)e.getKey();
            double v = (Double)e.getValue();
            if (k == 0) {
                return Byte2DoubleLinkedOpenHashMap.this.containsNullKey && Byte2DoubleLinkedOpenHashMap.this.value[Byte2DoubleLinkedOpenHashMap.this.n] == v;
            }
            byte[] key = Byte2DoubleLinkedOpenHashMap.this.key;
            int pos = HashCommon.mix(k) & Byte2DoubleLinkedOpenHashMap.this.mask;
            byte curr = key[pos];
            if (curr == 0) {
                return false;
            }
            if (k == curr) {
                return Byte2DoubleLinkedOpenHashMap.this.value[pos] == v;
            }
            do {
                if ((curr = key[pos = pos + 1 & Byte2DoubleLinkedOpenHashMap.this.mask]) != 0) continue;
                return false;
            } while (k != curr);
            return Byte2DoubleLinkedOpenHashMap.this.value[pos] == v;
        }

        @Override
        public boolean rem(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Double)) {
                return false;
            }
            byte k = (Byte)e.getKey();
            double v = (Double)e.getValue();
            if (k == 0) {
                if (Byte2DoubleLinkedOpenHashMap.this.containsNullKey && Byte2DoubleLinkedOpenHashMap.this.value[Byte2DoubleLinkedOpenHashMap.this.n] == v) {
                    Byte2DoubleLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            byte[] key = Byte2DoubleLinkedOpenHashMap.this.key;
            int pos = HashCommon.mix(k) & Byte2DoubleLinkedOpenHashMap.this.mask;
            byte curr = key[pos];
            if (curr == 0) {
                return false;
            }
            if (curr == k) {
                if (Byte2DoubleLinkedOpenHashMap.this.value[pos] == v) {
                    Byte2DoubleLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if ((curr = key[pos = pos + 1 & Byte2DoubleLinkedOpenHashMap.this.mask]) != 0) continue;
                return false;
            } while (curr != k || Byte2DoubleLinkedOpenHashMap.this.value[pos] != v);
            Byte2DoubleLinkedOpenHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Byte2DoubleLinkedOpenHashMap.this.size;
        }

        @Override
        public void clear() {
            Byte2DoubleLinkedOpenHashMap.this.clear();
        }

        @Override
        public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> iterator(Byte2DoubleMap.Entry from) {
            return new EntryIterator(from.getByteKey());
        }

        public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> fastIterator() {
            return new FastEntryIterator();
        }

        @Override
        public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> fastIterator(Byte2DoubleMap.Entry from) {
            return new FastEntryIterator(from.getByteKey());
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Byte2DoubleMap.Entry> {
        final MapEntry entry;

        public FastEntryIterator() {
            this.entry = new MapEntry();
        }

        public FastEntryIterator(byte from) {
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
        public void set(Byte2DoubleMap.Entry ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Byte2DoubleMap.Entry ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class EntryIterator
    extends MapIterator
    implements ObjectListIterator<Byte2DoubleMap.Entry> {
        private MapEntry entry;

        public EntryIterator() {
        }

        public EntryIterator(byte from) {
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
        public void set(Byte2DoubleMap.Entry ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Byte2DoubleMap.Entry ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class MapIterator {
        int prev = -1;
        int next = -1;
        int curr = -1;
        int index = -1;

        private MapIterator() {
            this.next = Byte2DoubleLinkedOpenHashMap.this.first;
            this.index = 0;
        }

        private MapIterator(byte from) {
            if (from == 0) {
                if (Byte2DoubleLinkedOpenHashMap.this.containsNullKey) {
                    this.next = (int)Byte2DoubleLinkedOpenHashMap.this.link[Byte2DoubleLinkedOpenHashMap.this.n];
                    this.prev = Byte2DoubleLinkedOpenHashMap.this.n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
            if (Byte2DoubleLinkedOpenHashMap.this.key[Byte2DoubleLinkedOpenHashMap.this.last] == from) {
                this.prev = Byte2DoubleLinkedOpenHashMap.this.last;
                this.index = Byte2DoubleLinkedOpenHashMap.this.size;
                return;
            }
            int pos = HashCommon.mix(from) & Byte2DoubleLinkedOpenHashMap.this.mask;
            while (Byte2DoubleLinkedOpenHashMap.this.key[pos] != 0) {
                if (Byte2DoubleLinkedOpenHashMap.this.key[pos] == from) {
                    this.next = (int)Byte2DoubleLinkedOpenHashMap.this.link[pos];
                    this.prev = pos;
                    return;
                }
                pos = pos + 1 & Byte2DoubleLinkedOpenHashMap.this.mask;
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
                this.index = Byte2DoubleLinkedOpenHashMap.this.size;
                return;
            }
            int pos = Byte2DoubleLinkedOpenHashMap.this.first;
            this.index = 1;
            while (pos != this.prev) {
                pos = (int)Byte2DoubleLinkedOpenHashMap.this.link[pos];
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
            this.next = (int)Byte2DoubleLinkedOpenHashMap.this.link[this.curr];
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
            this.prev = (int)(Byte2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
                this.prev = (int)(Byte2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
                this.next = (int)Byte2DoubleLinkedOpenHashMap.this.link[this.curr];
            }
            --Byte2DoubleLinkedOpenHashMap.this.size;
            if (this.prev == -1) {
                Byte2DoubleLinkedOpenHashMap.this.first = this.next;
            } else {
                int n = this.prev;
                Byte2DoubleLinkedOpenHashMap.this.link[n] = Byte2DoubleLinkedOpenHashMap.this.link[n] ^ (Byte2DoubleLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            if (this.next == -1) {
                Byte2DoubleLinkedOpenHashMap.this.last = this.prev;
            } else {
                int n = this.next;
                Byte2DoubleLinkedOpenHashMap.this.link[n] = Byte2DoubleLinkedOpenHashMap.this.link[n] ^ (Byte2DoubleLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos != Byte2DoubleLinkedOpenHashMap.this.n) {
                byte[] key = Byte2DoubleLinkedOpenHashMap.this.key;
                while (true) {
                    byte curr;
                    int last = pos;
                    pos = last + 1 & Byte2DoubleLinkedOpenHashMap.this.mask;
                    while (true) {
                        if ((curr = key[pos]) == 0) {
                            key[last] = 0;
                            return;
                        }
                        int slot = HashCommon.mix(curr) & Byte2DoubleLinkedOpenHashMap.this.mask;
                        if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                        pos = pos + 1 & Byte2DoubleLinkedOpenHashMap.this.mask;
                    }
                    key[last] = curr;
                    Byte2DoubleLinkedOpenHashMap.this.value[last] = Byte2DoubleLinkedOpenHashMap.this.value[pos];
                    if (this.next == pos) {
                        this.next = last;
                    }
                    if (this.prev == pos) {
                        this.prev = last;
                    }
                    Byte2DoubleLinkedOpenHashMap.this.fixPointers(pos, last);
                }
            }
            Byte2DoubleLinkedOpenHashMap.this.containsNullKey = false;
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
    implements Byte2DoubleMap.Entry,
    Map.Entry<Byte, Double> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Byte getKey() {
            return Byte2DoubleLinkedOpenHashMap.this.key[this.index];
        }

        @Override
        public byte getByteKey() {
            return Byte2DoubleLinkedOpenHashMap.this.key[this.index];
        }

        @Override
        @Deprecated
        public Double getValue() {
            return Byte2DoubleLinkedOpenHashMap.this.value[this.index];
        }

        @Override
        public double getDoubleValue() {
            return Byte2DoubleLinkedOpenHashMap.this.value[this.index];
        }

        @Override
        public double setValue(double v) {
            double oldValue = Byte2DoubleLinkedOpenHashMap.this.value[this.index];
            Byte2DoubleLinkedOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public Double setValue(Double v) {
            return this.setValue((double)v);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Byte2DoubleLinkedOpenHashMap.this.key[this.index] == (Byte)e.getKey() && Byte2DoubleLinkedOpenHashMap.this.value[this.index] == (Double)e.getValue();
        }

        @Override
        public int hashCode() {
            return Byte2DoubleLinkedOpenHashMap.this.key[this.index] ^ HashCommon.double2int(Byte2DoubleLinkedOpenHashMap.this.value[this.index]);
        }

        public String toString() {
            return Byte2DoubleLinkedOpenHashMap.this.key[this.index] + "=>" + Byte2DoubleLinkedOpenHashMap.this.value[this.index];
        }
    }
}

