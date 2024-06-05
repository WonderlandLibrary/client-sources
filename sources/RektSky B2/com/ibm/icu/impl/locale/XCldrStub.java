package com.ibm.icu.impl.locale;

import java.util.*;
import java.nio.charset.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.net.*;
import java.util.regex.*;

public class XCldrStub
{
    public static <T> String join(final T[] source, final String separator) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length; ++i) {
            if (i != 0) {
                result.append(separator);
            }
            result.append(source[i]);
        }
        return result.toString();
    }
    
    public static <T> String join(final Iterable<T> source, final String separator) {
        final StringBuilder result = new StringBuilder();
        boolean first = true;
        for (final T item : source) {
            if (!first) {
                result.append(separator);
            }
            else {
                first = false;
            }
            result.append(item.toString());
        }
        return result.toString();
    }
    
    public static class Multimap<K, V>
    {
        private final Map<K, Set<V>> map;
        private final Class<Set<V>> setClass;
        
        private Multimap(final Map<K, Set<V>> map, final Class<?> setClass) {
            this.map = map;
            this.setClass = (Class<Set<V>>)((setClass != null) ? setClass : HashSet.class);
        }
        
        public Multimap<K, V> putAll(final K key, final V... values) {
            if (values.length != 0) {
                this.createSetIfMissing(key).addAll((Collection<? extends V>)Arrays.asList(values));
            }
            return this;
        }
        
        public void putAll(final K key, final Collection<V> values) {
            if (!values.isEmpty()) {
                this.createSetIfMissing(key).addAll((Collection<? extends V>)values);
            }
        }
        
        public void putAll(final Collection<K> keys, final V value) {
            for (final K key : keys) {
                this.put(key, value);
            }
        }
        
        public void putAll(final Multimap<K, V> source) {
            for (final Map.Entry<K, Set<V>> entry : source.map.entrySet()) {
                this.putAll(entry.getKey(), entry.getValue());
            }
        }
        
        public void put(final K key, final V value) {
            this.createSetIfMissing(key).add(value);
        }
        
        private Set<V> createSetIfMissing(final K key) {
            Set<V> old = this.map.get(key);
            if (old == null) {
                this.map.put(key, old = this.getInstance());
            }
            return old;
        }
        
        private Set<V> getInstance() {
            try {
                return this.setClass.newInstance();
            }
            catch (Exception e) {
                throw new ICUException(e);
            }
        }
        
        public Set<V> get(final K key) {
            final Set<V> result = this.map.get(key);
            return result;
        }
        
        public Set<K> keySet() {
            return this.map.keySet();
        }
        
        public Map<K, Set<V>> asMap() {
            return this.map;
        }
        
        public Set<V> values() {
            final Collection<Set<V>> values = this.map.values();
            if (values.size() == 0) {
                return Collections.emptySet();
            }
            final Set<V> result = this.getInstance();
            for (final Set<V> valueSet : values) {
                result.addAll((Collection<? extends V>)valueSet);
            }
            return result;
        }
        
        public int size() {
            return this.map.size();
        }
        
        public Iterable<Map.Entry<K, V>> entries() {
            return new MultimapIterator<K, V>((Map)this.map);
        }
        
        @Override
        public boolean equals(final Object obj) {
            return this == obj || (obj != null && obj.getClass() == this.getClass() && this.map.equals(((Multimap)obj).map));
        }
        
        @Override
        public int hashCode() {
            return this.map.hashCode();
        }
    }
    
    public static class Multimaps
    {
        public static <K, V, R extends Multimap<K, V>> R invertFrom(final Multimap<V, K> source, final R target) {
            for (final Map.Entry<V, Set<K>> entry : source.asMap().entrySet()) {
                target.putAll(entry.getValue(), entry.getKey());
            }
            return target;
        }
        
        public static <K, V, R extends Multimap<K, V>> R invertFrom(final Map<V, K> source, final R target) {
            for (final Map.Entry<V, K> entry : source.entrySet()) {
                target.put(entry.getValue(), entry.getKey());
            }
            return target;
        }
        
        public static <K, V> Map<K, V> forMap(final Map<K, V> map) {
            return map;
        }
    }
    
    private static class MultimapIterator<K, V> implements Iterator<Map.Entry<K, V>>, Iterable<Map.Entry<K, V>>
    {
        private final Iterator<Map.Entry<K, Set<V>>> it1;
        private Iterator<V> it2;
        private final ReusableEntry<K, V> entry;
        
        private MultimapIterator(final Map<K, Set<V>> map) {
            this.it2 = null;
            this.entry = new ReusableEntry<K, V>();
            this.it1 = map.entrySet().iterator();
        }
        
        @Override
        public boolean hasNext() {
            return this.it1.hasNext() || (this.it2 != null && this.it2.hasNext());
        }
        
        @Override
        public Map.Entry<K, V> next() {
            if (this.it2 != null && this.it2.hasNext()) {
                this.entry.value = this.it2.next();
            }
            else {
                final Map.Entry<K, Set<V>> e = this.it1.next();
                this.entry.key = e.getKey();
                this.it2 = e.getValue().iterator();
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
    }
    
    private static class ReusableEntry<K, V> implements Map.Entry<K, V>
    {
        K key;
        V value;
        
        @Override
        public K getKey() {
            return this.key;
        }
        
        @Override
        public V getValue() {
            return this.value;
        }
        
        @Override
        public V setValue(final V value) {
            throw new UnsupportedOperationException();
        }
    }
    
    public static class HashMultimap<K, V> extends Multimap<K, V>
    {
        private HashMultimap() {
            super((Map)new HashMap(), (Class)HashSet.class);
        }
        
        public static <K, V> HashMultimap<K, V> create() {
            return new HashMultimap<K, V>();
        }
    }
    
    public static class TreeMultimap<K, V> extends Multimap<K, V>
    {
        private TreeMultimap() {
            super((Map)new TreeMap(), (Class)TreeSet.class);
        }
        
        public static <K, V> TreeMultimap<K, V> create() {
            return new TreeMultimap<K, V>();
        }
    }
    
    public static class LinkedHashMultimap<K, V> extends Multimap<K, V>
    {
        private LinkedHashMultimap() {
            super((Map)new LinkedHashMap(), (Class)LinkedHashSet.class);
        }
        
        public static <K, V> LinkedHashMultimap<K, V> create() {
            return new LinkedHashMultimap<K, V>();
        }
    }
    
    public static class CollectionUtilities
    {
        public static <T, U extends Iterable<T>> String join(final U source, final String separator) {
            return XCldrStub.join((Iterable<Object>)source, separator);
        }
    }
    
    public static class Joiner
    {
        private final String separator;
        
        private Joiner(final String separator) {
            this.separator = separator;
        }
        
        public static final Joiner on(final String separator) {
            return new Joiner(separator);
        }
        
        public <T> String join(final T[] source) {
            return XCldrStub.join(source, this.separator);
        }
        
        public <T> String join(final Iterable<T> source) {
            return XCldrStub.join(source, this.separator);
        }
    }
    
    public static class Splitter
    {
        Pattern pattern;
        boolean trimResults;
        
        public Splitter(final char c) {
            this(Pattern.compile("\\Q" + c + "\\E"));
        }
        
        public Splitter(final Pattern p) {
            this.trimResults = false;
            this.pattern = p;
        }
        
        public static Splitter on(final char c) {
            return new Splitter(c);
        }
        
        public static Splitter on(final Pattern p) {
            return new Splitter(p);
        }
        
        public List<String> splitToList(final String input) {
            final String[] items = this.pattern.split(input);
            if (this.trimResults) {
                for (int i = 0; i < items.length; ++i) {
                    items[i] = items[i].trim();
                }
            }
            return Arrays.asList(items);
        }
        
        public Splitter trimResults() {
            this.trimResults = true;
            return this;
        }
        
        public Iterable<String> split(final String input) {
            return this.splitToList(input);
        }
    }
    
    public static class ImmutableSet
    {
        public static <T> Set<T> copyOf(final Set<T> values) {
            return Collections.unmodifiableSet((Set<? extends T>)new LinkedHashSet<T>((Collection<? extends T>)values));
        }
    }
    
    public static class ImmutableMap
    {
        public static <K, V> Map<K, V> copyOf(final Map<K, V> values) {
            return Collections.unmodifiableMap((Map<? extends K, ? extends V>)new LinkedHashMap<K, V>((Map<? extends K, ? extends V>)values));
        }
    }
    
    public static class ImmutableMultimap
    {
        public static <K, V> Multimap<K, V> copyOf(final Multimap<K, V> values) {
            final LinkedHashMap<K, Set<V>> temp = new LinkedHashMap<K, Set<V>>();
            for (final Map.Entry<K, Set<V>> entry : values.asMap().entrySet()) {
                final Set<V> value = entry.getValue();
                temp.put(entry.getKey(), (value.size() == 1) ? Collections.singleton(value.iterator().next()) : Collections.unmodifiableSet((Set<? extends V>)new LinkedHashSet<V>((Collection<? extends V>)value)));
            }
            return new Multimap<K, V>((Map)Collections.unmodifiableMap((Map<?, ?>)temp), (Class)null);
        }
    }
    
    public static class FileUtilities
    {
        public static final Charset UTF8;
        
        public static BufferedReader openFile(final Class<?> class1, final String file) {
            return openFile(class1, file, FileUtilities.UTF8);
        }
        
        public static BufferedReader openFile(final Class<?> class1, final String file, Charset charset) {
            try {
                final InputStream resourceAsStream = class1.getResourceAsStream(file);
                if (charset == null) {
                    charset = FileUtilities.UTF8;
                }
                final InputStreamReader reader = new InputStreamReader(resourceAsStream, charset);
                final BufferedReader bufferedReader = new BufferedReader(reader, 65536);
                return bufferedReader;
            }
            catch (Exception e) {
                final String className = (class1 == null) ? null : class1.getCanonicalName();
                String canonicalName = null;
                try {
                    final String relativeFileName = getRelativeFileName(class1, "../util/");
                    canonicalName = new File(relativeFileName).getCanonicalPath();
                }
                catch (Exception e2) {
                    throw new ICUUncheckedIOException("Couldn't open file: " + file + "; relative to class: " + className, e);
                }
                throw new ICUUncheckedIOException("Couldn't open file " + file + "; in path " + canonicalName + "; relative to class: " + className, e);
            }
        }
        
        public static String getRelativeFileName(final Class<?> class1, final String filename) {
            final URL resource = (class1 == null) ? FileUtilities.class.getResource(filename) : class1.getResource(filename);
            final String resourceString = resource.toString();
            if (resourceString.startsWith("file:")) {
                return resourceString.substring(5);
            }
            if (resourceString.startsWith("jar:file:")) {
                return resourceString.substring(9);
            }
            throw new ICUUncheckedIOException("File not found: " + resourceString);
        }
        
        static {
            UTF8 = Charset.forName("utf-8");
        }
    }
    
    public static class RegexUtilities
    {
        public static int findMismatch(final Matcher m, final CharSequence s) {
            int i;
            for (i = 1; i < s.length(); ++i) {
                final boolean matches = m.reset(s.subSequence(0, i)).matches();
                if (!matches && !m.hitEnd()) {
                    break;
                }
            }
            return i - 1;
        }
        
        public static String showMismatch(final Matcher m, final CharSequence s) {
            final int failPoint = findMismatch(m, s);
            final String show = (Object)s.subSequence(0, failPoint) + "\u2639" + (Object)s.subSequence(failPoint, s.length());
            return show;
        }
    }
    
    public interface Predicate<T>
    {
        boolean test(final T p0);
    }
}
