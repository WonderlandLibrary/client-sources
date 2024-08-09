/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2LongMap;
import it.unimi.dsi.fastutil.floats.Float2LongFunction;
import it.unimi.dsi.fastutil.floats.Float2LongFunctions;
import it.unimi.dsi.fastutil.floats.Float2LongMap;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSets;
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

public final class Float2LongMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Float2LongMaps() {
    }

    public static ObjectIterator<Float2LongMap.Entry> fastIterator(Float2LongMap float2LongMap) {
        ObjectSet<Float2LongMap.Entry> objectSet = float2LongMap.float2LongEntrySet();
        return objectSet instanceof Float2LongMap.FastEntrySet ? ((Float2LongMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Float2LongMap float2LongMap, Consumer<? super Float2LongMap.Entry> consumer) {
        ObjectSet<Float2LongMap.Entry> objectSet = float2LongMap.float2LongEntrySet();
        if (objectSet instanceof Float2LongMap.FastEntrySet) {
            ((Float2LongMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Float2LongMap.Entry> fastIterable(Float2LongMap float2LongMap) {
        ObjectSet<Float2LongMap.Entry> objectSet = float2LongMap.float2LongEntrySet();
        return objectSet instanceof Float2LongMap.FastEntrySet ? new ObjectIterable<Float2LongMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Float2LongMap.Entry> iterator() {
                return ((Float2LongMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Float2LongMap.Entry> consumer) {
                ((Float2LongMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Float2LongMap singleton(float f, long l) {
        return new Singleton(f, l);
    }

    public static Float2LongMap singleton(Float f, Long l) {
        return new Singleton(f.floatValue(), l);
    }

    public static Float2LongMap synchronize(Float2LongMap float2LongMap) {
        return new SynchronizedMap(float2LongMap);
    }

    public static Float2LongMap synchronize(Float2LongMap float2LongMap, Object object) {
        return new SynchronizedMap(float2LongMap, object);
    }

    public static Float2LongMap unmodifiable(Float2LongMap float2LongMap) {
        return new UnmodifiableMap(float2LongMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Float2LongFunctions.UnmodifiableFunction
    implements Float2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2LongMap map;
        protected transient ObjectSet<Float2LongMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient LongCollection values;

        protected UnmodifiableMap(Float2LongMap float2LongMap) {
            super(float2LongMap);
            this.map = float2LongMap;
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
        public void putAll(Map<? extends Float, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2LongMap.Entry> float2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.float2LongEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Long>> entrySet() {
            return this.float2LongEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.unmodifiable(this.map.keySet());
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
        public long getOrDefault(float f, long l) {
            return this.map.getOrDefault(f, l);
        }

        @Override
        public void forEach(BiConsumer<? super Float, ? super Long> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long putIfAbsent(float f, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(float f, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long replace(float f, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(float f, long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsent(float f, DoubleToLongFunction doubleToLongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsentNullable(float f, DoubleFunction<? extends Long> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsentPartial(float f, Float2LongFunction float2LongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfPresent(float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long compute(float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long merge(float f, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
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
        public Long replace(Float f, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Float f, Long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long putIfAbsent(Float f, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long computeIfAbsent(Float f, Function<? super Float, ? extends Long> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long computeIfPresent(Float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long compute(Float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long merge(Float f, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Long>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Long)object2);
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
    extends Float2LongFunctions.SynchronizedFunction
    implements Float2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2LongMap map;
        protected transient ObjectSet<Float2LongMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient LongCollection values;

        protected SynchronizedMap(Float2LongMap float2LongMap, Object object) {
            super(float2LongMap, object);
            this.map = float2LongMap;
        }

        protected SynchronizedMap(Float2LongMap float2LongMap) {
            super(float2LongMap);
            this.map = float2LongMap;
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
        public void putAll(Map<? extends Float, ? extends Long> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Float2LongMap.Entry> float2LongEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.float2LongEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Long>> entrySet() {
            return this.float2LongEntrySet();
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
        public long getOrDefault(float f, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(f, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Float, ? super Long> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long putIfAbsent(float f, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(float f, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(f, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long replace(float f, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(float f, long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsent(float f, DoubleToLongFunction doubleToLongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(f, doubleToLongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsentNullable(float f, DoubleFunction<? extends Long> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(f, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsentPartial(float f, Float2LongFunction float2LongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(f, float2LongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfPresent(float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long compute(float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long merge(float f, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, l, biFunction);
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
        public Long replace(Float f, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Float f, Long l, Long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long putIfAbsent(Float f, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long computeIfAbsent(Float f, Function<? super Float, ? extends Long> function) {
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
        public Long computeIfPresent(Float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
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
        public Long compute(Float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
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
        public Long merge(Float f, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, l, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Long>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Long)object2);
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
    extends Float2LongFunctions.Singleton
    implements Float2LongMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Float2LongMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient LongCollection values;

        protected Singleton(float f, long l) {
            super(f, l);
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
        public void putAll(Map<? extends Float, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2LongMap.Entry> float2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractFloat2LongMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Long>> entrySet() {
            return this.float2LongEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.singleton(this.key);
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
            return HashCommon.float2int(this.key) ^ HashCommon.long2int(this.value);
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
    extends Float2LongFunctions.EmptyFunction
    implements Float2LongMap,
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
        public void putAll(Map<? extends Float, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2LongMap.Entry> float2LongEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public FloatSet keySet() {
            return FloatSets.EMPTY_SET;
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

