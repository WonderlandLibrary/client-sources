/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2CharFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2CharMap;
import it.unimi.dsi.fastutil.shorts.Short2CharMaps;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractShort2CharMap
extends AbstractShort2CharFunction
implements Short2CharMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2CharMap() {
    }

    @Override
    public boolean containsValue(char c) {
        return this.values().contains(c);
    }

    @Override
    public boolean containsKey(short s) {
        Iterator iterator2 = this.short2CharEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Short2CharMap.Entry)iterator2.next()).getShortKey() != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final AbstractShort2CharMap this$0;
            {
                this.this$0 = abstractShort2CharMap;
            }

            @Override
            public boolean contains(short s) {
                return this.this$0.containsKey(s);
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
            public ShortIterator iterator() {
                return new ShortIterator(this){
                    private final ObjectIterator<Short2CharMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2CharMaps.fastIterator(this.this$1.this$0);
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

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public CharCollection values() {
        return new AbstractCharCollection(this){
            final AbstractShort2CharMap this$0;
            {
                this.this$0 = abstractShort2CharMap;
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
                    private final ObjectIterator<Short2CharMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2CharMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Short2CharMap.Entry)this.i.next()).getCharValue();
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
    public void putAll(Map<? extends Short, ? extends Character> map) {
        if (map instanceof Short2CharMap) {
            ObjectIterator<Short2CharMap.Entry> objectIterator = Short2CharMaps.fastIterator((Short2CharMap)map);
            while (objectIterator.hasNext()) {
                Short2CharMap.Entry entry = (Short2CharMap.Entry)objectIterator.next();
                this.put(entry.getShortKey(), entry.getCharValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Short, ? extends Character>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Character> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Short2CharMap.Entry> objectIterator = Short2CharMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Short2CharMap.Entry)objectIterator.next()).hashCode();
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
        return this.short2CharEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Short2CharMap.Entry> objectIterator = Short2CharMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Short2CharMap.Entry entry = (Short2CharMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getShortKey()));
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
    extends AbstractObjectSet<Short2CharMap.Entry> {
        protected final Short2CharMap map;

        public BasicEntrySet(Short2CharMap short2CharMap) {
            this.map = short2CharMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2CharMap.Entry) {
                Short2CharMap.Entry entry = (Short2CharMap.Entry)object;
                short s = entry.getShortKey();
                return this.map.containsKey(s) && this.map.get(s) == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.map.containsKey(s) && this.map.get(s) == ((Character)v).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2CharMap.Entry) {
                Short2CharMap.Entry entry = (Short2CharMap.Entry)object;
                return this.map.remove(entry.getShortKey(), entry.getCharValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            char c = ((Character)v).charValue();
            return this.map.remove(s, c);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Short2CharMap.Entry {
        protected short key;
        protected char value;

        public BasicEntry() {
        }

        public BasicEntry(Short s, Character c) {
            this.key = s;
            this.value = c.charValue();
        }

        public BasicEntry(short s, char c) {
            this.key = s;
            this.value = c;
        }

        @Override
        public short getShortKey() {
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
            if (object instanceof Short2CharMap.Entry) {
                Short2CharMap.Entry entry = (Short2CharMap.Entry)object;
                return this.key == entry.getShortKey() && this.value == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.key == (Short)k && this.value == ((Character)v).charValue();
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

