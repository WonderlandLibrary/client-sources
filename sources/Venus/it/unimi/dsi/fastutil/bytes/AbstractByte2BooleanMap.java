/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanFunction;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMaps;
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
public abstract class AbstractByte2BooleanMap
extends AbstractByte2BooleanFunction
implements Byte2BooleanMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractByte2BooleanMap() {
    }

    @Override
    public boolean containsValue(boolean bl) {
        return this.values().contains(bl);
    }

    @Override
    public boolean containsKey(byte by) {
        Iterator iterator2 = this.byte2BooleanEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Byte2BooleanMap.Entry)iterator2.next()).getByteKey() != by) continue;
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
            final AbstractByte2BooleanMap this$0;
            {
                this.this$0 = abstractByte2BooleanMap;
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
                    private final ObjectIterator<Byte2BooleanMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Byte2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2BooleanMap.Entry)this.i.next()).getByteKey();
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
    public BooleanCollection values() {
        return new AbstractBooleanCollection(this){
            final AbstractByte2BooleanMap this$0;
            {
                this.this$0 = abstractByte2BooleanMap;
            }

            @Override
            public boolean contains(boolean bl) {
                return this.this$0.containsValue(bl);
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
            public BooleanIterator iterator() {
                return new BooleanIterator(this){
                    private final ObjectIterator<Byte2BooleanMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Byte2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        return ((Byte2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    public void putAll(Map<? extends Byte, ? extends Boolean> map) {
        if (map instanceof Byte2BooleanMap) {
            ObjectIterator<Byte2BooleanMap.Entry> objectIterator = Byte2BooleanMaps.fastIterator((Byte2BooleanMap)map);
            while (objectIterator.hasNext()) {
                Byte2BooleanMap.Entry entry = (Byte2BooleanMap.Entry)objectIterator.next();
                this.put(entry.getByteKey(), entry.getBooleanValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Byte, ? extends Boolean>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Byte, ? extends Boolean> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Byte2BooleanMap.Entry> objectIterator = Byte2BooleanMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Byte2BooleanMap.Entry)objectIterator.next()).hashCode();
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
        return this.byte2BooleanEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Byte2BooleanMap.Entry> objectIterator = Byte2BooleanMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Byte2BooleanMap.Entry entry = (Byte2BooleanMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getByteKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getBooleanValue()));
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
    extends AbstractObjectSet<Byte2BooleanMap.Entry> {
        protected final Byte2BooleanMap map;

        public BasicEntrySet(Byte2BooleanMap byte2BooleanMap) {
            this.map = byte2BooleanMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2BooleanMap.Entry) {
                Byte2BooleanMap.Entry entry = (Byte2BooleanMap.Entry)object;
                byte by = entry.getByteKey();
                return this.map.containsKey(by) && this.map.get(by) == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            byte by = (Byte)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.map.containsKey(by) && this.map.get(by) == ((Boolean)v).booleanValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2BooleanMap.Entry) {
                Byte2BooleanMap.Entry entry = (Byte2BooleanMap.Entry)object;
                return this.map.remove(entry.getByteKey(), entry.getBooleanValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            byte by = (Byte)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            boolean bl = (Boolean)v;
            return this.map.remove(by, bl);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Byte2BooleanMap.Entry {
        protected byte key;
        protected boolean value;

        public BasicEntry() {
        }

        public BasicEntry(Byte by, Boolean bl) {
            this.key = by;
            this.value = bl;
        }

        public BasicEntry(byte by, boolean bl) {
            this.key = by;
            this.value = bl;
        }

        @Override
        public byte getByteKey() {
            return this.key;
        }

        @Override
        public boolean getBooleanValue() {
            return this.value;
        }

        @Override
        public boolean setValue(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2BooleanMap.Entry) {
                Byte2BooleanMap.Entry entry = (Byte2BooleanMap.Entry)object;
                return this.key == entry.getByteKey() && this.value == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.key == (Byte)k && this.value == (Boolean)v;
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value ? 1231 : 1237);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

