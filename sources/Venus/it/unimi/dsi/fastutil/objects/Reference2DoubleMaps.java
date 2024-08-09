/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.objects.AbstractReference2DoubleMap;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.Reference2DoubleFunction;
import it.unimi.dsi.fastutil.objects.Reference2DoubleFunctions;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;
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
import java.util.function.ToDoubleFunction;

public final class Reference2DoubleMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Reference2DoubleMaps() {
    }

    public static <K> ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator(Reference2DoubleMap<K> reference2DoubleMap) {
        ObjectSet<Reference2DoubleMap.Entry<K>> objectSet = reference2DoubleMap.reference2DoubleEntrySet();
        return objectSet instanceof Reference2DoubleMap.FastEntrySet ? ((Reference2DoubleMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> void fastForEach(Reference2DoubleMap<K> reference2DoubleMap, Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
        ObjectSet<Reference2DoubleMap.Entry<K>> objectSet = reference2DoubleMap.reference2DoubleEntrySet();
        if (objectSet instanceof Reference2DoubleMap.FastEntrySet) {
            ((Reference2DoubleMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <K> ObjectIterable<Reference2DoubleMap.Entry<K>> fastIterable(Reference2DoubleMap<K> reference2DoubleMap) {
        ObjectSet<Reference2DoubleMap.Entry<K>> objectSet = reference2DoubleMap.reference2DoubleEntrySet();
        return objectSet instanceof Reference2DoubleMap.FastEntrySet ? new ObjectIterable<Reference2DoubleMap.Entry<K>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Reference2DoubleMap.Entry<K>> iterator() {
                return ((Reference2DoubleMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
                ((Reference2DoubleMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <K> Reference2DoubleMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Reference2DoubleMap<K> singleton(K k, double d) {
        return new Singleton<K>(k, d);
    }

    public static <K> Reference2DoubleMap<K> singleton(K k, Double d) {
        return new Singleton<K>(k, d);
    }

    public static <K> Reference2DoubleMap<K> synchronize(Reference2DoubleMap<K> reference2DoubleMap) {
        return new SynchronizedMap<K>(reference2DoubleMap);
    }

    public static <K> Reference2DoubleMap<K> synchronize(Reference2DoubleMap<K> reference2DoubleMap, Object object) {
        return new SynchronizedMap<K>(reference2DoubleMap, object);
    }

    public static <K> Reference2DoubleMap<K> unmodifiable(Reference2DoubleMap<K> reference2DoubleMap) {
        return new UnmodifiableMap<K>(reference2DoubleMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<K>
    extends Reference2DoubleFunctions.UnmodifiableFunction<K>
    implements Reference2DoubleMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2DoubleMap<K> map;
        protected transient ObjectSet<Reference2DoubleMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient DoubleCollection values;

        protected UnmodifiableMap(Reference2DoubleMap<K> reference2DoubleMap) {
            super(reference2DoubleMap);
            this.map = reference2DoubleMap;
        }

        @Override
        public boolean containsValue(double d) {
            return this.map.containsValue(d);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends K, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.reference2DoubleEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Double>> entrySet() {
            return this.reference2DoubleEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public DoubleCollection values() {
            if (this.values == null) {
                return DoubleCollections.unmodifiable(this.map.values());
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
        public double getOrDefault(Object object, double d) {
            return this.map.getOrDefault(object, d);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super Double> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double putIfAbsent(K k, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double replace(K k, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, double d, double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> toDoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeDoubleIfAbsentPartial(K k, Reference2DoubleFunction<? super K> reference2DoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double mergeDouble(K k, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double getOrDefault(Object object, Double d) {
            return this.map.getOrDefault(object, d);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double replace(K k, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(K k, Double d, Double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double putIfAbsent(K k, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Double computeIfAbsent(K k, Function<? super K, ? extends Double> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Double computeIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Double compute(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double merge(K k, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Double)object2, (BiFunction<Double, Double, Double>)biFunction);
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
            return this.replace((K)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Double)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Double)object2);
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
    extends Reference2DoubleFunctions.SynchronizedFunction<K>
    implements Reference2DoubleMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2DoubleMap<K> map;
        protected transient ObjectSet<Reference2DoubleMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient DoubleCollection values;

        protected SynchronizedMap(Reference2DoubleMap<K> reference2DoubleMap, Object object) {
            super(reference2DoubleMap, object);
            this.map = reference2DoubleMap;
        }

        protected SynchronizedMap(Reference2DoubleMap<K> reference2DoubleMap) {
            super(reference2DoubleMap);
            this.map = reference2DoubleMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(d);
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
        public void putAll(Map<? extends K, ? extends Double> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.reference2DoubleEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Double>> entrySet() {
            return this.reference2DoubleEntrySet();
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
        public DoubleCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return DoubleCollections.synchronize(this.map.values(), this.sync);
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
        public double getOrDefault(Object object, double d) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super K, ? super Double> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double putIfAbsent(K k, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object object, double d) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(object, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double replace(K k, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(K k, double d, double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> toDoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeDoubleIfAbsent((K)k, toDoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeDoubleIfAbsentPartial(K k, Reference2DoubleFunction<? super K> reference2DoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeDoubleIfAbsentPartial((K)k, reference2DoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeDoubleIfPresent((K)k, (BiFunction<? super K, Double, Double>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeDouble((K)k, (BiFunction<? super K, Double, Double>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double mergeDouble(K k, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.mergeDouble(k, d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double getOrDefault(Object object, Double d) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, d);
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
        public Double replace(K k, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(K k, Double d, Double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double putIfAbsent(K k, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Double computeIfAbsent(K k, Function<? super K, ? extends Double> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent((K)k, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Double computeIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent((K)k, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Double compute(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
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
        public Double merge(K k, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(k, d, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Double)object2, (BiFunction<Double, Double, Double>)biFunction);
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
            return this.replace((K)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Double)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Double)object2);
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
    extends Reference2DoubleFunctions.Singleton<K>
    implements Reference2DoubleMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Reference2DoubleMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient DoubleCollection values;

        protected Singleton(K k, double d) {
            super(k, d);
        }

        @Override
        public boolean containsValue(double d) {
            return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(d);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return Double.doubleToLongBits((Double)object) == Double.doubleToLongBits(this.value);
        }

        @Override
        public void putAll(Map<? extends K, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractReference2DoubleMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Double>> entrySet() {
            return this.reference2DoubleEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public DoubleCollection values() {
            if (this.values == null) {
                this.values = DoubleSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ HashCommon.double2int(this.value);
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
    extends Reference2DoubleFunctions.EmptyFunction<K>
    implements Reference2DoubleMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(double d) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ReferenceSet<K> keySet() {
            return ReferenceSets.EMPTY_SET;
        }

        @Override
        public DoubleCollection values() {
            return DoubleSets.EMPTY_SET;
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

