/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.longs.AbstractLong2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
import it.unimi.dsi.fastutil.longs.Long2ByteFunctions;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

public final class Long2ByteMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Long2ByteMaps() {
    }

    public static ObjectIterator<Long2ByteMap.Entry> fastIterator(Long2ByteMap long2ByteMap) {
        ObjectSet<Long2ByteMap.Entry> objectSet = long2ByteMap.long2ByteEntrySet();
        return objectSet instanceof Long2ByteMap.FastEntrySet ? ((Long2ByteMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Long2ByteMap long2ByteMap, Consumer<? super Long2ByteMap.Entry> consumer) {
        ObjectSet<Long2ByteMap.Entry> objectSet = long2ByteMap.long2ByteEntrySet();
        if (objectSet instanceof Long2ByteMap.FastEntrySet) {
            ((Long2ByteMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Long2ByteMap.Entry> fastIterable(Long2ByteMap long2ByteMap) {
        ObjectSet<Long2ByteMap.Entry> objectSet = long2ByteMap.long2ByteEntrySet();
        return objectSet instanceof Long2ByteMap.FastEntrySet ? new ObjectIterable<Long2ByteMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Long2ByteMap.Entry> iterator() {
                return ((Long2ByteMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Long2ByteMap.Entry> consumer) {
                ((Long2ByteMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Long2ByteMap singleton(long l, byte by) {
        return new Singleton(l, by);
    }

    public static Long2ByteMap singleton(Long l, Byte by) {
        return new Singleton(l, by);
    }

    public static Long2ByteMap synchronize(Long2ByteMap long2ByteMap) {
        return new SynchronizedMap(long2ByteMap);
    }

    public static Long2ByteMap synchronize(Long2ByteMap long2ByteMap, Object object) {
        return new SynchronizedMap(long2ByteMap, object);
    }

    public static Long2ByteMap unmodifiable(Long2ByteMap long2ByteMap) {
        return new UnmodifiableMap(long2ByteMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Long2ByteFunctions.UnmodifiableFunction
    implements Long2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ByteMap map;
        protected transient ObjectSet<Long2ByteMap.Entry> entries;
        protected transient LongSet keys;
        protected transient ByteCollection values;

        protected UnmodifiableMap(Long2ByteMap long2ByteMap) {
            super(long2ByteMap);
            this.map = long2ByteMap;
        }

        @Override
        public boolean containsValue(byte by) {
            return this.map.containsValue(by);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2ByteMap.Entry> long2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.long2ByteEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Byte>> entrySet() {
            return this.long2ByteEntrySet();
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                return ByteCollections.unmodifiable(this.map.values());
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public int hashCode() {
            return this.map.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            return this.map.equals(object);
        }

        @Override
        public byte getOrDefault(long l, byte by) {
            return this.map.getOrDefault(l, by);
        }

        @Override
        public void forEach(BiConsumer<? super Long, ? super Byte> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte putIfAbsent(long l, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(long l, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte replace(long l, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(long l, byte by, byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsentNullable(long l, LongFunction<? extends Byte> longFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsentPartial(long l, Long2ByteFunction long2ByteFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfPresent(long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte compute(long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte merge(long l, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte getOrDefault(Object object, Byte by) {
            return this.map.getOrDefault(object, by);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte replace(Long l, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Long l, Byte by, Byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte putIfAbsent(Long l, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte computeIfAbsent(Long l, Function<? super Long, ? extends Byte> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte computeIfPresent(Long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte compute(Long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte merge(Long l, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Long)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Long)object, (BiFunction<? super Long, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Byte>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Long)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Long)object, (Byte)object2, (Byte)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Long)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Collection values() {
            return this.values();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedMap
    extends Long2ByteFunctions.SynchronizedFunction
    implements Long2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ByteMap map;
        protected transient ObjectSet<Long2ByteMap.Entry> entries;
        protected transient LongSet keys;
        protected transient ByteCollection values;

        protected SynchronizedMap(Long2ByteMap long2ByteMap, Object object) {
            super(long2ByteMap, object);
            this.map = long2ByteMap;
        }

        protected SynchronizedMap(Long2ByteMap long2ByteMap) {
            super(long2ByteMap);
            this.map = long2ByteMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.containsValue(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void putAll(Map<? extends Long, ? extends Byte> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Long2ByteMap.Entry> long2ByteEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.long2ByteEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Byte>> entrySet() {
            return this.long2ByteEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = LongSets.synchronize(this.map.keySet(), this.sync);
                }
                return this.keys;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return ByteCollections.synchronize(this.map.values(), this.sync);
                }
                return this.values;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isEmpty() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.isEmpty();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.hashCode();
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
                return this.map.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte getOrDefault(long l, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Long, ? super Byte> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte putIfAbsent(long l, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(long l, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte replace(long l, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(long l, byte by, byte by2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, by, by2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(l, longToIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsentNullable(long l, LongFunction<? extends Byte> longFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(l, longFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsentPartial(long l, Long2ByteFunction long2ByteFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(l, long2ByteFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfPresent(long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte compute(long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte merge(long l, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(l, by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte getOrDefault(Object object, Byte by) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            Object object3 = this.sync;
            synchronized (object3) {
                return this.map.remove(object, object2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte replace(Long l, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Long l, Byte by, Byte by2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, by, by2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte putIfAbsent(Long l, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(l, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte computeIfAbsent(Long l, Function<? super Long, ? extends Byte> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(l, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte computeIfPresent(Long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte compute(Long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte merge(Long l, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(l, by, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Long)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Long)object, (BiFunction<? super Long, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Byte>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Long)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Long)object, (Byte)object2, (Byte)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Long)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Collection values() {
            return this.values();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends Long2ByteFunctions.Singleton
    implements Long2ByteMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Long2ByteMap.Entry> entries;
        protected transient LongSet keys;
        protected transient ByteCollection values;

        protected Singleton(long l, byte by) {
            super(l, by);
        }

        @Override
        public boolean containsValue(byte by) {
            return this.value == by;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Byte)object == this.value;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2ByteMap.Entry> long2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractLong2ByteMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Byte>> entrySet() {
            return this.long2ByteEntrySet();
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                this.values = ByteSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ this.value;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof Map)) {
                return true;
            }
            Map map = (Map)object;
            if (map.size() != 1) {
                return true;
            }
            return map.entrySet().iterator().next().equals(this.entrySet().iterator().next());
        }

        public String toString() {
            return "{" + this.key + "=>" + this.value + "}";
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Collection values() {
            return this.values();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyMap
    extends Long2ByteFunctions.EmptyFunction
    implements Long2ByteMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(byte by) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2ByteMap.Entry> long2ByteEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public LongSet keySet() {
            return LongSets.EMPTY_SET;
        }

        @Override
        public ByteCollection values() {
            return ByteSets.EMPTY_SET;
        }

        @Override
        public Object clone() {
            return EMPTY_MAP;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map)) {
                return true;
            }
            return ((Map)object).isEmpty();
        }

        @Override
        public String toString() {
            return "{}";
        }

        @Override
        public Collection values() {
            return this.values();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }
    }
}

