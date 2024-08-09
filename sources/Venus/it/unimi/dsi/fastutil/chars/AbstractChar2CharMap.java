/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2CharFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharMaps;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractChar2CharMap
extends AbstractChar2CharFunction
implements Char2CharMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2CharMap() {
    }

    @Override
    public boolean containsValue(char c) {
        return this.values().contains(c);
    }

    @Override
    public boolean containsKey(char c) {
        Iterator iterator2 = this.char2CharEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Char2CharMap.Entry)iterator2.next()).getCharKey() != c) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final AbstractChar2CharMap this$0;
            {
                this.this$0 = abstractChar2CharMap;
            }

            @Override
            public boolean contains(char c) {
                return this.this$0.containsKey(c);
            }

            @Override
            public int size() {
                return this.this$0.size();
            }

            @Override
            public void clear() {
                this.this$0.clear();
            }

            @Override
            public CharIterator iterator() {
                return new CharIterator(this){
                    private final ObjectIterator<Char2CharMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2CharMaps.fastIterator(this.this$1.this$0);
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

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public CharCollection values() {
        return new AbstractCharCollection(this){
            final AbstractChar2CharMap this$0;
            {
                this.this$0 = abstractChar2CharMap;
            }

            @Override
            public boolean contains(char c) {
                return this.this$0.containsValue(c);
            }

            @Override
            public int size() {
                return this.this$0.size();
            }

            @Override
            public void clear() {
                this.this$0.clear();
            }

            @Override
            public CharIterator iterator() {
                return new CharIterator(this){
                    private final ObjectIterator<Char2CharMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2CharMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2CharMap.Entry)this.i.next()).getCharValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                };
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public void putAll(Map<? extends Character, ? extends Character> map) {
        if (map instanceof Char2CharMap) {
            ObjectIterator<Char2CharMap.Entry> objectIterator = Char2CharMaps.fastIterator((Char2CharMap)map);
            while (objectIterator.hasNext()) {
                Char2CharMap.Entry entry = (Char2CharMap.Entry)objectIterator.next();
                this.put(entry.getCharKey(), entry.getCharValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Character, ? extends Character>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Character, ? extends Character> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Char2CharMap.Entry> objectIterator = Char2CharMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Char2CharMap.Entry)objectIterator.next()).hashCode();
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Map)) {
            return true;
        }
        Map map = (Map)object;
        if (map.size() != this.size()) {
            return true;
        }
        return this.char2CharEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Char2CharMap.Entry> objectIterator = Char2CharMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Char2CharMap.Entry entry = (Char2CharMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getCharKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getCharValue()));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    public static abstract class BasicEntrySet
    extends AbstractObjectSet<Char2CharMap.Entry> {
        protected final Char2CharMap map;

        public BasicEntrySet(Char2CharMap char2CharMap) {
            this.map = char2CharMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2CharMap.Entry) {
                Char2CharMap.Entry entry = (Char2CharMap.Entry)object;
                char c = entry.getCharKey();
                return this.map.containsKey(c) && this.map.get(c) == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.map.containsKey(c) && this.map.get(c) == ((Character)v).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2CharMap.Entry) {
                Char2CharMap.Entry entry = (Char2CharMap.Entry)object;
                return this.map.remove(entry.getCharKey(), entry.getCharValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            char c2 = ((Character)v).charValue();
            return this.map.remove(c, c2);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Char2CharMap.Entry {
        protected char key;
        protected char value;

        public BasicEntry() {
        }

        public BasicEntry(Character c, Character c2) {
            this.key = c.charValue();
            this.value = c2.charValue();
        }

        public BasicEntry(char c, char c2) {
            this.key = c;
            this.value = c2;
        }

        @Override
        public char getCharKey() {
            return this.key;
        }

        @Override
        public char getCharValue() {
            return this.value;
        }

        @Override
        public char setValue(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2CharMap.Entry) {
                Char2CharMap.Entry entry = (Char2CharMap.Entry)object;
                return this.key == entry.getCharKey() && this.value == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.key == ((Character)k).charValue() && this.value == ((Character)v).charValue();
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

