/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2CharMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2CharMap;
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
public class Float2CharOpenCustomHashMap
extends AbstractFloat2CharMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient float[] key;
    protected transient char[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected FloatHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Float2CharMap.FastEntrySet entries;
    protected transient FloatSet keys;
    protected transient CharCollection values;

    public Float2CharOpenCustomHashMap(int n, float f, FloatHash.Strategy strategy) {
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
        this.value = new char[this.n + 1];
    }

    public Float2CharOpenCustomHashMap(int n, FloatHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Float2CharOpenCustomHashMap(FloatHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Float2CharOpenCustomHashMap(Map<? extends Float, ? extends Character> map, float f, FloatHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Float2CharOpenCustomHashMap(Map<? extends Float, ? extends Character> map, FloatHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Float2CharOpenCustomHashMap(Float2CharMap float2CharMap, float f, FloatHash.Strategy strategy) {
        this(float2CharMap.size(), f, strategy);
        this.putAll(float2CharMap);
    }

    public Float2CharOpenCustomHashMap(Float2CharMap float2CharMap, FloatHash.Strategy strategy) {
        this(float2CharMap, 0.75f, strategy);
    }

    public Float2CharOpenCustomHashMap(float[] fArray, char[] cArray, float f, FloatHash.Strategy strategy) {
        this(fArray.length, f, strategy);
        if (fArray.length != cArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + fArray.length + " and " + cArray.length + ")");
        }
        for (int i = 0; i < fArray.length; ++i) {
            this.put(fArray[i], cArray[i]);
        }
    }

    public Float2CharOpenCustomHashMap(float[] fArray, char[] cArray, FloatHash.Strategy strategy) {
        this(fArray, cArray, 0.75f, strategy);
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
    public void putAll(Map<? extends Float, ? extends Character> map) {
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

    private void insert(int n, float f, char c) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = f;
        this.value[n] = c;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public char put(float f, char c) {
        int n = this.find(f);
        if (n < 0) {
            this.insert(-n - 1, f, c);
            return this.defRetValue;
        }
        char c2 = this.value[n];
        this.value[n] = c;
        return c2;
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
    public char remove(float f) {
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
    public char get(float f) {
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
    public boolean containsValue(char c) {
        char[] cArray = this.value;
        float[] fArray = this.key;
        if (this.containsNullKey && cArray[this.n] == c) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) == 0 || cArray[n] != c) continue;
            return false;
        }
        return true;
    }

    @Override
    public char getOrDefault(float f, char c) {
        if (this.strategy.equals(f, 0.0f)) {
            return this.containsNullKey ? this.value[this.n] : c;
        }
        float[] fArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(f)) & this.mask;
        float f2 = fArray[n];
        if (Float.floatToIntBits(f2) == 0) {
            return c;
        }
        if (this.strategy.equals(f, f2)) {
            return this.value[n];
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return c;
        } while (!this.strategy.equals(f, f2));
        return this.value[n];
    }

    @Override
    public char putIfAbsent(float f, char c) {
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, f, c);
        return this.defRetValue;
    }

    @Override
    public boolean remove(float f, char c) {
        if (this.strategy.equals(f, 0.0f)) {
            if (this.containsNullKey && c == this.value[this.n]) {
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
        if (this.strategy.equals(f, f2) && c == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (!this.strategy.equals(f, f2) || c != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(float f, char c, char c2) {
        int n = this.find(f);
        if (n < 0 || c != this.value[n]) {
            return true;
        }
        this.value[n] = c2;
        return false;
    }

    @Override
    public char replace(float f, char c) {
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        char c2 = this.value[n];
        this.value[n] = c;
        return c2;
    }

    @Override
    public char computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        char c = SafeMath.safeIntToChar(doubleToIntFunction.applyAsInt(f));
        this.insert(-n - 1, f, c);
        return c;
    }

    @Override
    public char computeIfAbsentNullable(float f, DoubleFunction<? extends Character> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.find(f);
        if (n >= 0) {
            return this.value[n];
        }
        Character c = doubleFunction.apply(f);
        if (c == null) {
            return this.defRetValue;
        }
        char c2 = c.charValue();
        this.insert(-n - 1, f, c2);
        return c2;
    }

    @Override
    public char computeIfPresent(float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            return this.defRetValue;
        }
        Character c = biFunction.apply(Float.valueOf(f), Character.valueOf(this.value[n]));
        if (c == null) {
            if (this.strategy.equals(f, 0.0f)) {
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
    public char compute(float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        Character c = biFunction.apply(Float.valueOf(f), n >= 0 ? Character.valueOf(this.value[n]) : null);
        if (c == null) {
            if (n >= 0) {
                if (this.strategy.equals(f, 0.0f)) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        char c2 = c.charValue();
        if (n < 0) {
            this.insert(-n - 1, f, c2);
            return c2;
        }
        this.value[n] = c2;
        return this.value[n];
    }

    @Override
    public char merge(float f, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(f);
        if (n < 0) {
            this.insert(-n - 1, f, c);
            return c;
        }
        Character c2 = biFunction.apply(Character.valueOf(this.value[n]), Character.valueOf(c));
        if (c2 == null) {
            if (this.strategy.equals(f, 0.0f)) {
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

    public Float2CharMap.FastEntrySet float2CharEntrySet() {
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
    public CharCollection values() {
        if (this.values == null) {
            this.values = new AbstractCharCollection(this){
                final Float2CharOpenCustomHashMap this$0;
                {
                    this.this$0 = float2CharOpenCustomHashMap;
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
        char[] cArray = this.value;
        int n2 = n - 1;
        float[] fArray2 = new float[n + 1];
        char[] cArray2 = new char[n + 1];
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
            cArray2[n5] = cArray[n3];
        }
        cArray2[n] = cArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = fArray2;
        this.value = cArray2;
    }

    public Float2CharOpenCustomHashMap clone() {
        Float2CharOpenCustomHashMap float2CharOpenCustomHashMap;
        try {
            float2CharOpenCustomHashMap = (Float2CharOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2CharOpenCustomHashMap.keys = null;
        float2CharOpenCustomHashMap.values = null;
        float2CharOpenCustomHashMap.entries = null;
        float2CharOpenCustomHashMap.containsNullKey = this.containsNullKey;
        float2CharOpenCustomHashMap.key = (float[])this.key.clone();
        float2CharOpenCustomHashMap.value = (char[])this.value.clone();
        float2CharOpenCustomHashMap.strategy = this.strategy;
        return float2CharOpenCustomHashMap;
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
        char[] cArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeFloat(fArray[n2]);
            objectOutputStream.writeChar(cArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new float[this.n + 1];
        float[] fArray = this.key;
        this.value = new char[this.n + 1];
        char[] cArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            float f = objectInputStream.readFloat();
            char c = objectInputStream.readChar();
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
            cArray[n2] = c;
        }
    }

    private void checkTable() {
    }

    public ObjectSet float2CharEntrySet() {
        return this.float2CharEntrySet();
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

    static char access$300(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
        return float2CharOpenCustomHashMap.removeNullEntry();
    }

    static char access$400(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap, int n) {
        return float2CharOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements CharIterator {
        final Float2CharOpenCustomHashMap this$0;

        public ValueIterator(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
            this.this$0 = float2CharOpenCustomHashMap;
            super(float2CharOpenCustomHashMap, null);
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
    extends AbstractFloatSet {
        final Float2CharOpenCustomHashMap this$0;

        private KeySet(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
            this.this$0 = float2CharOpenCustomHashMap;
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

        KeySet(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap, 1 var2_2) {
            this(float2CharOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements FloatIterator {
        final Float2CharOpenCustomHashMap this$0;

        public KeyIterator(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
            this.this$0 = float2CharOpenCustomHashMap;
            super(float2CharOpenCustomHashMap, null);
        }

        @Override
        public float nextFloat() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Float2CharMap.Entry>
    implements Float2CharMap.FastEntrySet {
        final Float2CharOpenCustomHashMap this$0;

        private MapEntrySet(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
            this.this$0 = float2CharOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Float2CharMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Float2CharMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            char c = ((Character)entry.getValue()).charValue();
            if (this.this$0.strategy.equals(f, 0.0f)) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == c;
            }
            float[] fArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(f)) & this.this$0.mask;
            float f2 = fArray[n];
            if (Float.floatToIntBits(f2) == 0) {
                return true;
            }
            if (this.this$0.strategy.equals(f, f2)) {
                return this.this$0.value[n] == c;
            }
            do {
                if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(f, f2));
            return this.this$0.value[n] == c;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            char c = ((Character)entry.getValue()).charValue();
            if (this.this$0.strategy.equals(f, 0.0f)) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == c) {
                    Float2CharOpenCustomHashMap.access$300(this.this$0);
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
                if (this.this$0.value[n] == c) {
                    Float2CharOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if (Float.floatToIntBits(f2 = fArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (!this.this$0.strategy.equals(f2, f) || this.this$0.value[n] != c);
            Float2CharOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Float2CharMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractFloat2CharMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (Float.floatToIntBits(this.this$0.key[n]) == 0) continue;
                consumer.accept(new AbstractFloat2CharMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Float2CharMap.Entry> consumer) {
            AbstractFloat2CharMap.BasicEntry basicEntry = new AbstractFloat2CharMap.BasicEntry();
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

        MapEntrySet(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap, 1 var2_2) {
            this(float2CharOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Float2CharMap.Entry> {
        private final MapEntry entry;
        final Float2CharOpenCustomHashMap this$0;

        private FastEntryIterator(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
            this.this$0 = float2CharOpenCustomHashMap;
            super(float2CharOpenCustomHashMap, null);
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

        FastEntryIterator(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap, 1 var2_2) {
            this(float2CharOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Float2CharMap.Entry> {
        private MapEntry entry;
        final Float2CharOpenCustomHashMap this$0;

        private EntryIterator(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
            this.this$0 = float2CharOpenCustomHashMap;
            super(float2CharOpenCustomHashMap, null);
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

        EntryIterator(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap, 1 var2_2) {
            this(float2CharOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        FloatArrayList wrapped;
        final Float2CharOpenCustomHashMap this$0;

        private MapIterator(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
            this.this$0 = float2CharOpenCustomHashMap;
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

        MapIterator(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap, 1 var2_2) {
            this(float2CharOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Float2CharMap.Entry,
    Map.Entry<Float, Character> {
        int index;
        final Float2CharOpenCustomHashMap this$0;

        MapEntry(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap, int n) {
            this.this$0 = float2CharOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Float2CharOpenCustomHashMap float2CharOpenCustomHashMap) {
            this.this$0 = float2CharOpenCustomHashMap;
        }

        @Override
        public float getFloatKey() {
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
        public Float getKey() {
            return Float.valueOf(this.this$0.key[this.index]);
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
            return this.this$0.strategy.equals(this.this$0.key[this.index], ((Float)entry.getKey()).floatValue()) && this.this$0.value[this.index] == ((Character)entry.getValue()).charValue();
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

