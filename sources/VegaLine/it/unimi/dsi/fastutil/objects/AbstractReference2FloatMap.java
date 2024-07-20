/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatFunction;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractReference2FloatMap<K>
extends AbstractReference2FloatFunction<K>
implements Reference2FloatMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2FloatMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue(((Float)ov).floatValue());
    }

    @Override
    public boolean containsValue(float v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(Object k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends K, ? extends Float> m) {
        int n = m.size();
        Iterator<Map.Entry<K, Float>> i = m.entrySet().iterator();
        if (m instanceof Reference2FloatMap) {
            while (n-- != 0) {
                Reference2FloatMap.Entry e = (Reference2FloatMap.Entry)i.next();
                this.put(e.getKey(), e.getFloatValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<K, Float> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(){

            @Override
            public boolean contains(Object k) {
                return AbstractReference2FloatMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractReference2FloatMap.this.size();
            }

            @Override
            public void clear() {
                AbstractReference2FloatMap.this.clear();
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new AbstractObjectIterator<K>(){
                    final ObjectIterator<Map.Entry<K, Float>> i;
                    {
                        this.i = AbstractReference2FloatMap.this.entrySet().iterator();
                    }

                    @Override
                    public K next() {
                        return ((Reference2FloatMap.Entry)this.i.next()).getKey();
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
    public FloatCollection values() {
        return new AbstractFloatCollection(){

            @Override
            public boolean contains(float k) {
                return AbstractReference2FloatMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractReference2FloatMap.this.size();
            }

            @Override
            public void clear() {
                AbstractReference2FloatMap.this.clear();
            }

            @Override
            public FloatIterator iterator() {
                return new AbstractFloatIterator(){
                    final ObjectIterator<Map.Entry<K, Float>> i;
                    {
                        this.i = AbstractReference2FloatMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public float nextFloat() {
                        return ((Reference2FloatMap.Entry)this.i.next()).getFloatValue();
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
    public ObjectSet<Map.Entry<K, Float>> entrySet() {
        return this.reference2FloatEntrySet();
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
            Reference2FloatMap.Entry e = (Reference2FloatMap.Entry)i.next();
            if (this == e.getKey()) {
                s.append("(this map)");
            } else {
                s.append(String.valueOf(e.getKey()));
            }
            s.append("=>");
            s.append(String.valueOf(e.getFloatValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry<K>
    implements Reference2FloatMap.Entry<K> {
        protected K key;
        protected float value;

        public BasicEntry(K key, Float value) {
            this.key = key;
            this.value = value.floatValue();
        }

        public BasicEntry(K key, float value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Float getValue() {
            return Float.valueOf(this.value);
        }

        @Override
        public float getFloatValue() {
            return this.value;
        }

        @Override
        public float setValue(float value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float setValue(Float value) {
            return Float.valueOf(this.setValue(value.floatValue()));
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            return this.key == e.getKey() && this.value == ((Float)e.getValue()).floatValue();
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ HashCommon.float2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

