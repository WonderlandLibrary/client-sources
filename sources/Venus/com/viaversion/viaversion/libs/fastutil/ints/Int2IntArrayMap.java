/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Int2IntArrayMap
extends AbstractInt2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    protected transient int[] key;
    protected transient int[] value;
    protected int size;
    protected transient Int2IntMap.FastEntrySet entries;
    protected transient IntSet keys;
    protected transient IntCollection values;

    public Int2IntArrayMap(int[] nArray, int[] nArray2) {
        this.key = nArray;
        this.value = nArray2;
        this.size = nArray.length;
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + nArray2.length + ")");
        }
    }

    public Int2IntArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Int2IntArrayMap(int n) {
        this.key = new int[n];
        this.value = new int[n];
    }

    public Int2IntArrayMap(Int2IntMap int2IntMap) {
        this(int2IntMap.size());
        int n = 0;
        for (Int2IntMap.Entry entry : int2IntMap.int2IntEntrySet()) {
            this.key[n] = entry.getIntKey();
            this.value[n] = entry.getIntValue();
            ++n;
        }
        this.size = n;
    }

    public Int2IntArrayMap(Map<? extends Integer, ? extends Integer> map) {
        this(map.size());
        int n = 0;
        for (Map.Entry<? extends Integer, ? extends Integer> entry : map.entrySet()) {
            this.key[n] = entry.getKey();
            this.value[n] = entry.getValue();
            ++n;
        }
        this.size = n;
    }

    public Int2IntArrayMap(int[] nArray, int[] nArray2, int n) {
        this.key = nArray;
        this.value = nArray2;
        this.size = n;
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + nArray2.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2IntMap.FastEntrySet int2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new EntrySet(this, null);
        }
        return this.entries;
    }

    private int findKey(int n) {
        int[] nArray = this.key;
        int n2 = this.size;
        while (n2-- != 0) {
            if (nArray[n2] != n) continue;
            return n2;
        }
        return 1;
    }

    @Override
    public int get(int n) {
        int[] nArray = this.key;
        int n2 = this.size;
        while (n2-- != 0) {
            if (nArray[n2] != n) continue;
            return this.value[n2];
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
    public boolean containsKey(int n) {
        return this.findKey(n) != -1;
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
    public int put(int n, int n2) {
        int n3 = this.findKey(n);
        if (n3 != -1) {
            int n4 = this.value[n3];
            this.value[n3] = n2;
            return n4;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int[] nArray2 = new int[this.size == 0 ? 2 : this.size * 2];
            int n5 = this.size;
            while (n5-- != 0) {
                nArray[n5] = this.key[n5];
                nArray2[n5] = this.value[n5];
            }
            this.key = nArray;
            this.value = nArray2;
        }
        this.key[this.size] = n;
        this.value[this.size] = n2;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        int n4 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n4);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n4);
        --this.size;
        return n3;
    }

    @Override
    public IntSet keySet() {
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

    public Int2IntArrayMap clone() {
        Int2IntArrayMap int2IntArrayMap;
        try {
            int2IntArrayMap = (Int2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2IntArrayMap.key = (int[])this.key.clone();
        int2IntArrayMap.value = (int[])this.value.clone();
        int2IntArrayMap.entries = null;
        int2IntArrayMap.keys = null;
        int2IntArrayMap.values = null;
        return int2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = this.size;
        for (int i = 0; i < n; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readInt();
        }
    }

    public ObjectSet int2IntEntrySet() {
        return this.int2IntEntrySet();
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

    static int access$000(Int2IntArrayMap int2IntArrayMap, int n) {
        return int2IntArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2IntMap.Entry>
    implements Int2IntMap.FastEntrySet {
        final Int2IntArrayMap this$0;

        private EntrySet(Int2IntArrayMap int2IntArrayMap) {
            this.this$0 = int2IntArrayMap;
        }

        @Override
        public ObjectIterator<Int2IntMap.Entry> iterator() {
            return new ObjectIterator<Int2IntMap.Entry>(this){
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
                public Int2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2IntMap.BasicEntry(this.this$1.this$0.key[this.curr], this.this$1.this$0.value[this.next++]);
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
                }

                @Override
                public void forEachRemaining(Consumer<? super Int2IntMap.Entry> consumer) {
                    int n = this.this$1.this$0.size;
                    while (this.next < n) {
                        this.curr = this.next;
                        consumer.accept(new AbstractInt2IntMap.BasicEntry(this.this$1.this$0.key[this.curr], this.this$1.this$0.value[this.next++]));
                    }
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Int2IntMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2IntMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < this.this$1.this$0.size;
                }

                @Override
                public Int2IntMap.Entry next() {
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
                }

                @Override
                public void forEachRemaining(Consumer<? super Int2IntMap.Entry> consumer) {
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
        public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
            return new EntrySetSpliterator(this, 0, this.this$0.size);
        }

        @Override
        public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
            int n = this.this$0.size;
            for (int i = 0; i < n; ++i) {
                consumer.accept(new AbstractInt2IntMap.BasicEntry(this.this$0.key[i], this.this$0.value[i]));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
            AbstractInt2IntMap.BasicEntry basicEntry = new AbstractInt2IntMap.BasicEntry();
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
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && this.this$0.get(n) == ((Integer)entry.getValue()).intValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            int n2 = (Integer)entry.getValue();
            int n3 = Int2IntArrayMap.access$000(this.this$0, n);
            if (n3 == -1 || n2 != this.this$0.value[n3]) {
                return true;
            }
            int n4 = this.this$0.size - n3 - 1;
            System.arraycopy(this.this$0.key, n3 + 1, this.this$0.key, n3, n4);
            System.arraycopy(this.this$0.value, n3 + 1, this.this$0.value, n3, n4);
            --this.this$0.size;
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

        EntrySet(Int2IntArrayMap int2IntArrayMap, 1 var2_2) {
            this(int2IntArrayMap);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        final class EntrySetSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Int2IntMap.Entry>
        implements ObjectSpliterator<Int2IntMap.Entry> {
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
            protected final Int2IntMap.Entry get(int n) {
                return new AbstractInt2IntMap.BasicEntry(this.this$1.this$0.key[n], this.this$1.this$0.value[n]);
            }

            protected final EntrySetSpliterator makeForSplit(int n, int n2) {
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class KeySet
    extends AbstractIntSet {
        final Int2IntArrayMap this$0;

        private KeySet(Int2IntArrayMap int2IntArrayMap) {
            this.this$0 = int2IntArrayMap;
        }

        @Override
        public boolean contains(int n) {
            return Int2IntArrayMap.access$000(this.this$0, n) != -1;
        }

        @Override
        public boolean remove(int n) {
            int n2 = Int2IntArrayMap.access$000(this.this$0, n);
            if (n2 == -1) {
                return true;
            }
            int n3 = this.this$0.size - n2 - 1;
            System.arraycopy(this.this$0.key, n2 + 1, this.this$0.key, n2, n3);
            System.arraycopy(this.this$0.value, n2 + 1, this.this$0.value, n2, n3);
            --this.this$0.size;
            return false;
        }

        @Override
        public IntIterator iterator() {
            return new IntIterator(this){
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
                public int nextInt() {
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
                }

                @Override
                public void forEachRemaining(IntConsumer intConsumer) {
                    int n = this.this$1.this$0.size;
                    while (this.pos < n) {
                        intConsumer.accept(this.this$1.this$0.key[this.pos++]);
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
            return new KeySetSpliterator(this, 0, this.this$0.size);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            int n = this.this$0.size;
            for (int i = 0; i < n; ++i) {
                intConsumer.accept(this.this$0.key[i]);
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

        KeySet(Int2IntArrayMap int2IntArrayMap, 1 var2_2) {
            this(int2IntArrayMap);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        final class KeySetSpliterator
        extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
        implements IntSpliterator {
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
            protected final int get(int n) {
                return this.this$1.this$0.key[n];
            }

            @Override
            protected final KeySetSpliterator makeForSplit(int n, int n2) {
                return new KeySetSpliterator(this.this$1, n, n2);
            }

            @Override
            public void forEachRemaining(IntConsumer intConsumer) {
                int n = this.this$1.this$0.size;
                while (this.pos < n) {
                    intConsumer.accept(this.this$1.this$0.key[this.pos++]);
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ValuesCollection
    extends AbstractIntCollection {
        final Int2IntArrayMap this$0;

        private ValuesCollection(Int2IntArrayMap int2IntArrayMap) {
            this.this$0 = int2IntArrayMap;
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

        ValuesCollection(Int2IntArrayMap int2IntArrayMap, 1 var2_2) {
            this(int2IntArrayMap);
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

            @Override
            protected final ValuesSpliterator makeForSplit(int n, int n2) {
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

