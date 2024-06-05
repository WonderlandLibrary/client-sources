/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.libs.fastutil.ints.IntListIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntStack;
import com.viaversion.viaversion.libs.fastutil.ints.IntUnaryOperator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.IntConsumer;

public abstract class AbstractIntList
extends AbstractIntCollection
implements IntList,
IntStack {
    protected AbstractIntList() {
    }

    protected void ensureIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index <= this.size()) return;
        throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
    }

    protected void ensureRestrictedIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index < this.size()) return;
        throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size() + ")");
    }

    @Override
    public void add(int index, int k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(int k) {
        this.add(this.size(), k);
        return true;
    }

    @Override
    public int removeInt(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int set(int index, int k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends Integer> c) {
        if (c instanceof IntCollection) {
            return this.addAll(index, (IntCollection)c);
        }
        this.ensureIndex(index);
        Iterator<? extends Integer> i = c.iterator();
        boolean retVal = i.hasNext();
        while (i.hasNext()) {
            this.add(index++, (int)i.next());
        }
        return retVal;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        return this.addAll(this.size(), c);
    }

    @Override
    public IntListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public IntListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public IntListIterator listIterator(int index) {
        this.ensureIndex(index);
        return new IntIterators.AbstractIndexBasedListIterator(0, index){

            @Override
            protected final int get(int i) {
                return AbstractIntList.this.getInt(i);
            }

            @Override
            protected final void add(int i, int k) {
                AbstractIntList.this.add(i, k);
            }

            @Override
            protected final void set(int i, int k) {
                AbstractIntList.this.set(i, k);
            }

            @Override
            protected final void remove(int i) {
                AbstractIntList.this.removeInt(i);
            }

            @Override
            protected final int getMaxPos() {
                return AbstractIntList.this.size();
            }
        };
    }

    @Override
    public boolean contains(int k) {
        if (this.indexOf(k) < 0) return false;
        return true;
    }

    @Override
    public int indexOf(int k) {
        int e;
        IntListIterator i = this.listIterator();
        do {
            if (!i.hasNext()) return -1;
        } while (k != (e = i.nextInt()));
        return i.previousIndex();
    }

    @Override
    public int lastIndexOf(int k) {
        int e;
        IntListIterator i = this.listIterator(this.size());
        do {
            if (!i.hasPrevious()) return -1;
        } while (k != (e = i.previousInt()));
        return i.nextIndex();
    }

    @Override
    public void size(int size) {
        int i = this.size();
        if (size > i) {
            while (i++ < size) {
                this.add(0);
            }
            return;
        }
        while (i-- != size) {
            this.removeInt(i);
        }
    }

    @Override
    public IntList subList(int from, int to) {
        IntSubList intSubList;
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        if (this instanceof RandomAccess) {
            intSubList = new IntRandomAccessSubList(this, from, to);
            return intSubList;
        }
        intSubList = new IntSubList(this, from, to);
        return intSubList;
    }

    @Override
    public void forEach(IntConsumer action) {
        if (!(this instanceof RandomAccess)) {
            IntList.super.forEach(action);
            return;
        }
        int i = 0;
        int max = this.size();
        while (i < max) {
            action.accept(this.getInt(i));
            ++i;
        }
    }

    @Override
    public void removeElements(int from, int to) {
        this.ensureIndex(to);
        IntListIterator i = this.listIterator(from);
        int n = to - from;
        if (n < 0) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0) {
            i.nextInt();
            i.remove();
        }
    }

    @Override
    public void addElements(int index, int[] a, int offset, int length) {
        this.ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (this instanceof RandomAccess) {
            while (length-- != 0) {
                this.add(index++, a[offset++]);
            }
            return;
        }
        IntListIterator iter = this.listIterator(index);
        while (length-- != 0) {
            iter.add(a[offset++]);
        }
    }

    @Override
    public void addElements(int index, int[] a) {
        this.addElements(index, a, 0, a.length);
    }

    @Override
    public void getElements(int from, int[] a, int offset, int length) {
        this.ensureIndex(from);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (from + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            int current = from;
            while (length-- != 0) {
                a[offset++] = this.getInt(current++);
            }
            return;
        }
        IntListIterator i = this.listIterator(from);
        while (length-- != 0) {
            a[offset++] = i.nextInt();
        }
    }

    @Override
    public void setElements(int index, int[] a, int offset, int length) {
        this.ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (index + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            int i = 0;
            while (i < length) {
                this.set(i + index, a[i + offset]);
                ++i;
            }
            return;
        }
        IntListIterator iter = this.listIterator(index);
        int i = 0;
        while (i < length) {
            iter.nextInt();
            iter.set(a[offset + i++]);
        }
    }

    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }

    @Override
    public int hashCode() {
        IntListIterator i = this.iterator();
        int h = 1;
        int s = this.size();
        while (s-- != 0) {
            int k = i.nextInt();
            h = 31 * h + k;
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        List l = (List)o;
        int s = this.size();
        if (s != l.size()) {
            return false;
        }
        if (l instanceof IntList) {
            IntListIterator i1 = this.listIterator();
            IntListIterator i2 = ((IntList)l).listIterator();
            do {
                if (s-- == 0) return true;
            } while (i1.nextInt() == i2.nextInt());
            return false;
        }
        IntListIterator i1 = this.listIterator();
        ListIterator i2 = l.listIterator();
        do {
            if (s-- == 0) return true;
        } while (Objects.equals(i1.next(), i2.next()));
        return false;
    }

    @Override
    public int compareTo(List<? extends Integer> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof IntList) {
            IntListIterator i1 = this.listIterator();
            IntListIterator i2 = ((IntList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                int e2;
                int e1 = i1.nextInt();
                int r = Integer.compare(e1, e2 = i2.nextInt());
                if (r == 0) continue;
                return r;
            }
            if (i2.hasNext()) {
                return -1;
            }
            if (!i1.hasNext()) return 0;
            return 1;
        }
        IntListIterator i1 = this.listIterator();
        ListIterator<? extends Integer> i2 = l.listIterator();
        while (i1.hasNext() && i2.hasNext()) {
            int r = ((Comparable)i1.next()).compareTo(i2.next());
            if (r == 0) continue;
            return r;
        }
        if (i2.hasNext()) {
            return -1;
        }
        if (!i1.hasNext()) return 0;
        return 1;
    }

    @Override
    public void push(int o) {
        this.add(o);
    }

    @Override
    public int popInt() {
        if (!this.isEmpty()) return this.removeInt(this.size() - 1);
        throw new NoSuchElementException();
    }

    @Override
    public int topInt() {
        if (!this.isEmpty()) return this.getInt(this.size() - 1);
        throw new NoSuchElementException();
    }

    @Override
    public int peekInt(int i) {
        return this.getInt(this.size() - 1 - i);
    }

    @Override
    public boolean rem(int k) {
        int index = this.indexOf(k);
        if (index == -1) {
            return false;
        }
        this.removeInt(index);
        return true;
    }

    @Override
    public int[] toIntArray() {
        int size = this.size();
        int[] ret = new int[size];
        this.getElements(0, ret, 0, size);
        return ret;
    }

    @Override
    public int[] toArray(int[] a) {
        int size = this.size();
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        this.getElements(0, a, 0, size);
        return a;
    }

    @Override
    public boolean addAll(int index, IntCollection c) {
        this.ensureIndex(index);
        IntIterator i = c.iterator();
        boolean retVal = i.hasNext();
        while (i.hasNext()) {
            this.add(index++, i.nextInt());
        }
        return retVal;
    }

    @Override
    public boolean addAll(IntCollection c) {
        return this.addAll(this.size(), c);
    }

    @Override
    public final void replaceAll(IntUnaryOperator operator) {
        this.replaceAll((java.util.function.IntUnaryOperator)operator);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        IntListIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("[");
        while (true) {
            if (n-- == 0) {
                s.append("]");
                return s.toString();
            }
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            int k = i.nextInt();
            s.append(String.valueOf(k));
        }
    }

    public static class IntRandomAccessSubList
    extends IntSubList
    implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public IntRandomAccessSubList(IntList l, int from, int to) {
            super(l, from, to);
        }

        @Override
        public IntList subList(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from <= to) return new IntRandomAccessSubList(this, from, to);
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
    }

    public static class IntSubList
    extends AbstractIntList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntList l;
        protected final int from;
        protected int to;

        public IntSubList(IntList l, int from, int to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }

        private boolean assertRange() {
            assert (this.from <= this.l.size());
            assert (this.to <= this.l.size());
            if ($assertionsDisabled) return true;
            if (this.to >= this.from) return true;
            throw new AssertionError();
        }

        @Override
        public boolean add(int k) {
            this.l.add(this.to, k);
            ++this.to;
            if ($assertionsDisabled) return true;
            if (this.assertRange()) return true;
            throw new AssertionError();
        }

        @Override
        public void add(int index, int k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
            if ($assertionsDisabled) return;
            if (this.assertRange()) return;
            throw new AssertionError();
        }

        @Override
        public boolean addAll(int index, Collection<? extends Integer> c) {
            this.ensureIndex(index);
            this.to += c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public int getInt(int index) {
            this.ensureRestrictedIndex(index);
            return this.l.getInt(this.from + index);
        }

        @Override
        public int removeInt(int index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.removeInt(this.from + index);
        }

        @Override
        public int set(int index, int k) {
            this.ensureRestrictedIndex(index);
            return this.l.set(this.from + index, k);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int from, int[] a, int offset, int length) {
            this.ensureIndex(from);
            if (from + length > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            this.l.removeElements(this.from + from, this.from + to);
            this.to -= to - from;
            if ($assertionsDisabled) return;
            if (this.assertRange()) return;
            throw new AssertionError();
        }

        @Override
        public void addElements(int index, int[] a, int offset, int length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
            if ($assertionsDisabled) return;
            if (this.assertRange()) return;
            throw new AssertionError();
        }

        @Override
        public void setElements(int index, int[] a, int offset, int length) {
            this.ensureIndex(index);
            this.l.setElements(this.from + index, a, offset, length);
            if ($assertionsDisabled) return;
            if (this.assertRange()) return;
            throw new AssertionError();
        }

        @Override
        public IntListIterator listIterator(int index) {
            IntListIterator intListIterator;
            this.ensureIndex(index);
            if (this.l instanceof RandomAccess) {
                intListIterator = new RandomAccessIter(index);
                return intListIterator;
            }
            intListIterator = new ParentWrappingIter(this.l.listIterator(index + this.from));
            return intListIterator;
        }

        @Override
        public IntSpliterator spliterator() {
            IntSpliterator intSpliterator;
            if (this.l instanceof RandomAccess) {
                intSpliterator = new IndexBasedSpliterator(this.l, this.from, this.to);
                return intSpliterator;
            }
            intSpliterator = super.spliterator();
            return intSpliterator;
        }

        @Override
        public IntList subList(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from <= to) return new IntSubList(this, from, to);
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }

        @Override
        public boolean rem(int k) {
            int index = this.indexOf(k);
            if (index == -1) {
                return false;
            }
            --this.to;
            this.l.removeInt(this.from + index);
            if ($assertionsDisabled) return true;
            if (this.assertRange()) return true;
            throw new AssertionError();
        }

        @Override
        public boolean addAll(int index, IntCollection c) {
            this.ensureIndex(index);
            return super.addAll(index, c);
        }

        @Override
        public boolean addAll(int index, IntList l) {
            this.ensureIndex(index);
            return super.addAll(index, l);
        }

        private final class RandomAccessIter
        extends IntIterators.AbstractIndexBasedListIterator {
            RandomAccessIter(int pos) {
                super(0, pos);
            }

            @Override
            protected final int get(int i) {
                return IntSubList.this.l.getInt(IntSubList.this.from + i);
            }

            @Override
            protected final void add(int i, int k) {
                IntSubList.this.add(i, k);
            }

            @Override
            protected final void set(int i, int k) {
                IntSubList.this.set(i, k);
            }

            @Override
            protected final void remove(int i) {
                IntSubList.this.removeInt(i);
            }

            @Override
            protected final int getMaxPos() {
                return IntSubList.this.to - IntSubList.this.from;
            }

            @Override
            public void add(int k) {
                super.add(k);
                if ($assertionsDisabled) return;
                if (IntSubList.this.assertRange()) return;
                throw new AssertionError();
            }

            @Override
            public void remove() {
                super.remove();
                if ($assertionsDisabled) return;
                if (IntSubList.this.assertRange()) return;
                throw new AssertionError();
            }
        }

        private class ParentWrappingIter
        implements IntListIterator {
            private IntListIterator parent;

            ParentWrappingIter(IntListIterator parent) {
                this.parent = parent;
            }

            @Override
            public int nextIndex() {
                return this.parent.nextIndex() - IntSubList.this.from;
            }

            @Override
            public int previousIndex() {
                return this.parent.previousIndex() - IntSubList.this.from;
            }

            @Override
            public boolean hasNext() {
                if (this.parent.nextIndex() >= IntSubList.this.to) return false;
                return true;
            }

            @Override
            public boolean hasPrevious() {
                if (this.parent.previousIndex() < IntSubList.this.from) return false;
                return true;
            }

            @Override
            public int nextInt() {
                if (this.hasNext()) return this.parent.nextInt();
                throw new NoSuchElementException();
            }

            @Override
            public int previousInt() {
                if (this.hasPrevious()) return this.parent.previousInt();
                throw new NoSuchElementException();
            }

            @Override
            public void add(int k) {
                this.parent.add(k);
            }

            @Override
            public void set(int k) {
                this.parent.set(k);
            }

            @Override
            public void remove() {
                this.parent.remove();
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int currentPos = this.parent.previousIndex();
                int parentNewPos = currentPos - n;
                if (parentNewPos < IntSubList.this.from - 1) {
                    parentNewPos = IntSubList.this.from - 1;
                }
                int toSkip = parentNewPos - currentPos;
                return this.parent.back(toSkip);
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int currentPos = this.parent.nextIndex();
                int parentNewPos = currentPos + n;
                if (parentNewPos > IntSubList.this.to) {
                    parentNewPos = IntSubList.this.to;
                }
                int toSkip = parentNewPos - currentPos;
                return this.parent.skip(toSkip);
            }
        }
    }

    static final class IndexBasedSpliterator
    extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
        final IntList l;

        IndexBasedSpliterator(IntList l, int pos) {
            super(pos);
            this.l = l;
        }

        IndexBasedSpliterator(IntList l, int pos, int maxPos) {
            super(pos, maxPos);
            this.l = l;
        }

        @Override
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override
        protected final int get(int i) {
            return this.l.getInt(i);
        }

        @Override
        protected final IndexBasedSpliterator makeForSplit(int pos, int maxPos) {
            return new IndexBasedSpliterator(this.l, pos, maxPos);
        }
    }
}

