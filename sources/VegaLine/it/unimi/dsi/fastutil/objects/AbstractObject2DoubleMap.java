/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2DoubleFunction;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractObject2DoubleMap<K>
extends AbstractObject2DoubleFunction<K>
implements Object2DoubleMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2DoubleMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Double)ov);
    }

    @Override
    public boolean containsValue(double v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(Object k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends K, ? extends Double> m) {
        int n = m.size();
        Iterator<Map.Entry<K, Double>> i = m.entrySet().iterator();
        if (m instanceof Object2DoubleMap) {
            while (n-- != 0) {
                Object2DoubleMap.Entry e = (Object2DoubleMap.Entry)i.next();
                this.put(e.getKey(), e.getDoubleValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<K, Double> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(){

            @Override
            public boolean contains(Object k) {
                return AbstractObject2DoubleMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractObject2DoubleMap.this.size();
            }

            @Override
            public void clear() {
                AbstractObject2DoubleMap.this.clear();
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new AbstractObjectIterator<K>(){
                    final ObjectIterator<Map.Entry<K, Double>> i;
                    {
                        this.i = AbstractObject2DoubleMap.this.entrySet().iterator();
                    }

                    @Override
                    public K next() {
                        return ((Object2DoubleMap.Entry)this.i.next()).getKey();
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
        };
    }

    @Override
    public DoubleCollection values() {
        return new AbstractDoubleCollection(){

            @Override
            public boolean contains(double k) {
                return AbstractObject2DoubleMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractObject2DoubleMap.this.size();
            }

            @Override
            public void clear() {
                AbstractObject2DoubleMap.this.clear();
            }

            @Override
            public DoubleIterator iterator() {
                return new AbstractDoubleIterator(){
                    final ObjectIterator<Map.Entry<K, Double>> i;
                    {
                        this.i = AbstractObject2DoubleMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public double nextDouble() {
                        return ((Object2DoubleMap.Entry)this.i.next()).getDoubleValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                };
            }
        };
    }

    @Override
    public ObjectSet<Map.Entry<K, Double>> entrySet() {
        return this.object2DoubleEntrySet();
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        ObjectIterator i = this.entrySet().iterator();
        while (n-- != 0) {
            h += ((Map.Entry)i.next()).hashCode();
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        Map m = (Map)o;
        if (m.size() != this.size()) {
            return false;
        }
        return this.entrySet().containsAll(m.entrySet());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = this.entrySet().iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Object2DoubleMap.Entry e = (Object2DoubleMap.Entry)i.next();
            if (this == e.getKey()) {
                s.append("(this map)");
            } else {
                s.append(String.valueOf(e.getKey()));
            }
            s.append("=>");
            s.append(String.valueOf(e.getDoubleValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry<K>
    implements Object2DoubleMap.Entry<K> {
        protected K key;
        protected double value;

        public BasicEntry(K key, Double value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(K key, double value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Double getValue() {
            return this.value;
        }

        @Override
        public double getDoubleValue() {
            return this.value;
        }

        @Override
        public double setValue(double value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Double setValue(Double value) {
            return this.setValue((double)value);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Double)) {
                return false;
            }
            return (this.key == null ? e.getKey() == null : this.key.equals(e.getKey())) && this.value == (Double)e.getValue();
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ HashCommon.double2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

