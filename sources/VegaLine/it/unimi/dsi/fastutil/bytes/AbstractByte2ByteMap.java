/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ByteFunction;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2ByteMap;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2ByteMap
extends AbstractByte2ByteFunction
implements Byte2ByteMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractByte2ByteMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Byte)ov);
    }

    @Override
    public boolean containsValue(byte v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(byte k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Byte, ? extends Byte> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Byte, ? extends Byte>> i = m.entrySet().iterator();
        if (m instanceof Byte2ByteMap) {
            while (n-- != 0) {
                Byte2ByteMap.Entry e = (Byte2ByteMap.Entry)i.next();
                this.put(e.getByteKey(), e.getByteValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Byte, ? extends Byte> e = i.next();
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
                return AbstractByte2ByteMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractByte2ByteMap.this.size();
            }

            @Override
            public void clear() {
                AbstractByte2ByteMap.this.clear();
            }

            @Override
            public ByteIterator iterator() {
                return new AbstractByteIterator(){
                    final ObjectIterator<Map.Entry<Byte, Byte>> i;
                    {
                        this.i = AbstractByte2ByteMap.this.entrySet().iterator();
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2ByteMap.Entry)this.i.next()).getByteKey();
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
    public ByteCollection values() {
        return new AbstractByteCollection(){

            @Override
            public boolean contains(byte k) {
                return AbstractByte2ByteMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractByte2ByteMap.this.size();
            }

            @Override
            public void clear() {
                AbstractByte2ByteMap.this.clear();
            }

            @Override
            public ByteIterator iterator() {
                return new AbstractByteIterator(){
                    final ObjectIterator<Map.Entry<Byte, Byte>> i;
                    {
                        this.i = AbstractByte2ByteMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public byte nextByte() {
                        return ((Byte2ByteMap.Entry)this.i.next()).getByteValue();
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
    public ObjectSet<Map.Entry<Byte, Byte>> entrySet() {
        return this.byte2ByteEntrySet();
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
            Byte2ByteMap.Entry e = (Byte2ByteMap.Entry)i.next();
            s.append(String.valueOf(e.getByteKey()));
            s.append("=>");
            s.append(String.valueOf(e.getByteValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Byte2ByteMap.Entry {
        protected byte key;
        protected byte value;

        public BasicEntry(Byte key, Byte value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(byte key, byte value) {
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
        public Byte getValue() {
            return this.value;
        }

        @Override
        public byte getByteValue() {
            return this.value;
        }

        @Override
        public byte setValue(byte value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Byte setValue(Byte value) {
            return this.setValue((byte)value);
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
            if (e.getValue() == null || !(e.getValue() instanceof Byte)) {
                return false;
            }
            return this.key == (Byte)e.getKey() && this.value == (Byte)e.getValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

