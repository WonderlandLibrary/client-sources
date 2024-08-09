/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2ObjectMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharHash;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
import java.util.function.IntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Char2ObjectOpenCustomHashMap<V>
extends AbstractChar2ObjectMap<V>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient char[] key;
    protected transient V[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected CharHash.Strategy strategy;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Char2ObjectMap.FastEntrySet<V> entries;
    protected transient CharSet keys;
    protected transient ObjectCollection<V> values;

    public Char2ObjectOpenCustomHashMap(int n, float f, CharHash.Strategy strategy) {
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
        this.key = new char[this.n + 1];
        this.value = new Object[this.n + 1];
    }

    public Char2ObjectOpenCustomHashMap(int n, CharHash.Strategy strategy) {
        this(n, 0.75f, strategy);
    }

    public Char2ObjectOpenCustomHashMap(CharHash.Strategy strategy) {
        this(16, 0.75f, strategy);
    }

    public Char2ObjectOpenCustomHashMap(Map<? extends Character, ? extends V> map, float f, CharHash.Strategy strategy) {
        this(map.size(), f, strategy);
        this.putAll(map);
    }

    public Char2ObjectOpenCustomHashMap(Map<? extends Character, ? extends V> map, CharHash.Strategy strategy) {
        this(map, 0.75f, strategy);
    }

    public Char2ObjectOpenCustomHashMap(Char2ObjectMap<V> char2ObjectMap, float f, CharHash.Strategy strategy) {
        this(char2ObjectMap.size(), f, strategy);
        this.putAll(char2ObjectMap);
    }

    public Char2ObjectOpenCustomHashMap(Char2ObjectMap<V> char2ObjectMap, CharHash.Strategy strategy) {
        this(char2ObjectMap, 0.75f, strategy);
    }

    public Char2ObjectOpenCustomHashMap(char[] cArray, V[] VArray, float f, CharHash.Strategy strategy) {
        this(cArray.length, f, strategy);
        if (cArray.length != VArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + cArray.length + " and " + VArray.length + ")");
        }
        for (int i = 0; i < cArray.length; ++i) {
            this.put(cArray[i], VArray[i]);
        }
    }

    public Char2ObjectOpenCustomHashMap(char[] cArray, V[] VArray, CharHash.Strategy strategy) {
        this(cArray, VArray, 0.75f, strategy);
    }

    public CharHash.Strategy strategy() {
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

    private V removeEntry(int n) {
        V v = this.value[n];
        this.value[n] = null;
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    private V removeNullEntry() {
        this.containsNullKey = false;
        V v = this.value[this.n];
        this.value[this.n] = null;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return v;
    }

    @Override
    public void putAll(Map<? extends Character, ? extends V> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(char c) {
        if (this.strategy.equals(c, '\u0000')) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(c)) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return -(n + 1);
        }
        if (this.strategy.equals(c, c2)) {
            return n;
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return -(n + 1);
        } while (!this.strategy.equals(c, c2));
        return n;
    }

    private void insert(int n, char c, V v) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = c;
        this.value[n] = v;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public V put(char c, V v) {
        int n = this.find(c);
        if (n < 0) {
            this.insert(-n - 1, c, v);
            return (V)this.defRetValue;
        }
        V v2 = this.value[n];
        this.value[n] = v;
        return v2;
    }

    protected final void shiftKeys(int n) {
        char[] cArray = this.key;
        while (true) {
            char c;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((c = cArray[n]) == '\u0000') {
                    cArray[n2] = '\u0000';
                    this.value[n2] = null;
                    return;
                }
                int n3 = HashCommon.mix(this.strategy.hashCode(c)) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            cArray[n2] = c;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public V remove(char c) {
        if (this.strategy.equals(c, '\u0000')) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return (V)this.defRetValue;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(c)) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(c, c2)) {
            return this.removeEntry(n);
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(c, c2));
        return this.removeEntry(n);
    }

    @Override
    public V get(char c) {
        if (this.strategy.equals(c, '\u0000')) {
            return (V)(this.containsNullKey ? this.value[this.n] : this.defRetValue);
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(c)) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return (V)this.defRetValue;
        }
        if (this.strategy.equals(c, c2)) {
            return this.value[n];
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return (V)this.defRetValue;
        } while (!this.strategy.equals(c, c2));
        return this.value[n];
    }

    @Override
    public boolean containsKey(char c) {
        if (this.strategy.equals(c, '\u0000')) {
            return this.containsNullKey;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(c)) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return true;
        }
        if (this.strategy.equals(c, c2)) {
            return false;
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return true;
        } while (!this.strategy.equals(c, c2));
        return false;
    }

    @Override
    public boolean containsValue(Object object) {
        V[] VArray = this.value;
        char[] cArray = this.key;
        if (this.containsNullKey && Objects.equals(VArray[this.n], object)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (cArray[n] == '\u0000' || !Objects.equals(VArray[n], object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public V getOrDefault(char c, V v) {
        if (this.strategy.equals(c, '\u0000')) {
            return this.containsNullKey ? this.value[this.n] : v;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(c)) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return v;
        }
        if (this.strategy.equals(c, c2)) {
            return this.value[n];
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return v;
        } while (!this.strategy.equals(c, c2));
        return this.value[n];
    }

    @Override
    public V putIfAbsent(char c, V v) {
        int n = this.find(c);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, c, v);
        return (V)this.defRetValue;
    }

    @Override
    public boolean remove(char c, Object object) {
        if (this.strategy.equals(c, '\u0000')) {
            if (this.containsNullKey && Objects.equals(object, this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(this.strategy.hashCode(c)) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return true;
        }
        if (this.strategy.equals(c, c2) && Objects.equals(object, this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return true;
        } while (!this.strategy.equals(c, c2) || !Objects.equals(object, this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(char c, V v, V v2) {
        int n = this.find(c);
        if (n < 0 || !Objects.equals(v, this.value[n])) {
            return true;
        }
        this.value[n] = v2;
        return false;
    }

    @Override
    public V replace(char c, V v) {
        int n = this.find(c);
        if (n < 0) {
            return (V)this.defRetValue;
        }
        V v2 = this.value[n];
        this.value[n] = v;
        return v2;
    }

    @Override
    public V computeIfAbsent(char c, IntFunction<? extends V> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(c);
        if (n >= 0) {
            return this.value[n];
        }
        V v = intFunction.apply(c);
        this.insert(-n - 1, c, v);
        return v;
    }

    @Override
    public V computeIfPresent(char c, BiFunction<? super Character, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        if (n < 0) {
            return (V)this.defRetValue;
        }
        V v = biFunction.apply(Character.valueOf(c), this.value[n]);
        if (v == null) {
            if (this.strategy.equals(c, '\u0000')) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return (V)this.defRetValue;
        }
        this.value[n] = v;
        return this.value[n];
    }

    @Override
    public V compute(char c, BiFunction<? super Character, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        V v = biFunction.apply(Character.valueOf(c), n >= 0 ? (Object)this.value[n] : null);
        if (v == null) {
            if (n >= 0) {
                if (this.strategy.equals(c, '\u0000')) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return (V)this.defRetValue;
        }
        V v2 = v;
        if (n < 0) {
            this.insert(-n - 1, c, v2);
            return v2;
        }
        this.value[n] = v2;
        return this.value[n];
    }

    @Override
    public V merge(char c, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        if (n < 0 || this.value[n] == null) {
            if (v == null) {
                return (V)this.defRetValue;
            }
            this.insert(-n - 1, c, v);
            return v;
        }
        V v2 = biFunction.apply(this.value[n], v);
        if (v2 == null) {
            if (this.strategy.equals(c, '\u0000')) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return (V)this.defRetValue;
        }
        this.value[n] = v2;
        return this.value[n];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, '\u0000');
        Arrays.fill(this.value, null);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Char2ObjectMap.FastEntrySet<V> char2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public CharSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection<V>(this){
                final Char2ObjectOpenCustomHashMap this$0;
                {
                    this.this$0 = char2ObjectOpenCustomHashMap;
                }

                @Override
                public ObjectIterator<V> iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(Object object) {
                    return this.this$0.containsValue(object);
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public void forEach(Consumer<? super V> consumer) {
                    if (this.this$0.containsNullKey) {
                        consumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (this.this$0.key[n] == '\u0000') continue;
                        consumer.accept(this.this$0.value[n]);
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
        char[] cArray = this.key;
        V[] VArray = this.value;
        int n2 = n - 1;
        char[] cArray2 = new char[n + 1];
        Object[] objectArray = new Object[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (cArray[--n3] == '\u0000') {
            }
            int n5 = HashCommon.mix(this.strategy.hashCode(cArray[n3])) & n2;
            if (cArray2[n5] != '\u0000') {
                while (cArray2[n5 = n5 + 1 & n2] != '\u0000') {
                }
            }
            cArray2[n5] = cArray[n3];
            objectArray[n5] = VArray[n3];
        }
        objectArray[n] = VArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = cArray2;
        this.value = objectArray;
    }

    public Char2ObjectOpenCustomHashMap<V> clone() {
        Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap;
        try {
            char2ObjectOpenCustomHashMap = (Char2ObjectOpenCustomHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2ObjectOpenCustomHashMap.keys = null;
        char2ObjectOpenCustomHashMap.values = null;
        char2ObjectOpenCustomHashMap.entries = null;
        char2ObjectOpenCustomHashMap.containsNullKey = this.containsNullKey;
        char2ObjectOpenCustomHashMap.key = (char[])this.key.clone();
        char2ObjectOpenCustomHashMap.value = (Object[])this.value.clone();
        char2ObjectOpenCustomHashMap.strategy = this.strategy;
        return char2ObjectOpenCustomHashMap;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        int n4 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == '\u0000') {
                ++n3;
            }
            n4 = this.strategy.hashCode(this.key[n3]);
            if (this != this.value[n3]) {
                n4 ^= this.value[n3] == null ? 0 : this.value[n3].hashCode();
            }
            n += n4;
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n] == null ? 0 : this.value[this.n].hashCode();
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        char[] cArray = this.key;
        V[] VArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeChar(cArray[n2]);
            objectOutputStream.writeObject(VArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new char[this.n + 1];
        char[] cArray = this.key;
        this.value = new Object[this.n + 1];
        Object[] objectArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            char c = objectInputStream.readChar();
            Object object = objectInputStream.readObject();
            if (this.strategy.equals(c, '\u0000')) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(this.strategy.hashCode(c)) & this.mask;
                while (cArray[n2] != '\u0000') {
                    n2 = n2 + 1 & this.mask;
                }
            }
            cArray[n2] = c;
            objectArray[n2] = object;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSet char2ObjectEntrySet() {
        return this.char2ObjectEntrySet();
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

    static Object access$300(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
        return char2ObjectOpenCustomHashMap.removeNullEntry();
    }

    static Object access$400(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap, int n) {
        return char2ObjectOpenCustomHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements ObjectIterator<V> {
        final Char2ObjectOpenCustomHashMap this$0;

        public ValueIterator(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
            this.this$0 = char2ObjectOpenCustomHashMap;
            super(char2ObjectOpenCustomHashMap, null);
        }

        @Override
        public V next() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractCharSet {
        final Char2ObjectOpenCustomHashMap this$0;

        private KeySet(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
            this.this$0 = char2ObjectOpenCustomHashMap;
        }

        @Override
        public CharIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                char c = this.this$0.key[n];
                if (c == '\u0000') continue;
                intConsumer.accept(c);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(char c) {
            return this.this$0.containsKey(c);
        }

        @Override
        public boolean remove(char c) {
            int n = this.this$0.size;
            this.this$0.remove(c);
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

        KeySet(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap, 1 var2_2) {
            this(char2ObjectOpenCustomHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements CharIterator {
        final Char2ObjectOpenCustomHashMap this$0;

        public KeyIterator(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
            this.this$0 = char2ObjectOpenCustomHashMap;
            super(char2ObjectOpenCustomHashMap, null);
        }

        @Override
        public char nextChar() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Char2ObjectMap.Entry<V>>
    implements Char2ObjectMap.FastEntrySet<V> {
        final Char2ObjectOpenCustomHashMap this$0;

        private MapEntrySet(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
            this.this$0 = char2ObjectOpenCustomHashMap;
        }

        @Override
        public ObjectIterator<Char2ObjectMap.Entry<V>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Char2ObjectMap.Entry<V>> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Character)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            Object v = entry.getValue();
            if (this.this$0.strategy.equals(c, '\u0000')) {
                return this.this$0.containsNullKey && Objects.equals(this.this$0.value[this.this$0.n], v);
            }
            char[] cArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(c)) & this.this$0.mask;
            char c2 = cArray[n];
            if (c2 == '\u0000') {
                return true;
            }
            if (this.this$0.strategy.equals(c, c2)) {
                return Objects.equals(this.this$0.value[n], v);
            }
            do {
                if ((c2 = cArray[n = n + 1 & this.this$0.mask]) != '\u0000') continue;
                return true;
            } while (!this.this$0.strategy.equals(c, c2));
            return Objects.equals(this.this$0.value[n], v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Character)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            Object v = entry.getValue();
            if (this.this$0.strategy.equals(c, '\u0000')) {
                if (this.this$0.containsNullKey && Objects.equals(this.this$0.value[this.this$0.n], v)) {
                    Char2ObjectOpenCustomHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            char[] cArray = this.this$0.key;
            int n = HashCommon.mix(this.this$0.strategy.hashCode(c)) & this.this$0.mask;
            char c2 = cArray[n];
            if (c2 == '\u0000') {
                return true;
            }
            if (this.this$0.strategy.equals(c2, c)) {
                if (Objects.equals(this.this$0.value[n], v)) {
                    Char2ObjectOpenCustomHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((c2 = cArray[n = n + 1 & this.this$0.mask]) != '\u0000') continue;
                return true;
            } while (!this.this$0.strategy.equals(c2, c) || !Objects.equals(this.this$0.value[n], v));
            Char2ObjectOpenCustomHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Char2ObjectMap.Entry<V>> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractChar2ObjectMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == '\u0000') continue;
                consumer.accept(new AbstractChar2ObjectMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Char2ObjectMap.Entry<V>> consumer) {
            AbstractChar2ObjectMap.BasicEntry basicEntry = new AbstractChar2ObjectMap.BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == '\u0000') continue;
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap, 1 var2_2) {
            this(char2ObjectOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Char2ObjectMap.Entry<V>> {
        private final MapEntry entry;
        final Char2ObjectOpenCustomHashMap this$0;

        private FastEntryIterator(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
            this.this$0 = char2ObjectOpenCustomHashMap;
            super(char2ObjectOpenCustomHashMap, null);
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

        FastEntryIterator(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap, 1 var2_2) {
            this(char2ObjectOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Char2ObjectMap.Entry<V>> {
        private MapEntry entry;
        final Char2ObjectOpenCustomHashMap this$0;

        private EntryIterator(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
            this.this$0 = char2ObjectOpenCustomHashMap;
            super(char2ObjectOpenCustomHashMap, null);
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

        EntryIterator(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap, 1 var2_2) {
            this(char2ObjectOpenCustomHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        CharArrayList wrapped;
        final Char2ObjectOpenCustomHashMap this$0;

        private MapIterator(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
            this.this$0 = char2ObjectOpenCustomHashMap;
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
            char[] cArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                char c = this.wrapped.getChar(-this.pos - 1);
                int n = HashCommon.mix(this.this$0.strategy.hashCode(c)) & this.this$0.mask;
                while (!this.this$0.strategy.equals(c, cArray[n])) {
                    n = n + 1 & this.this$0.mask;
                }
                return n;
            } while (cArray[this.pos] == '\u0000');
            this.last = this.pos;
            return this.last;
        }

        private void shiftKeys(int n) {
            char[] cArray = this.this$0.key;
            while (true) {
                char c;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((c = cArray[n]) == '\u0000') {
                        cArray[n2] = '\u0000';
                        this.this$0.value[n2] = null;
                        return;
                    }
                    int n3 = HashCommon.mix(this.this$0.strategy.hashCode(c)) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new CharArrayList(2);
                    }
                    this.wrapped.add(cArray[n]);
                }
                cArray[n2] = c;
                this.this$0.value[n2] = this.this$0.value[n];
            }
        }

        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNullKey = false;
                this.this$0.value[this.this$0.n] = null;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.getChar(-this.pos - 1));
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

        MapIterator(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap, 1 var2_2) {
            this(char2ObjectOpenCustomHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Char2ObjectMap.Entry<V>,
    Map.Entry<Character, V> {
        int index;
        final Char2ObjectOpenCustomHashMap this$0;

        MapEntry(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap, int n) {
            this.this$0 = char2ObjectOpenCustomHashMap;
            this.index = n;
        }

        MapEntry(Char2ObjectOpenCustomHashMap char2ObjectOpenCustomHashMap) {
            this.this$0 = char2ObjectOpenCustomHashMap;
        }

        @Override
        public char getCharKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public V getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public V setValue(V v) {
            Object v2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = v;
            return v2;
        }

        @Override
        @Deprecated
        public Character getKey() {
            return Character.valueOf(this.this$0.key[this.index]);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.strategy.equals(this.this$0.key[this.index], ((Character)entry.getKey()).charValue()) && Objects.equals(this.this$0.value[this.index], entry.getValue());
        }

        @Override
        public int hashCode() {
            return this.this$0.strategy.hashCode(this.this$0.key[this.index]) ^ (this.this$0.value[this.index] == null ? 0 : this.this$0.value[this.index].hashCode());
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object getKey() {
            return this.getKey();
        }
    }
}

