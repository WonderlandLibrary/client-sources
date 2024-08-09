/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ShortMap;
import it.unimi.dsi.fastutil.chars.Char2ShortFunction;
import it.unimi.dsi.fastutil.chars.Char2ShortFunctions;
import it.unimi.dsi.fastutil.chars.Char2ShortMap;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollections;
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

public final class Char2ShortMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Char2ShortMaps() {
    }

    public static ObjectIterator<Char2ShortMap.Entry> fastIterator(Char2ShortMap char2ShortMap) {
        ObjectSet<Char2ShortMap.Entry> objectSet = char2ShortMap.char2ShortEntrySet();
        return objectSet instanceof Char2ShortMap.FastEntrySet ? ((Char2ShortMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Char2ShortMap char2ShortMap, Consumer<? super Char2ShortMap.Entry> consumer) {
        ObjectSet<Char2ShortMap.Entry> objectSet = char2ShortMap.char2ShortEntrySet();
        if (objectSet instanceof Char2ShortMap.FastEntrySet) {
            ((Char2ShortMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Char2ShortMap.Entry> fastIterable(Char2ShortMap char2ShortMap) {
        ObjectSet<Char2ShortMap.Entry> objectSet = char2ShortMap.char2ShortEntrySet();
        return objectSet instanceof Char2ShortMap.FastEntrySet ? new ObjectIterable<Char2ShortMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Char2ShortMap.Entry> iterator() {
                return ((Char2ShortMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Char2ShortMap.Entry> consumer) {
                ((Char2ShortMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Char2ShortMap singleton(char c, short s) {
        return new Singleton(c, s);
    }

    public static Char2ShortMap singleton(Character c, Short s) {
        return new Singleton(c.charValue(), s);
    }

    public static Char2ShortMap synchronize(Char2ShortMap char2ShortMap) {
        return new SynchronizedMap(char2ShortMap);
    }

    public static Char2ShortMap synchronize(Char2ShortMap char2ShortMap, Object object) {
        return new SynchronizedMap(char2ShortMap, object);
    }

    public static Char2ShortMap unmodifiable(Char2ShortMap char2ShortMap) {
        return new UnmodifiableMap(char2ShortMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Char2ShortFunctions.UnmodifiableFunction
    implements Char2ShortMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ShortMap map;
        protected transient ObjectSet<Char2ShortMap.Entry> entries;
        protected transient CharSet keys;
        protected transient ShortCollection values;

        protected UnmodifiableMap(Char2ShortMap char2ShortMap) {
            super(char2ShortMap);
            this.map = char2ShortMap;
        }

        @Override
        public boolean containsValue(short s) {
            return this.map.containsValue(s);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2ShortMap.Entry> char2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.char2ShortEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Short>> entrySet() {
            return this.char2ShortEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ShortCollection values() {
            if (this.values == null) {
                return ShortCollections.unmodifiable(this.map.values());
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
        public short getOrDefault(char c, short s) {
            return this.map.getOrDefault(c, s);
        }

        @Override
        public void forEach(BiConsumer<? super Character, ? super Short> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short putIfAbsent(char c, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(char c, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short replace(char c, short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(char c, short s, short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsentNullable(char c, IntFunction<? extends Short> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfAbsentPartial(char c, Char2ShortFunction char2ShortFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short computeIfPresent(char c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short compute(char c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public short merge(char c, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short getOrDefault(Object object, Short s) {
            return this.map.getOrDefault(object, s);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short replace(Character c, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Character c, Short s, Short s2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short putIfAbsent(Character c, Short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short computeIfAbsent(Character c, Function<? super Character, ? extends Short> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short computeIfPresent(Character c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short compute(Character c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Short merge(Character c, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Short>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Short)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Short)object2, (Short)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Short)object2);
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
    extends Char2ShortFunctions.SynchronizedFunction
    implements Char2ShortMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ShortMap map;
        protected transient ObjectSet<Char2ShortMap.Entry> entries;
        protected transient CharSet keys;
        protected transient ShortCollection values;

        protected SynchronizedMap(Char2ShortMap char2ShortMap, Object object) {
            super(char2ShortMap, object);
            this.map = char2ShortMap;
        }

        protected SynchronizedMap(Char2ShortMap char2ShortMap) {
            super(char2ShortMap);
            this.map = char2ShortMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(s);
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
        public void putAll(Map<? extends Character, ? extends Short> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Char2ShortMap.Entry> char2ShortEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.char2ShortEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Short>> entrySet() {
            return this.char2ShortEntrySet();
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
        public ShortCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return ShortCollections.synchronize(this.map.values(), this.sync);
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
        public short getOrDefault(char c, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(c, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Character, ? super Short> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short putIfAbsent(char c, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(char c, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(c, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short replace(char c, short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(char c, short s, short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(c, intUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsentNullable(char c, IntFunction<? extends Short> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(c, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfAbsentPartial(char c, Char2ShortFunction char2ShortFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(c, char2ShortFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short computeIfPresent(char c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short compute(char c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short merge(char c, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, s, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short getOrDefault(Object object, Short s) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, s);
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
        public Short replace(Character c, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Character c, Short s, Short s2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, s, s2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short putIfAbsent(Character c, Short s) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short computeIfAbsent(Character c, Function<? super Character, ? extends Short> function) {
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
        public Short computeIfPresent(Character c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
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
        public Short compute(Character c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
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
        public Short merge(Character c, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, s, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Short, ? extends Short>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Short>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Short)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Short)object2, (Short)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Short)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Short)object2);
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
    extends Char2ShortFunctions.Singleton
    implements Char2ShortMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Char2ShortMap.Entry> entries;
        protected transient CharSet keys;
        protected transient ShortCollection values;

        protected Singleton(char c, short s) {
            super(c, s);
        }

        @Override
        public boolean containsValue(short s) {
            return this.value == s;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Short)object == this.value;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2ShortMap.Entry> char2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractChar2ShortMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Short>> entrySet() {
            return this.char2ShortEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ShortCollection values() {
            if (this.values == null) {
                this.values = ShortSets.singleton(this.value);
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
    extends Char2ShortFunctions.EmptyFunction
    implements Char2ShortMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(short s) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Short> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2ShortMap.Entry> char2ShortEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public CharSet keySet() {
            return CharSets.EMPTY_SET;
        }

        @Override
        public ShortCollection values() {
            return ShortSets.EMPTY_SET;
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

