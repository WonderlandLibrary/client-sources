/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIntPair;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
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
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Int2IntOpenHashMap
extends AbstractInt2IntMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Int2IntMap.FastEntrySet entries;
    protected transient IntSet keys;
    protected transient IntCollection values;

    public Int2IntOpenHashMap(int n, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (n < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.arraySize(n, f);
        this.mask = this.n - 1;
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = new int[this.n + 1];
        this.value = new int[this.n + 1];
    }

    public Int2IntOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Int2IntOpenHashMap() {
        this(16, 0.75f);
    }

    public Int2IntOpenHashMap(Map<? extends Integer, ? extends Integer> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Int2IntOpenHashMap(Map<? extends Integer, ? extends Integer> map) {
        this(map, 0.75f);
    }

    public Int2IntOpenHashMap(Int2IntMap int2IntMap, float f) {
        this(int2IntMap.size(), f);
        this.putAll(int2IntMap);
    }

    public Int2IntOpenHashMap(Int2IntMap int2IntMap) {
        this(int2IntMap, 0.75f);
    }

    public Int2IntOpenHashMap(int[] nArray, int[] nArray2, float f) {
        this(nArray.length, f);
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + nArray.length + " and " + nArray2.length + ")");
        }
        for (int i = 0; i < nArray.length; ++i) {
            this.put(nArray[i], nArray2[i]);
        }
    }

    public Int2IntOpenHashMap(int[] nArray, int[] nArray2) {
        this(nArray, nArray2, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    public void ensureCapacity(int n) {
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
    public void putAll(Map<? extends Integer, ? extends Integer> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(int n) {
        if (n == 0) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return -(n2 + 1);
        }
        if (n == n3) {
            return n2;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return -(n2 + 1);
        } while (n != n3);
        return n2;
    }

    private void insert(int n, int n2, int n3) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = n2;
        this.value[n] = n3;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public int put(int n, int n2) {
        int n3 = this.find(n);
        if (n3 < 0) {
            this.insert(-n3 - 1, n, n2);
            return this.defRetValue;
        }
        int n4 = this.value[n3];
        this.value[n3] = n2;
        return n4;
    }

    private int addToValue(int n, int n2) {
        int n3 = this.value[n];
        this.value[n] = n3 + n2;
        return n3;
    }

    public int addTo(int n, int n2) {
        int n3;
        if (n == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, n2);
            }
            n3 = this.n;
            this.containsNullKey = true;
        } else {
            int[] nArray = this.key;
            n3 = HashCommon.mix(n) & this.mask;
            int n4 = nArray[n3];
            if (n4 != 0) {
                if (n4 == n) {
                    return this.addToValue(n3, n2);
                }
                while ((n4 = nArray[n3 = n3 + 1 & this.mask]) != 0) {
                    if (n4 != n) continue;
                    return this.addToValue(n3, n2);
                }
            }
        }
        this.key[n3] = n;
        this.value[n3] = this.defRetValue + n2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
    }

    protected final void shiftKeys(int n) {
        int[] nArray = this.key;
        while (true) {
            int n2;
            int n3 = n;
            n = n3 + 1 & this.mask;
            while (true) {
                if ((n2 = nArray[n]) == 0) {
                    nArray[n3] = 0;
                    return;
                }
                int n4 = HashCommon.mix(n2) & this.mask;
                if (n3 <= n ? n3 >= n4 || n4 > n : n3 >= n4 && n4 > n) break;
                n = n + 1 & this.mask;
            }
            nArray[n3] = n2;
            this.value[n3] = this.value[n];
        }
    }

    @Override
    public int remove(int n) {
        if (n == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return this.defRetValue;
        }
        if (n == n3) {
            return this.removeEntry(n2);
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (n != n3);
        return this.removeEntry(n2);
    }

    @Override
    public int get(int n) {
        if (n == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return this.defRetValue;
        }
        if (n == n3) {
            return this.value[n2];
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return this.defRetValue;
        } while (n != n3);
        return this.value[n2];
    }

    @Override
    public boolean containsKey(int n) {
        if (n == 0) {
            return this.containsNullKey;
        }
        int[] nArray = this.key;
        int n2 = HashCommon.mix(n) & this.mask;
        int n3 = nArray[n2];
        if (n3 == 0) {
            return true;
        }
        if (n == n3) {
            return false;
        }
        do {
            if ((n3 = nArray[n2 = n2 + 1 & this.mask]) != 0) continue;
            return true;
        } while (n != n3);
        return false;
    }

    @Override
    public boolean containsValue(int n) {
        int[] nArray = this.value;
        int[] nArray2 = this.key;
        if (this.containsNullKey && nArray[this.n] == n) {
            return false;
        }
        int n2 = this.n;
        while (n2-- != 0) {
            if (nArray2[n2] == 0 || nArray[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getOrDefault(int n, int n2) {
        if (n == 0) {
            return this.containsNullKey ? this.value[this.n] : n2;
        }
        int[] nArray = this.key;
        int n3 = HashCommon.mix(n) & this.mask;
        int n4 = nArray[n3];
        if (n4 == 0) {
            return n2;
        }
        if (n == n4) {
            return this.value[n3];
        }
        do {
            if ((n4 = nArray[n3 = n3 + 1 & this.mask]) != 0) continue;
            return n2;
        } while (n != n4);
        return this.value[n3];
    }

    @Override
    public int putIfAbsent(int n, int n2) {
        int n3 = this.find(n);
        if (n3 >= 0) {
            return this.value[n3];
        }
        this.insert(-n3 - 1, n, n2);
        return this.defRetValue;
    }

    @Override
    public boolean remove(int n, int n2) {
        if (n == 0) {
            if (this.containsNullKey && n2 == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        int[] nArray = this.key;
        int n3 = HashCommon.mix(n) & this.mask;
        int n4 = nArray[n3];
        if (n4 == 0) {
            return true;
        }
        if (n == n4 && n2 == this.value[n3]) {
            this.removeEntry(n3);
            return false;
        }
        do {
            if ((n4 = nArray[n3 = n3 + 1 & this.mask]) != 0) continue;
            return true;
        } while (n != n4 || n2 != this.value[n3]);
        this.removeEntry(n3);
        return false;
    }

    @Override
    public boolean replace(int n, int n2, int n3) {
        int n4 = this.find(n);
        if (n4 < 0 || n2 != this.value[n4]) {
            return true;
        }
        this.value[n4] = n3;
        return false;
    }

    @Override
    public int replace(int n, int n2) {
        int n3 = this.find(n);
        if (n3 < 0) {
            return this.defRetValue;
        }
        int n4 = this.value[n3];
        this.value[n3] = n2;
        return n4;
    }

    @Override
    public int computeIfAbsent(int n, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        int n3 = intUnaryOperator.applyAsInt(n);
        this.insert(-n2 - 1, n, n3);
        return n3;
    }

    @Override
    public int computeIfAbsent(int n, Int2IntFunction int2IntFunction) {
        Objects.requireNonNull(int2IntFunction);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        if (!int2IntFunction.containsKey(n)) {
            return this.defRetValue;
        }
        int n3 = int2IntFunction.get(n);
        this.insert(-n2 - 1, n, n3);
        return n3;
    }

    @Override
    public int computeIfAbsentNullable(int n, IntFunction<? extends Integer> intFunction) {
        Objects.requireNonNull(intFunction);
        int n2 = this.find(n);
        if (n2 >= 0) {
            return this.value[n2];
        }
        Integer n3 = intFunction.apply(n);
        if (n3 == null) {
            return this.defRetValue;
        }
        int n4 = n3;
        this.insert(-n2 - 1, n, n4);
        return n4;
    }

    @Override
    public int computeIfPresent(int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        if (n2 < 0) {
            return this.defRetValue;
        }
        Integer n3 = biFunction.apply((Integer)n, (Integer)this.value[n2]);
        if (n3 == null) {
            if (n == 0) {
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
    public int compute(int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(n);
        Integer n3 = biFunction.apply((Integer)n, n2 >= 0 ? Integer.valueOf(this.value[n2]) : null);
        if (n3 == null) {
            if (n2 >= 0) {
                if (n == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n2);
                }
            }
            return this.defRetValue;
        }
        int n4 = n3;
        if (n2 < 0) {
            this.insert(-n2 - 1, n, n4);
            return n4;
        }
        this.value[n2] = n4;
        return this.value[n2];
    }

    @Override
    public int merge(int n, int n2, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n3 = this.find(n);
        if (n3 < 0) {
            if (n3 < 0) {
                this.insert(-n3 - 1, n, n2);
            } else {
                this.value[n3] = n2;
            }
            return n2;
        }
        Integer n4 = biFunction.apply((Integer)this.value[n3], (Integer)n2);
        if (n4 == null) {
            if (n == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n3);
            }
            return this.defRetValue;
        }
        this.value[n3] = n4;
        return this.value[n3];
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Int2IntMap.FastEntrySet int2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public IntSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(this){
                final Int2IntOpenHashMap this$0;
                {
                    this.this$0 = int2IntOpenHashMap;
                }

                @Override
                public IntIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public IntSpliterator spliterator() {
                    return new ValueSpliterator(this.this$0);
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
                public Spliterator spliterator() {
                    return this.spliterator();
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
        return this.trim(this.size);
    }

    public boolean trim(int n) {
        int n2 = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.f));
        if (n2 >= this.n || this.size > HashCommon.maxFill(n2, this.f)) {
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
        int[] nArray = this.key;
        int[] nArray2 = this.value;
        int n2 = n - 1;
        int[] nArray3 = new int[n + 1];
        int[] nArray4 = new int[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (nArray[--n3] == 0) {
            }
            int n5 = HashCommon.mix(nArray[n3]) & n2;
            if (nArray3[n5] != 0) {
                while (nArray3[n5 = n5 + 1 & n2] != 0) {
                }
            }
            nArray3[n5] = nArray[n3];
            nArray4[n5] = nArray2[n3];
        }
        nArray4[n] = nArray2[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = nArray3;
        this.value = nArray4;
    }

    public Int2IntOpenHashMap clone() {
        Int2IntOpenHashMap int2IntOpenHashMap;
        try {
            int2IntOpenHashMap = (Int2IntOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2IntOpenHashMap.keys = null;
        int2IntOpenHashMap.values = null;
        int2IntOpenHashMap.entries = null;
        int2IntOpenHashMap.containsNullKey = this.containsNullKey;
        int2IntOpenHashMap.key = (int[])this.key.clone();
        int2IntOpenHashMap.value = (int[])this.value.clone();
        return int2IntOpenHashMap;
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
        int[] nArray = this.key;
        int[] nArray2 = this.value;
        EntryIterator entryIterator = new EntryIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = entryIterator.nextEntry();
            objectOutputStream.writeInt(nArray[n2]);
            objectOutputStream.writeInt(nArray2[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new int[this.n + 1];
        int[] nArray = this.key;
        this.value = new int[this.n + 1];
        int[] nArray2 = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            int n3 = objectInputStream.readInt();
            int n4 = objectInputStream.readInt();
            if (n3 == 0) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(n3) & this.mask;
                while (nArray[n2] != 0) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            nArray[n2] = n3;
            nArray2[n2] = n4;
        }
    }

    private void checkTable() {
    }

    public ObjectSet int2IntEntrySet() {
        return this.int2IntEntrySet();
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

    static int access$100(Int2IntOpenHashMap int2IntOpenHashMap) {
        return int2IntOpenHashMap.realSize();
    }

    static int access$400(Int2IntOpenHashMap int2IntOpenHashMap) {
        return int2IntOpenHashMap.removeNullEntry();
    }

    static int access$500(Int2IntOpenHashMap int2IntOpenHashMap, int n) {
        return int2IntOpenHashMap.removeEntry(n);
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Int2IntMap.Entry>
    implements Int2IntMap.FastEntrySet {
        final Int2IntOpenHashMap this$0;

        private MapEntrySet(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
        }

        @Override
        public ObjectIterator<Int2IntMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Int2IntMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0, null);
        }

        @Override
        public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
            return new EntrySpliterator(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            int n2 = (Integer)entry.getValue();
            if (n == 0) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n2;
            }
            int[] nArray = this.this$0.key;
            int n3 = HashCommon.mix(n) & this.this$0.mask;
            int n4 = nArray[n3];
            if (n4 == 0) {
                return true;
            }
            if (n == n4) {
                return this.this$0.value[n3] == n2;
            }
            do {
                if ((n4 = nArray[n3 = n3 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (n != n4);
            return this.this$0.value[n3] == n2;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            int n2 = (Integer)entry.getValue();
            if (n == 0) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n2) {
                    Int2IntOpenHashMap.access$400(this.this$0);
                    return false;
                }
                return true;
            }
            int[] nArray = this.this$0.key;
            int n3 = HashCommon.mix(n) & this.this$0.mask;
            int n4 = nArray[n3];
            if (n4 == 0) {
                return true;
            }
            if (n4 == n) {
                if (this.this$0.value[n3] == n2) {
                    Int2IntOpenHashMap.access$500(this.this$0, n3);
                    return false;
                }
                return true;
            }
            do {
                if ((n4 = nArray[n3 = n3 + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (n4 != n || this.this$0.value[n3] != n2);
            Int2IntOpenHashMap.access$500(this.this$0, n3);
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
        public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new MapEntry(this.this$0, this.this$0.n));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new MapEntry(this.this$0, n));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
            MapEntry mapEntry = new MapEntry(this.this$0);
            if (this.this$0.containsNullKey) {
                mapEntry.index = this.this$0.n;
                consumer.accept(mapEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                mapEntry.index = n;
                consumer.accept(mapEntry);
            }
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        MapEntrySet(Int2IntOpenHashMap int2IntOpenHashMap, 1 var2_2) {
            this(int2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractIntSet {
        final Int2IntOpenHashMap this$0;

        private KeySet(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
        }

        @Override
        public IntIterator iterator() {
            return new KeyIterator(this.this$0);
        }

        @Override
        public IntSpliterator spliterator() {
            return new KeySpliterator(this.this$0);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                int n2 = this.this$0.key[n];
                if (n2 == 0) continue;
                intConsumer.accept(n2);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(int n) {
            return this.this$0.containsKey(n);
        }

        @Override
        public boolean remove(int n) {
            int n2 = this.this$0.size;
            this.this$0.remove(n);
            return this.this$0.size != n2;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Int2IntOpenHashMap int2IntOpenHashMap, 1 var2_2) {
            this(int2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class EntryIterator
    extends MapIterator<Consumer<? super Int2IntMap.Entry>>
    implements ObjectIterator<Int2IntMap.Entry> {
        private MapEntry entry;
        final Int2IntOpenHashMap this$0;

        private EntryIterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap, null);
        }

        @Override
        public MapEntry next() {
            this.entry = new MapEntry(this.this$0, this.nextEntry());
            return this.entry;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> consumer, int n) {
            this.entry = new MapEntry(this.this$0, n);
            consumer.accept(this.entry);
        }

        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override
        public Object next() {
            return this.next();
        }

        EntryIterator(Int2IntOpenHashMap int2IntOpenHashMap, 1 var2_2) {
            this(int2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ValueSpliterator
    extends MapSpliterator<IntConsumer, ValueSpliterator>
    implements IntSpliterator {
        private static final int POST_SPLIT_CHARACTERISTICS = 256;
        final Int2IntOpenHashMap this$0;

        ValueSpliterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap);
        }

        ValueSpliterator(Int2IntOpenHashMap int2IntOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap, n, n2, bl, bl2);
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 256 : 320;
        }

        @Override
        final void acceptOnIndex(IntConsumer intConsumer, int n) {
            intConsumer.accept(this.this$0.value[n]);
        }

        @Override
        final ValueSpliterator makeForSplit(int n, int n2, boolean bl) {
            return new ValueSpliterator(this.this$0, n, n2, bl, true);
        }

        @Override
        MapSpliterator makeForSplit(int n, int n2, boolean bl) {
            return this.makeForSplit(n, n2, bl);
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((IntConsumer)object, n);
        }

        @Override
        public IntSpliterator trySplit() {
            return (IntSpliterator)super.trySplit();
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            return super.tryAdvance(intConsumer);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt)super.trySplit();
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive)super.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }
    }

    private final class ValueIterator
    extends MapIterator<IntConsumer>
    implements IntIterator {
        final Int2IntOpenHashMap this$0;

        public ValueIterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap, null);
        }

        @Override
        final void acceptOnIndex(IntConsumer intConsumer, int n) {
            intConsumer.accept(this.this$0.value[n]);
        }

        @Override
        public int nextInt() {
            return this.this$0.value[this.nextEntry()];
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((IntConsumer)object, n);
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySpliterator
    extends MapSpliterator<IntConsumer, KeySpliterator>
    implements IntSpliterator {
        private static final int POST_SPLIT_CHARACTERISTICS = 257;
        final Int2IntOpenHashMap this$0;

        KeySpliterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap);
        }

        KeySpliterator(Int2IntOpenHashMap int2IntOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap, n, n2, bl, bl2);
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 257 : 321;
        }

        @Override
        final void acceptOnIndex(IntConsumer intConsumer, int n) {
            intConsumer.accept(this.this$0.key[n]);
        }

        @Override
        final KeySpliterator makeForSplit(int n, int n2, boolean bl) {
            return new KeySpliterator(this.this$0, n, n2, bl, true);
        }

        @Override
        MapSpliterator makeForSplit(int n, int n2, boolean bl) {
            return this.makeForSplit(n, n2, bl);
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((IntConsumer)object, n);
        }

        @Override
        public IntSpliterator trySplit() {
            return (IntSpliterator)super.trySplit();
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            return super.tryAdvance(intConsumer);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt)super.trySplit();
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive)super.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }
    }

    private final class KeyIterator
    extends MapIterator<IntConsumer>
    implements IntIterator {
        final Int2IntOpenHashMap this$0;

        public KeyIterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap, null);
        }

        @Override
        final void acceptOnIndex(IntConsumer intConsumer, int n) {
            intConsumer.accept(this.this$0.key[n]);
        }

        @Override
        public int nextInt() {
            return this.this$0.key[this.nextEntry()];
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((IntConsumer)object, n);
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class EntrySpliterator
    extends MapSpliterator<Consumer<? super Int2IntMap.Entry>, EntrySpliterator>
    implements ObjectSpliterator<Int2IntMap.Entry> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Int2IntOpenHashMap this$0;

        EntrySpliterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap);
        }

        EntrySpliterator(Int2IntOpenHashMap int2IntOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap, n, n2, bl, bl2);
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> consumer, int n) {
            consumer.accept(new MapEntry(this.this$0, n));
        }

        @Override
        final EntrySpliterator makeForSplit(int n, int n2, boolean bl) {
            return new EntrySpliterator(this.this$0, n, n2, bl, true);
        }

        @Override
        MapSpliterator makeForSplit(int n, int n2, boolean bl) {
            return this.makeForSplit(n, n2, bl);
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public ObjectSpliterator trySplit() {
            return (ObjectSpliterator)super.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override
        public boolean tryAdvance(Consumer consumer) {
            return super.tryAdvance(consumer);
        }
    }

    private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>> {
        int pos;
        int max;
        int c;
        boolean mustReturnNull;
        boolean hasSplit;
        final Int2IntOpenHashMap this$0;

        MapSpliterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
        }

        MapSpliterator(Int2IntOpenHashMap int2IntOpenHashMap, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = int2IntOpenHashMap;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
            this.pos = n;
            this.max = n2;
            this.mustReturnNull = bl;
            this.hasSplit = bl2;
        }

        abstract void acceptOnIndex(ConsumerType var1, int var2);

        abstract SplitType makeForSplit(int var1, int var2, boolean var3);

        public boolean tryAdvance(ConsumerType ConsumerType) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                this.acceptOnIndex(ConsumerType, this.this$0.n);
                return false;
            }
            int[] nArray = this.this$0.key;
            while (this.pos < this.max) {
                if (nArray[this.pos] != 0) {
                    ++this.c;
                    this.acceptOnIndex(ConsumerType, this.pos++);
                    return false;
                }
                ++this.pos;
            }
            return true;
        }

        public void forEachRemaining(ConsumerType ConsumerType) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                this.acceptOnIndex(ConsumerType, this.this$0.n);
            }
            int[] nArray = this.this$0.key;
            while (this.pos < this.max) {
                if (nArray[this.pos] != 0) {
                    this.acceptOnIndex(ConsumerType, this.pos);
                    ++this.c;
                }
                ++this.pos;
            }
        }

        public long estimateSize() {
            if (!this.hasSplit) {
                return this.this$0.size - this.c;
            }
            return Math.min((long)(this.this$0.size - this.c), (long)((double)Int2IntOpenHashMap.access$100(this.this$0) / (double)this.this$0.n * (double)(this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
        }

        public SplitType trySplit() {
            if (this.pos >= this.max - 1) {
                return null;
            }
            int n = this.max - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            int n2 = this.pos + n;
            int n3 = this.pos;
            int n4 = n2;
            SplitType SplitType = this.makeForSplit(n3, n4, this.mustReturnNull);
            this.pos = n2;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return SplitType;
        }

        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (l == 0L) {
                return 0L;
            }
            long l2 = 0L;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++l2;
                --l;
            }
            int[] nArray = this.this$0.key;
            while (this.pos < this.max && l > 0L) {
                if (nArray[this.pos++] == 0) continue;
                ++l2;
                --l;
            }
            return l2;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class FastEntryIterator
    extends MapIterator<Consumer<? super Int2IntMap.Entry>>
    implements ObjectIterator<Int2IntMap.Entry> {
        private final MapEntry entry;
        final Int2IntOpenHashMap this$0;

        private FastEntryIterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            super(int2IntOpenHashMap, null);
            this.entry = new MapEntry(this.this$0);
        }

        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }

        @Override
        final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> consumer, int n) {
            this.entry.index = n;
            consumer.accept(this.entry);
        }

        @Override
        void acceptOnIndex(Object object, int n) {
            this.acceptOnIndex((Consumer)object, n);
        }

        @Override
        public void forEachRemaining(Consumer consumer) {
            super.forEachRemaining(consumer);
        }

        @Override
        public Object next() {
            return this.next();
        }

        FastEntryIterator(Int2IntOpenHashMap int2IntOpenHashMap, 1 var2_2) {
            this(int2IntOpenHashMap);
        }
    }

    private abstract class MapIterator<ConsumerType> {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        IntArrayList wrapped;
        final Int2IntOpenHashMap this$0;

        private MapIterator(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNullKey = this.this$0.containsNullKey;
        }

        abstract void acceptOnIndex(ConsumerType var1, int var2);

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
            int[] nArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                int n = this.wrapped.getInt(-this.pos - 1);
                int n2 = HashCommon.mix(n) & this.this$0.mask;
                while (n != nArray[n2]) {
                    n2 = n2 + 1 & this.this$0.mask;
                }
                return n2;
            } while (nArray[this.pos] == 0);
            this.last = this.pos;
            return this.last;
        }

        public void forEachRemaining(ConsumerType ConsumerType) {
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                this.last = this.this$0.n;
                this.acceptOnIndex(ConsumerType, this.last);
                --this.c;
            }
            int[] nArray = this.this$0.key;
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    int n = this.wrapped.getInt(-this.pos - 1);
                    int n2 = HashCommon.mix(n) & this.this$0.mask;
                    while (n != nArray[n2]) {
                        n2 = n2 + 1 & this.this$0.mask;
                    }
                    this.acceptOnIndex(ConsumerType, n2);
                    --this.c;
                    continue;
                }
                if (nArray[this.pos] == 0) continue;
                this.last = this.pos;
                this.acceptOnIndex(ConsumerType, this.last);
                --this.c;
            }
        }

        private void shiftKeys(int n) {
            int[] nArray = this.this$0.key;
            while (true) {
                int n2;
                int n3 = n;
                n = n3 + 1 & this.this$0.mask;
                while (true) {
                    if ((n2 = nArray[n]) == 0) {
                        nArray[n3] = 0;
                        return;
                    }
                    int n4 = HashCommon.mix(n2) & this.this$0.mask;
                    if (n3 <= n ? n3 >= n4 || n4 > n : n3 >= n4 && n4 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n3) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(nArray[n]);
                }
                nArray[n3] = n2;
                this.this$0.value[n3] = this.this$0.value[n];
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
                this.this$0.remove(this.wrapped.getInt(-this.pos - 1));
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

        MapIterator(Int2IntOpenHashMap int2IntOpenHashMap, 1 var2_2) {
            this(int2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Int2IntMap.Entry,
    Map.Entry<Integer, Integer>,
    IntIntPair {
        int index;
        final Int2IntOpenHashMap this$0;

        MapEntry(Int2IntOpenHashMap int2IntOpenHashMap, int n) {
            this.this$0 = int2IntOpenHashMap;
            this.index = n;
        }

        MapEntry(Int2IntOpenHashMap int2IntOpenHashMap) {
            this.this$0 = int2IntOpenHashMap;
        }

        @Override
        public int getIntKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public int leftInt() {
            return this.this$0.key[this.index];
        }

        @Override
        public int getIntValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public int rightInt() {
            return this.this$0.value[this.index];
        }

        @Override
        public int setValue(int n) {
            int n2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = n;
            return n2;
        }

        @Override
        public IntIntPair right(int n) {
            this.this$0.value[this.index] = n;
            return this;
        }

        @Override
        @Deprecated
        public Integer getKey() {
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
            return this.this$0.key[this.index] == (Integer)entry.getKey() && this.this$0.value[this.index] == (Integer)entry.getValue();
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

