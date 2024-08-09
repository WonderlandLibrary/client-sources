/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntFunction;
import it.unimi.dsi.fastutil.objects.Object2IntFunctions;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
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
import java.util.function.ToIntFunction;

public final class Object2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Object2IntMaps() {
    }

    public static <K> ObjectIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap<K> object2IntMap) {
        ObjectSet<Object2IntMap.Entry<K>> objectSet = object2IntMap.object2IntEntrySet();
        return objectSet instanceof Object2IntMap.FastEntrySet ? ((Object2IntMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> void fastForEach(Object2IntMap<K> object2IntMap, Consumer<? super Object2IntMap.Entry<K>> consumer) {
        ObjectSet<Object2IntMap.Entry<K>> objectSet = object2IntMap.object2IntEntrySet();
        if (objectSet instanceof Object2IntMap.FastEntrySet) {
            ((Object2IntMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <K> ObjectIterable<Object2IntMap.Entry<K>> fastIterable(Object2IntMap<K> object2IntMap) {
        ObjectSet<Object2IntMap.Entry<K>> objectSet = object2IntMap.object2IntEntrySet();
        return objectSet instanceof Object2IntMap.FastEntrySet ? new ObjectIterable<Object2IntMap.Entry<K>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
                return ((Object2IntMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
                ((Object2IntMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <K> Object2IntMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Object2IntMap<K> singleton(K k, int n) {
        return new Singleton<K>(k, n);
    }

    public static <K> Object2IntMap<K> singleton(K k, Integer n) {
        return new Singleton<K>(k, n);
    }

    public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> object2IntMap) {
        return new SynchronizedMap<K>(object2IntMap);
    }

    public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> object2IntMap, Object object) {
        return new SynchronizedMap<K>(object2IntMap, object);
    }

    public static <K> Object2IntMap<K> unmodifiable(Object2IntMap<K> object2IntMap) {
        return new UnmodifiableMap<K>(object2IntMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<K>
    extends Object2IntFunctions.UnmodifiableFunction<K>
    implements Object2IntMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2IntMap<K> map;
        protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
        protected transient ObjectSet<K> keys;
        protected transient IntCollection values;

        protected UnmodifiableMap(Object2IntMap<K> object2IntMap) {
            super(object2IntMap);
            this.map = object2IntMap;
        }

        @Override
        public boolean containsValue(int n) {
            return this.map.containsValue(n);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends K, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.object2IntEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Integer>> entrySet() {
            return this.object2IntEntrySet();
        }

        @Override
        public ObjectSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public IntCollection values() {
            if (this.values == null) {
                return IntCollections.unmodifiable(this.map.values());
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
        public int getOrDefault(Object object, int n) {
            return this.map.getOrDefault(object, n);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super Integer> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int putIfAbsent(K k, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int replace(K k, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIntIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIntIfAbsentPartial(K k, Object2IntFunction<? super K> object2IntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int mergeInt(K k, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer getOrDefault(Object object, Integer n) {
            return this.map.getOrDefault(object, n);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer replace(K k, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(K k, Integer n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer putIfAbsent(K k, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer computeIfAbsent(K k, Function<? super K, ? extends Integer> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer computeIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer compute(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer merge(K k, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Integer)object2, (BiFunction<Integer, Integer, Integer>)biFunction);
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
            return this.replace((K)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Integer)object2);
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
    extends Object2IntFunctions.SynchronizedFunction<K>
    implements Object2IntMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2IntMap<K> map;
        protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
        protected transient ObjectSet<K> keys;
        protected transient IntCollection values;

        protected SynchronizedMap(Object2IntMap<K> object2IntMap, Object object) {
            super(object2IntMap, object);
            this.map = object2IntMap;
        }

        protected SynchronizedMap(Object2IntMap<K> object2IntMap) {
            super(object2IntMap);
            this.map = object2IntMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(n);
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
        public void putAll(Map<? extends K, ? extends Integer> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.object2IntEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Integer>> entrySet() {
            return this.object2IntEntrySet();
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
        public IntCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return IntCollections.synchronize(this.map.values(), this.sync);
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
        public int getOrDefault(Object object, int n) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super K, ? super Integer> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int putIfAbsent(K k, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object object, int n) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(object, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int replace(K k, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(K k, int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIntIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIntIfAbsent((K)k, toIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIntIfAbsentPartial(K k, Object2IntFunction<? super K> object2IntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIntIfAbsentPartial((K)k, object2IntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIntIfPresent((K)k, (BiFunction<? super K, Integer, Integer>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeInt((K)k, (BiFunction<? super K, Integer, Integer>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int mergeInt(K k, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.mergeInt(k, n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer getOrDefault(Object object, Integer n) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, n);
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
        public Integer replace(K k, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(K k, Integer n, Integer n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer putIfAbsent(K k, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Integer computeIfAbsent(K k, Function<? super K, ? extends Integer> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent((K)k, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Integer computeIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent((K)k, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Integer compute(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer merge(K k, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(k, n, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Integer)object2, (BiFunction<Integer, Integer, Integer>)biFunction);
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
            return this.replace((K)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Integer)object2);
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
    extends Object2IntFunctions.Singleton<K>
    implements Object2IntMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
        protected transient ObjectSet<K> keys;
        protected transient IntCollection values;

        protected Singleton(K k, int n) {
            super(k, n);
        }

        @Override
        public boolean containsValue(int n) {
            return this.value == n;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Integer)object == this.value;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractObject2IntMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Integer>> entrySet() {
            return this.object2IntEntrySet();
        }

        @Override
        public ObjectSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public IntCollection values() {
            if (this.values == null) {
                this.values = IntSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
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
    extends Object2IntFunctions.EmptyFunction<K>
    implements Object2IntMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(int n) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public IntCollection values() {
            return IntSets.EMPTY_SET;
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

