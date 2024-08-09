/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatList;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public final class FloatLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private FloatLists() {
    }

    public static FloatList shuffle(FloatList floatList, Random random2) {
        int n = floatList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            float f = floatList.getFloat(n);
            floatList.set(n, floatList.getFloat(n2));
            floatList.set(n2, f);
        }
        return floatList;
    }

    public static FloatList singleton(float f) {
        return new Singleton(f);
    }

    public static FloatList singleton(Object object) {
        return new Singleton(((Float)object).floatValue());
    }

    public static FloatList synchronize(FloatList floatList) {
        return floatList instanceof RandomAccess ? new SynchronizedRandomAccessList(floatList) : new SynchronizedList(floatList);
    }

    public static FloatList synchronize(FloatList floatList, Object object) {
        return floatList instanceof RandomAccess ? new SynchronizedRandomAccessList(floatList, object) : new SynchronizedList(floatList, object);
    }

    public static FloatList unmodifiable(FloatList floatList) {
        return floatList instanceof RandomAccess ? new UnmodifiableRandomAccessList(floatList) : new UnmodifiableList(floatList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableRandomAccessList
    extends UnmodifiableList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected UnmodifiableRandomAccessList(FloatList floatList) {
            super(floatList);
        }

        @Override
        public FloatList subList(int n, int n2) {
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
    extends FloatCollections.UnmodifiableCollection
    implements FloatList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatList list;

        protected UnmodifiableList(FloatList floatList) {
            super(floatList);
            this.list = floatList;
        }

        @Override
        public float getFloat(int n) {
            return this.list.getFloat(n);
        }

        @Override
        public float set(int n, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float removeFloat(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(float f) {
            return this.list.indexOf(f);
        }

        @Override
        public int lastIndexOf(float f) {
            return this.list.lastIndexOf(f);
        }

        @Override
        public boolean addAll(int n, Collection<? extends Float> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int n, float[] fArray, int n2, int n3) {
            this.list.getElements(n, fArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, float[] fArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, float[] fArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            this.list.size(n);
        }

        @Override
        public FloatListIterator listIterator() {
            return FloatIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public FloatListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public FloatListIterator listIterator(int n) {
            return FloatIterators.unmodifiable(this.list.listIterator(n));
        }

        @Override
        public FloatList subList(int n, int n2) {
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
        public int compareTo(List<? extends Float> list) {
            return this.list.compareTo(list);
        }

        @Override
        public boolean addAll(int n, FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(FloatList floatList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, FloatList floatList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float get(int n) {
            return this.list.get(n);
        }

        @Override
        @Deprecated
        public void add(int n, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float set(int n, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float remove(int n) {
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
        public FloatIterator iterator() {
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
            this.add(n, (Float)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Float)object);
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

        protected SynchronizedRandomAccessList(FloatList floatList, Object object) {
            super(floatList, object);
        }

        protected SynchronizedRandomAccessList(FloatList floatList) {
            super(floatList);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public FloatList subList(int n, int n2) {
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
    extends FloatCollections.SynchronizedCollection
    implements FloatList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatList list;

        protected SynchronizedList(FloatList floatList, Object object) {
            super(floatList, object);
            this.list = floatList;
        }

        protected SynchronizedList(FloatList floatList) {
            super(floatList);
            this.list = floatList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float getFloat(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getFloat(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float set(int n, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int n, float f) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float removeFloat(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeFloat(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, Collection<? extends Float> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int n, float[] fArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(n, fArray, n2, n3);
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
        public void addElements(int n, float[] fArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, fArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, float[] fArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, fArray);
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
        public FloatListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public FloatListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public FloatListIterator listIterator(int n) {
            return this.list.listIterator(n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public FloatList subList(int n, int n2) {
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
        public int compareTo(List<? extends Float> list) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(list);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, FloatCollection floatCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, floatCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, FloatList floatList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, floatList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(FloatList floatList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(floatList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float get(int n) {
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
        public void add(int n, Float f) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float set(int n, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float remove(int n) {
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
        public FloatIterator iterator() {
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
            this.add(n, (Float)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Float)object);
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
    extends AbstractFloatList
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final float element;

        protected Singleton(float f) {
            this.element = f;
        }

        @Override
        public float getFloat(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float removeFloat(int n) {
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
        public FloatListIterator listIterator() {
            return FloatIterators.singleton(this.element);
        }

        @Override
        public FloatListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public FloatListIterator listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            FloatListIterator floatListIterator = this.listIterator();
            if (n == 1) {
                floatListIterator.nextFloat();
            }
            return floatListIterator;
        }

        @Override
        public FloatList subList(int n, int n2) {
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
        public boolean addAll(int n, Collection<? extends Float> collection) {
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
        public boolean addAll(FloatList floatList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, FloatList floatList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, FloatCollection floatCollection) {
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
        public FloatIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyList
    extends FloatCollections.EmptyCollection
    implements FloatList,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public float getFloat(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float removeFloat(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float set(int n, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(float f) {
            return 1;
        }

        @Override
        public int lastIndexOf(float f) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Float> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(FloatList floatList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, FloatCollection floatCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, FloatList floatList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int n, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float get(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float set(int n, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float remove(int n) {
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
        public FloatListIterator listIterator() {
            return FloatIterators.EMPTY_ITERATOR;
        }

        @Override
        public FloatListIterator iterator() {
            return FloatIterators.EMPTY_ITERATOR;
        }

        @Override
        public FloatListIterator listIterator(int n) {
            if (n == 0) {
                return FloatIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public FloatList subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, float[] fArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= fArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, float[] fArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, float[] fArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Float> list) {
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
            this.add(n, (Float)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Float)object);
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

