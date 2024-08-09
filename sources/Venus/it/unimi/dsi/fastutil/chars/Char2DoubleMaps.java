/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2DoubleMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
import it.unimi.dsi.fastutil.chars.Char2DoubleFunctions;
import it.unimi.dsi.fastutil.chars.Char2DoubleMap;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSets;
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

public final class Char2DoubleMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Char2DoubleMaps() {
    }

    public static ObjectIterator<Char2DoubleMap.Entry> fastIterator(Char2DoubleMap char2DoubleMap) {
        ObjectSet<Char2DoubleMap.Entry> objectSet = char2DoubleMap.char2DoubleEntrySet();
        return objectSet instanceof Char2DoubleMap.FastEntrySet ? ((Char2DoubleMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Char2DoubleMap char2DoubleMap, Consumer<? super Char2DoubleMap.Entry> consumer) {
        ObjectSet<Char2DoubleMap.Entry> objectSet = char2DoubleMap.char2DoubleEntrySet();
        if (objectSet instanceof Char2DoubleMap.FastEntrySet) {
            ((Char2DoubleMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Char2DoubleMap.Entry> fastIterable(Char2DoubleMap char2DoubleMap) {
        ObjectSet<Char2DoubleMap.Entry> objectSet = char2DoubleMap.char2DoubleEntrySet();
        return objectSet instanceof Char2DoubleMap.FastEntrySet ? new ObjectIterable<Char2DoubleMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Char2DoubleMap.Entry> iterator() {
                return ((Char2DoubleMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Char2DoubleMap.Entry> consumer) {
                ((Char2DoubleMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Char2DoubleMap singleton(char c, double d) {
        return new Singleton(c, d);
    }

    public static Char2DoubleMap singleton(Character c, Double d) {
        return new Singleton(c.charValue(), d);
    }

    public static Char2DoubleMap synchronize(Char2DoubleMap char2DoubleMap) {
        return new SynchronizedMap(char2DoubleMap);
    }

    public static Char2DoubleMap synchronize(Char2DoubleMap char2DoubleMap, Object object) {
        return new SynchronizedMap(char2DoubleMap, object);
    }

    public static Char2DoubleMap unmodifiable(Char2DoubleMap char2DoubleMap) {
        return new UnmodifiableMap(char2DoubleMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Char2DoubleFunctions.UnmodifiableFunction
    implements Char2DoubleMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2DoubleMap map;
        protected transient ObjectSet<Char2DoubleMap.Entry> entries;
        protected transient CharSet keys;
        protected transient DoubleCollection values;

        protected UnmodifiableMap(Char2DoubleMap char2DoubleMap) {
            super(char2DoubleMap);
            this.map = char2DoubleMap;
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
        public void putAll(Map<? extends Character, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2DoubleMap.Entry> char2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.char2DoubleEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Double>> entrySet() {
            return this.char2DoubleEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.unmodifiable(this.map.keySet());
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
        public double getOrDefault(char c, double d) {
            return this.map.getOrDefault(c, d);
        }

        @Override
        public void forEach(BiConsumer<? super Character, ? super Double> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double putIfAbsent(char c, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(char c, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double replace(char c, double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(char c, double d, double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsent(char c, IntToDoubleFunction intToDoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsentNullable(char c, IntFunction<? extends Double> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfAbsentPartial(char c, Char2DoubleFunction char2DoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double computeIfPresent(char c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double compute(char c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public double merge(char c, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
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
        public Double replace(Character c, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Character c, Double d, Double d2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double putIfAbsent(Character c, Double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double computeIfAbsent(Character c, Function<? super Character, ? extends Double> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double computeIfPresent(Character c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double compute(Character c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double merge(Character c, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Double>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Double)object2);
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
    extends Char2DoubleFunctions.SynchronizedFunction
    implements Char2DoubleMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2DoubleMap map;
        protected transient ObjectSet<Char2DoubleMap.Entry> entries;
        protected transient CharSet keys;
        protected transient DoubleCollection values;

        protected SynchronizedMap(Char2DoubleMap char2DoubleMap, Object object) {
            super(char2DoubleMap, object);
            this.map = char2DoubleMap;
        }

        protected SynchronizedMap(Char2DoubleMap char2DoubleMap) {
            super(char2DoubleMap);
            this.map = char2DoubleMap;
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
        public void putAll(Map<? extends Character, ? extends Double> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Char2DoubleMap.Entry> char2DoubleEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.char2DoubleEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Double>> entrySet() {
            return this.char2DoubleEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public CharSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = CharSets.synchronize(this.map.keySet(), this.sync);
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
        public double getOrDefault(char c, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(c, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Character, ? super Double> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double putIfAbsent(char c, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(char c, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(c, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double replace(char c, double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(char c, double d, double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsent(char c, IntToDoubleFunction intToDoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(c, intToDoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsentNullable(char c, IntFunction<? extends Double> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(c, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfAbsentPartial(char c, Char2DoubleFunction char2DoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(c, char2DoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double computeIfPresent(char c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double compute(char c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double merge(char c, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, d, biFunction);
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
        public Double replace(Character c, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Character c, Double d, Double d2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, d, d2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double putIfAbsent(Character c, Double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double computeIfAbsent(Character c, Function<? super Character, ? extends Double> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(c, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double computeIfPresent(Character c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double compute(Character c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double merge(Character c, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, d, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Double, ? extends Double>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Double>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Double)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Double)object2, (Double)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Double)object2);
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
    extends Char2DoubleFunctions.Singleton
    implements Char2DoubleMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Char2DoubleMap.Entry> entries;
        protected transient CharSet keys;
        protected transient DoubleCollection values;

        protected Singleton(char c, double d) {
            super(c, d);
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
        public void putAll(Map<? extends Character, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2DoubleMap.Entry> char2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractChar2DoubleMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Double>> entrySet() {
            return this.char2DoubleEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.singleton(this.key);
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
    extends Char2DoubleFunctions.EmptyFunction
    implements Char2DoubleMap,
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
        public void putAll(Map<? extends Character, ? extends Double> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2DoubleMap.Entry> char2DoubleEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public CharSet keySet() {
            return CharSets.EMPTY_SET;
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

