/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2FloatMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatMaps;
import it.unimi.dsi.fastutil.shorts.Short2FloatSortedMap;
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

public final class Short2FloatSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Short2FloatSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Short, ?>> entryComparator(ShortComparator shortComparator) {
        return (arg_0, arg_1) -> Short2FloatSortedMaps.lambda$entryComparator$0(shortComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Short2FloatMap.Entry> fastIterator(Short2FloatSortedMap short2FloatSortedMap) {
        ObjectSet objectSet = short2FloatSortedMap.short2FloatEntrySet();
        return objectSet instanceof Short2FloatSortedMap.FastSortedEntrySet ? ((Short2FloatSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Short2FloatMap.Entry> fastIterable(Short2FloatSortedMap short2FloatSortedMap) {
        ObjectSet objectSet = short2FloatSortedMap.short2FloatEntrySet();
        return objectSet instanceof Short2FloatSortedMap.FastSortedEntrySet ? ((Short2FloatSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Short2FloatSortedMap singleton(Short s, Float f) {
        return new Singleton(s, f.floatValue());
    }

    public static Short2FloatSortedMap singleton(Short s, Float f, ShortComparator shortComparator) {
        return new Singleton(s, f.floatValue(), shortComparator);
    }

    public static Short2FloatSortedMap singleton(short s, float f) {
        return new Singleton(s, f);
    }

    public static Short2FloatSortedMap singleton(short s, float f, ShortComparator shortComparator) {
        return new Singleton(s, f, shortComparator);
    }

    public static Short2FloatSortedMap synchronize(Short2FloatSortedMap short2FloatSortedMap) {
        return new SynchronizedSortedMap(short2FloatSortedMap);
    }

    public static Short2FloatSortedMap synchronize(Short2FloatSortedMap short2FloatSortedMap, Object object) {
        return new SynchronizedSortedMap(short2FloatSortedMap, object);
    }

    public static Short2FloatSortedMap unmodifiable(Short2FloatSortedMap short2FloatSortedMap) {
        return new UnmodifiableSortedMap(short2FloatSortedMap);
    }

    private static int lambda$entryComparator$0(ShortComparator shortComparator, Map.Entry entry, Map.Entry entry2) {
        return shortComparator.compare((short)((Short)entry.getKey()), (short)((Short)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Short2FloatMaps.UnmodifiableMap
    implements Short2FloatSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2FloatSortedMap sortedMap;

        protected UnmodifiableSortedMap(Short2FloatSortedMap short2FloatSortedMap) {
            super(short2FloatSortedMap);
            this.sortedMap = short2FloatSortedMap;
        }

        @Override
        public ShortComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Short2FloatMap.Entry> short2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.short2FloatEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, Float>> entrySet() {
            return this.short2FloatEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2FloatSortedMap subMap(short s, short s2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(s, s2));
        }

        @Override
        public Short2FloatSortedMap headMap(short s) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(s));
        }

        @Override
        public Short2FloatSortedMap tailMap(short s) {
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
        public Short2FloatSortedMap subMap(Short s, Short s2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(s, s2));
        }

        @Override
        @Deprecated
        public Short2FloatSortedMap headMap(Short s) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(s));
        }

        @Override
        @Deprecated
        public Short2FloatSortedMap tailMap(Short s) {
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
        public ObjectSet short2FloatEntrySet() {
            return this.short2FloatEntrySet();
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
    extends Short2FloatMaps.SynchronizedMap
    implements Short2FloatSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2FloatSortedMap sortedMap;

        protected SynchronizedSortedMap(Short2FloatSortedMap short2FloatSortedMap, Object object) {
            super(short2FloatSortedMap, object);
            this.sortedMap = short2FloatSortedMap;
        }

        protected SynchronizedSortedMap(Short2FloatSortedMap short2FloatSortedMap) {
            super(short2FloatSortedMap);
            this.sortedMap = short2FloatSortedMap;
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
        public ObjectSortedSet<Short2FloatMap.Entry> short2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.short2FloatEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, Float>> entrySet() {
            return this.short2FloatEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2FloatSortedMap subMap(short s, short s2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(s, s2), this.sync);
        }

        @Override
        public Short2FloatSortedMap headMap(short s) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(s), this.sync);
        }

        @Override
        public Short2FloatSortedMap tailMap(short s) {
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
        public Short2FloatSortedMap subMap(Short s, Short s2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(s, s2), this.sync);
        }

        @Override
        @Deprecated
        public Short2FloatSortedMap headMap(Short s) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(s), this.sync);
        }

        @Override
        @Deprecated
        public Short2FloatSortedMap tailMap(Short s) {
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
        public ObjectSet short2FloatEntrySet() {
            return this.short2FloatEntrySet();
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
    extends Short2FloatMaps.Singleton
    implements Short2FloatSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortComparator comparator;

        protected Singleton(short s, float f, ShortComparator shortComparator) {
            super(s, f);
            this.comparator = shortComparator;
        }

        protected Singleton(short s, float f) {
            this(s, f, null);
        }

        final int compare(short s, short s2) {
            return this.comparator == null ? Short.compare(s, s2) : this.comparator.compare(s, s2);
        }

        @Override
        public ShortComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Short2FloatMap.Entry> short2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractShort2FloatMap.BasicEntry(this.key, this.value), Short2FloatSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, Float>> entrySet() {
            return this.short2FloatEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.singleton(this.key, this.comparator);
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2FloatSortedMap subMap(short s, short s2) {
            if (this.compare(s, this.key) <= 0 && this.compare(this.key, s2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Short2FloatSortedMap headMap(short s) {
            if (this.compare(this.key, s) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Short2FloatSortedMap tailMap(short s) {
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
        public Short2FloatSortedMap headMap(Short s) {
            return this.headMap((short)s);
        }

        @Override
        @Deprecated
        public Short2FloatSortedMap tailMap(Short s) {
            return this.tailMap((short)s);
        }

        @Override
        @Deprecated
        public Short2FloatSortedMap subMap(Short s, Short s2) {
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
        public ObjectSet short2FloatEntrySet() {
            return this.short2FloatEntrySet();
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
    extends Short2FloatMaps.EmptyMap
    implements Short2FloatSortedMap,
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
        public ObjectSortedSet<Short2FloatMap.Entry> short2FloatEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, Float>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ShortSortedSet keySet() {
            return ShortSortedSets.EMPTY_SET;
        }

        @Override
        public Short2FloatSortedMap subMap(short s, short s2) {
            return EMPTY_MAP;
        }

        @Override
        public Short2FloatSortedMap headMap(short s) {
            return EMPTY_MAP;
        }

        @Override
        public Short2FloatSortedMap tailMap(short s) {
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
        public Short2FloatSortedMap headMap(Short s) {
            return this.headMap((short)s);
        }

        @Override
        @Deprecated
        public Short2FloatSortedMap tailMap(Short s) {
            return this.tailMap((short)s);
        }

        @Override
        @Deprecated
        public Short2FloatSortedMap subMap(Short s, Short s2) {
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
        public ObjectSet short2FloatEntrySet() {
            return this.short2FloatEntrySet();
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

