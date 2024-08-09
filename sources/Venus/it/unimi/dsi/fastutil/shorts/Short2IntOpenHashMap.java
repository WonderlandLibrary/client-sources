/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2IntMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
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
public class Short2IntOpenHashMap
extends AbstractShort2IntMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient short[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Short2IntMap.FastEntrySet entries;
    protected transient ShortSet keys;
    protected transient IntCollection values;

    public Short2IntOpenHashMap(int n, float f) {
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
        this.value = new int[this.n + 1];
    }

    public Short2IntOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Short2IntOpenHashMap() {
        this(16, 0.75f);
    }

    public Short2IntOpenHashMap(Map<? extends Short, ? extends Integer> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Short2IntOpenHashMap(Map<? extends Short, ? extends Integer> map) {
        this(map, 0.75f);
    }

    public Short2IntOpenHashMap(Short2IntMap short2IntMap, float f) {
        this(short2IntMap.size(), f);
        this.putAll(short2IntMap);
    }

    public Short2IntOpenHashMap(Short2IntMap short2IntMap) {
        this(short2IntMap, 0.75f);
    }

    public Short2IntOpenHashMap(short[] sArray, int[] nArray, float f) {
        this(sArray.length, f);
        if (sArray.length != nArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + sArray.length + " and " + nArray.length + ")");
        }
        for (int i = 0; i < sArray.length; ++i) {
            this.put(sArray[i], nArray[i]);
        }
    }

    public Short2IntOpenHashMap(short[] sArray, int[] nArray) {
        this(sArray, nArray, 0.75f);
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
    public void putAll(Map<? extends Short, ? extends Integer> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(short s) {
        if (s == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return -(n + 1);
        }
        if (s == s2) {
            return n;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return -(n + 1);
        } while (s != s2);
        return n;
    }

    private void insert(int n, short s, int n2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = s;
        this.value[n] = n2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public int put(short s, int n) {
        int n2 = this.find(s);
        if (n2 < 0) {
            this.insert(-n2 - 1, s, n);
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

    public int addTo(short s, int n) {
        int n2;
        if (s == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, n);
            }
            n2 = this.n;
            this.containsNullKey = true;
        } else {
            short[] sArray = this.key;
            n2 = HashCommon.mix(s) & this.mask;
            short s2 = sArray[n2];
            if (s2 != 0) {
                if (s2 == s) {
                    return this.addToValue(n2, n);
                }
                while ((s2 = sArray[n2 = n2 + 1 & this.mask]) != 0) {
                    if (s2 != s) continue;
                    return this.addToValue(n2, n);
                }
            }
        }
        this.key[n2] = s;
        this.value[n2] = this.defRetValue + n;
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
                int n3 = HashCommon.mix(s) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            sArray[n2] = s;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public int remove(short s) {
        if (s == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return this.defRetValue;
        }
        if (s == s2) {
            return this.removeEntry(n);
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (s != s2);
        return this.removeEntry(n);
    }

    @Override
    public int get(short s) {
        if (s == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return this.defRetValue;
        }
        if (s == s2) {
            return this.value[n];
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (s != s2);
        return this.value[n];
    }

    @Override
    public boolean containsKey(short s) {
        if (s == 0) {
            return this.containsNullKey;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return true;
        }
        if (s == s2) {
            return false;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (s != s2);
        return false;
    }

    @Override
    public boolean containsValue(int n) {
        int[] nArray = this.value;
        short[] sArray = this.key;
        if (this.containsNullKey && nArray[this.n] == n) {
            return false;
        }
        int n2 = this.n;
        while (n2-- != 0) {
            if (sArray[n2] == 0 || nArray[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getOrDefault(short s, int n) {
        if (s == 0) {
            return this.containsNullKey ? this.value[this.n] : n;
        }
        short[] sArray = this.key;
        int n2 = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n2];
        if (s2 == 0) {
            return n;
        }
        if (s == s2) {
            return this.value[n2];
        }
        do {
            if ((s2 = sArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return n;
        } while (s != s2);
        return this.value[n2];
    }

    @Override
    public int putIfAbsent(short s, int n) {
        int n2 = this.find(s);
        if (n2 >= 0) {
            return this.value[n2];
        }
        this.insert(-n2 - 1, s, n);
        return this.defRetValue;
    }

    @Override
    public boolean remove(short s, int n) {
        if (s == 0) {
            if (this.containsNullKey && n == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        short[] sArray = this.key;
        int n2 = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n2];
        if (s2 == 0) {
            return true;
        }
        if (s == s2 && n == this.value[n2]) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if ((s2 = sArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (s != s2 || n != this.value[n2]);
        this.removeEntry(n2);
        return false;
    }

    @Override
    public boolean replace(short s, int n, int n2) {
        int n3 = this.find(s);
        if (n3 < 0 || n != this.value[n3]) {
            return true;
        }
        this.value[n3] = n2;
        return false;
    }

    @Override
    public int replace(short s, int n) {
        int n2 = this.find(s);
        if (n2 < 0) {
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        this.value[n2] = n;
        return n3;
    }

    @Override
    public int computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        int n2 = intUnaryOperator.applyAsInt(s);
        this.insert(-n - 1, s, n2);
        return n2;
    }

    @Override
    public int computeIfAbsentNullable(short s, IntFunction<? extends Integer> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        Integer n2 = intFunction.apply(s);
        if (n2 == null) {
            return this.defRetValue;
        }
        int n3 = n2;
        this.insert(-n - 1, s, n3);
        return n3;
    }

    @Override
    public int computeIfPresent(short s, BiFunction<? super Short, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0) {
            return this.defRetValue;
        }
        Integer n2 = biFunction.apply((Short)s, (Integer)this.value[n]);
        if (n2 == null) {
            if (s == 0) {
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
    public int compute(short s, BiFunction<? super Short, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        Integer n2 = biFunction.apply((Short)s, n >= 0 ? Integer.valueOf(this.value[n]) : null);
        if (n2 == null) {
            if (n >= 0) {
                if (s == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        int n3 = n2;
        if (n < 0) {
            this.insert(-n - 1, s, n3);
            return n3;
        }
        this.value[n] = n3;
        return this.value[n];
    }

    @Override
    public int merge(short s, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(s);
        if (n2 < 0) {
            this.insert(-n2 - 1, s, n);
            return n;
        }
        Integer n3 = biFunction.apply((Integer)this.value[n2], (Integer)n);
        if (n3 == null) {
            if (s == 0) {
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

    public Short2IntMap.FastEntrySet short2IntEntrySet() {
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
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(this){
                final Short2IntOpenHashMap this$0;
                {
                    this.this$0 = short2IntOpenHashMap;
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
        int[] nArray = this.value;
        int n2 = n - 1;
        short[] sArray2 = new short[n + 1];
        int[] nArray2 = new int[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (sArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(sArray[n3]) & n2;
            if (sArray2[n5] != 0) {
                while (sArray2[n5 = n5 + 1 & n2] != 0) {
                }
            }
            sArray2[n5] = sArray[n3];
            nArray2[n5] = nArray[n3];
        }
        nArray2[n] = nArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = sArray2;
        this.value = nArray2;
    }

    public Short2IntOpenHashMap clone() {
        Short2IntOpenHashMap short2IntOpenHashMap;
        try {
            short2IntOpenHashMap = (Short2IntOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2IntOpenHashMap.keys = null;
        short2IntOpenHashMap.values = null;
        short2IntOpenHashMap.entries = null;
        short2IntOpenHashMap.containsNullKey = this.containsNullKey;
        short2IntOpenHashMap.key = (short[])this.key.clone();
        short2IntOpenHashMap.value = (int[])this.value.clone();
        return short2IntOpenHashMap;
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
            n4 = this.key[n3];
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
        int[] nArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeShort(sArray[n2]);
            objectOutputStream.writeInt(nArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new short[this.n + 1];
        short[] sArray = this.key;
        this.value = new int[this.n + 1];
        int[] nArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            short s = objectInputStream.readShort();
            int n3 = objectInputStream.readInt();
            if (s == 0) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(s) & this.mask;
                while (sArray[n2] != 0) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            sArray[n2] = s;
            nArray[n2] = n3;
        }
    }

    private void checkTable() {
    }

    public ObjectSet short2IntEntrySet() {
        return this.short2IntEntrySet();
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

    static int access$300(Short2IntOpenHashMap short2IntOpenHashMap) {
        return short2IntOpenHashMap.removeNullEntry();
    }

    static int access$400(Short2IntOpenHashMap short2IntOpenHashMap, int n) {
        return short2IntOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements IntIterator {
        final Short2IntOpenHashMap this$0;

        public ValueIterator(Short2IntOpenHashMap short2IntOpenHashMap) {
            this.this$0 = short2IntOpenHashMap;
            super(short2IntOpenHashMap, null);
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
    extends AbstractShortSet {
        final Short2IntOpenHashMap this$0;

        private KeySet(Short2IntOpenHashMap short2IntOpenHashMap) {
            this.this$0 = short2IntOpenHashMap;
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

        KeySet(Short2IntOpenHashMap short2IntOpenHashMap, 1 var2_2) {
            this(short2IntOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ShortIterator {
        final Short2IntOpenHashMap this$0;

        public KeyIterator(Short2IntOpenHashMap short2IntOpenHashMap) {
            this.this$0 = short2IntOpenHashMap;
            super(short2IntOpenHashMap, null);
        }

        @Override
        public short nextShort() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Short2IntMap.Entry>
    implements Short2IntMap.FastEntrySet {
        final Short2IntOpenHashMap this$0;

        private MapEntrySet(Short2IntOpenHashMap short2IntOpenHashMap) {
            this.this$0 = short2IntOpenHashMap;
        }

        @Override
        public ObjectIterator<Short2IntMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Short2IntMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            short s = (Short)entry.getKey();
            int n = (Integer)entry.getValue();
            if (s == 0) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n;
            }
            short[] sArray = this.this$0.key;
            int n2 = HashCommon.mix(s) & this.this$0.mask;
            short s2 = sArray[n2];
            if (s2 == 0) {
                return true;
            }
            if (s == s2) {
                return this.this$0.value[n2] == n;
            }
            do {
                if ((s2 = sArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (s != s2);
            return this.this$0.value[n2] == n;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            short s = (Short)entry.getKey();
            int n = (Integer)entry.getValue();
            if (s == 0) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n) {
                    Short2IntOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            short[] sArray = this.this$0.key;
            int n2 = HashCommon.mix(s) & this.this$0.mask;
            short s2 = sArray[n2];
            if (s2 == 0) {
                return true;
            }
            if (s2 == s) {
                if (this.this$0.value[n2] == n) {
                    Short2IntOpenHashMap.access$400(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((s2 = sArray[n2 = n2 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (s2 != s || this.this$0.value[n2] != n);
            Short2IntOpenHashMap.access$400(this.this$0, n2);
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
        public void forEach(Consumer<? super Short2IntMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractShort2IntMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new AbstractShort2IntMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Short2IntMap.Entry> consumer) {
            AbstractShort2IntMap.BasicEntry basicEntry = new AbstractShort2IntMap.BasicEntry();
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

        MapEntrySet(Short2IntOpenHashMap short2IntOpenHashMap, 1 var2_2) {
            this(short2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Short2IntMap.Entry> {
        private final MapEntry entry;
        final Short2IntOpenHashMap this$0;

        private FastEntryIterator(Short2IntOpenHashMap short2IntOpenHashMap) {
            this.this$0 = short2IntOpenHashMap;
            super(short2IntOpenHashMap, null);
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

        FastEntryIterator(Short2IntOpenHashMap short2IntOpenHashMap, 1 var2_2) {
            this(short2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Short2IntMap.Entry> {
        private MapEntry entry;
        final Short2IntOpenHashMap this$0;

        private EntryIterator(Short2IntOpenHashMap short2IntOpenHashMap) {
            this.this$0 = short2IntOpenHashMap;
            super(short2IntOpenHashMap, null);
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

        EntryIterator(Short2IntOpenHashMap short2IntOpenHashMap, 1 var2_2) {
            this(short2IntOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ShortArrayList wrapped;
        final Short2IntOpenHashMap this$0;

        private MapIterator(Short2IntOpenHashMap short2IntOpenHashMap) {
            this.this$0 = short2IntOpenHashMap;
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
                int n = HashCommon.mix(s) & this.this$0.mask;
                while (s != sArray[n]) {
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
                    int n3 = HashCommon.mix(s) & this.this$0.mask;
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

        MapIterator(Short2IntOpenHashMap short2IntOpenHashMap, 1 var2_2) {
            this(short2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Short2IntMap.Entry,
    Map.Entry<Short, Integer> {
        int index;
        final Short2IntOpenHashMap this$0;

        MapEntry(Short2IntOpenHashMap short2IntOpenHashMap, int n) {
            this.this$0 = short2IntOpenHashMap;
            this.index = n;
        }

        MapEntry(Short2IntOpenHashMap short2IntOpenHashMap) {
            this.this$0 = short2IntOpenHashMap;
        }

        @Override
        public short getShortKey() {
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
        public Short getKey() {
            return this.this$0.key[this.index];
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
            return this.this$0.key[this.index] == (Short)entry.getKey() && this.this$0.value[this.index] == (Integer)entry.getValue();
        }

        @Override
        public int hashCode() {
            return this.this$0.key[this.index] ^ this.this$0.value[this.index];
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

