/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
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
import java.util.function.DoubleUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Double2DoubleOpenHashMap
extends AbstractDouble2DoubleMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient double[] key;
    protected transient double[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Double2DoubleMap.FastEntrySet entries;
    protected transient DoubleSet keys;
    protected transient DoubleCollection values;

    public Double2DoubleOpenHashMap(int n, float f) {
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
        this.value = new double[this.n + 1];
    }

    public Double2DoubleOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Double2DoubleOpenHashMap() {
        this(16, 0.75f);
    }

    public Double2DoubleOpenHashMap(Map<? extends Double, ? extends Double> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Double2DoubleOpenHashMap(Map<? extends Double, ? extends Double> map) {
        this(map, 0.75f);
    }

    public Double2DoubleOpenHashMap(Double2DoubleMap double2DoubleMap, float f) {
        this(double2DoubleMap.size(), f);
        this.putAll(double2DoubleMap);
    }

    public Double2DoubleOpenHashMap(Double2DoubleMap double2DoubleMap) {
        this(double2DoubleMap, 0.75f);
    }

    public Double2DoubleOpenHashMap(double[] dArray, double[] dArray2, float f) {
        this(dArray.length, f);
        if (dArray.length != dArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + dArray.length + " and " + dArray2.length + ")");
        }
        for (int i = 0; i < dArray.length; ++i) {
            this.put(dArray[i], dArray2[i]);
        }
    }

    public Double2DoubleOpenHashMap(double[] dArray, double[] dArray2) {
        this(dArray, dArray2, 0.75f);
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

    private double removeEntry(int n) {
        double d = this.value[n];
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    private double removeNullEntry() {
        this.containsNullKey = false;
        double d = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    @Override
    public void putAll(Map<? extends Double, ? extends Double> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return -(n + 1);
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return n;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return -(n + 1);
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return n;
    }

    private void insert(int n, double d, double d2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = d;
        this.value[n] = d2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public double put(double d, double d2) {
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, d2);
            return this.defRetValue;
        }
        double d3 = this.value[n];
        this.value[n] = d2;
        return d3;
    }

    private double addToValue(int n, double d) {
        double d2 = this.value[n];
        this.value[n] = d2 + d;
        return d2;
    }

    public double addTo(double d, double d2) {
        int n;
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, d2);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            double[] dArray = this.key;
            n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
            double d3 = dArray[n];
            if (Double.doubleToLongBits(d3) != 0L) {
                if (Double.doubleToLongBits(d3) == Double.doubleToLongBits(d)) {
                    return this.addToValue(n, d2);
                }
                while (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.mask]) != 0L) {
                    if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d)) continue;
                    return this.addToValue(n, d2);
                }
            }
        }
        this.key[n] = d;
        this.value[n] = this.defRetValue + d2;
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
                int n3 = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            dArray[n2] = d;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public double remove(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return this.defRetValue;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return this.removeEntry(n);
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return this.removeEntry(n);
    }

    @Override
    public double get(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return this.defRetValue;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(double d) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return true;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d2)) {
            return false;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2));
        return false;
    }

    @Override
    public boolean containsValue(double d) {
        double[] dArray = this.value;
        double[] dArray2 = this.key;
        if (this.containsNullKey && Double.doubleToLongBits(dArray[this.n]) == Double.doubleToLongBits(d)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray2[n]) == 0L || Double.doubleToLongBits(dArray[n]) != Double.doubleToLongBits(d)) continue;
            return false;
        }
        return true;
    }

    @Override
    public double getOrDefault(double d, double d2) {
        if (Double.doubleToLongBits(d) == 0L) {
            return this.containsNullKey ? this.value[this.n] : d2;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d3 = dArray[n];
        if (Double.doubleToLongBits(d3) == 0L) {
            return d2;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3)) {
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return d2;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d3));
        return this.value[n];
    }

    @Override
    public double putIfAbsent(double d, double d2) {
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, d, d2);
        return this.defRetValue;
    }

    @Override
    public boolean remove(double d, double d2) {
        if (Double.doubleToLongBits(d) == 0L) {
            if (this.containsNullKey && Double.doubleToLongBits(d2) == Double.doubleToLongBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        double[] dArray = this.key;
        int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
        double d3 = dArray[n];
        if (Double.doubleToLongBits(d3) == 0L) {
            return true;
        }
        if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3) && Double.doubleToLongBits(d2) == Double.doubleToLongBits(this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d3) || Double.doubleToLongBits(d2) != Double.doubleToLongBits(this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(double d, double d2, double d3) {
        int n = this.find(d);
        if (n < 0 || Double.doubleToLongBits(d2) != Double.doubleToLongBits(this.value[n])) {
            return true;
        }
        this.value[n] = d3;
        return false;
    }

    @Override
    public double replace(double d, double d2) {
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        double d3 = this.value[n];
        this.value[n] = d2;
        return d3;
    }

    @Override
    public double computeIfAbsent(double d, DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        double d2 = doubleUnaryOperator.applyAsDouble(d);
        this.insert(-n - 1, d, d2);
        return d2;
    }

    @Override
    public double computeIfAbsentNullable(double d, DoubleFunction<? extends Double> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        Double d2 = doubleFunction.apply(d);
        if (d2 == null) {
            return this.defRetValue;
        }
        double d3 = d2;
        this.insert(-n - 1, d, d3);
        return d3;
    }

    @Override
    public double computeIfPresent(double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        Double d2 = biFunction.apply((Double)d, (Double)this.value[n]);
        if (d2 == null) {
            if (Double.doubleToLongBits(d) == 0L) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = d2;
        return this.value[n];
    }

    @Override
    public double compute(double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        Double d2 = biFunction.apply((Double)d, n >= 0 ? Double.valueOf(this.value[n]) : null);
        if (d2 == null) {
            if (n >= 0) {
                if (Double.doubleToLongBits(d) == 0L) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        double d3 = d2;
        if (n < 0) {
            this.insert(-n - 1, d, d3);
            return d3;
        }
        this.value[n] = d3;
        return this.value[n];
    }

    @Override
    public double merge(double d, double d2, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, d2);
            return d2;
        }
        Double d3 = biFunction.apply((Double)this.value[n], (Double)d2);
        if (d3 == null) {
            if (Double.doubleToLongBits(d) == 0L) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = d3;
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

    public Double2DoubleMap.FastEntrySet double2DoubleEntrySet() {
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
    public DoubleCollection values() {
        if (this.values == null) {
            this.values = new AbstractDoubleCollection(this){
                final Double2DoubleOpenHashMap this$0;
                {
                    this.this$0 = double2DoubleOpenHashMap;
                }

                @Override
                public DoubleIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(double d) {
                    return this.this$0.containsValue(d);
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public void forEach(DoubleConsumer doubleConsumer) {
                    if (this.this$0.containsNullKey) {
                        doubleConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (Double.doubleToLongBits(this.this$0.key[n]) == 0L) continue;
                        doubleConsumer.accept(this.this$0.value[n]);
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
        double[] dArray2 = this.value;
        int n2 = n - 1;
        double[] dArray3 = new double[n + 1];
        double[] dArray4 = new double[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (Double.doubleToLongBits(dArray[--n3]) == 0L) {
            }
            int n5 = (int)HashCommon.mix(Double.doubleToRawLongBits(dArray[n3])) & n2;
            if (Double.doubleToLongBits(dArray3[n5]) != 0L) {
                while (Double.doubleToLongBits(dArray3[n5 = n5 + 1 & n2]) != 0L) {
                }
            }
            dArray3[n5] = dArray[n3];
            dArray4[n5] = dArray2[n3];
        }
        dArray4[n] = dArray2[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = dArray3;
        this.value = dArray4;
    }

    public Double2DoubleOpenHashMap clone() {
        Double2DoubleOpenHashMap double2DoubleOpenHashMap;
        try {
            double2DoubleOpenHashMap = (Double2DoubleOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2DoubleOpenHashMap.keys = null;
        double2DoubleOpenHashMap.values = null;
        double2DoubleOpenHashMap.entries = null;
        double2DoubleOpenHashMap.containsNullKey = this.containsNullKey;
        double2DoubleOpenHashMap.key = (double[])this.key.clone();
        double2DoubleOpenHashMap.value = (double[])this.value.clone();
        return double2DoubleOpenHashMap;
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
            n4 = HashCommon.double2int(this.key[n3]);
            n += (n4 ^= HashCommon.double2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.double2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        double[] dArray = this.key;
        double[] dArray2 = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeDouble(dArray[n2]);
            objectOutputStream.writeDouble(dArray2[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new double[this.n + 1];
        double[] dArray = this.key;
        this.value = new double[this.n + 1];
        double[] dArray2 = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            double d = objectInputStream.readDouble();
            double d2 = objectInputStream.readDouble();
            if (Double.doubleToLongBits(d) == 0L) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.mask;
                while (Double.doubleToLongBits(dArray[n2]) != 0L) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            dArray[n2] = d;
            dArray2[n2] = d2;
        }
    }

    private void checkTable() {
    }

    public ObjectSet double2DoubleEntrySet() {
        return this.double2DoubleEntrySet();
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

    static double access$300(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
        return double2DoubleOpenHashMap.removeNullEntry();
    }

    static double access$400(Double2DoubleOpenHashMap double2DoubleOpenHashMap, int n) {
        return double2DoubleOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements DoubleIterator {
        final Double2DoubleOpenHashMap this$0;

        public ValueIterator(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
            this.this$0 = double2DoubleOpenHashMap;
            super(double2DoubleOpenHashMap, null);
        }

        @Override
        public double nextDouble() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractDoubleSet {
        final Double2DoubleOpenHashMap this$0;

        private KeySet(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
            this.this$0 = double2DoubleOpenHashMap;
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

        KeySet(Double2DoubleOpenHashMap double2DoubleOpenHashMap, 1 var2_2) {
            this(double2DoubleOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements DoubleIterator {
        final Double2DoubleOpenHashMap this$0;

        public KeyIterator(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
            this.this$0 = double2DoubleOpenHashMap;
            super(double2DoubleOpenHashMap, null);
        }

        @Override
        public double nextDouble() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Double2DoubleMap.Entry>
    implements Double2DoubleMap.FastEntrySet {
        final Double2DoubleOpenHashMap this$0;

        private MapEntrySet(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
            this.this$0 = double2DoubleOpenHashMap;
        }

        @Override
        public ObjectIterator<Double2DoubleMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Double2DoubleMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            double d = (Double)entry.getKey();
            double d2 = (Double)entry.getValue();
            if (Double.doubleToLongBits(d) == 0L) {
                return this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d2);
            }
            double[] dArray = this.this$0.key;
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
            double d3 = dArray[n];
            if (Double.doubleToLongBits(d3) == 0L) {
                return true;
            }
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(d3)) {
                return Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d2);
            }
            do {
                if (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (Double.doubleToLongBits(d) != Double.doubleToLongBits(d3));
            return Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d2);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            double d = (Double)entry.getKey();
            double d2 = (Double)entry.getValue();
            if (Double.doubleToLongBits(d) == 0L) {
                if (this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d2)) {
                    Double2DoubleOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            double[] dArray = this.this$0.key;
            int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
            double d3 = dArray[n];
            if (Double.doubleToLongBits(d3) == 0L) {
                return true;
            }
            if (Double.doubleToLongBits(d3) == Double.doubleToLongBits(d)) {
                if (Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d2)) {
                    Double2DoubleOpenHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Double.doubleToLongBits(d3 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || Double.doubleToLongBits(this.this$0.value[n]) != Double.doubleToLongBits(d2));
            Double2DoubleOpenHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractDouble2DoubleMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (Double.doubleToLongBits(this.this$0.key[n]) == 0L) continue;
                consumer.accept(new AbstractDouble2DoubleMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
            AbstractDouble2DoubleMap.BasicEntry basicEntry = new AbstractDouble2DoubleMap.BasicEntry();
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

        MapEntrySet(Double2DoubleOpenHashMap double2DoubleOpenHashMap, 1 var2_2) {
            this(double2DoubleOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Double2DoubleMap.Entry> {
        private final MapEntry entry;
        final Double2DoubleOpenHashMap this$0;

        private FastEntryIterator(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
            this.this$0 = double2DoubleOpenHashMap;
            super(double2DoubleOpenHashMap, null);
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

        FastEntryIterator(Double2DoubleOpenHashMap double2DoubleOpenHashMap, 1 var2_2) {
            this(double2DoubleOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Double2DoubleMap.Entry> {
        private MapEntry entry;
        final Double2DoubleOpenHashMap this$0;

        private EntryIterator(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
            this.this$0 = double2DoubleOpenHashMap;
            super(double2DoubleOpenHashMap, null);
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

        EntryIterator(Double2DoubleOpenHashMap double2DoubleOpenHashMap, 1 var2_2) {
            this(double2DoubleOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        DoubleArrayList wrapped;
        final Double2DoubleOpenHashMap this$0;

        private MapIterator(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
            this.this$0 = double2DoubleOpenHashMap;
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
                int n = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
                while (Double.doubleToLongBits(d) != Double.doubleToLongBits(dArray[n])) {
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
                    int n3 = (int)HashCommon.mix(Double.doubleToRawLongBits(d)) & this.this$0.mask;
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

        MapIterator(Double2DoubleOpenHashMap double2DoubleOpenHashMap, 1 var2_2) {
            this(double2DoubleOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Double2DoubleMap.Entry,
    Map.Entry<Double, Double> {
        int index;
        final Double2DoubleOpenHashMap this$0;

        MapEntry(Double2DoubleOpenHashMap double2DoubleOpenHashMap, int n) {
            this.this$0 = double2DoubleOpenHashMap;
            this.index = n;
        }

        MapEntry(Double2DoubleOpenHashMap double2DoubleOpenHashMap) {
            this.this$0 = double2DoubleOpenHashMap;
        }

        @Override
        public double getDoubleKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public double getDoubleValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public double setValue(double d) {
            double d2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = d;
            return d2;
        }

        @Override
        @Deprecated
        public Double getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Double getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Double setValue(Double d) {
            return this.setValue((double)d);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Double.doubleToLongBits(this.this$0.key[this.index]) == Double.doubleToLongBits((Double)entry.getKey()) && Double.doubleToLongBits(this.this$0.value[this.index]) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.this$0.key[this.index]) ^ HashCommon.double2int(this.this$0.value[this.index]);
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Double)object);
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

