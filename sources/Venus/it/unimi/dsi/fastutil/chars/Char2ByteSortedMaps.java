/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ByteMap;
import it.unimi.dsi.fastutil.chars.Char2ByteMap;
import it.unimi.dsi.fastutil.chars.Char2ByteMaps;
import it.unimi.dsi.fastutil.chars.Char2ByteSortedMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.chars.CharSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public final class Char2ByteSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Char2ByteSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Character, ?>> entryComparator(CharComparator charComparator) {
        return (arg_0, arg_1) -> Char2ByteSortedMaps.lambda$entryComparator$0(charComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Char2ByteMap.Entry> fastIterator(Char2ByteSortedMap char2ByteSortedMap) {
        ObjectSet objectSet = char2ByteSortedMap.char2ByteEntrySet();
        return objectSet instanceof Char2ByteSortedMap.FastSortedEntrySet ? ((Char2ByteSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Char2ByteMap.Entry> fastIterable(Char2ByteSortedMap char2ByteSortedMap) {
        ObjectSet objectSet = char2ByteSortedMap.char2ByteEntrySet();
        return objectSet instanceof Char2ByteSortedMap.FastSortedEntrySet ? ((Char2ByteSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Char2ByteSortedMap singleton(Character c, Byte by) {
        return new Singleton(c.charValue(), by);
    }

    public static Char2ByteSortedMap singleton(Character c, Byte by, CharComparator charComparator) {
        return new Singleton(c.charValue(), by, charComparator);
    }

    public static Char2ByteSortedMap singleton(char c, byte by) {
        return new Singleton(c, by);
    }

    public static Char2ByteSortedMap singleton(char c, byte by, CharComparator charComparator) {
        return new Singleton(c, by, charComparator);
    }

    public static Char2ByteSortedMap synchronize(Char2ByteSortedMap char2ByteSortedMap) {
        return new SynchronizedSortedMap(char2ByteSortedMap);
    }

    public static Char2ByteSortedMap synchronize(Char2ByteSortedMap char2ByteSortedMap, Object object) {
        return new SynchronizedSortedMap(char2ByteSortedMap, object);
    }

    public static Char2ByteSortedMap unmodifiable(Char2ByteSortedMap char2ByteSortedMap) {
        return new UnmodifiableSortedMap(char2ByteSortedMap);
    }

    private static int lambda$entryComparator$0(CharComparator charComparator, Map.Entry entry, Map.Entry entry2) {
        return charComparator.compare(((Character)entry.getKey()).charValue(), ((Character)entry2.getKey()).charValue());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Char2ByteMaps.UnmodifiableMap
    implements Char2ByteSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ByteSortedMap sortedMap;

        protected UnmodifiableSortedMap(Char2ByteSortedMap char2ByteSortedMap) {
            super(char2ByteSortedMap);
            this.sortedMap = char2ByteSortedMap;
        }

        @Override
        public CharComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.char2ByteEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2ByteSortedMap subMap(char c, char c2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(c, c2));
        }

        @Override
        public Char2ByteSortedMap headMap(char c) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(c));
        }

        @Override
        public Char2ByteSortedMap tailMap(char c) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(c));
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
        @Deprecated
        public Character firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        @Deprecated
        public Character lastKey() {
            return this.sortedMap.lastKey();
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap subMap(Character c, Character c2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(c, c2));
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap headMap(Character c) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(c));
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap tailMap(Character c) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(c));
        }

        @Override
        public CharSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet char2ByteEntrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public Object lastKey() {
            return this.lastKey();
        }

        @Override
        @Deprecated
        public Object firstKey() {
            return this.firstKey();
        }

        @Override
        @Deprecated
        public SortedMap tailMap(Object object) {
            return this.tailMap((Character)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Character)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Character)object, (Character)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedSortedMap
    extends Char2ByteMaps.SynchronizedMap
    implements Char2ByteSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ByteSortedMap sortedMap;

        protected SynchronizedSortedMap(Char2ByteSortedMap char2ByteSortedMap, Object object) {
            super(char2ByteSortedMap, object);
            this.sortedMap = char2ByteSortedMap;
        }

        protected SynchronizedSortedMap(Char2ByteSortedMap char2ByteSortedMap) {
            super(char2ByteSortedMap);
            this.sortedMap = char2ByteSortedMap;
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
        public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.char2ByteEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2ByteSortedMap subMap(char c, char c2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(c, c2), this.sync);
        }

        @Override
        public Char2ByteSortedMap headMap(char c) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(c), this.sync);
        }

        @Override
        public Char2ByteSortedMap tailMap(char c) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(c), this.sync);
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
        @Deprecated
        public Character firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastKey();
            }
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap subMap(Character c, Character c2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(c, c2), this.sync);
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap headMap(Character c) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(c), this.sync);
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap tailMap(Character c) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(c), this.sync);
        }

        @Override
        public CharSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet char2ByteEntrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public Object lastKey() {
            return this.lastKey();
        }

        @Override
        @Deprecated
        public Object firstKey() {
            return this.firstKey();
        }

        @Override
        @Deprecated
        public SortedMap tailMap(Object object) {
            return this.tailMap((Character)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Character)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Character)object, (Character)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends Char2ByteMaps.Singleton
    implements Char2ByteSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharComparator comparator;

        protected Singleton(char c, byte by, CharComparator charComparator) {
            super(c, by);
            this.comparator = charComparator;
        }

        protected Singleton(char c, byte by) {
            this(c, by, null);
        }

        final int compare(char c, char c2) {
            return this.comparator == null ? Character.compare(c, c2) : this.comparator.compare(c, c2);
        }

        @Override
        public CharComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractChar2ByteMap.BasicEntry(this.key, this.value), Char2ByteSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.singleton(this.key, this.comparator);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2ByteSortedMap subMap(char c, char c2) {
            if (this.compare(c, this.key) <= 0 && this.compare(this.key, c2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2ByteSortedMap headMap(char c) {
            if (this.compare(this.key, c) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2ByteSortedMap tailMap(char c) {
            if (this.compare(c, this.key) <= 0) {
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
        public Char2ByteSortedMap headMap(Character c) {
            return this.headMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap tailMap(Character c) {
            return this.tailMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap subMap(Character c, Character c2) {
            return this.subMap(c.charValue(), c2.charValue());
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

        @Override
        public CharSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet char2ByteEntrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public Object lastKey() {
            return this.lastKey();
        }

        @Override
        @Deprecated
        public Object firstKey() {
            return this.firstKey();
        }

        @Override
        @Deprecated
        public SortedMap tailMap(Object object) {
            return this.tailMap((Character)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Character)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Character)object, (Character)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySortedMap
    extends Char2ByteMaps.EmptyMap
    implements Char2ByteSortedMap,
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
        public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public CharSortedSet keySet() {
            return CharSortedSets.EMPTY_SET;
        }

        @Override
        public Char2ByteSortedMap subMap(char c, char c2) {
            return EMPTY_MAP;
        }

        @Override
        public Char2ByteSortedMap headMap(char c) {
            return EMPTY_MAP;
        }

        @Override
        public Char2ByteSortedMap tailMap(char c) {
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
        public Char2ByteSortedMap headMap(Character c) {
            return this.headMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap tailMap(Character c) {
            return this.tailMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2ByteSortedMap subMap(Character c, Character c2) {
            return this.subMap(c.charValue(), c2.charValue());
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

        @Override
        public CharSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet char2ByteEntrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public Object lastKey() {
            return this.lastKey();
        }

        @Override
        @Deprecated
        public Object firstKey() {
            return this.firstKey();
        }

        @Override
        @Deprecated
        public SortedMap tailMap(Object object) {
            return this.tailMap((Character)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Character)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Character)object, (Character)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

