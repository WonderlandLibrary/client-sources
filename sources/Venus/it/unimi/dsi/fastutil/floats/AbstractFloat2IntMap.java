/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2IntFunction;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.Float2IntMaps;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
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
public abstract class AbstractFloat2IntMap
extends AbstractFloat2IntFunction
implements Float2IntMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractFloat2IntMap() {
    }

    @Override
    public boolean containsValue(int n) {
        return this.values().contains(n);
    }

    @Override
    public boolean containsKey(float f) {
        Iterator iterator2 = this.float2IntEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Float2IntMap.Entry)iterator2.next()).getFloatKey() != f) continue;
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
            final AbstractFloat2IntMap this$0;
            {
                this.this$0 = abstractFloat2IntMap;
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
                    private final ObjectIterator<Float2IntMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Float2IntMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        return ((Float2IntMap.Entry)this.i.next()).getFloatKey();
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
    public IntCollection values() {
        return new AbstractIntCollection(this){
            final AbstractFloat2IntMap this$0;
            {
                this.this$0 = abstractFloat2IntMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsValue(n);
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
            public IntIterator iterator() {
                return new IntIterator(this){
                    private final ObjectIterator<Float2IntMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Float2IntMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Float2IntMap.Entry)this.i.next()).getIntValue();
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
    public void putAll(Map<? extends Float, ? extends Integer> map) {
        if (map instanceof Float2IntMap) {
            ObjectIterator<Float2IntMap.Entry> objectIterator = Float2IntMaps.fastIterator((Float2IntMap)map);
            while (objectIterator.hasNext()) {
                Float2IntMap.Entry entry = (Float2IntMap.Entry)objectIterator.next();
                this.put(entry.getFloatKey(), entry.getIntValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Float, ? extends Integer>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Float, ? extends Integer> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Float2IntMap.Entry> objectIterator = Float2IntMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Float2IntMap.Entry)objectIterator.next()).hashCode();
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
        return this.float2IntEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Float2IntMap.Entry> objectIterator = Float2IntMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Float2IntMap.Entry entry = (Float2IntMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getFloatKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getIntValue()));
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
    extends AbstractObjectSet<Float2IntMap.Entry> {
        protected final Float2IntMap map;

        public BasicEntrySet(Float2IntMap float2IntMap) {
            this.map = float2IntMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2IntMap.Entry) {
                Float2IntMap.Entry entry = (Float2IntMap.Entry)object;
                float f = entry.getFloatKey();
                return this.map.containsKey(f) && this.map.get(f) == entry.getIntValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            float f = ((Float)k).floatValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            return this.map.containsKey(f) && this.map.get(f) == ((Integer)v).intValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2IntMap.Entry) {
                Float2IntMap.Entry entry = (Float2IntMap.Entry)object;
                return this.map.remove(entry.getFloatKey(), entry.getIntValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            float f = ((Float)k).floatValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            int n = (Integer)v;
            return this.map.remove(f, n);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Float2IntMap.Entry {
        protected float key;
        protected int value;

        public BasicEntry() {
        }

        public BasicEntry(Float f, Integer n) {
            this.key = f.floatValue();
            this.value = n;
        }

        public BasicEntry(float f, int n) {
            this.key = f;
            this.value = n;
        }

        @Override
        public float getFloatKey() {
            return this.key;
        }

        @Override
        public int getIntValue() {
            return this.value;
        }

        @Override
        public int setValue(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2IntMap.Entry) {
                Float2IntMap.Entry entry = (Float2IntMap.Entry)object;
                return Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && this.value == entry.getIntValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)k).floatValue()) && this.value == (Integer)v;
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

