/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharList;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public final class CharLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private CharLists() {
    }

    public static CharList shuffle(CharList charList, Random random2) {
        int n = charList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            char c = charList.getChar(n);
            charList.set(n, charList.getChar(n2));
            charList.set(n2, c);
        }
        return charList;
    }

    public static CharList singleton(char c) {
        return new Singleton(c);
    }

    public static CharList singleton(Object object) {
        return new Singleton(((Character)object).charValue());
    }

    public static CharList synchronize(CharList charList) {
        return charList instanceof RandomAccess ? new SynchronizedRandomAccessList(charList) : new SynchronizedList(charList);
    }

    public static CharList synchronize(CharList charList, Object object) {
        return charList instanceof RandomAccess ? new SynchronizedRandomAccessList(charList, object) : new SynchronizedList(charList, object);
    }

    public static CharList unmodifiable(CharList charList) {
        return charList instanceof RandomAccess ? new UnmodifiableRandomAccessList(charList) : new UnmodifiableList(charList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableRandomAccessList
    extends UnmodifiableList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected UnmodifiableRandomAccessList(CharList charList) {
            super(charList);
        }

        @Override
        public CharList subList(int n, int n2) {
            return new UnmodifiableRandomAccessList(this.list.subList(n, n2));
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableList
    extends CharCollections.UnmodifiableCollection
    implements CharList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharList list;

        protected UnmodifiableList(CharList charList) {
            super(charList);
            this.list = charList;
        }

        @Override
        public char getChar(int n) {
            return this.list.getChar(n);
        }

        @Override
        public char set(int n, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char removeChar(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(char c) {
            return this.list.indexOf(c);
        }

        @Override
        public int lastIndexOf(char c) {
            return this.list.lastIndexOf(c);
        }

        @Override
        public boolean addAll(int n, Collection<? extends Character> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(int n, char[] cArray, int n2, int n3) {
            this.list.getElements(n, cArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, char[] cArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, char[] cArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            this.list.size(n);
        }

        @Override
        public CharListIterator listIterator() {
            return CharIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public CharListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public CharListIterator listIterator(int n) {
            return CharIterators.unmodifiable(this.list.listIterator(n));
        }

        @Override
        public CharList subList(int n, int n2) {
            return new UnmodifiableList(this.list.subList(n, n2));
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
        public int compareTo(List<? extends Character> list) {
            return this.list.compareTo(list);
        }

        @Override
        public boolean addAll(int n, CharCollection charCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(CharList charList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, CharList charList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character get(int n) {
            return this.list.get(n);
        }

        @Override
        @Deprecated
        public void add(int n, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character set(int n, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
            return this.list.lastIndexOf(object);
        }

        @Override
        public CharIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Character)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Character)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedRandomAccessList
    extends SynchronizedList
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;

        protected SynchronizedRandomAccessList(CharList charList, Object object) {
            super(charList, object);
        }

        protected SynchronizedRandomAccessList(CharList charList) {
            super(charList);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public CharList subList(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return new SynchronizedRandomAccessList(this.list.subList(n, n2), this.sync);
            }
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedList
    extends CharCollections.SynchronizedCollection
    implements CharList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharList list;

        protected SynchronizedList(CharList charList, Object object) {
            super(charList, object);
            this.list = charList;
        }

        protected SynchronizedList(CharList charList) {
            super(charList);
            this.list = charList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char getChar(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getChar(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char set(int n, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int n, char c) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char removeChar(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeChar(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, Collection<? extends Character> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(int n, char[] cArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(n, cArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, char[] cArray, int n2, int n3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, cArray, n2, n3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(int n, char[] cArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(n, cArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void size(int n) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(n);
            }
        }

        @Override
        public CharListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public CharListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public CharListIterator listIterator(int n) {
            return this.list.listIterator(n);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public CharList subList(int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return new SynchronizedList(this.list.subList(n, n2), this.sync);
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
        public int compareTo(List<? extends Character> list) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(list);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, CharCollection charCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, charCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int n, CharList charList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(n, charList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(CharList charList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(charList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character get(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.get(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(int n, Character c) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(n, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character set(int n, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(n, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character remove(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.remove(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public int indexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.indexOf(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.lastIndexOf(object);
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
        public CharIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Character)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Character)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractCharList
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final char element;

        protected Singleton(char c) {
            this.element = c;
        }

        @Override
        public char getChar(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char removeChar(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(char c) {
            return c == this.element;
        }

        @Override
        public char[] toCharArray() {
            char[] cArray = new char[]{this.element};
            return cArray;
        }

        @Override
        public CharListIterator listIterator() {
            return CharIterators.singleton(this.element);
        }

        @Override
        public CharListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public CharListIterator listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            CharListIterator charListIterator = this.listIterator();
            if (n == 1) {
                charListIterator.nextChar();
            }
            return charListIterator;
        }

        @Override
        public CharList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            if (n != 0 || n2 != 1) {
                return EMPTY_LIST;
            }
            return this;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Character> collection) {
            throw new UnsupportedOperationException();
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
        public boolean addAll(CharList charList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, CharList charList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, CharCollection charCollection) {
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
        public int size() {
            return 0;
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public CharIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyList
    extends CharCollections.EmptyCollection
    implements CharList,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public char getChar(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char removeChar(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char set(int n, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(char c) {
            return 1;
        }

        @Override
        public int lastIndexOf(char c) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Character> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(CharList charList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, CharCollection charCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, CharList charList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int n, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character get(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character set(int n, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int indexOf(Object object) {
            return 1;
        }

        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
            return 1;
        }

        @Override
        public CharListIterator listIterator() {
            return CharIterators.EMPTY_ITERATOR;
        }

        @Override
        public CharListIterator iterator() {
            return CharIterators.EMPTY_ITERATOR;
        }

        @Override
        public CharListIterator listIterator(int n) {
            if (n == 0) {
                return CharIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public CharList subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, char[] cArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= cArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, char[] cArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, char[] cArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Character> list) {
            if (list == this) {
                return 1;
            }
            return list.isEmpty() ? 0 : -1;
        }

        public Object clone() {
            return EMPTY_LIST;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof List && ((List)object).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return EMPTY_LIST;
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public CharIterator iterator() {
            return this.iterator();
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

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Character)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Character)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }
}

