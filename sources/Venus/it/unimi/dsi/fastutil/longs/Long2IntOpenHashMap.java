/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
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
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Long2IntOpenHashMap
extends AbstractLong2IntMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Long2IntMap.FastEntrySet entries;
    protected transient LongSet keys;
    protected transient IntCollection values;

    public Long2IntOpenHashMap(int n, float f) {
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
        this.value = new int[this.n + 1];
    }

    public Long2IntOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Long2IntOpenHashMap() {
        this(16, 0.75f);
    }

    public Long2IntOpenHashMap(Map<? extends Long, ? extends Integer> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Long2IntOpenHashMap(Map<? extends Long, ? extends Integer> map) {
        this(map, 0.75f);
    }

    public Long2IntOpenHashMap(Long2IntMap long2IntMap, float f) {
        this(long2IntMap.size(), f);
        this.putAll(long2IntMap);
    }

    public Long2IntOpenHashMap(Long2IntMap long2IntMap) {
        this(long2IntMap, 0.75f);
    }

    public Long2IntOpenHashMap(long[] lArray, int[] nArray, float f) {
        this(lArray.length, f);
        if (lArray.length != nArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + lArray.length + " and " + nArray.length + ")");
        }
        for (int i = 0; i < lArray.length; ++i) {
            this.put(lArray[i], nArray[i]);
        }
    }

    public Long2IntOpenHashMap(long[] lArray, int[] nArray) {
        this(lArray, nArray, 0.75f);
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
    public void putAll(Map<? extends Long, ? extends Integer> map) {
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

    private void insert(int n, long l, int n2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = l;
        this.value[n] = n2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public int put(long l, int n) {
        int n2 = this.find(l);
        if (n2 < 0) {
            this.insert(-n2 - 1, l, n);
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

    public int addTo(long l, int n) {
        int n2;
        if (l == 0L) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, n);
            }
            n2 = this.n;
            this.containsNullKey = true;
        } else {
            long[] lArray = this.key;
            n2 = (int)HashCommon.mix(l) & this.mask;
            long l2 = lArray[n2];
            if (l2 != 0L) {
                if (l2 == l) {
                    return this.addToValue(n2, n);
                }
                while ((l2 = lArray[n2 = n2 + 1 & this.mask]) != 0L) {
                    if (l2 != l) continue;
                    return this.addToValue(n2, n);
                }
            }
        }
        this.key[n2] = l;
        this.value[n2] = this.defRetValue + n;
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
    public int remove(long l) {
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
    public int get(long l) {
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
    public boolean containsValue(int n) {
        int[] nArray = this.value;
        long[] lArray = this.key;
        if (this.containsNullKey && nArray[this.n] == n) {
            return false;
        }
        int n2 = this.n;
        while (n2-- != 0) {
            if (lArray[n2] == 0L || nArray[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getOrDefault(long l, int n) {
        if (l == 0L) {
            return this.containsNullKey ? this.value[this.n] : n;
        }
        long[] lArray = this.key;
        int n2 = (int)HashCommon.mix(l) & this.mask;
        long l2 = lArray[n2];
        if (l2 == 0L) {
            return n;
        }
        if (l == l2) {
            return this.value[n2];
        }
        do {
            if ((l2 = lArray[n2 = n2 + 1 & this.mask]) != 0L) continue;
            return n;
        } while (l != l2);
        return this.value[n2];
    }

    @Override
    public int putIfAbsent(long l, int n) {
        int n2 = this.find(l);
        if (n2 >= 0) {
            return this.value[n2];
        }
        this.insert(-n2 - 1, l, n);
        return this.defRetValue;
    }

    @Override
    public boolean remove(long l, int n) {
        if (l == 0L) {
            if (this.containsNullKey && n == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        long[] lArray = this.key;
        int n2 = (int)HashCommon.mix(l) & this.mask;
        long l2 = lArray[n2];
        if (l2 == 0L) {
            return true;
        }
        if (l == l2 && n == this.value[n2]) {
            this.removeEntry(n2);
            return false;
        }
        do {
            if ((l2 = lArray[n2 = n2 + 1 & this.mask]) != 0L) continue;
            return true;
        } while (l != l2 || n != this.value[n2]);
        this.removeEntry(n2);
        return false;
    }

    @Override
    public boolean replace(long l, int n, int n2) {
        int n3 = this.find(l);
        if (n3 < 0 || n != this.value[n3]) {
            return true;
        }
        this.value[n3] = n2;
        return false;
    }

    @Override
    public int replace(long l, int n) {
        int n2 = this.find(l);
        if (n2 < 0) {
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        this.value[n2] = n;
        return n3;
    }

    @Override
    public int computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        int n2 = longToIntFunction.applyAsInt(l);
        this.insert(-n - 1, l, n2);
        return n2;
    }

    @Override
    public int computeIfAbsentNullable(long l, LongFunction<? extends Integer> longFunction) {
        Objects.requireNonNull(longFunction);
        int n = this.find(l);
        if (n >= 0) {
            return this.value[n];
        }
        Integer n2 = longFunction.apply(l);
        if (n2 == null) {
            return this.defRetValue;
        }
        int n3 = n2;
        this.insert(-n - 1, l, n3);
        return n3;
    }

    @Override
    public int computeIfPresent(long l, BiFunction<? super Long, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        if (n < 0) {
            return this.defRetValue;
        }
        Integer n2 = biFunction.apply((Long)l, (Integer)this.value[n]);
        if (n2 == null) {
            if (l == 0L) {
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
    public int compute(long l, BiFunction<? super Long, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(l);
        Integer n2 = biFunction.apply((Long)l, n >= 0 ? Integer.valueOf(this.value[n]) : null);
        if (n2 == null) {
            if (n >= 0) {
                if (l == 0L) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        int n3 = n2;
        if (n < 0) {
            this.insert(-n - 1, l, n3);
            return n3;
        }
        this.value[n] = n3;
        return this.value[n];
    }

    @Override
    public int merge(long l, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n2 = this.find(l);
        if (n2 < 0) {
            this.insert(-n2 - 1, l, n);
            return n;
        }
        Integer n3 = biFunction.apply((Integer)this.value[n2], (Integer)n);
        if (n3 == null) {
            if (l == 0L) {
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

    public Long2IntMap.FastEntrySet long2IntEntrySet() {
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
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(this){
                final Long2IntOpenHashMap this$0;
                {
                    this.this$0 = long2IntOpenHashMap;
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
                        if (this.this$0.key[n] == 0L) continue;
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
        long[] lArray = this.key;
        int[] nArray = this.value;
        int n2 = n - 1;
        long[] lArray2 = new long[n + 1];
        int[] nArray2 = new int[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (lArray[--n3] == 0L) {
            }
            int n5 = (int)HashCommon.mix(lArray[n3]) & n2;
            if (lArray2[n5] != 0L) {
                while (lArray2[n5 = n5 + 1 & n2] != 0L) {
                }
            }
            lArray2[n5] = lArray[n3];
            nArray2[n5] = nArray[n3];
        }
        nArray2[n] = nArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = lArray2;
        this.value = nArray2;
    }

    public Long2IntOpenHashMap clone() {
        Long2IntOpenHashMap long2IntOpenHashMap;
        try {
            long2IntOpenHashMap = (Long2IntOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2IntOpenHashMap.keys = null;
        long2IntOpenHashMap.values = null;
        long2IntOpenHashMap.entries = null;
        long2IntOpenHashMap.containsNullKey = this.containsNullKey;
        long2IntOpenHashMap.key = (long[])this.key.clone();
        long2IntOpenHashMap.value = (int[])this.value.clone();
        return long2IntOpenHashMap;
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
            n += (n4 ^= this.value[n3]);
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n];
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        long[] lArray = this.key;
        int[] nArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeLong(lArray[n2]);
            objectOutputStream.writeInt(nArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new long[this.n + 1];
        long[] lArray = this.key;
        this.value = new int[this.n + 1];
        int[] nArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            long l = objectInputStream.readLong();
            int n3 = objectInputStream.readInt();
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
            nArray[n2] = n3;
        }
    }

    private void checkTable() {
    }

    public ObjectSet long2IntEntrySet() {
        return this.long2IntEntrySet();
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

    static int access$300(Long2IntOpenHashMap long2IntOpenHashMap) {
        return long2IntOpenHashMap.removeNullEntry();
    }

    static int access$400(Long2IntOpenHashMap long2IntOpenHashMap, int n) {
        return long2IntOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements IntIterator {
        final Long2IntOpenHashMap this$0;

        public ValueIterator(Long2IntOpenHashMap long2IntOpenHashMap) {
            this.this$0 = long2IntOpenHashMap;
            super(long2IntOpenHashMap, null);
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
    extends AbstractLongSet {
        final Long2IntOpenHashMap this$0;

        private KeySet(Long2IntOpenHashMap long2IntOpenHashMap) {
            this.this$0 = long2IntOpenHashMap;
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

        KeySet(Long2IntOpenHashMap long2IntOpenHashMap, 1 var2_2) {
            this(long2IntOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongIterator {
        final Long2IntOpenHashMap this$0;

        public KeyIterator(Long2IntOpenHashMap long2IntOpenHashMap) {
            this.this$0 = long2IntOpenHashMap;
            super(long2IntOpenHashMap, null);
        }

        @Override
        public long nextLong() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Long2IntMap.Entry>
    implements Long2IntMap.FastEntrySet {
        final Long2IntOpenHashMap this$0;

        private MapEntrySet(Long2IntOpenHashMap long2IntOpenHashMap) {
            this.this$0 = long2IntOpenHashMap;
        }

        @Override
        public ObjectIterator<Long2IntMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Long2IntMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            long l = (Long)entry.getKey();
            int n = (Integer)entry.getValue();
            if (l == 0L) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n;
            }
            long[] lArray = this.this$0.key;
            int n2 = (int)HashCommon.mix(l) & this.this$0.mask;
            long l2 = lArray[n2];
            if (l2 == 0L) {
                return true;
            }
            if (l == l2) {
                return this.this$0.value[n2] == n;
            }
            do {
                if ((l2 = lArray[n2 = n2 + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (l != l2);
            return this.this$0.value[n2] == n;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            long l = (Long)entry.getKey();
            int n = (Integer)entry.getValue();
            if (l == 0L) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == n) {
                    Long2IntOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            long[] lArray = this.this$0.key;
            int n2 = (int)HashCommon.mix(l) & this.this$0.mask;
            long l2 = lArray[n2];
            if (l2 == 0L) {
                return true;
            }
            if (l2 == l) {
                if (this.this$0.value[n2] == n) {
                    Long2IntOpenHashMap.access$400(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((l2 = lArray[n2 = n2 + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (l2 != l || this.this$0.value[n2] != n);
            Long2IntOpenHashMap.access$400(this.this$0, n2);
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
        public void forEach(Consumer<? super Long2IntMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractLong2IntMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0L) continue;
                consumer.accept(new AbstractLong2IntMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Long2IntMap.Entry> consumer) {
            AbstractLong2IntMap.BasicEntry basicEntry = new AbstractLong2IntMap.BasicEntry();
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

        MapEntrySet(Long2IntOpenHashMap long2IntOpenHashMap, 1 var2_2) {
            this(long2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Long2IntMap.Entry> {
        private final MapEntry entry;
        final Long2IntOpenHashMap this$0;

        private FastEntryIterator(Long2IntOpenHashMap long2IntOpenHashMap) {
            this.this$0 = long2IntOpenHashMap;
            super(long2IntOpenHashMap, null);
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

        FastEntryIterator(Long2IntOpenHashMap long2IntOpenHashMap, 1 var2_2) {
            this(long2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Long2IntMap.Entry> {
        private MapEntry entry;
        final Long2IntOpenHashMap this$0;

        private EntryIterator(Long2IntOpenHashMap long2IntOpenHashMap) {
            this.this$0 = long2IntOpenHashMap;
            super(long2IntOpenHashMap, null);
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

        EntryIterator(Long2IntOpenHashMap long2IntOpenHashMap, 1 var2_2) {
            this(long2IntOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        LongArrayList wrapped;
        final Long2IntOpenHashMap this$0;

        private MapIterator(Long2IntOpenHashMap long2IntOpenHashMap) {
            this.this$0 = long2IntOpenHashMap;
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

        MapIterator(Long2IntOpenHashMap long2IntOpenHashMap, 1 var2_2) {
            this(long2IntOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Long2IntMap.Entry,
    Map.Entry<Long, Integer> {
        int index;
        final Long2IntOpenHashMap this$0;

        MapEntry(Long2IntOpenHashMap long2IntOpenHashMap, int n) {
            this.this$0 = long2IntOpenHashMap;
            this.index = n;
        }

        MapEntry(Long2IntOpenHashMap long2IntOpenHashMap) {
            this.this$0 = long2IntOpenHashMap;
        }

        @Override
        public long getLongKey() {
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
        public Long getKey() {
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
            return this.this$0.key[this.index] == (Long)entry.getKey() && this.this$0.value[this.index] == (Integer)entry.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.this$0.key[this.index]) ^ this.this$0.value[this.index];
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

