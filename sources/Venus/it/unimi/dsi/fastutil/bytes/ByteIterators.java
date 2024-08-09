/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntPredicate;

public final class ByteIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private ByteIterators() {
    }

    public static ByteListIterator singleton(byte by) {
        return new SingletonIterator(by);
    }

    public static ByteListIterator wrap(byte[] byArray, int n, int n2) {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        return new ArrayIterator(byArray, n, n2);
    }

    public static ByteListIterator wrap(byte[] byArray) {
        return new ArrayIterator(byArray, 0, byArray.length);
    }

    public static int unwrap(ByteIterator byteIterator, byte[] byArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > byArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && byteIterator.hasNext()) {
            byArray[n++] = byteIterator.nextByte();
        }
        return n2 - n3 - 1;
    }

    public static int unwrap(ByteIterator byteIterator, byte[] byArray) {
        return ByteIterators.unwrap(byteIterator, byArray, 0, byArray.length);
    }

    public static byte[] unwrap(ByteIterator byteIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        byte[] byArray = new byte[16];
        int n2 = 0;
        while (n-- != 0 && byteIterator.hasNext()) {
            if (n2 == byArray.length) {
                byArray = ByteArrays.grow(byArray, n2 + 1);
            }
            byArray[n2++] = byteIterator.nextByte();
        }
        return ByteArrays.trim(byArray, n2);
    }

    public static byte[] unwrap(ByteIterator byteIterator) {
        return ByteIterators.unwrap(byteIterator, Integer.MAX_VALUE);
    }

    public static int unwrap(ByteIterator byteIterator, ByteCollection byteCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && byteIterator.hasNext()) {
            byteCollection.add(byteIterator.nextByte());
        }
        return n - n2 - 1;
    }

    public static long unwrap(ByteIterator byteIterator, ByteCollection byteCollection) {
        long l = 0L;
        while (byteIterator.hasNext()) {
            byteCollection.add(byteIterator.nextByte());
            ++l;
        }
        return l;
    }

    public static int pour(ByteIterator byteIterator, ByteCollection byteCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && byteIterator.hasNext()) {
            byteCollection.add(byteIterator.nextByte());
        }
        return n - n2 - 1;
    }

    public static int pour(ByteIterator byteIterator, ByteCollection byteCollection) {
        return ByteIterators.pour(byteIterator, byteCollection, Integer.MAX_VALUE);
    }

    public static ByteList pour(ByteIterator byteIterator, int n) {
        ByteArrayList byteArrayList = new ByteArrayList();
        ByteIterators.pour(byteIterator, byteArrayList, n);
        byteArrayList.trim();
        return byteArrayList;
    }

    public static ByteList pour(ByteIterator byteIterator) {
        return ByteIterators.pour(byteIterator, Integer.MAX_VALUE);
    }

    public static ByteIterator asByteIterator(Iterator iterator2) {
        if (iterator2 instanceof ByteIterator) {
            return (ByteIterator)iterator2;
        }
        return new IteratorWrapper(iterator2);
    }

    public static ByteListIterator asByteIterator(ListIterator listIterator2) {
        if (listIterator2 instanceof ByteListIterator) {
            return (ByteListIterator)listIterator2;
        }
        return new ListIteratorWrapper(listIterator2);
    }

    public static boolean any(ByteIterator byteIterator, IntPredicate intPredicate) {
        return ByteIterators.indexOf(byteIterator, intPredicate) != -1;
    }

    public static boolean all(ByteIterator byteIterator, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        do {
            if (byteIterator.hasNext()) continue;
            return false;
        } while (intPredicate.test(byteIterator.nextByte()));
        return true;
    }

    public static int indexOf(ByteIterator byteIterator, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        int n = 0;
        while (byteIterator.hasNext()) {
            if (intPredicate.test(byteIterator.nextByte())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static ByteListIterator fromTo(byte by, byte by2) {
        return new IntervalIterator(by, by2);
    }

    public static ByteIterator concat(ByteIterator[] byteIteratorArray) {
        return ByteIterators.concat(byteIteratorArray, 0, byteIteratorArray.length);
    }

    public static ByteIterator concat(ByteIterator[] byteIteratorArray, int n, int n2) {
        return new IteratorConcatenator(byteIteratorArray, n, n2);
    }

    public static ByteIterator unmodifiable(ByteIterator byteIterator) {
        return new UnmodifiableIterator(byteIterator);
    }

    public static ByteBidirectionalIterator unmodifiable(ByteBidirectionalIterator byteBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator(byteBidirectionalIterator);
    }

    public static ByteListIterator unmodifiable(ByteListIterator byteListIterator) {
        return new UnmodifiableListIterator(byteListIterator);
    }

    public static class UnmodifiableListIterator
    implements ByteListIterator {
        protected final ByteListIterator i;

        public UnmodifiableListIterator(ByteListIterator byteListIterator) {
            this.i = byteListIterator;
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
        public byte nextByte() {
            return this.i.nextByte();
        }

        @Override
        public byte previousByte() {
            return this.i.previousByte();
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
    implements ByteBidirectionalIterator {
        protected final ByteBidirectionalIterator i;

        public UnmodifiableBidirectionalIterator(ByteBidirectionalIterator byteBidirectionalIterator) {
            this.i = byteBidirectionalIterator;
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
        public byte nextByte() {
            return this.i.nextByte();
        }

        @Override
        public byte previousByte() {
            return this.i.previousByte();
        }
    }

    public static class UnmodifiableIterator
    implements ByteIterator {
        protected final ByteIterator i;

        public UnmodifiableIterator(ByteIterator byteIterator) {
            this.i = byteIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public byte nextByte() {
            return this.i.nextByte();
        }
    }

    private static class IteratorConcatenator
    implements ByteIterator {
        final ByteIterator[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(ByteIterator[] byteIteratorArray, int n, int n2) {
            this.a = byteIteratorArray;
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
        public byte nextByte() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            byte by = this.a[this.lastOffset].nextByte();
            this.advance();
            return by;
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
    implements ByteListIterator {
        private final byte from;
        private final byte to;
        byte curr;

        public IntervalIterator(byte by, byte by2) {
            this.from = this.curr = by;
            this.to = by2;
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
        public byte nextByte() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            byte by = this.curr;
            this.curr = (byte)(by + 1);
            return by;
        }

        @Override
        public byte previousByte() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = (byte)(this.curr - 1);
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
                this.curr = (byte)(this.curr + n);
                return n;
            }
            n = this.to - this.curr;
            this.curr = this.to;
            return n;
        }

        @Override
        public int back(int n) {
            if (this.curr - n >= this.from) {
                this.curr = (byte)(this.curr - n);
                return n;
            }
            n = this.curr - this.from;
            this.curr = this.from;
            return n;
        }
    }

    private static class ListIteratorWrapper
    implements ByteListIterator {
        final ListIterator<Byte> i;

        public ListIteratorWrapper(ListIterator<Byte> listIterator2) {
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
        public void set(byte by) {
            this.i.set(by);
        }

        @Override
        public void add(byte by) {
            this.i.add(by);
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public byte nextByte() {
            return this.i.next();
        }

        @Override
        public byte previousByte() {
            return this.i.previous();
        }
    }

    private static class IteratorWrapper
    implements ByteIterator {
        final Iterator<Byte> i;

        public IteratorWrapper(Iterator<Byte> iterator2) {
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
        public byte nextByte() {
            return this.i.next();
        }
    }

    private static class ArrayIterator
    implements ByteListIterator {
        private final byte[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(byte[] byArray, int n, int n2) {
            this.array = byArray;
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
        public byte nextByte() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public byte previousByte() {
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
    implements ByteListIterator {
        private final byte element;
        private int curr;

        public SingletonIterator(byte by) {
            this.element = by;
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
        public byte nextByte() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public byte previousByte() {
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
    implements ByteListIterator,
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
        public byte nextByte() {
            throw new NoSuchElementException();
        }

        @Override
        public byte previousByte() {
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

