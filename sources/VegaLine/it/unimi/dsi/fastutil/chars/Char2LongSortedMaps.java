/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2LongMap;
import it.unimi.dsi.fastutil.chars.Char2LongMaps;
import it.unimi.dsi.fastutil.chars.Char2LongSortedMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.chars.CharSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Char2LongSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Char2LongSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Character, ?>> entryComparator(final CharComparator comparator) {
        return new Comparator<Map.Entry<Character, ?>>(){

            @Override
            public int compare(Map.Entry<Character, ?> x, Map.Entry<Character, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static Char2LongSortedMap singleton(Character key, Long value) {
        return new Singleton(key.charValue(), value);
    }

    public static Char2LongSortedMap singleton(Character key, Long value, CharComparator comparator) {
        return new Singleton(key.charValue(), value, comparator);
    }

    public static Char2LongSortedMap singleton(char key, long value) {
        return new Singleton(key, value);
    }

    public static Char2LongSortedMap singleton(char key, long value, CharComparator comparator) {
        return new Singleton(key, value, comparator);
    }

    public static Char2LongSortedMap synchronize(Char2LongSortedMap m) {
        return new SynchronizedSortedMap(m);
    }

    public static Char2LongSortedMap synchronize(Char2LongSortedMap m, Object sync) {
        return new SynchronizedSortedMap(m, sync);
    }

    public static Char2LongSortedMap unmodifiable(Char2LongSortedMap m) {
        return new UnmodifiableSortedMap(m);
    }

    public static class UnmodifiableSortedMap
    extends Char2LongMaps.UnmodifiableMap
    implements Char2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2LongSortedMap sortedMap;

        protected UnmodifiableSortedMap(Char2LongSortedMap m) {
            super(m);
            this.sortedMap = m;
        }

        @Override
        public CharComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Char2LongMap.Entry> char2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.char2LongEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Character, Long>> entrySet() {
            return this.char2LongEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2LongSortedMap subMap(char from, char to) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
        }

        @Override
        public Char2LongSortedMap headMap(char to) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
        }

        @Override
        public Char2LongSortedMap tailMap(char from) {
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
        public Char2LongSortedMap subMap(Character from, Character to) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
        }

        @Override
        public Char2LongSortedMap headMap(Character to) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
        }

        @Override
        public Char2LongSortedMap tailMap(Character from) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
        }
    }

    public static class SynchronizedSortedMap
    extends Char2LongMaps.SynchronizedMap
    implements Char2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2LongSortedMap sortedMap;

        protected SynchronizedSortedMap(Char2LongSortedMap m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Char2LongSortedMap m) {
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
        public ObjectSortedSet<Char2LongMap.Entry> char2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.char2LongEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Character, Long>> entrySet() {
            return this.char2LongEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2LongSortedMap subMap(char from, char to) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Char2LongSortedMap headMap(char to) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Char2LongSortedMap tailMap(char from) {
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
        public Char2LongSortedMap subMap(Character from, Character to) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Char2LongSortedMap headMap(Character to) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Char2LongSortedMap tailMap(Character from) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
        }
    }

    public static class Singleton
    extends Char2LongMaps.Singleton
    implements Char2LongSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharComparator comparator;

        protected Singleton(char key, long value, CharComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(char key, long value) {
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
        public ObjectSortedSet<Char2LongMap.Entry> char2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Char2LongMaps.Singleton.SingletonEntry(), Char2LongSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Character, Long>> entrySet() {
            return this.char2LongEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.singleton(this.key, this.comparator);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2LongSortedMap subMap(char from, char to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2LongSortedMap headMap(char to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2LongSortedMap tailMap(char from) {
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
        public Char2LongSortedMap headMap(Character oto) {
            return this.headMap(oto.charValue());
        }

        @Override
        @Deprecated
        public Char2LongSortedMap tailMap(Character ofrom) {
            return this.tailMap(ofrom.charValue());
        }

        @Override
        @Deprecated
        public Char2LongSortedMap subMap(Character ofrom, Character oto) {
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
    extends Char2LongMaps.EmptyMap
    implements Char2LongSortedMap,
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
        public ObjectSortedSet<Char2LongMap.Entry> char2LongEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Character, Long>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public CharSortedSet keySet() {
            return CharSortedSets.EMPTY_SET;
        }

        @Override
        public Char2LongSortedMap subMap(char from, char to) {
            return EMPTY_MAP;
        }

        @Override
        public Char2LongSortedMap headMap(char to) {
            return EMPTY_MAP;
        }

        @Override
        public Char2LongSortedMap tailMap(char from) {
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
        public Char2LongSortedMap headMap(Character oto) {
            return this.headMap(oto.charValue());
        }

        @Override
        @Deprecated
        public Char2LongSortedMap tailMap(Character ofrom) {
            return this.tailMap(ofrom.charValue());
        }

        @Override
        @Deprecated
        public Char2LongSortedMap subMap(Character ofrom, Character oto) {
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

