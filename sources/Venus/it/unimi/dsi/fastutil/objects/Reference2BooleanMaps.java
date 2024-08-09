/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import it.unimi.dsi.fastutil.objects.AbstractReference2BooleanMap;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
import it.unimi.dsi.fastutil.objects.Reference2BooleanFunctions;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
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
import java.util.function.Predicate;

public final class Reference2BooleanMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Reference2BooleanMaps() {
    }

    public static <K> ObjectIterator<Reference2BooleanMap.Entry<K>> fastIterator(Reference2BooleanMap<K> reference2BooleanMap) {
        ObjectSet<Reference2BooleanMap.Entry<K>> objectSet = reference2BooleanMap.reference2BooleanEntrySet();
        return objectSet instanceof Reference2BooleanMap.FastEntrySet ? ((Reference2BooleanMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> void fastForEach(Reference2BooleanMap<K> reference2BooleanMap, Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
        ObjectSet<Reference2BooleanMap.Entry<K>> objectSet = reference2BooleanMap.reference2BooleanEntrySet();
        if (objectSet instanceof Reference2BooleanMap.FastEntrySet) {
            ((Reference2BooleanMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <K> ObjectIterable<Reference2BooleanMap.Entry<K>> fastIterable(Reference2BooleanMap<K> reference2BooleanMap) {
        ObjectSet<Reference2BooleanMap.Entry<K>> objectSet = reference2BooleanMap.reference2BooleanEntrySet();
        return objectSet instanceof Reference2BooleanMap.FastEntrySet ? new ObjectIterable<Reference2BooleanMap.Entry<K>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Reference2BooleanMap.Entry<K>> iterator() {
                return ((Reference2BooleanMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
                ((Reference2BooleanMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <K> Reference2BooleanMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Reference2BooleanMap<K> singleton(K k, boolean bl) {
        return new Singleton<K>(k, bl);
    }

    public static <K> Reference2BooleanMap<K> singleton(K k, Boolean bl) {
        return new Singleton<K>(k, bl);
    }

    public static <K> Reference2BooleanMap<K> synchronize(Reference2BooleanMap<K> reference2BooleanMap) {
        return new SynchronizedMap<K>(reference2BooleanMap);
    }

    public static <K> Reference2BooleanMap<K> synchronize(Reference2BooleanMap<K> reference2BooleanMap, Object object) {
        return new SynchronizedMap<K>(reference2BooleanMap, object);
    }

    public static <K> Reference2BooleanMap<K> unmodifiable(Reference2BooleanMap<K> reference2BooleanMap) {
        return new UnmodifiableMap<K>(reference2BooleanMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<K>
    extends Reference2BooleanFunctions.UnmodifiableFunction<K>
    implements Reference2BooleanMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2BooleanMap<K> map;
        protected transient ObjectSet<Reference2BooleanMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient BooleanCollection values;

        protected UnmodifiableMap(Reference2BooleanMap<K> reference2BooleanMap) {
            super(reference2BooleanMap);
            this.map = reference2BooleanMap;
        }

        @Override
        public boolean containsValue(boolean bl) {
            return this.map.containsValue(bl);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends K, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.reference2BooleanEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Boolean>> entrySet() {
            return this.reference2BooleanEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public BooleanCollection values() {
            if (this.values == null) {
                return BooleanCollections.unmodifiable(this.map.values());
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
        public boolean getOrDefault(Object object, boolean bl) {
            return this.map.getOrDefault(object, bl);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super Boolean> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putIfAbsent(K k, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, boolean bl, boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeBooleanIfAbsent(K k, Predicate<? super K> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeBooleanIfAbsentPartial(K k, Reference2BooleanFunction<? super K> reference2BooleanFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean mergeBoolean(K k, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean getOrDefault(Object object, Boolean bl) {
            return this.map.getOrDefault(object, bl);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean replace(K k, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(K k, Boolean bl, Boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean putIfAbsent(K k, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean computeIfAbsent(K k, Function<? super K, ? extends Boolean> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean computeIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Boolean compute(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean merge(K k, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Boolean)object2, (BiFunction<Boolean, Boolean, Boolean>)biFunction);
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
            return this.replace((K)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Boolean)object2);
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
    extends Reference2BooleanFunctions.SynchronizedFunction<K>
    implements Reference2BooleanMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2BooleanMap<K> map;
        protected transient ObjectSet<Reference2BooleanMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient BooleanCollection values;

        protected SynchronizedMap(Reference2BooleanMap<K> reference2BooleanMap, Object object) {
            super(reference2BooleanMap, object);
            this.map = reference2BooleanMap;
        }

        protected SynchronizedMap(Reference2BooleanMap<K> reference2BooleanMap) {
            super(reference2BooleanMap);
            this.map = reference2BooleanMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(bl);
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
        public void putAll(Map<? extends K, ? extends Boolean> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.reference2BooleanEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Boolean>> entrySet() {
            return this.reference2BooleanEntrySet();
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
        public BooleanCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return BooleanCollections.synchronize(this.map.values(), this.sync);
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
        public boolean getOrDefault(Object object, boolean bl) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super K, ? super Boolean> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean putIfAbsent(K k, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object object, boolean bl) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(object, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(K k, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(K k, boolean bl, boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeBooleanIfAbsent(K k, Predicate<? super K> predicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeBooleanIfAbsent((K)k, predicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeBooleanIfAbsentPartial(K k, Reference2BooleanFunction<? super K> reference2BooleanFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeBooleanIfAbsentPartial((K)k, reference2BooleanFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeBooleanIfPresent((K)k, (BiFunction<? super K, Boolean, Boolean>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeBoolean((K)k, (BiFunction<? super K, Boolean, Boolean>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean mergeBoolean(K k, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.mergeBoolean(k, bl, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean getOrDefault(Object object, Boolean bl) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, bl);
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
        public Boolean replace(K k, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(K k, Boolean bl, Boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean putIfAbsent(K k, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Boolean computeIfAbsent(K k, Function<? super K, ? extends Boolean> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent((K)k, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Boolean computeIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent((K)k, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Boolean compute(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean merge(K k, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(k, bl, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Boolean)object2, (BiFunction<Boolean, Boolean, Boolean>)biFunction);
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
            return this.replace((K)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Boolean)object2);
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
    extends Reference2BooleanFunctions.Singleton<K>
    implements Reference2BooleanMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Reference2BooleanMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient BooleanCollection values;

        protected Singleton(K k, boolean bl) {
            super(k, bl);
        }

        @Override
        public boolean containsValue(boolean bl) {
            return this.value == bl;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Boolean)object == this.value;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractReference2BooleanMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Boolean>> entrySet() {
            return this.reference2BooleanEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public BooleanCollection values() {
            if (this.values == null) {
                this.values = BooleanSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ (this.value ? 1231 : 1237);
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
    extends Reference2BooleanFunctions.EmptyFunction<K>
    implements Reference2BooleanMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(boolean bl) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ReferenceSet<K> keySet() {
            return ReferenceSets.EMPTY_SET;
        }

        @Override
        public BooleanCollection values() {
            return BooleanSets.EMPTY_SET;
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

