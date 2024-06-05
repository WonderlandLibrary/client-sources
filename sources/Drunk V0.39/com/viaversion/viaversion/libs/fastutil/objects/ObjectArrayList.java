/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList$SubList.SubListSpliterator
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Arrays;
import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class ObjectArrayList<K>
extends AbstractObjectList<K>
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353131L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected final boolean wrapped;
    protected transient K[] a;
    protected int size;
    private static final Collector<Object, ?, ObjectArrayList<Object>> TO_LIST_COLLECTOR = Collector.of(ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine, new Collector.Characteristics[0]);

    private static final <K> K[] copyArraySafe(K[] a, int length) {
        if (length != 0) return java.util.Arrays.copyOf(a, length, Object[].class);
        return ObjectArrays.EMPTY_ARRAY;
    }

    private static final <K> K[] copyArrayFromSafe(ObjectArrayList<K> l) {
        return ObjectArrayList.copyArraySafe(l.a, l.size);
    }

    protected ObjectArrayList(K[] a, boolean wrapped) {
        this.a = a;
        this.wrapped = wrapped;
    }

    private void initArrayFromCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
        }
        if (capacity == 0) {
            this.a = ObjectArrays.EMPTY_ARRAY;
            return;
        }
        this.a = new Object[capacity];
    }

    public ObjectArrayList(int capacity) {
        this.initArrayFromCapacity(capacity);
        this.wrapped = false;
    }

    public ObjectArrayList() {
        this.a = ObjectArrays.DEFAULT_EMPTY_ARRAY;
        this.wrapped = false;
    }

    public ObjectArrayList(Collection<? extends K> c) {
        if (c instanceof ObjectArrayList) {
            this.a = ObjectArrayList.copyArrayFromSafe((ObjectArrayList)c);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(c.size());
            if (c instanceof ObjectList) {
                this.size = c.size();
                ((ObjectList)c).getElements(0, this.a, 0, this.size);
            } else {
                this.size = ObjectIterators.unwrap(c.iterator(), this.a);
            }
        }
        this.wrapped = false;
    }

    public ObjectArrayList(ObjectCollection<? extends K> c) {
        if (c instanceof ObjectArrayList) {
            this.a = ObjectArrayList.copyArrayFromSafe((ObjectArrayList)c);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(c.size());
            if (c instanceof ObjectList) {
                this.size = c.size();
                ((ObjectList)c).getElements(0, this.a, 0, this.size);
            } else {
                this.size = ObjectIterators.unwrap(c.iterator(), this.a);
            }
        }
        this.wrapped = false;
    }

    public ObjectArrayList(ObjectList<? extends K> l) {
        if (l instanceof ObjectArrayList) {
            this.a = ObjectArrayList.copyArrayFromSafe((ObjectArrayList)l);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(l.size());
            this.size = l.size();
            l.getElements(0, this.a, 0, this.size);
        }
        this.wrapped = false;
    }

    public ObjectArrayList(K[] a) {
        this(a, 0, a.length);
    }

    public ObjectArrayList(K[] a, int offset, int length) {
        this(length);
        System.arraycopy(a, offset, this.a, 0, length);
        this.size = length;
    }

    public ObjectArrayList(Iterator<? extends K> i) {
        this();
        while (i.hasNext()) {
            this.add(i.next());
        }
    }

    public ObjectArrayList(ObjectIterator<? extends K> i) {
        this();
        while (i.hasNext()) {
            this.add((K)i.next());
        }
    }

    public K[] elements() {
        return this.a;
    }

    public static <K> ObjectArrayList<K> wrap(K[] a, int length) {
        if (length > a.length) {
            throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
        }
        ObjectArrayList<K> l = new ObjectArrayList<K>(a, true);
        l.size = length;
        return l;
    }

    public static <K> ObjectArrayList<K> wrap(K[] a) {
        return ObjectArrayList.wrap(a, a.length);
    }

    public static <K> ObjectArrayList<K> of() {
        return new ObjectArrayList<K>();
    }

    @SafeVarargs
    public static <K> ObjectArrayList<K> of(K ... init) {
        return ObjectArrayList.wrap(init);
    }

    ObjectArrayList<K> combine(ObjectArrayList<? extends K> toAddFrom) {
        this.addAll(toAddFrom);
        return this;
    }

    public static <K> Collector<K, ?, ObjectArrayList<K>> toList() {
        return TO_LIST_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectArrayList<K>> toListWithExpectedSize(int expectedSize) {
        if (expectedSize > 10) return Collector.of(new ObjectCollections.SizeDecreasingSupplier(expectedSize, size -> {
            ObjectArrayList objectArrayList;
            if (size <= 10) {
                objectArrayList = new ObjectArrayList();
                return objectArrayList;
            }
            objectArrayList = new ObjectArrayList(size);
            return objectArrayList;
        }), ObjectArrayList::add, ObjectArrayList::combine, new Collector.Characteristics[0]);
        return ObjectArrayList.toList();
    }

    public void ensureCapacity(int capacity) {
        if (capacity <= this.a.length) return;
        if (this.a == ObjectArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10) {
            return;
        }
        if (this.wrapped) {
            this.a = ObjectArrays.ensureCapacity(this.a, capacity, this.size);
        } else if (capacity > this.a.length) {
            Object[] t = new Object[capacity];
            System.arraycopy(this.a, 0, t, 0, this.size);
            this.a = t;
        }
        if ($assertionsDisabled) return;
        if (this.size <= this.a.length) return;
        throw new AssertionError();
    }

    private void grow(int capacity) {
        if (capacity <= this.a.length) {
            return;
        }
        if (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            capacity = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)capacity);
        } else if (capacity < 10) {
            capacity = 10;
        }
        if (this.wrapped) {
            this.a = ObjectArrays.forceCapacity(this.a, capacity, this.size);
        } else {
            Object[] t = new Object[capacity];
            System.arraycopy(this.a, 0, t, 0, this.size);
            this.a = t;
        }
        if ($assertionsDisabled) return;
        if (this.size <= this.a.length) return;
        throw new AssertionError();
    }

    @Override
    public void add(int index, K k) {
        this.ensureIndex(index);
        this.grow(this.size + 1);
        if (index != this.size) {
            System.arraycopy(this.a, index, this.a, index + 1, this.size - index);
        }
        this.a[index] = k;
        ++this.size;
        if ($assertionsDisabled) return;
        if (this.size <= this.a.length) return;
        throw new AssertionError();
    }

    @Override
    public boolean add(K k) {
        this.grow(this.size + 1);
        this.a[this.size++] = k;
        if ($assertionsDisabled) return true;
        if (this.size <= this.a.length) return true;
        throw new AssertionError();
    }

    @Override
    public K get(int index) {
        if (index < this.size) return this.a[index];
        throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
    }

    @Override
    public int indexOf(Object k) {
        int i = 0;
        while (i < this.size) {
            if (Objects.equals(k, this.a[i])) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object k) {
        int i = this.size;
        do {
            if (i-- == 0) return -1;
        } while (!Objects.equals(k, this.a[i]));
        return i;
    }

    @Override
    public K remove(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
        }
        K old = this.a[index];
        --this.size;
        if (index != this.size) {
            System.arraycopy(this.a, index + 1, this.a, index, this.size - index);
        }
        this.a[this.size] = null;
        if ($assertionsDisabled) return old;
        if (this.size <= this.a.length) return old;
        throw new AssertionError();
    }

    @Override
    public boolean remove(Object k) {
        int index = this.indexOf(k);
        if (index == -1) {
            return false;
        }
        this.remove(index);
        if ($assertionsDisabled) return true;
        if (this.size <= this.a.length) return true;
        throw new AssertionError();
    }

    @Override
    public K set(int index, K k) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
        }
        K old = this.a[index];
        this.a[index] = k;
        return old;
    }

    @Override
    public void clear() {
        java.util.Arrays.fill(this.a, 0, this.size, null);
        this.size = 0;
        if ($assertionsDisabled) return;
        if (this.size <= this.a.length) return;
        throw new AssertionError();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void size(int size) {
        if (size > this.a.length) {
            this.a = ObjectArrays.forceCapacity(this.a, size, this.size);
        }
        if (size > this.size) {
            java.util.Arrays.fill(this.a, this.size, size, null);
        } else {
            java.util.Arrays.fill(this.a, size, this.size, null);
        }
        this.size = size;
    }

    @Override
    public boolean isEmpty() {
        if (this.size != 0) return false;
        return true;
    }

    public void trim() {
        this.trim(0);
    }

    public void trim(int n) {
        if (n >= this.a.length) return;
        if (this.size == this.a.length) {
            return;
        }
        Object[] t = new Object[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, t, 0, this.size);
        this.a = t;
        if ($assertionsDisabled) return;
        if (this.size <= this.a.length) return;
        throw new AssertionError();
    }

    @Override
    public ObjectList<K> subList(int from, int to) {
        if (from == 0 && to == this.size()) {
            return this;
        }
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from <= to) return new SubList(from, to);
        throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
    }

    @Override
    public void getElements(int from, Object[] a, int offset, int length) {
        ObjectArrays.ensureOffsetLength(a, offset, length);
        System.arraycopy(this.a, from, a, offset, length);
    }

    @Override
    public void removeElements(int from, int to) {
        Arrays.ensureFromTo(this.size, from, to);
        System.arraycopy(this.a, to, this.a, from, this.size - to);
        this.size -= to - from;
        int i = to - from;
        while (i-- != 0) {
            this.a[this.size + i] = null;
        }
    }

    @Override
    public void addElements(int index, K[] a, int offset, int length) {
        this.ensureIndex(index);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        this.grow(this.size + length);
        System.arraycopy(this.a, index, this.a, index + length, this.size - index);
        System.arraycopy(a, offset, this.a, index, length);
        this.size += length;
    }

    @Override
    public void setElements(int index, K[] a, int offset, int length) {
        this.ensureIndex(index);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        if (index + length > this.size) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
        }
        System.arraycopy(a, offset, this.a, index, length);
    }

    @Override
    public void forEach(Consumer<? super K> action) {
        int i = 0;
        while (i < this.size) {
            action.accept(this.a[i]);
            ++i;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends K> c) {
        if (c instanceof ObjectList) {
            return this.addAll(index, (ObjectList)c);
        }
        this.ensureIndex(index);
        int n = c.size();
        if (n == 0) {
            return false;
        }
        this.grow(this.size + n);
        System.arraycopy(this.a, index, this.a, index + n, this.size - index);
        Iterator<K> i = c.iterator();
        this.size += n;
        while (true) {
            if (n-- == 0) {
                if ($assertionsDisabled) return true;
                if (this.size <= this.a.length) return true;
                throw new AssertionError();
            }
            this.a[index++] = i.next();
        }
    }

    @Override
    public boolean addAll(int index, ObjectList<? extends K> l) {
        this.ensureIndex(index);
        int n = l.size();
        if (n == 0) {
            return false;
        }
        this.grow(this.size + n);
        System.arraycopy(this.a, index, this.a, index + n, this.size - index);
        l.getElements(0, this.a, index, n);
        this.size += n;
        if ($assertionsDisabled) return true;
        if (this.size <= this.a.length) return true;
        throw new AssertionError();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] a = this.a;
        int j = 0;
        for (int i = 0; i < this.size; ++i) {
            if (c.contains(a[i])) continue;
            a[j++] = a[i];
        }
        java.util.Arrays.fill(a, j, this.size, null);
        boolean modified = this.size != j;
        this.size = j;
        return modified;
    }

    @Override
    public Object[] toArray() {
        return java.util.Arrays.copyOf(this.a, this.size(), Object[].class);
    }

    @Override
    public <K> K[] toArray(K[] a) {
        if (a == null) {
            a = new Object[this.size()];
        } else if (a.length < this.size()) {
            a = (Object[])Array.newInstance(a.getClass().getComponentType(), this.size());
        }
        System.arraycopy(this.a, 0, a, 0, this.size());
        if (a.length <= this.size()) return a;
        a[this.size()] = null;
        return a;
    }

    @Override
    public ObjectListIterator<K> listIterator(final int index) {
        this.ensureIndex(index);
        return new ObjectListIterator<K>(){
            int pos;
            int last;
            {
                this.pos = index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                if (this.pos >= ObjectArrayList.this.size) return false;
                return true;
            }

            @Override
            public boolean hasPrevious() {
                if (this.pos <= 0) return false;
                return true;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return ObjectArrayList.this.a[this.last];
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return ObjectArrayList.this.a[this.pos];
            }

            @Override
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(K k) {
                ObjectArrayList.this.add(this.pos++, k);
                this.last = -1;
            }

            @Override
            public void set(K k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                ObjectArrayList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                ObjectArrayList.this.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }

            @Override
            public void forEachRemaining(Consumer<? super K> action) {
                while (this.pos < ObjectArrayList.this.size) {
                    ++this.pos;
                    this.last = this.last;
                    action.accept(ObjectArrayList.this.a[this.last]);
                }
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int remaining = ObjectArrayList.this.size - this.pos;
                if (n < remaining) {
                    this.pos -= n;
                } else {
                    n = remaining;
                    this.pos = 0;
                }
                this.last = this.pos;
                return n;
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int remaining = ObjectArrayList.this.size - this.pos;
                if (n < remaining) {
                    this.pos += n;
                } else {
                    n = remaining;
                    this.pos = ObjectArrayList.this.size;
                }
                this.last = this.pos - 1;
                return n;
            }
        };
    }

    @Override
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator();
    }

    @Override
    public void sort(Comparator<? super K> comp) {
        if (comp == null) {
            ObjectArrays.stableSort(this.a, 0, this.size);
            return;
        }
        ObjectArrays.stableSort(this.a, 0, this.size, comp);
    }

    @Override
    public void unstableSort(Comparator<? super K> comp) {
        if (comp == null) {
            ObjectArrays.unstableSort(this.a, 0, this.size);
            return;
        }
        ObjectArrays.unstableSort(this.a, 0, this.size, comp);
    }

    public ObjectArrayList<K> clone() {
        ObjectArrayList<K> cloned = null;
        if (this.getClass() == ObjectArrayList.class) {
            cloned = new ObjectArrayList<K>(ObjectArrayList.copyArraySafe(this.a, this.size), false);
            cloned.size = this.size;
            return cloned;
        }
        try {
            cloned = (ObjectArrayList<K>)super.clone();
        }
        catch (CloneNotSupportedException err) {
            throw new InternalError(err);
        }
        cloned.a = ObjectArrayList.copyArraySafe(this.a, this.size);
        return cloned;
    }

    public boolean equals(ObjectArrayList<K> l) {
        if (l == this) {
            return true;
        }
        int s = this.size();
        if (s != l.size()) {
            return false;
        }
        K[] a1 = this.a;
        K[] a2 = l.a;
        if (a1 == a2 && s == l.size()) {
            return true;
        }
        do {
            if (s-- == 0) return true;
        } while (Objects.equals(a1[s], a2[s]));
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof List)) {
            return false;
        }
        if (o instanceof ObjectArrayList) {
            return this.equals((ObjectArrayList)o);
        }
        if (!(o instanceof SubList)) return super.equals(o);
        return ((SubList)o).equals(this);
    }

    @Override
    public int compareTo(ObjectArrayList<? extends K> l) {
        int i;
        int s1 = this.size();
        int s2 = l.size();
        K[] a1 = this.a;
        K[] a2 = l.a;
        for (i = 0; i < s1 && i < s2; ++i) {
            K e1 = a1[i];
            K e2 = a2[i];
            int r = ((Comparable)e1).compareTo(e2);
            if (r == 0) continue;
            return r;
        }
        if (i < s2) {
            return -1;
        }
        if (i >= s1) return 0;
        return 1;
    }

    @Override
    public int compareTo(List<? extends K> l) {
        if (l instanceof ObjectArrayList) {
            return this.compareTo((ObjectArrayList)l);
        }
        if (!(l instanceof SubList)) return super.compareTo(l);
        return -((SubList)l).compareTo(this);
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

    private class SubList
    extends AbstractObjectList.ObjectRandomAccessSubList<K> {
        private static final long serialVersionUID = -3185226345314976296L;

        protected SubList(int from, int to) {
            super(ObjectArrayList.this, from, to);
        }

        private K[] getParentArray() {
            return ObjectArrayList.this.a;
        }

        @Override
        public K get(int i) {
            this.ensureRestrictedIndex(i);
            return ObjectArrayList.this.a[i + this.from];
        }

        @Override
        public ObjectListIterator<K> listIterator(int index) {
            return new SubListIterator(index);
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(K[] otherA, int otherAFrom, int otherATo) {
            if (ObjectArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
                return true;
            }
            if (otherATo - otherAFrom != this.size()) {
                return false;
            }
            int pos = this.from;
            int otherPos = otherAFrom;
            do {
                if (pos >= this.to) return true;
            } while (Objects.equals(ObjectArrayList.this.a[pos++], otherA[otherPos++]));
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (!(o instanceof List)) {
                return false;
            }
            if (o instanceof ObjectArrayList) {
                ObjectArrayList other = (ObjectArrayList)o;
                return this.contentsEquals(other.a, 0, other.size());
            }
            if (!(o instanceof SubList)) return super.equals(o);
            SubList other = (SubList)o;
            return this.contentsEquals(other.getParentArray(), other.from, other.to);
        }

        int contentsCompareTo(K[] otherA, int otherAFrom, int otherATo) {
            int i;
            int j = otherAFrom;
            for (i = this.from; i < this.to && i < otherATo; ++i, ++j) {
                Object e1 = ObjectArrayList.this.a[i];
                Object e2 = otherA[j];
                int r = ((Comparable)e1).compareTo(e2);
                if (r == 0) continue;
                return r;
            }
            if (i < otherATo) {
                return -1;
            }
            if (i >= this.to) return 0;
            return 1;
        }

        @Override
        public int compareTo(List<? extends K> l) {
            if (l instanceof ObjectArrayList) {
                ObjectArrayList other = (ObjectArrayList)l;
                return this.contentsCompareTo(other.a, 0, other.size());
            }
            if (!(l instanceof SubList)) return super.compareTo(l);
            SubList other = (SubList)l;
            return this.contentsCompareTo(other.getParentArray(), other.from, other.to);
        }

        private final class SubListIterator
        extends ObjectIterators.AbstractIndexBasedListIterator<K> {
            SubListIterator(int index) {
                super(0, index);
            }

            @Override
            protected final K get(int i) {
                return ObjectArrayList.this.a[SubList.this.from + i];
            }

            @Override
            protected final void add(int i, K k) {
                SubList.this.add(i, k);
            }

            @Override
            protected final void set(int i, K k) {
                SubList.this.set(i, k);
            }

            @Override
            protected final void remove(int i) {
                SubList.this.remove(i);
            }

            @Override
            protected final int getMaxPos() {
                return SubList.this.to - SubList.this.from;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = this.pos++;
                return ObjectArrayList.this.a[SubList.this.from + this.lastReturned];
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = --this.pos;
                return ObjectArrayList.this.a[SubList.this.from + this.pos];
            }

            @Override
            public void forEachRemaining(Consumer<? super K> action) {
                int max = SubList.this.to - SubList.this.from;
                while (this.pos < max) {
                    ++this.pos;
                    this.lastReturned = this.lastReturned;
                    action.accept(ObjectArrayList.this.a[SubList.this.from + this.lastReturned]);
                }
            }
        }

        private final class SubListSpliterator
        extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
            SubListSpliterator() {
                super(SubList.this.from);
            }

            private SubListSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
            }

            @Override
            protected final int getMaxPosFromBackingStore() {
                return SubList.this.to;
            }

            @Override
            protected final K get(int i) {
                return ObjectArrayList.this.a[i];
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList$SubList.SubListSpliterator makeForSplit(int pos, int maxPos) {
                return new SubListSpliterator(pos, maxPos);
            }

            @Override
            public boolean tryAdvance(Consumer<? super K> action) {
                if (this.pos >= this.getMaxPos()) {
                    return false;
                }
                action.accept(ObjectArrayList.this.a[this.pos++]);
                return true;
            }

            @Override
            public void forEachRemaining(Consumer<? super K> action) {
                int max = this.getMaxPos();
                while (this.pos < max) {
                    action.accept(ObjectArrayList.this.a[this.pos++]);
                }
            }
        }
    }

    private final class Spliterator
    implements ObjectSpliterator<K> {
        boolean hasSplit = false;
        int pos;
        int max;

        public Spliterator() {
            this(0, objectArrayList.size, false);
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
            n = ObjectArrayList.this.size;
            return n;
        }

        @Override
        public int characteristics() {
            return 16464;
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
            action.accept(ObjectArrayList.this.a[this.pos++]);
            return true;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> action) {
            int max = this.getWorkingMax();
            while (this.pos < max) {
                action.accept(ObjectArrayList.this.a[this.pos]);
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

