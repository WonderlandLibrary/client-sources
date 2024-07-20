/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.AbstractByte2LongFunction;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2LongMap
extends AbstractByte2LongFunction
implements Byte2LongMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractByte2LongMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Long)ov);
    }

    @Override
    public boolean containsValue(long v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(byte k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Byte, ? extends Long> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Byte, ? extends Long>> i = m.entrySet().iterator();
        if (m instanceof Byte2LongMap) {
            while (n-- != 0) {
                Byte2LongMap.Entry e = (Byte2LongMap.Entry)i.next();
                this.put(e.getByteKey(), e.getLongValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Byte, ? extends Long> e = i.next();
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
                return AbstractByte2LongMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractByte2LongMap.this.size();
            }

            @Override
            public void clear() {
                AbstractByte2LongMap.this.clear();
            }

            @Override
            public ByteIterator iterator() {
                return new AbstractByteIterator(){
                    final ObjectIterator<Map.Entry<Byte, Long>> i;
                    {
                        this.i = AbstractByte2LongMap.this.entrySet().iterator();
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2LongMap.Entry)this.i.next()).getByteKey();
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
    public LongCollection values() {
        return new AbstractLongCollection(){

            @Override
            public boolean contains(long k) {
                return AbstractByte2LongMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractByte2LongMap.this.size();
            }

            @Override
            public void clear() {
                AbstractByte2LongMap.this.clear();
            }

            @Override
            public LongIterator iterator() {
                return new AbstractLongIterator(){
                    final ObjectIterator<Map.Entry<Byte, Long>> i;
                    {
                        this.i = AbstractByte2LongMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public long nextLong() {
                        return ((Byte2LongMap.Entry)this.i.next()).getLongValue();
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
    public ObjectSet<Map.Entry<Byte, Long>> entrySet() {
        return this.byte2LongEntrySet();
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
            Byte2LongMap.Entry e = (Byte2LongMap.Entry)i.next();
            s.append(String.valueOf(e.getByteKey()));
            s.append("=>");
            s.append(String.valueOf(e.getLongValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Byte2LongMap.Entry {
        protected byte key;
        protected long value;

        public BasicEntry(Byte key, Long value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(byte key, long value) {
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
        public Long getValue() {
            return this.value;
        }

        @Override
        public long getLongValue() {
            return this.value;
        }

        @Override
        public long setValue(long value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long setValue(Long value) {
            return this.setValue((long)value);
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
            if (e.getValue() == null || !(e.getValue() instanceof Long)) {
                return false;
            }
            return this.key == (Byte)e.getKey() && this.value == (Long)e.getValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.long2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

