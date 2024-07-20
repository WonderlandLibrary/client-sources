/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.DoubleArraySet;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollections;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2DoubleMap;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

public class Reference2DoubleArrayMap<K>
extends AbstractReference2DoubleMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient double[] value;
    private int size;

    public Reference2DoubleArrayMap(Object[] key, double[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Reference2DoubleArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Reference2DoubleArrayMap(int capacity) {
        this.key = new Object[capacity];
        this.value = new double[capacity];
    }

    public Reference2DoubleArrayMap(Reference2DoubleMap<K> m) {
        this(m.size());
        this.putAll(m);
    }

    public Reference2DoubleArrayMap(Map<? extends K, ? extends Double> m) {
        this(m.size());
        this.putAll(m);
    }

    public Reference2DoubleArrayMap(Object[] key, double[] value, int size) {
        this.key = key;
        this.value = value;
        this.size = size;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
        if (size > key.length) {
            throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
        }
    }

    public Reference2DoubleMap.FastEntrySet<K> reference2DoubleEntrySet() {
        return new EntrySet();
    }

    private int findKey(Object k) {
        Object[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (key[i] != k) continue;
            return i;
        }
        return -1;
    }

    @Override
    public double getDouble(Object k) {
        Object[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (key[i] != k) continue;
            return this.value[i];
        }
        return this.defRetValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        int i = this.size;
        while (i-- != 0) {
            this.key[i] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object k) {
        return this.findKey(k) != -1;
    }

    @Override
    public boolean containsValue(double v) {
        int i = this.size;
        while (i-- != 0) {
            if (this.value[i] != v) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public double put(K k, double v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            double oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
            double[] newValue = new double[this.size == 0 ? 2 : this.size * 2];
            int i = this.size;
            while (i-- != 0) {
                newKey[i] = this.key[i];
                newValue[i] = this.value[i];
            }
            this.key = newKey;
            this.value = newValue;
        }
        this.key[this.size] = k;
        this.value[this.size] = v;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double removeDouble(Object k) {
        int oldPos = this.findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        double oldValue = this.value[oldPos];
        int tail = this.size - oldPos - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        --this.size;
        this.key[this.size] = null;
        return oldValue;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new ReferenceArraySet(this.key, this.size);
    }

    @Override
    public DoubleCollection values() {
        return DoubleCollections.unmodifiable(new DoubleArraySet(this.value, this.size));
    }

    public Reference2DoubleArrayMap<K> clone() {
        Reference2DoubleArrayMap c;
        try {
            c = (Reference2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (Object[])this.key.clone();
        c.value = (double[])this.value.clone();
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            s.writeObject(this.key[i]);
            s.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = s.readObject();
            this.value[i] = s.readDouble();
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2DoubleMap.Entry<K>>
    implements Reference2DoubleMap.FastEntrySet<K> {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Reference2DoubleMap.Entry<K>> iterator() {
            return new AbstractObjectIterator<Reference2DoubleMap.Entry<K>>(){
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    return this.next < Reference2DoubleArrayMap.this.size;
                }

                @Override
                public Reference2DoubleMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2DoubleMap.BasicEntry<Object>(Reference2DoubleArrayMap.this.key[this.curr], Reference2DoubleArrayMap.this.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Reference2DoubleArrayMap.this.size-- - this.next--;
                    System.arraycopy(Reference2DoubleArrayMap.this.key, this.next + 1, Reference2DoubleArrayMap.this.key, this.next, tail);
                    System.arraycopy(Reference2DoubleArrayMap.this.value, this.next + 1, Reference2DoubleArrayMap.this.value, this.next, tail);
                    ((Reference2DoubleArrayMap)Reference2DoubleArrayMap.this).key[((Reference2DoubleArrayMap)Reference2DoubleArrayMap.this).size] = null;
                }
            };
        }

        @Override
        public ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator() {
            return new AbstractObjectIterator<Reference2DoubleMap.Entry<K>>(){
                int next = 0;
                int curr = -1;
                final AbstractReference2DoubleMap.BasicEntry<K> entry = new AbstractReference2DoubleMap.BasicEntry<Object>(null, 0.0);

                @Override
                public boolean hasNext() {
                    return this.next < Reference2DoubleArrayMap.this.size;
                }

                @Override
                public Reference2DoubleMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2DoubleArrayMap.this.key[this.curr];
                    this.entry.value = Reference2DoubleArrayMap.this.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Reference2DoubleArrayMap.this.size-- - this.next--;
                    System.arraycopy(Reference2DoubleArrayMap.this.key, this.next + 1, Reference2DoubleArrayMap.this.key, this.next, tail);
                    System.arraycopy(Reference2DoubleArrayMap.this.value, this.next + 1, Reference2DoubleArrayMap.this.value, this.next, tail);
                    ((Reference2DoubleArrayMap)Reference2DoubleArrayMap.this).key[((Reference2DoubleArrayMap)Reference2DoubleArrayMap.this).size] = null;
                }
            };
        }

        @Override
        public int size() {
            return Reference2DoubleArrayMap.this.size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Double)) {
                return false;
            }
            Object k = e.getKey();
            return Reference2DoubleArrayMap.this.containsKey(k) && Reference2DoubleArrayMap.this.getDouble(k) == ((Double)e.getValue()).doubleValue();
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Double)) {
                return false;
            }
            Object k = e.getKey();
            double v = (Double)e.getValue();
            int oldPos = Reference2DoubleArrayMap.this.findKey(k);
            if (oldPos == -1 || v != Reference2DoubleArrayMap.this.value[oldPos]) {
                return false;
            }
            int tail = Reference2DoubleArrayMap.this.size - oldPos - 1;
            System.arraycopy(Reference2DoubleArrayMap.this.key, oldPos + 1, Reference2DoubleArrayMap.this.key, oldPos, tail);
            System.arraycopy(Reference2DoubleArrayMap.this.value, oldPos + 1, Reference2DoubleArrayMap.this.value, oldPos, tail);
            Reference2DoubleArrayMap.this.size--;
            ((Reference2DoubleArrayMap)Reference2DoubleArrayMap.this).key[((Reference2DoubleArrayMap)Reference2DoubleArrayMap.this).size] = null;
            return true;
        }
    }
}

