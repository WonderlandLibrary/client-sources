/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.longs.AbstractLong2ShortMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ShortMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongHash;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
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
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Long2ShortOpenCustomHashMap
extends AbstractLong2ShortMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient short[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected LongHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Long2ShortMap.FastEntrySet entries;
    protected transient LongSet keys;
    protected transient ShortCollection values;

    public Long2ShortOpenCustomHashMap(int n, float f, LongHash.Strategy strategy) {
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
        this.key = new long[this.n + 1];
        this.value = new short[this.n + 1];
    }

    public Long2ShortOpenCustomHashMap(int n, LongHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Long2ShortOpenCustomHashMap(LongHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Long2ShortOpenCustomHashMap(Map<? extends Long, ? extends Short> map, float f, LongHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Long2ShortOpenCustomHashMap(Map<? extends Long, ? extends Short> map, LongHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Long2ShortOpenCustomHashMap(Long2ShortMap long2ShortMap, float f, LongHash.Strategy strategy) {
        this(long2ShortMap.size(), f, strategy);
        this.putAll(long2ShortMap);
    }

    public Long2ShortOpenCustomHashMap(Long2ShortMap long2ShortMap, LongHash.Strategy strategy) {
        this(long2ShortMap, 0.75f, strategy);
    }

    public Long2ShortOpenCustomHashMap(long[] lArray, short[] sArray, float f, LongHash.Strategy strategy) {
        this(lArray.length, f, strategy);
        if (lArray.length != sArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + lArray.length + " and " + sArray.length + ")");
        }
        for (int i = 0; i < lArray.length; ++i) {
            this.put(lArray[i], sArray[i]);
        }
    }

    public Long2ShortOpenCustomHashMap(long[] lArray, short[] sArray, LongHash.Strategy strategy) {
        this(lArray, sArray, 0.75f, strategy);
    }

    public LongHash.Strategy strategy() {
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
    public void putAll(Map<? extends Long, ? extends Short> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(long l) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return -(n + 1);
        }
        if (this.strategy.equals(l, l2)) {
            return n;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return -(n + 1);
        } while (!this.strategy.equals(l, l2));
        return n;
    }

    private void insert(int n, long l, short s) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = l;
        this.value[n] = s;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public short put(long l, short s) {
        int n = this.find(l);
        if (n < 0) {
            this.insert(-n - 1, l, s);
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

    public short addTo(long l, short s) {
        int n;
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, s);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            long[] lArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
            long l2 = lArray[n];
            if (l2 != 0L) {
                if (this.strategy.equals(l2, l)) {
                    return this.addToValue(n, s);
                }
                while ((l2 = lArray[n = n + 1 & this.mask]) != 0L) {
                    if (!this.strategy.equals(l2, l)) continue;
                    return this.addToValue(n, s);
                }
            }
        }
        this.key[n] = l;
        this.value[n] = (short)(this.defRetValue + s);
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int n) {
        long[] lArray = this.key;
        while (true) {
            long l;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((l = lArray[n]) == 0L) {
                    lArray[n2] = 0L;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            lArray[n2] = l;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public short remove(long l) {
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return this.defRetValue;
        }
        if (this.strategy.equals(l, l2)) {
            return this.removeEntry(n);
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(l, l2));
        return this.removeEntry(n);
    }

    @Override
    public short get(long l) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return this.defRetValue;
        }
        if (this.strategy.equals(l, l2)) {
            return this.value[n];
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(l, l2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(long l) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNullKey;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (this.strategy.equals(l, l2)) {
            return false;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(l, l2));
        return false;
    }

    @Override
    public boolean containsValue(short s) {
        short[] sArray = this.value;
        long[] lArray = this.key;
        if (this.containsNullKey && sArray[this.n] == s) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (lArray[n] == 0L || sArray[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public short getOrDefault(long l, short s) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNullKey ? this.value[this.n] : s;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return s;
        }
        if (this.strategy.equals(l, l2)) {
            return this.value[n];
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return s;
        } while (!this.strategy.equals(l, l2));
        return this.value[n];
    }

    @Override
    public short putIfAbsent(long l, short s) {
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, l, s);
        return this.defRetValue;
    }

    @Override
    public boolean remove(long l, short s) {
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNullKey && s == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (this.strategy.equals(l, l2) && s == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(l, l2) || s != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(long l, short s, short s2) {
        int n = this.find(l);
        if (n < 0 || s != this.value[n]) {
            return true;
        }
        this.value[n] = s2;
        return false;
    }

    @Override
    public short replace(long l, short s) {
        int n = this.find(l);
        if (n < 0) {
            return this.defRetValue;
        }
        short s2 = this.value[n];
        this.value[n] = s;
        return s2;
    }

    @Override
    public short computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        short s = SafeMath.safeIntToShort(longToIntFunction.applyAsInt(l));
        this.insert(-n - 1, l, s);
        return s;
    }

    @Override
    public short computeIfAbsentNullable(long l, LongFunction<? extends Short> longFunction) {
        Objects.requireNonNull(longFunction);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        Short s = longFunction.apply(l);
        if (s == null) {
            return this.defRetValue;
        }
        short s2 = s;
        this.insert(-n - 1, l, s2);
        return s2;
    }

    @Override
    public short computeIfPresent(long l, BiFunction<? super Long, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0) {
            return this.defRetValue;
        }
        Short s = biFunction.apply((Long)l, (Short)this.value[n]);
        if (s == null) {
            if (this.strategy.equals(l, 0L)) {
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
    public short compute(long l, BiFunction<? super Long, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        Short s = biFunction.apply((Long)l, n >= 0 ? Short.valueOf(this.value[n]) : null);
        if (s == null) {
            if (n >= 0) {
                if (this.strategy.equals(l, 0L)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        short s2 = s;
        if (n < 0) {
            this.insert(-n - 1, l, s2);
            return s2;
        }
        this.value[n] = s2;
        return this.value[n];
    }

    @Override
    public short merge(long l, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0) {
            this.insert(-n - 1, l, s);
            return s;
        }
        Short s2 = biFunction.apply((Short)this.value[n], (Short)s);
        if (s2 == null) {
            if (this.strategy.equals(l, 0L)) {
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

    public Long2ShortMap.FastEntrySet long2ShortEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public LongSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ShortCollection values() {
        if (this.values == null) {
            this.values = new AbstractShortCollection(this){
                final Long2ShortOpenCustomHashMap this$0;
                {
                    this.this$0 = long2ShortOpenCustomHashMap;
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
                        if (this.this$0.key[n] == 0L) continue;
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
        long[] lArray = this.key;
        short[] sArray = this.value;
        int n2 = n - 1;
        long[] lArray2 = new long[n + 1];
        short[] sArray2 = new short[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (lArray[--n3] == 0L) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(lArray[n3])) & n2;
            if (lArray2[n5] != 0L) {
                while (lArray2[n5 = n5 + 1 & n2] != 0L) {
                }
            }
            lArray2[n5] = lArray[n3];
            sArray2[n5] = sArray[n3];
        }
        sArray2[n] = sArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = lArray2;
        this.value = sArray2;
    }

    public Long2ShortOpenCustomHashMap clone() {
        Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap;
        try {
            long2ShortOpenCustomHashMap = (Long2ShortOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2ShortOpenCustomHashMap.keys = null;
        long2ShortOpenCustomHashMap.values = null;
        long2ShortOpenCustomHashMap.entries = null;
        long2ShortOpenCustomHashMap.containsNullKey = this.containsNullKey;
        long2ShortOpenCustomHashMap.key = (long[])this.key.clone();
        long2ShortOpenCustomHashMap.value = (short[])this.value.clone();
        long2ShortOpenCustomHashMap.strategy = this.strategy;
        return long2ShortOpenCustomHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == 0L) {
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
        long[] lArray = this.key;
        short[] sArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeLong(lArray[n2]);
            objectOutputStream.writeShort(sArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new long[this.n + 1];
        long[] lArray = this.key;
        this.value = new short[this.n + 1];
        short[] sArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            long l = objectInputStream.readLong();
            short s = objectInputStream.readShort();
            if (this.strategy.equals(l, 0L)) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
                while (lArray[n2] != 0L) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            lArray[n2] = l;
            sArray[n2] = s;
        }
    }

    private void checkTable() {
    }

    public ObjectSet long2ShortEntrySet() {
        return this.long2ShortEntrySet();
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

    static short access$300(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
        return long2ShortOpenCustomHashMap.removeNullEntry();
    }

    static short access$400(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap, int n) {
        return long2ShortOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ShortIterator {
        final Long2ShortOpenCustomHashMap this$0;

        public ValueIterator(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
            this.this$0 = long2ShortOpenCustomHashMap;
            super(long2ShortOpenCustomHashMap, null);
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
    extends AbstractLongSet {
        final Long2ShortOpenCustomHashMap this$0;

        private KeySet(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
            this.this$0 = long2ShortOpenCustomHashMap;
        }

        @Override
        public LongIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(LongConsumer longConsumer) {
            if (this.this$0.containsNullKey) {
                longConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                long l = this.this$0.key[n];
                if (l == 0L) continue;
                longConsumer.accept(l);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(long l) {
            return this.this$0.containsKey(l);
        }

        @Override
        public boolean remove(long l) {
            int n = this.this$0.size;
            this.this$0.remove(l);
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

        KeySet(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap, 1 var2_2) {
            this(long2ShortOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongIterator {
        final Long2ShortOpenCustomHashMap this$0;

        public KeyIterator(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
            this.this$0 = long2ShortOpenCustomHashMap;
            super(long2ShortOpenCustomHashMap, null);
        }

        @Override
        public long nextLong() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Long2ShortMap.Entry>
    implements Long2ShortMap.FastEntrySet {
        final Long2ShortOpenCustomHashMap this$0;

        private MapEntrySet(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
            this.this$0 = long2ShortOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Long2ShortMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Long2ShortMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            long l = (Long)entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(l, 0L)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s;
            }
            long[] lArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
            long l2 = lArray[n];
            if (l2 == 0L) {
                return true;
            }
            if (this.this$0.strategy.equals(l, l2)) {
                return this.this$0.value[n] == s;
            }
            do {
                if ((l2 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(l, l2));
            return this.this$0.value[n] == s;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            long l = (Long)entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(l, 0L)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s) {
                    Long2ShortOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            long[] lArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
            long l2 = lArray[n];
            if (l2 == 0L) {
                return true;
            }
            if (this.this$0.strategy.equals(l2, l)) {
                if (this.this$0.value[n] == s) {
                    Long2ShortOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((l2 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(l2, l) || this.this$0.value[n] != s);
            Long2ShortOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Long2ShortMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractLong2ShortMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0L) continue;
                consumer.accept(new AbstractLong2ShortMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Long2ShortMap.Entry> consumer) {
            AbstractLong2ShortMap.BasicEntry basicEntry = new AbstractLong2ShortMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0L) continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap, 1 var2_2) {
            this(long2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Long2ShortMap.Entry> {
        private final MapEntry entry;
        final Long2ShortOpenCustomHashMap this$0;

        private FastEntryIterator(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
            this.this$0 = long2ShortOpenCustomHashMap;
            super(long2ShortOpenCustomHashMap, null);
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

        FastEntryIterator(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap, 1 var2_2) {
            this(long2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Long2ShortMap.Entry> {
        private MapEntry entry;
        final Long2ShortOpenCustomHashMap this$0;

        private EntryIterator(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
            this.this$0 = long2ShortOpenCustomHashMap;
            super(long2ShortOpenCustomHashMap, null);
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

        EntryIterator(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap, 1 var2_2) {
            this(long2ShortOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        LongArrayList wrapped;
        final Long2ShortOpenCustomHashMap this$0;

        private MapIterator(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
            this.this$0 = long2ShortOpenCustomHashMap;
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
            long[] lArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                long l = this.wrapped.getLong(-this.pos - 1);
                int n = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(l, lArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (lArray[this.pos] == 0L);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            long[] lArray = this.this$0.key;
            while (true) {
                long l;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((l = lArray[n]) == 0L) {
                        lArray[n2] = 0L;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new LongArrayList(2);
                    }
                    this.wrapped.add(lArray[n]);
                }
                lArray[n2] = l;
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
                this.this$0.remove(this.wrapped.getLong(-this.pos - 1));
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

        MapIterator(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap, 1 var2_2) {
            this(long2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Long2ShortMap.Entry,
    Map.Entry<Long, Short> {
        int index;
        final Long2ShortOpenCustomHashMap this$0;

        MapEntry(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap, int n) {
            this.this$0 = long2ShortOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Long2ShortOpenCustomHashMap long2ShortOpenCustomHashMap) {
            this.this$0 = long2ShortOpenCustomHashMap;
        }

        @Override
        public long getLongKey() {
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
        public Long getKey() {
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
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Long)entry.getKey()) && this.this$0.value[this.index] == (Short)entry.getValue();
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

