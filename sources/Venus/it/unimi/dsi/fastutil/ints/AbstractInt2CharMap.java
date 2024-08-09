/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2CharFunction;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2CharMap;
import it.unimi.dsi.fastutil.ints.Int2CharMaps;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
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
public abstract class AbstractInt2CharMap
extends AbstractInt2CharFunction
implements Int2CharMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractInt2CharMap() {
    }

    @Override
    public boolean containsValue(char c) {
        return this.values().contains(c);
    }

    @Override
    public boolean containsKey(int n) {
        Iterator iterator2 = this.int2CharEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Int2CharMap.Entry)iterator2.next()).getIntKey() != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final AbstractInt2CharMap this$0;
            {
                this.this$0 = abstractInt2CharMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsKey(n);
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
                    private final ObjectIterator<Int2CharMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2CharMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Int2CharMap.Entry)this.i.next()).getIntKey();
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
            final AbstractInt2CharMap this$0;
            {
                this.this$0 = abstractInt2CharMap;
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
                    private final ObjectIterator<Int2CharMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2CharMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Int2CharMap.Entry)this.i.next()).getCharValue();
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
    public void putAll(Map<? extends Integer, ? extends Character> map) {
        if (map instanceof Int2CharMap) {
            ObjectIterator<Int2CharMap.Entry> objectIterator = Int2CharMaps.fastIterator((Int2CharMap)map);
            while (objectIterator.hasNext()) {
                Int2CharMap.Entry entry = (Int2CharMap.Entry)objectIterator.next();
                this.put(entry.getIntKey(), entry.getCharValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Integer, ? extends Character>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Integer, ? extends Character> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Int2CharMap.Entry> objectIterator = Int2CharMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Int2CharMap.Entry)objectIterator.next()).hashCode();
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
        return this.int2CharEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Int2CharMap.Entry> objectIterator = Int2CharMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Int2CharMap.Entry entry = (Int2CharMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getIntKey()));
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
    extends AbstractObjectSet<Int2CharMap.Entry> {
        protected final Int2CharMap map;

        public BasicEntrySet(Int2CharMap int2CharMap) {
            this.map = int2CharMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2CharMap.Entry) {
                Int2CharMap.Entry entry = (Int2CharMap.Entry)object;
                int n = entry.getIntKey();
                return this.map.containsKey(n) && this.map.get(n) == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.map.containsKey(n) && this.map.get(n) == ((Character)v).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2CharMap.Entry) {
                Int2CharMap.Entry entry = (Int2CharMap.Entry)object;
                return this.map.remove(entry.getIntKey(), entry.getCharValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            char c = ((Character)v).charValue();
            return this.map.remove(n, c);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Int2CharMap.Entry {
        protected int key;
        protected char value;

        public BasicEntry() {
        }

        public BasicEntry(Integer n, Character c) {
            this.key = n;
            this.value = c.charValue();
        }

        public BasicEntry(int n, char c) {
            this.key = n;
            this.value = c;
        }

        @Override
        public int getIntKey() {
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
            if (object instanceof Int2CharMap.Entry) {
                Int2CharMap.Entry entry = (Int2CharMap.Entry)object;
                return this.key == entry.getIntKey() && this.value == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.key == (Integer)k && this.value == ((Character)v).charValue();
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

