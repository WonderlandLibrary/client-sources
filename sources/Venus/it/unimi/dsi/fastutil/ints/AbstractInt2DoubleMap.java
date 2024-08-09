/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleFunction;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleMaps;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
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
public abstract class AbstractInt2DoubleMap
extends AbstractInt2DoubleFunction
implements Int2DoubleMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractInt2DoubleMap() {
    }

    @Override
    public boolean containsValue(double d) {
        return this.values().contains(d);
    }

    @Override
    public boolean containsKey(int n) {
        Iterator iterator2 = this.int2DoubleEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Int2DoubleMap.Entry)iterator2.next()).getIntKey() != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final AbstractInt2DoubleMap this$0;
            {
                this.this$0 = abstractInt2DoubleMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsKey(n);
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
                    private final ObjectIterator<Int2DoubleMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Int2DoubleMap.Entry)this.i.next()).getIntKey();
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
            final AbstractInt2DoubleMap this$0;
            {
                this.this$0 = abstractInt2DoubleMap;
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
                    private final ObjectIterator<Int2DoubleMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Int2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    public void putAll(Map<? extends Integer, ? extends Double> map) {
        if (map instanceof Int2DoubleMap) {
            ObjectIterator<Int2DoubleMap.Entry> objectIterator = Int2DoubleMaps.fastIterator((Int2DoubleMap)map);
            while (objectIterator.hasNext()) {
                Int2DoubleMap.Entry entry = (Int2DoubleMap.Entry)objectIterator.next();
                this.put(entry.getIntKey(), entry.getDoubleValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Integer, ? extends Double>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Integer, ? extends Double> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Int2DoubleMap.Entry> objectIterator = Int2DoubleMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Int2DoubleMap.Entry)objectIterator.next()).hashCode();
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
        return this.int2DoubleEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Int2DoubleMap.Entry> objectIterator = Int2DoubleMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Int2DoubleMap.Entry entry = (Int2DoubleMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getIntKey()));
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
    extends AbstractObjectSet<Int2DoubleMap.Entry> {
        protected final Int2DoubleMap map;

        public BasicEntrySet(Int2DoubleMap int2DoubleMap) {
            this.map = int2DoubleMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2DoubleMap.Entry) {
                Int2DoubleMap.Entry entry = (Int2DoubleMap.Entry)object;
                int n = entry.getIntKey();
                return this.map.containsKey(n) && Double.doubleToLongBits(this.map.get(n)) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.map.containsKey(n) && Double.doubleToLongBits(this.map.get(n)) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2DoubleMap.Entry) {
                Int2DoubleMap.Entry entry = (Int2DoubleMap.Entry)object;
                return this.map.remove(entry.getIntKey(), entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            double d = (Double)v;
            return this.map.remove(n, d);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Int2DoubleMap.Entry {
        protected int key;
        protected double value;

        public BasicEntry() {
        }

        public BasicEntry(Integer n, Double d) {
            this.key = n;
            this.value = d;
        }

        public BasicEntry(int n, double d) {
            this.key = n;
            this.value = d;
        }

        @Override
        public int getIntKey() {
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
            if (object instanceof Int2DoubleMap.Entry) {
                Int2DoubleMap.Entry entry = (Int2DoubleMap.Entry)object;
                return this.key == entry.getIntKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.key == (Integer)k && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.double2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

