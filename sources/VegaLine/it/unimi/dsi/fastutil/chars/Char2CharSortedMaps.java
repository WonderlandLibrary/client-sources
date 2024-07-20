/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharMaps;
import it.unimi.dsi.fastutil.chars.Char2CharSortedMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.chars.CharSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Char2CharSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Char2CharSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Character, ?>> entryComparator(final CharComparator comparator) {
        return new Comparator<Map.Entry<Character, ?>>(){

            @Override
            public int compare(Map.Entry<Character, ?> x, Map.Entry<Character, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static Char2CharSortedMap singleton(Character key, Character value) {
        return new Singleton(key.charValue(), value.charValue());
    }

    public static Char2CharSortedMap singleton(Character key, Character value, CharComparator comparator) {
        return new Singleton(key.charValue(), value.charValue(), comparator);
    }

    public static Char2CharSortedMap singleton(char key, char value) {
        return new Singleton(key, value);
    }

    public static Char2CharSortedMap singleton(char key, char value, CharComparator comparator) {
        return new Singleton(key, value, comparator);
    }

    public static Char2CharSortedMap synchronize(Char2CharSortedMap m) {
        return new SynchronizedSortedMap(m);
    }

    public static Char2CharSortedMap synchronize(Char2CharSortedMap m, Object sync) {
        return new SynchronizedSortedMap(m, sync);
    }

    public static Char2CharSortedMap unmodifiable(Char2CharSortedMap m) {
        return new UnmodifiableSortedMap(m);
    }

    public static class UnmodifiableSortedMap
    extends Char2CharMaps.UnmodifiableMap
    implements Char2CharSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2CharSortedMap sortedMap;

        protected UnmodifiableSortedMap(Char2CharSortedMap m) {
            super(m);
            this.sortedMap = m;
        }

        @Override
        public CharComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Char2CharMap.Entry> char2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.char2CharEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Character, Character>> entrySet() {
            return this.char2CharEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2CharSortedMap subMap(char from, char to) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
        }

        @Override
        public Char2CharSortedMap headMap(char to) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
        }

        @Override
        public Char2CharSortedMap tailMap(char from) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
        }

        @Override
        public char firstCharKey() {
            return this.sortedMap.firstCharKey();
        }

        @Override
        public char lastCharKey() {
            return this.sortedMap.lastCharKey();
        }

        @Override
        public Character firstKey() {
            return (Character)this.sortedMap.firstKey();
        }

        @Override
        public Character lastKey() {
            return (Character)this.sortedMap.lastKey();
        }

        @Override
        public Char2CharSortedMap subMap(Character from, Character to) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
        }

        @Override
        public Char2CharSortedMap headMap(Character to) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
        }

        @Override
        public Char2CharSortedMap tailMap(Character from) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
        }
    }

    public static class SynchronizedSortedMap
    extends Char2CharMaps.SynchronizedMap
    implements Char2CharSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2CharSortedMap sortedMap;

        protected SynchronizedSortedMap(Char2CharSortedMap m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Char2CharSortedMap m) {
            super(m);
            this.sortedMap = m;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public CharComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Char2CharMap.Entry> char2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.char2CharEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Character, Character>> entrySet() {
            return this.char2CharEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2CharSortedMap subMap(char from, char to) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Char2CharSortedMap headMap(char to) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Char2CharSortedMap tailMap(char from) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char firstCharKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstCharKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char lastCharKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastCharKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Character firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Character)this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Character lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Character)this.sortedMap.lastKey();
            }
        }

        @Override
        public Char2CharSortedMap subMap(Character from, Character to) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Char2CharSortedMap headMap(Character to) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Char2CharSortedMap tailMap(Character from) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
        }
    }

    public static class Singleton
    extends Char2CharMaps.Singleton
    implements Char2CharSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharComparator comparator;

        protected Singleton(char key, char value, CharComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(char key, char value) {
            this(key, value, null);
        }

        final int compare(char k1, char k2) {
            return this.comparator == null ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public CharComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Char2CharMap.Entry> char2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Char2CharMaps.Singleton.SingletonEntry(), Char2CharSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Character, Character>> entrySet() {
            return this.char2CharEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.singleton(this.key, this.comparator);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2CharSortedMap subMap(char from, char to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2CharSortedMap headMap(char to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2CharSortedMap tailMap(char from) {
            if (this.compare(from, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public char firstCharKey() {
            return this.key;
        }

        @Override
        public char lastCharKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Char2CharSortedMap headMap(Character oto) {
            return this.headMap(oto.charValue());
        }

        @Override
        @Deprecated
        public Char2CharSortedMap tailMap(Character ofrom) {
            return this.tailMap(ofrom.charValue());
        }

        @Override
        @Deprecated
        public Char2CharSortedMap subMap(Character ofrom, Character oto) {
            return this.subMap(ofrom.charValue(), oto.charValue());
        }

        @Override
        @Deprecated
        public Character firstKey() {
            return Character.valueOf(this.firstCharKey());
        }

        @Override
        @Deprecated
        public Character lastKey() {
            return Character.valueOf(this.lastCharKey());
        }
    }

    public static class EmptySortedMap
    extends Char2CharMaps.EmptyMap
    implements Char2CharSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public CharComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Char2CharMap.Entry> char2CharEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Character, Character>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public CharSortedSet keySet() {
            return CharSortedSets.EMPTY_SET;
        }

        @Override
        public Char2CharSortedMap subMap(char from, char to) {
            return EMPTY_MAP;
        }

        @Override
        public Char2CharSortedMap headMap(char to) {
            return EMPTY_MAP;
        }

        @Override
        public Char2CharSortedMap tailMap(char from) {
            return EMPTY_MAP;
        }

        @Override
        public char firstCharKey() {
            throw new NoSuchElementException();
        }

        @Override
        public char lastCharKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Char2CharSortedMap headMap(Character oto) {
            return this.headMap(oto.charValue());
        }

        @Override
        @Deprecated
        public Char2CharSortedMap tailMap(Character ofrom) {
            return this.tailMap(ofrom.charValue());
        }

        @Override
        @Deprecated
        public Char2CharSortedMap subMap(Character ofrom, Character oto) {
            return this.subMap(ofrom.charValue(), oto.charValue());
        }

        @Override
        @Deprecated
        public Character firstKey() {
            return Character.valueOf(this.firstCharKey());
        }

        @Override
        @Deprecated
        public Character lastKey() {
            return Character.valueOf(this.lastCharKey());
        }
    }
}

