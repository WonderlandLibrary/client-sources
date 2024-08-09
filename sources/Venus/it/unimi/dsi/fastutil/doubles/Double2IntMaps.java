/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2IntMap;
import it.unimi.dsi.fastutil.doubles.Double2IntFunction;
import it.unimi.dsi.fastutil.doubles.Double2IntFunctions;
import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
import it.unimi.dsi.fastutil.ints.IntSets;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public final class Double2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Double2IntMaps() {
    }

    public static ObjectIterator<Double2IntMap.Entry> fastIterator(Double2IntMap double2IntMap) {
        ObjectSet<Double2IntMap.Entry> objectSet = double2IntMap.double2IntEntrySet();
        return objectSet instanceof Double2IntMap.FastEntrySet ? ((Double2IntMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Double2IntMap double2IntMap, Consumer<? super Double2IntMap.Entry> consumer) {
        ObjectSet<Double2IntMap.Entry> objectSet = double2IntMap.double2IntEntrySet();
        if (objectSet instanceof Double2IntMap.FastEntrySet) {
            ((Double2IntMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Double2IntMap.Entry> fastIterable(Double2IntMap double2IntMap) {
        ObjectSet<Double2IntMap.Entry> objectSet = double2IntMap.double2IntEntrySet();
        return objectSet instanceof Double2IntMap.FastEntrySet ? new ObjectIterable<Double2IntMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Double2IntMap.Entry> iterator() {
                return ((Double2IntMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Double2IntMap.Entry> consumer) {
                ((Double2IntMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Double2IntMap singleton(double d, int n) {
        return new Singleton(d, n);
    }

    public static Double2IntMap singleton(Double d, Integer n) {
        return new Singleton(d, n);
    }

    public static Double2IntMap synchronize(Double2IntMap double2IntMap) {
        return new SynchronizedMap(double2IntMap);
    }

    public static Double2IntMap synchronize(Double2IntMap double2IntMap, Object object) {
        return new SynchronizedMap(double2IntMap, object);
    }

    public static Double2IntMap unmodifiable(Double2IntMap double2IntMap) {
        return new UnmodifiableMap(double2IntMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Double2IntFunctions.UnmodifiableFunction
    implements Double2IntMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2IntMap map;
        protected transient ObjectSet<Double2IntMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient IntCollection values;

        protected UnmodifiableMap(Double2IntMap double2IntMap) {
            super(double2IntMap);
            this.map = double2IntMap;
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
        public void putAll(Map<? extends Double, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2IntMap.Entry> double2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.double2IntEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Integer>> entrySet() {
            return this.double2IntEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.unmodifiable(this.map.keySet());
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
        public int getOrDefault(double d, int n) {
            return this.map.getOrDefault(d, n);
        }

        @Override
        public void forEach(BiConsumer<? super Double, ? super Integer> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int putIfAbsent(double d, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(double d, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int replace(double d, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(double d, int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsentNullable(double d, DoubleFunction<? extends Integer> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsentPartial(double d, Double2IntFunction double2IntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfPresent(double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compute(double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int merge(double d, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer replace(Double d, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Double d, Integer n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer putIfAbsent(Double d, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer computeIfAbsent(Double d, Function<? super Double, ? extends Integer> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer computeIfPresent(Double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer compute(Double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer merge(Double d, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Integer>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Integer)object2);
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
    public static class SynchronizedMap
    extends Double2IntFunctions.SynchronizedFunction
    implements Double2IntMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2IntMap map;
        protected transient ObjectSet<Double2IntMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient IntCollection values;

        protected SynchronizedMap(Double2IntMap double2IntMap, Object object) {
            super(double2IntMap, object);
            this.map = double2IntMap;
        }

        protected SynchronizedMap(Double2IntMap double2IntMap) {
            super(double2IntMap);
            this.map = double2IntMap;
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
        public void putAll(Map<? extends Double, ? extends Integer> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Double2IntMap.Entry> double2IntEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.double2IntEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Integer>> entrySet() {
            return this.double2IntEntrySet();
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
        public int getOrDefault(double d, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(d, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Double, ? super Integer> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int putIfAbsent(double d, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(double d, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(d, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int replace(double d, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(double d, int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(d, doubleToIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsentNullable(double d, DoubleFunction<? extends Integer> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(d, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsentPartial(double d, Double2IntFunction double2IntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(d, double2IntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfPresent(double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compute(double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int merge(double d, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, n, biFunction);
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
        public Integer replace(Double d, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Double d, Integer n, Integer n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer putIfAbsent(Double d, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer computeIfAbsent(Double d, Function<? super Double, ? extends Integer> function) {
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
        public Integer computeIfPresent(Double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer compute(Double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer merge(Double d, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, n, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Integer>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Integer)object2);
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
    public static class Singleton
    extends Double2IntFunctions.Singleton
    implements Double2IntMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Double2IntMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient IntCollection values;

        protected Singleton(double d, int n) {
            super(d, n);
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
        public void putAll(Map<? extends Double, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2IntMap.Entry> double2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractDouble2IntMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Integer>> entrySet() {
            return this.double2IntEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.singleton(this.key);
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
    extends Double2IntFunctions.EmptyFunction
    implements Double2IntMap,
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
        public void putAll(Map<? extends Double, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2IntMap.Entry> double2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public DoubleSet keySet() {
            return DoubleSets.EMPTY_SET;
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

