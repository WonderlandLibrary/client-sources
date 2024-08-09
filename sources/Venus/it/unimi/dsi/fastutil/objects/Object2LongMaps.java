/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.AbstractObject2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongFunction;
import it.unimi.dsi.fastutil.objects.Object2LongFunctions;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
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
import java.util.function.ToLongFunction;

public final class Object2LongMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Object2LongMaps() {
    }

    public static <K> ObjectIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap<K> object2LongMap) {
        ObjectSet<Object2LongMap.Entry<K>> objectSet = object2LongMap.object2LongEntrySet();
        return objectSet instanceof Object2LongMap.FastEntrySet ? ((Object2LongMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> void fastForEach(Object2LongMap<K> object2LongMap, Consumer<? super Object2LongMap.Entry<K>> consumer) {
        ObjectSet<Object2LongMap.Entry<K>> objectSet = object2LongMap.object2LongEntrySet();
        if (objectSet instanceof Object2LongMap.FastEntrySet) {
            ((Object2LongMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <K> ObjectIterable<Object2LongMap.Entry<K>> fastIterable(Object2LongMap<K> object2LongMap) {
        ObjectSet<Object2LongMap.Entry<K>> objectSet = object2LongMap.object2LongEntrySet();
        return objectSet instanceof Object2LongMap.FastEntrySet ? new ObjectIterable<Object2LongMap.Entry<K>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Object2LongMap.Entry<K>> iterator() {
                return ((Object2LongMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
                ((Object2LongMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <K> Object2LongMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Object2LongMap<K> singleton(K k, long l) {
        return new Singleton<K>(k, l);
    }

    public static <K> Object2LongMap<K> singleton(K k, Long l) {
        return new Singleton<K>(k, l);
    }

    public static <K> Object2LongMap<K> synchronize(Object2LongMap<K> object2LongMap) {
        return new SynchronizedMap<K>(object2LongMap);
    }

    public static <K> Object2LongMap<K> synchronize(Object2LongMap<K> object2LongMap, Object object) {
        return new SynchronizedMap<K>(object2LongMap, object);
    }

    public static <K> Object2LongMap<K> unmodifiable(Object2LongMap<K> object2LongMap) {
        return new UnmodifiableMap<K>(object2LongMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<K>
    extends Object2LongFunctions.UnmodifiableFunction<K>
    implements Object2LongMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2LongMap<K> map;
        protected transient ObjectSet<Object2LongMap.Entry<K>> entries;
        protected transient ObjectSet<K> keys;
        protected transient LongCollection values;

        protected UnmodifiableMap(Object2LongMap<K> object2LongMap) {
            super(object2LongMap);
            this.map = object2LongMap;
        }

        @Override
        public boolean containsValue(long l) {
            return this.map.containsValue(l);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends K, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.object2LongEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Long>> entrySet() {
            return this.object2LongEntrySet();
        }

        @Override
        public ObjectSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public LongCollection values() {
            if (this.values == null) {
                return LongCollections.unmodifiable(this.map.values());
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
        public long getOrDefault(Object object, long l) {
            return this.map.getOrDefault(object, l);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super Long> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long putIfAbsent(K k, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long replace(K k, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeLongIfAbsent(K k, ToLongFunction<? super K> toLongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeLongIfAbsentPartial(K k, Object2LongFunction<? super K> object2LongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long mergeLong(K k, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long getOrDefault(Object object, Long l) {
            return this.map.getOrDefault(object, l);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long replace(K k, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(K k, Long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long putIfAbsent(K k, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long computeIfAbsent(K k, Function<? super K, ? extends Long> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long computeIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long compute(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long merge(K k, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Long)object2, (BiFunction<Long, Long, Long>)biFunction);
        }

        @Override
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute(object, biFunction);
        }

        @Override
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent(object, biFunction);
        }

        @Override
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent(object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((K)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Long)object2);
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
    public static class SynchronizedMap<K>
    extends Object2LongFunctions.SynchronizedFunction<K>
    implements Object2LongMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2LongMap<K> map;
        protected transient ObjectSet<Object2LongMap.Entry<K>> entries;
        protected transient ObjectSet<K> keys;
        protected transient LongCollection values;

        protected SynchronizedMap(Object2LongMap<K> object2LongMap, Object object) {
            super(object2LongMap, object);
            this.map = object2LongMap;
        }

        protected SynchronizedMap(Object2LongMap<K> object2LongMap) {
            super(object2LongMap);
            this.map = object2LongMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(l);
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
        public void putAll(Map<? extends K, ? extends Long> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.object2LongEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Long>> entrySet() {
            return this.object2LongEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<K> keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync);
                }
                return this.keys;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return LongCollections.synchronize(this.map.values(), this.sync);
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
        public long getOrDefault(Object object, long l) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super K, ? super Long> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long putIfAbsent(K k, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object object, long l) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(object, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long replace(K k, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(K k, long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeLongIfAbsent(K k, ToLongFunction<? super K> toLongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeLongIfAbsent((K)k, toLongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeLongIfAbsentPartial(K k, Object2LongFunction<? super K> object2LongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeLongIfAbsentPartial((K)k, object2LongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeLongIfPresent((K)k, (BiFunction<? super K, Long, Long>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeLong((K)k, (BiFunction<? super K, Long, Long>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long mergeLong(K k, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.mergeLong(k, l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long getOrDefault(Object object, Long l) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, l);
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
        public Long replace(K k, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(K k, Long l, Long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long putIfAbsent(K k, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Long computeIfAbsent(K k, Function<? super K, ? extends Long> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent((K)k, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Long computeIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent((K)k, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Long compute(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute((K)k, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long merge(K k, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(k, l, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Long)object2, (BiFunction<Long, Long, Long>)biFunction);
        }

        @Override
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute(object, biFunction);
        }

        @Override
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent(object, biFunction);
        }

        @Override
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent(object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((K)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Long)object2);
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
    public static class Singleton<K>
    extends Object2LongFunctions.Singleton<K>
    implements Object2LongMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Object2LongMap.Entry<K>> entries;
        protected transient ObjectSet<K> keys;
        protected transient LongCollection values;

        protected Singleton(K k, long l) {
            super(k, l);
        }

        @Override
        public boolean containsValue(long l) {
            return this.value == l;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Long)object == this.value;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractObject2LongMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Long>> entrySet() {
            return this.object2LongEntrySet();
        }

        @Override
        public ObjectSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public LongCollection values() {
            if (this.values == null) {
                this.values = LongSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ HashCommon.long2int(this.value);
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
    public static class EmptyMap<K>
    extends Object2LongFunctions.EmptyFunction<K>
    implements Object2LongMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(long l) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Object2LongMap.Entry<K>> object2LongEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public LongCollection values() {
            return LongSets.EMPTY_SET;
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

