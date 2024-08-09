/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
import it.unimi.dsi.fastutil.doubles.Double2ObjectFunctions;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
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
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public final class Double2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Double2ObjectMaps() {
    }

    public static <V> ObjectIterator<Double2ObjectMap.Entry<V>> fastIterator(Double2ObjectMap<V> double2ObjectMap) {
        ObjectSet<Double2ObjectMap.Entry<V>> objectSet = double2ObjectMap.double2ObjectEntrySet();
        return objectSet instanceof Double2ObjectMap.FastEntrySet ? ((Double2ObjectMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> void fastForEach(Double2ObjectMap<V> double2ObjectMap, Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
        ObjectSet<Double2ObjectMap.Entry<V>> objectSet = double2ObjectMap.double2ObjectEntrySet();
        if (objectSet instanceof Double2ObjectMap.FastEntrySet) {
            ((Double2ObjectMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <V> ObjectIterable<Double2ObjectMap.Entry<V>> fastIterable(Double2ObjectMap<V> double2ObjectMap) {
        ObjectSet<Double2ObjectMap.Entry<V>> objectSet = double2ObjectMap.double2ObjectEntrySet();
        return objectSet instanceof Double2ObjectMap.FastEntrySet ? new ObjectIterable<Double2ObjectMap.Entry<V>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Double2ObjectMap.Entry<V>> iterator() {
                return ((Double2ObjectMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
                ((Double2ObjectMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <V> Double2ObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Double2ObjectMap<V> singleton(double d, V v) {
        return new Singleton<V>(d, v);
    }

    public static <V> Double2ObjectMap<V> singleton(Double d, V v) {
        return new Singleton<V>(d, v);
    }

    public static <V> Double2ObjectMap<V> synchronize(Double2ObjectMap<V> double2ObjectMap) {
        return new SynchronizedMap<V>(double2ObjectMap);
    }

    public static <V> Double2ObjectMap<V> synchronize(Double2ObjectMap<V> double2ObjectMap, Object object) {
        return new SynchronizedMap<V>(double2ObjectMap, object);
    }

    public static <V> Double2ObjectMap<V> unmodifiable(Double2ObjectMap<V> double2ObjectMap) {
        return new UnmodifiableMap<V>(double2ObjectMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<V>
    extends Double2ObjectFunctions.UnmodifiableFunction<V>
    implements Double2ObjectMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ObjectMap<V> map;
        protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
        protected transient DoubleSet keys;
        protected transient ObjectCollection<V> values;

        protected UnmodifiableMap(Double2ObjectMap<V> double2ObjectMap) {
            super(double2ObjectMap);
            this.map = double2ObjectMap;
        }

        @Override
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Double, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.double2ObjectEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ObjectEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                return ObjectCollections.unmodifiable(this.map.values());
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
        public V getOrDefault(double d, V v) {
            return this.map.getOrDefault(d, v);
        }

        @Override
        public void forEach(BiConsumer<? super Double, ? super V> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(double d, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(double d, Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(double d, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(double d, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(double d, DoubleFunction<? extends V> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsentPartial(double d, Double2ObjectFunction<? extends V> double2ObjectFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V merge(double d, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
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
        public V replace(Double d, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Double d, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V putIfAbsent(Double d, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V computeIfAbsent(Double d, Function<? super Double, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V computeIfPresent(Double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V compute(Double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V merge(Double d, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (V)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (V)object2, (V)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (V)object2);
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
    extends Double2ObjectFunctions.SynchronizedFunction<V>
    implements Double2ObjectMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ObjectMap<V> map;
        protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
        protected transient DoubleSet keys;
        protected transient ObjectCollection<V> values;

        protected SynchronizedMap(Double2ObjectMap<V> double2ObjectMap, Object object) {
            super(double2ObjectMap, object);
            this.map = double2ObjectMap;
        }

        protected SynchronizedMap(Double2ObjectMap<V> double2ObjectMap) {
            super(double2ObjectMap);
            this.map = double2ObjectMap;
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
        public void putAll(Map<? extends Double, ? extends V> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.double2ObjectEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ObjectEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DoubleSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync);
                }
                return this.keys;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectCollection<V> values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return ObjectCollections.synchronize(this.map.values(), this.sync);
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
        public V getOrDefault(double d, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(d, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Double, ? super V> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V putIfAbsent(double d, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(double d, Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(d, object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V replace(double d, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(double d, V v, V v2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, v, v2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfAbsent(double d, DoubleFunction<? extends V> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(d, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfAbsentPartial(double d, Double2ObjectFunction<? extends V> double2ObjectFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(d, double2ObjectFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfPresent(double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, (BiFunction<Double, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V compute(double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, (BiFunction<Double, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V merge(double d, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, (V)v, (BiFunction<? extends V, ? extends V, ? extends V>)biFunction);
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
        public V replace(Double d, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Double d, V v, V v2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, v, v2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V putIfAbsent(Double d, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V computeIfAbsent(Double d, Function<? super Double, ? extends V> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(d, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V computeIfPresent(Double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, (BiFunction<Double, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V compute(Double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, (BiFunction<Double, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V merge(Double d, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, (V)v, (BiFunction<? extends V, ? extends V, ? extends V>)biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (V)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (V)object2, (V)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (V)object2);
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
    extends Double2ObjectFunctions.Singleton<V>
    implements Double2ObjectMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
        protected transient DoubleSet keys;
        protected transient ObjectCollection<V> values;

        protected Singleton(double d, V v) {
            super(d, v);
        }

        @Override
        public boolean containsValue(Object object) {
            return Objects.equals(this.value, object);
        }

        @Override
        public void putAll(Map<? extends Double, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractDouble2ObjectMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ObjectEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                this.values = ObjectSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
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
    extends Double2ObjectFunctions.EmptyFunction<V>
    implements Double2ObjectMap<V>,
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
        public void putAll(Map<? extends Double, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public DoubleSet keySet() {
            return DoubleSets.EMPTY_SET;
        }

        @Override
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
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

