/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2LongMap;
import it.unimi.dsi.fastutil.ints.Int2LongMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongArraySet;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

public class Int2LongArrayMap
extends AbstractInt2LongMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient long[] value;
    private int size;

    public Int2LongArrayMap(int[] key, long[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Int2LongArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Int2LongArrayMap(int capacity) {
        this.key = new int[capacity];
        this.value = new long[capacity];
    }

    public Int2LongArrayMap(Int2LongMap m) {
        this(m.size());
        this.putAll(m);
    }

    public Int2LongArrayMap(Map<? extends Integer, ? extends Long> m) {
        this(m.size());
        this.putAll(m);
    }

    public Int2LongArrayMap(int[] key, long[] value, int size) {
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

    public Int2LongMap.FastEntrySet int2LongEntrySet() {
        return new EntrySet();
    }

    private int findKey(int k) {
        int[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (key[i] != k) continue;
            return i;
        }
        return -1;
    }

    @Override
    public long get(int k) {
        int[] key = this.key;
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
    public boolean containsKey(int k) {
        return this.findKey(k) != -1;
    }

    @Override
    public boolean containsValue(long v) {
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
    public long put(int k, long v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            long oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            int[] newKey = new int[this.size == 0 ? 2 : this.size * 2];
            long[] newValue = new long[this.size == 0 ? 2 : this.size * 2];
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
    public long remove(int k) {
        int oldPos = this.findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        long oldValue = this.value[oldPos];
        int tail = this.size - oldPos - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        --this.size;
        return oldValue;
    }

    @Override
    public IntSet keySet() {
        return new IntArraySet(this.key, this.size);
    }

    @Override
    public LongCollection values() {
        return LongCollections.unmodifiable(new LongArraySet(this.value, this.size));
    }

    public Int2LongArrayMap clone() {
        Int2LongArrayMap c;
        try {
            c = (Int2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (int[])this.key.clone();
        c.value = (long[])this.value.clone();
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            s.writeInt(this.key[i]);
            s.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new int[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = s.readInt();
            this.value[i] = s.readLong();
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2LongMap.Entry>
    implements Int2LongMap.FastEntrySet {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Int2LongMap.Entry> iterator() {
            return new AbstractObjectIterator<Int2LongMap.Entry>(){
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    return this.next < Int2LongArrayMap.this.size;
                }

                @Override
                public Int2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2LongMap.BasicEntry(Int2LongArrayMap.this.key[this.curr], Int2LongArrayMap.this.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Int2LongArrayMap.this.size-- - this.next--;
                    System.arraycopy(Int2LongArrayMap.this.key, this.next + 1, Int2LongArrayMap.this.key, this.next, tail);
                    System.arraycopy(Int2LongArrayMap.this.value, this.next + 1, Int2LongArrayMap.this.value, this.next, tail);
                }
            };
        }

        @Override
        public ObjectIterator<Int2LongMap.Entry> fastIterator() {
            return new AbstractObjectIterator<Int2LongMap.Entry>(){
                int next = 0;
                int curr = -1;
                final AbstractInt2LongMap.BasicEntry entry = new AbstractInt2LongMap.BasicEntry(0, 0L);

                @Override
                public boolean hasNext() {
                    return this.next < Int2LongArrayMap.this.size;
                }

                @Override
                public Int2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2LongArrayMap.this.key[this.curr];
                    this.entry.value = Int2LongArrayMap.this.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Int2LongArrayMap.this.size-- - this.next--;
                    System.arraycopy(Int2LongArrayMap.this.key, this.next + 1, Int2LongArrayMap.this.key, this.next, tail);
                    System.arraycopy(Int2LongArrayMap.this.value, this.next + 1, Int2LongArrayMap.this.value, this.next, tail);
                }
            };
        }

        @Override
        public int size() {
            return Int2LongArrayMap.this.size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Long)) {
                return false;
            }
            int k = (Integer)e.getKey();
            return Int2LongArrayMap.this.containsKey(k) && Int2LongArrayMap.this.get(k) == ((Long)e.getValue()).longValue();
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
                return false;
            }
            if (e.getValue() == null || !(e.getValue() instanceof Long)) {
                return false;
            }
            int k = (Integer)e.getKey();
            long v = (Long)e.getValue();
            int oldPos = Int2LongArrayMap.this.findKey(k);
            if (oldPos == -1 || v != Int2LongArrayMap.this.value[oldPos]) {
                return false;
            }
            int tail = Int2LongArrayMap.this.size - oldPos - 1;
            System.arraycopy(Int2LongArrayMap.this.key, oldPos + 1, Int2LongArrayMap.this.key, oldPos, tail);
            System.arraycopy(Int2LongArrayMap.this.value, oldPos + 1, Int2LongArrayMap.this.value, oldPos, tail);
            Int2LongArrayMap.this.size--;
            return true;
        }
    }
}

