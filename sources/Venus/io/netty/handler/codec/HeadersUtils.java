/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.handler.codec.Headers;
import io.netty.util.internal.ObjectUtil;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class HeadersUtils {
    private HeadersUtils() {
    }

    public static <K, V> List<String> getAllAsString(Headers<K, V, ?> headers, K k) {
        List<V> list = headers.getAll(k);
        return new AbstractList<String>(list){
            final List val$allNames;
            {
                this.val$allNames = list;
            }

            @Override
            public String get(int n) {
                Object e = this.val$allNames.get(n);
                return e != null ? e.toString() : null;
            }

            @Override
            public int size() {
                return this.val$allNames.size();
            }

            @Override
            public Object get(int n) {
                return this.get(n);
            }
        };
    }

    public static <K, V> String getAsString(Headers<K, V, ?> headers, K k) {
        V v = headers.get(k);
        return v != null ? v.toString() : null;
    }

    public static Iterator<Map.Entry<String, String>> iteratorAsString(Iterable<Map.Entry<CharSequence, CharSequence>> iterable) {
        return new StringEntryIterator(iterable.iterator());
    }

    public static <K, V> String toString(Class<?> clazz, Iterator<Map.Entry<K, V>> iterator2, int n) {
        String string = clazz.getSimpleName();
        if (n == 0) {
            return string + "[]";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + 2 + n * 20).append(string).append('[');
        while (iterator2.hasNext()) {
            Map.Entry<K, V> entry = iterator2.next();
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.append(']').toString();
    }

    public static Set<String> namesAsString(Headers<CharSequence, CharSequence, ?> headers) {
        return new CharSequenceDelegatingStringSet(headers.names());
    }

    private static abstract class DelegatingStringSet<T>
    extends AbstractCollection<String>
    implements Set<String> {
        protected final Set<T> allNames;

        DelegatingStringSet(Set<T> set) {
            this.allNames = ObjectUtil.checkNotNull(set, "allNames");
        }

        @Override
        public int size() {
            return this.allNames.size();
        }

        @Override
        public boolean isEmpty() {
            return this.allNames.isEmpty();
        }

        @Override
        public boolean contains(Object object) {
            return this.allNames.contains(object.toString());
        }

        @Override
        public Iterator<String> iterator() {
            return new StringIterator<T>(this.allNames.iterator());
        }

        @Override
        public boolean remove(Object object) {
            return this.allNames.remove(object);
        }

        @Override
        public void clear() {
            this.allNames.clear();
        }
    }

    private static final class CharSequenceDelegatingStringSet
    extends DelegatingStringSet<CharSequence> {
        CharSequenceDelegatingStringSet(Set<CharSequence> set) {
            super(set);
        }

        @Override
        public boolean add(String string) {
            return this.allNames.add(string);
        }

        @Override
        public boolean addAll(Collection<? extends String> collection) {
            return this.allNames.addAll(collection);
        }

        @Override
        public boolean add(Object object) {
            return this.add((String)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class StringIterator<T>
    implements Iterator<String> {
        private final Iterator<T> iter;

        StringIterator(Iterator<T> iterator2) {
            this.iter = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public String next() {
            T t = this.iter.next();
            return t != null ? t.toString() : null;
        }

        @Override
        public void remove() {
            this.iter.remove();
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class StringEntry
    implements Map.Entry<String, String> {
        private final Map.Entry<CharSequence, CharSequence> entry;
        private String name;
        private String value;

        StringEntry(Map.Entry<CharSequence, CharSequence> entry) {
            this.entry = entry;
        }

        @Override
        public String getKey() {
            if (this.name == null) {
                this.name = this.entry.getKey().toString();
            }
            return this.name;
        }

        @Override
        public String getValue() {
            if (this.value == null && this.entry.getValue() != null) {
                this.value = this.entry.getValue().toString();
            }
            return this.value;
        }

        @Override
        public String setValue(String string) {
            String string2 = this.getValue();
            this.entry.setValue(string);
            return string2;
        }

        public String toString() {
            return this.entry.toString();
        }

        @Override
        public Object setValue(Object object) {
            return this.setValue((String)object);
        }

        @Override
        public Object getValue() {
            return this.getValue();
        }

        @Override
        public Object getKey() {
            return this.getKey();
        }
    }

    private static final class StringEntryIterator
    implements Iterator<Map.Entry<String, String>> {
        private final Iterator<Map.Entry<CharSequence, CharSequence>> iter;

        StringEntryIterator(Iterator<Map.Entry<CharSequence, CharSequence>> iterator2) {
            this.iter = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public Map.Entry<String, String> next() {
            return new StringEntry(this.iter.next());
        }

        @Override
        public void remove() {
            this.iter.remove();
        }

        @Override
        public Object next() {
            return this.next();
        }
    }
}

