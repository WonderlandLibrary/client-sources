/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.ByteArraySet;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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

public class Byte2ObjectArrayMap<V>
extends AbstractByte2ObjectMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient Object[] value;
    private int size;

    public Byte2ObjectArrayMap(byte[] key, Object[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Byte2ObjectArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Byte2ObjectArrayMap(int capacity) {
        this.key = new byte[capacity];
        this.value = new Object[capacity];
    }

    public Byte2ObjectArrayMap(Byte2ObjectMap<V> m) {
        this(m.size());
        this.putAll(m);
    }

    public Byte2ObjectArrayMap(Map<? extends Byte, ? extends V> m) {
        this(m.size());
        this.putAll(m);
    }

    public Byte2ObjectArrayMap(byte[] key, Object[] value, int size) {
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

    public Byte2ObjectMap.FastEntrySet<V> byte2ObjectEntrySet() {
        return new EntrySet();
    }

    private int findKey(byte k) {
        byte[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (key[i] != k) continue;
            return i;
        }
        return -1;
    }

    @Override
    public V get(byte k) {
        byte[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (key[i] != k) continue;
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
    public boolean containsKey(byte k) {
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
    public V put(byte k, V v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            Object oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return (V)oldValue;
        }
        if (this.size == this.key.length) {
            byte[] newKey = new byte[this.size == 0 ? 2 : this.size * 2];
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
    public V remove(byte k) {
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
    public ByteSet keySet() {
        return new ByteArraySet(this.key, this.size);
    }

    @Override
    public ObjectCollection<V> values() {
        return ObjectCollections.unmodifiable(new ObjectArraySet(this.value, this.size));
    }

    public Byte2ObjectArrayMap<V> clone() {
        Byte2ObjectArrayMap c;
        try {
            c = (Byte2ObjectArrayMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (byte[])this.key.clone();
        c.value = (Object[])this.value.clone();
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            s.writeByte(this.key[i]);
            s.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = s.readByte();
            this.value[i] = s.readObject();
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2ObjectMap.Entry<V>>
    implements Byte2ObjectMap.FastEntrySet<V> {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Byte2ObjectMap.Entry<V>> iterator() {
            return new AbstractObjectIterator<Byte2ObjectMap.Entry<V>>(){
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    return this.next < Byte2ObjectArrayMap.this.size;
                }

                @Override
                public Byte2ObjectMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2ObjectMap.BasicEntry<Object>(Byte2ObjectArrayMap.this.key[this.curr], Byte2ObjectArrayMap.this.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Byte2ObjectArrayMap.this.size-- - this.next--;
                    System.arraycopy(Byte2ObjectArrayMap.this.key, this.next + 1, Byte2ObjectArrayMap.this.key, this.next, tail);
                    System.arraycopy(Byte2ObjectArrayMap.this.value, this.next + 1, Byte2ObjectArrayMap.this.value, this.next, tail);
                    ((Byte2ObjectArrayMap)Byte2ObjectArrayMap.this).value[((Byte2ObjectArrayMap)Byte2ObjectArrayMap.this).size] = null;
                }
            };
        }

        @Override
        public ObjectIterator<Byte2ObjectMap.Entry<V>> fastIterator() {
            return new AbstractObjectIterator<Byte2ObjectMap.Entry<V>>(){
                int next = 0;
                int curr = -1;
                final AbstractByte2ObjectMap.BasicEntry<V> entry = new AbstractByte2ObjectMap.BasicEntry<Object>(0, null);

                @Override
                public boolean hasNext() {
                    return this.next < Byte2ObjectArrayMap.this.size;
                }

                @Override
                public Byte2ObjectMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2ObjectArrayMap.this.key[this.curr];
                    this.entry.value = Byte2ObjectArrayMap.this.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Byte2ObjectArrayMap.this.size-- - this.next--;
                    System.arraycopy(Byte2ObjectArrayMap.this.key, this.next + 1, Byte2ObjectArrayMap.this.key, this.next, tail);
                    System.arraycopy(Byte2ObjectArrayMap.this.value, this.next + 1, Byte2ObjectArrayMap.this.value, this.next, tail);
                    ((Byte2ObjectArrayMap)Byte2ObjectArrayMap.this).value[((Byte2ObjectArrayMap)Byte2ObjectArrayMap.this).size] = null;
                }
            };
        }

        @Override
        public int size() {
            return Byte2ObjectArrayMap.this.size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
                return false;
            }
            byte k = (Byte)e.getKey();
            return Byte2ObjectArrayMap.this.containsKey(k) && (Byte2ObjectArrayMap.this.get(k) == null ? e.getValue() == null : Byte2ObjectArrayMap.this.get(k).equals(e.getValue()));
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Byte)) {
                return false;
            }
            byte k = (Byte)e.getKey();
            Object v = e.getValue();
            int oldPos = Byte2ObjectArrayMap.this.findKey(k);
            if (oldPos == -1 || !(v != null ? v.equals(Byte2ObjectArrayMap.this.value[oldPos]) : Byte2ObjectArrayMap.this.value[oldPos] == null)) {
                return false;
            }
            int tail = Byte2ObjectArrayMap.this.size - oldPos - 1;
            System.arraycopy(Byte2ObjectArrayMap.this.key, oldPos + 1, Byte2ObjectArrayMap.this.key, oldPos, tail);
            System.arraycopy(Byte2ObjectArrayMap.this.value, oldPos + 1, Byte2ObjectArrayMap.this.value, oldPos, tail);
            Byte2ObjectArrayMap.this.size--;
            ((Byte2ObjectArrayMap)Byte2ObjectArrayMap.this).value[((Byte2ObjectArrayMap)Byte2ObjectArrayMap.this).size] = null;
            return true;
        }
    }
}

