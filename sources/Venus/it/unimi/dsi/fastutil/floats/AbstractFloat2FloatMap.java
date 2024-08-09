/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatFunction;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMaps;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractFloat2FloatMap
extends AbstractFloat2FloatFunction
implements Float2FloatMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractFloat2FloatMap() {
    }

    @Override
    public boolean containsValue(float f) {
        return this.values().contains(f);
    }

    @Override
    public boolean containsKey(float f) {
        Iterator iterator2 = this.float2FloatEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Float2FloatMap.Entry)iterator2.next()).getFloatKey() != f) continue;
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
            final AbstractFloat2FloatMap this$0;
            {
                this.this$0 = abstractFloat2FloatMap;
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
                    private final ObjectIterator<Float2FloatMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Float2FloatMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        return ((Float2FloatMap.Entry)this.i.next()).getFloatKey();
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
            final AbstractFloat2FloatMap this$0;
            {
                this.this$0 = abstractFloat2FloatMap;
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
                    private final ObjectIterator<Float2FloatMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Float2FloatMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        return ((Float2FloatMap.Entry)this.i.next()).getFloatValue();
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
    public void putAll(Map<? extends Float, ? extends Float> map) {
        if (map instanceof Float2FloatMap) {
            ObjectIterator<Float2FloatMap.Entry> objectIterator = Float2FloatMaps.fastIterator((Float2FloatMap)map);
            while (objectIterator.hasNext()) {
                Float2FloatMap.Entry entry = (Float2FloatMap.Entry)objectIterator.next();
                this.put(entry.getFloatKey(), entry.getFloatValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Float, ? extends Float>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Float, ? extends Float> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Float2FloatMap.Entry> objectIterator = Float2FloatMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Float2FloatMap.Entry)objectIterator.next()).hashCode();
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
        return this.float2FloatEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Float2FloatMap.Entry> objectIterator = Float2FloatMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Float2FloatMap.Entry entry = (Float2FloatMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getFloatKey()));
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

    public static abstract class BasicEntrySet
    extends AbstractObjectSet<Float2FloatMap.Entry> {
        protected final Float2FloatMap map;

        public BasicEntrySet(Float2FloatMap float2FloatMap) {
            this.map = float2FloatMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2FloatMap.Entry) {
                Float2FloatMap.Entry entry = (Float2FloatMap.Entry)object;
                float f = entry.getFloatKey();
                return this.map.containsKey(f) && Float.floatToIntBits(this.map.get(f)) == Float.floatToIntBits(entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            float f = ((Float)k).floatValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            return this.map.containsKey(f) && Float.floatToIntBits(this.map.get(f)) == Float.floatToIntBits(((Float)v).floatValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2FloatMap.Entry) {
                Float2FloatMap.Entry entry = (Float2FloatMap.Entry)object;
                return this.map.remove(entry.getFloatKey(), entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            float f = ((Float)k).floatValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            float f2 = ((Float)v).floatValue();
            return this.map.remove(f, f2);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Float2FloatMap.Entry {
        protected float key;
        protected float value;

        public BasicEntry() {
        }

        public BasicEntry(Float f, Float f2) {
            this.key = f.floatValue();
            this.value = f2.floatValue();
        }

        public BasicEntry(float f, float f2) {
            this.key = f;
            this.value = f2;
        }

        @Override
        public float getFloatKey() {
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
            if (object instanceof Float2FloatMap.Entry) {
                Float2FloatMap.Entry entry = (Float2FloatMap.Entry)object;
                return Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)k).floatValue()) && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)v).floatValue());
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ HashCommon.float2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

