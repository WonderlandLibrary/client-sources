/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2DoubleFunction;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractLong2DoubleMap
extends AbstractLong2DoubleFunction
implements Long2DoubleMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractLong2DoubleMap() {
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
    public boolean containsKey(long k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Double> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Long, ? extends Double>> i = m.entrySet().iterator();
        if (m instanceof Long2DoubleMap) {
            while (n-- != 0) {
                Long2DoubleMap.Entry e = (Long2DoubleMap.Entry)i.next();
                this.put(e.getLongKey(), e.getDoubleValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Long, ? extends Double> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(){

            @Override
            public boolean contains(long k) {
                return AbstractLong2DoubleMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractLong2DoubleMap.this.size();
            }

            @Override
            public void clear() {
                AbstractLong2DoubleMap.this.clear();
            }

            @Override
            public LongIterator iterator() {
                return new AbstractLongIterator(){
                    final ObjectIterator<Map.Entry<Long, Double>> i;
                    {
                        this.i = AbstractLong2DoubleMap.this.entrySet().iterator();
                    }

                    @Override
                    public long nextLong() {
                        return ((Long2DoubleMap.Entry)this.i.next()).getLongKey();
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
                return AbstractLong2DoubleMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractLong2DoubleMap.this.size();
            }

            @Override
            public void clear() {
                AbstractLong2DoubleMap.this.clear();
            }

            @Override
            public DoubleIterator iterator() {
                return new AbstractDoubleIterator(){
                    final ObjectIterator<Map.Entry<Long, Double>> i;
                    {
                        this.i = AbstractLong2DoubleMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public double nextDouble() {
                        return ((Long2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    public ObjectSet<Map.Entry<Long, Double>> entrySet() {
        return this.long2DoubleEntrySet();
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
            Long2DoubleMap.Entry e = (Long2DoubleMap.Entry)i.next();
            s.append(String.valueOf(e.getLongKey()));
            s.append("=>");
            s.append(String.valueOf(e.getDoubleValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Long2DoubleMap.Entry {
        protected long key;
        protected double value;

        public BasicEntry(Long key, Double value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(long key, double value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Long getKey() {
            return this.key;
        }

        @Override
        public long getLongKey() {
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
            if (e.getKey() == null || !(e.getKey() instanceof Long)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Double)) {
                return false;
            }
            return this.key == (Long)e.getKey() && this.value == (Double)e.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ HashCommon.double2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

