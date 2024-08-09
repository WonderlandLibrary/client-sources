/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
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
public class LongArraySet
extends AbstractLongSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] a;
    private int size;

    public LongArraySet(long[] lArray) {
        this.a = lArray;
        this.size = lArray.length;
    }

    public LongArraySet() {
        this.a = LongArrays.EMPTY_ARRAY;
    }

    public LongArraySet(int n) {
        this.a = new long[n];
    }

    public LongArraySet(LongCollection longCollection) {
        this(longCollection.size());
        this.addAll(longCollection);
    }

    public LongArraySet(Collection<? extends Long> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public LongArraySet(long[] lArray, int n) {
        this.a = lArray;
        this.size = n;
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + lArray.length + ")");
        }
    }

    private int findKey(long l) {
        int n = this.size;
        while (n-- != 0) {
            if (this.a[n] != l) continue;
            return n;
        }
        return 1;
    }

    @Override
    public LongIterator iterator() {
        return new LongIterator(this){
            int next;
            final LongArraySet this$0;
            {
                this.this$0 = longArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < LongArraySet.access$000(this.this$0);
            }

            @Override
            public long nextLong() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return LongArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = LongArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(LongArraySet.access$100(this.this$0), this.next + 1, LongArraySet.access$100(this.this$0), this.next, n);
            }
        };
    }

    @Override
    public boolean contains(long l) {
        return this.findKey(l) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(long l) {
        int n = this.findKey(l);
        if (n == -1) {
            return true;
        }
        int n2 = this.size - n - 1;
        for (int i = 0; i < n2; ++i) {
            this.a[n + i] = this.a[n + i + 1];
        }
        --this.size;
        return false;
    }

    @Override
    public boolean add(long l) {
        int n = this.findKey(l);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.a[n2];
            }
            this.a = lArray;
        }
        this.a[this.size++] = l;
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

    public LongArraySet clone() {
        LongArraySet longArraySet;
        try {
            longArraySet = (LongArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        longArraySet.a = (long[])this.a.clone();
        return longArraySet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readLong();
        }
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(LongArraySet longArraySet) {
        return longArraySet.size;
    }

    static long[] access$100(LongArraySet longArraySet) {
        return longArraySet.a;
    }

    static int access$010(LongArraySet longArraySet) {
        return longArraySet.size--;
    }
}

