/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLists;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class ObjectImmutableList<K>
extends ObjectLists.ImmutableListBase<K>
implements ObjectList<K>,
RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = 0L;
    static final ObjectImmutableList EMPTY = new ObjectImmutableList<Object>(ObjectArrays.EMPTY_ARRAY);
    private final K[] a;
    private static final Collector<Object, ?, ObjectImmutableList<Object>> TO_LIST_COLLECTOR = Collector.of(ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine, ObjectImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);

    private static final <K> K[] emptyArray() {
        return ObjectArrays.EMPTY_ARRAY;
    }

    public ObjectImmutableList(K[] a) {
        this.a = a;
    }

    public ObjectImmutableList(Collection<? extends K> c) {
        this(c.isEmpty() ? ObjectImmutableList.emptyArray() : ObjectIterators.unwrap(c.iterator()));
    }

    public ObjectImmutableList(ObjectCollection<? extends K> c) {
        this(c.isEmpty() ? ObjectImmutableList.emptyArray() : ObjectIterators.unwrap(c.iterator()));
    }

    public ObjectImmutableList(ObjectList<? extends K> l) {
        this(l.isEmpty() ? ObjectImmutableList.emptyArray() : new Object[l.size()]);
        l.getElements(0, this.a, 0, l.size());
    }

    public ObjectImmutableList(K[] a, int offset, int length) {
        this(length == 0 ? ObjectImmutableList.emptyArray() : new Object[length]);
        System.arraycopy(a, offset, this.a, 0, length);
    }

    public ObjectImmutableList(ObjectIterator<? extends K> i) {
        this(i.hasNext() ? ObjectIterators.unwrap(i) : ObjectImmutableList.emptyArray());
    }

    public static <K> ObjectImmutableList<K> of() {
        return EMPTY;
    }

    @SafeVarargs
    public static <K> ObjectImmutableList<K> of(K ... init) {
        ObjectImmutableList<K> objectImmutableList;
        if (init.length == 0) {
            objectImmutableList = ObjectImmutableList.of();
            return objectImmutableList;
        }
        objectImmutableList = new ObjectImmutableList<K>(init);
        return objectImmutableList;
    }

    private static <K> ObjectImmutableList<K> convertTrustedToImmutableList(ObjectArrayList<K> arrayList) {
        if (arrayList.isEmpty()) {
            return ObjectImmutableList.of();
        }
        K[] backingArray = arrayList.elements();
        if (arrayList.size() == backingArray.length) return new ObjectImmutableList<K>(backingArray);
        backingArray = Arrays.copyOf(backingArray, arrayList.size());
        return new ObjectImmutableList<K>(backingArray);
    }

    public static <K> Collector<K, ?, ObjectImmutableList<K>> toList() {
        return TO_LIST_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectImmutableList<K>> toListWithExpectedSize(int expectedSize) {
        if (expectedSize > 10) return Collector.of(new ObjectCollections.SizeDecreasingSupplier(expectedSize, size -> {
            ObjectArrayList objectArrayList;
            if (size <= 10) {
                objectArrayList = new ObjectArrayList();
                return objectArrayList;
            }
            objectArrayList = new ObjectArrayList(size);
            return objectArrayList;
        }), ObjectArrayList::add, ObjectArrayList::combine, ObjectImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);
        return ObjectImmutableList.toList();
    }

    @Override
    public K get(int index) {
        if (index < this.a.length) return this.a[index];
        throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.a.length + ")");
    }

    @Override
    public int indexOf(Object k) {
        int i = 0;
        int size = this.a.length;
        while (i < size) {
            if (Objects.equals(k, this.a[i])) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object k) {
        int i = this.a.length;
        do {
            if (i-- == 0) return -1;
        } while (!Objects.equals(k, this.a[i]));
        return i;
    }

    @Override
    public int size() {
        return this.a.length;
    }

    @Override
    public boolean isEmpty() {
        if (this.a.length != 0) return false;
        return true;
    }

    @Override
    public void getElements(int from, Object[] a, int offset, int length) {
        ObjectArrays.ensureOffsetLength(a, offset, length);
        System.arraycopy(this.a, from, a, offset, length);
    }

    @Override
    public void forEach(Consumer<? super K> action) {
        int i = 0;
        while (i < this.a.length) {
            action.accept(this.a[i]);
            ++i;
        }
    }

    @Override
    public Object[] toArray() {
        if (!this.a.getClass().equals(Object[].class)) return Arrays.copyOf(this.a, this.a.length, Object[].class);
        return (Object[])this.a.clone();
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
            {
                this.pos = index;
            }

            @Override
            public boolean hasNext() {
                if (this.pos >= ObjectImmutableList.this.a.length) return false;
                return true;
            }

            @Override
            public boolean hasPrevious() {
                if (this.pos <= 0) return false;
                return true;
            }

            @Override
            public K next() {
                if (this.hasNext()) return ObjectImmutableList.this.a[this.pos++];
                throw new NoSuchElementException();
            }

            @Override
            public K previous() {
                if (this.hasPrevious()) return ObjectImmutableList.this.a[--this.pos];
                throw new NoSuchElementException();
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
            public void forEachRemaining(Consumer<? super K> action) {
                while (this.pos < ObjectImmutableList.this.a.length) {
                    action.accept(ObjectImmutableList.this.a[this.pos++]);
                }
            }

            @Override
            public void add(K k) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(K k) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int remaining = ObjectImmutableList.this.a.length - this.pos;
                if (n < remaining) {
                    this.pos -= n;
                    return n;
                }
                n = remaining;
                this.pos = 0;
                return n;
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int remaining = ObjectImmutableList.this.a.length - this.pos;
                if (n < remaining) {
                    this.pos += n;
                    return n;
                }
                n = remaining;
                this.pos = ObjectImmutableList.this.a.length;
                return n;
            }
        };
    }

    @Override
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator();
    }

    @Override
    public ObjectList<K> subList(int from, int to) {
        if (from == 0 && to == this.size()) {
            return this;
        }
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from == to) {
            return EMPTY;
        }
        if (from <= to) return new ImmutableSubList(this, from, to);
        throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
    }

    public ObjectImmutableList<K> clone() {
        return this;
    }

    public boolean equals(ObjectImmutableList<K> l) {
        if (l == this) {
            return true;
        }
        if (this.a == l.a) {
            return true;
        }
        int s = this.size();
        if (s != l.size()) {
            return false;
        }
        Object[] a1 = this.a;
        Object[] a2 = l.a;
        return Arrays.equals(a1, a2);
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
        if (o instanceof ObjectImmutableList) {
            return this.equals((ObjectImmutableList)o);
        }
        if (!(o instanceof ImmutableSubList)) return super.equals(o);
        return ((ImmutableSubList)o).equals(this);
    }

    @Override
    public int compareTo(ObjectImmutableList<? extends K> l) {
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
        if (l instanceof ObjectImmutableList) {
            return this.compareTo((ObjectImmutableList)l);
        }
        if (!(l instanceof ImmutableSubList)) return super.compareTo(l);
        ImmutableSubList other = (ImmutableSubList)l;
        return -other.compareTo(this);
    }

    private final class Spliterator
    implements ObjectSpliterator<K> {
        int pos;
        int max;

        public Spliterator() {
            this(0, objectImmutableList.a.length);
        }

        private Spliterator(int pos, int max) {
            assert (pos <= max) : "pos " + pos + " must be <= max " + max;
            this.pos = pos;
            this.max = max;
        }

        @Override
        public int characteristics() {
            return 17488;
        }

        @Override
        public long estimateSize() {
            return this.max - this.pos;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> action) {
            if (this.pos >= this.max) {
                return false;
            }
            action.accept(ObjectImmutableList.this.a[this.pos++]);
            return true;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> action) {
            while (this.pos < this.max) {
                action.accept(ObjectImmutableList.this.a[this.pos]);
                ++this.pos;
            }
        }

        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.pos >= this.max) {
                return 0L;
            }
            int remaining = this.max - this.pos;
            if (n < (long)remaining) {
                this.pos = SafeMath.safeLongToInt((long)this.pos + n);
                return n;
            }
            n = remaining;
            this.pos = this.max;
            return n;
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            int myNewPos;
            int retLen = this.max - this.pos >> 1;
            if (retLen <= 1) {
                return null;
            }
            int retMax = myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            return new Spliterator(oldPos, retMax);
        }
    }

    private static final class ImmutableSubList<K>
    extends ObjectLists.ImmutableListBase<K>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 7054639518438982401L;
        final ObjectImmutableList<K> innerList;
        final int from;
        final int to;
        final transient K[] a;

        ImmutableSubList(ObjectImmutableList<K> innerList, int from, int to) {
            this.innerList = innerList;
            this.from = from;
            this.to = to;
            this.a = ((ObjectImmutableList)innerList).a;
        }

        @Override
        public K get(int index) {
            this.ensureRestrictedIndex(index);
            return this.a[index + this.from];
        }

        @Override
        public int indexOf(Object k) {
            int i = this.from;
            while (i < this.to) {
                if (Objects.equals(k, this.a[i])) {
                    return i - this.from;
                }
                ++i;
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object k) {
            int i = this.to;
            do {
                if (i-- == this.from) return -1;
            } while (!Objects.equals(k, this.a[i]));
            return i - this.from;
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public boolean isEmpty() {
            if (this.to > this.from) return false;
            return true;
        }

        @Override
        public void getElements(int fromSublistIndex, Object[] a, int offset, int length) {
            ObjectArrays.ensureOffsetLength(a, offset, length);
            this.ensureRestrictedIndex(fromSublistIndex);
            if (this.from + length > this.to) {
                throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + this.size());
            }
            System.arraycopy(this.a, fromSublistIndex + this.from, a, offset, length);
        }

        @Override
        public void forEach(Consumer<? super K> action) {
            int i = this.from;
            while (i < this.to) {
                action.accept(this.a[i]);
                ++i;
            }
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOfRange(this.a, this.from, this.to, Object[].class);
        }

        @Override
        public <K> K[] toArray(K[] a) {
            int size = this.size();
            if (a == null) {
                a = new Object[size];
            } else if (a.length < size) {
                a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
            }
            System.arraycopy(this.a, this.from, a, 0, size);
            if (a.length <= size) return a;
            a[size] = null;
            return a;
        }

        @Override
        public ObjectListIterator<K> listIterator(final int index) {
            this.ensureIndex(index);
            return new ObjectListIterator<K>(){
                int pos;
                {
                    this.pos = index;
                }

                @Override
                public boolean hasNext() {
                    if (this.pos >= to) return false;
                    return true;
                }

                @Override
                public boolean hasPrevious() {
                    if (this.pos <= from) return false;
                    return true;
                }

                @Override
                public K next() {
                    if (this.hasNext()) return a[this.pos++ + from];
                    throw new NoSuchElementException();
                }

                @Override
                public K previous() {
                    if (this.hasPrevious()) return a[--this.pos + from];
                    throw new NoSuchElementException();
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
                public void forEachRemaining(Consumer<? super K> action) {
                    while (this.pos < to) {
                        action.accept(a[this.pos++ + from]);
                    }
                }

                @Override
                public void add(K k) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void set(K k) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int back(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int remaining = to - this.pos;
                    if (n < remaining) {
                        this.pos -= n;
                        return n;
                    }
                    n = remaining;
                    this.pos = 0;
                    return n;
                }

                @Override
                public int skip(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int remaining = to - this.pos;
                    if (n < remaining) {
                        this.pos += n;
                        return n;
                    }
                    n = remaining;
                    this.pos = to;
                    return n;
                }
            };
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(K[] otherA, int otherAFrom, int otherATo) {
            if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
                return true;
            }
            if (otherATo - otherAFrom != this.size()) {
                return false;
            }
            int pos = this.from;
            int otherPos = otherAFrom;
            do {
                if (pos >= this.to) return true;
            } while (Objects.equals(this.a[pos++], otherA[otherPos++]));
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
            if (o instanceof ObjectImmutableList) {
                ObjectImmutableList other = (ObjectImmutableList)o;
                return this.contentsEquals(other.a, 0, other.size());
            }
            if (!(o instanceof ImmutableSubList)) return super.equals(o);
            ImmutableSubList other = (ImmutableSubList)o;
            return this.contentsEquals(other.a, other.from, other.to);
        }

        int contentsCompareTo(K[] otherA, int otherAFrom, int otherATo) {
            int i;
            int j = otherAFrom;
            for (i = this.from; i < this.to && i < otherATo; ++i, ++j) {
                K e1 = this.a[i];
                K e2 = otherA[j];
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
            if (l instanceof ObjectImmutableList) {
                ObjectImmutableList other = (ObjectImmutableList)l;
                return this.contentsCompareTo(other.a, 0, other.size());
            }
            if (!(l instanceof ImmutableSubList)) return super.compareTo(l);
            ImmutableSubList other = (ImmutableSubList)l;
            return this.contentsCompareTo(other.a, other.from, other.to);
        }

        private Object readResolve() throws ObjectStreamException {
            try {
                return this.innerList.subList(this.from, this.to);
            }
            catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                throw (InvalidObjectException)new InvalidObjectException(ex.getMessage()).initCause(ex);
            }
        }

        @Override
        public ObjectList<K> subList(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from == to) {
                return EMPTY;
            }
            if (from <= to) return new ImmutableSubList<K>(this.innerList, from + this.from, to + this.from);
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }

        private final class SubListSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K> {
            SubListSpliterator() {
                super(ImmutableSubList.this.from, ImmutableSubList.this.to);
            }

            private SubListSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
            }

            @Override
            protected final K get(int i) {
                return ImmutableSubList.this.a[i];
            }

            protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
                return new SubListSpliterator(pos, maxPos);
            }

            @Override
            public boolean tryAdvance(Consumer<? super K> action) {
                if (this.pos >= this.maxPos) {
                    return false;
                }
                action.accept(ImmutableSubList.this.a[this.pos++]);
                return true;
            }

            @Override
            public void forEachRemaining(Consumer<? super K> action) {
                int max = this.maxPos;
                while (this.pos < max) {
                    action.accept(ImmutableSubList.this.a[this.pos++]);
                }
            }

            @Override
            public int characteristics() {
                return 17488;
            }
        }
    }
}

