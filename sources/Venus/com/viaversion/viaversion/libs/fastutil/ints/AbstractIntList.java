/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import java.util.Spliterator;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractIntList
extends AbstractIntCollection
implements IntList,
IntStack {
    protected AbstractIntList() {
    }

    protected void ensureIndex(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
    }

    protected void ensureRestrictedIndex(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }

    @Override
    public void add(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(int n) {
        this.add(this.size(), n);
        return false;
    }

    @Override
    public int removeInt(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int set(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Integer> collection) {
        if (collection instanceof IntCollection) {
            return this.addAll(n, (IntCollection)collection);
        }
        this.ensureIndex(n);
        Iterator<? extends Integer> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, (int)iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> collection) {
        return this.addAll(this.size(), collection);
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
    public IntListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new IntIterators.AbstractIndexBasedListIterator(this, 0, n){
            final AbstractIntList this$0;
            {
                this.this$0 = abstractIntList;
                super(n, n2);
            }

            @Override
            protected final int get(int n) {
                return this.this$0.getInt(n);
            }

            @Override
            protected final void add(int n, int n2) {
                this.this$0.add(n, n2);
            }

            @Override
            protected final void set(int n, int n2) {
                this.this$0.set(n, n2);
            }

            @Override
            protected final void remove(int n) {
                this.this$0.removeInt(n);
            }

            @Override
            protected final int getMaxPos() {
                return this.this$0.size();
            }
        };
    }

    @Override
    public boolean contains(int n) {
        return this.indexOf(n) >= 0;
    }

    @Override
    public int indexOf(int n) {
        IntListIterator intListIterator = this.listIterator();
        while (intListIterator.hasNext()) {
            int n2 = intListIterator.nextInt();
            if (n != n2) continue;
            return intListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(int n) {
        IntListIterator intListIterator = this.listIterator(this.size());
        while (intListIterator.hasPrevious()) {
            int n2 = intListIterator.previousInt();
            if (n != n2) continue;
            return intListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add(1);
            }
        } else {
            while (n2-- != n) {
                this.removeInt(n2);
            }
        }
    }

    @Override
    public IntList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return this instanceof RandomAccess ? new IntRandomAccessSubList(this, n, n2) : new IntSubList(this, n, n2);
    }

    @Override
    public void forEach(IntConsumer intConsumer) {
        if (this instanceof RandomAccess) {
            int n = this.size();
            for (int i = 0; i < n; ++i) {
                intConsumer.accept(this.getInt(i));
            }
        } else {
            IntList.super.forEach(intConsumer);
        }
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        IntListIterator intListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            intListIterator.nextInt();
            intListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, int[] nArray, int n2, int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        if (this instanceof RandomAccess) {
            while (n3-- != 0) {
                this.add(n++, nArray[n2++]);
            }
        } else {
            IntListIterator intListIterator = this.listIterator(n);
            while (n3-- != 0) {
                intListIterator.add(nArray[n2++]);
            }
        }
    }

    @Override
    public void addElements(int n, int[] nArray) {
        this.addElements(n, nArray, 0, nArray.length);
    }

    @Override
    public void getElements(int n, int[] nArray, int n2, int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            int n4 = n;
            while (n3-- != 0) {
                nArray[n2++] = this.getInt(n4++);
            }
        } else {
            IntListIterator intListIterator = this.listIterator(n);
            while (n3-- != 0) {
                nArray[n2++] = intListIterator.nextInt();
            }
        }
    }

    @Override
    public void setElements(int n, int[] nArray, int n2, int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            for (int i = 0; i < n3; ++i) {
                this.set(i + n, nArray[i + n2]);
            }
        } else {
            IntListIterator intListIterator = this.listIterator(n);
            int n4 = 0;
            while (n4 < n3) {
                intListIterator.nextInt();
                intListIterator.set(nArray[n2 + n4++]);
            }
        }
    }

    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }

    @Override
    public int hashCode() {
        IntListIterator intListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            int n3 = intListIterator.nextInt();
            n = 31 * n + n3;
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof List)) {
            return true;
        }
        List list = (List)object;
        int n = this.size();
        if (n != list.size()) {
            return true;
        }
        if (list instanceof IntList) {
            IntListIterator intListIterator = this.listIterator();
            IntListIterator intListIterator2 = ((IntList)list).listIterator();
            while (n-- != 0) {
                if (intListIterator.nextInt() == intListIterator2.nextInt()) continue;
                return true;
            }
            return false;
        }
        IntListIterator intListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (Objects.equals(intListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Integer> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof IntList) {
            IntListIterator intListIterator = this.listIterator();
            IntListIterator intListIterator2 = ((IntList)list).listIterator();
            while (intListIterator.hasNext() && intListIterator2.hasNext()) {
                int n;
                int n2 = intListIterator.nextInt();
                int n3 = Integer.compare(n2, n = intListIterator2.nextInt());
                if (n3 == 0) continue;
                return n3;
            }
            return intListIterator2.hasNext() ? -1 : (intListIterator.hasNext() ? 1 : 0);
        }
        IntListIterator intListIterator = this.listIterator();
        ListIterator<? extends Integer> listIterator2 = list.listIterator();
        while (intListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)intListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (intListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(int n) {
        this.add(n);
    }

    @Override
    public int popInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeInt(this.size() - 1);
    }

    @Override
    public int topInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getInt(this.size() - 1);
    }

    @Override
    public int peekInt(int n) {
        return this.getInt(this.size() - 1 - n);
    }

    @Override
    public boolean rem(int n) {
        int n2 = this.indexOf(n);
        if (n2 == -1) {
            return true;
        }
        this.removeInt(n2);
        return false;
    }

    @Override
    public int[] toIntArray() {
        int n = this.size();
        if (n == 0) {
            return IntArrays.EMPTY_ARRAY;
        }
        int[] nArray = new int[n];
        this.getElements(0, nArray, 0, n);
        return nArray;
    }

    @Override
    public int[] toArray(int[] nArray) {
        int n = this.size();
        if (nArray.length < n) {
            nArray = Arrays.copyOf(nArray, n);
        }
        this.getElements(0, nArray, 0, n);
        return nArray;
    }

    @Override
    public boolean addAll(int n, IntCollection intCollection) {
        this.ensureIndex(n);
        IntIterator intIterator = intCollection.iterator();
        boolean bl = intIterator.hasNext();
        while (intIterator.hasNext()) {
            this.add(n++, intIterator.nextInt());
        }
        return bl;
    }

    @Override
    public boolean addAll(IntCollection intCollection) {
        return this.addAll(this.size(), intCollection);
    }

    @Override
    public final void replaceAll(IntUnaryOperator intUnaryOperator) {
        this.replaceAll((java.util.function.IntUnaryOperator)intUnaryOperator);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        IntListIterator intListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            int n2 = intListIterator.nextInt();
            stringBuilder.append(String.valueOf(n2));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public IntIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    public ListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((List)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IntRandomAccessSubList
    extends IntSubList
    implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public IntRandomAccessSubList(IntList intList, int n, int n2) {
            super(intList, n, n2);
        }

        @Override
        public IntList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new IntRandomAccessSubList(this, n, n2);
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IntSubList
    extends AbstractIntList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractIntList.class.desiredAssertionStatus();

        public IntSubList(IntList intList, int n, int n2) {
            this.l = intList;
            this.from = n;
            this.to = n2;
        }

        private boolean assertRange() {
            if (!$assertionsDisabled && this.from > this.l.size()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to > this.l.size()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to < this.from) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean add(int n) {
            this.l.add(this.to, n);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, int n2) {
            this.ensureIndex(n);
            this.l.add(this.from + n, n2);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Integer> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public int getInt(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getInt(this.from + n);
        }

        @Override
        public int removeInt(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeInt(this.from + n);
        }

        @Override
        public int set(int n, int n2) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, n2);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, int[] nArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, nArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            this.l.removeElements(this.from + n, this.from + n2);
            this.to -= n2 - n;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public void addElements(int n, int[] nArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, nArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public void setElements(int n, int[] nArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.setElements(this.from + n, nArray, n2, n3);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public IntListIterator listIterator(int n) {
            this.ensureIndex(n);
            return this.l instanceof RandomAccess ? new RandomAccessIter(this, n) : new ParentWrappingIter(this, this.l.listIterator(n + this.from));
        }

        @Override
        public IntSpliterator spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override
        public IntList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new IntSubList(this, n, n2);
        }

        @Override
        public boolean rem(int n) {
            int n2 = this.indexOf(n);
            if (n2 == -1) {
                return true;
            }
            --this.to;
            this.l.removeInt(this.from + n2);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, IntCollection intCollection) {
            this.ensureIndex(n);
            return super.addAll(n, intCollection);
        }

        @Override
        public boolean addAll(int n, IntList intList) {
            this.ensureIndex(n);
            return super.addAll(n, intList);
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((List)object);
        }

        @Override
        public IntIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(IntSubList intSubList) {
            return intSubList.assertRange();
        }

        private final class RandomAccessIter
        extends IntIterators.AbstractIndexBasedListIterator {
            static final boolean $assertionsDisabled = !AbstractIntList.class.desiredAssertionStatus();
            final IntSubList this$0;

            RandomAccessIter(IntSubList intSubList, int n) {
                this.this$0 = intSubList;
                super(0, n);
            }

            @Override
            protected final int get(int n) {
                return this.this$0.l.getInt(this.this$0.from + n);
            }

            @Override
            protected final void add(int n, int n2) {
                this.this$0.add(n, n2);
            }

            @Override
            protected final void set(int n, int n2) {
                this.this$0.set(n, n2);
            }

            @Override
            protected final void remove(int n) {
                this.this$0.removeInt(n);
            }

            @Override
            protected final int getMaxPos() {
                return this.this$0.to - this.this$0.from;
            }

            @Override
            public void add(int n) {
                super.add(n);
                if (!$assertionsDisabled && !IntSubList.access$000(this.this$0)) {
                    throw new AssertionError();
                }
            }

            @Override
            public void remove() {
                super.remove();
                if (!$assertionsDisabled && !IntSubList.access$000(this.this$0)) {
                    throw new AssertionError();
                }
            }
        }

        private class ParentWrappingIter
        implements IntListIterator {
            private IntListIterator parent;
            final IntSubList this$0;

            ParentWrappingIter(IntSubList intSubList, IntListIterator intListIterator) {
                this.this$0 = intSubList;
                this.parent = intListIterator;
            }

            @Override
            public int nextIndex() {
                return this.parent.nextIndex() - this.this$0.from;
            }

            @Override
            public int previousIndex() {
                return this.parent.previousIndex() - this.this$0.from;
            }

            @Override
            public boolean hasNext() {
                return this.parent.nextIndex() < this.this$0.to;
            }

            @Override
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= this.this$0.from;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.parent.nextInt();
            }

            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return this.parent.previousInt();
            }

            @Override
            public void add(int n) {
                this.parent.add(n);
            }

            @Override
            public void set(int n) {
                this.parent.set(n);
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
                int n2 = this.parent.previousIndex();
                int n3 = n2 - n;
                if (n3 < this.this$0.from - 1) {
                    n3 = this.this$0.from - 1;
                }
                int n4 = n3 - n2;
                return this.parent.back(n4);
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = this.parent.nextIndex();
                int n3 = n2 + n;
                if (n3 > this.this$0.to) {
                    n3 = this.this$0.to;
                }
                int n4 = n3 - n2;
                return this.parent.skip(n4);
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class IndexBasedSpliterator
    extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
        final IntList l;

        IndexBasedSpliterator(IntList intList, int n) {
            super(n);
            this.l = intList;
        }

        IndexBasedSpliterator(IntList intList, int n, int n2) {
            super(n, n2);
            this.l = intList;
        }

        @Override
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override
        protected final int get(int n) {
            return this.l.getInt(n);
        }

        @Override
        protected final IndexBasedSpliterator makeForSplit(int n, int n2) {
            return new IndexBasedSpliterator(this.l, n, n2);
        }

        @Override
        protected IntSpliterator makeForSplit(int n, int n2) {
            return this.makeForSplit(n, n2);
        }
    }
}

