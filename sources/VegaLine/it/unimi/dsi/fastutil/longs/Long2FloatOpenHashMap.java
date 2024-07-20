/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2FloatMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

public class Long2FloatOpenHashMap
extends AbstractLong2FloatMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient float[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Long2FloatMap.FastEntrySet entries;
    protected transient LongSet keys;
    protected transient FloatCollection values;

    public Long2FloatOpenHashMap(int expected, float f) {
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
        this.value = new float[this.n + 1];
    }

    public Long2FloatOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Long2FloatOpenHashMap() {
        this(16, 0.75f);
    }

    public Long2FloatOpenHashMap(Map<? extends Long, ? extends Float> m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Long2FloatOpenHashMap(Map<? extends Long, ? extends Float> m) {
        this(m, 0.75f);
    }

    public Long2FloatOpenHashMap(Long2FloatMap m, float f) {
        this(m.size(), f);
        this.putAll(m);
    }

    public Long2FloatOpenHashMap(Long2FloatMap m) {
        this(m, 0.75f);
    }

    public Long2FloatOpenHashMap(long[] k, float[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Long2FloatOpenHashMap(long[] k, float[] v) {
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

    private float removeEntry(int pos) {
        float oldValue = this.value[pos];
        --this.size;
        this.shiftKeys(pos);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    private float removeNullEntry() {
        this.containsNullKey = false;
        float oldValue = this.value[this.n];
        --this.size;
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Float> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(long k, float v) {
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
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return -1;
    }

    @Override
    public float put(long k, float v) {
        int pos = this.insert(k, v);
        if (pos < 0) {
            return this.defRetValue;
        }
        float oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override
    @Deprecated
    public Float put(Long ok, Float ov) {
        float v = ov.floatValue();
        int pos = this.insert(ok, v);
        if (pos < 0) {
            return null;
        }
        float oldValue = this.value[pos];
        this.value[pos] = v;
        return Float.valueOf(oldValue);
    }

    private float addToValue(int pos, float incr) {
        float oldValue = this.value[pos];
        this.value[pos] = oldValue + incr;
        return oldValue;
    }

    public float addTo(long k, float incr) {
        int pos;
        if (k == 0L) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, incr);
            }
            pos = this.n;
            this.containsNullKey = true;
        } else {
            long[] key = this.key;
            pos = (int)HashCommon.mix(k) & this.mask;
            long curr = key[pos];
            if (curr != 0L) {
                if (curr == k) {
                    return this.addToValue(pos, incr);
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
                    if (curr != k) continue;
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
        long[] key = this.key;
        while (true) {
            long curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if ((curr = key[pos]) == 0L) {
                    key[last] = 0L;
                    return;
                }
                int slot = (int)HashCommon.mix(curr) & this.mask;
                if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                pos = pos + 1 & this.mask;
            }
            key[last] = curr;
            this.value[last] = this.value[pos];
        }
    }

    @Override
    public float remove(long k) {
        if (k == 0L) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return this.defRetValue;
        }
        if (k == curr) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (k != curr);
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public Float remove(Object ok) {
        long k = (Long)ok;
        if (k == 0L) {
            if (this.containsNullKey) {
                return Float.valueOf(this.removeNullEntry());
            }
            return null;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return null;
        }
        if (curr == k) {
            return Float.valueOf(this.removeEntry(pos));
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return null;
        } while (curr != k);
        return Float.valueOf(this.removeEntry(pos));
    }

    @Deprecated
    public Float get(Long ok) {
        if (ok == null) {
            return null;
        }
        long k = ok;
        if (k == 0L) {
            return this.containsNullKey ? Float.valueOf(this.value[this.n]) : null;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return null;
        }
        if (k == curr) {
            return Float.valueOf(this.value[pos]);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return null;
        } while (k != curr);
        return Float.valueOf(this.value[pos]);
    }

    @Override
    public float get(long k) {
        if (k == 0L) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        long[] key = this.key;
        int pos = (int)HashCommon.mix(k) & this.mask;
        long curr = key[pos];
        if (curr == 0L) {
            return this.defRetValue;
        }
        if (k == curr) {
            return this.value[pos];
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
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
    public boolean containsValue(float v) {
        float[] value = this.value;
        long[] key = this.key;
        if (this.containsNullKey && value[this.n] == v) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (key[i] == 0L || value[i] != v) continue;
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

    public Long2FloatMap.FastEntrySet long2FloatEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public LongSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public FloatCollection values() {
        if (this.values == null) {
            this.values = new AbstractFloatCollection(){

                @Override
                public FloatIterator iterator() {
                    return new ValueIterator();
                }

                @Override
                public int size() {
                    return Long2FloatOpenHashMap.this.size;
                }

                @Override
                public boolean contains(float v) {
                    return Long2FloatOpenHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Long2FloatOpenHashMap.this.clear();
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
        float[] value = this.value;
        int mask = newN - 1;
        long[] newKey = new long[newN + 1];
        float[] newValue = new float[newN + 1];
        int i = this.n;
        int j = this.realSize();
        while (j-- != 0) {
            while (key[--i] == 0L) {
            }
            int pos = (int)HashCommon.mix(key[i]) & mask;
            if (newKey[pos] != 0L) {
                while (newKey[pos = pos + 1 & mask] != 0L) {
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

    public Long2FloatOpenHashMap clone() {
        Long2FloatOpenHashMap c;
        try {
            c = (Long2FloatOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (long[])this.key.clone();
        c.value = (float[])this.value.clone();
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
            h += (t ^= HashCommon.float2int(this.value[i]));
            ++i;
        }
        if (this.containsNullKey) {
            h += HashCommon.float2int(this.value[this.n]);
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        long[] key = this.key;
        float[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeLong(key[e]);
            s.writeFloat(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new long[this.n + 1];
        long[] key = this.key;
        this.value = new float[this.n + 1];
        float[] value = this.value;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            long k = s.readLong();
            float v = s.readFloat();
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
        }
    }

    private void checkTable() {
    }

    private final class ValueIterator
    extends MapIterator
    implements FloatIterator {
        @Override
        public float nextFloat() {
            return Long2FloatOpenHashMap.this.value[this.nextEntry()];
        }

        @Override
        @Deprecated
        public Float next() {
            return Float.valueOf(Long2FloatOpenHashMap.this.value[this.nextEntry()]);
        }
    }

    private final class KeySet
    extends AbstractLongSet {
        private KeySet() {
        }

        @Override
        public LongIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Long2FloatOpenHashMap.this.size;
        }

        @Override
        public boolean contains(long k) {
            return Long2FloatOpenHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(long k) {
            int oldSize = Long2FloatOpenHashMap.this.size;
            Long2FloatOpenHashMap.this.remove(k);
            return Long2FloatOpenHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Long2FloatOpenHashMap.this.clear();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongIterator {
        @Override
        public long nextLong() {
            return Long2FloatOpenHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Long next() {
            return Long2FloatOpenHashMap.this.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Long2FloatMap.Entry>
    implements Long2FloatMap.FastEntrySet {
        private MapEntrySet() {
        }

        @Override
        public ObjectIterator<Long2FloatMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override
        public ObjectIterator<Long2FloatMap.Entry> fastIterator() {
            return new FastEntryIterator();
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
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            long k = (Long)e.getKey();
            float v = ((Float)e.getValue()).floatValue();
            if (k == 0L) {
                return Long2FloatOpenHashMap.this.containsNullKey && Long2FloatOpenHashMap.this.value[Long2FloatOpenHashMap.this.n] == v;
            }
            long[] key = Long2FloatOpenHashMap.this.key;
            int pos = (int)HashCommon.mix(k) & Long2FloatOpenHashMap.this.mask;
            long curr = key[pos];
            if (curr == 0L) {
                return false;
            }
            if (k == curr) {
                return Long2FloatOpenHashMap.this.value[pos] == v;
            }
            do {
                if ((curr = key[pos = pos + 1 & Long2FloatOpenHashMap.this.mask]) != 0L) continue;
                return false;
            } while (k != curr);
            return Long2FloatOpenHashMap.this.value[pos] == v;
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
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            long k = (Long)e.getKey();
            float v = ((Float)e.getValue()).floatValue();
            if (k == 0L) {
                if (Long2FloatOpenHashMap.this.containsNullKey && Long2FloatOpenHashMap.this.value[Long2FloatOpenHashMap.this.n] == v) {
                    Long2FloatOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            long[] key = Long2FloatOpenHashMap.this.key;
            int pos = (int)HashCommon.mix(k) & Long2FloatOpenHashMap.this.mask;
            long curr = key[pos];
            if (curr == 0L) {
                return false;
            }
            if (curr == k) {
                if (Long2FloatOpenHashMap.this.value[pos] == v) {
                    Long2FloatOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if ((curr = key[pos = pos + 1 & Long2FloatOpenHashMap.this.mask]) != 0L) continue;
                return false;
            } while (curr != k || Long2FloatOpenHashMap.this.value[pos] != v);
            Long2FloatOpenHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Long2FloatOpenHashMap.this.size;
        }

        @Override
        public void clear() {
            Long2FloatOpenHashMap.this.clear();
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Long2FloatMap.Entry> {
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
    implements ObjectIterator<Long2FloatMap.Entry> {
        private MapEntry entry;

        private EntryIterator() {
        }

        @Override
        public Long2FloatMap.Entry next() {
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
        LongArrayList wrapped;

        private MapIterator() {
            this.pos = Long2FloatOpenHashMap.this.n;
            this.last = -1;
            this.c = Long2FloatOpenHashMap.this.size;
            this.mustReturnNullKey = Long2FloatOpenHashMap.this.containsNullKey;
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
                this.last = Long2FloatOpenHashMap.this.n;
                return this.last;
            }
            long[] key = Long2FloatOpenHashMap.this.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                long k = this.wrapped.getLong(-this.pos - 1);
                int p = (int)HashCommon.mix(k) & Long2FloatOpenHashMap.this.mask;
                while (k != key[p]) {
                    p = p + 1 & Long2FloatOpenHashMap.this.mask;
                }
                return p;
            } while (key[this.pos] == 0L);
            this.last = this.pos;
            return this.last;
        }

        private final void shiftKeys(int pos) {
            long[] key = Long2FloatOpenHashMap.this.key;
            while (true) {
                long curr;
                int last = pos;
                pos = last + 1 & Long2FloatOpenHashMap.this.mask;
                while (true) {
                    if ((curr = key[pos]) == 0L) {
                        key[last] = 0L;
                        return;
                    }
                    int slot = (int)HashCommon.mix(curr) & Long2FloatOpenHashMap.this.mask;
                    if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                    pos = pos + 1 & Long2FloatOpenHashMap.this.mask;
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new LongArrayList(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
                Long2FloatOpenHashMap.this.value[last] = Long2FloatOpenHashMap.this.value[pos];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == Long2FloatOpenHashMap.this.n) {
                Long2FloatOpenHashMap.this.containsNullKey = false;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                Long2FloatOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
                this.last = -1;
                return;
            }
            --Long2FloatOpenHashMap.this.size;
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
    implements Long2FloatMap.Entry,
    Map.Entry<Long, Float> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Long getKey() {
            return Long2FloatOpenHashMap.this.key[this.index];
        }

        @Override
        public long getLongKey() {
            return Long2FloatOpenHashMap.this.key[this.index];
        }

        @Override
        @Deprecated
        public Float getValue() {
            return Float.valueOf(Long2FloatOpenHashMap.this.value[this.index]);
        }

        @Override
        public float getFloatValue() {
            return Long2FloatOpenHashMap.this.value[this.index];
        }

        @Override
        public float setValue(float v) {
            float oldValue = Long2FloatOpenHashMap.this.value[this.index];
            Long2FloatOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public Float setValue(Float v) {
            return Float.valueOf(this.setValue(v.floatValue()));
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Long2FloatOpenHashMap.this.key[this.index] == (Long)e.getKey() && Long2FloatOpenHashMap.this.value[this.index] == ((Float)e.getValue()).floatValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(Long2FloatOpenHashMap.this.key[this.index]) ^ HashCommon.float2int(Long2FloatOpenHashMap.this.value[this.index]);
        }

        public String toString() {
            return Long2FloatOpenHashMap.this.key[this.index] + "=>" + Long2FloatOpenHashMap.this.value[this.index];
        }
    }
}

