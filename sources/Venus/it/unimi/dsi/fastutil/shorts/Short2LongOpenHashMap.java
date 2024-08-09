/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2LongMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
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
import java.util.function.IntToLongFunction;
import java.util.function.LongConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Short2LongOpenHashMap
extends AbstractShort2LongMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient short[] key;
    protected transient long[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Short2LongMap.FastEntrySet entries;
    protected transient ShortSet keys;
    protected transient LongCollection values;

    public Short2LongOpenHashMap(int n, float f) {
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
        this.value = new long[this.n + 1];
    }

    public Short2LongOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Short2LongOpenHashMap() {
        this(16, 0.75f);
    }

    public Short2LongOpenHashMap(Map<? extends Short, ? extends Long> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Short2LongOpenHashMap(Map<? extends Short, ? extends Long> map) {
        this(map, 0.75f);
    }

    public Short2LongOpenHashMap(Short2LongMap short2LongMap, float f) {
        this(short2LongMap.size(), f);
        this.putAll(short2LongMap);
    }

    public Short2LongOpenHashMap(Short2LongMap short2LongMap) {
        this(short2LongMap, 0.75f);
    }

    public Short2LongOpenHashMap(short[] sArray, long[] lArray, float f) {
        this(sArray.length, f);
        if (sArray.length != lArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + sArray.length + " and " + lArray.length + ")");
        }
        for (int i = 0; i < sArray.length; ++i) {
            this.put(sArray[i], lArray[i]);
        }
    }

    public Short2LongOpenHashMap(short[] sArray, long[] lArray) {
        this(sArray, lArray, 0.75f);
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
    public void putAll(Map<? extends Short, ? extends Long> map) {
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

    private void insert(int n, short s, long l) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = s;
        this.value[n] = l;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public long put(short s, long l) {
        int n = this.find(s);
        if (n < 0) {
            this.insert(-n - 1, s, l);
            return this.defRetValue;
        }
        long l2 = this.value[n];
        this.value[n] = l;
        return l2;
    }

    private long addToValue(int n, long l) {
        long l2 = this.value[n];
        this.value[n] = l2 + l;
        return l2;
    }

    public long addTo(short s, long l) {
        int n;
        if (s == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, l);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            short[] sArray = this.key;
            n = HashCommon.mix(s) & this.mask;
            short s2 = sArray[n];
            if (s2 != 0) {
                if (s2 == s) {
                    return this.addToValue(n, l);
                }
                while ((s2 = sArray[n = n + 1 & this.mask]) != 0) {
                    if (s2 != s) continue;
                    return this.addToValue(n, l);
                }
            }
        }
        this.key[n] = s;
        this.value[n] = this.defRetValue + l;
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
    public long remove(short s) {
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
    public long get(short s) {
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
    public boolean containsValue(long l) {
        long[] lArray = this.value;
        short[] sArray = this.key;
        if (this.containsNullKey && lArray[this.n] == l) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (sArray[n] == 0 || lArray[n] != l) continue;
            return false;
        }
        return true;
    }

    @Override
    public long getOrDefault(short s, long l) {
        if (s == 0) {
            return this.containsNullKey ? this.value[this.n] : l;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return l;
        }
        if (s == s2) {
            return this.value[n];
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return l;
        } while (s != s2);
        return this.value[n];
    }

    @Override
    public long putIfAbsent(short s, long l) {
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, s, l);
        return this.defRetValue;
    }

    @Override
    public boolean remove(short s, long l) {
        if (s == 0) {
            if (this.containsNullKey && l == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        short[] sArray = this.key;
        int n = HashCommon.mix(s) & this.mask;
        short s2 = sArray[n];
        if (s2 == 0) {
            return true;
        }
        if (s == s2 && l == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((s2 = sArray[n = n + 1 & this.mask]) != 0) continue;
            return true;
        } while (s != s2 || l != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(short s, long l, long l2) {
        int n = this.find(s);
        if (n < 0 || l != this.value[n]) {
            return true;
        }
        this.value[n] = l2;
        return false;
    }

    @Override
    public long replace(short s, long l) {
        int n = this.find(s);
        if (n < 0) {
            return this.defRetValue;
        }
        long l2 = this.value[n];
        this.value[n] = l;
        return l2;
    }

    @Override
    public long computeIfAbsent(short s, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        long l = intToLongFunction.applyAsLong(s);
        this.insert(-n - 1, s, l);
        return l;
    }

    @Override
    public long computeIfAbsentNullable(short s, IntFunction<? extends Long> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(s);
        if (n >= 0) {
            return this.value[n];
        }
        Long l = intFunction.apply(s);
        if (l == null) {
            return this.defRetValue;
        }
        long l2 = l;
        this.insert(-n - 1, s, l2);
        return l2;
    }

    @Override
    public long computeIfPresent(short s, BiFunction<? super Short, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0) {
            return this.defRetValue;
        }
        Long l = biFunction.apply((Short)s, (Long)this.value[n]);
        if (l == null) {
            if (s == 0) {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = l;
        return this.value[n];
    }

    @Override
    public long compute(short s, BiFunction<? super Short, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        Long l = biFunction.apply((Short)s, n >= 0 ? Long.valueOf(this.value[n]) : null);
        if (l == null) {
            if (n >= 0) {
                if (s == 0) {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        long l2 = l;
        if (n < 0) {
            this.insert(-n - 1, s, l2);
            return l2;
        }
        this.value[n] = l2;
        return this.value[n];
    }

    @Override
    public long merge(short s, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(s);
        if (n < 0) {
            this.insert(-n - 1, s, l);
            return l;
        }
        Long l2 = biFunction.apply((Long)this.value[n], (Long)l);
        if (l2 == null) {
            if (s == 0) {
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

    public Short2LongMap.FastEntrySet short2LongEntrySet() {
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
    public LongCollection values() {
        if (this.values == null) {
            this.values = new AbstractLongCollection(this){
                final Short2LongOpenHashMap this$0;
                {
                    this.this$0 = short2LongOpenHashMap;
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
                        if (this.this$0.key[n] == 0) continue;
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
        short[] sArray = this.key;
        long[] lArray = this.value;
        int n2 = n - 1;
        short[] sArray2 = new short[n + 1];
        long[] lArray2 = new long[n + 1];
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
            lArray2[n5] = lArray[n3];
        }
        lArray2[n] = lArray[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = sArray2;
        this.value = lArray2;
    }

    public Short2LongOpenHashMap clone() {
        Short2LongOpenHashMap short2LongOpenHashMap;
        try {
            short2LongOpenHashMap = (Short2LongOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2LongOpenHashMap.keys = null;
        short2LongOpenHashMap.values = null;
        short2LongOpenHashMap.entries = null;
        short2LongOpenHashMap.containsNullKey = this.containsNullKey;
        short2LongOpenHashMap.key = (short[])this.key.clone();
        short2LongOpenHashMap.value = (long[])this.value.clone();
        return short2LongOpenHashMap;
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
            n += (n4 ^= HashCommon.long2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.long2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        short[] sArray = this.key;
        long[] lArray = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeShort(sArray[n2]);
            objectOutputStream.writeLong(lArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new short[this.n + 1];
        short[] sArray = this.key;
        this.value = new long[this.n + 1];
        long[] lArray = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            short s = objectInputStream.readShort();
            long l = objectInputStream.readLong();
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
            lArray[n2] = l;
        }
    }

    private void checkTable() {
    }

    public ObjectSet short2LongEntrySet() {
        return this.short2LongEntrySet();
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

    static long access$300(Short2LongOpenHashMap short2LongOpenHashMap) {
        return short2LongOpenHashMap.removeNullEntry();
    }

    static long access$400(Short2LongOpenHashMap short2LongOpenHashMap, int n) {
        return short2LongOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements LongIterator {
        final Short2LongOpenHashMap this$0;

        public ValueIterator(Short2LongOpenHashMap short2LongOpenHashMap) {
            this.this$0 = short2LongOpenHashMap;
            super(short2LongOpenHashMap, null);
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
    extends AbstractShortSet {
        final Short2LongOpenHashMap this$0;

        private KeySet(Short2LongOpenHashMap short2LongOpenHashMap) {
            this.this$0 = short2LongOpenHashMap;
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

        KeySet(Short2LongOpenHashMap short2LongOpenHashMap, 1 var2_2) {
            this(short2LongOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements ShortIterator {
        final Short2LongOpenHashMap this$0;

        public KeyIterator(Short2LongOpenHashMap short2LongOpenHashMap) {
            this.this$0 = short2LongOpenHashMap;
            super(short2LongOpenHashMap, null);
        }

        @Override
        public short nextShort() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Short2LongMap.Entry>
    implements Short2LongMap.FastEntrySet {
        final Short2LongOpenHashMap this$0;

        private MapEntrySet(Short2LongOpenHashMap short2LongOpenHashMap) {
            this.this$0 = short2LongOpenHashMap;
        }

        @Override
        public ObjectIterator<Short2LongMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Short2LongMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            short s = (Short)entry.getKey();
            long l = (Long)entry.getValue();
            if (s == 0) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == l;
            }
            short[] sArray = this.this$0.key;
            int n = HashCommon.mix(s) & this.this$0.mask;
            short s2 = sArray[n];
            if (s2 == 0) {
                return true;
            }
            if (s == s2) {
                return this.this$0.value[n] == l;
            }
            do {
                if ((s2 = sArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (s != s2);
            return this.this$0.value[n] == l;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            short s = (Short)entry.getKey();
            long l = (Long)entry.getValue();
            if (s == 0) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == l) {
                    Short2LongOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            short[] sArray = this.this$0.key;
            int n = HashCommon.mix(s) & this.this$0.mask;
            short s2 = sArray[n];
            if (s2 == 0) {
                return true;
            }
            if (s2 == s) {
                if (this.this$0.value[n] == l) {
                    Short2LongOpenHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((s2 = sArray[n = n + 1 & this.this$0.mask]) != 0) continue;
                return true;
            } while (s2 != s || this.this$0.value[n] != l);
            Short2LongOpenHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Short2LongMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractShort2LongMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == 0) continue;
                consumer.accept(new AbstractShort2LongMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Short2LongMap.Entry> consumer) {
            AbstractShort2LongMap.BasicEntry basicEntry = new AbstractShort2LongMap.BasicEntry();
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

        MapEntrySet(Short2LongOpenHashMap short2LongOpenHashMap, 1 var2_2) {
            this(short2LongOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Short2LongMap.Entry> {
        private final MapEntry entry;
        final Short2LongOpenHashMap this$0;

        private FastEntryIterator(Short2LongOpenHashMap short2LongOpenHashMap) {
            this.this$0 = short2LongOpenHashMap;
            super(short2LongOpenHashMap, null);
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

        FastEntryIterator(Short2LongOpenHashMap short2LongOpenHashMap, 1 var2_2) {
            this(short2LongOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Short2LongMap.Entry> {
        private MapEntry entry;
        final Short2LongOpenHashMap this$0;

        private EntryIterator(Short2LongOpenHashMap short2LongOpenHashMap) {
            this.this$0 = short2LongOpenHashMap;
            super(short2LongOpenHashMap, null);
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

        EntryIterator(Short2LongOpenHashMap short2LongOpenHashMap, 1 var2_2) {
            this(short2LongOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ShortArrayList wrapped;
        final Short2LongOpenHashMap this$0;

        private MapIterator(Short2LongOpenHashMap short2LongOpenHashMap) {
            this.this$0 = short2LongOpenHashMap;
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

        MapIterator(Short2LongOpenHashMap short2LongOpenHashMap, 1 var2_2) {
            this(short2LongOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Short2LongMap.Entry,
    Map.Entry<Short, Long> {
        int index;
        final Short2LongOpenHashMap this$0;

        MapEntry(Short2LongOpenHashMap short2LongOpenHashMap, int n) {
            this.this$0 = short2LongOpenHashMap;
            this.index = n;
        }

        MapEntry(Short2LongOpenHashMap short2LongOpenHashMap) {
            this.this$0 = short2LongOpenHashMap;
        }

        @Override
        public short getShortKey() {
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
        public Short getKey() {
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
            return this.this$0.key[this.index] == (Short)entry.getKey() && this.this$0.value[this.index] == (Long)entry.getValue();
        }

        @Override
        public int hashCode() {
            return this.this$0.key[this.index] ^ HashCommon.long2int(this.this$0.value[this.index]);
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

