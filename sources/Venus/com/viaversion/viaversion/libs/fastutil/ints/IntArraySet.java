/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntArraySet
extends AbstractIntSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    protected transient int[] a;
    protected int size;

    public IntArraySet(int[] nArray) {
        this.a = nArray;
        this.size = nArray.length;
    }

    public IntArraySet() {
        this.a = IntArrays.EMPTY_ARRAY;
    }

    public IntArraySet(int n) {
        this.a = new int[n];
    }

    public IntArraySet(IntCollection intCollection) {
        this(intCollection.size());
        this.addAll(intCollection);
    }

    public IntArraySet(Collection<? extends Integer> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public IntArraySet(IntSet intSet) {
        this(intSet.size());
        int n = 0;
        IntIterator intIterator = intSet.iterator();
        while (intIterator.hasNext()) {
            int n2;
            this.a[n] = n2 = ((Integer)intIterator.next()).intValue();
            ++n;
        }
        this.size = n;
    }

    public IntArraySet(Set<? extends Integer> set) {
        this(set.size());
        int n = 0;
        for (Integer n2 : set) {
            this.a[n] = n2;
            ++n;
        }
        this.size = n;
    }

    public IntArraySet(int[] nArray, int n) {
        this.a = nArray;
        this.size = n;
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + nArray.length + ")");
        }
    }

    public static IntArraySet of() {
        return IntArraySet.ofUnchecked();
    }

    public static IntArraySet of(int n) {
        return IntArraySet.ofUnchecked(n);
    }

    public static IntArraySet of(int ... nArray) {
        if (nArray.length == 2) {
            if (nArray[0] == nArray[1]) {
                throw new IllegalArgumentException("Duplicate element: " + nArray[1]);
            }
        } else if (nArray.length > 2) {
            IntOpenHashSet.of(nArray);
        }
        return IntArraySet.ofUnchecked(nArray);
    }

    public static IntArraySet ofUnchecked() {
        return new IntArraySet();
    }

    public static IntArraySet ofUnchecked(int ... nArray) {
        return new IntArraySet(nArray);
    }

    private int findKey(int n) {
        int n2 = this.size;
        while (n2-- != 0) {
            if (this.a[n2] != n) continue;
            return n2;
        }
        return 1;
    }

    @Override
    public IntIterator iterator() {
        return new IntIterator(this){
            int next;
            final IntArraySet this$0;
            {
                this.this$0 = intArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < this.this$0.size;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.this$0.a[this.next++];
            }

            @Override
            public void remove() {
                int n = this.this$0.size-- - this.next--;
                System.arraycopy(this.this$0.a, this.next + 1, this.this$0.a, this.next, n);
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = this.this$0.size - this.next;
                if (n < n2) {
                    this.next += n;
                    return n;
                }
                n = n2;
                this.next = this.this$0.size;
                return n;
            }
        };
    }

    @Override
    public IntSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override
    public boolean contains(int n) {
        return this.findKey(n) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return true;
        }
        int n3 = this.size - n2 - 1;
        for (int i = 0; i < n3; ++i) {
            this.a[n2 + i] = this.a[n2 + i + 1];
        }
        --this.size;
        return false;
    }

    @Override
    public boolean add(int n) {
        int n2 = this.findKey(n);
        if (n2 != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n3 = this.size;
            while (n3-- != 0) {
                nArray[n3] = this.a[n3];
            }
            this.a = nArray;
        }
        this.a[this.size++] = n;
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int[] toIntArray() {
        if (this.size == 0) {
            return IntArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(this.a, this.size);
    }

    @Override
    public int[] toArray(int[] nArray) {
        if (nArray == null || nArray.length < this.size) {
            nArray = new int[this.size];
        }
        System.arraycopy(this.a, 0, nArray, 0, this.size);
        return nArray;
    }

    public IntArraySet clone() {
        IntArraySet intArraySet;
        try {
            intArraySet = (IntArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        intArraySet.a = (int[])this.a.clone();
        return intArraySet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readInt();
        }
    }

    @Override
    public java.util.Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class Spliterator
    implements IntSpliterator {
        boolean hasSplit;
        int pos;
        int max;
        static final boolean $assertionsDisabled = !IntArraySet.class.desiredAssertionStatus();
        final IntArraySet this$0;

        public Spliterator(IntArraySet intArraySet) {
            this(intArraySet, 0, intArraySet.size, false);
        }

        private Spliterator(IntArraySet intArraySet, int n, int n2, boolean bl) {
            this.this$0 = intArraySet;
            this.hasSplit = false;
            if (!$assertionsDisabled && n > n2) {
                throw new AssertionError((Object)("pos " + n + " must be <= max " + n2));
            }
            this.pos = n;
            this.max = n2;
            this.hasSplit = bl;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : this.this$0.size;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            if (this.pos >= this.getWorkingMax()) {
                return true;
            }
            intConsumer.accept(this.this$0.a[this.pos++]);
            return false;
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            int n = this.getWorkingMax();
            while (this.pos < n) {
                intConsumer.accept(this.this$0.a[this.pos]);
                ++this.pos;
            }
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            int n = this.getWorkingMax();
            if (this.pos >= n) {
                return 0L;
            }
            int n2 = n - this.pos;
            if (l < (long)n2) {
                this.pos = SafeMath.safeLongToInt((long)this.pos + l);
                return l;
            }
            l = n2;
            this.pos = n;
            return l;
        }

        @Override
        public IntSpliterator trySplit() {
            int n;
            int n2 = this.getWorkingMax();
            int n3 = n2 - this.pos >> 1;
            if (n3 <= 1) {
                return null;
            }
            this.max = n2;
            int n4 = n = this.pos + n3;
            int n5 = this.pos;
            this.pos = n;
            this.hasSplit = true;
            return new Spliterator(this.this$0, n5, n4, true);
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
}

