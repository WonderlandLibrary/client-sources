/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2DoubleFunction;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMaps;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractReference2DoubleMap<K>
extends AbstractReference2DoubleFunction<K>
implements Reference2DoubleMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2DoubleMap() {
    }

    @Override
    public boolean containsValue(double d) {
        return this.values().contains(d);
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator iterator2 = this.reference2DoubleEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Reference2DoubleMap.Entry)iterator2.next()).getKey() != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final AbstractReference2DoubleMap this$0;
            {
                this.this$0 = abstractReference2DoubleMap;
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
                    private final ObjectIterator<Reference2DoubleMap.Entry<K>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        return ((Reference2DoubleMap.Entry)this.i.next()).getKey();
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
            final AbstractReference2DoubleMap this$0;
            {
                this.this$0 = abstractReference2DoubleMap;
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
                    private final ObjectIterator<Reference2DoubleMap.Entry<K>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Reference2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    public void putAll(Map<? extends K, ? extends Double> map) {
        if (map instanceof Reference2DoubleMap) {
            ObjectIterator objectIterator = Reference2DoubleMaps.fastIterator((Reference2DoubleMap)map);
            while (objectIterator.hasNext()) {
                Reference2DoubleMap.Entry entry = (Reference2DoubleMap.Entry)objectIterator.next();
                this.put(entry.getKey(), entry.getDoubleValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<K, Double>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<K, Double> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Reference2DoubleMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Reference2DoubleMap.Entry)objectIterator.next()).hashCode();
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
        return this.reference2DoubleEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Reference2DoubleMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Reference2DoubleMap.Entry entry = (Reference2DoubleMap.Entry)objectIterator.next();
            if (this == entry.getKey()) {
                stringBuilder.append("(this map)");
            } else {
                stringBuilder.append(String.valueOf(entry.getKey()));
            }
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

    public static abstract class BasicEntrySet<K>
    extends AbstractObjectSet<Reference2DoubleMap.Entry<K>> {
        protected final Reference2DoubleMap<K> map;

        public BasicEntrySet(Reference2DoubleMap<K> reference2DoubleMap) {
            this.map = reference2DoubleMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2DoubleMap.Entry) {
                Reference2DoubleMap.Entry entry = (Reference2DoubleMap.Entry)object;
                Object k = entry.getKey();
                return this.map.containsKey(k) && Double.doubleToLongBits(this.map.getDouble(k)) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.map.containsKey(k) && Double.doubleToLongBits(this.map.getDouble(k)) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2DoubleMap.Entry) {
                Reference2DoubleMap.Entry entry = (Reference2DoubleMap.Entry)object;
                return this.map.remove(entry.getKey(), entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            double d = (Double)v;
            return this.map.remove(k, d);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<K>
    implements Reference2DoubleMap.Entry<K> {
        protected K key;
        protected double value;

        public BasicEntry() {
        }

        public BasicEntry(K k, Double d) {
            this.key = k;
            this.value = d;
        }

        public BasicEntry(K k, double d) {
            this.key = k;
            this.value = d;
        }

        @Override
        public K getKey() {
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
            if (object instanceof Reference2DoubleMap.Entry) {
                Reference2DoubleMap.Entry entry = (Reference2DoubleMap.Entry)object;
                return this.key == entry.getKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.key == k && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ HashCommon.double2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

