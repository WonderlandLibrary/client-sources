/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2LongMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortHash;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

public class Short2LongOpenCustomHashMap
extends AbstractShort2LongMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient short[] key;
    protected transient long[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected ShortHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Short2LongMap.FastEntrySet entries;
    protected transient ShortSet keys;
    protected transient LongCollection values;

    public Short2LongOpenCustomHashMap(int expected, float f, ShortHash.Strategy strategy) {
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
        this.key = new short[this.n + 1];
        this.value = new long[this.n + 1];
    }

    public Short2LongOpenCustomHashMap(int expected, ShortHash.Strategy strategy) {
        this(expected, 0.75f, strategy);
    }

    public Short2LongOpenCustomHashMap(ShortHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Short2LongOpenCustomHashMap(Map<? extends Short, ? extends Long> m, float f, ShortHash.Strategy strategy) {
        this(m.size(), f, strategy);
        this.putAll(m);
    }

    public Short2LongOpenCustomHashMap(Map<? extends Short, ? extends Long> m, ShortHash.Strategy strategy) {
        this(m, 0.75f, strategy);
    }

    public Short2LongOpenCustomHashMap(Short2LongMap m, float f, ShortHash.Strategy strategy) {
        this(m.size(), f, strategy);
        this.putAll(m);
    }

    public Short2LongOpenCustomHashMap(Short2LongMap m, ShortHash.Strategy strategy) {
        this(m, 0.75f, strategy);
    }

    public Short2LongOpenCustomHashMap(short[] k, long[] v, float f, ShortHash.Strategy strategy) {
        this(k.length, f, strategy);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Short2LongOpenCustomHashMap(short[] k, long[] v, ShortHash.Strategy strategy) {
        this(k, v, 0.75f, strategy);
    }

    public ShortHash.Strategy strategy() {
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

    private long removeEntry(int pos) {
        long oldValue = this.value[pos];
        --this.size;
        this.shiftKeys(pos);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    private long removeNullEntry() {
        this.containsNullKey = false;
        long oldValue = this.value[this.n];
        --this.size;
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Short, ? extends Long> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(short k, long v) {
        int pos;
        if (this.strategy.equals(k, (short)0)) {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            short[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            short curr = key[pos];
            if (curr != 0) {
                if (this.strategy.equals(curr, k)) {
                    return pos;
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(curr, k)) continue;
                    return pos;
                }
            }
        }
        this.key[pos] = k;
        this.value[pos] = v;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return -1;
    }

    @Override
    public long put(short k, long v) {
        int pos = this.insert(k, v);
        if (pos < 0) {
            return this.defRetValue;
        }
        long oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override
    @Deprecated
    public Long put(Short ok, Long ov) {
        long v = ov;
        int pos = this.insert(ok, v);
        if (pos < 0) {
            return null;
        }
        long oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    private long addToValue(int pos, long incr) {
        long oldValue = this.value[pos];
        this.value[pos] = oldValue + incr;
        return oldValue;
    }

    public long addTo(short k, long incr) {
        int pos;
        if (this.strategy.equals(k, (short)0)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, incr);
            }
            pos = this.n;
            this.containsNullKey = true;
        } else {
            short[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            short curr = key[pos];
            if (curr != 0) {
                if (this.strategy.equals(curr, k)) {
                    return this.addToValue(pos, incr);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(curr, k)) continue;
                    return this.addToValue(pos, incr);
                }
            }
        }
        this.key[pos] = k;
        this.value[pos] = this.defRetValue + incr;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
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
                    return;
                }
                int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
                if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                pos = pos + 1 & this.mask;
            }
            key[last] = curr;
            this.value[last] = this.value[pos];
        }
    }

    @Override
    public long remove(short k) {
        if (this.strategy.equals(k, (short)0)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public Long remove(Object ok) {
        short k = (Short)ok;
        if (this.strategy.equals(k, (short)0)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return null;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return null;
        }
        if (this.strategy.equals(curr, k)) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return null;
        } while (!this.strategy.equals(curr, k));
        return this.removeEntry(pos);
    }

    @Deprecated
    public Long get(Short ok) {
        if (ok == null) {
            return null;
        }
        short k = ok;
        if (this.strategy.equals(k, (short)0)) {
            return this.containsNullKey ? Long.valueOf(this.value[this.n]) : null;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return null;
        }
        if (this.strategy.equals(k, curr)) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return null;
        } while (!this.strategy.equals(k, curr));
        return this.value[pos];
    }

    @Override
    public long get(short k) {
        if (this.strategy.equals(k, (short)0)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.value[pos];
    }

    @Override
    public boolean containsKey(short k) {
        if (this.strategy.equals(k, (short)0)) {
            return this.containsNullKey;
        }
        short[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        short curr = key[pos];
        if (curr == 0) {
            return false;
        }
        if (this.strategy.equals(k, curr)) {
            return true;
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return false;
        } while (!this.strategy.equals(k, curr));
        return true;
    }

    @Override
    public boolean containsValue(long v) {
        long[] value = this.value;
        short[] key = this.key;
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
        Arrays.fill(this.key, (short)0);
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

    public Short2LongMap.FastEntrySet short2LongEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public ShortSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public LongCollection values() {
        if (this.values == null) {
            this.values = new AbstractLongCollection(){

                @Override
                public LongIterator iterator() {
                    return new ValueIterator();
                }

                @Override
                public int size() {
                    return Short2LongOpenCustomHashMap.this.size;
                }

                @Override
                public boolean contains(long v) {
                    return Short2LongOpenCustomHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Short2LongOpenCustomHashMap.this.clear();
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
        long[] value = this.value;
        int mask = newN - 1;
        short[] newKey = new short[newN + 1];
        long[] newValue = new long[newN + 1];
        int i = this.n;
        int j = this.realSize();
        while (j-- != 0) {
            while (key[--i] == 0) {
            }
            int pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
            if (newKey[pos] != 0) {
                while (newKey[pos = pos + 1 & mask] != 0) {
                }
            }
            newKey[pos] = key[i];
            newValue[pos] = value[i];
        }
        newValue[newN] = value[this.n];
        this.n = newN;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = newKey;
        this.value = newValue;
    }

    public Short2LongOpenCustomHashMap clone() {
        Short2LongOpenCustomHashMap c;
        try {
            c = (Short2LongOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (short[])this.key.clone();
        c.value = (long[])this.value.clone();
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
            while (this.key[i] == 0) {
                ++i;
            }
            t = this.strategy.hashCode(this.key[i]);
            h += (t ^= HashCommon.long2int(this.value[i]));
            ++i;
        }
        if (this.containsNullKey) {
            h += HashCommon.long2int(this.value[this.n]);
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        short[] key = this.key;
        long[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeShort(key[e]);
            s.writeLong(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new short[this.n + 1];
        short[] key = this.key;
        this.value = new long[this.n + 1];
        long[] value = this.value;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            short k = s.readShort();
            long v = s.readLong();
            if (this.strategy.equals(k, (short)0)) {
                pos = this.n;
                this.containsNullKey = true;
            } else {
                pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
                while (key[pos] != 0) {
                    pos = pos + 1 & this.mask;
                }
            }
            key[pos] = k;
            value[pos] = v;
        }
    }

    private void checkTable() {
    }

    private final class ValueIterator
    extends MapIterator
    implements LongIterator {
        @Override
        public long nextLong() {
            return Short2LongOpenCustomHashMap.this.value[this.nextEntry()];
        }

        @Override
        @Deprecated
        public Long next() {
            return Short2LongOpenCustomHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractShortSet {
        private KeySet() {
        }

        @Override
        public ShortIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Short2LongOpenCustomHashMap.this.size;
        }

        @Override
        public boolean contains(short k) {
            return Short2LongOpenCustomHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(short k) {
            int oldSize = Short2LongOpenCustomHashMap.this.size;
            Short2LongOpenCustomHashMap.this.remove(k);
            return Short2LongOpenCustomHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Short2LongOpenCustomHashMap.this.clear();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ShortIterator {
        @Override
        public short nextShort() {
            return Short2LongOpenCustomHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Short next() {
            return Short2LongOpenCustomHashMap.this.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Short2LongMap.Entry>
    implements Short2LongMap.FastEntrySet {
        private MapEntrySet() {
        }

        @Override
        public ObjectIterator<Short2LongMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override
        public ObjectIterator<Short2LongMap.Entry> fastIterator() {
            return new FastEntryIterator();
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
            if (e.getValue() == null || !(e.getValue() instanceof Long)) {
                return false;
            }
            short k = (Short)e.getKey();
            long v = (Long)e.getValue();
            if (Short2LongOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
                return Short2LongOpenCustomHashMap.this.containsNullKey && Short2LongOpenCustomHashMap.this.value[Short2LongOpenCustomHashMap.this.n] == v;
            }
            short[] key = Short2LongOpenCustomHashMap.this.key;
            int pos = HashCommon.mix(Short2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Short2LongOpenCustomHashMap.this.mask;
            short curr = key[pos];
            if (curr == 0) {
                return false;
            }
            if (Short2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
                return Short2LongOpenCustomHashMap.this.value[pos] == v;
            }
            do {
                if ((curr = key[pos = pos + 1 & Short2LongOpenCustomHashMap.this.mask]) != 0) continue;
                return false;
            } while (!Short2LongOpenCustomHashMap.this.strategy.equals(k, curr));
            return Short2LongOpenCustomHashMap.this.value[pos] == v;
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
            if (e.getValue() == null || !(e.getValue() instanceof Long)) {
                return false;
            }
            short k = (Short)e.getKey();
            long v = (Long)e.getValue();
            if (Short2LongOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
                if (Short2LongOpenCustomHashMap.this.containsNullKey && Short2LongOpenCustomHashMap.this.value[Short2LongOpenCustomHashMap.this.n] == v) {
                    Short2LongOpenCustomHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            short[] key = Short2LongOpenCustomHashMap.this.key;
            int pos = HashCommon.mix(Short2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Short2LongOpenCustomHashMap.this.mask;
            short curr = key[pos];
            if (curr == 0) {
                return false;
            }
            if (Short2LongOpenCustomHashMap.this.strategy.equals(curr, k)) {
                if (Short2LongOpenCustomHashMap.this.value[pos] == v) {
                    Short2LongOpenCustomHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if ((curr = key[pos = pos + 1 & Short2LongOpenCustomHashMap.this.mask]) != 0) continue;
                return false;
            } while (!Short2LongOpenCustomHashMap.this.strategy.equals(curr, k) || Short2LongOpenCustomHashMap.this.value[pos] != v);
            Short2LongOpenCustomHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Short2LongOpenCustomHashMap.this.size;
        }

        @Override
        public void clear() {
            Short2LongOpenCustomHashMap.this.clear();
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Short2LongMap.Entry> {
        private final MapEntry entry;

        private FastEntryIterator() {
            this.entry = new MapEntry();
        }

        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }
    }

    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Short2LongMap.Entry> {
        private MapEntry entry;

        private EntryIterator() {
        }

        @Override
        public Short2LongMap.Entry next() {
            this.entry = new MapEntry(this.nextEntry());
            return this.entry;
        }

        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ShortArrayList wrapped;

        private MapIterator() {
            this.pos = Short2LongOpenCustomHashMap.this.n;
            this.last = -1;
            this.c = Short2LongOpenCustomHashMap.this.size;
            this.mustReturnNullKey = Short2LongOpenCustomHashMap.this.containsNullKey;
        }

        public boolean hasNext() {
            return this.c != 0;
        }

        public int nextEntry() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                this.last = Short2LongOpenCustomHashMap.this.n;
                return this.last;
            }
            short[] key = Short2LongOpenCustomHashMap.this.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                short k = this.wrapped.getShort(-this.pos - 1);
                int p = HashCommon.mix(Short2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Short2LongOpenCustomHashMap.this.mask;
                while (!Short2LongOpenCustomHashMap.this.strategy.equals(k, key[p])) {
                    p = p + 1 & Short2LongOpenCustomHashMap.this.mask;
                }
                return p;
            } while (key[this.pos] == 0);
            this.last = this.pos;
            return this.last;
        }

        private final void shiftKeys(int pos) {
            short[] key = Short2LongOpenCustomHashMap.this.key;
            while (true) {
                short curr;
                int last = pos;
                pos = last + 1 & Short2LongOpenCustomHashMap.this.mask;
                while (true) {
                    if ((curr = key[pos]) == 0) {
                        key[last] = 0;
                        return;
                    }
                    int slot = HashCommon.mix(Short2LongOpenCustomHashMap.this.strategy.hashCode(curr)) & Short2LongOpenCustomHashMap.this.mask;
                    if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                    pos = pos + 1 & Short2LongOpenCustomHashMap.this.mask;
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new ShortArrayList(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
                Short2LongOpenCustomHashMap.this.value[last] = Short2LongOpenCustomHashMap.this.value[pos];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == Short2LongOpenCustomHashMap.this.n) {
                Short2LongOpenCustomHashMap.this.containsNullKey = false;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                Short2LongOpenCustomHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
                this.last = -1;
                return;
            }
            --Short2LongOpenCustomHashMap.this.size;
            this.last = -1;
        }

        public int skip(int n) {
            int i = n;
            while (i-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - i - 1;
        }
    }

    final class MapEntry
    implements Short2LongMap.Entry,
    Map.Entry<Short, Long> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Short getKey() {
            return Short2LongOpenCustomHashMap.this.key[this.index];
        }

        @Override
        public short getShortKey() {
            return Short2LongOpenCustomHashMap.this.key[this.index];
        }

        @Override
        @Deprecated
        public Long getValue() {
            return Short2LongOpenCustomHashMap.this.value[this.index];
        }

        @Override
        public long getLongValue() {
            return Short2LongOpenCustomHashMap.this.value[this.index];
        }

        @Override
        public long setValue(long v) {
            long oldValue = Short2LongOpenCustomHashMap.this.value[this.index];
            Short2LongOpenCustomHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public Long setValue(Long v) {
            return this.setValue((long)v);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Short2LongOpenCustomHashMap.this.strategy.equals(Short2LongOpenCustomHashMap.this.key[this.index], (Short)e.getKey()) && Short2LongOpenCustomHashMap.this.value[this.index] == (Long)e.getValue();
        }

        @Override
        public int hashCode() {
            return Short2LongOpenCustomHashMap.this.strategy.hashCode(Short2LongOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Short2LongOpenCustomHashMap.this.value[this.index]);
        }

        public String toString() {
            return Short2LongOpenCustomHashMap.this.key[this.index] + "=>" + Short2LongOpenCustomHashMap.this.value[this.index];
        }
    }
}

