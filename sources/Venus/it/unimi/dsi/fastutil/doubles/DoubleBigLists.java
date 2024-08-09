/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleBigList;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBigList;
import it.unimi.dsi.fastutil.doubles.DoubleBigListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleBigListIterators;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class DoubleBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private DoubleBigLists() {
    }

    public static DoubleBigList shuffle(DoubleBigList doubleBigList, Random random2) {
        long l = doubleBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            double d = doubleBigList.getDouble(l);
            doubleBigList.set(l, doubleBigList.getDouble(l2));
            doubleBigList.set(l2, d);
        }
        return doubleBigList;
    }

    public static DoubleBigList singleton(double d) {
        return new Singleton(d);
    }

    public static DoubleBigList singleton(Object object) {
        return new Singleton((Double)object);
    }

    public static DoubleBigList synchronize(DoubleBigList doubleBigList) {
        return new SynchronizedBigList(doubleBigList);
    }

    public static DoubleBigList synchronize(DoubleBigList doubleBigList, Object object) {
        return new SynchronizedBigList(doubleBigList, object);
    }

    public static DoubleBigList unmodifiable(DoubleBigList doubleBigList) {
        return new UnmodifiableBigList(doubleBigList);
    }

    public static DoubleBigList asBigList(DoubleList doubleList) {
        return new ListBigList(doubleList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList
    extends AbstractDoubleBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final DoubleList list;

        protected ListBigList(DoubleList doubleList) {
            this.list = doubleList;
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
        public DoubleBigListIterator iterator() {
            return DoubleBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public DoubleBigListIterator listIterator() {
            return DoubleBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public DoubleBigListIterator listIterator(long l) {
            return DoubleBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends Double> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public DoubleBigList subList(long l, long l2) {
            return new ListBigList(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(double d) {
            return this.list.contains(d);
        }

        @Override
        public double[] toDoubleArray() {
            return this.list.toDoubleArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        @Deprecated
        public double[] toDoubleArray(double[] dArray) {
            return this.list.toArray(dArray);
        }

        @Override
        public boolean addAll(long l, DoubleCollection doubleCollection) {
            return this.list.addAll(this.intIndex(l), doubleCollection);
        }

        @Override
        public boolean addAll(DoubleCollection doubleCollection) {
            return this.list.addAll(doubleCollection);
        }

        @Override
        public boolean addAll(long l, DoubleBigList doubleBigList) {
            return this.list.addAll(this.intIndex(l), doubleBigList);
        }

        @Override
        public boolean addAll(DoubleBigList doubleBigList) {
            return this.list.addAll(doubleBigList);
        }

        @Override
        public boolean containsAll(DoubleCollection doubleCollection) {
            return this.list.containsAll(doubleCollection);
        }

        @Override
        public boolean removeAll(DoubleCollection doubleCollection) {
            return this.list.removeAll(doubleCollection);
        }

        @Override
        public boolean retainAll(DoubleCollection doubleCollection) {
            return this.list.retainAll(doubleCollection);
        }

        @Override
        public void add(long l, double d) {
            this.list.add(this.intIndex(l), d);
        }

        @Override
        public boolean add(double d) {
            return this.list.add(d);
        }

        @Override
        public double getDouble(long l) {
            return this.list.getDouble(this.intIndex(l));
        }

        @Override
        public long indexOf(double d) {
            return this.list.indexOf(d);
        }

        @Override
        public long lastIndexOf(double d) {
            return this.list.lastIndexOf(d);
        }

        @Override
        public double removeDouble(long l) {
            return this.list.removeDouble(this.intIndex(l));
        }

        @Override
        public double set(long l, double d) {
            return this.list.set(this.intIndex(l), d);
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
        public boolean addAll(Collection<? extends Double> collection) {
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
        public DoubleIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableBigList
    extends DoubleCollections.UnmodifiableCollection
    implements DoubleBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleBigList list;

        protected UnmodifiableBigList(DoubleBigList doubleBigList) {
            super(doubleBigList);
            this.list = doubleBigList;
        }

        @Override
        public double getDouble(long l) {
            return this.list.getDouble(l);
        }

        @Override
        public double set(long l, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double removeDouble(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(double d) {
            return this.list.indexOf(d);
        }

        @Override
        public long lastIndexOf(double d) {
            return this.list.lastIndexOf(d);
        }

        @Override
        public boolean addAll(long l, Collection<? extends Double> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, double[][] dArray, long l2, long l3) {
            this.list.getElements(l, dArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, double[][] dArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, double[][] dArray) {
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
        public DoubleBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public DoubleBigListIterator listIterator() {
            return DoubleBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public DoubleBigListIterator listIterator(long l) {
            return DoubleBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public DoubleBigList subList(long l, long l2) {
            return DoubleBigLists.unmodifiable(this.list.subList(l, l2));
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
        public int compareTo(BigList<? extends Double> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public boolean addAll(long l, DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(DoubleBigList doubleBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, DoubleBigList doubleBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double get(long l) {
            return this.list.get(l);
        }

        @Override
        @Deprecated
        public void add(long l, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double set(long l, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public long indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        @Deprecated
        public long lastIndexOf(Object object) {
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
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Double)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Double)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedBigList
    extends DoubleCollections.SynchronizedCollection
    implements DoubleBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleBigList list;

        protected SynchronizedBigList(DoubleBigList doubleBigList, Object object) {
            super(doubleBigList, object);
            this.list = doubleBigList;
        }

        protected SynchronizedBigList(DoubleBigList doubleBigList) {
            super(doubleBigList);
            this.list = doubleBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double getDouble(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getDouble(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double set(long l, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, double d) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double removeDouble(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeDouble(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends Double> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, double[][] dArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, dArray, l2, l3);
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
        public void addElements(long l, double[][] dArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, dArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, double[][] dArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, dArray);
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
        public DoubleBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public DoubleBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public DoubleBigListIterator listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DoubleBigList subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return DoubleBigLists.synchronize(this.list.subList(l, l2), this.sync);
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
        public int compareTo(BigList<? extends Double> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, DoubleCollection doubleCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, doubleCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, DoubleBigList doubleBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, doubleBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(DoubleBigList doubleBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(doubleBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(long l, Double d) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double set(long l, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double remove(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.remove(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
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
        @Deprecated
        public long lastIndexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.lastIndexOf(object);
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
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Double)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Double)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractDoubleBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final double element;

        protected Singleton(double d) {
            this.element = d;
        }

        @Override
        public double getDouble(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double removeDouble(long l) {
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
        public DoubleBigListIterator listIterator() {
            return DoubleBigListIterators.singleton(this.element);
        }

        @Override
        public DoubleBigListIterator listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            DoubleBigListIterator doubleBigListIterator = this.listIterator();
            if (l == 1L) {
                doubleBigListIterator.nextDouble();
            }
            return doubleBigListIterator;
        }

        @Override
        public DoubleBigList subList(long l, long l2) {
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
        public boolean addAll(long l, Collection<? extends Double> collection) {
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
        public boolean addAll(DoubleBigList doubleBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, DoubleBigList doubleBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, DoubleCollection doubleCollection) {
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
    public static class EmptyBigList
    extends DoubleCollections.EmptyCollection
    implements DoubleBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public double getDouble(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double removeDouble(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double set(long l, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(double d) {
            return -1L;
        }

        @Override
        public long lastIndexOf(double d) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Double> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(DoubleBigList doubleBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, DoubleBigList doubleBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(long l, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public Double set(long l, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public long indexOf(Object object) {
            return -1L;
        }

        @Override
        @Deprecated
        public long lastIndexOf(Object object) {
            return -1L;
        }

        @Override
        public DoubleBigListIterator listIterator() {
            return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public DoubleBigListIterator iterator() {
            return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public DoubleBigListIterator listIterator(long l) {
            if (l == 0L) {
                return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public DoubleBigList subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, double[][] dArray, long l2, long l3) {
            DoubleBigArrays.ensureOffsetLength(dArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, double[][] dArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, double[][] dArray) {
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
        public int compareTo(BigList<? extends Double> bigList) {
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
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Double)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Double)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }
}

