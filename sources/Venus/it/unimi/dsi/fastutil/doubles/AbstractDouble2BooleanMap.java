/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanFunction;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMaps;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
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
public abstract class AbstractDouble2BooleanMap
extends AbstractDouble2BooleanFunction
implements Double2BooleanMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractDouble2BooleanMap() {
    }

    @Override
    public boolean containsValue(boolean bl) {
        return this.values().contains(bl);
    }

    @Override
    public boolean containsKey(double d) {
        Iterator iterator2 = this.double2BooleanEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Double2BooleanMap.Entry)iterator2.next()).getDoubleKey() != d) continue;
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
            final AbstractDouble2BooleanMap this$0;
            {
                this.this$0 = abstractDouble2BooleanMap;
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
                    private final ObjectIterator<Double2BooleanMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Double2BooleanMap.Entry)this.i.next()).getDoubleKey();
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
    public BooleanCollection values() {
        return new AbstractBooleanCollection(this){
            final AbstractDouble2BooleanMap this$0;
            {
                this.this$0 = abstractDouble2BooleanMap;
            }

            @Override
            public boolean contains(boolean bl) {
                return this.this$0.containsValue(bl);
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
            public BooleanIterator iterator() {
                return new BooleanIterator(this){
                    private final ObjectIterator<Double2BooleanMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        return ((Double2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    public void putAll(Map<? extends Double, ? extends Boolean> map) {
        if (map instanceof Double2BooleanMap) {
            ObjectIterator<Double2BooleanMap.Entry> objectIterator = Double2BooleanMaps.fastIterator((Double2BooleanMap)map);
            while (objectIterator.hasNext()) {
                Double2BooleanMap.Entry entry = (Double2BooleanMap.Entry)objectIterator.next();
                this.put(entry.getDoubleKey(), entry.getBooleanValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Double, ? extends Boolean>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Double, ? extends Boolean> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Double2BooleanMap.Entry> objectIterator = Double2BooleanMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Double2BooleanMap.Entry)objectIterator.next()).hashCode();
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
        return this.double2BooleanEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Double2BooleanMap.Entry> objectIterator = Double2BooleanMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Double2BooleanMap.Entry entry = (Double2BooleanMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getDoubleKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getBooleanValue()));
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
    extends AbstractObjectSet<Double2BooleanMap.Entry> {
        protected final Double2BooleanMap map;

        public BasicEntrySet(Double2BooleanMap double2BooleanMap) {
            this.map = double2BooleanMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2BooleanMap.Entry) {
                Double2BooleanMap.Entry entry = (Double2BooleanMap.Entry)object;
                double d = entry.getDoubleKey();
                return this.map.containsKey(d) && this.map.get(d) == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.map.containsKey(d) && this.map.get(d) == ((Boolean)v).booleanValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2BooleanMap.Entry) {
                Double2BooleanMap.Entry entry = (Double2BooleanMap.Entry)object;
                return this.map.remove(entry.getDoubleKey(), entry.getBooleanValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            boolean bl = (Boolean)v;
            return this.map.remove(d, bl);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Double2BooleanMap.Entry {
        protected double key;
        protected boolean value;

        public BasicEntry() {
        }

        public BasicEntry(Double d, Boolean bl) {
            this.key = d;
            this.value = bl;
        }

        public BasicEntry(double d, boolean bl) {
            this.key = d;
            this.value = bl;
        }

        @Override
        public double getDoubleKey() {
            return this.key;
        }

        @Override
        public boolean getBooleanValue() {
            return this.value;
        }

        @Override
        public boolean setValue(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2BooleanMap.Entry) {
                Double2BooleanMap.Entry entry = (Double2BooleanMap.Entry)object;
                return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)k) && this.value == (Boolean)v;
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ (this.value ? 1231 : 1237);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

