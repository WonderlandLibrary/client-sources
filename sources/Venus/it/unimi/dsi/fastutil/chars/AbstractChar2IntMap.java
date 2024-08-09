/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2IntFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntMaps;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
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
public abstract class AbstractChar2IntMap
extends AbstractChar2IntFunction
implements Char2IntMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2IntMap() {
    }

    @Override
    public boolean containsValue(int n) {
        return this.values().contains(n);
    }

    @Override
    public boolean containsKey(char c) {
        Iterator iterator2 = this.char2IntEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Char2IntMap.Entry)iterator2.next()).getCharKey() != c) continue;
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
            final AbstractChar2IntMap this$0;
            {
                this.this$0 = abstractChar2IntMap;
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
                    private final ObjectIterator<Char2IntMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2IntMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2IntMap.Entry)this.i.next()).getCharKey();
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
    public IntCollection values() {
        return new AbstractIntCollection(this){
            final AbstractChar2IntMap this$0;
            {
                this.this$0 = abstractChar2IntMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsValue(n);
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
            public IntIterator iterator() {
                return new IntIterator(this){
                    private final ObjectIterator<Char2IntMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2IntMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Char2IntMap.Entry)this.i.next()).getIntValue();
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
    public void putAll(Map<? extends Character, ? extends Integer> map) {
        if (map instanceof Char2IntMap) {
            ObjectIterator<Char2IntMap.Entry> objectIterator = Char2IntMaps.fastIterator((Char2IntMap)map);
            while (objectIterator.hasNext()) {
                Char2IntMap.Entry entry = (Char2IntMap.Entry)objectIterator.next();
                this.put(entry.getCharKey(), entry.getIntValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Character, ? extends Integer>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Character, ? extends Integer> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Char2IntMap.Entry> objectIterator = Char2IntMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Char2IntMap.Entry)objectIterator.next()).hashCode();
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
        return this.char2IntEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Char2IntMap.Entry> objectIterator = Char2IntMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Char2IntMap.Entry entry = (Char2IntMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getCharKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getIntValue()));
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
    extends AbstractObjectSet<Char2IntMap.Entry> {
        protected final Char2IntMap map;

        public BasicEntrySet(Char2IntMap char2IntMap) {
            this.map = char2IntMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2IntMap.Entry) {
                Char2IntMap.Entry entry = (Char2IntMap.Entry)object;
                char c = entry.getCharKey();
                return this.map.containsKey(c) && this.map.get(c) == entry.getIntValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            return this.map.containsKey(c) && this.map.get(c) == ((Integer)v).intValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2IntMap.Entry) {
                Char2IntMap.Entry entry = (Char2IntMap.Entry)object;
                return this.map.remove(entry.getCharKey(), entry.getIntValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            int n = (Integer)v;
            return this.map.remove(c, n);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Char2IntMap.Entry {
        protected char key;
        protected int value;

        public BasicEntry() {
        }

        public BasicEntry(Character c, Integer n) {
            this.key = c.charValue();
            this.value = n;
        }

        public BasicEntry(char c, int n) {
            this.key = c;
            this.value = n;
        }

        @Override
        public char getCharKey() {
            return this.key;
        }

        @Override
        public int getIntValue() {
            return this.value;
        }

        @Override
        public int setValue(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2IntMap.Entry) {
                Char2IntMap.Entry entry = (Char2IntMap.Entry)object;
                return this.key == entry.getCharKey() && this.value == entry.getIntValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            return this.key == ((Character)k).charValue() && this.value == (Integer)v;
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

