/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanSet;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterators;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import it.unimi.dsi.fastutil.booleans.BooleanSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class BooleanSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private BooleanSets() {
    }

    public static BooleanSet singleton(boolean bl) {
        return new Singleton(bl);
    }

    public static BooleanSet singleton(Boolean bl) {
        return new Singleton(bl);
    }

    public static BooleanSet synchronize(BooleanSet booleanSet) {
        return new SynchronizedSet(booleanSet);
    }

    public static BooleanSet synchronize(BooleanSet booleanSet, Object object) {
        return new SynchronizedSet(booleanSet, object);
    }

    public static BooleanSet unmodifiable(BooleanSet booleanSet) {
        return new UnmodifiableSet(booleanSet);
    }

    public static class UnmodifiableSet
    extends BooleanCollections.UnmodifiableCollection
    implements BooleanSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(BooleanSet booleanSet) {
            super(booleanSet);
        }

        @Override
        public boolean remove(boolean bl) {
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
        public boolean rem(boolean bl) {
            return super.rem(bl);
        }
    }

    public static class SynchronizedSet
    extends BooleanCollections.SynchronizedCollection
    implements BooleanSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(BooleanSet booleanSet, Object object) {
            super(booleanSet, object);
        }

        protected SynchronizedSet(BooleanSet booleanSet) {
            super(booleanSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(bl);
            }
        }

        @Override
        @Deprecated
        public boolean rem(boolean bl) {
            return super.rem(bl);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractBooleanSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final boolean element;

        protected Singleton(boolean bl) {
            this.element = bl;
        }

        @Override
        public boolean contains(boolean bl) {
            return bl == this.element;
        }

        @Override
        public boolean remove(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BooleanListIterator iterator() {
            return BooleanIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean addAll(Collection<? extends Boolean> collection) {
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
        public boolean addAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(BooleanCollection booleanCollection) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public BooleanIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class EmptySet
    extends BooleanCollections.EmptyCollection
    implements BooleanSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(boolean bl) {
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
        public boolean rem(boolean bl) {
            return super.rem(bl);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

