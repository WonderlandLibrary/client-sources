/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.chars.AbstractChar2BooleanFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractChar2BooleanMap
extends AbstractChar2BooleanFunction
implements Char2BooleanMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2BooleanMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Boolean)ov);
    }

    @Override
    public boolean containsValue(boolean v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(char k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Character, ? extends Boolean> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Character, ? extends Boolean>> i = m.entrySet().iterator();
        if (m instanceof Char2BooleanMap) {
            while (n-- != 0) {
                Char2BooleanMap.Entry e = (Char2BooleanMap.Entry)i.next();
                this.put(e.getCharKey(), e.getBooleanValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Character, ? extends Boolean> e = i.next();
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
                return AbstractChar2BooleanMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractChar2BooleanMap.this.size();
            }

            @Override
            public void clear() {
                AbstractChar2BooleanMap.this.clear();
            }

            @Override
            public CharIterator iterator() {
                return new AbstractCharIterator(){
                    final ObjectIterator<Map.Entry<Character, Boolean>> i;
                    {
                        this.i = AbstractChar2BooleanMap.this.entrySet().iterator();
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2BooleanMap.Entry)this.i.next()).getCharKey();
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
    public BooleanCollection values() {
        return new AbstractBooleanCollection(){

            @Override
            public boolean contains(boolean k) {
                return AbstractChar2BooleanMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractChar2BooleanMap.this.size();
            }

            @Override
            public void clear() {
                AbstractChar2BooleanMap.this.clear();
            }

            @Override
            public BooleanIterator iterator() {
                return new AbstractBooleanIterator(){
                    final ObjectIterator<Map.Entry<Character, Boolean>> i;
                    {
                        this.i = AbstractChar2BooleanMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public boolean nextBoolean() {
                        return ((Char2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    public ObjectSet<Map.Entry<Character, Boolean>> entrySet() {
        return this.char2BooleanEntrySet();
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
            Char2BooleanMap.Entry e = (Char2BooleanMap.Entry)i.next();
            s.append(String.valueOf(e.getCharKey()));
            s.append("=>");
            s.append(String.valueOf(e.getBooleanValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Char2BooleanMap.Entry {
        protected char key;
        protected boolean value;

        public BasicEntry(Character key, Boolean value) {
            this.key = key.charValue();
            this.value = value;
        }

        public BasicEntry(char key, boolean value) {
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
        public Boolean getValue() {
            return this.value;
        }

        @Override
        public boolean getBooleanValue() {
            return this.value;
        }

        @Override
        public boolean setValue(boolean value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean setValue(Boolean value) {
            return this.setValue((boolean)value);
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
            if (e.getValue() == null || !(e.getValue() instanceof Boolean)) {
                return false;
            }
            return this.key == ((Character)e.getKey()).charValue() && this.value == (Boolean)e.getValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value ? 1231 : 1237);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

