/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class LongSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private LongSets() {
    }

    public static LongSet singleton(long l) {
        return new Singleton(l);
    }

    public static LongSet singleton(Long l) {
        return new Singleton(l);
    }

    public static LongSet synchronize(LongSet longSet) {
        return new SynchronizedSet(longSet);
    }

    public static LongSet synchronize(LongSet longSet, Object object) {
        return new SynchronizedSet(longSet, object);
    }

    public static LongSet unmodifiable(LongSet longSet) {
        return new UnmodifiableSet(longSet);
    }

    public static class UnmodifiableSet
    extends LongCollections.UnmodifiableCollection
    implements LongSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(LongSet longSet) {
            super(longSet);
        }

        @Override
        public boolean remove(long l) {
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

        @Override
        @Deprecated
        public boolean rem(long l) {
            return super.rem(l);
        }
    }

    public static class SynchronizedSet
    extends LongCollections.SynchronizedCollection
    implements LongSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(LongSet longSet, Object object) {
            super(longSet, object);
        }

        protected SynchronizedSet(LongSet longSet) {
            super(longSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(l);
            }
        }

        @Override
        @Deprecated
        public boolean rem(long l) {
            return super.rem(l);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractLongSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final long element;

        protected Singleton(long l) {
            this.element = l;
        }

        @Override
        public boolean contains(long l) {
            return l == this.element;
        }

        @Override
        public boolean remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public LongListIterator iterator() {
            return LongIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean addAll(Collection<? extends Long> collection) {
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
        public boolean addAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public LongIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class EmptySet
    extends LongCollections.EmptyCollection
    implements LongSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(long l) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return EMPTY_SET;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof Set && ((Set)object).isEmpty();
        }

        @Override
        @Deprecated
        public boolean rem(long l) {
            return super.rem(l);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

