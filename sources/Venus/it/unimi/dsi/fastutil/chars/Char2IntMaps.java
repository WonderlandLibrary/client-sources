/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntFunction;
import it.unimi.dsi.fastutil.chars.Char2IntFunctions;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntCollections;
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
import java.util.function.IntUnaryOperator;

public final class Char2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Char2IntMaps() {
    }

    public static ObjectIterator<Char2IntMap.Entry> fastIterator(Char2IntMap char2IntMap) {
        ObjectSet<Char2IntMap.Entry> objectSet = char2IntMap.char2IntEntrySet();
        return objectSet instanceof Char2IntMap.FastEntrySet ? ((Char2IntMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Char2IntMap char2IntMap, Consumer<? super Char2IntMap.Entry> consumer) {
        ObjectSet<Char2IntMap.Entry> objectSet = char2IntMap.char2IntEntrySet();
        if (objectSet instanceof Char2IntMap.FastEntrySet) {
            ((Char2IntMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Char2IntMap.Entry> fastIterable(Char2IntMap char2IntMap) {
        ObjectSet<Char2IntMap.Entry> objectSet = char2IntMap.char2IntEntrySet();
        return objectSet instanceof Char2IntMap.FastEntrySet ? new ObjectIterable<Char2IntMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Char2IntMap.Entry> iterator() {
                return ((Char2IntMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Char2IntMap.Entry> consumer) {
                ((Char2IntMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Char2IntMap singleton(char c, int n) {
        return new Singleton(c, n);
    }

    public static Char2IntMap singleton(Character c, Integer n) {
        return new Singleton(c.charValue(), n);
    }

    public static Char2IntMap synchronize(Char2IntMap char2IntMap) {
        return new SynchronizedMap(char2IntMap);
    }

    public static Char2IntMap synchronize(Char2IntMap char2IntMap, Object object) {
        return new SynchronizedMap(char2IntMap, object);
    }

    public static Char2IntMap unmodifiable(Char2IntMap char2IntMap) {
        return new UnmodifiableMap(char2IntMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Char2IntFunctions.UnmodifiableFunction
    implements Char2IntMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2IntMap map;
        protected transient ObjectSet<Char2IntMap.Entry> entries;
        protected transient CharSet keys;
        protected transient IntCollection values;

        protected UnmodifiableMap(Char2IntMap char2IntMap) {
            super(char2IntMap);
            this.map = char2IntMap;
        }

        @Override
        public boolean containsValue(int n) {
            return this.map.containsValue(n);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2IntMap.Entry> char2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.char2IntEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Integer>> entrySet() {
            return this.char2IntEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public IntCollection values() {
            if (this.values == null) {
                return IntCollections.unmodifiable(this.map.values());
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
        public int getOrDefault(char c, int n) {
            return this.map.getOrDefault(c, n);
        }

        @Override
        public void forEach(BiConsumer<? super Character, ? super Integer> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int putIfAbsent(char c, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(char c, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int replace(char c, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(char c, int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsentNullable(char c, IntFunction<? extends Integer> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfAbsentPartial(char c, Char2IntFunction char2IntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int computeIfPresent(char c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compute(char c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int merge(char c, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer getOrDefault(Object object, Integer n) {
            return this.map.getOrDefault(object, n);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer replace(Character c, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Character c, Integer n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer putIfAbsent(Character c, Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer computeIfAbsent(Character c, Function<? super Character, ? extends Integer> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer computeIfPresent(Character c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer compute(Character c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer merge(Character c, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Integer>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Integer)object2);
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
    extends Char2IntFunctions.SynchronizedFunction
    implements Char2IntMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2IntMap map;
        protected transient ObjectSet<Char2IntMap.Entry> entries;
        protected transient CharSet keys;
        protected transient IntCollection values;

        protected SynchronizedMap(Char2IntMap char2IntMap, Object object) {
            super(char2IntMap, object);
            this.map = char2IntMap;
        }

        protected SynchronizedMap(Char2IntMap char2IntMap) {
            super(char2IntMap);
            this.map = char2IntMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(n);
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
        public void putAll(Map<? extends Character, ? extends Integer> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Char2IntMap.Entry> char2IntEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.char2IntEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Integer>> entrySet() {
            return this.char2IntEntrySet();
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
        public IntCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return IntCollections.synchronize(this.map.values(), this.sync);
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
        public int getOrDefault(char c, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(c, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Character, ? super Integer> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int putIfAbsent(char c, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(char c, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(c, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int replace(char c, int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(char c, int n, int n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(c, intUnaryOperator);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsentNullable(char c, IntFunction<? extends Integer> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(c, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfAbsentPartial(char c, Char2IntFunction char2IntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(c, char2IntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int computeIfPresent(char c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int compute(char c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int merge(char c, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, n, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer getOrDefault(Object object, Integer n) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, n);
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
        public Integer replace(Character c, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Character c, Integer n, Integer n2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, n, n2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer putIfAbsent(Character c, Integer n) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer computeIfAbsent(Character c, Function<? super Character, ? extends Integer> function) {
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
        public Integer computeIfPresent(Character c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer compute(Character c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
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
        public Integer merge(Character c, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, n, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Integer, ? extends Integer>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Integer>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Integer)object2, (Integer)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Integer)object2);
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
    extends Char2IntFunctions.Singleton
    implements Char2IntMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Char2IntMap.Entry> entries;
        protected transient CharSet keys;
        protected transient IntCollection values;

        protected Singleton(char c, int n) {
            super(c, n);
        }

        @Override
        public boolean containsValue(int n) {
            return this.value == n;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Integer)object == this.value;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2IntMap.Entry> char2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractChar2IntMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Integer>> entrySet() {
            return this.char2IntEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public IntCollection values() {
            if (this.values == null) {
                this.values = IntSets.singleton(this.value);
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
    extends Char2IntFunctions.EmptyFunction
    implements Char2IntMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(int n) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2IntMap.Entry> char2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public CharSet keySet() {
            return CharSets.EMPTY_SET;
        }

        @Override
        public IntCollection values() {
            return IntSets.EMPTY_SET;
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

