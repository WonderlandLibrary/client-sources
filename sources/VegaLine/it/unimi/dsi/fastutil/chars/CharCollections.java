/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterable;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.Serializable;
import java.util.Collection;

public class CharCollections {
    private CharCollections() {
    }

    public static CharCollection synchronize(CharCollection c) {
        return new SynchronizedCollection(c);
    }

    public static CharCollection synchronize(CharCollection c, Object sync) {
        return new SynchronizedCollection(c, sync);
    }

    public static CharCollection unmodifiable(CharCollection c) {
        return new UnmodifiableCollection(c);
    }

    public static CharCollection asCollection(CharIterable iterable) {
        if (iterable instanceof CharCollection) {
            return (CharCollection)iterable;
        }
        return new IterableCollection(iterable);
    }

    public static class IterableCollection
    extends AbstractCharCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharIterable iterable;

        protected IterableCollection(CharIterable iterable) {
            if (iterable == null) {
                throw new NullPointerException();
            }
            this.iterable = iterable;
        }

        @Override
        public int size() {
            int c = 0;
            CharIterator iterator = this.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                ++c;
            }
            return c;
        }

        @Override
        public boolean isEmpty() {
            return !this.iterable.iterator().hasNext();
        }

        @Override
        public CharIterator iterator() {
            return this.iterable.iterator();
        }

        @Override
        @Deprecated
        public CharIterator charIterator() {
            return this.iterator();
        }
    }

    public static class UnmodifiableCollection
    implements CharCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharCollection collection;

        protected UnmodifiableCollection(CharCollection c) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
        }

        @Override
        public int size() {
            return this.collection.size();
        }

        @Override
        public boolean isEmpty() {
            return this.collection.isEmpty();
        }

        @Override
        public boolean contains(char o) {
            return this.collection.contains(o);
        }

        @Override
        public CharIterator iterator() {
            return CharIterators.unmodifiable(this.collection.iterator());
        }

        @Override
        @Deprecated
        public CharIterator charIterator() {
            return this.iterator();
        }

        @Override
        public boolean add(char k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Character> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return this.collection.containsAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return this.collection.toString();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return this.collection.toArray(a);
        }

        @Override
        public Object[] toArray() {
            return this.collection.toArray();
        }

        @Override
        public char[] toCharArray() {
            return this.collection.toCharArray();
        }

        @Override
        public char[] toCharArray(char[] a) {
            return this.collection.toCharArray(a);
        }

        @Override
        public char[] toArray(char[] a) {
            return this.collection.toArray(a);
        }

        @Override
        public boolean rem(char k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(CharCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(CharCollection c) {
            return this.collection.containsAll(c);
        }

        @Override
        public boolean removeAll(CharCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(CharCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Character k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object k) {
            return this.collection.contains(k);
        }
    }

    public static class SynchronizedCollection
    implements CharCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(CharCollection c, Object sync) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
            this.sync = sync;
        }

        protected SynchronizedCollection(CharCollection c) {
            if (c == null) {
                throw new NullPointerException();
            }
            this.collection = c;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isEmpty() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.isEmpty();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(char o) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char[] toCharArray() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toCharArray();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object[] toArray() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char[] toCharArray(char[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toCharArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char[] toArray(char[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toCharArray(a);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(CharCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(CharCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(CharCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(CharCollection c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(Character k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public <T> T[] toArray(T[] a) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(a);
            }
        }

        @Override
        public CharIterator iterator() {
            return this.collection.iterator();
        }

        @Override
        @Deprecated
        public CharIterator charIterator() {
            return this.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(char k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(char k) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object ok) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.remove(ok);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends Character> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.sync;
            synchronized (object) {
                this.collection.clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public String toString() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toString();
            }
        }
    }

    public static abstract class EmptyCollection
    extends AbstractCharCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean add(char k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(char k) {
            return false;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public char[] toCharArray(char[] a) {
            return a;
        }

        @Override
        public char[] toCharArray() {
            return CharArrays.EMPTY_ARRAY;
        }

        @Override
        public boolean rem(char k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(CharCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(CharCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(CharCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(CharCollection c) {
            return c.isEmpty();
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return CharIterators.EMPTY_ITERATOR;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void clear() {
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Collection)) {
                return false;
            }
            return ((Collection)o).isEmpty();
        }
    }
}

