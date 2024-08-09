/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.longs.AbstractLong2DoubleMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleFunction;
import it.unimi.dsi.fastutil.longs.Long2DoubleFunctions;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
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
import java.util.function.LongToDoubleFunction;

public final class Long2DoubleMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Long2DoubleMaps() {
    }

    public static ObjectIterator<Long2DoubleMap.Entry> fastIterator(Long2DoubleMap long2DoubleMap) {
        ObjectSet<Long2DoubleMap.Entry> objectSet = long2DoubleMap.long2DoubleEntrySet();
        return objectSet instanceof Long2DoubleMap.FastEntrySet ? ((Long2DoubleMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Long2DoubleMap long2DoubleMap, Consumer<? super Long2DoubleMap.Entry> consumer) {
        ObjectSet<Long2DoubleMap.Entry> objectSet = long2DoubleMap.long2DoubleEntrySet();
        if (objectSet instanceof Long2DoubleMap.FastEntrySet) {
            ((Long2DoubleMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Long2DoubleMap.Entry> fastIterable(Long2DoubleMap long2DoubleMap) {
        ObjectSet<Long2DoubleMap.Entry> objectSet = long2DoubleMap.long2DoubleEntrySet();
        return objectSet instanceof Long2DoubleMap.FastEntrySet ? new ObjectIterable<Long2DoubleMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Long2DoubleMap.Entry> iterator() {
                return ((Long2DoubleMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Long2DoubleMap.Entry> consumer) {
                ((Long2DoubleMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Long2DoubleMap singleton(long l, double d) {
        return new Singleton(l, d);
    }

    public static Long2DoubleMap singleton(Long l, Double d) {
        return new Singleton(l, d);
    }

    public static Long2DoubleMap synchronize(Long2DoubleMap long2DoubleMap) {
        return new SynchronizedMap(long2DoubleMap);
    }

    public static Long2DoubleMap synchronize(Long2DoubleMap long2DoubleMap, Object object) {
        return new SynchronizedMap(long2DoubleMap, object);
    }

    public static Long2DoubleMap unmodifiable(Long2DoubleMap long2DoubleMap) {
        return new UnmodifiableMap(long2DoubleMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Long2DoubleFunctions.UnmodifiableFunction
    implements Long2DoubleMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2DoubleMap map;
        protected transient ObjectSet<Long2DoubleMap.Entry> entries;
        protected transient LongSet keys;
        protected transient DoubleCollection values;

        protected UnmodifiableMap(Long2DoubleMap long2DoubleMap) {
            super(long2DoubleMap);
            this.map = long2DoubleMap;
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
        public void putAll(Map<? extends Long, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2DoubleMap.Entry> long2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.long2DoubleEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Double>> entrySet() {
            return this.long2DoubleEntrySet();
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.unmodifiable(this.map.keySet());
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
        public double getOrDefault(long l, double d) {
            return this.map.getOrDefault(l, d);
        }

        @Override
        public void forEach(BiConsumer<? super Long, ? super Double> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double putIfAbsent(long l, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(long l, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double replace(long l, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(long l, double d, double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsent(long l, LongToDoubleFunction longToDoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsentNullable(long l, LongFunction<? extends Double> longFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsentPartial(long l, Long2DoubleFunction long2DoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfPresent(long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double compute(long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double merge(long l, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
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
        public Double replace(Long l, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Long l, Double d, Double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double putIfAbsent(Long l, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double computeIfAbsent(Long l, Function<? super Long, ? extends Double> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double computeIfPresent(Long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double compute(Long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double merge(Long l, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Long)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Long)object, (BiFunction<? super Long, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Double>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Long)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Long)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Long)object, (Double)object2);
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
    public static class SynchronizedMap
    extends Long2DoubleFunctions.SynchronizedFunction
    implements Long2DoubleMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2DoubleMap map;
        protected transient ObjectSet<Long2DoubleMap.Entry> entries;
        protected transient LongSet keys;
        protected transient DoubleCollection values;

        protected SynchronizedMap(Long2DoubleMap long2DoubleMap, Object object) {
            super(long2DoubleMap, object);
            this.map = long2DoubleMap;
        }

        protected SynchronizedMap(Long2DoubleMap long2DoubleMap) {
            super(long2DoubleMap);
            this.map = long2DoubleMap;
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
        public void putAll(Map<? extends Long, ? extends Double> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Long2DoubleMap.Entry> long2DoubleEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.long2DoubleEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Double>> entrySet() {
            return this.long2DoubleEntrySet();
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
        public double getOrDefault(long l, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Long, ? super Double> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double putIfAbsent(long l, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(long l, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double replace(long l, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(long l, double d, double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsent(long l, LongToDoubleFunction longToDoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(l, longToDoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsentNullable(long l, LongFunction<? extends Double> longFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(l, longFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsentPartial(long l, Long2DoubleFunction long2DoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(l, long2DoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfPresent(long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double compute(long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double merge(long l, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(l, d, biFunction);
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
        public Double replace(Long l, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Long l, Double d, Double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double putIfAbsent(Long l, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(l, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double computeIfAbsent(Long l, Function<? super Long, ? extends Double> function) {
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
        public Double computeIfPresent(Long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
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
        public Double compute(Long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
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
        public Double merge(Long l, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(l, d, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Long)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Long)object, (BiFunction<? super Long, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Double>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Long)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Long)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Long)object, (Double)object2);
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
    public static class Singleton
    extends Long2DoubleFunctions.Singleton
    implements Long2DoubleMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Long2DoubleMap.Entry> entries;
        protected transient LongSet keys;
        protected transient DoubleCollection values;

        protected Singleton(long l, double d) {
            super(l, d);
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
        public void putAll(Map<? extends Long, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2DoubleMap.Entry> long2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractLong2DoubleMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Double>> entrySet() {
            return this.long2DoubleEntrySet();
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.singleton(this.key);
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
            return HashCommon.long2int(this.key) ^ HashCommon.double2int(this.value);
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
    extends Long2DoubleFunctions.EmptyFunction
    implements Long2DoubleMap,
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
        public void putAll(Map<? extends Long, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2DoubleMap.Entry> long2DoubleEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public LongSet keySet() {
            return LongSets.EMPTY_SET;
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

