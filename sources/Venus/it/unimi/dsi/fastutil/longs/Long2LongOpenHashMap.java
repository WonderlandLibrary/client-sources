/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLong2LongMap;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongCollection;
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
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Long2LongOpenHashMap
extends AbstractLong2LongMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient long[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Long2LongMap.FastEntrySet entries;
    protected transient LongSet keys;
    protected transient LongCollection values;

    public Long2LongOpenHashMap(int n, float f) {
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
        this.value = new long[this.n + 1];
    }

    public Long2LongOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Long2LongOpenHashMap() {
        this(16, 0.75f);
    }

    public Long2LongOpenHashMap(Map<? extends Long, ? extends Long> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Long2LongOpenHashMap(Map<? extends Long, ? extends Long> map) {
        this(map, 0.75f);
    }

    public Long2LongOpenHashMap(Long2LongMap long2LongMap, float f) {
        this(long2LongMap.size(), f);
        this.putAll(long2LongMap);
    }

    public Long2LongOpenHashMap(Long2LongMap long2LongMap) {
        this(long2LongMap, 0.75f);
    }

    public Long2LongOpenHashMap(long[] lArray, long[] lArray2, float f) {
        this(lArray.length, f);
        if (lArray.length != lArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + lArray.length + " and " + lArray2.length + ")");
        }
        for (int i = 0; i < lArray.length; ++i) {
            this.put(lArray[i], lArray2[i]);
        }
    }

    public Long2LongOpenHashMap(long[] lArray, long[] lArray2) {
        this(lArray, lArray2, 0.75f);
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

    private long removeEntry(int n) {
        long l = this.value[n];
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return l;
    }

    private long removeNullEntry() {
        this.containsNullKey = false;
        long l = this.value[this.n];
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return l;
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Long> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(long l) {
        if (l == 0L) {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        long[] lArray = this.key;
        int n = (int)HashCommon.mix(l) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return -(n + 1);
        }
        if (l == l2) {
            return n;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return -(n + 1);
        } while (l != l2);
        return n;
    }

    private void insert(int n, long l, long l2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = l;
        this.value[n] = l2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public long put(long l, long l2) {
        int n = this.find(l);
        if (n < 0) {
            this.insert(-n - 1, l, l2);
            return this.defRetValue;
        }
        long l3 = this.value[n];
        this.value[n] = l2;
        return l3;
    }

    private long addToValue(int n, long l) {
        long l2 = this.value[n];
        this.value[n] = l2 + l;
        return l2;
    }

    public long addTo(long l, long l2) {
        int n;
        if (l == 0L) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, l2);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            long[] lArray = this.key;
            n = (int)HashCommon.mix(l) & this.mask;
            long l3 = lArray[n];
            if (l3 != 0L) {
                if (l3 == l) {
                    return this.addToValue(n, l2);
                }
                while ((l3 = lArray[n = n + 1 & this.mask]) != 0L) {
                    if (l3 != l) continue;
                    return this.addToValue(n, l2);
                }
            }
        }
        this.key[n] = l;
        this.value[n] = this.defRetValue + l2;
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
                int n3 = (int)HashCommon.mix(l) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            lArray[n2] = l;
            this.value[n2] = this.value[n];
        }
    }

    @Override
    public long remove(long l) {
        if (l == 0L) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        long[] lArray = this.key;
        int n = (int)HashCommon.mix(l) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return this.defRetValue;
        }
        if (l == l2) {
            return this.removeEntry(n);
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (l != l2);
        return this.removeEntry(n);
    }

    @Override
    public long get(long l) {
        if (l == 0L) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        long[] lArray = this.key;
        int n = (int)HashCommon.mix(l) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return this.defRetValue;
        }
        if (l == l2) {
            return this.value[n];
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (l != l2);
        return this.value[n];
    }

    @Override
    public boolean containsKey(long l) {
        if (l == 0L) {
            return this.containsNullKey;
        }
        long[] lArray = this.key;
        int n = (int)HashCommon.mix(l) & this.mask;
        long l2 = lArray[n];
        if (l2 == 0L) {
            return true;
        }
        if (l == l2) {
            return false;
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (l != l2);
        return false;
    }

    @Override
    public boolean containsValue(long l) {
        long[] lArray = this.value;
        long[] lArray2 = this.key;
        if (this.containsNullKey && lArray[this.n] == l) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (lArray2[n] == 0L || lArray[n] != l) continue;
            return false;
        }
        return true;
    }

    @Override
    public long getOrDefault(long l, long l2) {
        if (l == 0L) {
            return this.containsNullKey ? this.value[this.n] : l2;
        }
        long[] lArray = this.key;
        int n = (int)HashCommon.mix(l) & this.mask;
        long l3 = lArray[n];
        if (l3 == 0L) {
            return l2;
        }
        if (l == l3) {
            return this.value[n];
        }
        do {
            if ((l3 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return l2;
        } while (l != l3);
        return this.value[n];
    }

    @Override
    public long putIfAbsent(long l, long l2) {
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, l, l2);
        return this.defRetValue;
    }

    @Override
    public boolean remove(long l, long l2) {
        if (l == 0L) {
            if (this.containsNullKey && l2 == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        long[] lArray = this.key;
        int n = (int)HashCommon.mix(l) & this.mask;
        long l3 = lArray[n];
        if (l3 == 0L) {
            return true;
        }
        if (l == l3 && l2 == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((l3 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return true;
        } while (l != l3 || l2 != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(long l, long l2, long l3) {
        int n = this.find(l);
        if (n < 0 || l2 != this.value[n]) {
            return true;
        }
        this.value[n] = l3;
        return false;
    }

    @Override
    public long replace(long l, long l2) {
        int n = this.find(l);
        if (n < 0) {
            return this.defRetValue;
        }
        long l3 = this.value[n];
        this.value[n] = l2;
        return l3;
    }

    @Override
    public long computeIfAbsent(long l, LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        long l2 = longUnaryOperator.applyAsLong(l);
        this.insert(-n - 1, l, l2);
        return l2;
    }

    @Override
    public long computeIfAbsentNullable(long l, LongFunction<? extends Long> longFunction) {
        Objects.requireNonNull(longFunction);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        Long l2 = longFunction.apply(l);
        if (l2 == null) {
            return this.defRetValue;
        }
        long l3 = l2;
        this.insert(-n - 1, l, l3);
        return l3;
    }

    @Override
    public long computeIfPresent(long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0) {
            return this.defRetValue;
        }
        Long l2 = biFunction.apply((Long)l, (Long)this.value[n]);
        if (l2 == null) {
            if (l == 0L) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = l2;
        return this.value[n];
    }

    @Override
    public long compute(long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        Long l2 = biFunction.apply((Long)l, n >= 0 ? Long.valueOf(this.value[n]) : null);
        if (l2 == null) {
            if (n >= 0) {
                if (l == 0L) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        long l3 = l2;
        if (n < 0) {
            this.insert(-n - 1, l, l3);
            return l3;
        }
        this.value[n] = l3;
        return this.value[n];
    }

    @Override
    public long merge(long l, long l2, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0) {
            this.insert(-n - 1, l, l2);
            return l2;
        }
        Long l3 = biFunction.apply((Long)this.value[n], (Long)l2);
        if (l3 == null) {
            if (l == 0L) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = l3;
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

    public Long2LongMap.FastEntrySet long2LongEntrySet() {
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
    public LongCollection values() {
        if (this.values == null) {
            this.values = new AbstractLongCollection(this){
                final Long2LongOpenHashMap this$0;
                {
                    this.this$0 = long2LongOpenHashMap;
                }

                @Override
                public LongIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(long l) {
                    return this.this$0.containsValue(l);
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public void forEach(LongConsumer longConsumer) {
                    if (this.this$0.containsNullKey) {
                        longConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (this.this$0.key[n] == 0L) continue;
                        longConsumer.accept(this.this$0.value[n]);
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
        long[] lArray2 = this.value;
        int n2 = n - 1;
        long[] lArray3 = new long[n + 1];
        long[] lArray4 = new long[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (lArray[--n3] == 0L) {
            }
            int n5 = (int)HashCommon.mix(lArray[n3]) & n2;
            if (lArray3[n5] != 0L) {
                while (lArray3[n5 = n5 + 1 & n2] != 0L) {
                }
            }
            lArray3[n5] = lArray[n3];
            lArray4[n5] = lArray2[n3];
        }
        lArray4[n] = lArray2[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = lArray3;
        this.value = lArray4;
    }

    public Long2LongOpenHashMap clone() {
        Long2LongOpenHashMap long2LongOpenHashMap;
        try {
            long2LongOpenHashMap = (Long2LongOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2LongOpenHashMap.keys = null;
        long2LongOpenHashMap.values = null;
        long2LongOpenHashMap.entries = null;
        long2LongOpenHashMap.containsNullKey = this.containsNullKey;
        long2LongOpenHashMap.key = (long[])this.key.clone();
        long2LongOpenHashMap.value = (long[])this.value.clone();
        return long2LongOpenHashMap;
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
            n4 = HashCommon.long2int(this.key[n3]);
            n += (n4 ^= HashCommon.long2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.long2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        long[] lArray = this.key;
        long[] lArray2 = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeLong(lArray[n2]);
            objectOutputStream.writeLong(lArray2[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new long[this.n + 1];
        long[] lArray = this.key;
        this.value = new long[this.n + 1];
        long[] lArray2 = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            long l = objectInputStream.readLong();
            long l2 = objectInputStream.readLong();
            if (l == 0L) {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = (int)HashCommon.mix(l) & this.mask;
                while (lArray[n2] != 0L) {
                    n2 = n2 + 1 & this.mask;
                }
            }
            lArray[n2] = l;
            lArray2[n2] = l2;
        }
    }

    private void checkTable() {
    }

    public ObjectSet long2LongEntrySet() {
        return this.long2LongEntrySet();
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

    static long access$300(Long2LongOpenHashMap long2LongOpenHashMap) {
        return long2LongOpenHashMap.removeNullEntry();
    }

    static long access$400(Long2LongOpenHashMap long2LongOpenHashMap, int n) {
        return long2LongOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements LongIterator {
        final Long2LongOpenHashMap this$0;

        public ValueIterator(Long2LongOpenHashMap long2LongOpenHashMap) {
            this.this$0 = long2LongOpenHashMap;
            super(long2LongOpenHashMap, null);
        }

        @Override
        public long nextLong() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractLongSet {
        final Long2LongOpenHashMap this$0;

        private KeySet(Long2LongOpenHashMap long2LongOpenHashMap) {
            this.this$0 = long2LongOpenHashMap;
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

        KeySet(Long2LongOpenHashMap long2LongOpenHashMap, 1 var2_2) {
            this(long2LongOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongIterator {
        final Long2LongOpenHashMap this$0;

        public KeyIterator(Long2LongOpenHashMap long2LongOpenHashMap) {
            this.this$0 = long2LongOpenHashMap;
            super(long2LongOpenHashMap, null);
        }

        @Override
        public long nextLong() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Long2LongMap.Entry>
    implements Long2LongMap.FastEntrySet {
        final Long2LongOpenHashMap this$0;

        private MapEntrySet(Long2LongOpenHashMap long2LongOpenHashMap) {
            this.this$0 = long2LongOpenHashMap;
        }

        @Override
        public ObjectIterator<Long2LongMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Long2LongMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            long l = (Long)entry.getKey();
            long l2 = (Long)entry.getValue();
            if (l == 0L) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == l2;
            }
            long[] lArray = this.this$0.key;
            int n = (int)HashCommon.mix(l) & this.this$0.mask;
            long l3 = lArray[n];
            if (l3 == 0L) {
                return true;
            }
            if (l == l3) {
                return this.this$0.value[n] == l2;
            }
            do {
                if ((l3 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (l != l3);
            return this.this$0.value[n] == l2;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            long l = (Long)entry.getKey();
            long l2 = (Long)entry.getValue();
            if (l == 0L) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == l2) {
                    Long2LongOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            long[] lArray = this.this$0.key;
            int n = (int)HashCommon.mix(l) & this.this$0.mask;
            long l3 = lArray[n];
            if (l3 == 0L) {
                return true;
            }
            if (l3 == l) {
                if (this.this$0.value[n] == l2) {
                    Long2LongOpenHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((l3 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (l3 != l || this.this$0.value[n] != l2);
            Long2LongOpenHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Long2LongMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractLong2LongMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0L) continue;
                consumer.accept(new AbstractLong2LongMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Long2LongMap.Entry> consumer) {
            AbstractLong2LongMap.BasicEntry basicEntry = new AbstractLong2LongMap.BasicEntry();
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

        MapEntrySet(Long2LongOpenHashMap long2LongOpenHashMap, 1 var2_2) {
            this(long2LongOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Long2LongMap.Entry> {
        private final MapEntry entry;
        final Long2LongOpenHashMap this$0;

        private FastEntryIterator(Long2LongOpenHashMap long2LongOpenHashMap) {
            this.this$0 = long2LongOpenHashMap;
            super(long2LongOpenHashMap, null);
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

        FastEntryIterator(Long2LongOpenHashMap long2LongOpenHashMap, 1 var2_2) {
            this(long2LongOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Long2LongMap.Entry> {
        private MapEntry entry;
        final Long2LongOpenHashMap this$0;

        private EntryIterator(Long2LongOpenHashMap long2LongOpenHashMap) {
            this.this$0 = long2LongOpenHashMap;
            super(long2LongOpenHashMap, null);
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

        EntryIterator(Long2LongOpenHashMap long2LongOpenHashMap, 1 var2_2) {
            this(long2LongOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        LongArrayList wrapped;
        final Long2LongOpenHashMap this$0;

        private MapIterator(Long2LongOpenHashMap long2LongOpenHashMap) {
            this.this$0 = long2LongOpenHashMap;
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
                int n = (int)HashCommon.mix(l) & this.this$0.mask;
                while (l != lArray[n]) {
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
                    int n3 = (int)HashCommon.mix(l) & this.this$0.mask;
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

        MapIterator(Long2LongOpenHashMap long2LongOpenHashMap, 1 var2_2) {
            this(long2LongOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Long2LongMap.Entry,
    Map.Entry<Long, Long> {
        int index;
        final Long2LongOpenHashMap this$0;

        MapEntry(Long2LongOpenHashMap long2LongOpenHashMap, int n) {
            this.this$0 = long2LongOpenHashMap;
            this.index = n;
        }

        MapEntry(Long2LongOpenHashMap long2LongOpenHashMap) {
            this.this$0 = long2LongOpenHashMap;
        }

        @Override
        public long getLongKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public long getLongValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public long setValue(long l) {
            long l2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = l;
            return l2;
        }

        @Override
        @Deprecated
        public Long getKey() {
            return this.this$0.key[this.index];
        }

        @Override
        @Deprecated
        public Long getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Long setValue(Long l) {
            return this.setValue((long)l);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.key[this.index] == (Long)entry.getKey() && this.this$0.value[this.index] == (Long)entry.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.this$0.key[this.index]) ^ HashCommon.long2int(this.this$0.value[this.index]);
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Long)object);
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

