/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.chars.AbstractChar2ByteMap;
import it.unimi.dsi.fastutil.chars.Char2ByteFunction;
import it.unimi.dsi.fastutil.chars.Char2ByteFunctions;
import it.unimi.dsi.fastutil.chars.Char2ByteMap;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSets;
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
import java.util.function.IntUnaryOperator;

public final class Char2ByteMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Char2ByteMaps() {
    }

    public static ObjectIterator<Char2ByteMap.Entry> fastIterator(Char2ByteMap char2ByteMap) {
        ObjectSet<Char2ByteMap.Entry> objectSet = char2ByteMap.char2ByteEntrySet();
        return objectSet instanceof Char2ByteMap.FastEntrySet ? ((Char2ByteMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Char2ByteMap char2ByteMap, Consumer<? super Char2ByteMap.Entry> consumer) {
        ObjectSet<Char2ByteMap.Entry> objectSet = char2ByteMap.char2ByteEntrySet();
        if (objectSet instanceof Char2ByteMap.FastEntrySet) {
            ((Char2ByteMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Char2ByteMap.Entry> fastIterable(Char2ByteMap char2ByteMap) {
        ObjectSet<Char2ByteMap.Entry> objectSet = char2ByteMap.char2ByteEntrySet();
        return objectSet instanceof Char2ByteMap.FastEntrySet ? new ObjectIterable<Char2ByteMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Char2ByteMap.Entry> iterator() {
                return ((Char2ByteMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Char2ByteMap.Entry> consumer) {
                ((Char2ByteMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Char2ByteMap singleton(char c, byte by) {
        return new Singleton(c, by);
    }

    public static Char2ByteMap singleton(Character c, Byte by) {
        return new Singleton(c.charValue(), by);
    }

    public static Char2ByteMap synchronize(Char2ByteMap char2ByteMap) {
        return new SynchronizedMap(char2ByteMap);
    }

    public static Char2ByteMap synchronize(Char2ByteMap char2ByteMap, Object object) {
        return new SynchronizedMap(char2ByteMap, object);
    }

    public static Char2ByteMap unmodifiable(Char2ByteMap char2ByteMap) {
        return new UnmodifiableMap(char2ByteMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Char2ByteFunctions.UnmodifiableFunction
    implements Char2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ByteMap map;
        protected transient ObjectSet<Char2ByteMap.Entry> entries;
        protected transient CharSet keys;
        protected transient ByteCollection values;

        protected UnmodifiableMap(Char2ByteMap char2ByteMap) {
            super(char2ByteMap);
            this.map = char2ByteMap;
        }

        @Override
        public boolean containsValue(byte by) {
            return this.map.containsValue(by);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2ByteMap.Entry> char2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.char2ByteEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Byte>> entrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                return ByteCollections.unmodifiable(this.map.values());
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
        public byte getOrDefault(char c, byte by) {
            return this.map.getOrDefault(c, by);
        }

        @Override
        public void forEach(BiConsumer<? super Character, ? super Byte> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte putIfAbsent(char c, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(char c, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte replace(char c, byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(char c, byte by, byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsentNullable(char c, IntFunction<? extends Byte> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfAbsentPartial(char c, Char2ByteFunction char2ByteFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte computeIfPresent(char c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte compute(char c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte merge(char c, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte getOrDefault(Object object, Byte by) {
            return this.map.getOrDefault(object, by);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte replace(Character c, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Character c, Byte by, Byte by2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte putIfAbsent(Character c, Byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte computeIfAbsent(Character c, Function<? super Character, ? extends Byte> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte computeIfPresent(Character c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte compute(Character c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte merge(Character c, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Byte>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Byte)object2, (Byte)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Byte)object2);
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
    extends Char2ByteFunctions.SynchronizedFunction
    implements Char2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2ByteMap map;
        protected transient ObjectSet<Char2ByteMap.Entry> entries;
        protected transient CharSet keys;
        protected transient ByteCollection values;

        protected SynchronizedMap(Char2ByteMap char2ByteMap, Object object) {
            super(char2ByteMap, object);
            this.map = char2ByteMap;
        }

        protected SynchronizedMap(Char2ByteMap char2ByteMap) {
            super(char2ByteMap);
            this.map = char2ByteMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(by);
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
        public void putAll(Map<? extends Character, ? extends Byte> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Char2ByteMap.Entry> char2ByteEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.char2ByteEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Byte>> entrySet() {
            return this.char2ByteEntrySet();
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
        public ByteCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return ByteCollections.synchronize(this.map.values(), this.sync);
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
        public byte getOrDefault(char c, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(c, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Character, ? super Byte> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte putIfAbsent(char c, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(char c, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(c, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte replace(char c, byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(char c, byte by, byte by2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, by, by2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(c, intUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsentNullable(char c, IntFunction<? extends Byte> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(c, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfAbsentPartial(char c, Char2ByteFunction char2ByteFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(c, char2ByteFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte computeIfPresent(char c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte compute(char c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte merge(char c, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, by, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte getOrDefault(Object object, Byte by) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, by);
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
        public Byte replace(Character c, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Character c, Byte by, Byte by2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, by, by2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte putIfAbsent(Character c, Byte by) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, by);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte computeIfAbsent(Character c, Function<? super Character, ? extends Byte> function) {
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
        public Byte computeIfPresent(Character c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
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
        public Byte compute(Character c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
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
        public Byte merge(Character c, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, by, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Byte, ? extends Byte>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Byte>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Byte)object2, (Byte)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Byte)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Byte)object2);
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
    extends Char2ByteFunctions.Singleton
    implements Char2ByteMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Char2ByteMap.Entry> entries;
        protected transient CharSet keys;
        protected transient ByteCollection values;

        protected Singleton(char c, byte by) {
            super(c, by);
        }

        @Override
        public boolean containsValue(byte by) {
            return this.value == by;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Byte)object == this.value;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2ByteMap.Entry> char2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractChar2ByteMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Byte>> entrySet() {
            return this.char2ByteEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                this.values = ByteSets.singleton(this.value);
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
    extends Char2ByteFunctions.EmptyFunction
    implements Char2ByteMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(byte by) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Byte> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2ByteMap.Entry> char2ByteEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public CharSet keySet() {
            return CharSets.EMPTY_SET;
        }

        @Override
        public ByteCollection values() {
            return ByteSets.EMPTY_SET;
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

