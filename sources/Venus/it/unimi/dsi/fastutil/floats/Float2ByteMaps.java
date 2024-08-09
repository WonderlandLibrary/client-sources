/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ByteMap;
import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
import it.unimi.dsi.fastutil.floats.Float2ByteFunctions;
import it.unimi.dsi.fastutil.floats.Float2ByteMap;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public final class Float2ByteMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Float2ByteMaps() {
    }

    public static ObjectIterator<Float2ByteMap.Entry> fastIterator(Float2ByteMap float2ByteMap) {
        ObjectSet<Float2ByteMap.Entry> objectSet = float2ByteMap.float2ByteEntrySet();
        return objectSet instanceof Float2ByteMap.FastEntrySet ? ((Float2ByteMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Float2ByteMap float2ByteMap, Consumer<? super Float2ByteMap.Entry> consumer) {
        ObjectSet<Float2ByteMap.Entry> objectSet = float2ByteMap.float2ByteEntrySet();
        if (objectSet instanceof Float2ByteMap.FastEntrySet) {
            ((Float2ByteMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Float2ByteMap.Entry> fastIterable(Float2ByteMap float2ByteMap) {
        ObjectSet<Float2ByteMap.Entry> objectSet = float2ByteMap.float2ByteEntrySet();
        return objectSet instanceof Float2ByteMap.FastEntrySet ? new ObjectIterable<Float2ByteMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Float2ByteMap.Entry> iterator() {
                return ((Float2ByteMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Float2ByteMap.Entry> consumer) {
                ((Float2ByteMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Float2ByteMap singleton(float f, byte by) {
        return new Singleton(f, by);
    }

    public static Float2ByteMap singleton(Float f, Byte by) {
        return new Singleton(f.floatValue(), by);
    }

    public static Float2ByteMap synchronize(Float2ByteMap float2ByteMap) {
        return new SynchronizedMap(float2ByteMap);
    }

    public static Float2ByteMap synchronize(Float2ByteMap float2ByteMap, Object object) {
        return new SynchronizedMap(float2ByteMap, object);
    }

    public static Float2ByteMap unmodifiable(Float2ByteMap float2ByteMap) {
        return new UnmodifiableMap(float2ByteMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Float2ByteFunctions.UnmodifiableFunction
    implements Float2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2ByteMap map;
        protected transient ObjectSet<Float2ByteMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient ByteCollection values;

        protected UnmodifiableMap(Float2ByteMap float2ByteMap) {
            super(float2ByteMap);
            this.map = float2ByteMap;
        }

        @Override
        public boolean containsValue(byte by) {
            return this.map.containsValue(by);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Float, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2ByteMap.Entry> float2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.float2ByteEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Byte>> entrySet() {
            return this.float2ByteEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                return ByteCollections.unmodifiable(this.map.values());
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
        public byte getOrDefault(float f, byte by) {
            return this.map.getOrDefault(f, by);
        }

        @Override
        public void forEach(BiConsumer<? super Float, ? super Byte> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte putIfAbsent(float f, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(float f, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte replace(float f, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(float f, byte by, byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsentNullable(float f, DoubleFunction<? extends Byte> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsentPartial(float f, Float2ByteFunction float2ByteFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfPresent(float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte compute(float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte merge(float f, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte getOrDefault(Object object, Byte by) {
            return this.map.getOrDefault(object, by);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte replace(Float f, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Float f, Byte by, Byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte putIfAbsent(Float f, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte computeIfAbsent(Float f, Function<? super Float, ? extends Byte> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte computeIfPresent(Float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte compute(Float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte merge(Float f, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Byte>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Byte)object2, (Byte)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Byte)object2);
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
    extends Float2ByteFunctions.SynchronizedFunction
    implements Float2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2ByteMap map;
        protected transient ObjectSet<Float2ByteMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient ByteCollection values;

        protected SynchronizedMap(Float2ByteMap float2ByteMap, Object object) {
            super(float2ByteMap, object);
            this.map = float2ByteMap;
        }

        protected SynchronizedMap(Float2ByteMap float2ByteMap) {
            super(float2ByteMap);
            this.map = float2ByteMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(by);
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
        public void putAll(Map<? extends Float, ? extends Byte> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Float2ByteMap.Entry> float2ByteEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.float2ByteEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Byte>> entrySet() {
            return this.float2ByteEntrySet();
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
        public ByteCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return ByteCollections.synchronize(this.map.values(), this.sync);
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
        public byte getOrDefault(float f, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(f, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Float, ? super Byte> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte putIfAbsent(float f, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(float f, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(f, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte replace(float f, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(float f, byte by, byte by2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, by, by2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(f, doubleToIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsentNullable(float f, DoubleFunction<? extends Byte> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(f, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsentPartial(float f, Float2ByteFunction float2ByteFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(f, float2ByteFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfPresent(float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte compute(float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte merge(float f, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte getOrDefault(Object object, Byte by) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, by);
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
        public Byte replace(Float f, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Float f, Byte by, Byte by2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, by, by2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte putIfAbsent(Float f, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte computeIfAbsent(Float f, Function<? super Float, ? extends Byte> function) {
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
        public Byte computeIfPresent(Float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
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
        public Byte compute(Float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
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
        public Byte merge(Float f, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, by, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Byte>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Byte)object2, (Byte)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Byte)object2);
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
    extends Float2ByteFunctions.Singleton
    implements Float2ByteMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Float2ByteMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient ByteCollection values;

        protected Singleton(float f, byte by) {
            super(f, by);
        }

        @Override
        public boolean containsValue(byte by) {
            return this.value == by;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Byte)object == this.value;
        }

        @Override
        public void putAll(Map<? extends Float, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2ByteMap.Entry> float2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractFloat2ByteMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Byte>> entrySet() {
            return this.float2ByteEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                this.values = ByteSets.singleton(this.value);
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
    extends Float2ByteFunctions.EmptyFunction
    implements Float2ByteMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(byte by) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Float, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2ByteMap.Entry> float2ByteEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public FloatSet keySet() {
            return FloatSets.EMPTY_SET;
        }

        @Override
        public ByteCollection values() {
            return ByteSets.EMPTY_SET;
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

