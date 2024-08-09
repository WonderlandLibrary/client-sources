/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ReferenceMap;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMap;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMaps;
import it.unimi.dsi.fastutil.chars.Char2ReferenceSortedMap;
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

public final class Char2ReferenceSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Char2ReferenceSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Character, ?>> entryComparator(CharComparator charComparator) {
        return (arg_0, arg_1) -> Char2ReferenceSortedMaps.lambda$entryComparator$0(charComparator, arg_0, arg_1);
    }

    public static <V> ObjectBidirectionalIterator<Char2ReferenceMap.Entry<V>> fastIterator(Char2ReferenceSortedMap<V> char2ReferenceSortedMap) {
        ObjectSet objectSet = char2ReferenceSortedMap.char2ReferenceEntrySet();
        return objectSet instanceof Char2ReferenceSortedMap.FastSortedEntrySet ? ((Char2ReferenceSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> ObjectBidirectionalIterable<Char2ReferenceMap.Entry<V>> fastIterable(Char2ReferenceSortedMap<V> char2ReferenceSortedMap) {
        ObjectSet objectSet = char2ReferenceSortedMap.char2ReferenceEntrySet();
        return objectSet instanceof Char2ReferenceSortedMap.FastSortedEntrySet ? ((Char2ReferenceSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <V> Char2ReferenceSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Char2ReferenceSortedMap<V> singleton(Character c, V v) {
        return new Singleton<V>(c.charValue(), v);
    }

    public static <V> Char2ReferenceSortedMap<V> singleton(Character c, V v, CharComparator charComparator) {
        return new Singleton<V>(c.charValue(), v, charComparator);
    }

    public static <V> Char2ReferenceSortedMap<V> singleton(char c, V v) {
        return new Singleton<V>(c, v);
    }

    public static <V> Char2ReferenceSortedMap<V> singleton(char c, V v, CharComparator charComparator) {
        return new Singleton<V>(c, v, charComparator);
    }

    public static <V> Char2ReferenceSortedMap<V> synchronize(Char2ReferenceSortedMap<V> char2ReferenceSortedMap) {
        return new SynchronizedSortedMap<V>(char2ReferenceSortedMap);
    }

    public static <V> Char2ReferenceSortedMap<V> synchronize(Char2ReferenceSortedMap<V> char2ReferenceSortedMap, Object object) {
        return new SynchronizedSortedMap<V>(char2ReferenceSortedMap, object);
    }

    public static <V> Char2ReferenceSortedMap<V> unmodifiable(Char2ReferenceSortedMap<V> char2ReferenceSortedMap) {
        return new UnmodifiableSortedMap<V>(char2ReferenceSortedMap);
    }

    private static int lambda$entryComparator$0(CharComparator charComparator, Map.Entry entry, Map.Entry entry2) {
        return charComparator.compare(((Character)entry.getKey()).charValue(), ((Character)entry2.getKey()).charValue());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<V>
    extends Char2ReferenceMaps.UnmodifiableMap<V>
    implements Char2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ReferenceSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Char2ReferenceSortedMap<V> char2ReferenceSortedMap) {
            super(char2ReferenceSortedMap);
            this.sortedMap = char2ReferenceSortedMap;
        }

        @Override
        public CharComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.char2ReferenceEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
            return this.char2ReferenceEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2ReferenceSortedMap<V> subMap(char c, char c2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(c, c2));
        }

        @Override
        public Char2ReferenceSortedMap<V> headMap(char c) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(c));
        }

        @Override
        public Char2ReferenceSortedMap<V> tailMap(char c) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(c));
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
        public Char2ReferenceSortedMap<V> subMap(Character c, Character c2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(c, c2));
        }

        @Override
        @Deprecated
        public Char2ReferenceSortedMap<V> headMap(Character c) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(c));
        }

        @Override
        @Deprecated
        public Char2ReferenceSortedMap<V> tailMap(Character c) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(c));
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
        public ObjectSet char2ReferenceEntrySet() {
            return this.char2ReferenceEntrySet();
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
    public static class SynchronizedSortedMap<V>
    extends Char2ReferenceMaps.SynchronizedMap<V>
    implements Char2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ReferenceSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Char2ReferenceSortedMap<V> char2ReferenceSortedMap, Object object) {
            super(char2ReferenceSortedMap, object);
            this.sortedMap = char2ReferenceSortedMap;
        }

        protected SynchronizedSortedMap(Char2ReferenceSortedMap<V> char2ReferenceSortedMap) {
            super(char2ReferenceSortedMap);
            this.sortedMap = char2ReferenceSortedMap;
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
        public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.char2ReferenceEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
            return this.char2ReferenceEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2ReferenceSortedMap<V> subMap(char c, char c2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(c, c2), this.sync);
        }

        @Override
        public Char2ReferenceSortedMap<V> headMap(char c) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(c), this.sync);
        }

        @Override
        public Char2ReferenceSortedMap<V> tailMap(char c) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(c), this.sync);
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
        public Char2ReferenceSortedMap<V> subMap(Character c, Character c2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(c, c2), this.sync);
        }

        @Override
        @Deprecated
        public Char2ReferenceSortedMap<V> headMap(Character c) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(c), this.sync);
        }

        @Override
        @Deprecated
        public Char2ReferenceSortedMap<V> tailMap(Character c) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(c), this.sync);
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
        public ObjectSet char2ReferenceEntrySet() {
            return this.char2ReferenceEntrySet();
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
    public static class Singleton<V>
    extends Char2ReferenceMaps.Singleton<V>
    implements Char2ReferenceSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharComparator comparator;

        protected Singleton(char c, V v, CharComparator charComparator) {
            super(c, v);
            this.comparator = charComparator;
        }

        protected Singleton(char c, V v) {
            this(c, v, null);
        }

        final int compare(char c, char c2) {
            return this.comparator == null ? Character.compare(c, c2) : this.comparator.compare(c, c2);
        }

        @Override
        public CharComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractChar2ReferenceMap.BasicEntry<Object>(this.key, this.value), Char2ReferenceSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
            return this.char2ReferenceEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.singleton(this.key, this.comparator);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2ReferenceSortedMap<V> subMap(char c, char c2) {
            if (this.compare(c, this.key) <= 0 && this.compare(this.key, c2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2ReferenceSortedMap<V> headMap(char c) {
            if (this.compare(this.key, c) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2ReferenceSortedMap<V> tailMap(char c) {
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
        public Char2ReferenceSortedMap<V> headMap(Character c) {
            return this.headMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2ReferenceSortedMap<V> tailMap(Character c) {
            return this.tailMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2ReferenceSortedMap<V> subMap(Character c, Character c2) {
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
        public ObjectSet char2ReferenceEntrySet() {
            return this.char2ReferenceEntrySet();
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
    public static class EmptySortedMap<V>
    extends Char2ReferenceMaps.EmptyMap<V>
    implements Char2ReferenceSortedMap<V>,
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
        public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public CharSortedSet keySet() {
            return CharSortedSets.EMPTY_SET;
        }

        @Override
        public Char2ReferenceSortedMap<V> subMap(char c, char c2) {
            return EMPTY_MAP;
        }

        @Override
        public Char2ReferenceSortedMap<V> headMap(char c) {
            return EMPTY_MAP;
        }

        @Override
        public Char2ReferenceSortedMap<V> tailMap(char c) {
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
        public Char2ReferenceSortedMap<V> headMap(Character c) {
            return this.headMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2ReferenceSortedMap<V> tailMap(Character c) {
            return this.tailMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2ReferenceSortedMap<V> subMap(Character c, Character c2) {
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
        public ObjectSet char2ReferenceEntrySet() {
            return this.char2ReferenceEntrySet();
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

