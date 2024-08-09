/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

public final class BooleanIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private BooleanIterators() {
    }

    public static BooleanListIterator singleton(boolean bl) {
        return new SingletonIterator(bl);
    }

    public static BooleanListIterator wrap(boolean[] blArray, int n, int n2) {
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        return new ArrayIterator(blArray, n, n2);
    }

    public static BooleanListIterator wrap(boolean[] blArray) {
        return new ArrayIterator(blArray, 0, blArray.length);
    }

    public static int unwrap(BooleanIterator booleanIterator, boolean[] blArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > blArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && booleanIterator.hasNext()) {
            blArray[n++] = booleanIterator.nextBoolean();
        }
        return n2 - n3 - 1;
    }

    public static int unwrap(BooleanIterator booleanIterator, boolean[] blArray) {
        return BooleanIterators.unwrap(booleanIterator, blArray, 0, blArray.length);
    }

    public static boolean[] unwrap(BooleanIterator booleanIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        boolean[] blArray = new boolean[16];
        int n2 = 0;
        while (n-- != 0 && booleanIterator.hasNext()) {
            if (n2 == blArray.length) {
                blArray = BooleanArrays.grow(blArray, n2 + 1);
            }
            blArray[n2++] = booleanIterator.nextBoolean();
        }
        return BooleanArrays.trim(blArray, n2);
    }

    public static boolean[] unwrap(BooleanIterator booleanIterator) {
        return BooleanIterators.unwrap(booleanIterator, Integer.MAX_VALUE);
    }

    public static int unwrap(BooleanIterator booleanIterator, BooleanCollection booleanCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && booleanIterator.hasNext()) {
            booleanCollection.add(booleanIterator.nextBoolean());
        }
        return n - n2 - 1;
    }

    public static long unwrap(BooleanIterator booleanIterator, BooleanCollection booleanCollection) {
        long l = 0L;
        while (booleanIterator.hasNext()) {
            booleanCollection.add(booleanIterator.nextBoolean());
            ++l;
        }
        return l;
    }

    public static int pour(BooleanIterator booleanIterator, BooleanCollection booleanCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && booleanIterator.hasNext()) {
            booleanCollection.add(booleanIterator.nextBoolean());
        }
        return n - n2 - 1;
    }

    public static int pour(BooleanIterator booleanIterator, BooleanCollection booleanCollection) {
        return BooleanIterators.pour(booleanIterator, booleanCollection, Integer.MAX_VALUE);
    }

    public static BooleanList pour(BooleanIterator booleanIterator, int n) {
        BooleanArrayList booleanArrayList = new BooleanArrayList();
        BooleanIterators.pour(booleanIterator, booleanArrayList, n);
        booleanArrayList.trim();
        return booleanArrayList;
    }

    public static BooleanList pour(BooleanIterator booleanIterator) {
        return BooleanIterators.pour(booleanIterator, Integer.MAX_VALUE);
    }

    public static BooleanIterator asBooleanIterator(Iterator iterator2) {
        if (iterator2 instanceof BooleanIterator) {
            return (BooleanIterator)iterator2;
        }
        return new IteratorWrapper(iterator2);
    }

    public static BooleanListIterator asBooleanIterator(ListIterator listIterator2) {
        if (listIterator2 instanceof BooleanListIterator) {
            return (BooleanListIterator)listIterator2;
        }
        return new ListIteratorWrapper(listIterator2);
    }

    public static boolean any(BooleanIterator booleanIterator, Predicate<? super Boolean> predicate) {
        return BooleanIterators.indexOf(booleanIterator, predicate) != -1;
    }

    public static boolean all(BooleanIterator booleanIterator, Predicate<? super Boolean> predicate) {
        Objects.requireNonNull(predicate);
        do {
            if (booleanIterator.hasNext()) continue;
            return false;
        } while (predicate.test((Boolean)booleanIterator.nextBoolean()));
        return true;
    }

    public static int indexOf(BooleanIterator booleanIterator, Predicate<? super Boolean> predicate) {
        Objects.requireNonNull(predicate);
        int n = 0;
        while (booleanIterator.hasNext()) {
            if (predicate.test((Boolean)booleanIterator.nextBoolean())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static BooleanIterator concat(BooleanIterator[] booleanIteratorArray) {
        return BooleanIterators.concat(booleanIteratorArray, 0, booleanIteratorArray.length);
    }

    public static BooleanIterator concat(BooleanIterator[] booleanIteratorArray, int n, int n2) {
        return new IteratorConcatenator(booleanIteratorArray, n, n2);
    }

    public static BooleanIterator unmodifiable(BooleanIterator booleanIterator) {
        return new UnmodifiableIterator(booleanIterator);
    }

    public static BooleanBidirectionalIterator unmodifiable(BooleanBidirectionalIterator booleanBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator(booleanBidirectionalIterator);
    }

    public static BooleanListIterator unmodifiable(BooleanListIterator booleanListIterator) {
        return new UnmodifiableListIterator(booleanListIterator);
    }

    public static class UnmodifiableListIterator
    implements BooleanListIterator {
        protected final BooleanListIterator i;

        public UnmodifiableListIterator(BooleanListIterator booleanListIterator) {
            this.i = booleanListIterator;
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
        public boolean nextBoolean() {
            return this.i.nextBoolean();
        }

        @Override
        public boolean previousBoolean() {
            return this.i.previousBoolean();
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
    implements BooleanBidirectionalIterator {
        protected final BooleanBidirectionalIterator i;

        public UnmodifiableBidirectionalIterator(BooleanBidirectionalIterator booleanBidirectionalIterator) {
            this.i = booleanBidirectionalIterator;
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
        public boolean nextBoolean() {
            return this.i.nextBoolean();
        }

        @Override
        public boolean previousBoolean() {
            return this.i.previousBoolean();
        }
    }

    public static class UnmodifiableIterator
    implements BooleanIterator {
        protected final BooleanIterator i;

        public UnmodifiableIterator(BooleanIterator booleanIterator) {
            this.i = booleanIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean nextBoolean() {
            return this.i.nextBoolean();
        }
    }

    private static class IteratorConcatenator
    implements BooleanIterator {
        final BooleanIterator[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(BooleanIterator[] booleanIteratorArray, int n, int n2) {
            this.a = booleanIteratorArray;
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
        public boolean nextBoolean() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            boolean bl = this.a[this.lastOffset].nextBoolean();
            this.advance();
            return bl;
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
    implements BooleanListIterator {
        final ListIterator<Boolean> i;

        public ListIteratorWrapper(ListIterator<Boolean> listIterator2) {
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
        public void set(boolean bl) {
            this.i.set(bl);
        }

        @Override
        public void add(boolean bl) {
            this.i.add(bl);
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public boolean nextBoolean() {
            return this.i.next();
        }

        @Override
        public boolean previousBoolean() {
            return this.i.previous();
        }
    }

    private static class IteratorWrapper
    implements BooleanIterator {
        final Iterator<Boolean> i;

        public IteratorWrapper(Iterator<Boolean> iterator2) {
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
        public boolean nextBoolean() {
            return this.i.next();
        }
    }

    private static class ArrayIterator
    implements BooleanListIterator {
        private final boolean[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(boolean[] blArray, int n, int n2) {
            this.array = blArray;
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
        public boolean nextBoolean() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public boolean previousBoolean() {
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
    implements BooleanListIterator {
        private final boolean element;
        private int curr;

        public SingletonIterator(boolean bl) {
            this.element = bl;
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
        public boolean nextBoolean() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public boolean previousBoolean() {
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
    implements BooleanListIterator,
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
        public boolean nextBoolean() {
            throw new NoSuchElementException();
        }

        @Override
        public boolean previousBoolean() {
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

