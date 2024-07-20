/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2FloatFunction;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractLong2FloatMap
extends AbstractLong2FloatFunction
implements Long2FloatMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractLong2FloatMap() {
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
    public boolean containsKey(long k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Float> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Long, ? extends Float>> i = m.entrySet().iterator();
        if (m instanceof Long2FloatMap) {
            while (n-- != 0) {
                Long2FloatMap.Entry e = (Long2FloatMap.Entry)i.next();
                this.put(e.getLongKey(), e.getFloatValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Long, ? extends Float> e = i.next();
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
                return AbstractLong2FloatMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractLong2FloatMap.this.size();
            }

            @Override
            public void clear() {
                AbstractLong2FloatMap.this.clear();
            }

            @Override
            public LongIterator iterator() {
                return new AbstractLongIterator(){
                    final ObjectIterator<Map.Entry<Long, Float>> i;
                    {
                        this.i = AbstractLong2FloatMap.this.entrySet().iterator();
                    }

                    @Override
                    public long nextLong() {
                        return ((Long2FloatMap.Entry)this.i.next()).getLongKey();
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
                return AbstractLong2FloatMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractLong2FloatMap.this.size();
            }

            @Override
            public void clear() {
                AbstractLong2FloatMap.this.clear();
            }

            @Override
            public FloatIterator iterator() {
                return new AbstractFloatIterator(){
                    final ObjectIterator<Map.Entry<Long, Float>> i;
                    {
                        this.i = AbstractLong2FloatMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public float nextFloat() {
                        return ((Long2FloatMap.Entry)this.i.next()).getFloatValue();
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
    public ObjectSet<Map.Entry<Long, Float>> entrySet() {
        return this.long2FloatEntrySet();
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
            Long2FloatMap.Entry e = (Long2FloatMap.Entry)i.next();
            s.append(String.valueOf(e.getLongKey()));
            s.append("=>");
            s.append(String.valueOf(e.getFloatValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Long2FloatMap.Entry {
        protected long key;
        protected float value;

        public BasicEntry(Long key, Float value) {
            this.key = key;
            this.value = value.floatValue();
        }

        public BasicEntry(long key, float value) {
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
            if (e.getKey() == null || !(e.getKey() instanceof Long)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            return this.key == (Long)e.getKey() && this.value == ((Float)e.getValue()).floatValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ HashCommon.float2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

