/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatBigList;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatBigArrays;
import it.unimi.dsi.fastutil.floats.FloatBigList;
import it.unimi.dsi.fastutil.floats.FloatBigListIterator;
import it.unimi.dsi.fastutil.floats.FloatBigListIterators;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class FloatBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private FloatBigLists() {
    }

    public static FloatBigList shuffle(FloatBigList floatBigList, Random random2) {
        long l = floatBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            float f = floatBigList.getFloat(l);
            floatBigList.set(l, floatBigList.getFloat(l2));
            floatBigList.set(l2, f);
        }
        return floatBigList;
    }

    public static FloatBigList singleton(float f) {
        return new Singleton(f);
    }

    public static FloatBigList singleton(Object object) {
        return new Singleton(((Float)object).floatValue());
    }

    public static FloatBigList synchronize(FloatBigList floatBigList) {
        return new SynchronizedBigList(floatBigList);
    }

    public static FloatBigList synchronize(FloatBigList floatBigList, Object object) {
        return new SynchronizedBigList(floatBigList, object);
    }

    public static FloatBigList unmodifiable(FloatBigList floatBigList) {
        return new UnmodifiableBigList(floatBigList);
    }

    public static FloatBigList asBigList(FloatList floatList) {
        return new ListBigList(floatList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList
    extends AbstractFloatBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final FloatList list;

        protected ListBigList(FloatList floatList) {
            this.list = floatList;
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
        public FloatBigListIterator iterator() {
            return FloatBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public FloatBigListIterator listIterator() {
            return FloatBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public FloatBigListIterator listIterator(long l) {
            return FloatBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends Float> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public FloatBigList subList(long l, long l2) {
            return new ListBigList(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(float f) {
            return this.list.contains(f);
        }

        @Override
        public float[] toFloatArray() {
            return this.list.toFloatArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        @Deprecated
        public float[] toFloatArray(float[] fArray) {
            return this.list.toArray(fArray);
        }

        @Override
        public boolean addAll(long l, FloatCollection floatCollection) {
            return this.list.addAll(this.intIndex(l), floatCollection);
        }

        @Override
        public boolean addAll(FloatCollection floatCollection) {
            return this.list.addAll(floatCollection);
        }

        @Override
        public boolean addAll(long l, FloatBigList floatBigList) {
            return this.list.addAll(this.intIndex(l), floatBigList);
        }

        @Override
        public boolean addAll(FloatBigList floatBigList) {
            return this.list.addAll(floatBigList);
        }

        @Override
        public boolean containsAll(FloatCollection floatCollection) {
            return this.list.containsAll(floatCollection);
        }

        @Override
        public boolean removeAll(FloatCollection floatCollection) {
            return this.list.removeAll(floatCollection);
        }

        @Override
        public boolean retainAll(FloatCollection floatCollection) {
            return this.list.retainAll(floatCollection);
        }

        @Override
        public void add(long l, float f) {
            this.list.add(this.intIndex(l), f);
        }

        @Override
        public boolean add(float f) {
            return this.list.add(f);
        }

        @Override
        public float getFloat(long l) {
            return this.list.getFloat(this.intIndex(l));
        }

        @Override
        public long indexOf(float f) {
            return this.list.indexOf(f);
        }

        @Override
        public long lastIndexOf(float f) {
            return this.list.lastIndexOf(f);
        }

        @Override
        public float removeFloat(long l) {
            return this.list.removeFloat(this.intIndex(l));
        }

        @Override
        public float set(long l, float f) {
            return this.list.set(this.intIndex(l), f);
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
        public boolean addAll(Collection<? extends Float> collection) {
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
        public FloatIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableBigList
    extends FloatCollections.UnmodifiableCollection
    implements FloatBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatBigList list;

        protected UnmodifiableBigList(FloatBigList floatBigList) {
            super(floatBigList);
            this.list = floatBigList;
        }

        @Override
        public float getFloat(long l) {
            return this.list.getFloat(l);
        }

        @Override
        public float set(long l, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float removeFloat(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(float f) {
            return this.list.indexOf(f);
        }

        @Override
        public long lastIndexOf(float f) {
            return this.list.lastIndexOf(f);
        }

        @Override
        public boolean addAll(long l, Collection<? extends Float> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, float[][] fArray, long l2, long l3) {
            this.list.getElements(l, fArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, float[][] fArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, float[][] fArray) {
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
        public FloatBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public FloatBigListIterator listIterator() {
            return FloatBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public FloatBigListIterator listIterator(long l) {
            return FloatBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public FloatBigList subList(long l, long l2) {
            return FloatBigLists.unmodifiable(this.list.subList(l, l2));
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
        public int compareTo(BigList<? extends Float> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public boolean addAll(long l, FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(FloatBigList floatBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, FloatBigList floatBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float get(long l) {
            return this.list.get(l);
        }

        @Override
        @Deprecated
        public void add(long l, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float set(long l, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float remove(long l) {
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
        public FloatIterator iterator() {
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
            this.add(l, (Float)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Float)object);
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
    extends FloatCollections.SynchronizedCollection
    implements FloatBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatBigList list;

        protected SynchronizedBigList(FloatBigList floatBigList, Object object) {
            super(floatBigList, object);
            this.list = floatBigList;
        }

        protected SynchronizedBigList(FloatBigList floatBigList) {
            super(floatBigList);
            this.list = floatBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float getFloat(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getFloat(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float set(long l, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, float f) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float removeFloat(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeFloat(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends Float> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, float[][] fArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, fArray, l2, l3);
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
        public void addElements(long l, float[][] fArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, fArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, float[][] fArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, fArray);
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
        public FloatBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public FloatBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public FloatBigListIterator listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public FloatBigList subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return FloatBigLists.synchronize(this.list.subList(l, l2), this.sync);
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
        public int compareTo(BigList<? extends Float> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, FloatCollection floatCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, floatCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, FloatBigList floatBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, floatBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(FloatBigList floatBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(floatBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(long l, Float f) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float get(long l) {
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
        public Float set(long l, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float remove(long l) {
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
        public FloatIterator iterator() {
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
            this.add(l, (Float)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Float)object);
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
    extends AbstractFloatBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final float element;

        protected Singleton(float f) {
            this.element = f;
        }

        @Override
        public float getFloat(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float removeFloat(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(float f) {
            return Float.floatToIntBits(f) == Float.floatToIntBits(this.element);
        }

        @Override
        public float[] toFloatArray() {
            float[] fArray = new float[]{this.element};
            return fArray;
        }

        @Override
        public FloatBigListIterator listIterator() {
            return FloatBigListIterators.singleton(this.element);
        }

        @Override
        public FloatBigListIterator listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            FloatBigListIterator floatBigListIterator = this.listIterator();
            if (l == 1L) {
                floatBigListIterator.nextFloat();
            }
            return floatBigListIterator;
        }

        @Override
        public FloatBigList subList(long l, long l2) {
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
        public boolean addAll(long l, Collection<? extends Float> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Float> collection) {
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
        public boolean addAll(FloatBigList floatBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, FloatBigList floatBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(FloatCollection floatCollection) {
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
    extends FloatCollections.EmptyCollection
    implements FloatBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public float getFloat(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float removeFloat(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float set(long l, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(float f) {
            return -1L;
        }

        @Override
        public long lastIndexOf(float f) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Float> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(FloatBigList floatBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, FloatBigList floatBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(long l, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public Float set(long l, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float remove(long l) {
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
        public FloatBigListIterator listIterator() {
            return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public FloatBigListIterator iterator() {
            return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public FloatBigListIterator listIterator(long l) {
            if (l == 0L) {
                return FloatBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public FloatBigList subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, float[][] fArray, long l2, long l3) {
            FloatBigArrays.ensureOffsetLength(fArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, float[][] fArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, float[][] fArray) {
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
        public int compareTo(BigList<? extends Float> bigList) {
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
        public FloatBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public FloatIterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Float)object);
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
            this.add(l, (Float)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Float)object);
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

