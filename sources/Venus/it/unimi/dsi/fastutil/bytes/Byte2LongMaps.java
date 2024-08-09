/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByte2LongMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
import it.unimi.dsi.fastutil.bytes.Byte2LongFunctions;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSets;
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
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;

public final class Byte2LongMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Byte2LongMaps() {
    }

    public static ObjectIterator<Byte2LongMap.Entry> fastIterator(Byte2LongMap byte2LongMap) {
        ObjectSet<Byte2LongMap.Entry> objectSet = byte2LongMap.byte2LongEntrySet();
        return objectSet instanceof Byte2LongMap.FastEntrySet ? ((Byte2LongMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Byte2LongMap byte2LongMap, Consumer<? super Byte2LongMap.Entry> consumer) {
        ObjectSet<Byte2LongMap.Entry> objectSet = byte2LongMap.byte2LongEntrySet();
        if (objectSet instanceof Byte2LongMap.FastEntrySet) {
            ((Byte2LongMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Byte2LongMap.Entry> fastIterable(Byte2LongMap byte2LongMap) {
        ObjectSet<Byte2LongMap.Entry> objectSet = byte2LongMap.byte2LongEntrySet();
        return objectSet instanceof Byte2LongMap.FastEntrySet ? new ObjectIterable<Byte2LongMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Byte2LongMap.Entry> iterator() {
                return ((Byte2LongMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Byte2LongMap.Entry> consumer) {
                ((Byte2LongMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Byte2LongMap singleton(byte by, long l) {
        return new Singleton(by, l);
    }

    public static Byte2LongMap singleton(Byte by, Long l) {
        return new Singleton(by, l);
    }

    public static Byte2LongMap synchronize(Byte2LongMap byte2LongMap) {
        return new SynchronizedMap(byte2LongMap);
    }

    public static Byte2LongMap synchronize(Byte2LongMap byte2LongMap, Object object) {
        return new SynchronizedMap(byte2LongMap, object);
    }

    public static Byte2LongMap unmodifiable(Byte2LongMap byte2LongMap) {
        return new UnmodifiableMap(byte2LongMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Byte2LongFunctions.UnmodifiableFunction
    implements Byte2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2LongMap map;
        protected transient ObjectSet<Byte2LongMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient LongCollection values;

        protected UnmodifiableMap(Byte2LongMap byte2LongMap) {
            super(byte2LongMap);
            this.map = byte2LongMap;
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
        public void putAll(Map<? extends Byte, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.byte2LongEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Long>> entrySet() {
            return this.byte2LongEntrySet();
        }

        @Override
        public ByteSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSets.unmodifiable(this.map.keySet());
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
        public long getOrDefault(byte by, long l) {
            return this.map.getOrDefault(by, l);
        }

        @Override
        public void forEach(BiConsumer<? super Byte, ? super Long> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long putIfAbsent(byte by, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(byte by, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long replace(byte by, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(byte by, long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsent(byte by, IntToLongFunction intToLongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsentNullable(byte by, IntFunction<? extends Long> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsentPartial(byte by, Byte2LongFunction byte2LongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfPresent(byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long compute(byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long merge(byte by, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
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
        public Long replace(Byte by, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Byte by, Long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long putIfAbsent(Byte by, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long computeIfAbsent(Byte by, Function<? super Byte, ? extends Long> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long computeIfPresent(Byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long compute(Byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long merge(Byte by, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Byte)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Byte)object, (BiFunction<? super Byte, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Long>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Byte)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Byte)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Byte)object, (Long)object2);
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
    extends Byte2LongFunctions.SynchronizedFunction
    implements Byte2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2LongMap map;
        protected transient ObjectSet<Byte2LongMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient LongCollection values;

        protected SynchronizedMap(Byte2LongMap byte2LongMap, Object object) {
            super(byte2LongMap, object);
            this.map = byte2LongMap;
        }

        protected SynchronizedMap(Byte2LongMap byte2LongMap) {
            super(byte2LongMap);
            this.map = byte2LongMap;
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
        public void putAll(Map<? extends Byte, ? extends Long> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.byte2LongEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Long>> entrySet() {
            return this.byte2LongEntrySet();
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
        public long getOrDefault(byte by, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(by, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Byte, ? super Long> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long putIfAbsent(byte by, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(by, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(byte by, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(by, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long replace(byte by, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(byte by, long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsent(byte by, IntToLongFunction intToLongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(by, intToLongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsentNullable(byte by, IntFunction<? extends Long> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(by, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsentPartial(byte by, Byte2LongFunction byte2LongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(by, byte2LongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfPresent(byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long compute(byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long merge(byte by, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(by, l, biFunction);
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
        public Long replace(Byte by, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Byte by, Long l, Long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long putIfAbsent(Byte by, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(by, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long computeIfAbsent(Byte by, Function<? super Byte, ? extends Long> function) {
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
        public Long computeIfPresent(Byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
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
        public Long compute(Byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
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
        public Long merge(Byte by, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(by, l, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Byte)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Byte)object, (BiFunction<? super Byte, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Long>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Byte)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Byte)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Byte)object, (Long)object2);
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
    extends Byte2LongFunctions.Singleton
    implements Byte2LongMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Byte2LongMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient LongCollection values;

        protected Singleton(byte by, long l) {
            super(by, l);
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
        public void putAll(Map<? extends Byte, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractByte2LongMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Long>> entrySet() {
            return this.byte2LongEntrySet();
        }

        @Override
        public ByteSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSets.singleton(this.key);
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
            return this.key ^ HashCommon.long2int(this.value);
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
    extends Byte2LongFunctions.EmptyFunction
    implements Byte2LongMap,
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
        public void putAll(Map<? extends Byte, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ByteSet keySet() {
            return ByteSets.EMPTY_SET;
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

