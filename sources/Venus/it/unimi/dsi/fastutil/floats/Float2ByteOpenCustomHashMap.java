/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ByteMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2ByteMap;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatHash;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
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
public class Float2ByteOpenCustomHashMap
extends AbstractFloat2ByteMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[] key;
    protected transient byte[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected FloatHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Float2ByteMap.FastEntrySet entries;
    protected transient FloatSet keys;
    protected transient ByteCollection values;

    public Float2ByteOpenCustomHashMap(int n, float f, FloatHash.Strategy strategy) {
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
        this.key = new float[this.n + 1];
        this.value = new byte[this.n + 1];
    }

    public Float2ByteOpenCustomHashMap(int n, FloatHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Float2ByteOpenCustomHashMap(FloatHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Float2ByteOpenCustomHashMap(Map<? extends Float, ? extends Byte> map, float f, FloatHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Float2ByteOpenCustomHashMap(Map<? extends Float, ? extends Byte> map, FloatHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Float2ByteOpenCustomHashMap(Float2ByteMap float2ByteMap, float f, FloatHash.Strategy strategy) {
        this(float2ByteMap.size(), f, strategy);
        this.putAll(float2ByteMap);
    }

    public Float2ByteOpenCustomHashMap(Float2ByteMap float2ByteMap, FloatHash.Strategy strategy) {
        this(float2ByteMap, 0.75f, strategy);
    }

    public Float2ByteOpenCustomHashMap(float[] fArray, byte[] byArray, float f, FloatHash.Strategy strategy) {
        this(fArray.length, f, strategy);
        if (fArray.length != byArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + fArray.length + " and " + byArray.length + ")");
        }
        for (int i = 0; i < fArray.length; ++i) {
            this.put(fArray[i], byArray[i]);
        }
    }

    public Float2ByteOpenCustomHashMap(float[] fArray, byte[] byArray, FloatHash.Strategy strategy) {
        this(fArray, byArray, 0.75f, strategy);
    }

    public FloatHash.Strategy strategy() {
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
    public void putAll(Map<? extends Float, ? extends Byte> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(float f) {
        if (this.strategy.equals(f, 0.0f)) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return -(n + 1);
        }
        if (this.strategy.equals(f, f2)) {
            return n;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return -(n + 1);
        } while (!this.strategy.equals(f, f2));
        return n;
    }

    private void insert(int n, float f, byte by) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = f;
        this.value[n] = by;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public byte put(float f, byte by) {
        int n = this.find(f);
        if (n < 0) {
            this.insert(-n - 1, f, by);
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

    public byte addTo(float f, byte by) {
        int n;
        if (this.strategy.equals(f, 0.0f)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, by);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            float[] fArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) != 0) {
                if (this.strategy.equals(f2, f)) {
                    return this.addToValue(n, by);
                }
                while (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(f2, f)) continue;
                    return this.addToValue(n, by);
                }
            }
        }
        this.key[n] = f;
        this.value[n] = (byte)(this.defRetValue + by);
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int n) {
        float[] fArray = this.key;
        while (true) {
            float f;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if (Float.floatToIntBits(f = fArray[n]) == 0) {
                    fArray[n2] = 0.0f;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            fArray[n2] = f;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public byte remove(float f) {
        if (this.strategy.equals(f, 0.0f)) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(f, f2)) {
            return this.removeEntry(n);
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(f, f2));
        return this.removeEntry(n);
    }

    @Override
    public byte get(float f) {
        if (this.strategy.equals(f, 0.0f)) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return this.defRetValue;
        }
        if (this.strategy.equals(f, f2)) {
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (!this.strategy.equals(f, f2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(float f) {
        if (this.strategy.equals(f, 0.0f)) {
            return this.containsNullKey;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (this.strategy.equals(f, f2)) {
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(f, f2));
        return false;
    }

    @Override
    public boolean containsValue(byte by) {
        byte[] byArray = this.value;
        float[] fArray = this.key;
        if (this.containsNullKey && byArray[this.n] == by) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) == 0 || byArray[n] != by) continue;
            return false;
        }
        return true;
    }

    @Override
    public byte getOrDefault(float f, byte by) {
        if (this.strategy.equals(f, 0.0f)) {
            return this.containsNullKey ? this.value[this.n] : by;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return by;
        }
        if (this.strategy.equals(f, f2)) {
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return by;
        } while (!this.strategy.equals(f, f2));
        return this.value[n];
    }

    @Override
    public byte putIfAbsent(float f, byte by) {
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, f, by);
        return this.defRetValue;
    }

    @Override
    public boolean remove(float f, byte by) {
        if (this.strategy.equals(f, 0.0f)) {
            if (this.containsNullKey && by == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (this.strategy.equals(f, f2) && by == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(f, f2) || by != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(float f, byte by, byte by2) {
        int n = this.find(f);
        if (n < 0 || by != this.value[n]) {
            return true;
        }
        this.value[n] = by2;
        return false;
    }

    @Override
    public byte replace(float f, byte by) {
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        byte by2 = this.value[n];
        this.value[n] = by;
        return by2;
    }

    @Override
    public byte computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        byte by = SafeMath.safeIntToByte(doubleToIntFunction.applyAsInt(f));
        this.insert(-n - 1, f, by);
        return by;
    }

    @Override
    public byte computeIfAbsentNullable(float f, DoubleFunction<? extends Byte> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        Byte by = doubleFunction.apply(f);
        if (by == null) {
            return this.defRetValue;
        }
        byte by2 = by;
        this.insert(-n - 1, f, by2);
        return by2;
    }

    @Override
    public byte computeIfPresent(float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        Byte by = biFunction.apply(Float.valueOf(f), (Byte)this.value[n]);
        if (by == null) {
            if (this.strategy.equals(f, 0.0f)) {
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
    public byte compute(float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        Byte by = biFunction.apply(Float.valueOf(f), n >= 0 ? Byte.valueOf(this.value[n]) : null);
        if (by == null) {
            if (n >= 0) {
                if (this.strategy.equals(f, 0.0f)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        byte by2 = by;
        if (n < 0) {
            this.insert(-n - 1, f, by2);
            return by2;
        }
        this.value[n] = by2;
        return this.value[n];
    }

    @Override
    public byte merge(float f, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            this.insert(-n - 1, f, by);
            return by;
        }
        Byte by2 = biFunction.apply((Byte)this.value[n], (Byte)by);
        if (by2 == null) {
            if (this.strategy.equals(f, 0.0f)) {
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
        Arrays.fill(this.key, 0.0f);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Float2ByteMap.FastEntrySet float2ByteEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public FloatSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ByteCollection values() {
        if (this.values == null) {
            this.values = new AbstractByteCollection(this){
                final Float2ByteOpenCustomHashMap this$0;
                {
                    this.this$0 = float2ByteOpenCustomHashMap;
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
                        if (Float.floatToIntBits(this.this$0.key[n]) == 0) continue;
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
        float[] fArray = this.key;
        byte[] byArray = this.value;
        int n2 = n - 1;
        float[] fArray2 = new float[n + 1];
        byte[] byArray2 = new byte[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (Float.floatToIntBits(fArray[--n3]) == 0) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(fArray[n3])) & n2;
            if (Float.floatToIntBits(fArray2[n5]) != 0) {
                while (Float.floatToIntBits(fArray2[n5 = n5 + 1 & n2]) != 0) {
                }
            }
            fArray2[n5] = fArray[n3];
            byArray2[n5] = byArray[n3];
        }
        byArray2[n] = byArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = fArray2;
        this.value = byArray2;
    }

    public Float2ByteOpenCustomHashMap clone() {
        Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap;
        try {
            float2ByteOpenCustomHashMap = (Float2ByteOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2ByteOpenCustomHashMap.keys = null;
        float2ByteOpenCustomHashMap.values = null;
        float2ByteOpenCustomHashMap.entries = null;
        float2ByteOpenCustomHashMap.containsNullKey = this.containsNullKey;
        float2ByteOpenCustomHashMap.key = (float[])this.key.clone();
        float2ByteOpenCustomHashMap.value = (byte[])this.value.clone();
        float2ByteOpenCustomHashMap.strategy = this.strategy;
        return float2ByteOpenCustomHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (Float.floatToIntBits(this.key[n3]) == 0) {
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
        float[] fArray = this.key;
        byte[] byArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeFloat(fArray[n2]);
            objectOutputStream.writeByte(byArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new float[this.n + 1];
        float[] fArray = this.key;
        this.value = new byte[this.n + 1];
        byte[] byArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            float f = objectInputStream.readFloat();
            byte by = objectInputStream.readByte();
            if (this.strategy.equals(f, 0.0f)) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
                while (Float.floatToIntBits(fArray[n2]) != 0) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            fArray[n2] = f;
            byArray[n2] = by;
        }
    }

    private void checkTable() {
    }

    public ObjectSet float2ByteEntrySet() {
        return this.float2ByteEntrySet();
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

    static byte access$300(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
        return float2ByteOpenCustomHashMap.removeNullEntry();
    }

    static byte access$400(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap, int n) {
        return float2ByteOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ByteIterator {
        final Float2ByteOpenCustomHashMap this$0;

        public ValueIterator(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
            this.this$0 = float2ByteOpenCustomHashMap;
            super(float2ByteOpenCustomHashMap, null);
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
    extends AbstractFloatSet {
        final Float2ByteOpenCustomHashMap this$0;

        private KeySet(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
            this.this$0 = float2ByteOpenCustomHashMap;
        }

        @Override
        public FloatIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(DoubleConsumer doubleConsumer) {
            if (this.this$0.containsNullKey) {
                doubleConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                float f = this.this$0.key[n];
                if (Float.floatToIntBits(f) == 0) continue;
                doubleConsumer.accept(f);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(float f) {
            return this.this$0.containsKey(f);
        }

        @Override
        public boolean remove(float f) {
            int n = this.this$0.size;
            this.this$0.remove(f);
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

        KeySet(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap, 1 var2_2) {
            this(float2ByteOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements FloatIterator {
        final Float2ByteOpenCustomHashMap this$0;

        public KeyIterator(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
            this.this$0 = float2ByteOpenCustomHashMap;
            super(float2ByteOpenCustomHashMap, null);
        }

        @Override
        public float nextFloat() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Float2ByteMap.Entry>
    implements Float2ByteMap.FastEntrySet {
        final Float2ByteOpenCustomHashMap this$0;

        private MapEntrySet(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
            this.this$0 = float2ByteOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Float2ByteMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Float2ByteMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            byte by = (Byte)entry.getValue();
            if (this.this$0.strategy.equals(f, 0.0f)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == by;
            }
            float[] fArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(f)) & this.this$0.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(f, f2)) {
                return this.this$0.value[n] == by;
            }
            do {
                if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(f, f2));
            return this.this$0.value[n] == by;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            byte by = (Byte)entry.getValue();
            if (this.this$0.strategy.equals(f, 0.0f)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == by) {
                    Float2ByteOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            float[] fArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(f)) & this.this$0.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(f2, f)) {
                if (this.this$0.value[n] == by) {
                    Float2ByteOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(f2, f) || this.this$0.value[n] != by);
            Float2ByteOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Float2ByteMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractFloat2ByteMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (Float.floatToIntBits(this.this$0.key[n]) == 0) continue;
                consumer.accept(new AbstractFloat2ByteMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Float2ByteMap.Entry> consumer) {
            AbstractFloat2ByteMap.BasicEntry basicEntry = new AbstractFloat2ByteMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (Float.floatToIntBits(this.this$0.key[n]) == 0) continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap, 1 var2_2) {
            this(float2ByteOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Float2ByteMap.Entry> {
        private final MapEntry entry;
        final Float2ByteOpenCustomHashMap this$0;

        private FastEntryIterator(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
            this.this$0 = float2ByteOpenCustomHashMap;
            super(float2ByteOpenCustomHashMap, null);
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

        FastEntryIterator(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap, 1 var2_2) {
            this(float2ByteOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Float2ByteMap.Entry> {
        private MapEntry entry;
        final Float2ByteOpenCustomHashMap this$0;

        private EntryIterator(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
            this.this$0 = float2ByteOpenCustomHashMap;
            super(float2ByteOpenCustomHashMap, null);
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

        EntryIterator(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap, 1 var2_2) {
            this(float2ByteOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        FloatArrayList wrapped;
        final Float2ByteOpenCustomHashMap this$0;

        private MapIterator(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
            this.this$0 = float2ByteOpenCustomHashMap;
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
            float[] fArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                float f = this.wrapped.getFloat(-this.pos - 1);
                int n = HashCommon.mix(this.this$0.strategy.hashCode(f)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(f, fArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (Float.floatToIntBits(fArray[this.pos]) == 0);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            float[] fArray = this.this$0.key;
            while (true) {
                float f;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if (Float.floatToIntBits(f = fArray[n]) == 0) {
                        fArray[n2] = 0.0f;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(f)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new FloatArrayList(2);
                    }
                    this.wrapped.add(fArray[n]);
                }
                fArray[n2] = f;
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
                this.this$0.remove(this.wrapped.getFloat(-this.pos - 1));
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

        MapIterator(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap, 1 var2_2) {
            this(float2ByteOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Float2ByteMap.Entry,
    Map.Entry<Float, Byte> {
        int index;
        final Float2ByteOpenCustomHashMap this$0;

        MapEntry(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap, int n) {
            this.this$0 = float2ByteOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Float2ByteOpenCustomHashMap float2ByteOpenCustomHashMap) {
            this.this$0 = float2ByteOpenCustomHashMap;
        }

        @Override
        public float getFloatKey() {
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
        public Float getKey() {
            return Float.valueOf(this.this$0.key[this.index]);
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
            return this.this$0.strategy.equals(this.this$0.key[this.index], ((Float)entry.getKey()).floatValue()) && this.this$0.value[this.index] == (Byte)entry.getValue();
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

