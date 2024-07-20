/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.FloatArraySet;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

public class Float2ObjectArrayMap<V>
extends AbstractFloat2ObjectMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient Object[] value;
    private int size;

    public Float2ObjectArrayMap(float[] key, Object[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Float2ObjectArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Float2ObjectArrayMap(int capacity) {
        this.key = new float[capacity];
        this.value = new Object[capacity];
    }

    public Float2ObjectArrayMap(Float2ObjectMap<V> m) {
        this(m.size());
        this.putAll(m);
    }

    public Float2ObjectArrayMap(Map<? extends Float, ? extends V> m) {
        this(m.size());
        this.putAll(m);
    }

    public Float2ObjectArrayMap(float[] key, Object[] value, int size) {
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

    public Float2ObjectMap.FastEntrySet<V> float2ObjectEntrySet() {
        return new EntrySet();
    }

    private int findKey(float k) {
        float[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (Float.floatToIntBits(key[i]) != Float.floatToIntBits(k)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public V get(float k) {
        float[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (Float.floatToIntBits(key[i]) != Float.floatToIntBits(k)) continue;
            return (V)this.value[i];
        }
        return (V)this.defRetValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        int i = this.size;
        while (i-- != 0) {
            this.value[i] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(float k) {
        return this.findKey(k) != -1;
    }

    @Override
    public boolean containsValue(Object v) {
        int i = this.size;
        while (i-- != 0) {
            if (!(this.value[i] == null ? v == null : this.value[i].equals(v))) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public V put(float k, V v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            Object oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return (V)oldValue;
        }
        if (this.size == this.key.length) {
            float[] newKey = new float[this.size == 0 ? 2 : this.size * 2];
            Object[] newValue = new Object[this.size == 0 ? 2 : this.size * 2];
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
        return (V)this.defRetValue;
    }

    @Override
    public V remove(float k) {
        int oldPos = this.findKey(k);
        if (oldPos == -1) {
            return (V)this.defRetValue;
        }
        Object oldValue = this.value[oldPos];
        int tail = this.size - oldPos - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        --this.size;
        this.value[this.size] = null;
        return (V)oldValue;
    }

    @Override
    public FloatSet keySet() {
        return new FloatArraySet(this.key, this.size);
    }

    @Override
    public ObjectCollection<V> values() {
        return ObjectCollections.unmodifiable(new ObjectArraySet(this.value, this.size));
    }

    public Float2ObjectArrayMap<V> clone() {
        Float2ObjectArrayMap c;
        try {
            c = (Float2ObjectArrayMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (float[])this.key.clone();
        c.value = (Object[])this.value.clone();
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            s.writeFloat(this.key[i]);
            s.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new float[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = s.readFloat();
            this.value[i] = s.readObject();
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2ObjectMap.Entry<V>>
    implements Float2ObjectMap.FastEntrySet<V> {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Float2ObjectMap.Entry<V>> iterator() {
            return new AbstractObjectIterator<Float2ObjectMap.Entry<V>>(){
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    return this.next < Float2ObjectArrayMap.this.size;
                }

                @Override
                public Float2ObjectMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2ObjectMap.BasicEntry<Object>(Float2ObjectArrayMap.this.key[this.curr], Float2ObjectArrayMap.this.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Float2ObjectArrayMap.this.size-- - this.next--;
                    System.arraycopy(Float2ObjectArrayMap.this.key, this.next + 1, Float2ObjectArrayMap.this.key, this.next, tail);
                    System.arraycopy(Float2ObjectArrayMap.this.value, this.next + 1, Float2ObjectArrayMap.this.value, this.next, tail);
                    ((Float2ObjectArrayMap)Float2ObjectArrayMap.this).value[((Float2ObjectArrayMap)Float2ObjectArrayMap.this).size] = null;
                }
            };
        }

        @Override
        public ObjectIterator<Float2ObjectMap.Entry<V>> fastIterator() {
            return new AbstractObjectIterator<Float2ObjectMap.Entry<V>>(){
                int next = 0;
                int curr = -1;
                final AbstractFloat2ObjectMap.BasicEntry<V> entry = new AbstractFloat2ObjectMap.BasicEntry<Object>(0.0f, null);

                @Override
                public boolean hasNext() {
                    return this.next < Float2ObjectArrayMap.this.size;
                }

                @Override
                public Float2ObjectMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2ObjectArrayMap.this.key[this.curr];
                    this.entry.value = Float2ObjectArrayMap.this.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Float2ObjectArrayMap.this.size-- - this.next--;
                    System.arraycopy(Float2ObjectArrayMap.this.key, this.next + 1, Float2ObjectArrayMap.this.key, this.next, tail);
                    System.arraycopy(Float2ObjectArrayMap.this.value, this.next + 1, Float2ObjectArrayMap.this.value, this.next, tail);
                    ((Float2ObjectArrayMap)Float2ObjectArrayMap.this).value[((Float2ObjectArrayMap)Float2ObjectArrayMap.this).size] = null;
                }
            };
        }

        @Override
        public int size() {
            return Float2ObjectArrayMap.this.size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                return false;
            }
            float k = ((Float)e.getKey()).floatValue();
            return Float2ObjectArrayMap.this.containsKey(k) && (Float2ObjectArrayMap.this.get(k) == null ? e.getValue() == null : Float2ObjectArrayMap.this.get(k).equals(e.getValue()));
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Float)) {
                return false;
            }
            float k = ((Float)e.getKey()).floatValue();
            Object v = e.getValue();
            int oldPos = Float2ObjectArrayMap.this.findKey(k);
            if (oldPos == -1 || !(v != null ? v.equals(Float2ObjectArrayMap.this.value[oldPos]) : Float2ObjectArrayMap.this.value[oldPos] == null)) {
                return false;
            }
            int tail = Float2ObjectArrayMap.this.size - oldPos - 1;
            System.arraycopy(Float2ObjectArrayMap.this.key, oldPos + 1, Float2ObjectArrayMap.this.key, oldPos, tail);
            System.arraycopy(Float2ObjectArrayMap.this.value, oldPos + 1, Float2ObjectArrayMap.this.value, oldPos, tail);
            Float2ObjectArrayMap.this.size--;
            ((Float2ObjectArrayMap)Float2ObjectArrayMap.this).value[((Float2ObjectArrayMap)Float2ObjectArrayMap.this).size] = null;
            return true;
        }
    }
}

