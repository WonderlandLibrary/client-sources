/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2ShortFunction;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ShortMap;
import it.unimi.dsi.fastutil.objects.Reference2ShortMaps;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
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
public abstract class AbstractReference2ShortMap<K>
extends AbstractReference2ShortFunction<K>
implements Reference2ShortMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2ShortMap() {
    }

    @Override
    public boolean containsValue(short s) {
        return this.values().contains(s);
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator iterator2 = this.reference2ShortEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Reference2ShortMap.Entry)iterator2.next()).getKey() != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final AbstractReference2ShortMap this$0;
            {
                this.this$0 = abstractReference2ShortMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsKey(object);
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
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(this){
                    private final ObjectIterator<Reference2ShortMap.Entry<K>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2ShortMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        return ((Reference2ShortMap.Entry)this.i.next()).getKey();
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
            final AbstractReference2ShortMap this$0;
            {
                this.this$0 = abstractReference2ShortMap;
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
                    private final ObjectIterator<Reference2ShortMap.Entry<K>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2ShortMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Reference2ShortMap.Entry)this.i.next()).getShortValue();
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
    public void putAll(Map<? extends K, ? extends Short> map) {
        if (map instanceof Reference2ShortMap) {
            ObjectIterator objectIterator = Reference2ShortMaps.fastIterator((Reference2ShortMap)map);
            while (objectIterator.hasNext()) {
                Reference2ShortMap.Entry entry = (Reference2ShortMap.Entry)objectIterator.next();
                this.put(entry.getKey(), entry.getShortValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<K, Short>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<K, Short> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Reference2ShortMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Reference2ShortMap.Entry)objectIterator.next()).hashCode();
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
        return this.reference2ShortEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Reference2ShortMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Reference2ShortMap.Entry entry = (Reference2ShortMap.Entry)objectIterator.next();
            if (this == entry.getKey()) {
                stringBuilder.append("(this map)");
            } else {
                stringBuilder.append(String.valueOf(entry.getKey()));
            }
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

    public static abstract class BasicEntrySet<K>
    extends AbstractObjectSet<Reference2ShortMap.Entry<K>> {
        protected final Reference2ShortMap<K> map;

        public BasicEntrySet(Reference2ShortMap<K> reference2ShortMap) {
            this.map = reference2ShortMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2ShortMap.Entry) {
                Reference2ShortMap.Entry entry = (Reference2ShortMap.Entry)object;
                Object k = entry.getKey();
                return this.map.containsKey(k) && this.map.getShort(k) == entry.getShortValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            return this.map.containsKey(k) && this.map.getShort(k) == ((Short)v).shortValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2ShortMap.Entry) {
                Reference2ShortMap.Entry entry = (Reference2ShortMap.Entry)object;
                return this.map.remove(entry.getKey(), entry.getShortValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            short s = (Short)v;
            return this.map.remove(k, s);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<K>
    implements Reference2ShortMap.Entry<K> {
        protected K key;
        protected short value;

        public BasicEntry() {
        }

        public BasicEntry(K k, Short s) {
            this.key = k;
            this.value = s;
        }

        public BasicEntry(K k, short s) {
            this.key = k;
            this.value = s;
        }

        @Override
        public K getKey() {
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
            if (object instanceof Reference2ShortMap.Entry) {
                Reference2ShortMap.Entry entry = (Reference2ShortMap.Entry)object;
                return this.key == entry.getKey() && this.value == entry.getShortValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            return this.key == k && this.value == (Short)v;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

