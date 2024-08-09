/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.collection;

import io.netty.util.collection.LongObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LongCollections {
    private static final LongObjectMap<Object> EMPTY_MAP = new EmptyMap(null);

    private LongCollections() {
    }

    public static <V> LongObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> LongObjectMap<V> unmodifiableMap(LongObjectMap<V> longObjectMap) {
        return new UnmodifiableMap<V>(longObjectMap);
    }

    private static final class UnmodifiableMap<V>
    implements LongObjectMap<V> {
        private final LongObjectMap<V> map;
        private Set<Long> keySet;
        private Set<Map.Entry<Long, V>> entrySet;
        private Collection<V> values;
        private Iterable<LongObjectMap.PrimitiveEntry<V>> entries;

        UnmodifiableMap(LongObjectMap<V> longObjectMap) {
            this.map = longObjectMap;
        }

        @Override
        public V get(long l) {
            return this.map.get(l);
        }

        @Override
        public V put(long l, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public V remove(long l) {
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
        public boolean containsKey(long l) {
            return this.map.containsKey(l);
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
        public V put(Long l, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public V remove(Object object) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public void putAll(Map<? extends Long, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override
        public Iterable<LongObjectMap.PrimitiveEntry<V>> entries() {
            if (this.entries == null) {
                this.entries = new Iterable<LongObjectMap.PrimitiveEntry<V>>(this){
                    final UnmodifiableMap this$0;
                    {
                        this.this$0 = unmodifiableMap;
                    }

                    @Override
                    public Iterator<LongObjectMap.PrimitiveEntry<V>> iterator() {
                        return new IteratorImpl(this.this$0, UnmodifiableMap.access$100(this.this$0).entries().iterator());
                    }
                };
            }
            return this.entries;
        }

        @Override
        public Set<Long> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        @Override
        public Set<Map.Entry<Long, V>> entrySet() {
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
            return this.put((Long)object, (V)object2);
        }

        static LongObjectMap access$100(UnmodifiableMap unmodifiableMap) {
            return unmodifiableMap.map;
        }

        private class EntryImpl
        implements LongObjectMap.PrimitiveEntry<V> {
            private final LongObjectMap.PrimitiveEntry<V> entry;
            final UnmodifiableMap this$0;

            EntryImpl(UnmodifiableMap unmodifiableMap, LongObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.this$0 = unmodifiableMap;
                this.entry = primitiveEntry;
            }

            @Override
            public long key() {
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
        implements Iterator<LongObjectMap.PrimitiveEntry<V>> {
            final Iterator<LongObjectMap.PrimitiveEntry<V>> iter;
            final UnmodifiableMap this$0;

            IteratorImpl(UnmodifiableMap unmodifiableMap, Iterator<LongObjectMap.PrimitiveEntry<V>> iterator2) {
                this.this$0 = unmodifiableMap;
                this.iter = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public LongObjectMap.PrimitiveEntry<V> next() {
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
    implements LongObjectMap<Object> {
        private EmptyMap() {
        }

        @Override
        public Object get(long l) {
            return null;
        }

        @Override
        public Object put(long l, Object object) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public Object remove(long l) {
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
        public Set<Long> keySet() {
            return Collections.emptySet();
        }

        @Override
        public boolean containsKey(long l) {
            return true;
        }

        @Override
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public Iterable<LongObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        @Override
        public Object get(Object object) {
            return null;
        }

        @Override
        public Object put(Long l, Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object remove(Object object) {
            return null;
        }

        @Override
        public void putAll(Map<? extends Long, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override
        public Set<Map.Entry<Long, Object>> entrySet() {
            return Collections.emptySet();
        }

        @Override
        public Object put(Object object, Object object2) {
            return this.put((Long)object, object2);
        }

        EmptyMap(1 var1_1) {
            this();
        }
    }
}

