/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2LongMap;
import it.unimi.dsi.fastutil.chars.Char2LongFunction;
import it.unimi.dsi.fastutil.chars.Char2LongFunctions;
import it.unimi.dsi.fastutil.chars.Char2LongMap;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.longs.LongSets;
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
import java.util.function.IntToLongFunction;

public final class Char2LongMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Char2LongMaps() {
    }

    public static ObjectIterator<Char2LongMap.Entry> fastIterator(Char2LongMap char2LongMap) {
        ObjectSet<Char2LongMap.Entry> objectSet = char2LongMap.char2LongEntrySet();
        return objectSet instanceof Char2LongMap.FastEntrySet ? ((Char2LongMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Char2LongMap char2LongMap, Consumer<? super Char2LongMap.Entry> consumer) {
        ObjectSet<Char2LongMap.Entry> objectSet = char2LongMap.char2LongEntrySet();
        if (objectSet instanceof Char2LongMap.FastEntrySet) {
            ((Char2LongMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Char2LongMap.Entry> fastIterable(Char2LongMap char2LongMap) {
        ObjectSet<Char2LongMap.Entry> objectSet = char2LongMap.char2LongEntrySet();
        return objectSet instanceof Char2LongMap.FastEntrySet ? new ObjectIterable<Char2LongMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Char2LongMap.Entry> iterator() {
                return ((Char2LongMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Char2LongMap.Entry> consumer) {
                ((Char2LongMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Char2LongMap singleton(char c, long l) {
        return new Singleton(c, l);
    }

    public static Char2LongMap singleton(Character c, Long l) {
        return new Singleton(c.charValue(), l);
    }

    public static Char2LongMap synchronize(Char2LongMap char2LongMap) {
        return new SynchronizedMap(char2LongMap);
    }

    public static Char2LongMap synchronize(Char2LongMap char2LongMap, Object object) {
        return new SynchronizedMap(char2LongMap, object);
    }

    public static Char2LongMap unmodifiable(Char2LongMap char2LongMap) {
        return new UnmodifiableMap(char2LongMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Char2LongFunctions.UnmodifiableFunction
    implements Char2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2LongMap map;
        protected transient ObjectSet<Char2LongMap.Entry> entries;
        protected transient CharSet keys;
        protected transient LongCollection values;

        protected UnmodifiableMap(Char2LongMap char2LongMap) {
            super(char2LongMap);
            this.map = char2LongMap;
        }

        @Override
        public boolean containsValue(long l) {
            return this.map.containsValue(l);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2LongMap.Entry> char2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.char2LongEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Long>> entrySet() {
            return this.char2LongEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public LongCollection values() {
            if (this.values == null) {
                return LongCollections.unmodifiable(this.map.values());
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
        public long getOrDefault(char c, long l) {
            return this.map.getOrDefault(c, l);
        }

        @Override
        public void forEach(BiConsumer<? super Character, ? super Long> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long putIfAbsent(char c, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(char c, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long replace(char c, long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(char c, long l, long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsent(char c, IntToLongFunction intToLongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsentNullable(char c, IntFunction<? extends Long> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfAbsentPartial(char c, Char2LongFunction char2LongFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long computeIfPresent(char c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long compute(char c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long merge(char c, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long getOrDefault(Object object, Long l) {
            return this.map.getOrDefault(object, l);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long replace(Character c, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Character c, Long l, Long l2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long putIfAbsent(Character c, Long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long computeIfAbsent(Character c, Function<? super Character, ? extends Long> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long computeIfPresent(Character c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long compute(Character c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long merge(Character c, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Long>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Long)object2);
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
    extends Char2LongFunctions.SynchronizedFunction
    implements Char2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2LongMap map;
        protected transient ObjectSet<Char2LongMap.Entry> entries;
        protected transient CharSet keys;
        protected transient LongCollection values;

        protected SynchronizedMap(Char2LongMap char2LongMap, Object object) {
            super(char2LongMap, object);
            this.map = char2LongMap;
        }

        protected SynchronizedMap(Char2LongMap char2LongMap) {
            super(char2LongMap);
            this.map = char2LongMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(l);
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
        public void putAll(Map<? extends Character, ? extends Long> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Char2LongMap.Entry> char2LongEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.char2LongEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Long>> entrySet() {
            return this.char2LongEntrySet();
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
        public LongCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return LongCollections.synchronize(this.map.values(), this.sync);
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
        public long getOrDefault(char c, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(c, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Character, ? super Long> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long putIfAbsent(char c, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(char c, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(c, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long replace(char c, long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(char c, long l, long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsent(char c, IntToLongFunction intToLongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(c, intToLongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsentNullable(char c, IntFunction<? extends Long> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(c, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfAbsentPartial(char c, Char2LongFunction char2LongFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(c, char2LongFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long computeIfPresent(char c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long compute(char c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long merge(char c, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, l, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long getOrDefault(Object object, Long l) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, l);
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
        public Long replace(Character c, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Character c, Long l, Long l2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, l, l2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long putIfAbsent(Character c, Long l) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, l);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long computeIfAbsent(Character c, Function<? super Character, ? extends Long> function) {
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
        public Long computeIfPresent(Character c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
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
        public Long compute(Character c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
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
        public Long merge(Character c, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, l, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Long, ? extends Long>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Long>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Long)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Long)object2, (Long)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Long)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Long)object2);
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
    extends Char2LongFunctions.Singleton
    implements Char2LongMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Char2LongMap.Entry> entries;
        protected transient CharSet keys;
        protected transient LongCollection values;

        protected Singleton(char c, long l) {
            super(c, l);
        }

        @Override
        public boolean containsValue(long l) {
            return this.value == l;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return (Long)object == this.value;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2LongMap.Entry> char2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractChar2LongMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Long>> entrySet() {
            return this.char2LongEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public LongCollection values() {
            if (this.values == null) {
                this.values = LongSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.long2int(this.value);
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
    extends Char2LongFunctions.EmptyFunction
    implements Char2LongMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(long l) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2LongMap.Entry> char2LongEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public CharSet keySet() {
            return CharSets.EMPTY_SET;
        }

        @Override
        public LongCollection values() {
            return LongSets.EMPTY_SET;
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

