/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2IntFunction;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractReference2IntMap<K>
extends AbstractReference2IntFunction<K>
implements Reference2IntMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2IntMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Integer)ov);
    }

    @Override
    public boolean containsValue(int v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(Object k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends K, ? extends Integer> m) {
        int n = m.size();
        Iterator<Map.Entry<K, Integer>> i = m.entrySet().iterator();
        if (m instanceof Reference2IntMap) {
            while (n-- != 0) {
                Reference2IntMap.Entry e = (Reference2IntMap.Entry)i.next();
                this.put(e.getKey(), e.getIntValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<K, Integer> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(){

            @Override
            public boolean contains(Object k) {
                return AbstractReference2IntMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractReference2IntMap.this.size();
            }

            @Override
            public void clear() {
                AbstractReference2IntMap.this.clear();
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new AbstractObjectIterator<K>(){
                    final ObjectIterator<Map.Entry<K, Integer>> i;
                    {
                        this.i = AbstractReference2IntMap.this.entrySet().iterator();
                    }

                    @Override
                    public K next() {
                        return ((Reference2IntMap.Entry)this.i.next()).getKey();
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
    public IntCollection values() {
        return new AbstractIntCollection(){

            @Override
            public boolean contains(int k) {
                return AbstractReference2IntMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractReference2IntMap.this.size();
            }

            @Override
            public void clear() {
                AbstractReference2IntMap.this.clear();
            }

            @Override
            public IntIterator iterator() {
                return new AbstractIntIterator(){
                    final ObjectIterator<Map.Entry<K, Integer>> i;
                    {
                        this.i = AbstractReference2IntMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public int nextInt() {
                        return ((Reference2IntMap.Entry)this.i.next()).getIntValue();
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
    public ObjectSet<Map.Entry<K, Integer>> entrySet() {
        return this.reference2IntEntrySet();
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
            Reference2IntMap.Entry e = (Reference2IntMap.Entry)i.next();
            if (this == e.getKey()) {
                s.append("(this map)");
            } else {
                s.append(String.valueOf(e.getKey()));
            }
            s.append("=>");
            s.append(String.valueOf(e.getIntValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry<K>
    implements Reference2IntMap.Entry<K> {
        protected K key;
        protected int value;

        public BasicEntry(K key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(K key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Integer getValue() {
            return this.value;
        }

        @Override
        public int getIntValue() {
            return this.value;
        }

        @Override
        public int setValue(int value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer setValue(Integer value) {
            return this.setValue((int)value);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            return this.key == e.getKey() && this.value == (Integer)e.getValue();
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

