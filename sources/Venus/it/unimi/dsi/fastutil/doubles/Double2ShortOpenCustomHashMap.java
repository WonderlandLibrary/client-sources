/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleHash;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Double2ShortMap.FastEntrySet entries;
    protected transient DoubleSet keys;
    protected transient ShortCollection values;

    public Double2ShortOpenCustomHashMap(int n, float f, DoubleHash.Strategy strategy) {
        this.strategy = strategy;
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (n < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.arraySize(n, f);
        this.mask = this.n - 1;
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = new double[this.n + 1];
        this.value = new short[this.n + 1];
    }

    public Double2ShortOpenCustomHashMap(int n, DoubleHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Double2ShortOpenCustomHashMap(DoubleHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Double2ShortOpenCustomHashMap(Map<? extends Double, ? extends Short> map, float f, DoubleHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Double2ShortOpenCustomHashMap(Map<? extends Double, ? extends Short> map, DoubleHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Double2ShortOpenCustomHashMap(Double2ShortMap double2ShortMap, float f, DoubleHash.Strategy strategy) {
        this(double2ShortMap.size(), f, strategy);
        this.putAll(double2ShortMap);
    }

    public Double2ShortOpenCustomHashMap(Double2ShortMap double2ShortMap, DoubleHash.Strategy strategy) {
        this(double2ShortMap, 0.75f, strategy);
    }

    public Double2ShortOpenCustomHashMap(double[] dArray, short[] sArray, float f, DoubleHash.Strategy strategy) {
        this(dArray.length, f, strategy);
        if (dArray.length != sArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + dArray.length + " and " + sArray.length + ")");
        }
        for (int i = 0; i < dArray.length; ++i) {
            this.put(dArray[i], sArray[i]);
        }
    }

    public Double2ShortOpenCustomHashMap(double[] dArray, short[] sArray, DoubleHash.Strategy strategy) {
        this(dArray, sArray, 0.75f, strategy);
    }

    public DoubleHash.Strategy strategy() {
        return this.strategy;
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int n) {
        int n2 = HashCommon.arraySize(n, this.f);
        if (n2 > this.n) {
            this.rehash(n2);
        }
    }

    private void tryCapacity(long l) {
        int n = (int)Math.min(0x40000000L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)l / this.f))));
        if (n > this.n) {
            this.rehash(n);
        }
    }

    private short removeEntry(int n) {
        short s = this.value[n];
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return s;
    }

    private short removeNullEntry() {
        this.containsNullKey = false;
        short s = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return s;
    }

    @Override
    public void putAll(Map<? extends Double, ? extends Short> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(double d) {
        if (this.strategy.equals(d, 0.0)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        double[] dArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return -(n + 1);
        }
        if (this.strategy.equals(d, d2)) {
            return n;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return -(n + 1);
        } while (!this.strategy.equals(d, d2));
        return n;
    }

    private void insert(int n, double d, short s) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = d;
        this.value[n] = s;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public short put(double d, short s) {
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, s);
            return this.defRetValue;
        }
        short s2 = this.value[n];
        this.value[n] = s;
        return s2;
    }

    private short addToValue(int n, short s) {
        short s2 = this.value[n];
        this.value[n] = (short)(s2 + s);
        return s2;
    }

    public short addTo(double d, short s) {
        int n;
        if (this.strategy.equals(d, 0.0)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, s);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            double[] dArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) != 0L) {
                if (this.strategy.equals(d2, d)) {
                    return this.addToValue(n, s);
                }
                while (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) {
                    if (!this.strategy.equals(d2, d)) continue;
                    return this.addToValue(n, s);
                }
            }
        }
        this.key[n] = d;
        this.value[n] = (short)(this.defRetValue + s);
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int n) {
        double[] dArray = this.key;
        while (true) {
            double d;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if (Double.doubleToLongBits(d = dArray[n]) == 0L) {
                    dArray[n2] = 0.0;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            dArray[n2] = d;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public short remove(double d) {
        if (this.strategy.equals(d, 0.0)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        double[] dArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return this.defRetValue;
        }
        if (this.strategy.equals(d, d2)) {
            return this.removeEntry(n);
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(d, d2));
        return this.removeEntry(n);
    }

    @Override
    public short get(double d) {
        if (this.strategy.equals(d, 0.0)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        double[] dArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return this.defRetValue;
        }
        if (this.strategy.equals(d, d2)) {
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(d, d2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(double d) {
        if (this.strategy.equals(d, 0.0)) {
            return this.containsNullKey;
        }
        double[] dArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return true;
        }
        if (this.strategy.equals(d, d2)) {
            return false;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(d, d2));
        return false;
    }

    @Override
    public boolean containsValue(short s) {
        short[] sArray = this.value;
        double[] dArray = this.key;
        if (this.containsNullKey && sArray[this.n] == s) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray[n]) == 0L || sArray[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public short getOrDefault(double d, short s) {
        if (this.strategy.equals(d, 0.0)) {
            return this.containsNullKey ? this.value[this.n] : s;
        }
        double[] dArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return s;
        }
        if (this.strategy.equals(d, d2)) {
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return s;
        } while (!this.strategy.equals(d, d2));
        return this.value[n];
    }

    @Override
    public short putIfAbsent(double d, short s) {
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, d, s);
        return this.defRetValue;
    }

    @Override
    public boolean remove(double d, short s) {
        if (this.strategy.equals(d, 0.0)) {
            if (this.containsNullKey && s == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        double[] dArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return true;
        }
        if (this.strategy.equals(d, d2) && s == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(d, d2) || s != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(double d, short s, short s2) {
        int n = this.find(d);
        if (n < 0 || s != this.value[n]) {
            return true;
        }
        this.value[n] = s2;
        return false;
    }

    @Override
    public short replace(double d, short s) {
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        short s2 = this.value[n];
        this.value[n] = s;
        return s2;
    }

    @Override
    public short computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        short s = SafeMath.safeIntToShort(doubleToIntFunction.applyAsInt(d));
        this.insert(-n - 1, d, s);
        return s;
    }

    @Override
    public short computeIfAbsentNullable(double d, DoubleFunction<? extends Short> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        Short s = doubleFunction.apply(d);
        if (s == null) {
            return this.defRetValue;
        }
        short s2 = s;
        this.insert(-n - 1, d, s2);
        return s2;
    }

    @Override
    public short computeIfPresent(double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        Short s = biFunction.apply((Double)d, (Short)this.value[n]);
        if (s == null) {
            if (this.strategy.equals(d, 0.0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = s;
        return this.value[n];
    }

    @Override
    public short compute(double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        Short s = biFunction.apply((Double)d, n >= 0 ? Short.valueOf(this.value[n]) : null);
        if (s == null) {
            if (n >= 0) {
                if (this.strategy.equals(d, 0.0)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        short s2 = s;
        if (n < 0) {
            this.insert(-n - 1, d, s2);
            return s2;
        }
        this.value[n] = s2;
        return this.value[n];
    }

    @Override
    public short merge(double d, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, s);
            return s;
        }
        Short s2 = biFunction.apply((Short)this.value[n], (Short)s);
        if (s2 == null) {
            if (this.strategy.equals(d, 0.0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = s2;
        return this.value[n];
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

    public Double2ShortMap.FastEntrySet double2ShortEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public DoubleSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ShortCollection values() {
        if (this.values == null) {
            this.values = new AbstractShortCollection(this){
                final Double2ShortOpenCustomHashMap this$0;
                {
                    this.this$0 = double2ShortOpenCustomHashMap;
                }

                @Override
                public ShortIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(short s) {
                    return this.this$0.containsValue(s);
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public void forEach(IntConsumer intConsumer) {
                    if (this.this$0.containsNullKey) {
                        intConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (Double.doubleToLongBits(this.this$0.key[n]) == 0L) continue;
                        intConsumer.accept(this.this$0.value[n]);
                    }
                }

                @Override
                public Iterator iterator() {
                    return this.iterator();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        int n = HashCommon.arraySize(this.size, this.f);
        if (n >= this.n || this.size > HashCommon.maxFill(n, this.f)) {
            return false;
        }
        try {
            this.rehash(n);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    public boolean trim(int n) {
        int n2 = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.f));
        if (n2 >= n || this.size > HashCommon.maxFill(n2, this.f)) {
            return false;
        }
        try {
            this.rehash(n2);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    protected void rehash(int n) {
        double[] dArray = this.key;
        short[] sArray = this.value;
        int n2 = n - 1;
        double[] dArray2 = new double[n + 1];
        short[] sArray2 = new short[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (Double.doubleToLongBits(dArray[--n3]) == 0L) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(dArray[n3])) & n2;
            if (Double.doubleToLongBits(dArray2[n5]) != 0L) {
                while (Double.doubleToLongBits(dArray2[n5 = n5 + 1 & n2]) != 0L) {
                }
            }
            dArray2[n5] = dArray[n3];
            sArray2[n5] = sArray[n3];
        }
        sArray2[n] = sArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = dArray2;
        this.value = sArray2;
    }

    public Double2ShortOpenCustomHashMap clone() {
        Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap;
        try {
            double2ShortOpenCustomHashMap = (Double2ShortOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2ShortOpenCustomHashMap.keys = null;
        double2ShortOpenCustomHashMap.values = null;
        double2ShortOpenCustomHashMap.entries = null;
        double2ShortOpenCustomHashMap.containsNullKey = this.containsNullKey;
        double2ShortOpenCustomHashMap.key = (double[])this.key.clone();
        double2ShortOpenCustomHashMap.value = (short[])this.value.clone();
        double2ShortOpenCustomHashMap.strategy = this.strategy;
        return double2ShortOpenCustomHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (Double.doubleToLongBits(this.key[n3]) == 0L) {
                ++n3;
            }
            n4 = this.strategy.hashCode(this.key[n3]);
            n += (n4 ^= this.value[n3]);
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n];
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        double[] dArray = this.key;
        short[] sArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeDouble(dArray[n2]);
            objectOutputStream.writeShort(sArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new double[this.n + 1];
        double[] dArray = this.key;
        this.value = new short[this.n + 1];
        short[] sArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            double d = objectInputStream.readDouble();
            short s = objectInputStream.readShort();
            if (this.strategy.equals(d, 0.0)) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
                while (Double.doubleToLongBits(dArray[n2]) != 0L) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            dArray[n2] = d;
            sArray[n2] = s;
        }
    }

    private void checkTable() {
    }

    public ObjectSet double2ShortEntrySet() {
        return this.double2ShortEntrySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static short access$300(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
        return double2ShortOpenCustomHashMap.removeNullEntry();
    }

    static short access$400(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap, int n) {
        return double2ShortOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ShortIterator {
        final Double2ShortOpenCustomHashMap this$0;

        public ValueIterator(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
            this.this$0 = double2ShortOpenCustomHashMap;
            super(double2ShortOpenCustomHashMap, null);
        }

        @Override
        public short nextShort() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractDoubleSet {
        final Double2ShortOpenCustomHashMap this$0;

        private KeySet(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
            this.this$0 = double2ShortOpenCustomHashMap;
        }

        @Override
        public DoubleIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(DoubleConsumer doubleConsumer) {
            if (this.this$0.containsNullKey) {
                doubleConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                double d = this.this$0.key[n];
                if (Double.doubleToLongBits(d) == 0L) continue;
                doubleConsumer.accept(d);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(double d) {
            return this.this$0.containsKey(d);
        }

        @Override
        public boolean remove(double d) {
            int n = this.this$0.size;
            this.this$0.remove(d);
            return this.this$0.size != n;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap, 1 var2_2) {
            this(double2ShortOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements DoubleIterator {
        final Double2ShortOpenCustomHashMap this$0;

        public KeyIterator(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
            this.this$0 = double2ShortOpenCustomHashMap;
            super(double2ShortOpenCustomHashMap, null);
        }

        @Override
        public double nextDouble() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Double2ShortMap.Entry>
    implements Double2ShortMap.FastEntrySet {
        final Double2ShortOpenCustomHashMap this$0;

        private MapEntrySet(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
            this.this$0 = double2ShortOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Double2ShortMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Double2ShortMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Double)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            double d = (Double)entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(d, 0.0)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s;
            }
            double[] dArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(d)) & this.this$0.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) == 0L) {
                return true;
            }
            if (this.this$0.strategy.equals(d, d2)) {
                return this.this$0.value[n] == s;
            }
            do {
                if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(d, d2));
            return this.this$0.value[n] == s;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Double)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            double d = (Double)entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(d, 0.0)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s) {
                    Double2ShortOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            double[] dArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(d)) & this.this$0.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) == 0L) {
                return true;
            }
            if (this.this$0.strategy.equals(d2, d)) {
                if (this.this$0.value[n] == s) {
                    Double2ShortOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(d2, d) || this.this$0.value[n] != s);
            Double2ShortOpenCustomHashMap.access$400(this.this$0, n);
            return false;
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public void forEach(Consumer<? super Double2ShortMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractDouble2ShortMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (Double.doubleToLongBits(this.this$0.key[n]) == 0L) continue;
                consumer.accept(new AbstractDouble2ShortMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Double2ShortMap.Entry> consumer) {
            AbstractDouble2ShortMap.BasicEntry basicEntry = new AbstractDouble2ShortMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (Double.doubleToLongBits(this.this$0.key[n]) == 0L) continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap, 1 var2_2) {
            this(double2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Double2ShortMap.Entry> {
        private final MapEntry entry;
        final Double2ShortOpenCustomHashMap this$0;

        private FastEntryIterator(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
            this.this$0 = double2ShortOpenCustomHashMap;
            super(double2ShortOpenCustomHashMap, null);
            this.entry = new MapEntry(this.this$0);
        }

        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }

        @Override
        public Object next() {
            return this.next();
        }

        FastEntryIterator(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap, 1 var2_2) {
            this(double2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Double2ShortMap.Entry> {
        private MapEntry entry;
        final Double2ShortOpenCustomHashMap this$0;

        private EntryIterator(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
            this.this$0 = double2ShortOpenCustomHashMap;
            super(double2ShortOpenCustomHashMap, null);
        }

        @Override
        public MapEntry next() {
            this.entry = new MapEntry(this.this$0, this.nextEntry());
            return this.entry;
        }

        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }

        @Override
        public Object next() {
            return this.next();
        }

        EntryIterator(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap, 1 var2_2) {
            this(double2ShortOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        DoubleArrayList wrapped;
        final Double2ShortOpenCustomHashMap this$0;

        private MapIterator(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
            this.this$0 = double2ShortOpenCustomHashMap;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNullKey = this.this$0.containsNullKey;
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
                this.last = this.this$0.n;
                return this.last;
            }
            double[] dArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                double d = this.wrapped.getDouble(-this.pos - 1);
                int n = HashCommon.mix(this.this$0.strategy.hashCode(d)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(d, dArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (Double.doubleToLongBits(dArray[this.pos]) == 0L);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            double[] dArray = this.this$0.key;
            while (true) {
                double d;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if (Double.doubleToLongBits(d = dArray[n]) == 0L) {
                        dArray[n2] = 0.0;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(d)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new DoubleArrayList(2);
                    }
                    this.wrapped.add(dArray[n]);
                }
                dArray[n2] = d;
                this.this$0.value[n2] = this.this$0.value[n];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNullKey = false;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.getDouble(-this.pos - 1));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        public int skip(int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - n2 - 1;
        }

        MapIterator(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap, 1 var2_2) {
            this(double2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Double2ShortMap.Entry,
    Map.Entry<Double, Short> {
        int index;
        final Double2ShortOpenCustomHashMap this$0;

        MapEntry(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap, int n) {
            this.this$0 = double2ShortOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Double2ShortOpenCustomHashMap double2ShortOpenCustomHashMap) {
            this.this$0 = double2ShortOpenCustomHashMap;
        }

        @Override
        public double getDoubleKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public short getShortValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public short setValue(short s) {
            short s2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = s;
            return s2;
        }

        @Override
        @Deprecated
        public Double getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Short getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Short setValue(Short s) {
            return this.setValue((short)s);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Double)entry.getKey()) && this.this$0.value[this.index] == (Short)entry.getValue();
        }

        @Override
        public int hashCode() {
            return this.this$0.strategy.hashCode(this.this$0.key[this.index]) ^ this.this$0.value[this.index];
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Short)object);
        }

        @Override
        @Deprecated
        public Object getValue() {
            return this.getValue();
        }

        @Override
        @Deprecated
        public Object getKey() {
            return this.getKey();
        }
    }
}

