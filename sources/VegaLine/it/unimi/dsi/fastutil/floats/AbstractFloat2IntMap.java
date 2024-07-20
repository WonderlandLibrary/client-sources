/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2IntFunction;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractFloat2IntMap
extends AbstractFloat2IntFunction
implements Float2IntMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractFloat2IntMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Integer)ov);
    }

    @Override
    public boolean containsValue(int v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(float k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Float, ? extends Integer> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Float, ? extends Integer>> i = m.entrySet().iterator();
        if (m instanceof Float2IntMap) {
            while (n-- != 0) {
                Float2IntMap.Entry e = (Float2IntMap.Entry)i.next();
                this.put(e.getFloatKey(), e.getIntValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Float, ? extends Integer> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public FloatSet keySet() {
        return new AbstractFloatSet(){

            @Override
            public boolean contains(float k) {
                return AbstractFloat2IntMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractFloat2IntMap.this.size();
            }

            @Override
            public void clear() {
                AbstractFloat2IntMap.this.clear();
            }

            @Override
            public FloatIterator iterator() {
                return new AbstractFloatIterator(){
                    final ObjectIterator<Map.Entry<Float, Integer>> i;
                    {
                        this.i = AbstractFloat2IntMap.this.entrySet().iterator();
                    }

                    @Override
                    public float nextFloat() {
                        return ((Float2IntMap.Entry)this.i.next()).getFloatKey();
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
    public IntCollection values() {
        return new AbstractIntCollection(){

            @Override
            public boolean contains(int k) {
                return AbstractFloat2IntMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractFloat2IntMap.this.size();
            }

            @Override
            public void clear() {
                AbstractFloat2IntMap.this.clear();
            }

            @Override
            public IntIterator iterator() {
                return new AbstractIntIterator(){
                    final ObjectIterator<Map.Entry<Float, Integer>> i;
                    {
                        this.i = AbstractFloat2IntMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public int nextInt() {
                        return ((Float2IntMap.Entry)this.i.next()).getIntValue();
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
    public ObjectSet<Map.Entry<Float, Integer>> entrySet() {
        return this.float2IntEntrySet();
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
            Float2IntMap.Entry e = (Float2IntMap.Entry)i.next();
            s.append(String.valueOf(e.getFloatKey()));
            s.append("=>");
            s.append(String.valueOf(e.getIntValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Float2IntMap.Entry {
        protected float key;
        protected int value;

        public BasicEntry(Float key, Integer value) {
            this.key = key.floatValue();
            this.value = value;
        }

        public BasicEntry(float key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Float getKey() {
            return Float.valueOf(this.key);
        }

        @Override
        public float getFloatKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Integer getValue() {
            return this.value;
        }

        @Override
        public int getIntValue() {
            return this.value;
        }

        @Override
        public int setValue(int value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer setValue(Integer value) {
            return this.setValue((int)value);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && this.value == (Integer)e.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

