/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectFunction;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMaps;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractFloat2ObjectMap<V>
extends AbstractFloat2ObjectFunction<V>
implements Float2ObjectMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractFloat2ObjectMap() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values().contains(object);
    }

    @Override
    public boolean containsKey(float f) {
        Iterator iterator2 = this.float2ObjectEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Float2ObjectMap.Entry)iterator2.next()).getFloatKey() != f) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final AbstractFloat2ObjectMap this$0;
            {
                this.this$0 = abstractFloat2ObjectMap;
            }

            @Override
            public boolean contains(float f) {
                return this.this$0.containsKey(f);
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
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    private final ObjectIterator<Float2ObjectMap.Entry<V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Float2ObjectMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        return ((Float2ObjectMap.Entry)this.i.next()).getFloatKey();
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
            final AbstractFloat2ObjectMap this$0;
            {
                this.this$0 = abstractFloat2ObjectMap;
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
                    private final ObjectIterator<Float2ObjectMap.Entry<V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Float2ObjectMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Float2ObjectMap.Entry)this.i.next()).getValue();
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
    public void putAll(Map<? extends Float, ? extends V> map) {
        if (map instanceof Float2ObjectMap) {
            ObjectIterator objectIterator = Float2ObjectMaps.fastIterator((Float2ObjectMap)map);
            while (objectIterator.hasNext()) {
                Float2ObjectMap.Entry entry = (Float2ObjectMap.Entry)objectIterator.next();
                this.put(entry.getFloatKey(), entry.getValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<Float, V>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<Float, V> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Float2ObjectMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Float2ObjectMap.Entry)objectIterator.next()).hashCode();
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
        return this.float2ObjectEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Float2ObjectMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Float2ObjectMap.Entry entry = (Float2ObjectMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getFloatKey()));
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

    public static abstract class BasicEntrySet<V>
    extends AbstractObjectSet<Float2ObjectMap.Entry<V>> {
        protected final Float2ObjectMap<V> map;

        public BasicEntrySet(Float2ObjectMap<V> float2ObjectMap) {
            this.map = float2ObjectMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2ObjectMap.Entry) {
                Float2ObjectMap.Entry entry = (Float2ObjectMap.Entry)object;
                float f = entry.getFloatKey();
                return this.map.containsKey(f) && Objects.equals(this.map.get(f), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            float f = ((Float)k).floatValue();
            Object v = entry.getValue();
            return this.map.containsKey(f) && Objects.equals(this.map.get(f), v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2ObjectMap.Entry) {
                Float2ObjectMap.Entry entry = (Float2ObjectMap.Entry)object;
                return this.map.remove(entry.getFloatKey(), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            float f = ((Float)k).floatValue();
            Object v = entry.getValue();
            return this.map.remove(f, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<V>
    implements Float2ObjectMap.Entry<V> {
        protected float key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(Float f, V v) {
            this.key = f.floatValue();
            this.value = v;
        }

        public BasicEntry(float f, V v) {
            this.key = f;
            this.value = v;
        }

        @Override
        public float getFloatKey() {
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
            if (object instanceof Float2ObjectMap.Entry) {
                Float2ObjectMap.Entry entry = (Float2ObjectMap.Entry)object;
                return Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && Objects.equals(this.value, entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            Object v = entry.getValue();
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)k).floatValue()) && Objects.equals(this.value, v);
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

