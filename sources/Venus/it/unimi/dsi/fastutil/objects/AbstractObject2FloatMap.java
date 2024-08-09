/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2FloatFunction;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractObject2FloatMap<K>
extends AbstractObject2FloatFunction<K>
implements Object2FloatMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2FloatMap() {
    }

    @Override
    public boolean containsValue(float f) {
        return this.values().contains(f);
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator iterator2 = this.object2FloatEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Object2FloatMap.Entry)iterator2.next()).getKey() != object) continue;
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
            final AbstractObject2FloatMap this$0;
            {
                this.this$0 = abstractObject2FloatMap;
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
                    private final ObjectIterator<Object2FloatMap.Entry<K>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Object2FloatMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        return ((Object2FloatMap.Entry)this.i.next()).getKey();
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
    public FloatCollection values() {
        return new AbstractFloatCollection(this){
            final AbstractObject2FloatMap this$0;
            {
                this.this$0 = abstractObject2FloatMap;
            }

            @Override
            public boolean contains(float f) {
                return this.this$0.containsValue(f);
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
                    private final ObjectIterator<Object2FloatMap.Entry<K>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Object2FloatMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        return ((Object2FloatMap.Entry)this.i.next()).getFloatValue();
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
    public void putAll(Map<? extends K, ? extends Float> map) {
        if (map instanceof Object2FloatMap) {
            ObjectIterator objectIterator = Object2FloatMaps.fastIterator((Object2FloatMap)map);
            while (objectIterator.hasNext()) {
                Object2FloatMap.Entry entry = (Object2FloatMap.Entry)objectIterator.next();
                this.put(entry.getKey(), entry.getFloatValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<K, Float>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<K, Float> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Object2FloatMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Object2FloatMap.Entry)objectIterator.next()).hashCode();
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
        return this.object2FloatEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Object2FloatMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Object2FloatMap.Entry entry = (Object2FloatMap.Entry)objectIterator.next();
            if (this == entry.getKey()) {
                stringBuilder.append("(this map)");
            } else {
                stringBuilder.append(String.valueOf(entry.getKey()));
            }
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getFloatValue()));
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

    public static abstract class BasicEntrySet<K>
    extends AbstractObjectSet<Object2FloatMap.Entry<K>> {
        protected final Object2FloatMap<K> map;

        public BasicEntrySet(Object2FloatMap<K> object2FloatMap) {
            this.map = object2FloatMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Object2FloatMap.Entry) {
                Object2FloatMap.Entry entry = (Object2FloatMap.Entry)object;
                Object k = entry.getKey();
                return this.map.containsKey(k) && Float.floatToIntBits(this.map.getFloat(k)) == Float.floatToIntBits(entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            return this.map.containsKey(k) && Float.floatToIntBits(this.map.getFloat(k)) == Float.floatToIntBits(((Float)v).floatValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Object2FloatMap.Entry) {
                Object2FloatMap.Entry entry = (Object2FloatMap.Entry)object;
                return this.map.remove(entry.getKey(), entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            float f = ((Float)v).floatValue();
            return this.map.remove(k, f);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<K>
    implements Object2FloatMap.Entry<K> {
        protected K key;
        protected float value;

        public BasicEntry() {
        }

        public BasicEntry(K k, Float f) {
            this.key = k;
            this.value = f.floatValue();
        }

        public BasicEntry(K k, float f) {
            this.key = k;
            this.value = f;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public float getFloatValue() {
            return this.value;
        }

        @Override
        public float setValue(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Object2FloatMap.Entry) {
                Object2FloatMap.Entry entry = (Object2FloatMap.Entry)object;
                return Objects.equals(this.key, entry.getKey()) && Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            return Objects.equals(this.key, k) && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)v).floatValue());
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ HashCommon.float2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

