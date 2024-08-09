/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLong2LongMap;
import it.unimi.dsi.fastutil.longs.AbstractLong2LongSortedMap;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Long2LongLinkedOpenHashMap
extends AbstractLong2LongSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient long[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first = -1;
    protected transient int last = -1;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Long2LongSortedMap.FastSortedEntrySet entries;
    protected transient LongSortedSet keys;
    protected transient LongCollection values;

    public Long2LongLinkedOpenHashMap(int n, float f) {
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
        this.link = new long[this.n + 1];
    }

    public Long2LongLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Long2LongLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Long2LongLinkedOpenHashMap(Map<? extends Long, ? extends Long> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Long2LongLinkedOpenHashMap(Map<? extends Long, ? extends Long> map) {
        this(map, 0.75f);
    }

    public Long2LongLinkedOpenHashMap(Long2LongMap long2LongMap, float f) {
        this(long2LongMap.size(), f);
        this.putAll(long2LongMap);
    }

    public Long2LongLinkedOpenHashMap(Long2LongMap long2LongMap) {
        this(long2LongMap, 0.75f);
    }

    public Long2LongLinkedOpenHashMap(long[] lArray, long[] lArray2, float f) {
        this(lArray.length, f);
        if (lArray.length != lArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + lArray.length + " and " + lArray2.length + ")");
        }
        for (int i = 0; i < lArray.length; ++i) {
            this.put(lArray[i], lArray2[i]);
        }
    }

    public Long2LongLinkedOpenHashMap(long[] lArray, long[] lArray2) {
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
        this.fixPointers(n);
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
        this.fixPointers(this.n);
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
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n2 = this.last;
            this.link[n2] = this.link[n2] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n;
        }
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
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n2 = this.last;
            this.link[n2] = this.link[n2] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n;
        }
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
            this.fixPointers(n, n2);
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

    private long setValue(int n, long l) {
        long l2 = this.value[n];
        this.value[n] = l;
        return l2;
    }

    public long removeFirstLong() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.first;
        this.first = (int)this.link[n];
        if (0 <= this.first) {
            int n2 = this.first;
            this.link[n2] = this.link[n2] | 0xFFFFFFFF00000000L;
        }
        --this.size;
        long l = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return l;
    }

    public long removeLastLong() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.last;
        this.last = (int)(this.link[n] >>> 32);
        if (0 <= this.last) {
            int n2 = this.last;
            this.link[n2] = this.link[n2] | 0xFFFFFFFFL;
        }
        --this.size;
        long l = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return l;
    }

    private void moveIndexToFirst(int n) {
        if (this.size == 1 || this.first == n) {
            return;
        }
        if (this.last == n) {
            int n2 = this.last = (int)(this.link[n] >>> 32);
            this.link[n2] = this.link[n2] | 0xFFFFFFFFL;
        } else {
            long l = this.link[n];
            int n3 = (int)(l >>> 32);
            int n4 = (int)l;
            int n5 = n3;
            this.link[n5] = this.link[n5] ^ (this.link[n3] ^ l & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            int n6 = n4;
            this.link[n6] = this.link[n6] ^ (this.link[n4] ^ l & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
        }
        int n7 = this.first;
        this.link[n7] = this.link[n7] ^ (this.link[this.first] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
        this.link[n] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
        this.first = n;
    }

    private void moveIndexToLast(int n) {
        if (this.size == 1 || this.last == n) {
            return;
        }
        if (this.first == n) {
            int n2 = this.first = (int)this.link[n];
            this.link[n2] = this.link[n2] | 0xFFFFFFFF00000000L;
        } else {
            long l = this.link[n];
            int n3 = (int)(l >>> 32);
            int n4 = (int)l;
            int n5 = n3;
            this.link[n5] = this.link[n5] ^ (this.link[n3] ^ l & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            int n6 = n4;
            this.link[n6] = this.link[n6] ^ (this.link[n4] ^ l & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
        }
        int n7 = this.last;
        this.link[n7] = this.link[n7] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
        this.last = n;
    }

    public long getAndMoveToFirst(long l) {
        if (l == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
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
            this.moveIndexToFirst(n);
            return this.value[n];
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (l != l2);
        this.moveIndexToFirst(n);
        return this.value[n];
    }

    public long getAndMoveToLast(long l) {
        if (l == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
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
            this.moveIndexToLast(n);
            return this.value[n];
        }
        do {
            if ((l2 = lArray[n = n + 1 & this.mask]) != 0L) continue;
            return this.defRetValue;
        } while (l != l2);
        this.moveIndexToLast(n);
        return this.value[n];
    }

    public long putAndMoveToFirst(long l, long l2) {
        int n;
        if (l == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, l2);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            long[] lArray = this.key;
            n = (int)HashCommon.mix(l) & this.mask;
            long l3 = lArray[n];
            if (l3 != 0L) {
                if (l3 == l) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, l2);
                }
                while ((l3 = lArray[n = n + 1 & this.mask]) != 0L) {
                    if (l3 != l) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, l2);
                }
            }
        }
        this.key[n] = l;
        this.value[n] = l2;
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n2 = this.first;
            this.link[n2] = this.link[n2] ^ (this.link[this.first] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[n] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
            this.first = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }

    public long putAndMoveToLast(long l, long l2) {
        int n;
        if (l == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, l2);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            long[] lArray = this.key;
            n = (int)HashCommon.mix(l) & this.mask;
            long l3 = lArray[n];
            if (l3 != 0L) {
                if (l3 == l) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, l2);
                }
                while ((l3 = lArray[n = n + 1 & this.mask]) != 0L) {
                    if (l3 != l) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, l2);
                }
            }
        }
        this.key[n] = l;
        this.value[n] = l2;
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n2 = this.last;
            this.link[n2] = this.link[n2] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
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
        this.last = -1;
        this.first = -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    protected void fixPointers(int n) {
        if (this.size == 0) {
            this.last = -1;
            this.first = -1;
            return;
        }
        if (this.first == n) {
            this.first = (int)this.link[n];
            if (0 <= this.first) {
                int n2 = this.first;
                this.link[n2] = this.link[n2] | 0xFFFFFFFF00000000L;
            }
            return;
        }
        if (this.last == n) {
            this.last = (int)(this.link[n] >>> 32);
            if (0 <= this.last) {
                int n3 = this.last;
                this.link[n3] = this.link[n3] | 0xFFFFFFFFL;
            }
            return;
        }
        long l = this.link[n];
        int n4 = (int)(l >>> 32);
        int n5 = (int)l;
        int n6 = n4;
        this.link[n6] = this.link[n6] ^ (this.link[n4] ^ l & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        int n7 = n5;
        this.link[n7] = this.link[n7] ^ (this.link[n5] ^ l & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
    }

    protected void fixPointers(int n, int n2) {
        if (this.size == 1) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
            return;
        }
        if (this.first == n) {
            this.first = n2;
            int n3 = (int)this.link[n];
            this.link[n3] = this.link[n3] ^ (this.link[(int)this.link[n]] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[n2] = this.link[n];
            return;
        }
        if (this.last == n) {
            this.last = n2;
            int n4 = (int)(this.link[n] >>> 32);
            this.link[n4] = this.link[n4] ^ (this.link[(int)(this.link[n] >>> 32)] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n2] = this.link[n];
            return;
        }
        long l = this.link[n];
        int n5 = (int)(l >>> 32);
        int n6 = (int)l;
        int n7 = n5;
        this.link[n7] = this.link[n7] ^ (this.link[n5] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
        int n8 = n6;
        this.link[n8] = this.link[n8] ^ (this.link[n6] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
        this.link[n2] = l;
    }

    @Override
    public long firstLongKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public long lastLongKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public Long2LongSortedMap tailMap(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long2LongSortedMap headMap(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long2LongSortedMap subMap(long l, long l2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LongComparator comparator() {
        return null;
    }

    @Override
    public Long2LongSortedMap.FastSortedEntrySet long2LongEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public LongSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public LongCollection values() {
        if (this.values == null) {
            this.values = new AbstractLongCollection(this){
                final Long2LongLinkedOpenHashMap this$0;
                {
                    this.this$0 = long2LongLinkedOpenHashMap;
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
        int n3 = this.first;
        int n4 = -1;
        int n5 = -1;
        long[] lArray5 = this.link;
        long[] lArray6 = new long[n + 1];
        this.first = -1;
        int n6 = this.size;
        while (n6-- != 0) {
            int n7;
            if (lArray[n3] == 0L) {
                n7 = n;
            } else {
                n7 = (int)HashCommon.mix(lArray[n3]) & n2;
                while (lArray3[n7] != 0L) {
                    n7 = n7 + 1 & n2;
                }
            }
            lArray3[n7] = lArray[n3];
            lArray4[n7] = lArray2[n3];
            if (n4 != -1) {
                int n8 = n5;
                lArray6[n8] = lArray6[n8] ^ (lArray6[n5] ^ (long)n7 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n9 = n7;
                lArray6[n9] = lArray6[n9] ^ (lArray6[n7] ^ ((long)n5 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n5 = n7;
            } else {
                n5 = this.first = n7;
                lArray6[n7] = -1L;
            }
            int n10 = n3;
            n3 = (int)lArray5[n3];
            n4 = n10;
        }
        this.link = lArray6;
        this.last = n5;
        if (n5 != -1) {
            int n11 = n5;
            lArray6[n11] = lArray6[n11] | 0xFFFFFFFFL;
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = lArray3;
        this.value = lArray4;
    }

    public Long2LongLinkedOpenHashMap clone() {
        Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap;
        try {
            long2LongLinkedOpenHashMap = (Long2LongLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2LongLinkedOpenHashMap.keys = null;
        long2LongLinkedOpenHashMap.values = null;
        long2LongLinkedOpenHashMap.entries = null;
        long2LongLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        long2LongLinkedOpenHashMap.key = (long[])this.key.clone();
        long2LongLinkedOpenHashMap.value = (long[])this.value.clone();
        long2LongLinkedOpenHashMap.link = (long[])this.link.clone();
        return long2LongLinkedOpenHashMap;
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
        MapIterator mapIterator = new MapIterator(this);
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
        this.link = new long[this.n + 1];
        long[] lArray3 = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            long l = objectInputStream.readLong();
            long l2 = objectInputStream.readLong();
            if (l == 0L) {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = (int)HashCommon.mix(l) & this.mask;
                while (lArray[n3] != 0L) {
                    n3 = n3 + 1 & this.mask;
                }
            }
            lArray[n3] = l;
            lArray2[n3] = l2;
            if (this.first != -1) {
                int n4 = n;
                lArray3[n4] = lArray3[n4] ^ (lArray3[n] ^ (long)n3 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n5 = n3;
                lArray3[n5] = lArray3[n5] ^ (lArray3[n3] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n = n3;
                continue;
            }
            n = this.first = n3;
            int n6 = n3;
            lArray3[n6] = lArray3[n6] | 0xFFFFFFFF00000000L;
        }
        this.last = n;
        if (n != -1) {
            int n7 = n;
            lArray3[n7] = lArray3[n7] | 0xFFFFFFFFL;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSortedSet long2LongEntrySet() {
        return this.long2LongEntrySet();
    }

    @Override
    public LongSet keySet() {
        return this.keySet();
    }

    @Override
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

    @Override
    public Comparator comparator() {
        return this.comparator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static long access$100(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
        return long2LongLinkedOpenHashMap.removeNullEntry();
    }

    static long access$200(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, int n) {
        return long2LongLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements LongListIterator {
        final Long2LongLinkedOpenHashMap this$0;

        @Override
        public long previousLong() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
            this.this$0 = long2LongLinkedOpenHashMap;
            super(long2LongLinkedOpenHashMap);
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
    extends AbstractLongSortedSet {
        final Long2LongLinkedOpenHashMap this$0;

        private KeySet(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
            this.this$0 = long2LongLinkedOpenHashMap;
        }

        @Override
        public LongListIterator iterator(long l) {
            return new KeyIterator(this.this$0, l);
        }

        @Override
        public LongListIterator iterator() {
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
        public long firstLong() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }

        @Override
        public long lastLong() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }

        @Override
        public LongComparator comparator() {
            return null;
        }

        @Override
        public LongSortedSet tailSet(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongSortedSet headSet(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongSortedSet subSet(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public LongBidirectionalIterator iterator(long l) {
            return this.iterator(l);
        }

        @Override
        public LongIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        KeySet(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, 1 var2_2) {
            this(long2LongLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongListIterator {
        final Long2LongLinkedOpenHashMap this$0;

        public KeyIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, long l) {
            this.this$0 = long2LongLinkedOpenHashMap;
            super(long2LongLinkedOpenHashMap, l, null);
        }

        @Override
        public long previousLong() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
            this.this$0 = long2LongLinkedOpenHashMap;
            super(long2LongLinkedOpenHashMap);
        }

        @Override
        public long nextLong() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class MapEntrySet
    extends AbstractObjectSortedSet<Long2LongMap.Entry>
    implements Long2LongSortedMap.FastSortedEntrySet {
        final Long2LongLinkedOpenHashMap this$0;

        private MapEntrySet(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
            this.this$0 = long2LongLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Long2LongMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Long2LongMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Long2LongMap.Entry> subSet(Long2LongMap.Entry entry, Long2LongMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Long2LongMap.Entry> headSet(Long2LongMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Long2LongMap.Entry> tailSet(Long2LongMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long2LongMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Long2LongMap.Entry last() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.last);
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
                    Long2LongLinkedOpenHashMap.access$100(this.this$0);
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
                    Long2LongLinkedOpenHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((l3 = lArray[n = n + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (l3 != l || this.this$0.value[n] != l2);
            Long2LongLinkedOpenHashMap.access$200(this.this$0, n);
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
        public ObjectListIterator<Long2LongMap.Entry> iterator(Long2LongMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getLongKey());
        }

        @Override
        public ObjectListIterator<Long2LongMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Long2LongMap.Entry> fastIterator(Long2LongMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getLongKey());
        }

        @Override
        public void forEach(Consumer<? super Long2LongMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractLong2LongMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Long2LongMap.Entry> consumer) {
            AbstractLong2LongMap.BasicEntry basicEntry = new AbstractLong2LongMap.BasicEntry();
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                basicEntry.key = this.this$0.key[n3];
                basicEntry.value = this.this$0.value[n3];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public ObjectSortedSet tailSet(Object object) {
            return this.tailSet((Long2LongMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Long2LongMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Long2LongMap.Entry)object, (Long2LongMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Long2LongMap.Entry)object);
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public Object last() {
            return this.last();
        }

        @Override
        public Object first() {
            return this.first();
        }

        @Override
        public SortedSet tailSet(Object object) {
            return this.tailSet((Long2LongMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Long2LongMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Long2LongMap.Entry)object, (Long2LongMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Long2LongMap.Entry entry) {
            return this.fastIterator(entry);
        }

        @Override
        public ObjectBidirectionalIterator fastIterator() {
            return this.fastIterator();
        }

        @Override
        public ObjectIterator fastIterator() {
            return this.fastIterator();
        }

        MapEntrySet(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, 1 var2_2) {
            this(long2LongLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Long2LongMap.Entry> {
        final MapEntry entry;
        final Long2LongLinkedOpenHashMap this$0;

        public FastEntryIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
            this.this$0 = long2LongLinkedOpenHashMap;
            super(long2LongLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, long l) {
            this.this$0 = long2LongLinkedOpenHashMap;
            super(long2LongLinkedOpenHashMap, l, null);
            this.entry = new MapEntry(this.this$0);
        }

        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }

        @Override
        public MapEntry previous() {
            this.entry.index = this.previousEntry();
            return this.entry;
        }

        @Override
        public void add(Object object) {
            super.add((Long2LongMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Long2LongMap.Entry)object);
        }

        @Override
        public Object next() {
            return this.next();
        }

        @Override
        public Object previous() {
            return this.previous();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectListIterator<Long2LongMap.Entry> {
        private MapEntry entry;
        final Long2LongLinkedOpenHashMap this$0;

        public EntryIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
            this.this$0 = long2LongLinkedOpenHashMap;
            super(long2LongLinkedOpenHashMap);
        }

        public EntryIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, long l) {
            this.this$0 = long2LongLinkedOpenHashMap;
            super(long2LongLinkedOpenHashMap, l, null);
        }

        @Override
        public MapEntry next() {
            this.entry = new MapEntry(this.this$0, this.nextEntry());
            return this.entry;
        }

        @Override
        public MapEntry previous() {
            this.entry = new MapEntry(this.this$0, this.previousEntry());
            return this.entry;
        }

        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }

        @Override
        public void add(Object object) {
            super.add((Long2LongMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Long2LongMap.Entry)object);
        }

        @Override
        public Object next() {
            return this.next();
        }

        @Override
        public Object previous() {
            return this.previous();
        }
    }

    private class MapIterator {
        int prev;
        int next;
        int curr;
        int index;
        final Long2LongLinkedOpenHashMap this$0;

        protected MapIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
            this.this$0 = long2LongLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = long2LongLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, long l) {
            this.this$0 = long2LongLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (l == 0L) {
                if (long2LongLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)long2LongLinkedOpenHashMap.link[long2LongLinkedOpenHashMap.n];
                    this.prev = long2LongLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + l + " does not belong to this map.");
            }
            if (long2LongLinkedOpenHashMap.key[long2LongLinkedOpenHashMap.last] == l) {
                this.prev = long2LongLinkedOpenHashMap.last;
                this.index = long2LongLinkedOpenHashMap.size;
                return;
            }
            int n = (int)HashCommon.mix(l) & long2LongLinkedOpenHashMap.mask;
            while (long2LongLinkedOpenHashMap.key[n] != 0L) {
                if (long2LongLinkedOpenHashMap.key[n] == l) {
                    this.next = (int)long2LongLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & long2LongLinkedOpenHashMap.mask;
            }
            throw new NoSuchElementException("The key " + l + " does not belong to this map.");
        }

        public boolean hasNext() {
            return this.next != -1;
        }

        public boolean hasPrevious() {
            return this.prev != -1;
        }

        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
                return;
            }
            if (this.next == -1) {
                this.index = this.this$0.size;
                return;
            }
            int n = this.this$0.first;
            this.index = 1;
            while (n != this.prev) {
                n = (int)this.this$0.link[n];
                ++this.index;
            }
        }

        public int nextIndex() {
            this.ensureIndexKnown();
            return this.index;
        }

        public int previousIndex() {
            this.ensureIndexKnown();
            return this.index - 1;
        }

        public int nextEntry() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int)this.this$0.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                ++this.index;
            }
            return this.curr;
        }

        public int previousEntry() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                --this.index;
            }
            return this.curr;
        }

        public void remove() {
            this.ensureIndexKnown();
            if (this.curr == -1) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                --this.index;
                this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            } else {
                this.next = (int)this.this$0.link[this.curr];
            }
            --this.this$0.size;
            if (this.prev == -1) {
                this.this$0.first = this.next;
            } else {
                int n = this.prev;
                this.this$0.link[n] = this.this$0.link[n] ^ (this.this$0.link[this.prev] ^ (long)this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            }
            if (this.next == -1) {
                this.this$0.last = this.prev;
            } else {
                int n = this.next;
                this.this$0.link[n] = this.this$0.link[n] ^ (this.this$0.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            }
            int n = this.curr;
            this.curr = -1;
            if (n != this.this$0.n) {
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
                    lArray[n2] = l;
                    this.this$0.value[n2] = this.this$0.value[n];
                    if (this.next == n) {
                        this.next = n2;
                    }
                    if (this.prev == n) {
                        this.prev = n2;
                    }
                    this.this$0.fixPointers(n, n2);
                }
            }
            this.this$0.containsNullKey = false;
        }

        public int skip(int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - n2 - 1;
        }

        public int back(int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasPrevious()) {
                this.previousEntry();
            }
            return n - n2 - 1;
        }

        public void set(Long2LongMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Long2LongMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, long l, 1 var4_3) {
            this(long2LongLinkedOpenHashMap, l);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Long2LongMap.Entry,
    Map.Entry<Long, Long> {
        int index;
        final Long2LongLinkedOpenHashMap this$0;

        MapEntry(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap, int n) {
            this.this$0 = long2LongLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Long2LongLinkedOpenHashMap long2LongLinkedOpenHashMap) {
            this.this$0 = long2LongLinkedOpenHashMap;
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

