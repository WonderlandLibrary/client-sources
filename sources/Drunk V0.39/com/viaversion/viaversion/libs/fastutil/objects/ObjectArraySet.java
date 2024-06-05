/*
 * Decompiled with CFR 0.152.
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
    private transient Object[] a;
    private int size;

    public ObjectArraySet(Object[] a) {
        this.a = a;
        this.size = a.length;
    }

    public ObjectArraySet() {
        this.a = ObjectArrays.EMPTY_ARRAY;
    }

    public ObjectArraySet(int capacity) {
        this.a = new Object[capacity];
    }

    public ObjectArraySet(ObjectCollection<K> c) {
        this(c.size());
        this.addAll(c);
    }

    public ObjectArraySet(Collection<? extends K> c) {
        this(c.size());
        this.addAll(c);
    }

    public ObjectArraySet(ObjectSet<K> c) {
        this(c.size());
        int i = 0;
        Iterator iterator = c.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.size = i;
                return;
            }
            Object x = iterator.next();
            this.a[i] = x;
            ++i;
        }
    }

    public ObjectArraySet(Set<? extends K> c) {
        this(c.size());
        int i = 0;
        Iterator<K> iterator = c.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.size = i;
                return;
            }
            K x = iterator.next();
            this.a[i] = x;
            ++i;
        }
    }

    public ObjectArraySet(Object[] a, int size) {
        this.a = a;
        this.size = size;
        if (size <= a.length) return;
        throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
    }

    public static <K> ObjectArraySet<K> of() {
        return ObjectArraySet.ofUnchecked();
    }

    public static <K> ObjectArraySet<K> of(K e) {
        return ObjectArraySet.ofUnchecked(e);
    }

    @SafeVarargs
    public static <K> ObjectArraySet<K> of(K ... a) {
        if (a.length == 2) {
            if (!Objects.equals(a[0], a[1])) return ObjectArraySet.ofUnchecked(a);
            throw new IllegalArgumentException("Duplicate element: " + a[1]);
        }
        if (a.length <= 2) return ObjectArraySet.ofUnchecked(a);
        ObjectOpenHashSet.of(a);
        return ObjectArraySet.ofUnchecked(a);
    }

    public static <K> ObjectArraySet<K> ofUnchecked() {
        return new ObjectArraySet<K>();
    }

    @SafeVarargs
    public static <K> ObjectArraySet<K> ofUnchecked(K ... a) {
        return new ObjectArraySet<K>(a);
    }

    private int findKey(Object o) {
        int i = this.size;
        do {
            if (i-- == 0) return -1;
        } while (!Objects.equals(this.a[i], o));
        return i;
    }

    @Override
    public ObjectIterator<K> iterator() {
        return new ObjectIterator<K>(){
            int next = 0;

            @Override
            public boolean hasNext() {
                if (this.next >= ObjectArraySet.this.size) return false;
                return true;
            }

            @Override
            public K next() {
                if (this.hasNext()) return ObjectArraySet.this.a[this.next++];
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                int tail = ObjectArraySet.this.size-- - this.next--;
                System.arraycopy(ObjectArraySet.this.a, this.next + 1, ObjectArraySet.this.a, this.next, tail);
                ((ObjectArraySet)ObjectArraySet.this).a[((ObjectArraySet)ObjectArraySet.this).size] = null;
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int remaining = ObjectArraySet.this.size - this.next;
                if (n < remaining) {
                    this.next += n;
                    return n;
                }
                n = remaining;
                this.next = ObjectArraySet.this.size;
                return n;
            }
        };
    }

    @Override
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator();
    }

    @Override
    public boolean contains(Object k) {
        if (this.findKey(k) == -1) return false;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(Object k) {
        int pos = this.findKey(k);
        if (pos == -1) {
            return false;
        }
        int tail = this.size - pos - 1;
        int i = 0;
        while (true) {
            if (i >= tail) {
                --this.size;
                this.a[this.size] = null;
                return true;
            }
            this.a[pos + i] = this.a[pos + i + 1];
            ++i;
        }
    }

    @Override
    public boolean add(K k) {
        int pos = this.findKey(k);
        if (pos != -1) {
            return false;
        }
        if (this.size == this.a.length) {
            Object[] b = new Object[this.size == 0 ? 2 : this.size * 2];
            int i = this.size;
            while (i-- != 0) {
                b[i] = this.a[i];
            }
            this.a = b;
        }
        this.a[this.size++] = k;
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(this.a, 0, this.size, null);
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        if (this.size != 0) return false;
        return true;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.a, this.size, Object[].class);
    }

    @Override
    public <K> K[] toArray(K[] a) {
        if (a == null) {
            a = new Object[this.size];
        } else if (a.length < this.size) {
            a = (Object[])Array.newInstance(a.getClass().getComponentType(), this.size);
        }
        System.arraycopy(this.a, 0, a, 0, this.size);
        if (a.length <= this.size) return a;
        a[this.size] = null;
        return a;
    }

    public ObjectArraySet<K> clone() {
        ObjectArraySet c;
        try {
            c = (ObjectArraySet)super.clone();
        }
        catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.a = (Object[])this.a.clone();
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        int i = 0;
        while (i < this.size) {
            s.writeObject(this.a[i]);
            ++i;
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.a = new Object[this.size];
        int i = 0;
        while (i < this.size) {
            this.a[i] = s.readObject();
            ++i;
        }
    }

    private final class Spliterator
    implements ObjectSpliterator<K> {
        boolean hasSplit = false;
        int pos;
        int max;

        public Spliterator() {
            this(0, objectArraySet.size, false);
        }

        private Spliterator(int pos, int max, boolean hasSplit) {
            assert (pos <= max) : "pos " + pos + " must be <= max " + max;
            this.pos = pos;
            this.max = max;
            this.hasSplit = hasSplit;
        }

        private int getWorkingMax() {
            int n;
            if (this.hasSplit) {
                n = this.max;
                return n;
            }
            n = ObjectArraySet.this.size;
            return n;
        }

        @Override
        public int characteristics() {
            return 16465;
        }

        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> action) {
            if (this.pos >= this.getWorkingMax()) {
                return false;
            }
            action.accept(ObjectArraySet.this.a[this.pos++]);
            return true;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> action) {
            int max = this.getWorkingMax();
            while (this.pos < max) {
                action.accept(ObjectArraySet.this.a[this.pos]);
                ++this.pos;
            }
        }

        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            int max = this.getWorkingMax();
            if (this.pos >= max) {
                return 0L;
            }
            int remaining = max - this.pos;
            if (n < (long)remaining) {
                this.pos = SafeMath.safeLongToInt((long)this.pos + n);
                return n;
            }
            n = remaining;
            this.pos = max;
            return n;
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            int myNewPos;
            int max = this.getWorkingMax();
            int retLen = max - this.pos >> 1;
            if (retLen <= 1) {
                return null;
            }
            this.max = max;
            int retMax = myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            this.hasSplit = true;
            return new Spliterator(oldPos, retMax, true);
        }
    }
}

