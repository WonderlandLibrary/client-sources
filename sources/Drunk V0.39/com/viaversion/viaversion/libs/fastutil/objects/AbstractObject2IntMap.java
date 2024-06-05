/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;

public abstract class AbstractObject2IntMap<K>
extends AbstractObject2IntFunction<K>
implements Object2IntMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2IntMap() {
    }

    @Override
    public boolean containsKey(Object k) {
        Iterator i = this.object2IntEntrySet().iterator();
        do {
            if (!i.hasNext()) return false;
        } while (((Object2IntMap.Entry)i.next()).getKey() != k);
        return true;
    }

    @Override
    public boolean containsValue(int v) {
        Iterator i = this.object2IntEntrySet().iterator();
        do {
            if (!i.hasNext()) return false;
        } while (((Object2IntMap.Entry)i.next()).getIntValue() != v);
        return true;
    }

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    @Override
    public final int mergeInt(K key, int value, com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator remappingFunction) {
        return this.mergeInt(key, value, (IntBinaryOperator)remappingFunction);
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(){

            @Override
            public boolean contains(Object k) {
                return AbstractObject2IntMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractObject2IntMap.this.size();
            }

            @Override
            public void clear() {
                AbstractObject2IntMap.this.clear();
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(){
                    private final ObjectIterator<Object2IntMap.Entry<K>> i;
                    {
                        this.i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);
                    }

                    @Override
                    public K next() {
                        return ((Object2IntMap.Entry)this.i.next()).getKey();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }

                    @Override
                    public void forEachRemaining(Consumer<? super K> action) {
                        this.i.forEachRemaining((? super E entry) -> action.accept((Object)entry.getKey()));
                    }
                };
            }

            @Override
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 65);
            }
        };
    }

    @Override
    public IntCollection values() {
        return new AbstractIntCollection(){

            @Override
            public boolean contains(int k) {
                return AbstractObject2IntMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractObject2IntMap.this.size();
            }

            @Override
            public void clear() {
                AbstractObject2IntMap.this.clear();
            }

            @Override
            public IntIterator iterator() {
                return new IntIterator(){
                    private final ObjectIterator<Object2IntMap.Entry<K>> i;
                    {
                        this.i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);
                    }

                    @Override
                    public int nextInt() {
                        return ((Object2IntMap.Entry)this.i.next()).getIntValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }

                    @Override
                    public void forEachRemaining(IntConsumer action) {
                        this.i.forEachRemaining((? super E entry) -> action.accept(entry.getIntValue()));
                    }
                };
            }

            @Override
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 320);
            }
        };
    }

    @Override
    public void putAll(Map<? extends K, ? extends Integer> m) {
        if (m instanceof Object2IntMap) {
            ObjectIterator i = Object2IntMaps.fastIterator((Object2IntMap)m);
            while (i.hasNext()) {
                Object2IntMap.Entry e = (Object2IntMap.Entry)i.next();
                this.put(e.getKey(), e.getIntValue());
            }
            return;
        }
        int n = m.size();
        Iterator<Map.Entry<K, Integer>> i = m.entrySet().iterator();
        while (n-- != 0) {
            Map.Entry<K, Integer> e = i.next();
            this.put(e.getKey(), e.getValue());
        }
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        ObjectIterator i = Object2IntMaps.fastIterator(this);
        while (n-- != 0) {
            h += ((Object2IntMap.Entry)i.next()).hashCode();
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
        if (m.size() == this.size()) return this.object2IntEntrySet().containsAll(m.entrySet());
        return false;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = Object2IntMaps.fastIterator(this);
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (true) {
            if (n-- == 0) {
                s.append("}");
                return s.toString();
            }
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Object2IntMap.Entry e = (Object2IntMap.Entry)i.next();
            if (this == e.getKey()) {
                s.append("(this map)");
            } else {
                s.append(String.valueOf(e.getKey()));
            }
            s.append("=>");
            s.append(String.valueOf(e.getIntValue()));
        }
    }

    public static abstract class BasicEntrySet<K>
    extends AbstractObjectSet<Object2IntMap.Entry<K>> {
        protected final Object2IntMap<K> map;

        public BasicEntrySet(Object2IntMap<K> map) {
            this.map = map;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry e = (Object2IntMap.Entry)o;
                Object k = e.getKey();
                if (!this.map.containsKey(k)) return false;
                if (this.map.getInt(k) != e.getIntValue()) return false;
                return true;
            }
            Map.Entry e = (Map.Entry)o;
            Object k = e.getKey();
            Object value = e.getValue();
            if (value == null) return false;
            if (!(value instanceof Integer)) {
                return false;
            }
            if (!this.map.containsKey(k)) return false;
            if (this.map.getInt(k) != ((Integer)value).intValue()) return false;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry e = (Object2IntMap.Entry)o;
                return this.map.remove(e.getKey(), e.getIntValue());
            }
            Map.Entry e = (Map.Entry)o;
            Object k = e.getKey();
            Object value = e.getValue();
            if (value == null) return false;
            if (!(value instanceof Integer)) {
                return false;
            }
            int v = (Integer)value;
            return this.map.remove(k, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
        }
    }

    public static class BasicEntry<K>
    implements Object2IntMap.Entry<K> {
        protected K key;
        protected int value;

        public BasicEntry() {
        }

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
        public int getIntValue() {
            return this.value;
        }

        @Override
        public int setValue(int value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry e = (Object2IntMap.Entry)o;
                if (!Objects.equals(this.key, e.getKey())) return false;
                if (this.value != e.getIntValue()) return false;
                return true;
            }
            Map.Entry e = (Map.Entry)o;
            Object key = e.getKey();
            Object value = e.getValue();
            if (value == null) return false;
            if (!(value instanceof Integer)) {
                return false;
            }
            if (!Objects.equals(this.key, key)) return false;
            if (this.value != (Integer)value) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int n;
            if (this.key == null) {
                n = 0;
                return n ^ this.value;
            }
            n = this.key.hashCode();
            return n ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

