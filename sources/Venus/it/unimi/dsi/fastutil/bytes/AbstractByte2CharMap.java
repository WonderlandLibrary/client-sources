/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2CharFunction;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharMaps;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
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
public abstract class AbstractByte2CharMap
extends AbstractByte2CharFunction
implements Byte2CharMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractByte2CharMap() {
    }

    @Override
    public boolean containsValue(char c) {
        return this.values().contains(c);
    }

    @Override
    public boolean containsKey(byte by) {
        Iterator iterator2 = this.byte2CharEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Byte2CharMap.Entry)iterator2.next()).getByteKey() != by) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final AbstractByte2CharMap this$0;
            {
                this.this$0 = abstractByte2CharMap;
            }

            @Override
            public boolean contains(byte by) {
                return this.this$0.containsKey(by);
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
                    private final ObjectIterator<Byte2CharMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Byte2CharMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2CharMap.Entry)this.i.next()).getByteKey();
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
            final AbstractByte2CharMap this$0;
            {
                this.this$0 = abstractByte2CharMap;
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
                    private final ObjectIterator<Byte2CharMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Byte2CharMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Byte2CharMap.Entry)this.i.next()).getCharValue();
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
    public void putAll(Map<? extends Byte, ? extends Character> map) {
        if (map instanceof Byte2CharMap) {
            ObjectIterator<Byte2CharMap.Entry> objectIterator = Byte2CharMaps.fastIterator((Byte2CharMap)map);
            while (objectIterator.hasNext()) {
                Byte2CharMap.Entry entry = (Byte2CharMap.Entry)objectIterator.next();
                this.put(entry.getByteKey(), entry.getCharValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Byte, ? extends Character>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Byte, ? extends Character> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Byte2CharMap.Entry> objectIterator = Byte2CharMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Byte2CharMap.Entry)objectIterator.next()).hashCode();
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
        return this.byte2CharEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Byte2CharMap.Entry> objectIterator = Byte2CharMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Byte2CharMap.Entry entry = (Byte2CharMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getByteKey()));
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
    extends AbstractObjectSet<Byte2CharMap.Entry> {
        protected final Byte2CharMap map;

        public BasicEntrySet(Byte2CharMap byte2CharMap) {
            this.map = byte2CharMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2CharMap.Entry) {
                Byte2CharMap.Entry entry = (Byte2CharMap.Entry)object;
                byte by = entry.getByteKey();
                return this.map.containsKey(by) && this.map.get(by) == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            byte by = (Byte)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.map.containsKey(by) && this.map.get(by) == ((Character)v).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2CharMap.Entry) {
                Byte2CharMap.Entry entry = (Byte2CharMap.Entry)object;
                return this.map.remove(entry.getByteKey(), entry.getCharValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            byte by = (Byte)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            char c = ((Character)v).charValue();
            return this.map.remove(by, c);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Byte2CharMap.Entry {
        protected byte key;
        protected char value;

        public BasicEntry() {
        }

        public BasicEntry(Byte by, Character c) {
            this.key = by;
            this.value = c.charValue();
        }

        public BasicEntry(byte by, char c) {
            this.key = by;
            this.value = c;
        }

        @Override
        public byte getByteKey() {
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
            if (object instanceof Byte2CharMap.Entry) {
                Byte2CharMap.Entry entry = (Byte2CharMap.Entry)object;
                return this.key == entry.getByteKey() && this.value == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.key == (Byte)k && this.value == ((Character)v).charValue();
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

