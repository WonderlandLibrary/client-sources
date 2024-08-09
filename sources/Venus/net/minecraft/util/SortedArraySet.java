/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArraySet<T>
extends AbstractSet<T> {
    private final Comparator<T> comparator;
    private T[] storage;
    private int maxIndex;

    private SortedArraySet(int n, Comparator<T> comparator) {
        this.comparator = comparator;
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.storage = SortedArraySet.cast(new Object[n]);
    }

    public static <T extends Comparable<T>> SortedArraySet<T> newSet(int n) {
        return new SortedArraySet(n, Comparator.naturalOrder());
    }

    private static <T> T[] cast(Object[] objectArray) {
        return objectArray;
    }

    private int binarySearch(T t) {
        return Arrays.binarySearch(this.storage, 0, this.maxIndex, t, this.comparator);
    }

    private static int func_226179_b_(int n) {
        return -n - 1;
    }

    @Override
    public boolean add(T t) {
        int n = this.binarySearch(t);
        if (n >= 0) {
            return true;
        }
        int n2 = SortedArraySet.func_226179_b_(n);
        this.func_226176_a_(t, n2);
        return false;
    }

    private void func_226181_c_(int n) {
        if (n > this.storage.length) {
            if (this.storage != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
                n = (int)Math.max(Math.min((long)this.storage.length + (long)(this.storage.length >> 1), 0x7FFFFFF7L), (long)n);
            } else if (n < 10) {
                n = 10;
            }
            Object[] objectArray = new Object[n];
            System.arraycopy(this.storage, 0, objectArray, 0, this.maxIndex);
            this.storage = SortedArraySet.cast(objectArray);
        }
    }

    private void func_226176_a_(T t, int n) {
        this.func_226181_c_(this.maxIndex + 1);
        if (n != this.maxIndex) {
            System.arraycopy(this.storage, n, this.storage, n + 1, this.maxIndex - n);
        }
        this.storage[n] = t;
        ++this.maxIndex;
    }

    private void func_226183_d_(int n) {
        --this.maxIndex;
        if (n != this.maxIndex) {
            System.arraycopy(this.storage, n + 1, this.storage, n, this.maxIndex - n);
        }
        this.storage[this.maxIndex] = null;
    }

    private T func_226184_e_(int n) {
        return this.storage[n];
    }

    public T func_226175_a_(T t) {
        int n = this.binarySearch(t);
        if (n >= 0) {
            return this.func_226184_e_(n);
        }
        this.func_226176_a_(t, SortedArraySet.func_226179_b_(n));
        return t;
    }

    @Override
    public boolean remove(Object object) {
        int n = this.binarySearch(object);
        if (n >= 0) {
            this.func_226183_d_(n);
            return false;
        }
        return true;
    }

    public T getSmallest() {
        return this.func_226184_e_(0);
    }

    @Override
    public boolean contains(Object object) {
        int n = this.binarySearch(object);
        return n >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr(this);
    }

    @Override
    public int size() {
        return this.maxIndex;
    }

    @Override
    public Object[] toArray() {
        return (Object[])this.storage.clone();
    }

    @Override
    public <U> U[] toArray(U[] UArray) {
        if (UArray.length < this.maxIndex) {
            return Arrays.copyOf(this.storage, this.maxIndex, UArray.getClass());
        }
        System.arraycopy(this.storage, 0, UArray, 0, this.maxIndex);
        if (UArray.length > this.maxIndex) {
            UArray[this.maxIndex] = null;
        }
        return UArray;
    }

    @Override
    public void clear() {
        Arrays.fill(this.storage, 0, this.maxIndex, null);
        this.maxIndex = 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof SortedArraySet) {
            SortedArraySet sortedArraySet = (SortedArraySet)object;
            if (this.comparator.equals(sortedArraySet.comparator)) {
                return this.maxIndex == sortedArraySet.maxIndex && Arrays.equals(this.storage, sortedArraySet.storage);
            }
        }
        return super.equals(object);
    }

    class Itr
    implements Iterator<T> {
        private int field_226186_b_;
        private int field_226187_c_;
        final SortedArraySet this$0;

        private Itr(SortedArraySet sortedArraySet) {
            this.this$0 = sortedArraySet;
            this.field_226187_c_ = -1;
        }

        @Override
        public boolean hasNext() {
            return this.field_226186_b_ < this.this$0.maxIndex;
        }

        @Override
        public T next() {
            if (this.field_226186_b_ >= this.this$0.maxIndex) {
                throw new NoSuchElementException();
            }
            this.field_226187_c_ = this.field_226186_b_++;
            return this.this$0.storage[this.field_226187_c_];
        }

        @Override
        public void remove() {
            if (this.field_226187_c_ == -1) {
                throw new IllegalStateException();
            }
            this.this$0.func_226183_d_(this.field_226187_c_);
            --this.field_226186_b_;
            this.field_226187_c_ = -1;
        }
    }
}

