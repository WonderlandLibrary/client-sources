/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import it.unimi.dsi.fastutil.floats.FloatSets;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public final class FloatSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private FloatSortedSets() {
    }

    public static FloatSortedSet singleton(float f) {
        return new Singleton(f, null);
    }

    public static FloatSortedSet singleton(float f, FloatComparator floatComparator) {
        return new Singleton(f, floatComparator);
    }

    public static FloatSortedSet singleton(Object object) {
        return new Singleton(((Float)object).floatValue(), null);
    }

    public static FloatSortedSet singleton(Object object, FloatComparator floatComparator) {
        return new Singleton(((Float)object).floatValue(), floatComparator);
    }

    public static FloatSortedSet synchronize(FloatSortedSet floatSortedSet) {
        return new SynchronizedSortedSet(floatSortedSet);
    }

    public static FloatSortedSet synchronize(FloatSortedSet floatSortedSet, Object object) {
        return new SynchronizedSortedSet(floatSortedSet, object);
    }

    public static FloatSortedSet unmodifiable(FloatSortedSet floatSortedSet) {
        return new UnmodifiableSortedSet(floatSortedSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedSet
    extends FloatSets.UnmodifiableSet
    implements FloatSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatSortedSet sortedSet;

        protected UnmodifiableSortedSet(FloatSortedSet floatSortedSet) {
            super(floatSortedSet);
            this.sortedSet = floatSortedSet;
        }

        @Override
        public FloatComparator comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public FloatSortedSet subSet(float f, float f2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(f, f2));
        }

        @Override
        public FloatSortedSet headSet(float f) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(f));
        }

        @Override
        public FloatSortedSet tailSet(float f) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(f));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return FloatIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public FloatBidirectionalIterator iterator(float f) {
            return FloatIterators.unmodifiable(this.sortedSet.iterator(f));
        }

        @Override
        public float firstFloat() {
            return this.sortedSet.firstFloat();
        }

        @Override
        public float lastFloat() {
            return this.sortedSet.lastFloat();
        }

        @Override
        @Deprecated
        public Float first() {
            return this.sortedSet.first();
        }

        @Override
        @Deprecated
        public Float last() {
            return this.sortedSet.last();
        }

        @Override
        @Deprecated
        public FloatSortedSet subSet(Float f, Float f2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(f, f2));
        }

        @Override
        @Deprecated
        public FloatSortedSet headSet(Float f) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(f));
        }

        @Override
        @Deprecated
        public FloatSortedSet tailSet(Float f) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(f));
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
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Float)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Float)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Float)object, (Float)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedSortedSet
    extends FloatSets.SynchronizedSet
    implements FloatSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatSortedSet sortedSet;

        protected SynchronizedSortedSet(FloatSortedSet floatSortedSet, Object object) {
            super(floatSortedSet, object);
            this.sortedSet = floatSortedSet;
        }

        protected SynchronizedSortedSet(FloatSortedSet floatSortedSet) {
            super(floatSortedSet);
            this.sortedSet = floatSortedSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public FloatComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.comparator();
            }
        }

        @Override
        public FloatSortedSet subSet(float f, float f2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(f, f2), this.sync);
        }

        @Override
        public FloatSortedSet headSet(float f) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(f), this.sync);
        }

        @Override
        public FloatSortedSet tailSet(float f) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(f), this.sync);
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public FloatBidirectionalIterator iterator(float f) {
            return this.sortedSet.iterator(f);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float firstFloat() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.firstFloat();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float lastFloat() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.lastFloat();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float first() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.first();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float last() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.last();
            }
        }

        @Override
        @Deprecated
        public FloatSortedSet subSet(Float f, Float f2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(f, f2), this.sync);
        }

        @Override
        @Deprecated
        public FloatSortedSet headSet(Float f) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(f), this.sync);
        }

        @Override
        @Deprecated
        public FloatSortedSet tailSet(Float f) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(f), this.sync);
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
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Float)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Float)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Float)object, (Float)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends FloatSets.Singleton
    implements FloatSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final FloatComparator comparator;

        protected Singleton(float f, FloatComparator floatComparator) {
            super(f);
            this.comparator = floatComparator;
        }

        private Singleton(float f) {
            this(f, (FloatComparator)null);
        }

        final int compare(float f, float f2) {
            return this.comparator == null ? Float.compare(f, f2) : this.comparator.compare(f, f2);
        }

        @Override
        public FloatBidirectionalIterator iterator(float f) {
            FloatBidirectionalIterator floatBidirectionalIterator = this.iterator();
            if (this.compare(this.element, f) <= 0) {
                floatBidirectionalIterator.nextFloat();
            }
            return floatBidirectionalIterator;
        }

        @Override
        public FloatComparator comparator() {
            return this.comparator;
        }

        @Override
        public FloatSortedSet subSet(float f, float f2) {
            if (this.compare(f, this.element) <= 0 && this.compare(this.element, f2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public FloatSortedSet headSet(float f) {
            if (this.compare(this.element, f) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public FloatSortedSet tailSet(float f) {
            if (this.compare(f, this.element) <= 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public float firstFloat() {
            return this.element;
        }

        @Override
        public float lastFloat() {
            return this.element;
        }

        @Override
        @Deprecated
        public FloatSortedSet subSet(Float f, Float f2) {
            return this.subSet(f.floatValue(), f2.floatValue());
        }

        @Override
        @Deprecated
        public FloatSortedSet headSet(Float f) {
            return this.headSet(f.floatValue());
        }

        @Override
        @Deprecated
        public FloatSortedSet tailSet(Float f) {
            return this.tailSet(f.floatValue());
        }

        @Override
        @Deprecated
        public Float first() {
            return Float.valueOf(this.element);
        }

        @Override
        @Deprecated
        public Float last() {
            return Float.valueOf(this.element);
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return super.iterator();
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Float)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Float)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Float)object, (Float)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        Singleton(float f, 1 var2_2) {
            this(f);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySet
    extends FloatSets.EmptySet
    implements FloatSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public FloatBidirectionalIterator iterator(float f) {
            return FloatIterators.EMPTY_ITERATOR;
        }

        @Override
        public FloatSortedSet subSet(float f, float f2) {
            return EMPTY_SET;
        }

        @Override
        public FloatSortedSet headSet(float f) {
            return EMPTY_SET;
        }

        @Override
        public FloatSortedSet tailSet(float f) {
            return EMPTY_SET;
        }

        @Override
        public float firstFloat() {
            throw new NoSuchElementException();
        }

        @Override
        public float lastFloat() {
            throw new NoSuchElementException();
        }

        @Override
        public FloatComparator comparator() {
            return null;
        }

        @Override
        @Deprecated
        public FloatSortedSet subSet(Float f, Float f2) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public FloatSortedSet headSet(Float f) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public FloatSortedSet tailSet(Float f) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Float first() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Float last() {
            throw new NoSuchElementException();
        }

        @Override
        public Object clone() {
            return EMPTY_SET;
        }

        private Object readResolve() {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Float)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Float)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Float)object, (Float)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

