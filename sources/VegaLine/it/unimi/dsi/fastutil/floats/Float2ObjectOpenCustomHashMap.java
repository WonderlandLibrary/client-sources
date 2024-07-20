/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatHash;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

public class Float2ObjectOpenCustomHashMap<V>
extends AbstractFloat2ObjectMap<V>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected FloatHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Float2ObjectMap.FastEntrySet<V> entries;
    protected transient FloatSet keys;
    protected transient ObjectCollection<V> values;

    public Float2ObjectOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
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
        this.key = new float[this.n + 1];
        this.value = new Object[this.n + 1];
    }

    public Float2ObjectOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
        this(expected, 0.75f, strategy);
    }

    public Float2ObjectOpenCustomHashMap(FloatHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Float2ObjectOpenCustomHashMap(Map<? extends Float, ? extends V> m, float f, FloatHash.Strategy strategy) {
        this(m.size(), f, strategy);
        this.putAll(m);
    }

    public Float2ObjectOpenCustomHashMap(Map<? extends Float, ? extends V> m, FloatHash.Strategy strategy) {
        this(m, 0.75f, strategy);
    }

    public Float2ObjectOpenCustomHashMap(Float2ObjectMap<V> m, float f, FloatHash.Strategy strategy) {
        this(m.size(), f, strategy);
        this.putAll(m);
    }

    public Float2ObjectOpenCustomHashMap(Float2ObjectMap<V> m, FloatHash.Strategy strategy) {
        this(m, 0.75f, strategy);
    }

    public Float2ObjectOpenCustomHashMap(float[] k, V[] v, float f, FloatHash.Strategy strategy) {
        this(k.length, f, strategy);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Float2ObjectOpenCustomHashMap(float[] k, V[] v, FloatHash.Strategy strategy) {
        this(k, v, 0.75f, strategy);
    }

    public FloatHash.Strategy strategy() {
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

    private V removeEntry(int pos) {
        V oldValue = this.value[pos];
        this.value[pos] = null;
        --this.size;
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
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Float, ? extends V> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(float k, V v) {
        int pos;
        if (this.strategy.equals(k, 0.0f)) {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            float[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            float curr = key[pos];
            if (Float.floatToIntBits(curr) != 0) {
                if (this.strategy.equals(curr, k)) {
                    return pos;
                }
                while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
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
    public V put(float k, V v) {
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
    public V put(Float ok, V ov) {
        V v = ov;
        int pos = this.insert(ok.floatValue(), v);
        if (pos < 0) {
            return (V)this.defRetValue;
        }
        V oldValue = this.value[pos];
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
                    this.value[last] = null;
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
    public V remove(float k) {
        if (this.strategy.equals(k, 0.0f)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.removeEntry(pos);
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public V remove(Object ok) {
        float k = ((Float)ok).floatValue();
        if (this.strategy.equals(k, 0.0f)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(curr, k)) {
            return this.removeEntry(pos);
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(curr, k));
        return this.removeEntry(pos);
    }

    @Deprecated
    public V get(Float ok) {
        if (ok == null) {
            return null;
        }
        float k = ok.floatValue();
        if (this.strategy.equals(k, 0.0f)) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        float[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.value[pos];
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.value[pos];
    }

    @Override
    public V get(float k) {
        if (this.strategy.equals(k, 0.0f)) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        float[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.value[pos];
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.value[pos];
    }

    @Override
    public boolean containsKey(float k) {
        if (this.strategy.equals(k, 0.0f)) {
            return this.containsNullKey;
        }
        float[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        float curr = key[pos];
        if (Float.floatToIntBits(curr) == 0) {
            return false;
        }
        if (this.strategy.equals(k, curr)) {
            return true;
        }
        do {
            if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) continue;
            return false;
        } while (!this.strategy.equals(k, curr));
        return true;
    }

    @Override
    public boolean containsValue(Object v) {
        V[] value = this.value;
        float[] key = this.key;
        if (this.containsNullKey && (value[this.n] == null ? v == null : value[this.n].equals(v))) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (Float.floatToIntBits(key[i]) == 0 || !(value[i] == null ? v == null : value[i].equals(v))) continue;
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
        Arrays.fill(this.value, null);
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

    public Float2ObjectMap.FastEntrySet<V> float2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public FloatSet keySet() {
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
                    return Float2ObjectOpenCustomHashMap.this.size;
                }

                @Override
                public boolean contains(Object v) {
                    return Float2ObjectOpenCustomHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Float2ObjectOpenCustomHashMap.this.clear();
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
        V[] value = this.value;
        int mask = newN - 1;
        float[] newKey = new float[newN + 1];
        Object[] newValue = new Object[newN + 1];
        int i = this.n;
        int j = this.realSize();
        while (j-- != 0) {
            while (Float.floatToIntBits(key[--i]) == 0) {
            }
            int pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
            if (Float.floatToIntBits(newKey[pos]) != 0) {
                while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0) {
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

    public Float2ObjectOpenCustomHashMap<V> clone() {
        Float2ObjectOpenCustomHashMap c;
        try {
            c = (Float2ObjectOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (float[])this.key.clone();
        c.value = (Object[])this.value.clone();
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
            while (Float.floatToIntBits(this.key[i]) == 0) {
                ++i;
            }
            t = this.strategy.hashCode(this.key[i]);
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
        float[] key = this.key;
        V[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeFloat(key[e]);
            s.writeObject(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new float[this.n + 1];
        float[] key = this.key;
        this.value = new Object[this.n + 1];
        Object[] value = this.value;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            float k = s.readFloat();
            Object v = s.readObject();
            if (this.strategy.equals(k, 0.0f)) {
                pos = this.n;
                this.containsNullKey = true;
            } else {
                pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
                while (Float.floatToIntBits(key[pos]) != 0) {
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
    implements ObjectIterator<V> {
        @Override
        public V next() {
            return Float2ObjectOpenCustomHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractFloatSet {
        private KeySet() {
        }

        @Override
        public FloatIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Float2ObjectOpenCustomHashMap.this.size;
        }

        @Override
        public boolean contains(float k) {
            return Float2ObjectOpenCustomHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(float k) {
            int oldSize = Float2ObjectOpenCustomHashMap.this.size;
            Float2ObjectOpenCustomHashMap.this.remove(k);
            return Float2ObjectOpenCustomHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Float2ObjectOpenCustomHashMap.this.clear();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements FloatIterator {
        @Override
        public float nextFloat() {
            return Float2ObjectOpenCustomHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Float next() {
            return Float.valueOf(Float2ObjectOpenCustomHashMap.this.key[this.nextEntry()]);
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Float2ObjectMap.Entry<V>>
    implements Float2ObjectMap.FastEntrySet<V> {
        private MapEntrySet() {
        }

        @Override
        public ObjectIterator<Float2ObjectMap.Entry<V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public ObjectIterator<Float2ObjectMap.Entry<V>> fastIterator() {
            return new FastEntryIterator();
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
            float k = ((Float)e.getKey()).floatValue();
            Object v = e.getValue();
            if (Float2ObjectOpenCustomHashMap.this.strategy.equals(k, 0.0f)) {
                return Float2ObjectOpenCustomHashMap.this.containsNullKey && (Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n] == null ? v == null : Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n].equals(v));
            }
            float[] key = Float2ObjectOpenCustomHashMap.this.key;
            int pos = HashCommon.mix(Float2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ObjectOpenCustomHashMap.this.mask;
            float curr = key[pos];
            if (Float.floatToIntBits(curr) == 0) {
                return false;
            }
            if (Float2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
                return Float2ObjectOpenCustomHashMap.this.value[pos] == null ? v == null : Float2ObjectOpenCustomHashMap.this.value[pos].equals(v);
            }
            do {
                if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ObjectOpenCustomHashMap.this.mask]) != 0) continue;
                return false;
            } while (!Float2ObjectOpenCustomHashMap.this.strategy.equals(k, curr));
            return Float2ObjectOpenCustomHashMap.this.value[pos] == null ? v == null : Float2ObjectOpenCustomHashMap.this.value[pos].equals(v);
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
            float k = ((Float)e.getKey()).floatValue();
            Object v = e.getValue();
            if (Float2ObjectOpenCustomHashMap.this.strategy.equals(k, 0.0f)) {
                if (Float2ObjectOpenCustomHashMap.this.containsNullKey && (Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n] == null ? v == null : Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n].equals(v))) {
                    Float2ObjectOpenCustomHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            float[] key = Float2ObjectOpenCustomHashMap.this.key;
            int pos = HashCommon.mix(Float2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ObjectOpenCustomHashMap.this.mask;
            float curr = key[pos];
            if (Float.floatToIntBits(curr) == 0) {
                return false;
            }
            if (Float2ObjectOpenCustomHashMap.this.strategy.equals(curr, k)) {
                if (Float2ObjectOpenCustomHashMap.this.value[pos] == null ? v == null : Float2ObjectOpenCustomHashMap.this.value[pos].equals(v)) {
                    Float2ObjectOpenCustomHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ObjectOpenCustomHashMap.this.mask]) != 0) continue;
                return false;
            } while (!Float2ObjectOpenCustomHashMap.this.strategy.equals(curr, k) || !(Float2ObjectOpenCustomHashMap.this.value[pos] == null ? v == null : Float2ObjectOpenCustomHashMap.this.value[pos].equals(v)));
            Float2ObjectOpenCustomHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Float2ObjectOpenCustomHashMap.this.size;
        }

        @Override
        public void clear() {
            Float2ObjectOpenCustomHashMap.this.clear();
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Float2ObjectMap.Entry<V>> {
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
    implements ObjectIterator<Float2ObjectMap.Entry<V>> {
        private MapEntry entry;

        private EntryIterator() {
        }

        @Override
        public Float2ObjectMap.Entry<V> next() {
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
        FloatArrayList wrapped;

        private MapIterator() {
            this.pos = Float2ObjectOpenCustomHashMap.this.n;
            this.last = -1;
            this.c = Float2ObjectOpenCustomHashMap.this.size;
            this.mustReturnNullKey = Float2ObjectOpenCustomHashMap.this.containsNullKey;
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
                this.last = Float2ObjectOpenCustomHashMap.this.n;
                return this.last;
            }
            float[] key = Float2ObjectOpenCustomHashMap.this.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                float k = this.wrapped.getFloat(-this.pos - 1);
                int p = HashCommon.mix(Float2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ObjectOpenCustomHashMap.this.mask;
                while (!Float2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p])) {
                    p = p + 1 & Float2ObjectOpenCustomHashMap.this.mask;
                }
                return p;
            } while (Float.floatToIntBits(key[this.pos]) == 0);
            this.last = this.pos;
            return this.last;
        }

        private final void shiftKeys(int pos) {
            float[] key = Float2ObjectOpenCustomHashMap.this.key;
            while (true) {
                float curr;
                int last = pos;
                pos = last + 1 & Float2ObjectOpenCustomHashMap.this.mask;
                while (true) {
                    if (Float.floatToIntBits(curr = key[pos]) == 0) {
                        key[last] = 0.0f;
                        Float2ObjectOpenCustomHashMap.this.value[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(Float2ObjectOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2ObjectOpenCustomHashMap.this.mask;
                    if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                    pos = pos + 1 & Float2ObjectOpenCustomHashMap.this.mask;
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new FloatArrayList(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
                Float2ObjectOpenCustomHashMap.this.value[last] = Float2ObjectOpenCustomHashMap.this.value[pos];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == Float2ObjectOpenCustomHashMap.this.n) {
                Float2ObjectOpenCustomHashMap.this.containsNullKey = false;
                Float2ObjectOpenCustomHashMap.this.value[Float2ObjectOpenCustomHashMap.this.n] = null;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                Float2ObjectOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
                this.last = -1;
                return;
            }
            --Float2ObjectOpenCustomHashMap.this.size;
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
    implements Float2ObjectMap.Entry<V>,
    Map.Entry<Float, V> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Float getKey() {
            return Float.valueOf(Float2ObjectOpenCustomHashMap.this.key[this.index]);
        }

        @Override
        public float getFloatKey() {
            return Float2ObjectOpenCustomHashMap.this.key[this.index];
        }

        @Override
        public V getValue() {
            return Float2ObjectOpenCustomHashMap.this.value[this.index];
        }

        @Override
        public V setValue(V v) {
            Object oldValue = Float2ObjectOpenCustomHashMap.this.value[this.index];
            Float2ObjectOpenCustomHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Float2ObjectOpenCustomHashMap.this.strategy.equals(Float2ObjectOpenCustomHashMap.this.key[this.index], ((Float)e.getKey()).floatValue()) && (Float2ObjectOpenCustomHashMap.this.value[this.index] == null ? e.getValue() == null : Float2ObjectOpenCustomHashMap.this.value[this.index].equals(e.getValue()));
        }

        @Override
        public int hashCode() {
            return Float2ObjectOpenCustomHashMap.this.strategy.hashCode(Float2ObjectOpenCustomHashMap.this.key[this.index]) ^ (Float2ObjectOpenCustomHashMap.this.value[this.index] == null ? 0 : Float2ObjectOpenCustomHashMap.this.value[this.index].hashCode());
        }

        public String toString() {
            return Float2ObjectOpenCustomHashMap.this.key[this.index] + "=>" + Float2ObjectOpenCustomHashMap.this.value[this.index];
        }
    }
}

