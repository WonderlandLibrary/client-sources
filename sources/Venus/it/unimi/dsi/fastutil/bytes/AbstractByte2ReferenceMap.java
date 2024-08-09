/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ReferenceFunction;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMaps;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractByte2ReferenceMap<V>
extends AbstractByte2ReferenceFunction<V>
implements Byte2ReferenceMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractByte2ReferenceMap() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values().contains(object);
    }

    @Override
    public boolean containsKey(byte by) {
        Iterator iterator2 = this.byte2ReferenceEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Byte2ReferenceMap.Entry)iterator2.next()).getByteKey() != by) continue;
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
            final AbstractByte2ReferenceMap this$0;
            {
                this.this$0 = abstractByte2ReferenceMap;
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
                    private final ObjectIterator<Byte2ReferenceMap.Entry<V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Byte2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        return ((Byte2ReferenceMap.Entry)this.i.next()).getByteKey();
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
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(this){
            final AbstractByte2ReferenceMap this$0;
            {
                this.this$0 = abstractByte2ReferenceMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsValue(object);
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
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(this){
                    private final ObjectIterator<Byte2ReferenceMap.Entry<V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Byte2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Byte2ReferenceMap.Entry)this.i.next()).getValue();
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
    public void putAll(Map<? extends Byte, ? extends V> map) {
        if (map instanceof Byte2ReferenceMap) {
            ObjectIterator objectIterator = Byte2ReferenceMaps.fastIterator((Byte2ReferenceMap)map);
            while (objectIterator.hasNext()) {
                Byte2ReferenceMap.Entry entry = (Byte2ReferenceMap.Entry)objectIterator.next();
                this.put(entry.getByteKey(), entry.getValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<Byte, V>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<Byte, V> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Byte2ReferenceMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Byte2ReferenceMap.Entry)objectIterator.next()).hashCode();
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
        return this.byte2ReferenceEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Byte2ReferenceMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Byte2ReferenceMap.Entry entry = (Byte2ReferenceMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getByteKey()));
            stringBuilder.append("=>");
            if (this == entry.getValue()) {
                stringBuilder.append("(this map)");
                continue;
            }
            stringBuilder.append(String.valueOf(entry.getValue()));
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

    public static abstract class BasicEntrySet<V>
    extends AbstractObjectSet<Byte2ReferenceMap.Entry<V>> {
        protected final Byte2ReferenceMap<V> map;

        public BasicEntrySet(Byte2ReferenceMap<V> byte2ReferenceMap) {
            this.map = byte2ReferenceMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2ReferenceMap.Entry) {
                Byte2ReferenceMap.Entry entry = (Byte2ReferenceMap.Entry)object;
                byte by = entry.getByteKey();
                return this.map.containsKey(by) && this.map.get(by) == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            byte by = (Byte)k;
            Object v = entry.getValue();
            return this.map.containsKey(by) && this.map.get(by) == v;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2ReferenceMap.Entry) {
                Byte2ReferenceMap.Entry entry = (Byte2ReferenceMap.Entry)object;
                return this.map.remove(entry.getByteKey(), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            byte by = (Byte)k;
            Object v = entry.getValue();
            return this.map.remove(by, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<V>
    implements Byte2ReferenceMap.Entry<V> {
        protected byte key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(Byte by, V v) {
            this.key = by;
            this.value = v;
        }

        public BasicEntry(byte by, V v) {
            this.key = by;
            this.value = v;
        }

        @Override
        public byte getByteKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Byte2ReferenceMap.Entry) {
                Byte2ReferenceMap.Entry entry = (Byte2ReferenceMap.Entry)object;
                return this.key == entry.getByteKey() && this.value == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Byte)) {
                return true;
            }
            Object v = entry.getValue();
            return this.key == (Byte)k && this.value == v;
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value == null ? 0 : System.identityHashCode(this.value));
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

