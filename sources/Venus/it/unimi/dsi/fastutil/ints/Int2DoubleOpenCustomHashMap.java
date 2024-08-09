/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntHash;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Int2DoubleOpenCustomHashMap
extends AbstractInt2DoubleMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient double[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected IntHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Int2DoubleMap.FastEntrySet entries;
    protected transient IntSet keys;
    protected transient DoubleCollection values;

    public Int2DoubleOpenCustomHashMap(int n, float f, IntHash.Strategy strategy) {
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
        this.key = new int[this.n + 1];
        this.value = new double[this.n + 1];
    }

    public Int2DoubleOpenCustomHashMap(int n, IntHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Int2DoubleOpenCustomHashMap(IntHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Int2DoubleOpenCustomHashMap(Map<? extends Integer, ? extends Double> map, float f, IntHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Int2DoubleOpenCustomHashMap(Map<? extends Integer, ? extends Double> map, IntHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Int2DoubleOpenCustomHashMap(Int2DoubleMap int2DoubleMap, float f, IntHash.Strategy strategy) {
        this(int2DoubleMap.size(), f, strategy);
        this.putAll(int2DoubleMap);
    }

    public Int2DoubleOpenCustomHashMap(Int2DoubleMap int2DoubleMap, IntHash.Strategy strategy) {
        this(int2DoubleMap, 0.75f, strategy);
    }

    public Int2DoubleOpenCustomHashMap(int[] nArray, double[] dArray, float f, IntHash.Strategy strategy) {
        this(nArray.length, f, strategy);
        if (nArray.length != dArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + nArray.length + " and " + dArray.length + ")");
        }
        for (int i = 0; i < nArray.length; ++i) {
            this.put(nArray[i], dArray[i]);
        }
    }

    public Int2DoubleOpenCustomHashMap(int[] nArray, double[] dArray, IntHash.Strategy strategy) {
        this(nArray, dArray, 0.75f, strategy);
    }

    public IntHash.Strategy strategy() {
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
    public void putAll(Map<? extends Integer, ? extends Double> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(int n) {
        if (this.strategy.equals(n, 0)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return -(n2 + 1);
        }
        if (this.strategy.equals(n, n3)) {
            return n2;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return -(n2 + 1);
        } while (!this.strategy.equals(n, n3));
        return n2;
    }

    private void insert(int n, int n2, double d) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = n2;
        this.value[n] = d;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public double put(int n, double d) {
        int n2 = this.find(n);
        if (n2 < 0) {
            this.insert(-n2 - 1, n, d);
            return this.defRetValue;
        }
        double d2 = this.value[n2];
        this.value[n2] = d;
        return d2;
    }

    private double addToValue(int n, double d) {
        double d2 = this.value[n];
        this.value[n] = d2 + d;
        return d2;
    }

    public double addTo(int n, double d) {
        int n2;
        if (this.strategy.equals(n, 0)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, d);
            }
            n2 = this.n;
            this.containsNullKey = true;
        } else {
            int[] nArray = this.key;
            n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
            int n3 = nArray[n2];
            if (n3 != 0) {
                if (this.strategy.equals(n3, n)) {
                    return this.addToValue(n2, d);
                }
                while ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(n3, n)) continue;
                    return this.addToValue(n2, d);
                }
            }
        }
        this.key[n2] = n;
        this.value[n2] = this.defRetValue + d;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int n) {
        int[] nArray = this.key;
        while (true) {
            int n2;
            int n3 = n;
            n = n3 + 1 & this.mask;
            while (true) {
                if ((n2 = nArray[n]) == 0) {
                    nArray[n3] = 0;
                    return;
                }
                int n4 = HashCommon.mix(this.strategy.hashCode(n2)) & this.mask;
                if (n3 <= n ? n3 >= n4 || n4 > n : n3 >= n4 && n4 > n) break;
                n = n + 1 & this.mask;
            }
            nArray[n3] = n2;
            this.value[n3] = this.value[n];
        }
    }

    @Override
    public double remove(int n) {
        if (this.strategy.equals(n, 0)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(n, n3)) {
            return this.removeEntry(n2);
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(n, n3));
        return this.removeEntry(n2);
    }

    @Override
    public double get(int n) {
        if (this.strategy.equals(n, 0)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(n, n3)) {
            return this.value[n2];
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(n, n3));
        return this.value[n2];
    }

    @Override
    public boolean containsKey(int n) {
        if (this.strategy.equals(n, 0)) {
            return this.containsNullKey;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return true;
        }
        if (this.strategy.equals(n, n3)) {
            return false;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(n, n3));
        return false;
    }

    @Override
    public boolean containsValue(double d) {
        double[] dArray = this.value;
        int[] nArray = this.key;
        if (this.containsNullKey && Double.doubleToLongBits(dArray[this.n]) == Double.doubleToLongBits(d)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (nArray[n] == 0 || Double.doubleToLongBits(dArray[n]) != Double.doubleToLongBits(d)) continue;
            return false;
        }
        return true;
    }

    @Override
    public double getOrDefault(int n, double d) {
        if (this.strategy.equals(n, 0)) {
            return this.containsNullKey ? this.value[this.n] : d;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return d;
        }
        if (this.strategy.equals(n, n3)) {
            return this.value[n2];
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return d;
        } while (!this.strategy.equals(n, n3));
        return this.value[n2];
    }

    @Override
    public double putIfAbsent(int n, double d) {
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        this.insert(-n2 - 1, n, d);
        return this.defRetValue;
    }

    @Override
    public boolean remove(int n, double d) {
        if (this.strategy.equals(n, 0)) {
            if (this.containsNullKey && Double.doubleToLongBits(d) == Double.doubleToLongBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return true;
        }
        if (this.strategy.equals(n, n3) && Double.doubleToLongBits(d) == Double.doubleToLongBits(this.value[n2])) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(n, n3) || Double.doubleToLongBits(d) != Double.doubleToLongBits(this.value[n2]));
        this.removeEntry(n2);
        return false;
    }

    @Override
    public boolean replace(int n, double d, double d2) {
        int n2 = this.find(n);
        if (n2 < 0 || Double.doubleToLongBits(d) != Double.doubleToLongBits(this.value[n2])) {
            return true;
        }
        this.value[n2] = d2;
        return false;
    }

    @Override
    public double replace(int n, double d) {
        int n2 = this.find(n);
        if (n2 < 0) {
            return this.defRetValue;
        }
        double d2 = this.value[n2];
        this.value[n2] = d;
        return d2;
    }

    @Override
    public double computeIfAbsent(int n, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        double d = intToDoubleFunction.applyAsDouble(n);
        this.insert(-n2 - 1, n, d);
        return d;
    }

    @Override
    public double computeIfAbsentNullable(int n, IntFunction<? extends Double> intFunction) {
        Objects.requireNonNull(intFunction);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        Double d = intFunction.apply(n);
        if (d == null) {
            return this.defRetValue;
        }
        double d2 = d;
        this.insert(-n2 - 1, n, d2);
        return d2;
    }

    @Override
    public double computeIfPresent(int n, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        if (n2 < 0) {
            return this.defRetValue;
        }
        Double d = biFunction.apply((Integer)n, (Double)this.value[n2]);
        if (d == null) {
            if (this.strategy.equals(n, 0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = d;
        return this.value[n2];
    }

    @Override
    public double compute(int n, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        Double d = biFunction.apply((Integer)n, n2 >= 0 ? Double.valueOf(this.value[n2]) : null);
        if (d == null) {
            if (n2 >= 0) {
                if (this.strategy.equals(n, 0)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n2);
                }
            }
            return this.defRetValue;
        }
        double d2 = d;
        if (n2 < 0) {
            this.insert(-n2 - 1, n, d2);
            return d2;
        }
        this.value[n2] = d2;
        return this.value[n2];
    }

    @Override
    public double merge(int n, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        if (n2 < 0) {
            this.insert(-n2 - 1, n, d);
            return d;
        }
        Double d2 = biFunction.apply((Double)this.value[n2], (Double)d);
        if (d2 == null) {
            if (this.strategy.equals(n, 0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = d2;
        return this.value[n2];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Int2DoubleMap.FastEntrySet int2DoubleEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public IntSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public DoubleCollection values() {
        if (this.values == null) {
            this.values = new AbstractDoubleCollection(this){
                final Int2DoubleOpenCustomHashMap this$0;
                {
                    this.this$0 = int2DoubleOpenCustomHashMap;
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
                        if (this.this$0.key[n] == 0) continue;
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
        int[] nArray = this.key;
        double[] dArray = this.value;
        int n2 = n - 1;
        int[] nArray2 = new int[n + 1];
        double[] dArray2 = new double[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (nArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(nArray[n3])) & n2;
            if (nArray2[n5] != 0) {
                while (nArray2[n5 = n5 + 1 & n2] != 0) {
                }
            }
            nArray2[n5] = nArray[n3];
            dArray2[n5] = dArray[n3];
        }
        dArray2[n] = dArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = nArray2;
        this.value = dArray2;
    }

    public Int2DoubleOpenCustomHashMap clone() {
        Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap;
        try {
            int2DoubleOpenCustomHashMap = (Int2DoubleOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2DoubleOpenCustomHashMap.keys = null;
        int2DoubleOpenCustomHashMap.values = null;
        int2DoubleOpenCustomHashMap.entries = null;
        int2DoubleOpenCustomHashMap.containsNullKey = this.containsNullKey;
        int2DoubleOpenCustomHashMap.key = (int[])this.key.clone();
        int2DoubleOpenCustomHashMap.value = (double[])this.value.clone();
        int2DoubleOpenCustomHashMap.strategy = this.strategy;
        return int2DoubleOpenCustomHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == 0) {
                ++n3;
            }
            n4 = this.strategy.hashCode(this.key[n3]);
            n += (n4 ^= HashCommon.double2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.double2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int[] nArray = this.key;
        double[] dArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeInt(nArray[n2]);
            objectOutputStream.writeDouble(dArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new int[this.n + 1];
        int[] nArray = this.key;
        this.value = new double[this.n + 1];
        double[] dArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            int n3 = objectInputStream.readInt();
            double d = objectInputStream.readDouble();
            if (this.strategy.equals(n3, 0)) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(n3)) & this.mask;
                while (nArray[n2] != 0) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            nArray[n2] = n3;
            dArray[n2] = d;
        }
    }

    private void checkTable() {
    }

    public ObjectSet int2DoubleEntrySet() {
        return this.int2DoubleEntrySet();
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

    static double access$300(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
        return int2DoubleOpenCustomHashMap.removeNullEntry();
    }

    static double access$400(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap, int n) {
        return int2DoubleOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements DoubleIterator {
        final Int2DoubleOpenCustomHashMap this$0;

        public ValueIterator(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
            this.this$0 = int2DoubleOpenCustomHashMap;
            super(int2DoubleOpenCustomHashMap, null);
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
    extends AbstractIntSet {
        final Int2DoubleOpenCustomHashMap this$0;

        private KeySet(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
            this.this$0 = int2DoubleOpenCustomHashMap;
        }

        @Override
        public IntIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                int n2 = this.this$0.key[n];
                if (n2 == 0) continue;
                intConsumer.accept(n2);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(int n) {
            return this.this$0.containsKey(n);
        }

        @Override
        public boolean remove(int n) {
            int n2 = this.this$0.size;
            this.this$0.remove(n);
            return this.this$0.size != n2;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap, 1 var2_2) {
            this(int2DoubleOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements IntIterator {
        final Int2DoubleOpenCustomHashMap this$0;

        public KeyIterator(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
            this.this$0 = int2DoubleOpenCustomHashMap;
            super(int2DoubleOpenCustomHashMap, null);
        }

        @Override
        public int nextInt() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Int2DoubleMap.Entry>
    implements Int2DoubleMap.FastEntrySet {
        final Int2DoubleOpenCustomHashMap this$0;

        private MapEntrySet(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
            this.this$0 = int2DoubleOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Int2DoubleMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Int2DoubleMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            double d = (Double)entry.getValue();
            if (this.this$0.strategy.equals(n, 0)) {
                return this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d);
            }
            int[] nArray = this.this$0.key;
            int n2 = HashCommon.mix(this.this$0.strategy.hashCode(n)) & this.this$0.mask;
            int n3 = nArray[n2];
            if (n3 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(n, n3)) {
                return Double.doubleToLongBits(this.this$0.value[n2]) == Double.doubleToLongBits(d);
            }
            do {
                if ((n3 = nArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(n, n3));
            return Double.doubleToLongBits(this.this$0.value[n2]) == Double.doubleToLongBits(d);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            double d = (Double)entry.getValue();
            if (this.this$0.strategy.equals(n, 0)) {
                if (this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d)) {
                    Int2DoubleOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            int[] nArray = this.this$0.key;
            int n2 = HashCommon.mix(this.this$0.strategy.hashCode(n)) & this.this$0.mask;
            int n3 = nArray[n2];
            if (n3 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(n3, n)) {
                if (Double.doubleToLongBits(this.this$0.value[n2]) == Double.doubleToLongBits(d)) {
                    Int2DoubleOpenCustomHashMap.access$400(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((n3 = nArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(n3, n) || Double.doubleToLongBits(this.this$0.value[n2]) != Double.doubleToLongBits(d));
            Int2DoubleOpenCustomHashMap.access$400(this.this$0, n2);
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
        public void forEach(Consumer<? super Int2DoubleMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractInt2DoubleMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new AbstractInt2DoubleMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Int2DoubleMap.Entry> consumer) {
            AbstractInt2DoubleMap.BasicEntry basicEntry = new AbstractInt2DoubleMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap, 1 var2_2) {
            this(int2DoubleOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Int2DoubleMap.Entry> {
        private final MapEntry entry;
        final Int2DoubleOpenCustomHashMap this$0;

        private FastEntryIterator(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
            this.this$0 = int2DoubleOpenCustomHashMap;
            super(int2DoubleOpenCustomHashMap, null);
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

        FastEntryIterator(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap, 1 var2_2) {
            this(int2DoubleOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Int2DoubleMap.Entry> {
        private MapEntry entry;
        final Int2DoubleOpenCustomHashMap this$0;

        private EntryIterator(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
            this.this$0 = int2DoubleOpenCustomHashMap;
            super(int2DoubleOpenCustomHashMap, null);
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

        EntryIterator(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap, 1 var2_2) {
            this(int2DoubleOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        IntArrayList wrapped;
        final Int2DoubleOpenCustomHashMap this$0;

        private MapIterator(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
            this.this$0 = int2DoubleOpenCustomHashMap;
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
            int[] nArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                int n = this.wrapped.getInt(-this.pos - 1);
                int n2 = HashCommon.mix(this.this$0.strategy.hashCode(n)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(n, nArray[n2])) {
                    n2 = n2 + 1 & this.this$0.mask;
                }
                return n2;
            } while (nArray[this.pos] == 0);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            int[] nArray = this.this$0.key;
            while (true) {
                int n2;
                int n3 = n;
                n = n3 + 1 & this.this$0.mask;
                while (true) {
                    if ((n2 = nArray[n]) == 0) {
                        nArray[n3] = 0;
                        return;
                    }
                    int n4 = HashCommon.mix(this.this$0.strategy.hashCode(n2)) & this.this$0.mask;
                    if (n3 <= n ? n3 >= n4 || n4 > n : n3 >= n4 && n4 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n3) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(nArray[n]);
                }
                nArray[n3] = n2;
                this.this$0.value[n3] = this.this$0.value[n];
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
                this.this$0.remove(this.wrapped.getInt(-this.pos - 1));
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

        MapIterator(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap, 1 var2_2) {
            this(int2DoubleOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Int2DoubleMap.Entry,
    Map.Entry<Integer, Double> {
        int index;
        final Int2DoubleOpenCustomHashMap this$0;

        MapEntry(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap, int n) {
            this.this$0 = int2DoubleOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Int2DoubleOpenCustomHashMap int2DoubleOpenCustomHashMap) {
            this.this$0 = int2DoubleOpenCustomHashMap;
        }

        @Override
        public int getIntKey() {
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
        public Integer getKey() {
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
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Integer)entry.getKey()) && Double.doubleToLongBits(this.this$0.value[this.index]) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public int hashCode() {
            return this.this$0.strategy.hashCode(this.this$0.key[this.index]) ^ HashCommon.double2int(this.this$0.value[this.index]);
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

