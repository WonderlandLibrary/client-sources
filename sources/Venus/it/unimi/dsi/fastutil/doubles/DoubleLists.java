/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterators;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public final class DoubleLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private DoubleLists() {
    }

    public static DoubleList shuffle(DoubleList doubleList, Random random2) {
        int n = doubleList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            double d = doubleList.getDouble(n);
            doubleList.set(n, doubleList.getDouble(n2));
            doubleList.set(n2, d);
        }
        return doubleList;
    }

    public static DoubleList singleton(double d) {
        return new Singleton(d);
    }

    public static DoubleList singleton(Object object) {
        return new Singleton((Double)object);
    }

    public static DoubleList synchronize(DoubleList doubleList) {
        return doubleList instanceof RandomAccess ? new SynchronizedRandomAccessList(doubleList) : new SynchronizedList(doubleList);
    }

    public static DoubleList synchronize(DoubleList doubleList, Object object) {
        return doubleList instanceof RandomAccess ? new SynchronizedRandomAccessList(doubleList, object) : new SynchronizedList(doubleList, object);
    }

    public static DoubleList unmodifiable(DoubleList doubleList) {
        return doubleList instanceof RandomAccess ? new UnmodifiableRandomAccessList(doubleList) : new UnmodifiableList(doubleList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableRandomAccessList
    extends UnmodifiableList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected UnmodifiableRandomAccessList(DoubleList doubleList) {
            super(doubleList);
        }

        @Override
        public DoubleList subList(int n, int n2) {
            return new UnmodifiableRandomAccessList(this.list.subList(n, n2));
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableList
    extends DoubleCollections.UnmodifiableCollection
    implements DoubleList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleList list;

        protected UnmodifiableList(DoubleList doubleList) {
            super(doubleList);
            this.list = doubleList;
        }

        @Override
        public double getDouble(int n) {
            return this.list.getDouble(n);
        }

        @Override
        public double set(int n, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double removeDouble(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(double d) {
            return this.list.indexOf(d);
        }

        @Override
        public int lastIndexOf(double d) {
            return this.list.lastIndexOf(d);
        }

        @Override
        public boolean addAll(int n, Collection<? extends Double> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int n, double[] dArray, int n2, int n3) {
            this.list.getElements(n, dArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, double[] dArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, double[] dArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            this.list.size(n);
        }

        @Override
        public DoubleListIterator listIterator() {
            return DoubleIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public DoubleListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public DoubleListIterator listIterator(int n) {
            return DoubleIterators.unmodifiable(this.list.listIterator(n));
        }

        @Override
        public DoubleList subList(int n, int n2) {
            return new UnmodifiableList(this.list.subList(n, n2));
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.collection.equals(object);
        }

        @Override
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override
        public int compareTo(List<? extends Double> list) {
            return this.list.compareTo(list);
        }

        @Override
        public boolean addAll(int n, DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(DoubleList doubleList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, DoubleList doubleList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double get(int n) {
            return this.list.get(n);
        }

        @Override
        @Deprecated
        public void add(int n, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double set(int n, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
            return this.list.lastIndexOf(object);
        }

        @Override
        public DoubleIterator iterator() {
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
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Double)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Double)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedRandomAccessList
    extends SynchronizedList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected SynchronizedRandomAccessList(DoubleList doubleList, Object object) {
            super(doubleList, object);
        }

        protected SynchronizedRandomAccessList(DoubleList doubleList) {
            super(doubleList);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DoubleList subList(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return new SynchronizedRandomAccessList(this.list.subList(n, n2), this.sync);
            }
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedList
    extends DoubleCollections.SynchronizedCollection
    implements DoubleList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleList list;

        protected SynchronizedList(DoubleList doubleList, Object object) {
            super(doubleList, object);
            this.list = doubleList;
        }

        protected SynchronizedList(DoubleList doubleList) {
            super(doubleList);
            this.list = doubleList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double getDouble(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getDouble(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double set(int n, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int n, double d) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double removeDouble(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeDouble(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, Collection<? extends Double> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int n, double[] dArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(n, dArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, double[] dArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, dArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, double[] dArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, dArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void size(int n) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(n);
            }
        }

        @Override
        public DoubleListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public DoubleListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public DoubleListIterator listIterator(int n) {
            return this.list.listIterator(n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DoubleList subList(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return new SynchronizedList(this.list.subList(n, n2), this.sync);
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
                return this.collection.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compareTo(List<? extends Double> list) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(list);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, DoubleCollection doubleCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, doubleCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, DoubleList doubleList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, doubleList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(DoubleList doubleList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(doubleList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double get(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.get(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(int n, Double d) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double set(int n, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double remove(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.remove(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public int indexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.indexOf(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.lastIndexOf(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
            }
        }

        @Override
        public DoubleIterator iterator() {
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
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Double)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Double)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractDoubleList
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final double element;

        protected Singleton(double d) {
            this.element = d;
        }

        @Override
        public double getDouble(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double removeDouble(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(double d) {
            return Double.doubleToLongBits(d) == Double.doubleToLongBits(this.element);
        }

        @Override
        public double[] toDoubleArray() {
            double[] dArray = new double[]{this.element};
            return dArray;
        }

        @Override
        public DoubleListIterator listIterator() {
            return DoubleIterators.singleton(this.element);
        }

        @Override
        public DoubleListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public DoubleListIterator listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            DoubleListIterator doubleListIterator = this.listIterator();
            if (n == 1) {
                doubleListIterator.nextDouble();
            }
            return doubleListIterator;
        }

        @Override
        public DoubleList subList(int n, int n2) {
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
        public boolean addAll(int n, Collection<? extends Double> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Double> collection) {
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
        public boolean addAll(DoubleList doubleList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, DoubleList doubleList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(DoubleCollection doubleCollection) {
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
        public DoubleIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyList
    extends DoubleCollections.EmptyCollection
    implements DoubleList,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public double getDouble(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double removeDouble(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double set(int n, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(double d) {
            return 1;
        }

        @Override
        public int lastIndexOf(double d) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Double> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(DoubleList doubleList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, DoubleList doubleList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int n, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double get(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double set(int n, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int indexOf(Object object) {
            return 1;
        }

        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
            return 1;
        }

        @Override
        public DoubleListIterator listIterator() {
            return DoubleIterators.EMPTY_ITERATOR;
        }

        @Override
        public DoubleListIterator iterator() {
            return DoubleIterators.EMPTY_ITERATOR;
        }

        @Override
        public DoubleListIterator listIterator(int n) {
            if (n == 0) {
                return DoubleIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public DoubleList subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, double[] dArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= dArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, double[] dArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, double[] dArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Double> list) {
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
        public DoubleBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public DoubleIterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Double)object);
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
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Double)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Double)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }
}

