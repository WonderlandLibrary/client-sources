/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2IntSortedMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntSortedMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
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

public class Char2IntLinkedOpenHashMap
extends AbstractChar2IntSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient char[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Char2IntSortedMap.FastSortedEntrySet entries;
    protected transient CharSortedSet keys;
    protected transient IntCollection values;

    public Char2IntLinkedOpenHashMap(int expected, float f) {
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
        this.key = new char[this.n + 1];
        this.value = new int[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Char2IntLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Char2IntLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Char2IntLinkedOpenHashMap(Map<? extends Character, ? extends Integer> m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Char2IntLinkedOpenHashMap(Map<? extends Character, ? extends Integer> m) {
        this(m, 0.75f);
    }

    public Char2IntLinkedOpenHashMap(Char2IntMap m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Char2IntLinkedOpenHashMap(Char2IntMap m) {
        this(m, 0.75f);
    }

    public Char2IntLinkedOpenHashMap(char[] k, int[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Char2IntLinkedOpenHashMap(char[] k, int[] v) {
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
        int oldValue = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Character, ? extends Integer> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(char k, int v) {
        int pos;
        if (k == '\u0000') {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            char[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            char curr = key[pos];
            if (curr != '\u0000') {
                if (curr == k) {
                    return pos;
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') {
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
    public int put(char k, int v) {
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
    public Integer put(Character ok, Integer ov) {
        int v = ov;
        int pos = this.insert(ok.charValue(), v);
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

    public int addTo(char k, int incr) {
        int pos;
        if (k == '\u0000') {
            if (this.containsNullKey) {
                return this.addToValue(this.n, incr);
            }
            pos = this.n;
            this.containsNullKey = true;
        } else {
            char[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            char curr = key[pos];
            if (curr != '\u0000') {
                if (curr == k) {
                    return this.addToValue(pos, incr);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') {
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
        char[] key = this.key;
        while (true) {
            char curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if ((curr = key[pos]) == '\u0000') {
                    key[last] = '\u0000';
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
    public int remove(char k) {
        if (k == '\u0000') {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        char[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        char curr = key[pos];
        if (curr == '\u0000') {
            return this.defRetValue;
        }
        if (k == curr) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') continue;
            return this.defRetValue;
        } while (k != curr);
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public Integer remove(Object ok) {
        char k = ((Character)ok).charValue();
        if (k == '\u0000') {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return null;
        }
        char[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        char curr = key[pos];
        if (curr == '\u0000') {
            return null;
        }
        if (curr == k) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') continue;
            return null;
        } while (curr != k);
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

    public int getAndMoveToFirst(char k) {
        if (k == '\u0000') {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        char[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        char curr = key[pos];
        if (curr == '\u0000') {
            return this.defRetValue;
        }
        if (k == curr) {
            this.moveIndexToFirst(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') continue;
            return this.defRetValue;
        } while (k != curr);
        this.moveIndexToFirst(pos);
        return this.value[pos];
    }

    public int getAndMoveToLast(char k) {
        if (k == '\u0000') {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        char[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        char curr = key[pos];
        if (curr == '\u0000') {
            return this.defRetValue;
        }
        if (k == curr) {
            this.moveIndexToLast(pos);
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') continue;
            return this.defRetValue;
        } while (k != curr);
        this.moveIndexToLast(pos);
        return this.value[pos];
    }

    public int putAndMoveToFirst(char k, int v) {
        int pos;
        if (k == '\u0000') {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            char[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            char curr = key[pos];
            if (curr != '\u0000') {
                if (curr == k) {
                    this.moveIndexToFirst(pos);
                    return this.setValue(pos, v);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') {
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

    public int putAndMoveToLast(char k, int v) {
        int pos;
        if (k == '\u0000') {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, v);
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            char[] key = this.key;
            pos = HashCommon.mix(k) & this.mask;
            char curr = key[pos];
            if (curr != '\u0000') {
                if (curr == k) {
                    this.moveIndexToLast(pos);
                    return this.setValue(pos, v);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') {
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
    public Integer get(Character ok) {
        if (ok == null) {
            return null;
        }
        char k = ok.charValue();
        if (k == '\u0000') {
            return this.containsNullKey ? Integer.valueOf(this.value[this.n]) : null;
        }
        char[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        char curr = key[pos];
        if (curr == '\u0000') {
            return null;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') continue;
            return null;
        } while (k != curr);
        return this.value[pos];
    }

    @Override
    public int get(char k) {
        if (k == '\u0000') {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        char[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        char curr = key[pos];
        if (curr == '\u0000') {
            return this.defRetValue;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') continue;
            return this.defRetValue;
        } while (k != curr);
        return this.value[pos];
    }

    @Override
    public boolean containsKey(char k) {
        if (k == '\u0000') {
            return this.containsNullKey;
        }
        char[] key = this.key;
        int pos = HashCommon.mix(k) & this.mask;
        char curr = key[pos];
        if (curr == '\u0000') {
            return false;
        }
        if (k == curr) {
            return true;
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != '\u0000') continue;
            return false;
        } while (k != curr);
        return true;
    }

    @Override
    public boolean containsValue(int v) {
        int[] value = this.value;
        char[] key = this.key;
        if (this.containsNullKey && value[this.n] == v) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (key[i] == '\u0000' || value[i] != v) continue;
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
        Arrays.fill(this.key, '\u0000');
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
    public char firstCharKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public char lastCharKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public CharComparator comparator() {
        return null;
    }

    @Override
    public Char2IntSortedMap tailMap(char from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Char2IntSortedMap headMap(char to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Char2IntSortedMap subMap(char from, char to) {
        throw new UnsupportedOperationException();
    }

    public Char2IntSortedMap.FastSortedEntrySet char2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public CharSortedSet keySet() {
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
                    return Char2IntLinkedOpenHashMap.this.size;
                }

                @Override
                public boolean contains(int v) {
                    return Char2IntLinkedOpenHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Char2IntLinkedOpenHashMap.this.clear();
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
        char[] key = this.key;
        int[] value = this.value;
        int mask = newN - 1;
        char[] newKey = new char[newN + 1];
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
            if (key[i] == '\u0000') {
                pos = newN;
            } else {
                pos = HashCommon.mix(key[i]) & mask;
                while (newKey[pos] != '\u0000') {
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

    public Char2IntLinkedOpenHashMap clone() {
        Char2IntLinkedOpenHashMap c;
        try {
            c = (Char2IntLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (char[])this.key.clone();
        c.value = (int[])this.value.clone();
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
            while (this.key[i] == '\u0000') {
                ++i;
            }
            t = this.key[i];
            h += (t ^= this.value[i]);
            ++i;
        }
        if (this.containsNullKey) {
            h += this.value[this.n];
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        char[] key = this.key;
        int[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeChar(key[e]);
            s.writeInt(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new char[this.n + 1];
        char[] key = this.key;
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
            char k = s.readChar();
            int v = s.readInt();
            if (k == '\u0000') {
                pos = this.n;
                this.containsNullKey = true;
            } else {
                pos = HashCommon.mix(k) & this.mask;
                while (key[pos] != '\u0000') {
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
            return Char2IntLinkedOpenHashMap.this.value[this.previousEntry()];
        }

        @Override
        public Integer previous() {
            return Char2IntLinkedOpenHashMap.this.value[this.previousEntry()];
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
            return Char2IntLinkedOpenHashMap.this.value[this.nextEntry()];
        }

        @Override
        @Deprecated
        public Integer next() {
            return Char2IntLinkedOpenHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractCharSortedSet {
        private KeySet() {
        }

        @Override
        public CharListIterator iterator(char from) {
            return new KeyIterator(from);
        }

        @Override
        public CharListIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Char2IntLinkedOpenHashMap.this.size;
        }

        @Override
        public boolean contains(char k) {
            return Char2IntLinkedOpenHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(char k) {
            int oldSize = Char2IntLinkedOpenHashMap.this.size;
            Char2IntLinkedOpenHashMap.this.remove(k);
            return Char2IntLinkedOpenHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Char2IntLinkedOpenHashMap.this.clear();
        }

        @Override
        public char firstChar() {
            if (Char2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Char2IntLinkedOpenHashMap.this.key[Char2IntLinkedOpenHashMap.this.first];
        }

        @Override
        public char lastChar() {
            if (Char2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Char2IntLinkedOpenHashMap.this.key[Char2IntLinkedOpenHashMap.this.last];
        }

        @Override
        public CharComparator comparator() {
            return null;
        }

        @Override
        public final CharSortedSet tailSet(char from) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final CharSortedSet headSet(char to) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final CharSortedSet subSet(char from, char to) {
            throw new UnsupportedOperationException();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements CharListIterator {
        public KeyIterator(char k) {
            super(k);
        }

        @Override
        public char previousChar() {
            return Char2IntLinkedOpenHashMap.this.key[this.previousEntry()];
        }

        @Override
        public void set(char k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(char k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Character previous() {
            return Character.valueOf(Char2IntLinkedOpenHashMap.this.key[this.previousEntry()]);
        }

        @Override
        public void set(Character ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Character ok) {
            throw new UnsupportedOperationException();
        }

        public KeyIterator() {
        }

        @Override
        public char nextChar() {
            return Char2IntLinkedOpenHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Character next() {
            return Character.valueOf(Char2IntLinkedOpenHashMap.this.key[this.nextEntry()]);
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSortedSet<Char2IntMap.Entry>
    implements Char2IntSortedMap.FastSortedEntrySet {
        private MapEntrySet() {
        }

        @Override
        public ObjectBidirectionalIterator<Char2IntMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override
        public Comparator<? super Char2IntMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Char2IntMap.Entry> subSet(Char2IntMap.Entry fromElement, Char2IntMap.Entry toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Char2IntMap.Entry> headSet(Char2IntMap.Entry toElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Char2IntMap.Entry> tailSet(Char2IntMap.Entry fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Char2IntMap.Entry first() {
            if (Char2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Char2IntLinkedOpenHashMap.this.first);
        }

        @Override
        public Char2IntMap.Entry last() {
            if (Char2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Char2IntLinkedOpenHashMap.this.last);
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Character)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            char k = ((Character)e.getKey()).charValue();
            int v = (Integer)e.getValue();
            if (k == '\u0000') {
                return Char2IntLinkedOpenHashMap.this.containsNullKey && Char2IntLinkedOpenHashMap.this.value[Char2IntLinkedOpenHashMap.this.n] == v;
            }
            char[] key = Char2IntLinkedOpenHashMap.this.key;
            int pos = HashCommon.mix(k) & Char2IntLinkedOpenHashMap.this.mask;
            char curr = key[pos];
            if (curr == '\u0000') {
                return false;
            }
            if (k == curr) {
                return Char2IntLinkedOpenHashMap.this.value[pos] == v;
            }
            do {
                if ((curr = key[pos = pos + 1 & Char2IntLinkedOpenHashMap.this.mask]) != '\u0000') continue;
                return false;
            } while (k != curr);
            return Char2IntLinkedOpenHashMap.this.value[pos] == v;
        }

        @Override
        public boolean rem(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Character)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            char k = ((Character)e.getKey()).charValue();
            int v = (Integer)e.getValue();
            if (k == '\u0000') {
                if (Char2IntLinkedOpenHashMap.this.containsNullKey && Char2IntLinkedOpenHashMap.this.value[Char2IntLinkedOpenHashMap.this.n] == v) {
                    Char2IntLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            char[] key = Char2IntLinkedOpenHashMap.this.key;
            int pos = HashCommon.mix(k) & Char2IntLinkedOpenHashMap.this.mask;
            char curr = key[pos];
            if (curr == '\u0000') {
                return false;
            }
            if (curr == k) {
                if (Char2IntLinkedOpenHashMap.this.value[pos] == v) {
                    Char2IntLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if ((curr = key[pos = pos + 1 & Char2IntLinkedOpenHashMap.this.mask]) != '\u0000') continue;
                return false;
            } while (curr != k || Char2IntLinkedOpenHashMap.this.value[pos] != v);
            Char2IntLinkedOpenHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Char2IntLinkedOpenHashMap.this.size;
        }

        @Override
        public void clear() {
            Char2IntLinkedOpenHashMap.this.clear();
        }

        @Override
        public ObjectBidirectionalIterator<Char2IntMap.Entry> iterator(Char2IntMap.Entry from) {
            return new EntryIterator(from.getCharKey());
        }

        public ObjectBidirectionalIterator<Char2IntMap.Entry> fastIterator() {
            return new FastEntryIterator();
        }

        @Override
        public ObjectBidirectionalIterator<Char2IntMap.Entry> fastIterator(Char2IntMap.Entry from) {
            return new FastEntryIterator(from.getCharKey());
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Char2IntMap.Entry> {
        final MapEntry entry;

        public FastEntryIterator() {
            this.entry = new MapEntry();
        }

        public FastEntryIterator(char from) {
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
        public void set(Char2IntMap.Entry ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Char2IntMap.Entry ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class EntryIterator
    extends MapIterator
    implements ObjectListIterator<Char2IntMap.Entry> {
        private MapEntry entry;

        public EntryIterator() {
        }

        public EntryIterator(char from) {
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
        public void set(Char2IntMap.Entry ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Char2IntMap.Entry ok) {
            throw new UnsupportedOperationException();
        }
    }

    private class MapIterator {
        int prev = -1;
        int next = -1;
        int curr = -1;
        int index = -1;

        private MapIterator() {
            this.next = Char2IntLinkedOpenHashMap.this.first;
            this.index = 0;
        }

        private MapIterator(char from) {
            if (from == '\u0000') {
                if (Char2IntLinkedOpenHashMap.this.containsNullKey) {
                    this.next = (int)Char2IntLinkedOpenHashMap.this.link[Char2IntLinkedOpenHashMap.this.n];
                    this.prev = Char2IntLinkedOpenHashMap.this.n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            }
            if (Char2IntLinkedOpenHashMap.this.key[Char2IntLinkedOpenHashMap.this.last] == from) {
                this.prev = Char2IntLinkedOpenHashMap.this.last;
                this.index = Char2IntLinkedOpenHashMap.this.size;
                return;
            }
            int pos = HashCommon.mix(from) & Char2IntLinkedOpenHashMap.this.mask;
            while (Char2IntLinkedOpenHashMap.this.key[pos] != '\u0000') {
                if (Char2IntLinkedOpenHashMap.this.key[pos] == from) {
                    this.next = (int)Char2IntLinkedOpenHashMap.this.link[pos];
                    this.prev = pos;
                    return;
                }
                pos = pos + 1 & Char2IntLinkedOpenHashMap.this.mask;
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
                this.index = Char2IntLinkedOpenHashMap.this.size;
                return;
            }
            int pos = Char2IntLinkedOpenHashMap.this.first;
            this.index = 1;
            while (pos != this.prev) {
                pos = (int)Char2IntLinkedOpenHashMap.this.link[pos];
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
            this.next = (int)Char2IntLinkedOpenHashMap.this.link[this.curr];
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
            this.prev = (int)(Char2IntLinkedOpenHashMap.this.link[this.curr] >>> 32);
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
                this.prev = (int)(Char2IntLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
                this.next = (int)Char2IntLinkedOpenHashMap.this.link[this.curr];
            }
            --Char2IntLinkedOpenHashMap.this.size;
            if (this.prev == -1) {
                Char2IntLinkedOpenHashMap.this.first = this.next;
            } else {
                int n = this.prev;
                Char2IntLinkedOpenHashMap.this.link[n] = Char2IntLinkedOpenHashMap.this.link[n] ^ (Char2IntLinkedOpenHashMap.this.link[this.prev] ^ (long)this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            if (this.next == -1) {
                Char2IntLinkedOpenHashMap.this.last = this.prev;
            } else {
                int n = this.next;
                Char2IntLinkedOpenHashMap.this.link[n] = Char2IntLinkedOpenHashMap.this.link[n] ^ (Char2IntLinkedOpenHashMap.this.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos != Char2IntLinkedOpenHashMap.this.n) {
                char[] key = Char2IntLinkedOpenHashMap.this.key;
                while (true) {
                    char curr;
                    int last = pos;
                    pos = last + 1 & Char2IntLinkedOpenHashMap.this.mask;
                    while (true) {
                        if ((curr = key[pos]) == '\u0000') {
                            key[last] = '\u0000';
                            return;
                        }
                        int slot = HashCommon.mix(curr) & Char2IntLinkedOpenHashMap.this.mask;
                        if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                        pos = pos + 1 & Char2IntLinkedOpenHashMap.this.mask;
                    }
                    key[last] = curr;
                    Char2IntLinkedOpenHashMap.this.value[last] = Char2IntLinkedOpenHashMap.this.value[pos];
                    if (this.next == pos) {
                        this.next = last;
                    }
                    if (this.prev == pos) {
                        this.prev = last;
                    }
                    Char2IntLinkedOpenHashMap.this.fixPointers(pos, last);
                }
            }
            Char2IntLinkedOpenHashMap.this.containsNullKey = false;
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
    implements Char2IntMap.Entry,
    Map.Entry<Character, Integer> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Character getKey() {
            return Character.valueOf(Char2IntLinkedOpenHashMap.this.key[this.index]);
        }

        @Override
        public char getCharKey() {
            return Char2IntLinkedOpenHashMap.this.key[this.index];
        }

        @Override
        @Deprecated
        public Integer getValue() {
            return Char2IntLinkedOpenHashMap.this.value[this.index];
        }

        @Override
        public int getIntValue() {
            return Char2IntLinkedOpenHashMap.this.value[this.index];
        }

        @Override
        public int setValue(int v) {
            int oldValue = Char2IntLinkedOpenHashMap.this.value[this.index];
            Char2IntLinkedOpenHashMap.this.value[this.index] = v;
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
            return Char2IntLinkedOpenHashMap.this.key[this.index] == ((Character)e.getKey()).charValue() && Char2IntLinkedOpenHashMap.this.value[this.index] == (Integer)e.getValue();
        }

        @Override
        public int hashCode() {
            return Char2IntLinkedOpenHashMap.this.key[this.index] ^ Char2IntLinkedOpenHashMap.this.value[this.index];
        }

        public String toString() {
            return Char2IntLinkedOpenHashMap.this.key[this.index] + "=>" + Char2IntLinkedOpenHashMap.this.value[this.index];
        }
    }
}

