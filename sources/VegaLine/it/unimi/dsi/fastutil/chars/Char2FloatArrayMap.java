/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2FloatMap;
import it.unimi.dsi.fastutil.chars.Char2FloatMap;
import it.unimi.dsi.fastutil.chars.CharArraySet;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.floats.FloatArraySet;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

public class Char2FloatArrayMap
extends AbstractChar2FloatMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient float[] value;
    private int size;

    public Char2FloatArrayMap(char[] key, float[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Char2FloatArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Char2FloatArrayMap(int capacity) {
        this.key = new char[capacity];
        this.value = new float[capacity];
    }

    public Char2FloatArrayMap(Char2FloatMap m) {
        this(m.size());
        this.putAll(m);
    }

    public Char2FloatArrayMap(Map<? extends Character, ? extends Float> m) {
        this(m.size());
        this.putAll(m);
    }

    public Char2FloatArrayMap(char[] key, float[] value, int size) {
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

    public Char2FloatMap.FastEntrySet char2FloatEntrySet() {
        return new EntrySet();
    }

    private int findKey(char k) {
        char[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (key[i] != k) continue;
            return i;
        }
        return -1;
    }

    @Override
    public float get(char k) {
        char[] key = this.key;
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
        this.size = 0;
    }

    @Override
    public boolean containsKey(char k) {
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
    public float put(char k, float v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            float oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            char[] newKey = new char[this.size == 0 ? 2 : this.size * 2];
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
    public float remove(char k) {
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
    public CharSet keySet() {
        return new CharArraySet(this.key, this.size);
    }

    @Override
    public FloatCollection values() {
        return FloatCollections.unmodifiable(new FloatArraySet(this.value, this.size));
    }

    public Char2FloatArrayMap clone() {
        Char2FloatArrayMap c;
        try {
            c = (Char2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (char[])this.key.clone();
        c.value = (float[])this.value.clone();
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            s.writeChar(this.key[i]);
            s.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new char[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = s.readChar();
            this.value[i] = s.readFloat();
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2FloatMap.Entry>
    implements Char2FloatMap.FastEntrySet {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Char2FloatMap.Entry> iterator() {
            return new AbstractObjectIterator<Char2FloatMap.Entry>(){
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    return this.next < Char2FloatArrayMap.this.size;
                }

                @Override
                public Char2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2FloatMap.BasicEntry(Char2FloatArrayMap.this.key[this.curr], Char2FloatArrayMap.this.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Char2FloatArrayMap.this.size-- - this.next--;
                    System.arraycopy(Char2FloatArrayMap.this.key, this.next + 1, Char2FloatArrayMap.this.key, this.next, tail);
                    System.arraycopy(Char2FloatArrayMap.this.value, this.next + 1, Char2FloatArrayMap.this.value, this.next, tail);
                }
            };
        }

        @Override
        public ObjectIterator<Char2FloatMap.Entry> fastIterator() {
            return new AbstractObjectIterator<Char2FloatMap.Entry>(){
                int next = 0;
                int curr = -1;
                final AbstractChar2FloatMap.BasicEntry entry = new AbstractChar2FloatMap.BasicEntry('\u0000', 0.0f);

                @Override
                public boolean hasNext() {
                    return this.next < Char2FloatArrayMap.this.size;
                }

                @Override
                public Char2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2FloatArrayMap.this.key[this.curr];
                    this.entry.value = Char2FloatArrayMap.this.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Char2FloatArrayMap.this.size-- - this.next--;
                    System.arraycopy(Char2FloatArrayMap.this.key, this.next + 1, Char2FloatArrayMap.this.key, this.next, tail);
                    System.arraycopy(Char2FloatArrayMap.this.value, this.next + 1, Char2FloatArrayMap.this.value, this.next, tail);
                }
            };
        }

        @Override
        public int size() {
            return Char2FloatArrayMap.this.size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Character)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            char k = ((Character)e.getKey()).charValue();
            return Char2FloatArrayMap.this.containsKey(k) && Char2FloatArrayMap.this.get(k) == ((Float)e.getValue()).floatValue();
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Character)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                return false;
            }
            char k = ((Character)e.getKey()).charValue();
            float v = ((Float)e.getValue()).floatValue();
            int oldPos = Char2FloatArrayMap.this.findKey(k);
            if (oldPos == -1 || v != Char2FloatArrayMap.this.value[oldPos]) {
                return false;
            }
            int tail = Char2FloatArrayMap.this.size - oldPos - 1;
            System.arraycopy(Char2FloatArrayMap.this.key, oldPos + 1, Char2FloatArrayMap.this.key, oldPos, tail);
            System.arraycopy(Char2FloatArrayMap.this.value, oldPos + 1, Char2FloatArrayMap.this.value, oldPos, tail);
            Char2FloatArrayMap.this.size--;
            return true;
        }
    }
}

