/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2FloatMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
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
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Short2FloatOpenCustomHashMap
extends AbstractShort2FloatMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient short[] key;
    protected transient float[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected ShortHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Short2FloatMap.FastEntrySet entries;
    protected transient ShortSet keys;
    protected transient FloatCollection values;

    public Short2FloatOpenCustomHashMap(int n, float f, ShortHash.Strategy strategy) {
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
        this.value = new float[this.n + 1];
    }

    public Short2FloatOpenCustomHashMap(int n, ShortHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Short2FloatOpenCustomHashMap(ShortHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Short2FloatOpenCustomHashMap(Map<? extends Short, ? extends Float> map, float f, ShortHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Short2FloatOpenCustomHashMap(Map<? extends Short, ? extends Float> map, ShortHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Short2FloatOpenCustomHashMap(Short2FloatMap short2FloatMap, float f, ShortHash.Strategy strategy) {
        this(short2FloatMap.size(), f, strategy);
        this.putAll(short2FloatMap);
    }

    public Short2FloatOpenCustomHashMap(Short2FloatMap short2FloatMap, ShortHash.Strategy strategy) {
        this(short2FloatMap, 0.75f, strategy);
    }

    public Short2FloatOpenCustomHashMap(short[] sArray, float[] fArray, float f, ShortHash.Strategy strategy) {
        this(sArray.length, f, strategy);
        if (sArray.length != fArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + sArray.length + " and " + fArray.length + ")");
        }
        for (int i = 0; i < sArray.length; ++i) {
            this.put(sArray[i], fArray[i]);
        }
    }

    public Short2FloatOpenCustomHashMap(short[] sArray, float[] fArray, ShortHash.Strategy strategy) {
        this(sArray, fArray, 0.75f, strategy);
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

    private float removeEntry(int n) {
        float f = this.value[n];
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return f;
    }

    private float removeNullEntry() {
        this.containsNullKey = false;
        float f = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return f;
    }

    @Override
    public void putAll(Map<? extends Short, ? extends Float> map) {
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

    private void insert(int n, short s, float f) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = s;
        this.value[n] = f;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public float put(short s, float f) {
        int n = this.find(s);
        if (n < 0) {
            this.insert(-n - 1, s, f);
            return this.defRetValue;
        }
        float f2 = this.value[n];
        this.value[n] = f;
        return f2;
    }

    private float addToValue(int n, float f) {
        float f2 = this.value[n];
        this.value[n] = f2 + f;
        return f2;
    }

    public float addTo(short s, float f) {
        int n;
        if (this.strategy.equals(s, (short)0)) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, f);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            short[] sArray = this.key;
            n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
            short s2 = sArray[n];
            if (s2 != 0) {
                if (this.strategy.equals(s2, s)) {
                    return this.addToValue(n, f);
                }
                while ((s2 = sArray[n = n + 1 & this.mask]) != 0) {
                    if (!this.strategy.equals(s2, s)) continue;
                    return this.addToValue(n, f);
                }
            }
        }
        this.key[n] = s;
        this.value[n] = this.defRetValue + f;
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
    public float remove(short s) {
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
    public float get(short s) {
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
    public boolean containsValue(float f) {
        float[] fArray = this.value;
        short[] sArray = this.key;
        if (this.containsNullKey && Float.floatToIntBits(fArray[this.n]) == Float.floatToIntBits(f)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (sArray[n] == 0 || Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public float getOrDefault(short s, float f) {
        if (this.strategy.equals(s, (short)0)) {
            return this.containsNullKey ? this.value[this.n] : f;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return f;
        }
        if (this.strategy.equals(s, s2)) {
            return this.value[n];
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return f;
        } while (!this.strategy.equals(s, s2));
        return this.value[n];
    }

    @Override
    public float putIfAbsent(short s, float f) {
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, s, f);
        return this.defRetValue;
    }

    @Override
    public boolean remove(short s, float f) {
        if (this.strategy.equals(s, (short)0)) {
            if (this.containsNullKey && Float.floatToIntBits(f) == Float.floatToIntBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(s)) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return true;
        }
        if (this.strategy.equals(s, s2) && Float.floatToIntBits(f) == Float.floatToIntBits(this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(s, s2) || Float.floatToIntBits(f) != Float.floatToIntBits(this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(short s, float f, float f2) {
        int n = this.find(s);
        if (n < 0 || Float.floatToIntBits(f) != Float.floatToIntBits(this.value[n])) {
            return true;
        }
        this.value[n] = f2;
        return false;
    }

    @Override
    public float replace(short s, float f) {
        int n = this.find(s);
        if (n < 0) {
            return this.defRetValue;
        }
        float f2 = this.value[n];
        this.value[n] = f;
        return f2;
    }

    @Override
    public float computeIfAbsent(short s, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        float f = SafeMath.safeDoubleToFloat(intToDoubleFunction.applyAsDouble(s));
        this.insert(-n - 1, s, f);
        return f;
    }

    @Override
    public float computeIfAbsentNullable(short s, IntFunction<? extends Float> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        Float f = intFunction.apply(s);
        if (f == null) {
            return this.defRetValue;
        }
        float f2 = f.floatValue();
        this.insert(-n - 1, s, f2);
        return f2;
    }

    @Override
    public float computeIfPresent(short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0) {
            return this.defRetValue;
        }
        Float f = biFunction.apply((Short)s, Float.valueOf(this.value[n]));
        if (f == null) {
            if (this.strategy.equals(s, (short)0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = f.floatValue();
        return this.value[n];
    }

    @Override
    public float compute(short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        Float f = biFunction.apply((Short)s, n >= 0 ? Float.valueOf(this.value[n]) : null);
        if (f == null) {
            if (n >= 0) {
                if (this.strategy.equals(s, (short)0)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        float f2 = f.floatValue();
        if (n < 0) {
            this.insert(-n - 1, s, f2);
            return f2;
        }
        this.value[n] = f2;
        return this.value[n];
    }

    @Override
    public float merge(short s, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0) {
            this.insert(-n - 1, s, f);
            return f;
        }
        Float f2 = biFunction.apply(Float.valueOf(this.value[n]), Float.valueOf(f));
        if (f2 == null) {
            if (this.strategy.equals(s, (short)0)) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = f2.floatValue();
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

    public Short2FloatMap.FastEntrySet short2FloatEntrySet() {
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
    public FloatCollection values() {
        if (this.values == null) {
            this.values = new AbstractFloatCollection(this){
                final Short2FloatOpenCustomHashMap this$0;
                {
                    this.this$0 = short2FloatOpenCustomHashMap;
                }

                @Override
                public FloatIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(float f) {
                    return this.this$0.containsValue(f);
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
        short[] sArray = this.key;
        float[] fArray = this.value;
        int n2 = n - 1;
        short[] sArray2 = new short[n + 1];
        float[] fArray2 = new float[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (sArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(sArray[n3])) & n2;
            if (sArray2[n5] != 0) {
                while (sArray2[n5 = n5 + 1 & n2] != 0) {
                }
            }
            sArray2[n5] = sArray[n3];
            fArray2[n5] = fArray[n3];
        }
        fArray2[n] = fArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = sArray2;
        this.value = fArray2;
    }

    public Short2FloatOpenCustomHashMap clone() {
        Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap;
        try {
            short2FloatOpenCustomHashMap = (Short2FloatOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2FloatOpenCustomHashMap.keys = null;
        short2FloatOpenCustomHashMap.values = null;
        short2FloatOpenCustomHashMap.entries = null;
        short2FloatOpenCustomHashMap.containsNullKey = this.containsNullKey;
        short2FloatOpenCustomHashMap.key = (short[])this.key.clone();
        short2FloatOpenCustomHashMap.value = (float[])this.value.clone();
        short2FloatOpenCustomHashMap.strategy = this.strategy;
        return short2FloatOpenCustomHashMap;
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
            n += (n4 ^= HashCommon.float2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.float2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        short[] sArray = this.key;
        float[] fArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeShort(sArray[n2]);
            objectOutputStream.writeFloat(fArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new short[this.n + 1];
        short[] sArray = this.key;
        this.value = new float[this.n + 1];
        float[] fArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            short s = objectInputStream.readShort();
            float f = objectInputStream.readFloat();
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
            fArray[n2] = f;
        }
    }

    private void checkTable() {
    }

    public ObjectSet short2FloatEntrySet() {
        return this.short2FloatEntrySet();
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

    static float access$300(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
        return short2FloatOpenCustomHashMap.removeNullEntry();
    }

    static float access$400(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap, int n) {
        return short2FloatOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements FloatIterator {
        final Short2FloatOpenCustomHashMap this$0;

        public ValueIterator(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
            this.this$0 = short2FloatOpenCustomHashMap;
            super(short2FloatOpenCustomHashMap, null);
        }

        @Override
        public float nextFloat() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractShortSet {
        final Short2FloatOpenCustomHashMap this$0;

        private KeySet(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
            this.this$0 = short2FloatOpenCustomHashMap;
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

        KeySet(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap, 1 var2_2) {
            this(short2FloatOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ShortIterator {
        final Short2FloatOpenCustomHashMap this$0;

        public KeyIterator(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
            this.this$0 = short2FloatOpenCustomHashMap;
            super(short2FloatOpenCustomHashMap, null);
        }

        @Override
        public short nextShort() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Short2FloatMap.Entry>
    implements Short2FloatMap.FastEntrySet {
        final Short2FloatOpenCustomHashMap this$0;

        private MapEntrySet(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
            this.this$0 = short2FloatOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Short2FloatMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Short2FloatMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            short s = (Short)entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            if (this.this$0.strategy.equals(s, (short)0)) {
                return this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f);
            }
            short[] sArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(s)) & this.this$0.mask;
            short s2 = sArray[n];
            if (s2 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(s, s2)) {
                return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f);
            }
            do {
                if ((s2 = sArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(s, s2));
            return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            short s = (Short)entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            if (this.this$0.strategy.equals(s, (short)0)) {
                if (this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f)) {
                    Short2FloatOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            short[] sArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(s)) & this.this$0.mask;
            short s2 = sArray[n];
            if (s2 == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(s2, s)) {
                if (Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f)) {
                    Short2FloatOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((s2 = sArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(s2, s) || Float.floatToIntBits(this.this$0.value[n]) != Float.floatToIntBits(f));
            Short2FloatOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Short2FloatMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractShort2FloatMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new AbstractShort2FloatMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Short2FloatMap.Entry> consumer) {
            AbstractShort2FloatMap.BasicEntry basicEntry = new AbstractShort2FloatMap.BasicEntry();
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

        MapEntrySet(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap, 1 var2_2) {
            this(short2FloatOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Short2FloatMap.Entry> {
        private final MapEntry entry;
        final Short2FloatOpenCustomHashMap this$0;

        private FastEntryIterator(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
            this.this$0 = short2FloatOpenCustomHashMap;
            super(short2FloatOpenCustomHashMap, null);
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

        FastEntryIterator(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap, 1 var2_2) {
            this(short2FloatOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Short2FloatMap.Entry> {
        private MapEntry entry;
        final Short2FloatOpenCustomHashMap this$0;

        private EntryIterator(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
            this.this$0 = short2FloatOpenCustomHashMap;
            super(short2FloatOpenCustomHashMap, null);
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

        EntryIterator(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap, 1 var2_2) {
            this(short2FloatOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ShortArrayList wrapped;
        final Short2FloatOpenCustomHashMap this$0;

        private MapIterator(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
            this.this$0 = short2FloatOpenCustomHashMap;
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

        MapIterator(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap, 1 var2_2) {
            this(short2FloatOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Short2FloatMap.Entry,
    Map.Entry<Short, Float> {
        int index;
        final Short2FloatOpenCustomHashMap this$0;

        MapEntry(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap, int n) {
            this.this$0 = short2FloatOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Short2FloatOpenCustomHashMap short2FloatOpenCustomHashMap) {
            this.this$0 = short2FloatOpenCustomHashMap;
        }

        @Override
        public short getShortKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public float getFloatValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public float setValue(float f) {
            float f2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = f;
            return f2;
        }

        @Override
        @Deprecated
        public Short getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Float getValue() {
            return Float.valueOf(this.this$0.value[this.index]);
        }

        @Override
        @Deprecated
        public Float setValue(Float f) {
            return Float.valueOf(this.setValue(f.floatValue()));
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.strategy.equals(this.this$0.key[this.index], (Short)entry.getKey()) && Float.floatToIntBits(this.this$0.value[this.index]) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
        }

        @Override
        public int hashCode() {
            return this.this$0.strategy.hashCode(this.this$0.key[this.index]) ^ HashCommon.float2int(this.this$0.value[this.index]);
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Float)object);
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

