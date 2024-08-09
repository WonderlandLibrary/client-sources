/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.chars.AbstractChar2ByteFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ByteMap;
import it.unimi.dsi.fastutil.chars.Char2ByteMaps;
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
public abstract class AbstractChar2ByteMap
extends AbstractChar2ByteFunction
implements Char2ByteMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2ByteMap() {
    }

    @Override
    public boolean containsValue(byte by) {
        return this.values().contains(by);
    }

    @Override
    public boolean containsKey(char c) {
        Iterator iterator2 = this.char2ByteEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Char2ByteMap.Entry)iterator2.next()).getCharKey() != c) continue;
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
            final AbstractChar2ByteMap this$0;
            {
                this.this$0 = abstractChar2ByteMap;
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
                    private final ObjectIterator<Char2ByteMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2ByteMaps.fastIterator(this.this$1.this$0);
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

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public ByteCollection values() {
        return new AbstractByteCollection(this){
            final AbstractChar2ByteMap this$0;
            {
                this.this$0 = abstractChar2ByteMap;
            }

            @Override
            public boolean contains(byte by) {
                return this.this$0.containsValue(by);
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
            public ByteIterator iterator() {
                return new ByteIterator(this){
                    private final ObjectIterator<Char2ByteMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2ByteMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        return ((Char2ByteMap.Entry)this.i.next()).getByteValue();
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
    public void putAll(Map<? extends Character, ? extends Byte> map) {
        if (map instanceof Char2ByteMap) {
            ObjectIterator<Char2ByteMap.Entry> objectIterator = Char2ByteMaps.fastIterator((Char2ByteMap)map);
            while (objectIterator.hasNext()) {
                Char2ByteMap.Entry entry = (Char2ByteMap.Entry)objectIterator.next();
                this.put(entry.getCharKey(), entry.getByteValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Character, ? extends Byte>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Character, ? extends Byte> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Char2ByteMap.Entry> objectIterator = Char2ByteMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Char2ByteMap.Entry)objectIterator.next()).hashCode();
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
        return this.char2ByteEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Char2ByteMap.Entry> objectIterator = Char2ByteMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Char2ByteMap.Entry entry = (Char2ByteMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getCharKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getByteValue()));
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
    extends AbstractObjectSet<Char2ByteMap.Entry> {
        protected final Char2ByteMap map;

        public BasicEntrySet(Char2ByteMap char2ByteMap) {
            this.map = char2ByteMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2ByteMap.Entry) {
                Char2ByteMap.Entry entry = (Char2ByteMap.Entry)object;
                char c = entry.getCharKey();
                return this.map.containsKey(c) && this.map.get(c) == entry.getByteValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            return this.map.containsKey(c) && this.map.get(c) == ((Byte)v).byteValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2ByteMap.Entry) {
                Char2ByteMap.Entry entry = (Char2ByteMap.Entry)object;
                return this.map.remove(entry.getCharKey(), entry.getByteValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            byte by = (Byte)v;
            return this.map.remove(c, by);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Char2ByteMap.Entry {
        protected char key;
        protected byte value;

        public BasicEntry() {
        }

        public BasicEntry(Character c, Byte by) {
            this.key = c.charValue();
            this.value = by;
        }

        public BasicEntry(char c, byte by) {
            this.key = c;
            this.value = by;
        }

        @Override
        public char getCharKey() {
            return this.key;
        }

        @Override
        public byte getByteValue() {
            return this.value;
        }

        @Override
        public byte setValue(byte by) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2ByteMap.Entry) {
                Char2ByteMap.Entry entry = (Char2ByteMap.Entry)object;
                return this.key == entry.getCharKey() && this.value == entry.getByteValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            return this.key == ((Character)k).charValue() && this.value == (Byte)v;
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

