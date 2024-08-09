/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@GwtIncompatible
final class Serialization {
    private Serialization() {
    }

    static int readCount(ObjectInputStream objectInputStream) throws IOException {
        return objectInputStream.readInt();
    }

    static <K, V> void writeMap(Map<K, V> map, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    static <K, V> void populateMap(Map<K, V> map, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int n = objectInputStream.readInt();
        Serialization.populateMap(map, objectInputStream, n);
    }

    static <K, V> void populateMap(Map<K, V> map, ObjectInputStream objectInputStream, int n) throws IOException, ClassNotFoundException {
        for (int i = 0; i < n; ++i) {
            Object object = objectInputStream.readObject();
            Object object2 = objectInputStream.readObject();
            map.put(object, object2);
        }
    }

    static <E> void writeMultiset(Multiset<E> multiset, ObjectOutputStream objectOutputStream) throws IOException {
        int n = multiset.entrySet().size();
        objectOutputStream.writeInt(n);
        for (Multiset.Entry<E> entry : multiset.entrySet()) {
            objectOutputStream.writeObject(entry.getElement());
            objectOutputStream.writeInt(entry.getCount());
        }
    }

    static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int n = objectInputStream.readInt();
        Serialization.populateMultiset(multiset, objectInputStream, n);
    }

    static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream objectInputStream, int n) throws IOException, ClassNotFoundException {
        for (int i = 0; i < n; ++i) {
            Object object = objectInputStream.readObject();
            int n2 = objectInputStream.readInt();
            multiset.add(object, n2);
        }
    }

    static <K, V> void writeMultimap(Multimap<K, V> multimap, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(multimap.asMap().size());
        for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeInt(entry.getValue().size());
            for (V v : entry.getValue()) {
                objectOutputStream.writeObject(v);
            }
        }
    }

    static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int n = objectInputStream.readInt();
        Serialization.populateMultimap(multimap, objectInputStream, n);
    }

    static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream objectInputStream, int n) throws IOException, ClassNotFoundException {
        for (int i = 0; i < n; ++i) {
            Object object = objectInputStream.readObject();
            Collection<V> collection = multimap.get(object);
            int n2 = objectInputStream.readInt();
            for (int j = 0; j < n2; ++j) {
                Object object2 = objectInputStream.readObject();
                collection.add(object2);
            }
        }
    }

    static <T> FieldSetter<T> getFieldSetter(Class<T> clazz, String string) {
        try {
            Field field = clazz.getDeclaredField(string);
            return new FieldSetter(field, null);
        } catch (NoSuchFieldException noSuchFieldException) {
            throw new AssertionError((Object)noSuchFieldException);
        }
    }

    static final class FieldSetter<T> {
        private final Field field;

        private FieldSetter(Field field) {
            this.field = field;
            field.setAccessible(false);
        }

        void set(T t, Object object) {
            try {
                this.field.set(t, object);
            } catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError((Object)illegalAccessException);
            }
        }

        void set(T t, int n) {
            try {
                this.field.set(t, n);
            } catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError((Object)illegalAccessException);
            }
        }

        FieldSetter(Field field, 1 var2_2) {
            this(field);
        }
    }
}

