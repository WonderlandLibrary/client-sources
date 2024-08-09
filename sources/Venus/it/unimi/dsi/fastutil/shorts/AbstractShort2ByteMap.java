/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ByteFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteMaps;
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
public abstract class AbstractShort2ByteMap
extends AbstractShort2ByteFunction
implements Short2ByteMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2ByteMap() {
    }

    @Override
    public boolean containsValue(byte by) {
        return this.values().contains(by);
    }

    @Override
    public boolean containsKey(short s) {
        Iterator iterator2 = this.short2ByteEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Short2ByteMap.Entry)iterator2.next()).getShortKey() != s) continue;
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
            final AbstractShort2ByteMap this$0;
            {
                this.this$0 = abstractShort2ByteMap;
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
                    private final ObjectIterator<Short2ByteMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2ByteMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2ByteMap.Entry)this.i.next()).getShortKey();
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
            final AbstractShort2ByteMap this$0;
            {
                this.this$0 = abstractShort2ByteMap;
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
                    private final ObjectIterator<Short2ByteMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2ByteMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        return ((Short2ByteMap.Entry)this.i.next()).getByteValue();
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
    public void putAll(Map<? extends Short, ? extends Byte> map) {
        if (map instanceof Short2ByteMap) {
            ObjectIterator<Short2ByteMap.Entry> objectIterator = Short2ByteMaps.fastIterator((Short2ByteMap)map);
            while (objectIterator.hasNext()) {
                Short2ByteMap.Entry entry = (Short2ByteMap.Entry)objectIterator.next();
                this.put(entry.getShortKey(), entry.getByteValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Short, ? extends Byte>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Byte> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Short2ByteMap.Entry> objectIterator = Short2ByteMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Short2ByteMap.Entry)objectIterator.next()).hashCode();
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
        return this.short2ByteEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Short2ByteMap.Entry> objectIterator = Short2ByteMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Short2ByteMap.Entry entry = (Short2ByteMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getShortKey()));
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
    extends AbstractObjectSet<Short2ByteMap.Entry> {
        protected final Short2ByteMap map;

        public BasicEntrySet(Short2ByteMap short2ByteMap) {
            this.map = short2ByteMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2ByteMap.Entry) {
                Short2ByteMap.Entry entry = (Short2ByteMap.Entry)object;
                short s = entry.getShortKey();
                return this.map.containsKey(s) && this.map.get(s) == entry.getByteValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            return this.map.containsKey(s) && this.map.get(s) == ((Byte)v).byteValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2ByteMap.Entry) {
                Short2ByteMap.Entry entry = (Short2ByteMap.Entry)object;
                return this.map.remove(entry.getShortKey(), entry.getByteValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            byte by = (Byte)v;
            return this.map.remove(s, by);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Short2ByteMap.Entry {
        protected short key;
        protected byte value;

        public BasicEntry() {
        }

        public BasicEntry(Short s, Byte by) {
            this.key = s;
            this.value = by;
        }

        public BasicEntry(short s, byte by) {
            this.key = s;
            this.value = by;
        }

        @Override
        public short getShortKey() {
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
            if (object instanceof Short2ByteMap.Entry) {
                Short2ByteMap.Entry entry = (Short2ByteMap.Entry)object;
                return this.key == entry.getShortKey() && this.value == entry.getByteValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Byte)) {
                return true;
            }
            return this.key == (Short)k && this.value == (Byte)v;
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

