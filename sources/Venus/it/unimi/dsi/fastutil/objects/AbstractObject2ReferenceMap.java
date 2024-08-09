/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ReferenceFunction;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMaps;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractObject2ReferenceMap<K, V>
extends AbstractObject2ReferenceFunction<K, V>
implements Object2ReferenceMap<K, V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2ReferenceMap() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values().contains(object);
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator iterator2 = this.object2ReferenceEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Object2ReferenceMap.Entry)iterator2.next()).getKey() != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(this){
            final AbstractObject2ReferenceMap this$0;
            {
                this.this$0 = abstractObject2ReferenceMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsKey(object);
            }

            @Override
            public int size() {
                return this.this$0.size();
            }

            @Override
            public void clear() {
                this.this$0.clear();
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(this){
                    private final ObjectIterator<Object2ReferenceMap.Entry<K, V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Object2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        return ((Object2ReferenceMap.Entry)this.i.next()).getKey();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }
                };
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(this){
            final AbstractObject2ReferenceMap this$0;
            {
                this.this$0 = abstractObject2ReferenceMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsValue(object);
            }

            @Override
            public int size() {
                return this.this$0.size();
            }

            @Override
            public void clear() {
                this.this$0.clear();
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(this){
                    private final ObjectIterator<Object2ReferenceMap.Entry<K, V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Object2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Object2ReferenceMap.Entry)this.i.next()).getValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                };
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map instanceof Object2ReferenceMap) {
            ObjectIterator objectIterator = Object2ReferenceMaps.fastIterator((Object2ReferenceMap)map);
            while (objectIterator.hasNext()) {
                Object2ReferenceMap.Entry entry = (Object2ReferenceMap.Entry)objectIterator.next();
                this.put(entry.getKey(), entry.getValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<K, V>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<K, V> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Object2ReferenceMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Object2ReferenceMap.Entry)objectIterator.next()).hashCode();
        }
        return n;
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
        if (map.size() != this.size()) {
            return true;
        }
        return this.object2ReferenceEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Object2ReferenceMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Object2ReferenceMap.Entry entry = (Object2ReferenceMap.Entry)objectIterator.next();
            if (this == entry.getKey()) {
                stringBuilder.append("(this map)");
            } else {
                stringBuilder.append(String.valueOf(entry.getKey()));
            }
            stringBuilder.append("=>");
            if (this == entry.getValue()) {
                stringBuilder.append("(this map)");
                continue;
            }
            stringBuilder.append(String.valueOf(entry.getValue()));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    public static abstract class BasicEntrySet<K, V>
    extends AbstractObjectSet<Object2ReferenceMap.Entry<K, V>> {
        protected final Object2ReferenceMap<K, V> map;

        public BasicEntrySet(Object2ReferenceMap<K, V> object2ReferenceMap) {
            this.map = object2ReferenceMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Object2ReferenceMap.Entry) {
                Object2ReferenceMap.Entry entry = (Object2ReferenceMap.Entry)object;
                Object k = entry.getKey();
                return this.map.containsKey(k) && this.map.get(k) == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            return this.map.containsKey(k) && this.map.get(k) == v;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Object2ReferenceMap.Entry) {
                Object2ReferenceMap.Entry entry = (Object2ReferenceMap.Entry)object;
                return this.map.remove(entry.getKey(), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            return this.map.remove(k, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<K, V>
    implements Object2ReferenceMap.Entry<K, V> {
        protected K key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(K k, V v) {
            this.key = k;
            this.value = v;
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
        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Object2ReferenceMap.Entry) {
                Object2ReferenceMap.Entry entry = (Object2ReferenceMap.Entry)object;
                return Objects.equals(this.key, entry.getKey()) && this.value == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            return Objects.equals(this.key, k) && this.value == v;
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : System.identityHashCode(this.value));
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

