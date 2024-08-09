/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets$SynchronizedSortedSet
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets$UnmodifiableSortedSet
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.Spliterator;

public final class ObjectSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ObjectSortedSets() {
    }

    public static <K> ObjectSet<K> emptySet() {
        return EMPTY_SET;
    }

    public static <K> ObjectSortedSet<K> singleton(K k) {
        return new Singleton<K>(k);
    }

    public static <K> ObjectSortedSet<K> singleton(K k, Comparator<? super K> comparator) {
        return new Singleton<K>(k, comparator);
    }

    public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> objectSortedSet) {
        return new SynchronizedSortedSet(objectSortedSet);
    }

    public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> objectSortedSet, Object object) {
        return new SynchronizedSortedSet(objectSortedSet, object);
    }

    public static <K> ObjectSortedSet<K> unmodifiable(ObjectSortedSet<K> objectSortedSet) {
        return new UnmodifiableSortedSet(objectSortedSet);
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
        public ObjectBidirectionalIterator<K> iterator(K k) {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override
        public ObjectSortedSet<K> subSet(K k, K k2) {
            return EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> headSet(K k) {
            return EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> tailSet(K k) {
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

    public static class Singleton<K>
    extends ObjectSets.Singleton<K>
    implements ObjectSortedSet<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final Comparator<? super K> comparator;

        protected Singleton(K k, Comparator<? super K> comparator) {
            super(k);
            this.comparator = comparator;
        }

        Singleton(K k) {
            this(k, null);
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
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.singleton(this.element, this.comparator);
        }

        @Override
        public ObjectSortedSet<K> subSet(K k, K k2) {
            if (this.compare(k, this.element) <= 0 && this.compare(this.element, k2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> headSet(K k) {
            if (this.compare(this.element, k) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> tailSet(K k) {
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
        public Spliterator spliterator() {
            return this.spliterator();
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
    }
}

