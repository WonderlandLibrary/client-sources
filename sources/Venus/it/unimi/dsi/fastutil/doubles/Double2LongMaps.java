/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2LongMap;
import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
import it.unimi.dsi.fastutil.doubles.Double2LongFunctions;
import it.unimi.dsi.fastutil.doubles.Double2LongMap;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
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
import java.util.function.DoubleFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;

public final class Double2LongMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Double2LongMaps() {
    }

    public static ObjectIterator<Double2LongMap.Entry> fastIterator(Double2LongMap double2LongMap) {
        ObjectSet<Double2LongMap.Entry> objectSet = double2LongMap.double2LongEntrySet();
        return objectSet instanceof Double2LongMap.FastEntrySet ? ((Double2LongMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Double2LongMap double2LongMap, Consumer<? super Double2LongMap.Entry> consumer) {
        ObjectSet<Double2LongMap.Entry> objectSet = double2LongMap.double2LongEntrySet();
        if (objectSet instanceof Double2LongMap.FastEntrySet) {
            ((Double2LongMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Double2LongMap.Entry> fastIterable(Double2LongMap double2LongMap) {
        ObjectSet<Double2LongMap.Entry> objectSet = double2LongMap.double2LongEntrySet();
        return objectSet instanceof Double2LongMap.FastEntrySet ? new ObjectIterable<Double2LongMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Double2LongMap.Entry> iterator() {
                return ((Double2LongMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Double2LongMap.Entry> consumer) {
                ((Double2LongMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Double2LongMap singleton(double d, long l) {
        return new Singleton(d, l);
    }

    public static Double2LongMap singleton(Double d, Long l) {
        return new Singleton(d, l);
    }

    public static Double2LongMap synchronize(Double2LongMap double2LongMap) {
        return new SynchronizedMap(double2LongMap);
    }

    public static Double2LongMap synchronize(Double2LongMap double2LongMap, Object object) {
        return new SynchronizedMap(double2LongMap, object);
    }

    public static Double2LongMap unmodifiable(Double2LongMap double2LongMap) {
        return new UnmodifiableMap(double2LongMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Double2LongFunctions.UnmodifiableFunction
    implements Double2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2LongMap map;
        protected transient ObjectSet<Double2LongMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient LongCollection values;

        protected UnmodifiableMap(Double2LongMap double2LongMap) {
            super(double2LongMap);
            this.map = double2LongMap;
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
        public void putAll(Map<? extends Double, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2LongMap.Entry> double2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.double2LongEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Long>> entrySet() {
            return this.double2LongEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.unmodifiable(this.map.keySet());
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
        public long getOrDefault(double d, long l) {
            return this.map.getOrDefault(d, l);
        }

        @Override
        public void forEach(BiConsumer<? super Double, ? super Long> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long putIfAbsent(double d, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(double d, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long replace(double d, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(double d, long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsent(double d, DoubleToLongFunction doubleToLongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsentNullable(double d, DoubleFunction<? extends Long> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsentPartial(double d, Double2LongFunction double2LongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfPresent(double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long compute(double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long merge(double d, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
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
        public Long replace(Double d, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Double d, Long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long putIfAbsent(Double d, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long computeIfAbsent(Double d, Function<? super Double, ? extends Long> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long computeIfPresent(Double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long compute(Double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long merge(Double d, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Long>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Long)object2);
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
    public static class SynchronizedMap
    extends Double2LongFunctions.SynchronizedFunction
    implements Double2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2LongMap map;
        protected transient ObjectSet<Double2LongMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient LongCollection values;

        protected SynchronizedMap(Double2LongMap double2LongMap, Object object) {
            super(double2LongMap, object);
            this.map = double2LongMap;
        }

        protected SynchronizedMap(Double2LongMap double2LongMap) {
            super(double2LongMap);
            this.map = double2LongMap;
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
        public void putAll(Map<? extends Double, ? extends Long> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Double2LongMap.Entry> double2LongEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.double2LongEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Long>> entrySet() {
            return this.double2LongEntrySet();
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
        public long getOrDefault(double d, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(d, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Double, ? super Long> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long putIfAbsent(double d, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(double d, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(d, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long replace(double d, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(double d, long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsent(double d, DoubleToLongFunction doubleToLongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(d, doubleToLongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsentNullable(double d, DoubleFunction<? extends Long> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(d, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsentPartial(double d, Double2LongFunction double2LongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(d, double2LongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfPresent(double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long compute(double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long merge(double d, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, l, biFunction);
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
        public Long replace(Double d, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Double d, Long l, Long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long putIfAbsent(Double d, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long computeIfAbsent(Double d, Function<? super Double, ? extends Long> function) {
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
        public Long computeIfPresent(Double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long compute(Double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long merge(Double d, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, l, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Long>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Long)object2);
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
    public static class Singleton
    extends Double2LongFunctions.Singleton
    implements Double2LongMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Double2LongMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient LongCollection values;

        protected Singleton(double d, long l) {
            super(d, l);
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
        public void putAll(Map<? extends Double, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2LongMap.Entry> double2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractDouble2LongMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Long>> entrySet() {
            return this.double2LongEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.singleton(this.key);
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
            return HashCommon.double2int(this.key) ^ HashCommon.long2int(this.value);
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
    extends Double2LongFunctions.EmptyFunction
    implements Double2LongMap,
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
        public void putAll(Map<? extends Double, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2LongMap.Entry> double2LongEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public DoubleSet keySet() {
            return DoubleSets.EMPTY_SET;
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

