/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.AbstractShort2CharMap;
import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
import it.unimi.dsi.fastutil.shorts.Short2CharFunctions;
import it.unimi.dsi.fastutil.shorts.Short2CharMap;
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
import java.util.function.IntUnaryOperator;

public final class Short2CharMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Short2CharMaps() {
    }

    public static ObjectIterator<Short2CharMap.Entry> fastIterator(Short2CharMap short2CharMap) {
        ObjectSet<Short2CharMap.Entry> objectSet = short2CharMap.short2CharEntrySet();
        return objectSet instanceof Short2CharMap.FastEntrySet ? ((Short2CharMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Short2CharMap short2CharMap, Consumer<? super Short2CharMap.Entry> consumer) {
        ObjectSet<Short2CharMap.Entry> objectSet = short2CharMap.short2CharEntrySet();
        if (objectSet instanceof Short2CharMap.FastEntrySet) {
            ((Short2CharMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Short2CharMap.Entry> fastIterable(Short2CharMap short2CharMap) {
        ObjectSet<Short2CharMap.Entry> objectSet = short2CharMap.short2CharEntrySet();
        return objectSet instanceof Short2CharMap.FastEntrySet ? new ObjectIterable<Short2CharMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Short2CharMap.Entry> iterator() {
                return ((Short2CharMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Short2CharMap.Entry> consumer) {
                ((Short2CharMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Short2CharMap singleton(short s, char c) {
        return new Singleton(s, c);
    }

    public static Short2CharMap singleton(Short s, Character c) {
        return new Singleton(s, c.charValue());
    }

    public static Short2CharMap synchronize(Short2CharMap short2CharMap) {
        return new SynchronizedMap(short2CharMap);
    }

    public static Short2CharMap synchronize(Short2CharMap short2CharMap, Object object) {
        return new SynchronizedMap(short2CharMap, object);
    }

    public static Short2CharMap unmodifiable(Short2CharMap short2CharMap) {
        return new UnmodifiableMap(short2CharMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Short2CharFunctions.UnmodifiableFunction
    implements Short2CharMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2CharMap map;
        protected transient ObjectSet<Short2CharMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient CharCollection values;

        protected UnmodifiableMap(Short2CharMap short2CharMap) {
            super(short2CharMap);
            this.map = short2CharMap;
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
        public void putAll(Map<? extends Short, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2CharMap.Entry> short2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.short2CharEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Character>> entrySet() {
            return this.short2CharEntrySet();
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.unmodifiable(this.map.keySet());
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
        public char getOrDefault(short s, char c) {
            return this.map.getOrDefault(s, c);
        }

        @Override
        public void forEach(BiConsumer<? super Short, ? super Character> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char putIfAbsent(short s, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(short s, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char replace(short s, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(short s, char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsentNullable(short s, IntFunction<? extends Character> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsentPartial(short s, Short2CharFunction short2CharFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfPresent(short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char compute(short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char merge(short s, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
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
        public Character replace(Short s, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Short s, Character c, Character c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character putIfAbsent(Short s, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character computeIfAbsent(Short s, Function<? super Short, ? extends Character> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character computeIfPresent(Short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character compute(Short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character merge(Short s, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Short)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Short)object, (BiFunction<? super Short, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Character>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Short)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Short)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Short)object, (Character)object2);
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
    extends Short2CharFunctions.SynchronizedFunction
    implements Short2CharMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2CharMap map;
        protected transient ObjectSet<Short2CharMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient CharCollection values;

        protected SynchronizedMap(Short2CharMap short2CharMap, Object object) {
            super(short2CharMap, object);
            this.map = short2CharMap;
        }

        protected SynchronizedMap(Short2CharMap short2CharMap) {
            super(short2CharMap);
            this.map = short2CharMap;
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
        public void putAll(Map<? extends Short, ? extends Character> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Short2CharMap.Entry> short2CharEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.short2CharEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Character>> entrySet() {
            return this.short2CharEntrySet();
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
        public char getOrDefault(short s, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(s, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Short, ? super Character> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char putIfAbsent(short s, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(s, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(short s, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(s, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char replace(short s, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(short s, char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(s, intUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsentNullable(short s, IntFunction<? extends Character> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(s, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsentPartial(short s, Short2CharFunction short2CharFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(s, short2CharFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfPresent(short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char compute(short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char merge(short s, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(s, c, biFunction);
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
        public Character replace(Short s, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Short s, Character c, Character c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(s, c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character putIfAbsent(Short s, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(s, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character computeIfAbsent(Short s, Function<? super Short, ? extends Character> function) {
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
        public Character computeIfPresent(Short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
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
        public Character compute(Short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
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
        public Character merge(Short s, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(s, c, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Short)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Short)object, (BiFunction<? super Short, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Character>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Short)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Short)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Short)object, (Character)object2);
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
    extends Short2CharFunctions.Singleton
    implements Short2CharMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Short2CharMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient CharCollection values;

        protected Singleton(short s, char c) {
            super(s, c);
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
        public void putAll(Map<? extends Short, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2CharMap.Entry> short2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractShort2CharMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Short, Character>> entrySet() {
            return this.short2CharEntrySet();
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.singleton(this.key);
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
    extends Short2CharFunctions.EmptyFunction
    implements Short2CharMap,
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
        public void putAll(Map<? extends Short, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2CharMap.Entry> short2CharEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ShortSet keySet() {
            return ShortSets.EMPTY_SET;
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

