/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterable;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.IntPredicate;

public final class CharCollections {
    private CharCollections() {
    }

    public static CharCollection synchronize(CharCollection charCollection) {
        return new SynchronizedCollection(charCollection);
    }

    public static CharCollection synchronize(CharCollection charCollection, Object object) {
        return new SynchronizedCollection(charCollection, object);
    }

    public static CharCollection unmodifiable(CharCollection charCollection) {
        return new UnmodifiableCollection(charCollection);
    }

    public static CharCollection asCollection(CharIterable charIterable) {
        if (charIterable instanceof CharCollection) {
            return (CharCollection)charIterable;
        }
        return new IterableCollection(charIterable);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IterableCollection
    extends AbstractCharCollection
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharIterable iterable;

        protected IterableCollection(CharIterable charIterable) {
            if (charIterable == null) {
                throw new NullPointerException();
            }
            this.iterable = charIterable;
        }

        @Override
        public int size() {
            int n = 0;
            CharIterator charIterator = this.iterator();
            while (charIterator.hasNext()) {
                charIterator.nextChar();
                ++n;
            }
            return n;
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
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableCollection
    implements CharCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharCollection collection;

        protected UnmodifiableCollection(CharCollection charCollection) {
            if (charCollection == null) {
                throw new NullPointerException();
            }
            this.collection = charCollection;
        }

        @Override
        public boolean add(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean rem(char c) {
            throw new UnsupportedOperationException();
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
        public boolean contains(char c) {
            return this.collection.contains(c);
        }

        @Override
        public CharIterator iterator() {
            return CharIterators.unmodifiable(this.collection.iterator());
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.collection.toArray(TArray);
        }

        @Override
        public Object[] toArray() {
            return this.collection.toArray();
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.collection.containsAll(collection);
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
        @Deprecated
        public boolean add(Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean contains(Object object) {
            return this.collection.contains(object);
        }

        @Override
        @Deprecated
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char[] toCharArray() {
            return this.collection.toCharArray();
        }

        @Override
        @Deprecated
        public char[] toCharArray(char[] cArray) {
            return this.toArray(cArray);
        }

        @Override
        public char[] toArray(char[] cArray) {
            return this.collection.toArray(cArray);
        }

        @Override
        public boolean containsAll(CharCollection charCollection) {
            return this.collection.containsAll(charCollection);
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

        public String toString() {
            return this.collection.toString();
        }

        @Override
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.collection.equals(object);
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Character)object);
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedCollection
    implements CharCollection,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(CharCollection charCollection, Object object) {
            if (charCollection == null) {
                throw new NullPointerException();
            }
            this.collection = charCollection;
            this.sync = object;
        }

        protected SynchronizedCollection(CharCollection charCollection) {
            if (charCollection == null) {
                throw new NullPointerException();
            }
            this.collection = charCollection;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.contains(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean rem(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.rem(c);
            }
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

        @Override
        @Deprecated
        public char[] toCharArray(char[] cArray) {
            return this.toArray(cArray);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char[] toArray(char[] cArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(cArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(CharCollection charCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(charCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(CharCollection charCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(charCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(CharCollection charCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(charCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeIf(IntPredicate intPredicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeIf(intPredicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(CharCollection charCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(charCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean add(Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.add(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean contains(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.collection.contains(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean remove(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.collection.remove(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public <T> T[] toArray(T[] TArray) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.toArray(TArray);
            }
        }

        @Override
        public CharIterator iterator() {
            return this.collection.iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends Character> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.addAll(collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(Collection<?> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.containsAll(collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(Collection<?> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.removeAll(collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(Collection<?> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.retainAll(collection);
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.collection.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            Object object2 = this.sync;
            synchronized (object2) {
                return this.collection.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
            }
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Character)object);
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class EmptyCollection
    extends AbstractCharCollection {
        protected EmptyCollection() {
        }

        @Override
        public boolean contains(char c) {
            return true;
        }

        @Override
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return CharIterators.EMPTY_ITERATOR;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public void clear() {
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof Collection)) {
                return true;
            }
            return ((Collection)object).isEmpty();
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

        @Override
        public CharIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

