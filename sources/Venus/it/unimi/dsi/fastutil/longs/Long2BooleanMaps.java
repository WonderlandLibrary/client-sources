/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import it.unimi.dsi.fastutil.longs.AbstractLong2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
import it.unimi.dsi.fastutil.longs.Long2BooleanFunctions;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.LongSet;
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
import java.util.function.LongFunction;
import java.util.function.LongPredicate;

public final class Long2BooleanMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Long2BooleanMaps() {
    }

    public static ObjectIterator<Long2BooleanMap.Entry> fastIterator(Long2BooleanMap long2BooleanMap) {
        ObjectSet<Long2BooleanMap.Entry> objectSet = long2BooleanMap.long2BooleanEntrySet();
        return objectSet instanceof Long2BooleanMap.FastEntrySet ? ((Long2BooleanMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Long2BooleanMap long2BooleanMap, Consumer<? super Long2BooleanMap.Entry> consumer) {
        ObjectSet<Long2BooleanMap.Entry> objectSet = long2BooleanMap.long2BooleanEntrySet();
        if (objectSet instanceof Long2BooleanMap.FastEntrySet) {
            ((Long2BooleanMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Long2BooleanMap.Entry> fastIterable(Long2BooleanMap long2BooleanMap) {
        ObjectSet<Long2BooleanMap.Entry> objectSet = long2BooleanMap.long2BooleanEntrySet();
        return objectSet instanceof Long2BooleanMap.FastEntrySet ? new ObjectIterable<Long2BooleanMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Long2BooleanMap.Entry> iterator() {
                return ((Long2BooleanMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Long2BooleanMap.Entry> consumer) {
                ((Long2BooleanMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Long2BooleanMap singleton(long l, boolean bl) {
        return new Singleton(l, bl);
    }

    public static Long2BooleanMap singleton(Long l, Boolean bl) {
        return new Singleton(l, bl);
    }

    public static Long2BooleanMap synchronize(Long2BooleanMap long2BooleanMap) {
        return new SynchronizedMap(long2BooleanMap);
    }

    public static Long2BooleanMap synchronize(Long2BooleanMap long2BooleanMap, Object object) {
        return new SynchronizedMap(long2BooleanMap, object);
    }

    public static Long2BooleanMap unmodifiable(Long2BooleanMap long2BooleanMap) {
        return new UnmodifiableMap(long2BooleanMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Long2BooleanFunctions.UnmodifiableFunction
    implements Long2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanMap map;
        protected transient ObjectSet<Long2BooleanMap.Entry> entries;
        protected transient LongSet keys;
        protected transient BooleanCollection values;

        protected UnmodifiableMap(Long2BooleanMap long2BooleanMap) {
            super(long2BooleanMap);
            this.map = long2BooleanMap;
        }

        @Override
        public boolean containsValue(boolean bl) {
            return this.map.containsValue(bl);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.long2BooleanEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
            return this.long2BooleanEntrySet();
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public BooleanCollection values() {
            if (this.values == null) {
                return BooleanCollections.unmodifiable(this.map.values());
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
        public boolean getOrDefault(long l, boolean bl) {
            return this.map.getOrDefault(l, bl);
        }

        @Override
        public void forEach(BiConsumer<? super Long, ? super Boolean> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putIfAbsent(long l, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(long l, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(long l, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(long l, boolean bl, boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsent(long l, LongPredicate longPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsentNullable(long l, LongFunction<? extends Boolean> longFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsentPartial(long l, Long2BooleanFunction long2BooleanFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfPresent(long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean compute(long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean merge(long l, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean getOrDefault(Object object, Boolean bl) {
            return this.map.getOrDefault(object, bl);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean replace(Long l, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Long l, Boolean bl, Boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean putIfAbsent(Long l, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean computeIfAbsent(Long l, Function<? super Long, ? extends Boolean> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean computeIfPresent(Long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean compute(Long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean merge(Long l, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Long)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Long)object, (BiFunction<? super Long, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Boolean>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Long)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Long)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Long)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Boolean)object2);
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
    extends Long2BooleanFunctions.SynchronizedFunction
    implements Long2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanMap map;
        protected transient ObjectSet<Long2BooleanMap.Entry> entries;
        protected transient LongSet keys;
        protected transient BooleanCollection values;

        protected SynchronizedMap(Long2BooleanMap long2BooleanMap, Object object) {
            super(long2BooleanMap, object);
            this.map = long2BooleanMap;
        }

        protected SynchronizedMap(Long2BooleanMap long2BooleanMap) {
            super(long2BooleanMap);
            this.map = long2BooleanMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(bl);
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
        public void putAll(Map<? extends Long, ? extends Boolean> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.long2BooleanEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
            return this.long2BooleanEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = LongSets.synchronize(this.map.keySet(), this.sync);
                }
                return this.keys;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public BooleanCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return BooleanCollections.synchronize(this.map.values(), this.sync);
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
        public boolean getOrDefault(long l, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Long, ? super Boolean> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean putIfAbsent(long l, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(long l, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(long l, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(long l, boolean bl, boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsent(long l, LongPredicate longPredicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(l, longPredicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsentNullable(long l, LongFunction<? extends Boolean> longFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(l, longFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsentPartial(long l, Long2BooleanFunction long2BooleanFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(l, long2BooleanFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfPresent(long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean compute(long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean merge(long l, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(l, bl, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean getOrDefault(Object object, Boolean bl) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, bl);
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
        public Boolean replace(Long l, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Long l, Boolean bl, Boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(l, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean putIfAbsent(Long l, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(l, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean computeIfAbsent(Long l, Function<? super Long, ? extends Boolean> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(l, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean computeIfPresent(Long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean compute(Long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean merge(Long l, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(l, bl, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Long)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Long)object, (BiFunction<? super Long, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Boolean>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Long)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Long)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Long)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Boolean)object2);
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
    extends Long2BooleanFunctions.Singleton
    implements Long2BooleanMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Long2BooleanMap.Entry> entries;
        protected transient LongSet keys;
        protected transient BooleanCollection values;

        protected Singleton(long l, boolean bl) {
            super(l, bl);
        }

        @Override
        public boolean containsValue(boolean bl) {
            return this.value == bl;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Boolean)object == this.value;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractLong2BooleanMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
            return this.long2BooleanEntrySet();
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public BooleanCollection values() {
            if (this.values == null) {
                this.values = BooleanSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ (this.value ? 1231 : 1237);
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
    extends Long2BooleanFunctions.EmptyFunction
    implements Long2BooleanMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(boolean bl) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public LongSet keySet() {
            return LongSets.EMPTY_SET;
        }

        @Override
        public BooleanCollection values() {
            return BooleanSets.EMPTY_SET;
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

