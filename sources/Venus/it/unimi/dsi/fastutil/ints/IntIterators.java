/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntPredicate;

public final class IntIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private IntIterators() {
    }

    public static IntListIterator singleton(int n) {
        return new SingletonIterator(n);
    }

    public static IntListIterator wrap(int[] nArray, int n, int n2) {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        return new ArrayIterator(nArray, n, n2);
    }

    public static IntListIterator wrap(int[] nArray) {
        return new ArrayIterator(nArray, 0, nArray.length);
    }

    public static int unwrap(IntIterator intIterator, int[] nArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > nArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && intIterator.hasNext()) {
            nArray[n++] = intIterator.nextInt();
        }
        return n2 - n3 - 1;
    }

    public static int unwrap(IntIterator intIterator, int[] nArray) {
        return IntIterators.unwrap(intIterator, nArray, 0, nArray.length);
    }

    public static int[] unwrap(IntIterator intIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int[] nArray = new int[16];
        int n2 = 0;
        while (n-- != 0 && intIterator.hasNext()) {
            if (n2 == nArray.length) {
                nArray = IntArrays.grow(nArray, n2 + 1);
            }
            nArray[n2++] = intIterator.nextInt();
        }
        return IntArrays.trim(nArray, n2);
    }

    public static int[] unwrap(IntIterator intIterator) {
        return IntIterators.unwrap(intIterator, Integer.MAX_VALUE);
    }

    public static int unwrap(IntIterator intIterator, IntCollection intCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && intIterator.hasNext()) {
            intCollection.add(intIterator.nextInt());
        }
        return n - n2 - 1;
    }

    public static long unwrap(IntIterator intIterator, IntCollection intCollection) {
        long l = 0L;
        while (intIterator.hasNext()) {
            intCollection.add(intIterator.nextInt());
            ++l;
        }
        return l;
    }

    public static int pour(IntIterator intIterator, IntCollection intCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && intIterator.hasNext()) {
            intCollection.add(intIterator.nextInt());
        }
        return n - n2 - 1;
    }

    public static int pour(IntIterator intIterator, IntCollection intCollection) {
        return IntIterators.pour(intIterator, intCollection, Integer.MAX_VALUE);
    }

    public static IntList pour(IntIterator intIterator, int n) {
        IntArrayList intArrayList = new IntArrayList();
        IntIterators.pour(intIterator, intArrayList, n);
        intArrayList.trim();
        return intArrayList;
    }

    public static IntList pour(IntIterator intIterator) {
        return IntIterators.pour(intIterator, Integer.MAX_VALUE);
    }

    public static IntIterator asIntIterator(Iterator iterator2) {
        if (iterator2 instanceof IntIterator) {
            return (IntIterator)iterator2;
        }
        return new IteratorWrapper(iterator2);
    }

    public static IntListIterator asIntIterator(ListIterator listIterator2) {
        if (listIterator2 instanceof IntListIterator) {
            return (IntListIterator)listIterator2;
        }
        return new ListIteratorWrapper(listIterator2);
    }

    public static boolean any(IntIterator intIterator, IntPredicate intPredicate) {
        return IntIterators.indexOf(intIterator, intPredicate) != -1;
    }

    public static boolean all(IntIterator intIterator, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        do {
            if (intIterator.hasNext()) continue;
            return false;
        } while (intPredicate.test(intIterator.nextInt()));
        return true;
    }

    public static int indexOf(IntIterator intIterator, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        int n = 0;
        while (intIterator.hasNext()) {
            if (intPredicate.test(intIterator.nextInt())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static IntListIterator fromTo(int n, int n2) {
        return new IntervalIterator(n, n2);
    }

    public static IntIterator concat(IntIterator[] intIteratorArray) {
        return IntIterators.concat(intIteratorArray, 0, intIteratorArray.length);
    }

    public static IntIterator concat(IntIterator[] intIteratorArray, int n, int n2) {
        return new IteratorConcatenator(intIteratorArray, n, n2);
    }

    public static IntIterator unmodifiable(IntIterator intIterator) {
        return new UnmodifiableIterator(intIterator);
    }

    public static IntBidirectionalIterator unmodifiable(IntBidirectionalIterator intBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator(intBidirectionalIterator);
    }

    public static IntListIterator unmodifiable(IntListIterator intListIterator) {
        return new UnmodifiableListIterator(intListIterator);
    }

    public static IntIterator wrap(ByteIterator byteIterator) {
        return new ByteIteratorWrapper(byteIterator);
    }

    public static IntIterator wrap(ShortIterator shortIterator) {
        return new ShortIteratorWrapper(shortIterator);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected static class ShortIteratorWrapper
    implements IntIterator {
        final ShortIterator iterator;

        public ShortIteratorWrapper(ShortIterator shortIterator) {
            this.iterator = shortIterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        @Deprecated
        public Integer next() {
            return this.iterator.nextShort();
        }

        @Override
        public int nextInt() {
            return this.iterator.nextShort();
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }

        @Override
        public int skip(int n) {
            return this.iterator.skip(n);
        }

        @Override
        @Deprecated
        public Object next() {
            return this.next();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected static class ByteIteratorWrapper
    implements IntIterator {
        final ByteIterator iterator;

        public ByteIteratorWrapper(ByteIterator byteIterator) {
            this.iterator = byteIterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        @Deprecated
        public Integer next() {
            return this.iterator.nextByte();
        }

        @Override
        public int nextInt() {
            return this.iterator.nextByte();
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }

        @Override
        public int skip(int n) {
            return this.iterator.skip(n);
        }

        @Override
        @Deprecated
        public Object next() {
            return this.next();
        }
    }

    public static class UnmodifiableListIterator
    implements IntListIterator {
        protected final IntListIterator i;

        public UnmodifiableListIterator(IntListIterator intListIterator) {
            this.i = intListIterator;
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
        public int nextInt() {
            return this.i.nextInt();
        }

        @Override
        public int previousInt() {
            return this.i.previousInt();
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

    public static class UnmodifiableBidirectionalIterator
    implements IntBidirectionalIterator {
        protected final IntBidirectionalIterator i;

        public UnmodifiableBidirectionalIterator(IntBidirectionalIterator intBidirectionalIterator) {
            this.i = intBidirectionalIterator;
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
        public int nextInt() {
            return this.i.nextInt();
        }

        @Override
        public int previousInt() {
            return this.i.previousInt();
        }
    }

    public static class UnmodifiableIterator
    implements IntIterator {
        protected final IntIterator i;

        public UnmodifiableIterator(IntIterator intIterator) {
            this.i = intIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public int nextInt() {
            return this.i.nextInt();
        }
    }

    private static class IteratorConcatenator
    implements IntIterator {
        final IntIterator[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(IntIterator[] intIteratorArray, int n, int n2) {
            this.a = intIteratorArray;
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
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            int n = this.a[this.lastOffset].nextInt();
            this.advance();
            return n;
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

    private static class IntervalIterator
    implements IntListIterator {
        private final int from;
        private final int to;
        int curr;

        public IntervalIterator(int n, int n2) {
            this.from = this.curr = n;
            this.to = n2;
        }

        @Override
        public boolean hasNext() {
            return this.curr < this.to;
        }

        @Override
        public boolean hasPrevious() {
            return this.curr > this.from;
        }

        @Override
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.curr++;
        }

        @Override
        public int previousInt() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            return --this.curr;
        }

        @Override
        public int nextIndex() {
            return this.curr - this.from;
        }

        @Override
        public int previousIndex() {
            return this.curr - this.from - 1;
        }

        @Override
        public int skip(int n) {
            if (this.curr + n <= this.to) {
                this.curr += n;
                return n;
            }
            n = this.to - this.curr;
            this.curr = this.to;
            return n;
        }

        @Override
        public int back(int n) {
            if (this.curr - n >= this.from) {
                this.curr -= n;
                return n;
            }
            n = this.curr - this.from;
            this.curr = this.from;
            return n;
        }
    }

    private static class ListIteratorWrapper
    implements IntListIterator {
        final ListIterator<Integer> i;

        public ListIteratorWrapper(ListIterator<Integer> listIterator2) {
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
        public void set(int n) {
            this.i.set(n);
        }

        @Override
        public void add(int n) {
            this.i.add(n);
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public int nextInt() {
            return this.i.next();
        }

        @Override
        public int previousInt() {
            return this.i.previous();
        }
    }

    private static class IteratorWrapper
    implements IntIterator {
        final Iterator<Integer> i;

        public IteratorWrapper(Iterator<Integer> iterator2) {
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
        public int nextInt() {
            return this.i.next();
        }
    }

    private static class ArrayIterator
    implements IntListIterator {
        private final int[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(int[] nArray, int n, int n2) {
            this.array = nArray;
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
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public int previousInt() {
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

    private static class SingletonIterator
    implements IntListIterator {
        private final int element;
        private int curr;

        public SingletonIterator(int n) {
            this.element = n;
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
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public int previousInt() {
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

    public static class EmptyIterator
    implements IntListIterator,
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
        public int nextInt() {
            throw new NoSuchElementException();
        }

        @Override
        public int previousInt() {
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

