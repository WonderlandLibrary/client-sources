/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2DoubleMap;
import it.unimi.dsi.fastutil.chars.AbstractChar2DoubleSortedMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2DoubleMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleSortedMap;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
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
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Char2DoubleLinkedOpenHashMap
extends AbstractChar2DoubleSortedMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient char[] key;
    protected transient double[] value;
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
    protected transient Char2DoubleSortedMap.FastSortedEntrySet entries;
    protected transient CharSortedSet keys;
    protected transient DoubleCollection values;

    public Char2DoubleLinkedOpenHashMap(int n, float f) {
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
        this.value = new double[this.n + 1];
        this.link = new long[this.n + 1];
    }

    public Char2DoubleLinkedOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Char2DoubleLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Char2DoubleLinkedOpenHashMap(Map<? extends Character, ? extends Double> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Char2DoubleLinkedOpenHashMap(Map<? extends Character, ? extends Double> map) {
        this(map, 0.75f);
    }

    public Char2DoubleLinkedOpenHashMap(Char2DoubleMap char2DoubleMap, float f) {
        this(char2DoubleMap.size(), f);
        this.putAll(char2DoubleMap);
    }

    public Char2DoubleLinkedOpenHashMap(Char2DoubleMap char2DoubleMap) {
        this(char2DoubleMap, 0.75f);
    }

    public Char2DoubleLinkedOpenHashMap(char[] cArray, double[] dArray, float f) {
        this(cArray.length, f);
        if (cArray.length != dArray.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + cArray.length + " and " + dArray.length + ")");
        }
        for (int i = 0; i < cArray.length; ++i) {
            this.put(cArray[i], dArray[i]);
        }
    }

    public Char2DoubleLinkedOpenHashMap(char[] cArray, double[] dArray) {
        this(cArray, dArray, 0.75f);
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

    private double removeEntry(int n) {
        double d = this.value[n];
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    private double removeNullEntry() {
        this.containsNullKey = false;
        double d = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    @Override
    public void putAll(Map<? extends Character, ? extends Double> map) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(map.size());
        } else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }

    private int find(char c) {
        if (c == '\u0000') {
            return this.containsNullKey ? this.n : -(this.n + 1);
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return -(n + 1);
        }
        if (c == c2) {
            return n;
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return -(n + 1);
        } while (c != c2);
        return n;
    }

    private void insert(int n, char c, double d) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = c;
        this.value[n] = d;
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
    public double put(char c, double d) {
        int n = this.find(c);
        if (n < 0) {
            this.insert(-n - 1, c, d);
            return this.defRetValue;
        }
        double d2 = this.value[n];
        this.value[n] = d;
        return d2;
    }

    private double addToValue(int n, double d) {
        double d2 = this.value[n];
        this.value[n] = d2 + d;
        return d2;
    }

    public double addTo(char c, double d) {
        int n;
        if (c == '\u0000') {
            if (this.containsNullKey) {
                return this.addToValue(this.n, d);
            }
            n = this.n;
            this.containsNullKey = true;
        } else {
            char[] cArray = this.key;
            n = HashCommon.mix(c) & this.mask;
            char c2 = cArray[n];
            if (c2 != '\u0000') {
                if (c2 == c) {
                    return this.addToValue(n, d);
                }
                while ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') {
                    if (c2 != c) continue;
                    return this.addToValue(n, d);
                }
            }
        }
        this.key[n] = c;
        this.value[n] = this.defRetValue + d;
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
        char[] cArray = this.key;
        while (true) {
            char c;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((c = cArray[n]) == '\u0000') {
                    cArray[n2] = '\u0000';
                    return;
                }
                int n3 = HashCommon.mix(c) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            cArray[n2] = c;
            this.value[n2] = this.value[n];
            this.fixPointers(n, n2);
        }
    }

    @Override
    public double remove(char c) {
        if (c == '\u0000') {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return this.defRetValue;
        }
        if (c == c2) {
            return this.removeEntry(n);
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return this.defRetValue;
        } while (c != c2);
        return this.removeEntry(n);
    }

    private double setValue(int n, double d) {
        double d2 = this.value[n];
        this.value[n] = d;
        return d2;
    }

    public double removeFirstDouble() {
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
        double d = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
    }

    public double removeLastDouble() {
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
        double d = this.value[n];
        if (n == this.n) {
            this.containsNullKey = false;
        } else {
            this.shiftKeys(n);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return d;
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

    public double getAndMoveToFirst(char c) {
        if (c == '\u0000') {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return this.defRetValue;
        }
        if (c == c2) {
            this.moveIndexToFirst(n);
            return this.value[n];
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return this.defRetValue;
        } while (c != c2);
        this.moveIndexToFirst(n);
        return this.value[n];
    }

    public double getAndMoveToLast(char c) {
        if (c == '\u0000') {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return this.defRetValue;
        }
        if (c == c2) {
            this.moveIndexToLast(n);
            return this.value[n];
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return this.defRetValue;
        } while (c != c2);
        this.moveIndexToLast(n);
        return this.value[n];
    }

    public double putAndMoveToFirst(char c, double d) {
        int n;
        if (c == '\u0000') {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, d);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            char[] cArray = this.key;
            n = HashCommon.mix(c) & this.mask;
            char c2 = cArray[n];
            if (c2 != '\u0000') {
                if (c2 == c) {
                    this.moveIndexToFirst(n);
                    return this.setValue(n, d);
                }
                while ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') {
                    if (c2 != c) continue;
                    this.moveIndexToFirst(n);
                    return this.setValue(n, d);
                }
            }
        }
        this.key[n] = c;
        this.value[n] = d;
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

    public double putAndMoveToLast(char c, double d) {
        int n;
        if (c == '\u0000') {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, d);
            }
            this.containsNullKey = true;
            n = this.n;
        } else {
            char[] cArray = this.key;
            n = HashCommon.mix(c) & this.mask;
            char c2 = cArray[n];
            if (c2 != '\u0000') {
                if (c2 == c) {
                    this.moveIndexToLast(n);
                    return this.setValue(n, d);
                }
                while ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') {
                    if (c2 != c) continue;
                    this.moveIndexToLast(n);
                    return this.setValue(n, d);
                }
            }
        }
        this.key[n] = c;
        this.value[n] = d;
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
    public double get(char c) {
        if (c == '\u0000') {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return this.defRetValue;
        }
        if (c == c2) {
            return this.value[n];
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return this.defRetValue;
        } while (c != c2);
        return this.value[n];
    }

    @Override
    public boolean containsKey(char c) {
        if (c == '\u0000') {
            return this.containsNullKey;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return true;
        }
        if (c == c2) {
            return false;
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return true;
        } while (c != c2);
        return false;
    }

    @Override
    public boolean containsValue(double d) {
        double[] dArray = this.value;
        char[] cArray = this.key;
        if (this.containsNullKey && Double.doubleToLongBits(dArray[this.n]) == Double.doubleToLongBits(d)) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (cArray[n] == '\u0000' || Double.doubleToLongBits(dArray[n]) != Double.doubleToLongBits(d)) continue;
            return false;
        }
        return true;
    }

    @Override
    public double getOrDefault(char c, double d) {
        if (c == '\u0000') {
            return this.containsNullKey ? this.value[this.n] : d;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return d;
        }
        if (c == c2) {
            return this.value[n];
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return d;
        } while (c != c2);
        return this.value[n];
    }

    @Override
    public double putIfAbsent(char c, double d) {
        int n = this.find(c);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, c, d);
        return this.defRetValue;
    }

    @Override
    public boolean remove(char c, double d) {
        if (c == '\u0000') {
            if (this.containsNullKey && Double.doubleToLongBits(d) == Double.doubleToLongBits(this.value[this.n])) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c2 = cArray[n];
        if (c2 == '\u0000') {
            return true;
        }
        if (c == c2 && Double.doubleToLongBits(d) == Double.doubleToLongBits(this.value[n])) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((c2 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return true;
        } while (c != c2 || Double.doubleToLongBits(d) != Double.doubleToLongBits(this.value[n]));
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(char c, double d, double d2) {
        int n = this.find(c);
        if (n < 0 || Double.doubleToLongBits(d) != Double.doubleToLongBits(this.value[n])) {
            return true;
        }
        this.value[n] = d2;
        return false;
    }

    @Override
    public double replace(char c, double d) {
        int n = this.find(c);
        if (n < 0) {
            return this.defRetValue;
        }
        double d2 = this.value[n];
        this.value[n] = d;
        return d2;
    }

    @Override
    public double computeIfAbsent(char c, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        int n = this.find(c);
        if (n >= 0) {
            return this.value[n];
        }
        double d = intToDoubleFunction.applyAsDouble(c);
        this.insert(-n - 1, c, d);
        return d;
    }

    @Override
    public double computeIfAbsentNullable(char c, IntFunction<? extends Double> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(c);
        if (n >= 0) {
            return this.value[n];
        }
        Double d = intFunction.apply(c);
        if (d == null) {
            return this.defRetValue;
        }
        double d2 = d;
        this.insert(-n - 1, c, d2);
        return d2;
    }

    @Override
    public double computeIfPresent(char c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        if (n < 0) {
            return this.defRetValue;
        }
        Double d = biFunction.apply(Character.valueOf(c), (Double)this.value[n]);
        if (d == null) {
            if (c == '\u0000') {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = d;
        return this.value[n];
    }

    @Override
    public double compute(char c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        Double d = biFunction.apply(Character.valueOf(c), n >= 0 ? Double.valueOf(this.value[n]) : null);
        if (d == null) {
            if (n >= 0) {
                if (c == '\u0000') {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        double d2 = d;
        if (n < 0) {
            this.insert(-n - 1, c, d2);
            return d2;
        }
        this.value[n] = d2;
        return this.value[n];
    }

    @Override
    public double merge(char c, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        if (n < 0) {
            this.insert(-n - 1, c, d);
            return d;
        }
        Double d2 = biFunction.apply((Double)this.value[n], (Double)d);
        if (d2 == null) {
            if (c == '\u0000') {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = d2;
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
    public char firstCharKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override
    public char lastCharKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override
    public Char2DoubleSortedMap tailMap(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Char2DoubleSortedMap headMap(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Char2DoubleSortedMap subMap(char c, char c2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharComparator comparator() {
        return null;
    }

    @Override
    public Char2DoubleSortedMap.FastSortedEntrySet char2DoubleEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(this, null);
        }
        return this.entries;
    }

    @Override
    public CharSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public DoubleCollection values() {
        if (this.values == null) {
            this.values = new AbstractDoubleCollection(this){
                final Char2DoubleLinkedOpenHashMap this$0;
                {
                    this.this$0 = char2DoubleLinkedOpenHashMap;
                }

                @Override
                public DoubleIterator iterator() {
                    return new ValueIterator(this.this$0);
                }

                @Override
                public int size() {
                    return this.this$0.size;
                }

                @Override
                public boolean contains(double d) {
                    return this.this$0.containsValue(d);
                }

                @Override
                public void clear() {
                    this.this$0.clear();
                }

                @Override
                public void forEach(DoubleConsumer doubleConsumer) {
                    if (this.this$0.containsNullKey) {
                        doubleConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (this.this$0.key[n] == '\u0000') continue;
                        doubleConsumer.accept(this.this$0.value[n]);
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
        double[] dArray = this.value;
        int n2 = n - 1;
        char[] cArray2 = new char[n + 1];
        double[] dArray2 = new double[n + 1];
        int n3 = this.first;
        int n4 = -1;
        int n5 = -1;
        long[] lArray = this.link;
        long[] lArray2 = new long[n + 1];
        this.first = -1;
        int n6 = this.size;
        while (n6-- != 0) {
            int n7;
            if (cArray[n3] == '\u0000') {
                n7 = n;
            } else {
                n7 = HashCommon.mix(cArray[n3]) & n2;
                while (cArray2[n7] != '\u0000') {
                    n7 = n7 + 1 & n2;
                }
            }
            cArray2[n7] = cArray[n3];
            dArray2[n7] = dArray[n3];
            if (n4 != -1) {
                int n8 = n5;
                lArray2[n8] = lArray2[n8] ^ (lArray2[n5] ^ (long)n7 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n9 = n7;
                lArray2[n9] = lArray2[n9] ^ (lArray2[n7] ^ ((long)n5 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n5 = n7;
            } else {
                n5 = this.first = n7;
                lArray2[n7] = -1L;
            }
            int n10 = n3;
            n3 = (int)lArray[n3];
            n4 = n10;
        }
        this.link = lArray2;
        this.last = n5;
        if (n5 != -1) {
            int n11 = n5;
            lArray2[n11] = lArray2[n11] | 0xFFFFFFFFL;
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = cArray2;
        this.value = dArray2;
    }

    public Char2DoubleLinkedOpenHashMap clone() {
        Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap;
        try {
            char2DoubleLinkedOpenHashMap = (Char2DoubleLinkedOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2DoubleLinkedOpenHashMap.keys = null;
        char2DoubleLinkedOpenHashMap.values = null;
        char2DoubleLinkedOpenHashMap.entries = null;
        char2DoubleLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        char2DoubleLinkedOpenHashMap.key = (char[])this.key.clone();
        char2DoubleLinkedOpenHashMap.value = (double[])this.value.clone();
        char2DoubleLinkedOpenHashMap.link = (long[])this.link.clone();
        return char2DoubleLinkedOpenHashMap;
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
            n4 = this.key[n3];
            n += (n4 ^= HashCommon.double2int(this.value[n3]));
            ++n3;
        }
        if (this.containsNullKey) {
            n += HashCommon.double2int(this.value[this.n]);
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        char[] cArray = this.key;
        double[] dArray = this.value;
        MapIterator mapIterator = new MapIterator(this);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeChar(cArray[n2]);
            objectOutputStream.writeDouble(dArray[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new char[this.n + 1];
        char[] cArray = this.key;
        this.value = new double[this.n + 1];
        double[] dArray = this.value;
        this.link = new long[this.n + 1];
        long[] lArray = this.link;
        int n = -1;
        this.last = -1;
        this.first = -1;
        int n2 = this.size;
        while (n2-- != 0) {
            int n3;
            char c = objectInputStream.readChar();
            double d = objectInputStream.readDouble();
            if (c == '\u0000') {
                n3 = this.n;
                this.containsNullKey = true;
            } else {
                n3 = HashCommon.mix(c) & this.mask;
                while (cArray[n3] != '\u0000') {
                    n3 = n3 + 1 & this.mask;
                }
            }
            cArray[n3] = c;
            dArray[n3] = d;
            if (this.first != -1) {
                int n4 = n;
                lArray[n4] = lArray[n4] ^ (lArray[n] ^ (long)n3 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
                int n5 = n3;
                lArray[n5] = lArray[n5] ^ (lArray[n3] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L;
                n = n3;
                continue;
            }
            n = this.first = n3;
            int n6 = n3;
            lArray[n6] = lArray[n6] | 0xFFFFFFFF00000000L;
        }
        this.last = n;
        if (n != -1) {
            int n7 = n;
            lArray[n7] = lArray[n7] | 0xFFFFFFFFL;
        }
    }

    private void checkTable() {
    }

    @Override
    public ObjectSortedSet char2DoubleEntrySet() {
        return this.char2DoubleEntrySet();
    }

    @Override
    public CharSet keySet() {
        return this.keySet();
    }

    @Override
    public ObjectSet char2DoubleEntrySet() {
        return this.char2DoubleEntrySet();
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

    static double access$100(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
        return char2DoubleLinkedOpenHashMap.removeNullEntry();
    }

    static double access$200(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, int n) {
        return char2DoubleLinkedOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements DoubleListIterator {
        final Char2DoubleLinkedOpenHashMap this$0;

        @Override
        public double previousDouble() {
            return this.this$0.value[this.previousEntry()];
        }

        public ValueIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            super(char2DoubleLinkedOpenHashMap);
        }

        @Override
        public double nextDouble() {
            return this.this$0.value[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractCharSortedSet {
        final Char2DoubleLinkedOpenHashMap this$0;

        private KeySet(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
        }

        @Override
        public CharListIterator iterator(char c) {
            return new KeyIterator(this.this$0, c);
        }

        @Override
        public CharListIterator iterator() {
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
        public char firstChar() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }

        @Override
        public char lastChar() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }

        @Override
        public CharComparator comparator() {
            return null;
        }

        @Override
        public CharSortedSet tailSet(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CharSortedSet headSet(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CharSortedSet subSet(char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public CharBidirectionalIterator iterator(char c) {
            return this.iterator(c);
        }

        @Override
        public CharIterator iterator() {
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

        KeySet(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, 1 var2_2) {
            this(char2DoubleLinkedOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements CharListIterator {
        final Char2DoubleLinkedOpenHashMap this$0;

        public KeyIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, char c) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            super(char2DoubleLinkedOpenHashMap, c, null);
        }

        @Override
        public char previousChar() {
            return this.this$0.key[this.previousEntry()];
        }

        public KeyIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            super(char2DoubleLinkedOpenHashMap);
        }

        @Override
        public char nextChar() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class MapEntrySet
    extends AbstractObjectSortedSet<Char2DoubleMap.Entry>
    implements Char2DoubleSortedMap.FastSortedEntrySet {
        final Char2DoubleLinkedOpenHashMap this$0;

        private MapEntrySet(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
        }

        @Override
        public ObjectBidirectionalIterator<Char2DoubleMap.Entry> iterator() {
            return new EntryIterator(this.this$0);
        }

        @Override
        public Comparator<? super Char2DoubleMap.Entry> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Char2DoubleMap.Entry> subSet(Char2DoubleMap.Entry entry, Char2DoubleMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Char2DoubleMap.Entry> headSet(Char2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSortedSet<Char2DoubleMap.Entry> tailSet(Char2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Char2DoubleMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(this.this$0, this.this$0.first);
        }

        @Override
        public Char2DoubleMap.Entry last() {
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
            if (entry.getKey() == null || !(entry.getKey() instanceof Character)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            double d = (Double)entry.getValue();
            if (c == '\u0000') {
                return this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d);
            }
            char[] cArray = this.this$0.key;
            int n = HashCommon.mix(c) & this.this$0.mask;
            char c2 = cArray[n];
            if (c2 == '\u0000') {
                return true;
            }
            if (c == c2) {
                return Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d);
            }
            do {
                if ((c2 = cArray[n = n + 1 & this.this$0.mask]) != '\u0000') continue;
                return true;
            } while (c != c2);
            return Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            double d = (Double)entry.getValue();
            if (c == '\u0000') {
                if (this.this$0.containsNullKey && Double.doubleToLongBits(this.this$0.value[this.this$0.n]) == Double.doubleToLongBits(d)) {
                    Char2DoubleLinkedOpenHashMap.access$100(this.this$0);
                    return false;
                }
                return true;
            }
            char[] cArray = this.this$0.key;
            int n = HashCommon.mix(c) & this.this$0.mask;
            char c2 = cArray[n];
            if (c2 == '\u0000') {
                return true;
            }
            if (c2 == c) {
                if (Double.doubleToLongBits(this.this$0.value[n]) == Double.doubleToLongBits(d)) {
                    Char2DoubleLinkedOpenHashMap.access$200(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((c2 = cArray[n = n + 1 & this.this$0.mask]) != '\u0000') continue;
                return true;
            } while (c2 != c || Double.doubleToLongBits(this.this$0.value[n]) != Double.doubleToLongBits(d));
            Char2DoubleLinkedOpenHashMap.access$200(this.this$0, n);
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
        public ObjectListIterator<Char2DoubleMap.Entry> iterator(Char2DoubleMap.Entry entry) {
            return new EntryIterator(this.this$0, entry.getCharKey());
        }

        @Override
        public ObjectListIterator<Char2DoubleMap.Entry> fastIterator() {
            return new FastEntryIterator(this.this$0);
        }

        public ObjectListIterator<Char2DoubleMap.Entry> fastIterator(Char2DoubleMap.Entry entry) {
            return new FastEntryIterator(this.this$0, entry.getCharKey());
        }

        @Override
        public void forEach(Consumer<? super Char2DoubleMap.Entry> consumer) {
            int n = this.this$0.size;
            int n2 = this.this$0.first;
            while (n-- != 0) {
                int n3 = n2;
                n2 = (int)this.this$0.link[n3];
                consumer.accept(new AbstractChar2DoubleMap.BasicEntry(this.this$0.key[n3], this.this$0.value[n3]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Char2DoubleMap.Entry> consumer) {
            AbstractChar2DoubleMap.BasicEntry basicEntry = new AbstractChar2DoubleMap.BasicEntry();
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
            return this.tailSet((Char2DoubleMap.Entry)object);
        }

        @Override
        public ObjectSortedSet headSet(Object object) {
            return this.headSet((Char2DoubleMap.Entry)object);
        }

        @Override
        public ObjectSortedSet subSet(Object object, Object object2) {
            return this.subSet((Char2DoubleMap.Entry)object, (Char2DoubleMap.Entry)object2);
        }

        @Override
        public ObjectBidirectionalIterator iterator(Object object) {
            return this.iterator((Char2DoubleMap.Entry)object);
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
            return this.tailSet((Char2DoubleMap.Entry)object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet((Char2DoubleMap.Entry)object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Char2DoubleMap.Entry)object, (Char2DoubleMap.Entry)object2);
        }

        public ObjectBidirectionalIterator fastIterator(Char2DoubleMap.Entry entry) {
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

        MapEntrySet(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, 1 var2_2) {
            this(char2DoubleLinkedOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectListIterator<Char2DoubleMap.Entry> {
        final MapEntry entry;
        final Char2DoubleLinkedOpenHashMap this$0;

        public FastEntryIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            super(char2DoubleLinkedOpenHashMap);
            this.entry = new MapEntry(this.this$0);
        }

        public FastEntryIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, char c) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            super(char2DoubleLinkedOpenHashMap, c, null);
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
            super.add((Char2DoubleMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Char2DoubleMap.Entry)object);
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
    implements ObjectListIterator<Char2DoubleMap.Entry> {
        private MapEntry entry;
        final Char2DoubleLinkedOpenHashMap this$0;

        public EntryIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            super(char2DoubleLinkedOpenHashMap);
        }

        public EntryIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, char c) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            super(char2DoubleLinkedOpenHashMap, c, null);
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
            super.add((Char2DoubleMap.Entry)object);
        }

        @Override
        public void set(Object object) {
            super.set((Char2DoubleMap.Entry)object);
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
        final Char2DoubleLinkedOpenHashMap this$0;

        protected MapIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = char2DoubleLinkedOpenHashMap.first;
            this.index = 0;
        }

        private MapIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, char c) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (c == '\u0000') {
                if (char2DoubleLinkedOpenHashMap.containsNullKey) {
                    this.next = (int)char2DoubleLinkedOpenHashMap.link[char2DoubleLinkedOpenHashMap.n];
                    this.prev = char2DoubleLinkedOpenHashMap.n;
                    return;
                }
                throw new NoSuchElementException("The key " + c + " does not belong to this map.");
            }
            if (char2DoubleLinkedOpenHashMap.key[char2DoubleLinkedOpenHashMap.last] == c) {
                this.prev = char2DoubleLinkedOpenHashMap.last;
                this.index = char2DoubleLinkedOpenHashMap.size;
                return;
            }
            int n = HashCommon.mix(c) & char2DoubleLinkedOpenHashMap.mask;
            while (char2DoubleLinkedOpenHashMap.key[n] != '\u0000') {
                if (char2DoubleLinkedOpenHashMap.key[n] == c) {
                    this.next = (int)char2DoubleLinkedOpenHashMap.link[n];
                    this.prev = n;
                    return;
                }
                n = n + 1 & char2DoubleLinkedOpenHashMap.mask;
            }
            throw new NoSuchElementException("The key " + c + " does not belong to this map.");
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
                char[] cArray = this.this$0.key;
                while (true) {
                    char c;
                    int n2 = n;
                    n = n2 + 1 & this.this$0.mask;
                    while (true) {
                        if ((c = cArray[n]) == '\u0000') {
                            cArray[n2] = '\u0000';
                            return;
                        }
                        int n3 = HashCommon.mix(c) & this.this$0.mask;
                        if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                        n = n + 1 & this.this$0.mask;
                    }
                    cArray[n2] = c;
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

        public void set(Char2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        public void add(Char2DoubleMap.Entry entry) {
            throw new UnsupportedOperationException();
        }

        MapIterator(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, char c, 1 var3_3) {
            this(char2DoubleLinkedOpenHashMap, c);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Char2DoubleMap.Entry,
    Map.Entry<Character, Double> {
        int index;
        final Char2DoubleLinkedOpenHashMap this$0;

        MapEntry(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap, int n) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
            this.index = n;
        }

        MapEntry(Char2DoubleLinkedOpenHashMap char2DoubleLinkedOpenHashMap) {
            this.this$0 = char2DoubleLinkedOpenHashMap;
        }

        @Override
        public char getCharKey() {
            return this.this$0.key[this.index];
        }

        @Override
        public double getDoubleValue() {
            return this.this$0.value[this.index];
        }

        @Override
        public double setValue(double d) {
            double d2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = d;
            return d2;
        }

        @Override
        @Deprecated
        public Character getKey() {
            return Character.valueOf(this.this$0.key[this.index]);
        }

        @Override
        @Deprecated
        public Double getValue() {
            return this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Double setValue(Double d) {
            return this.setValue((double)d);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            return this.this$0.key[this.index] == ((Character)entry.getKey()).charValue() && Double.doubleToLongBits(this.this$0.value[this.index]) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public int hashCode() {
            return this.this$0.key[this.index] ^ HashCommon.double2int(this.this$0.value[this.index]);
        }

        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }

        @Override
        @Deprecated
        public Object setValue(Object object) {
            return this.setValue((Double)object);
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

