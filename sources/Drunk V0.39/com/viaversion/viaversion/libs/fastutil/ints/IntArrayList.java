/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Arrays;
import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.libs.fastutil.ints.IntListIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class IntArrayList
extends AbstractIntList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient int[] a;
    protected int size;

    private static final int[] copyArraySafe(int[] a, int length) {
        if (length != 0) return java.util.Arrays.copyOf(a, length);
        return IntArrays.EMPTY_ARRAY;
    }

    private static final int[] copyArrayFromSafe(IntArrayList l) {
        return IntArrayList.copyArraySafe(l.a, l.size);
    }

    protected IntArrayList(int[] a, boolean wrapped) {
        this.a = a;
    }

    private void initArrayFromCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
        }
        if (capacity == 0) {
            this.a = IntArrays.EMPTY_ARRAY;
            return;
        }
        this.a = new int[capacity];
    }

    public IntArrayList(int capacity) {
        this.initArrayFromCapacity(capacity);
    }

    public IntArrayList() {
        this.a = IntArrays.DEFAULT_EMPTY_ARRAY;
    }

    public IntArrayList(Collection<? extends Integer> c) {
        if (c instanceof IntArrayList) {
            this.a = IntArrayList.copyArrayFromSafe((IntArrayList)c);
            this.size = this.a.length;
            return;
        }
        this.initArrayFromCapacity(c.size());
        if (c instanceof IntList) {
            this.size = c.size();
            ((IntList)c).getElements(0, this.a, 0, this.size);
            return;
        }
        this.size = IntIterators.unwrap(IntIterators.asIntIterator(c.iterator()), this.a);
    }

    public IntArrayList(IntCollection c) {
        if (c instanceof IntArrayList) {
            this.a = IntArrayList.copyArrayFromSafe((IntArrayList)c);
            this.size = this.a.length;
            return;
        }
        this.initArrayFromCapacity(c.size());
        if (c instanceof IntList) {
            this.size = c.size();
            ((IntList)c).getElements(0, this.a, 0, this.size);
            return;
        }
        this.size = IntIterators.unwrap(c.iterator(), this.a);
    }

    public IntArrayList(IntList l) {
        if (l instanceof IntArrayList) {
            this.a = IntArrayList.copyArrayFromSafe((IntArrayList)l);
            this.size = this.a.length;
            return;
        }
        this.initArrayFromCapacity(l.size());
        this.size = l.size();
        l.getElements(0, this.a, 0, this.size);
    }

    public IntArrayList(int[] a) {
        this(a, 0, a.length);
    }

    public IntArrayList(int[] a, int offset, int length) {
        this(length);
        System.arraycopy(a, offset, this.a, 0, length);
        this.size = length;
    }

    public IntArrayList(Iterator<? extends Integer> i) {
        this();
        while (i.hasNext()) {
            this.add((int)i.next());
        }
    }

    public IntArrayList(IntIterator i) {
        this();
        while (i.hasNext()) {
            this.add(i.nextInt());
        }
    }

    public int[] elements() {
        return this.a;
    }

    public static IntArrayList wrap(int[] a, int length) {
        if (length > a.length) {
            throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
        }
        IntArrayList l = new IntArrayList(a, true);
        l.size = length;
        return l;
    }

    public static IntArrayList wrap(int[] a) {
        return IntArrayList.wrap(a, a.length);
    }

    public static IntArrayList of() {
        return new IntArrayList();
    }

    public static IntArrayList of(int ... init) {
        return IntArrayList.wrap(init);
    }

    public static IntArrayList toList(IntStream stream) {
        return stream.collect(IntArrayList::new, IntArrayList::add, IntList::addAll);
    }

    public static IntArrayList toListWithExpectedSize(IntStream stream, int expectedSize) {
        if (expectedSize > 10) return stream.collect(new IntCollections.SizeDecreasingSupplier<IntArrayList>(expectedSize, size -> {
            IntArrayList intArrayList;
            if (size <= 10) {
                intArrayList = new IntArrayList();
                return intArrayList;
            }
            intArrayList = new IntArrayList(size);
            return intArrayList;
        }), IntArrayList::add, IntList::addAll);
        return IntArrayList.toList(stream);
    }

    public void ensureCapacity(int capacity) {
        if (capacity <= this.a.length) return;
        if (this.a == IntArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10) {
            return;
        }
        this.a = IntArrays.ensureCapacity(this.a, capacity, this.size);
        if ($assertionsDisabled) return;
        if (this.size <= this.a.length) return;
        throw new AssertionError();
    }

    private void grow(int capacity) {
        if (capacity <= this.a.length) {
            return;
        }
        if (this.a != IntArrays.DEFAULT_EMPTY_ARRAY) {
            capacity = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)capacity);
        } else if (capacity < 10) {
            capacity = 10;
        }
        this.a = IntArrays.forceCapacity(this.a, capacity, this.size);
        if ($assertionsDisabled) return;
        if (this.size <= this.a.length) return;
        throw new AssertionError();
    }

    @Override
    public void add(int index, int k) {
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
    public boolean add(int k) {
        this.grow(this.size + 1);
        this.a[this.size++] = k;
        if ($assertionsDisabled) return true;
        if (this.size <= this.a.length) return true;
        throw new AssertionError();
    }

    @Override
    public int getInt(int index) {
        if (index < this.size) return this.a[index];
        throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
    }

    @Override
    public int indexOf(int k) {
        int i = 0;
        while (i < this.size) {
            if (k == this.a[i]) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(int k) {
        int i = this.size;
        do {
            if (i-- == 0) return -1;
        } while (k != this.a[i]);
        return i;
    }

    @Override
    public int removeInt(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
        }
        int old = this.a[index];
        --this.size;
        if (index != this.size) {
            System.arraycopy(this.a, index + 1, this.a, index, this.size - index);
        }
        if ($assertionsDisabled) return old;
        if (this.size <= this.a.length) return old;
        throw new AssertionError();
    }

    @Override
    public boolean rem(int k) {
        int index = this.indexOf(k);
        if (index == -1) {
            return false;
        }
        this.removeInt(index);
        if ($assertionsDisabled) return true;
        if (this.size <= this.a.length) return true;
        throw new AssertionError();
    }

    @Override
    public int set(int index, int k) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
        }
        int old = this.a[index];
        this.a[index] = k;
        return old;
    }

    @Override
    public void clear() {
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
            this.a = IntArrays.forceCapacity(this.a, size, this.size);
        }
        if (size > this.size) {
            java.util.Arrays.fill(this.a, this.size, size, 0);
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
        int[] t = new int[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, t, 0, this.size);
        this.a = t;
        if ($assertionsDisabled) return;
        if (this.size <= this.a.length) return;
        throw new AssertionError();
    }

    @Override
    public IntList subList(int from, int to) {
        if (from == 0 && to == this.size()) {
            return this;
        }
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from <= to) return new SubList(from, to);
        throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
    }

    @Override
    public void getElements(int from, int[] a, int offset, int length) {
        IntArrays.ensureOffsetLength(a, offset, length);
        System.arraycopy(this.a, from, a, offset, length);
    }

    @Override
    public void removeElements(int from, int to) {
        Arrays.ensureFromTo(this.size, from, to);
        System.arraycopy(this.a, to, this.a, from, this.size - to);
        this.size -= to - from;
    }

    @Override
    public void addElements(int index, int[] a, int offset, int length) {
        this.ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        this.grow(this.size + length);
        System.arraycopy(this.a, index, this.a, index + length, this.size - index);
        System.arraycopy(a, offset, this.a, index, length);
        this.size += length;
    }

    @Override
    public void setElements(int index, int[] a, int offset, int length) {
        this.ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (index + length > this.size) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
        }
        System.arraycopy(a, offset, this.a, index, length);
    }

    @Override
    public void forEach(IntConsumer action) {
        int i = 0;
        while (i < this.size) {
            action.accept(this.a[i]);
            ++i;
        }
    }

    @Override
    public boolean addAll(int index, IntCollection c) {
        if (c instanceof IntList) {
            return this.addAll(index, (IntList)c);
        }
        this.ensureIndex(index);
        int n = c.size();
        if (n == 0) {
            return false;
        }
        this.grow(this.size + n);
        System.arraycopy(this.a, index, this.a, index + n, this.size - index);
        IntIterator i = c.iterator();
        this.size += n;
        while (true) {
            if (n-- == 0) {
                if ($assertionsDisabled) return true;
                if (this.size <= this.a.length) return true;
                throw new AssertionError();
            }
            this.a[index++] = i.nextInt();
        }
    }

    @Override
    public boolean addAll(int index, IntList l) {
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
    public boolean removeAll(IntCollection c) {
        int[] a = this.a;
        int j = 0;
        for (int i = 0; i < this.size; ++i) {
            if (c.contains(a[i])) continue;
            a[j++] = a[i];
        }
        boolean modified = this.size != j;
        this.size = j;
        return modified;
    }

    @Override
    public int[] toArray(int[] a) {
        if (a == null || a.length < this.size) {
            a = java.util.Arrays.copyOf(a, this.size);
        }
        System.arraycopy(this.a, 0, a, 0, this.size);
        return a;
    }

    @Override
    public IntListIterator listIterator(final int index) {
        this.ensureIndex(index);
        return new IntListIterator(){
            int pos;
            int last;
            {
                this.pos = index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                if (this.pos >= IntArrayList.this.size) return false;
                return true;
            }

            @Override
            public boolean hasPrevious() {
                if (this.pos <= 0) return false;
                return true;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return IntArrayList.this.a[this.last];
            }

            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return IntArrayList.this.a[this.pos];
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
            public void add(int k) {
                IntArrayList.this.add(this.pos++, k);
                this.last = -1;
            }

            @Override
            public void set(int k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                IntArrayList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                IntArrayList.this.removeInt(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }

            @Override
            public void forEachRemaining(IntConsumer action) {
                while (this.pos < IntArrayList.this.size) {
                    ++this.pos;
                    this.last = this.last;
                    action.accept(IntArrayList.this.a[this.last]);
                }
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int remaining = IntArrayList.this.size - this.pos;
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
                int remaining = IntArrayList.this.size - this.pos;
                if (n < remaining) {
                    this.pos += n;
                } else {
                    n = remaining;
                    this.pos = IntArrayList.this.size;
                }
                this.last = this.pos - 1;
                return n;
            }
        };
    }

    @Override
    public IntSpliterator spliterator() {
        return new Spliterator();
    }

    @Override
    public void sort(IntComparator comp) {
        if (comp == null) {
            IntArrays.stableSort(this.a, 0, this.size);
            return;
        }
        IntArrays.stableSort(this.a, 0, this.size, comp);
    }

    @Override
    public void unstableSort(IntComparator comp) {
        if (comp == null) {
            IntArrays.unstableSort(this.a, 0, this.size);
            return;
        }
        IntArrays.unstableSort(this.a, 0, this.size, comp);
    }

    public IntArrayList clone() {
        IntArrayList cloned = null;
        if (this.getClass() == IntArrayList.class) {
            cloned = new IntArrayList(IntArrayList.copyArraySafe(this.a, this.size), false);
            cloned.size = this.size;
            return cloned;
        }
        try {
            cloned = (IntArrayList)super.clone();
        }
        catch (CloneNotSupportedException err) {
            throw new InternalError(err);
        }
        cloned.a = IntArrayList.copyArraySafe(this.a, this.size);
        return cloned;
    }

    public boolean equals(IntArrayList l) {
        if (l == this) {
            return true;
        }
        int s = this.size();
        if (s != l.size()) {
            return false;
        }
        int[] a1 = this.a;
        int[] a2 = l.a;
        if (a1 == a2 && s == l.size()) {
            return true;
        }
        do {
            if (s-- == 0) return true;
        } while (a1[s] == a2[s]);
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
        if (o instanceof IntArrayList) {
            return this.equals((IntArrayList)o);
        }
        if (!(o instanceof SubList)) return super.equals(o);
        return ((SubList)o).equals(this);
    }

    @Override
    public int compareTo(IntArrayList l) {
        int i;
        int s1 = this.size();
        int s2 = l.size();
        int[] a1 = this.a;
        int[] a2 = l.a;
        if (a1 == a2 && s1 == s2) {
            return 0;
        }
        for (i = 0; i < s1 && i < s2; ++i) {
            int e1 = a1[i];
            int e2 = a2[i];
            int r = Integer.compare(e1, e2);
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
    public int compareTo(List<? extends Integer> l) {
        if (l instanceof IntArrayList) {
            return this.compareTo((IntArrayList)l);
        }
        if (!(l instanceof SubList)) return super.compareTo(l);
        return -((SubList)l).compareTo(this);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        int i = 0;
        while (i < this.size) {
            s.writeInt(this.a[i]);
            ++i;
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.a = new int[this.size];
        int i = 0;
        while (i < this.size) {
            this.a[i] = s.readInt();
            ++i;
        }
    }

    private class SubList
    extends AbstractIntList.IntRandomAccessSubList {
        private static final long serialVersionUID = -3185226345314976296L;

        protected SubList(int from, int to) {
            super(IntArrayList.this, from, to);
        }

        private int[] getParentArray() {
            return IntArrayList.this.a;
        }

        @Override
        public int getInt(int i) {
            this.ensureRestrictedIndex(i);
            return IntArrayList.this.a[i + this.from];
        }

        @Override
        public IntListIterator listIterator(int index) {
            return new SubListIterator(index);
        }

        @Override
        public IntSpliterator spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(int[] otherA, int otherAFrom, int otherATo) {
            if (IntArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
                return true;
            }
            if (otherATo - otherAFrom != this.size()) {
                return false;
            }
            int pos = this.from;
            int otherPos = otherAFrom;
            do {
                if (pos >= this.to) return true;
            } while (IntArrayList.this.a[pos++] == otherA[otherPos++]);
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
            if (o instanceof IntArrayList) {
                IntArrayList other = (IntArrayList)o;
                return this.contentsEquals(other.a, 0, other.size());
            }
            if (!(o instanceof SubList)) return super.equals(o);
            SubList other = (SubList)o;
            return this.contentsEquals(other.getParentArray(), other.from, other.to);
        }

        int contentsCompareTo(int[] otherA, int otherAFrom, int otherATo) {
            int i;
            if (IntArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
                return 0;
            }
            int j = otherAFrom;
            for (i = this.from; i < this.to && i < otherATo; ++i, ++j) {
                int e1 = IntArrayList.this.a[i];
                int e2 = otherA[j];
                int r = Integer.compare(e1, e2);
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
        public int compareTo(List<? extends Integer> l) {
            if (l instanceof IntArrayList) {
                IntArrayList other = (IntArrayList)l;
                return this.contentsCompareTo(other.a, 0, other.size());
            }
            if (!(l instanceof SubList)) return super.compareTo(l);
            SubList other = (SubList)l;
            return this.contentsCompareTo(other.getParentArray(), other.from, other.to);
        }

        private final class SubListIterator
        extends IntIterators.AbstractIndexBasedListIterator {
            SubListIterator(int index) {
                super(0, index);
            }

            @Override
            protected final int get(int i) {
                return IntArrayList.this.a[SubList.this.from + i];
            }

            @Override
            protected final void add(int i, int k) {
                SubList.this.add(i, k);
            }

            @Override
            protected final void set(int i, int k) {
                SubList.this.set(i, k);
            }

            @Override
            protected final void remove(int i) {
                SubList.this.removeInt(i);
            }

            @Override
            protected final int getMaxPos() {
                return SubList.this.to - SubList.this.from;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = this.pos++;
                return IntArrayList.this.a[SubList.this.from + this.lastReturned];
            }

            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = --this.pos;
                return IntArrayList.this.a[SubList.this.from + this.pos];
            }

            @Override
            public void forEachRemaining(IntConsumer action) {
                int max = SubList.this.to - SubList.this.from;
                while (this.pos < max) {
                    ++this.pos;
                    this.lastReturned = this.lastReturned;
                    action.accept(IntArrayList.this.a[SubList.this.from + this.lastReturned]);
                }
            }
        }

        private final class SubListSpliterator
        extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
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
            protected final int get(int i) {
                return IntArrayList.this.a[i];
            }

            @Override
            protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
                return new SubListSpliterator(pos, maxPos);
            }

            @Override
            public boolean tryAdvance(IntConsumer action) {
                if (this.pos >= this.getMaxPos()) {
                    return false;
                }
                action.accept(IntArrayList.this.a[this.pos++]);
                return true;
            }

            @Override
            public void forEachRemaining(IntConsumer action) {
                int max = this.getMaxPos();
                while (this.pos < max) {
                    action.accept(IntArrayList.this.a[this.pos++]);
                }
            }
        }
    }

    private final class Spliterator
    implements IntSpliterator {
        boolean hasSplit = false;
        int pos;
        int max;

        public Spliterator() {
            this(0, intArrayList.size, false);
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
            n = IntArrayList.this.size;
            return n;
        }

        @Override
        public int characteristics() {
            return 16720;
        }

        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            if (this.pos >= this.getWorkingMax()) {
                return false;
            }
            action.accept(IntArrayList.this.a[this.pos++]);
            return true;
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
            int max = this.getWorkingMax();
            while (this.pos < max) {
                action.accept(IntArrayList.this.a[this.pos]);
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
        public IntSpliterator trySplit() {
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

