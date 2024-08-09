/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ReferenceFunction;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceMaps;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDouble2ReferenceMap<V>
extends AbstractDouble2ReferenceFunction<V>
implements Double2ReferenceMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractDouble2ReferenceMap() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values().contains(object);
    }

    @Override
    public boolean containsKey(double d) {
        Iterator iterator2 = this.double2ReferenceEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Double2ReferenceMap.Entry)iterator2.next()).getDoubleKey() != d) continue;
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
            final AbstractDouble2ReferenceMap this$0;
            {
                this.this$0 = abstractDouble2ReferenceMap;
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
                    private final ObjectIterator<Double2ReferenceMap.Entry<V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Double2ReferenceMap.Entry)this.i.next()).getDoubleKey();
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
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(this){
            final AbstractDouble2ReferenceMap this$0;
            {
                this.this$0 = abstractDouble2ReferenceMap;
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
                    private final ObjectIterator<Double2ReferenceMap.Entry<V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Double2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Double2ReferenceMap.Entry)this.i.next()).getValue();
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
    public void putAll(Map<? extends Double, ? extends V> map) {
        if (map instanceof Double2ReferenceMap) {
            ObjectIterator objectIterator = Double2ReferenceMaps.fastIterator((Double2ReferenceMap)map);
            while (objectIterator.hasNext()) {
                Double2ReferenceMap.Entry entry = (Double2ReferenceMap.Entry)objectIterator.next();
                this.put(entry.getDoubleKey(), entry.getValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<Double, V>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<Double, V> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Double2ReferenceMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Double2ReferenceMap.Entry)objectIterator.next()).hashCode();
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
        return this.double2ReferenceEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Double2ReferenceMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Double2ReferenceMap.Entry entry = (Double2ReferenceMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getDoubleKey()));
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
    extends AbstractObjectSet<Double2ReferenceMap.Entry<V>> {
        protected final Double2ReferenceMap<V> map;

        public BasicEntrySet(Double2ReferenceMap<V> double2ReferenceMap) {
            this.map = double2ReferenceMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2ReferenceMap.Entry) {
                Double2ReferenceMap.Entry entry = (Double2ReferenceMap.Entry)object;
                double d = entry.getDoubleKey();
                return this.map.containsKey(d) && this.map.get(d) == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            return this.map.containsKey(d) && this.map.get(d) == v;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Double2ReferenceMap.Entry) {
                Double2ReferenceMap.Entry entry = (Double2ReferenceMap.Entry)object;
                return this.map.remove(entry.getDoubleKey(), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            double d = (Double)k;
            Object v = entry.getValue();
            return this.map.remove(d, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<V>
    implements Double2ReferenceMap.Entry<V> {
        protected double key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(Double d, V v) {
            this.key = d;
            this.value = v;
        }

        public BasicEntry(double d, V v) {
            this.key = d;
            this.value = v;
        }

        @Override
        public double getDoubleKey() {
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
            if (object instanceof Double2ReferenceMap.Entry) {
                Double2ReferenceMap.Entry entry = (Double2ReferenceMap.Entry)object;
                return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Double)) {
                return true;
            }
            Object v = entry.getValue();
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits((Double)k) && this.value == v;
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ (this.value == null ? 0 : System.identityHashCode(this.value));
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

