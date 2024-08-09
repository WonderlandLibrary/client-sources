/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanSet;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
public class BooleanArraySet
extends AbstractBooleanSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient boolean[] a;
    private int size;

    public BooleanArraySet(boolean[] blArray) {
        this.a = blArray;
        this.size = blArray.length;
    }

    public BooleanArraySet() {
        this.a = BooleanArrays.EMPTY_ARRAY;
    }

    public BooleanArraySet(int n) {
        this.a = new boolean[n];
    }

    public BooleanArraySet(BooleanCollection booleanCollection) {
        this(booleanCollection.size());
        this.addAll(booleanCollection);
    }

    public BooleanArraySet(Collection<? extends Boolean> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public BooleanArraySet(boolean[] blArray, int n) {
        this.a = blArray;
        this.size = n;
        if (n > blArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + blArray.length + ")");
        }
    }

    private int findKey(boolean bl) {
        int n = this.size;
        while (n-- != 0) {
            if (this.a[n] != bl) continue;
            return n;
        }
        return 1;
    }

    @Override
    public BooleanIterator iterator() {
        return new BooleanIterator(this){
            int next;
            final BooleanArraySet this$0;
            {
                this.this$0 = booleanArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < BooleanArraySet.access$000(this.this$0);
            }

            @Override
            public boolean nextBoolean() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return BooleanArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = BooleanArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(BooleanArraySet.access$100(this.this$0), this.next + 1, BooleanArraySet.access$100(this.this$0), this.next, n);
            }
        };
    }

    @Override
    public boolean contains(boolean bl) {
        return this.findKey(bl) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(boolean bl) {
        int n = this.findKey(bl);
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
    public boolean add(boolean bl) {
        int n = this.findKey(bl);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                blArray[n2] = this.a[n2];
            }
            this.a = blArray;
        }
        this.a[this.size++] = bl;
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

    public BooleanArraySet clone() {
        BooleanArraySet booleanArraySet;
        try {
            booleanArraySet = (BooleanArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        booleanArraySet.a = (boolean[])this.a.clone();
        return booleanArraySet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeBoolean(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readBoolean();
        }
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(BooleanArraySet booleanArraySet) {
        return booleanArraySet.size;
    }

    static boolean[] access$100(BooleanArraySet booleanArraySet) {
        return booleanArraySet.a;
    }

    static int access$010(BooleanArraySet booleanArraySet) {
        return booleanArraySet.size--;
    }
}

