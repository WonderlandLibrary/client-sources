/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollections;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class ReferenceSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ReferenceSets() {
    }

    public static <K> ReferenceSet<K> emptySet() {
        return EMPTY_SET;
    }

    public static <K> ReferenceSet<K> singleton(K k) {
        return new Singleton<K>(k);
    }

    public static <K> ReferenceSet<K> synchronize(ReferenceSet<K> referenceSet) {
        return new SynchronizedSet<K>(referenceSet);
    }

    public static <K> ReferenceSet<K> synchronize(ReferenceSet<K> referenceSet, Object object) {
        return new SynchronizedSet<K>(referenceSet, object);
    }

    public static <K> ReferenceSet<K> unmodifiable(ReferenceSet<K> referenceSet) {
        return new UnmodifiableSet<K>(referenceSet);
    }

    public static class UnmodifiableSet<K>
    extends ReferenceCollections.UnmodifiableCollection<K>
    implements ReferenceSet<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(ReferenceSet<K> referenceSet) {
            super(referenceSet);
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
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
    }

    public static class SynchronizedSet<K>
    extends ReferenceCollections.SynchronizedCollection<K>
    implements ReferenceSet<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(ReferenceSet<K> referenceSet, Object object) {
            super(referenceSet, object);
        }

        protected SynchronizedSet(ReferenceSet<K> referenceSet) {
            super(referenceSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.collection.remove(object);
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton<K>
    extends AbstractReferenceSet<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K element;

        protected Singleton(K k) {
            this.element = k;
        }

        @Override
        public boolean contains(Object object) {
            return object == this.element;
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return ObjectIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
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

        public Object clone() {
            return this;
        }

        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class EmptySet<K>
    extends ReferenceCollections.EmptyCollection<K>
    implements ReferenceSet<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return EMPTY_SET;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof Set && ((Set)object).isEmpty();
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

