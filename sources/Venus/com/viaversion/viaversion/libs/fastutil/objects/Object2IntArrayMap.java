/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$EntrySet.EntrySetSpliterator
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$KeySet.KeySetSpliterator
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$ValuesCollection.ValuesSpliterator
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
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
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Object2IntArrayMap<K>
extends AbstractObject2IntMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    protected transient Object[] key;
    protected transient int[] value;
    protected int size;
    protected transient Object2IntMap.FastEntrySet<K> entries;
    protected transient ObjectSet<K> keys;
    protected transient IntCollection values;

    public Object2IntArrayMap(Object[] objectArray, int[] nArray) {
        this.key = objectArray;
        this.value = nArray;
        this.size = objectArray.length;
        if (objectArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + nArray.length + ")");
        }
    }

    public Object2IntArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Object2IntArrayMap(int n) {
        this.key = new Object[n];
        this.value = new int[n];
    }

    public Object2IntArrayMap(Object2IntMap<K> object2IntMap) {
        this(object2IntMap.size());
        int n = 0;
        for (Object2IntMap.Entry entry : object2IntMap.object2IntEntrySet()) {
            this.key[n] = entry.getKey();
            this.value[n] = entry.getIntValue();
            ++n;
        }
        this.size = n;
    }

    public Object2IntArrayMap(Map<? extends K, ? extends Integer> map) {
        this(map.size());
        int n = 0;
        for (Map.Entry<K, Integer> entry : map.entrySet()) {
            this.key[n] = entry.getKey();
            this.value[n] = entry.getValue();
            ++n;
        }
        this.size = n;
    }

    public Object2IntArrayMap(Object[] objectArray, int[] nArray, int n) {
        this.key = objectArray;
        this.value = nArray;
        this.size = n;
        if (objectArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + nArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
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
    public int getInt(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(objectArray[n], object)) continue;
            return this.value[n];
        }
        return this.defRetValue;
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
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.findKey(object) != -1;
    }

    @Override
    public boolean containsValue(int n) {
        int n2 = this.size;
        while (n2-- != 0) {
            if (this.value[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int put(K k, int n) {
        int n2 = this.findKey(k);
        if (n2 != -1) {
            int n3 = this.value[n2];
            this.value[n2] = n;
            return n3;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n4 = this.size;
            while (n4-- != 0) {
                objectArray[n4] = this.key[n4];
                nArray[n4] = this.value[n4];
            }
            this.key = objectArray;
            this.value = nArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int removeInt(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        int n2 = this.value[n];
        int n3 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n3);
        System.arraycopy(this.value, n + 1, this.value, n, n3);
        --this.size;
        this.key[this.size] = null;
        return n2;
    }

    @Override
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(this, null);
        }
        return this.keys;
    }

    @Override
    public IntCollection values() {
        if (this.values == null) {
            this.values = new ValuesCollection(this, null);
        }
        return this.values;
    }

    public Object2IntArrayMap<K> clone() {
        Object2IntArrayMap object2IntArrayMap;
        try {
            object2IntArrayMap = (Object2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2IntArrayMap.key = (Object[])this.key.clone();
        object2IntArrayMap.value = (int[])this.value.clone();
        object2IntArrayMap.entries = null;
        object2IntArrayMap.keys = null;
        object2IntArrayMap.values = null;
        return object2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        for (int i = 0; i < n; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readInt();
        }
    }

    @Override
    public ObjectSet object2IntEntrySet() {
        return this.object2IntEntrySet();
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

    static int access$000(Object2IntArrayMap object2IntArrayMap, Object object) {
        return object2IntArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Object2IntMap.Entry<K>>
    implements Object2IntMap.FastEntrySet<K> {
        final Object2IntArrayMap this$0;

        private EntrySet(Object2IntArrayMap object2IntArrayMap) {
            this.this$0 = object2IntArrayMap;
        }

        @Override
        public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
            return new ObjectIterator<Object2IntMap.Entry<K>>(this){
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
                public Object2IntMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractObject2IntMap.BasicEntry<Object>(this.this$1.this$0.key[this.curr], this.this$1.this$0.value[this.next++]);
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
                }

                @Override
                public void forEachRemaining(Consumer<? super Object2IntMap.Entry<K>> consumer) {
                    int n = this.this$1.this$0.size;
                    while (this.next < n) {
                        this.curr = this.next;
                        consumer.accept(new AbstractObject2IntMap.BasicEntry<Object>(this.this$1.this$0.key[this.curr], this.this$1.this$0.value[this.next++]));
                    }
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Object2IntMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractObject2IntMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractObject2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < this.this$1.this$0.size;
                }

                @Override
                public Object2IntMap.Entry<K> next() {
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
                }

                @Override
                public void forEachRemaining(Consumer<? super Object2IntMap.Entry<K>> consumer) {
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
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return new EntrySetSpliterator(this, 0, this.this$0.size);
        }

        @Override
        public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
            int n = this.this$0.size;
            for (int i = 0; i < n; ++i) {
                consumer.accept(new AbstractObject2IntMap.BasicEntry<Object>(this.this$0.key[i], this.this$0.value[i]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
            AbstractObject2IntMap.BasicEntry basicEntry = new AbstractObject2IntMap.BasicEntry();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && this.this$0.getInt(k) == ((Integer)entry.getValue()).intValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            Object k = entry.getKey();
            int n = (Integer)entry.getValue();
            int n2 = Object2IntArrayMap.access$000(this.this$0, k);
            if (n2 == -1 || n != this.this$0.value[n2]) {
                return true;
            }
            int n3 = this.this$0.size - n2 - 1;
            System.arraycopy(this.this$0.key, n2 + 1, this.this$0.key, n2, n3);
            System.arraycopy(this.this$0.value, n2 + 1, this.this$0.value, n2, n3);
            --this.this$0.size;
            this.this$0.key[this.this$0.size] = null;
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

        EntrySet(Object2IntArrayMap object2IntArrayMap, 1 var2_2) {
            this(object2IntArrayMap);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        final class EntrySetSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Object2IntMap.Entry<K>>
        implements ObjectSpliterator<Object2IntMap.Entry<K>> {
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
            protected final Object2IntMap.Entry<K> get(int n) {
                return new AbstractObject2IntMap.BasicEntry<Object>(this.this$1.this$0.key[n], this.this$1.this$0.value[n]);
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$EntrySet.EntrySetSpliterator makeForSplit(int n, int n2) {
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
        final Object2IntArrayMap this$0;

        private KeySet(Object2IntArrayMap object2IntArrayMap) {
            this.this$0 = object2IntArrayMap;
        }

        @Override
        public boolean contains(Object object) {
            return Object2IntArrayMap.access$000(this.this$0, object) != -1;
        }

        @Override
        public boolean remove(Object object) {
            int n = Object2IntArrayMap.access$000(this.this$0, object);
            if (n == -1) {
                return true;
            }
            int n2 = this.this$0.size - n - 1;
            System.arraycopy(this.this$0.key, n + 1, this.this$0.key, n, n2);
            System.arraycopy(this.this$0.value, n + 1, this.this$0.value, n, n2);
            --this.this$0.size;
            this.this$0.key[this.this$0.size] = null;
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

        KeySet(Object2IntArrayMap object2IntArrayMap, 1 var2_2) {
            this(object2IntArrayMap);
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

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$KeySet.KeySetSpliterator makeForSplit(int n, int n2) {
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ValuesCollection
    extends AbstractIntCollection {
        final Object2IntArrayMap this$0;

        private ValuesCollection(Object2IntArrayMap object2IntArrayMap) {
            this.this$0 = object2IntArrayMap;
        }

        @Override
        public boolean contains(int n) {
            return this.this$0.containsValue(n);
        }

        @Override
        public IntIterator iterator() {
            return new IntIterator(this){
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
                public int nextInt() {
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
                }

                @Override
                public void forEachRemaining(IntConsumer intConsumer) {
                    int n = this.this$1.this$0.size;
                    while (this.pos < n) {
                        intConsumer.accept(this.this$1.this$0.value[this.pos++]);
                    }
                }

                @Override
                public void forEachRemaining(Object object) {
                    this.forEachRemaining((IntConsumer)object);
                }
            };
        }

        @Override
        public IntSpliterator spliterator() {
            return new ValuesSpliterator(this, 0, this.this$0.size);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            int n = this.this$0.size;
            for (int i = 0; i < n; ++i) {
                intConsumer.accept(this.this$0.value[i]);
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

        ValuesCollection(Object2IntArrayMap object2IntArrayMap, 1 var2_2) {
            this(object2IntArrayMap);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        final class ValuesSpliterator
        extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
        implements IntSpliterator {
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
            protected final int get(int n) {
                return this.this$1.this$0.value[n];
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$ValuesCollection.ValuesSpliterator makeForSplit(int n, int n2) {
                return new ValuesSpliterator(this.this$1, n, n2);
            }

            @Override
            public void forEachRemaining(IntConsumer intConsumer) {
                int n = this.this$1.this$0.size;
                while (this.pos < n) {
                    intConsumer.accept(this.this$1.this$0.value[this.pos++]);
                }
            }

            @Override
            protected IntSpliterator makeForSplit(int n, int n2) {
                return this.makeForSplit(n, n2);
            }

            @Override
            public void forEachRemaining(Object object) {
                this.forEachRemaining((IntConsumer)object);
            }
        }
    }
}

