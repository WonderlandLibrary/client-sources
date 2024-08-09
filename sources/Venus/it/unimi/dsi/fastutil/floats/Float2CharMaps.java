/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.floats.AbstractFloat2CharMap;
import it.unimi.dsi.fastutil.floats.Float2CharFunction;
import it.unimi.dsi.fastutil.floats.Float2CharFunctions;
import it.unimi.dsi.fastutil.floats.Float2CharMap;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSets;
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

public final class Float2CharMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Float2CharMaps() {
    }

    public static ObjectIterator<Float2CharMap.Entry> fastIterator(Float2CharMap float2CharMap) {
        ObjectSet<Float2CharMap.Entry> objectSet = float2CharMap.float2CharEntrySet();
        return objectSet instanceof Float2CharMap.FastEntrySet ? ((Float2CharMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Float2CharMap float2CharMap, Consumer<? super Float2CharMap.Entry> consumer) {
        ObjectSet<Float2CharMap.Entry> objectSet = float2CharMap.float2CharEntrySet();
        if (objectSet instanceof Float2CharMap.FastEntrySet) {
            ((Float2CharMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Float2CharMap.Entry> fastIterable(Float2CharMap float2CharMap) {
        ObjectSet<Float2CharMap.Entry> objectSet = float2CharMap.float2CharEntrySet();
        return objectSet instanceof Float2CharMap.FastEntrySet ? new ObjectIterable<Float2CharMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Float2CharMap.Entry> iterator() {
                return ((Float2CharMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Float2CharMap.Entry> consumer) {
                ((Float2CharMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Float2CharMap singleton(float f, char c) {
        return new Singleton(f, c);
    }

    public static Float2CharMap singleton(Float f, Character c) {
        return new Singleton(f.floatValue(), c.charValue());
    }

    public static Float2CharMap synchronize(Float2CharMap float2CharMap) {
        return new SynchronizedMap(float2CharMap);
    }

    public static Float2CharMap synchronize(Float2CharMap float2CharMap, Object object) {
        return new SynchronizedMap(float2CharMap, object);
    }

    public static Float2CharMap unmodifiable(Float2CharMap float2CharMap) {
        return new UnmodifiableMap(float2CharMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Float2CharFunctions.UnmodifiableFunction
    implements Float2CharMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2CharMap map;
        protected transient ObjectSet<Float2CharMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient CharCollection values;

        protected UnmodifiableMap(Float2CharMap float2CharMap) {
            super(float2CharMap);
            this.map = float2CharMap;
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
        public void putAll(Map<? extends Float, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2CharMap.Entry> float2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.float2CharEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Character>> entrySet() {
            return this.float2CharEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.unmodifiable(this.map.keySet());
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
        public char getOrDefault(float f, char c) {
            return this.map.getOrDefault(f, c);
        }

        @Override
        public void forEach(BiConsumer<? super Float, ? super Character> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char putIfAbsent(float f, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(float f, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char replace(float f, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(float f, char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsentNullable(float f, DoubleFunction<? extends Character> doubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsentPartial(float f, Float2CharFunction float2CharFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfPresent(float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char compute(float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char merge(float f, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
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
        public Character replace(Float f, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Float f, Character c, Character c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character putIfAbsent(Float f, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character computeIfAbsent(Float f, Function<? super Float, ? extends Character> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character computeIfPresent(Float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character compute(Float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character merge(Float f, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Character>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Character)object2);
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
    extends Float2CharFunctions.SynchronizedFunction
    implements Float2CharMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2CharMap map;
        protected transient ObjectSet<Float2CharMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient CharCollection values;

        protected SynchronizedMap(Float2CharMap float2CharMap, Object object) {
            super(float2CharMap, object);
            this.map = float2CharMap;
        }

        protected SynchronizedMap(Float2CharMap float2CharMap) {
            super(float2CharMap);
            this.map = float2CharMap;
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
        public void putAll(Map<? extends Float, ? extends Character> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Float2CharMap.Entry> float2CharEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.float2CharEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Character>> entrySet() {
            return this.float2CharEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public FloatSet keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = FloatSets.synchronize(this.map.keySet(), this.sync);
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
        public char getOrDefault(float f, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(f, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Float, ? super Character> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char putIfAbsent(float f, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(float f, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(f, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char replace(float f, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(float f, char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(f, doubleToIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsentNullable(float f, DoubleFunction<? extends Character> doubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(f, doubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsentPartial(float f, Float2CharFunction float2CharFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(f, float2CharFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfPresent(float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char compute(float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char merge(float f, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, c, biFunction);
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
        public Character replace(Float f, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Float f, Character c, Character c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(f, c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character putIfAbsent(Float f, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(f, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character computeIfAbsent(Float f, Function<? super Float, ? extends Character> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(f, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character computeIfPresent(Float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character compute(Float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character merge(Float f, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(f, c, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Float)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Float)object, (BiFunction<? super Float, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Character>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Float)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Float)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Float)object, (Character)object2);
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
    extends Float2CharFunctions.Singleton
    implements Float2CharMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Float2CharMap.Entry> entries;
        protected transient FloatSet keys;
        protected transient CharCollection values;

        protected Singleton(float f, char c) {
            super(f, c);
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
        public void putAll(Map<? extends Float, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2CharMap.Entry> float2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractFloat2CharMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Float, Character>> entrySet() {
            return this.float2CharEntrySet();
        }

        @Override
        public FloatSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSets.singleton(this.key);
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
            return HashCommon.float2int(this.key) ^ this.value;
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
    extends Float2CharFunctions.EmptyFunction
    implements Float2CharMap,
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
        public void putAll(Map<? extends Float, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Float2CharMap.Entry> float2CharEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public FloatSet keySet() {
            return FloatSets.EMPTY_SET;
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

