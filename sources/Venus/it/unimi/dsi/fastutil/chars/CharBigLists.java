/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharBigList;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharBigArrays;
import it.unimi.dsi.fastutil.chars.CharBigList;
import it.unimi.dsi.fastutil.chars.CharBigListIterator;
import it.unimi.dsi.fastutil.chars.CharBigListIterators;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class CharBigLists {
    public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();

    private CharBigLists() {
    }

    public static CharBigList shuffle(CharBigList charBigList, Random random2) {
        long l = charBigList.size64();
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            char c = charBigList.getChar(l);
            charBigList.set(l, charBigList.getChar(l2));
            charBigList.set(l2, c);
        }
        return charBigList;
    }

    public static CharBigList singleton(char c) {
        return new Singleton(c);
    }

    public static CharBigList singleton(Object object) {
        return new Singleton(((Character)object).charValue());
    }

    public static CharBigList synchronize(CharBigList charBigList) {
        return new SynchronizedBigList(charBigList);
    }

    public static CharBigList synchronize(CharBigList charBigList, Object object) {
        return new SynchronizedBigList(charBigList, object);
    }

    public static CharBigList unmodifiable(CharBigList charBigList) {
        return new UnmodifiableBigList(charBigList);
    }

    public static CharBigList asBigList(CharList charList) {
        return new ListBigList(charList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ListBigList
    extends AbstractCharBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final CharList list;

        protected ListBigList(CharList charList) {
            this.list = charList;
        }

        private int intIndex(long l) {
            if (l >= Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices");
            }
            return (int)l;
        }

        @Override
        public long size64() {
            return this.list.size();
        }

        @Override
        public void size(long l) {
            this.list.size(this.intIndex(l));
        }

        @Override
        public CharBigListIterator iterator() {
            return CharBigListIterators.asBigListIterator(this.list.iterator());
        }

        @Override
        public CharBigListIterator listIterator() {
            return CharBigListIterators.asBigListIterator(this.list.listIterator());
        }

        @Override
        public CharBigListIterator listIterator(long l) {
            return CharBigListIterators.asBigListIterator(this.list.listIterator(this.intIndex(l)));
        }

        @Override
        public boolean addAll(long l, Collection<? extends Character> collection) {
            return this.list.addAll(this.intIndex(l), collection);
        }

        @Override
        public CharBigList subList(long l, long l2) {
            return new ListBigList(this.list.subList(this.intIndex(l), this.intIndex(l2)));
        }

        @Override
        public boolean contains(char c) {
            return this.list.contains(c);
        }

        @Override
        public char[] toCharArray() {
            return this.list.toCharArray();
        }

        @Override
        public void removeElements(long l, long l2) {
            this.list.removeElements(this.intIndex(l), this.intIndex(l2));
        }

        @Override
        @Deprecated
        public char[] toCharArray(char[] cArray) {
            return this.list.toArray(cArray);
        }

        @Override
        public boolean addAll(long l, CharCollection charCollection) {
            return this.list.addAll(this.intIndex(l), charCollection);
        }

        @Override
        public boolean addAll(CharCollection charCollection) {
            return this.list.addAll(charCollection);
        }

        @Override
        public boolean addAll(long l, CharBigList charBigList) {
            return this.list.addAll(this.intIndex(l), charBigList);
        }

        @Override
        public boolean addAll(CharBigList charBigList) {
            return this.list.addAll(charBigList);
        }

        @Override
        public boolean containsAll(CharCollection charCollection) {
            return this.list.containsAll(charCollection);
        }

        @Override
        public boolean removeAll(CharCollection charCollection) {
            return this.list.removeAll(charCollection);
        }

        @Override
        public boolean retainAll(CharCollection charCollection) {
            return this.list.retainAll(charCollection);
        }

        @Override
        public void add(long l, char c) {
            this.list.add(this.intIndex(l), c);
        }

        @Override
        public boolean add(char c) {
            return this.list.add(c);
        }

        @Override
        public char getChar(long l) {
            return this.list.getChar(this.intIndex(l));
        }

        @Override
        public long indexOf(char c) {
            return this.list.indexOf(c);
        }

        @Override
        public long lastIndexOf(char c) {
            return this.list.lastIndexOf(c);
        }

        @Override
        public char removeChar(long l) {
            return this.list.removeChar(this.intIndex(l));
        }

        @Override
        public char set(long l, char c) {
            return this.list.set(this.intIndex(l), c);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.list.toArray(TArray);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.list.containsAll(collection);
        }

        @Override
        public boolean addAll(Collection<? extends Character> collection) {
            return this.list.addAll(collection);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.list.removeAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.list.retainAll(collection);
        }

        @Override
        public void clear() {
            this.list.clear();
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
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
    public static class UnmodifiableBigList
    extends CharCollections.UnmodifiableCollection
    implements CharBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharBigList list;

        protected UnmodifiableBigList(CharBigList charBigList) {
            super(charBigList);
            this.list = charBigList;
        }

        @Override
        public char getChar(long l) {
            return this.list.getChar(l);
        }

        @Override
        public char set(long l, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char removeChar(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(char c) {
            return this.list.indexOf(c);
        }

        @Override
        public long lastIndexOf(char c) {
            return this.list.lastIndexOf(c);
        }

        @Override
        public boolean addAll(long l, Collection<? extends Character> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getElements(long l, char[][] cArray, long l2, long l3) {
            this.list.getElements(l, cArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, char[][] cArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, char[][] cArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void size(long l) {
            this.list.size(l);
        }

        @Override
        public long size64() {
            return this.list.size64();
        }

        @Override
        public CharBigListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public CharBigListIterator listIterator() {
            return CharBigListIterators.unmodifiable(this.list.listIterator());
        }

        @Override
        public CharBigListIterator listIterator(long l) {
            return CharBigListIterators.unmodifiable(this.list.listIterator(l));
        }

        @Override
        public CharBigList subList(long l, long l2) {
            return CharBigLists.unmodifiable(this.list.subList(l, l2));
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.list.equals(object);
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }

        @Override
        public int compareTo(BigList<? extends Character> bigList) {
            return this.list.compareTo(bigList);
        }

        @Override
        public boolean addAll(long l, CharCollection charCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(CharBigList charBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, CharBigList charBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character get(long l) {
            return this.list.get(l);
        }

        @Override
        @Deprecated
        public void add(long l, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character set(long l, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public long indexOf(Object object) {
            return this.list.indexOf(object);
        }

        @Override
        @Deprecated
        public long lastIndexOf(Object object) {
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
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Character)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Character)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedBigList
    extends CharCollections.SynchronizedCollection
    implements CharBigList,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharBigList list;

        protected SynchronizedBigList(CharBigList charBigList, Object object) {
            super(charBigList, object);
            this.list = charBigList;
        }

        protected SynchronizedBigList(CharBigList charBigList) {
            super(charBigList);
            this.list = charBigList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char getChar(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.getChar(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char set(long l, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(long l, char c) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char removeChar(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.removeChar(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long indexOf(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.indexOf(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastIndexOf(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.lastIndexOf(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, Collection<? extends Character> collection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, collection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void getElements(long l, char[][] cArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.getElements(l, cArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void removeElements(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                this.list.removeElements(l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, char[][] cArray, long l2, long l3) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, cArray, l2, l3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void addElements(long l, char[][] cArray) {
            Object object = this.sync;
            synchronized (object) {
                this.list.addElements(l, cArray);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void size(long l) {
            Object object = this.sync;
            synchronized (object) {
                this.list.size(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long size64() {
            Object object = this.sync;
            synchronized (object) {
                return this.list.size64();
            }
        }

        @Override
        public CharBigListIterator iterator() {
            return this.list.listIterator();
        }

        @Override
        public CharBigListIterator listIterator() {
            return this.list.listIterator();
        }

        @Override
        public CharBigListIterator listIterator(long l) {
            return this.list.listIterator(l);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public CharBigList subList(long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return CharBigLists.synchronize(this.list.subList(l, l2), this.sync);
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
                return this.list.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.list.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compareTo(BigList<? extends Character> bigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.compareTo(bigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, CharCollection charCollection) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, charCollection);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(long l, CharBigList charBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(l, charBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(CharBigList charBigList) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.addAll(charBigList);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void add(long l, Character c) {
            Object object = this.sync;
            synchronized (object) {
                this.list.add(l, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character get(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.get(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character set(long l, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.set(l, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character remove(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.list.remove(l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public long indexOf(Object object) {
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
        public long lastIndexOf(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.list.lastIndexOf(object);
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
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Character)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Character)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractCharBigList
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final char element;

        protected Singleton(char c) {
            this.element = c;
        }

        @Override
        public char getChar(long l) {
            if (l == 0L) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char removeChar(long l) {
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
        public CharBigListIterator listIterator() {
            return CharBigListIterators.singleton(this.element);
        }

        @Override
        public CharBigListIterator listIterator(long l) {
            if (l > 1L || l < 0L) {
                throw new IndexOutOfBoundsException();
            }
            CharBigListIterator charBigListIterator = this.listIterator();
            if (l == 1L) {
                charBigListIterator.nextChar();
            }
            return charBigListIterator;
        }

        @Override
        public CharBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            if (l != 0L || l2 != 1L) {
                return EMPTY_BIG_LIST;
            }
            return this;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Character> collection) {
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
        public boolean addAll(CharBigList charBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, CharBigList charBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, CharCollection charCollection) {
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
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long size64() {
            return 1L;
        }

        public Object clone() {
            return this;
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyBigList
    extends CharCollections.EmptyCollection
    implements CharBigList,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigList() {
        }

        @Override
        public char getChar(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char removeChar(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(long l, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char set(long l, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long indexOf(char c) {
            return -1L;
        }

        @Override
        public long lastIndexOf(char c) {
            return -1L;
        }

        @Override
        public boolean addAll(long l, Collection<? extends Character> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(CharCollection charCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(CharBigList charBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, CharCollection charCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(long l, CharBigList charBigList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(long l, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character get(long l) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        @Deprecated
        public Character set(long l, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character remove(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public long indexOf(Object object) {
            return -1L;
        }

        @Override
        @Deprecated
        public long lastIndexOf(Object object) {
            return -1L;
        }

        @Override
        public CharBigListIterator listIterator() {
            return CharBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public CharBigListIterator iterator() {
            return CharBigListIterators.EMPTY_BIG_LIST_ITERATOR;
        }

        @Override
        public CharBigListIterator listIterator(long l) {
            if (l == 0L) {
                return CharBigListIterators.EMPTY_BIG_LIST_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }

        @Override
        public CharBigList subList(long l, long l2) {
            if (l == 0L && l2 == 0L) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(long l, char[][] cArray, long l2, long l3) {
            CharBigArrays.ensureOffsetLength(cArray, l2, l3);
            if (l != 0L) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void removeElements(long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, char[][] cArray, long l2, long l3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(long l, char[][] cArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long size64() {
            return 0L;
        }

        @Override
        public int compareTo(BigList<? extends Character> bigList) {
            if (bigList == this) {
                return 1;
            }
            return bigList.isEmpty() ? 0 : -1;
        }

        public Object clone() {
            return EMPTY_BIG_LIST;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof BigList && ((BigList)object).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return EMPTY_BIG_LIST;
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
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return this.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            this.add(l, (Character)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return this.set(l, (Character)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return this.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return this.get(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((BigList)object);
        }
    }
}

