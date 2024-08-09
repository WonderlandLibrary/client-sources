/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.AbstractChar2CharMap;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
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
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Char2CharOpenHashMap
extends AbstractChar2CharMap
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient char[] key;
    protected transient char[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Char2CharMap.FastEntrySet entries;
    protected transient CharSet keys;
    protected transient CharCollection values;

    public Char2CharOpenHashMap(int n, float f) {
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
        this.value = new char[this.n + 1];
    }

    public Char2CharOpenHashMap(int n) {
        this(n, 0.75f);
    }

    public Char2CharOpenHashMap() {
        this(16, 0.75f);
    }

    public Char2CharOpenHashMap(Map<? extends Character, ? extends Character> map, float f) {
        this(map.size(), f);
        this.putAll(map);
    }

    public Char2CharOpenHashMap(Map<? extends Character, ? extends Character> map) {
        this(map, 0.75f);
    }

    public Char2CharOpenHashMap(Char2CharMap char2CharMap, float f) {
        this(char2CharMap.size(), f);
        this.putAll(char2CharMap);
    }

    public Char2CharOpenHashMap(Char2CharMap char2CharMap) {
        this(char2CharMap, 0.75f);
    }

    public Char2CharOpenHashMap(char[] cArray, char[] cArray2, float f) {
        this(cArray.length, f);
        if (cArray.length != cArray2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + cArray.length + " and " + cArray2.length + ")");
        }
        for (int i = 0; i < cArray.length; ++i) {
            this.put(cArray[i], cArray2[i]);
        }
    }

    public Char2CharOpenHashMap(char[] cArray, char[] cArray2) {
        this(cArray, cArray2, 0.75f);
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
    public void putAll(Map<? extends Character, ? extends Character> map) {
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

    private void insert(int n, char c, char c2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = c;
        this.value[n] = c2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }

    @Override
    public char put(char c, char c2) {
        int n = this.find(c);
        if (n < 0) {
            this.insert(-n - 1, c, c2);
            return this.defRetValue;
        }
        char c3 = this.value[n];
        this.value[n] = c2;
        return c3;
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
        }
    }

    @Override
    public char remove(char c) {
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

    @Override
    public char get(char c) {
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
    public boolean containsValue(char c) {
        char[] cArray = this.value;
        char[] cArray2 = this.key;
        if (this.containsNullKey && cArray[this.n] == c) {
            return false;
        }
        int n = this.n;
        while (n-- != 0) {
            if (cArray2[n] == '\u0000' || cArray[n] != c) continue;
            return false;
        }
        return true;
    }

    @Override
    public char getOrDefault(char c, char c2) {
        if (c == '\u0000') {
            return this.containsNullKey ? this.value[this.n] : c2;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c3 = cArray[n];
        if (c3 == '\u0000') {
            return c2;
        }
        if (c == c3) {
            return this.value[n];
        }
        do {
            if ((c3 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return c2;
        } while (c != c3);
        return this.value[n];
    }

    @Override
    public char putIfAbsent(char c, char c2) {
        int n = this.find(c);
        if (n >= 0) {
            return this.value[n];
        }
        this.insert(-n - 1, c, c2);
        return this.defRetValue;
    }

    @Override
    public boolean remove(char c, char c2) {
        if (c == '\u0000') {
            if (this.containsNullKey && c2 == this.value[this.n]) {
                this.removeNullEntry();
                return false;
            }
            return true;
        }
        char[] cArray = this.key;
        int n = HashCommon.mix(c) & this.mask;
        char c3 = cArray[n];
        if (c3 == '\u0000') {
            return true;
        }
        if (c == c3 && c2 == this.value[n]) {
            this.removeEntry(n);
            return false;
        }
        do {
            if ((c3 = cArray[n = n + 1 & this.mask]) != '\u0000') continue;
            return true;
        } while (c != c3 || c2 != this.value[n]);
        this.removeEntry(n);
        return false;
    }

    @Override
    public boolean replace(char c, char c2, char c3) {
        int n = this.find(c);
        if (n < 0 || c2 != this.value[n]) {
            return true;
        }
        this.value[n] = c3;
        return false;
    }

    @Override
    public char replace(char c, char c2) {
        int n = this.find(c);
        if (n < 0) {
            return this.defRetValue;
        }
        char c3 = this.value[n];
        this.value[n] = c2;
        return c3;
    }

    @Override
    public char computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n = this.find(c);
        if (n >= 0) {
            return this.value[n];
        }
        char c2 = SafeMath.safeIntToChar(intUnaryOperator.applyAsInt(c));
        this.insert(-n - 1, c, c2);
        return c2;
    }

    @Override
    public char computeIfAbsentNullable(char c, IntFunction<? extends Character> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.find(c);
        if (n >= 0) {
            return this.value[n];
        }
        Character c2 = intFunction.apply(c);
        if (c2 == null) {
            return this.defRetValue;
        }
        char c3 = c2.charValue();
        this.insert(-n - 1, c, c3);
        return c3;
    }

    @Override
    public char computeIfPresent(char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        if (n < 0) {
            return this.defRetValue;
        }
        Character c2 = biFunction.apply(Character.valueOf(c), Character.valueOf(this.value[n]));
        if (c2 == null) {
            if (c == '\u0000') {
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
    public char compute(char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        Character c2 = biFunction.apply(Character.valueOf(c), n >= 0 ? Character.valueOf(this.value[n]) : null);
        if (c2 == null) {
            if (n >= 0) {
                if (c == '\u0000') {
                    this.removeNullEntry();
                } else {
                    this.removeEntry(n);
                }
            }
            return this.defRetValue;
        }
        char c3 = c2.charValue();
        if (n < 0) {
            this.insert(-n - 1, c, c3);
            return c3;
        }
        this.value[n] = c3;
        return this.value[n];
    }

    @Override
    public char merge(char c, char c2, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.find(c);
        if (n < 0) {
            this.insert(-n - 1, c, c2);
            return c2;
        }
        Character c3 = biFunction.apply(Character.valueOf(this.value[n]), Character.valueOf(c2));
        if (c3 == null) {
            if (c == '\u0000') {
                this.removeNullEntry();
            } else {
                this.removeEntry(n);
            }
            return this.defRetValue;
        }
        this.value[n] = c3.charValue();
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
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public Char2CharMap.FastEntrySet char2CharEntrySet() {
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
    public CharCollection values() {
        if (this.values == null) {
            this.values = new AbstractCharCollection(this){
                final Char2CharOpenHashMap this$0;
                {
                    this.this$0 = char2CharOpenHashMap;
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
                        if (this.this$0.key[n] == '\u0000') continue;
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
        char[] cArray = this.key;
        char[] cArray2 = this.value;
        int n2 = n - 1;
        char[] cArray3 = new char[n + 1];
        char[] cArray4 = new char[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (cArray[--n3] == '\u0000') {
            }
            int n5 = HashCommon.mix(cArray[n3]) & n2;
            if (cArray3[n5] != '\u0000') {
                while (cArray3[n5 = n5 + 1 & n2] != '\u0000') {
                }
            }
            cArray3[n5] = cArray[n3];
            cArray4[n5] = cArray2[n3];
        }
        cArray4[n] = cArray2[this.n];
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = cArray3;
        this.value = cArray4;
    }

    public Char2CharOpenHashMap clone() {
        Char2CharOpenHashMap char2CharOpenHashMap;
        try {
            char2CharOpenHashMap = (Char2CharOpenHashMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2CharOpenHashMap.keys = null;
        char2CharOpenHashMap.values = null;
        char2CharOpenHashMap.entries = null;
        char2CharOpenHashMap.containsNullKey = this.containsNullKey;
        char2CharOpenHashMap.key = (char[])this.key.clone();
        char2CharOpenHashMap.value = (char[])this.value.clone();
        return char2CharOpenHashMap;
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
            n += (n4 ^= this.value[n3]);
            ++n3;
        }
        if (this.containsNullKey) {
            n += this.value[this.n];
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        char[] cArray = this.key;
        char[] cArray2 = this.value;
        MapIterator mapIterator = new MapIterator(this, null);
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            int n2 = mapIterator.nextEntry();
            objectOutputStream.writeChar(cArray[n2]);
            objectOutputStream.writeChar(cArray2[n2]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new char[this.n + 1];
        char[] cArray = this.key;
        this.value = new char[this.n + 1];
        char[] cArray2 = this.value;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            char c = objectInputStream.readChar();
            char c2 = objectInputStream.readChar();
            if (c == '\u0000') {
                n2 = this.n;
                this.containsNullKey = true;
            } else {
                n2 = HashCommon.mix(c) & this.mask;
                while (cArray[n2] != '\u0000') {
                    n2 = n2 + 1 & this.mask;
                }
            }
            cArray[n2] = c;
            cArray2[n2] = c2;
        }
    }

    private void checkTable() {
    }

    public ObjectSet char2CharEntrySet() {
        return this.char2CharEntrySet();
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

    static char access$300(Char2CharOpenHashMap char2CharOpenHashMap) {
        return char2CharOpenHashMap.removeNullEntry();
    }

    static char access$400(Char2CharOpenHashMap char2CharOpenHashMap, int n) {
        return char2CharOpenHashMap.removeEntry(n);
    }

    private final class ValueIterator
    extends MapIterator
    implements CharIterator {
        final Char2CharOpenHashMap this$0;

        public ValueIterator(Char2CharOpenHashMap char2CharOpenHashMap) {
            this.this$0 = char2CharOpenHashMap;
            super(char2CharOpenHashMap, null);
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
    extends AbstractCharSet {
        final Char2CharOpenHashMap this$0;

        private KeySet(Char2CharOpenHashMap char2CharOpenHashMap) {
            this.this$0 = char2CharOpenHashMap;
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

        KeySet(Char2CharOpenHashMap char2CharOpenHashMap, 1 var2_2) {
            this(char2CharOpenHashMap);
        }
    }

    private final class KeyIterator
    extends MapIterator
    implements CharIterator {
        final Char2CharOpenHashMap this$0;

        public KeyIterator(Char2CharOpenHashMap char2CharOpenHashMap) {
            this.this$0 = char2CharOpenHashMap;
            super(char2CharOpenHashMap, null);
        }

        @Override
        public char nextChar() {
            return this.this$0.key[this.nextEntry()];
        }
    }

    private final class MapEntrySet
    extends AbstractObjectSet<Char2CharMap.Entry>
    implements Char2CharMap.FastEntrySet {
        final Char2CharOpenHashMap this$0;

        private MapEntrySet(Char2CharOpenHashMap char2CharOpenHashMap) {
            this.this$0 = char2CharOpenHashMap;
        }

        @Override
        public ObjectIterator<Char2CharMap.Entry> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        @Override
        public ObjectIterator<Char2CharMap.Entry> fastIterator() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            char c2 = ((Character)entry.getValue()).charValue();
            if (c == '\u0000') {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == c2;
            }
            char[] cArray = this.this$0.key;
            int n = HashCommon.mix(c) & this.this$0.mask;
            char c3 = cArray[n];
            if (c3 == '\u0000') {
                return true;
            }
            if (c == c3) {
                return this.this$0.value[n] == c2;
            }
            do {
                if ((c3 = cArray[n = n + 1 & this.this$0.mask]) != '\u0000') continue;
                return true;
            } while (c != c3);
            return this.this$0.value[n] == c2;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            char c2 = ((Character)entry.getValue()).charValue();
            if (c == '\u0000') {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == c2) {
                    Char2CharOpenHashMap.access$300(this.this$0);
                    return false;
                }
                return true;
            }
            char[] cArray = this.this$0.key;
            int n = HashCommon.mix(c) & this.this$0.mask;
            char c3 = cArray[n];
            if (c3 == '\u0000') {
                return true;
            }
            if (c3 == c) {
                if (this.this$0.value[n] == c2) {
                    Char2CharOpenHashMap.access$400(this.this$0, n);
                    return false;
                }
                return true;
            }
            do {
                if ((c3 = cArray[n = n + 1 & this.this$0.mask]) != '\u0000') continue;
                return true;
            } while (c3 != c || this.this$0.value[n] != c2);
            Char2CharOpenHashMap.access$400(this.this$0, n);
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
        public void forEach(Consumer<? super Char2CharMap.Entry> consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new AbstractChar2CharMap.BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] == '\u0000') continue;
                consumer.accept(new AbstractChar2CharMap.BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Char2CharMap.Entry> consumer) {
            AbstractChar2CharMap.BasicEntry basicEntry = new AbstractChar2CharMap.BasicEntry();
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

        MapEntrySet(Char2CharOpenHashMap char2CharOpenHashMap, 1 var2_2) {
            this(char2CharOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class FastEntryIterator
    extends MapIterator
    implements ObjectIterator<Char2CharMap.Entry> {
        private final MapEntry entry;
        final Char2CharOpenHashMap this$0;

        private FastEntryIterator(Char2CharOpenHashMap char2CharOpenHashMap) {
            this.this$0 = char2CharOpenHashMap;
            super(char2CharOpenHashMap, null);
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

        FastEntryIterator(Char2CharOpenHashMap char2CharOpenHashMap, 1 var2_2) {
            this(char2CharOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntryIterator
    extends MapIterator
    implements ObjectIterator<Char2CharMap.Entry> {
        private MapEntry entry;
        final Char2CharOpenHashMap this$0;

        private EntryIterator(Char2CharOpenHashMap char2CharOpenHashMap) {
            this.this$0 = char2CharOpenHashMap;
            super(char2CharOpenHashMap, null);
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

        EntryIterator(Char2CharOpenHashMap char2CharOpenHashMap, 1 var2_2) {
            this(char2CharOpenHashMap);
        }
    }

    private class MapIterator {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        CharArrayList wrapped;
        final Char2CharOpenHashMap this$0;

        private MapIterator(Char2CharOpenHashMap char2CharOpenHashMap) {
            this.this$0 = char2CharOpenHashMap;
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
                int n = HashCommon.mix(c) & this.this$0.mask;
                while (c != cArray[n]) {
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
                        return;
                    }
                    int n3 = HashCommon.mix(c) & this.this$0.mask;
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

        MapIterator(Char2CharOpenHashMap char2CharOpenHashMap, 1 var2_2) {
            this(char2CharOpenHashMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Char2CharMap.Entry,
    Map.Entry<Character, Character> {
        int index;
        final Char2CharOpenHashMap this$0;

        MapEntry(Char2CharOpenHashMap char2CharOpenHashMap, int n) {
            this.this$0 = char2CharOpenHashMap;
            this.index = n;
        }

        MapEntry(Char2CharOpenHashMap char2CharOpenHashMap) {
            this.this$0 = char2CharOpenHashMap;
        }

        @Override
        public char getCharKey() {
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
        public Character getKey() {
            return Character.valueOf(this.this$0.key[this.index]);
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
            return this.this$0.key[this.index] == ((Character)entry.getKey()).charValue() && this.this$0.value[this.index] == ((Character)entry.getValue()).charValue();
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

