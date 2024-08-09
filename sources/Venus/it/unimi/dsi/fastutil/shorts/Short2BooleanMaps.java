/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
import it.unimi.dsi.fastutil.shorts.Short2BooleanFunctions;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
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
import java.util.function.IntPredicate;

public final class Short2BooleanMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Short2BooleanMaps() {
    }

    public static ObjectIterator<Short2BooleanMap.Entry> fastIterator(Short2BooleanMap short2BooleanMap) {
        ObjectSet<Short2BooleanMap.Entry> objectSet = short2BooleanMap.short2BooleanEntrySet();
        return objectSet instanceof Short2BooleanMap.FastEntrySet ? ((Short2BooleanMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Short2BooleanMap short2BooleanMap, Consumer<? super Short2BooleanMap.Entry> consumer) {
        ObjectSet<Short2BooleanMap.Entry> objectSet = short2BooleanMap.short2BooleanEntrySet();
        if (objectSet instanceof Short2BooleanMap.FastEntrySet) {
            ((Short2BooleanMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Short2BooleanMap.Entry> fastIterable(Short2BooleanMap short2BooleanMap) {
        ObjectSet<Short2BooleanMap.Entry> objectSet = short2BooleanMap.short2BooleanEntrySet();
        return objectSet instanceof Short2BooleanMap.FastEntrySet ? new ObjectIterable<Short2BooleanMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Short2BooleanMap.Entry> iterator() {
                return ((Short2BooleanMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Short2BooleanMap.Entry> consumer) {
                ((Short2BooleanMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Short2BooleanMap singleton(short s, boolean bl) {
        return new Singleton(s, bl);
    }

    public static Short2BooleanMap singleton(Short s, Boolean bl) {
        return new Singleton(s, bl);
    }

    public static Short2BooleanMap synchronize(Short2BooleanMap short2BooleanMap) {
        return new SynchronizedMap(short2BooleanMap);
    }

    public static Short2BooleanMap synchronize(Short2BooleanMap short2BooleanMap, Object object) {
        return new SynchronizedMap(short2BooleanMap, object);
    }

    public static Short2BooleanMap unmodifiable(Short2BooleanMap short2BooleanMap) {
        return new UnmodifiableMap(short2BooleanMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Short2BooleanFunctions.UnmodifiableFunction
    implements Short2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2BooleanMap map;
        protected transient ObjectSet<Short2BooleanMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient BooleanCollection values;

        protected UnmodifiableMap(Short2BooleanMap short2BooleanMap) {
            super(short2BooleanMap);
            this.map = short2BooleanMap;
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
        public void putAll(Map<? extends Short, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.short2BooleanEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Boolean>> entrySet() {
            return this.short2BooleanEntrySet();
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.unmodifiable(this.map.keySet());
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
        public boolean getOrDefault(short s, boolean bl) {
            return this.map.getOrDefault(s, bl);
        }

        @Override
        public void forEach(BiConsumer<? super Short, ? super Boolean> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putIfAbsent(short s, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(short s, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(short s, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(short s, boolean bl, boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsent(short s, IntPredicate intPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsentNullable(short s, IntFunction<? extends Boolean> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsentPartial(short s, Short2BooleanFunction short2BooleanFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfPresent(short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean compute(short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean merge(short s, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean replace(Short s, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Short s, Boolean bl, Boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean putIfAbsent(Short s, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean computeIfAbsent(Short s, Function<? super Short, ? extends Boolean> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean computeIfPresent(Short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean compute(Short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean merge(Short s, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Short)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Short)object, (BiFunction<? super Short, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Boolean>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Short)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Short)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Short)object, (Boolean)object2);
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
    extends Short2BooleanFunctions.SynchronizedFunction
    implements Short2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2BooleanMap map;
        protected transient ObjectSet<Short2BooleanMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient BooleanCollection values;

        protected SynchronizedMap(Short2BooleanMap short2BooleanMap, Object object) {
            super(short2BooleanMap, object);
            this.map = short2BooleanMap;
        }

        protected SynchronizedMap(Short2BooleanMap short2BooleanMap) {
            super(short2BooleanMap);
            this.map = short2BooleanMap;
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
        public void putAll(Map<? extends Short, ? extends Boolean> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.short2BooleanEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Boolean>> entrySet() {
            return this.short2BooleanEntrySet();
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
        public boolean getOrDefault(short s, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(s, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Short, ? super Boolean> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean putIfAbsent(short s, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(s, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(short s, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(s, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(short s, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(short s, boolean bl, boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsent(short s, IntPredicate intPredicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(s, intPredicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsentNullable(short s, IntFunction<? extends Boolean> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(s, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsentPartial(short s, Short2BooleanFunction short2BooleanFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(s, short2BooleanFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfPresent(short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean compute(short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean merge(short s, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(s, bl, biFunction);
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
        public Boolean replace(Short s, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Short s, Boolean bl, Boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean putIfAbsent(Short s, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(s, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean computeIfAbsent(Short s, Function<? super Short, ? extends Boolean> function) {
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
        public Boolean computeIfPresent(Short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean compute(Short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean merge(Short s, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(s, bl, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Short)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Short)object, (BiFunction<? super Short, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Boolean>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Short)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Short)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Short)object, (Boolean)object2);
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
    extends Short2BooleanFunctions.Singleton
    implements Short2BooleanMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Short2BooleanMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient BooleanCollection values;

        protected Singleton(short s, boolean bl) {
            super(s, bl);
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
        public void putAll(Map<? extends Short, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractShort2BooleanMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Boolean>> entrySet() {
            return this.short2BooleanEntrySet();
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.singleton(this.key);
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
            return this.key ^ (this.value ? 1231 : 1237);
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
    extends Short2BooleanFunctions.EmptyFunction
    implements Short2BooleanMap,
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
        public void putAll(Map<? extends Short, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ShortSet keySet() {
            return ShortSets.EMPTY_SET;
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

