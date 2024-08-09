/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import java.util.Spliterator;
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

    public ObjectOpenHashSet(int n, float f) {
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
        this.key = new Object[this.n + 1];
    }

    public ObjectOpenHashSet(int n) {
        this(n, 0.75f);
    }

    public ObjectOpenHashSet() {
        this(16, 0.75f);
    }

    public ObjectOpenHashSet(Collection<? extends K> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public ObjectOpenHashSet(Collection<? extends K> collection) {
        this(collection, 0.75f);
    }

    public ObjectOpenHashSet(ObjectCollection<? extends K> objectCollection, float f) {
        this(objectCollection.size(), f);
        this.addAll(objectCollection);
    }

    public ObjectOpenHashSet(ObjectCollection<? extends K> objectCollection) {
        this(objectCollection, 0.75f);
    }

    public ObjectOpenHashSet(Iterator<? extends K> iterator2, float f) {
        this(16, f);
        while (iterator2.hasNext()) {
            this.add(iterator2.next());
        }
    }

    public ObjectOpenHashSet(Iterator<? extends K> iterator2) {
        this(iterator2, 0.75f);
    }

    public ObjectOpenHashSet(K[] KArray, int n, int n2, float f) {
        this(n2 < 0 ? 0 : n2, f);
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(KArray[n + i]);
        }
    }

    public ObjectOpenHashSet(K[] KArray, int n, int n2) {
        this(KArray, n, n2, 0.75f);
    }

    public ObjectOpenHashSet(K[] KArray, float f) {
        this(KArray, 0, KArray.length, f);
    }

    public ObjectOpenHashSet(K[] KArray) {
        this(KArray, 0.75f);
    }

    public static <K> ObjectOpenHashSet<K> of() {
        return new ObjectOpenHashSet<K>();
    }

    public static <K> ObjectOpenHashSet<K> of(K k) {
        ObjectOpenHashSet<K> objectOpenHashSet = new ObjectOpenHashSet<K>(1, 0.75f);
        objectOpenHashSet.add(k);
        return objectOpenHashSet;
    }

    public static <K> ObjectOpenHashSet<K> of(K k, K k2) {
        ObjectOpenHashSet<K> objectOpenHashSet = new ObjectOpenHashSet<K>(2, 0.75f);
        objectOpenHashSet.add(k);
        if (!objectOpenHashSet.add(k2)) {
            throw new IllegalArgumentException("Duplicate element: " + k2);
        }
        return objectOpenHashSet;
    }

    public static <K> ObjectOpenHashSet<K> of(K k, K k2, K k3) {
        ObjectOpenHashSet<K> objectOpenHashSet = new ObjectOpenHashSet<K>(3, 0.75f);
        objectOpenHashSet.add(k);
        if (!objectOpenHashSet.add(k2)) {
            throw new IllegalArgumentException("Duplicate element: " + k2);
        }
        if (!objectOpenHashSet.add(k3)) {
            throw new IllegalArgumentException("Duplicate element: " + k3);
        }
        return objectOpenHashSet;
    }

    @SafeVarargs
    public static <K> ObjectOpenHashSet<K> of(K ... KArray) {
        ObjectOpenHashSet<K> objectOpenHashSet = new ObjectOpenHashSet<K>(KArray.length, 0.75f);
        for (K k : KArray) {
            if (objectOpenHashSet.add(k)) continue;
            throw new IllegalArgumentException("Duplicate element " + k);
        }
        return objectOpenHashSet;
    }

    private ObjectOpenHashSet<K> combine(ObjectOpenHashSet<? extends K> objectOpenHashSet) {
        this.addAll((Collection<? extends K>)objectOpenHashSet);
        return this;
    }

    public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSet() {
        return TO_SET_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSetWithExpectedSize(int n) {
        if (n <= 16) {
            return ObjectOpenHashSet.toSet();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(n, ObjectOpenHashSet::lambda$toSetWithExpectedSize$0), ObjectOpenHashSet::add, ObjectOpenHashSet::combine, Collector.Characteristics.UNORDERED);
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
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

    @Override
    public boolean addAll(Collection<? extends K> collection) {
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        } else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(K k) {
        if (k == null) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            K[] KArray = this.key;
            int n = HashCommon.mix(k.hashCode()) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (k2.equals(k)) {
                    return true;
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (!k2.equals(k)) continue;
                    return true;
                }
            }
            KArray[n] = k;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return false;
    }

    public K addOrGet(K k) {
        if (k == null) {
            if (this.containsNull) {
                return this.key[this.n];
            }
            this.containsNull = true;
        } else {
            K[] KArray = this.key;
            int n = HashCommon.mix(k.hashCode()) & this.mask;
            K k2 = KArray[n];
            if (k2 != null) {
                if (k2.equals(k)) {
                    return k2;
                }
                while ((k2 = KArray[n = n + 1 & this.mask]) != null) {
                    if (!k2.equals(k)) continue;
                    return k2;
                }
            }
            KArray[n] = k;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return k;
    }

    protected final void shiftKeys(int n) {
        K[] KArray = this.key;
        while (true) {
            K k;
            int n2 = n;
            n = n2 + 1 & this.mask;
            while (true) {
                if ((k = KArray[n]) == null) {
                    KArray[n2] = null;
                    return;
                }
                int n3 = HashCommon.mix(k.hashCode()) & this.mask;
                if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                n = n + 1 & this.mask;
            }
            KArray[n2] = k;
        }
    }

    private boolean removeEntry(int n) {
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.n] = null;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return false;
    }

    @Override
    public boolean remove(Object object) {
        if (object == null) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object.equals(k)) {
            return this.removeEntry(n);
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k));
        return this.removeEntry(n);
    }

    @Override
    public boolean contains(Object object) {
        if (object == null) {
            return this.containsNull;
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return true;
        }
        if (object.equals(k)) {
            return false;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return true;
        } while (!object.equals(k));
        return false;
    }

    public K get(Object object) {
        if (object == null) {
            return this.key[this.n];
        }
        K[] KArray = this.key;
        int n = HashCommon.mix(object.hashCode()) & this.mask;
        K k = KArray[n];
        if (k == null) {
            return null;
        }
        if (object.equals(k)) {
            return k;
        }
        do {
            if ((k = KArray[n = n + 1 & this.mask]) != null) continue;
            return null;
        } while (!object.equals(k));
        return k;
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
        return this.size == 0;
    }

    @Override
    public ObjectIterator<K> iterator() {
        return new SetIterator(this, null);
    }

    @Override
    public ObjectSpliterator<K> spliterator() {
        return new SetSpliterator(this);
    }

    @Override
    public void forEach(Consumer<? super K> consumer) {
        if (this.containsNull) {
            consumer.accept(this.key[this.n]);
        }
        K[] KArray = this.key;
        int n = this.n;
        while (n-- != 0) {
            if (KArray[n] == null) continue;
            consumer.accept(KArray[n]);
        }
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
        K[] KArray = this.key;
        int n2 = n - 1;
        Object[] objectArray = new Object[n + 1];
        int n3 = this.n;
        int n4 = this.realSize();
        while (n4-- != 0) {
            while (KArray[--n3] == null) {
            }
            int n5 = HashCommon.mix(KArray[n3].hashCode()) & n2;
            if (objectArray[n5] != null) {
                while (objectArray[n5 = n5 + 1 & n2] != null) {
                }
            }
            objectArray[n5] = KArray[n3];
        }
        this.n = n;
        this.mask = n2;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = objectArray;
    }

    public ObjectOpenHashSet<K> clone() {
        ObjectOpenHashSet objectOpenHashSet;
        try {
            objectOpenHashSet = (ObjectOpenHashSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        objectOpenHashSet.key = (Object[])this.key.clone();
        objectOpenHashSet.containsNull = this.containsNull;
        return objectOpenHashSet;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.realSize();
        int n3 = 0;
        while (n2-- != 0) {
            while (this.key[n3] == null) {
                ++n3;
            }
            if (this != this.key[n3]) {
                n += this.key[n3].hashCode();
            }
            ++n3;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Iterator iterator2 = this.iterator();
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        while (n-- != 0) {
            objectOutputStream.writeObject(iterator2.next());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        this.key = new Object[this.n + 1];
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            int n2;
            Object object = objectInputStream.readObject();
            if (object == null) {
                n2 = this.n;
                this.containsNull = true;
            } else {
                n2 = HashCommon.mix(object.hashCode()) & this.mask;
                if (objectArray[n2] != null) {
                    while (objectArray[n2 = n2 + 1 & this.mask] != null) {
                    }
                }
            }
            objectArray[n2] = object;
        }
    }

    private void checkTable() {
    }

    @Override
    public Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private static ObjectOpenHashSet lambda$toSetWithExpectedSize$0(int n) {
        return n <= 16 ? new ObjectOpenHashSet() : new ObjectOpenHashSet(n);
    }

    static int access$100(ObjectOpenHashSet objectOpenHashSet) {
        return objectOpenHashSet.realSize();
    }

    private final class SetIterator
    implements ObjectIterator<K> {
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        ObjectArrayList<K> wrapped;
        final ObjectOpenHashSet this$0;

        private SetIterator(ObjectOpenHashSet objectOpenHashSet) {
            this.this$0 = objectOpenHashSet;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNull = this.this$0.containsNull;
        }

        @Override
        public boolean hasNext() {
            return this.c != 0;
        }

        @Override
        public K next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            K[] KArray = this.this$0.key;
            do {
                if (--this.pos >= 0) continue;
                this.last = Integer.MIN_VALUE;
                return this.wrapped.get(-this.pos - 1);
            } while (KArray[this.pos] == null);
            this.last = this.pos;
            return KArray[this.last];
        }

        private final void shiftKeys(int n) {
            K[] KArray = this.this$0.key;
            while (true) {
                Object k;
                int n2 = n;
                n = n2 + 1 & this.this$0.mask;
                while (true) {
                    if ((k = KArray[n]) == null) {
                        KArray[n2] = null;
                        return;
                    }
                    int n3 = HashCommon.mix(k.hashCode()) & this.this$0.mask;
                    if (n2 <= n ? n2 >= n3 || n3 > n : n2 >= n3 && n3 > n) break;
                    n = n + 1 & this.this$0.mask;
                }
                if (n < n2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList(2);
                    }
                    this.wrapped.add(KArray[n]);
                }
                KArray[n2] = k;
            }
        }

        @Override
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = null;
            } else if (this.pos >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.set(-this.pos - 1, (Object)null));
                this.last = -1;
                return;
            }
            --this.this$0.size;
            this.last = -1;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            K[] KArray = this.this$0.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                consumer.accept(KArray[this.this$0.n]);
                --this.c;
            }
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    consumer.accept(this.wrapped.get(-this.pos - 1));
                    --this.c;
                    continue;
                }
                if (KArray[this.pos] == null) continue;
                this.last = this.pos;
                consumer.accept(KArray[this.last]);
                --this.c;
            }
        }

        SetIterator(ObjectOpenHashSet objectOpenHashSet, 1 var2_2) {
            this(objectOpenHashSet);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class SetSpliterator
    implements ObjectSpliterator<K> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        int pos;
        int max;
        int c;
        boolean mustReturnNull;
        boolean hasSplit;
        final ObjectOpenHashSet this$0;

        SetSpliterator(ObjectOpenHashSet objectOpenHashSet) {
            this.this$0 = objectOpenHashSet;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNull;
            this.hasSplit = false;
        }

        SetSpliterator(ObjectOpenHashSet objectOpenHashSet, int n, int n2, boolean bl, boolean bl2) {
            this.this$0 = objectOpenHashSet;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNull;
            this.hasSplit = false;
            this.pos = n;
            this.max = n2;
            this.mustReturnNull = bl;
            this.hasSplit = bl2;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                consumer.accept(this.this$0.key[this.this$0.n]);
                return false;
            }
            K[] KArray = this.this$0.key;
            while (this.pos < this.max) {
                if (KArray[this.pos] != null) {
                    ++this.c;
                    consumer.accept(KArray[this.pos++]);
                    return false;
                }
                ++this.pos;
            }
            return true;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            K[] KArray = this.this$0.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                consumer.accept(KArray[this.this$0.n]);
                ++this.c;
            }
            while (this.pos < this.max) {
                if (KArray[this.pos] != null) {
                    consumer.accept(KArray[this.pos]);
                    ++this.c;
                }
                ++this.pos;
            }
        }

        @Override
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        @Override
        public long estimateSize() {
            if (!this.hasSplit) {
                return this.this$0.size - this.c;
            }
            return Math.min((long)(this.this$0.size - this.c), (long)((double)ObjectOpenHashSet.access$100(this.this$0) / (double)this.this$0.n * (double)(this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
        }

        @Override
        public SetSpliterator trySplit() {
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
            SetSpliterator setSpliterator = new SetSpliterator(this.this$0, n3, n4, this.mustReturnNull, true);
            this.pos = n2;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return setSpliterator;
        }

        @Override
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
            K[] KArray = this.this$0.key;
            while (this.pos < this.max && l > 0L) {
                if (KArray[this.pos++] == null) continue;
                ++l2;
                --l;
            }
            return l2;
        }

        @Override
        public ObjectSpliterator trySplit() {
            return this.trySplit();
        }

        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
}

