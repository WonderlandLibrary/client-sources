/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps$SynchronizedMap
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps$UnmodifiableMap
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntSets;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Object2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Object2IntMaps() {
    }

    public static <K> ObjectIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap<K> object2IntMap) {
        ObjectSet<Object2IntMap.Entry<K>> objectSet = object2IntMap.object2IntEntrySet();
        return objectSet instanceof Object2IntMap.FastEntrySet ? ((Object2IntMap.FastEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> void fastForEach(Object2IntMap<K> object2IntMap, Consumer<? super Object2IntMap.Entry<K>> consumer) {
        ObjectSet<Object2IntMap.Entry<K>> objectSet = object2IntMap.object2IntEntrySet();
        if (objectSet instanceof Object2IntMap.FastEntrySet) {
            ((Object2IntMap.FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    public static <K> ObjectIterable<Object2IntMap.Entry<K>> fastIterable(Object2IntMap<K> object2IntMap) {
        ObjectSet<Object2IntMap.Entry<K>> objectSet = object2IntMap.object2IntEntrySet();
        return objectSet instanceof Object2IntMap.FastEntrySet ? new ObjectIterable<Object2IntMap.Entry<K>>(objectSet){
            final ObjectSet val$entries;
            {
                this.val$entries = objectSet;
            }

            @Override
            public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
                return ((Object2IntMap.FastEntrySet)this.val$entries).fastIterator();
            }

            @Override
            public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
                return this.val$entries.spliterator();
            }

            @Override
            public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
                ((Object2IntMap.FastEntrySet)this.val$entries).fastForEach(consumer);
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

    public static <K> Object2IntMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Object2IntMap<K> singleton(K k, int n) {
        return new Singleton<K>(k, n);
    }

    public static <K> Object2IntMap<K> singleton(K k, Integer n) {
        return new Singleton<K>(k, n);
    }

    public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> object2IntMap) {
        return new SynchronizedMap(object2IntMap);
    }

    public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> object2IntMap, Object object) {
        return new SynchronizedMap(object2IntMap, object);
    }

    public static <K> Object2IntMap<K> unmodifiable(Object2IntMap<? extends K> object2IntMap) {
        return new UnmodifiableMap(object2IntMap);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyMap<K>
    extends Object2IntFunctions.EmptyFunction<K>
    implements Object2IntMap<K>,
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
        public Integer getOrDefault(Object object, Integer n) {
            return n;
        }

        @Override
        public int getOrDefault(Object object, int n) {
            return n;
        }

        @Override
        @Deprecated
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public IntCollection values() {
            return IntSets.EMPTY_SET;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super Integer> biConsumer) {
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
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Integer)object2);
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
    extends Object2IntFunctions.Singleton<K>
    implements Object2IntMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
        protected transient ObjectSet<K> keys;
        protected transient IntCollection values;

        protected Singleton(K k, int n) {
            super(k, n);
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
        public void putAll(Map<? extends K, ? extends Integer> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractObject2IntMap.BasicEntry<Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        @Deprecated
        public ObjectSet<Map.Entry<K, Integer>> entrySet() {
            return this.object2IntEntrySet();
        }

        @Override
        public ObjectSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.singleton(this.key);
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
            return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
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

