/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntPredicate;

public final class CharIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private CharIterators() {
    }

    public static CharListIterator singleton(char c) {
        return new SingletonIterator(c);
    }

    public static CharListIterator wrap(char[] cArray, int n, int n2) {
        CharArrays.ensureOffsetLength(cArray, n, n2);
        return new ArrayIterator(cArray, n, n2);
    }

    public static CharListIterator wrap(char[] cArray) {
        return new ArrayIterator(cArray, 0, cArray.length);
    }

    public static int unwrap(CharIterator charIterator, char[] cArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > cArray.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && charIterator.hasNext()) {
            cArray[n++] = charIterator.nextChar();
        }
        return n2 - n3 - 1;
    }

    public static int unwrap(CharIterator charIterator, char[] cArray) {
        return CharIterators.unwrap(charIterator, cArray, 0, cArray.length);
    }

    public static char[] unwrap(CharIterator charIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        char[] cArray = new char[16];
        int n2 = 0;
        while (n-- != 0 && charIterator.hasNext()) {
            if (n2 == cArray.length) {
                cArray = CharArrays.grow(cArray, n2 + 1);
            }
            cArray[n2++] = charIterator.nextChar();
        }
        return CharArrays.trim(cArray, n2);
    }

    public static char[] unwrap(CharIterator charIterator) {
        return CharIterators.unwrap(charIterator, Integer.MAX_VALUE);
    }

    public static int unwrap(CharIterator charIterator, CharCollection charCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && charIterator.hasNext()) {
            charCollection.add(charIterator.nextChar());
        }
        return n - n2 - 1;
    }

    public static long unwrap(CharIterator charIterator, CharCollection charCollection) {
        long l = 0L;
        while (charIterator.hasNext()) {
            charCollection.add(charIterator.nextChar());
            ++l;
        }
        return l;
    }

    public static int pour(CharIterator charIterator, CharCollection charCollection, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && charIterator.hasNext()) {
            charCollection.add(charIterator.nextChar());
        }
        return n - n2 - 1;
    }

    public static int pour(CharIterator charIterator, CharCollection charCollection) {
        return CharIterators.pour(charIterator, charCollection, Integer.MAX_VALUE);
    }

    public static CharList pour(CharIterator charIterator, int n) {
        CharArrayList charArrayList = new CharArrayList();
        CharIterators.pour(charIterator, charArrayList, n);
        charArrayList.trim();
        return charArrayList;
    }

    public static CharList pour(CharIterator charIterator) {
        return CharIterators.pour(charIterator, Integer.MAX_VALUE);
    }

    public static CharIterator asCharIterator(Iterator iterator2) {
        if (iterator2 instanceof CharIterator) {
            return (CharIterator)iterator2;
        }
        return new IteratorWrapper(iterator2);
    }

    public static CharListIterator asCharIterator(ListIterator listIterator2) {
        if (listIterator2 instanceof CharListIterator) {
            return (CharListIterator)listIterator2;
        }
        return new ListIteratorWrapper(listIterator2);
    }

    public static boolean any(CharIterator charIterator, IntPredicate intPredicate) {
        return CharIterators.indexOf(charIterator, intPredicate) != -1;
    }

    public static boolean all(CharIterator charIterator, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        do {
            if (charIterator.hasNext()) continue;
            return false;
        } while (intPredicate.test(charIterator.nextChar()));
        return true;
    }

    public static int indexOf(CharIterator charIterator, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        int n = 0;
        while (charIterator.hasNext()) {
            if (intPredicate.test(charIterator.nextChar())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static CharListIterator fromTo(char c, char c2) {
        return new IntervalIterator(c, c2);
    }

    public static CharIterator concat(CharIterator[] charIteratorArray) {
        return CharIterators.concat(charIteratorArray, 0, charIteratorArray.length);
    }

    public static CharIterator concat(CharIterator[] charIteratorArray, int n, int n2) {
        return new IteratorConcatenator(charIteratorArray, n, n2);
    }

    public static CharIterator unmodifiable(CharIterator charIterator) {
        return new UnmodifiableIterator(charIterator);
    }

    public static CharBidirectionalIterator unmodifiable(CharBidirectionalIterator charBidirectionalIterator) {
        return new UnmodifiableBidirectionalIterator(charBidirectionalIterator);
    }

    public static CharListIterator unmodifiable(CharListIterator charListIterator) {
        return new UnmodifiableListIterator(charListIterator);
    }

    public static class UnmodifiableListIterator
    implements CharListIterator {
        protected final CharListIterator i;

        public UnmodifiableListIterator(CharListIterator charListIterator) {
            this.i = charListIterator;
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
        public char nextChar() {
            return this.i.nextChar();
        }

        @Override
        public char previousChar() {
            return this.i.previousChar();
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
    implements CharBidirectionalIterator {
        protected final CharBidirectionalIterator i;

        public UnmodifiableBidirectionalIterator(CharBidirectionalIterator charBidirectionalIterator) {
            this.i = charBidirectionalIterator;
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
        public char nextChar() {
            return this.i.nextChar();
        }

        @Override
        public char previousChar() {
            return this.i.previousChar();
        }
    }

    public static class UnmodifiableIterator
    implements CharIterator {
        protected final CharIterator i;

        public UnmodifiableIterator(CharIterator charIterator) {
            this.i = charIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public char nextChar() {
            return this.i.nextChar();
        }
    }

    private static class IteratorConcatenator
    implements CharIterator {
        final CharIterator[] a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(CharIterator[] charIteratorArray, int n, int n2) {
            this.a = charIteratorArray;
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
        public char nextChar() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastOffset = this.offset;
            char c = this.a[this.lastOffset].nextChar();
            this.advance();
            return c;
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
    implements CharListIterator {
        private final char from;
        private final char to;
        char curr;

        public IntervalIterator(char c, char c2) {
            this.from = this.curr = c;
            this.to = c2;
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
        public char nextChar() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            char c = this.curr;
            this.curr = (char)(c + '\u0001');
            return c;
        }

        @Override
        public char previousChar() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = (char)(this.curr - '\u0001');
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
                this.curr = (char)(this.curr + n);
                return n;
            }
            n = this.to - this.curr;
            this.curr = this.to;
            return n;
        }

        @Override
        public int back(int n) {
            if (this.curr - n >= this.from) {
                this.curr = (char)(this.curr - n);
                return n;
            }
            n = this.curr - this.from;
            this.curr = this.from;
            return n;
        }
    }

    private static class ListIteratorWrapper
    implements CharListIterator {
        final ListIterator<Character> i;

        public ListIteratorWrapper(ListIterator<Character> listIterator2) {
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
        public void set(char c) {
            this.i.set(Character.valueOf(c));
        }

        @Override
        public void add(char c) {
            this.i.add(Character.valueOf(c));
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public char nextChar() {
            return this.i.next().charValue();
        }

        @Override
        public char previousChar() {
            return this.i.previous().charValue();
        }
    }

    private static class IteratorWrapper
    implements CharIterator {
        final Iterator<Character> i;

        public IteratorWrapper(Iterator<Character> iterator2) {
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
        public char nextChar() {
            return this.i.next().charValue();
        }
    }

    private static class ArrayIterator
    implements CharListIterator {
        private final char[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(char[] cArray, int n, int n2) {
            this.array = cArray;
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
        public char nextChar() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }

        @Override
        public char previousChar() {
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
    implements CharListIterator {
        private final char element;
        private int curr;

        public SingletonIterator(char c) {
            this.element = c;
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
        public char nextChar() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public char previousChar() {
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
    implements CharListIterator,
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
        public char nextChar() {
            throw new NoSuchElementException();
        }

        @Override
        public char previousChar() {
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

