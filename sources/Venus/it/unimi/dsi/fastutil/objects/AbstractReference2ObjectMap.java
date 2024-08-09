/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2ObjectFunction;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractReference2ObjectMap<K, V>
extends AbstractReference2ObjectFunction<K, V>
implements Reference2ObjectMap<K, V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2ObjectMap() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values().contains(object);
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator iterator2 = this.reference2ObjectEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Reference2ObjectMap.Entry)iterator2.next()).getKey() != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final AbstractReference2ObjectMap this$0;
            {
                this.this$0 = abstractReference2ObjectMap;
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
                    private final ObjectIterator<Reference2ObjectMap.Entry<K, V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2ObjectMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        return ((Reference2ObjectMap.Entry)this.i.next()).getKey();
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
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(this){
            final AbstractReference2ObjectMap this$0;
            {
                this.this$0 = abstractReference2ObjectMap;
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
                    private final ObjectIterator<Reference2ObjectMap.Entry<K, V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2ObjectMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Reference2ObjectMap.Entry)this.i.next()).getValue();
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
        if (map instanceof Reference2ObjectMap) {
            ObjectIterator objectIterator = Reference2ObjectMaps.fastIterator((Reference2ObjectMap)map);
            while (objectIterator.hasNext()) {
                Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry)objectIterator.next();
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
        ObjectIterator objectIterator = Reference2ObjectMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Reference2ObjectMap.Entry)objectIterator.next()).hashCode();
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
        return this.reference2ObjectEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Reference2ObjectMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry)objectIterator.next();
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
    extends AbstractObjectSet<Reference2ObjectMap.Entry<K, V>> {
        protected final Reference2ObjectMap<K, V> map;

        public BasicEntrySet(Reference2ObjectMap<K, V> reference2ObjectMap) {
            this.map = reference2ObjectMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2ObjectMap.Entry) {
                Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry)object;
                Object k = entry.getKey();
                return this.map.containsKey(k) && Objects.equals(this.map.get(k), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            return this.map.containsKey(k) && Objects.equals(this.map.get(k), v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2ObjectMap.Entry) {
                Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry)object;
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
    implements Reference2ObjectMap.Entry<K, V> {
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
            if (object instanceof Reference2ObjectMap.Entry) {
                Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry)object;
                return this.key == entry.getKey() && Objects.equals(this.value, entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            return this.key == k && Objects.equals(this.value, v);
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

