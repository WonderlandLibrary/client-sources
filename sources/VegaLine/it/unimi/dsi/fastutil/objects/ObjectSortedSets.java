/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ObjectSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ObjectSortedSets() {
    }

    public static <K> ObjectSet<K> emptySet() {
        return EMPTY_SET;
    }

    public static <K> ObjectSortedSet<K> singleton(K element) {
        return new Singleton((Object)element);
    }

    public static <K> ObjectSortedSet<K> singleton(K element, Comparator<? super K> comparator) {
        return new Singleton(element, comparator);
    }

    public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s) {
        return new SynchronizedSortedSet<K>(s);
    }

    public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s, Object sync) {
        return new SynchronizedSortedSet<K>(s, sync);
    }

    public static <K> ObjectSortedSet<K> unmodifiable(ObjectSortedSet<K> s) {
        return new UnmodifiableSortedSet<K>(s);
    }

    public static class UnmodifiableSortedSet<K>
    extends ObjectSets.UnmodifiableSet<K>
    implements ObjectSortedSet<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectSortedSet<K> sortedSet;

        protected UnmodifiableSortedSet(ObjectSortedSet<K> s) {
            super(s);
            this.sortedSet = s;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            return new UnmodifiableSortedSet<K>(this.sortedSet.subSet(from, to));
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            return new UnmodifiableSortedSet<K>(this.sortedSet.headSet(to));
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            return new UnmodifiableSortedSet<K>(this.sortedSet.tailSet(from));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return ObjectIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return ObjectIterators.unmodifiable(this.sortedSet.iterator(from));
        }

        @Override
        @Deprecated
        public ObjectBidirectionalIterator<K> objectIterator() {
            return this.iterator();
        }

        @Override
        public K first() {
            return (K)this.sortedSet.first();
        }

        @Override
        public K last() {
            return (K)this.sortedSet.last();
        }
    }

    public static class SynchronizedSortedSet<K>
    extends ObjectSets.SynchronizedSet<K>
    implements ObjectSortedSet<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectSortedSet<K> sortedSet;

        protected SynchronizedSortedSet(ObjectSortedSet<K> s, Object sync) {
            super(s, sync);
            this.sortedSet = s;
        }

        protected SynchronizedSortedSet(ObjectSortedSet<K> s) {
            super(s);
            this.sortedSet = s;
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
        public ObjectSortedSet<K> subSet(K from, K to) {
            return new SynchronizedSortedSet<K>(this.sortedSet.subSet(from, to), this.sync);
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            return new SynchronizedSortedSet<K>(this.sortedSet.headSet(to), this.sync);
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            return new SynchronizedSortedSet<K>(this.sortedSet.tailSet(from), this.sync);
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return this.sortedSet.iterator(from);
        }

        @Override
        @Deprecated
        public ObjectBidirectionalIterator<K> objectIterator() {
            return this.sortedSet.iterator();
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
    }

    public static class Singleton<K>
    extends ObjectSets.Singleton<K>
    implements ObjectSortedSet<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final Comparator<? super K> comparator;

        private Singleton(K element, Comparator<? super K> comparator) {
            super(element);
            this.comparator = comparator;
        }

        private Singleton(K element) {
            this(element, (Comparator<K>)null);
        }

        final int compare(K k1, K k2) {
            return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
        }

        @Override
        @Deprecated
        public ObjectBidirectionalIterator<K> objectIterator() {
            return this.iterator();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            ObjectBidirectionalIterator i = this.iterator();
            if (this.compare(this.element, from) <= 0) {
                i.next();
            }
            return i;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            if (this.compare(from, this.element) <= 0 && this.compare(this.element, to) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            if (this.compare(this.element, to) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            if (this.compare(from, this.element) <= 0) {
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
    }

    public static class EmptySet<K>
    extends ObjectSets.EmptySet<K>
    implements ObjectSortedSet<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean rem(Object ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public ObjectBidirectionalIterator<K> objectIterator() {
            return this.iterator();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            return EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> headSet(K from) {
            return EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> tailSet(K to) {
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
    }
}

