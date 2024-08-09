/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ReferenceFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMaps;
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
public abstract class AbstractShort2ReferenceMap<V>
extends AbstractShort2ReferenceFunction<V>
implements Short2ReferenceMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2ReferenceMap() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values().contains(object);
    }

    @Override
    public boolean containsKey(short s) {
        Iterator iterator2 = this.short2ReferenceEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Short2ReferenceMap.Entry)iterator2.next()).getShortKey() != s) continue;
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
            final AbstractShort2ReferenceMap this$0;
            {
                this.this$0 = abstractShort2ReferenceMap;
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
                    private final ObjectIterator<Short2ReferenceMap.Entry<V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2ReferenceMap.Entry)this.i.next()).getShortKey();
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
            final AbstractShort2ReferenceMap this$0;
            {
                this.this$0 = abstractShort2ReferenceMap;
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
                    private final ObjectIterator<Short2ReferenceMap.Entry<V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Short2ReferenceMap.Entry)this.i.next()).getValue();
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
    public void putAll(Map<? extends Short, ? extends V> map) {
        if (map instanceof Short2ReferenceMap) {
            ObjectIterator objectIterator = Short2ReferenceMaps.fastIterator((Short2ReferenceMap)map);
            while (objectIterator.hasNext()) {
                Short2ReferenceMap.Entry entry = (Short2ReferenceMap.Entry)objectIterator.next();
                this.put(entry.getShortKey(), entry.getValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<Short, V>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<Short, V> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Short2ReferenceMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Short2ReferenceMap.Entry)objectIterator.next()).hashCode();
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
        return this.short2ReferenceEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Short2ReferenceMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Short2ReferenceMap.Entry entry = (Short2ReferenceMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getShortKey()));
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
    extends AbstractObjectSet<Short2ReferenceMap.Entry<V>> {
        protected final Short2ReferenceMap<V> map;

        public BasicEntrySet(Short2ReferenceMap<V> short2ReferenceMap) {
            this.map = short2ReferenceMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2ReferenceMap.Entry) {
                Short2ReferenceMap.Entry entry = (Short2ReferenceMap.Entry)object;
                short s = entry.getShortKey();
                return this.map.containsKey(s) && this.map.get(s) == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            return this.map.containsKey(s) && this.map.get(s) == v;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2ReferenceMap.Entry) {
                Short2ReferenceMap.Entry entry = (Short2ReferenceMap.Entry)object;
                return this.map.remove(entry.getShortKey(), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            return this.map.remove(s, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<V>
    implements Short2ReferenceMap.Entry<V> {
        protected short key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(Short s, V v) {
            this.key = s;
            this.value = v;
        }

        public BasicEntry(short s, V v) {
            this.key = s;
            this.value = v;
        }

        @Override
        public short getShortKey() {
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
            if (object instanceof Short2ReferenceMap.Entry) {
                Short2ReferenceMap.Entry entry = (Short2ReferenceMap.Entry)object;
                return this.key == entry.getShortKey() && this.value == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            Object v = entry.getValue();
            return this.key == (Short)k && this.value == v;
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

