/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2CharFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2CharMap;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractShort2CharMap
extends AbstractShort2CharFunction
implements Short2CharMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2CharMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue(((Character)ov).charValue());
    }

    @Override
    public boolean containsValue(char v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(short k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Short, ? extends Character> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Short, ? extends Character>> i = m.entrySet().iterator();
        if (m instanceof Short2CharMap) {
            while (n-- != 0) {
                Short2CharMap.Entry e = (Short2CharMap.Entry)i.next();
                this.put(e.getShortKey(), e.getCharValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Character> e = i.next();
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
                return AbstractShort2CharMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractShort2CharMap.this.size();
            }

            @Override
            public void clear() {
                AbstractShort2CharMap.this.clear();
            }

            @Override
            public ShortIterator iterator() {
                return new AbstractShortIterator(){
                    final ObjectIterator<Map.Entry<Short, Character>> i;
                    {
                        this.i = AbstractShort2CharMap.this.entrySet().iterator();
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2CharMap.Entry)this.i.next()).getShortKey();
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
    public CharCollection values() {
        return new AbstractCharCollection(){

            @Override
            public boolean contains(char k) {
                return AbstractShort2CharMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractShort2CharMap.this.size();
            }

            @Override
            public void clear() {
                AbstractShort2CharMap.this.clear();
            }

            @Override
            public CharIterator iterator() {
                return new AbstractCharIterator(){
                    final ObjectIterator<Map.Entry<Short, Character>> i;
                    {
                        this.i = AbstractShort2CharMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public char nextChar() {
                        return ((Short2CharMap.Entry)this.i.next()).getCharValue();
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
    public ObjectSet<Map.Entry<Short, Character>> entrySet() {
        return this.short2CharEntrySet();
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
            Short2CharMap.Entry e = (Short2CharMap.Entry)i.next();
            s.append(String.valueOf(e.getShortKey()));
            s.append("=>");
            s.append(String.valueOf(e.getCharValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Short2CharMap.Entry {
        protected short key;
        protected char value;

        public BasicEntry(Short key, Character value) {
            this.key = key;
            this.value = value.charValue();
        }

        public BasicEntry(short key, char value) {
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
        public Character getValue() {
            return Character.valueOf(this.value);
        }

        @Override
        public char getCharValue() {
            return this.value;
        }

        @Override
        public char setValue(char value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character setValue(Character value) {
            return Character.valueOf(this.setValue(value.charValue()));
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
            if (e.getValue() == null || !(e.getValue() instanceof Character)) {
                return false;
            }
            return this.key == (Short)e.getKey() && this.value == ((Character)e.getValue()).charValue();
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

