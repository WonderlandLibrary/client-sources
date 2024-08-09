/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2IntMap;
import it.unimi.dsi.fastutil.floats.Float2IntFunction;
import it.unimi.dsi.fastutil.floats.Float2IntFunctions;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSets;
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

public final class Float2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Float2IntMaps() {
    }

    public static ObjectIterator<Float2IntMap.Entry> fastIterator(Float2IntMap float2IntMap) {
        ObjectSet<Float2IntMap.Entry> objectSet = float2IntMap.float2IntEntrySet();
        return objectSet instanceof Float2IntMap.FastEntrySet ? ((Float2IntMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Float2IntMap float2IntMap, Consumer<? super Float2IntMap.Entry> consumer) {
        ObjectSet<Float2IntMap.Entry> objectSet = float2IntMap.float2IntEntrySet();
        if (objectSet instanceof Float2IntMap.FastEntrySet) {
            ((Float2IntMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Float2IntMap.Entry> fastIterable(Float2IntMap float2IntMap) {
        ObjectSet<Float2IntMap.Entry> objectSet = float2IntMap.float2IntEntrySet();
        return objectSet instanceof Float2IntMap.FastEntrySet ? new ObjectIterable<Float2IntMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Float2IntMap.Entry> iterator() {
                return ((Float2IntMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Float2IntMap.Entry> consumer) {
                ((Float2IntMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Float2IntMap singleton(float f, int n) {
        return new Singleton(f, n);
    }

    public static Float2IntMap singleton(Float f, Integer n) {
        return new Singleton(f.floatValue(), n);
    }

    public static Float2IntMap synchronize(Float2IntMap float2IntMap) {
        return new SynchronizedMap(float2IntMap);
    }

    public static Float2IntMap synchronize(Float2IntMap float2IntMap, Object object) {
        return new SynchronizedMap(float2IntMap, object);
    }

    public static Float2IntMap unmodifiable(Float2IntMap float2IntMap) {
        return new UnmodifiableMap(float2IntMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Float2IntFunctions.UnmodifiableFunction
    implements Float2IntMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2IntMap map;
        protected transient ObjectSet<Float2IntMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient IntCollection values;

        protected UnmodifiableMap(Float2IntMap float2IntMap) {
            super(float2IntMap);
            this.map = float2IntMap;
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
        public void putAll(Map<? extends Float, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2IntMap.Entry> float2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.float2IntEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Integer>> entrySet() {
            return this.float2IntEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.unmodifiable(this.map.keySet());
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
        public int getOrDefault(float f, int n) {
            return this.map.getOrDefault(f, n);
        }

        @Override
        public void forEach(BiConsumer<? super Float, ? super Integer> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int putIfAbsent(float f, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(float f, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int replace(float f, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(float f, int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsentNullable(float f, DoubleFunction<? extends Integer> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsentPartial(float f, Float2IntFunction float2IntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfPresent(float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compute(float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int merge(float f, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer replace(Float f, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Float f, Integer n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer putIfAbsent(Float f, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer computeIfAbsent(Float f, Function<? super Float, ? extends Integer> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer computeIfPresent(Float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer compute(Float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer merge(Float f, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Integer>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Integer)object2);
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
    extends Float2IntFunctions.SynchronizedFunction
    implements Float2IntMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2IntMap map;
        protected transient ObjectSet<Float2IntMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient IntCollection values;

        protected SynchronizedMap(Float2IntMap float2IntMap, Object object) {
            super(float2IntMap, object);
            this.map = float2IntMap;
        }

        protected SynchronizedMap(Float2IntMap float2IntMap) {
            super(float2IntMap);
            this.map = float2IntMap;
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
        public void putAll(Map<? extends Float, ? extends Integer> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Float2IntMap.Entry> float2IntEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.float2IntEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Integer>> entrySet() {
            return this.float2IntEntrySet();
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
        public int getOrDefault(float f, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(f, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Float, ? super Integer> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int putIfAbsent(float f, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(float f, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(f, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int replace(float f, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(float f, int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(f, doubleToIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsentNullable(float f, DoubleFunction<? extends Integer> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(f, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsentPartial(float f, Float2IntFunction float2IntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(f, float2IntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfPresent(float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compute(float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int merge(float f, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, n, biFunction);
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
        public Integer replace(Float f, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Float f, Integer n, Integer n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer putIfAbsent(Float f, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer computeIfAbsent(Float f, Function<? super Float, ? extends Integer> function) {
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
        public Integer computeIfPresent(Float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer compute(Float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer merge(Float f, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, n, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Integer>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Integer)object2);
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
    extends Float2IntFunctions.Singleton
    implements Float2IntMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Float2IntMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient IntCollection values;

        protected Singleton(float f, int n) {
            super(f, n);
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
        public void putAll(Map<? extends Float, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2IntMap.Entry> float2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractFloat2IntMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Integer>> entrySet() {
            return this.float2IntEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.singleton(this.key);
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
            return HashCommon.float2int(this.key) ^ this.value;
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
    extends Float2IntFunctions.EmptyFunction
    implements Float2IntMap,
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
        public void putAll(Map<? extends Float, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2IntMap.Entry> float2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public FloatSet keySet() {
            return FloatSets.EMPTY_SET;
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

