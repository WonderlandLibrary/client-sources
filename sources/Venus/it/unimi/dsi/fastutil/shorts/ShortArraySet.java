/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
public class ShortArraySet
extends AbstractShortSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] a;
    private int size;

    public ShortArraySet(short[] sArray) {
        this.a = sArray;
        this.size = sArray.length;
    }

    public ShortArraySet() {
        this.a = ShortArrays.EMPTY_ARRAY;
    }

    public ShortArraySet(int n) {
        this.a = new short[n];
    }

    public ShortArraySet(ShortCollection shortCollection) {
        this(shortCollection.size());
        this.addAll(shortCollection);
    }

    public ShortArraySet(Collection<? extends Short> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public ShortArraySet(short[] sArray, int n) {
        this.a = sArray;
        this.size = n;
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + sArray.length + ")");
        }
    }

    private int findKey(short s) {
        int n = this.size;
        while (n-- != 0) {
            if (this.a[n] != s) continue;
            return n;
        }
        return 1;
    }

    @Override
    public ShortIterator iterator() {
        return new ShortIterator(this){
            int next;
            final ShortArraySet this$0;
            {
                this.this$0 = shortArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < ShortArraySet.access$000(this.this$0);
            }

            @Override
            public short nextShort() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return ShortArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = ShortArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(ShortArraySet.access$100(this.this$0), this.next + 1, ShortArraySet.access$100(this.this$0), this.next, n);
            }
        };
    }

    @Override
    public boolean contains(short s) {
        return this.findKey(s) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(short s) {
        int n = this.findKey(s);
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
    public boolean add(short s) {
        int n = this.findKey(s);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                sArray[n2] = this.a[n2];
            }
            this.a = sArray;
        }
        this.a[this.size++] = s;
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

    public ShortArraySet clone() {
        ShortArraySet shortArraySet;
        try {
            shortArraySet = (ShortArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        shortArraySet.a = (short[])this.a.clone();
        return shortArraySet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readShort();
        }
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(ShortArraySet shortArraySet) {
        return shortArraySet.size;
    }

    static short[] access$100(ShortArraySet shortArraySet) {
        return shortArraySet.a;
    }

    static int access$010(ShortArraySet shortArraySet) {
        return shortArraySet.size--;
    }
}

