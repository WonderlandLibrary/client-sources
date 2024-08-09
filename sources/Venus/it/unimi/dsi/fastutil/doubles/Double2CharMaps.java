/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2CharMap;
import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
import it.unimi.dsi.fastutil.doubles.Double2CharFunctions;
import it.unimi.dsi.fastutil.doubles.Double2CharMap;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
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
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public final class Double2CharMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Double2CharMaps() {
    }

    public static ObjectIterator<Double2CharMap.Entry> fastIterator(Double2CharMap double2CharMap) {
        ObjectSet<Double2CharMap.Entry> objectSet = double2CharMap.double2CharEntrySet();
        return objectSet instanceof Double2CharMap.FastEntrySet ? ((Double2CharMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Double2CharMap double2CharMap, Consumer<? super Double2CharMap.Entry> consumer) {
        ObjectSet<Double2CharMap.Entry> objectSet = double2CharMap.double2CharEntrySet();
        if (objectSet instanceof Double2CharMap.FastEntrySet) {
            ((Double2CharMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Double2CharMap.Entry> fastIterable(Double2CharMap double2CharMap) {
        ObjectSet<Double2CharMap.Entry> objectSet = double2CharMap.double2CharEntrySet();
        return objectSet instanceof Double2CharMap.FastEntrySet ? new ObjectIterable<Double2CharMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Double2CharMap.Entry> iterator() {
                return ((Double2CharMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Double2CharMap.Entry> consumer) {
                ((Double2CharMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Double2CharMap singleton(double d, char c) {
        return new Singleton(d, c);
    }

    public static Double2CharMap singleton(Double d, Character c) {
        return new Singleton(d, c.charValue());
    }

    public static Double2CharMap synchronize(Double2CharMap double2CharMap) {
        return new SynchronizedMap(double2CharMap);
    }

    public static Double2CharMap synchronize(Double2CharMap double2CharMap, Object object) {
        return new SynchronizedMap(double2CharMap, object);
    }

    public static Double2CharMap unmodifiable(Double2CharMap double2CharMap) {
        return new UnmodifiableMap(double2CharMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Double2CharFunctions.UnmodifiableFunction
    implements Double2CharMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2CharMap map;
        protected transient ObjectSet<Double2CharMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient CharCollection values;

        protected UnmodifiableMap(Double2CharMap double2CharMap) {
            super(double2CharMap);
            this.map = double2CharMap;
        }

        @Override
        public boolean containsValue(char c) {
            return this.map.containsValue(c);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Double, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2CharMap.Entry> double2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.double2CharEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Character>> entrySet() {
            return this.double2CharEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public CharCollection values() {
            if (this.values == null) {
                return CharCollections.unmodifiable(this.map.values());
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
        public char getOrDefault(double d, char c) {
            return this.map.getOrDefault(d, c);
        }

        @Override
        public void forEach(BiConsumer<? super Double, ? super Character> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char putIfAbsent(double d, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(double d, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char replace(double d, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(double d, char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsentNullable(double d, DoubleFunction<? extends Character> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsentPartial(double d, Double2CharFunction double2CharFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfPresent(double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char compute(double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char merge(double d, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character getOrDefault(Object object, Character c) {
            return this.map.getOrDefault(object, c);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character replace(Double d, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Double d, Character c, Character c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character putIfAbsent(Double d, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character computeIfAbsent(Double d, Function<? super Double, ? extends Character> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character computeIfPresent(Double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character compute(Double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character merge(Double d, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Character>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Character)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Character)object2);
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
    extends Double2CharFunctions.SynchronizedFunction
    implements Double2CharMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2CharMap map;
        protected transient ObjectSet<Double2CharMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient CharCollection values;

        protected SynchronizedMap(Double2CharMap double2CharMap, Object object) {
            super(double2CharMap, object);
            this.map = double2CharMap;
        }

        protected SynchronizedMap(Double2CharMap double2CharMap) {
            super(double2CharMap);
            this.map = double2CharMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(c);
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
        public void putAll(Map<? extends Double, ? extends Character> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Double2CharMap.Entry> double2CharEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.double2CharEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Character>> entrySet() {
            return this.double2CharEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DoubleSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync);
                }
                return this.keys;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public CharCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return CharCollections.synchronize(this.map.values(), this.sync);
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
        public char getOrDefault(double d, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(d, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Double, ? super Character> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char putIfAbsent(double d, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(double d, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(d, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char replace(double d, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(double d, char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(d, doubleToIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsentNullable(double d, DoubleFunction<? extends Character> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(d, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsentPartial(double d, Double2CharFunction double2CharFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(d, double2CharFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfPresent(double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char compute(double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char merge(double d, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character getOrDefault(Object object, Character c) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, c);
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
        public Character replace(Double d, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Double d, Character c, Character c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(d, c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character putIfAbsent(Double d, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(d, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character computeIfAbsent(Double d, Function<? super Double, ? extends Character> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(d, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character computeIfPresent(Double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character compute(Double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(d, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character merge(Double d, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(d, c, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Double)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Double)object, (BiFunction<? super Double, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Character>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Double)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Double)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Double)object, (Character)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Character)object2);
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
    extends Double2CharFunctions.Singleton
    implements Double2CharMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Double2CharMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient CharCollection values;

        protected Singleton(double d, char c) {
            super(d, c);
        }

        @Override
        public boolean containsValue(char c) {
            return this.value == c;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return ((Character)object).charValue() == this.value;
        }

        @Override
        public void putAll(Map<? extends Double, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2CharMap.Entry> double2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractDouble2CharMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Double, Character>> entrySet() {
            return this.double2CharEntrySet();
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public CharCollection values() {
            if (this.values == null) {
                this.values = CharSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ this.value;
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
    extends Double2CharFunctions.EmptyFunction
    implements Double2CharMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(char c) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Double, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2CharMap.Entry> double2CharEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public DoubleSet keySet() {
            return DoubleSets.EMPTY_SET;
        }

        @Override
        public CharCollection values() {
            return CharSets.EMPTY_SET;
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

