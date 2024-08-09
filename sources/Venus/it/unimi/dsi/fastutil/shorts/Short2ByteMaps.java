/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ByteMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
import it.unimi.dsi.fastutil.shorts.Short2ByteFunctions;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
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
import java.util.function.IntUnaryOperator;

public final class Short2ByteMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Short2ByteMaps() {
    }

    public static ObjectIterator<Short2ByteMap.Entry> fastIterator(Short2ByteMap short2ByteMap) {
        ObjectSet<Short2ByteMap.Entry> objectSet = short2ByteMap.short2ByteEntrySet();
        return objectSet instanceof Short2ByteMap.FastEntrySet ? ((Short2ByteMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Short2ByteMap short2ByteMap, Consumer<? super Short2ByteMap.Entry> consumer) {
        ObjectSet<Short2ByteMap.Entry> objectSet = short2ByteMap.short2ByteEntrySet();
        if (objectSet instanceof Short2ByteMap.FastEntrySet) {
            ((Short2ByteMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Short2ByteMap.Entry> fastIterable(Short2ByteMap short2ByteMap) {
        ObjectSet<Short2ByteMap.Entry> objectSet = short2ByteMap.short2ByteEntrySet();
        return objectSet instanceof Short2ByteMap.FastEntrySet ? new ObjectIterable<Short2ByteMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Short2ByteMap.Entry> iterator() {
                return ((Short2ByteMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Short2ByteMap.Entry> consumer) {
                ((Short2ByteMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Short2ByteMap singleton(short s, byte by) {
        return new Singleton(s, by);
    }

    public static Short2ByteMap singleton(Short s, Byte by) {
        return new Singleton(s, by);
    }

    public static Short2ByteMap synchronize(Short2ByteMap short2ByteMap) {
        return new SynchronizedMap(short2ByteMap);
    }

    public static Short2ByteMap synchronize(Short2ByteMap short2ByteMap, Object object) {
        return new SynchronizedMap(short2ByteMap, object);
    }

    public static Short2ByteMap unmodifiable(Short2ByteMap short2ByteMap) {
        return new UnmodifiableMap(short2ByteMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Short2ByteFunctions.UnmodifiableFunction
    implements Short2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2ByteMap map;
        protected transient ObjectSet<Short2ByteMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient ByteCollection values;

        protected UnmodifiableMap(Short2ByteMap short2ByteMap) {
            super(short2ByteMap);
            this.map = short2ByteMap;
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
        public void putAll(Map<? extends Short, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2ByteMap.Entry> short2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.short2ByteEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Byte>> entrySet() {
            return this.short2ByteEntrySet();
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.unmodifiable(this.map.keySet());
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
        public byte getOrDefault(short s, byte by) {
            return this.map.getOrDefault(s, by);
        }

        @Override
        public void forEach(BiConsumer<? super Short, ? super Byte> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte putIfAbsent(short s, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(short s, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte replace(short s, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(short s, byte by, byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsentNullable(short s, IntFunction<? extends Byte> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsentPartial(short s, Short2ByteFunction short2ByteFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfPresent(short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte compute(short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte merge(short s, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
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
        public Byte replace(Short s, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Short s, Byte by, Byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte putIfAbsent(Short s, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte computeIfAbsent(Short s, Function<? super Short, ? extends Byte> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte computeIfPresent(Short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte compute(Short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte merge(Short s, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Short)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Short)object, (BiFunction<? super Short, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Byte>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Short)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Short)object, (Byte)object2, (Byte)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Short)object, (Byte)object2);
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
    extends Short2ByteFunctions.SynchronizedFunction
    implements Short2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2ByteMap map;
        protected transient ObjectSet<Short2ByteMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient ByteCollection values;

        protected SynchronizedMap(Short2ByteMap short2ByteMap, Object object) {
            super(short2ByteMap, object);
            this.map = short2ByteMap;
        }

        protected SynchronizedMap(Short2ByteMap short2ByteMap) {
            super(short2ByteMap);
            this.map = short2ByteMap;
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
        public void putAll(Map<? extends Short, ? extends Byte> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Short2ByteMap.Entry> short2ByteEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.short2ByteEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Byte>> entrySet() {
            return this.short2ByteEntrySet();
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
        public byte getOrDefault(short s, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(s, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Short, ? super Byte> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte putIfAbsent(short s, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(s, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(short s, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(s, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte replace(short s, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(short s, byte by, byte by2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, by, by2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(s, intUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsentNullable(short s, IntFunction<? extends Byte> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(s, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsentPartial(short s, Short2ByteFunction short2ByteFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(s, short2ByteFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfPresent(short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte compute(short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte merge(short s, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(s, by, biFunction);
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
        public Byte replace(Short s, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Short s, Byte by, Byte by2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, by, by2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte putIfAbsent(Short s, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(s, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte computeIfAbsent(Short s, Function<? super Short, ? extends Byte> function) {
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
        public Byte computeIfPresent(Short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
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
        public Byte compute(Short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
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
        public Byte merge(Short s, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(s, by, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Short)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Short)object, (BiFunction<? super Short, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Byte>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Short)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Short)object, (Byte)object2, (Byte)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Short)object, (Byte)object2);
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
    extends Short2ByteFunctions.Singleton
    implements Short2ByteMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Short2ByteMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient ByteCollection values;

        protected Singleton(short s, byte by) {
            super(s, by);
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
        public void putAll(Map<? extends Short, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2ByteMap.Entry> short2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractShort2ByteMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Byte>> entrySet() {
            return this.short2ByteEntrySet();
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.singleton(this.key);
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
            return this.key ^ this.value;
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
    extends Short2ByteFunctions.EmptyFunction
    implements Short2ByteMap,
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
        public void putAll(Map<? extends Short, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2ByteMap.Entry> short2ByteEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ShortSet keySet() {
            return ShortSets.EMPTY_SET;
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

