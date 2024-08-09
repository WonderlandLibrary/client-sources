/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap$EntrySet.EntrySetSpliterator
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap$KeySet.KeySetSpliterator
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap$ValuesCollection.ValuesSpliterator
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Object2ObjectArrayMap<K, V>
extends AbstractObject2ObjectMap<K, V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    protected transient Object[] key;
    protected transient Object[] value;
    protected int size;
    protected transient Object2ObjectMap.FastEntrySet<K, V> entries;
    protected transient ObjectSet<K> keys;
    protected transient ObjectCollection<V> values;

    public Object2ObjectArrayMap(Object[] objectArray, Object[] objectArray2) {
        this.key = objectArray;
        this.value = objectArray2;
        this.size = objectArray.length;
        if (objectArray.length != objectArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + objectArray2.length + ")");
        }
    }

    public Object2ObjectArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Object2ObjectArrayMap(int n) {
        this.key = new Object[n];
        this.value = new Object[n];
    }

    public Object2ObjectArrayMap(Object2ObjectMap<K, V> object2ObjectMap) {
        this(object2ObjectMap.size());
        int n = 0;
        for (Object2ObjectMap.Entry entry : object2ObjectMap.object2ObjectEntrySet()) {
            this.key[n] = entry.getKey();
            this.value[n] = entry.getValue();
            ++n;
        }
        this.size = n;
    }

    public Object2ObjectArrayMap(Map<? extends K, ? extends V> map) {
        this(map.size());
        int n = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.key[n] = entry.getKey();
            this.value[n] = entry.getValue();
            ++n;
        }
        this.size = n;
    }

    public Object2ObjectArrayMap(Object[] objectArray, Object[] objectArray2, int n) {
        this.key = objectArray;
        this.value = objectArray2;
        this.size = n;
        if (objectArray.length != objectArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + objectArray2.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new EntrySet(this, null);
        }
        return this.entries;
    }

    private int findKey(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(objectArray[n], object)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public V get(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(objectArray[n], object)) continue;
            return (V)this.value[n];
        }
        return (V)this.defRetValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        int n = this.size;
        while (n-- != 0) {
            this.key[n] = null;
            this.value[n] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.findKey(object) != -1;
    }

    @Override
    public boolean containsValue(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(this.value[n], object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public V put(K k, V v) {
        int n = this.findKey(k);
        if (n != -1) {
            Object object = this.value[n];
            this.value[n] = v;
            return (V)object;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            Object[] objectArray2 = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.key[n2];
                objectArray2[n2] = this.value[n2];
            }
            this.key = objectArray;
            this.value = objectArray2;
        }
        this.key[this.size] = k;
        this.value[this.size] = v;
        ++this.size;
        return (V)this.defRetValue;
    }

    @Override
    public V remove(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return (V)this.defRetValue;
        }
        Object object2 = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.key[this.size] = null;
        this.value[this.size] = null;
        return (V)object2;
    }

    @Override
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new ValuesCollection(this, null);
        }
        return this.values;
    }

    public Object2ObjectArrayMap<K, V> clone() {
        Object2ObjectArrayMap object2ObjectArrayMap;
        try {
            object2ObjectArrayMap = (Object2ObjectArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2ObjectArrayMap.key = (Object[])this.key.clone();
        object2ObjectArrayMap.value = (Object[])this.value.clone();
        object2ObjectArrayMap.entries = null;
        object2ObjectArrayMap.keys = null;
        object2ObjectArrayMap.values = null;
        return object2ObjectArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        for (int i = 0; i < n; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readObject();
        }
    }

    @Override
    public ObjectSet object2ObjectEntrySet() {
        return this.object2ObjectEntrySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(Object2ObjectArrayMap object2ObjectArrayMap, Object object) {
        return object2ObjectArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>>
    implements Object2ObjectMap.FastEntrySet<K, V> {
        final Object2ObjectArrayMap this$0;

        private EntrySet(Object2ObjectArrayMap object2ObjectArrayMap) {
            this.this$0 = object2ObjectArrayMap;
        }

        @Override
        public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
            return new ObjectIterator<Object2ObjectMap.Entry<K, V>>(this){
                int curr;
                int next;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.curr = -1;
                    this.next = 0;
                }

                @Override
                public boolean hasNext() {
                    return this.next < this.this$1.this$0.size;
                }

                @Override
                public Object2ObjectMap.Entry<K, V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractObject2ObjectMap.BasicEntry<Object, Object>(this.this$1.this$0.key[this.curr], this.this$1.this$0.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = this.this$1.this$0.size-- - this.next--;
                    System.arraycopy(this.this$1.this$0.key, this.next + 1, this.this$1.this$0.key, this.next, n);
                    System.arraycopy(this.this$1.this$0.value, this.next + 1, this.this$1.this$0.value, this.next, n);
                    this.this$1.this$0.key[this.this$1.this$0.size] = null;
                    this.this$1.this$0.value[this.this$1.this$0.size] = null;
                }

                @Override
                public void forEachRemaining(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
                    int n = this.this$1.this$0.size;
                    while (this.next < n) {
                        this.curr = this.next;
                        consumer.accept(new AbstractObject2ObjectMap.BasicEntry<Object, Object>(this.this$1.this$0.key[this.curr], this.this$1.this$0.value[this.next++]));
                    }
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
            return new ObjectIterator<Object2ObjectMap.Entry<K, V>>(this){
                int next;
                int curr;
                final AbstractObject2ObjectMap.BasicEntry<K, V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractObject2ObjectMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < this.this$1.this$0.size;
                }

                @Override
                public Object2ObjectMap.Entry<K, V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = this.this$1.this$0.key[this.curr];
                    this.entry.value = this.this$1.this$0.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = this.this$1.this$0.size-- - this.next--;
                    System.arraycopy(this.this$1.this$0.key, this.next + 1, this.this$1.this$0.key, this.next, n);
                    System.arraycopy(this.this$1.this$0.value, this.next + 1, this.this$1.this$0.value, this.next, n);
                    this.this$1.this$0.key[this.this$1.this$0.size] = null;
                    this.this$1.this$0.value[this.this$1.this$0.size] = null;
                }

                @Override
                public void forEachRemaining(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
                    int n = this.this$1.this$0.size;
                    while (this.next < n) {
                        this.curr = this.next;
                        this.entry.key = this.this$1.this$0.key[this.curr];
                        this.entry.value = this.this$1.this$0.value[this.next++];
                        consumer.accept(this.entry);
                    }
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
            return new EntrySetSpliterator(this, 0, this.this$0.size);
        }

        @Override
        public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
            int n = this.this$0.size;
            for (int i = 0; i < n; ++i) {
                consumer.accept(new AbstractObject2ObjectMap.BasicEntry<Object, Object>(this.this$0.key[i], this.this$0.value[i]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
            AbstractObject2ObjectMap.BasicEntry basicEntry = new AbstractObject2ObjectMap.BasicEntry();
            int n = this.this$0.size;
            for (int i = 0; i < n; ++i) {
                basicEntry.key = this.this$0.key[i];
                basicEntry.value = this.this$0.value[i];
                consumer.accept(basicEntry);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && Objects.equals(this.this$0.get(k), entry.getValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            int n = Object2ObjectArrayMap.access$000(this.this$0, k);
            if (n == -1 || !Objects.equals(v, this.this$0.value[n])) {
                return true;
            }
            int n2 = this.this$0.size - n - 1;
            System.arraycopy(this.this$0.key, n + 1, this.this$0.key, n, n2);
            System.arraycopy(this.this$0.value, n + 1, this.this$0.value, n, n2);
            --this.this$0.size;
            this.this$0.key[this.this$0.size] = null;
            this.this$0.value[this.this$0.size] = null;
            return false;
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Object2ObjectArrayMap object2ObjectArrayMap, 1 var2_2) {
            this(object2ObjectArrayMap);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        final class EntrySetSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Object2ObjectMap.Entry<K, V>>
        implements ObjectSpliterator<Object2ObjectMap.Entry<K, V>> {
            final EntrySet this$1;

            EntrySetSpliterator(EntrySet entrySet, int n, int n2) {
                this.this$1 = entrySet;
                super(n, n2);
            }

            @Override
            public int characteristics() {
                return 0;
            }

            @Override
            protected final Object2ObjectMap.Entry<K, V> get(int n) {
                return new AbstractObject2ObjectMap.BasicEntry<Object, Object>(this.this$1.this$0.key[n], this.this$1.this$0.value[n]);
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap$EntrySet.EntrySetSpliterator makeForSplit(int n, int n2) {
                return new EntrySetSpliterator(this.this$1, n, n2);
            }

            @Override
            protected ObjectSpliterator makeForSplit(int n, int n2) {
                return this.makeForSplit(n, n2);
            }

            @Override
            protected Object get(int n) {
                return this.get(n);
            }
        }
    }

    private final class KeySet
    extends AbstractObjectSet<K> {
        final Object2ObjectArrayMap this$0;

        private KeySet(Object2ObjectArrayMap object2ObjectArrayMap) {
            this.this$0 = object2ObjectArrayMap;
        }

        @Override
        public boolean contains(Object object) {
            return Object2ObjectArrayMap.access$000(this.this$0, object) != -1;
        }

        @Override
        public boolean remove(Object object) {
            int n = Object2ObjectArrayMap.access$000(this.this$0, object);
            if (n == -1) {
                return true;
            }
            int n2 = this.this$0.size - n - 1;
            System.arraycopy(this.this$0.key, n + 1, this.this$0.key, n, n2);
            System.arraycopy(this.this$0.value, n + 1, this.this$0.value, n, n2);
            --this.this$0.size;
            this.this$0.key[this.this$0.size] = null;
            this.this$0.value[this.this$0.size] = null;
            return false;
        }

        @Override
        public ObjectIterator<K> iterator() {
            return new ObjectIterator<K>(this){
                int pos;
                final KeySet this$1;
                {
                    this.this$1 = keySet;
                    this.pos = 0;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$1.this$0.size;
                }

                @Override
                public K next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return this.this$1.this$0.key[this.pos++];
                }

                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    int n = this.this$1.this$0.size - this.pos;
                    System.arraycopy(this.this$1.this$0.key, this.pos, this.this$1.this$0.key, this.pos - 1, n);
                    System.arraycopy(this.this$1.this$0.value, this.pos, this.this$1.this$0.value, this.pos - 1, n);
                    --this.this$1.this$0.size;
                    --this.pos;
                    this.this$1.this$0.key[this.this$1.this$0.size] = null;
                    this.this$1.this$0.value[this.this$1.this$0.size] = null;
                }

                @Override
                public void forEachRemaining(Consumer<? super K> consumer) {
                    int n = this.this$1.this$0.size;
                    while (this.pos < n) {
                        consumer.accept(this.this$1.this$0.key[this.pos++]);
                    }
                }
            };
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return new KeySetSpliterator(this, 0, this.this$0.size);
        }

        @Override
        public void forEach(Consumer<? super K> consumer) {
            int n = this.this$0.size;
            for (int i = 0; i < n; ++i) {
                consumer.accept(this.this$0.key[i]);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        KeySet(Object2ObjectArrayMap object2ObjectArrayMap, 1 var2_2) {
            this(object2ObjectArrayMap);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        final class KeySetSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K>
        implements ObjectSpliterator<K> {
            final KeySet this$1;

            KeySetSpliterator(KeySet keySet, int n, int n2) {
                this.this$1 = keySet;
                super(n, n2);
            }

            @Override
            public int characteristics() {
                return 0;
            }

            @Override
            protected final K get(int n) {
                return this.this$1.this$0.key[n];
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap$KeySet.KeySetSpliterator makeForSplit(int n, int n2) {
                return new KeySetSpliterator(this.this$1, n, n2);
            }

            @Override
            public void forEachRemaining(Consumer<? super K> consumer) {
                int n = this.this$1.this$0.size;
                while (this.pos < n) {
                    consumer.accept(this.this$1.this$0.key[this.pos++]);
                }
            }

            @Override
            protected ObjectSpliterator makeForSplit(int n, int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }

    private final class ValuesCollection
    extends AbstractObjectCollection<V> {
        final Object2ObjectArrayMap this$0;

        private ValuesCollection(Object2ObjectArrayMap object2ObjectArrayMap) {
            this.this$0 = object2ObjectArrayMap;
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsValue(object);
        }

        @Override
        public ObjectIterator<V> iterator() {
            return new ObjectIterator<V>(this){
                int pos;
                final ValuesCollection this$1;
                {
                    this.this$1 = valuesCollection;
                    this.pos = 0;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$1.this$0.size;
                }

                @Override
                public V next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return this.this$1.this$0.value[this.pos++];
                }

                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    int n = this.this$1.this$0.size - this.pos;
                    System.arraycopy(this.this$1.this$0.key, this.pos, this.this$1.this$0.key, this.pos - 1, n);
                    System.arraycopy(this.this$1.this$0.value, this.pos, this.this$1.this$0.value, this.pos - 1, n);
                    --this.this$1.this$0.size;
                    --this.pos;
                    this.this$1.this$0.key[this.this$1.this$0.size] = null;
                    this.this$1.this$0.value[this.this$1.this$0.size] = null;
                }

                @Override
                public void forEachRemaining(Consumer<? super V> consumer) {
                    int n = this.this$1.this$0.size;
                    while (this.pos < n) {
                        consumer.accept(this.this$1.this$0.value[this.pos++]);
                    }
                }
            };
        }

        @Override
        public ObjectSpliterator<V> spliterator() {
            return new ValuesSpliterator(this, 0, this.this$0.size);
        }

        @Override
        public void forEach(Consumer<? super V> consumer) {
            int n = this.this$0.size;
            for (int i = 0; i < n; ++i) {
                consumer.accept(this.this$0.value[i]);
            }
        }

        @Override
        public int size() {
            return this.this$0.size;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        ValuesCollection(Object2ObjectArrayMap object2ObjectArrayMap, 1 var2_2) {
            this(object2ObjectArrayMap);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        final class ValuesSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<V>
        implements ObjectSpliterator<V> {
            final ValuesCollection this$1;

            ValuesSpliterator(ValuesCollection valuesCollection, int n, int n2) {
                this.this$1 = valuesCollection;
                super(n, n2);
            }

            @Override
            public int characteristics() {
                return 1;
            }

            @Override
            protected final V get(int n) {
                return this.this$1.this$0.value[n];
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap$ValuesCollection.ValuesSpliterator makeForSplit(int n, int n2) {
                return new ValuesSpliterator(this.this$1, n, n2);
            }

            @Override
            public void forEachRemaining(Consumer<? super V> consumer) {
                int n = this.this$1.this$0.size;
                while (this.pos < n) {
                    consumer.accept(this.this$1.this$0.value[this.pos++]);
                }
            }

            @Override
            protected ObjectSpliterator makeForSplit(int n, int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }
}

