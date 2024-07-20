/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanFunction;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractFloat2BooleanMap
extends AbstractFloat2BooleanFunction
implements Float2BooleanMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractFloat2BooleanMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Boolean)ov);
    }

    @Override
    public boolean containsValue(boolean v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(float k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Float, ? extends Boolean> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Float, ? extends Boolean>> i = m.entrySet().iterator();
        if (m instanceof Float2BooleanMap) {
            while (n-- != 0) {
                Float2BooleanMap.Entry e = (Float2BooleanMap.Entry)i.next();
                this.put(e.getFloatKey(), e.getBooleanValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Float, ? extends Boolean> e = i.next();
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
                return AbstractFloat2BooleanMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractFloat2BooleanMap.this.size();
            }

            @Override
            public void clear() {
                AbstractFloat2BooleanMap.this.clear();
            }

            @Override
            public FloatIterator iterator() {
                return new AbstractFloatIterator(){
                    final ObjectIterator<Map.Entry<Float, Boolean>> i;
                    {
                        this.i = AbstractFloat2BooleanMap.this.entrySet().iterator();
                    }

                    @Override
                    public float nextFloat() {
                        return ((Float2BooleanMap.Entry)this.i.next()).getFloatKey();
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
    public BooleanCollection values() {
        return new AbstractBooleanCollection(){

            @Override
            public boolean contains(boolean k) {
                return AbstractFloat2BooleanMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractFloat2BooleanMap.this.size();
            }

            @Override
            public void clear() {
                AbstractFloat2BooleanMap.this.clear();
            }

            @Override
            public BooleanIterator iterator() {
                return new AbstractBooleanIterator(){
                    final ObjectIterator<Map.Entry<Float, Boolean>> i;
                    {
                        this.i = AbstractFloat2BooleanMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public boolean nextBoolean() {
                        return ((Float2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    public ObjectSet<Map.Entry<Float, Boolean>> entrySet() {
        return this.float2BooleanEntrySet();
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
            Float2BooleanMap.Entry e = (Float2BooleanMap.Entry)i.next();
            s.append(String.valueOf(e.getFloatKey()));
            s.append("=>");
            s.append(String.valueOf(e.getBooleanValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Float2BooleanMap.Entry {
        protected float key;
        protected boolean value;

        public BasicEntry(Float key, Boolean value) {
            this.key = key.floatValue();
            this.value = value;
        }

        public BasicEntry(float key, boolean value) {
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
        public Boolean getValue() {
            return this.value;
        }

        @Override
        public boolean getBooleanValue() {
            return this.value;
        }

        @Override
        public boolean setValue(boolean value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean setValue(Boolean value) {
            return this.setValue((boolean)value);
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
            if (e.getValue() == null || !(e.getValue() instanceof Boolean)) {
                return false;
            }
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && this.value == (Boolean)e.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ (this.value ? 1231 : 1237);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

