/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceFunction;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractLong2ReferenceMap<V>
extends AbstractLong2ReferenceFunction<V>
implements Long2ReferenceMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractLong2ReferenceMap() {
    }

    @Override
    public boolean containsValue(Object v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(long k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Long, ? extends V> m) {
        int n = m.size();
        Iterator<Map.Entry<Long, V>> i = m.entrySet().iterator();
        if (m instanceof Long2ReferenceMap) {
            while (n-- != 0) {
                Long2ReferenceMap.Entry e = (Long2ReferenceMap.Entry)i.next();
                this.put(e.getLongKey(), e.getValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<Long, V> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(){

            @Override
            public boolean contains(long k) {
                return AbstractLong2ReferenceMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractLong2ReferenceMap.this.size();
            }

            @Override
            public void clear() {
                AbstractLong2ReferenceMap.this.clear();
            }

            @Override
            public LongIterator iterator() {
                return new AbstractLongIterator(){
                    final ObjectIterator<Map.Entry<Long, V>> i;
                    {
                        this.i = AbstractLong2ReferenceMap.this.entrySet().iterator();
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
        };
    }

    @Override
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(){

            @Override
            public boolean contains(Object k) {
                return AbstractLong2ReferenceMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractLong2ReferenceMap.this.size();
            }

            @Override
            public void clear() {
                AbstractLong2ReferenceMap.this.clear();
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new AbstractObjectIterator<V>(){
                    final ObjectIterator<Map.Entry<Long, V>> i;
                    {
                        this.i = AbstractLong2ReferenceMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public V next() {
                        return ((Long2ReferenceMap.Entry)this.i.next()).getValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                };
            }
        };
    }

    @Override
    public ObjectSet<Map.Entry<Long, V>> entrySet() {
        return this.long2ReferenceEntrySet();
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        ObjectIterator i = this.entrySet().iterator();
        while (n-- != 0) {
            h += ((Map.Entry)i.next()).hashCode();
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        Map m = (Map)o;
        if (m.size() != this.size()) {
            return false;
        }
        return this.entrySet().containsAll(m.entrySet());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = this.entrySet().iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Long2ReferenceMap.Entry e = (Long2ReferenceMap.Entry)i.next();
            s.append(String.valueOf(e.getLongKey()));
            s.append("=>");
            if (this == e.getValue()) {
                s.append("(this map)");
                continue;
            }
            s.append(String.valueOf(e.getValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry<V>
    implements Long2ReferenceMap.Entry<V> {
        protected long key;
        protected V value;

        public BasicEntry(Long key, V value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(long key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Long getKey() {
            return this.key;
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
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Long)) {
                return false;
            }
            return this.key == (Long)e.getKey() && this.value == e.getValue();
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

