/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectBigList;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectBigArrays;
import it.unimi.dsi.fastutil.objects.ObjectBigList;
import it.unimi.dsi.fastutil.objects.ObjectBigListIterator;
import it.unimi.dsi.fastutil.objects.ObjectBigListIterators;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public final class ObjectBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private ObjectBigLists() {
    }

    public static <K> ObjectBigList<K> shuffle(ObjectBigList<K> objectBigList, Random random2) {
        long l = objectBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            Object k = objectBigList.get(l);
            objectBigList.set(l, objectBigList.get(l2));
            objectBigList.set(l2, k);
        }
        return objectBigList;
    }

    public static <K> ObjectBigList<K> emptyList() {
        return EMPTY_BIG_LIST;
    }

    public static <K> ObjectBigList<K> singleton(K k) {
        return new Singleton<K>(k);
    }

    public static <K> ObjectBigList<K> synchronize(ObjectBigList<K> objectBigList) {
        return new SynchronizedBigList<K>(objectBigList);
    }

    public static <K> ObjectBigList<K> synchronize(ObjectBigList<K> objectBigList, Object object) {
        return new SynchronizedBigList<K>(objectBigList, object);
    }

    public static <K> ObjectBigList<K> unmodifiable(ObjectBigList<K> objectBigList) {
        return new UnmodifiableBigList<K>(objectBigList);
    }

    public static <K> ObjectBigList<K> asBigList(ObjectList<K> objectList) {
        return new ListBigList<K>(objectList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList<K>
    extends AbstractObjectBigList<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final ObjectList<K> list;

        protected ListBigList(ObjectList<K> objectList) {
            this.list = objectList;
        }

        private int intIndex(long l) {
            if (l >= Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices");
            }
            return (int)l;
        }

        @Override
        public long size64() {
            return this.list.size();
        }

        @Override
        public void size(long l) {
            this.list.size(this.intIndex(l));
        }

        @Override
        public ObjectBigListIterator<K> iterator() {
            return ObjectBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public ObjectBigListIterator<K> listIterator() {
            return ObjectBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public ObjectBigListIterator<K> listIterator(long l) {
            return ObjectBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends K> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public ObjectBigList<K> subList(long l, long l2) {
            return new ListBigList<K>(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(Object object) {
            return this.list.contains(object);
        }

        @Override
        public Object[] toArray() {
            return this.list.toArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        public void add(long l, K k) {
            this.list.add(this.intIndex(l), k);
        }

        @Override
        public boolean add(K k) {
            return this.list.add(k);
        }

        @Override
        public K get(long l) {
            return (K)this.list.get(this.intIndex(l));
        }

        @Override
        public long indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        public long lastIndexOf(Object object) {
            return this.list.lastIndexOf(object);
        }

        @Override
        public K remove(long l) {
            return (K)this.list.remove(this.intIndex(l));
        }

        @Override
        public K set(long l, K k) {
            return this.list.set(this.intIndex(l), k);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.list.toArray(TArray);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.list.containsAll(collection);
        }

        @Override
        public boolean addAll(Collection<? extends K> collection) {
            return this.list.addAll(collection);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.list.removeAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.list.retainAll(collection);
        }

        @Override
        public void clear() {
            this.list.clear();
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableBigList<K>
    extends ObjectCollections.UnmodifiableCollection<K>
    implements ObjectBigList<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectBigList<K> list;

        protected UnmodifiableBigList(ObjectBigList<K> objectBigList) {
            super(objectBigList);
            this.list = objectBigList;
        }

        @Override
        public K get(long l) {
            return this.list.get(l);
        }

        @Override
        public K set(long l, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        public long lastIndexOf(Object object) {
            return this.list.lastIndexOf(object);
        }

        @Override
        public boolean addAll(long l, Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, Object[][] objectArray, long l2, long l3) {
            this.list.getElements(l, objectArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, K[][] KArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, K[][] KArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void size(long l) {
            this.list.size(l);
        }

        @Override
        public long size64() {
            return this.list.size64();
        }

        @Override
        public ObjectBigListIterator<K> iterator() {
            return this.listIterator();
        }

        @Override
        public ObjectBigListIterator<K> listIterator() {
            return ObjectBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public ObjectBigListIterator<K> listIterator(long l) {
            return ObjectBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public ObjectBigList<K> subList(long l, long l2) {
            return ObjectBigLists.unmodifiable(this.list.subList(l, l2));
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.list.equals(object);
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public int compareTo(BigList<? extends K> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedBigList<K>
    extends ObjectCollections.SynchronizedCollection<K>
    implements ObjectBigList<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectBigList<K> list;

        protected SynchronizedBigList(ObjectBigList<K> objectBigList, Object object) {
            super(objectBigList, object);
            this.list = objectBigList;
        }

        protected SynchronizedBigList(ObjectBigList<K> objectBigList) {
            super(objectBigList);
            this.list = objectBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K set(long l, K k) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, K k) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K remove(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.remove(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.indexOf(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.lastIndexOf(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends K> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, Object[][] objectArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, objectArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, K[][] KArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, KArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, K[][] KArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, KArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void size(long l) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long size64() {
            Object object = this.sync;
            synchronized (object) {
                return this.list.size64();
            }
        }

        @Override
        public ObjectBigListIterator<K> iterator() {
            return this.list.listIterator();
        }

        @Override
        public ObjectBigListIterator<K> listIterator() {
            return this.list.listIterator();
        }

        @Override
        public ObjectBigListIterator<K> listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectBigList<K> subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return ObjectBigLists.synchronize(this.list.subList(l, l2), this.sync);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.list.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compareTo(BigList<? extends K> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }

    public static class Singleton<K>
    extends AbstractObjectBigList<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final K element;

        protected Singleton(K k) {
            this.element = k;
        }

        @Override
        public K get(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object object) {
            return Objects.equals(object, this.element);
        }

        @Override
        public Object[] toArray() {
            Object[] objectArray = new Object[]{this.element};
            return objectArray;
        }

        @Override
        public ObjectBigListIterator<K> listIterator() {
            return ObjectBigListIterators.singleton(this.element);
        }

        @Override
        public ObjectBigListIterator<K> listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            BigListIterator bigListIterator = this.listIterator();
            if (l == 1L) {
                bigListIterator.next();
            }
            return bigListIterator;
        }

        @Override
        public ObjectBigList<K> subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            if (l != 0L || l2 != 1L) {
                return EMPTY_BIG_LIST;
            }
            return this;
        }

        @Override
        public boolean addAll(long l, Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long size64() {
            return 1L;
        }

        public Object clone() {
            return this;
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyBigList<K>
    extends ObjectCollections.EmptyCollection<K>
    implements ObjectBigList<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public K get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K set(long l, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(Object object) {
            return -1L;
        }

        @Override
        public long lastIndexOf(Object object) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectBigListIterator<K> listIterator() {
            return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public ObjectBigListIterator<K> iterator() {
            return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public ObjectBigListIterator<K> listIterator(long l) {
            if (l == 0L) {
                return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public ObjectBigList<K> subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, Object[][] objectArray, long l2, long l3) {
            ObjectBigArrays.ensureOffsetLength(objectArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, K[][] KArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, K[][] KArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long size64() {
            return 0L;
        }

        @Override
        public int compareTo(BigList<? extends K> bigList) {
            if (bigList == this) {
                return 1;
            }
            return bigList.isEmpty() ? 0 : -1;
        }

        public Object clone() {
            return EMPTY_BIG_LIST;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof BigList && ((BigList)object).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return EMPTY_BIG_LIST;
        }

        @Override
        public ObjectBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }
}

