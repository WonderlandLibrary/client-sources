/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatSets;
import it.unimi.dsi.fastutil.ints.AbstractInt2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2FloatFunctions;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

public final class Int2FloatMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Int2FloatMaps() {
    }

    public static ObjectIterator<Int2FloatMap.Entry> fastIterator(Int2FloatMap int2FloatMap) {
        ObjectSet<Int2FloatMap.Entry> objectSet = int2FloatMap.int2FloatEntrySet();
        return objectSet instanceof Int2FloatMap.FastEntrySet ? ((Int2FloatMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Int2FloatMap int2FloatMap, Consumer<? super Int2FloatMap.Entry> consumer) {
        ObjectSet<Int2FloatMap.Entry> objectSet = int2FloatMap.int2FloatEntrySet();
        if (objectSet instanceof Int2FloatMap.FastEntrySet) {
            ((Int2FloatMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Int2FloatMap.Entry> fastIterable(Int2FloatMap int2FloatMap) {
        ObjectSet<Int2FloatMap.Entry> objectSet = int2FloatMap.int2FloatEntrySet();
        return objectSet instanceof Int2FloatMap.FastEntrySet ? new ObjectIterable<Int2FloatMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Int2FloatMap.Entry> iterator() {
                return ((Int2FloatMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Int2FloatMap.Entry> consumer) {
                ((Int2FloatMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Int2FloatMap singleton(int n, float f) {
        return new Singleton(n, f);
    }

    public static Int2FloatMap singleton(Integer n, Float f) {
        return new Singleton(n, f.floatValue());
    }

    public static Int2FloatMap synchronize(Int2FloatMap int2FloatMap) {
        return new SynchronizedMap(int2FloatMap);
    }

    public static Int2FloatMap synchronize(Int2FloatMap int2FloatMap, Object object) {
        return new SynchronizedMap(int2FloatMap, object);
    }

    public static Int2FloatMap unmodifiable(Int2FloatMap int2FloatMap) {
        return new UnmodifiableMap(int2FloatMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Int2FloatFunctions.UnmodifiableFunction
    implements Int2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2FloatMap map;
        protected transient ObjectSet<Int2FloatMap.Entry> entries;
        protected transient IntSet keys;
        protected transient FloatCollection values;

        protected UnmodifiableMap(Int2FloatMap int2FloatMap) {
            super(int2FloatMap);
            this.map = int2FloatMap;
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
        public void putAll(Map<? extends Integer, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2FloatMap.Entry> int2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.int2FloatEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Float>> entrySet() {
            return this.int2FloatEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.unmodifiable(this.map.keySet());
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
        public float getOrDefault(int n, float f) {
            return this.map.getOrDefault(n, f);
        }

        @Override
        public void forEach(BiConsumer<? super Integer, ? super Float> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float putIfAbsent(int n, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(int n, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float replace(int n, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(int n, float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsent(int n, IntToDoubleFunction intToDoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentNullable(int n, IntFunction<? extends Float> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentPartial(int n, Int2FloatFunction int2FloatFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfPresent(int n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float compute(int n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float merge(int n, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
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
        public Float replace(Integer n, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Integer n, Float f, Float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float putIfAbsent(Integer n, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfAbsent(Integer n, Function<? super Integer, ? extends Float> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfPresent(Integer n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float compute(Integer n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float merge(Integer n, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Integer)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Integer)object, (BiFunction<? super Integer, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Integer)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Integer)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Integer)object, (Float)object2);
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
    extends Int2FloatFunctions.SynchronizedFunction
    implements Int2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2FloatMap map;
        protected transient ObjectSet<Int2FloatMap.Entry> entries;
        protected transient IntSet keys;
        protected transient FloatCollection values;

        protected SynchronizedMap(Int2FloatMap int2FloatMap, Object object) {
            super(int2FloatMap, object);
            this.map = int2FloatMap;
        }

        protected SynchronizedMap(Int2FloatMap int2FloatMap) {
            super(int2FloatMap);
            this.map = int2FloatMap;
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
        public void putAll(Map<? extends Integer, ? extends Float> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Int2FloatMap.Entry> int2FloatEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.int2FloatEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Float>> entrySet() {
            return this.int2FloatEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public IntSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = IntSets.synchronize(this.map.keySet(), this.sync);
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
        public float getOrDefault(int n, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Integer, ? super Float> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float putIfAbsent(int n, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(int n, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float replace(int n, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(int n, float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsent(int n, IntToDoubleFunction intToDoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(n, intToDoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentNullable(int n, IntFunction<? extends Float> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(n, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentPartial(int n, Int2FloatFunction int2FloatFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(n, int2FloatFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfPresent(int n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float compute(int n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float merge(int n, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(n, f, biFunction);
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
        public Float replace(Integer n, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Integer n, Float f, Float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float putIfAbsent(Integer n, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(n, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float computeIfAbsent(Integer n, Function<? super Integer, ? extends Float> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(n, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float computeIfPresent(Integer n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float compute(Integer n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float merge(Integer n, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(n, f, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Integer)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Integer)object, (BiFunction<? super Integer, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Integer)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Integer)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Integer)object, (Float)object2);
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
    extends Int2FloatFunctions.Singleton
    implements Int2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2FloatMap.Entry> entries;
        protected transient IntSet keys;
        protected transient FloatCollection values;

        protected Singleton(int n, float f) {
            super(n, f);
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
        public void putAll(Map<? extends Integer, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2FloatMap.Entry> int2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2FloatMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Float>> entrySet() {
            return this.int2FloatEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
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
            return this.key ^ HashCommon.float2int(this.value);
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
    extends Int2FloatFunctions.EmptyFunction
    implements Int2FloatMap,
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
        public void putAll(Map<? extends Integer, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2FloatMap.Entry> int2FloatEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
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

