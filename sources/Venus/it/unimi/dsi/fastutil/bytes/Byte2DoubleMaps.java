/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunctions;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
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
import java.util.function.IntToDoubleFunction;

public final class Byte2DoubleMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Byte2DoubleMaps() {
    }

    public static ObjectIterator<Byte2DoubleMap.Entry> fastIterator(Byte2DoubleMap byte2DoubleMap) {
        ObjectSet<Byte2DoubleMap.Entry> objectSet = byte2DoubleMap.byte2DoubleEntrySet();
        return objectSet instanceof Byte2DoubleMap.FastEntrySet ? ((Byte2DoubleMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Byte2DoubleMap byte2DoubleMap, Consumer<? super Byte2DoubleMap.Entry> consumer) {
        ObjectSet<Byte2DoubleMap.Entry> objectSet = byte2DoubleMap.byte2DoubleEntrySet();
        if (objectSet instanceof Byte2DoubleMap.FastEntrySet) {
            ((Byte2DoubleMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Byte2DoubleMap.Entry> fastIterable(Byte2DoubleMap byte2DoubleMap) {
        ObjectSet<Byte2DoubleMap.Entry> objectSet = byte2DoubleMap.byte2DoubleEntrySet();
        return objectSet instanceof Byte2DoubleMap.FastEntrySet ? new ObjectIterable<Byte2DoubleMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Byte2DoubleMap.Entry> iterator() {
                return ((Byte2DoubleMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Byte2DoubleMap.Entry> consumer) {
                ((Byte2DoubleMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Byte2DoubleMap singleton(byte by, double d) {
        return new Singleton(by, d);
    }

    public static Byte2DoubleMap singleton(Byte by, Double d) {
        return new Singleton(by, d);
    }

    public static Byte2DoubleMap synchronize(Byte2DoubleMap byte2DoubleMap) {
        return new SynchronizedMap(byte2DoubleMap);
    }

    public static Byte2DoubleMap synchronize(Byte2DoubleMap byte2DoubleMap, Object object) {
        return new SynchronizedMap(byte2DoubleMap, object);
    }

    public static Byte2DoubleMap unmodifiable(Byte2DoubleMap byte2DoubleMap) {
        return new UnmodifiableMap(byte2DoubleMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Byte2DoubleFunctions.UnmodifiableFunction
    implements Byte2DoubleMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2DoubleMap map;
        protected transient ObjectSet<Byte2DoubleMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient DoubleCollection values;

        protected UnmodifiableMap(Byte2DoubleMap byte2DoubleMap) {
            super(byte2DoubleMap);
            this.map = byte2DoubleMap;
        }

        @Override
        public boolean containsValue(double d) {
            return this.map.containsValue(d);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Byte, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.byte2DoubleEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Double>> entrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        public ByteSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public DoubleCollection values() {
            if (this.values == null) {
                return DoubleCollections.unmodifiable(this.map.values());
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
        public double getOrDefault(byte by, double d) {
            return this.map.getOrDefault(by, d);
        }

        @Override
        public void forEach(BiConsumer<? super Byte, ? super Double> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double putIfAbsent(byte by, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(byte by, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double replace(byte by, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(byte by, double d, double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsent(byte by, IntToDoubleFunction intToDoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsentNullable(byte by, IntFunction<? extends Double> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsentPartial(byte by, Byte2DoubleFunction byte2DoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfPresent(byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double compute(byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double merge(byte by, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double getOrDefault(Object object, Double d) {
            return this.map.getOrDefault(object, d);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double replace(Byte by, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Byte by, Double d, Double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double putIfAbsent(Byte by, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double computeIfAbsent(Byte by, Function<? super Byte, ? extends Double> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double computeIfPresent(Byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double compute(Byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double merge(Byte by, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Byte)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Byte)object, (BiFunction<? super Byte, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Double>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Byte)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Byte)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Byte)object, (Double)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Double)object2);
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
    extends Byte2DoubleFunctions.SynchronizedFunction
    implements Byte2DoubleMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2DoubleMap map;
        protected transient ObjectSet<Byte2DoubleMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient DoubleCollection values;

        protected SynchronizedMap(Byte2DoubleMap byte2DoubleMap, Object object) {
            super(byte2DoubleMap, object);
            this.map = byte2DoubleMap;
        }

        protected SynchronizedMap(Byte2DoubleMap byte2DoubleMap) {
            super(byte2DoubleMap);
            this.map = byte2DoubleMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(d);
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
        public void putAll(Map<? extends Byte, ? extends Double> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.byte2DoubleEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Double>> entrySet() {
            return this.byte2DoubleEntrySet();
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
        public DoubleCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return DoubleCollections.synchronize(this.map.values(), this.sync);
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
        public double getOrDefault(byte by, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(by, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Byte, ? super Double> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double putIfAbsent(byte by, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(by, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(byte by, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(by, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double replace(byte by, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(byte by, double d, double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsent(byte by, IntToDoubleFunction intToDoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(by, intToDoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsentNullable(byte by, IntFunction<? extends Double> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(by, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsentPartial(byte by, Byte2DoubleFunction byte2DoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(by, byte2DoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfPresent(byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double compute(byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double merge(byte by, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(by, d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double getOrDefault(Object object, Double d) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, d);
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
        public Double replace(Byte by, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Byte by, Double d, Double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(by, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double putIfAbsent(Byte by, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(by, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double computeIfAbsent(Byte by, Function<? super Byte, ? extends Double> function) {
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
        public Double computeIfPresent(Byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
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
        public Double compute(Byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
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
        public Double merge(Byte by, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(by, d, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Byte)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Byte)object, (BiFunction<? super Byte, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Double>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Byte)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Byte)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Byte)object, (Double)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Double)object2);
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
    extends Byte2DoubleFunctions.Singleton
    implements Byte2DoubleMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Byte2DoubleMap.Entry> entries;
        protected transient ByteSet keys;
        protected transient DoubleCollection values;

        protected Singleton(byte by, double d) {
            super(by, d);
        }

        @Override
        public boolean containsValue(double d) {
            return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(d);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return Double.doubleToLongBits((Double)object) == Double.doubleToLongBits(this.value);
        }

        @Override
        public void putAll(Map<? extends Byte, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractByte2DoubleMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Byte, Double>> entrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        public ByteSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public DoubleCollection values() {
            if (this.values == null) {
                this.values = DoubleSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.double2int(this.value);
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
    extends Byte2DoubleFunctions.EmptyFunction
    implements Byte2DoubleMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(double d) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Byte, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ByteSet keySet() {
            return ByteSets.EMPTY_SET;
        }

        @Override
        public DoubleCollection values() {
            return DoubleSets.EMPTY_SET;
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

