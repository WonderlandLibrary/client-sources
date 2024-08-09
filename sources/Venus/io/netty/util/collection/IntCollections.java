/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.collection;

import io.netty.util.collection.IntObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class IntCollections {
    private static final IntObjectMap<Object> EMPTY_MAP = new EmptyMap(null);

    private IntCollections() {
    }

    public static <V> IntObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> IntObjectMap<V> unmodifiableMap(IntObjectMap<V> intObjectMap) {
        return new UnmodifiableMap<V>(intObjectMap);
    }

    private static final class UnmodifiableMap<V>
    implements IntObjectMap<V> {
        private final IntObjectMap<V> map;
        private Set<Integer> keySet;
        private Set<Map.Entry<Integer, V>> entrySet;
        private Collection<V> values;
        private Iterable<IntObjectMap.PrimitiveEntry<V>> entries;

        UnmodifiableMap(IntObjectMap<V> intObjectMap) {
            this.map = intObjectMap;
        }

        @Override
        public V get(int n) {
            return this.map.get(n);
        }

        @Override
        public V put(int n, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public V remove(int n) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        @Override
        public boolean containsKey(int n) {
            return this.map.containsKey(n);
        }

        @Override
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public boolean containsKey(Object object) {
            return this.map.containsKey(object);
        }

        @Override
        public V get(Object object) {
            return this.map.get(object);
        }

        @Override
        public V put(Integer n, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public V remove(Object object) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override
        public Iterable<IntObjectMap.PrimitiveEntry<V>> entries() {
            if (this.entries == null) {
                this.entries = new Iterable<IntObjectMap.PrimitiveEntry<V>>(this){
                    final UnmodifiableMap this$0;
                    {
                        this.this$0 = unmodifiableMap;
                    }

                    @Override
                    public Iterator<IntObjectMap.PrimitiveEntry<V>> iterator() {
                        return new IteratorImpl(this.this$0, UnmodifiableMap.access$100(this.this$0).entries().iterator());
                    }
                };
            }
            return this.entries;
        }

        @Override
        public Set<Integer> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        @Override
        public Set<Map.Entry<Integer, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = Collections.unmodifiableSet(this.map.entrySet());
            }
            return this.entrySet;
        }

        @Override
        public Collection<V> values() {
            if (this.values == null) {
                this.values = Collections.unmodifiableCollection(this.map.values());
            }
            return this.values;
        }

        @Override
        public Object put(Object object, Object object2) {
            return this.put((Integer)object, (V)object2);
        }

        static IntObjectMap access$100(UnmodifiableMap unmodifiableMap) {
            return unmodifiableMap.map;
        }

        private class EntryImpl
        implements IntObjectMap.PrimitiveEntry<V> {
            private final IntObjectMap.PrimitiveEntry<V> entry;
            final UnmodifiableMap this$0;

            EntryImpl(UnmodifiableMap unmodifiableMap, IntObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.this$0 = unmodifiableMap;
                this.entry = primitiveEntry;
            }

            @Override
            public int key() {
                return this.entry.key();
            }

            @Override
            public V value() {
                return this.entry.value();
            }

            @Override
            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }

        private class IteratorImpl
        implements Iterator<IntObjectMap.PrimitiveEntry<V>> {
            final Iterator<IntObjectMap.PrimitiveEntry<V>> iter;
            final UnmodifiableMap this$0;

            IteratorImpl(UnmodifiableMap unmodifiableMap, Iterator<IntObjectMap.PrimitiveEntry<V>> iterator2) {
                this.this$0 = unmodifiableMap;
                this.iter = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public IntObjectMap.PrimitiveEntry<V> next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return new EntryImpl(this.this$0, this.iter.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove");
            }

            @Override
            public Object next() {
                return this.next();
            }
        }
    }

    private static final class EmptyMap
    implements IntObjectMap<Object> {
        private EmptyMap() {
        }

        @Override
        public Object get(int n) {
            return null;
        }

        @Override
        public Object put(int n, Object object) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public Object remove(int n) {
            return null;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object object) {
            return true;
        }

        @Override
        public void clear() {
        }

        @Override
        public Set<Integer> keySet() {
            return Collections.emptySet();
        }

        @Override
        public boolean containsKey(int n) {
            return true;
        }

        @Override
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public Iterable<IntObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        @Override
        public Object get(Object object) {
            return null;
        }

        @Override
        public Object put(Integer n, Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object remove(Object object) {
            return null;
        }

        @Override
        public void putAll(Map<? extends Integer, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override
        public Set<Map.Entry<Integer, Object>> entrySet() {
            return Collections.emptySet();
        }

        @Override
        public Object put(Object object, Object object2) {
            return this.put((Integer)object, object2);
        }

        EmptyMap(1 var1_1) {
            this();
        }
    }
}

