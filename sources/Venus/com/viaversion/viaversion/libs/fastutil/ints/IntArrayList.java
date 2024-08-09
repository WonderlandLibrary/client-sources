/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntArrayList
extends AbstractIntList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient int[] a;
    protected int size;
    static final boolean $assertionsDisabled = !IntArrayList.class.desiredAssertionStatus();

    private static final int[] copyArraySafe(int[] nArray, int n) {
        if (n == 0) {
            return IntArrays.EMPTY_ARRAY;
        }
        return java.util.Arrays.copyOf(nArray, n);
    }

    private static final int[] copyArrayFromSafe(IntArrayList intArrayList) {
        return IntArrayList.copyArraySafe(intArrayList.a, intArrayList.size);
    }

    protected IntArrayList(int[] nArray, boolean bl) {
        this.a = nArray;
    }

    private void initArrayFromCapacity(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? IntArrays.EMPTY_ARRAY : new int[n];
    }

    public IntArrayList(int n) {
        this.initArrayFromCapacity(n);
    }

    public IntArrayList() {
        this.a = IntArrays.DEFAULT_EMPTY_ARRAY;
    }

    public IntArrayList(Collection<? extends Integer> collection) {
        if (collection instanceof IntArrayList) {
            this.a = IntArrayList.copyArrayFromSafe((IntArrayList)collection);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(collection.size());
            if (collection instanceof IntList) {
                this.size = collection.size();
                ((IntList)collection).getElements(0, this.a, 0, this.size);
            } else {
                this.size = IntIterators.unwrap(IntIterators.asIntIterator(collection.iterator()), this.a);
            }
        }
    }

    public IntArrayList(IntCollection intCollection) {
        if (intCollection instanceof IntArrayList) {
            this.a = IntArrayList.copyArrayFromSafe((IntArrayList)intCollection);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(intCollection.size());
            if (intCollection instanceof IntList) {
                this.size = intCollection.size();
                ((IntList)intCollection).getElements(0, this.a, 0, this.size);
            } else {
                this.size = IntIterators.unwrap(intCollection.iterator(), this.a);
            }
        }
    }

    public IntArrayList(IntList intList) {
        if (intList instanceof IntArrayList) {
            this.a = IntArrayList.copyArrayFromSafe((IntArrayList)intList);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(intList.size());
            this.size = intList.size();
            intList.getElements(0, this.a, 0, this.size);
        }
    }

    public IntArrayList(int[] nArray) {
        this(nArray, 0, nArray.length);
    }

    public IntArrayList(int[] nArray, int n, int n2) {
        this(n2);
        System.arraycopy(nArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public IntArrayList(Iterator<? extends Integer> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((int)iterator2.next());
        }
    }

    public IntArrayList(IntIterator intIterator) {
        this();
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }

    public int[] elements() {
        return this.a;
    }

    public static IntArrayList wrap(int[] nArray, int n) {
        if (n > nArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + nArray.length + ")");
        }
        IntArrayList intArrayList = new IntArrayList(nArray, true);
        intArrayList.size = n;
        return intArrayList;
    }

    public static IntArrayList wrap(int[] nArray) {
        return IntArrayList.wrap(nArray, nArray.length);
    }

    public static IntArrayList of() {
        return new IntArrayList();
    }

    public static IntArrayList of(int ... nArray) {
        return IntArrayList.wrap(nArray);
    }

    public static IntArrayList toList(IntStream intStream) {
        return intStream.collect(IntArrayList::new, IntArrayList::add, IntList::addAll);
    }

    public static IntArrayList toListWithExpectedSize(IntStream intStream, int n) {
        if (n <= 10) {
            return IntArrayList.toList(intStream);
        }
        return intStream.collect(new IntCollections.SizeDecreasingSupplier<IntArrayList>(n, IntArrayList::lambda$toListWithExpectedSize$0), IntArrayList::add, IntList::addAll);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == IntArrays.DEFAULT_EMPTY_ARRAY && n <= 10) {
            return;
        }
        this.a = IntArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != IntArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = IntArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, int n2) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = n2;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(int n) {
        this.grow(this.size + 1);
        this.a[this.size++] = n;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public int getInt(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(int n) {
        for (int i = 0; i < this.size; ++i) {
            if (n != this.a[i]) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(int n) {
        int n2 = this.size;
        while (n2-- != 0) {
            if (n != this.a[n2]) continue;
            return n2;
        }
        return 1;
    }

    @Override
    public int removeInt(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        int n2 = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return n2;
    }

    @Override
    public boolean rem(int n) {
        int n2 = this.indexOf(n);
        if (n2 == -1) {
            return true;
        }
        this.removeInt(n2);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public int set(int n, int n2) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        int n3 = this.a[n];
        this.a[n] = n2;
        return n3;
    }

    @Override
    public void clear() {
        this.size = 0;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void size(int n) {
        if (n > this.a.length) {
            this.a = IntArrays.forceCapacity(this.a, n, this.size);
        }
        if (n > this.size) {
            java.util.Arrays.fill(this.a, this.size, n, 0);
        }
        this.size = n;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void trim() {
        this.trim(0);
    }

    public void trim(int n) {
        if (n >= this.a.length || this.size == this.a.length) {
            return;
        }
        int[] nArray = new int[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, nArray, 0, this.size);
        this.a = nArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public IntList subList(int n, int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new SubList(this, n, n2);
    }

    @Override
    public void getElements(int n, int[] nArray, int n2, int n3) {
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        System.arraycopy(this.a, n, nArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, int[] nArray, int n2, int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(nArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public void setElements(int n, int[] nArray, int n2, int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        if (n + n3 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size + ")");
        }
        System.arraycopy(nArray, n2, this.a, n, n3);
    }

    @Override
    public void forEach(IntConsumer intConsumer) {
        for (int i = 0; i < this.size; ++i) {
            intConsumer.accept(this.a[i]);
        }
    }

    @Override
    public boolean addAll(int n, IntCollection intCollection) {
        if (intCollection instanceof IntList) {
            return this.addAll(n, (IntList)intCollection);
        }
        this.ensureIndex(n);
        int n2 = intCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        IntIterator intIterator = intCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = intIterator.nextInt();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, IntList intList) {
        this.ensureIndex(n);
        int n2 = intList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        intList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(IntCollection intCollection) {
        int n;
        int[] nArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (intCollection.contains(nArray[n])) continue;
            nArray[n2++] = nArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public int[] toArray(int[] nArray) {
        if (nArray == null || nArray.length < this.size) {
            nArray = java.util.Arrays.copyOf(nArray, this.size);
        }
        System.arraycopy(this.a, 0, nArray, 0, this.size);
        return nArray;
    }

    @Override
    public IntListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new IntListIterator(){
            int pos;
            int last;
            final int val$index;
            final IntArrayList this$0;
            {
                this.this$0 = intArrayList;
                this.val$index = n;
                this.pos = this.val$index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.a[this.pos];
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
            public void add(int n) {
                this.this$0.add(this.pos++, n);
                this.last = -1;
            }

            @Override
            public void set(int n) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, n);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeInt(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }

            @Override
            public void forEachRemaining(IntConsumer intConsumer) {
                while (this.pos < this.this$0.size) {
                    ++this.pos;
                    this.last = this.last;
                    intConsumer.accept(this.this$0.a[this.last]);
                }
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = this.this$0.size - this.pos;
                if (n < n2) {
                    this.pos -= n;
                } else {
                    n = n2;
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
                int n2 = this.this$0.size - this.pos;
                if (n < n2) {
                    this.pos += n;
                } else {
                    n = n2;
                    this.pos = this.this$0.size;
                }
                this.last = this.pos - 1;
                return n;
            }

            @Override
            public void forEachRemaining(Object object) {
                this.forEachRemaining((IntConsumer)object);
            }
        };
    }

    @Override
    public IntSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override
    public void sort(IntComparator intComparator) {
        if (intComparator == null) {
            IntArrays.stableSort(this.a, 0, this.size);
        } else {
            IntArrays.stableSort(this.a, 0, this.size, intComparator);
        }
    }

    @Override
    public void unstableSort(IntComparator intComparator) {
        if (intComparator == null) {
            IntArrays.unstableSort(this.a, 0, this.size);
        } else {
            IntArrays.unstableSort(this.a, 0, this.size, intComparator);
        }
    }

    public IntArrayList clone() {
        IntArrayList intArrayList = null;
        if (this.getClass() == IntArrayList.class) {
            intArrayList = new IntArrayList(IntArrayList.copyArraySafe(this.a, this.size), false);
            intArrayList.size = this.size;
        } else {
            try {
                intArrayList = (IntArrayList)super.clone();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new InternalError(cloneNotSupportedException);
            }
            intArrayList.a = IntArrayList.copyArraySafe(this.a, this.size);
        }
        return intArrayList;
    }

    public boolean equals(IntArrayList intArrayList) {
        if (intArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != intArrayList.size()) {
            return true;
        }
        int[] nArray = this.a;
        int[] nArray2 = intArrayList.a;
        if (nArray == nArray2 && n == intArrayList.size()) {
            return false;
        }
        while (n-- != 0) {
            if (nArray[n] == nArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof List)) {
            return true;
        }
        if (object instanceof IntArrayList) {
            return this.equals((IntArrayList)object);
        }
        if (object instanceof SubList) {
            return ((SubList)object).equals(this);
        }
        return super.equals(object);
    }

    @Override
    public int compareTo(IntArrayList intArrayList) {
        int n;
        int n2 = this.size();
        int n3 = intArrayList.size();
        int[] nArray = this.a;
        int[] nArray2 = intArrayList.a;
        if (nArray == nArray2 && n2 == n3) {
            return 1;
        }
        for (n = 0; n < n2 && n < n3; ++n) {
            int n4 = nArray[n];
            int n5 = nArray2[n];
            int n6 = Integer.compare(n4, n5);
            if (n6 == 0) continue;
            return n6;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    @Override
    public int compareTo(List<? extends Integer> list) {
        if (list instanceof IntArrayList) {
            return this.compareTo((IntArrayList)list);
        }
        if (list instanceof SubList) {
            return -((SubList)list).compareTo(this);
        }
        return super.compareTo(list);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readInt();
        }
    }

    @Override
    public java.util.Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((List)object);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private static IntArrayList lambda$toListWithExpectedSize$0(int n) {
        return n <= 10 ? new IntArrayList() : new IntArrayList(n);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class SubList
    extends AbstractIntList.IntRandomAccessSubList {
        private static final long serialVersionUID = -3185226345314976296L;
        final IntArrayList this$0;

        protected SubList(IntArrayList intArrayList, int n, int n2) {
            this.this$0 = intArrayList;
            super(intArrayList, n, n2);
        }

        private int[] getParentArray() {
            return this.this$0.a;
        }

        @Override
        public int getInt(int n) {
            this.ensureRestrictedIndex(n);
            return this.this$0.a[n + this.from];
        }

        @Override
        public IntListIterator listIterator(int n) {
            return new SubListIterator(this, n);
        }

        @Override
        public IntSpliterator spliterator() {
            return new SubListSpliterator(this);
        }

        boolean contentsEquals(int[] nArray, int n, int n2) {
            if (this.this$0.a == nArray && this.from == n && this.to == n2) {
                return false;
            }
            if (n2 - n != this.size()) {
                return true;
            }
            int n3 = this.from;
            int n4 = n;
            while (n3 < this.to) {
                if (this.this$0.a[n3++] == nArray[n4++]) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null) {
                return true;
            }
            if (!(object instanceof List)) {
                return true;
            }
            if (object instanceof IntArrayList) {
                IntArrayList intArrayList = (IntArrayList)object;
                return this.contentsEquals(intArrayList.a, 0, intArrayList.size());
            }
            if (object instanceof SubList) {
                SubList subList = (SubList)object;
                return this.contentsEquals(subList.getParentArray(), subList.from, subList.to);
            }
            return super.equals(object);
        }

        int contentsCompareTo(int[] nArray, int n, int n2) {
            if (this.this$0.a == nArray && this.from == n && this.to == n2) {
                return 1;
            }
            int n3 = this.from;
            int n4 = n;
            while (n3 < this.to && n3 < n2) {
                int n5 = this.this$0.a[n3];
                int n6 = nArray[n4];
                int n7 = Integer.compare(n5, n6);
                if (n7 != 0) {
                    return n7;
                }
                ++n3;
                ++n4;
            }
            return n3 < n2 ? -1 : (n3 < this.to ? 1 : 0);
        }

        @Override
        public int compareTo(List<? extends Integer> list) {
            if (list instanceof IntArrayList) {
                IntArrayList intArrayList = (IntArrayList)list;
                return this.contentsCompareTo(intArrayList.a, 0, intArrayList.size());
            }
            if (list instanceof SubList) {
                SubList subList = (SubList)list;
                return this.contentsCompareTo(subList.getParentArray(), subList.from, subList.to);
            }
            return super.compareTo(list);
        }

        @Override
        public java.util.Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }

        private final class SubListIterator
        extends IntIterators.AbstractIndexBasedListIterator {
            final SubList this$1;

            SubListIterator(SubList subList, int n) {
                this.this$1 = subList;
                super(0, n);
            }

            @Override
            protected final int get(int n) {
                return this.this$1.this$0.a[this.this$1.from + n];
            }

            @Override
            protected final void add(int n, int n2) {
                this.this$1.add(n, n2);
            }

            @Override
            protected final void set(int n, int n2) {
                this.this$1.set(n, n2);
            }

            @Override
            protected final void remove(int n) {
                this.this$1.removeInt(n);
            }

            @Override
            protected final int getMaxPos() {
                return this.this$1.to - this.this$1.from;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = this.pos++;
                return this.this$1.this$0.a[this.this$1.from + this.lastReturned];
            }

            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = --this.pos;
                return this.this$1.this$0.a[this.this$1.from + this.pos];
            }

            @Override
            public void forEachRemaining(IntConsumer intConsumer) {
                int n = this.this$1.to - this.this$1.from;
                while (this.pos < n) {
                    ++this.pos;
                    this.lastReturned = this.lastReturned;
                    intConsumer.accept(this.this$1.this$0.a[this.this$1.from + this.lastReturned]);
                }
            }

            @Override
            public void forEachRemaining(Object object) {
                this.forEachRemaining((IntConsumer)object);
            }
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private final class SubListSpliterator
        extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
            final SubList this$1;

            SubListSpliterator(SubList subList) {
                this.this$1 = subList;
                super(subList.from);
            }

            private SubListSpliterator(SubList subList, int n, int n2) {
                this.this$1 = subList;
                super(n, n2);
            }

            @Override
            protected final int getMaxPosFromBackingStore() {
                return this.this$1.to;
            }

            @Override
            protected final int get(int n) {
                return this.this$1.this$0.a[n];
            }

            @Override
            protected final SubListSpliterator makeForSplit(int n, int n2) {
                return new SubListSpliterator(this.this$1, n, n2);
            }

            @Override
            public boolean tryAdvance(IntConsumer intConsumer) {
                if (this.pos >= this.getMaxPos()) {
                    return true;
                }
                intConsumer.accept(this.this$1.this$0.a[this.pos++]);
                return false;
            }

            @Override
            public void forEachRemaining(IntConsumer intConsumer) {
                int n = this.getMaxPos();
                while (this.pos < n) {
                    intConsumer.accept(this.this$1.this$0.a[this.pos++]);
                }
            }

            @Override
            protected IntSpliterator makeForSplit(int n, int n2) {
                return this.makeForSplit(n, n2);
            }

            @Override
            public void forEachRemaining(Object object) {
                this.forEachRemaining((IntConsumer)object);
            }

            @Override
            public boolean tryAdvance(Object object) {
                return this.tryAdvance((IntConsumer)object);
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class Spliterator
    implements IntSpliterator {
        boolean hasSplit;
        int pos;
        int max;
        static final boolean $assertionsDisabled = !IntArrayList.class.desiredAssertionStatus();
        final IntArrayList this$0;

        public Spliterator(IntArrayList intArrayList) {
            this(intArrayList, 0, intArrayList.size, false);
        }

        private Spliterator(IntArrayList intArrayList, int n, int n2, boolean bl) {
            this.this$0 = intArrayList;
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
            return 1;
        }

        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            if (this.pos >= this.getWorkingMax()) {
                return true;
            }
            intConsumer.accept(this.this$0.a[this.pos++]);
            return false;
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            int n = this.getWorkingMax();
            while (this.pos < n) {
                intConsumer.accept(this.this$0.a[this.pos]);
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
        public IntSpliterator trySplit() {
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
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
    }
}

