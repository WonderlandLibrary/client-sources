/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunction;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunctions;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSets;
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

public final class Byte2BooleanMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Byte2BooleanMaps() {
    }

    public static ObjectIterator<Byte2BooleanMap.Entry> fastIterator(Byte2BooleanMap byte2BooleanMap) {
        ObjectSet<Byte2BooleanMap.Entry> objectSet = byte2BooleanMap.byte2BooleanEntrySet();
        return objectSet instanceof Byte2BooleanMap.FastEntrySet ? ((Byte2BooleanMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Byte2BooleanMap byte2BooleanMap, Consumer<? super Byte2BooleanMap.Entry> consumer) {
        ObjectSet<Byte2BooleanMap.Entry> objectSet = byte2BooleanMap.byte2BooleanEntrySet();
        if (objectSet instanceof Byte2BooleanMap.FastEntrySet) {
            ((Byte2BooleanMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Byte2BooleanMap.Entry> fastIterable(Byte2BooleanMap byte2BooleanMap) {
        ObjectSet<Byte2BooleanMap.Entry> objectSet = byte2BooleanMap.byte2BooleanEntrySet();
        return objectSet instanceof Byte2BooleanMap.FastEntrySet ? new ObjectIterable<Byte2BooleanMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Byte2BooleanMap.Entry> iterator() {
                return ((Byte2BooleanMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Byte2BooleanMap.Entry> consumer) {
                ((Byte2BooleanMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Byte2BooleanMap singleton(byte by, boolean bl) {
        return new Singleton(by, bl);
    }

    public static Byte2BooleanMap singleton(Byte by, Boolean bl) {
        return new Singleton(by, bl);
    }

    public static Byte2BooleanMap synchronize(Byte2BooleanMap byte2BooleanMap) {
        return new SynchronizedMap(byte2BooleanMap);
    }

    public static Byte2BooleanMap synchronize(Byte2BooleanMap byte2BooleanMap, Object object) {
        return new SynchronizedMap(byte2BooleanMap, object);
    }

    public static Byte2BooleanMap unmodifiable(Byte2BooleanMap byte2BooleanMap) {
        return new UnmodifiableMap(byte2BooleanMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Byte2BooleanFunctions.UnmodifiableFunction
    implements Byte2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2BooleanMap map;
        protected transient ObjectSet<Byte2BooleanMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient BooleanCollection values;

        protected UnmodifiableMap(Byte2BooleanMap byte2BooleanMap) {
            super(byte2BooleanMap);
            this.map = byte2BooleanMap;
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
        public void putAll(Map<? extends Byte, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2BooleanMap.Entry> byte2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.byte2BooleanEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Boolean>> entrySet() {
            return this.byte2BooleanEntrySet();
        }

        @Override
        public ByteSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSets.unmodifiable(this.map.keySet());
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
        public boolean getOrDefault(byte by, boolean bl) {
            return this.map.getOrDefault(by, bl);
        }

        @Override
        public void forEach(BiConsumer<? super Byte, ? super Boolean> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putIfAbsent(byte by, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(byte by, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(byte by, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(byte by, boolean bl, boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsent(byte by, IntPredicate intPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsentNullable(byte by, IntFunction<? extends Boolean> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfAbsentPartial(byte by, Byte2BooleanFunction byte2BooleanFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean computeIfPresent(byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean compute(byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean merge(byte by, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean replace(Byte by, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Byte by, Boolean bl, Boolean bl2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean putIfAbsent(Byte by, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean computeIfAbsent(Byte by, Function<? super Byte, ? extends Boolean> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean computeIfPresent(Byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean compute(Byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean merge(Byte by, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Byte)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Byte)object, (BiFunction<? super Byte, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Boolean>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Byte)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Byte)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Byte)object, (Boolean)object2);
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
    extends Byte2BooleanFunctions.SynchronizedFunction
    implements Byte2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2BooleanMap map;
        protected transient ObjectSet<Byte2BooleanMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient BooleanCollection values;

        protected SynchronizedMap(Byte2BooleanMap byte2BooleanMap, Object object) {
            super(byte2BooleanMap, object);
            this.map = byte2BooleanMap;
        }

        protected SynchronizedMap(Byte2BooleanMap byte2BooleanMap) {
            super(byte2BooleanMap);
            this.map = byte2BooleanMap;
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
        public void putAll(Map<? extends Byte, ? extends Boolean> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Byte2BooleanMap.Entry> byte2BooleanEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.byte2BooleanEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Boolean>> entrySet() {
            return this.byte2BooleanEntrySet();
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
        public boolean getOrDefault(byte by, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(by, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Byte, ? super Boolean> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean putIfAbsent(byte by, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(by, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(byte by, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(by, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(byte by, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(byte by, boolean bl, boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsent(byte by, IntPredicate intPredicate) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(by, intPredicate);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsentNullable(byte by, IntFunction<? extends Boolean> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(by, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfAbsentPartial(byte by, Byte2BooleanFunction byte2BooleanFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(by, byte2BooleanFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean computeIfPresent(byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean compute(byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean merge(byte by, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(by, bl, biFunction);
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
        public Boolean replace(Byte by, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Byte by, Boolean bl, Boolean bl2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, bl, bl2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean putIfAbsent(Byte by, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(by, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean computeIfAbsent(Byte by, Function<? super Byte, ? extends Boolean> function) {
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
        public Boolean computeIfPresent(Byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean compute(Byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
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
        public Boolean merge(Byte by, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(by, bl, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Byte)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Byte)object, (BiFunction<? super Byte, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Boolean, ? extends Boolean>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Boolean>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Byte)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Byte)object, (Boolean)object2, (Boolean)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Byte)object, (Boolean)object2);
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
    extends Byte2BooleanFunctions.Singleton
    implements Byte2BooleanMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Byte2BooleanMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient BooleanCollection values;

        protected Singleton(byte by, boolean bl) {
            super(by, bl);
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
        public void putAll(Map<? extends Byte, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2BooleanMap.Entry> byte2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractByte2BooleanMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Boolean>> entrySet() {
            return this.byte2BooleanEntrySet();
        }

        @Override
        public ByteSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSets.singleton(this.key);
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
    extends Byte2BooleanFunctions.EmptyFunction
    implements Byte2BooleanMap,
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
        public void putAll(Map<? extends Byte, ? extends Boolean> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2BooleanMap.Entry> byte2BooleanEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ByteSet keySet() {
            return ByteSets.EMPTY_SET;
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

