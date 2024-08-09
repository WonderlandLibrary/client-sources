/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ByteMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteMaps;
import it.unimi.dsi.fastutil.shorts.Short2ByteSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public final class Short2ByteSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Short2ByteSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Short, ?>> entryComparator(ShortComparator shortComparator) {
        return (arg_0, arg_1) -> Short2ByteSortedMaps.lambda$entryComparator$0(shortComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Short2ByteMap.Entry> fastIterator(Short2ByteSortedMap short2ByteSortedMap) {
        ObjectSet objectSet = short2ByteSortedMap.short2ByteEntrySet();
        return objectSet instanceof Short2ByteSortedMap.FastSortedEntrySet ? ((Short2ByteSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Short2ByteMap.Entry> fastIterable(Short2ByteSortedMap short2ByteSortedMap) {
        ObjectSet objectSet = short2ByteSortedMap.short2ByteEntrySet();
        return objectSet instanceof Short2ByteSortedMap.FastSortedEntrySet ? ((Short2ByteSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Short2ByteSortedMap singleton(Short s, Byte by) {
        return new Singleton(s, by);
    }

    public static Short2ByteSortedMap singleton(Short s, Byte by, ShortComparator shortComparator) {
        return new Singleton(s, by, shortComparator);
    }

    public static Short2ByteSortedMap singleton(short s, byte by) {
        return new Singleton(s, by);
    }

    public static Short2ByteSortedMap singleton(short s, byte by, ShortComparator shortComparator) {
        return new Singleton(s, by, shortComparator);
    }

    public static Short2ByteSortedMap synchronize(Short2ByteSortedMap short2ByteSortedMap) {
        return new SynchronizedSortedMap(short2ByteSortedMap);
    }

    public static Short2ByteSortedMap synchronize(Short2ByteSortedMap short2ByteSortedMap, Object object) {
        return new SynchronizedSortedMap(short2ByteSortedMap, object);
    }

    public static Short2ByteSortedMap unmodifiable(Short2ByteSortedMap short2ByteSortedMap) {
        return new UnmodifiableSortedMap(short2ByteSortedMap);
    }

    private static int lambda$entryComparator$0(ShortComparator shortComparator, Map.Entry entry, Map.Entry entry2) {
        return shortComparator.compare((short)((Short)entry.getKey()), (short)((Short)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Short2ByteMaps.UnmodifiableMap
    implements Short2ByteSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2ByteSortedMap sortedMap;

        protected UnmodifiableSortedMap(Short2ByteSortedMap short2ByteSortedMap) {
            super(short2ByteSortedMap);
            this.sortedMap = short2ByteSortedMap;
        }

        @Override
        public ShortComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.short2ByteEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
            return this.short2ByteEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2ByteSortedMap subMap(short s, short s2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(s, s2));
        }

        @Override
        public Short2ByteSortedMap headMap(short s) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(s));
        }

        @Override
        public Short2ByteSortedMap tailMap(short s) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(s));
        }

        @Override
        public short firstShortKey() {
            return this.sortedMap.firstShortKey();
        }

        @Override
        public short lastShortKey() {
            return this.sortedMap.lastShortKey();
        }

        @Override
        @Deprecated
        public Short firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        @Deprecated
        public Short lastKey() {
            return this.sortedMap.lastKey();
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap subMap(Short s, Short s2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(s, s2));
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap headMap(Short s) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(s));
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap tailMap(Short s) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(s));
        }

        @Override
        public ShortSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet short2ByteEntrySet() {
            return this.short2ByteEntrySet();
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
            return this.tailMap((Short)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Short)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Short)object, (Short)object2);
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
    extends Short2ByteMaps.SynchronizedMap
    implements Short2ByteSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2ByteSortedMap sortedMap;

        protected SynchronizedSortedMap(Short2ByteSortedMap short2ByteSortedMap, Object object) {
            super(short2ByteSortedMap, object);
            this.sortedMap = short2ByteSortedMap;
        }

        protected SynchronizedSortedMap(Short2ByteSortedMap short2ByteSortedMap) {
            super(short2ByteSortedMap);
            this.sortedMap = short2ByteSortedMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.short2ByteEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
            return this.short2ByteEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2ByteSortedMap subMap(short s, short s2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(s, s2), this.sync);
        }

        @Override
        public Short2ByteSortedMap headMap(short s) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(s), this.sync);
        }

        @Override
        public Short2ByteSortedMap tailMap(short s) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(s), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short firstShortKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstShortKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short lastShortKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastShortKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short firstKey() {
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
        public Short lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastKey();
            }
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap subMap(Short s, Short s2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(s, s2), this.sync);
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap headMap(Short s) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(s), this.sync);
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap tailMap(Short s) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(s), this.sync);
        }

        @Override
        public ShortSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet short2ByteEntrySet() {
            return this.short2ByteEntrySet();
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
            return this.tailMap((Short)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Short)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Short)object, (Short)object2);
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
    extends Short2ByteMaps.Singleton
    implements Short2ByteSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortComparator comparator;

        protected Singleton(short s, byte by, ShortComparator shortComparator) {
            super(s, by);
            this.comparator = shortComparator;
        }

        protected Singleton(short s, byte by) {
            this(s, by, null);
        }

        final int compare(short s, short s2) {
            return this.comparator == null ? Short.compare(s, s2) : this.comparator.compare(s, s2);
        }

        @Override
        public ShortComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractShort2ByteMap.BasicEntry(this.key, this.value), Short2ByteSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
            return this.short2ByteEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.singleton(this.key, this.comparator);
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2ByteSortedMap subMap(short s, short s2) {
            if (this.compare(s, this.key) <= 0 && this.compare(this.key, s2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Short2ByteSortedMap headMap(short s) {
            if (this.compare(this.key, s) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Short2ByteSortedMap tailMap(short s) {
            if (this.compare(s, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public short firstShortKey() {
            return this.key;
        }

        @Override
        public short lastShortKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap headMap(Short s) {
            return this.headMap((short)s);
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap tailMap(Short s) {
            return this.tailMap((short)s);
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap subMap(Short s, Short s2) {
            return this.subMap((short)s, (short)s2);
        }

        @Override
        @Deprecated
        public Short firstKey() {
            return this.firstShortKey();
        }

        @Override
        @Deprecated
        public Short lastKey() {
            return this.lastShortKey();
        }

        @Override
        public ShortSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet short2ByteEntrySet() {
            return this.short2ByteEntrySet();
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
            return this.tailMap((Short)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Short)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Short)object, (Short)object2);
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
    extends Short2ByteMaps.EmptyMap
    implements Short2ByteSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public ShortComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ShortSortedSet keySet() {
            return ShortSortedSets.EMPTY_SET;
        }

        @Override
        public Short2ByteSortedMap subMap(short s, short s2) {
            return EMPTY_MAP;
        }

        @Override
        public Short2ByteSortedMap headMap(short s) {
            return EMPTY_MAP;
        }

        @Override
        public Short2ByteSortedMap tailMap(short s) {
            return EMPTY_MAP;
        }

        @Override
        public short firstShortKey() {
            throw new NoSuchElementException();
        }

        @Override
        public short lastShortKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap headMap(Short s) {
            return this.headMap((short)s);
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap tailMap(Short s) {
            return this.tailMap((short)s);
        }

        @Override
        @Deprecated
        public Short2ByteSortedMap subMap(Short s, Short s2) {
            return this.subMap((short)s, (short)s2);
        }

        @Override
        @Deprecated
        public Short firstKey() {
            return this.firstShortKey();
        }

        @Override
        @Deprecated
        public Short lastKey() {
            return this.lastShortKey();
        }

        @Override
        public ShortSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet short2ByteEntrySet() {
            return this.short2ByteEntrySet();
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
            return this.tailMap((Short)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Short)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Short)object, (Short)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

