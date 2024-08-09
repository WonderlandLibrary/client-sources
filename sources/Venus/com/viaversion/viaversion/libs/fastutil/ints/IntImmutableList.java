/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.libs.fastutil.ints.IntListIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntLists;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntImmutableList
extends IntLists.ImmutableListBase
implements IntList,
RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = 0L;
    static final IntImmutableList EMPTY = new IntImmutableList(IntArrays.EMPTY_ARRAY);
    private final int[] a;

    public IntImmutableList(int[] nArray) {
        this.a = nArray;
    }

    public IntImmutableList(Collection<? extends Integer> collection) {
        this(collection.isEmpty() ? IntArrays.EMPTY_ARRAY : IntIterators.unwrap(IntIterators.asIntIterator(collection.iterator())));
    }

    public IntImmutableList(IntCollection intCollection) {
        this(intCollection.isEmpty() ? IntArrays.EMPTY_ARRAY : IntIterators.unwrap(intCollection.iterator()));
    }

    public IntImmutableList(IntList intList) {
        this(intList.isEmpty() ? IntArrays.EMPTY_ARRAY : new int[intList.size()]);
        intList.getElements(0, this.a, 0, intList.size());
    }

    public IntImmutableList(int[] nArray, int n, int n2) {
        this(n2 == 0 ? IntArrays.EMPTY_ARRAY : new int[n2]);
        System.arraycopy(nArray, n, this.a, 0, n2);
    }

    public IntImmutableList(IntIterator intIterator) {
        this(intIterator.hasNext() ? IntIterators.unwrap(intIterator) : IntArrays.EMPTY_ARRAY);
    }

    public static IntImmutableList of() {
        return EMPTY;
    }

    public static IntImmutableList of(int ... nArray) {
        return nArray.length == 0 ? IntImmutableList.of() : new IntImmutableList(nArray);
    }

    private static IntImmutableList convertTrustedToImmutableList(IntArrayList intArrayList) {
        if (intArrayList.isEmpty()) {
            return IntImmutableList.of();
        }
        int[] nArray = intArrayList.elements();
        if (intArrayList.size() != nArray.length) {
            nArray = Arrays.copyOf(nArray, intArrayList.size());
        }
        return new IntImmutableList(nArray);
    }

    public static IntImmutableList toList(IntStream intStream) {
        return IntImmutableList.convertTrustedToImmutableList(IntArrayList.toList(intStream));
    }

    public static IntImmutableList toListWithExpectedSize(IntStream intStream, int n) {
        return IntImmutableList.convertTrustedToImmutableList(IntArrayList.toListWithExpectedSize(intStream, n));
    }

    @Override
    public int getInt(int n) {
        if (n >= this.a.length) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.a.length + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(int n) {
        int n2 = this.a.length;
        for (int i = 0; i < n2; ++i) {
            if (n != this.a[i]) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(int n) {
        int n2 = this.a.length;
        while (n2-- != 0) {
            if (n != this.a[n2]) continue;
            return n2;
        }
        return 1;
    }

    @Override
    public int size() {
        return this.a.length;
    }

    @Override
    public boolean isEmpty() {
        return this.a.length == 0;
    }

    @Override
    public void getElements(int n, int[] nArray, int n2, int n3) {
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        System.arraycopy(this.a, n, nArray, n2, n3);
    }

    @Override
    public void forEach(IntConsumer intConsumer) {
        for (int i = 0; i < this.a.length; ++i) {
            intConsumer.accept(this.a[i]);
        }
    }

    @Override
    public int[] toIntArray() {
        if (this.a.length == 0) {
            return IntArrays.EMPTY_ARRAY;
        }
        return (int[])this.a.clone();
    }

    @Override
    public int[] toArray(int[] nArray) {
        if (nArray == null || nArray.length < this.size()) {
            nArray = new int[this.a.length];
        }
        System.arraycopy(this.a, 0, nArray, 0, nArray.length);
        return nArray;
    }

    @Override
    public IntListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new IntListIterator(){
            int pos;
            final int val$index;
            final IntImmutableList this$0;
            {
                this.this$0 = intImmutableList;
                this.val$index = n;
                this.pos = this.val$index;
            }

            @Override
            public boolean hasNext() {
                return this.pos < IntImmutableList.access$000(this.this$0).length;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return IntImmutableList.access$000(this.this$0)[this.pos++];
            }

            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return IntImmutableList.access$000(this.this$0)[--this.pos];
            }

            @Override
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void forEachRemaining(IntConsumer intConsumer) {
                while (this.pos < IntImmutableList.access$000(this.this$0).length) {
                    intConsumer.accept(IntImmutableList.access$000(this.this$0)[this.pos++]);
                }
            }

            @Override
            public void add(int n) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(int n) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = IntImmutableList.access$000(this.this$0).length - this.pos;
                if (n < n2) {
                    this.pos -= n;
                } else {
                    n = n2;
                    this.pos = 0;
                }
                return n;
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = IntImmutableList.access$000(this.this$0).length - this.pos;
                if (n < n2) {
                    this.pos += n;
                } else {
                    n = n2;
                    this.pos = IntImmutableList.access$000(this.this$0).length;
                }
                return n;
            }

            @Override
            public void forEachRemaining(Object object) {
                this.forEachRemaining((IntConsumer)object);
            }
        };
    }

    @Override
    public IntSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override
    public IntList subList(int n, int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n == n2) {
            return EMPTY;
        }
        if (n > n2) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new ImmutableSubList(this, n, n2);
    }

    public IntImmutableList clone() {
        return this;
    }

    public boolean equals(IntImmutableList intImmutableList) {
        if (intImmutableList == this) {
            return false;
        }
        if (this.a == intImmutableList.a) {
            return false;
        }
        int n = this.size();
        if (n != intImmutableList.size()) {
            return true;
        }
        int[] nArray = this.a;
        int[] nArray2 = intImmutableList.a;
        return Arrays.equals(nArray, nArray2);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof List)) {
            return true;
        }
        if (object instanceof IntImmutableList) {
            return this.equals((IntImmutableList)object);
        }
        if (object instanceof ImmutableSubList) {
            return ((ImmutableSubList)object).equals(this);
        }
        return super.equals(object);
    }

    @Override
    public int compareTo(IntImmutableList intImmutableList) {
        int n;
        if (this.a == intImmutableList.a) {
            return 1;
        }
        int n2 = this.size();
        int n3 = intImmutableList.size();
        int[] nArray = this.a;
        int[] nArray2 = intImmutableList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            int n4 = nArray[n];
            int n5 = nArray2[n];
            int n6 = Integer.compare(n4, n5);
            if (n6 == 0) continue;
            return n6;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    @Override
    public int compareTo(List<? extends Integer> list) {
        if (list instanceof IntImmutableList) {
            return this.compareTo((IntImmutableList)list);
        }
        if (list instanceof ImmutableSubList) {
            ImmutableSubList immutableSubList = (ImmutableSubList)list;
            return -immutableSubList.compareTo(this);
        }
        return super.compareTo(list);
    }

    @Override
    public java.util.Spliterator spliterator() {
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
    public int compareTo(Object object) {
        return this.compareTo((List)object);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int[] access$000(IntImmutableList intImmutableList) {
        return intImmutableList.a;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class Spliterator
    implements IntSpliterator {
        int pos;
        int max;
        static final boolean $assertionsDisabled = !IntImmutableList.class.desiredAssertionStatus();
        final IntImmutableList this$0;

        public Spliterator(IntImmutableList intImmutableList) {
            this(intImmutableList, 0, IntImmutableList.access$000(intImmutableList).length);
        }

        private Spliterator(IntImmutableList intImmutableList, int n, int n2) {
            this.this$0 = intImmutableList;
            if (!$assertionsDisabled && n > n2) {
                throw new AssertionError((Object)("pos " + n + " must be <= max " + n2));
            }
            this.pos = n;
            this.max = n2;
        }

        @Override
        public int characteristics() {
            return 1;
        }

        @Override
        public long estimateSize() {
            return this.max - this.pos;
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            if (this.pos >= this.max) {
                return true;
            }
            intConsumer.accept(IntImmutableList.access$000(this.this$0)[this.pos++]);
            return false;
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            while (this.pos < this.max) {
                intConsumer.accept(IntImmutableList.access$000(this.this$0)[this.pos]);
                ++this.pos;
            }
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (this.pos >= this.max) {
                return 0L;
            }
            int n = this.max - this.pos;
            if (l < (long)n) {
                this.pos = SafeMath.safeLongToInt((long)this.pos + l);
                return l;
            }
            l = n;
            this.pos = this.max;
            return l;
        }

        @Override
        public IntSpliterator trySplit() {
            int n;
            int n2 = this.max - this.pos >> 1;
            if (n2 <= 1) {
                return null;
            }
            int n3 = n = this.pos + n2;
            int n4 = this.pos;
            this.pos = n;
            return new Spliterator(this.this$0, n4, n3);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }

        @Override
        public void forEachRemaining(Object object) {
            this.forEachRemaining((IntConsumer)object);
        }

        @Override
        public boolean tryAdvance(Object object) {
            return this.tryAdvance((IntConsumer)object);
        }

        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }

        @Override
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class ImmutableSubList
    extends IntLists.ImmutableListBase
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 7054639518438982401L;
        final IntImmutableList innerList;
        final int from;
        final int to;
        final transient int[] a;

        ImmutableSubList(IntImmutableList intImmutableList, int n, int n2) {
            this.innerList = intImmutableList;
            this.from = n;
            this.to = n2;
            this.a = IntImmutableList.access$000(intImmutableList);
        }

        @Override
        public int getInt(int n) {
            this.ensureRestrictedIndex(n);
            return this.a[n + this.from];
        }

        @Override
        public int indexOf(int n) {
            for (int i = this.from; i < this.to; ++i) {
                if (n != this.a[i]) continue;
                return i - this.from;
            }
            return 1;
        }

        @Override
        public int lastIndexOf(int n) {
            int n2 = this.to;
            while (n2-- != this.from) {
                if (n != this.a[n2]) continue;
                return n2 - this.from;
            }
            return 1;
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public boolean isEmpty() {
            return this.to <= this.from;
        }

        @Override
        public void getElements(int n, int[] nArray, int n2, int n3) {
            IntArrays.ensureOffsetLength(nArray, n2, n3);
            this.ensureRestrictedIndex(n);
            if (this.from + n3 > this.to) {
                throw new IndexOutOfBoundsException("Final index " + (this.from + n3) + " (startingIndex: " + this.from + " + length: " + n3 + ") is greater then list length " + this.size());
            }
            System.arraycopy(this.a, n + this.from, nArray, n2, n3);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            for (int i = this.from; i < this.to; ++i) {
                intConsumer.accept(this.a[i]);
            }
        }

        @Override
        public int[] toIntArray() {
            return Arrays.copyOfRange(this.a, this.from, this.to);
        }

        @Override
        public int[] toArray(int[] nArray) {
            if (nArray == null || nArray.length < this.size()) {
                nArray = new int[this.size()];
            }
            System.arraycopy(this.a, this.from, nArray, 0, this.size());
            return nArray;
        }

        @Override
        public IntListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new IntListIterator(){
                int pos;
                final int val$index;
                final ImmutableSubList this$0;
                {
                    this.this$0 = immutableSubList;
                    this.val$index = n;
                    this.pos = this.val$index;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.to;
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > this.this$0.from;
                }

                @Override
                public int nextInt() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return this.this$0.a[this.pos++ + this.this$0.from];
                }

                @Override
                public int previousInt() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    return this.this$0.a[--this.pos + this.this$0.from];
                }

                @Override
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void forEachRemaining(IntConsumer intConsumer) {
                    while (this.pos < this.this$0.to) {
                        intConsumer.accept(this.this$0.a[this.pos++ + this.this$0.from]);
                    }
                }

                @Override
                public void add(int n) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void set(int n) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int back(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int n2 = this.this$0.to - this.pos;
                    if (n < n2) {
                        this.pos -= n;
                    } else {
                        n = n2;
                        this.pos = 0;
                    }
                    return n;
                }

                @Override
                public int skip(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int n2 = this.this$0.to - this.pos;
                    if (n < n2) {
                        this.pos += n;
                    } else {
                        n = n2;
                        this.pos = this.this$0.to;
                    }
                    return n;
                }

                @Override
                public void forEachRemaining(Object object) {
                    this.forEachRemaining((IntConsumer)object);
                }
            };
        }

        @Override
        public IntSpliterator spliterator() {
            return new SubListSpliterator(this);
        }

        boolean contentsEquals(int[] nArray, int n, int n2) {
            if (this.a == nArray && this.from == n && this.to == n2) {
                return false;
            }
            if (n2 - n != this.size()) {
                return true;
            }
            int n3 = this.from;
            int n4 = n;
            while (n3 < this.to) {
                if (this.a[n3++] == nArray[n4++]) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null) {
                return true;
            }
            if (!(object instanceof List)) {
                return true;
            }
            if (object instanceof IntImmutableList) {
                IntImmutableList intImmutableList = (IntImmutableList)object;
                return this.contentsEquals(IntImmutableList.access$000(intImmutableList), 0, intImmutableList.size());
            }
            if (object instanceof ImmutableSubList) {
                ImmutableSubList immutableSubList = (ImmutableSubList)object;
                return this.contentsEquals(immutableSubList.a, immutableSubList.from, immutableSubList.to);
            }
            return super.equals(object);
        }

        int contentsCompareTo(int[] nArray, int n, int n2) {
            if (this.a == nArray && this.from == n && this.to == n2) {
                return 1;
            }
            int n3 = this.from;
            int n4 = n;
            while (n3 < this.to && n3 < n2) {
                int n5 = this.a[n3];
                int n6 = nArray[n4];
                int n7 = Integer.compare(n5, n6);
                if (n7 != 0) {
                    return n7;
                }
                ++n3;
                ++n4;
            }
            return n3 < n2 ? -1 : (n3 < this.to ? 1 : 0);
        }

        @Override
        public int compareTo(List<? extends Integer> list) {
            if (list instanceof IntImmutableList) {
                IntImmutableList intImmutableList = (IntImmutableList)list;
                return this.contentsCompareTo(IntImmutableList.access$000(intImmutableList), 0, intImmutableList.size());
            }
            if (list instanceof ImmutableSubList) {
                ImmutableSubList immutableSubList = (ImmutableSubList)list;
                return this.contentsCompareTo(immutableSubList.a, immutableSubList.from, immutableSubList.to);
            }
            return super.compareTo(list);
        }

        private Object readResolve() throws ObjectStreamException {
            try {
                return this.innerList.subList(this.from, this.to);
            } catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {
                throw (InvalidObjectException)new InvalidObjectException(runtimeException.getMessage()).initCause(runtimeException);
            }
        }

        @Override
        public IntList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n == n2) {
                return EMPTY;
            }
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ImmutableSubList(this.innerList, n + this.from, n2 + this.from);
        }

        @Override
        public java.util.Spliterator spliterator() {
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
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private final class SubListSpliterator
        extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator {
            final ImmutableSubList this$0;

            SubListSpliterator(ImmutableSubList immutableSubList) {
                this.this$0 = immutableSubList;
                super(immutableSubList.from, immutableSubList.to);
            }

            private SubListSpliterator(ImmutableSubList immutableSubList, int n, int n2) {
                this.this$0 = immutableSubList;
                super(n, n2);
            }

            @Override
            protected final int get(int n) {
                return this.this$0.a[n];
            }

            @Override
            protected final SubListSpliterator makeForSplit(int n, int n2) {
                return new SubListSpliterator(this.this$0, n, n2);
            }

            @Override
            public boolean tryAdvance(IntConsumer intConsumer) {
                if (this.pos >= this.maxPos) {
                    return true;
                }
                intConsumer.accept(this.this$0.a[this.pos++]);
                return false;
            }

            @Override
            public void forEachRemaining(IntConsumer intConsumer) {
                int n = this.maxPos;
                while (this.pos < n) {
                    intConsumer.accept(this.this$0.a[this.pos++]);
                }
            }

            @Override
            public int characteristics() {
                return 1;
            }

            @Override
            protected IntSpliterator makeForSplit(int n, int n2) {
                return this.makeForSplit(n, n2);
            }

            @Override
            public void forEachRemaining(Object object) {
                this.forEachRemaining((IntConsumer)object);
            }

            @Override
            public boolean tryAdvance(Object object) {
                return this.tryAdvance((IntConsumer)object);
            }
        }
    }
}

