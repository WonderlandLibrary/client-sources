/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ShortMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortHash;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
public class Short2ShortOpenCustomHashMap
extends AbstractShort2ShortMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient short[] key;
    protected transient short[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected ShortHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Short2ShortMap.FastEntrySet entries;
    protected transient ShortSet keys;
    protected transient ShortCollection values;

    public Short2ShortOpenCustomHashMap(int n, float f, ShortHash.Strategy strategy) {
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
        this.key = new short[this.n + 1];
        this.value = new short[this.n + 1];
    }

    public Short2ShortOpenCustomHashMap(int n, ShortHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Short2ShortOpenCustomHashMap(ShortHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Short2ShortOpenCustomHashMap(Map<? extends Short, ? extends Short> map, float f, ShortHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Short2ShortOpenCustomHashMap(Map<? extends Short, ? extends Short> map, ShortHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Short2ShortOpenCustomHashMap(Short2ShortMap short2ShortMap, float f, ShortHash.Strategy strategy) {
        this(short2ShortMap.size(), f, strategy);
        this.putAll(short2ShortMap);
    }

    public Short2ShortOpenCustomHashMap(Short2ShortMap short2ShortMap, ShortHash.Strategy strategy) {
        this(short2ShortMap, 0.75f, strategy);
    }

    public Short2ShortOpenCustomHashMap(short[] sArray, short[] sArray2, float f, ShortHash.Strategy strategy) {
        this(sArray.length, f, strategy);
        if (sArray.length != sArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + sArray.length + " and " + sArray2.length + ")");
        }
        for (int i = 0; i < sArray.length; ++i) {
            this.put(sArray[i], sArray2[i]);
        }
    }

    public Short2ShortOpenCustomHashMap(short[] sArray, short[] sArray2, ShortHash.Strategy strategy) {
        this(sArray, sArray2, 0.75f, strategy);
    }

    public ShortHash.Strategy strategy() {
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
    public void putAll(Map<? extends Short, ? extends Short> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(short s) {
        if (this.strategy.equals(s, (short)0)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return -(n + 1);
        }
        if (this.strategy.equals(s, s2)) {
            return n;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return -(n + 1);
        } while (!this.strategy.equals(s, s2));
        return n;
    }

    private void insert(int n, short s, short s2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = s;
        this.value[n] = s2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public short put(short s, short s2) {
        int n = this.find(s);
        if (n < 0) {
            this.insert(-n - 1, s, s2);
            return this.defRetValue;
        }
        short s3 = this.value[n];
        this.value[n] = s2;
        return s3;
    }

    private short addToValue(int n, short s) {
        short s2 = this.value[n];
        this.value[n] = (short)(s2 + s);
        return s2;
    }

    public short addTo(short s, short s2) {
        int n;
        if (this.strategy.equals(s, (short)0)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, s2);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            short[] sArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
            short s3 = sArray[n];
            if (s3 != 0) {
                if (this.strategy.equals(s3, s)) {
                    return this.addToValue(n, s2);
                }
                while ((s3 = sArray[n = n + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(s3, s)) continue;
                    return this.addToValue(n, s2);
                }
            }
        }
        this.key[n] = s;
        this.value[n] = (short)(this.defRetValue + s2);
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int n) {
        short[] sArray = this.key;
        while (true) {
            short s;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((s = sArray[n]) == 0) {
                    sArray[n2] = 0;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            sArray[n2] = s;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public short remove(short s) {
        if (this.strategy.equals(s, (short)0)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(s, s2)) {
            return this.removeEntry(n);
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(s, s2));
        return this.removeEntry(n);
    }

    @Override
    public short get(short s) {
        if (this.strategy.equals(s, (short)0)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(s, s2)) {
            return this.value[n];
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(s, s2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(short s) {
        if (this.strategy.equals(s, (short)0)) {
            return this.containsNullKey;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return true;
        }
        if (this.strategy.equals(s, s2)) {
            return false;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(s, s2));
        return false;
    }

    @Override
    public boolean containsValue(short s) {
        short[] sArray = this.value;
        short[] sArray2 = this.key;
        if (this.containsNullKey && sArray[this.n] == s) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (sArray2[n] == 0 || sArray[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public short getOrDefault(short s, short s2) {
        if (this.strategy.equals(s, (short)0)) {
            return this.containsNullKey ? this.value[this.n] : s2;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
        short s3 = sArray[n];
        if (s3 == 0) {
            return s2;
        }
        if (this.strategy.equals(s, s3)) {
            return this.value[n];
        }
        do {
            if ((s3 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return s2;
        } while (!this.strategy.equals(s, s3));
        return this.value[n];
    }

    @Override
    public short putIfAbsent(short s, short s2) {
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, s, s2);
        return this.defRetValue;
    }

    @Override
    public boolean remove(short s, short s2) {
        if (this.strategy.equals(s, (short)0)) {
            if (this.containsNullKey && s2 == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
        short s3 = sArray[n];
        if (s3 == 0) {
            return true;
        }
        if (this.strategy.equals(s, s3) && s2 == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((s3 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(s, s3) || s2 != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(short s, short s2, short s3) {
        int n = this.find(s);
        if (n < 0 || s2 != this.value[n]) {
            return true;
        }
        this.value[n] = s3;
        return false;
    }

    @Override
    public short replace(short s, short s2) {
        int n = this.find(s);
        if (n < 0) {
            return this.defRetValue;
        }
        short s3 = this.value[n];
        this.value[n] = s2;
        return s3;
    }

    @Override
    public short computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        short s2 = SafeMath.safeIntToShort(intUnaryOperator.applyAsInt(s));
        this.insert(-n - 1, s, s2);
        return s2;
    }

    @Override
    public short computeIfAbsentNullable(short s, IntFunction<? extends Short> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        Short s2 = intFunction.apply(s);
        if (s2 == null) {
            return this.defRetValue;
        }
        short s3 = s2;
        this.insert(-n - 1, s, s3);
        return s3;
    }

    @Override
    public short computeIfPresent(short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0) {
            return this.defRetValue;
        }
        Short s2 = biFunction.apply((Short)s, (Short)this.value[n]);
        if (s2 == null) {
            if (this.strategy.equals(s, (short)0)) {
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
    public short compute(short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        Short s2 = biFunction.apply((Short)s, n >= 0 ? Short.valueOf(this.value[n]) : null);
        if (s2 == null) {
            if (n >= 0) {
                if (this.strategy.equals(s, (short)0)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        short s3 = s2;
        if (n < 0) {
            this.insert(-n - 1, s, s3);
            return s3;
        }
        this.value[n] = s3;
        return this.value[n];
    }

    @Override
    public short merge(short s, short s2, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0) {
            this.insert(-n - 1, s, s2);
            return s2;
        }
        Short s3 = biFunction.apply((Short)this.value[n], (Short)s2);
        if (s3 == null) {
            if (this.strategy.equals(s, (short)0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = s3;
        return this.value[n];
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

    public Short2ShortMap.FastEntrySet short2ShortEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ShortSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ShortCollection values() {
        if (this.values == null) {
            this.values = new AbstractShortCollection(this){
                final Short2ShortOpenCustomHashMap this$0;
                {
                    this.this$0 = short2ShortOpenCustomHashMap;
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
        short[] sArray = this.key;
        short[] sArray2 = this.value;
        int n2 = n - 1;
        short[] sArray3 = new short[n + 1];
        short[] sArray4 = new short[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (sArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(sArray[n3])) & n2;
            if (sArray3[n5] != 0) {
                while (sArray3[n5 = n5 + 1 & n2] != 0) {
                }
            }
            sArray3[n5] = sArray[n3];
            sArray4[n5] = sArray2[n3];
        }
        sArray4[n] = sArray2[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = sArray3;
        this.value = sArray4;
    }

    public Short2ShortOpenCustomHashMap clone() {
        Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap;
        try {
            short2ShortOpenCustomHashMap = (Short2ShortOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2ShortOpenCustomHashMap.keys = null;
        short2ShortOpenCustomHashMap.values = null;
        short2ShortOpenCustomHashMap.entries = null;
        short2ShortOpenCustomHashMap.containsNullKey = this.containsNullKey;
        short2ShortOpenCustomHashMap.key = (short[])this.key.clone();
        short2ShortOpenCustomHashMap.value = (short[])this.value.clone();
        short2ShortOpenCustomHashMap.strategy = this.strategy;
        return short2ShortOpenCustomHashMap;
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
        short[] sArray = this.key;
        short[] sArray2 = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeShort(sArray[n2]);
            objectOutputStream.writeShort(sArray2[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new short[this.n + 1];
        short[] sArray = this.key;
        this.value = new short[this.n + 1];
        short[] sArray2 = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            short s = objectInputStream.readShort();
            short s2 = objectInputStream.readShort();
            if (this.strategy.equals(s, (short)0)) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
                while (sArray[n2] != 0) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            sArray[n2] = s;
            sArray2[n2] = s2;
        }
    }

    private void checkTable() {
    }

    public ObjectSet short2ShortEntrySet() {
        return this.short2ShortEntrySet();
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

    static short access$300(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
        return short2ShortOpenCustomHashMap.removeNullEntry();
    }

    static short access$400(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap, int n) {
        return short2ShortOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ShortIterator {
        final Short2ShortOpenCustomHashMap this$0;

        public ValueIterator(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
            this.this$0 = short2ShortOpenCustomHashMap;
            super(short2ShortOpenCustomHashMap, null);
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
    extends AbstractShortSet {
        final Short2ShortOpenCustomHashMap this$0;

        private KeySet(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
            this.this$0 = short2ShortOpenCustomHashMap;
        }

        @Override
        public ShortIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                short s = this.this$0.key[n];
                if (s == 0) continue;
                intConsumer.accept(s);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(short s) {
            return this.this$0.containsKey(s);
        }

        @Override
        public boolean remove(short s) {
            int n = this.this$0.size;
            this.this$0.remove(s);
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

        KeySet(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap, 1 var2_2) {
            this(short2ShortOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ShortIterator {
        final Short2ShortOpenCustomHashMap this$0;

        public KeyIterator(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
            this.this$0 = short2ShortOpenCustomHashMap;
            super(short2ShortOpenCustomHashMap, null);
        }

        @Override
        public short nextShort() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Short2ShortMap.Entry>
    implements Short2ShortMap.FastEntrySet {
        final Short2ShortOpenCustomHashMap this$0;

        private MapEntrySet(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
            this.this$0 = short2ShortOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Short2ShortMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Short2ShortMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Short)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            short s = (Short)entry.getKey();
            short s2 = (Short)entry.getValue();
            if (this.this$0.strategy.equals(s, (short)0)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s2;
            }
            short[] sArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(s)) & this.this$0.mask;
            short s3 = sArray[n];
            if (s3 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(s, s3)) {
                return this.this$0.value[n] == s2;
            }
            do {
                if ((s3 = sArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(s, s3));
            return this.this$0.value[n] == s2;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Short)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            short s = (Short)entry.getKey();
            short s2 = (Short)entry.getValue();
            if (this.this$0.strategy.equals(s, (short)0)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s2) {
                    Short2ShortOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            short[] sArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(s)) & this.this$0.mask;
            short s3 = sArray[n];
            if (s3 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(s3, s)) {
                if (this.this$0.value[n] == s2) {
                    Short2ShortOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((s3 = sArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(s3, s) || this.this$0.value[n] != s2);
            Short2ShortOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Short2ShortMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractShort2ShortMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new AbstractShort2ShortMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Short2ShortMap.Entry> consumer) {
            AbstractShort2ShortMap.BasicEntry basicEntry = new AbstractShort2ShortMap.BasicEntry();
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

        MapEntrySet(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap, 1 var2_2) {
            this(short2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Short2ShortMap.Entry> {
        private final MapEntry entry;
        final Short2ShortOpenCustomHashMap this$0;

        private FastEntryIterator(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
            this.this$0 = short2ShortOpenCustomHashMap;
            super(short2ShortOpenCustomHashMap, null);
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

        FastEntryIterator(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap, 1 var2_2) {
            this(short2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Short2ShortMap.Entry> {
        private MapEntry entry;
        final Short2ShortOpenCustomHashMap this$0;

        private EntryIterator(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
            this.this$0 = short2ShortOpenCustomHashMap;
            super(short2ShortOpenCustomHashMap, null);
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

        EntryIterator(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap, 1 var2_2) {
            this(short2ShortOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ShortArrayList wrapped;
        final Short2ShortOpenCustomHashMap this$0;

        private MapIterator(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
            this.this$0 = short2ShortOpenCustomHashMap;
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
            short[] sArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                short s = this.wrapped.getShort(-this.pos - 1);
                int n = HashCommon.mix(this.this$0.strategy.hashCode(s)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(s, sArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (sArray[this.pos] == 0);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            short[] sArray = this.this$0.key;
            while (true) {
                short s;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((s = sArray[n]) == 0) {
                        sArray[n2] = 0;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(s)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ShortArrayList(2);
                    }
                    this.wrapped.add(sArray[n]);
                }
                sArray[n2] = s;
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
                this.this$0.remove(this.wrapped.getShort(-this.pos - 1));
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

        MapIterator(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap, 1 var2_2) {
            this(short2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Short2ShortMap.Entry,
    Map.Entry<Short, Short> {
        int index;
        final Short2ShortOpenCustomHashMap this$0;

        MapEntry(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap, int n) {
            this.this$0 = short2ShortOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Short2ShortOpenCustomHashMap short2ShortOpenCustomHashMap) {
            this.this$0 = short2ShortOpenCustomHashMap;
        }

        @Override
        public short getShortKey() {
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
        public Short getKey() {
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
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Short)entry.getKey()) && this.this$0.value[this.index] == (Short)entry.getValue();
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

