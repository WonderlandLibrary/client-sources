/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.DoublePredicate;

public final class DoubleIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private DoubleIterators() {
    }

    public static DoubleListIterator singleton(double d) {
        return new SingletonIterator(d);
    }

    public static DoubleListIterator wrap(double[] dArray, int n, int n2) {
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        return new ArrayIterator(dArray, n, n2);
    }

    public static DoubleListIterator wrap(double[] dArray) {
        return new ArrayIterator(dArray, 0, dArray.length);
    }

    public static int unwrap(DoubleIterator doubleIterator, double[] dArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > dArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && doubleIterator.hasNext()) {
            dArray[n++] = doubleIterator.nextDouble();
        }
        return n2 - n3 - 1;
    }

    public static int unwrap(DoubleIterator doubleIterator, double[] dArray) {
        return DoubleIterators.unwrap(doubleIterator, dArray, 0, dArray.length);
    }

    public static double[] unwrap(DoubleIterator doubleIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        double[] dArray = new double[16];
        int n2 = 0;
        while (n-- != 0 && doubleIterator.hasNext()) {
            if (n2 == dArray.length) {
                dArray = DoubleArrays.grow(dArray, n2 + 1);
            }
            dArray[n2++] = doubleIterator.nextDouble();
        }
        return DoubleArrays.trim(dArray, n2);
    }

    public static double[] unwrap(DoubleIterator doubleIterator) {
        return DoubleIterators.unwrap(doubleIterator, Integer.MAX_VALUE);
    }

    public static int unwrap(DoubleIterator doubleIterator, DoubleCollection doubleCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && doubleIterator.hasNext()) {
            doubleCollection.add(doubleIterator.nextDouble());
        }
        return n - n2 - 1;
    }

    public static long unwrap(DoubleIterator doubleIterator, DoubleCollection doubleCollection) {
        long l = 0L;
        while (doubleIterator.hasNext()) {
            doubleCollection.add(doubleIterator.nextDouble());
            ++l;
        }
        return l;
    }

    public static int pour(DoubleIterator doubleIterator, DoubleCollection doubleCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && doubleIterator.hasNext()) {
            doubleCollection.add(doubleIterator.nextDouble());
        }
        return n - n2 - 1;
    }

    public static int pour(DoubleIterator doubleIterator, DoubleCollection doubleCollection) {
        return DoubleIterators.pour(doubleIterator, doubleCollection, Integer.MAX_VALUE);
    }

    public static DoubleList pour(DoubleIterator doubleIterator, int n) {
        DoubleArrayList doubleArrayList = new DoubleArrayList();
        DoubleIterators.pour(doubleIterator, doubleArrayList, n);
        doubleArrayList.trim();
        return doubleArrayList;
    }

    public static DoubleList pour(DoubleIterator doubleIterator) {
        return DoubleIterators.pour(doubleIterator, Integer.MAX_VALUE);
    }

    public static DoubleIterator asDoubleIterator(Iterator iterator2) {
        if (iterator2 instanceof DoubleIterator) {
            return (DoubleIterator)iterator2;
        }
        return new IteratorWrapper(iterator2);
    }

    public static DoubleListIterator asDoubleIterator(ListIterator listIterator2) {
        if (listIterator2 instanceof DoubleListIterator) {
            return (DoubleListIterator)listIterator2;
        }
        return new ListIteratorWrapper(listIterator2);
    }

    public static boolean any(DoubleIterator doubleIterator, DoublePredicate doublePredicate) {
        return DoubleIterators.indexOf(doubleIterator, doublePredicate) != -1;
    }

    public static boolean all(DoubleIterator doubleIterator, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        do {
            if (doubleIterator.hasNext()) continue;
            return false;
        } while (doublePredicate.test(doubleIterator.nextDouble()));
        return true;
    }

    public static int indexOf(DoubleIterator doubleIterator, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        int n = 0;
        while (doubleIterator.hasNext()) {
            if (doublePredicate.test(doubleIterator.nextDouble())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static DoubleIterator concat(DoubleIterator[] doubleIteratorArray) {
        return DoubleIterators.concat(doubleIteratorArray, 0, doubleIteratorArray.length);
    }

    public static DoubleIterator concat(DoubleIterator[] doubleIteratorArray, int n, int n2) {
        return new IteratorConcatenator(doubleIteratorArray, n, n2);
    }

    public static DoubleIterator unmodifiable(DoubleIterator doubleIterator) {
        return new UnmodifiableIterator(doubleIterator);
    }

    public static DoubleBidirectionalIterator unmodifiable(DoubleBidirectionalIterator doubleBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator(doubleBidirectionalIterator);
    }

    public static DoubleListIterator unmodifiable(DoubleListIterator doubleListIterator) {
        return new UnmodifiableListIterator(doubleListIterator);
    }

    public static DoubleIterator wrap(ByteIterator byteIterator) {
        return new ByteIteratorWrapper(byteIterator);
    }

    public static DoubleIterator wrap(ShortIterator shortIterator) {
        return new ShortIteratorWrapper(shortIterator);
    }

    public static DoubleIterator wrap(IntIterator intIterator) {
        return new IntIteratorWrapper(intIterator);
    }

    public static DoubleIterator wrap(FloatIterator floatIterator) {
        return new FloatIteratorWrapper(floatIterator);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected static class FloatIteratorWrapper
    implements DoubleIterator {
        final FloatIterator iterator;

        public FloatIteratorWrapper(FloatIterator floatIterator) {
            this.iterator = floatIterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        @Deprecated
        public Double next() {
            return this.iterator.nextFloat();
        }

        @Override
        public double nextDouble() {
            return this.iterator.nextFloat();
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
    protected static class IntIteratorWrapper
    implements DoubleIterator {
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
        public Double next() {
            return this.iterator.nextInt();
        }

        @Override
        public double nextDouble() {
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
    implements DoubleIterator {
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
        public Double next() {
            return this.iterator.nextShort();
        }

        @Override
        public double nextDouble() {
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
    implements DoubleIterator {
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
        public Double next() {
            return this.iterator.nextByte();
        }

        @Override
        public double nextDouble() {
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
    implements DoubleListIterator {
        protected final DoubleListIterator i;

        public UnmodifiableListIterator(DoubleListIterator doubleListIterator) {
            this.i = doubleListIterator;
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
        public double nextDouble() {
            return this.i.nextDouble();
        }

        @Override
        public double previousDouble() {
            return this.i.previousDouble();
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
    implements DoubleBidirectionalIterator {
        protected final DoubleBidirectionalIterator i;

        public UnmodifiableBidirectionalIterator(DoubleBidirectionalIterator doubleBidirectionalIterator) {
            this.i = doubleBidirectionalIterator;
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
        public double nextDouble() {
            return this.i.nextDouble();
        }

        @Override
        public double previousDouble() {
            return this.i.previousDouble();
        }
    }

    public static class UnmodifiableIterator
    implements DoubleIterator {
        protected final DoubleIterator i;

        public UnmodifiableIterator(DoubleIterator doubleIterator) {
            this.i = doubleIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public double nextDouble() {
            return this.i.nextDouble();
        }
    }

    private static class IteratorConcatenator
    implements DoubleIterator {
        final DoubleIterator[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(DoubleIterator[] doubleIteratorArray, int n, int n2) {
            this.a = doubleIteratorArray;
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
        public double nextDouble() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            double d = this.a[this.lastOffset].nextDouble();
            this.advance();
            return d;
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

    private static class ListIteratorWrapper
    implements DoubleListIterator {
        final ListIterator<Double> i;

        public ListIteratorWrapper(ListIterator<Double> listIterator2) {
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
        public void set(double d) {
            this.i.set(d);
        }

        @Override
        public void add(double d) {
            this.i.add(d);
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public double nextDouble() {
            return this.i.next();
        }

        @Override
        public double previousDouble() {
            return this.i.previous();
        }
    }

    private static class IteratorWrapper
    implements DoubleIterator {
        final Iterator<Double> i;

        public IteratorWrapper(Iterator<Double> iterator2) {
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
        public double nextDouble() {
            return this.i.next();
        }
    }

    private static class ArrayIterator
    implements DoubleListIterator {
        private final double[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(double[] dArray, int n, int n2) {
            this.array = dArray;
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
        public double nextDouble() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public double previousDouble() {
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
    implements DoubleListIterator {
        private final double element;
        private int curr;

        public SingletonIterator(double d) {
            this.element = d;
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
        public double nextDouble() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public double previousDouble() {
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
    implements DoubleListIterator,
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
        public double nextDouble() {
            throw new NoSuchElementException();
        }

        @Override
        public double previousDouble() {
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

