/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class ByteSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ByteSets() {
    }

    public static ByteSet singleton(byte by) {
        return new Singleton(by);
    }

    public static ByteSet singleton(Byte by) {
        return new Singleton(by);
    }

    public static ByteSet synchronize(ByteSet byteSet) {
        return new SynchronizedSet(byteSet);
    }

    public static ByteSet synchronize(ByteSet byteSet, Object object) {
        return new SynchronizedSet(byteSet, object);
    }

    public static ByteSet unmodifiable(ByteSet byteSet) {
        return new UnmodifiableSet(byteSet);
    }

    public static class UnmodifiableSet
    extends ByteCollections.UnmodifiableCollection
    implements ByteSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(ByteSet byteSet) {
            super(byteSet);
        }

        @Override
        public boolean remove(byte by) {
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
        public boolean rem(byte by) {
            return super.rem(by);
        }
    }

    public static class SynchronizedSet
    extends ByteCollections.SynchronizedCollection
    implements ByteSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(ByteSet byteSet, Object object) {
            super(byteSet, object);
        }

        protected SynchronizedSet(ByteSet byteSet) {
            super(byteSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(by);
            }
        }

        @Override
        @Deprecated
        public boolean rem(byte by) {
            return super.rem(by);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractByteSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final byte element;

        protected Singleton(byte by) {
            this.element = by;
        }

        @Override
        public boolean contains(byte by) {
            return by == this.element;
        }

        @Override
        public boolean remove(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ByteListIterator iterator() {
            return ByteIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean addAll(Collection<? extends Byte> collection) {
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
        public boolean addAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(ByteCollection byteCollection) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public ByteIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class EmptySet
    extends ByteCollections.EmptyCollection
    implements ByteSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(byte by) {
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
        public boolean rem(byte by) {
            return super.rem(by);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

