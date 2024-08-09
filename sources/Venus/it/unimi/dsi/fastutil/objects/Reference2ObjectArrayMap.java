/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2ObjectMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
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

public class Reference2ObjectArrayMap<K, V>
extends AbstractReference2ObjectMap<K, V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient Object[] value;
    private int size;

    public Reference2ObjectArrayMap(Object[] objectArray, Object[] objectArray2) {
        this.key = objectArray;
        this.value = objectArray2;
        this.size = objectArray.length;
        if (objectArray.length != objectArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + objectArray2.length + ")");
        }
    }

    public Reference2ObjectArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Reference2ObjectArrayMap(int n) {
        this.key = new Object[n];
        this.value = new Object[n];
    }

    public Reference2ObjectArrayMap(Reference2ObjectMap<K, V> reference2ObjectMap) {
        this(reference2ObjectMap.size());
        this.putAll(reference2ObjectMap);
    }

    public Reference2ObjectArrayMap(Map<? extends K, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }

    public Reference2ObjectArrayMap(Object[] objectArray, Object[] objectArray2, int n) {
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

    public Reference2ObjectMap.FastEntrySet<K, V> reference2ObjectEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (objectArray[n] != object) continue;
            return n;
        }
        return 1;
    }

    @Override
    public V get(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (objectArray[n] != object) continue;
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
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final Reference2ObjectArrayMap this$0;
            {
                this.this$0 = reference2ObjectArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Reference2ObjectArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Reference2ObjectArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Reference2ObjectArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Reference2ObjectArrayMap.access$100(this.this$0), n + 1, Reference2ObjectArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Reference2ObjectArrayMap.access$200(this.this$0), n + 1, Reference2ObjectArrayMap.access$200(this.this$0), n, n2);
                Reference2ObjectArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Reference2ObjectArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Reference2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Reference2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2ObjectArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2ObjectArrayMap.access$000(this.this$0);
            }

            @Override
            public void clear() {
                this.this$0.clear();
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(this){
            final Reference2ObjectArrayMap this$0;
            {
                this.this$0 = reference2ObjectArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsValue(object);
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Reference2ObjectArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2ObjectArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Reference2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Reference2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2ObjectArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2ObjectArrayMap.access$000(this.this$0);
            }

            @Override
            public void clear() {
                this.this$0.clear();
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    public Reference2ObjectArrayMap<K, V> clone() {
        Reference2ObjectArrayMap reference2ObjectArrayMap;
        try {
            reference2ObjectArrayMap = (Reference2ObjectArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2ObjectArrayMap.key = (Object[])this.key.clone();
        reference2ObjectArrayMap.value = (Object[])this.value.clone();
        return reference2ObjectArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
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
    public ObjectSet reference2ObjectEntrySet() {
        return this.reference2ObjectEntrySet();
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

    static int access$000(Reference2ObjectArrayMap reference2ObjectArrayMap) {
        return reference2ObjectArrayMap.size;
    }

    static Object[] access$100(Reference2ObjectArrayMap reference2ObjectArrayMap) {
        return reference2ObjectArrayMap.key;
    }

    static Object[] access$200(Reference2ObjectArrayMap reference2ObjectArrayMap) {
        return reference2ObjectArrayMap.value;
    }

    static int access$010(Reference2ObjectArrayMap reference2ObjectArrayMap) {
        return reference2ObjectArrayMap.size--;
    }

    static int access$300(Reference2ObjectArrayMap reference2ObjectArrayMap, Object object) {
        return reference2ObjectArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2ObjectMap.Entry<K, V>>
    implements Reference2ObjectMap.FastEntrySet<K, V> {
        final Reference2ObjectArrayMap this$0;

        private EntrySet(Reference2ObjectArrayMap reference2ObjectArrayMap) {
            this.this$0 = reference2ObjectArrayMap;
        }

        @Override
        public ObjectIterator<Reference2ObjectMap.Entry<K, V>> iterator() {
            return new ObjectIterator<Reference2ObjectMap.Entry<K, V>>(this){
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
                    return this.next < Reference2ObjectArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2ObjectMap.Entry<K, V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2ObjectMap.BasicEntry<Object, Object>(Reference2ObjectArrayMap.access$100(this.this$1.this$0)[this.curr], Reference2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2ObjectArrayMap.access$100((Reference2ObjectArrayMap)this.this$1.this$0)[Reference2ObjectArrayMap.access$000((Reference2ObjectArrayMap)this.this$1.this$0)] = null;
                    Reference2ObjectArrayMap.access$200((Reference2ObjectArrayMap)this.this$1.this$0)[Reference2ObjectArrayMap.access$000((Reference2ObjectArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator() {
            return new ObjectIterator<Reference2ObjectMap.Entry<K, V>>(this){
                int next;
                int curr;
                final AbstractReference2ObjectMap.BasicEntry<K, V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractReference2ObjectMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Reference2ObjectArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2ObjectMap.Entry<K, V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2ObjectArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Reference2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2ObjectArrayMap.access$100((Reference2ObjectArrayMap)this.this$1.this$0)[Reference2ObjectArrayMap.access$000((Reference2ObjectArrayMap)this.this$1.this$0)] = null;
                    Reference2ObjectArrayMap.access$200((Reference2ObjectArrayMap)this.this$1.this$0)[Reference2ObjectArrayMap.access$000((Reference2ObjectArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Reference2ObjectArrayMap.access$000(this.this$0);
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
            int n = Reference2ObjectArrayMap.access$300(this.this$0, k);
            if (n == -1 || !Objects.equals(v, Reference2ObjectArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Reference2ObjectArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Reference2ObjectArrayMap.access$100(this.this$0), n + 1, Reference2ObjectArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Reference2ObjectArrayMap.access$200(this.this$0), n + 1, Reference2ObjectArrayMap.access$200(this.this$0), n, n2);
            Reference2ObjectArrayMap.access$010(this.this$0);
            Reference2ObjectArrayMap.access$100((Reference2ObjectArrayMap)this.this$0)[Reference2ObjectArrayMap.access$000((Reference2ObjectArrayMap)this.this$0)] = null;
            Reference2ObjectArrayMap.access$200((Reference2ObjectArrayMap)this.this$0)[Reference2ObjectArrayMap.access$000((Reference2ObjectArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Reference2ObjectArrayMap reference2ObjectArrayMap, 1 var2_2) {
            this(reference2ObjectArrayMap);
        }
    }
}

