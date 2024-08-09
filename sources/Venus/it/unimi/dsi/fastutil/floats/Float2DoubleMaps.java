/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.floats.AbstractFloat2DoubleMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
import it.unimi.dsi.fastutil.floats.Float2DoubleFunctions;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSets;
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
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public final class Float2DoubleMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Float2DoubleMaps() {
    }

    public static ObjectIterator<Float2DoubleMap.Entry> fastIterator(Float2DoubleMap float2DoubleMap) {
        ObjectSet<Float2DoubleMap.Entry> objectSet = float2DoubleMap.float2DoubleEntrySet();
        return objectSet instanceof Float2DoubleMap.FastEntrySet ? ((Float2DoubleMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Float2DoubleMap float2DoubleMap, Consumer<? super Float2DoubleMap.Entry> consumer) {
        ObjectSet<Float2DoubleMap.Entry> objectSet = float2DoubleMap.float2DoubleEntrySet();
        if (objectSet instanceof Float2DoubleMap.FastEntrySet) {
            ((Float2DoubleMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Float2DoubleMap.Entry> fastIterable(Float2DoubleMap float2DoubleMap) {
        ObjectSet<Float2DoubleMap.Entry> objectSet = float2DoubleMap.float2DoubleEntrySet();
        return objectSet instanceof Float2DoubleMap.FastEntrySet ? new ObjectIterable<Float2DoubleMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Float2DoubleMap.Entry> iterator() {
                return ((Float2DoubleMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
                ((Float2DoubleMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Float2DoubleMap singleton(float f, double d) {
        return new Singleton(f, d);
    }

    public static Float2DoubleMap singleton(Float f, Double d) {
        return new Singleton(f.floatValue(), d);
    }

    public static Float2DoubleMap synchronize(Float2DoubleMap float2DoubleMap) {
        return new SynchronizedMap(float2DoubleMap);
    }

    public static Float2DoubleMap synchronize(Float2DoubleMap float2DoubleMap, Object object) {
        return new SynchronizedMap(float2DoubleMap, object);
    }

    public static Float2DoubleMap unmodifiable(Float2DoubleMap float2DoubleMap) {
        return new UnmodifiableMap(float2DoubleMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Float2DoubleFunctions.UnmodifiableFunction
    implements Float2DoubleMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2DoubleMap map;
        protected transient ObjectSet<Float2DoubleMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient DoubleCollection values;

        protected UnmodifiableMap(Float2DoubleMap float2DoubleMap) {
            super(float2DoubleMap);
            this.map = float2DoubleMap;
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
        public void putAll(Map<? extends Float, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2DoubleMap.Entry> float2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.float2DoubleEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Double>> entrySet() {
            return this.float2DoubleEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.unmodifiable(this.map.keySet());
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
        public double getOrDefault(float f, double d) {
            return this.map.getOrDefault(f, d);
        }

        @Override
        public void forEach(BiConsumer<? super Float, ? super Double> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double putIfAbsent(float f, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(float f, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double replace(float f, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(float f, double d, double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsent(float f, DoubleUnaryOperator doubleUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsentNullable(float f, DoubleFunction<? extends Double> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsentPartial(float f, Float2DoubleFunction float2DoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfPresent(float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double compute(float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double merge(float f, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
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
        public Double replace(Float f, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Float f, Double d, Double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double putIfAbsent(Float f, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double computeIfAbsent(Float f, Function<? super Float, ? extends Double> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double computeIfPresent(Float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double compute(Float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double merge(Float f, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Double>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Double)object2);
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
    extends Float2DoubleFunctions.SynchronizedFunction
    implements Float2DoubleMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2DoubleMap map;
        protected transient ObjectSet<Float2DoubleMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient DoubleCollection values;

        protected SynchronizedMap(Float2DoubleMap float2DoubleMap, Object object) {
            super(float2DoubleMap, object);
            this.map = float2DoubleMap;
        }

        protected SynchronizedMap(Float2DoubleMap float2DoubleMap) {
            super(float2DoubleMap);
            this.map = float2DoubleMap;
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
        public void putAll(Map<? extends Float, ? extends Double> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Float2DoubleMap.Entry> float2DoubleEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.float2DoubleEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Double>> entrySet() {
            return this.float2DoubleEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public FloatSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = FloatSets.synchronize(this.map.keySet(), this.sync);
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
        public double getOrDefault(float f, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(f, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Float, ? super Double> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double putIfAbsent(float f, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(float f, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(f, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double replace(float f, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(float f, double d, double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsent(float f, DoubleUnaryOperator doubleUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(f, doubleUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsentNullable(float f, DoubleFunction<? extends Double> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(f, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsentPartial(float f, Float2DoubleFunction float2DoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(f, float2DoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfPresent(float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double compute(float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double merge(float f, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, d, biFunction);
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
        public Double replace(Float f, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Float f, Double d, Double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double putIfAbsent(Float f, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double computeIfAbsent(Float f, Function<? super Float, ? extends Double> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(f, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double computeIfPresent(Float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double compute(Float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double merge(Float f, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, d, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Double>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Double)object2);
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
    extends Float2DoubleFunctions.Singleton
    implements Float2DoubleMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Float2DoubleMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient DoubleCollection values;

        protected Singleton(float f, double d) {
            super(f, d);
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
        public void putAll(Map<? extends Float, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2DoubleMap.Entry> float2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractFloat2DoubleMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Double>> entrySet() {
            return this.float2DoubleEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.singleton(this.key);
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
            return HashCommon.float2int(this.key) ^ HashCommon.double2int(this.value);
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
    extends Float2DoubleFunctions.EmptyFunction
    implements Float2DoubleMap,
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
        public void putAll(Map<? extends Float, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2DoubleMap.Entry> float2DoubleEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public FloatSet keySet() {
            return FloatSets.EMPTY_SET;
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

