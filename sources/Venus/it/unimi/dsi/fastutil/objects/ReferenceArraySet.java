/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReferenceArraySet<K>
extends AbstractReferenceSet<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] a;
    private int size;

    public ReferenceArraySet(Object[] objectArray) {
        this.a = objectArray;
        this.size = objectArray.length;
    }

    public ReferenceArraySet() {
        this.a = ObjectArrays.EMPTY_ARRAY;
    }

    public ReferenceArraySet(int n) {
        this.a = new Object[n];
    }

    public ReferenceArraySet(ReferenceCollection<K> referenceCollection) {
        this(referenceCollection.size());
        this.addAll(referenceCollection);
    }

    public ReferenceArraySet(Collection<? extends K> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public ReferenceArraySet(Object[] objectArray, int n) {
        this.a = objectArray;
        this.size = n;
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + objectArray.length + ")");
        }
    }

    private int findKey(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (this.a[n] != object) continue;
            return n;
        }
        return 1;
    }

    @Override
    public ObjectIterator<K> iterator() {
        return new ObjectIterator<K>(this){
            int next;
            final ReferenceArraySet this$0;
            {
                this.this$0 = referenceArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < ReferenceArraySet.access$000(this.this$0);
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return ReferenceArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = ReferenceArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(ReferenceArraySet.access$100(this.this$0), this.next + 1, ReferenceArraySet.access$100(this.this$0), this.next, n);
                ReferenceArraySet.access$100((ReferenceArraySet)this.this$0)[ReferenceArraySet.access$000((ReferenceArraySet)this.this$0)] = null;
            }
        };
    }

    @Override
    public boolean contains(Object object) {
        return this.findKey(object) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return true;
        }
        int n2 = this.size - n - 1;
        for (int i = 0; i < n2; ++i) {
            this.a[n + i] = this.a[n + i + 1];
        }
        --this.size;
        this.a[this.size] = null;
        return false;
    }

    @Override
    public boolean add(K k) {
        int n = this.findKey(k);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.a[n2];
            }
            this.a = objectArray;
        }
        this.a[this.size++] = k;
        return false;
    }

    @Override
    public void clear() {
        Arrays.fill(this.a, 0, this.size, null);
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public ReferenceArraySet<K> clone() {
        ReferenceArraySet referenceArraySet;
        try {
            referenceArraySet = (ReferenceArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        referenceArraySet.a = (Object[])this.a.clone();
        return referenceArraySet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readObject();
        }
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(ReferenceArraySet referenceArraySet) {
        return referenceArraySet.size;
    }

    static Object[] access$100(ReferenceArraySet referenceArraySet) {
        return referenceArraySet.a;
    }

    static int access$010(ReferenceArraySet referenceArraySet) {
        return referenceArraySet.size--;
    }
}

