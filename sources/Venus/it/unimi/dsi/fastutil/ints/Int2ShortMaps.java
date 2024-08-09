/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
import it.unimi.dsi.fastutil.ints.Int2ShortFunctions;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollections;
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

public final class Int2ShortMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Int2ShortMaps() {
    }

    public static ObjectIterator<Int2ShortMap.Entry> fastIterator(Int2ShortMap int2ShortMap) {
        ObjectSet<Int2ShortMap.Entry> objectSet = int2ShortMap.int2ShortEntrySet();
        return objectSet instanceof Int2ShortMap.FastEntrySet ? ((Int2ShortMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Int2ShortMap int2ShortMap, Consumer<? super Int2ShortMap.Entry> consumer) {
        ObjectSet<Int2ShortMap.Entry> objectSet = int2ShortMap.int2ShortEntrySet();
        if (objectSet instanceof Int2ShortMap.FastEntrySet) {
            ((Int2ShortMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Int2ShortMap.Entry> fastIterable(Int2ShortMap int2ShortMap) {
        ObjectSet<Int2ShortMap.Entry> objectSet = int2ShortMap.int2ShortEntrySet();
        return objectSet instanceof Int2ShortMap.FastEntrySet ? new ObjectIterable<Int2ShortMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Int2ShortMap.Entry> iterator() {
                return ((Int2ShortMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Int2ShortMap.Entry> consumer) {
                ((Int2ShortMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Int2ShortMap singleton(int n, short s) {
        return new Singleton(n, s);
    }

    public static Int2ShortMap singleton(Integer n, Short s) {
        return new Singleton(n, s);
    }

    public static Int2ShortMap synchronize(Int2ShortMap int2ShortMap) {
        return new SynchronizedMap(int2ShortMap);
    }

    public static Int2ShortMap synchronize(Int2ShortMap int2ShortMap, Object object) {
        return new SynchronizedMap(int2ShortMap, object);
    }

    public static Int2ShortMap unmodifiable(Int2ShortMap int2ShortMap) {
        return new UnmodifiableMap(int2ShortMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Int2ShortFunctions.UnmodifiableFunction
    implements Int2ShortMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ShortMap map;
        protected transient ObjectSet<Int2ShortMap.Entry> entries;
        protected transient IntSet keys;
        protected transient ShortCollection values;

        protected UnmodifiableMap(Int2ShortMap int2ShortMap) {
            super(int2ShortMap);
            this.map = int2ShortMap;
        }

        @Override
        public boolean containsValue(short s) {
            return this.map.containsValue(s);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2ShortMap.Entry> int2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.int2ShortEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Short>> entrySet() {
            return this.int2ShortEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ShortCollection values() {
            if (this.values == null) {
                return ShortCollections.unmodifiable(this.map.values());
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
        public short getOrDefault(int n, short s) {
            return this.map.getOrDefault(n, s);
        }

        @Override
        public void forEach(BiConsumer<? super Integer, ? super Short> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short putIfAbsent(int n, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(int n, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short replace(int n, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(int n, short s, short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsent(int n, IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsentNullable(int n, IntFunction<? extends Short> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsentPartial(int n, Int2ShortFunction int2ShortFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfPresent(int n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short compute(int n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short merge(int n, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short getOrDefault(Object object, Short s) {
            return this.map.getOrDefault(object, s);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short replace(Integer n, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Integer n, Short s, Short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short putIfAbsent(Integer n, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short computeIfAbsent(Integer n, Function<? super Integer, ? extends Short> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short computeIfPresent(Integer n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short compute(Integer n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short merge(Integer n, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Integer)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Integer)object, (BiFunction<? super Integer, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Short>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Integer)object, (Short)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Integer)object, (Short)object2, (Short)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Integer)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Short)object2);
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
    extends Int2ShortFunctions.SynchronizedFunction
    implements Int2ShortMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ShortMap map;
        protected transient ObjectSet<Int2ShortMap.Entry> entries;
        protected transient IntSet keys;
        protected transient ShortCollection values;

        protected SynchronizedMap(Int2ShortMap int2ShortMap, Object object) {
            super(int2ShortMap, object);
            this.map = int2ShortMap;
        }

        protected SynchronizedMap(Int2ShortMap int2ShortMap) {
            super(int2ShortMap);
            this.map = int2ShortMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(s);
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
        public void putAll(Map<? extends Integer, ? extends Short> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Int2ShortMap.Entry> int2ShortEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.int2ShortEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Short>> entrySet() {
            return this.int2ShortEntrySet();
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
        public ShortCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return ShortCollections.synchronize(this.map.values(), this.sync);
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
        public short getOrDefault(int n, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Integer, ? super Short> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short putIfAbsent(int n, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(int n, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short replace(int n, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(int n, short s, short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsent(int n, IntUnaryOperator intUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(n, intUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsentNullable(int n, IntFunction<? extends Short> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(n, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsentPartial(int n, Int2ShortFunction int2ShortFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(n, int2ShortFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfPresent(int n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short compute(int n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short merge(int n, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(n, s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short getOrDefault(Object object, Short s) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, s);
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
        public Short replace(Integer n, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Integer n, Short s, Short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short putIfAbsent(Integer n, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(n, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short computeIfAbsent(Integer n, Function<? super Integer, ? extends Short> function) {
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
        public Short computeIfPresent(Integer n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
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
        public Short compute(Integer n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
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
        public Short merge(Integer n, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(n, s, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Integer)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Integer)object, (BiFunction<? super Integer, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Short>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Integer)object, (Short)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Integer)object, (Short)object2, (Short)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Integer)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Short)object2);
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
    extends Int2ShortFunctions.Singleton
    implements Int2ShortMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2ShortMap.Entry> entries;
        protected transient IntSet keys;
        protected transient ShortCollection values;

        protected Singleton(int n, short s) {
            super(n, s);
        }

        @Override
        public boolean containsValue(short s) {
            return this.value == s;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Short)object == this.value;
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2ShortMap.Entry> int2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2ShortMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Short>> entrySet() {
            return this.int2ShortEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ShortCollection values() {
            if (this.values == null) {
                this.values = ShortSets.singleton(this.value);
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
    extends Int2ShortFunctions.EmptyFunction
    implements Int2ShortMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(short s) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2ShortMap.Entry> int2ShortEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
        }

        @Override
        public ShortCollection values() {
            return ShortSets.EMPTY_SET;
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

