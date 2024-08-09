/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ShortFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ShortMap;
import it.unimi.dsi.fastutil.chars.Char2ShortMaps;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractChar2ShortMap
extends AbstractChar2ShortFunction
implements Char2ShortMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2ShortMap() {
    }

    @Override
    public boolean containsValue(short s) {
        return this.values().contains(s);
    }

    @Override
    public boolean containsKey(char c) {
        Iterator iterator2 = this.char2ShortEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Char2ShortMap.Entry)iterator2.next()).getCharKey() != c) continue;
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
            final AbstractChar2ShortMap this$0;
            {
                this.this$0 = abstractChar2ShortMap;
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
                    private final ObjectIterator<Char2ShortMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2ShortMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2ShortMap.Entry)this.i.next()).getCharKey();
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
    public ShortCollection values() {
        return new AbstractShortCollection(this){
            final AbstractChar2ShortMap this$0;
            {
                this.this$0 = abstractChar2ShortMap;
            }

            @Override
            public boolean contains(short s) {
                return this.this$0.containsValue(s);
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
                    private final ObjectIterator<Char2ShortMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2ShortMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Char2ShortMap.Entry)this.i.next()).getShortValue();
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
    public void putAll(Map<? extends Character, ? extends Short> map) {
        if (map instanceof Char2ShortMap) {
            ObjectIterator<Char2ShortMap.Entry> objectIterator = Char2ShortMaps.fastIterator((Char2ShortMap)map);
            while (objectIterator.hasNext()) {
                Char2ShortMap.Entry entry = (Char2ShortMap.Entry)objectIterator.next();
                this.put(entry.getCharKey(), entry.getShortValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Character, ? extends Short>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Character, ? extends Short> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Char2ShortMap.Entry> objectIterator = Char2ShortMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Char2ShortMap.Entry)objectIterator.next()).hashCode();
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
        return this.char2ShortEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Char2ShortMap.Entry> objectIterator = Char2ShortMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Char2ShortMap.Entry entry = (Char2ShortMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getCharKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getShortValue()));
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
    extends AbstractObjectSet<Char2ShortMap.Entry> {
        protected final Char2ShortMap map;

        public BasicEntrySet(Char2ShortMap char2ShortMap) {
            this.map = char2ShortMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2ShortMap.Entry) {
                Char2ShortMap.Entry entry = (Char2ShortMap.Entry)object;
                char c = entry.getCharKey();
                return this.map.containsKey(c) && this.map.get(c) == entry.getShortValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            return this.map.containsKey(c) && this.map.get(c) == ((Short)v).shortValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2ShortMap.Entry) {
                Char2ShortMap.Entry entry = (Char2ShortMap.Entry)object;
                return this.map.remove(entry.getCharKey(), entry.getShortValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            short s = (Short)v;
            return this.map.remove(c, s);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Char2ShortMap.Entry {
        protected char key;
        protected short value;

        public BasicEntry() {
        }

        public BasicEntry(Character c, Short s) {
            this.key = c.charValue();
            this.value = s;
        }

        public BasicEntry(char c, short s) {
            this.key = c;
            this.value = s;
        }

        @Override
        public char getCharKey() {
            return this.key;
        }

        @Override
        public short getShortValue() {
            return this.value;
        }

        @Override
        public short setValue(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2ShortMap.Entry) {
                Char2ShortMap.Entry entry = (Char2ShortMap.Entry)object;
                return this.key == entry.getCharKey() && this.value == entry.getShortValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            return this.key == ((Character)k).charValue() && this.value == (Short)v;
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

