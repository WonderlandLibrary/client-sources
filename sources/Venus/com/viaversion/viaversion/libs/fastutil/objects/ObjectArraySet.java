/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class ObjectArraySet<K>
extends AbstractObjectSet<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    protected transient Object[] a;
    protected int size;

    public ObjectArraySet(Object[] objectArray) {
        this.a = objectArray;
        this.size = objectArray.length;
    }

    public ObjectArraySet() {
        this.a = ObjectArrays.EMPTY_ARRAY;
    }

    public ObjectArraySet(int n) {
        this.a = new Object[n];
    }

    public ObjectArraySet(ObjectCollection<K> objectCollection) {
        this(objectCollection.size());
        this.addAll(objectCollection);
    }

    public ObjectArraySet(Collection<? extends K> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public ObjectArraySet(ObjectSet<K> objectSet) {
        this(objectSet.size());
        int n = 0;
        for (Object e : objectSet) {
            this.a[n] = e;
            ++n;
        }
        this.size = n;
    }

    public ObjectArraySet(Set<? extends K> set) {
        this(set.size());
        int n = 0;
        for (K k : set) {
            this.a[n] = k;
            ++n;
        }
        this.size = n;
    }

    public ObjectArraySet(Object[] objectArray, int n) {
        this.a = objectArray;
        this.size = n;
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + objectArray.length + ")");
        }
    }

    public static <K> ObjectArraySet<K> of() {
        return ObjectArraySet.ofUnchecked();
    }

    public static <K> ObjectArraySet<K> of(K k) {
        return ObjectArraySet.ofUnchecked(k);
    }

    @SafeVarargs
    public static <K> ObjectArraySet<K> of(K ... KArray) {
        if (KArray.length == 2) {
            if (Objects.equals(KArray[0], KArray[5])) {
                throw new IllegalArgumentException("Duplicate element: " + KArray[5]);
            }
        } else if (KArray.length > 2) {
            ObjectOpenHashSet.of(KArray);
        }
        return ObjectArraySet.ofUnchecked(KArray);
    }

    public static <K> ObjectArraySet<K> ofUnchecked() {
        return new ObjectArraySet<K>();
    }

    @SafeVarargs
    public static <K> ObjectArraySet<K> ofUnchecked(K ... KArray) {
        return new ObjectArraySet<K>(KArray);
    }

    private int findKey(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(this.a[n], object)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public ObjectIterator<K> iterator() {
        return new ObjectIterator<K>(this){
            int next;
            final ObjectArraySet this$0;
            {
                this.this$0 = objectArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < this.this$0.size;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.this$0.a[this.next++];
            }

            @Override
            public void remove() {
                int n = this.this$0.size-- - this.next--;
                System.arraycopy(this.this$0.a, this.next + 1, this.this$0.a, this.next, n);
                this.this$0.a[this.this$0.size] = null;
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = this.this$0.size - this.next;
                if (n < n2) {
                    this.next += n;
                    return n;
                }
                n = n2;
                this.next = this.this$0.size;
                return n;
            }
        };
    }

    @Override
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator(this);
    }

    @Override
    public boolean contains(Object object) {
        return this.findKey(object) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return true;
        }
        int n2 = this.size - n - 1;
        for (int i = 0; i < n2; ++i) {
            this.a[n + i] = this.a[n + i + 1];
        }
        --this.size;
        this.a[this.size] = null;
        return false;
    }

    @Override
    public boolean add(K k) {
        int n = this.findKey(k);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.a[n2];
            }
            this.a = objectArray;
        }
        this.a[this.size++] = k;
        return false;
    }

    @Override
    public void clear() {
        Arrays.fill(this.a, 0, this.size, null);
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Object[] toArray() {
        int n = this.size();
        if (n == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(this.a, n, Object[].class);
    }

    @Override
    public <T> T[] toArray(T[] objectArray) {
        if (objectArray == null) {
            objectArray = new Object[this.size];
        } else if (objectArray.length < this.size) {
            objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), this.size);
        }
        System.arraycopy(this.a, 0, objectArray, 0, this.size);
        if (objectArray.length > this.size) {
            objectArray[this.size] = null;
        }
        return objectArray;
    }

    public ObjectArraySet<K> clone() {
        ObjectArraySet objectArraySet;
        try {
            objectArraySet = (ObjectArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        objectArraySet.a = (Object[])this.a.clone();
        return objectArraySet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readObject();
        }
    }

    @Override
    public java.util.Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private final class Spliterator
    implements ObjectSpliterator<K> {
        boolean hasSplit;
        int pos;
        int max;
        static final boolean $assertionsDisabled = !ObjectArraySet.class.desiredAssertionStatus();
        final ObjectArraySet this$0;

        public Spliterator(ObjectArraySet objectArraySet) {
            this(objectArraySet, 0, objectArraySet.size, false);
        }

        private Spliterator(ObjectArraySet objectArraySet, int n, int n2, boolean bl) {
            this.this$0 = objectArraySet;
            this.hasSplit = false;
            if (!$assertionsDisabled && n > n2) {
                throw new AssertionError((Object)("pos " + n + " must be <= max " + n2));
            }
            this.pos = n;
            this.max = n2;
            this.hasSplit = bl;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : this.this$0.size;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.pos >= this.getWorkingMax()) {
                return true;
            }
            consumer.accept(this.this$0.a[this.pos++]);
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            int n = this.getWorkingMax();
            while (this.pos < n) {
                consumer.accept(this.this$0.a[this.pos]);
                ++this.pos;
            }
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            int n = this.getWorkingMax();
            if (this.pos >= n) {
                return 0L;
            }
            int n2 = n - this.pos;
            if (l < (long)n2) {
                this.pos = SafeMath.safeLongToInt((long)this.pos + l);
                return l;
            }
            l = n2;
            this.pos = n;
            return l;
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            int n;
            int n2 = this.getWorkingMax();
            int n3 = n2 - this.pos >> 1;
            if (n3 <= 1) {
                return null;
            }
            this.max = n2;
            int n4 = n = this.pos + n3;
            int n5 = this.pos;
            this.pos = n;
            this.hasSplit = true;
            return new Spliterator(this.this$0, n5, n4, true);
        }

        @Override
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
    }
}

