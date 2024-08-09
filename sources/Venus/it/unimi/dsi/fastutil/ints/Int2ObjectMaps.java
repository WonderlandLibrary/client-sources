/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunctions;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
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
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Int2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Int2ObjectMaps() {
    }

    public static <V> ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap<V> int2ObjectMap) {
        ObjectSet<Int2ObjectMap.Entry<V>> objectSet = int2ObjectMap.int2ObjectEntrySet();
        return objectSet instanceof Int2ObjectMap.FastEntrySet ? ((Int2ObjectMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> void fastForEach(Int2ObjectMap<V> int2ObjectMap, Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
        ObjectSet<Int2ObjectMap.Entry<V>> objectSet = int2ObjectMap.int2ObjectEntrySet();
        if (objectSet instanceof Int2ObjectMap.FastEntrySet) {
            ((Int2ObjectMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <V> ObjectIterable<Int2ObjectMap.Entry<V>> fastIterable(Int2ObjectMap<V> int2ObjectMap) {
        ObjectSet<Int2ObjectMap.Entry<V>> objectSet = int2ObjectMap.int2ObjectEntrySet();
        return objectSet instanceof Int2ObjectMap.FastEntrySet ? new ObjectIterable<Int2ObjectMap.Entry<V>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
                return ((Int2ObjectMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
                ((Int2ObjectMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <V> Int2ObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Int2ObjectMap<V> singleton(int n, V v) {
        return new Singleton<V>(n, v);
    }

    public static <V> Int2ObjectMap<V> singleton(Integer n, V v) {
        return new Singleton<V>(n, v);
    }

    public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> int2ObjectMap) {
        return new SynchronizedMap<V>(int2ObjectMap);
    }

    public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> int2ObjectMap, Object object) {
        return new SynchronizedMap<V>(int2ObjectMap, object);
    }

    public static <V> Int2ObjectMap<V> unmodifiable(Int2ObjectMap<V> int2ObjectMap) {
        return new UnmodifiableMap<V>(int2ObjectMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<V>
    extends Int2ObjectFunctions.UnmodifiableFunction<V>
    implements Int2ObjectMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ObjectMap<V> map;
        protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
        protected transient IntSet keys;
        protected transient ObjectCollection<V> values;

        protected UnmodifiableMap(Int2ObjectMap<V> int2ObjectMap) {
            super(int2ObjectMap);
            this.map = int2ObjectMap;
        }

        @Override
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.int2ObjectEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, V>> entrySet() {
            return this.int2ObjectEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.unmodifiable(this.map.keySet());
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
        public V getOrDefault(int n, V v) {
            return this.map.getOrDefault(n, v);
        }

        @Override
        public void forEach(BiConsumer<? super Integer, ? super V> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(int n, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(int n, Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(int n, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(int n, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(int n, IntFunction<? extends V> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsentPartial(int n, Int2ObjectFunction<? extends V> int2ObjectFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(int n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(int n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V merge(int n, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
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
        public V replace(Integer n, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Integer n, V v, V v2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V putIfAbsent(Integer n, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V computeIfAbsent(Integer n, Function<? super Integer, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V computeIfPresent(Integer n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V compute(Integer n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V merge(Integer n, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Integer)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Integer)object, (BiFunction<? super Integer, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Integer)object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Integer)object, (V)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Integer)object, (V)object2, (V)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Integer)object, (V)object2);
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
    extends Int2ObjectFunctions.SynchronizedFunction<V>
    implements Int2ObjectMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ObjectMap<V> map;
        protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
        protected transient IntSet keys;
        protected transient ObjectCollection<V> values;

        protected SynchronizedMap(Int2ObjectMap<V> int2ObjectMap, Object object) {
            super(int2ObjectMap, object);
            this.map = int2ObjectMap;
        }

        protected SynchronizedMap(Int2ObjectMap<V> int2ObjectMap) {
            super(int2ObjectMap);
            this.map = int2ObjectMap;
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
        public void putAll(Map<? extends Integer, ? extends V> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.int2ObjectEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, V>> entrySet() {
            return this.int2ObjectEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public IntSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = IntSets.synchronize(this.map.keySet(), this.sync);
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
        public V getOrDefault(int n, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(n, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Integer, ? super V> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V putIfAbsent(int n, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(n, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(int n, Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(n, object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V replace(int n, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(int n, V v, V v2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, v, v2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfAbsent(int n, IntFunction<? extends V> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(n, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfAbsentPartial(int n, Int2ObjectFunction<? extends V> int2ObjectFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(n, int2ObjectFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V computeIfPresent(int n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(n, (BiFunction<Integer, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V compute(int n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(n, (BiFunction<Integer, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V merge(int n, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(n, (V)v, (BiFunction<? extends V, ? extends V, ? extends V>)biFunction);
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
        public V replace(Integer n, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Integer n, V v, V v2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, v, v2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V putIfAbsent(Integer n, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(n, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V computeIfAbsent(Integer n, Function<? super Integer, ? extends V> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(n, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V computeIfPresent(Integer n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(n, (BiFunction<Integer, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V compute(Integer n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(n, (BiFunction<Integer, ? extends V, ? extends V>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V merge(Integer n, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(n, (V)v, (BiFunction<? extends V, ? extends V, ? extends V>)biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Integer)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Integer)object, (BiFunction<? super Integer, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super V, ? extends V>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Integer)object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Integer)object, (V)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Integer)object, (V)object2, (V)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Integer)object, (V)object2);
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
    extends Int2ObjectFunctions.Singleton<V>
    implements Int2ObjectMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
        protected transient IntSet keys;
        protected transient ObjectCollection<V> values;

        protected Singleton(int n, V v) {
            super(n, v);
        }

        @Override
        public boolean containsValue(Object object) {
            return Objects.equals(this.value, object);
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2ObjectMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, V>> entrySet() {
            return this.int2ObjectEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
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
            return this.key ^ (this.value == null ? 0 : this.value.hashCode());
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
    extends Int2ObjectFunctions.EmptyFunction<V>
    implements Int2ObjectMap<V>,
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
        public void putAll(Map<? extends Integer, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
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

