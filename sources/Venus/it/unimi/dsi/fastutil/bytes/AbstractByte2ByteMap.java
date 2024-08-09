/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ByteFunction;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2ByteMap;
import it.unimi.dsi.fastutil.bytes.Byte2ByteMaps;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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
public abstract class AbstractByte2ByteMap
extends AbstractByte2ByteFunction
implements Byte2ByteMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractByte2ByteMap() {
    }

    @Override
    public boolean containsValue(byte by) {
        return this.values().contains(by);
    }

    @Override
    public boolean containsKey(byte by) {
        Iterator iterator2 = this.byte2ByteEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Byte2ByteMap.Entry)iterator2.next()).getByteKey() != by) continue;
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
            final AbstractByte2ByteMap this$0;
            {
                this.this$0 = abstractByte2ByteMap;
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
                    private final ObjectIterator<Byte2ByteMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Byte2ByteMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2ByteMap.Entry)this.i.next()).getByteKey();
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
            final AbstractByte2ByteMap this$0;
            {
                this.this$0 = abstractByte2ByteMap;
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
                    private final ObjectIterator<Byte2ByteMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Byte2ByteMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2ByteMap.Entry)this.i.next()).getByteValue();
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
    public void putAll(Map<? extends Byte, ? extends Byte> map) {
        if (map instanceof Byte2ByteMap) {
            ObjectIterator<Byte2ByteMap.Entry> objectIterator = Byte2ByteMaps.fastIterator((Byte2ByteMap)map);
            while (objectIterator.hasNext()) {
                Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)objectIterator.next();
                this.put(entry.getByteKey(), entry.getByteValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Byte, ? extends Byte>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Byte, ? extends Byte> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Byte2ByteMap.Entry> objectIterator = Byte2ByteMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Byte2ByteMap.Entry)objectIterator.next()).hashCode();
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
        return this.byte2ByteEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Byte2ByteMap.Entry> objectIterator = Byte2ByteMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getByteKey()));
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
    extends AbstractObjectSet<Byte2ByteMap.Entry> {
        protected final Byte2ByteMap map;

        public BasicEntrySet(Byte2ByteMap byte2ByteMap) {
            this.map = byte2ByteMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2ByteMap.Entry) {
                Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)object;
                byte by = entry.getByteKey();
                return this.map.containsKey(by) && this.map.get(by) == entry.getByteValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            byte by = (Byte)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            return this.map.containsKey(by) && this.map.get(by) == ((Byte)v).byteValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2ByteMap.Entry) {
                Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)object;
                return this.map.remove(entry.getByteKey(), entry.getByteValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            byte by = (Byte)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            byte by2 = (Byte)v;
            return this.map.remove(by, by2);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Byte2ByteMap.Entry {
        protected byte key;
        protected byte value;

        public BasicEntry() {
        }

        public BasicEntry(Byte by, Byte by2) {
            this.key = by;
            this.value = by2;
        }

        public BasicEntry(byte by, byte by2) {
            this.key = by;
            this.value = by2;
        }

        @Override
        public byte getByteKey() {
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
            if (object instanceof Byte2ByteMap.Entry) {
                Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)object;
                return this.key == entry.getByteKey() && this.value == entry.getByteValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            return this.key == (Byte)k && this.value == (Byte)v;
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

