/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList$SubList.SubListSpliterator
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Arrays;
import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.stream.Collector;

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
    private static final Collector<Object, ?, ObjectArrayList<Object>> TO_LIST_COLLECTOR;
    static final boolean $assertionsDisabled;

    private static final <K> K[] copyArraySafe(K[] KArray, int n) {
        if (n == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        return java.util.Arrays.copyOf(KArray, n, Object[].class);
    }

    private static final <K> K[] copyArrayFromSafe(ObjectArrayList<K> objectArrayList) {
        return ObjectArrayList.copyArraySafe(objectArrayList.a, objectArrayList.size);
    }

    protected ObjectArrayList(K[] KArray, boolean bl) {
        this.a = KArray;
        this.wrapped = bl;
    }

    private void initArrayFromCapacity(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? ObjectArrays.EMPTY_ARRAY : new Object[n];
    }

    public ObjectArrayList(int n) {
        this.initArrayFromCapacity(n);
        this.wrapped = false;
    }

    public ObjectArrayList() {
        this.a = ObjectArrays.DEFAULT_EMPTY_ARRAY;
        this.wrapped = false;
    }

    public ObjectArrayList(Collection<? extends K> collection) {
        if (collection instanceof ObjectArrayList) {
            this.a = ObjectArrayList.copyArrayFromSafe((ObjectArrayList)collection);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(collection.size());
            if (collection instanceof ObjectList) {
                this.size = collection.size();
                ((ObjectList)collection).getElements(0, this.a, 0, this.size);
            } else {
                this.size = ObjectIterators.unwrap(collection.iterator(), this.a);
            }
        }
        this.wrapped = false;
    }

    public ObjectArrayList(ObjectCollection<? extends K> objectCollection) {
        if (objectCollection instanceof ObjectArrayList) {
            this.a = ObjectArrayList.copyArrayFromSafe((ObjectArrayList)objectCollection);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(objectCollection.size());
            if (objectCollection instanceof ObjectList) {
                this.size = objectCollection.size();
                ((ObjectList)objectCollection).getElements(0, this.a, 0, this.size);
            } else {
                this.size = ObjectIterators.unwrap(objectCollection.iterator(), this.a);
            }
        }
        this.wrapped = false;
    }

    public ObjectArrayList(ObjectList<? extends K> objectList) {
        if (objectList instanceof ObjectArrayList) {
            this.a = ObjectArrayList.copyArrayFromSafe((ObjectArrayList)objectList);
            this.size = this.a.length;
        } else {
            this.initArrayFromCapacity(objectList.size());
            this.size = objectList.size();
            objectList.getElements(0, this.a, 0, this.size);
        }
        this.wrapped = false;
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
        ObjectArrayList<K> objectArrayList = new ObjectArrayList<K>(KArray, true);
        objectArrayList.size = n;
        return objectArrayList;
    }

    public static <K> ObjectArrayList<K> wrap(K[] KArray) {
        return ObjectArrayList.wrap(KArray, KArray.length);
    }

    public static <K> ObjectArrayList<K> of() {
        return new ObjectArrayList<K>();
    }

    @SafeVarargs
    public static <K> ObjectArrayList<K> of(K ... KArray) {
        return ObjectArrayList.wrap(KArray);
    }

    ObjectArrayList<K> combine(ObjectArrayList<? extends K> objectArrayList) {
        this.addAll(objectArrayList);
        return this;
    }

    public static <K> Collector<K, ?, ObjectArrayList<K>> toList() {
        return TO_LIST_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectArrayList<K>> toListWithExpectedSize(int n) {
        if (n <= 10) {
            return ObjectArrayList.toList();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(n, ObjectArrayList::lambda$toListWithExpectedSize$0), ObjectArrayList::add, ObjectArrayList::combine, new Collector.Characteristics[0]);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == ObjectArrays.DEFAULT_EMPTY_ARRAY && n <= 10) {
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
            this.a = ObjectArrays.forceCapacity(this.a, n, this.size);
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
    public ObjectList<K> subList(int n, int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new SubList(this, n, n2);
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
    public void setElements(int n, K[] KArray, int n2, int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(KArray, n2, n3);
        if (n + n3 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size + ")");
        }
        System.arraycopy(KArray, n2, this.a, n, n3);
    }

    @Override
    public void forEach(Consumer<? super K> consumer) {
        for (int i = 0; i < this.size; ++i) {
            consumer.accept(this.a[i]);
        }
    }

    @Override
    public boolean addAll(int n, Collection<? extends K> collection) {
        if (collection instanceof ObjectList) {
            return this.addAll(n, (ObjectList)collection);
        }
        this.ensureIndex(n);
        int n2 = collection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        Iterator<K> iterator2 = collection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = iterator2.next();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, ObjectList<? extends K> objectList) {
        this.ensureIndex(n);
        int n2 = objectList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        objectList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
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
    public Object[] toArray() {
        int n = this.size();
        if (n == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        return java.util.Arrays.copyOf(this.a, n, Object[].class);
    }

    @Override
    public <T> T[] toArray(T[] objectArray) {
        if (objectArray == null) {
            objectArray = new Object[this.size()];
        } else if (objectArray.length < this.size()) {
            objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), this.size());
        }
        System.arraycopy(this.a, 0, objectArray, 0, this.size());
        if (objectArray.length > this.size()) {
            objectArray[this.size()] = null;
        }
        return objectArray;
    }

    @Override
    public ObjectListIterator<K> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<K>(){
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

            @Override
            public void forEachRemaining(Consumer<? super K> consumer) {
                while (this.pos < this.this$0.size) {
                    ++this.pos;
                    this.last = this.last;
                    consumer.accept(this.this$0.a[this.last]);
                }
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = this.this$0.size - this.pos;
                if (n < n2) {
                    this.pos -= n;
                } else {
                    n = n2;
                    this.pos = 0;
                }
                this.last = this.pos;
                return n;
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = this.this$0.size - this.pos;
                if (n < n2) {
                    this.pos += n;
                } else {
                    n = n2;
                    this.pos = this.this$0.size;
                }
                this.last = this.pos - 1;
                return n;
            }
        };
    }

    @Override
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator(this);
    }

    @Override
    public void sort(Comparator<? super K> comparator) {
        if (comparator == null) {
            ObjectArrays.stableSort(this.a, 0, this.size);
        } else {
            ObjectArrays.stableSort(this.a, 0, this.size, comparator);
        }
    }

    @Override
    public void unstableSort(Comparator<? super K> comparator) {
        if (comparator == null) {
            ObjectArrays.unstableSort(this.a, 0, this.size);
        } else {
            ObjectArrays.unstableSort(this.a, 0, this.size, comparator);
        }
    }

    public ObjectArrayList<K> clone() {
        ObjectArrayList<K> objectArrayList = null;
        if (this.getClass() == ObjectArrayList.class) {
            objectArrayList = new ObjectArrayList<K>(ObjectArrayList.copyArraySafe(this.a, this.size), false);
            objectArrayList.size = this.size;
        } else {
            try {
                objectArrayList = (ObjectArrayList<K>)super.clone();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new InternalError(cloneNotSupportedException);
            }
            objectArrayList.a = ObjectArrayList.copyArraySafe(this.a, this.size);
        }
        return objectArrayList;
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
        if (KArray == KArray2 && n == objectArrayList.size()) {
            return false;
        }
        while (n-- != 0) {
            if (Objects.equals(KArray[n], KArray2[n])) continue;
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
        if (object instanceof ObjectArrayList) {
            return this.equals((ObjectArrayList)object);
        }
        if (object instanceof SubList) {
            return ((SubList)object).equals(this);
        }
        return super.equals(object);
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

    @Override
    public int compareTo(List<? extends K> list) {
        if (list instanceof ObjectArrayList) {
            return this.compareTo((ObjectArrayList)list);
        }
        if (list instanceof SubList) {
            return -((SubList)list).compareTo(this);
        }
        return super.compareTo(list);
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

    private static ObjectArrayList lambda$toListWithExpectedSize$0(int n) {
        return n <= 10 ? new ObjectArrayList() : new ObjectArrayList(n);
    }

    static {
        $assertionsDisabled = !ObjectArrayList.class.desiredAssertionStatus();
        TO_LIST_COLLECTOR = Collector.of(ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine, new Collector.Characteristics[0]);
    }

    private class SubList
    extends AbstractObjectList.ObjectRandomAccessSubList<K> {
        private static final long serialVersionUID = -3185226345314976296L;
        final ObjectArrayList this$0;

        protected SubList(ObjectArrayList objectArrayList, int n, int n2) {
            this.this$0 = objectArrayList;
            super(objectArrayList, n, n2);
        }

        private K[] getParentArray() {
            return this.this$0.a;
        }

        @Override
        public K get(int n) {
            this.ensureRestrictedIndex(n);
            return this.this$0.a[n + this.from];
        }

        @Override
        public ObjectListIterator<K> listIterator(int n) {
            return new SubListIterator(this, n);
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return new SubListSpliterator(this);
        }

        boolean contentsEquals(K[] KArray, int n, int n2) {
            if (this.this$0.a == KArray && this.from == n && this.to == n2) {
                return false;
            }
            if (n2 - n != this.size()) {
                return true;
            }
            int n3 = this.from;
            int n4 = n;
            while (n3 < this.to) {
                if (Objects.equals(this.this$0.a[n3++], KArray[n4++])) continue;
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
            if (object instanceof ObjectArrayList) {
                ObjectArrayList objectArrayList = (ObjectArrayList)object;
                return this.contentsEquals(objectArrayList.a, 0, objectArrayList.size());
            }
            if (object instanceof SubList) {
                SubList subList = (SubList)object;
                return this.contentsEquals(subList.getParentArray(), subList.from, subList.to);
            }
            return super.equals(object);
        }

        int contentsCompareTo(K[] KArray, int n, int n2) {
            int n3 = this.from;
            int n4 = n;
            while (n3 < this.to && n3 < n2) {
                Object k = this.this$0.a[n3];
                Object k2 = KArray[n4];
                int n5 = ((Comparable)k).compareTo(k2);
                if (n5 != 0) {
                    return n5;
                }
                ++n3;
                ++n4;
            }
            return n3 < n2 ? -1 : (n3 < this.to ? 1 : 0);
        }

        @Override
        public int compareTo(List<? extends K> list) {
            if (list instanceof ObjectArrayList) {
                ObjectArrayList objectArrayList = (ObjectArrayList)list;
                return this.contentsCompareTo(objectArrayList.a, 0, objectArrayList.size());
            }
            if (list instanceof SubList) {
                SubList subList = (SubList)list;
                return this.contentsCompareTo(subList.getParentArray(), subList.from, subList.to);
            }
            return super.compareTo(list);
        }

        @Override
        public java.util.Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }

        private final class SubListIterator
        extends ObjectIterators.AbstractIndexBasedListIterator<K> {
            final SubList this$1;

            SubListIterator(SubList subList, int n) {
                this.this$1 = subList;
                super(0, n);
            }

            @Override
            protected final K get(int n) {
                return this.this$1.this$0.a[this.this$1.from + n];
            }

            @Override
            protected final void add(int n, K k) {
                this.this$1.add(n, k);
            }

            @Override
            protected final void set(int n, K k) {
                this.this$1.set(n, k);
            }

            @Override
            protected final void remove(int n) {
                this.this$1.remove(n);
            }

            @Override
            protected final int getMaxPos() {
                return this.this$1.to - this.this$1.from;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = this.pos++;
                return this.this$1.this$0.a[this.this$1.from + this.lastReturned];
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.lastReturned = --this.pos;
                return this.this$1.this$0.a[this.this$1.from + this.pos];
            }

            @Override
            public void forEachRemaining(Consumer<? super K> consumer) {
                int n = this.this$1.to - this.this$1.from;
                while (this.pos < n) {
                    ++this.pos;
                    this.lastReturned = this.lastReturned;
                    consumer.accept(this.this$1.this$0.a[this.this$1.from + this.lastReturned]);
                }
            }
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private final class SubListSpliterator
        extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
            final SubList this$1;

            SubListSpliterator(SubList subList) {
                this.this$1 = subList;
                super(subList.from);
            }

            private SubListSpliterator(SubList subList, int n, int n2) {
                this.this$1 = subList;
                super(n, n2);
            }

            @Override
            protected final int getMaxPosFromBackingStore() {
                return this.this$1.to;
            }

            @Override
            protected final K get(int n) {
                return this.this$1.this$0.a[n];
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList$SubList.SubListSpliterator makeForSplit(int n, int n2) {
                return new SubListSpliterator(this.this$1, n, n2);
            }

            @Override
            public boolean tryAdvance(Consumer<? super K> consumer) {
                if (this.pos >= this.getMaxPos()) {
                    return true;
                }
                consumer.accept(this.this$1.this$0.a[this.pos++]);
                return false;
            }

            @Override
            public void forEachRemaining(Consumer<? super K> consumer) {
                int n = this.getMaxPos();
                while (this.pos < n) {
                    consumer.accept(this.this$1.this$0.a[this.pos++]);
                }
            }

            @Override
            protected ObjectSpliterator makeForSplit(int n, int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }

    private final class Spliterator
    implements ObjectSpliterator<K> {
        boolean hasSplit;
        int pos;
        int max;
        static final boolean $assertionsDisabled = !ObjectArrayList.class.desiredAssertionStatus();
        final ObjectArrayList this$0;

        public Spliterator(ObjectArrayList objectArrayList) {
            this(objectArrayList, 0, objectArrayList.size, false);
        }

        private Spliterator(ObjectArrayList objectArrayList, int n, int n2, boolean bl) {
            this.this$0 = objectArrayList;
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
            return 1;
        }

        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.pos >= this.getWorkingMax()) {
                return true;
            }
            consumer.accept(this.this$0.a[this.pos++]);
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            int n = this.getWorkingMax();
            while (this.pos < n) {
                consumer.accept(this.this$0.a[this.pos]);
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
        public ObjectSpliterator<K> trySplit() {
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
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
    }
}

