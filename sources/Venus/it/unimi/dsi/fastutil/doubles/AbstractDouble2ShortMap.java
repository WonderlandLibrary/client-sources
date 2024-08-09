/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortFunction;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap;
import it.unimi.dsi.fastutil.doubles.Double2ShortMaps;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDouble2ShortMap
extends AbstractDouble2ShortFunction
implements Double2ShortMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractDouble2ShortMap() {
    }

    @Override
    public boolean containsValue(short s) {
        return this.values().contains(s);
    }

    @Override
    public boolean containsKey(double d) {
        Iterator iterator2 = this.double2ShortEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Double2ShortMap.Entry)iterator2.next()).getDoubleKey() != d) continue;
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
            final AbstractDouble2ShortMap this$0;
            {
                this.this$0 = abstractDouble2ShortMap;
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
                    private final ObjectIterator<Double2ShortMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2ShortMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Double2ShortMap.Entry)this.i.next()).getDoubleKey();
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
    public ShortCollection values() {
        return new AbstractShortCollection(this){
            final AbstractDouble2ShortMap this$0;
            {
                this.this$0 = abstractDouble2ShortMap;
            }

            @Override
            public boolean contains(short s) {
                return this.this$0.containsValue(s);
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
            public ShortIterator iterator() {
                return new ShortIterator(this){
                    private final ObjectIterator<Double2ShortMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2ShortMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Double2ShortMap.Entry)this.i.next()).getShortValue();
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
    public void putAll(Map<? extends Double, ? extends Short> map) {
        if (map instanceof Double2ShortMap) {
            ObjectIterator<Double2ShortMap.Entry> objectIterator = Double2ShortMaps.fastIterator((Double2ShortMap)map);
            while (objectIterator.hasNext()) {
                Double2ShortMap.Entry entry = (Double2ShortMap.Entry)objectIterator.next();
                this.put(entry.getDoubleKey(), entry.getShortValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Double, ? extends Short>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Double, ? extends Short> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Double2ShortMap.Entry> objectIterator = Double2ShortMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Double2ShortMap.Entry)objectIterator.next()).hashCode();
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
        return this.double2ShortEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Double2ShortMap.Entry> objectIterator = Double2ShortMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Double2ShortMap.Entry entry = (Double2ShortMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getDoubleKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getShortValue()));
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
    extends AbstractObjectSet<Double2ShortMap.Entry> {
        protected final Double2ShortMap map;

        public BasicEntrySet(Double2ShortMap double2ShortMap) {
            this.map = double2ShortMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2ShortMap.Entry) {
                Double2ShortMap.Entry entry = (Double2ShortMap.Entry)object;
                double d = entry.getDoubleKey();
                return this.map.containsKey(d) && this.map.get(d) == entry.getShortValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            return this.map.containsKey(d) && this.map.get(d) == ((Short)v).shortValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2ShortMap.Entry) {
                Double2ShortMap.Entry entry = (Double2ShortMap.Entry)object;
                return this.map.remove(entry.getDoubleKey(), entry.getShortValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            short s = (Short)v;
            return this.map.remove(d, s);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Double2ShortMap.Entry {
        protected double key;
        protected short value;

        public BasicEntry() {
        }

        public BasicEntry(Double d, Short s) {
            this.key = d;
            this.value = s;
        }

        public BasicEntry(double d, short s) {
            this.key = d;
            this.value = s;
        }

        @Override
        public double getDoubleKey() {
            return this.key;
        }

        @Override
        public short getShortValue() {
            return this.value;
        }

        @Override
        public short setValue(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2ShortMap.Entry) {
                Double2ShortMap.Entry entry = (Double2ShortMap.Entry)object;
                return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry.getShortValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)k) && this.value == (Short)v;
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

