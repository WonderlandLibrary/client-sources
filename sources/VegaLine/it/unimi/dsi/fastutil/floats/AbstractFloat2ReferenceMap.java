/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ReferenceFunction;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2ReferenceMap;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractFloat2ReferenceMap<V>
extends AbstractFloat2ReferenceFunction<V>
implements Float2ReferenceMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractFloat2ReferenceMap() {
    }

    @Override
    public boolean containsValue(Object v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(float k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Float, ? extends V> m) {
        int n = m.size();
        Iterator<Map.Entry<Float, V>> i = m.entrySet().iterator();
        if (m instanceof Float2ReferenceMap) {
            while (n-- != 0) {
                Float2ReferenceMap.Entry e = (Float2ReferenceMap.Entry)i.next();
                this.put(e.getFloatKey(), e.getValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<Float, V> e = i.next();
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
                return AbstractFloat2ReferenceMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractFloat2ReferenceMap.this.size();
            }

            @Override
            public void clear() {
                AbstractFloat2ReferenceMap.this.clear();
            }

            @Override
            public FloatIterator iterator() {
                return new AbstractFloatIterator(){
                    final ObjectIterator<Map.Entry<Float, V>> i;
                    {
                        this.i = AbstractFloat2ReferenceMap.this.entrySet().iterator();
                    }

                    @Override
                    public float nextFloat() {
                        return ((Float2ReferenceMap.Entry)this.i.next()).getFloatKey();
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
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(){

            @Override
            public boolean contains(Object k) {
                return AbstractFloat2ReferenceMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractFloat2ReferenceMap.this.size();
            }

            @Override
            public void clear() {
                AbstractFloat2ReferenceMap.this.clear();
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new AbstractObjectIterator<V>(){
                    final ObjectIterator<Map.Entry<Float, V>> i;
                    {
                        this.i = AbstractFloat2ReferenceMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public V next() {
                        return ((Float2ReferenceMap.Entry)this.i.next()).getValue();
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
    public ObjectSet<Map.Entry<Float, V>> entrySet() {
        return this.float2ReferenceEntrySet();
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
            Float2ReferenceMap.Entry e = (Float2ReferenceMap.Entry)i.next();
            s.append(String.valueOf(e.getFloatKey()));
            s.append("=>");
            if (this == e.getValue()) {
                s.append("(this map)");
                continue;
            }
            s.append(String.valueOf(e.getValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry<V>
    implements Float2ReferenceMap.Entry<V> {
        protected float key;
        protected V value;

        public BasicEntry(Float key, V value) {
            this.key = key.floatValue();
            this.value = value;
        }

        public BasicEntry(float key, V value) {
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
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
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
            return Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && this.value == e.getValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.float2int(this.key) ^ (this.value == null ? 0 : System.identityHashCode(this.value));
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

