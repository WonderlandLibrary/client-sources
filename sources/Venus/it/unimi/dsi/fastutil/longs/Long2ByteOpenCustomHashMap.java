/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2ByteMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongHash;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
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
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Long2ByteOpenCustomHashMap
extends AbstractLong2ByteMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient byte[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected LongHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Long2ByteMap.FastEntrySet entries;
    protected transient LongSet keys;
    protected transient ByteCollection values;

    public Long2ByteOpenCustomHashMap(int n, float f, LongHash.Strategy strategy) {
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
        this.value = new byte[this.n + 1];
    }

    public Long2ByteOpenCustomHashMap(int n, LongHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Long2ByteOpenCustomHashMap(LongHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Long2ByteOpenCustomHashMap(Map<? extends Long, ? extends Byte> map, float f, LongHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Long2ByteOpenCustomHashMap(Map<? extends Long, ? extends Byte> map, LongHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Long2ByteOpenCustomHashMap(Long2ByteMap long2ByteMap, float f, LongHash.Strategy strategy) {
        this(long2ByteMap.size(), f, strategy);
        this.putAll(long2ByteMap);
    }

    public Long2ByteOpenCustomHashMap(Long2ByteMap long2ByteMap, LongHash.Strategy strategy) {
        this(long2ByteMap, 0.75f, strategy);
    }

    public Long2ByteOpenCustomHashMap(long[] lArray, byte[] byArray, float f, LongHash.Strategy strategy) {
        this(lArray.length, f, strategy);
        if (lArray.length != byArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + lArray.length + " and " + byArray.length + ")");
        }
        for (int i = 0; i < lArray.length; ++i) {
            this.put(lArray[i], byArray[i]);
        }
    }

    public Long2ByteOpenCustomHashMap(long[] lArray, byte[] byArray, LongHash.Strategy strategy) {
        this(lArray, byArray, 0.75f, strategy);
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

    private byte removeEntry(int n) {
        byte by = this.value[n];
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return by;
    }

    private byte removeNullEntry() {
        this.containsNullKey = false;
        byte by = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return by;
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Byte> map) {
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

    private void insert(int n, long l, byte by) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = l;
        this.value[n] = by;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public byte put(long l, byte by) {
        int n = this.find(l);
        if (n < 0) {
            this.insert(-n - 1, l, by);
            return this.defRetValue;
        }
        byte by2 = this.value[n];
        this.value[n] = by;
        return by2;
    }

    private byte addToValue(int n, byte by) {
        byte by2 = this.value[n];
        this.value[n] = (byte)(by2 + by);
        return by2;
    }

    public byte addTo(long l, byte by) {
        int n;
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, by);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            long[] lArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
            long l2 = lArray[n];
            if (l2 != 0L) {
                if (this.strategy.equals(l2, l)) {
                    return this.addToValue(n, by);
                }
                while ((l2 = lArray[n = n + 1 & this.mask]) != 0L) {
                    if (!this.strategy.equals(l2, l)) continue;
                    return this.addToValue(n, by);
                }
            }
        }
        this.key[n] = l;
        this.value[n] = (byte)(this.defRetValue + by);
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
    public byte remove(long l) {
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
    public byte get(long l) {
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
    public boolean containsValue(byte by) {
        byte[] byArray = this.value;
        long[] lArray = this.key;
        if (this.containsNullKey && byArray[this.n] == by) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (lArray[n] == 0L || byArray[n] != by) continue;
            return false;
        }
        return true;
    }

    @Override
    public byte getOrDefault(long l, byte by) {
        if (this.strategy.equals(l, 0L)) {
            return this.containsNullKey ? this.value[this.n] : by;
        }
        long[] lArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(l)) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return by;
        }
        if (this.strategy.equals(l, l2)) {
            return this.value[n];
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return by;
        } while (!this.strategy.equals(l, l2));
        return this.value[n];
    }

    @Override
    public byte putIfAbsent(long l, byte by) {
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, l, by);
        return this.defRetValue;
    }

    @Override
    public boolean remove(long l, byte by) {
        if (this.strategy.equals(l, 0L)) {
            if (this.containsNullKey && by == this.value[this.n]) {
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
        if (this.strategy.equals(l, l2) && by == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (!this.strategy.equals(l, l2) || by != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(long l, byte by, byte by2) {
        int n = this.find(l);
        if (n < 0 || by != this.value[n]) {
            return true;
        }
        this.value[n] = by2;
        return false;
    }

    @Override
    public byte replace(long l, byte by) {
        int n = this.find(l);
        if (n < 0) {
            return this.defRetValue;
        }
        byte by2 = this.value[n];
        this.value[n] = by;
        return by2;
    }

    @Override
    public byte computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        byte by = SafeMath.safeIntToByte(longToIntFunction.applyAsInt(l));
        this.insert(-n - 1, l, by);
        return by;
    }

    @Override
    public byte computeIfAbsentNullable(long l, LongFunction<? extends Byte> longFunction) {
        Objects.requireNonNull(longFunction);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        Byte by = longFunction.apply(l);
        if (by == null) {
            return this.defRetValue;
        }
        byte by2 = by;
        this.insert(-n - 1, l, by2);
        return by2;
    }

    @Override
    public byte computeIfPresent(long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0) {
            return this.defRetValue;
        }
        Byte by = biFunction.apply((Long)l, (Byte)this.value[n]);
        if (by == null) {
            if (this.strategy.equals(l, 0L)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = by;
        return this.value[n];
    }

    @Override
    public byte compute(long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        Byte by = biFunction.apply((Long)l, n >= 0 ? Byte.valueOf(this.value[n]) : null);
        if (by == null) {
            if (n >= 0) {
                if (this.strategy.equals(l, 0L)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        byte by2 = by;
        if (n < 0) {
            this.insert(-n - 1, l, by2);
            return by2;
        }
        this.value[n] = by2;
        return this.value[n];
    }

    @Override
    public byte merge(long l, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0) {
            this.insert(-n - 1, l, by);
            return by;
        }
        Byte by2 = biFunction.apply((Byte)this.value[n], (Byte)by);
        if (by2 == null) {
            if (this.strategy.equals(l, 0L)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = by2;
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

    public Long2ByteMap.FastEntrySet long2ByteEntrySet() {
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
    public ByteCollection values() {
        if (this.values == null) {
            this.values = new AbstractByteCollection(this){
                final Long2ByteOpenCustomHashMap this$0;
                {
                    this.this$0 = long2ByteOpenCustomHashMap;
                }

                @Override
                public ByteIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(byte by) {
                    return this.this$0.containsValue(by);
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
        byte[] byArray = this.value;
        int n2 = n - 1;
        long[] lArray2 = new long[n + 1];
        byte[] byArray2 = new byte[n + 1];
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
            byArray2[n5] = byArray[n3];
        }
        byArray2[n] = byArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = lArray2;
        this.value = byArray2;
    }

    public Long2ByteOpenCustomHashMap clone() {
        Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap;
        try {
            long2ByteOpenCustomHashMap = (Long2ByteOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2ByteOpenCustomHashMap.keys = null;
        long2ByteOpenCustomHashMap.values = null;
        long2ByteOpenCustomHashMap.entries = null;
        long2ByteOpenCustomHashMap.containsNullKey = this.containsNullKey;
        long2ByteOpenCustomHashMap.key = (long[])this.key.clone();
        long2ByteOpenCustomHashMap.value = (byte[])this.value.clone();
        long2ByteOpenCustomHashMap.strategy = this.strategy;
        return long2ByteOpenCustomHashMap;
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
        byte[] byArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeLong(lArray[n2]);
            objectOutputStream.writeByte(byArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new long[this.n + 1];
        long[] lArray = this.key;
        this.value = new byte[this.n + 1];
        byte[] byArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            long l = objectInputStream.readLong();
            byte by = objectInputStream.readByte();
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
            byArray[n2] = by;
        }
    }

    private void checkTable() {
    }

    public ObjectSet long2ByteEntrySet() {
        return this.long2ByteEntrySet();
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

    static byte access$300(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
        return long2ByteOpenCustomHashMap.removeNullEntry();
    }

    static byte access$400(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap, int n) {
        return long2ByteOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ByteIterator {
        final Long2ByteOpenCustomHashMap this$0;

        public ValueIterator(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
            this.this$0 = long2ByteOpenCustomHashMap;
            super(long2ByteOpenCustomHashMap, null);
        }

        @Override
        public byte nextByte() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractLongSet {
        final Long2ByteOpenCustomHashMap this$0;

        private KeySet(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
            this.this$0 = long2ByteOpenCustomHashMap;
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

        KeySet(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap, 1 var2_2) {
            this(long2ByteOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongIterator {
        final Long2ByteOpenCustomHashMap this$0;

        public KeyIterator(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
            this.this$0 = long2ByteOpenCustomHashMap;
            super(long2ByteOpenCustomHashMap, null);
        }

        @Override
        public long nextLong() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Long2ByteMap.Entry>
    implements Long2ByteMap.FastEntrySet {
        final Long2ByteOpenCustomHashMap this$0;

        private MapEntrySet(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
            this.this$0 = long2ByteOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Long2ByteMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Long2ByteMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            long l = (Long)entry.getKey();
            byte by = (Byte)entry.getValue();
            if (this.this$0.strategy.equals(l, 0L)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == by;
            }
            long[] lArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(l)) & this.this$0.mask;
            long l2 = lArray[n];
            if (l2 == 0L) {
                return true;
            }
            if (this.this$0.strategy.equals(l, l2)) {
                return this.this$0.value[n] == by;
            }
            do {
                if ((l2 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(l, l2));
            return this.this$0.value[n] == by;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            long l = (Long)entry.getKey();
            byte by = (Byte)entry.getValue();
            if (this.this$0.strategy.equals(l, 0L)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == by) {
                    Long2ByteOpenCustomHashMap.access$300(this.this$0);
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
                if (this.this$0.value[n] == by) {
                    Long2ByteOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((l2 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (!this.this$0.strategy.equals(l2, l) || this.this$0.value[n] != by);
            Long2ByteOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Long2ByteMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractLong2ByteMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0L) continue;
                consumer.accept(new AbstractLong2ByteMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Long2ByteMap.Entry> consumer) {
            AbstractLong2ByteMap.BasicEntry basicEntry = new AbstractLong2ByteMap.BasicEntry();
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

        MapEntrySet(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap, 1 var2_2) {
            this(long2ByteOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Long2ByteMap.Entry> {
        private final MapEntry entry;
        final Long2ByteOpenCustomHashMap this$0;

        private FastEntryIterator(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
            this.this$0 = long2ByteOpenCustomHashMap;
            super(long2ByteOpenCustomHashMap, null);
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

        FastEntryIterator(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap, 1 var2_2) {
            this(long2ByteOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Long2ByteMap.Entry> {
        private MapEntry entry;
        final Long2ByteOpenCustomHashMap this$0;

        private EntryIterator(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
            this.this$0 = long2ByteOpenCustomHashMap;
            super(long2ByteOpenCustomHashMap, null);
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

        EntryIterator(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap, 1 var2_2) {
            this(long2ByteOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        LongArrayList wrapped;
        final Long2ByteOpenCustomHashMap this$0;

        private MapIterator(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
            this.this$0 = long2ByteOpenCustomHashMap;
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

        MapIterator(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap, 1 var2_2) {
            this(long2ByteOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Long2ByteMap.Entry,
    Map.Entry<Long, Byte> {
        int index;
        final Long2ByteOpenCustomHashMap this$0;

        MapEntry(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap, int n) {
            this.this$0 = long2ByteOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Long2ByteOpenCustomHashMap long2ByteOpenCustomHashMap) {
            this.this$0 = long2ByteOpenCustomHashMap;
        }

        @Override
        public long getLongKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public byte getByteValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public byte setValue(byte by) {
            byte by2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = by;
            return by2;
        }

        @Override
        @Deprecated
        public Long getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Byte getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Byte setValue(Byte by) {
            return this.setValue((byte)by);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Long)entry.getKey()) && this.this$0.value[this.index] == (Byte)entry.getValue();
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
            return this.setValue((Byte)object);
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

