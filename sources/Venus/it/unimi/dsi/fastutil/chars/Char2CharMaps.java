/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharFunction;
import it.unimi.dsi.fastutil.chars.Char2CharFunctions;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
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

public final class Char2CharMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Char2CharMaps() {
    }

    public static ObjectIterator<Char2CharMap.Entry> fastIterator(Char2CharMap char2CharMap) {
        ObjectSet<Char2CharMap.Entry> objectSet = char2CharMap.char2CharEntrySet();
        return objectSet instanceof Char2CharMap.FastEntrySet ? ((Char2CharMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Char2CharMap char2CharMap, Consumer<? super Char2CharMap.Entry> consumer) {
        ObjectSet<Char2CharMap.Entry> objectSet = char2CharMap.char2CharEntrySet();
        if (objectSet instanceof Char2CharMap.FastEntrySet) {
            ((Char2CharMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Char2CharMap.Entry> fastIterable(Char2CharMap char2CharMap) {
        ObjectSet<Char2CharMap.Entry> objectSet = char2CharMap.char2CharEntrySet();
        return objectSet instanceof Char2CharMap.FastEntrySet ? new ObjectIterable<Char2CharMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Char2CharMap.Entry> iterator() {
                return ((Char2CharMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Char2CharMap.Entry> consumer) {
                ((Char2CharMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Char2CharMap singleton(char c, char c2) {
        return new Singleton(c, c2);
    }

    public static Char2CharMap singleton(Character c, Character c2) {
        return new Singleton(c.charValue(), c2.charValue());
    }

    public static Char2CharMap synchronize(Char2CharMap char2CharMap) {
        return new SynchronizedMap(char2CharMap);
    }

    public static Char2CharMap synchronize(Char2CharMap char2CharMap, Object object) {
        return new SynchronizedMap(char2CharMap, object);
    }

    public static Char2CharMap unmodifiable(Char2CharMap char2CharMap) {
        return new UnmodifiableMap(char2CharMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Char2CharFunctions.UnmodifiableFunction
    implements Char2CharMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2CharMap map;
        protected transient ObjectSet<Char2CharMap.Entry> entries;
        protected transient CharSet keys;
        protected transient CharCollection values;

        protected UnmodifiableMap(Char2CharMap char2CharMap) {
            super(char2CharMap);
            this.map = char2CharMap;
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
        public void putAll(Map<? extends Character, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2CharMap.Entry> char2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.char2CharEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Character>> entrySet() {
            return this.char2CharEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.unmodifiable(this.map.keySet());
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
        public char getOrDefault(char c, char c2) {
            return this.map.getOrDefault(c, c2);
        }

        @Override
        public void forEach(BiConsumer<? super Character, ? super Character> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char putIfAbsent(char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char replace(char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(char c, char c2, char c3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsentNullable(char c, IntFunction<? extends Character> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfAbsentPartial(char c, Char2CharFunction char2CharFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeIfPresent(char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char compute(char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char merge(char c, char c2, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
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
        public Character replace(Character c, Character c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Character c, Character c2, Character c3) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character putIfAbsent(Character c, Character c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character computeIfAbsent(Character c, Function<? super Character, ? extends Character> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character computeIfPresent(Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character compute(Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character merge(Character c, Character c2, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Character>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Character)object2);
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
    extends Char2CharFunctions.SynchronizedFunction
    implements Char2CharMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2CharMap map;
        protected transient ObjectSet<Char2CharMap.Entry> entries;
        protected transient CharSet keys;
        protected transient CharCollection values;

        protected SynchronizedMap(Char2CharMap char2CharMap, Object object) {
            super(char2CharMap, object);
            this.map = char2CharMap;
        }

        protected SynchronizedMap(Char2CharMap char2CharMap) {
            super(char2CharMap);
            this.map = char2CharMap;
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
        public void putAll(Map<? extends Character, ? extends Character> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Char2CharMap.Entry> char2CharEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.char2CharEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Character>> entrySet() {
            return this.char2CharEntrySet();
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
        public char getOrDefault(char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Character, ? super Character> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char putIfAbsent(char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char replace(char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(char c, char c2, char c3) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, c2, c3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(c, intUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsentNullable(char c, IntFunction<? extends Character> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(c, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfAbsentPartial(char c, Char2CharFunction char2CharFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(c, char2CharFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeIfPresent(char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char compute(char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char merge(char c, char c2, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, c2, biFunction);
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
        public Character replace(Character c, Character c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Character c, Character c2, Character c3) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, c2, c3);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character putIfAbsent(Character c, Character c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character computeIfAbsent(Character c, Function<? super Character, ? extends Character> function) {
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
        public Character computeIfPresent(Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
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
        public Character compute(Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
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
        public Character merge(Character c, Character c2, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, c2, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Character>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Character)object2);
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
    extends Char2CharFunctions.Singleton
    implements Char2CharMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Char2CharMap.Entry> entries;
        protected transient CharSet keys;
        protected transient CharCollection values;

        protected Singleton(char c, char c2) {
            super(c, c2);
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
        public void putAll(Map<? extends Character, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2CharMap.Entry> char2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractChar2CharMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Character>> entrySet() {
            return this.char2CharEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.singleton(this.key);
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
    extends Char2CharFunctions.EmptyFunction
    implements Char2CharMap,
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
        public void putAll(Map<? extends Character, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2CharMap.Entry> char2CharEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public CharSet keySet() {
            return CharSets.EMPTY_SET;
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

