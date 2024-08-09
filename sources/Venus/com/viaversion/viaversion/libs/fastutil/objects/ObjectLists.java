/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectLists$SynchronizedList
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectLists$SynchronizedRandomAccessList
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectLists$UnmodifiableList
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectLists$UnmodifiableRandomAccessList
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLists;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class ObjectLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private ObjectLists() {
    }

    public static <K> ObjectList<K> shuffle(ObjectList<K> objectList, Random random2) {
        int n = objectList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            Object e = objectList.get(n);
            objectList.set(n, objectList.get(n2));
            objectList.set(n2, e);
        }
        return objectList;
    }

    public static <K> ObjectList<K> emptyList() {
        return EMPTY_LIST;
    }

    public static <K> ObjectList<K> singleton(K k) {
        return new Singleton<K>(k);
    }

    public static <K> ObjectList<K> synchronize(ObjectList<K> objectList) {
        return objectList instanceof RandomAccess ? new SynchronizedRandomAccessList(objectList) : new SynchronizedList(objectList);
    }

    public static <K> ObjectList<K> synchronize(ObjectList<K> objectList, Object object) {
        return objectList instanceof RandomAccess ? new SynchronizedRandomAccessList(objectList, object) : new SynchronizedList(objectList, object);
    }

    public static <K> ObjectList<K> unmodifiable(ObjectList<? extends K> objectList) {
        return objectList instanceof RandomAccess ? new UnmodifiableRandomAccessList(objectList) : new UnmodifiableList(objectList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyList<K>
    extends ObjectCollections.EmptyCollection<K>
    implements ObjectList<K>,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public K get(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K set(int n, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(Object object) {
            return 1;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(UnaryOperator<K> unaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void sort(Comparator<? super K> comparator) {
        }

        @Override
        public void unstableSort(Comparator<? super K> comparator) {
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectListIterator<K> listIterator(int n) {
            if (n == 0) {
                return ObjectIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public ObjectList<K> subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, Object[] objectArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= objectArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, K[] KArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, K[] KArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(K[] KArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int n, K[] KArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int n, K[] KArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends K> list) {
            if (list == this) {
                return 1;
            }
            return list.isEmpty() ? 0 : -1;
        }

        public Object clone() {
            return EMPTY_LIST;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof List && ((List)object).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return EMPTY_LIST;
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
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton<K>
    extends AbstractObjectList<K>
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final K element;

        protected Singleton(K k) {
            this.element = k;
        }

        @Override
        public K get(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public K remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object object) {
            return Objects.equals(object, this.element);
        }

        @Override
        public int indexOf(Object object) {
            return Objects.equals(object, this.element) ? 0 : -1;
        }

        @Override
        public Object[] toArray() {
            return new Object[]{this.element};
        }

        @Override
        public ObjectListIterator<K> listIterator() {
            return ObjectIterators.singleton(this.element);
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return this.listIterator();
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.singleton(this.element);
        }

        @Override
        public ObjectListIterator<K> listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            ListIterator listIterator2 = this.listIterator();
            if (n == 1) {
                listIterator2.next();
            }
            return listIterator2;
        }

        @Override
        public ObjectList<K> subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            if (n != 0 || n2 != 1) {
                return EMPTY_LIST;
            }
            return this;
        }

        @Override
        public void forEach(Consumer<? super K> consumer) {
            consumer.accept(this.element);
        }

        @Override
        public boolean addAll(int n, Collection<? extends K> collection) {
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
        public boolean removeIf(Predicate<? super K> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(UnaryOperator<K> unaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void sort(Comparator<? super K> comparator) {
        }

        @Override
        public void unstableSort(Comparator<? super K> comparator) {
        }

        @Override
        public void getElements(int n, Object[] objectArray, int n2, int n3) {
            if (n2 < 0) {
                throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
            }
            if (n2 + n3 > objectArray.length) {
                throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + objectArray.length + ")");
            }
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
            }
            if (n3 <= 0) {
                return;
            }
            objectArray[n2] = this.element;
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, K[] KArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, K[] KArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(K[] KArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int n, K[] KArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int n, K[] KArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public Spliterator spliterator() {
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
        public ListIterator listIterator() {
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

    static abstract class ImmutableListBase<K>
    extends AbstractObjectList<K>
    implements ObjectList<K> {
        ImmutableListBase() {
        }

        @Override
        @Deprecated
        public final void add(int n, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean addAll(int n, Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final K remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean removeIf(Predicate<? super K> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void replaceAll(UnaryOperator<K> unaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final K set(int n, K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void addElements(int n, K[] KArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void setElements(int n, K[] KArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void sort(Comparator<? super K> comparator) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void unstableSort(Comparator<? super K> comparator) {
            throw new UnsupportedOperationException();
        }
    }
}

