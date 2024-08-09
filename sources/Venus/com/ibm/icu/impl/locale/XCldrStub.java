/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XCldrStub {
    public static <T> String join(T[] TArray, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < TArray.length; ++i) {
            if (i != 0) {
                stringBuilder.append(string);
            }
            stringBuilder.append(TArray[i]);
        }
        return stringBuilder.toString();
    }

    public static <T> String join(Iterable<T> iterable, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = true;
        for (T t : iterable) {
            if (!bl) {
                stringBuilder.append(string);
            } else {
                bl = false;
            }
            stringBuilder.append(t.toString());
        }
        return stringBuilder.toString();
    }

    public static interface Predicate<T> {
        public boolean test(T var1);
    }

    public static class RegexUtilities {
        public static int findMismatch(Matcher matcher, CharSequence charSequence) {
            boolean bl;
            int n;
            for (n = 1; n < charSequence.length() && ((bl = matcher.reset(charSequence.subSequence(0, n)).matches()) || matcher.hitEnd()); ++n) {
            }
            return n - 1;
        }

        public static String showMismatch(Matcher matcher, CharSequence charSequence) {
            int n = RegexUtilities.findMismatch(matcher, charSequence);
            String string = charSequence.subSequence(0, n) + "\u2639" + charSequence.subSequence(n, charSequence.length());
            return string;
        }
    }

    public static class FileUtilities {
        public static final Charset UTF8 = Charset.forName("utf-8");

        public static BufferedReader openFile(Class<?> clazz, String string) {
            return FileUtilities.openFile(clazz, string, UTF8);
        }

        public static BufferedReader openFile(Class<?> clazz, String string, Charset charset) {
            try {
                InputStream inputStream = clazz.getResourceAsStream(string);
                if (charset == null) {
                    charset = UTF8;
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 65536);
                return bufferedReader;
            } catch (Exception exception) {
                String string2 = clazz == null ? null : clazz.getCanonicalName();
                String string3 = null;
                try {
                    String string4 = FileUtilities.getRelativeFileName(clazz, "../util/");
                    string3 = new File(string4).getCanonicalPath();
                } catch (Exception exception2) {
                    throw new ICUUncheckedIOException("Couldn't open file: " + string + "; relative to class: " + string2, exception);
                }
                throw new ICUUncheckedIOException("Couldn't open file " + string + "; in path " + string3 + "; relative to class: " + string2, exception);
            }
        }

        public static String getRelativeFileName(Class<?> clazz, String string) {
            URL uRL = clazz == null ? FileUtilities.class.getResource(string) : clazz.getResource(string);
            String string2 = uRL.toString();
            if (string2.startsWith("file:")) {
                return string2.substring(5);
            }
            if (string2.startsWith("jar:file:")) {
                return string2.substring(9);
            }
            throw new ICUUncheckedIOException("File not found: " + string2);
        }
    }

    public static class ImmutableMultimap {
        public static <K, V> Multimap<K, V> copyOf(Multimap<K, V> multimap) {
            LinkedHashMap<K, Set<V>> linkedHashMap = new LinkedHashMap<K, Set<V>>();
            for (Map.Entry<K, Set<V>> entry : multimap.asMap().entrySet()) {
                Set<V> set = entry.getValue();
                linkedHashMap.put(entry.getKey(), set.size() == 1 ? Collections.singleton(set.iterator().next()) : Collections.unmodifiableSet(new LinkedHashSet<V>(set)));
            }
            return new Multimap(Collections.unmodifiableMap(linkedHashMap), null, null);
        }
    }

    public static class ImmutableMap {
        public static <K, V> Map<K, V> copyOf(Map<K, V> map) {
            return Collections.unmodifiableMap(new LinkedHashMap<K, V>(map));
        }
    }

    public static class ImmutableSet {
        public static <T> Set<T> copyOf(Set<T> set) {
            return Collections.unmodifiableSet(new LinkedHashSet<T>(set));
        }
    }

    public static class Splitter {
        Pattern pattern;
        boolean trimResults = false;

        public Splitter(char c) {
            this(Pattern.compile("\\Q" + c + "\\E"));
        }

        public Splitter(Pattern pattern) {
            this.pattern = pattern;
        }

        public static Splitter on(char c) {
            return new Splitter(c);
        }

        public static Splitter on(Pattern pattern) {
            return new Splitter(pattern);
        }

        public List<String> splitToList(String string) {
            String[] stringArray = this.pattern.split(string);
            if (this.trimResults) {
                for (int i = 0; i < stringArray.length; ++i) {
                    stringArray[i] = stringArray[i].trim();
                }
            }
            return Arrays.asList(stringArray);
        }

        public Splitter trimResults() {
            this.trimResults = true;
            return this;
        }

        public Iterable<String> split(String string) {
            return this.splitToList(string);
        }
    }

    public static class Joiner {
        private final String separator;

        private Joiner(String string) {
            this.separator = string;
        }

        public static final Joiner on(String string) {
            return new Joiner(string);
        }

        public <T> String join(T[] TArray) {
            return XCldrStub.join(TArray, this.separator);
        }

        public <T> String join(Iterable<T> iterable) {
            return XCldrStub.join(iterable, this.separator);
        }
    }

    public static class CollectionUtilities {
        public static <T, U extends Iterable<T>> String join(U u, String string) {
            return XCldrStub.join(u, string);
        }
    }

    public static class LinkedHashMultimap<K, V>
    extends Multimap<K, V> {
        private LinkedHashMultimap() {
            super(new LinkedHashMap(), LinkedHashSet.class, null);
        }

        public static <K, V> LinkedHashMultimap<K, V> create() {
            return new LinkedHashMultimap<K, V>();
        }
    }

    public static class TreeMultimap<K, V>
    extends Multimap<K, V> {
        private TreeMultimap() {
            super(new TreeMap(), TreeSet.class, null);
        }

        public static <K, V> TreeMultimap<K, V> create() {
            return new TreeMultimap<K, V>();
        }
    }

    public static class HashMultimap<K, V>
    extends Multimap<K, V> {
        private HashMultimap() {
            super(new HashMap(), HashSet.class, null);
        }

        public static <K, V> HashMultimap<K, V> create() {
            return new HashMultimap<K, V>();
        }
    }

    private static class ReusableEntry<K, V>
    implements Map.Entry<K, V> {
        K key;
        V value;

        private ReusableEntry() {
        }

        @Override
        public K getKey() {
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

        ReusableEntry(1 var1_1) {
            this();
        }
    }

    private static class MultimapIterator<K, V>
    implements Iterator<Map.Entry<K, V>>,
    Iterable<Map.Entry<K, V>> {
        private final Iterator<Map.Entry<K, Set<V>>> it1;
        private Iterator<V> it2 = null;
        private final ReusableEntry<K, V> entry = new ReusableEntry(null);

        private MultimapIterator(Map<K, Set<V>> map) {
            this.it1 = map.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return this.it1.hasNext() || this.it2 != null && this.it2.hasNext();
        }

        @Override
        public Map.Entry<K, V> next() {
            if (this.it2 != null && this.it2.hasNext()) {
                this.entry.value = this.it2.next();
            } else {
                Map.Entry<K, Set<V>> entry = this.it1.next();
                this.entry.key = entry.getKey();
                this.it2 = entry.getValue().iterator();
            }
            return this.entry;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return this;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object next() {
            return this.next();
        }

        MultimapIterator(Map map, 1 var2_2) {
            this(map);
        }
    }

    public static class Multimaps {
        public static <K, V, R extends Multimap<K, V>> R invertFrom(Multimap<V, K> multimap, R r) {
            for (Map.Entry<V, Set<K>> entry : multimap.asMap().entrySet()) {
                r.putAll((Collection)entry.getValue(), entry.getKey());
            }
            return r;
        }

        public static <K, V, R extends Multimap<K, V>> R invertFrom(Map<V, K> map, R r) {
            for (Map.Entry<V, K> entry : map.entrySet()) {
                r.put(entry.getValue(), entry.getKey());
            }
            return r;
        }

        public static <K, V> Map<K, V> forMap(Map<K, V> map) {
            return map;
        }
    }

    public static class Multimap<K, V> {
        private final Map<K, Set<V>> map;
        private final Class<Set<V>> setClass;

        private Multimap(Map<K, Set<V>> map, Class<?> clazz) {
            this.map = map;
            this.setClass = clazz != null ? clazz : HashSet.class;
        }

        @SafeVarargs
        public final Multimap<K, V> putAll(K k, V ... VArray) {
            if (VArray.length != 0) {
                this.createSetIfMissing(k).addAll(Arrays.asList(VArray));
            }
            return this;
        }

        public void putAll(K k, Collection<V> collection) {
            if (!collection.isEmpty()) {
                this.createSetIfMissing(k).addAll(collection);
            }
        }

        public void putAll(Collection<K> collection, V v) {
            for (K k : collection) {
                this.put(k, v);
            }
        }

        public void putAll(Multimap<K, V> multimap) {
            for (Map.Entry<K, Set<V>> entry : multimap.map.entrySet()) {
                this.putAll(entry.getKey(), (Collection)entry.getValue());
            }
        }

        public void put(K k, V v) {
            this.createSetIfMissing(k).add(v);
        }

        private Set<V> createSetIfMissing(K k) {
            Set<V> set = this.map.get(k);
            if (set == null) {
                set = this.getInstance();
                this.map.put(k, set);
            }
            return set;
        }

        private Set<V> getInstance() {
            try {
                return this.setClass.newInstance();
            } catch (Exception exception) {
                throw new ICUException(exception);
            }
        }

        public Set<V> get(K k) {
            Set<V> set = this.map.get(k);
            return set;
        }

        public Set<K> keySet() {
            return this.map.keySet();
        }

        public Map<K, Set<V>> asMap() {
            return this.map;
        }

        public Set<V> values() {
            Collection<Set<V>> collection = this.map.values();
            if (collection.size() == 0) {
                return Collections.emptySet();
            }
            Set<V> set = this.getInstance();
            for (Set<V> set2 : collection) {
                set.addAll(set2);
            }
            return set;
        }

        public int size() {
            return this.map.size();
        }

        public Iterable<Map.Entry<K, V>> entries() {
            return new MultimapIterator(this.map, null);
        }

        public boolean equals(Object object) {
            return this == object || object != null && object.getClass() == this.getClass() && this.map.equals(((Multimap)object).map);
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        Multimap(Map map, Class clazz, 1 var3_3) {
            this(map, clazz);
        }
    }
}

