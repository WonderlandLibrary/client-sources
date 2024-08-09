/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
import it.unimi.dsi.fastutil.doubles.Double2FloatFunctions;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
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

public final class Double2FloatMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Double2FloatMaps() {
    }

    public static ObjectIterator<Double2FloatMap.Entry> fastIterator(Double2FloatMap double2FloatMap) {
        ObjectSet<Double2FloatMap.Entry> objectSet = double2FloatMap.double2FloatEntrySet();
        return objectSet instanceof Double2FloatMap.FastEntrySet ? ((Double2FloatMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Double2FloatMap double2FloatMap, Consumer<? super Double2FloatMap.Entry> consumer) {
        ObjectSet<Double2FloatMap.Entry> objectSet = double2FloatMap.double2FloatEntrySet();
        if (objectSet instanceof Double2FloatMap.FastEntrySet) {
            ((Double2FloatMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Double2FloatMap.Entry> fastIterable(Double2FloatMap double2FloatMap) {
        ObjectSet<Double2FloatMap.Entry> objectSet = double2FloatMap.double2FloatEntrySet();
        return objectSet instanceof Double2FloatMap.FastEntrySet ? new ObjectIterable<Double2FloatMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Double2FloatMap.Entry> iterator() {
                return ((Double2FloatMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Double2FloatMap.Entry> consumer) {
                ((Double2FloatMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Double2FloatMap singleton(double d, float f) {
        return new Singleton(d, f);
    }

    public static Double2FloatMap singleton(Double d, Float f) {
        return new Singleton(d, f.floatValue());
    }

    public static Double2FloatMap synchronize(Double2FloatMap double2FloatMap) {
        return new SynchronizedMap(double2FloatMap);
    }

    public static Double2FloatMap synchronize(Double2FloatMap double2FloatMap, Object object) {
        return new SynchronizedMap(double2FloatMap, object);
    }

    public static Double2FloatMap unmodifiable(Double2FloatMap double2FloatMap) {
        return new UnmodifiableMap(double2FloatMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Double2FloatFunctions.UnmodifiableFunction
    implements Double2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2FloatMap map;
        protected transient ObjectSet<Double2FloatMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient FloatCollection values;

        protected UnmodifiableMap(Double2FloatMap double2FloatMap) {
            super(double2FloatMap);
            this.map = double2FloatMap;
        }

        @Override
        public boolean containsValue(float f) {
            return this.map.containsValue(f);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Double, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2FloatMap.Entry> double2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.double2FloatEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Float>> entrySet() {
            return this.double2FloatEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public FloatCollection values() {
            if (this.values == null) {
                return FloatCollections.unmodifiable(this.map.values());
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
        public float getOrDefault(double d, float f) {
            return this.map.getOrDefault(d, f);
        }

        @Override
        public void forEach(BiConsumer<? super Double, ? super Float> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float putIfAbsent(double d, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(double d, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float replace(double d, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(double d, float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsent(double d, DoubleUnaryOperator doubleUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentNullable(double d, DoubleFunction<? extends Float> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentPartial(double d, Double2FloatFunction double2FloatFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfPresent(double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float compute(double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float merge(double d, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float getOrDefault(Object object, Float f) {
            return this.map.getOrDefault(object, f);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float replace(Double d, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Double d, Float f, Float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float putIfAbsent(Double d, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfAbsent(Double d, Function<? super Double, ? extends Float> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfPresent(Double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float compute(Double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float merge(Double d, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Float)object2);
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
    extends Double2FloatFunctions.SynchronizedFunction
    implements Double2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2FloatMap map;
        protected transient ObjectSet<Double2FloatMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient FloatCollection values;

        protected SynchronizedMap(Double2FloatMap double2FloatMap, Object object) {
            super(double2FloatMap, object);
            this.map = double2FloatMap;
        }

        protected SynchronizedMap(Double2FloatMap double2FloatMap) {
            super(double2FloatMap);
            this.map = double2FloatMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(f);
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
        public void putAll(Map<? extends Double, ? extends Float> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Double2FloatMap.Entry> double2FloatEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.double2FloatEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Float>> entrySet() {
            return this.double2FloatEntrySet();
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
        public FloatCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return FloatCollections.synchronize(this.map.values(), this.sync);
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
        public float getOrDefault(double d, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(d, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Double, ? super Float> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float putIfAbsent(double d, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(double d, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(d, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float replace(double d, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(double d, float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsent(double d, DoubleUnaryOperator doubleUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(d, doubleUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentNullable(double d, DoubleFunction<? extends Float> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(d, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentPartial(double d, Double2FloatFunction double2FloatFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(d, double2FloatFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfPresent(double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float compute(double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float merge(double d, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float getOrDefault(Object object, Float f) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, f);
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
        public Float replace(Double d, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Double d, Float f, Float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float putIfAbsent(Double d, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float computeIfAbsent(Double d, Function<? super Double, ? extends Float> function) {
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
        public Float computeIfPresent(Double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
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
        public Float compute(Double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
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
        public Float merge(Double d, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, f, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Float)object2);
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
    extends Double2FloatFunctions.Singleton
    implements Double2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Double2FloatMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient FloatCollection values;

        protected Singleton(double d, float f) {
            super(d, f);
        }

        @Override
        public boolean containsValue(float f) {
            return Float.floatToIntBits(this.value) == Float.floatToIntBits(f);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return Float.floatToIntBits(((Float)object).floatValue()) == Float.floatToIntBits(this.value);
        }

        @Override
        public void putAll(Map<? extends Double, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2FloatMap.Entry> double2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractDouble2FloatMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Float>> entrySet() {
            return this.double2FloatEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public FloatCollection values() {
            if (this.values == null) {
                this.values = FloatSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ HashCommon.float2int(this.value);
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
    extends Double2FloatFunctions.EmptyFunction
    implements Double2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(float f) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Double, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2FloatMap.Entry> double2FloatEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public DoubleSet keySet() {
            return DoubleSets.EMPTY_SET;
        }

        @Override
        public FloatCollection values() {
            return FloatSets.EMPTY_SET;
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

