/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class CharSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private CharSets() {
    }

    public static CharSet singleton(char c) {
        return new Singleton(c);
    }

    public static CharSet singleton(Character c) {
        return new Singleton(c.charValue());
    }

    public static CharSet synchronize(CharSet charSet) {
        return new SynchronizedSet(charSet);
    }

    public static CharSet synchronize(CharSet charSet, Object object) {
        return new SynchronizedSet(charSet, object);
    }

    public static CharSet unmodifiable(CharSet charSet) {
        return new UnmodifiableSet(charSet);
    }

    public static class UnmodifiableSet
    extends CharCollections.UnmodifiableCollection
    implements CharSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected UnmodifiableSet(CharSet charSet) {
            super(charSet);
        }

        @Override
        public boolean remove(char c) {
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
        public boolean rem(char c) {
            return super.rem(c);
        }
    }

    public static class SynchronizedSet
    extends CharCollections.SynchronizedCollection
    implements CharSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected SynchronizedSet(CharSet charSet, Object object) {
            super(charSet, object);
        }

        protected SynchronizedSet(CharSet charSet) {
            super(charSet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(c);
            }
        }

        @Override
        @Deprecated
        public boolean rem(char c) {
            return super.rem(c);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractCharSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final char element;

        protected Singleton(char c) {
            this.element = c;
        }

        @Override
        public boolean contains(char c) {
            return c == this.element;
        }

        @Override
        public boolean remove(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CharListIterator iterator() {
            return CharIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean addAll(Collection<? extends Character> collection) {
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
        public boolean addAll(CharCollection charCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(CharCollection charCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(CharCollection charCollection) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public CharIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    public static class EmptySet
    extends CharCollections.EmptyCollection
    implements CharSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(char c) {
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
        public boolean rem(char c) {
            return super.rem(c);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }
}

