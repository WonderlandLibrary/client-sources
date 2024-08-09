/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2LongFunction;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2LongMap;
import it.unimi.dsi.fastutil.doubles.Double2LongMaps;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
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
public abstract class AbstractDouble2LongMap
extends AbstractDouble2LongFunction
implements Double2LongMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractDouble2LongMap() {
    }

    @Override
    public boolean containsValue(long l) {
        return this.values().contains(l);
    }

    @Override
    public boolean containsKey(double d) {
        Iterator iterator2 = this.double2LongEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Double2LongMap.Entry)iterator2.next()).getDoubleKey() != d) continue;
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
            final AbstractDouble2LongMap this$0;
            {
                this.this$0 = abstractDouble2LongMap;
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
                    private final ObjectIterator<Double2LongMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2LongMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Double2LongMap.Entry)this.i.next()).getDoubleKey();
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
    public LongCollection values() {
        return new AbstractLongCollection(this){
            final AbstractDouble2LongMap this$0;
            {
                this.this$0 = abstractDouble2LongMap;
            }

            @Override
            public boolean contains(long l) {
                return this.this$0.containsValue(l);
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
            public LongIterator iterator() {
                return new LongIterator(this){
                    private final ObjectIterator<Double2LongMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2LongMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        return ((Double2LongMap.Entry)this.i.next()).getLongValue();
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
    public void putAll(Map<? extends Double, ? extends Long> map) {
        if (map instanceof Double2LongMap) {
            ObjectIterator<Double2LongMap.Entry> objectIterator = Double2LongMaps.fastIterator((Double2LongMap)map);
            while (objectIterator.hasNext()) {
                Double2LongMap.Entry entry = (Double2LongMap.Entry)objectIterator.next();
                this.put(entry.getDoubleKey(), entry.getLongValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Double, ? extends Long>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Double, ? extends Long> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Double2LongMap.Entry> objectIterator = Double2LongMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Double2LongMap.Entry)objectIterator.next()).hashCode();
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
        return this.double2LongEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Double2LongMap.Entry> objectIterator = Double2LongMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Double2LongMap.Entry entry = (Double2LongMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getDoubleKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getLongValue()));
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
    extends AbstractObjectSet<Double2LongMap.Entry> {
        protected final Double2LongMap map;

        public BasicEntrySet(Double2LongMap double2LongMap) {
            this.map = double2LongMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2LongMap.Entry) {
                Double2LongMap.Entry entry = (Double2LongMap.Entry)object;
                double d = entry.getDoubleKey();
                return this.map.containsKey(d) && this.map.get(d) == entry.getLongValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Long)) {
                return true;
            }
            return this.map.containsKey(d) && this.map.get(d) == ((Long)v).longValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2LongMap.Entry) {
                Double2LongMap.Entry entry = (Double2LongMap.Entry)object;
                return this.map.remove(entry.getDoubleKey(), entry.getLongValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Long)) {
                return true;
            }
            long l = (Long)v;
            return this.map.remove(d, l);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Double2LongMap.Entry {
        protected double key;
        protected long value;

        public BasicEntry() {
        }

        public BasicEntry(Double d, Long l) {
            this.key = d;
            this.value = l;
        }

        public BasicEntry(double d, long l) {
            this.key = d;
            this.value = l;
        }

        @Override
        public double getDoubleKey() {
            return this.key;
        }

        @Override
        public long getLongValue() {
            return this.value;
        }

        @Override
        public long setValue(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2LongMap.Entry) {
                Double2LongMap.Entry entry = (Double2LongMap.Entry)object;
                return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry.getLongValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Long)) {
                return true;
            }
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)k) && this.value == (Long)v;
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ HashCommon.long2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

