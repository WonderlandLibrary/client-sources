/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleFunction;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractInt2DoubleMap
extends AbstractInt2DoubleFunction
implements Int2DoubleMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractInt2DoubleMap() {
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
    public boolean containsKey(int k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Double> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Integer, ? extends Double>> i = m.entrySet().iterator();
        if (m instanceof Int2DoubleMap) {
            while (n-- != 0) {
                Int2DoubleMap.Entry e = (Int2DoubleMap.Entry)i.next();
                this.put(e.getIntKey(), e.getDoubleValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Integer, ? extends Double> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(){

            @Override
            public boolean contains(int k) {
                return AbstractInt2DoubleMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractInt2DoubleMap.this.size();
            }

            @Override
            public void clear() {
                AbstractInt2DoubleMap.this.clear();
            }

            @Override
            public IntIterator iterator() {
                return new AbstractIntIterator(){
                    final ObjectIterator<Map.Entry<Integer, Double>> i;
                    {
                        this.i = AbstractInt2DoubleMap.this.entrySet().iterator();
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
        };
    }

    @Override
    public DoubleCollection values() {
        return new AbstractDoubleCollection(){

            @Override
            public boolean contains(double k) {
                return AbstractInt2DoubleMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractInt2DoubleMap.this.size();
            }

            @Override
            public void clear() {
                AbstractInt2DoubleMap.this.clear();
            }

            @Override
            public DoubleIterator iterator() {
                return new AbstractDoubleIterator(){
                    final ObjectIterator<Map.Entry<Integer, Double>> i;
                    {
                        this.i = AbstractInt2DoubleMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public double nextDouble() {
                        return ((Int2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    public ObjectSet<Map.Entry<Integer, Double>> entrySet() {
        return this.int2DoubleEntrySet();
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
            Int2DoubleMap.Entry e = (Int2DoubleMap.Entry)i.next();
            s.append(String.valueOf(e.getIntKey()));
            s.append("=>");
            s.append(String.valueOf(e.getDoubleValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Int2DoubleMap.Entry {
        protected int key;
        protected double value;

        public BasicEntry(Integer key, Double value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(int key, double value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Integer getKey() {
            return this.key;
        }

        @Override
        public int getIntKey() {
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
            if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Double)) {
                return false;
            }
            return this.key == (Integer)e.getKey() && this.value == (Double)e.getValue();
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

