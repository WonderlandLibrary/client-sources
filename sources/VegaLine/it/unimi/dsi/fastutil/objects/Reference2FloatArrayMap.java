/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.FloatArraySet;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2FloatMap;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

public class Reference2FloatArrayMap<K>
extends AbstractReference2FloatMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient float[] value;
    private int size;

    public Reference2FloatArrayMap(Object[] key, float[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Reference2FloatArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Reference2FloatArrayMap(int capacity) {
        this.key = new Object[capacity];
        this.value = new float[capacity];
    }

    public Reference2FloatArrayMap(Reference2FloatMap<K> m) {
        this(m.size());
        this.putAll(m);
    }

    public Reference2FloatArrayMap(Map<? extends K, ? extends Float> m) {
        this(m.size());
        this.putAll(m);
    }

    public Reference2FloatArrayMap(Object[] key, float[] value, int size) {
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

    public Reference2FloatMap.FastEntrySet<K> reference2FloatEntrySet() {
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
    public float getFloat(Object k) {
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
    public boolean containsValue(float v) {
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
    public float put(K k, float v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            float oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
            float[] newValue = new float[this.size == 0 ? 2 : this.size * 2];
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
    public float removeFloat(Object k) {
        int oldPos = this.findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        float oldValue = this.value[oldPos];
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
    public FloatCollection values() {
        return FloatCollections.unmodifiable(new FloatArraySet(this.value, this.size));
    }

    public Reference2FloatArrayMap<K> clone() {
        Reference2FloatArrayMap c;
        try {
            c = (Reference2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (Object[])this.key.clone();
        c.value = (float[])this.value.clone();
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            s.writeObject(this.key[i]);
            s.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = s.readObject();
            this.value[i] = s.readFloat();
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2FloatMap.Entry<K>>
    implements Reference2FloatMap.FastEntrySet<K> {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Reference2FloatMap.Entry<K>> iterator() {
            return new AbstractObjectIterator<Reference2FloatMap.Entry<K>>(){
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    return this.next < Reference2FloatArrayMap.this.size;
                }

                @Override
                public Reference2FloatMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2FloatMap.BasicEntry<Object>(Reference2FloatArrayMap.this.key[this.curr], Reference2FloatArrayMap.this.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Reference2FloatArrayMap.this.size-- - this.next--;
                    System.arraycopy(Reference2FloatArrayMap.this.key, this.next + 1, Reference2FloatArrayMap.this.key, this.next, tail);
                    System.arraycopy(Reference2FloatArrayMap.this.value, this.next + 1, Reference2FloatArrayMap.this.value, this.next, tail);
                    ((Reference2FloatArrayMap)Reference2FloatArrayMap.this).key[((Reference2FloatArrayMap)Reference2FloatArrayMap.this).size] = null;
                }
            };
        }

        @Override
        public ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator() {
            return new AbstractObjectIterator<Reference2FloatMap.Entry<K>>(){
                int next = 0;
                int curr = -1;
                final AbstractReference2FloatMap.BasicEntry<K> entry = new AbstractReference2FloatMap.BasicEntry<Object>(null, 0.0f);

                @Override
                public boolean hasNext() {
                    return this.next < Reference2FloatArrayMap.this.size;
                }

                @Override
                public Reference2FloatMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2FloatArrayMap.this.key[this.curr];
                    this.entry.value = Reference2FloatArrayMap.this.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Reference2FloatArrayMap.this.size-- - this.next--;
                    System.arraycopy(Reference2FloatArrayMap.this.key, this.next + 1, Reference2FloatArrayMap.this.key, this.next, tail);
                    System.arraycopy(Reference2FloatArrayMap.this.value, this.next + 1, Reference2FloatArrayMap.this.value, this.next, tail);
                    ((Reference2FloatArrayMap)Reference2FloatArrayMap.this).key[((Reference2FloatArrayMap)Reference2FloatArrayMap.this).size] = null;
                }
            };
        }

        @Override
        public int size() {
            return Reference2FloatArrayMap.this.size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            Object k = e.getKey();
            return Reference2FloatArrayMap.this.containsKey(k) && Reference2FloatArrayMap.this.getFloat(k) == ((Float)e.getValue()).floatValue();
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            Object k = e.getKey();
            float v = ((Float)e.getValue()).floatValue();
            int oldPos = Reference2FloatArrayMap.this.findKey(k);
            if (oldPos == -1 || v != Reference2FloatArrayMap.this.value[oldPos]) {
                return false;
            }
            int tail = Reference2FloatArrayMap.this.size - oldPos - 1;
            System.arraycopy(Reference2FloatArrayMap.this.key, oldPos + 1, Reference2FloatArrayMap.this.key, oldPos, tail);
            System.arraycopy(Reference2FloatArrayMap.this.value, oldPos + 1, Reference2FloatArrayMap.this.value, oldPos, tail);
            Reference2FloatArrayMap.this.size--;
            ((Reference2FloatArrayMap)Reference2FloatArrayMap.this).key[((Reference2FloatArrayMap)Reference2FloatArrayMap.this).size] = null;
            return true;
        }
    }
}

