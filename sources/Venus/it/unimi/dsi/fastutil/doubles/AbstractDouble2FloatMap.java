/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatFunction;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatMaps;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
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
public abstract class AbstractDouble2FloatMap
extends AbstractDouble2FloatFunction
implements Double2FloatMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractDouble2FloatMap() {
    }

    @Override
    public boolean containsValue(float f) {
        return this.values().contains(f);
    }

    @Override
    public boolean containsKey(double d) {
        Iterator iterator2 = this.double2FloatEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Double2FloatMap.Entry)iterator2.next()).getDoubleKey() != d) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final AbstractDouble2FloatMap this$0;
            {
                this.this$0 = abstractDouble2FloatMap;
            }

            @Override
            public boolean contains(double d) {
                return this.this$0.containsKey(d);
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
                    private final ObjectIterator<Double2FloatMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2FloatMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Double2FloatMap.Entry)this.i.next()).getDoubleKey();
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
            final AbstractDouble2FloatMap this$0;
            {
                this.this$0 = abstractDouble2FloatMap;
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
                    private final ObjectIterator<Double2FloatMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2FloatMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        return ((Double2FloatMap.Entry)this.i.next()).getFloatValue();
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
    public void putAll(Map<? extends Double, ? extends Float> map) {
        if (map instanceof Double2FloatMap) {
            ObjectIterator<Double2FloatMap.Entry> objectIterator = Double2FloatMaps.fastIterator((Double2FloatMap)map);
            while (objectIterator.hasNext()) {
                Double2FloatMap.Entry entry = (Double2FloatMap.Entry)objectIterator.next();
                this.put(entry.getDoubleKey(), entry.getFloatValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Double, ? extends Float>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Double, ? extends Float> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Double2FloatMap.Entry> objectIterator = Double2FloatMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Double2FloatMap.Entry)objectIterator.next()).hashCode();
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
        return this.double2FloatEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Double2FloatMap.Entry> objectIterator = Double2FloatMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Double2FloatMap.Entry entry = (Double2FloatMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getDoubleKey()));
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
    extends AbstractObjectSet<Double2FloatMap.Entry> {
        protected final Double2FloatMap map;

        public BasicEntrySet(Double2FloatMap double2FloatMap) {
            this.map = double2FloatMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2FloatMap.Entry) {
                Double2FloatMap.Entry entry = (Double2FloatMap.Entry)object;
                double d = entry.getDoubleKey();
                return this.map.containsKey(d) && Float.floatToIntBits(this.map.get(d)) == Float.floatToIntBits(entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            return this.map.containsKey(d) && Float.floatToIntBits(this.map.get(d)) == Float.floatToIntBits(((Float)v).floatValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2FloatMap.Entry) {
                Double2FloatMap.Entry entry = (Double2FloatMap.Entry)object;
                return this.map.remove(entry.getDoubleKey(), entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            float f = ((Float)v).floatValue();
            return this.map.remove(d, f);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Double2FloatMap.Entry {
        protected double key;
        protected float value;

        public BasicEntry() {
        }

        public BasicEntry(Double d, Float f) {
            this.key = d;
            this.value = f.floatValue();
        }

        public BasicEntry(double d, float f) {
            this.key = d;
            this.value = f;
        }

        @Override
        public double getDoubleKey() {
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
            if (object instanceof Double2FloatMap.Entry) {
                Double2FloatMap.Entry entry = (Double2FloatMap.Entry)object;
                return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)k) && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)v).floatValue());
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ HashCommon.float2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

