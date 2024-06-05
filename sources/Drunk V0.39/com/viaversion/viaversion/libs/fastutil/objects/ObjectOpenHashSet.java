/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class ObjectOpenHashSet<K>
extends AbstractObjectSet<K>
implements Serializable,
Cloneable,
Hash {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    private static final Collector<Object, ?, ObjectOpenHashSet<Object>> TO_SET_COLLECTOR = Collector.of(ObjectOpenHashSet::new, ObjectOpenHashSet::add, ObjectOpenHashSet::combine, Collector.Characteristics.UNORDERED);

    public ObjectOpenHashSet(int expected, float f) {
        if (f <= 0.0f) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        if (f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.arraySize(expected, f);
        this.mask = this.n - 1;
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = new Object[this.n + 1];
    }

    public ObjectOpenHashSet(int expected) {
        this(expected, 0.75f);
    }

    public ObjectOpenHashSet() {
        this(16, 0.75f);
    }

    public ObjectOpenHashSet(Collection<? extends K> c, float f) {
        this(c.size(), f);
        this.addAll(c);
    }

    public ObjectOpenHashSet(Collection<? extends K> c) {
        this(c, 0.75f);
    }

    public ObjectOpenHashSet(ObjectCollection<? extends K> c, float f) {
        this(c.size(), f);
        this.addAll(c);
    }

    public ObjectOpenHashSet(ObjectCollection<? extends K> c) {
        this(c, 0.75f);
    }

    public ObjectOpenHashSet(Iterator<? extends K> i, float f) {
        this(16, f);
        while (i.hasNext()) {
            this.add(i.next());
        }
    }

    public ObjectOpenHashSet(Iterator<? extends K> i) {
        this(i, 0.75f);
    }

    public ObjectOpenHashSet(K[] a, int offset, int length, float f) {
        this(length < 0 ? 0 : length, f);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        int i = 0;
        while (i < length) {
            this.add(a[offset + i]);
            ++i;
        }
    }

    public ObjectOpenHashSet(K[] a, int offset, int length) {
        this(a, offset, length, 0.75f);
    }

    public ObjectOpenHashSet(K[] a, float f) {
        this(a, 0, a.length, f);
    }

    public ObjectOpenHashSet(K[] a) {
        this(a, 0.75f);
    }

    public static <K> ObjectOpenHashSet<K> of() {
        return new ObjectOpenHashSet<K>();
    }

    public static <K> ObjectOpenHashSet<K> of(K e) {
        ObjectOpenHashSet<K> result = new ObjectOpenHashSet<K>(1, 0.75f);
        result.add(e);
        return result;
    }

    public static <K> ObjectOpenHashSet<K> of(K e0, K e1) {
        ObjectOpenHashSet<K> result = new ObjectOpenHashSet<K>(2, 0.75f);
        result.add(e0);
        if (result.add(e1)) return result;
        throw new IllegalArgumentException("Duplicate element: " + e1);
    }

    public static <K> ObjectOpenHashSet<K> of(K e0, K e1, K e2) {
        ObjectOpenHashSet<K> result = new ObjectOpenHashSet<K>(3, 0.75f);
        result.add(e0);
        if (!result.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        if (result.add(e2)) return result;
        throw new IllegalArgumentException("Duplicate element: " + e2);
    }

    @SafeVarargs
    public static <K> ObjectOpenHashSet<K> of(K ... a) {
        ObjectOpenHashSet<K> result = new ObjectOpenHashSet<K>(a.length, 0.75f);
        K[] KArray = a;
        int n = KArray.length;
        int n2 = 0;
        while (n2 < n) {
            K element = KArray[n2];
            if (!result.add(element)) {
                throw new IllegalArgumentException("Duplicate element " + element);
            }
            ++n2;
        }
        return result;
    }

    private ObjectOpenHashSet<K> combine(ObjectOpenHashSet<? extends K> toAddFrom) {
        this.addAll((Collection<? extends K>)toAddFrom);
        return this;
    }

    public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSet() {
        return TO_SET_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSetWithExpectedSize(int expectedSize) {
        if (expectedSize > 16) return Collector.of(new ObjectCollections.SizeDecreasingSupplier(expectedSize, size -> {
            ObjectOpenHashSet objectOpenHashSet;
            if (size <= 16) {
                objectOpenHashSet = new ObjectOpenHashSet();
                return objectOpenHashSet;
            }
            objectOpenHashSet = new ObjectOpenHashSet(size);
            return objectOpenHashSet;
        }), ObjectOpenHashSet::add, ObjectOpenHashSet::combine, Collector.Characteristics.UNORDERED);
        return ObjectOpenHashSet.toSet();
    }

    private int realSize() {
        int n;
        if (this.containsNull) {
            n = this.size - 1;
            return n;
        }
        n = this.size;
        return n;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f);
        if (needed <= this.n) return;
        this.rehash(needed);
    }

    private void tryCapacity(long capacity) {
        int needed = (int)Math.min(0x40000000L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)capacity / this.f))));
        if (needed <= this.n) return;
        this.rehash(needed);
    }

    @Override
    public boolean addAll(Collection<? extends K> c) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(c.size());
            return super.addAll(c);
        }
        this.tryCapacity(this.size() + c.size());
        return super.addAll(c);
    }

    @Override
    public boolean add(K k) {
        if (k == null) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        } else {
            K[] key = this.key;
            int pos = HashCommon.mix(k.hashCode()) & this.mask;
            K curr = key[pos];
            if (curr != null) {
                if (curr.equals(k)) {
                    return false;
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != null) {
                    if (!curr.equals(k)) continue;
                    return false;
                }
            }
            key[pos] = k;
        }
        if (this.size++ < this.maxFill) return true;
        this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        return true;
    }

    public K addOrGet(K k) {
        if (k == null) {
            if (this.containsNull) {
                return this.key[this.n];
            }
            this.containsNull = true;
        } else {
            K[] key = this.key;
            int pos = HashCommon.mix(k.hashCode()) & this.mask;
            K curr = key[pos];
            if (curr != null) {
                if (curr.equals(k)) {
                    return curr;
                }
                while ((curr = key[pos = pos + 1 & this.mask]) != null) {
                    if (!curr.equals(k)) continue;
                    return curr;
                }
            }
            key[pos] = k;
        }
        if (this.size++ < this.maxFill) return k;
        this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        return k;
    }

    protected final void shiftKeys(int pos) {
        K[] key = this.key;
        while (true) {
            K curr;
            int last = pos;
            pos = last + 1 & this.mask;
            while (true) {
                if ((curr = key[pos]) == null) {
                    key[last] = null;
                    return;
                }
                int slot = HashCommon.mix(curr.hashCode()) & this.mask;
                if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                pos = pos + 1 & this.mask;
            }
            key[last] = curr;
        }
    }

    private boolean removeEntry(int pos) {
        --this.size;
        this.shiftKeys(pos);
        if (this.n <= this.minN) return true;
        if (this.size >= this.maxFill / 4) return true;
        if (this.n <= 16) return true;
        this.rehash(this.n / 2);
        return true;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.n] = null;
        --this.size;
        if (this.n <= this.minN) return true;
        if (this.size >= this.maxFill / 4) return true;
        if (this.n <= 16) return true;
        this.rehash(this.n / 2);
        return true;
    }

    @Override
    public boolean remove(Object k) {
        if (k == null) {
            if (!this.containsNull) return false;
            return this.removeNullEntry();
        }
        K[] key = this.key;
        int pos = HashCommon.mix(k.hashCode()) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return false;
        }
        if (k.equals(curr)) {
            return this.removeEntry(pos);
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return false;
        } while (!k.equals(curr));
        return this.removeEntry(pos);
    }

    @Override
    public boolean contains(Object k) {
        if (k == null) {
            return this.containsNull;
        }
        K[] key = this.key;
        int pos = HashCommon.mix(k.hashCode()) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return false;
        }
        if (k.equals(curr)) {
            return true;
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return false;
        } while (!k.equals(curr));
        return true;
    }

    public K get(Object k) {
        if (k == null) {
            return this.key[this.n];
        }
        K[] key = this.key;
        int pos = HashCommon.mix(k.hashCode()) & this.mask;
        K curr = key[pos];
        if (curr == null) {
            return null;
        }
        if (k.equals(curr)) {
            return curr;
        }
        do {
            if ((curr = key[pos = pos + 1 & this.mask]) != null) continue;
            return null;
        } while (!k.equals(curr));
        return curr;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, null);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        if (this.size != 0) return false;
        return true;
    }

    @Override
    public ObjectIterator<K> iterator() {
        return new SetIterator();
    }

    @Override
    public ObjectSpliterator<K> spliterator() {
        return new SetSpliterator();
    }

    @Override
    public void forEach(Consumer<? super K> action) {
        if (this.containsNull) {
            action.accept(this.key[this.n]);
        }
        K[] key = this.key;
        int pos = this.n;
        while (pos-- != 0) {
            if (key[pos] == null) continue;
            action.accept(key[pos]);
        }
    }

    public boolean trim() {
        return this.trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.f));
        if (l >= this.n) return true;
        if (this.size > HashCommon.maxFill(l, this.f)) {
            return true;
        }
        try {
            this.rehash(l);
            return true;
        }
        catch (OutOfMemoryError cantDoIt) {
            return false;
        }
    }

    protected void rehash(int newN) {
        K[] key = this.key;
        int mask = newN - 1;
        Object[] newKey = new Object[newN + 1];
        int i = this.n;
        int j = this.realSize();
        while (true) {
            if (j-- == 0) {
                this.n = newN;
                this.mask = mask;
                this.maxFill = HashCommon.maxFill(this.n, this.f);
                this.key = newKey;
                return;
            }
            while (key[--i] == null) {
            }
            int pos = HashCommon.mix(key[i].hashCode()) & mask;
            if (newKey[pos] != null) {
                while (newKey[pos = pos + 1 & mask] != null) {
                }
            }
            newKey[pos] = key[i];
        }
    }

    public ObjectOpenHashSet<K> clone() {
        ObjectOpenHashSet c;
        try {
            c = (ObjectOpenHashSet)super.clone();
        }
        catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (Object[])this.key.clone();
        c.containsNull = this.containsNull;
        return c;
    }

    @Override
    public int hashCode() {
        int h = 0;
        int j = this.realSize();
        int i = 0;
        while (j-- != 0) {
            while (this.key[i] == null) {
                ++i;
            }
            if (this != this.key[i]) {
                h += this.key[i].hashCode();
            }
            ++i;
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        Iterator i = this.iterator();
        s.defaultWriteObject();
        int j = this.size;
        while (j-- != 0) {
            s.writeObject(i.next());
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            int pos;
            Object k = s.readObject();
            if (k == null) {
                pos = this.n;
                this.containsNull = true;
            } else {
                pos = HashCommon.mix(k.hashCode()) & this.mask;
                if (key[pos] != null) {
                    while (key[pos = pos + 1 & this.mask] != null) {
                    }
                }
            }
            key[pos] = k;
        }
    }

    private void checkTable() {
    }

    private final class SetIterator
    implements ObjectIterator<K> {
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        ObjectArrayList<K> wrapped;

        private SetIterator() {
            this.pos = ObjectOpenHashSet.this.n;
            this.last = -1;
            this.c = ObjectOpenHashSet.this.size;
            this.mustReturnNull = ObjectOpenHashSet.this.containsNull;
        }

        @Override
        public boolean hasNext() {
            if (this.c == 0) return false;
            return true;
        }

        @Override
        public K next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = ObjectOpenHashSet.this.n;
                return ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n];
            }
            K[] key = ObjectOpenHashSet.this.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                return this.wrapped.get(-this.pos - 1);
            } while (key[this.pos] == null);
            this.last = this.pos;
            return key[this.last];
        }

        private final void shiftKeys(int pos) {
            K[] key = ObjectOpenHashSet.this.key;
            while (true) {
                Object curr;
                int last = pos;
                pos = last + 1 & ObjectOpenHashSet.this.mask;
                while (true) {
                    if ((curr = key[pos]) == null) {
                        key[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(curr.hashCode()) & ObjectOpenHashSet.this.mask;
                    if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) break;
                    pos = pos + 1 & ObjectOpenHashSet.this.mask;
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
            }
        }

        @Override
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == ObjectOpenHashSet.this.n) {
                ObjectOpenHashSet.this.containsNull = false;
                ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n] = null;
            } else {
                if (this.pos < 0) {
                    ObjectOpenHashSet.this.remove(this.wrapped.set(-this.pos - 1, (Object)null));
                    this.last = -1;
                    return;
                }
                this.shiftKeys(this.last);
            }
            --ObjectOpenHashSet.this.size;
            this.last = -1;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> action) {
            K[] key = ObjectOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = ObjectOpenHashSet.this.n;
                action.accept(key[ObjectOpenHashSet.this.n]);
                --this.c;
            }
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    action.accept(this.wrapped.get(-this.pos - 1));
                    --this.c;
                    continue;
                }
                if (key[this.pos] == null) continue;
                this.last = this.pos;
                action.accept(key[this.last]);
                --this.c;
            }
        }
    }

    private final class SetSpliterator
    implements ObjectSpliterator<K> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        int pos = 0;
        int max;
        int c;
        boolean mustReturnNull;
        boolean hasSplit;

        SetSpliterator() {
            this.max = ObjectOpenHashSet.this.n;
            this.c = 0;
            this.mustReturnNull = ObjectOpenHashSet.this.containsNull;
            this.hasSplit = false;
        }

        SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            this.max = ObjectOpenHashSet.this.n;
            this.c = 0;
            this.mustReturnNull = ObjectOpenHashSet.this.containsNull;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> action) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                action.accept(ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n]);
                return true;
            }
            K[] key = ObjectOpenHashSet.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    ++this.c;
                    action.accept(key[this.pos++]);
                    return true;
                }
                ++this.pos;
            }
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> action) {
            K[] key = ObjectOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                action.accept(key[ObjectOpenHashSet.this.n]);
                ++this.c;
            }
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    action.accept(key[this.pos]);
                    ++this.c;
                }
                ++this.pos;
            }
        }

        @Override
        public int characteristics() {
            if (!this.hasSplit) return 65;
            return 1;
        }

        @Override
        public long estimateSize() {
            int n;
            if (!this.hasSplit) {
                return ObjectOpenHashSet.this.size - this.c;
            }
            long l = ObjectOpenHashSet.this.size - this.c;
            long l2 = (long)((double)ObjectOpenHashSet.this.realSize() / (double)ObjectOpenHashSet.this.n * (double)(this.max - this.pos));
            if (this.mustReturnNull) {
                n = 1;
                return Math.min(l, l2 + (long)n);
            }
            n = 0;
            return Math.min(l, l2 + (long)n);
        }

        @Override
        public SetSpliterator trySplit() {
            if (this.pos >= this.max - 1) {
                return null;
            }
            int retLen = this.max - this.pos >> 1;
            if (retLen <= 1) {
                return null;
            }
            int myNewPos = this.pos + retLen;
            int retPos = this.pos;
            int retMax = myNewPos;
            SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
            this.pos = myNewPos;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return split;
        }

        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0L) {
                return 0L;
            }
            long skipped = 0L;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++skipped;
                --n;
            }
            K[] key = ObjectOpenHashSet.this.key;
            while (this.pos < this.max) {
                if (n <= 0L) return skipped;
                if (key[this.pos++] == null) continue;
                ++skipped;
                --n;
            }
            return skipped;
        }
    }
}

