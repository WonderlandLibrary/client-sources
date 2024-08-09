/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2FloatMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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
import java.util.function.ToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Object2FloatOpenHashMap<K>
extends AbstractObject2FloatMap<K>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient float[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Object2FloatMap.FastEntrySet<K> entries;
    protected transient ObjectSet<K> keys;
    protected transient FloatCollection values;

    public Object2FloatOpenHashMap(int n, float f) {
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
        this.key = new Object[this.n + 1];
        this.value = new float[this.n + 1];
    }

    public Object2FloatOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Object2FloatOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2FloatOpenHashMap(Map<? extends K, ? extends Float> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Object2FloatOpenHashMap(Map<? extends K, ? extends Float> map) {
        this(map, 0.75f);
    }

    public Object2FloatOpenHashMap(Object2FloatMap<K> object2FloatMap, float f) {
        this(object2FloatMap.size(), f);
        this.putAll(object2FloatMap);
    }

    public Object2FloatOpenHashMap(Object2FloatMap<K> object2FloatMap) {
        this(object2FloatMap, 0.75f);
    }

    public Object2FloatOpenHashMap(K[] KArray, float[] fArray, float f) {
        this(KArray.length, f);
        if (KArray.length != fArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + KArray.length + " and " + fArray.length + ")");
        }
        for (int i = 0; i < KArray.length; ++i) {
            this.put(KArray[i], fArray[i]);
        }
    }

    public Object2FloatOpenHashMap(K[] KArray, float[] fArray) {
        this(KArray, fArray, 0.75f);
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
        this.key[this.n] = null;
        float f = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return f;
    }

    @Override
    public void putAll(Map<? extends K, ? extends Float> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(K k) {
        if (k == null) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(k.hashCode()) & this.mask;
        K k2 = KArray[n];
        if (k2 == null) {
            return -(n + 1);
        }
        if (k.equals(k2)) {
            return n;
        }
        do {
            if ((k2 = KArray[n = n + 1 & this.mask]) != null) continue;
            return -(n + 1);
        } while (!k.equals(k2));
        return n;
    }

    private void insert(int n, K k, float f) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = k;
        this.value[n] = f;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public float put(K k, float f) {
        int n = this.find(k);
        if (n < 0) {
            this.insert(-n - 1, k, f);
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

    public float addTo(K k, float f) {
        int n;
        if (k == null) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, f);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            K[] KArray = this.key;
            n = HashCommon.mix(k.hashCode()) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (k2.equals(k)) {
                    return this.addToValue(n, f);
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (!k2.equals(k)) continue;
                    return this.addToValue(n, f);
                }
            }
        }
        this.key[n] = k;
        this.value[n] = this.defRetValue + f;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int n) {
        K[] KArray = this.key;
        while (true) {
            K k;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((k = KArray[n]) == null) {
                    KArray[n2] = null;
                    return;
                }
                int n3 = HashCommon.mix(k.hashCode()) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            KArray[n2] = k;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public float removeFloat(Object object) {
        if (object == null) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object.equals(k)) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!object.equals(k));
        return this.removeEntry(n);
    }

    @Override
    public float getFloat(Object object) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return this.defRetValue;
        }
        if (object.equals(k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return this.defRetValue;
        } while (!object.equals(k));
        return this.value[n];
    }

    @Override
    public boolean containsKey(Object object) {
        if (object == null) {
            return this.containsNullKey;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object.equals(k)) {
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k));
        return false;
    }

    @Override
    public boolean containsValue(float f) {
        float[] fArray = this.value;
        K[] KArray = this.key;
        if (this.containsNullKey && Float.floatToIntBits(fArray[this.n]) == Float.floatToIntBits(f)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (KArray[n] == null || Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public float getOrDefault(Object object, float f) {
        if (object == null) {
            return this.containsNullKey ? this.value[this.n] : f;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return f;
        }
        if (object.equals(k)) {
            return this.value[n];
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return f;
        } while (!object.equals(k));
        return this.value[n];
    }

    @Override
    public float putIfAbsent(K k, float f) {
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, k, f);
        return this.defRetValue;
    }

    @Override
    public boolean remove(Object object, float f) {
        if (object == null) {
            if (this.containsNullKey && Float.floatToIntBits(f) == Float.floatToIntBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object.equals(k) && Float.floatToIntBits(f) == Float.floatToIntBits(this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k) || Float.floatToIntBits(f) != Float.floatToIntBits(this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(K k, float f, float f2) {
        int n = this.find(k);
        if (n < 0 || Float.floatToIntBits(f) != Float.floatToIntBits(this.value[n])) {
            return true;
        }
        this.value[n] = f2;
        return false;
    }

    @Override
    public float replace(K k, float f) {
        int n = this.find(k);
        if (n < 0) {
            return this.defRetValue;
        }
        float f2 = this.value[n];
        this.value[n] = f;
        return f2;
    }

    @Override
    public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        int n = this.find(k);
        if (n >= 0) {
            return this.value[n];
        }
        float f = SafeMath.safeDoubleToFloat(toDoubleFunction.applyAsDouble(k));
        this.insert(-n - 1, k, f);
        return f;
    }

    @Override
    public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            return this.defRetValue;
        }
        Float f = biFunction.apply(k, Float.valueOf(this.value[n]));
        if (f == null) {
            if (k == null) {
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
    public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        Float f = biFunction.apply(k, n >= 0 ? Float.valueOf(this.value[n]) : null);
        if (f == null) {
            if (n >= 0) {
                if (k == null) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        float f2 = f.floatValue();
        if (n < 0) {
            this.insert(-n - 1, k, f2);
            return f2;
        }
        this.value[n] = f2;
        return this.value[n];
    }

    @Override
    public float mergeFloat(K k, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(k);
        if (n < 0) {
            this.insert(-n - 1, k, f);
            return f;
        }
        Float f2 = biFunction.apply(Float.valueOf(this.value[n]), Float.valueOf(f));
        if (f2 == null) {
            if (k == null) {
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
        Arrays.fill(this.key, null);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Object2FloatMap.FastEntrySet<K> object2FloatEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public FloatCollection values() {
        if (this.values == null) {
            this.values = new AbstractFloatCollection(this){
                final Object2FloatOpenHashMap this$0;
                {
                    this.this$0 = object2FloatOpenHashMap;
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
                        if (this.this$0.key[n] == null) continue;
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
        K[] KArray = this.key;
        float[] fArray = this.value;
        int n2 = n - 1;
        Object[] objectArray = new Object[n + 1];
        float[] fArray2 = new float[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (KArray[--n3] == null) {
            }
            int n5 = HashCommon.mix(KArray[n3].hashCode()) & n2;
            if (objectArray[n5] != null) {
                while (objectArray[n5 = n5 + 1 & n2] != null) {
                }
            }
            objectArray[n5] = KArray[n3];
            fArray2[n5] = fArray[n3];
        }
        fArray2[n] = fArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = objectArray;
        this.value = fArray2;
    }

    public Object2FloatOpenHashMap<K> clone() {
        Object2FloatOpenHashMap object2FloatOpenHashMap;
        try {
            object2FloatOpenHashMap = (Object2FloatOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2FloatOpenHashMap.keys = null;
        object2FloatOpenHashMap.values = null;
        object2FloatOpenHashMap.entries = null;
        object2FloatOpenHashMap.containsNullKey = this.containsNullKey;
        object2FloatOpenHashMap.key = (Object[])this.key.clone();
        object2FloatOpenHashMap.value = (float[])this.value.clone();
        return object2FloatOpenHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == null) {
                ++n3;
            }
            if (this != this.key[n3]) {
                n4 = this.key[n3].hashCode();
            }
            n += (n4 ^= HashCommon.float2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.float2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        K[] KArray = this.key;
        float[] fArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeObject(KArray[n2]);
            objectOutputStream.writeFloat(fArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] objectArray = this.key;
        this.value = new float[this.n + 1];
        float[] fArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            Object object = objectInputStream.readObject();
            float f = objectInputStream.readFloat();
            if (object == null) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(object.hashCode()) & this.mask;
                while (objectArray[n2] != null) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            objectArray[n2] = object;
            fArray[n2] = f;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSet object2FloatEntrySet() {
        return this.object2FloatEntrySet();
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

    static float access$300(Object2FloatOpenHashMap object2FloatOpenHashMap) {
        return object2FloatOpenHashMap.removeNullEntry();
    }

    static float access$400(Object2FloatOpenHashMap object2FloatOpenHashMap, int n) {
        return object2FloatOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements FloatIterator {
        final Object2FloatOpenHashMap this$0;

        public ValueIterator(Object2FloatOpenHashMap object2FloatOpenHashMap) {
            this.this$0 = object2FloatOpenHashMap;
            super(object2FloatOpenHashMap, null);
        }

        @Override
        public float nextFloat() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    private final class KeySet
    extends AbstractObjectSet<K> {
        final Object2FloatOpenHashMap this$0;

        private KeySet(Object2FloatOpenHashMap object2FloatOpenHashMap) {
            this.this$0 = object2FloatOpenHashMap;
        }

        @Override
        public ObjectIterator<K> iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(Consumer<? super K> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                Object k = this.this$0.key[n];
                if (k == null) continue;
                consumer.accept(k);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public boolean remove(Object object) {
            int n = this.this$0.size;
            this.this$0.removeFloat(object);
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

        KeySet(Object2FloatOpenHashMap object2FloatOpenHashMap, 1 var2_2) {
            this(object2FloatOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ObjectIterator<K> {
        final Object2FloatOpenHashMap this$0;

        public KeyIterator(Object2FloatOpenHashMap object2FloatOpenHashMap) {
            this.this$0 = object2FloatOpenHashMap;
            super(object2FloatOpenHashMap, null);
        }

        @Override
        public K next() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Object2FloatMap.Entry<K>>
    implements Object2FloatMap.FastEntrySet<K> {
        final Object2FloatOpenHashMap this$0;

        private MapEntrySet(Object2FloatOpenHashMap object2FloatOpenHashMap) {
            this.this$0 = object2FloatOpenHashMap;
        }

        @Override
        public ObjectIterator<Object2FloatMap.Entry<K>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Object2FloatMap.Entry<K>> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            Object k = entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            if (k == null) {
                return this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f);
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(k.hashCode()) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (k.equals(k2)) {
                return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f);
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!k.equals(k2));
            return Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            Object k = entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            if (k == null) {
                if (this.this$0.containsNullKey && Float.floatToIntBits(this.this$0.value[this.this$0.n]) == Float.floatToIntBits(f)) {
                    Object2FloatOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            K[] KArray = this.this$0.key;
            int n = HashCommon.mix(k.hashCode()) & this.this$0.mask;
            Object k2 = KArray[n];
            if (k2 == null) {
                return true;
            }
            if (k2.equals(k)) {
                if (Float.floatToIntBits(this.this$0.value[n]) == Float.floatToIntBits(f)) {
                    Object2FloatOpenHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((k2 = KArray[n = n + 1 & this.this$0.mask]) != null) continue;
                return true;
            } while (!k2.equals(k) || Float.floatToIntBits(this.this$0.value[n]) != Float.floatToIntBits(f));
            Object2FloatOpenHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractObject2FloatMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == null) continue;
                consumer.accept(new AbstractObject2FloatMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
            AbstractObject2FloatMap.BasicEntry basicEntry = new AbstractObject2FloatMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == null) continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Object2FloatOpenHashMap object2FloatOpenHashMap, 1 var2_2) {
            this(object2FloatOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Object2FloatMap.Entry<K>> {
        private final MapEntry entry;
        final Object2FloatOpenHashMap this$0;

        private FastEntryIterator(Object2FloatOpenHashMap object2FloatOpenHashMap) {
            this.this$0 = object2FloatOpenHashMap;
            super(object2FloatOpenHashMap, null);
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

        FastEntryIterator(Object2FloatOpenHashMap object2FloatOpenHashMap, 1 var2_2) {
            this(object2FloatOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Object2FloatMap.Entry<K>> {
        private MapEntry entry;
        final Object2FloatOpenHashMap this$0;

        private EntryIterator(Object2FloatOpenHashMap object2FloatOpenHashMap) {
            this.this$0 = object2FloatOpenHashMap;
            super(object2FloatOpenHashMap, null);
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

        EntryIterator(Object2FloatOpenHashMap object2FloatOpenHashMap, 1 var2_2) {
            this(object2FloatOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ObjectArrayList<K> wrapped;
        final Object2FloatOpenHashMap this$0;

        private MapIterator(Object2FloatOpenHashMap object2FloatOpenHashMap) {
            this.this$0 = object2FloatOpenHashMap;
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
            K[] KArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                Object k = this.wrapped.get(-this.pos - 1);
                int n = HashCommon.mix(k.hashCode()) & this.this$0.mask;
                while (!k.equals(KArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (KArray[this.pos] == null);
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            K[] KArray = this.this$0.key;
            while (true) {
                Object k;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((k = KArray[n]) == null) {
                        KArray[n2] = null;
                        return;
                    }
                    int n3 = HashCommon.mix(k.hashCode()) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList(2);
                    }
                    this.wrapped.add(KArray[n]);
                }
                KArray[n2] = k;
                this.this$0.value[n2] = this.this$0.value[n];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNullKey = false;
                this.this$0.key[this.this$0.n] = null;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.removeFloat(this.wrapped.set(-this.pos - 1, (Object)null));
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

        MapIterator(Object2FloatOpenHashMap object2FloatOpenHashMap, 1 var2_2) {
            this(object2FloatOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Object2FloatMap.Entry<K>,
    Map.Entry<K, Float> {
        int index;
        final Object2FloatOpenHashMap this$0;

        MapEntry(Object2FloatOpenHashMap object2FloatOpenHashMap, int n) {
            this.this$0 = object2FloatOpenHashMap;
            this.index = n;
        }

        MapEntry(Object2FloatOpenHashMap object2FloatOpenHashMap) {
            this.this$0 = object2FloatOpenHashMap;
        }

        @Override
        public K getKey() {
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
            return Objects.equals(this.this$0.key[this.index], entry.getKey()) && Float.floatToIntBits(this.this$0.value[this.index]) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
        }

        @Override
        public int hashCode() {
            return (this.this$0.key[this.index] == null ? 0 : this.this$0.key[this.index].hashCode()) ^ HashCommon.float2int(this.this$0.value[this.index]);
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
    }
}

