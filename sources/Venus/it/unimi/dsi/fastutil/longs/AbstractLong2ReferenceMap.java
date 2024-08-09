/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceFunction;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
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
public abstract class AbstractLong2ReferenceMap<V>
extends AbstractLong2ReferenceFunction<V>
implements Long2ReferenceMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractLong2ReferenceMap() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values().contains(object);
    }

    @Override
    public boolean containsKey(long l) {
        Iterator iterator2 = this.long2ReferenceEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Long2ReferenceMap.Entry)iterator2.next()).getLongKey() != l) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final AbstractLong2ReferenceMap this$0;
            {
                this.this$0 = abstractLong2ReferenceMap;
            }

            @Override
            public boolean contains(long l) {
                return this.this$0.containsKey(l);
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
                    private final ObjectIterator<Long2ReferenceMap.Entry<V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Long2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        return ((Long2ReferenceMap.Entry)this.i.next()).getLongKey();
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
            final AbstractLong2ReferenceMap this$0;
            {
                this.this$0 = abstractLong2ReferenceMap;
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
                    private final ObjectIterator<Long2ReferenceMap.Entry<V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Long2ReferenceMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Long2ReferenceMap.Entry)this.i.next()).getValue();
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
    public void putAll(Map<? extends Long, ? extends V> map) {
        if (map instanceof Long2ReferenceMap) {
            ObjectIterator objectIterator = Long2ReferenceMaps.fastIterator((Long2ReferenceMap)map);
            while (objectIterator.hasNext()) {
                Long2ReferenceMap.Entry entry = (Long2ReferenceMap.Entry)objectIterator.next();
                this.put(entry.getLongKey(), entry.getValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<Long, V>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<Long, V> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Long2ReferenceMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Long2ReferenceMap.Entry)objectIterator.next()).hashCode();
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
        return this.long2ReferenceEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Long2ReferenceMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Long2ReferenceMap.Entry entry = (Long2ReferenceMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getLongKey()));
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
    extends AbstractObjectSet<Long2ReferenceMap.Entry<V>> {
        protected final Long2ReferenceMap<V> map;

        public BasicEntrySet(Long2ReferenceMap<V> long2ReferenceMap) {
            this.map = long2ReferenceMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2ReferenceMap.Entry) {
                Long2ReferenceMap.Entry entry = (Long2ReferenceMap.Entry)object;
                long l = entry.getLongKey();
                return this.map.containsKey(l) && this.map.get(l) == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            long l = (Long)k;
            Object v = entry.getValue();
            return this.map.containsKey(l) && this.map.get(l) == v;
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2ReferenceMap.Entry) {
                Long2ReferenceMap.Entry entry = (Long2ReferenceMap.Entry)object;
                return this.map.remove(entry.getLongKey(), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            long l = (Long)k;
            Object v = entry.getValue();
            return this.map.remove(l, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<V>
    implements Long2ReferenceMap.Entry<V> {
        protected long key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(Long l, V v) {
            this.key = l;
            this.value = v;
        }

        public BasicEntry(long l, V v) {
            this.key = l;
            this.value = v;
        }

        @Override
        public long getLongKey() {
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
            if (object instanceof Long2ReferenceMap.Entry) {
                Long2ReferenceMap.Entry entry = (Long2ReferenceMap.Entry)object;
                return this.key == entry.getLongKey() && this.value == entry.getValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            Object v = entry.getValue();
            return this.key == (Long)k && this.value == v;
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ (this.value == null ? 0 : System.identityHashCode(this.value));
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

