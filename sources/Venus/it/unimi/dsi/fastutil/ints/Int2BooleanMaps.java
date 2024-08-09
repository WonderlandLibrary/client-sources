/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import it.unimi.dsi.fastutil.ints.AbstractInt2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunctions;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import java.util.function.IntPredicate;

public final class Int2BooleanMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Int2BooleanMaps() {
    }

    public static ObjectIterator<Int2BooleanMap.Entry> fastIterator(Int2BooleanMap int2BooleanMap) {
        ObjectSet<Int2BooleanMap.Entry> objectSet = int2BooleanMap.int2BooleanEntrySet();
        return objectSet instanceof Int2BooleanMap.FastEntrySet ? ((Int2BooleanMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Int2BooleanMap int2BooleanMap, Consumer<? super Int2BooleanMap.Entry> consumer) {
        ObjectSet<Int2BooleanMap.Entry> objectSet = int2BooleanMap.int2BooleanEntrySet();
        if (objectSet instanceof Int2BooleanMap.FastEntrySet) {
            ((Int2BooleanMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Int2BooleanMap.Entry> fastIterable(Int2BooleanMap int2BooleanMap) {
        ObjectSet<Int2BooleanMap.Entry> objectSet = int2BooleanMap.int2BooleanEntrySet();
        return objectSet instanceof Int2BooleanMap.FastEntrySet ? new ObjectIterable<Int2BooleanMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Int2BooleanMap.Entry> iterator() {
                return ((Int2BooleanMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Int2BooleanMap.Entry> consumer) {
                ((Int2BooleanMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Int2BooleanMap singleton(int n, boolean bl) {
        return new Singleton(n, bl);
    }

    public static Int2BooleanMap singleton(Integer n, Boolean bl) {
        return new Singleton(n, bl);
    }

    public static Int2BooleanMap synchronize(Int2BooleanMap int2BooleanMap) {
        return new SynchronizedMap(int2BooleanMap);
    }

    public static Int2BooleanMap synchronize(Int2BooleanMap int2BooleanMap, Object object) {
        return new SynchronizedMap(int2BooleanMap, object);
    }

    public static Int2BooleanMap unmodifiable(Int2BooleanMap int2BooleanMap) {
        return new UnmodifiableMap(int2BooleanMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Int2BooleanFunctions.UnmodifiableFunction
    implements Int2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2BooleanMap map;
        protected transient ObjectSet<Int2BooleanMap.Entry> entries;
        protected transient IntSet keys;
        protected transient BooleanCollection values;

        protected UnmodifiableMap(Int2BooleanMap int2BooleanMap) {
            super(int2BooleanMap);
            this.map = int2BooleanMap;
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
        public void putAll(Map<? extends Integer, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.int2BooleanEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Boolean>> entrySet() {
            return this.int2BooleanEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.unmodifiable(this.map.keySet());
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
        public boolean getOrDefault(int n, boolean bl) {
            return this.map.getOrDefault(n, bl);
        }

        @Override
        public void forEach(BiConsumer<? super Integer, ? super Boolean> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putIfAbsent(int n, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(int n, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(int n, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(int n, boolean bl, boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsent(int n, IntPredicate intPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsentNullable(int n, IntFunction<? extends Boolean> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsentPartial(int n, Int2BooleanFunction int2BooleanFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfPresent(int n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean compute(int n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean merge(int n, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean replace(Integer n, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Integer n, Boolean bl, Boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean putIfAbsent(Integer n, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean computeIfAbsent(Integer n, Function<? super Integer, ? extends Boolean> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean computeIfPresent(Integer n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean compute(Integer n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean merge(Integer n, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Integer)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Integer)object, (BiFunction<? super Integer, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Boolean>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Integer)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Integer)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Integer)object, (Boolean)object2);
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
    extends Int2BooleanFunctions.SynchronizedFunction
    implements Int2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2BooleanMap map;
        protected transient ObjectSet<Int2BooleanMap.Entry> entries;
        protected transient IntSet keys;
        protected transient BooleanCollection values;

        protected SynchronizedMap(Int2BooleanMap int2BooleanMap, Object object) {
            super(int2BooleanMap, object);
            this.map = int2BooleanMap;
        }

        protected SynchronizedMap(Int2BooleanMap int2BooleanMap) {
            super(int2BooleanMap);
            this.map = int2BooleanMap;
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
        public void putAll(Map<? extends Integer, ? extends Boolean> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.int2BooleanEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Boolean>> entrySet() {
            return this.int2BooleanEntrySet();
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
        public boolean getOrDefault(int n, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Integer, ? super Boolean> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean putIfAbsent(int n, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(int n, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(int n, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(int n, boolean bl, boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsent(int n, IntPredicate intPredicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(n, intPredicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsentNullable(int n, IntFunction<? extends Boolean> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(n, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsentPartial(int n, Int2BooleanFunction int2BooleanFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(n, int2BooleanFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfPresent(int n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean compute(int n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean merge(int n, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(n, bl, biFunction);
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
        public Boolean replace(Integer n, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Integer n, Boolean bl, Boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(n, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean putIfAbsent(Integer n, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(n, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean computeIfAbsent(Integer n, Function<? super Integer, ? extends Boolean> function) {
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
        public Boolean computeIfPresent(Integer n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean compute(Integer n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean merge(Integer n, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(n, bl, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Integer)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Integer)object, (BiFunction<? super Integer, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Boolean>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Integer)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Integer)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Integer)object, (Boolean)object2);
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
    extends Int2BooleanFunctions.Singleton
    implements Int2BooleanMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2BooleanMap.Entry> entries;
        protected transient IntSet keys;
        protected transient BooleanCollection values;

        protected Singleton(int n, boolean bl) {
            super(n, bl);
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
        public void putAll(Map<? extends Integer, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2BooleanMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, Boolean>> entrySet() {
            return this.int2BooleanEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
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
    extends Int2BooleanFunctions.EmptyFunction
    implements Int2BooleanMap,
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
        public void putAll(Map<? extends Integer, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
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

