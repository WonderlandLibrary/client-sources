/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2CharMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2CharMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleHash;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Double2CharOpenCustomHashMap
extends AbstractDouble2CharMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient double[] key;
    protected transient char[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected DoubleHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Double2CharMap.FastEntrySet entries;
    protected transient DoubleSet keys;
    protected transient CharCollection values;

    public Double2CharOpenCustomHashMap(int n, float f, DoubleHash.Strategy strategy) {
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
        this.value = new char[this.n + 1];
    }

    public Double2CharOpenCustomHashMap(int n, DoubleHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Double2CharOpenCustomHashMap(DoubleHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Double2CharOpenCustomHashMap(Map<? extends Double, ? extends Character> map, float f, DoubleHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Double2CharOpenCustomHashMap(Map<? extends Double, ? extends Character> map, DoubleHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Double2CharOpenCustomHashMap(Double2CharMap double2CharMap, float f, DoubleHash.Strategy strategy) {
        this(double2CharMap.size(), f, strategy);
        this.putAll(double2CharMap);
    }

    public Double2CharOpenCustomHashMap(Double2CharMap double2CharMap, DoubleHash.Strategy strategy) {
        this(double2CharMap, 0.75f, strategy);
    }

    public Double2CharOpenCustomHashMap(double[] dArray, char[] cArray, float f, DoubleHash.Strategy strategy) {
        this(dArray.length, f, strategy);
        if (dArray.length != cArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + dArray.length + " and " + cArray.length + ")");
        }
        for (int i = 0; i < dArray.length; ++i) {
            this.put(dArray[i], cArray[i]);
        }
    }

    public Double2CharOpenCustomHashMap(double[] dArray, char[] cArray, DoubleHash.Strategy strategy) {
        this(dArray, cArray, 0.75f, strategy);
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

    private char removeEntry(int n) {
        char c = this.value[n];
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return c;
    }

    private char removeNullEntry() {
        this.containsNullKey = false;
        char c = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return c;
    }

    @Override
    public void putAll(Map<? extends Double, ? extends Character> map) {
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

    private void insert(int n, double d, char c) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = d;
        this.value[n] = c;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public char put(double d, char c) {
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, c);
            return this.defRetValue;
        }
        char c2 = this.value[n];
        this.value[n] = c;
        return c2;
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
    public char remove(double d) {
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
    public char get(double d) {
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
    public boolean containsValue(char c) {
        char[] cArray = this.value;
        double[] dArray = this.key;
        if (this.containsNullKey && cArray[this.n] == c) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray[n]) == 0L || cArray[n] != c) continue;
            return false;
        }
        return true;
    }

    @Override
    public char getOrDefault(double d, char c) {
        if (this.strategy.equals(d, 0.0)) {
            return this.containsNullKey ? this.value[this.n] : c;
        }
        double[] dArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(d)) & this.mask;
        double d2 = dArray[n];
        if (Double.doubleToLongBits(d2) == 0L) {
            return c;
        }
        if (this.strategy.equals(d, d2)) {
            return this.value[n];
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return c;
        } while (!this.strategy.equals(d, d2));
        return this.value[n];
    }

    @Override
    public char putIfAbsent(double d, char c) {
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, d, c);
        return this.defRetValue;
    }

    @Override
    public boolean remove(double d, char c) {
        if (this.strategy.equals(d, 0.0)) {
            if (this.containsNullKey && c == this.value[this.n]) {
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
        if (this.strategy.equals(d, d2) && c == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(d, d2) || c != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(double d, char c, char c2) {
        int n = this.find(d);
        if (n < 0 || c != this.value[n]) {
            return true;
        }
        this.value[n] = c2;
        return false;
    }

    @Override
    public char replace(double d, char c) {
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        char c2 = this.value[n];
        this.value[n] = c;
        return c2;
    }

    @Override
    public char computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        char c = SafeMath.safeIntToChar(doubleToIntFunction.applyAsInt(d));
        this.insert(-n - 1, d, c);
        return c;
    }

    @Override
    public char computeIfAbsentNullable(double d, DoubleFunction<? extends Character> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(d);
        if (n >= 0) {
            return this.value[n];
        }
        Character c = doubleFunction.apply(d);
        if (c == null) {
            return this.defRetValue;
        }
        char c2 = c.charValue();
        this.insert(-n - 1, d, c2);
        return c2;
    }

    @Override
    public char computeIfPresent(double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            return this.defRetValue;
        }
        Character c = biFunction.apply((Double)d, Character.valueOf(this.value[n]));
        if (c == null) {
            if (this.strategy.equals(d, 0.0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = c.charValue();
        return this.value[n];
    }

    @Override
    public char compute(double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        Character c = biFunction.apply((Double)d, n >= 0 ? Character.valueOf(this.value[n]) : null);
        if (c == null) {
            if (n >= 0) {
                if (this.strategy.equals(d, 0.0)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        char c2 = c.charValue();
        if (n < 0) {
            this.insert(-n - 1, d, c2);
            return c2;
        }
        this.value[n] = c2;
        return this.value[n];
    }

    @Override
    public char merge(double d, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(d);
        if (n < 0) {
            this.insert(-n - 1, d, c);
            return c;
        }
        Character c2 = biFunction.apply(Character.valueOf(this.value[n]), Character.valueOf(c));
        if (c2 == null) {
            if (this.strategy.equals(d, 0.0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = c2.charValue();
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

    public Double2CharMap.FastEntrySet double2CharEntrySet() {
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
    public CharCollection values() {
        if (this.values == null) {
            this.values = new AbstractCharCollection(this){
                final Double2CharOpenCustomHashMap this$0;
                {
                    this.this$0 = double2CharOpenCustomHashMap;
                }

                @Override
                public CharIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(char c) {
                    return this.this$0.containsValue(c);
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
        char[] cArray = this.value;
        int n2 = n - 1;
        double[] dArray2 = new double[n + 1];
        char[] cArray2 = new char[n + 1];
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
            cArray2[n5] = cArray[n3];
        }
        cArray2[n] = cArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = dArray2;
        this.value = cArray2;
    }

    public Double2CharOpenCustomHashMap clone() {
        Double2CharOpenCustomHashMap double2CharOpenCustomHashMap;
        try {
            double2CharOpenCustomHashMap = (Double2CharOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2CharOpenCustomHashMap.keys = null;
        double2CharOpenCustomHashMap.values = null;
        double2CharOpenCustomHashMap.entries = null;
        double2CharOpenCustomHashMap.containsNullKey = this.containsNullKey;
        double2CharOpenCustomHashMap.key = (double[])this.key.clone();
        double2CharOpenCustomHashMap.value = (char[])this.value.clone();
        double2CharOpenCustomHashMap.strategy = this.strategy;
        return double2CharOpenCustomHashMap;
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
        char[] cArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeDouble(dArray[n2]);
            objectOutputStream.writeChar(cArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new double[this.n + 1];
        double[] dArray = this.key;
        this.value = new char[this.n + 1];
        char[] cArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            double d = objectInputStream.readDouble();
            char c = objectInputStream.readChar();
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
            cArray[n2] = c;
        }
    }

    private void checkTable() {
    }

    public ObjectSet double2CharEntrySet() {
        return this.double2CharEntrySet();
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

    static char access$300(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
        return double2CharOpenCustomHashMap.removeNullEntry();
    }

    static char access$400(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap, int n) {
        return double2CharOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements CharIterator {
        final Double2CharOpenCustomHashMap this$0;

        public ValueIterator(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
            this.this$0 = double2CharOpenCustomHashMap;
            super(double2CharOpenCustomHashMap, null);
        }

        @Override
        public char nextChar() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractDoubleSet {
        final Double2CharOpenCustomHashMap this$0;

        private KeySet(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
            this.this$0 = double2CharOpenCustomHashMap;
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

        KeySet(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap, 1 var2_2) {
            this(double2CharOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements DoubleIterator {
        final Double2CharOpenCustomHashMap this$0;

        public KeyIterator(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
            this.this$0 = double2CharOpenCustomHashMap;
            super(double2CharOpenCustomHashMap, null);
        }

        @Override
        public double nextDouble() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Double2CharMap.Entry>
    implements Double2CharMap.FastEntrySet {
        final Double2CharOpenCustomHashMap this$0;

        private MapEntrySet(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
            this.this$0 = double2CharOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Double2CharMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Double2CharMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            double d = (Double)entry.getKey();
            char c = ((Character)entry.getValue()).charValue();
            if (this.this$0.strategy.equals(d, 0.0)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == c;
            }
            double[] dArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(d)) & this.this$0.mask;
            double d2 = dArray[n];
            if (Double.doubleToLongBits(d2) == 0L) {
                return true;
            }
            if (this.this$0.strategy.equals(d, d2)) {
                return this.this$0.value[n] == c;
            }
            do {
                if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(d, d2));
            return this.this$0.value[n] == c;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            double d = (Double)entry.getKey();
            char c = ((Character)entry.getValue()).charValue();
            if (this.this$0.strategy.equals(d, 0.0)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == c) {
                    Double2CharOpenCustomHashMap.access$300(this.this$0);
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
                if (this.this$0.value[n] == c) {
                    Double2CharOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Double.doubleToLongBits(d2 = dArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(d2, d) || this.this$0.value[n] != c);
            Double2CharOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Double2CharMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractDouble2CharMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (Double.doubleToLongBits(this.this$0.key[n]) == 0L) continue;
                consumer.accept(new AbstractDouble2CharMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Double2CharMap.Entry> consumer) {
            AbstractDouble2CharMap.BasicEntry basicEntry = new AbstractDouble2CharMap.BasicEntry();
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

        MapEntrySet(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap, 1 var2_2) {
            this(double2CharOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Double2CharMap.Entry> {
        private final MapEntry entry;
        final Double2CharOpenCustomHashMap this$0;

        private FastEntryIterator(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
            this.this$0 = double2CharOpenCustomHashMap;
            super(double2CharOpenCustomHashMap, null);
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

        FastEntryIterator(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap, 1 var2_2) {
            this(double2CharOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Double2CharMap.Entry> {
        private MapEntry entry;
        final Double2CharOpenCustomHashMap this$0;

        private EntryIterator(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
            this.this$0 = double2CharOpenCustomHashMap;
            super(double2CharOpenCustomHashMap, null);
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

        EntryIterator(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap, 1 var2_2) {
            this(double2CharOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        DoubleArrayList wrapped;
        final Double2CharOpenCustomHashMap this$0;

        private MapIterator(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
            this.this$0 = double2CharOpenCustomHashMap;
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

        MapIterator(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap, 1 var2_2) {
            this(double2CharOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Double2CharMap.Entry,
    Map.Entry<Double, Character> {
        int index;
        final Double2CharOpenCustomHashMap this$0;

        MapEntry(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap, int n) {
            this.this$0 = double2CharOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Double2CharOpenCustomHashMap double2CharOpenCustomHashMap) {
            this.this$0 = double2CharOpenCustomHashMap;
        }

        @Override
        public double getDoubleKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public char getCharValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public char setValue(char c) {
            char c2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = c;
            return c2;
        }

        @Override
        @Deprecated
        public Double getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Character getValue() {
            return Character.valueOf(this.this$0.value[this.index]);
        }

        @Override
        @Deprecated
        public Character setValue(Character c) {
            return Character.valueOf(this.setValue(c.charValue()));
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Double)entry.getKey()) && this.this$0.value[this.index] == ((Character)entry.getValue()).charValue();
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
            return this.setValue((Character)object);
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

