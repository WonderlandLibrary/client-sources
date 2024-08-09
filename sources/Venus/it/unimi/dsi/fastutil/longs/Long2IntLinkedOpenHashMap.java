/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntMap;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntSortedMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
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
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Long2IntLinkedOpenHashMap
extends AbstractLong2IntSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient long[] key;
    protected transient int[] value;
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
    protected transient Long2IntSortedMap.FastSortedEntrySet entries;
    protected transient LongSortedSet keys;
    protected transient IntCollection values;

    public Long2IntLinkedOpenHashMap(int n, float f) {
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
        this.link = new long[this.n + 1];
    }

    public Long2IntLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Long2IntLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Long2IntLinkedOpenHashMap(Map<? extends Long, ? extends Integer> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Long2IntLinkedOpenHashMap(Map<? extends Long, ? extends Integer> map) {
        this(map, 0.75f);
    }

    public Long2IntLinkedOpenHashMap(Long2IntMap long2IntMap, float f) {
        this(long2IntMap.size(), f);
        this.putAll(long2IntMap);
    }

    public Long2IntLinkedOpenHashMap(Long2IntMap long2IntMap) {
        this(long2IntMap, 0.75f);
    }

    public Long2IntLinkedOpenHashMap(long[] lArray, int[] nArray, float f) {
        this(lArray.length, f);
        if (lArray.length != nArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + lArray.length + " and " + nArray.length + ")");
        }
        for (int i = 0; i < lArray.length; ++i) {
            this.put(lArray[i], nArray[i]);
        }
    }

    public Long2IntLinkedOpenHashMap(long[] lArray, int[] nArray) {
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
        this.fixPointers(n);
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
        this.fixPointers(this.n);
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
        if (this.size == 0) {
            this.first = this.last = n;
            this.link[n] = -1L;
        } else {
            int n3 = this.last;
            this.link[n3] = this.link[n3] ^ (this.link[this.last] ^ (long)n & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n;
        }
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
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n3 = this.last;
            this.link[n3] = this.link[n3] ^ (this.link[this.last] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n2] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n2;
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

    private int setValue(int n, int n2) {
        int n3 = this.value[n];
        this.value[n] = n2;
        return n3;
    }

    public int removeFirstInt() {
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
        int n3 = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n3;
    }

    public int removeLastInt() {
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
        int n3 = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n3;
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

    public int getAndMoveToFirst(long l) {
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

    public int getAndMoveToLast(long l) {
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

    public int putAndMoveToFirst(long l, int n) {
        int n2;
        if (l == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, n);
            }
            this.containsNullKey = true;
            n2 = this.n;
        } else {
            long[] lArray = this.key;
            n2 = (int)HashCommon.mix(l) & this.mask;
            long l2 = lArray[n2];
            if (l2 != 0L) {
                if (l2 == l) {
                    this.moveIndexToFirst(n2);
                    return this.setValue(n2, n);
                }
                while ((l2 = lArray[n2 = n2 + 1 & this.mask]) != 0L) {
                    if (l2 != l) continue;
                    this.moveIndexToFirst(n2);
                    return this.setValue(n2, n);
                }
            }
        }
        this.key[n2] = l;
        this.value[n2] = n;
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n3 = this.first;
            this.link[n3] = this.link[n3] ^ (this.link[this.first] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
            this.link[n2] = 0xFFFFFFFF00000000L | (long)this.first & 0xFFFFFFFFL;
            this.first = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }

    public int putAndMoveToLast(long l, int n) {
        int n2;
        if (l == 0L) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, n);
            }
            this.containsNullKey = true;
            n2 = this.n;
        } else {
            long[] lArray = this.key;
            n2 = (int)HashCommon.mix(l) & this.mask;
            long l2 = lArray[n2];
            if (l2 != 0L) {
                if (l2 == l) {
                    this.moveIndexToLast(n2);
                    return this.setValue(n2, n);
                }
                while ((l2 = lArray[n2 = n2 + 1 & this.mask]) != 0L) {
                    if (l2 != l) continue;
                    this.moveIndexToLast(n2);
                    return this.setValue(n2, n);
                }
            }
        }
        this.key[n2] = l;
        this.value[n2] = n;
        if (this.size == 0) {
            this.first = this.last = n2;
            this.link[n2] = -1L;
        } else {
            int n3 = this.last;
            this.link[n3] = this.link[n3] ^ (this.link[this.last] ^ (long)n2 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
            this.link[n2] = ((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL;
            this.last = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
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
    public Long2IntSortedMap tailMap(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long2IntSortedMap headMap(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long2IntSortedMap subMap(long l, long l2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LongComparator comparator() {
        return null;
    }

    @Override
    public Long2IntSortedMap.FastSortedEntrySet long2IntEntrySet() {
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
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection(this){
                final Long2IntLinkedOpenHashMap this$0;
                {
                    this.this$0 = long2IntLinkedOpenHashMap;
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
        int n3 = this.first;
        int n4 = -1;
        int n5 = -1;
        long[] lArray3 = this.link;
        long[] lArray4 = new long[n + 1];
        this.first = -1;
        int n6 = this.size;
        while (n6-- != 0) {
            int n7;
            if (lArray[n3] == 0L) {
                n7 = n;
            } else {
                n7 = (int)HashCommon.mix(lArray[n3]) & n2;
                while (lArray2[n7] != 0L) {
                    n7 = n7 + 1 & n2;
                }
            }
            lArray2[n7] = lArray[n3];
            nArray2[n7] = nArray[n3];
            if (n4 != -1) {
                int n8 = n5;
                lArray4[n8] = lArray4[n8] ^ (lArray4[n5] ^ (long)n7 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n9 = n7;
                lArray4[n9] = lArray4[n9] ^ (lArray4[n7] ^ ((long)n5 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n5 = n7;
            } else {
                n5 = this.first = n7;
                lArray4[n7] = -1L;
            }
            int n10 = n3;
            n3 = (int)lArray3[n3];
            n4 = n10;
        }
        this.link = lArray4;
        this.last = n5;
        if (n5 != -1) {
            int n11 = n5;
            lArray4[n11] = lArray4[n11] | 0xFFFFFFFFL;
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = lArray2;
        this.value = nArray2;
    }

    public Long2IntLinkedOpenHashMap clone() {
        Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap;
        try {
            long2IntLinkedOpenHashMap = (Long2IntLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2IntLinkedOpenHashMap.keys = null;
        long2IntLinkedOpenHashMap.values = null;
        long2IntLinkedOpenHashMap.entries = null;
        long2IntLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        long2IntLinkedOpenHashMap.key = (long[])this.key.clone();
        long2IntLinkedOpenHashMap.value = (int[])this.value.clone();
        long2IntLinkedOpenHashMap.link = (long[])this.link.clone();
        return long2IntLinkedOpenHashMap;
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
        MapIterator mapIterator = new MapIterator(this);
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
        this.link = new long[this.n + 1];
        long[] lArray2 = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            long l = objectInputStream.readLong();
            int n4 = objectInputStream.readInt();
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
            nArray[n3] = n4;
            if (this.first != -1) {
                int n5 = n;
                lArray2[n5] = lArray2[n5] ^ (lArray2[n] ^ (long)n3 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n6 = n3;
                lArray2[n6] = lArray2[n6] ^ (lArray2[n3] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n = n3;
                continue;
            }
            n = this.first = n3;
            int n7 = n3;
            lArray2[n7] = lArray2[n7] | 0xFFFFFFFF00000000L;
        }
        this.last = n;
        if (n != -1) {
            int n8 = n;
            lArray2[n8] = lArray2[n8] | 0xFFFFFFFFL;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSortedSet long2IntEntrySet() {
        return this.long2IntEntrySet();
    }

    @Override
    public LongSet keySet() {
        return this.keySet();
    }

    @Override
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

    @Override
    public Comparator comparator() {
        return this.comparator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$100(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
        return long2IntLinkedOpenHashMap.removeNullEntry();
    }

    static int access$200(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, int n) {
        return long2IntLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements IntListIterator {
        final Long2IntLinkedOpenHashMap this$0;

        @Override
        public int previousInt() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
            this.this$0 = long2IntLinkedOpenHashMap;
            super(long2IntLinkedOpenHashMap);
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
    extends AbstractLongSortedSet {
        final Long2IntLinkedOpenHashMap this$0;

        private KeySet(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
            this.this$0 = long2IntLinkedOpenHashMap;
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

        KeySet(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, 1 var2_2) {
            this(long2IntLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements LongListIterator {
        final Long2IntLinkedOpenHashMap this$0;

        public KeyIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, long l) {
            this.this$0 = long2IntLinkedOpenHashMap;
            super(long2IntLinkedOpenHashMap, l, null);
        }

        @Override
        public long previousLong() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
            this.this$0 = long2IntLinkedOpenHashMap;
            super(long2IntLinkedOpenHashMap);
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
    extends AbstractObjectSortedSet<Long2IntMap.Entry>
    implements Long2IntSortedMap.FastSortedEntrySet {
        final Long2IntLinkedOpenHashMap this$0;

        private MapEntrySet(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
            this.this$0 = long2IntLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Long2IntMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Long2IntMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Long2IntMap.Entry> subSet(Long2IntMap.Entry entry, Long2IntMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Long2IntMap.Entry> headSet(Long2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Long2IntMap.Entry> tailSet(Long2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long2IntMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Long2IntMap.Entry last() {
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
                    Long2IntLinkedOpenHashMap.access$100(this.this$0);
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
                    Long2IntLinkedOpenHashMap.access$200(this.this$0, n2);
                    return false;
                }
                return true;
            }
            do {
                if ((l2 = lArray[n2 = n2 + 1 & this.this$0.mask]) != 0L) continue;
                return true;
            } while (l2 != l || this.this$0.value[n2] != n);
            Long2IntLinkedOpenHashMap.access$200(this.this$0, n2);
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
        public ObjectListIterator<Long2IntMap.Entry> iterator(Long2IntMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getLongKey());
        }

        @Override
        public ObjectListIterator<Long2IntMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Long2IntMap.Entry> fastIterator(Long2IntMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getLongKey());
        }

        @Override
        public void forEach(Consumer<? super Long2IntMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractLong2IntMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Long2IntMap.Entry> consumer) {
            AbstractLong2IntMap.BasicEntry basicEntry = new AbstractLong2IntMap.BasicEntry();
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
            return this.tailSet((Long2IntMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Long2IntMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Long2IntMap.Entry)object, (Long2IntMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Long2IntMap.Entry)object);
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
            return this.tailSet((Long2IntMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Long2IntMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Long2IntMap.Entry)object, (Long2IntMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Long2IntMap.Entry entry) {
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

        MapEntrySet(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, 1 var2_2) {
            this(long2IntLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Long2IntMap.Entry> {
        final MapEntry entry;
        final Long2IntLinkedOpenHashMap this$0;

        public FastEntryIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
            this.this$0 = long2IntLinkedOpenHashMap;
            super(long2IntLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, long l) {
            this.this$0 = long2IntLinkedOpenHashMap;
            super(long2IntLinkedOpenHashMap, l, null);
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
            super.add((Long2IntMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Long2IntMap.Entry)object);
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
    implements ObjectListIterator<Long2IntMap.Entry> {
        private MapEntry entry;
        final Long2IntLinkedOpenHashMap this$0;

        public EntryIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
            this.this$0 = long2IntLinkedOpenHashMap;
            super(long2IntLinkedOpenHashMap);
        }

        public EntryIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, long l) {
            this.this$0 = long2IntLinkedOpenHashMap;
            super(long2IntLinkedOpenHashMap, l, null);
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
            super.add((Long2IntMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Long2IntMap.Entry)object);
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
        final Long2IntLinkedOpenHashMap this$0;

        protected MapIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
            this.this$0 = long2IntLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = long2IntLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, long l) {
            this.this$0 = long2IntLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (l == 0L) {
                if (long2IntLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)long2IntLinkedOpenHashMap.link[long2IntLinkedOpenHashMap.n];
                    this.prev = long2IntLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + l + " does not belong to this map.");
            }
            if (long2IntLinkedOpenHashMap.key[long2IntLinkedOpenHashMap.last] == l) {
                this.prev = long2IntLinkedOpenHashMap.last;
                this.index = long2IntLinkedOpenHashMap.size;
                return;
            }
            int n = (int)HashCommon.mix(l) & long2IntLinkedOpenHashMap.mask;
            while (long2IntLinkedOpenHashMap.key[n] != 0L) {
                if (long2IntLinkedOpenHashMap.key[n] == l) {
                    this.next = (int)long2IntLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & long2IntLinkedOpenHashMap.mask;
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

        public void set(Long2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Long2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, long l, 1 var4_3) {
            this(long2IntLinkedOpenHashMap, l);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Long2IntMap.Entry,
    Map.Entry<Long, Integer> {
        int index;
        final Long2IntLinkedOpenHashMap this$0;

        MapEntry(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, int n) {
            this.this$0 = long2IntLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap) {
            this.this$0 = long2IntLinkedOpenHashMap;
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

