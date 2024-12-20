/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2LongFunction;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractObject2LongMap<K>
extends AbstractObject2LongFunction<K>
implements Object2LongMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2LongMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Long)ov);
    }

    @Override
    public boolean containsValue(long v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(Object k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends K, ? extends Long> m) {
        int n = m.size();
        Iterator<Map.Entry<K, Long>> i = m.entrySet().iterator();
        if (m instanceof Object2LongMap) {
            while (n-- != 0) {
                Object2LongMap.Entry e = (Object2LongMap.Entry)i.next();
                this.put(e.getKey(), e.getLongValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<K, Long> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(){

            @Override
            public boolean contains(Object k) {
                return AbstractObject2LongMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractObject2LongMap.this.size();
            }

            @Override
            public void clear() {
                AbstractObject2LongMap.this.clear();
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new AbstractObjectIterator<K>(){
                    final ObjectIterator<Map.Entry<K, Long>> i;
                    {
                        this.i = AbstractObject2LongMap.this.entrySet().iterator();
                    }

                    @Override
                    public K next() {
                        return ((Object2LongMap.Entry)this.i.next()).getKey();
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
    public LongCollection values() {
        return new AbstractLongCollection(){

            @Override
            public boolean contains(long k) {
                return AbstractObject2LongMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractObject2LongMap.this.size();
            }

            @Override
            public void clear() {
                AbstractObject2LongMap.this.clear();
            }

            @Override
            public LongIterator iterator() {
                return new AbstractLongIterator(){
                    final ObjectIterator<Map.Entry<K, Long>> i;
                    {
                        this.i = AbstractObject2LongMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public long nextLong() {
                        return ((Object2LongMap.Entry)this.i.next()).getLongValue();
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
    public ObjectSet<Map.Entry<K, Long>> entrySet() {
        return this.object2LongEntrySet();
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
            Object2LongMap.Entry e = (Object2LongMap.Entry)i.next();
            if (this == e.getKey()) {
                s.append("(this map)");
            } else {
                s.append(String.valueOf(e.getKey()));
            }
            s.append("=>");
            s.append(String.valueOf(e.getLongValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry<K>
    implements Object2LongMap.Entry<K> {
        protected K key;
        protected long value;

        public BasicEntry(K key, Long value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(K key, long value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Long getValue() {
            return this.value;
        }

        @Override
        public long getLongValue() {
            return this.value;
        }

        @Override
        public long setValue(long value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long setValue(Long value) {
            return this.setValue((long)value);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Long)) {
                return false;
            }
            return (this.key == null ? e.getKey() == null : this.key.equals(e.getKey())) && this.value == (Long)e.getValue();
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ HashCommon.long2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

