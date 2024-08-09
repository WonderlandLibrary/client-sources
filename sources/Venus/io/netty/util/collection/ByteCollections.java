/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.collection;

import io.netty.util.collection.ByteObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class ByteCollections {
    private static final ByteObjectMap<Object> EMPTY_MAP = new EmptyMap(null);

    private ByteCollections() {
    }

    public static <V> ByteObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> ByteObjectMap<V> unmodifiableMap(ByteObjectMap<V> byteObjectMap) {
        return new UnmodifiableMap<V>(byteObjectMap);
    }

    private static final class UnmodifiableMap<V>
    implements ByteObjectMap<V> {
        private final ByteObjectMap<V> map;
        private Set<Byte> keySet;
        private Set<Map.Entry<Byte, V>> entrySet;
        private Collection<V> values;
        private Iterable<ByteObjectMap.PrimitiveEntry<V>> entries;

        UnmodifiableMap(ByteObjectMap<V> byteObjectMap) {
            this.map = byteObjectMap;
        }

        @Override
        public V get(byte by) {
            return this.map.get(by);
        }

        @Override
        public V put(byte by, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public V remove(byte by) {
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
        public boolean containsKey(byte by) {
            return this.map.containsKey(by);
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
        public V put(Byte by, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public V remove(Object object) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public void putAll(Map<? extends Byte, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override
        public Iterable<ByteObjectMap.PrimitiveEntry<V>> entries() {
            if (this.entries == null) {
                this.entries = new Iterable<ByteObjectMap.PrimitiveEntry<V>>(this){
                    final UnmodifiableMap this$0;
                    {
                        this.this$0 = unmodifiableMap;
                    }

                    @Override
                    public Iterator<ByteObjectMap.PrimitiveEntry<V>> iterator() {
                        return new IteratorImpl(this.this$0, UnmodifiableMap.access$100(this.this$0).entries().iterator());
                    }
                };
            }
            return this.entries;
        }

        @Override
        public Set<Byte> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        @Override
        public Set<Map.Entry<Byte, V>> entrySet() {
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
            return this.put((Byte)object, (V)object2);
        }

        static ByteObjectMap access$100(UnmodifiableMap unmodifiableMap) {
            return unmodifiableMap.map;
        }

        private class EntryImpl
        implements ByteObjectMap.PrimitiveEntry<V> {
            private final ByteObjectMap.PrimitiveEntry<V> entry;
            final UnmodifiableMap this$0;

            EntryImpl(UnmodifiableMap unmodifiableMap, ByteObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.this$0 = unmodifiableMap;
                this.entry = primitiveEntry;
            }

            @Override
            public byte key() {
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
        implements Iterator<ByteObjectMap.PrimitiveEntry<V>> {
            final Iterator<ByteObjectMap.PrimitiveEntry<V>> iter;
            final UnmodifiableMap this$0;

            IteratorImpl(UnmodifiableMap unmodifiableMap, Iterator<ByteObjectMap.PrimitiveEntry<V>> iterator2) {
                this.this$0 = unmodifiableMap;
                this.iter = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public ByteObjectMap.PrimitiveEntry<V> next() {
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
    implements ByteObjectMap<Object> {
        private EmptyMap() {
        }

        @Override
        public Object get(byte by) {
            return null;
        }

        @Override
        public Object put(byte by, Object object) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public Object remove(byte by) {
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
        public Set<Byte> keySet() {
            return Collections.emptySet();
        }

        @Override
        public boolean containsKey(byte by) {
            return true;
        }

        @Override
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public Iterable<ByteObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        @Override
        public Object get(Object object) {
            return null;
        }

        @Override
        public Object put(Byte by, Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object remove(Object object) {
            return null;
        }

        @Override
        public void putAll(Map<? extends Byte, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override
        public Set<Map.Entry<Byte, Object>> entrySet() {
            return Collections.emptySet();
        }

        @Override
        public Object put(Object object, Object object2) {
            return this.put((Byte)object, object2);
        }

        EmptyMap(1 var1_1) {
            this();
        }
    }
}

