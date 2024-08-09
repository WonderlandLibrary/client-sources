/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.AbstractByte2ShortMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteHash;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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
public class Byte2ShortOpenCustomHashMap
extends AbstractByte2ShortMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient byte[] key;
    protected transient short[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected ByteHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Byte2ShortMap.FastEntrySet entries;
    protected transient ByteSet keys;
    protected transient ShortCollection values;

    public Byte2ShortOpenCustomHashMap(int n, float f, ByteHash.Strategy strategy) {
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
        this.key = new byte[this.n + 1];
        this.value = new short[this.n + 1];
    }

    public Byte2ShortOpenCustomHashMap(int n, ByteHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Byte2ShortOpenCustomHashMap(ByteHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Byte2ShortOpenCustomHashMap(Map<? extends Byte, ? extends Short> map, float f, ByteHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Byte2ShortOpenCustomHashMap(Map<? extends Byte, ? extends Short> map, ByteHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Byte2ShortOpenCustomHashMap(Byte2ShortMap byte2ShortMap, float f, ByteHash.Strategy strategy) {
        this(byte2ShortMap.size(), f, strategy);
        this.putAll(byte2ShortMap);
    }

    public Byte2ShortOpenCustomHashMap(Byte2ShortMap byte2ShortMap, ByteHash.Strategy strategy) {
        this(byte2ShortMap, 0.75f, strategy);
    }

    public Byte2ShortOpenCustomHashMap(byte[] byArray, short[] sArray, float f, ByteHash.Strategy strategy) {
        this(byArray.length, f, strategy);
        if (byArray.length != sArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + byArray.length + " and " + sArray.length + ")");
        }
        for (int i = 0; i < byArray.length; ++i) {
            this.put(byArray[i], sArray[i]);
        }
    }

    public Byte2ShortOpenCustomHashMap(byte[] byArray, short[] sArray, ByteHash.Strategy strategy) {
        this(byArray, sArray, 0.75f, strategy);
    }

    public ByteHash.Strategy strategy() {
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
    public void putAll(Map<? extends Byte, ? extends Short> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(byte by) {
        if (this.strategy.equals(by, (byte)0)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return -(n + 1);
        }
        if (this.strategy.equals(by, by2)) {
            return n;
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return -(n + 1);
        } while (!this.strategy.equals(by, by2));
        return n;
    }

    private void insert(int n, byte by, short s) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = by;
        this.value[n] = s;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public short put(byte by, short s) {
        int n = this.find(by);
        if (n < 0) {
            this.insert(-n - 1, by, s);
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

    public short addTo(byte by, short s) {
        int n;
        if (this.strategy.equals(by, (byte)0)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, s);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            byte[] byArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
            byte by2 = byArray[n];
            if (by2 != 0) {
                if (this.strategy.equals(by2, by)) {
                    return this.addToValue(n, s);
                }
                while ((by2 = byArray[n = n + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(by2, by)) continue;
                    return this.addToValue(n, s);
                }
            }
        }
        this.key[n] = by;
        this.value[n] = (short)(this.defRetValue + s);
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int n) {
        byte[] byArray = this.key;
        while (true) {
            byte by;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((by = byArray[n]) == 0) {
                    byArray[n2] = 0;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            byArray[n2] = by;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public short remove(byte by) {
        if (this.strategy.equals(by, (byte)0)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(by, by2)) {
            return this.removeEntry(n);
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(by, by2));
        return this.removeEntry(n);
    }

    @Override
    public short get(byte by) {
        if (this.strategy.equals(by, (byte)0)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(by, by2)) {
            return this.value[n];
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(by, by2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(byte by) {
        if (this.strategy.equals(by, (byte)0)) {
            return this.containsNullKey;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return true;
        }
        if (this.strategy.equals(by, by2)) {
            return false;
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(by, by2));
        return false;
    }

    @Override
    public boolean containsValue(short s) {
        short[] sArray = this.value;
        byte[] byArray = this.key;
        if (this.containsNullKey && sArray[this.n] == s) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (byArray[n] == 0 || sArray[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public short getOrDefault(byte by, short s) {
        if (this.strategy.equals(by, (byte)0)) {
            return this.containsNullKey ? this.value[this.n] : s;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return s;
        }
        if (this.strategy.equals(by, by2)) {
            return this.value[n];
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return s;
        } while (!this.strategy.equals(by, by2));
        return this.value[n];
    }

    @Override
    public short putIfAbsent(byte by, short s) {
        int n = this.find(by);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, by, s);
        return this.defRetValue;
    }

    @Override
    public boolean remove(byte by, short s) {
        if (this.strategy.equals(by, (byte)0)) {
            if (this.containsNullKey && s == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        byte[] byArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
        byte by2 = byArray[n];
        if (by2 == 0) {
            return true;
        }
        if (this.strategy.equals(by, by2) && s == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((by2 = byArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(by, by2) || s != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(byte by, short s, short s2) {
        int n = this.find(by);
        if (n < 0 || s != this.value[n]) {
            return true;
        }
        this.value[n] = s2;
        return false;
    }

    @Override
    public short replace(byte by, short s) {
        int n = this.find(by);
        if (n < 0) {
            return this.defRetValue;
        }
        short s2 = this.value[n];
        this.value[n] = s;
        return s2;
    }

    @Override
    public short computeIfAbsent(byte by, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n = this.find(by);
        if (n >= 0) {
            return this.value[n];
        }
        short s = SafeMath.safeIntToShort(intUnaryOperator.applyAsInt(by));
        this.insert(-n - 1, by, s);
        return s;
    }

    @Override
    public short computeIfAbsentNullable(byte by, IntFunction<? extends Short> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(by);
        if (n >= 0) {
            return this.value[n];
        }
        Short s = intFunction.apply(by);
        if (s == null) {
            return this.defRetValue;
        }
        short s2 = s;
        this.insert(-n - 1, by, s2);
        return s2;
    }

    @Override
    public short computeIfPresent(byte by, BiFunction<? super Byte, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(by);
        if (n < 0) {
            return this.defRetValue;
        }
        Short s = biFunction.apply((Byte)by, (Short)this.value[n]);
        if (s == null) {
            if (this.strategy.equals(by, (byte)0)) {
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
    public short compute(byte by, BiFunction<? super Byte, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(by);
        Short s = biFunction.apply((Byte)by, n >= 0 ? Short.valueOf(this.value[n]) : null);
        if (s == null) {
            if (n >= 0) {
                if (this.strategy.equals(by, (byte)0)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        short s2 = s;
        if (n < 0) {
            this.insert(-n - 1, by, s2);
            return s2;
        }
        this.value[n] = s2;
        return this.value[n];
    }

    @Override
    public short merge(byte by, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(by);
        if (n < 0) {
            this.insert(-n - 1, by, s);
            return s;
        }
        Short s2 = biFunction.apply((Short)this.value[n], (Short)s);
        if (s2 == null) {
            if (this.strategy.equals(by, (byte)0)) {
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
        Arrays.fill(this.key, (byte)0);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Byte2ShortMap.FastEntrySet byte2ShortEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ByteSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ShortCollection values() {
        if (this.values == null) {
            this.values = new AbstractShortCollection(this){
                final Byte2ShortOpenCustomHashMap this$0;
                {
                    this.this$0 = byte2ShortOpenCustomHashMap;
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
        byte[] byArray = this.key;
        short[] sArray = this.value;
        int n2 = n - 1;
        byte[] byArray2 = new byte[n + 1];
        short[] sArray2 = new short[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (byArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(byArray[n3])) & n2;
            if (byArray2[n5] != 0) {
                while (byArray2[n5 = n5 + 1 & n2] != 0) {
                }
            }
            byArray2[n5] = byArray[n3];
            sArray2[n5] = sArray[n3];
        }
        sArray2[n] = sArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = byArray2;
        this.value = sArray2;
    }

    public Byte2ShortOpenCustomHashMap clone() {
        Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap;
        try {
            byte2ShortOpenCustomHashMap = (Byte2ShortOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2ShortOpenCustomHashMap.keys = null;
        byte2ShortOpenCustomHashMap.values = null;
        byte2ShortOpenCustomHashMap.entries = null;
        byte2ShortOpenCustomHashMap.containsNullKey = this.containsNullKey;
        byte2ShortOpenCustomHashMap.key = (byte[])this.key.clone();
        byte2ShortOpenCustomHashMap.value = (short[])this.value.clone();
        byte2ShortOpenCustomHashMap.strategy = this.strategy;
        return byte2ShortOpenCustomHashMap;
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
        byte[] byArray = this.key;
        short[] sArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeByte(byArray[n2]);
            objectOutputStream.writeShort(sArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new byte[this.n + 1];
        byte[] byArray = this.key;
        this.value = new short[this.n + 1];
        short[] sArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            byte by = objectInputStream.readByte();
            short s = objectInputStream.readShort();
            if (this.strategy.equals(by, (byte)0)) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(by)) & this.mask;
                while (byArray[n2] != 0) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            byArray[n2] = by;
            sArray[n2] = s;
        }
    }

    private void checkTable() {
    }

    public ObjectSet byte2ShortEntrySet() {
        return this.byte2ShortEntrySet();
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

    static short access$300(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
        return byte2ShortOpenCustomHashMap.removeNullEntry();
    }

    static short access$400(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap, int n) {
        return byte2ShortOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ShortIterator {
        final Byte2ShortOpenCustomHashMap this$0;

        public ValueIterator(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
            this.this$0 = byte2ShortOpenCustomHashMap;
            super(byte2ShortOpenCustomHashMap, null);
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
    extends AbstractByteSet {
        final Byte2ShortOpenCustomHashMap this$0;

        private KeySet(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
            this.this$0 = byte2ShortOpenCustomHashMap;
        }

        @Override
        public ByteIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                byte by = this.this$0.key[n];
                if (by == 0) continue;
                intConsumer.accept(by);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(byte by) {
            return this.this$0.containsKey(by);
        }

        @Override
        public boolean remove(byte by) {
            int n = this.this$0.size;
            this.this$0.remove(by);
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

        KeySet(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap, 1 var2_2) {
            this(byte2ShortOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ByteIterator {
        final Byte2ShortOpenCustomHashMap this$0;

        public KeyIterator(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
            this.this$0 = byte2ShortOpenCustomHashMap;
            super(byte2ShortOpenCustomHashMap, null);
        }

        @Override
        public byte nextByte() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Byte2ShortMap.Entry>
    implements Byte2ShortMap.FastEntrySet {
        final Byte2ShortOpenCustomHashMap this$0;

        private MapEntrySet(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
            this.this$0 = byte2ShortOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Byte2ShortMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Byte2ShortMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(by, (byte)0)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s;
            }
            byte[] byArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(by)) & this.this$0.mask;
            byte by2 = byArray[n];
            if (by2 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(by, by2)) {
                return this.this$0.value[n] == s;
            }
            do {
                if ((by2 = byArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(by, by2));
            return this.this$0.value[n] == s;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            short s = (Short)entry.getValue();
            if (this.this$0.strategy.equals(by, (byte)0)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == s) {
                    Byte2ShortOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            byte[] byArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(by)) & this.this$0.mask;
            byte by2 = byArray[n];
            if (by2 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(by2, by)) {
                if (this.this$0.value[n] == s) {
                    Byte2ShortOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((by2 = byArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(by2, by) || this.this$0.value[n] != s);
            Byte2ShortOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Byte2ShortMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractByte2ShortMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new AbstractByte2ShortMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Byte2ShortMap.Entry> consumer) {
            AbstractByte2ShortMap.BasicEntry basicEntry = new AbstractByte2ShortMap.BasicEntry();
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

        MapEntrySet(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap, 1 var2_2) {
            this(byte2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Byte2ShortMap.Entry> {
        private final MapEntry entry;
        final Byte2ShortOpenCustomHashMap this$0;

        private FastEntryIterator(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
            this.this$0 = byte2ShortOpenCustomHashMap;
            super(byte2ShortOpenCustomHashMap, null);
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

        FastEntryIterator(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap, 1 var2_2) {
            this(byte2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Byte2ShortMap.Entry> {
        private MapEntry entry;
        final Byte2ShortOpenCustomHashMap this$0;

        private EntryIterator(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
            this.this$0 = byte2ShortOpenCustomHashMap;
            super(byte2ShortOpenCustomHashMap, null);
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

        EntryIterator(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap, 1 var2_2) {
            this(byte2ShortOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ByteArrayList wrapped;
        final Byte2ShortOpenCustomHashMap this$0;

        private MapIterator(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
            this.this$0 = byte2ShortOpenCustomHashMap;
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
            byte[] byArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                byte by = this.wrapped.getByte(-this.pos - 1);
                int n = HashCommon.mix(this.this$0.strategy.hashCode(by)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(by, byArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (byArray[this.pos] == 0);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            byte[] byArray = this.this$0.key;
            while (true) {
                byte by;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((by = byArray[n]) == 0) {
                        byArray[n2] = 0;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(by)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ByteArrayList(2);
                    }
                    this.wrapped.add(byArray[n]);
                }
                byArray[n2] = by;
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
                this.this$0.remove(this.wrapped.getByte(-this.pos - 1));
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

        MapIterator(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap, 1 var2_2) {
            this(byte2ShortOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Byte2ShortMap.Entry,
    Map.Entry<Byte, Short> {
        int index;
        final Byte2ShortOpenCustomHashMap this$0;

        MapEntry(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap, int n) {
            this.this$0 = byte2ShortOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Byte2ShortOpenCustomHashMap byte2ShortOpenCustomHashMap) {
            this.this$0 = byte2ShortOpenCustomHashMap;
        }

        @Override
        public byte getByteKey() {
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
        public Byte getKey() {
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
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Byte)entry.getKey()) && this.this$0.value[this.index] == (Short)entry.getValue();
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

