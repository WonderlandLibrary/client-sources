/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleHash;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

public class Double2ShortOpenCustomHashMap
extends AbstractDouble2ShortMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient double[] key;
    protected transient short[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected DoubleHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected int size;
    protected final float f;
    protected transient Double2ShortMap.FastEntrySet entries;
    protected transient DoubleSet keys;
    protected transient ShortCollection values;

    public Double2ShortOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
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
        this.key = new double[this.n + 1];
        this.value = new short[this.n + 1];
    }

    public Double2ShortOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
        this(expected, 0.75f, strategy);
    }

    public Double2ShortOpenCustomHashMap(DoubleHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Double2ShortOpenCustomHashMap(Map<? extends Double, ? extends Short> m, float f, DoubleHash.Strategy strategy) {
        this(m.size(), f, strategy);
        this.putAll(m);
    }

    public Double2ShortOpenCustomHashMap(Map<? extends Double, ? extends Short> m, DoubleHash.Strategy strategy) {
        this(m, 0.75f, strategy);
    }

    public Double2ShortOpenCustomHashMap(Double2ShortMap m, float f, DoubleHash.Strategy strategy) {
        this(m.size(), f, strategy);
        this.putAll(m);
    }

    public Double2ShortOpenCustomHashMap(Double2ShortMap m, DoubleHash.Strategy strategy) {
        this(m, 0.75f, strategy);
    }

    public Double2ShortOpenCustomHashMap(double[] k, short[] v, float f, DoubleHash.Strategy strategy) {
        this(k.length, f, strategy);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
        }
    }

    public Double2ShortOpenCustomHashMap(double[] k, short[] v, DoubleHash.Strategy strategy) {
        this(k, v, 0.75f, strategy);
    }

    public DoubleHash.Strategy strategy() {
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

    private short removeEntry(int pos) {
        short oldValue = this.value[pos];
        --this.size;
        this.shiftKeys(pos);
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    private short removeNullEntry() {
        this.containsNullKey = false;
        short oldValue = this.value[this.n];
        --this.size;
        if (this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Double, ? extends Short> m) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(m.size());
        } else {
            this.tryCapacity(this.size() + m.size());
        }
        super.putAll(m);
    }

    private int insert(double k, short v) {
        int pos;
        if (this.strategy.equals(k, 0.0)) {
            if (this.containsNullKey) {
                return this.n;
            }
            this.containsNullKey = true;
            pos = this.n;
        } else {
            double[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            double curr = key[pos];
            if (Double.doubleToLongBits(curr) != 0L) {
                if (this.strategy.equals(curr, k)) {
                    return pos;
                }
                while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
    public short put(double k, short v) {
        int pos = this.insert(k, v);
        if (pos < 0) {
            return this.defRetValue;
        }
        short oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override
    @Deprecated
    public Short put(Double ok, Short ov) {
        short v = ov;
        int pos = this.insert(ok, v);
        if (pos < 0) {
            return null;
        }
        short oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    private short addToValue(int pos, short incr) {
        short oldValue = this.value[pos];
        this.value[pos] = (short)(oldValue + incr);
        return oldValue;
    }

    public short addTo(double k, short incr) {
        int pos;
        if (this.strategy.equals(k, 0.0)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, incr);
            }
            pos = this.n;
            this.containsNullKey = true;
        } else {
            double[] key = this.key;
            pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
            double curr = key[pos];
            if (Double.doubleToLongBits(curr) != 0L) {
                if (this.strategy.equals(curr, k)) {
                    return this.addToValue(pos, incr);
                }
                while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
                    if (!this.strategy.equals(curr, k)) continue;
                    return this.addToValue(pos, incr);
                }
            }
        }
        this.key[pos] = k;
        this.value[pos] = (short)(this.defRetValue + incr);
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int pos) {
        double[] key = this.key;
        while (true) {
            double curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
                    key[last] = 0.0;
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
    public short remove(double k) {
        if (this.strategy.equals(k, 0.0)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        double[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        double curr = key[pos];
        if (Double.doubleToLongBits(curr) == 0L) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.removeEntry(pos);
        }
        do {
            if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.removeEntry(pos);
    }

    @Override
    @Deprecated
    public Short remove(Object ok) {
        double k = (Double)ok;
        if (this.strategy.equals(k, 0.0)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return null;
        }
        double[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        double curr = key[pos];
        if (Double.doubleToLongBits(curr) == 0L) {
            return null;
        }
        if (this.strategy.equals(curr, k)) {
            return this.removeEntry(pos);
        }
        do {
            if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return null;
        } while (!this.strategy.equals(curr, k));
        return this.removeEntry(pos);
    }

    @Deprecated
    public Short get(Double ok) {
        if (ok == null) {
            return null;
        }
        double k = ok;
        if (this.strategy.equals(k, 0.0)) {
            return this.containsNullKey ? Short.valueOf(this.value[this.n]) : null;
        }
        double[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        double curr = key[pos];
        if (Double.doubleToLongBits(curr) == 0L) {
            return null;
        }
        if (this.strategy.equals(k, curr)) {
            return this.value[pos];
        }
        do {
            if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return null;
        } while (!this.strategy.equals(k, curr));
        return this.value[pos];
    }

    @Override
    public short get(double k) {
        if (this.strategy.equals(k, 0.0)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        double[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        double curr = key[pos];
        if (Double.doubleToLongBits(curr) == 0L) {
            return this.defRetValue;
        }
        if (this.strategy.equals(k, curr)) {
            return this.value[pos];
        }
        do {
            if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(k, curr));
        return this.value[pos];
    }

    @Override
    public boolean containsKey(double k) {
        if (this.strategy.equals(k, 0.0)) {
            return this.containsNullKey;
        }
        double[] key = this.key;
        int pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
        double curr = key[pos];
        if (Double.doubleToLongBits(curr) == 0L) {
            return false;
        }
        if (this.strategy.equals(k, curr)) {
            return true;
        }
        do {
            if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) continue;
            return false;
        } while (!this.strategy.equals(k, curr));
        return true;
    }

    @Override
    public boolean containsValue(short v) {
        short[] value = this.value;
        double[] key = this.key;
        if (this.containsNullKey && value[this.n] == v) {
            return true;
        }
        int i = this.n;
        while (i-- != 0) {
            if (Double.doubleToLongBits(key[i]) == 0L || value[i] != v) continue;
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
        Arrays.fill(this.key, 0.0);
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

    public Double2ShortMap.FastEntrySet double2ShortEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    @Override
    public DoubleSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public ShortCollection values() {
        if (this.values == null) {
            this.values = new AbstractShortCollection(){

                @Override
                public ShortIterator iterator() {
                    return new ValueIterator();
                }

                @Override
                public int size() {
                    return Double2ShortOpenCustomHashMap.this.size;
                }

                @Override
                public boolean contains(short v) {
                    return Double2ShortOpenCustomHashMap.this.containsValue(v);
                }

                @Override
                public void clear() {
                    Double2ShortOpenCustomHashMap.this.clear();
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
        double[] key = this.key;
        short[] value = this.value;
        int mask = newN - 1;
        double[] newKey = new double[newN + 1];
        short[] newValue = new short[newN + 1];
        int i = this.n;
        int j = this.realSize();
        while (j-- != 0) {
            while (Double.doubleToLongBits(key[--i]) == 0L) {
            }
            int pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
            if (Double.doubleToLongBits(newKey[pos]) != 0L) {
                while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L) {
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

    public Double2ShortOpenCustomHashMap clone() {
        Double2ShortOpenCustomHashMap c;
        try {
            c = (Double2ShortOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.keys = null;
        c.values = null;
        c.entries = null;
        c.containsNullKey = this.containsNullKey;
        c.key = (double[])this.key.clone();
        c.value = (short[])this.value.clone();
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
            while (Double.doubleToLongBits(this.key[i]) == 0L) {
                ++i;
            }
            t = this.strategy.hashCode(this.key[i]);
            h += (t ^= this.value[i]);
            ++i;
        }
        if (this.containsNullKey) {
            h += this.value[this.n];
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        double[] key = this.key;
        short[] value = this.value;
        MapIterator i = new MapIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            int e = i.nextEntry();
            s.writeDouble(key[e]);
            s.writeShort(value[e]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new double[this.n + 1];
        double[] key = this.key;
        this.value = new short[this.n + 1];
        short[] value = this.value;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            double k = s.readDouble();
            short v = s.readShort();
            if (this.strategy.equals(k, 0.0)) {
                pos = this.n;
                this.containsNullKey = true;
            } else {
                pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
                while (Double.doubleToLongBits(key[pos]) != 0L) {
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
    implements ShortIterator {
        @Override
        public short nextShort() {
            return Double2ShortOpenCustomHashMap.this.value[this.nextEntry()];
        }

        @Override
        @Deprecated
        public Short next() {
            return Double2ShortOpenCustomHashMap.this.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractDoubleSet {
        private KeySet() {
        }

        @Override
        public DoubleIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return Double2ShortOpenCustomHashMap.this.size;
        }

        @Override
        public boolean contains(double k) {
            return Double2ShortOpenCustomHashMap.this.containsKey(k);
        }

        @Override
        public boolean rem(double k) {
            int oldSize = Double2ShortOpenCustomHashMap.this.size;
            Double2ShortOpenCustomHashMap.this.remove(k);
            return Double2ShortOpenCustomHashMap.this.size != oldSize;
        }

        @Override
        public void clear() {
            Double2ShortOpenCustomHashMap.this.clear();
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements DoubleIterator {
        @Override
        public double nextDouble() {
            return Double2ShortOpenCustomHashMap.this.key[this.nextEntry()];
        }

        @Override
        public Double next() {
            return Double2ShortOpenCustomHashMap.this.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Double2ShortMap.Entry>
    implements Double2ShortMap.FastEntrySet {
        private MapEntrySet() {
        }

        @Override
        public ObjectIterator<Double2ShortMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override
        public ObjectIterator<Double2ShortMap.Entry> fastIterator() {
            return new FastEntryIterator();
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Double)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Short)) {
                return false;
            }
            double k = (Double)e.getKey();
            short v = (Short)e.getValue();
            if (Double2ShortOpenCustomHashMap.this.strategy.equals(k, 0.0)) {
                return Double2ShortOpenCustomHashMap.this.containsNullKey && Double2ShortOpenCustomHashMap.this.value[Double2ShortOpenCustomHashMap.this.n] == v;
            }
            double[] key = Double2ShortOpenCustomHashMap.this.key;
            int pos = HashCommon.mix(Double2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ShortOpenCustomHashMap.this.mask;
            double curr = key[pos];
            if (Double.doubleToLongBits(curr) == 0L) {
                return false;
            }
            if (Double2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) {
                return Double2ShortOpenCustomHashMap.this.value[pos] == v;
            }
            do {
                if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ShortOpenCustomHashMap.this.mask]) != 0L) continue;
                return false;
            } while (!Double2ShortOpenCustomHashMap.this.strategy.equals(k, curr));
            return Double2ShortOpenCustomHashMap.this.value[pos] == v;
        }

        @Override
        public boolean rem(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Double)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Short)) {
                return false;
            }
            double k = (Double)e.getKey();
            short v = (Short)e.getValue();
            if (Double2ShortOpenCustomHashMap.this.strategy.equals(k, 0.0)) {
                if (Double2ShortOpenCustomHashMap.this.containsNullKey && Double2ShortOpenCustomHashMap.this.value[Double2ShortOpenCustomHashMap.this.n] == v) {
                    Double2ShortOpenCustomHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            double[] key = Double2ShortOpenCustomHashMap.this.key;
            int pos = HashCommon.mix(Double2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ShortOpenCustomHashMap.this.mask;
            double curr = key[pos];
            if (Double.doubleToLongBits(curr) == 0L) {
                return false;
            }
            if (Double2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
                if (Double2ShortOpenCustomHashMap.this.value[pos] == v) {
                    Double2ShortOpenCustomHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            do {
                if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ShortOpenCustomHashMap.this.mask]) != 0L) continue;
                return false;
            } while (!Double2ShortOpenCustomHashMap.this.strategy.equals(curr, k) || Double2ShortOpenCustomHashMap.this.value[pos] != v);
            Double2ShortOpenCustomHashMap.this.removeEntry(pos);
            return true;
        }

        @Override
        public int size() {
            return Double2ShortOpenCustomHashMap.this.size;
        }

        @Override
        public void clear() {
            Double2ShortOpenCustomHashMap.this.clear();
        }
    }

    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Double2ShortMap.Entry> {
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
    implements ObjectIterator<Double2ShortMap.Entry> {
        private MapEntry entry;

        private EntryIterator() {
        }

        @Override
        public Double2ShortMap.Entry next() {
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
        DoubleArrayList wrapped;

        private MapIterator() {
            this.pos = Double2ShortOpenCustomHashMap.this.n;
            this.last = -1;
            this.c = Double2ShortOpenCustomHashMap.this.size;
            this.mustReturnNullKey = Double2ShortOpenCustomHashMap.this.containsNullKey;
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
                this.last = Double2ShortOpenCustomHashMap.this.n;
                return this.last;
            }
            double[] key = Double2ShortOpenCustomHashMap.this.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                double k = this.wrapped.getDouble(-this.pos - 1);
                int p = HashCommon.mix(Double2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Double2ShortOpenCustomHashMap.this.mask;
                while (!Double2ShortOpenCustomHashMap.this.strategy.equals(k, key[p])) {
                    p = p + 1 & Double2ShortOpenCustomHashMap.this.mask;
                }
                return p;
            } while (Double.doubleToLongBits(key[this.pos]) == 0L);
            this.last = this.pos;
            return this.last;
        }

        private final void shiftKeys(int pos) {
            double[] key = Double2ShortOpenCustomHashMap.this.key;
            while (true) {
                double curr;
                int last = pos;
                pos = last + 1 & Double2ShortOpenCustomHashMap.this.mask;
                while (true) {
                    if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
                        key[last] = 0.0;
                        return;
                    }
                    int slot = HashCommon.mix(Double2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2ShortOpenCustomHashMap.this.mask;
                    if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                    pos = pos + 1 & Double2ShortOpenCustomHashMap.this.mask;
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new DoubleArrayList(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
                Double2ShortOpenCustomHashMap.this.value[last] = Double2ShortOpenCustomHashMap.this.value[pos];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == Double2ShortOpenCustomHashMap.this.n) {
                Double2ShortOpenCustomHashMap.this.containsNullKey = false;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                Double2ShortOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
                this.last = -1;
                return;
            }
            --Double2ShortOpenCustomHashMap.this.size;
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
    implements Double2ShortMap.Entry,
    Map.Entry<Double, Short> {
        int index;

        MapEntry(int index) {
            this.index = index;
        }

        MapEntry() {
        }

        @Override
        @Deprecated
        public Double getKey() {
            return Double2ShortOpenCustomHashMap.this.key[this.index];
        }

        @Override
        public double getDoubleKey() {
            return Double2ShortOpenCustomHashMap.this.key[this.index];
        }

        @Override
        @Deprecated
        public Short getValue() {
            return Double2ShortOpenCustomHashMap.this.value[this.index];
        }

        @Override
        public short getShortValue() {
            return Double2ShortOpenCustomHashMap.this.value[this.index];
        }

        @Override
        public short setValue(short v) {
            short oldValue = Double2ShortOpenCustomHashMap.this.value[this.index];
            Double2ShortOpenCustomHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public Short setValue(Short v) {
            return this.setValue((short)v);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Double2ShortOpenCustomHashMap.this.strategy.equals(Double2ShortOpenCustomHashMap.this.key[this.index], (Double)e.getKey()) && Double2ShortOpenCustomHashMap.this.value[this.index] == (Short)e.getValue();
        }

        @Override
        public int hashCode() {
            return Double2ShortOpenCustomHashMap.this.strategy.hashCode(Double2ShortOpenCustomHashMap.this.key[this.index]) ^ Double2ShortOpenCustomHashMap.this.value[this.index];
        }

        public String toString() {
            return Double2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Double2ShortOpenCustomHashMap.this.value[this.index];
        }
    }
}

