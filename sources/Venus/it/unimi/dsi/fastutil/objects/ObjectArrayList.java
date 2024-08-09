/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.objects.AbstractObjectList;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;

public class ObjectArrayList<K>
extends AbstractObjectList<K>
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353131L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected final boolean wrapped;
    protected transient K[] a;
    protected int size;
    static final boolean $assertionsDisabled = !ObjectArrayList.class.desiredAssertionStatus();

    protected ObjectArrayList(K[] KArray, boolean bl) {
        this.a = KArray;
        this.wrapped = true;
    }

    public ObjectArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? ObjectArrays.EMPTY_ARRAY : new Object[n];
        this.wrapped = false;
    }

    public ObjectArrayList() {
        this.a = ObjectArrays.DEFAULT_EMPTY_ARRAY;
        this.wrapped = false;
    }

    public ObjectArrayList(Collection<? extends K> collection) {
        this(collection.size());
        this.size = ObjectIterators.unwrap(collection.iterator(), this.a);
    }

    public ObjectArrayList(ObjectCollection<? extends K> objectCollection) {
        this(objectCollection.size());
        this.size = ObjectIterators.unwrap(objectCollection.iterator(), this.a);
    }

    public ObjectArrayList(ObjectList<? extends K> objectList) {
        this(objectList.size());
        this.size = objectList.size();
        objectList.getElements(0, this.a, 0, this.size);
    }

    public ObjectArrayList(K[] KArray) {
        this(KArray, 0, KArray.length);
    }

    public ObjectArrayList(K[] KArray, int n, int n2) {
        this(n2);
        System.arraycopy(KArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public ObjectArrayList(Iterator<? extends K> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add(iterator2.next());
        }
    }

    public ObjectArrayList(ObjectIterator<? extends K> objectIterator) {
        this();
        while (objectIterator.hasNext()) {
            this.add((K)objectIterator.next());
        }
    }

    public K[] elements() {
        return this.a;
    }

    public static <K> ObjectArrayList<K> wrap(K[] KArray, int n) {
        if (n > KArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + KArray.length + ")");
        }
        ObjectArrayList<K> objectArrayList = new ObjectArrayList<K>(KArray, false);
        objectArrayList.size = n;
        return objectArrayList;
    }

    public static <K> ObjectArrayList<K> wrap(K[] KArray) {
        return ObjectArrayList.wrap(KArray, KArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        if (this.wrapped) {
            this.a = ObjectArrays.ensureCapacity(this.a, n, this.size);
        } else if (n > this.a.length) {
            Object[] objectArray = new Object[n];
            System.arraycopy(this.a, 0, objectArray, 0, this.size);
            this.a = objectArray;
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        if (this.wrapped) {
            this.a = ObjectArrays.forceCapacity(this.a, n, this.size);
        } else {
            Object[] objectArray = new Object[n];
            System.arraycopy(this.a, 0, objectArray, 0, this.size);
            this.a = objectArray;
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, K k) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = k;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(K k) {
        this.grow(this.size + 1);
        this.a[this.size++] = k;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public K get(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i < this.size; ++i) {
            if (!Objects.equals(object, this.a[i])) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(object, this.a[n])) continue;
            return n;
        }
        return 1;
    }

    @Override
    public K remove(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        K k = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        this.a[this.size] = null;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return k;
    }

    @Override
    public boolean remove(Object object) {
        int n = this.indexOf(object);
        if (n == -1) {
            return true;
        }
        this.remove(n);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public K set(int n, K k) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        K k2 = this.a[n];
        this.a[n] = k;
        return k2;
    }

    @Override
    public void clear() {
        java.util.Arrays.fill(this.a, 0, this.size, null);
        this.size = 0;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void size(int n) {
        if (n > this.a.length) {
            this.ensureCapacity(n);
        }
        if (n > this.size) {
            java.util.Arrays.fill(this.a, this.size, n, null);
        } else {
            java.util.Arrays.fill(this.a, n, this.size, null);
        }
        this.size = n;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void trim() {
        this.trim(0);
    }

    public void trim(int n) {
        if (n >= this.a.length || this.size == this.a.length) {
            return;
        }
        Object[] objectArray = new Object[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, objectArray, 0, this.size);
        this.a = objectArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, Object[] objectArray, int n2, int n3) {
        ObjectArrays.ensureOffsetLength(objectArray, n2, n3);
        System.arraycopy(this.a, n, objectArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
        int n3 = n2 - n;
        while (n3-- != 0) {
            this.a[this.size + n3] = null;
        }
    }

    @Override
    public void addElements(int n, K[] KArray, int n2, int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(KArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(KArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        Object[] objectArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; n += 1) {
            if (collection.contains(objectArray[n])) continue;
            objectArray[n2++] = objectArray[n];
        }
        java.util.Arrays.fill(objectArray, n2, this.size, null);
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public ObjectListIterator<K> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<K>(this, n){
            int pos;
            int last;
            final int val$index;
            final ObjectArrayList this$0;
            {
                this.this$0 = objectArrayList;
                this.val$index = n;
                this.pos = this.val$index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.a[this.pos];
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
            public void add(K k) {
                this.this$0.add(this.pos++, k);
                this.last = -1;
            }

            @Override
            public void set(K k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public ObjectArrayList<K> clone() {
        ObjectArrayList<K> objectArrayList = new ObjectArrayList<K>(this.size);
        System.arraycopy(this.a, 0, objectArrayList.a, 0, this.size);
        objectArrayList.size = this.size;
        return objectArrayList;
    }

    private boolean valEquals(K k, K k2) {
        return k == null ? k2 == null : k.equals(k2);
    }

    public boolean equals(ObjectArrayList<K> objectArrayList) {
        if (objectArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != objectArrayList.size()) {
            return true;
        }
        K[] KArray = this.a;
        K[] KArray2 = objectArrayList.a;
        while (n-- != 0) {
            if (this.valEquals(KArray[n], KArray2[n])) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(ObjectArrayList<? extends K> objectArrayList) {
        int n;
        int n2 = this.size();
        int n3 = objectArrayList.size();
        K[] KArray = this.a;
        K[] KArray2 = objectArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            K k = KArray[n];
            K k2 = KArray2[n];
            int n4 = ((Comparable)k).compareTo(k2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
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
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

