/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
import it.unimi.dsi.fastutil.longs.Long2ReferenceFunctions;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.objects.ReferenceCollections;
import it.unimi.dsi.fastutil.objects.ReferenceSets;
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

public final class Long2ReferenceMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Long2ReferenceMaps() {
    }

    public static <V> ObjectIterator<Long2ReferenceMap.Entry<V>> fastIterator(Long2ReferenceMap<V> long2ReferenceMap) {
        ObjectSet<Long2ReferenceMap.Entry<V>> objectSet = long2ReferenceMap.long2ReferenceEntrySet();
        return objectSet instanceof Long2ReferenceMap.FastEntrySet ? ((Long2ReferenceMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> void fastForEach(Long2ReferenceMap<V> long2ReferenceMap, Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
        ObjectSet<Long2ReferenceMap.Entry<V>> objectSet = long2ReferenceMap.long2ReferenceEntrySet();
        if (objectSet instanceof Long2ReferenceMap.FastEntrySet) {
            ((Long2ReferenceMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <V> ObjectIterable<Long2ReferenceMap.Entry<V>> fastIterable(Long2ReferenceMap<V> long2ReferenceMap) {
        ObjectSet<Long2ReferenceMap.Entry<V>> objectSet = long2ReferenceMap.long2ReferenceEntrySet();
        return objectSet instanceof Long2ReferenceMap.FastEntrySet ? new ObjectIterable<Long2ReferenceMap.Entry<V>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Long2ReferenceMap.Entry<V>> iterator() {
                return ((Long2ReferenceMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
                ((Long2ReferenceMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <V> Long2ReferenceMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Long2ReferenceMap<V> singleton(long l, V v) {
        return new Singleton<V>(l, v);
    }

    public static <V> Long2ReferenceMap<V> singleton(Long l, V v) {
        return new Singleton<V>(l, v);
    }

    public static <V> Long2ReferenceMap<V> synchronize(Long2ReferenceMap<V> long2ReferenceMap) {
        return new SynchronizedMap<V>(long2ReferenceMap);
    }

    public static <V> Long2ReferenceMap<V> synchronize(Long2ReferenceMap<V> long2ReferenceMap, Object object) {
        return new SynchronizedMap<V>(long2ReferenceMap, object);
    }

    public static <V> Long2ReferenceMap<V> unmodifiable(Long2ReferenceMap<V> long2ReferenceMap) {
        return new UnmodifiableMap<V>(long2ReferenceMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<V>
    extends Long2ReferenceFunctions.UnmodifiableFunction<V>
    implements Long2ReferenceMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ReferenceMap<V> map;
        protected transient ObjectSet<Long2ReferenceMap.Entry<V>> entries;
        protected transient LongSet keys;
        protected transient ReferenceCollection<V> values;

        protected UnmodifiableMap(Long2ReferenceMap<V> long2ReferenceMap) {
            super(long2ReferenceMap);
            this.map = long2ReferenceMap;
        }

        @Override
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Long, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.long2ReferenceEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, V>> entrySet() {
            return this.long2ReferenceEntrySet();
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ReferenceCollection<V> values() {
            if (this.values == null) {
                return ReferenceCollections.unmodifiable(this.map.values());
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
        public V getOrDefault(long l, V v) {
            return this.map.getOrDefault(l, v);
        }

        @Override
        public void forEach(BiConsumer<? super Long, ? super V> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(long l, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(long l, Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(long l, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(long l, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(long l, LongFunction<? extends V> longFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsentPartial(long l, Long2ReferenceFunction<? extends V> long2ReferenceFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V merge(long l, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V getOrDefault(Object object, V v) {
            return this.map.getOrDefault(object, v);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V replace(Long l, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Long l, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V putIfAbsent(Long l, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V computeIfAbsent(Long l, Function<? super Long, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V computeIfPresent(Long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V compute(Long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V merge(Long l, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Long)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Long)object, (BiFunction<? super Long, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Long)object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Long)object, (V)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Long)object, (V)object2, (V)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Long)object, (V)object2);
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
    public static class SynchronizedMap<V>
    extends Long2ReferenceFunctions.SynchronizedFunction<V>
    implements Long2ReferenceMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ReferenceMap<V> map;
        protected transient ObjectSet<Long2ReferenceMap.Entry<V>> entries;
        protected transient LongSet keys;
        protected transient ReferenceCollection<V> values;

        protected SynchronizedMap(Long2ReferenceMap<V> long2ReferenceMap, Object object) {
            super(long2ReferenceMap, object);
            this.map = long2ReferenceMap;
        }

        protected SynchronizedMap(Long2ReferenceMap<V> long2ReferenceMap) {
            super(long2ReferenceMap);
            this.map = long2ReferenceMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
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
        public void putAll(Map<? extends Long, ? extends V> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.long2ReferenceEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, V>> entrySet() {
            return this.long2ReferenceEntrySet();
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
        public ReferenceCollection<V> values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return ReferenceCollections.synchronize(this.map.values(), this.sync);
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
        public V getOrDefault(long l, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(l, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Long, ? super V> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V putIfAbsent(long l, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(l, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(long l, Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(l, object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V replace(long l, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(long l, V v, V v2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, v, v2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfAbsent(long l, LongFunction<? extends V> longFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(l, longFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfAbsentPartial(long l, Long2ReferenceFunction<? extends V> long2ReferenceFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(l, long2ReferenceFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfPresent(long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(l, (BiFunction<Long, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V compute(long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(l, (BiFunction<Long, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V merge(long l, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(l, (V)v, (BiFunction<? extends V, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V getOrDefault(Object object, V v) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, v);
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
        public V replace(Long l, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Long l, V v, V v2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, v, v2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V putIfAbsent(Long l, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(l, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V computeIfAbsent(Long l, Function<? super Long, ? extends V> function) {
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
        public V computeIfPresent(Long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(l, (BiFunction<Long, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V compute(Long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(l, (BiFunction<Long, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V merge(Long l, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(l, (V)v, (BiFunction<? extends V, ? extends V, ? extends V>)biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Long)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Long)object, (BiFunction<? super Long, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Long)object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Long)object, (V)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Long)object, (V)object2, (V)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Long)object, (V)object2);
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
    public static class Singleton<V>
    extends Long2ReferenceFunctions.Singleton<V>
    implements Long2ReferenceMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Long2ReferenceMap.Entry<V>> entries;
        protected transient LongSet keys;
        protected transient ReferenceCollection<V> values;

        protected Singleton(long l, V v) {
            super(l, v);
        }

        @Override
        public boolean containsValue(Object object) {
            return this.value == object;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractLong2ReferenceMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, V>> entrySet() {
            return this.long2ReferenceEntrySet();
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ReferenceCollection<V> values() {
            if (this.values == null) {
                this.values = ReferenceSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ (this.value == null ? 0 : System.identityHashCode(this.value));
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
    public static class EmptyMap<V>
    extends Long2ReferenceFunctions.EmptyFunction<V>
    implements Long2ReferenceMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public LongSet keySet() {
            return LongSets.EMPTY_SET;
        }

        @Override
        public ReferenceCollection<V> values() {
            return ReferenceSets.EMPTY_SET;
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

