/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2IntMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
import it.unimi.dsi.fastutil.bytes.Byte2IntFunctions;
import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSets;
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
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public final class Byte2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Byte2IntMaps() {
    }

    public static ObjectIterator<Byte2IntMap.Entry> fastIterator(Byte2IntMap byte2IntMap) {
        ObjectSet<Byte2IntMap.Entry> objectSet = byte2IntMap.byte2IntEntrySet();
        return objectSet instanceof Byte2IntMap.FastEntrySet ? ((Byte2IntMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Byte2IntMap byte2IntMap, Consumer<? super Byte2IntMap.Entry> consumer) {
        ObjectSet<Byte2IntMap.Entry> objectSet = byte2IntMap.byte2IntEntrySet();
        if (objectSet instanceof Byte2IntMap.FastEntrySet) {
            ((Byte2IntMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Byte2IntMap.Entry> fastIterable(Byte2IntMap byte2IntMap) {
        ObjectSet<Byte2IntMap.Entry> objectSet = byte2IntMap.byte2IntEntrySet();
        return objectSet instanceof Byte2IntMap.FastEntrySet ? new ObjectIterable<Byte2IntMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Byte2IntMap.Entry> iterator() {
                return ((Byte2IntMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Byte2IntMap.Entry> consumer) {
                ((Byte2IntMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Byte2IntMap singleton(byte by, int n) {
        return new Singleton(by, n);
    }

    public static Byte2IntMap singleton(Byte by, Integer n) {
        return new Singleton(by, n);
    }

    public static Byte2IntMap synchronize(Byte2IntMap byte2IntMap) {
        return new SynchronizedMap(byte2IntMap);
    }

    public static Byte2IntMap synchronize(Byte2IntMap byte2IntMap, Object object) {
        return new SynchronizedMap(byte2IntMap, object);
    }

    public static Byte2IntMap unmodifiable(Byte2IntMap byte2IntMap) {
        return new UnmodifiableMap(byte2IntMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Byte2IntFunctions.UnmodifiableFunction
    implements Byte2IntMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2IntMap map;
        protected transient ObjectSet<Byte2IntMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient IntCollection values;

        protected UnmodifiableMap(Byte2IntMap byte2IntMap) {
            super(byte2IntMap);
            this.map = byte2IntMap;
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
        public void putAll(Map<? extends Byte, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2IntMap.Entry> byte2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.byte2IntEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Integer>> entrySet() {
            return this.byte2IntEntrySet();
        }

        @Override
        public ByteSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSets.unmodifiable(this.map.keySet());
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
        public int getOrDefault(byte by, int n) {
            return this.map.getOrDefault(by, n);
        }

        @Override
        public void forEach(BiConsumer<? super Byte, ? super Integer> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int putIfAbsent(byte by, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(byte by, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int replace(byte by, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(byte by, int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsent(byte by, IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsentNullable(byte by, IntFunction<? extends Integer> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsentPartial(byte by, Byte2IntFunction byte2IntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfPresent(byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compute(byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int merge(byte by, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer replace(Byte by, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Byte by, Integer n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer putIfAbsent(Byte by, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer computeIfAbsent(Byte by, Function<? super Byte, ? extends Integer> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer computeIfPresent(Byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer compute(Byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer merge(Byte by, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Byte)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Byte)object, (BiFunction<? super Byte, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Integer>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Byte)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Byte)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Byte)object, (Integer)object2);
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
    extends Byte2IntFunctions.SynchronizedFunction
    implements Byte2IntMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2IntMap map;
        protected transient ObjectSet<Byte2IntMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient IntCollection values;

        protected SynchronizedMap(Byte2IntMap byte2IntMap, Object object) {
            super(byte2IntMap, object);
            this.map = byte2IntMap;
        }

        protected SynchronizedMap(Byte2IntMap byte2IntMap) {
            super(byte2IntMap);
            this.map = byte2IntMap;
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
        public void putAll(Map<? extends Byte, ? extends Integer> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Byte2IntMap.Entry> byte2IntEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.byte2IntEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Integer>> entrySet() {
            return this.byte2IntEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = ByteSets.synchronize(this.map.keySet(), this.sync);
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
        public int getOrDefault(byte by, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(by, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Byte, ? super Integer> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int putIfAbsent(byte by, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(by, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(byte by, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(by, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int replace(byte by, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(byte by, int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsent(byte by, IntUnaryOperator intUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(by, intUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsentNullable(byte by, IntFunction<? extends Integer> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(by, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsentPartial(byte by, Byte2IntFunction byte2IntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(by, byte2IntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfPresent(byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compute(byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int merge(byte by, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(by, n, biFunction);
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
        public Integer replace(Byte by, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Byte by, Integer n, Integer n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer putIfAbsent(Byte by, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(by, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer computeIfAbsent(Byte by, Function<? super Byte, ? extends Integer> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(by, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer computeIfPresent(Byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer compute(Byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer merge(Byte by, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(by, n, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Byte)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Byte)object, (BiFunction<? super Byte, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Integer>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Byte)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Byte)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Byte)object, (Integer)object2);
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
    extends Byte2IntFunctions.Singleton
    implements Byte2IntMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Byte2IntMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient IntCollection values;

        protected Singleton(byte by, int n) {
            super(by, n);
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
        public void putAll(Map<? extends Byte, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2IntMap.Entry> byte2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractByte2IntMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Integer>> entrySet() {
            return this.byte2IntEntrySet();
        }

        @Override
        public ByteSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSets.singleton(this.key);
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
    extends Byte2IntFunctions.EmptyFunction
    implements Byte2IntMap,
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
        public void putAll(Map<? extends Byte, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2IntMap.Entry> byte2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ByteSet keySet() {
            return ByteSets.EMPTY_SET;
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

