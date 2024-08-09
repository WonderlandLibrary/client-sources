/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterators;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class FloatSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private FloatSets() {
    }

    public static FloatSet singleton(float f) {
        return new Singleton(f);
    }

    public static FloatSet singleton(Float f) {
        return new Singleton(f.floatValue());
    }

    public static FloatSet synchronize(FloatSet floatSet) {
        return new SynchronizedSet(floatSet);
    }

    public static FloatSet synchronize(FloatSet floatSet, Object object) {
        return new SynchronizedSet(floatSet, object);
    }

    public static FloatSet unmodifiable(FloatSet floatSet) {
        return new UnmodifiableSet(floatSet);
    }

    public static class UnmodifiableSet
    extends FloatCollections.UnmodifiableCollection
    implements FloatSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(FloatSet floatSet) {
            super(floatSet);
        }

        @Override
        public boolean remove(float f) {
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
        public boolean rem(float f) {
            return super.rem(f);
        }
    }

    public static class SynchronizedSet
    extends FloatCollections.SynchronizedCollection
    implements FloatSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(FloatSet floatSet, Object object) {
            super(floatSet, object);
        }

        protected SynchronizedSet(FloatSet floatSet) {
            super(floatSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(f);
            }
        }

        @Override
        @Deprecated
        public boolean rem(float f) {
            return super.rem(f);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractFloatSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final float element;

        protected Singleton(float f) {
            this.element = f;
        }

        @Override
        public boolean contains(float f) {
            return Float.floatToIntBits(f) == Float.floatToIntBits(this.element);
        }

        @Override
        public boolean remove(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public FloatListIterator iterator() {
            return FloatIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
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

        public Object clone() {
            return this;
        }

        @Override
        public FloatIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class EmptySet
    extends FloatCollections.EmptyCollection
    implements FloatSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(float f) {
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
        public boolean rem(float f) {
            return super.rem(f);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

