/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractObject2ObjectMap<K, V>
extends AbstractObject2ObjectFunction<K, V>
implements Object2ObjectMap<K, V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2ObjectMap() {
    }

    @Override
    public boolean containsKey(Object k) {
        Iterator i = this.object2ObjectEntrySet().iterator();
        do {
            if (!i.hasNext()) return false;
        } while (((Object2ObjectMap.Entry)i.next()).getKey() != k);
        return true;
    }

    @Override
    public boolean containsValue(Object v) {
        Iterator i = this.object2ObjectEntrySet().iterator();
        do {
            if (!i.hasNext()) return false;
        } while (((Object2ObjectMap.Entry)i.next()).getValue() != v);
        return true;
    }

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(){

            @Override
            public boolean contains(Object k) {
                return AbstractObject2ObjectMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractObject2ObjectMap.this.size();
            }

            @Override
            public void clear() {
                AbstractObject2ObjectMap.this.clear();
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(){
                    private final ObjectIterator<Object2ObjectMap.Entry<K, V>> i;
                    {
                        this.i = Object2ObjectMaps.fastIterator(AbstractObject2ObjectMap.this);
                    }

                    @Override
                    public K next() {
                        return ((Object2ObjectMap.Entry)this.i.next()).getKey();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }

                    @Override
                    public void forEachRemaining(Consumer<? super K> action) {
                        this.i.forEachRemaining((? super E entry) -> action.accept((Object)entry.getKey()));
                    }
                };
            }

            @Override
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractObject2ObjectMap.this), 65);
            }
        };
    }

    @Override
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(){

            @Override
            public boolean contains(Object k) {
                return AbstractObject2ObjectMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractObject2ObjectMap.this.size();
            }

            @Override
            public void clear() {
                AbstractObject2ObjectMap.this.clear();
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(){
                    private final ObjectIterator<Object2ObjectMap.Entry<K, V>> i;
                    {
                        this.i = Object2ObjectMaps.fastIterator(AbstractObject2ObjectMap.this);
                    }

                    @Override
                    public V next() {
                        return ((Object2ObjectMap.Entry)this.i.next()).getValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }

                    @Override
                    public void forEachRemaining(Consumer<? super V> action) {
                        this.i.forEachRemaining((? super E entry) -> action.accept((Object)entry.getValue()));
                    }
                };
            }

            @Override
            public ObjectSpliterator<V> spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractObject2ObjectMap.this), 64);
            }
        };
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m instanceof Object2ObjectMap) {
            ObjectIterator i = Object2ObjectMaps.fastIterator((Object2ObjectMap)m);
            while (i.hasNext()) {
                Object2ObjectMap.Entry e = (Object2ObjectMap.Entry)i.next();
                this.put(e.getKey(), e.getValue());
            }
            return;
        }
        int n = m.size();
        Iterator<Map.Entry<K, V>> i = m.entrySet().iterator();
        while (n-- != 0) {
            Map.Entry<K, V> e = i.next();
            this.put(e.getKey(), e.getValue());
        }
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        ObjectIterator i = Object2ObjectMaps.fastIterator(this);
        while (n-- != 0) {
            h += ((Object2ObjectMap.Entry)i.next()).hashCode();
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        Map m = (Map)o;
        if (m.size() == this.size()) return this.object2ObjectEntrySet().containsAll(m.entrySet());
        return false;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = Object2ObjectMaps.fastIterator(this);
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (true) {
            if (n-- == 0) {
                s.append("}");
                return s.toString();
            }
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Object2ObjectMap.Entry e = (Object2ObjectMap.Entry)i.next();
            if (this == e.getKey()) {
                s.append("(this map)");
            } else {
                s.append(String.valueOf(e.getKey()));
            }
            s.append("=>");
            if (this == e.getValue()) {
                s.append("(this map)");
                continue;
            }
            s.append(String.valueOf(e.getValue()));
        }
    }

    public static abstract class BasicEntrySet<K, V>
    extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> {
        protected final Object2ObjectMap<K, V> map;

        public BasicEntrySet(Object2ObjectMap<K, V> map) {
            this.map = map;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2ObjectMap.Entry) {
                Object2ObjectMap.Entry e = (Object2ObjectMap.Entry)o;
                Object k = e.getKey();
                if (!this.map.containsKey(k)) return false;
                if (!Objects.equals(this.map.get(k), e.getValue())) return false;
                return true;
            }
            Map.Entry e = (Map.Entry)o;
            Object k = e.getKey();
            Object value = e.getValue();
            if (!this.map.containsKey(k)) return false;
            if (!Objects.equals(this.map.get(k), value)) return false;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2ObjectMap.Entry) {
                Object2ObjectMap.Entry e = (Object2ObjectMap.Entry)o;
                return this.map.remove(e.getKey(), e.getValue());
            }
            Map.Entry e = (Map.Entry)o;
            Object k = e.getKey();
            Object v = e.getValue();
            return this.map.remove(k, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
        }
    }

    public static class BasicEntry<K, V>
    implements Object2ObjectMap.Entry<K, V> {
        protected K key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2ObjectMap.Entry) {
                Object2ObjectMap.Entry e = (Object2ObjectMap.Entry)o;
                if (!Objects.equals(this.key, e.getKey())) return false;
                if (!Objects.equals(this.value, e.getValue())) return false;
                return true;
            }
            Map.Entry e = (Map.Entry)o;
            Object key = e.getKey();
            Object value = e.getValue();
            if (!Objects.equals(this.key, key)) return false;
            if (!Objects.equals(this.value, value)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int n;
            int n2 = this.key == null ? 0 : this.key.hashCode();
            if (this.value == null) {
                n = 0;
                return n2 ^ n;
            }
            n = this.value.hashCode();
            return n2 ^ n;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

