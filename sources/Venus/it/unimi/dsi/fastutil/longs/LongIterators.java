/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.LongPredicate;

public final class LongIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private LongIterators() {
    }

    public static LongListIterator singleton(long l) {
        return new SingletonIterator(l);
    }

    public static LongListIterator wrap(long[] lArray, int n, int n2) {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        return new ArrayIterator(lArray, n, n2);
    }

    public static LongListIterator wrap(long[] lArray) {
        return new ArrayIterator(lArray, 0, lArray.length);
    }

    public static int unwrap(LongIterator longIterator, long[] lArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > lArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && longIterator.hasNext()) {
            lArray[n++] = longIterator.nextLong();
        }
        return n2 - n3 - 1;
    }

    public static int unwrap(LongIterator longIterator, long[] lArray) {
        return LongIterators.unwrap(longIterator, lArray, 0, lArray.length);
    }

    public static long[] unwrap(LongIterator longIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        long[] lArray = new long[16];
        int n2 = 0;
        while (n-- != 0 && longIterator.hasNext()) {
            if (n2 == lArray.length) {
                lArray = LongArrays.grow(lArray, n2 + 1);
            }
            lArray[n2++] = longIterator.nextLong();
        }
        return LongArrays.trim(lArray, n2);
    }

    public static long[] unwrap(LongIterator longIterator) {
        return LongIterators.unwrap(longIterator, Integer.MAX_VALUE);
    }

    public static int unwrap(LongIterator longIterator, LongCollection longCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && longIterator.hasNext()) {
            longCollection.add(longIterator.nextLong());
        }
        return n - n2 - 1;
    }

    public static long unwrap(LongIterator longIterator, LongCollection longCollection) {
        long l = 0L;
        while (longIterator.hasNext()) {
            longCollection.add(longIterator.nextLong());
            ++l;
        }
        return l;
    }

    public static int pour(LongIterator longIterator, LongCollection longCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && longIterator.hasNext()) {
            longCollection.add(longIterator.nextLong());
        }
        return n - n2 - 1;
    }

    public static int pour(LongIterator longIterator, LongCollection longCollection) {
        return LongIterators.pour(longIterator, longCollection, Integer.MAX_VALUE);
    }

    public static LongList pour(LongIterator longIterator, int n) {
        LongArrayList longArrayList = new LongArrayList();
        LongIterators.pour(longIterator, longArrayList, n);
        longArrayList.trim();
        return longArrayList;
    }

    public static LongList pour(LongIterator longIterator) {
        return LongIterators.pour(longIterator, Integer.MAX_VALUE);
    }

    public static LongIterator asLongIterator(Iterator iterator2) {
        if (iterator2 instanceof LongIterator) {
            return (LongIterator)iterator2;
        }
        return new IteratorWrapper(iterator2);
    }

    public static LongListIterator asLongIterator(ListIterator listIterator2) {
        if (listIterator2 instanceof LongListIterator) {
            return (LongListIterator)listIterator2;
        }
        return new ListIteratorWrapper(listIterator2);
    }

    public static boolean any(LongIterator longIterator, LongPredicate longPredicate) {
        return LongIterators.indexOf(longIterator, longPredicate) != -1;
    }

    public static boolean all(LongIterator longIterator, LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        do {
            if (longIterator.hasNext()) continue;
            return false;
        } while (longPredicate.test(longIterator.nextLong()));
        return true;
    }

    public static int indexOf(LongIterator longIterator, LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        int n = 0;
        while (longIterator.hasNext()) {
            if (longPredicate.test(longIterator.nextLong())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static LongBidirectionalIterator fromTo(long l, long l2) {
        return new IntervalIterator(l, l2);
    }

    public static LongIterator concat(LongIterator[] longIteratorArray) {
        return LongIterators.concat(longIteratorArray, 0, longIteratorArray.length);
    }

    public static LongIterator concat(LongIterator[] longIteratorArray, int n, int n2) {
        return new IteratorConcatenator(longIteratorArray, n, n2);
    }

    public static LongIterator unmodifiable(LongIterator longIterator) {
        return new UnmodifiableIterator(longIterator);
    }

    public static LongBidirectionalIterator unmodifiable(LongBidirectionalIterator longBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator(longBidirectionalIterator);
    }

    public static LongListIterator unmodifiable(LongListIterator longListIterator) {
        return new UnmodifiableListIterator(longListIterator);
    }

    public static LongIterator wrap(ByteIterator byteIterator) {
        return new ByteIteratorWrapper(byteIterator);
    }

    public static LongIterator wrap(ShortIterator shortIterator) {
        return new ShortIteratorWrapper(shortIterator);
    }

    public static LongIterator wrap(IntIterator intIterator) {
        return new IntIteratorWrapper(intIterator);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected static class IntIteratorWrapper
    implements LongIterator {
        final IntIterator iterator;

        public IntIteratorWrapper(IntIterator intIterator) {
            this.iterator = intIterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        @Deprecated
        public Long next() {
            return this.iterator.nextInt();
        }

        @Override
        public long nextLong() {
            return this.iterator.nextInt();
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
    protected static class ShortIteratorWrapper
    implements LongIterator {
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
        public Long next() {
            return this.iterator.nextShort();
        }

        @Override
        public long nextLong() {
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
    implements LongIterator {
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
        public Long next() {
            return this.iterator.nextByte();
        }

        @Override
        public long nextLong() {
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
    implements LongListIterator {
        protected final LongListIterator i;

        public UnmodifiableListIterator(LongListIterator longListIterator) {
            this.i = longListIterator;
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
        public long nextLong() {
            return this.i.nextLong();
        }

        @Override
        public long previousLong() {
            return this.i.previousLong();
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
    implements LongBidirectionalIterator {
        protected final LongBidirectionalIterator i;

        public UnmodifiableBidirectionalIterator(LongBidirectionalIterator longBidirectionalIterator) {
            this.i = longBidirectionalIterator;
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
        public long nextLong() {
            return this.i.nextLong();
        }

        @Override
        public long previousLong() {
            return this.i.previousLong();
        }
    }

    public static class UnmodifiableIterator
    implements LongIterator {
        protected final LongIterator i;

        public UnmodifiableIterator(LongIterator longIterator) {
            this.i = longIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public long nextLong() {
            return this.i.nextLong();
        }
    }

    private static class IteratorConcatenator
    implements LongIterator {
        final LongIterator[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(LongIterator[] longIteratorArray, int n, int n2) {
            this.a = longIteratorArray;
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
        public long nextLong() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            long l = this.a[this.lastOffset].nextLong();
            this.advance();
            return l;
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
    implements LongBidirectionalIterator {
        private final long from;
        private final long to;
        long curr;

        public IntervalIterator(long l, long l2) {
            this.from = this.curr = l;
            this.to = l2;
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
        public long nextLong() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.curr++;
        }

        @Override
        public long previousLong() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            return --this.curr;
        }

        @Override
        public int skip(int n) {
            if (this.curr + (long)n <= this.to) {
                this.curr += (long)n;
                return n;
            }
            n = (int)(this.to - this.curr);
            this.curr = this.to;
            return n;
        }

        @Override
        public int back(int n) {
            if (this.curr - (long)n >= this.from) {
                this.curr -= (long)n;
                return n;
            }
            n = (int)(this.curr - this.from);
            this.curr = this.from;
            return n;
        }
    }

    private static class ListIteratorWrapper
    implements LongListIterator {
        final ListIterator<Long> i;

        public ListIteratorWrapper(ListIterator<Long> listIterator2) {
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
        public void set(long l) {
            this.i.set(l);
        }

        @Override
        public void add(long l) {
            this.i.add(l);
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public long nextLong() {
            return this.i.next();
        }

        @Override
        public long previousLong() {
            return this.i.previous();
        }
    }

    private static class IteratorWrapper
    implements LongIterator {
        final Iterator<Long> i;

        public IteratorWrapper(Iterator<Long> iterator2) {
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
        public long nextLong() {
            return this.i.next();
        }
    }

    private static class ArrayIterator
    implements LongListIterator {
        private final long[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(long[] lArray, int n, int n2) {
            this.array = lArray;
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
        public long nextLong() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public long previousLong() {
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
    implements LongListIterator {
        private final long element;
        private int curr;

        public SingletonIterator(long l) {
            this.element = l;
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
        public long nextLong() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public long previousLong() {
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
    implements LongListIterator,
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
        public long nextLong() {
            throw new NoSuchElementException();
        }

        @Override
        public long previousLong() {
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

