/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSets;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public final class ReferenceSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ReferenceSortedSets() {
    }

    public static <K> ReferenceSet<K> emptySet() {
        return EMPTY_SET;
    }

    public static <K> ReferenceSortedSet<K> singleton(K k) {
        return new Singleton((Object)k, null);
    }

    public static <K> ReferenceSortedSet<K> singleton(K k, Comparator<? super K> comparator) {
        return new Singleton<K>(k, comparator);
    }

    public static <K> ReferenceSortedSet<K> synchronize(ReferenceSortedSet<K> referenceSortedSet) {
        return new SynchronizedSortedSet<K>(referenceSortedSet);
    }

    public static <K> ReferenceSortedSet<K> synchronize(ReferenceSortedSet<K> referenceSortedSet, Object object) {
        return new SynchronizedSortedSet<K>(referenceSortedSet, object);
    }

    public static <K> ReferenceSortedSet<K> unmodifiable(ReferenceSortedSet<K> referenceSortedSet) {
        return new UnmodifiableSortedSet<K>(referenceSortedSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedSet<K>
    extends ReferenceSets.UnmodifiableSet<K>
    implements ReferenceSortedSet<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ReferenceSortedSet<K> sortedSet;

        protected UnmodifiableSortedSet(ReferenceSortedSet<K> referenceSortedSet) {
            super(referenceSortedSet);
            this.sortedSet = referenceSortedSet;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public ReferenceSortedSet<K> subSet(K k, K k2) {
            return new UnmodifiableSortedSet<K>(this.sortedSet.subSet((Object)k, (Object)k2));
        }

        @Override
        public ReferenceSortedSet<K> headSet(K k) {
            return new UnmodifiableSortedSet<K>(this.sortedSet.headSet((Object)k));
        }

        @Override
        public ReferenceSortedSet<K> tailSet(K k) {
            return new UnmodifiableSortedSet<K>(this.sortedSet.tailSet((Object)k));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return ObjectIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K k) {
            return ObjectIterators.unmodifiable(this.sortedSet.iterator(k));
        }

        @Override
        public K first() {
            return (K)this.sortedSet.first();
        }

        @Override
        public K last() {
            return (K)this.sortedSet.last();
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
        public SortedSet tailSet(Object object) {
            return this.tailSet(object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet(object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet(object, object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedSortedSet<K>
    extends ReferenceSets.SynchronizedSet<K>
    implements ReferenceSortedSet<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ReferenceSortedSet<K> sortedSet;

        protected SynchronizedSortedSet(ReferenceSortedSet<K> referenceSortedSet, Object object) {
            super(referenceSortedSet, object);
            this.sortedSet = referenceSortedSet;
        }

        protected SynchronizedSortedSet(ReferenceSortedSet<K> referenceSortedSet) {
            super(referenceSortedSet);
            this.sortedSet = referenceSortedSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Comparator<? super K> comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.comparator();
            }
        }

        @Override
        public ReferenceSortedSet<K> subSet(K k, K k2) {
            return new SynchronizedSortedSet<K>(this.sortedSet.subSet((Object)k, (Object)k2), this.sync);
        }

        @Override
        public ReferenceSortedSet<K> headSet(K k) {
            return new SynchronizedSortedSet<K>(this.sortedSet.headSet((Object)k), this.sync);
        }

        @Override
        public ReferenceSortedSet<K> tailSet(K k) {
            return new SynchronizedSortedSet<K>(this.sortedSet.tailSet((Object)k), this.sync);
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K k) {
            return this.sortedSet.iterator(k);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K first() {
            Object object = this.sync;
            synchronized (object) {
                return (K)this.sortedSet.first();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K last() {
            Object object = this.sync;
            synchronized (object) {
                return (K)this.sortedSet.last();
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
        public SortedSet tailSet(Object object) {
            return this.tailSet(object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet(object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet(object, object2);
        }
    }

    public static class Singleton<K>
    extends ReferenceSets.Singleton<K>
    implements ReferenceSortedSet<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final Comparator<? super K> comparator;

        protected Singleton(K k, Comparator<? super K> comparator) {
            super(k);
            this.comparator = comparator;
        }

        private Singleton(K k) {
            this(k, (Comparator<K>)null);
        }

        final int compare(K k, K k2) {
            return this.comparator == null ? ((Comparable)k).compareTo(k2) : this.comparator.compare(k, k2);
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K k) {
            ObjectBidirectionalIterator objectBidirectionalIterator = this.iterator();
            if (this.compare(this.element, k) <= 0) {
                objectBidirectionalIterator.next();
            }
            return objectBidirectionalIterator;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ReferenceSortedSet<K> subSet(K k, K k2) {
            if (this.compare(k, this.element) <= 0 && this.compare(this.element, k2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> headSet(K k) {
            if (this.compare(this.element, k) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> tailSet(K k) {
            if (this.compare(k, this.element) <= 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public K first() {
            return (K)this.element;
        }

        @Override
        public K last() {
            return (K)this.element;
        }

        @Override
        public ObjectBidirectionalIterator iterator() {
            return super.iterator();
        }

        @Override
        public SortedSet tailSet(Object object) {
            return this.tailSet(object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet(object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet(object, object2);
        }

        Singleton(Object object, 1 var2_2) {
            this(object);
        }
    }

    public static class EmptySet<K>
    extends ReferenceSets.EmptySet<K>
    implements ReferenceSortedSet<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K k) {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ReferenceSortedSet<K> subSet(K k, K k2) {
            return EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> headSet(K k) {
            return EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> tailSet(K k) {
            return EMPTY_SET;
        }

        @Override
        public K first() {
            throw new NoSuchElementException();
        }

        @Override
        public K last() {
            throw new NoSuchElementException();
        }

        @Override
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override
        public Object clone() {
            return EMPTY_SET;
        }

        private Object readResolve() {
            return EMPTY_SET;
        }

        @Override
        public SortedSet tailSet(Object object) {
            return this.tailSet(object);
        }

        @Override
        public SortedSet headSet(Object object) {
            return this.headSet(object);
        }

        @Override
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet(object, object2);
        }
    }
}

