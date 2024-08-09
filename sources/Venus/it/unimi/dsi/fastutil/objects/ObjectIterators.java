/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

public final class ObjectIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private ObjectIterators() {
    }

    public static <K> ObjectIterator<K> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    public static <K> ObjectListIterator<K> singleton(K k) {
        return new SingletonIterator<K>(k);
    }

    public static <K> ObjectListIterator<K> wrap(K[] KArray, int n, int n2) {
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        return new ArrayIterator<K>(KArray, n, n2);
    }

    public static <K> ObjectListIterator<K> wrap(K[] KArray) {
        return new ArrayIterator<K>(KArray, 0, KArray.length);
    }

    public static <K> int unwrap(Iterator<? extends K> iterator2, K[] KArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > KArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && iterator2.hasNext()) {
            KArray[n++] = iterator2.next();
        }
        return n2 - n3 - 1;
    }

    public static <K> int unwrap(Iterator<? extends K> iterator2, K[] KArray) {
        return ObjectIterators.unwrap(iterator2, KArray, 0, KArray.length);
    }

    public static <K> K[] unwrap(Iterator<? extends K> iterator2, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        Object[] objectArray = new Object[16];
        int n2 = 0;
        while (n-- != 0 && iterator2.hasNext()) {
            if (n2 == objectArray.length) {
                objectArray = ObjectArrays.grow(objectArray, n2 + 1);
            }
            objectArray[n2++] = iterator2.next();
        }
        return ObjectArrays.trim(objectArray, n2);
    }

    public static <K> K[] unwrap(Iterator<? extends K> iterator2) {
        return ObjectIterators.unwrap(iterator2, Integer.MAX_VALUE);
    }

    public static <K> int unwrap(Iterator<K> iterator2, ObjectCollection<? super K> objectCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && iterator2.hasNext()) {
            objectCollection.add(iterator2.next());
        }
        return n - n2 - 1;
    }

    public static <K> long unwrap(Iterator<K> iterator2, ObjectCollection<? super K> objectCollection) {
        long l = 0L;
        while (iterator2.hasNext()) {
            objectCollection.add(iterator2.next());
            ++l;
        }
        return l;
    }

    public static <K> int pour(Iterator<K> iterator2, ObjectCollection<? super K> objectCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && iterator2.hasNext()) {
            objectCollection.add(iterator2.next());
        }
        return n - n2 - 1;
    }

    public static <K> int pour(Iterator<K> iterator2, ObjectCollection<? super K> objectCollection) {
        return ObjectIterators.pour(iterator2, objectCollection, Integer.MAX_VALUE);
    }

    public static <K> ObjectList<K> pour(Iterator<K> iterator2, int n) {
        ObjectArrayList objectArrayList = new ObjectArrayList();
        ObjectIterators.pour(iterator2, objectArrayList, n);
        objectArrayList.trim();
        return objectArrayList;
    }

    public static <K> ObjectList<K> pour(Iterator<K> iterator2) {
        return ObjectIterators.pour(iterator2, Integer.MAX_VALUE);
    }

    public static <K> ObjectIterator<K> asObjectIterator(Iterator<K> iterator2) {
        if (iterator2 instanceof ObjectIterator) {
            return (ObjectIterator)iterator2;
        }
        return new IteratorWrapper<K>(iterator2);
    }

    public static <K> ObjectListIterator<K> asObjectIterator(ListIterator<K> listIterator2) {
        if (listIterator2 instanceof ObjectListIterator) {
            return (ObjectListIterator)listIterator2;
        }
        return new ListIteratorWrapper<K>(listIterator2);
    }

    public static <K> boolean any(ObjectIterator<K> objectIterator, Predicate<? super K> predicate) {
        return ObjectIterators.indexOf(objectIterator, predicate) != -1;
    }

    public static <K> boolean all(ObjectIterator<K> objectIterator, Predicate<? super K> predicate) {
        Objects.requireNonNull(predicate);
        do {
            if (objectIterator.hasNext()) continue;
            return false;
        } while (predicate.test(objectIterator.next()));
        return true;
    }

    public static <K> int indexOf(ObjectIterator<K> objectIterator, Predicate<? super K> predicate) {
        Objects.requireNonNull(predicate);
        int n = 0;
        while (objectIterator.hasNext()) {
            if (predicate.test(objectIterator.next())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>[] objectIteratorArray) {
        return ObjectIterators.concat(objectIteratorArray, 0, objectIteratorArray.length);
    }

    public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>[] objectIteratorArray, int n, int n2) {
        return new IteratorConcatenator<K>(objectIteratorArray, n, n2);
    }

    public static <K> ObjectIterator<K> unmodifiable(ObjectIterator<K> objectIterator) {
        return new UnmodifiableIterator<K>(objectIterator);
    }

    public static <K> ObjectBidirectionalIterator<K> unmodifiable(ObjectBidirectionalIterator<K> objectBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator<K>(objectBidirectionalIterator);
    }

    public static <K> ObjectListIterator<K> unmodifiable(ObjectListIterator<K> objectListIterator) {
        return new UnmodifiableListIterator<K>(objectListIterator);
    }

    public static class UnmodifiableListIterator<K>
    implements ObjectListIterator<K> {
        protected final ObjectListIterator<K> i;

        public UnmodifiableListIterator(ObjectListIterator<K> objectListIterator) {
            this.i = objectListIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }

        @Override
        public K next() {
            return (K)this.i.next();
        }

        @Override
        public K previous() {
            return this.i.previous();
        }

        @Override
        public int nextIndex() {
            return this.i.nextIndex();
        }

        @Override
        public int previousIndex() {
            return this.i.previousIndex();
        }
    }

    public static class UnmodifiableBidirectionalIterator<K>
    implements ObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<K> i;

        public UnmodifiableBidirectionalIterator(ObjectBidirectionalIterator<K> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }

        @Override
        public K next() {
            return (K)this.i.next();
        }

        @Override
        public K previous() {
            return this.i.previous();
        }
    }

    public static class UnmodifiableIterator<K>
    implements ObjectIterator<K> {
        protected final ObjectIterator<K> i;

        public UnmodifiableIterator(ObjectIterator<K> objectIterator) {
            this.i = objectIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public K next() {
            return (K)this.i.next();
        }
    }

    private static class IteratorConcatenator<K>
    implements ObjectIterator<K> {
        final ObjectIterator<? extends K>[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(ObjectIterator<? extends K>[] objectIteratorArray, int n, int n2) {
            this.a = objectIteratorArray;
            this.offset = n;
            this.length = n2;
            this.advance();
        }

        private void advance() {
            while (this.length != 0 && !this.a[this.offset].hasNext()) {
                --this.length;
                ++this.offset;
            }
        }

        @Override
        public boolean hasNext() {
            return this.length > 0;
        }

        @Override
        public K next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            Object e = this.a[this.lastOffset].next();
            this.advance();
            return (K)e;
        }

        @Override
        public void remove() {
            if (this.lastOffset == -1) {
                throw new IllegalStateException();
            }
            this.a[this.lastOffset].remove();
        }

        @Override
        public int skip(int n) {
            this.lastOffset = -1;
            int n2 = 0;
            while (n2 < n && this.length != 0) {
                n2 += this.a[this.offset].skip(n - n2);
                if (this.a[this.offset].hasNext()) break;
                --this.length;
                ++this.offset;
            }
            return n2;
        }
    }

    private static class ListIteratorWrapper<K>
    implements ObjectListIterator<K> {
        final ListIterator<K> i;

        public ListIteratorWrapper(ListIterator<K> listIterator2) {
            this.i = listIterator2;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }

        @Override
        public int nextIndex() {
            return this.i.nextIndex();
        }

        @Override
        public int previousIndex() {
            return this.i.previousIndex();
        }

        @Override
        public void set(K k) {
            this.i.set(k);
        }

        @Override
        public void add(K k) {
            this.i.add(k);
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public K next() {
            return this.i.next();
        }

        @Override
        public K previous() {
            return this.i.previous();
        }
    }

    private static class IteratorWrapper<K>
    implements ObjectIterator<K> {
        final Iterator<K> i;

        public IteratorWrapper(Iterator<K> iterator2) {
            this.i = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public K next() {
            return this.i.next();
        }
    }

    private static class ArrayIterator<K>
    implements ObjectListIterator<K> {
        private final K[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(K[] KArray, int n, int n2) {
            this.array = KArray;
            this.offset = n;
            this.length = n2;
        }

        @Override
        public boolean hasNext() {
            return this.curr < this.length;
        }

        @Override
        public boolean hasPrevious() {
            return this.curr > 0;
        }

        @Override
        public K next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public K previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + --this.curr];
        }

        @Override
        public int skip(int n) {
            if (n <= this.length - this.curr) {
                this.curr += n;
                return n;
            }
            n = this.length - this.curr;
            this.curr = this.length;
            return n;
        }

        @Override
        public int back(int n) {
            if (n <= this.curr) {
                this.curr -= n;
                return n;
            }
            n = this.curr;
            this.curr = 0;
            return n;
        }

        @Override
        public int nextIndex() {
            return this.curr;
        }

        @Override
        public int previousIndex() {
            return this.curr - 1;
        }
    }

    private static class SingletonIterator<K>
    implements ObjectListIterator<K> {
        private final K element;
        private int curr;

        public SingletonIterator(K k) {
            this.element = k;
        }

        @Override
        public boolean hasNext() {
            return this.curr == 0;
        }

        @Override
        public boolean hasPrevious() {
            return this.curr == 1;
        }

        @Override
        public K next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public K previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = 0;
            return this.element;
        }

        @Override
        public int nextIndex() {
            return this.curr;
        }

        @Override
        public int previousIndex() {
            return this.curr - 1;
        }
    }

    public static class EmptyIterator<K>
    implements ObjectListIterator<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyIterator() {
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public boolean hasPrevious() {
            return true;
        }

        @Override
        public K next() {
            throw new NoSuchElementException();
        }

        @Override
        public K previous() {
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return 1;
        }

        @Override
        public int previousIndex() {
            return 1;
        }

        @Override
        public int skip(int n) {
            return 1;
        }

        @Override
        public int back(int n) {
            return 1;
        }

        public Object clone() {
            return EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return EMPTY_ITERATOR;
        }
    }
}

