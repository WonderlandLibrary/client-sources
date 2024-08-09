/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2FloatMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
import it.unimi.dsi.fastutil.shorts.Short2FloatFunctions;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

public final class Short2FloatMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Short2FloatMaps() {
    }

    public static ObjectIterator<Short2FloatMap.Entry> fastIterator(Short2FloatMap short2FloatMap) {
        ObjectSet<Short2FloatMap.Entry> objectSet = short2FloatMap.short2FloatEntrySet();
        return objectSet instanceof Short2FloatMap.FastEntrySet ? ((Short2FloatMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Short2FloatMap short2FloatMap, Consumer<? super Short2FloatMap.Entry> consumer) {
        ObjectSet<Short2FloatMap.Entry> objectSet = short2FloatMap.short2FloatEntrySet();
        if (objectSet instanceof Short2FloatMap.FastEntrySet) {
            ((Short2FloatMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Short2FloatMap.Entry> fastIterable(Short2FloatMap short2FloatMap) {
        ObjectSet<Short2FloatMap.Entry> objectSet = short2FloatMap.short2FloatEntrySet();
        return objectSet instanceof Short2FloatMap.FastEntrySet ? new ObjectIterable<Short2FloatMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Short2FloatMap.Entry> iterator() {
                return ((Short2FloatMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Short2FloatMap.Entry> consumer) {
                ((Short2FloatMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Short2FloatMap singleton(short s, float f) {
        return new Singleton(s, f);
    }

    public static Short2FloatMap singleton(Short s, Float f) {
        return new Singleton(s, f.floatValue());
    }

    public static Short2FloatMap synchronize(Short2FloatMap short2FloatMap) {
        return new SynchronizedMap(short2FloatMap);
    }

    public static Short2FloatMap synchronize(Short2FloatMap short2FloatMap, Object object) {
        return new SynchronizedMap(short2FloatMap, object);
    }

    public static Short2FloatMap unmodifiable(Short2FloatMap short2FloatMap) {
        return new UnmodifiableMap(short2FloatMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Short2FloatFunctions.UnmodifiableFunction
    implements Short2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2FloatMap map;
        protected transient ObjectSet<Short2FloatMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient FloatCollection values;

        protected UnmodifiableMap(Short2FloatMap short2FloatMap) {
            super(short2FloatMap);
            this.map = short2FloatMap;
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
        public void putAll(Map<? extends Short, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2FloatMap.Entry> short2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.short2FloatEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Float>> entrySet() {
            return this.short2FloatEntrySet();
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.unmodifiable(this.map.keySet());
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
        public float getOrDefault(short s, float f) {
            return this.map.getOrDefault(s, f);
        }

        @Override
        public void forEach(BiConsumer<? super Short, ? super Float> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float putIfAbsent(short s, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(short s, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float replace(short s, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(short s, float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsent(short s, IntToDoubleFunction intToDoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentNullable(short s, IntFunction<? extends Float> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentPartial(short s, Short2FloatFunction short2FloatFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfPresent(short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float compute(short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float merge(short s, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
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
        public Float replace(Short s, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Short s, Float f, Float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float putIfAbsent(Short s, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfAbsent(Short s, Function<? super Short, ? extends Float> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfPresent(Short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float compute(Short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float merge(Short s, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Short)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Short)object, (BiFunction<? super Short, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Short)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Short)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Short)object, (Float)object2);
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
    extends Short2FloatFunctions.SynchronizedFunction
    implements Short2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2FloatMap map;
        protected transient ObjectSet<Short2FloatMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient FloatCollection values;

        protected SynchronizedMap(Short2FloatMap short2FloatMap, Object object) {
            super(short2FloatMap, object);
            this.map = short2FloatMap;
        }

        protected SynchronizedMap(Short2FloatMap short2FloatMap) {
            super(short2FloatMap);
            this.map = short2FloatMap;
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
        public void putAll(Map<? extends Short, ? extends Float> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Short2FloatMap.Entry> short2FloatEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.short2FloatEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Float>> entrySet() {
            return this.short2FloatEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = ShortSets.synchronize(this.map.keySet(), this.sync);
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
        public float getOrDefault(short s, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(s, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Short, ? super Float> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float putIfAbsent(short s, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(s, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(short s, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(s, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float replace(short s, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(short s, float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsent(short s, IntToDoubleFunction intToDoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(s, intToDoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentNullable(short s, IntFunction<? extends Float> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(s, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentPartial(short s, Short2FloatFunction short2FloatFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(s, short2FloatFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfPresent(short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float compute(short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float merge(short s, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(s, f, biFunction);
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
        public Float replace(Short s, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Short s, Float f, Float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float putIfAbsent(Short s, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(s, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float computeIfAbsent(Short s, Function<? super Short, ? extends Float> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(s, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float computeIfPresent(Short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float compute(Short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float merge(Short s, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(s, f, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Short)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Short)object, (BiFunction<? super Short, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Short)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Short)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Short)object, (Float)object2);
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
    extends Short2FloatFunctions.Singleton
    implements Short2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Short2FloatMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient FloatCollection values;

        protected Singleton(short s, float f) {
            super(s, f);
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
        public void putAll(Map<? extends Short, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2FloatMap.Entry> short2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractShort2FloatMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Float>> entrySet() {
            return this.short2FloatEntrySet();
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.singleton(this.key);
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
    extends Short2FloatFunctions.EmptyFunction
    implements Short2FloatMap,
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
        public void putAll(Map<? extends Short, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2FloatMap.Entry> short2FloatEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ShortSet keySet() {
            return ShortSets.EMPTY_SET;
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

