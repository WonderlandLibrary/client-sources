/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByte2FloatFunction;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2FloatMap;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2FloatMap
extends AbstractByte2FloatFunction
implements Byte2FloatMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractByte2FloatMap() {
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
    public boolean containsKey(byte k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Byte, ? extends Float> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Byte, ? extends Float>> i = m.entrySet().iterator();
        if (m instanceof Byte2FloatMap) {
            while (n-- != 0) {
                Byte2FloatMap.Entry e = (Byte2FloatMap.Entry)i.next();
                this.put(e.getByteKey(), e.getFloatValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Byte, ? extends Float> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ByteSet keySet() {
        return new AbstractByteSet(){

            @Override
            public boolean contains(byte k) {
                return AbstractByte2FloatMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractByte2FloatMap.this.size();
            }

            @Override
            public void clear() {
                AbstractByte2FloatMap.this.clear();
            }

            @Override
            public ByteIterator iterator() {
                return new AbstractByteIterator(){
                    final ObjectIterator<Map.Entry<Byte, Float>> i;
                    {
                        this.i = AbstractByte2FloatMap.this.entrySet().iterator();
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2FloatMap.Entry)this.i.next()).getByteKey();
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
                return AbstractByte2FloatMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractByte2FloatMap.this.size();
            }

            @Override
            public void clear() {
                AbstractByte2FloatMap.this.clear();
            }

            @Override
            public FloatIterator iterator() {
                return new AbstractFloatIterator(){
                    final ObjectIterator<Map.Entry<Byte, Float>> i;
                    {
                        this.i = AbstractByte2FloatMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public float nextFloat() {
                        return ((Byte2FloatMap.Entry)this.i.next()).getFloatValue();
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
    public ObjectSet<Map.Entry<Byte, Float>> entrySet() {
        return this.byte2FloatEntrySet();
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
            Byte2FloatMap.Entry e = (Byte2FloatMap.Entry)i.next();
            s.append(String.valueOf(e.getByteKey()));
            s.append("=>");
            s.append(String.valueOf(e.getFloatValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Byte2FloatMap.Entry {
        protected byte key;
        protected float value;

        public BasicEntry(Byte key, Float value) {
            this.key = key;
            this.value = value.floatValue();
        }

        public BasicEntry(byte key, float value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Byte getKey() {
            return this.key;
        }

        @Override
        public byte getByteKey() {
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
            if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            return this.key == (Byte)e.getKey() && this.value == ((Float)e.getValue()).floatValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.float2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

