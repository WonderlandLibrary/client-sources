/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2LongMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2LongMap;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Int2LongArrayMap
extends AbstractInt2LongMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient long[] value;
    private int size;

    public Int2LongArrayMap(int[] nArray, long[] lArray) {
        this.key = nArray;
        this.value = lArray;
        this.size = nArray.length;
        if (nArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + lArray.length + ")");
        }
    }

    public Int2LongArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Int2LongArrayMap(int n) {
        this.key = new int[n];
        this.value = new long[n];
    }

    public Int2LongArrayMap(Int2LongMap int2LongMap) {
        this(int2LongMap.size());
        this.putAll(int2LongMap);
    }

    public Int2LongArrayMap(Map<? extends Integer, ? extends Long> map) {
        this(map.size());
        this.putAll(map);
    }

    public Int2LongArrayMap(int[] nArray, long[] lArray, int n) {
        this.key = nArray;
        this.value = lArray;
        this.size = n;
        if (nArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + lArray.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2LongMap.FastEntrySet int2LongEntrySet() {
        return new EntrySet(this, null);
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
    public long get(int n) {
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
    public boolean containsValue(long l) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != l) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public long put(int n, long l) {
        int n2 = this.findKey(n);
        if (n2 != -1) {
            long l2 = this.value[n2];
            this.value[n2] = l;
            return l2;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int n3 = this.size;
            while (n3-- != 0) {
                nArray[n3] = this.key[n3];
                lArray[n3] = this.value[n3];
            }
            this.key = nArray;
            this.value = lArray;
        }
        this.key[this.size] = n;
        this.value[this.size] = l;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public long remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        long l = this.value[n2];
        int n3 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n3);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n3);
        --this.size;
        return l;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final Int2LongArrayMap this$0;
            {
                this.this$0 = int2LongArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return Int2LongArrayMap.access$300(this.this$0, n) != -1;
            }

            @Override
            public boolean remove(int n) {
                int n2 = Int2LongArrayMap.access$300(this.this$0, n);
                if (n2 == -1) {
                    return true;
                }
                int n3 = Int2LongArrayMap.access$000(this.this$0) - n2 - 1;
                System.arraycopy(Int2LongArrayMap.access$100(this.this$0), n2 + 1, Int2LongArrayMap.access$100(this.this$0), n2, n3);
                System.arraycopy(Int2LongArrayMap.access$200(this.this$0), n2 + 1, Int2LongArrayMap.access$200(this.this$0), n2, n3);
                Int2LongArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public IntIterator iterator() {
                return new IntIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Int2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2LongArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2LongArrayMap.access$100(this.this$1.this$0), this.pos, Int2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2LongArrayMap.access$200(this.this$1.this$0), this.pos, Int2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2LongArrayMap.access$000(this.this$0);
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
    public LongCollection values() {
        return new AbstractLongCollection(this){
            final Int2LongArrayMap this$0;
            {
                this.this$0 = int2LongArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return this.this$0.containsValue(l);
            }

            @Override
            public LongIterator iterator() {
                return new LongIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Int2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2LongArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2LongArrayMap.access$100(this.this$1.this$0), this.pos, Int2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2LongArrayMap.access$200(this.this$1.this$0), this.pos, Int2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2LongArrayMap.access$000(this.this$0);
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

    public Int2LongArrayMap clone() {
        Int2LongArrayMap int2LongArrayMap;
        try {
            int2LongArrayMap = (Int2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2LongArrayMap.key = (int[])this.key.clone();
        int2LongArrayMap.value = (long[])this.value.clone();
        return int2LongArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readLong();
        }
    }

    public ObjectSet int2LongEntrySet() {
        return this.int2LongEntrySet();
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

    static int access$000(Int2LongArrayMap int2LongArrayMap) {
        return int2LongArrayMap.size;
    }

    static int[] access$100(Int2LongArrayMap int2LongArrayMap) {
        return int2LongArrayMap.key;
    }

    static long[] access$200(Int2LongArrayMap int2LongArrayMap) {
        return int2LongArrayMap.value;
    }

    static int access$010(Int2LongArrayMap int2LongArrayMap) {
        return int2LongArrayMap.size--;
    }

    static int access$300(Int2LongArrayMap int2LongArrayMap, int n) {
        return int2LongArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2LongMap.Entry>
    implements Int2LongMap.FastEntrySet {
        final Int2LongArrayMap this$0;

        private EntrySet(Int2LongArrayMap int2LongArrayMap) {
            this.this$0 = int2LongArrayMap;
        }

        @Override
        public ObjectIterator<Int2LongMap.Entry> iterator() {
            return new ObjectIterator<Int2LongMap.Entry>(this){
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
                    return this.next < Int2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2LongMap.BasicEntry(Int2LongArrayMap.access$100(this.this$1.this$0)[this.curr], Int2LongArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2LongMap.Entry> fastIterator() {
            return new ObjectIterator<Int2LongMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2LongMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2LongMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Int2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2LongArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Int2LongArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Int2LongArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && this.this$0.get(n) == ((Long)entry.getValue()).longValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            long l = (Long)entry.getValue();
            int n2 = Int2LongArrayMap.access$300(this.this$0, n);
            if (n2 == -1 || l != Int2LongArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Int2LongArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Int2LongArrayMap.access$100(this.this$0), n2 + 1, Int2LongArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Int2LongArrayMap.access$200(this.this$0), n2 + 1, Int2LongArrayMap.access$200(this.this$0), n2, n3);
            Int2LongArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Int2LongArrayMap int2LongArrayMap, 1 var2_2) {
            this(int2LongArrayMap);
        }
    }
}

