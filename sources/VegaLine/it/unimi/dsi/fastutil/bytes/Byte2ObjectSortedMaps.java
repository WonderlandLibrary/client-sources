/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMaps;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Byte2ObjectSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Byte2ObjectSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Byte, ?>> entryComparator(final ByteComparator comparator) {
        return new Comparator<Map.Entry<Byte, ?>>(){

            @Override
            public int compare(Map.Entry<Byte, ?> x, Map.Entry<Byte, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static <V> Byte2ObjectSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Byte2ObjectSortedMap<V> singleton(Byte key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Byte2ObjectSortedMap<V> singleton(Byte key, V value, ByteComparator comparator) {
        return new Singleton<V>(key, value, comparator);
    }

    public static <V> Byte2ObjectSortedMap<V> singleton(byte key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Byte2ObjectSortedMap<V> singleton(byte key, V value, ByteComparator comparator) {
        return new Singleton<V>(key, value, comparator);
    }

    public static <V> Byte2ObjectSortedMap<V> synchronize(Byte2ObjectSortedMap<V> m) {
        return new SynchronizedSortedMap<V>(m);
    }

    public static <V> Byte2ObjectSortedMap<V> synchronize(Byte2ObjectSortedMap<V> m, Object sync) {
        return new SynchronizedSortedMap<V>(m, sync);
    }

    public static <V> Byte2ObjectSortedMap<V> unmodifiable(Byte2ObjectSortedMap<V> m) {
        return new UnmodifiableSortedMap<V>(m);
    }

    public static class UnmodifiableSortedMap<V>
    extends Byte2ObjectMaps.UnmodifiableMap<V>
    implements Byte2ObjectSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2ObjectSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Byte2ObjectSortedMap<V> m) {
            super(m);
            this.sortedMap = m;
        }

        @Override
        public ByteComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.byte2ObjectEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
            return this.byte2ObjectEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(byte from, byte to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(byte to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(to));
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(byte from) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(from));
        }

        @Override
        public byte firstByteKey() {
            return this.sortedMap.firstByteKey();
        }

        @Override
        public byte lastByteKey() {
            return this.sortedMap.lastByteKey();
        }

        @Override
        public Byte firstKey() {
            return (Byte)this.sortedMap.firstKey();
        }

        @Override
        public Byte lastKey() {
            return (Byte)this.sortedMap.lastKey();
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(Byte from, Byte to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(Byte to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(to));
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(Byte from) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(from));
        }
    }

    public static class SynchronizedSortedMap<V>
    extends Byte2ObjectMaps.SynchronizedMap<V>
    implements Byte2ObjectSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2ObjectSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Byte2ObjectSortedMap<V> m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Byte2ObjectSortedMap<V> m) {
            super(m);
            this.sortedMap = m;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.byte2ObjectEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
            return this.byte2ObjectEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(byte from, byte to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(byte to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(byte from) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(from), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte firstByteKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstByteKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte lastByteKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastByteKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Byte firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Byte lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Byte)this.sortedMap.lastKey();
            }
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(Byte from, Byte to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(Byte to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(Byte from) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(from), this.sync);
        }
    }

    public static class Singleton<V>
    extends Byte2ObjectMaps.Singleton<V>
    implements Byte2ObjectSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteComparator comparator;

        protected Singleton(byte key, V value, ByteComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(byte key, V value) {
            this(key, value, null);
        }

        final int compare(byte k1, byte k2) {
            return this.comparator == null ? Byte.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public ByteComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Byte2ObjectMaps.Singleton.SingletonEntry(), Byte2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
            return this.byte2ObjectEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.singleton(this.key, this.comparator);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(byte from, byte to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(byte to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(byte from) {
            if (this.compare(from, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public byte firstByteKey() {
            return this.key;
        }

        @Override
        public byte lastByteKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> headMap(Byte oto) {
            return this.headMap((byte)oto);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> tailMap(Byte ofrom) {
            return this.tailMap((byte)ofrom);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> subMap(Byte ofrom, Byte oto) {
            return this.subMap((byte)ofrom, (byte)oto);
        }

        @Override
        @Deprecated
        public Byte firstKey() {
            return this.firstByteKey();
        }

        @Override
        @Deprecated
        public Byte lastKey() {
            return this.lastByteKey();
        }
    }

    public static class EmptySortedMap<V>
    extends Byte2ObjectMaps.EmptyMap<V>
    implements Byte2ObjectSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public ByteComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ByteSortedSet keySet() {
            return ByteSortedSets.EMPTY_SET;
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(byte from, byte to) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(byte to) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(byte from) {
            return EMPTY_MAP;
        }

        @Override
        public byte firstByteKey() {
            throw new NoSuchElementException();
        }

        @Override
        public byte lastByteKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> headMap(Byte oto) {
            return this.headMap((byte)oto);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> tailMap(Byte ofrom) {
            return this.tailMap((byte)ofrom);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> subMap(Byte ofrom, Byte oto) {
            return this.subMap((byte)ofrom, (byte)oto);
        }

        @Override
        @Deprecated
        public Byte firstKey() {
            return this.firstByteKey();
        }

        @Override
        @Deprecated
        public Byte lastKey() {
            return this.lastByteKey();
        }
    }
}

