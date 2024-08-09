/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortMap;
import it.unimi.dsi.fastutil.doubles.Double2ShortFunction;
import it.unimi.dsi.fastutil.doubles.Double2ShortFunctions;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
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
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public final class Double2ShortMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Double2ShortMaps() {
    }

    public static ObjectIterator<Double2ShortMap.Entry> fastIterator(Double2ShortMap double2ShortMap) {
        ObjectSet<Double2ShortMap.Entry> objectSet = double2ShortMap.double2ShortEntrySet();
        return objectSet instanceof Double2ShortMap.FastEntrySet ? ((Double2ShortMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Double2ShortMap double2ShortMap, Consumer<? super Double2ShortMap.Entry> consumer) {
        ObjectSet<Double2ShortMap.Entry> objectSet = double2ShortMap.double2ShortEntrySet();
        if (objectSet instanceof Double2ShortMap.FastEntrySet) {
            ((Double2ShortMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Double2ShortMap.Entry> fastIterable(Double2ShortMap double2ShortMap) {
        ObjectSet<Double2ShortMap.Entry> objectSet = double2ShortMap.double2ShortEntrySet();
        return objectSet instanceof Double2ShortMap.FastEntrySet ? new ObjectIterable<Double2ShortMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Double2ShortMap.Entry> iterator() {
                return ((Double2ShortMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Double2ShortMap.Entry> consumer) {
                ((Double2ShortMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Double2ShortMap singleton(double d, short s) {
        return new Singleton(d, s);
    }

    public static Double2ShortMap singleton(Double d, Short s) {
        return new Singleton(d, s);
    }

    public static Double2ShortMap synchronize(Double2ShortMap double2ShortMap) {
        return new SynchronizedMap(double2ShortMap);
    }

    public static Double2ShortMap synchronize(Double2ShortMap double2ShortMap, Object object) {
        return new SynchronizedMap(double2ShortMap, object);
    }

    public static Double2ShortMap unmodifiable(Double2ShortMap double2ShortMap) {
        return new UnmodifiableMap(double2ShortMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Double2ShortFunctions.UnmodifiableFunction
    implements Double2ShortMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ShortMap map;
        protected transient ObjectSet<Double2ShortMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient ShortCollection values;

        protected UnmodifiableMap(Double2ShortMap double2ShortMap) {
            super(double2ShortMap);
            this.map = double2ShortMap;
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
        public void putAll(Map<? extends Double, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ShortMap.Entry> double2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.double2ShortEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Short>> entrySet() {
            return this.double2ShortEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.unmodifiable(this.map.keySet());
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
        public short getOrDefault(double d, short s) {
            return this.map.getOrDefault(d, s);
        }

        @Override
        public void forEach(BiConsumer<? super Double, ? super Short> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short putIfAbsent(double d, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(double d, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short replace(double d, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(double d, short s, short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsentNullable(double d, DoubleFunction<? extends Short> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsentPartial(double d, Double2ShortFunction double2ShortFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfPresent(double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short compute(double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short merge(double d, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
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
        public Short replace(Double d, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Double d, Short s, Short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short putIfAbsent(Double d, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short computeIfAbsent(Double d, Function<? super Double, ? extends Short> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short computeIfPresent(Double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short compute(Double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short merge(Double d, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Short>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Short)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Short)object2, (Short)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Short)object2);
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
    public static class SynchronizedMap
    extends Double2ShortFunctions.SynchronizedFunction
    implements Double2ShortMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ShortMap map;
        protected transient ObjectSet<Double2ShortMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient ShortCollection values;

        protected SynchronizedMap(Double2ShortMap double2ShortMap, Object object) {
            super(double2ShortMap, object);
            this.map = double2ShortMap;
        }

        protected SynchronizedMap(Double2ShortMap double2ShortMap) {
            super(double2ShortMap);
            this.map = double2ShortMap;
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
        public void putAll(Map<? extends Double, ? extends Short> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Double2ShortMap.Entry> double2ShortEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.double2ShortEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Short>> entrySet() {
            return this.double2ShortEntrySet();
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
        public short getOrDefault(double d, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(d, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Double, ? super Short> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short putIfAbsent(double d, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(double d, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(d, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short replace(double d, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(double d, short s, short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(d, doubleToIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsentNullable(double d, DoubleFunction<? extends Short> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(d, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsentPartial(double d, Double2ShortFunction double2ShortFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(d, double2ShortFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfPresent(double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short compute(double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short merge(double d, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, s, biFunction);
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
        public Short replace(Double d, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Double d, Short s, Short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short putIfAbsent(Double d, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short computeIfAbsent(Double d, Function<? super Double, ? extends Short> function) {
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
        public Short computeIfPresent(Double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
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
        public Short compute(Double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
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
        public Short merge(Double d, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, s, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Short>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Short)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Short)object2, (Short)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Short)object2);
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
    public static class Singleton
    extends Double2ShortFunctions.Singleton
    implements Double2ShortMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Double2ShortMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient ShortCollection values;

        protected Singleton(double d, short s) {
            super(d, s);
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
        public void putAll(Map<? extends Double, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ShortMap.Entry> double2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractDouble2ShortMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Short>> entrySet() {
            return this.double2ShortEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.singleton(this.key);
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
            return HashCommon.double2int(this.key) ^ this.value;
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
    extends Double2ShortFunctions.EmptyFunction
    implements Double2ShortMap,
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
        public void putAll(Map<? extends Double, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ShortMap.Entry> double2ShortEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public DoubleSet keySet() {
            return DoubleSets.EMPTY_SET;
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

