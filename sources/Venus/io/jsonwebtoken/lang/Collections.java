/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Objects;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public final class Collections {
    private Collections() {
    }

    public static <T> List<T> emptyList() {
        return java.util.Collections.emptyList();
    }

    public static <T> Set<T> emptySet() {
        return java.util.Collections.emptySet();
    }

    public static <K, V> Map<K, V> emptyMap() {
        return java.util.Collections.emptyMap();
    }

    @SafeVarargs
    public static <T> List<T> of(T ... TArray) {
        if (TArray == null || TArray.length == 0) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(java.util.Arrays.asList(TArray));
    }

    public static <T> Set<T> asSet(Collection<T> collection) {
        if (collection instanceof Set) {
            return (Set)collection;
        }
        if (Collections.isEmpty(collection)) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableSet(new LinkedHashSet<T>(collection));
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T ... TArray) {
        if (TArray == null || TArray.length == 0) {
            return java.util.Collections.emptySet();
        }
        LinkedHashSet<T> linkedHashSet = new LinkedHashSet<T>(java.util.Arrays.asList(TArray));
        return Collections.immutable(linkedHashSet);
    }

    public static <K, V> Map<K, V> immutable(Map<K, V> map) {
        return map != null ? java.util.Collections.unmodifiableMap(map) : null;
    }

    public static <T> Set<T> immutable(Set<T> set) {
        return set != null ? java.util.Collections.unmodifiableSet(set) : null;
    }

    public static <T> List<T> immutable(List<T> list) {
        return list != null ? java.util.Collections.unmodifiableList(list) : null;
    }

    public static <T, C extends Collection<T>> C immutable(C c) {
        if (c == null) {
            return null;
        }
        if (c instanceof Set) {
            return (C)java.util.Collections.unmodifiableSet((Set)c);
        }
        if (c instanceof List) {
            return (C)java.util.Collections.unmodifiableList((List)c);
        }
        return (C)java.util.Collections.unmodifiableCollection(c);
    }

    public static <T> Set<T> nullSafe(Set<T> set) {
        return set == null ? Collections.emptySet() : set;
    }

    public static <T> Collection<T> nullSafe(Collection<T> collection) {
        return collection == null ? Collections.emptyList() : collection;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return Collections.size(collection) == 0;
    }

    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    public static int size(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return Collections.size(map) == 0;
    }

    public static List arrayToList(Object object) {
        return java.util.Arrays.asList(Objects.toObjectArray(object));
    }

    @SafeVarargs
    public static <T> Set<T> concat(Set<T> set, T ... TArray) {
        int n = Math.max(1, Collections.size(set) + Arrays.length(TArray));
        LinkedHashSet<T> linkedHashSet = new LinkedHashSet<T>(n);
        linkedHashSet.addAll(set);
        java.util.Collections.addAll(linkedHashSet, TArray);
        return Collections.immutable(linkedHashSet);
    }

    public static void mergeArrayIntoCollection(Object object, Collection collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null");
        }
        Object[] objectArray = Objects.toObjectArray(object);
        java.util.Collections.addAll(collection, objectArray);
    }

    public static void mergePropertiesIntoMap(Properties properties, Map map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null");
        }
        if (properties != null) {
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                String string2 = properties.getProperty(string);
                if (string2 == null) {
                    string2 = properties.get(string);
                }
                map.put(string, string2);
            }
        }
    }

    public static boolean contains(Iterator iterator2, Object object) {
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                Object e = iterator2.next();
                if (!Objects.nullSafeEquals(e, object)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean contains(Enumeration enumeration, Object object) {
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Object e = enumeration.nextElement();
                if (!Objects.nullSafeEquals(e, object)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean containsInstance(Collection collection, Object object) {
        if (collection != null) {
            for (Object e : collection) {
                if (e != object) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean containsAny(Collection collection, Collection collection2) {
        if (Collections.isEmpty(collection) || Collections.isEmpty(collection2)) {
            return true;
        }
        for (Object e : collection2) {
            if (!collection.contains(e)) continue;
            return false;
        }
        return true;
    }

    public static Object findFirstMatch(Collection collection, Collection collection2) {
        if (Collections.isEmpty(collection) || Collections.isEmpty(collection2)) {
            return null;
        }
        for (Object e : collection2) {
            if (!collection.contains(e)) continue;
            return e;
        }
        return null;
    }

    public static <T> T findValueOfType(Collection<?> collection, Class<T> clazz) {
        if (Collections.isEmpty(collection)) {
            return null;
        }
        T t = null;
        for (Object obj : collection) {
            if (clazz != null && !clazz.isInstance(obj)) continue;
            if (t != null) {
                return null;
            }
            t = (T)obj;
        }
        return t;
    }

    public static Object findValueOfType(Collection<?> collection, Class<?>[] classArray) {
        if (Collections.isEmpty(collection) || Objects.isEmpty(classArray)) {
            return null;
        }
        for (Class<?> clazz : classArray) {
            Object obj = Collections.findValueOfType(collection, clazz);
            if (obj == null) continue;
            return obj;
        }
        return null;
    }

    public static boolean hasUniqueObject(Collection collection) {
        if (Collections.isEmpty(collection)) {
            return true;
        }
        boolean bl = false;
        Object var2_2 = null;
        for (Object e : collection) {
            if (!bl) {
                bl = true;
                var2_2 = e;
                continue;
            }
            if (var2_2 == e) continue;
            return true;
        }
        return false;
    }

    public static Class<?> findCommonElementType(Collection collection) {
        if (Collections.isEmpty(collection)) {
            return null;
        }
        Class<?> clazz = null;
        for (Object e : collection) {
            if (e == null) continue;
            if (clazz == null) {
                clazz = e.getClass();
                continue;
            }
            if (clazz == e.getClass()) continue;
            return null;
        }
        return clazz;
    }

    public static <A, E extends A> A[] toArray(Enumeration<E> enumeration, A[] AArray) {
        ArrayList<E> arrayList = new ArrayList<E>();
        while (enumeration.hasMoreElements()) {
            arrayList.add(enumeration.nextElement());
        }
        return arrayList.toArray(AArray);
    }

    public static <E> Iterator<E> toIterator(Enumeration<E> enumeration) {
        return new EnumerationIterator<E>(enumeration);
    }

    private static class EnumerationIterator<E>
    implements Iterator<E> {
        private final Enumeration<E> enumeration;

        public EnumerationIterator(Enumeration<E> enumeration) {
            this.enumeration = enumeration;
        }

        @Override
        public boolean hasNext() {
            return this.enumeration.hasMoreElements();
        }

        @Override
        public E next() {
            return this.enumeration.nextElement();
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Not supported");
        }
    }
}

