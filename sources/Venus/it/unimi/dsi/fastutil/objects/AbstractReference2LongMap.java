/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2LongFunction;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2LongMap;
import it.unimi.dsi.fastutil.objects.Reference2LongMaps;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractReference2LongMap<K>
extends AbstractReference2LongFunction<K>
implements Reference2LongMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2LongMap() {
    }

    @Override
    public boolean containsValue(long l) {
        return this.values().contains(l);
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator iterator2 = this.reference2LongEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Reference2LongMap.Entry)iterator2.next()).getKey() != object) continue;
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
            final AbstractReference2LongMap this$0;
            {
                this.this$0 = abstractReference2LongMap;
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
                    private final ObjectIterator<Reference2LongMap.Entry<K>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2LongMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        return ((Reference2LongMap.Entry)this.i.next()).getKey();
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
    public LongCollection values() {
        return new AbstractLongCollection(this){
            final AbstractReference2LongMap this$0;
            {
                this.this$0 = abstractReference2LongMap;
            }

            @Override
            public boolean contains(long l) {
                return this.this$0.containsValue(l);
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
            public LongIterator iterator() {
                return new LongIterator(this){
                    private final ObjectIterator<Reference2LongMap.Entry<K>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2LongMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        return ((Reference2LongMap.Entry)this.i.next()).getLongValue();
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
    public void putAll(Map<? extends K, ? extends Long> map) {
        if (map instanceof Reference2LongMap) {
            ObjectIterator objectIterator = Reference2LongMaps.fastIterator((Reference2LongMap)map);
            while (objectIterator.hasNext()) {
                Reference2LongMap.Entry entry = (Reference2LongMap.Entry)objectIterator.next();
                this.put(entry.getKey(), entry.getLongValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<K, Long>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<K, Long> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Reference2LongMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Reference2LongMap.Entry)objectIterator.next()).hashCode();
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
        return this.reference2LongEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Reference2LongMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Reference2LongMap.Entry entry = (Reference2LongMap.Entry)objectIterator.next();
            if (this == entry.getKey()) {
                stringBuilder.append("(this map)");
            } else {
                stringBuilder.append(String.valueOf(entry.getKey()));
            }
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getLongValue()));
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
    extends AbstractObjectSet<Reference2LongMap.Entry<K>> {
        protected final Reference2LongMap<K> map;

        public BasicEntrySet(Reference2LongMap<K> reference2LongMap) {
            this.map = reference2LongMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2LongMap.Entry) {
                Reference2LongMap.Entry entry = (Reference2LongMap.Entry)object;
                Object k = entry.getKey();
                return this.map.containsKey(k) && this.map.getLong(k) == entry.getLongValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Long)) {
                return true;
            }
            return this.map.containsKey(k) && this.map.getLong(k) == ((Long)v).longValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2LongMap.Entry) {
                Reference2LongMap.Entry entry = (Reference2LongMap.Entry)object;
                return this.map.remove(entry.getKey(), entry.getLongValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Long)) {
                return true;
            }
            long l = (Long)v;
            return this.map.remove(k, l);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<K>
    implements Reference2LongMap.Entry<K> {
        protected K key;
        protected long value;

        public BasicEntry() {
        }

        public BasicEntry(K k, Long l) {
            this.key = k;
            this.value = l;
        }

        public BasicEntry(K k, long l) {
            this.key = k;
            this.value = l;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public long getLongValue() {
            return this.value;
        }

        @Override
        public long setValue(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2LongMap.Entry) {
                Reference2LongMap.Entry entry = (Reference2LongMap.Entry)object;
                return this.key == entry.getKey() && this.value == entry.getLongValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Long)) {
                return true;
            }
            return this.key == k && this.value == (Long)v;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ HashCommon.long2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

