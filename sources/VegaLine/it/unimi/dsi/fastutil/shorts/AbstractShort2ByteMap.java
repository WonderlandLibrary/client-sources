/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ByteFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractShort2ByteMap
extends AbstractShort2ByteFunction
implements Short2ByteMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2ByteMap() {
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
    public boolean containsKey(short k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Short, ? extends Byte> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Short, ? extends Byte>> i = m.entrySet().iterator();
        if (m instanceof Short2ByteMap) {
            while (n-- != 0) {
                Short2ByteMap.Entry e = (Short2ByteMap.Entry)i.next();
                this.put(e.getShortKey(), e.getByteValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Byte> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(){

            @Override
            public boolean contains(short k) {
                return AbstractShort2ByteMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractShort2ByteMap.this.size();
            }

            @Override
            public void clear() {
                AbstractShort2ByteMap.this.clear();
            }

            @Override
            public ShortIterator iterator() {
                return new AbstractShortIterator(){
                    final ObjectIterator<Map.Entry<Short, Byte>> i;
                    {
                        this.i = AbstractShort2ByteMap.this.entrySet().iterator();
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2ByteMap.Entry)this.i.next()).getShortKey();
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
                return AbstractShort2ByteMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractShort2ByteMap.this.size();
            }

            @Override
            public void clear() {
                AbstractShort2ByteMap.this.clear();
            }

            @Override
            public ByteIterator iterator() {
                return new AbstractByteIterator(){
                    final ObjectIterator<Map.Entry<Short, Byte>> i;
                    {
                        this.i = AbstractShort2ByteMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public byte nextByte() {
                        return ((Short2ByteMap.Entry)this.i.next()).getByteValue();
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
    public ObjectSet<Map.Entry<Short, Byte>> entrySet() {
        return this.short2ByteEntrySet();
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
            Short2ByteMap.Entry e = (Short2ByteMap.Entry)i.next();
            s.append(String.valueOf(e.getShortKey()));
            s.append("=>");
            s.append(String.valueOf(e.getByteValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Short2ByteMap.Entry {
        protected short key;
        protected byte value;

        public BasicEntry(Short key, Byte value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(short key, byte value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Short getKey() {
            return this.key;
        }

        @Override
        public short getShortKey() {
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
            if (e.getKey() == null || !(e.getKey() instanceof Short)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Byte)) {
                return false;
            }
            return this.key == (Short)e.getKey() && this.value == (Byte)e.getValue();
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

