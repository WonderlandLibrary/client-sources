/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntArraySet
extends AbstractIntSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] a;
    private int size;

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

    public IntArraySet(int[] nArray, int n) {
        this.a = nArray;
        this.size = n;
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + nArray.length + ")");
        }
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
                return this.next < IntArraySet.access$000(this.this$0);
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return IntArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = IntArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(IntArraySet.access$100(this.this$0), this.next + 1, IntArraySet.access$100(this.this$0), this.next, n);
            }
        };
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
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(IntArraySet intArraySet) {
        return intArraySet.size;
    }

    static int[] access$100(IntArraySet intArraySet) {
        return intArraySet.a;
    }

    static int access$010(IntArraySet intArraySet) {
        return intArraySet.size--;
    }
}

