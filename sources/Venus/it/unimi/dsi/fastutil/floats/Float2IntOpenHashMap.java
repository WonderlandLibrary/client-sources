/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2IntMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
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
public class Float2IntOpenHashMap
extends AbstractFloat2IntMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Float2IntMap.FastEntrySet entries;
    protected transient FloatSet keys;
    protected transient IntCollection values;

    public Float2IntOpenHashMap(int n, float f) {
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
        this.value = new int[this.n + 1];
    }

    public Float2IntOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Float2IntOpenHashMap() {
        this(16, 0.75f);
    }

    public Float2IntOpenHashMap(Map<? extends Float, ? extends Integer> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Float2IntOpenHashMap(Map<? extends Float, ? extends Integer> map) {
        this(map, 0.75f);
    }

    public Float2IntOpenHashMap(Float2IntMap float2IntMap, float f) {
        this(float2IntMap.size(), f);
        this.putAll(float2IntMap);
    }

    public Float2IntOpenHashMap(Float2IntMap float2IntMap) {
        this(float2IntMap, 0.75f);
    }

    public Float2IntOpenHashMap(float[] fArray, int[] nArray, float f) {
        this(fArray.length, f);
        if (fArray.length != nArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + fArray.length + " and " + nArray.length + ")");
        }
        for (int i = 0; i < fArray.length; ++i) {
            this.put(fArray[i], nArray[i]);
        }
    }

    public Float2IntOpenHashMap(float[] fArray, int[] nArray) {
        this(fArray, nArray, 0.75f);
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

    private int removeEntry(int n) {
        int n2 = this.value[n];
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n2;
    }

    private int removeNullEntry() {
        this.containsNullKey = false;
        int n = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n;
    }

    @Override
    public void putAll(Map<? extends Float, ? extends Integer> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(float f) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return -(n + 1);
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return n;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return -(n + 1);
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return n;
    }

    private void insert(int n, float f, int n2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = f;
        this.value[n] = n2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public int put(float f, int n) {
        int n2 = this.find(f);
        if (n2 < 0) {
            this.insert(-n2 - 1, f, n);
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        this.value[n2] = n;
        return n3;
    }

    private int addToValue(int n, int n2) {
        int n3 = this.value[n];
        this.value[n] = n3 + n2;
        return n3;
    }

    public int addTo(float f, int n) {
        int n2;
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, n);
            }
            n2 = this.n;
            this.containsNullKey = true;
        } else {
            float[] fArray = this.key;
            n2 = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
            float f2 = fArray[n2];
            if (Float.floatToIntBits(f2) != 0) {
                if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
                    return this.addToValue(n2, n);
                }
                while (Float.floatToIntBits(f2 = fArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (Float.floatToIntBits(f2) != Float.floatToIntBits(f)) continue;
                    return this.addToValue(n2, n);
                }
            }
        }
        this.key[n2] = f;
        this.value[n2] = this.defRetValue + n;
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
                int n3 = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            fArray[n2] = f;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public int remove(float f) {
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return this.removeEntry(n);
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return this.removeEntry(n);
    }

    @Override
    public int get(float f) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return this.defRetValue;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(float f) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return false;
    }

    @Override
    public boolean containsValue(int n) {
        int[] nArray = this.value;
        float[] fArray = this.key;
        if (this.containsNullKey && nArray[this.n] == n) {
            return false;
        }
        int n2 = this.n;
        while (n2-- != 0) {
            if (Float.floatToIntBits(fArray[n2]) == 0 || nArray[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getOrDefault(float f, int n) {
        if (Float.floatToIntBits(f) == 0) {
            return this.containsNullKey ? this.value[this.n] : n;
        }
        float[] fArray = this.key;
        int n2 = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n2];
        if (Float.floatToIntBits(f2) == 0) {
            return n;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
            return this.value[n2];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return n;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
        return this.value[n2];
    }

    @Override
    public int putIfAbsent(float f, int n) {
        int n2 = this.find(f);
        if (n2 >= 0) {
            return this.value[n2];
        }
        this.insert(-n2 - 1, f, n);
        return this.defRetValue;
    }

    @Override
    public boolean remove(float f, int n) {
        if (Float.floatToIntBits(f) == 0) {
            if (this.containsNullKey && n == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        float[] fArray = this.key;
        int n2 = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
        float f2 = fArray[n2];
        if (Float.floatToIntBits(f2) == 0) {
            return true;
        }
        if (Float.floatToIntBits(f) == Float.floatToIntBits(f2) && n == this.value[n2]) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2) || n != this.value[n2]);
        this.removeEntry(n2);
        return false;
    }

    @Override
    public boolean replace(float f, int n, int n2) {
        int n3 = this.find(f);
        if (n3 < 0 || n != this.value[n3]) {
            return true;
        }
        this.value[n3] = n2;
        return false;
    }

    @Override
    public int replace(float f, int n) {
        int n2 = this.find(f);
        if (n2 < 0) {
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        this.value[n2] = n;
        return n3;
    }

    @Override
    public int computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        int n2 = doubleToIntFunction.applyAsInt(f);
        this.insert(-n - 1, f, n2);
        return n2;
    }

    @Override
    public int computeIfAbsentNullable(float f, DoubleFunction<? extends Integer> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        Integer n2 = doubleFunction.apply(f);
        if (n2 == null) {
            return this.defRetValue;
        }
        int n3 = n2;
        this.insert(-n - 1, f, n3);
        return n3;
    }

    @Override
    public int computeIfPresent(float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        Integer n2 = biFunction.apply(Float.valueOf(f), (Integer)this.value[n]);
        if (n2 == null) {
            if (Float.floatToIntBits(f) == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = n2;
        return this.value[n];
    }

    @Override
    public int compute(float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        Integer n2 = biFunction.apply(Float.valueOf(f), n >= 0 ? Integer.valueOf(this.value[n]) : null);
        if (n2 == null) {
            if (n >= 0) {
                if (Float.floatToIntBits(f) == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        int n3 = n2;
        if (n < 0) {
            this.insert(-n - 1, f, n3);
            return n3;
        }
        this.value[n] = n3;
        return this.value[n];
    }

    @Override
    public int merge(float f, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(f);
        if (n2 < 0) {
            this.insert(-n2 - 1, f, n);
            return n;
        }
        Integer n3 = biFunction.apply((Integer)this.value[n2], (Integer)n);
        if (n3 == null) {
            if (Float.floatToIntBits(f) == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n2);
            }
            return this.defRetValue;
        }
        this.value[n2] = n3;
        return this.value[n2];
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

    public Float2IntMap.FastEntrySet float2IntEntrySet() {
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
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(this){
                final Float2IntOpenHashMap this$0;
                {
                    this.this$0 = float2IntOpenHashMap;
                }

                @Override
                public IntIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(int n) {
                    return this.this$0.containsValue(n);
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
        int[] nArray = this.value;
        int n2 = n - 1;
        float[] fArray2 = new float[n + 1];
        int[] nArray2 = new int[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (Float.floatToIntBits(fArray[--n3]) == 0) {
            }
            int n5 = HashCommon.mix(HashCommon.float2int(fArray[n3])) & n2;
            if (Float.floatToIntBits(fArray2[n5]) != 0) {
                while (Float.floatToIntBits(fArray2[n5 = n5 + 1 & n2]) != 0) {
                }
            }
            fArray2[n5] = fArray[n3];
            nArray2[n5] = nArray[n3];
        }
        nArray2[n] = nArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = fArray2;
        this.value = nArray2;
    }

    public Float2IntOpenHashMap clone() {
        Float2IntOpenHashMap float2IntOpenHashMap;
        try {
            float2IntOpenHashMap = (Float2IntOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2IntOpenHashMap.keys = null;
        float2IntOpenHashMap.values = null;
        float2IntOpenHashMap.entries = null;
        float2IntOpenHashMap.containsNullKey = this.containsNullKey;
        float2IntOpenHashMap.key = (float[])this.key.clone();
        float2IntOpenHashMap.value = (int[])this.value.clone();
        return float2IntOpenHashMap;
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
            n4 = HashCommon.float2int(this.key[n3]);
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
        int[] nArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeFloat(fArray[n2]);
            objectOutputStream.writeInt(nArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new float[this.n + 1];
        float[] fArray = this.key;
        this.value = new int[this.n + 1];
        int[] nArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            float f = objectInputStream.readFloat();
            int n3 = objectInputStream.readInt();
            if (Float.floatToIntBits(f) == 0) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(HashCommon.float2int(f)) & this.mask;
                while (Float.floatToIntBits(fArray[n2]) != 0) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            fArray[n2] = f;
            nArray[n2] = n3;
        }
    }

    private void checkTable() {
    }

    public ObjectSet float2IntEntrySet() {
        return this.float2IntEntrySet();
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

    static int access$300(Float2IntOpenHashMap float2IntOpenHashMap) {
        return float2IntOpenHashMap.removeNullEntry();
    }

    static int access$400(Float2IntOpenHashMap float2IntOpenHashMap, int n) {
        return float2IntOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements IntIterator {
        final Float2IntOpenHashMap this$0;

        public ValueIterator(Float2IntOpenHashMap float2IntOpenHashMap) {
            this.this$0 = float2IntOpenHashMap;
            super(float2IntOpenHashMap, null);
        }

        @Override
        public int nextInt() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractFloatSet {
        final Float2IntOpenHashMap this$0;

        private KeySet(Float2IntOpenHashMap float2IntOpenHashMap) {
            this.this$0 = float2IntOpenHashMap;
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

        KeySet(Float2IntOpenHashMap float2IntOpenHashMap, 1 var2_2) {
            this(float2IntOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements FloatIterator {
        final Float2IntOpenHashMap this$0;

        public KeyIterator(Float2IntOpenHashMap float2IntOpenHashMap) {
            this.this$0 = float2IntOpenHashMap;
            super(float2IntOpenHashMap, null);
        }

        @Override
        public float nextFloat() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Float2IntMap.Entry>
    implements Float2IntMap.FastEntrySet {
        final Float2IntOpenHashMap this$0;

        private MapEntrySet(Float2IntOpenHashMap float2IntOpenHashMap) {
            this.this$0 = float2IntOpenHashMap;
        }

        @Override
        public ObjectIterator<Float2IntMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Float2IntMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            int n = (Integer)entry.getValue();
            if (Float.floatToIntBits(f) == 0) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n;
            }
            float[] fArray = this.this$0.key;
            int n2 = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
            float f2 = fArray[n2];
            if (Float.floatToIntBits(f2) == 0) {
                return true;
            }
            if (Float.floatToIntBits(f) == Float.floatToIntBits(f2)) {
                return this.this$0.value[n2] == n;
            }
            do {
                if (Float.floatToIntBits(f2 = fArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (Float.floatToIntBits(f) != Float.floatToIntBits(f2));
            return this.this$0.value[n2] == n;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            int n = (Integer)entry.getValue();
            if (Float.floatToIntBits(f) == 0) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n) {
                    Float2IntOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            float[] fArray = this.this$0.key;
            int n2 = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
            float f2 = fArray[n2];
            if (Float.floatToIntBits(f2) == 0) {
                return true;
            }
            if (Float.floatToIntBits(f2) == Float.floatToIntBits(f)) {
                if (this.this$0.value[n2] == n) {
                    Float2IntOpenHashMap.access$400(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if (Float.floatToIntBits(f2 = fArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || this.this$0.value[n2] != n);
            Float2IntOpenHashMap.access$400(this.this$0, n2);
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
        public void forEach(Consumer<? super Float2IntMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractFloat2IntMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (Float.floatToIntBits(this.this$0.key[n]) == 0) continue;
                consumer.accept(new AbstractFloat2IntMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Float2IntMap.Entry> consumer) {
            AbstractFloat2IntMap.BasicEntry basicEntry = new AbstractFloat2IntMap.BasicEntry();
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

        MapEntrySet(Float2IntOpenHashMap float2IntOpenHashMap, 1 var2_2) {
            this(float2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Float2IntMap.Entry> {
        private final MapEntry entry;
        final Float2IntOpenHashMap this$0;

        private FastEntryIterator(Float2IntOpenHashMap float2IntOpenHashMap) {
            this.this$0 = float2IntOpenHashMap;
            super(float2IntOpenHashMap, null);
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

        FastEntryIterator(Float2IntOpenHashMap float2IntOpenHashMap, 1 var2_2) {
            this(float2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Float2IntMap.Entry> {
        private MapEntry entry;
        final Float2IntOpenHashMap this$0;

        private EntryIterator(Float2IntOpenHashMap float2IntOpenHashMap) {
            this.this$0 = float2IntOpenHashMap;
            super(float2IntOpenHashMap, null);
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

        EntryIterator(Float2IntOpenHashMap float2IntOpenHashMap, 1 var2_2) {
            this(float2IntOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        FloatArrayList wrapped;
        final Float2IntOpenHashMap this$0;

        private MapIterator(Float2IntOpenHashMap float2IntOpenHashMap) {
            this.this$0 = float2IntOpenHashMap;
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
                int n = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
                while (Float.floatToIntBits(f) != Float.floatToIntBits(fArray[n])) {
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
                    int n3 = HashCommon.mix(HashCommon.float2int(f)) & this.this$0.mask;
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

        MapIterator(Float2IntOpenHashMap float2IntOpenHashMap, 1 var2_2) {
            this(float2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Float2IntMap.Entry,
    Map.Entry<Float, Integer> {
        int index;
        final Float2IntOpenHashMap this$0;

        MapEntry(Float2IntOpenHashMap float2IntOpenHashMap, int n) {
            this.this$0 = float2IntOpenHashMap;
            this.index = n;
        }

        MapEntry(Float2IntOpenHashMap float2IntOpenHashMap) {
            this.this$0 = float2IntOpenHashMap;
        }

        @Override
        public float getFloatKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public int getIntValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public int setValue(int n) {
            int n2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = n;
            return n2;
        }

        @Override
        @Deprecated
        public Float getKey() {
            return Float.valueOf(this.this$0.key[this.index]);
        }

        @Override
        @Deprecated
        public Integer getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Integer setValue(Integer n) {
            return this.setValue((int)n);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return Float.floatToIntBits(this.this$0.key[this.index]) == Float.floatToIntBits(((Float)entry.getKey()).floatValue()) && this.this$0.value[this.index] == (Integer)entry.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.this$0.key[this.index]) ^ this.this$0.value[this.index];
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Integer)object);
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

