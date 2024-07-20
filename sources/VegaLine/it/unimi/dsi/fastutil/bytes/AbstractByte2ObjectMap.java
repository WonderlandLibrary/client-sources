/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ObjectFunction;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractByte2ObjectMap<V>
extends AbstractByte2ObjectFunction<V>
implements Byte2ObjectMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractByte2ObjectMap() {
    }

    @Override
    public boolean containsValue(Object v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(byte k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Byte, ? extends V> m) {
        int n = m.size();
        Iterator<Map.Entry<Byte, V>> i = m.entrySet().iterator();
        if (m instanceof Byte2ObjectMap) {
            while (n-- != 0) {
                Byte2ObjectMap.Entry e = (Byte2ObjectMap.Entry)i.next();
                this.put(e.getByteKey(), e.getValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<Byte, V> e = i.next();
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
                return AbstractByte2ObjectMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractByte2ObjectMap.this.size();
            }

            @Override
            public void clear() {
                AbstractByte2ObjectMap.this.clear();
            }

            @Override
            public ByteIterator iterator() {
                return new AbstractByteIterator(){
                    final ObjectIterator<Map.Entry<Byte, V>> i;
                    {
                        this.i = AbstractByte2ObjectMap.this.entrySet().iterator();
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2ObjectMap.Entry)this.i.next()).getByteKey();
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
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(){

            @Override
            public boolean contains(Object k) {
                return AbstractByte2ObjectMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractByte2ObjectMap.this.size();
            }

            @Override
            public void clear() {
                AbstractByte2ObjectMap.this.clear();
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new AbstractObjectIterator<V>(){
                    final ObjectIterator<Map.Entry<Byte, V>> i;
                    {
                        this.i = AbstractByte2ObjectMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public V next() {
                        return ((Byte2ObjectMap.Entry)this.i.next()).getValue();
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
    public ObjectSet<Map.Entry<Byte, V>> entrySet() {
        return this.byte2ObjectEntrySet();
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
            Byte2ObjectMap.Entry e = (Byte2ObjectMap.Entry)i.next();
            s.append(String.valueOf(e.getByteKey()));
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
    implements Byte2ObjectMap.Entry<V> {
        protected byte key;
        protected V value;

        public BasicEntry(Byte key, V value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(byte key, V value) {
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
            if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
                return false;
            }
            return this.key == (Byte)e.getKey() && (this.value == null ? e.getValue() == null : this.value.equals(e.getValue()));
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

