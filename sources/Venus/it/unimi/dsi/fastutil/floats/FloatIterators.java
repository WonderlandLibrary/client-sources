/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.DoublePredicate;

public final class FloatIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private FloatIterators() {
    }

    public static FloatListIterator singleton(float f) {
        return new SingletonIterator(f);
    }

    public static FloatListIterator wrap(float[] fArray, int n, int n2) {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        return new ArrayIterator(fArray, n, n2);
    }

    public static FloatListIterator wrap(float[] fArray) {
        return new ArrayIterator(fArray, 0, fArray.length);
    }

    public static int unwrap(FloatIterator floatIterator, float[] fArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > fArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && floatIterator.hasNext()) {
            fArray[n++] = floatIterator.nextFloat();
        }
        return n2 - n3 - 1;
    }

    public static int unwrap(FloatIterator floatIterator, float[] fArray) {
        return FloatIterators.unwrap(floatIterator, fArray, 0, fArray.length);
    }

    public static float[] unwrap(FloatIterator floatIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        float[] fArray = new float[16];
        int n2 = 0;
        while (n-- != 0 && floatIterator.hasNext()) {
            if (n2 == fArray.length) {
                fArray = FloatArrays.grow(fArray, n2 + 1);
            }
            fArray[n2++] = floatIterator.nextFloat();
        }
        return FloatArrays.trim(fArray, n2);
    }

    public static float[] unwrap(FloatIterator floatIterator) {
        return FloatIterators.unwrap(floatIterator, Integer.MAX_VALUE);
    }

    public static int unwrap(FloatIterator floatIterator, FloatCollection floatCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && floatIterator.hasNext()) {
            floatCollection.add(floatIterator.nextFloat());
        }
        return n - n2 - 1;
    }

    public static long unwrap(FloatIterator floatIterator, FloatCollection floatCollection) {
        long l = 0L;
        while (floatIterator.hasNext()) {
            floatCollection.add(floatIterator.nextFloat());
            ++l;
        }
        return l;
    }

    public static int pour(FloatIterator floatIterator, FloatCollection floatCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && floatIterator.hasNext()) {
            floatCollection.add(floatIterator.nextFloat());
        }
        return n - n2 - 1;
    }

    public static int pour(FloatIterator floatIterator, FloatCollection floatCollection) {
        return FloatIterators.pour(floatIterator, floatCollection, Integer.MAX_VALUE);
    }

    public static FloatList pour(FloatIterator floatIterator, int n) {
        FloatArrayList floatArrayList = new FloatArrayList();
        FloatIterators.pour(floatIterator, floatArrayList, n);
        floatArrayList.trim();
        return floatArrayList;
    }

    public static FloatList pour(FloatIterator floatIterator) {
        return FloatIterators.pour(floatIterator, Integer.MAX_VALUE);
    }

    public static FloatIterator asFloatIterator(Iterator iterator2) {
        if (iterator2 instanceof FloatIterator) {
            return (FloatIterator)iterator2;
        }
        return new IteratorWrapper(iterator2);
    }

    public static FloatListIterator asFloatIterator(ListIterator listIterator2) {
        if (listIterator2 instanceof FloatListIterator) {
            return (FloatListIterator)listIterator2;
        }
        return new ListIteratorWrapper(listIterator2);
    }

    public static boolean any(FloatIterator floatIterator, DoublePredicate doublePredicate) {
        return FloatIterators.indexOf(floatIterator, doublePredicate) != -1;
    }

    public static boolean all(FloatIterator floatIterator, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        do {
            if (floatIterator.hasNext()) continue;
            return false;
        } while (doublePredicate.test(floatIterator.nextFloat()));
        return true;
    }

    public static int indexOf(FloatIterator floatIterator, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        int n = 0;
        while (floatIterator.hasNext()) {
            if (doublePredicate.test(floatIterator.nextFloat())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static FloatIterator concat(FloatIterator[] floatIteratorArray) {
        return FloatIterators.concat(floatIteratorArray, 0, floatIteratorArray.length);
    }

    public static FloatIterator concat(FloatIterator[] floatIteratorArray, int n, int n2) {
        return new IteratorConcatenator(floatIteratorArray, n, n2);
    }

    public static FloatIterator unmodifiable(FloatIterator floatIterator) {
        return new UnmodifiableIterator(floatIterator);
    }

    public static FloatBidirectionalIterator unmodifiable(FloatBidirectionalIterator floatBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator(floatBidirectionalIterator);
    }

    public static FloatListIterator unmodifiable(FloatListIterator floatListIterator) {
        return new UnmodifiableListIterator(floatListIterator);
    }

    public static FloatIterator wrap(ByteIterator byteIterator) {
        return new ByteIteratorWrapper(byteIterator);
    }

    public static FloatIterator wrap(ShortIterator shortIterator) {
        return new ShortIteratorWrapper(shortIterator);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected static class ShortIteratorWrapper
    implements FloatIterator {
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
        public Float next() {
            return Float.valueOf(this.iterator.nextShort());
        }

        @Override
        public float nextFloat() {
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
    implements FloatIterator {
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
        public Float next() {
            return Float.valueOf(this.iterator.nextByte());
        }

        @Override
        public float nextFloat() {
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
    implements FloatListIterator {
        protected final FloatListIterator i;

        public UnmodifiableListIterator(FloatListIterator floatListIterator) {
            this.i = floatListIterator;
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
        public float nextFloat() {
            return this.i.nextFloat();
        }

        @Override
        public float previousFloat() {
            return this.i.previousFloat();
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
    implements FloatBidirectionalIterator {
        protected final FloatBidirectionalIterator i;

        public UnmodifiableBidirectionalIterator(FloatBidirectionalIterator floatBidirectionalIterator) {
            this.i = floatBidirectionalIterator;
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
        public float nextFloat() {
            return this.i.nextFloat();
        }

        @Override
        public float previousFloat() {
            return this.i.previousFloat();
        }
    }

    public static class UnmodifiableIterator
    implements FloatIterator {
        protected final FloatIterator i;

        public UnmodifiableIterator(FloatIterator floatIterator) {
            this.i = floatIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public float nextFloat() {
            return this.i.nextFloat();
        }
    }

    private static class IteratorConcatenator
    implements FloatIterator {
        final FloatIterator[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(FloatIterator[] floatIteratorArray, int n, int n2) {
            this.a = floatIteratorArray;
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
        public float nextFloat() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            float f = this.a[this.lastOffset].nextFloat();
            this.advance();
            return f;
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
    implements FloatListIterator {
        final ListIterator<Float> i;

        public ListIteratorWrapper(ListIterator<Float> listIterator2) {
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
        public void set(float f) {
            this.i.set(Float.valueOf(f));
        }

        @Override
        public void add(float f) {
            this.i.add(Float.valueOf(f));
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public float nextFloat() {
            return this.i.next().floatValue();
        }

        @Override
        public float previousFloat() {
            return this.i.previous().floatValue();
        }
    }

    private static class IteratorWrapper
    implements FloatIterator {
        final Iterator<Float> i;

        public IteratorWrapper(Iterator<Float> iterator2) {
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
        public float nextFloat() {
            return this.i.next().floatValue();
        }
    }

    private static class ArrayIterator
    implements FloatListIterator {
        private final float[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(float[] fArray, int n, int n2) {
            this.array = fArray;
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
        public float nextFloat() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public float previousFloat() {
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
    implements FloatListIterator {
        private final float element;
        private int curr;

        public SingletonIterator(float f) {
            this.element = f;
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
        public float nextFloat() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public float previousFloat() {
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
    implements FloatListIterator,
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
        public float nextFloat() {
            throw new NoSuchElementException();
        }

        @Override
        public float previousFloat() {
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

