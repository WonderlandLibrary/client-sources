/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Int2IntArrayMap
extends AbstractInt2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient int[] value;
    private int size;
    private transient Int2IntMap.FastEntrySet entries;
    private transient IntSet keys;
    private transient IntCollection values;

    public Int2IntArrayMap(int[] key, int[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length == value.length) return;
        throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
    }

    public Int2IntArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Int2IntArrayMap(int capacity) {
        this.key = new int[capacity];
        this.value = new int[capacity];
    }

    public Int2IntArrayMap(Int2IntMap m) {
        this(m.size());
        int i = 0;
        Iterator iterator = m.int2IntEntrySet().iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.size = i;
                return;
            }
            Int2IntMap.Entry e = (Int2IntMap.Entry)iterator.next();
            this.key[i] = e.getIntKey();
            this.value[i] = e.getIntValue();
            ++i;
        }
    }

    public Int2IntArrayMap(Map<? extends Integer, ? extends Integer> m) {
        this(m.size());
        int i = 0;
        Iterator<Map.Entry<? extends Integer, ? extends Integer>> iterator = m.entrySet().iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.size = i;
                return;
            }
            Map.Entry<? extends Integer, ? extends Integer> e = iterator.next();
            this.key[i] = e.getKey();
            this.value[i] = e.getValue();
            ++i;
        }
    }

    public Int2IntArrayMap(int[] key, int[] value, int size) {
        this.key = key;
        this.value = value;
        this.size = size;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
        if (size <= key.length) return;
        throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
    }

    public Int2IntMap.FastEntrySet int2IntEntrySet() {
        if (this.entries != null) return this.entries;
        this.entries = new EntrySet();
        return this.entries;
    }

    private int findKey(int k) {
        int[] key = this.key;
        int i = this.size;
        do {
            if (i-- == 0) return -1;
        } while (key[i] != k);
        return i;
    }

    @Override
    public int get(int k) {
        int[] key = this.key;
        int i = this.size;
        do {
            if (i-- == 0) return this.defRetValue;
        } while (key[i] != k);
        return this.value[i];
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
        if (this.findKey(k) == -1) return false;
        return true;
    }

    @Override
    public boolean containsValue(int v) {
        int i = this.size;
        do {
            if (i-- == 0) return false;
        } while (this.value[i] != v);
        return true;
    }

    @Override
    public boolean isEmpty() {
        if (this.size != 0) return false;
        return true;
    }

    @Override
    public int put(int k, int v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            int oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            int[] newKey = new int[this.size == 0 ? 2 : this.size * 2];
            int[] newValue = new int[this.size == 0 ? 2 : this.size * 2];
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
    public int remove(int k) {
        int oldPos = this.findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        int oldValue = this.value[oldPos];
        int tail = this.size - oldPos - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        --this.size;
        return oldValue;
    }

    @Override
    public IntSet keySet() {
        if (this.keys != null) return this.keys;
        this.keys = new KeySet();
        return this.keys;
    }

    @Override
    public IntCollection values() {
        if (this.values != null) return this.values;
        this.values = new ValuesCollection();
        return this.values;
    }

    public Int2IntArrayMap clone() {
        Int2IntArrayMap c;
        try {
            c = (Int2IntArrayMap)super.clone();
        }
        catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (int[])this.key.clone();
        c.value = (int[])this.value.clone();
        c.entries = null;
        c.keys = null;
        c.values = null;
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        int i = 0;
        int max = this.size;
        while (i < max) {
            s.writeInt(this.key[i]);
            s.writeInt(this.value[i]);
            ++i;
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new int[this.size];
        this.value = new int[this.size];
        int i = 0;
        while (i < this.size) {
            this.key[i] = s.readInt();
            this.value[i] = s.readInt();
            ++i;
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2IntMap.Entry>
    implements Int2IntMap.FastEntrySet {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Int2IntMap.Entry> iterator() {
            return new ObjectIterator<Int2IntMap.Entry>(){
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    if (this.next >= Int2IntArrayMap.this.size) return false;
                    return true;
                }

                @Override
                public Int2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[this.curr], Int2IntArrayMap.this.value[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Int2IntArrayMap.this.size-- - this.next--;
                    System.arraycopy(Int2IntArrayMap.this.key, this.next + 1, Int2IntArrayMap.this.key, this.next, tail);
                    System.arraycopy(Int2IntArrayMap.this.value, this.next + 1, Int2IntArrayMap.this.value, this.next, tail);
                }

                @Override
                public void forEachRemaining(Consumer<? super Int2IntMap.Entry> action) {
                    int max = Int2IntArrayMap.this.size;
                    while (this.next < max) {
                        this.curr = this.next;
                        action.accept(new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[this.curr], Int2IntArrayMap.this.value[this.next++]));
                    }
                }
            };
        }

        @Override
        public ObjectIterator<Int2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Int2IntMap.Entry>(){
                int next = 0;
                int curr = -1;
                final AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();

                @Override
                public boolean hasNext() {
                    if (this.next >= Int2IntArrayMap.this.size) return false;
                    return true;
                }

                @Override
                public Int2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2IntArrayMap.this.key[this.curr];
                    this.entry.value = Int2IntArrayMap.this.value[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Int2IntArrayMap.this.size-- - this.next--;
                    System.arraycopy(Int2IntArrayMap.this.key, this.next + 1, Int2IntArrayMap.this.key, this.next, tail);
                    System.arraycopy(Int2IntArrayMap.this.value, this.next + 1, Int2IntArrayMap.this.value, this.next, tail);
                }

                @Override
                public void forEachRemaining(Consumer<? super Int2IntMap.Entry> action) {
                    int max = Int2IntArrayMap.this.size;
                    while (this.next < max) {
                        this.curr = this.next;
                        this.entry.key = Int2IntArrayMap.this.key[this.curr];
                        this.entry.value = Int2IntArrayMap.this.value[this.next++];
                        action.accept(this.entry);
                    }
                }
            };
        }

        @Override
        public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
            return new EntrySetSpliterator(0, Int2IntArrayMap.this.size);
        }

        @Override
        public void forEach(Consumer<? super Int2IntMap.Entry> action) {
            int i = 0;
            int max = Int2IntArrayMap.this.size;
            while (i < max) {
                action.accept(new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[i], Int2IntArrayMap.this.value[i]));
                ++i;
            }
        }

        @Override
        public void fastForEach(Consumer<? super Int2IntMap.Entry> action) {
            AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
            int i = 0;
            int max = Int2IntArrayMap.this.size;
            while (i < max) {
                entry.key = Int2IntArrayMap.this.key[i];
                entry.value = Int2IntArrayMap.this.value[i];
                action.accept(entry);
                ++i;
            }
        }

        @Override
        public int size() {
            return Int2IntArrayMap.this.size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null) return false;
            if (!(e.getKey() instanceof Integer)) {
                return false;
            }
            if (e.getValue() == null) return false;
            if (!(e.getValue() instanceof Integer)) {
                return false;
            }
            int k = (Integer)e.getKey();
            if (!Int2IntArrayMap.this.containsKey(k)) return false;
            if (Int2IntArrayMap.this.get(k) != ((Integer)e.getValue()).intValue()) return false;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getKey() == null) return false;
            if (!(e.getKey() instanceof Integer)) {
                return false;
            }
            if (e.getValue() == null) return false;
            if (!(e.getValue() instanceof Integer)) {
                return false;
            }
            int k = (Integer)e.getKey();
            int v = (Integer)e.getValue();
            int oldPos = Int2IntArrayMap.this.findKey(k);
            if (oldPos == -1) return false;
            if (v != Int2IntArrayMap.this.value[oldPos]) {
                return false;
            }
            int tail = Int2IntArrayMap.this.size - oldPos - 1;
            System.arraycopy(Int2IntArrayMap.this.key, oldPos + 1, Int2IntArrayMap.this.key, oldPos, tail);
            System.arraycopy(Int2IntArrayMap.this.value, oldPos + 1, Int2IntArrayMap.this.value, oldPos, tail);
            Int2IntArrayMap.this.size--;
            return true;
        }

        final class EntrySetSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Int2IntMap.Entry>
        implements ObjectSpliterator<Int2IntMap.Entry> {
            EntrySetSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
            }

            @Override
            public int characteristics() {
                return 16465;
            }

            @Override
            protected final Int2IntMap.Entry get(int location) {
                return new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[location], Int2IntArrayMap.this.value[location]);
            }

            protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
                return new EntrySetSpliterator(pos, maxPos);
            }
        }
    }

    private final class KeySet
    extends AbstractIntSet {
        private KeySet() {
        }

        @Override
        public boolean contains(int k) {
            if (Int2IntArrayMap.this.findKey(k) == -1) return false;
            return true;
        }

        @Override
        public boolean remove(int k) {
            int oldPos = Int2IntArrayMap.this.findKey(k);
            if (oldPos == -1) {
                return false;
            }
            int tail = Int2IntArrayMap.this.size - oldPos - 1;
            System.arraycopy(Int2IntArrayMap.this.key, oldPos + 1, Int2IntArrayMap.this.key, oldPos, tail);
            System.arraycopy(Int2IntArrayMap.this.value, oldPos + 1, Int2IntArrayMap.this.value, oldPos, tail);
            Int2IntArrayMap.this.size--;
            return true;
        }

        @Override
        public IntIterator iterator() {
            return new IntIterator(){
                int pos = 0;

                @Override
                public boolean hasNext() {
                    if (this.pos >= Int2IntArrayMap.this.size) return false;
                    return true;
                }

                @Override
                public int nextInt() {
                    if (this.hasNext()) return Int2IntArrayMap.this.key[this.pos++];
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    int tail = Int2IntArrayMap.this.size - this.pos;
                    System.arraycopy(Int2IntArrayMap.this.key, this.pos, Int2IntArrayMap.this.key, this.pos - 1, tail);
                    System.arraycopy(Int2IntArrayMap.this.value, this.pos, Int2IntArrayMap.this.value, this.pos - 1, tail);
                    Int2IntArrayMap.this.size--;
                    --this.pos;
                }

                @Override
                public void forEachRemaining(IntConsumer action) {
                    int max = Int2IntArrayMap.this.size;
                    while (this.pos < max) {
                        action.accept(Int2IntArrayMap.this.key[this.pos++]);
                    }
                }
            };
        }

        @Override
        public IntSpliterator spliterator() {
            return new KeySetSpliterator(0, Int2IntArrayMap.this.size);
        }

        @Override
        public void forEach(IntConsumer action) {
            int i = 0;
            int max = Int2IntArrayMap.this.size;
            while (i < max) {
                action.accept(Int2IntArrayMap.this.key[i]);
                ++i;
            }
        }

        @Override
        public int size() {
            return Int2IntArrayMap.this.size;
        }

        @Override
        public void clear() {
            Int2IntArrayMap.this.clear();
        }

        final class KeySetSpliterator
        extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
        implements IntSpliterator {
            KeySetSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
            }

            @Override
            public int characteristics() {
                return 16721;
            }

            @Override
            protected final int get(int location) {
                return Int2IntArrayMap.this.key[location];
            }

            @Override
            protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
                return new KeySetSpliterator(pos, maxPos);
            }

            @Override
            public void forEachRemaining(IntConsumer action) {
                int max = Int2IntArrayMap.this.size;
                while (this.pos < max) {
                    action.accept(Int2IntArrayMap.this.key[this.pos++]);
                }
            }
        }
    }

    private final class ValuesCollection
    extends AbstractIntCollection {
        private ValuesCollection() {
        }

        @Override
        public boolean contains(int v) {
            return Int2IntArrayMap.this.containsValue(v);
        }

        @Override
        public IntIterator iterator() {
            return new IntIterator(){
                int pos = 0;

                @Override
                public boolean hasNext() {
                    if (this.pos >= Int2IntArrayMap.this.size) return false;
                    return true;
                }

                @Override
                public int nextInt() {
                    if (this.hasNext()) return Int2IntArrayMap.this.value[this.pos++];
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    int tail = Int2IntArrayMap.this.size - this.pos;
                    System.arraycopy(Int2IntArrayMap.this.key, this.pos, Int2IntArrayMap.this.key, this.pos - 1, tail);
                    System.arraycopy(Int2IntArrayMap.this.value, this.pos, Int2IntArrayMap.this.value, this.pos - 1, tail);
                    Int2IntArrayMap.this.size--;
                    --this.pos;
                }

                @Override
                public void forEachRemaining(IntConsumer action) {
                    int max = Int2IntArrayMap.this.size;
                    while (this.pos < max) {
                        action.accept(Int2IntArrayMap.this.value[this.pos++]);
                    }
                }
            };
        }

        @Override
        public IntSpliterator spliterator() {
            return new ValuesSpliterator(0, Int2IntArrayMap.this.size);
        }

        @Override
        public void forEach(IntConsumer action) {
            int i = 0;
            int max = Int2IntArrayMap.this.size;
            while (i < max) {
                action.accept(Int2IntArrayMap.this.value[i]);
                ++i;
            }
        }

        @Override
        public int size() {
            return Int2IntArrayMap.this.size;
        }

        @Override
        public void clear() {
            Int2IntArrayMap.this.clear();
        }

        final class ValuesSpliterator
        extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
        implements IntSpliterator {
            ValuesSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
            }

            @Override
            public int characteristics() {
                return 16720;
            }

            @Override
            protected final int get(int location) {
                return Int2IntArrayMap.this.value[location];
            }

            @Override
            protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
                return new ValuesSpliterator(pos, maxPos);
            }

            @Override
            public void forEachRemaining(IntConsumer action) {
                int max = Int2IntArrayMap.this.size;
                while (this.pos < max) {
                    action.accept(Int2IntArrayMap.this.value[this.pos++]);
                }
            }
        }
    }
}

