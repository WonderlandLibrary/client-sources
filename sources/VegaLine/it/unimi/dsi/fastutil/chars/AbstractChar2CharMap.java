/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2CharFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractChar2CharMap
extends AbstractChar2CharFunction
implements Char2CharMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2CharMap() {
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
    public boolean containsKey(char k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Character, ? extends Character> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Character, ? extends Character>> i = m.entrySet().iterator();
        if (m instanceof Char2CharMap) {
            while (n-- != 0) {
                Char2CharMap.Entry e = (Char2CharMap.Entry)i.next();
                this.put(e.getCharKey(), e.getCharValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Character, ? extends Character> e = i.next();
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
                return AbstractChar2CharMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractChar2CharMap.this.size();
            }

            @Override
            public void clear() {
                AbstractChar2CharMap.this.clear();
            }

            @Override
            public CharIterator iterator() {
                return new AbstractCharIterator(){
                    final ObjectIterator<Map.Entry<Character, Character>> i;
                    {
                        this.i = AbstractChar2CharMap.this.entrySet().iterator();
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2CharMap.Entry)this.i.next()).getCharKey();
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
                return AbstractChar2CharMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractChar2CharMap.this.size();
            }

            @Override
            public void clear() {
                AbstractChar2CharMap.this.clear();
            }

            @Override
            public CharIterator iterator() {
                return new AbstractCharIterator(){
                    final ObjectIterator<Map.Entry<Character, Character>> i;
                    {
                        this.i = AbstractChar2CharMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public char nextChar() {
                        return ((Char2CharMap.Entry)this.i.next()).getCharValue();
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
    public ObjectSet<Map.Entry<Character, Character>> entrySet() {
        return this.char2CharEntrySet();
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
            Char2CharMap.Entry e = (Char2CharMap.Entry)i.next();
            s.append(String.valueOf(e.getCharKey()));
            s.append("=>");
            s.append(String.valueOf(e.getCharValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Char2CharMap.Entry {
        protected char key;
        protected char value;

        public BasicEntry(Character key, Character value) {
            this.key = key.charValue();
            this.value = value.charValue();
        }

        public BasicEntry(char key, char value) {
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
            if (e.getKey() == null || !(e.getKey() instanceof Character)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Character)) {
                return false;
            }
            return this.key == ((Character)e.getKey()).charValue() && this.value == ((Character)e.getValue()).charValue();
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

