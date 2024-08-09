/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2BooleanMap;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap;
import it.unimi.dsi.fastutil.chars.Char2BooleanMaps;
import it.unimi.dsi.fastutil.chars.Char2BooleanSortedMap;
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

public final class Char2BooleanSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Char2BooleanSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Character, ?>> entryComparator(CharComparator charComparator) {
        return (arg_0, arg_1) -> Char2BooleanSortedMaps.lambda$entryComparator$0(charComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Char2BooleanMap.Entry> fastIterator(Char2BooleanSortedMap char2BooleanSortedMap) {
        ObjectSet objectSet = char2BooleanSortedMap.char2BooleanEntrySet();
        return objectSet instanceof Char2BooleanSortedMap.FastSortedEntrySet ? ((Char2BooleanSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Char2BooleanMap.Entry> fastIterable(Char2BooleanSortedMap char2BooleanSortedMap) {
        ObjectSet objectSet = char2BooleanSortedMap.char2BooleanEntrySet();
        return objectSet instanceof Char2BooleanSortedMap.FastSortedEntrySet ? ((Char2BooleanSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Char2BooleanSortedMap singleton(Character c, Boolean bl) {
        return new Singleton(c.charValue(), bl);
    }

    public static Char2BooleanSortedMap singleton(Character c, Boolean bl, CharComparator charComparator) {
        return new Singleton(c.charValue(), bl, charComparator);
    }

    public static Char2BooleanSortedMap singleton(char c, boolean bl) {
        return new Singleton(c, bl);
    }

    public static Char2BooleanSortedMap singleton(char c, boolean bl, CharComparator charComparator) {
        return new Singleton(c, bl, charComparator);
    }

    public static Char2BooleanSortedMap synchronize(Char2BooleanSortedMap char2BooleanSortedMap) {
        return new SynchronizedSortedMap(char2BooleanSortedMap);
    }

    public static Char2BooleanSortedMap synchronize(Char2BooleanSortedMap char2BooleanSortedMap, Object object) {
        return new SynchronizedSortedMap(char2BooleanSortedMap, object);
    }

    public static Char2BooleanSortedMap unmodifiable(Char2BooleanSortedMap char2BooleanSortedMap) {
        return new UnmodifiableSortedMap(char2BooleanSortedMap);
    }

    private static int lambda$entryComparator$0(CharComparator charComparator, Map.Entry entry, Map.Entry entry2) {
        return charComparator.compare(((Character)entry.getKey()).charValue(), ((Character)entry2.getKey()).charValue());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Char2BooleanMaps.UnmodifiableMap
    implements Char2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2BooleanSortedMap sortedMap;

        protected UnmodifiableSortedMap(Char2BooleanSortedMap char2BooleanSortedMap) {
            super(char2BooleanSortedMap);
            this.sortedMap = char2BooleanSortedMap;
        }

        @Override
        public CharComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Char2BooleanMap.Entry> char2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.char2BooleanEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, Boolean>> entrySet() {
            return this.char2BooleanEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2BooleanSortedMap subMap(char c, char c2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(c, c2));
        }

        @Override
        public Char2BooleanSortedMap headMap(char c) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(c));
        }

        @Override
        public Char2BooleanSortedMap tailMap(char c) {
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
        public Char2BooleanSortedMap subMap(Character c, Character c2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(c, c2));
        }

        @Override
        @Deprecated
        public Char2BooleanSortedMap headMap(Character c) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(c));
        }

        @Override
        @Deprecated
        public Char2BooleanSortedMap tailMap(Character c) {
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
        public ObjectSet char2BooleanEntrySet() {
            return this.char2BooleanEntrySet();
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
    extends Char2BooleanMaps.SynchronizedMap
    implements Char2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2BooleanSortedMap sortedMap;

        protected SynchronizedSortedMap(Char2BooleanSortedMap char2BooleanSortedMap, Object object) {
            super(char2BooleanSortedMap, object);
            this.sortedMap = char2BooleanSortedMap;
        }

        protected SynchronizedSortedMap(Char2BooleanSortedMap char2BooleanSortedMap) {
            super(char2BooleanSortedMap);
            this.sortedMap = char2BooleanSortedMap;
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
        public ObjectSortedSet<Char2BooleanMap.Entry> char2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.char2BooleanEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, Boolean>> entrySet() {
            return this.char2BooleanEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2BooleanSortedMap subMap(char c, char c2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(c, c2), this.sync);
        }

        @Override
        public Char2BooleanSortedMap headMap(char c) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(c), this.sync);
        }

        @Override
        public Char2BooleanSortedMap tailMap(char c) {
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
        public Char2BooleanSortedMap subMap(Character c, Character c2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(c, c2), this.sync);
        }

        @Override
        @Deprecated
        public Char2BooleanSortedMap headMap(Character c) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(c), this.sync);
        }

        @Override
        @Deprecated
        public Char2BooleanSortedMap tailMap(Character c) {
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
        public ObjectSet char2BooleanEntrySet() {
            return this.char2BooleanEntrySet();
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
    extends Char2BooleanMaps.Singleton
    implements Char2BooleanSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharComparator comparator;

        protected Singleton(char c, boolean bl, CharComparator charComparator) {
            super(c, bl);
            this.comparator = charComparator;
        }

        protected Singleton(char c, boolean bl) {
            this(c, bl, null);
        }

        final int compare(char c, char c2) {
            return this.comparator == null ? Character.compare(c, c2) : this.comparator.compare(c, c2);
        }

        @Override
        public CharComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Char2BooleanMap.Entry> char2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractChar2BooleanMap.BasicEntry(this.key, this.value), Char2BooleanSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, Boolean>> entrySet() {
            return this.char2BooleanEntrySet();
        }

        @Override
        public CharSortedSet keySet() {
            if (this.keys == null) {
                this.keys = CharSortedSets.singleton(this.key, this.comparator);
            }
            return (CharSortedSet)this.keys;
        }

        @Override
        public Char2BooleanSortedMap subMap(char c, char c2) {
            if (this.compare(c, this.key) <= 0 && this.compare(this.key, c2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2BooleanSortedMap headMap(char c) {
            if (this.compare(this.key, c) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Char2BooleanSortedMap tailMap(char c) {
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
        public Char2BooleanSortedMap headMap(Character c) {
            return this.headMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2BooleanSortedMap tailMap(Character c) {
            return this.tailMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2BooleanSortedMap subMap(Character c, Character c2) {
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
        public ObjectSet char2BooleanEntrySet() {
            return this.char2BooleanEntrySet();
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
    extends Char2BooleanMaps.EmptyMap
    implements Char2BooleanSortedMap,
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
        public ObjectSortedSet<Char2BooleanMap.Entry> char2BooleanEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Character, Boolean>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public CharSortedSet keySet() {
            return CharSortedSets.EMPTY_SET;
        }

        @Override
        public Char2BooleanSortedMap subMap(char c, char c2) {
            return EMPTY_MAP;
        }

        @Override
        public Char2BooleanSortedMap headMap(char c) {
            return EMPTY_MAP;
        }

        @Override
        public Char2BooleanSortedMap tailMap(char c) {
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
        public Char2BooleanSortedMap headMap(Character c) {
            return this.headMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2BooleanSortedMap tailMap(Character c) {
            return this.tailMap(c.charValue());
        }

        @Override
        @Deprecated
        public Char2BooleanSortedMap subMap(Character c, Character c2) {
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
        public ObjectSet char2BooleanEntrySet() {
            return this.char2BooleanEntrySet();
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

