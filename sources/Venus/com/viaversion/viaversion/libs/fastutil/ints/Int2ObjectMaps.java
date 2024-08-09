/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps$SynchronizedMap
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps$UnmodifiableMap
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSets;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Int2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Int2ObjectMaps() {
    }

    public static <V> ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap<V> int2ObjectMap) {
        ObjectSet<Int2ObjectMap.Entry<V>> objectSet = int2ObjectMap.int2ObjectEntrySet();
        return objectSet instanceof Int2ObjectMap.FastEntrySet ? ((Int2ObjectMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> void fastForEach(Int2ObjectMap<V> int2ObjectMap, Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
        ObjectSet<Int2ObjectMap.Entry<V>> objectSet = int2ObjectMap.int2ObjectEntrySet();
        if (objectSet instanceof Int2ObjectMap.FastEntrySet) {
            ((Int2ObjectMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <V> ObjectIterable<Int2ObjectMap.Entry<V>> fastIterable(Int2ObjectMap<V> int2ObjectMap) {
        ObjectSet<Int2ObjectMap.Entry<V>> objectSet = int2ObjectMap.int2ObjectEntrySet();
        return objectSet instanceof Int2ObjectMap.FastEntrySet ? new ObjectIterable<Int2ObjectMap.Entry<V>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
                return ((Int2ObjectMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
                return this.val$entries.spliterator();
            }

            @Override
            public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
                ((Int2ObjectMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }

            @Override
            public Spliterator spliterator() {
                return this.spliterator();
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : objectSet;
    }

    public static <V> Int2ObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Int2ObjectMap<V> singleton(int n, V v) {
        return new Singleton<V>(n, v);
    }

    public static <V> Int2ObjectMap<V> singleton(Integer n, V v) {
        return new Singleton<V>(n, v);
    }

    public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> int2ObjectMap) {
        return new SynchronizedMap(int2ObjectMap);
    }

    public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> int2ObjectMap, Object object) {
        return new SynchronizedMap(int2ObjectMap, object);
    }

    public static <V> Int2ObjectMap<V> unmodifiable(Int2ObjectMap<? extends V> int2ObjectMap) {
        return new UnmodifiableMap(int2ObjectMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyMap<V>
    extends Int2ObjectFunctions.EmptyFunction<V>
    implements Int2ObjectMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        @Deprecated
        public V getOrDefault(Object object, V v) {
            return v;
        }

        @Override
        public V getOrDefault(int n, V v) {
            return v;
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
        }

        @Override
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public void forEach(BiConsumer<? super Integer, ? super V> biConsumer) {
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton<V>
    extends Int2ObjectFunctions.Singleton<V>
    implements Int2ObjectMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
        protected transient IntSet keys;
        protected transient ObjectCollection<V> values;

        protected Singleton(int n, V v) {
            super(n, v);
        }

        @Override
        public boolean containsValue(Object object) {
            return Objects.equals(this.value, object);
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends V> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2ObjectMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<Integer, V>> entrySet() {
            return this.int2ObjectEntrySet();
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                this.values = ObjectSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value == null ? 0 : this.value.hashCode());
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
}

