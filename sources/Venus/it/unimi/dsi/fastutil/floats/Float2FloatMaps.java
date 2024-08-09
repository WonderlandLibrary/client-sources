/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.floats.Float2FloatFunctions;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
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

public final class Float2FloatMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Float2FloatMaps() {
    }

    public static ObjectIterator<Float2FloatMap.Entry> fastIterator(Float2FloatMap float2FloatMap) {
        ObjectSet<Float2FloatMap.Entry> objectSet = float2FloatMap.float2FloatEntrySet();
        return objectSet instanceof Float2FloatMap.FastEntrySet ? ((Float2FloatMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Float2FloatMap float2FloatMap, Consumer<? super Float2FloatMap.Entry> consumer) {
        ObjectSet<Float2FloatMap.Entry> objectSet = float2FloatMap.float2FloatEntrySet();
        if (objectSet instanceof Float2FloatMap.FastEntrySet) {
            ((Float2FloatMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Float2FloatMap.Entry> fastIterable(Float2FloatMap float2FloatMap) {
        ObjectSet<Float2FloatMap.Entry> objectSet = float2FloatMap.float2FloatEntrySet();
        return objectSet instanceof Float2FloatMap.FastEntrySet ? new ObjectIterable<Float2FloatMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Float2FloatMap.Entry> iterator() {
                return ((Float2FloatMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Float2FloatMap.Entry> consumer) {
                ((Float2FloatMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Float2FloatMap singleton(float f, float f2) {
        return new Singleton(f, f2);
    }

    public static Float2FloatMap singleton(Float f, Float f2) {
        return new Singleton(f.floatValue(), f2.floatValue());
    }

    public static Float2FloatMap synchronize(Float2FloatMap float2FloatMap) {
        return new SynchronizedMap(float2FloatMap);
    }

    public static Float2FloatMap synchronize(Float2FloatMap float2FloatMap, Object object) {
        return new SynchronizedMap(float2FloatMap, object);
    }

    public static Float2FloatMap unmodifiable(Float2FloatMap float2FloatMap) {
        return new UnmodifiableMap(float2FloatMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Float2FloatFunctions.UnmodifiableFunction
    implements Float2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2FloatMap map;
        protected transient ObjectSet<Float2FloatMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient FloatCollection values;

        protected UnmodifiableMap(Float2FloatMap float2FloatMap) {
            super(float2FloatMap);
            this.map = float2FloatMap;
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
        public void putAll(Map<? extends Float, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2FloatMap.Entry> float2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.float2FloatEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Float>> entrySet() {
            return this.float2FloatEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.unmodifiable(this.map.keySet());
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
        public float getOrDefault(float f, float f2) {
            return this.map.getOrDefault(f, f2);
        }

        @Override
        public void forEach(BiConsumer<? super Float, ? super Float> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float putIfAbsent(float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float replace(float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(float f, float f2, float f3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsent(float f, DoubleUnaryOperator doubleUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentNullable(float f, DoubleFunction<? extends Float> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentPartial(float f, Float2FloatFunction float2FloatFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfPresent(float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float compute(float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float merge(float f, float f2, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
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
        public Float replace(Float f, Float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Float f, Float f2, Float f3) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float putIfAbsent(Float f, Float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfAbsent(Float f, Function<? super Float, ? extends Float> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfPresent(Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float compute(Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float merge(Float f, Float f2, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Float)object2);
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
    extends Float2FloatFunctions.SynchronizedFunction
    implements Float2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2FloatMap map;
        protected transient ObjectSet<Float2FloatMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient FloatCollection values;

        protected SynchronizedMap(Float2FloatMap float2FloatMap, Object object) {
            super(float2FloatMap, object);
            this.map = float2FloatMap;
        }

        protected SynchronizedMap(Float2FloatMap float2FloatMap) {
            super(float2FloatMap);
            this.map = float2FloatMap;
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
        public void putAll(Map<? extends Float, ? extends Float> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Float2FloatMap.Entry> float2FloatEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.float2FloatEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Float>> entrySet() {
            return this.float2FloatEntrySet();
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
        public float getOrDefault(float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Float, ? super Float> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float putIfAbsent(float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float replace(float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(float f, float f2, float f3) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, f2, f3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsent(float f, DoubleUnaryOperator doubleUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(f, doubleUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentNullable(float f, DoubleFunction<? extends Float> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(f, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentPartial(float f, Float2FloatFunction float2FloatFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(f, float2FloatFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfPresent(float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float compute(float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float merge(float f, float f2, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, f2, biFunction);
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
        public Float replace(Float f, Float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Float f, Float f2, Float f3) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, f2, f3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float putIfAbsent(Float f, Float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float computeIfAbsent(Float f, Function<? super Float, ? extends Float> function) {
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
        public Float computeIfPresent(Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
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
        public Float compute(Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
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
        public Float merge(Float f, Float f2, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, f2, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Float)object2);
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
    extends Float2FloatFunctions.Singleton
    implements Float2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Float2FloatMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient FloatCollection values;

        protected Singleton(float f, float f2) {
            super(f, f2);
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
        public void putAll(Map<? extends Float, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2FloatMap.Entry> float2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractFloat2FloatMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Float>> entrySet() {
            return this.float2FloatEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.singleton(this.key);
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
            return HashCommon.float2int(this.key) ^ HashCommon.float2int(this.value);
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
    extends Float2FloatFunctions.EmptyFunction
    implements Float2FloatMap,
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
        public void putAll(Map<? extends Float, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2FloatMap.Entry> float2FloatEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public FloatSet keySet() {
            return FloatSets.EMPTY_SET;
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

