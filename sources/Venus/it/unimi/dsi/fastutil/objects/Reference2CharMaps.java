/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharCollections;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.objects.AbstractReference2CharMap;
import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
import it.unimi.dsi.fastutil.objects.Reference2CharFunctions;
import it.unimi.dsi.fastutil.objects.Reference2CharMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSets;
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
import java.util.function.ToIntFunction;

public final class Reference2CharMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Reference2CharMaps() {
    }

    public static <K> ObjectIterator<Reference2CharMap.Entry<K>> fastIterator(Reference2CharMap<K> reference2CharMap) {
        ObjectSet<Reference2CharMap.Entry<K>> objectSet = reference2CharMap.reference2CharEntrySet();
        return objectSet instanceof Reference2CharMap.FastEntrySet ? ((Reference2CharMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> void fastForEach(Reference2CharMap<K> reference2CharMap, Consumer<? super Reference2CharMap.Entry<K>> consumer) {
        ObjectSet<Reference2CharMap.Entry<K>> objectSet = reference2CharMap.reference2CharEntrySet();
        if (objectSet instanceof Reference2CharMap.FastEntrySet) {
            ((Reference2CharMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <K> ObjectIterable<Reference2CharMap.Entry<K>> fastIterable(Reference2CharMap<K> reference2CharMap) {
        ObjectSet<Reference2CharMap.Entry<K>> objectSet = reference2CharMap.reference2CharEntrySet();
        return objectSet instanceof Reference2CharMap.FastEntrySet ? new ObjectIterable<Reference2CharMap.Entry<K>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Reference2CharMap.Entry<K>> iterator() {
                return ((Reference2CharMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
                ((Reference2CharMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <K> Reference2CharMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Reference2CharMap<K> singleton(K k, char c) {
        return new Singleton<K>(k, c);
    }

    public static <K> Reference2CharMap<K> singleton(K k, Character c) {
        return new Singleton<K>(k, c.charValue());
    }

    public static <K> Reference2CharMap<K> synchronize(Reference2CharMap<K> reference2CharMap) {
        return new SynchronizedMap<K>(reference2CharMap);
    }

    public static <K> Reference2CharMap<K> synchronize(Reference2CharMap<K> reference2CharMap, Object object) {
        return new SynchronizedMap<K>(reference2CharMap, object);
    }

    public static <K> Reference2CharMap<K> unmodifiable(Reference2CharMap<K> reference2CharMap) {
        return new UnmodifiableMap<K>(reference2CharMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap<K>
    extends Reference2CharFunctions.UnmodifiableFunction<K>
    implements Reference2CharMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2CharMap<K> map;
        protected transient ObjectSet<Reference2CharMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient CharCollection values;

        protected UnmodifiableMap(Reference2CharMap<K> reference2CharMap) {
            super(reference2CharMap);
            this.map = reference2CharMap;
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
        public void putAll(Map<? extends K, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2CharMap.Entry<K>> reference2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.reference2CharEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Character>> entrySet() {
            return this.reference2CharEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.unmodifiable(this.map.keySet());
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
        public char getOrDefault(Object object, char c) {
            return this.map.getOrDefault(object, c);
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super Character> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char putIfAbsent(K k, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char replace(K k, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K k, char c, char c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeCharIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeCharIfAbsentPartial(K k, Reference2CharFunction<? super K> reference2CharFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char mergeChar(K k, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
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
        public Character replace(K k, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(K k, Character c, Character c2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character putIfAbsent(K k, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Character computeIfAbsent(K k, Function<? super K, ? extends Character> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Character computeIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Character compute(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character merge(K k, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Character)object2, (BiFunction<Character, Character, Character>)biFunction);
        }

        @Override
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute(object, biFunction);
        }

        @Override
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent(object, biFunction);
        }

        @Override
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent(object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((K)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Character)object2);
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
    public static class SynchronizedMap<K>
    extends Reference2CharFunctions.SynchronizedFunction<K>
    implements Reference2CharMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2CharMap<K> map;
        protected transient ObjectSet<Reference2CharMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient CharCollection values;

        protected SynchronizedMap(Reference2CharMap<K> reference2CharMap, Object object) {
            super(reference2CharMap, object);
            this.map = reference2CharMap;
        }

        protected SynchronizedMap(Reference2CharMap<K> reference2CharMap) {
            super(reference2CharMap);
            this.map = reference2CharMap;
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
        public void putAll(Map<? extends K, ? extends Character> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Reference2CharMap.Entry<K>> reference2CharEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.reference2CharEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Character>> entrySet() {
            return this.reference2CharEntrySet();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ReferenceSet<K> keySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync);
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
        public char getOrDefault(Object object, char c) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super K, ? super Character> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char putIfAbsent(K k, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object object, char c) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.remove(object, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char replace(K k, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(K k, char c, char c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeCharIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeCharIfAbsent((K)k, toIntFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeCharIfAbsentPartial(K k, Reference2CharFunction<? super K> reference2CharFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeCharIfAbsentPartial((K)k, reference2CharFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeCharIfPresent((K)k, (BiFunction<? super K, Character, Character>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeChar((K)k, (BiFunction<? super K, Character, Character>)biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char mergeChar(K k, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.mergeChar(k, c, biFunction);
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
        public Character replace(K k, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(K k, Character c, Character c2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(k, c, c2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character putIfAbsent(K k, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(k, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Character computeIfAbsent(K k, Function<? super K, ? extends Character> function) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent((K)k, function);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Character computeIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent((K)k, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Character compute(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute((K)k, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character merge(K k, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(k, c, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge(object, (Character)object2, (BiFunction<Character, Character, Character>)biFunction);
        }

        @Override
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute(object, biFunction);
        }

        @Override
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent(object, biFunction);
        }

        @Override
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent(object, function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((K)object, (Character)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((K)object, (Character)object2, (Character)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((K)object, (Character)object2);
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
    public static class Singleton<K>
    extends Reference2CharFunctions.Singleton<K>
    implements Reference2CharMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Reference2CharMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient CharCollection values;

        protected Singleton(K k, char c) {
            super(k, c);
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
        public void putAll(Map<? extends K, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2CharMap.Entry<K>> reference2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractReference2CharMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Character>> entrySet() {
            return this.reference2CharEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.singleton(this.key);
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
            return System.identityHashCode(this.key) ^ this.value;
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
    public static class EmptyMap<K>
    extends Reference2CharFunctions.EmptyFunction<K>
    implements Reference2CharMap<K>,
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
        public void putAll(Map<? extends K, ? extends Character> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2CharMap.Entry<K>> reference2CharEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ReferenceSet<K> keySet() {
            return ReferenceSets.EMPTY_SET;
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

