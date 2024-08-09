/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.objects.AbstractReferenceBigList;
import it.unimi.dsi.fastutil.objects.ObjectBigArrays;
import it.unimi.dsi.fastutil.objects.ObjectBigListIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceBigList;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class ReferenceBigArrayBigList<K>
extends AbstractReferenceBigList<K>
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353131L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected final boolean wrapped;
    protected transient K[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !ReferenceBigArrayBigList.class.desiredAssertionStatus();

    protected ReferenceBigArrayBigList(K[][] KArray, boolean bl) {
        this.a = KArray;
        this.wrapped = true;
    }

    public ReferenceBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? ObjectBigArrays.EMPTY_BIG_ARRAY : ObjectBigArrays.newBigArray(l);
        this.wrapped = false;
    }

    public ReferenceBigArrayBigList() {
        this.a = ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
        this.wrapped = false;
    }

    public ReferenceBigArrayBigList(ReferenceCollection<? extends K> referenceCollection) {
        this(referenceCollection.size());
        Iterator iterator2 = referenceCollection.iterator();
        while (iterator2.hasNext()) {
            this.add((K)iterator2.next());
        }
    }

    public ReferenceBigArrayBigList(ReferenceBigList<? extends K> referenceBigList) {
        this(referenceBigList.size64());
        this.size = referenceBigList.size64();
        referenceBigList.getElements(0L, this.a, 0L, this.size);
    }

    public ReferenceBigArrayBigList(K[][] KArray) {
        this(KArray, 0L, ObjectBigArrays.length(KArray));
    }

    public ReferenceBigArrayBigList(K[][] KArray, long l, long l2) {
        this(l2);
        ObjectBigArrays.copy(KArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public ReferenceBigArrayBigList(Iterator<? extends K> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add(iterator2.next());
        }
    }

    public ReferenceBigArrayBigList(ObjectIterator<? extends K> objectIterator) {
        this();
        while (objectIterator.hasNext()) {
            this.add((K)objectIterator.next());
        }
    }

    public K[][] elements() {
        return this.a;
    }

    public static <K> ReferenceBigArrayBigList<K> wrap(K[][] KArray, long l) {
        if (l > ObjectBigArrays.length(KArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + ObjectBigArrays.length(KArray) + ")");
        }
        ReferenceBigArrayBigList<K> referenceBigArrayBigList = new ReferenceBigArrayBigList<K>(KArray, false);
        referenceBigArrayBigList.size = l;
        return referenceBigArrayBigList;
    }

    public static <K> ReferenceBigArrayBigList<K> wrap(K[][] KArray) {
        return ReferenceBigArrayBigList.wrap(KArray, ObjectBigArrays.length(KArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        if (this.wrapped) {
            this.a = ObjectBigArrays.forceCapacity(this.a, l, this.size);
        } else if (l > ObjectBigArrays.length(this.a)) {
            Object[][] objectArray = ObjectBigArrays.newBigArray(l);
            ObjectBigArrays.copy(this.a, 0L, objectArray, 0L, this.size);
            this.a = objectArray;
        }
        if (!$assertionsDisabled && this.size > ObjectBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = ObjectBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        if (this.wrapped) {
            this.a = ObjectBigArrays.forceCapacity(this.a, l, this.size);
        } else {
            Object[][] objectArray = ObjectBigArrays.newBigArray(l);
            ObjectBigArrays.copy(this.a, 0L, objectArray, 0L, this.size);
            this.a = objectArray;
        }
        if (!$assertionsDisabled && this.size > ObjectBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, K k) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            ObjectBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        ObjectBigArrays.set(this.a, l, k);
        ++this.size;
        if (!$assertionsDisabled && this.size > ObjectBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(K k) {
        this.grow(this.size + 1L);
        ObjectBigArrays.set(this.a, this.size++, k);
        if (!$assertionsDisabled && this.size > ObjectBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public K get(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return ObjectBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(Object object) {
        for (long i = 0L; i < this.size; ++i) {
            if (object != ObjectBigArrays.get(this.a, i)) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(Object object) {
        long l = this.size;
        while (l-- != 0L) {
            if (object != ObjectBigArrays.get(this.a, l)) continue;
            return l;
        }
        return -1L;
    }

    @Override
    public K remove(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        K k = ObjectBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            ObjectBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        ObjectBigArrays.set(this.a, this.size, null);
        if (!$assertionsDisabled && this.size > ObjectBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return k;
    }

    @Override
    public boolean remove(Object object) {
        long l = this.indexOf(object);
        if (l == -1L) {
            return true;
        }
        this.remove(l);
        if (!$assertionsDisabled && this.size > ObjectBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public K set(long l, K k) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        K k2 = ObjectBigArrays.get(this.a, l);
        ObjectBigArrays.set(this.a, l, k);
        return k2;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        long l;
        K[] KArray = null;
        K[] KArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                KArray = this.a[++n];
            }
            if (!collection.contains(KArray[n2])) {
                if (n4 == 0x8000000) {
                    KArray2 = this.a[++n3];
                    n4 = 0;
                }
                KArray2[n4++] = KArray[n2];
            }
            ++n2;
        }
        l = BigArrays.index(n3, n4);
        ObjectBigArrays.fill(this.a, l, this.size, null);
        boolean bl = this.size != l;
        this.size = l;
        return bl;
    }

    @Override
    public void clear() {
        ObjectBigArrays.fill(this.a, 0L, this.size, null);
        this.size = 0L;
        if (!$assertionsDisabled && this.size > ObjectBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > ObjectBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            ObjectBigArrays.fill(this.a, this.size, l, null);
        } else {
            ObjectBigArrays.fill(this.a, l, this.size, null);
        }
        this.size = l;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0L;
    }

    public void trim() {
        this.trim(0L);
    }

    public void trim(long l) {
        long l2 = ObjectBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = ObjectBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > ObjectBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, Object[][] objectArray, long l2, long l3) {
        ObjectBigArrays.copy(this.a, l, objectArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        ObjectBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
        ObjectBigArrays.fill(this.a, this.size, this.size + l2 - l, null);
    }

    @Override
    public void addElements(long l, K[][] KArray, long l2, long l3) {
        this.ensureIndex(l);
        ObjectBigArrays.ensureOffsetLength(KArray, l2, l3);
        this.grow(this.size + l3);
        ObjectBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        ObjectBigArrays.copy(KArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public ObjectBigListIterator<K> listIterator(long l) {
        this.ensureIndex(l);
        return new ObjectBigListIterator<K>(this, l){
            long pos;
            long last;
            final long val$index;
            final ReferenceBigArrayBigList this$0;
            {
                this.this$0 = referenceBigArrayBigList;
                this.val$index = l;
                this.pos = this.val$index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return ObjectBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return ObjectBigArrays.get(this.this$0.a, this.pos);
            }

            @Override
            public long nextIndex() {
                return this.pos;
            }

            @Override
            public long previousIndex() {
                return this.pos - 1L;
            }

            @Override
            public void add(K k) {
                this.this$0.add(this.pos++, k);
                this.last = -1L;
            }

            @Override
            public void set(K k) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public ReferenceBigArrayBigList<K> clone() {
        ReferenceBigArrayBigList<K> referenceBigArrayBigList = new ReferenceBigArrayBigList<K>(this.size);
        ObjectBigArrays.copy(this.a, 0L, referenceBigArrayBigList.a, 0L, this.size);
        referenceBigArrayBigList.size = this.size;
        return referenceBigArrayBigList;
    }

    public boolean equals(ReferenceBigArrayBigList<K> referenceBigArrayBigList) {
        if (referenceBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != referenceBigArrayBigList.size64()) {
            return true;
        }
        K[][] KArray = this.a;
        K[][] KArray2 = referenceBigArrayBigList.a;
        while (l-- != 0L) {
            if (ObjectBigArrays.get(KArray, l) == ObjectBigArrays.get(KArray2, l)) continue;
            return true;
        }
        return false;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = 0;
        while ((long)n < this.size) {
            objectOutputStream.writeObject(ObjectBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = ObjectBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            ObjectBigArrays.set(this.a, n, objectInputStream.readObject());
            ++n;
        }
    }

    @Override
    public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

