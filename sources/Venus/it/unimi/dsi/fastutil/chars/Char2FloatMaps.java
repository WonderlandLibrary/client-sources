/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2FloatMap;
import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
import it.unimi.dsi.fastutil.chars.Char2FloatFunctions;
import it.unimi.dsi.fastutil.chars.Char2FloatMap;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
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
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

public final class Char2FloatMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Char2FloatMaps() {
    }

    public static ObjectIterator<Char2FloatMap.Entry> fastIterator(Char2FloatMap char2FloatMap) {
        ObjectSet<Char2FloatMap.Entry> objectSet = char2FloatMap.char2FloatEntrySet();
        return objectSet instanceof Char2FloatMap.FastEntrySet ? ((Char2FloatMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static void fastForEach(Char2FloatMap char2FloatMap, Consumer<? super Char2FloatMap.Entry> consumer) {
        ObjectSet<Char2FloatMap.Entry> objectSet = char2FloatMap.char2FloatEntrySet();
        if (objectSet instanceof Char2FloatMap.FastEntrySet) {
            ((Char2FloatMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static ObjectIterable<Char2FloatMap.Entry> fastIterable(Char2FloatMap char2FloatMap) {
        ObjectSet<Char2FloatMap.Entry> objectSet = char2FloatMap.char2FloatEntrySet();
        return objectSet instanceof Char2FloatMap.FastEntrySet ? new ObjectIterable<Char2FloatMap.Entry>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Char2FloatMap.Entry> iterator() {
                return ((Char2FloatMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public void forEach(Consumer<? super Char2FloatMap.Entry> consumer) {
                ((Char2FloatMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static Char2FloatMap singleton(char c, float f) {
        return new Singleton(c, f);
    }

    public static Char2FloatMap singleton(Character c, Float f) {
        return new Singleton(c.charValue(), f.floatValue());
    }

    public static Char2FloatMap synchronize(Char2FloatMap char2FloatMap) {
        return new SynchronizedMap(char2FloatMap);
    }

    public static Char2FloatMap synchronize(Char2FloatMap char2FloatMap, Object object) {
        return new SynchronizedMap(char2FloatMap, object);
    }

    public static Char2FloatMap unmodifiable(Char2FloatMap char2FloatMap) {
        return new UnmodifiableMap(char2FloatMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableMap
    extends Char2FloatFunctions.UnmodifiableFunction
    implements Char2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2FloatMap map;
        protected transient ObjectSet<Char2FloatMap.Entry> entries;
        protected transient CharSet keys;
        protected transient FloatCollection values;

        protected UnmodifiableMap(Char2FloatMap char2FloatMap) {
            super(char2FloatMap);
            this.map = char2FloatMap;
        }

        @Override
        public boolean containsValue(float f) {
            return this.map.containsValue(f);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2FloatMap.Entry> char2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.char2FloatEntrySet());
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Float>> entrySet() {
            return this.char2FloatEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public FloatCollection values() {
            if (this.values == null) {
                return FloatCollections.unmodifiable(this.map.values());
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
        public float getOrDefault(char c, float f) {
            return this.map.getOrDefault(c, f);
        }

        @Override
        public void forEach(BiConsumer<? super Character, ? super Float> biConsumer) {
            this.map.forEach(biConsumer);
        }

        @Override
        public void replaceAll(BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float putIfAbsent(char c, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(char c, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float replace(char c, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(char c, float f, float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsent(char c, IntToDoubleFunction intToDoubleFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentNullable(char c, IntFunction<? extends Float> intFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfAbsentPartial(char c, Char2FloatFunction char2FloatFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float computeIfPresent(char c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float compute(char c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float merge(char c, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float getOrDefault(Object object, Float f) {
            return this.map.getOrDefault(object, f);
        }

        @Override
        @Deprecated
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float replace(Character c, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean replace(Character c, Float f, Float f2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float putIfAbsent(Character c, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfAbsent(Character c, Function<? super Character, ? extends Float> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float computeIfPresent(Character c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float compute(Character c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float merge(Character c, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Float)object2);
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
    extends Char2FloatFunctions.SynchronizedFunction
    implements Char2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2FloatMap map;
        protected transient ObjectSet<Char2FloatMap.Entry> entries;
        protected transient CharSet keys;
        protected transient FloatCollection values;

        protected SynchronizedMap(Char2FloatMap char2FloatMap, Object object) {
            super(char2FloatMap, object);
            this.map = char2FloatMap;
        }

        protected SynchronizedMap(Char2FloatMap char2FloatMap) {
            super(char2FloatMap);
            this.map = char2FloatMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(f);
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
        public void putAll(Map<? extends Character, ? extends Float> map) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Char2FloatMap.Entry> char2FloatEntrySet() {
            Object object = this.sync;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = ObjectSets.synchronize(this.map.char2FloatEntrySet(), this.sync);
                }
                return this.entries;
            }
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Float>> entrySet() {
            return this.char2FloatEntrySet();
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
        public FloatCollection values() {
            Object object = this.sync;
            synchronized (object) {
                if (this.values == null) {
                    return FloatCollections.synchronize(this.map.values(), this.sync);
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
        public float getOrDefault(char c, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getOrDefault(c, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void forEach(BiConsumer<? super Character, ? super Float> biConsumer) {
            Object object = this.sync;
            synchronized (object) {
                this.map.forEach(biConsumer);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void replaceAll(BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                this.map.replaceAll(biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float putIfAbsent(char c, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(char c, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(c, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float replace(char c, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean replace(char c, float f, float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsent(char c, IntToDoubleFunction intToDoubleFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsent(c, intToDoubleFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentNullable(char c, IntFunction<? extends Float> intFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentNullable(c, intFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfAbsentPartial(char c, Char2FloatFunction char2FloatFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfAbsentPartial(c, char2FloatFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float computeIfPresent(char c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.computeIfPresent(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float compute(char c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.compute(c, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float merge(char c, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, f, biFunction);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float getOrDefault(Object object, Float f) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.map.getOrDefault(object, f);
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
        public Float replace(Character c, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean replace(Character c, Float f, Float f2) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.replace(c, f, f2);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float putIfAbsent(Character c, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.putIfAbsent(c, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float computeIfAbsent(Character c, Function<? super Character, ? extends Float> function) {
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
        public Float computeIfPresent(Character c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
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
        public Float compute(Character c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
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
        public Float merge(Character c, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.merge(c, f, biFunction);
            }
        }

        @Override
        @Deprecated
        public Object merge(Object object, Object object2, BiFunction biFunction) {
            return this.merge((Character)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object compute(Object object, BiFunction biFunction) {
            return this.compute((Character)object, (BiFunction<? super Character, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfPresent(Object object, BiFunction biFunction) {
            return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Float, ? extends Float>)biFunction);
        }

        @Override
        @Deprecated
        public Object computeIfAbsent(Object object, Function function) {
            return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Float>)function);
        }

        @Override
        @Deprecated
        public Object replace(Object object, Object object2) {
            return this.replace((Character)object, (Float)object2);
        }

        @Override
        @Deprecated
        public boolean replace(Object object, Object object2, Object object3) {
            return this.replace((Character)object, (Float)object2, (Float)object3);
        }

        @Override
        @Deprecated
        public Object putIfAbsent(Object object, Object object2) {
            return this.putIfAbsent((Character)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Float)object2);
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
    extends Char2FloatFunctions.Singleton
    implements Char2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Char2FloatMap.Entry> entries;
        protected transient CharSet keys;
        protected transient FloatCollection values;

        protected Singleton(char c, float f) {
            super(c, f);
        }

        @Override
        public boolean containsValue(float f) {
            return Float.floatToIntBits(this.value) == Float.floatToIntBits(f);
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return Float.floatToIntBits(((Float)object).floatValue()) == Float.floatToIntBits(this.value);
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2FloatMap.Entry> char2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractChar2FloatMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Character, Float>> entrySet() {
            return this.char2FloatEntrySet();
        }

        @Override
        public CharSet keySet() {
            if (this.keys == null) {
                this.keys = CharSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public FloatCollection values() {
            if (this.values == null) {
                this.values = FloatSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.float2int(this.value);
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
    extends Char2FloatFunctions.EmptyFunction
    implements Char2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(float f) {
            return true;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends Character, ? extends Float> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Char2FloatMap.Entry> char2FloatEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public CharSet keySet() {
            return CharSets.EMPTY_SET;
        }

        @Override
        public FloatCollection values() {
            return FloatSets.EMPTY_SET;
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

