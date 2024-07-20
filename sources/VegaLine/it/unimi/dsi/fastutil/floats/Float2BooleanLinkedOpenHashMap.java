/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanSortedMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap;
import it.unimi.dsi.fastutil.floats.Float2BooleanSortedMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
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

public class Float2BooleanLinkedOpenHashMap
extends AbstractFloat2BooleanSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[] key;
    protected transient boolean[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Float2BooleanSortedMap.FastSortedEntrySet entries;
    protected transient FloatSortedSet keys;
    protected transient BooleanCollection values;

    public Float2BooleanLinkedOpenHashMap(int expected, float f) {
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
        this.key = new float[this.n + 1];
        this.value = new boolean[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Float2BooleanLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Float2BooleanLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Float2BooleanLinkedOpenHashMap(Map<? extends Float, ? extends Boolean> m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Float2BooleanLinkedOpenHashMap(Map<? extends Float, ? extends Boolean> m) {
        this(m, 0.75f);
    }

    public Float2BooleanLinkedOpenHashMap(Float2BooleanMap m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Float2BooleanLinkedOpenHashMap(Float2BooleanMap m) {
        this(m, 0.75f);
    }

    public Float2BooleanLinkedOpenHashMap(float[] k, boolean[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Float2BooleanLinkedOpenHashMap(float[] k, boolean[] v) {
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

    private boolean removeEntry(int pos) {
        boolean oldValue = this.value[pos];
        --this.size;
        this.fixPointers(pos);
        this.shiftKeys(pos);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    private boolean removeNullEntry() {
        this.containsNullKey = false;
        boolean oldValue = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Float, ? extends Boolean> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(float k, boolean v) {
        int pos;
        if (Float.floatToIntBits(k) == 0) {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            float[] key = this.key;
            pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
            float curr = key[pos];
            if (Float.floatToIntBits(curr) != 0) {
                if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
                    return pos;
                }
                while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(curr) != Float.floatToIntBits(k)) continue;
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
    public boolean put(float k, boolean v) {
        int pos = this.insert(k, v);
        if (pos < 0) {
            return this.defRetValue;
        }
        boolean oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override
    @Deprecated
    public Boolean put(Float ok, Boolean ov) {
        boolean v = ov;
        int pos = this.insert(ok.floatValue(), v);
        if (pos < 0) {
            return null;
        }
        boolean oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    protected final void shiftKeys(int pos) {
        float[] key = this.key;
        while (true) {
            float curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if (Float.floatToIntBits(curr = key[pos]) == 0) {
                    key[last] = 0.0f;
                    return;
                }
                int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
                if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                pos = pos + 1 & this.mask;
            }
            key[last] = curr;
            this.value[last] = this.value[pos];
            this.fixPointers(pos, last);
        }
    }

    @Override
    public boolean remove(float k) {
        if (Float.floatToIntBits(k) == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
            return this.removeEntry(pos);
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(k) != Float.floatToIntBits(curr));
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public Boolean remove(Object ok) {
        float k = ((Float)ok).floatValue();
        if (Float.floatToIntBits(k) == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return null;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return null;
        }
        if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
            return this.removeEntry(pos);
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return null;
        } while (Float.floatToIntBits(curr) != Float.floatToIntBits(k));
        return this.removeEntry(pos);
    }

    private boolean setValue(int pos, boolean v) {
        boolean oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    public boolean removeFirstBoolean() {
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
        boolean v = this.value[pos];
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

    public boolean removeLastBoolean() {
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
        boolean v = this.value[pos];
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

    public boolean getAndMoveToFirst(float k) {
        if (Float.floatToIntBits(k) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(k) != Float.floatToIntBits(curr));
        this.moveIndexToFirst(pos);
        return this.value[pos];
    }

    public boolean getAndMoveToLast(float k) {
        if (Float.floatToIntBits(k) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
            this.moveIndexToLast(pos);
            return this.value[pos];
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(k) != Float.floatToIntBits(curr));
        this.moveIndexToLast(pos);
        return this.value[pos];
    }

    public boolean putAndMoveToFirst(float k, boolean v) {
        int pos;
        if (Float.floatToIntBits(k) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            float[] key = this.key;
            pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
            float curr = key[pos];
            if (Float.floatToIntBits(curr) != 0) {
                if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
                    this.moveIndexToFirst(pos);
                    return this.setValue(pos, v);
                }
                while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(curr) != Float.floatToIntBits(k)) continue;
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

    public boolean putAndMoveToLast(float k, boolean v) {
        int pos;
        if (Float.floatToIntBits(k) == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            float[] key = this.key;
            pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
            float curr = key[pos];
            if (Float.floatToIntBits(curr) != 0) {
                if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
                    this.moveIndexToLast(pos);
                    return this.setValue(pos, v);
                }
                while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(curr) != Float.floatToIntBits(k)) continue;
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
    public Boolean get(Float ok) {
        if (ok == null) {
            return null;
        }
        float k = ok.floatValue();
        if (Float.floatToIntBits(k) == 0) {
            return this.containsNullKey ? Boolean.valueOf(this.value[this.n]) : null;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return null;
        }
        if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
            return this.value[pos];
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return null;
        } while (Float.floatToIntBits(k) != Float.floatToIntBits(curr));
        return this.value[pos];
    }

    @Override
    public boolean get(float k) {
        if (Float.floatToIntBits(k) == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
            return this.value[pos];
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(k) != Float.floatToIntBits(curr));
        return this.value[pos];
    }

    @Override
    public boolean containsKey(float k) {
        if (Float.floatToIntBits(k) == 0) {
            return this.containsNullKey;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return false;
        }
        if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
            return true;
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return false;
        } while (Float.floatToIntBits(k) != Float.floatToIntBits(curr));
        return true;
    }

    @Override
    public boolean containsValue(boolean v) {
        boolean[] value = this.value;
        float[] key = this.key;
        if (this.containsNullKey && value[this.n] == v) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (Float.floatToIntBits(key[i]) == 0 || value[i] != v) continue;
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
    public FloatComparator comparator() {
        return null;
    }

    @Override
    public Float2BooleanSortedMap tailMap(float from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float2BooleanSortedMap headMap(float to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Float2BooleanSortedMap subMap(float from, float to) {
        throw new UnsupportedOperationException();
    }

    public Float2BooleanSortedMap.FastSortedEntrySet float2BooleanEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public FloatSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public BooleanCollection values() {
        if (this.values == null) {
            this.values = new AbstractBooleanCollection(){

                @Override
                public BooleanIterator iterator() {
                    return new ValueIterator();
                }

                @Override
                public int size() {
                    return Float2BooleanLinkedOpenHashMap.this.size;
                }

                @Override
                public boolean contains(boolean v) {
                    return Float2BooleanLinkedOpenHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Float2BooleanLinkedOpenHashMap.this.clear();
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
        float[] key = this.key;
        boolean[] value = this.value;
        int mask = newN - 1;
        float[] newKey = new float[newN + 1];
        boolean[] newValue = new boolean[newN + 1];
        int i = this.first;
        int prev = -1;
        int newPrev = -1;
        long[] link = this.link;
        long[] newLink = new long[newN + 1];
        this.first = -1;
        int j = this.size;
        while (j-- != 0) {
            int pos;
            if (Float.floatToIntBits(key[i]) == 0) {
                pos = newN;
            } else {
                pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
                while (Float.floatToIntBits(newKey[pos]) != 0) {
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

    public Float2BooleanLinkedOpenHashMap clone() {
        Float2BooleanLinkedOpenHashMap c;
        try {
            c = (Float2BooleanLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (float[])this.key.clone();
        c.value = (boolean[])this.value.clone();
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
            while (Float.floatToIntBits(this.key[i]) == 0) {
                ++i;
            }
            t = HashCommon.float2int(this.key[i]);
            h += (t ^= this.value[i] ? 1231 : 1237);
            ++i;
        }
        if (this.containsNullKey) {
            h += this.value[this.n] ? 1231 : 1237;
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        float[] key = this.key;
        boolean[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeFloat(key[e]);
            s.writeBoolean(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new float[this.n + 1];
        float[] key = this.key;
        this.value = new boolean[this.n + 1];
        boolean[] value = this.value;
        this.link = new long[this.n + 1];
        long[] link = this.link;
        int prev = -1;
        this.last = -1;
        this.first = -1;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            float k = s.readFloat();
            boolean v = s.readBoolean();
            if (Float.floatToIntBits(k) == 0) {
                pos = this.n;
                this.containsNullKey = true;
            } else {
                pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
                while (Float.floatToIntBits(key[pos]) != 0) {
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
    implements BooleanListIterator {
        @Override
        public boolean previousBoolean() {
            return Float2BooleanLinkedOpenHashMap.this.value[this.previousEntry()];
        }

        @Override
        public Boolean previous() {
            return Float2BooleanLinkedOpenHashMap.this.value[this.previousEntry()];
        }

        @Override
        public void set(Boolean ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Boolean ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(boolean v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(boolean v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean nextBoolean() {
            return Float2BooleanLinkedOpenHashMap.this.value[this.nextEntry()];
        }

        @Override
        @Deprecated
        public Boolean next() {
            return Float2BooleanLinkedOpenHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractFloatSortedSet {
        private KeySet() {
        }

        @Override
        public FloatListIterator iterator(float from) {
            return new KeyIterator(from);
        }

        @Override
        public FloatListIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Float2BooleanLinkedOpenHashMap.this.size;
        }

        @Override
        public boolean contains(float k) {
            return Float2BooleanLinkedOpenHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(float k) {
            int oldSize = Float2BooleanLinkedOpenHashMap.this.size;
            Float2BooleanLinkedOpenHashMap.this.remove(k);
            return Float2BooleanLinkedOpenHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Float2BooleanLinkedOpenHashMap.this.clear();
        }

        @Override
        public float firstFloat() {
            if (Float2BooleanLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.first];
        }

        @Override
        public float lastFloat() {
            if (Float2BooleanLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.last];
        }

        @Override
        public FloatComparator comparator() {
            return null;
        }

        @Override
        public final FloatSortedSet tailSet(float from) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final FloatSortedSet headSet(float to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final FloatSortedSet subSet(float from, float to) {
            throw new UnsupportedOperationException();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements FloatListIterator {
        public KeyIterator(float k) {
            super(k);
        }

        @Override
        public float previousFloat() {
            return Float2BooleanLinkedOpenHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(float k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Float previous() {
            return Float.valueOf(Float2BooleanLinkedOpenHashMap.this.key[this.previousEntry()]);
        }

        @Override
        public void set(Float ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Float ok) {
            throw new UnsupportedOperationException();
        }

        public KeyIterator() {
        }

        @Override
        public float nextFloat() {
            return Float2BooleanLinkedOpenHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Float next() {
            return Float.valueOf(Float2BooleanLinkedOpenHashMap.this.key[this.nextEntry()]);
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSortedSet<Float2BooleanMap.Entry>
    implements Float2BooleanSortedMap.FastSortedEntrySet {
        private MapEntrySet() {
        }

        @Override
        public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override
        public Comparator<? super Float2BooleanMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Float2BooleanMap.Entry> subSet(Float2BooleanMap.Entry fromElement, Float2BooleanMap.Entry toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Float2BooleanMap.Entry> headSet(Float2BooleanMap.Entry toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Float2BooleanMap.Entry> tailSet(Float2BooleanMap.Entry fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Float2BooleanMap.Entry first() {
            if (Float2BooleanLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Float2BooleanLinkedOpenHashMap.this.first);
        }

        @Override
        public Float2BooleanMap.Entry last() {
            if (Float2BooleanLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Float2BooleanLinkedOpenHashMap.this.last);
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Boolean)) {
                return false;
            }
            float k = ((Float)e.getKey()).floatValue();
            boolean v = (Boolean)e.getValue();
            if (Float.floatToIntBits(k) == 0) {
                return Float2BooleanLinkedOpenHashMap.this.containsNullKey && Float2BooleanLinkedOpenHashMap.this.value[Float2BooleanLinkedOpenHashMap.this.n] == v;
            }
            float[] key = Float2BooleanLinkedOpenHashMap.this.key;
            int pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanLinkedOpenHashMap.this.mask;
            float curr = key[pos];
            if (Float.floatToIntBits(curr) == 0) {
                return false;
            }
            if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
                return Float2BooleanLinkedOpenHashMap.this.value[pos] == v;
            }
            do {
                if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask]) != 0) continue;
                return false;
            } while (Float.floatToIntBits(k) != Float.floatToIntBits(curr));
            return Float2BooleanLinkedOpenHashMap.this.value[pos] == v;
        }

        @Override
        public boolean rem(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Boolean)) {
                return false;
            }
            float k = ((Float)e.getKey()).floatValue();
            boolean v = (Boolean)e.getValue();
            if (Float.floatToIntBits(k) == 0) {
                if (Float2BooleanLinkedOpenHashMap.this.containsNullKey && Float2BooleanLinkedOpenHashMap.this.value[Float2BooleanLinkedOpenHashMap.this.n] == v) {
                    Float2BooleanLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            float[] key = Float2BooleanLinkedOpenHashMap.this.key;
            int pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanLinkedOpenHashMap.this.mask;
            float curr = key[pos];
            if (Float.floatToIntBits(curr) == 0) {
                return false;
            }
            if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
                if (Float2BooleanLinkedOpenHashMap.this.value[pos] == v) {
                    Float2BooleanLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask]) != 0) continue;
                return false;
            } while (Float.floatToIntBits(curr) != Float.floatToIntBits(k) || Float2BooleanLinkedOpenHashMap.this.value[pos] != v);
            Float2BooleanLinkedOpenHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Float2BooleanLinkedOpenHashMap.this.size;
        }

        @Override
        public void clear() {
            Float2BooleanLinkedOpenHashMap.this.clear();
        }

        @Override
        public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator(Float2BooleanMap.Entry from) {
            return new EntryIterator(from.getFloatKey());
        }

        public ObjectBidirectionalIterator<Float2BooleanMap.Entry> fastIterator() {
            return new FastEntryIterator();
        }

        @Override
        public ObjectBidirectionalIterator<Float2BooleanMap.Entry> fastIterator(Float2BooleanMap.Entry from) {
            return new FastEntryIterator(from.getFloatKey());
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Float2BooleanMap.Entry> {
        final MapEntry entry;

        public FastEntryIterator() {
            this.entry = new MapEntry();
        }

        public FastEntryIterator(float from) {
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
        public void set(Float2BooleanMap.Entry ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Float2BooleanMap.Entry ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class EntryIterator
    extends MapIterator
    implements ObjectListIterator<Float2BooleanMap.Entry> {
        private MapEntry entry;

        public EntryIterator() {
        }

        public EntryIterator(float from) {
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
        public void set(Float2BooleanMap.Entry ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Float2BooleanMap.Entry ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class MapIterator {
        int prev = -1;
        int next = -1;
        int curr = -1;
        int index = -1;

        private MapIterator() {
            this.next = Float2BooleanLinkedOpenHashMap.this.first;
            this.index = 0;
        }

        private MapIterator(float from) {
            if (Float.floatToIntBits(from) == 0) {
                if (Float2BooleanLinkedOpenHashMap.this.containsNullKey) {
                    this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[Float2BooleanLinkedOpenHashMap.this.n];
                    this.prev = Float2BooleanLinkedOpenHashMap.this.n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
            if (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
                this.prev = Float2BooleanLinkedOpenHashMap.this.last;
                this.index = Float2BooleanLinkedOpenHashMap.this.size;
                return;
            }
            int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2BooleanLinkedOpenHashMap.this.mask;
            while (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[pos]) != 0) {
                if (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
                    this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[pos];
                    this.prev = pos;
                    return;
                }
                pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask;
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
                this.index = Float2BooleanLinkedOpenHashMap.this.size;
                return;
            }
            int pos = Float2BooleanLinkedOpenHashMap.this.first;
            this.index = 1;
            while (pos != this.prev) {
                pos = (int)Float2BooleanLinkedOpenHashMap.this.link[pos];
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
            this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[this.curr];
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
            this.prev = (int)(Float2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
                this.prev = (int)(Float2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
                this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[this.curr];
            }
            --Float2BooleanLinkedOpenHashMap.this.size;
            if (this.prev == -1) {
                Float2BooleanLinkedOpenHashMap.this.first = this.next;
            } else {
                int n = this.prev;
                Float2BooleanLinkedOpenHashMap.this.link[n] = Float2BooleanLinkedOpenHashMap.this.link[n] ^ (Float2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            if (this.next == -1) {
                Float2BooleanLinkedOpenHashMap.this.last = this.prev;
            } else {
                int n = this.next;
                Float2BooleanLinkedOpenHashMap.this.link[n] = Float2BooleanLinkedOpenHashMap.this.link[n] ^ (Float2BooleanLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos != Float2BooleanLinkedOpenHashMap.this.n) {
                float[] key = Float2BooleanLinkedOpenHashMap.this.key;
                while (true) {
                    float curr;
                    int last = pos;
                    pos = last + 1 & Float2BooleanLinkedOpenHashMap.this.mask;
                    while (true) {
                        if (Float.floatToIntBits(curr = key[pos]) == 0) {
                            key[last] = 0.0f;
                            return;
                        }
                        int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2BooleanLinkedOpenHashMap.this.mask;
                        if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                        pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask;
                    }
                    key[last] = curr;
                    Float2BooleanLinkedOpenHashMap.this.value[last] = Float2BooleanLinkedOpenHashMap.this.value[pos];
                    if (this.next == pos) {
                        this.next = last;
                    }
                    if (this.prev == pos) {
                        this.prev = last;
                    }
                    Float2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
                }
            }
            Float2BooleanLinkedOpenHashMap.this.containsNullKey = false;
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
    implements Float2BooleanMap.Entry,
    Map.Entry<Float, Boolean> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Float getKey() {
            return Float.valueOf(Float2BooleanLinkedOpenHashMap.this.key[this.index]);
        }

        @Override
        public float getFloatKey() {
            return Float2BooleanLinkedOpenHashMap.this.key[this.index];
        }

        @Override
        @Deprecated
        public Boolean getValue() {
            return Float2BooleanLinkedOpenHashMap.this.value[this.index];
        }

        @Override
        public boolean getBooleanValue() {
            return Float2BooleanLinkedOpenHashMap.this.value[this.index];
        }

        @Override
        public boolean setValue(boolean v) {
            boolean oldValue = Float2BooleanLinkedOpenHashMap.this.value[this.index];
            Float2BooleanLinkedOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public Boolean setValue(Boolean v) {
            return this.setValue((boolean)v);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2BooleanLinkedOpenHashMap.this.value[this.index] == (Boolean)e.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(Float2BooleanLinkedOpenHashMap.this.key[this.index]) ^ (Float2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
        }

        public String toString() {
            return Float2BooleanLinkedOpenHashMap.this.key[this.index] + "=>" + Float2BooleanLinkedOpenHashMap.this.value[this.index];
        }
    }
}

