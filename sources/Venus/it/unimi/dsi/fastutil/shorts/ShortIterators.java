/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntPredicate;

public final class ShortIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private ShortIterators() {
    }

    public static ShortListIterator singleton(short s) {
        return new SingletonIterator(s);
    }

    public static ShortListIterator wrap(short[] sArray, int n, int n2) {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        return new ArrayIterator(sArray, n, n2);
    }

    public static ShortListIterator wrap(short[] sArray) {
        return new ArrayIterator(sArray, 0, sArray.length);
    }

    public static int unwrap(ShortIterator shortIterator, short[] sArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > sArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && shortIterator.hasNext()) {
            sArray[n++] = shortIterator.nextShort();
        }
        return n2 - n3 - 1;
    }

    public static int unwrap(ShortIterator shortIterator, short[] sArray) {
        return ShortIterators.unwrap(shortIterator, sArray, 0, sArray.length);
    }

    public static short[] unwrap(ShortIterator shortIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        short[] sArray = new short[16];
        int n2 = 0;
        while (n-- != 0 && shortIterator.hasNext()) {
            if (n2 == sArray.length) {
                sArray = ShortArrays.grow(sArray, n2 + 1);
            }
            sArray[n2++] = shortIterator.nextShort();
        }
        return ShortArrays.trim(sArray, n2);
    }

    public static short[] unwrap(ShortIterator shortIterator) {
        return ShortIterators.unwrap(shortIterator, Integer.MAX_VALUE);
    }

    public static int unwrap(ShortIterator shortIterator, ShortCollection shortCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && shortIterator.hasNext()) {
            shortCollection.add(shortIterator.nextShort());
        }
        return n - n2 - 1;
    }

    public static long unwrap(ShortIterator shortIterator, ShortCollection shortCollection) {
        long l = 0L;
        while (shortIterator.hasNext()) {
            shortCollection.add(shortIterator.nextShort());
            ++l;
        }
        return l;
    }

    public static int pour(ShortIterator shortIterator, ShortCollection shortCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && shortIterator.hasNext()) {
            shortCollection.add(shortIterator.nextShort());
        }
        return n - n2 - 1;
    }

    public static int pour(ShortIterator shortIterator, ShortCollection shortCollection) {
        return ShortIterators.pour(shortIterator, shortCollection, Integer.MAX_VALUE);
    }

    public static ShortList pour(ShortIterator shortIterator, int n) {
        ShortArrayList shortArrayList = new ShortArrayList();
        ShortIterators.pour(shortIterator, shortArrayList, n);
        shortArrayList.trim();
        return shortArrayList;
    }

    public static ShortList pour(ShortIterator shortIterator) {
        return ShortIterators.pour(shortIterator, Integer.MAX_VALUE);
    }

    public static ShortIterator asShortIterator(Iterator iterator2) {
        if (iterator2 instanceof ShortIterator) {
            return (ShortIterator)iterator2;
        }
        return new IteratorWrapper(iterator2);
    }

    public static ShortListIterator asShortIterator(ListIterator listIterator2) {
        if (listIterator2 instanceof ShortListIterator) {
            return (ShortListIterator)listIterator2;
        }
        return new ListIteratorWrapper(listIterator2);
    }

    public static boolean any(ShortIterator shortIterator, IntPredicate intPredicate) {
        return ShortIterators.indexOf(shortIterator, intPredicate) != -1;
    }

    public static boolean all(ShortIterator shortIterator, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        do {
            if (shortIterator.hasNext()) continue;
            return false;
        } while (intPredicate.test(shortIterator.nextShort()));
        return true;
    }

    public static int indexOf(ShortIterator shortIterator, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        int n = 0;
        while (shortIterator.hasNext()) {
            if (intPredicate.test(shortIterator.nextShort())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static ShortListIterator fromTo(short s, short s2) {
        return new IntervalIterator(s, s2);
    }

    public static ShortIterator concat(ShortIterator[] shortIteratorArray) {
        return ShortIterators.concat(shortIteratorArray, 0, shortIteratorArray.length);
    }

    public static ShortIterator concat(ShortIterator[] shortIteratorArray, int n, int n2) {
        return new IteratorConcatenator(shortIteratorArray, n, n2);
    }

    public static ShortIterator unmodifiable(ShortIterator shortIterator) {
        return new UnmodifiableIterator(shortIterator);
    }

    public static ShortBidirectionalIterator unmodifiable(ShortBidirectionalIterator shortBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator(shortBidirectionalIterator);
    }

    public static ShortListIterator unmodifiable(ShortListIterator shortListIterator) {
        return new UnmodifiableListIterator(shortListIterator);
    }

    public static ShortIterator wrap(ByteIterator byteIterator) {
        return new ByteIteratorWrapper(byteIterator);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected static class ByteIteratorWrapper
    implements ShortIterator {
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
        public Short next() {
            return this.iterator.nextByte();
        }

        @Override
        public short nextShort() {
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
    implements ShortListIterator {
        protected final ShortListIterator i;

        public UnmodifiableListIterator(ShortListIterator shortListIterator) {
            this.i = shortListIterator;
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
        public short nextShort() {
            return this.i.nextShort();
        }

        @Override
        public short previousShort() {
            return this.i.previousShort();
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
    implements ShortBidirectionalIterator {
        protected final ShortBidirectionalIterator i;

        public UnmodifiableBidirectionalIterator(ShortBidirectionalIterator shortBidirectionalIterator) {
            this.i = shortBidirectionalIterator;
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
        public short nextShort() {
            return this.i.nextShort();
        }

        @Override
        public short previousShort() {
            return this.i.previousShort();
        }
    }

    public static class UnmodifiableIterator
    implements ShortIterator {
        protected final ShortIterator i;

        public UnmodifiableIterator(ShortIterator shortIterator) {
            this.i = shortIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public short nextShort() {
            return this.i.nextShort();
        }
    }

    private static class IteratorConcatenator
    implements ShortIterator {
        final ShortIterator[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(ShortIterator[] shortIteratorArray, int n, int n2) {
            this.a = shortIteratorArray;
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
        public short nextShort() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            short s = this.a[this.lastOffset].nextShort();
            this.advance();
            return s;
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
    implements ShortListIterator {
        private final short from;
        private final short to;
        short curr;

        public IntervalIterator(short s, short s2) {
            this.from = this.curr = s;
            this.to = s2;
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
        public short nextShort() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            short s = this.curr;
            this.curr = (short)(s + 1);
            return s;
        }

        @Override
        public short previousShort() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = (short)(this.curr - 1);
            return this.curr;
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
                this.curr = (short)(this.curr + n);
                return n;
            }
            n = this.to - this.curr;
            this.curr = this.to;
            return n;
        }

        @Override
        public int back(int n) {
            if (this.curr - n >= this.from) {
                this.curr = (short)(this.curr - n);
                return n;
            }
            n = this.curr - this.from;
            this.curr = this.from;
            return n;
        }
    }

    private static class ListIteratorWrapper
    implements ShortListIterator {
        final ListIterator<Short> i;

        public ListIteratorWrapper(ListIterator<Short> listIterator2) {
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
        public void set(short s) {
            this.i.set(s);
        }

        @Override
        public void add(short s) {
            this.i.add(s);
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public short nextShort() {
            return this.i.next();
        }

        @Override
        public short previousShort() {
            return this.i.previous();
        }
    }

    private static class IteratorWrapper
    implements ShortIterator {
        final Iterator<Short> i;

        public IteratorWrapper(Iterator<Short> iterator2) {
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
        public short nextShort() {
            return this.i.next();
        }
    }

    private static class ArrayIterator
    implements ShortListIterator {
        private final short[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(short[] sArray, int n, int n2) {
            this.array = sArray;
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
        public short nextShort() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public short previousShort() {
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
    implements ShortListIterator {
        private final short element;
        private int curr;

        public SingletonIterator(short s) {
            this.element = s;
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
        public short nextShort() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public short previousShort() {
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
    implements ShortListIterator,
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
        public short nextShort() {
            throw new NoSuchElementException();
        }

        @Override
        public short previousShort() {
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

