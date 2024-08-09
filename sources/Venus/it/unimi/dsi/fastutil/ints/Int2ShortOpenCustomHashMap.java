/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.ints.AbstractInt2ShortMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntHash;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Int2ShortOpenCustomHashMap
extends AbstractInt2ShortMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient short[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected IntHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Int2ShortMap.FastEntrySet entries;
    protected transient IntSet keys;
    protected transient ShortCollection values;

    public Int2ShortOpenCustomHashMap(int n, float f, IntHash.Strategy strategy) {
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
        this.value = new short[this.n + 1];
    }

    public Int2ShortOpenCustomHashMap(int n, IntHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Int2ShortOpenCustomHashMap(IntHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Int2ShortOpenCustomHashMap(Map<? extends Integer, ? extends Short> map, float f, IntHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Int2ShortOpenCustomHashMap(Map<? extends Integer, ? extends Short> map, IntHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Int2ShortOpenCustomHashMap(Int2ShortMap int2ShortMap, float f, IntHash.Strategy strategy) {
        this(int2ShortMap.size(), f, strategy);
        this.putAll(int2ShortMap);
    }

    public Int2ShortOpenCustomHashMap(Int2ShortMap int2ShortMap, IntHash.Strategy strategy) {
        this(int2ShortMap, 0.75f, strategy);
    }

    public Int2ShortOpenCustomHashMap(int[] nArray, short[] sArray, float f, IntHash.Strategy strategy) {
        this(nArray.length, f, strategy);
        if (nArray.length != sArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + nArray.length + " and " + sArray.length + ")");
        }
        for (int i = 0; i < nArray.length; ++i) {
            this.put(nArray[i], sArray[i]);
        }
    }

    public Int2ShortOpenCustomHashMap(int[] nArray, short[] sArray, IntHash.Strategy strategy) {
        this(nArray, sArray, 0.75f, strategy);
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
    public void putAll(Map<? extends Integer, ? extends Short> map) {
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

    private void insert(int n, int n2, short s) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = n2;
        this.value[n] = s;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public short put(int n, short s) {
        int n2 = this.find(n);
        if (n2 < 0) {
            this.insert(-n2 - 1, n, s);
            return this.defRetValue;
        }
        short s2 = this.value[n2];
        this.value[n2] = s;
        return s2;
    }

    private short addToValue(int n, short s) {
        short s2 = this.value[n];
        this.value[n] = (short)(s2 + s);
        return s2;
    }

    public short addTo(int n, short s) {
        int n2;
        if (this.strategy.equals(n, 0)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, s);
            }
            n2 = this.n;
            this.containsNullKey = true;
        } else {
            int[] nArray = this.key;
            n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
            int n3 = nArray[n2];
            if (n3 != 0) {
                if (this.strategy.equals(n3, n)) {
                    return this.addToValue(n2, s);
                }
                while ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(n3, n)) continue;
                    return this.addToValue(n2, s);
                }
            }
        }
        this.key[n2] = n;
        this.value[n2] = (short)(this.defRetValue + s);
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
    public short remove(int n) {
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
    public short get(int n) {
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
    public boolean containsValue(short s) {
        short[] sArray = this.value;
        int[] nArray = this.key;
        if (this.containsNullKey && sArray[this.n] == s) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (nArray[n] == 0 || sArray[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public short getOrDefault(int n, short s) {
        if (this.strategy.equals(n, 0)) {
            return this.containsNullKey ? this.value[this.n] : s;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(this.strategy.hashCode(n)) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return s;
        }
        if (this.strategy.equals(n, n3)) {
            return this.value[n2];
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return s;
        } while (!this.strategy.equals(n, n3));
        return this.value[n2];
    }

    @Override
    public short putIfAbsent(int n, short s) {
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        this.insert(-n2 - 1, n, s);
        return this.defRetValue;
    }

    @Override
    public boolean remove(int n, short s) {
        if (this.strategy.equals(n, 0)) {
            if (this.containsNullKey && s == this.value[this.n]) {
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
        if (this.strategy.equals(n, n3) && s == this.value[n2]) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(n, n3) || s != this.value[n2]);
        this.removeEntry(n2);
        return false;
    }

    @Override
    public boolean replace(int n, short s, short s2) {
        int n2 = this.find(n);
        if (n2 < 0 || s != this.value[n2]) {
            return true;
        }
        this.value[n2] = s2;
        return false;
    }

    @Override
    public short replace(int n, short s) {
        int n2 = this.find(n);
        if (n2 < 0) {
            return this.defRetValue;
        }
        short s2 = this.value[n2];
        this.value[n2] = s;
        return s2;
    }

    @Override
    public short computeIfAbsent(int n, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        short s = SafeMath.safeIntToShort(intUnaryOperator.applyAsInt(n));
        this.insert(-n2 - 1, n, s);
        return s;
    }

    @Override
    public short computeIfAbsentNullable(int n, IntFunction<? extends Short> intFunction) {
        Objects.requireNonNull(intFunction);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        Short s = intFunction.apply(n);
        if (s == null) {
            return this.defRetValue;
        }
        short s2 = s;
        this.insert(-n2 - 1, n, s2);
        return s2;
    }

    @Override
    public short computeIfPresent(int n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        if (n2 < 0) {
            return this.defRetValue;
        }
        Short s = biFunction.apply((Integer)n, (Short)this.value[n2]);
        if (s == null) {
            if (this.strategy.equals(n, 0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = s;
        return this.value[n2];
    }

    @Override
    public short compute(int n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        Short s = biFunction.apply((Integer)n, n2 >= 0 ? Short.valueOf(this.value[n2]) : null);
        if (s == null) {
            if (n2 >= 0) {
                if (this.strategy.equals(n, 0)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n2);
                }
            }
            return this.defRetValue;
        }
        short s2 = s;
        if (n2 < 0) {
            this.insert(-n2 - 1, n, s2);
            return s2;
        }
        this.value[n2] = s2;
        return this.value[n2];
    }

    @Override
    public short merge(int n, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        if (n2 < 0) {
            this.insert(-n2 - 1, n, s);
            return s;
        }
        Short s2 = biFunction.apply((Short)this.value[n2], (Short)s);
        if (s2 == null) {
            if (this.strategy.equals(n, 0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = s2;
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

    public Int2ShortMap.FastEntrySet int2ShortEntrySet() {
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
    public ShortCollection values() {
        if (this.values == null) {
            this.values = new AbstractShortCollection(this){
                final Int2ShortOpenCustomHashMap this$0;
                {
                    this.this$0 = int2ShortOpenCustomHashMap;
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
                        if (this.this$0.key[n] == 0) continue;
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
        int[] nArray = this.key;
        short[] sArray = this.value;
        int n2 = n - 1;
        int[] nArray2 = new int[n + 1];
        short[] sArray2 = new short[n + 1];
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
            sArray2[n5] = sArray[n3];
        }
        sArray2[n] = sArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = nArray2;
        this.value = sArray2;
    }

    public Int2ShortOpenCustomHashMap clone() {
        Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap;
        try {
            int2ShortOpenCustomHashMap = (Int2ShortOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2ShortOpenCustomHashMap.keys = null;
        int2ShortOpenCustomHashMap.values = null;
        int2ShortOpenCustomHashMap.entries = null;
        int2ShortOpenCustomHashMap.containsNullKey = this.containsNullKey;
        int2ShortOpenCustomHashMap.key = (int[])this.key.clone();
        int2ShortOpenCustomHashMap.value = (short[])this.value.clone();
        int2ShortOpenCustomHashMap.strategy = this.strategy;
        return int2ShortOpenCustomHashMap;
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
            n += (n4 ^= this.value[n3]);
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n];
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int[] nArray = this.key;
        short[] sArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeInt(nArray[n2]);
            objectOutputStream.writeShort(sArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new int[this.n + 1];
        int[] nArray = this.key;
        this.value = new short[this.n + 1];
        short[] sArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            int n3 = objectInputStream.readInt();
            short s = objectInputStream.readShort();
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
            sArray[n2] = s;
        }
    }

    private void checkTable() {
    }

    public ObjectSet int2ShortEntrySet() {
        return this.int2ShortEntrySet();
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

    static short access$300(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
        return int2ShortOpenCustomHashMap.removeNullEntry();
    }

    static short access$400(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap, int n) {
        return int2ShortOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ShortIterator {
        final Int2ShortOpenCustomHashMap this$0;

        public ValueIterator(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
            this.this$0 = int2ShortOpenCustomHashMap;
            super(int2ShortOpenCustomHashMap, null);
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
    extends AbstractIntSet {
        final Int2ShortOpenCustomHashMap this$0;

        private KeySet(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
            this.this$0 = int2ShortOpenCustomHashMap;
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

        KeySet(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap, 1 var2_2) {
            this(int2ShortOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements IntIterator {
        final Int2ShortOpenCustomHashMap this$0;

        public KeyIterator(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
            this.this$0 = int2ShortOpenCustomHashMap;
            super(int2ShortOpenCustomHashMap, null);
        }

        @Override
        public int nextInt() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Int2ShortMap.Entry>
    implements Int2ShortMap.FastEntrySet {
        final Int2ShortOpenCustomHashMap this$0;

        private MapEntrySet(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
            this.this$0 = int2ShortOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Int2ShortMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Int2ShortMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(n, 0)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s;
            }
            int[] nArray = this.this$0.key;
            int n2 = HashCommon.mix(this.this$0.strategy.hashCode(n)) & this.this$0.mask;
            int n3 = nArray[n2];
            if (n3 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(n, n3)) {
                return this.this$0.value[n2] == s;
            }
            do {
                if ((n3 = nArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(n, n3));
            return this.this$0.value[n2] == s;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(n, 0)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s) {
                    Int2ShortOpenCustomHashMap.access$300(this.this$0);
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
                if (this.this$0.value[n2] == s) {
                    Int2ShortOpenCustomHashMap.access$400(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((n3 = nArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(n3, n) || this.this$0.value[n2] != s);
            Int2ShortOpenCustomHashMap.access$400(this.this$0, n2);
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
        public void forEach(Consumer<? super Int2ShortMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractInt2ShortMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new AbstractInt2ShortMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Int2ShortMap.Entry> consumer) {
            AbstractInt2ShortMap.BasicEntry basicEntry = new AbstractInt2ShortMap.BasicEntry();
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

        MapEntrySet(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap, 1 var2_2) {
            this(int2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Int2ShortMap.Entry> {
        private final MapEntry entry;
        final Int2ShortOpenCustomHashMap this$0;

        private FastEntryIterator(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
            this.this$0 = int2ShortOpenCustomHashMap;
            super(int2ShortOpenCustomHashMap, null);
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

        FastEntryIterator(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap, 1 var2_2) {
            this(int2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Int2ShortMap.Entry> {
        private MapEntry entry;
        final Int2ShortOpenCustomHashMap this$0;

        private EntryIterator(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
            this.this$0 = int2ShortOpenCustomHashMap;
            super(int2ShortOpenCustomHashMap, null);
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

        EntryIterator(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap, 1 var2_2) {
            this(int2ShortOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        IntArrayList wrapped;
        final Int2ShortOpenCustomHashMap this$0;

        private MapIterator(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
            this.this$0 = int2ShortOpenCustomHashMap;
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

        MapIterator(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap, 1 var2_2) {
            this(int2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Int2ShortMap.Entry,
    Map.Entry<Integer, Short> {
        int index;
        final Int2ShortOpenCustomHashMap this$0;

        MapEntry(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap, int n) {
            this.this$0 = int2ShortOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Int2ShortOpenCustomHashMap int2ShortOpenCustomHashMap) {
            this.this$0 = int2ShortOpenCustomHashMap;
        }

        @Override
        public int getIntKey() {
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
        public Integer getKey() {
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
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Integer)entry.getKey()) && this.this$0.value[this.index] == (Short)entry.getValue();
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

