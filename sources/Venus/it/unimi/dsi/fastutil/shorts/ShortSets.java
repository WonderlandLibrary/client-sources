/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollections;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterators;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class ShortSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ShortSets() {
    }

    public static ShortSet singleton(short s) {
        return new Singleton(s);
    }

    public static ShortSet singleton(Short s) {
        return new Singleton(s);
    }

    public static ShortSet synchronize(ShortSet shortSet) {
        return new SynchronizedSet(shortSet);
    }

    public static ShortSet synchronize(ShortSet shortSet, Object object) {
        return new SynchronizedSet(shortSet, object);
    }

    public static ShortSet unmodifiable(ShortSet shortSet) {
        return new UnmodifiableSet(shortSet);
    }

    public static class UnmodifiableSet
    extends ShortCollections.UnmodifiableCollection
    implements ShortSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(ShortSet shortSet) {
            super(shortSet);
        }

        @Override
        public boolean remove(short s) {
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
        public boolean rem(short s) {
            return super.rem(s);
        }
    }

    public static class SynchronizedSet
    extends ShortCollections.SynchronizedCollection
    implements ShortSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(ShortSet shortSet, Object object) {
            super(shortSet, object);
        }

        protected SynchronizedSet(ShortSet shortSet) {
            super(shortSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(s);
            }
        }

        @Override
        @Deprecated
        public boolean rem(short s) {
            return super.rem(s);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractShortSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final short element;

        protected Singleton(short s) {
            this.element = s;
        }

        @Override
        public boolean contains(short s) {
            return s == this.element;
        }

        @Override
        public boolean remove(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ShortListIterator iterator() {
            return ShortIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean addAll(Collection<? extends Short> collection) {
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
        public boolean addAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public ShortIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class EmptySet
    extends ShortCollections.EmptyCollection
    implements ShortSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(short s) {
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
        public boolean rem(short s) {
            return super.rem(s);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

