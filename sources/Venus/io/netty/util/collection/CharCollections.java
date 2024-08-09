/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.collection;

import io.netty.util.collection.CharObjectMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class CharCollections {
    private static final CharObjectMap<Object> EMPTY_MAP = new EmptyMap(null);

    private CharCollections() {
    }

    public static <V> CharObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> CharObjectMap<V> unmodifiableMap(CharObjectMap<V> charObjectMap) {
        return new UnmodifiableMap<V>(charObjectMap);
    }

    private static final class UnmodifiableMap<V>
    implements CharObjectMap<V> {
        private final CharObjectMap<V> map;
        private Set<Character> keySet;
        private Set<Map.Entry<Character, V>> entrySet;
        private Collection<V> values;
        private Iterable<CharObjectMap.PrimitiveEntry<V>> entries;

        UnmodifiableMap(CharObjectMap<V> charObjectMap) {
            this.map = charObjectMap;
        }

        @Override
        public V get(char c) {
            return this.map.get(c);
        }

        @Override
        public V put(char c, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public V remove(char c) {
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
        public boolean containsKey(char c) {
            return this.map.containsKey(c);
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
        public V put(Character c, V v) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public V remove(Object object) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public void putAll(Map<? extends Character, ? extends V> map) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override
        public Iterable<CharObjectMap.PrimitiveEntry<V>> entries() {
            if (this.entries == null) {
                this.entries = new Iterable<CharObjectMap.PrimitiveEntry<V>>(this){
                    final UnmodifiableMap this$0;
                    {
                        this.this$0 = unmodifiableMap;
                    }

                    @Override
                    public Iterator<CharObjectMap.PrimitiveEntry<V>> iterator() {
                        return new IteratorImpl(this.this$0, UnmodifiableMap.access$100(this.this$0).entries().iterator());
                    }
                };
            }
            return this.entries;
        }

        @Override
        public Set<Character> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        @Override
        public Set<Map.Entry<Character, V>> entrySet() {
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
            return this.put((Character)object, (V)object2);
        }

        static CharObjectMap access$100(UnmodifiableMap unmodifiableMap) {
            return unmodifiableMap.map;
        }

        private class EntryImpl
        implements CharObjectMap.PrimitiveEntry<V> {
            private final CharObjectMap.PrimitiveEntry<V> entry;
            final UnmodifiableMap this$0;

            EntryImpl(UnmodifiableMap unmodifiableMap, CharObjectMap.PrimitiveEntry<V> primitiveEntry) {
                this.this$0 = unmodifiableMap;
                this.entry = primitiveEntry;
            }

            @Override
            public char key() {
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
        implements Iterator<CharObjectMap.PrimitiveEntry<V>> {
            final Iterator<CharObjectMap.PrimitiveEntry<V>> iter;
            final UnmodifiableMap this$0;

            IteratorImpl(UnmodifiableMap unmodifiableMap, Iterator<CharObjectMap.PrimitiveEntry<V>> iterator2) {
                this.this$0 = unmodifiableMap;
                this.iter = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public CharObjectMap.PrimitiveEntry<V> next() {
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
    implements CharObjectMap<Object> {
        private EmptyMap() {
        }

        @Override
        public Object get(char c) {
            return null;
        }

        @Override
        public Object put(char c, Object object) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public Object remove(char c) {
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
        public Set<Character> keySet() {
            return Collections.emptySet();
        }

        @Override
        public boolean containsKey(char c) {
            return true;
        }

        @Override
        public boolean containsValue(Object object) {
            return true;
        }

        @Override
        public Iterable<CharObjectMap.PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        @Override
        public Object get(Object object) {
            return null;
        }

        @Override
        public Object put(Character c, Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object remove(Object object) {
            return null;
        }

        @Override
        public void putAll(Map<? extends Character, ?> map) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<Object> values() {
            return Collections.emptyList();
        }

        @Override
        public Set<Map.Entry<Character, Object>> entrySet() {
            return Collections.emptySet();
        }

        @Override
        public Object put(Object object, Object object2) {
            return this.put((Character)object, object2);
        }

        EmptyMap(1 var1_1) {
            this();
        }
    }
}

