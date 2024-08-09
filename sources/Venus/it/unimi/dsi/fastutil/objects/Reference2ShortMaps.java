/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2ShortMap;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.Reference2ShortFunction;
import it.unimi.dsi.fastutil.objects.Reference2ShortFunctions;
import it.unimi.dsi.fastutil.objects.Reference2ShortMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSets;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollections;
import it.unimi.dsi.fastutil.shorts.ShortSets;
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

public final class Reference2ShortMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Reference2ShortMaps() {
    }

    public static <K> ObjectIterator<Reference2ShortMap.Entry<K>> fastIterator(Reference2ShortMap<K> reference2ShortMap) {
        ObjectSet<Reference2ShortMap.Entry<K>> objectSet = reference2ShortMap.reference2ShortEntrySet();
        return objectSet instanceof Reference2ShortMap.FastEntrySet ? ((Reference2ShortMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> void fastForEach(Reference2ShortMap<K> reference2ShortMap, Consumer<? super Reference2ShortMap.Entry<K>> consumer) {
        ObjectSet<Reference2ShortMap.Entry<K>> objectSet = reference2ShortMap.reference2ShortEntrySet();
        if (objectSet instanceof Reference2ShortMap.FastEntrySet) {
            ((Reference2ShortMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <K> ObjectIterable<Reference2ShortMap.Entry<K>> fastIterable(Reference2ShortMap<K> reference2ShortMap) {
        ObjectSet<Reference2ShortMap.Entry<K>> objectSet = reference2ShortMap.reference2ShortEntrySet();
        return objectSet instanceof Reference2ShortMap.FastEntrySet ? new ObjectIterable<Reference2ShortMap.Entry<K>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Reference2ShortMap.Entry<K>> iterator() {
                return ((Reference2ShortMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Reference2ShortMap.Entry<K>> consumer) {
                ((Reference2ShortMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <K> Reference2ShortMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Reference2ShortMap<K> singleton(K k, short s) {
        return new Singleton<K>(k, s);
    }

    public static <K> Reference2ShortMap<K> singleton(K k, Short s) {
        return new Singleton<K>(k, s);
    }

    public static <K> Reference2ShortMap<K> synchronize(Reference2ShortMap<K> reference2ShortMap) {
        return new SynchronizedMap<K>(reference2ShortMap);
    }

    public static <K> Reference2ShortMap<K> synchronize(Reference2ShortMap<K> reference2ShortMap, Object object) {
        return new SynchronizedMap<K>(reference2ShortMap, object);
    }

    public static <K> Reference2ShortMap<K> unmodifiable(Reference2ShortMap<K> reference2ShortMap) {
        return new UnmodifiableMap<K>(reference2ShortMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<K>
    extends Reference2ShortFunctions.UnmodifiableFunction<K>
    implements Reference2ShortMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ShortMap<K> map;
        protected transient ObjectSet<Reference2ShortMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient ShortCollection values;

        protected UnmodifiableMap(Reference2ShortMap<K> reference2ShortMap) {
            super(reference2ShortMap);
            this.map = reference2ShortMap;
        }

        @Override
        public boolean containsValue(short s) {
            return this.map.containsValue(s);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends K, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2ShortMap.Entry<K>> reference2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.reference2ShortEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Short>> entrySet() {
            return this.reference2ShortEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ShortCollection values() {
            if (this.values == null) {
                return ShortCollections.unmodifiable(this.map.values());
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
        public short getOrDefault(Object object, short s) {
            return this.map.getOrDefault(object, s);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super Short> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short putIfAbsent(K k, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short replace(K k, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, short s, short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeShortIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeShortIfAbsentPartial(K k, Reference2ShortFunction<? super K> reference2ShortFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short mergeShort(K k, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short getOrDefault(Object object, Short s) {
            return this.map.getOrDefault(object, s);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short replace(K k, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(K k, Short s, Short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short putIfAbsent(K k, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short computeIfAbsent(K k, Function<? super K, ? extends Short> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short computeIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Short compute(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short merge(K k, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Short)object2, (BiFunction<Short, Short, Short>)biFunction);
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
            return this.replace((K)object, (Short)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Short)object2, (Short)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Short)object2);
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
    extends Reference2ShortFunctions.SynchronizedFunction<K>
    implements Reference2ShortMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ShortMap<K> map;
        protected transient ObjectSet<Reference2ShortMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient ShortCollection values;

        protected SynchronizedMap(Reference2ShortMap<K> reference2ShortMap, Object object) {
            super(reference2ShortMap, object);
            this.map = reference2ShortMap;
        }

        protected SynchronizedMap(Reference2ShortMap<K> reference2ShortMap) {
            super(reference2ShortMap);
            this.map = reference2ShortMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(s);
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
        public void putAll(Map<? extends K, ? extends Short> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Reference2ShortMap.Entry<K>> reference2ShortEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.reference2ShortEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Short>> entrySet() {
            return this.reference2ShortEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ReferenceSet<K> keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync);
                }
                return this.keys;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return ShortCollections.synchronize(this.map.values(), this.sync);
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
        public short getOrDefault(Object object, short s) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super K, ? super Short> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short putIfAbsent(K k, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object object, short s) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(object, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short replace(K k, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(K k, short s, short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeShortIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeShortIfAbsent((K)k, toIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeShortIfAbsentPartial(K k, Reference2ShortFunction<? super K> reference2ShortFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeShortIfAbsentPartial((K)k, reference2ShortFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeShortIfPresent((K)k, (BiFunction<? super K, Short, Short>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeShort((K)k, (BiFunction<? super K, Short, Short>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short mergeShort(K k, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.mergeShort(k, s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short getOrDefault(Object object, Short s) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, s);
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
        public Short replace(K k, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(K k, Short s, Short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short putIfAbsent(K k, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Short computeIfAbsent(K k, Function<? super K, ? extends Short> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent((K)k, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Short computeIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent((K)k, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Short compute(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
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
        public Short merge(K k, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(k, s, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Short)object2, (BiFunction<Short, Short, Short>)biFunction);
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
            return this.replace((K)object, (Short)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Short)object2, (Short)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Short)object2);
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
    extends Reference2ShortFunctions.Singleton<K>
    implements Reference2ShortMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Reference2ShortMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient ShortCollection values;

        protected Singleton(K k, short s) {
            super(k, s);
        }

        @Override
        public boolean containsValue(short s) {
            return this.value == s;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Short)object == this.value;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2ShortMap.Entry<K>> reference2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractReference2ShortMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Short>> entrySet() {
            return this.reference2ShortEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ShortCollection values() {
            if (this.values == null) {
                this.values = ShortSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ this.value;
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
    extends Reference2ShortFunctions.EmptyFunction<K>
    implements Reference2ShortMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(short s) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2ShortMap.Entry<K>> reference2ShortEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ReferenceSet<K> keySet() {
            return ReferenceSets.EMPTY_SET;
        }

        @Override
        public ShortCollection values() {
            return ShortSets.EMPTY_SET;
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

