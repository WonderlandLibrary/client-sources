/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.FloatArraySet;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

public class Float2FloatArrayMap
extends AbstractFloat2FloatMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient float[] value;
    private int size;

    public Float2FloatArrayMap(float[] key, float[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Float2FloatArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Float2FloatArrayMap(int capacity) {
        this.key = new float[capacity];
        this.value = new float[capacity];
    }

    public Float2FloatArrayMap(Float2FloatMap m) {
        this(m.size());
        this.putAll(m);
    }

    public Float2FloatArrayMap(Map<? extends Float, ? extends Float> m) {
        this(m.size());
        this.putAll(m);
    }

    public Float2FloatArrayMap(float[] key, float[] value, int size) {
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

    public Float2FloatMap.FastEntrySet float2FloatEntrySet() {
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
    public float get(float k) {
        float[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (Float.floatToIntBits(key[i]) != Float.floatToIntBits(k)) continue;
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
        this.size = 0;
    }

    @Override
    public boolean containsKey(float k) {
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
    public float put(float k, float v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            float oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            float[] newKey = new float[this.size == 0 ? 2 : this.size * 2];
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
    public float remove(float k) {
        int oldPos = this.findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        float oldValue = this.value[oldPos];
        int tail = this.size - oldPos - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        --this.size;
        return oldValue;
    }

    @Override
    public FloatSet keySet() {
        return new FloatArraySet(this.key, this.size);
    }

    @Override
    public FloatCollection values() {
        return FloatCollections.unmodifiable(new FloatArraySet(this.value, this.size));
    }

    public Float2FloatArrayMap clone() {
        Float2FloatArrayMap c;
        try {
            c = (Float2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (float[])this.key.clone();
        c.value = (float[])this.value.clone();
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            s.writeFloat(this.key[i]);
            s.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new float[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = s.readFloat();
            this.value[i] = s.readFloat();
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2FloatMap.Entry>
    implements Float2FloatMap.FastEntrySet {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Float2FloatMap.Entry> iterator() {
            return new AbstractObjectIterator<Float2FloatMap.Entry>(){
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    return this.next < Float2FloatArrayMap.this.size;
                }

                @Override
                public Float2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2FloatMap.BasicEntry(Float2FloatArrayMap.this.key[this.curr], Float2FloatArrayMap.this.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Float2FloatArrayMap.this.size-- - this.next--;
                    System.arraycopy(Float2FloatArrayMap.this.key, this.next + 1, Float2FloatArrayMap.this.key, this.next, tail);
                    System.arraycopy(Float2FloatArrayMap.this.value, this.next + 1, Float2FloatArrayMap.this.value, this.next, tail);
                }
            };
        }

        @Override
        public ObjectIterator<Float2FloatMap.Entry> fastIterator() {
            return new AbstractObjectIterator<Float2FloatMap.Entry>(){
                int next = 0;
                int curr = -1;
                final AbstractFloat2FloatMap.BasicEntry entry = new AbstractFloat2FloatMap.BasicEntry(0.0f, 0.0f);

                @Override
                public boolean hasNext() {
                    return this.next < Float2FloatArrayMap.this.size;
                }

                @Override
                public Float2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2FloatArrayMap.this.key[this.curr];
                    this.entry.value = Float2FloatArrayMap.this.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Float2FloatArrayMap.this.size-- - this.next--;
                    System.arraycopy(Float2FloatArrayMap.this.key, this.next + 1, Float2FloatArrayMap.this.key, this.next, tail);
                    System.arraycopy(Float2FloatArrayMap.this.value, this.next + 1, Float2FloatArrayMap.this.value, this.next, tail);
                }
            };
        }

        @Override
        public int size() {
            return Float2FloatArrayMap.this.size;
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
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            float k = ((Float)e.getKey()).floatValue();
            return Float2FloatArrayMap.this.containsKey(k) && Float2FloatArrayMap.this.get(k) == ((Float)e.getValue()).floatValue();
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
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            float k = ((Float)e.getKey()).floatValue();
            float v = ((Float)e.getValue()).floatValue();
            int oldPos = Float2FloatArrayMap.this.findKey(k);
            if (oldPos == -1 || v != Float2FloatArrayMap.this.value[oldPos]) {
                return false;
            }
            int tail = Float2FloatArrayMap.this.size - oldPos - 1;
            System.arraycopy(Float2FloatArrayMap.this.key, oldPos + 1, Float2FloatArrayMap.this.key, oldPos, tail);
            System.arraycopy(Float2FloatArrayMap.this.value, oldPos + 1, Float2FloatArrayMap.this.value, oldPos, tail);
            Float2FloatArrayMap.this.size--;
            return true;
        }
    }
}

