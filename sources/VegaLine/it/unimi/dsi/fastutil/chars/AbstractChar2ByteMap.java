/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.chars.AbstractChar2ByteFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ByteMap;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractChar2ByteMap
extends AbstractChar2ByteFunction
implements Char2ByteMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2ByteMap() {
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
    public boolean containsKey(char k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Character, ? extends Byte> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Character, ? extends Byte>> i = m.entrySet().iterator();
        if (m instanceof Char2ByteMap) {
            while (n-- != 0) {
                Char2ByteMap.Entry e = (Char2ByteMap.Entry)i.next();
                this.put(e.getCharKey(), e.getByteValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Character, ? extends Byte> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(){

            @Override
            public boolean contains(char k) {
                return AbstractChar2ByteMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractChar2ByteMap.this.size();
            }

            @Override
            public void clear() {
                AbstractChar2ByteMap.this.clear();
            }

            @Override
            public CharIterator iterator() {
                return new AbstractCharIterator(){
                    final ObjectIterator<Map.Entry<Character, Byte>> i;
                    {
                        this.i = AbstractChar2ByteMap.this.entrySet().iterator();
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2ByteMap.Entry)this.i.next()).getCharKey();
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
                return AbstractChar2ByteMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractChar2ByteMap.this.size();
            }

            @Override
            public void clear() {
                AbstractChar2ByteMap.this.clear();
            }

            @Override
            public ByteIterator iterator() {
                return new AbstractByteIterator(){
                    final ObjectIterator<Map.Entry<Character, Byte>> i;
                    {
                        this.i = AbstractChar2ByteMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public byte nextByte() {
                        return ((Char2ByteMap.Entry)this.i.next()).getByteValue();
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
    public ObjectSet<Map.Entry<Character, Byte>> entrySet() {
        return this.char2ByteEntrySet();
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
            Char2ByteMap.Entry e = (Char2ByteMap.Entry)i.next();
            s.append(String.valueOf(e.getCharKey()));
            s.append("=>");
            s.append(String.valueOf(e.getByteValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Char2ByteMap.Entry {
        protected char key;
        protected byte value;

        public BasicEntry(Character key, Byte value) {
            this.key = key.charValue();
            this.value = value;
        }

        public BasicEntry(char key, byte value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Character getKey() {
            return Character.valueOf(this.key);
        }

        @Override
        public char getCharKey() {
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
            if (e.getKey() == null || !(e.getKey() instanceof Character)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Byte)) {
                return false;
            }
            return this.key == ((Character)e.getKey()).charValue() && this.value == (Byte)e.getValue();
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

