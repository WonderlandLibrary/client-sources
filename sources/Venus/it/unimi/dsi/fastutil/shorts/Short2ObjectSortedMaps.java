/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMaps;
import it.unimi.dsi.fastutil.shorts.Short2ObjectSortedMap;
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

public final class Short2ObjectSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Short2ObjectSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Short, ?>> entryComparator(ShortComparator shortComparator) {
        return (arg_0, arg_1) -> Short2ObjectSortedMaps.lambda$entryComparator$0(shortComparator, arg_0, arg_1);
    }

    public static <V> ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> fastIterator(Short2ObjectSortedMap<V> short2ObjectSortedMap) {
        ObjectSet objectSet = short2ObjectSortedMap.short2ObjectEntrySet();
        return objectSet instanceof Short2ObjectSortedMap.FastSortedEntrySet ? ((Short2ObjectSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> ObjectBidirectionalIterable<Short2ObjectMap.Entry<V>> fastIterable(Short2ObjectSortedMap<V> short2ObjectSortedMap) {
        ObjectSet objectSet = short2ObjectSortedMap.short2ObjectEntrySet();
        return objectSet instanceof Short2ObjectSortedMap.FastSortedEntrySet ? ((Short2ObjectSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <V> Short2ObjectSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Short2ObjectSortedMap<V> singleton(Short s, V v) {
        return new Singleton<V>(s, v);
    }

    public static <V> Short2ObjectSortedMap<V> singleton(Short s, V v, ShortComparator shortComparator) {
        return new Singleton<V>(s, v, shortComparator);
    }

    public static <V> Short2ObjectSortedMap<V> singleton(short s, V v) {
        return new Singleton<V>(s, v);
    }

    public static <V> Short2ObjectSortedMap<V> singleton(short s, V v, ShortComparator shortComparator) {
        return new Singleton<V>(s, v, shortComparator);
    }

    public static <V> Short2ObjectSortedMap<V> synchronize(Short2ObjectSortedMap<V> short2ObjectSortedMap) {
        return new SynchronizedSortedMap<V>(short2ObjectSortedMap);
    }

    public static <V> Short2ObjectSortedMap<V> synchronize(Short2ObjectSortedMap<V> short2ObjectSortedMap, Object object) {
        return new SynchronizedSortedMap<V>(short2ObjectSortedMap, object);
    }

    public static <V> Short2ObjectSortedMap<V> unmodifiable(Short2ObjectSortedMap<V> short2ObjectSortedMap) {
        return new UnmodifiableSortedMap<V>(short2ObjectSortedMap);
    }

    private static int lambda$entryComparator$0(ShortComparator shortComparator, Map.Entry entry, Map.Entry entry2) {
        return shortComparator.compare((short)((Short)entry.getKey()), (short)((Short)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<V>
    extends Short2ObjectMaps.UnmodifiableMap<V>
    implements Short2ObjectSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2ObjectSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Short2ObjectSortedMap<V> short2ObjectSortedMap) {
            super(short2ObjectSortedMap);
            this.sortedMap = short2ObjectSortedMap;
        }

        @Override
        public ShortComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.short2ObjectEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
            return this.short2ObjectEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2ObjectSortedMap<V> subMap(short s, short s2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(s, s2));
        }

        @Override
        public Short2ObjectSortedMap<V> headMap(short s) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(s));
        }

        @Override
        public Short2ObjectSortedMap<V> tailMap(short s) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(s));
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
        public Short2ObjectSortedMap<V> subMap(Short s, Short s2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(s, s2));
        }

        @Override
        @Deprecated
        public Short2ObjectSortedMap<V> headMap(Short s) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(s));
        }

        @Override
        @Deprecated
        public Short2ObjectSortedMap<V> tailMap(Short s) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(s));
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
        public ObjectSet short2ObjectEntrySet() {
            return this.short2ObjectEntrySet();
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
    public static class SynchronizedSortedMap<V>
    extends Short2ObjectMaps.SynchronizedMap<V>
    implements Short2ObjectSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2ObjectSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Short2ObjectSortedMap<V> short2ObjectSortedMap, Object object) {
            super(short2ObjectSortedMap, object);
            this.sortedMap = short2ObjectSortedMap;
        }

        protected SynchronizedSortedMap(Short2ObjectSortedMap<V> short2ObjectSortedMap) {
            super(short2ObjectSortedMap);
            this.sortedMap = short2ObjectSortedMap;
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
        public ObjectSortedSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.short2ObjectEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
            return this.short2ObjectEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2ObjectSortedMap<V> subMap(short s, short s2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(s, s2), this.sync);
        }

        @Override
        public Short2ObjectSortedMap<V> headMap(short s) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(s), this.sync);
        }

        @Override
        public Short2ObjectSortedMap<V> tailMap(short s) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(s), this.sync);
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
        public Short2ObjectSortedMap<V> subMap(Short s, Short s2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(s, s2), this.sync);
        }

        @Override
        @Deprecated
        public Short2ObjectSortedMap<V> headMap(Short s) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(s), this.sync);
        }

        @Override
        @Deprecated
        public Short2ObjectSortedMap<V> tailMap(Short s) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(s), this.sync);
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
        public ObjectSet short2ObjectEntrySet() {
            return this.short2ObjectEntrySet();
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
    public static class Singleton<V>
    extends Short2ObjectMaps.Singleton<V>
    implements Short2ObjectSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortComparator comparator;

        protected Singleton(short s, V v, ShortComparator shortComparator) {
            super(s, v);
            this.comparator = shortComparator;
        }

        protected Singleton(short s, V v) {
            this(s, v, null);
        }

        final int compare(short s, short s2) {
            return this.comparator == null ? Short.compare(s, s2) : this.comparator.compare(s, s2);
        }

        @Override
        public ShortComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractShort2ObjectMap.BasicEntry<Object>(this.key, this.value), Short2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
            return this.short2ObjectEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.singleton(this.key, this.comparator);
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2ObjectSortedMap<V> subMap(short s, short s2) {
            if (this.compare(s, this.key) <= 0 && this.compare(this.key, s2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Short2ObjectSortedMap<V> headMap(short s) {
            if (this.compare(this.key, s) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Short2ObjectSortedMap<V> tailMap(short s) {
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
        public Short2ObjectSortedMap<V> headMap(Short s) {
            return this.headMap((short)s);
        }

        @Override
        @Deprecated
        public Short2ObjectSortedMap<V> tailMap(Short s) {
            return this.tailMap((short)s);
        }

        @Override
        @Deprecated
        public Short2ObjectSortedMap<V> subMap(Short s, Short s2) {
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
        public ObjectSet short2ObjectEntrySet() {
            return this.short2ObjectEntrySet();
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
    public static class EmptySortedMap<V>
    extends Short2ObjectMaps.EmptyMap<V>
    implements Short2ObjectSortedMap<V>,
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
        public ObjectSortedSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ShortSortedSet keySet() {
            return ShortSortedSets.EMPTY_SET;
        }

        @Override
        public Short2ObjectSortedMap<V> subMap(short s, short s2) {
            return EMPTY_MAP;
        }

        @Override
        public Short2ObjectSortedMap<V> headMap(short s) {
            return EMPTY_MAP;
        }

        @Override
        public Short2ObjectSortedMap<V> tailMap(short s) {
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
        public Short2ObjectSortedMap<V> headMap(Short s) {
            return this.headMap((short)s);
        }

        @Override
        @Deprecated
        public Short2ObjectSortedMap<V> tailMap(Short s) {
            return this.tailMap((short)s);
        }

        @Override
        @Deprecated
        public Short2ObjectSortedMap<V> subMap(Short s, Short s2) {
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
        public ObjectSet short2ObjectEntrySet() {
            return this.short2ObjectEntrySet();
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

