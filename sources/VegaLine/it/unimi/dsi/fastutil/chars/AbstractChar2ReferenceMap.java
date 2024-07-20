/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ReferenceFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMap;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractChar2ReferenceMap<V>
extends AbstractChar2ReferenceFunction<V>
implements Char2ReferenceMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2ReferenceMap() {
    }

    @Override
    public boolean containsValue(Object v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(char k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Character, ? extends V> m) {
        int n = m.size();
        Iterator<Map.Entry<Character, V>> i = m.entrySet().iterator();
        if (m instanceof Char2ReferenceMap) {
            while (n-- != 0) {
                Char2ReferenceMap.Entry e = (Char2ReferenceMap.Entry)i.next();
                this.put(e.getCharKey(), e.getValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<Character, V> e = i.next();
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
                return AbstractChar2ReferenceMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractChar2ReferenceMap.this.size();
            }

            @Override
            public void clear() {
                AbstractChar2ReferenceMap.this.clear();
            }

            @Override
            public CharIterator iterator() {
                return new AbstractCharIterator(){
                    final ObjectIterator<Map.Entry<Character, V>> i;
                    {
                        this.i = AbstractChar2ReferenceMap.this.entrySet().iterator();
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2ReferenceMap.Entry)this.i.next()).getCharKey();
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
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(){

            @Override
            public boolean contains(Object k) {
                return AbstractChar2ReferenceMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractChar2ReferenceMap.this.size();
            }

            @Override
            public void clear() {
                AbstractChar2ReferenceMap.this.clear();
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new AbstractObjectIterator<V>(){
                    final ObjectIterator<Map.Entry<Character, V>> i;
                    {
                        this.i = AbstractChar2ReferenceMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public V next() {
                        return ((Char2ReferenceMap.Entry)this.i.next()).getValue();
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
    public ObjectSet<Map.Entry<Character, V>> entrySet() {
        return this.char2ReferenceEntrySet();
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
            Char2ReferenceMap.Entry e = (Char2ReferenceMap.Entry)i.next();
            s.append(String.valueOf(e.getCharKey()));
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
    implements Char2ReferenceMap.Entry<V> {
        protected char key;
        protected V value;

        public BasicEntry(Character key, V value) {
            this.key = key.charValue();
            this.value = value;
        }

        public BasicEntry(char key, V value) {
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
            if (e.getKey() == null || !(e.getKey() instanceof Character)) {
                return false;
            }
            return this.key == ((Character)e.getKey()).charValue() && this.value == e.getValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value == null ? 0 : System.identityHashCode(this.value));
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

