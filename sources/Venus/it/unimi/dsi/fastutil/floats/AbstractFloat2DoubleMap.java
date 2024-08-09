/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2DoubleFunction;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleMaps;
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
public abstract class AbstractFloat2DoubleMap
extends AbstractFloat2DoubleFunction
implements Float2DoubleMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractFloat2DoubleMap() {
    }

    @Override
    public boolean containsValue(double d) {
        return this.values().contains(d);
    }

    @Override
    public boolean containsKey(float f) {
        Iterator iterator2 = this.float2DoubleEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Float2DoubleMap.Entry)iterator2.next()).getFloatKey() != f) continue;
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
            final AbstractFloat2DoubleMap this$0;
            {
                this.this$0 = abstractFloat2DoubleMap;
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
                    private final ObjectIterator<Float2DoubleMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Float2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        return ((Float2DoubleMap.Entry)this.i.next()).getFloatKey();
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
    public DoubleCollection values() {
        return new AbstractDoubleCollection(this){
            final AbstractFloat2DoubleMap this$0;
            {
                this.this$0 = abstractFloat2DoubleMap;
            }

            @Override
            public boolean contains(double d) {
                return this.this$0.containsValue(d);
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
            public DoubleIterator iterator() {
                return new DoubleIterator(this){
                    private final ObjectIterator<Float2DoubleMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Float2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Float2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    public void putAll(Map<? extends Float, ? extends Double> map) {
        if (map instanceof Float2DoubleMap) {
            ObjectIterator<Float2DoubleMap.Entry> objectIterator = Float2DoubleMaps.fastIterator((Float2DoubleMap)map);
            while (objectIterator.hasNext()) {
                Float2DoubleMap.Entry entry = (Float2DoubleMap.Entry)objectIterator.next();
                this.put(entry.getFloatKey(), entry.getDoubleValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Float, ? extends Double>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Float, ? extends Double> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Float2DoubleMap.Entry> objectIterator = Float2DoubleMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Float2DoubleMap.Entry)objectIterator.next()).hashCode();
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
        return this.float2DoubleEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Float2DoubleMap.Entry> objectIterator = Float2DoubleMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Float2DoubleMap.Entry entry = (Float2DoubleMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getFloatKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getDoubleValue()));
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
    extends AbstractObjectSet<Float2DoubleMap.Entry> {
        protected final Float2DoubleMap map;

        public BasicEntrySet(Float2DoubleMap float2DoubleMap) {
            this.map = float2DoubleMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2DoubleMap.Entry) {
                Float2DoubleMap.Entry entry = (Float2DoubleMap.Entry)object;
                float f = entry.getFloatKey();
                return this.map.containsKey(f) && Double.doubleToLongBits(this.map.get(f)) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            float f = ((Float)k).floatValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.map.containsKey(f) && Double.doubleToLongBits(this.map.get(f)) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2DoubleMap.Entry) {
                Float2DoubleMap.Entry entry = (Float2DoubleMap.Entry)object;
                return this.map.remove(entry.getFloatKey(), entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            float f = ((Float)k).floatValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            double d = (Double)v;
            return this.map.remove(f, d);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Float2DoubleMap.Entry {
        protected float key;
        protected double value;

        public BasicEntry() {
        }

        public BasicEntry(Float f, Double d) {
            this.key = f.floatValue();
            this.value = d;
        }

        public BasicEntry(float f, double d) {
            this.key = f;
            this.value = d;
        }

        @Override
        public float getFloatKey() {
            return this.key;
        }

        @Override
        public double getDoubleValue() {
            return this.value;
        }

        @Override
        public double setValue(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Float2DoubleMap.Entry) {
                Float2DoubleMap.Entry entry = (Float2DoubleMap.Entry)object;
                return Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Float)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)k).floatValue()) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ HashCommon.double2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

