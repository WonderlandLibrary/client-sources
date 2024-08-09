/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
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
public class Long2IntArrayMap
extends AbstractLong2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient int[] value;
    private int size;

    public Long2IntArrayMap(long[] lArray, int[] nArray) {
        this.key = lArray;
        this.value = nArray;
        this.size = lArray.length;
        if (lArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + nArray.length + ")");
        }
    }

    public Long2IntArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Long2IntArrayMap(int n) {
        this.key = new long[n];
        this.value = new int[n];
    }

    public Long2IntArrayMap(Long2IntMap long2IntMap) {
        this(long2IntMap.size());
        this.putAll(long2IntMap);
    }

    public Long2IntArrayMap(Map<? extends Long, ? extends Integer> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2IntArrayMap(long[] lArray, int[] nArray, int n) {
        this.key = lArray;
        this.value = nArray;
        this.size = n;
        if (lArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + nArray.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2IntMap.FastEntrySet long2IntEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(long l) {
        long[] lArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (lArray[n] != l) continue;
            return n;
        }
        return 1;
    }

    @Override
    public int get(long l) {
        long[] lArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (lArray[n] != l) continue;
            return this.value[n];
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
    public boolean containsKey(long l) {
        return this.findKey(l) != -1;
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
    public int put(long l, int n) {
        int n2 = this.findKey(l);
        if (n2 != -1) {
            int n3 = this.value[n2];
            this.value[n2] = n;
            return n3;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n4 = this.size;
            while (n4-- != 0) {
                lArray[n4] = this.key[n4];
                nArray[n4] = this.value[n4];
            }
            this.key = lArray;
            this.value = nArray;
        }
        this.key[this.size] = l;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int remove(long l) {
        int n = this.findKey(l);
        if (n == -1) {
            return this.defRetValue;
        }
        int n2 = this.value[n];
        int n3 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n3);
        System.arraycopy(this.value, n + 1, this.value, n, n3);
        --this.size;
        return n2;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2IntArrayMap this$0;
            {
                this.this$0 = long2IntArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2IntArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2IntArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2IntArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2IntArrayMap.access$100(this.this$0), n + 1, Long2IntArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2IntArrayMap.access$200(this.this$0), n + 1, Long2IntArrayMap.access$200(this.this$0), n, n2);
                Long2IntArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public LongIterator iterator() {
                return new LongIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Long2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2IntArrayMap.access$100(this.this$1.this$0), this.pos, Long2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2IntArrayMap.access$200(this.this$1.this$0), this.pos, Long2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2IntArrayMap.access$000(this.this$0);
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
    public IntCollection values() {
        return new AbstractIntCollection(this){
            final Long2IntArrayMap this$0;
            {
                this.this$0 = long2IntArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsValue(n);
            }

            @Override
            public IntIterator iterator() {
                return new IntIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Long2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2IntArrayMap.access$100(this.this$1.this$0), this.pos, Long2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2IntArrayMap.access$200(this.this$1.this$0), this.pos, Long2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2IntArrayMap.access$000(this.this$0);
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

    public Long2IntArrayMap clone() {
        Long2IntArrayMap long2IntArrayMap;
        try {
            long2IntArrayMap = (Long2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2IntArrayMap.key = (long[])this.key.clone();
        long2IntArrayMap.value = (int[])this.value.clone();
        return long2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readInt();
        }
    }

    public ObjectSet long2IntEntrySet() {
        return this.long2IntEntrySet();
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

    static int access$000(Long2IntArrayMap long2IntArrayMap) {
        return long2IntArrayMap.size;
    }

    static long[] access$100(Long2IntArrayMap long2IntArrayMap) {
        return long2IntArrayMap.key;
    }

    static int[] access$200(Long2IntArrayMap long2IntArrayMap) {
        return long2IntArrayMap.value;
    }

    static int access$010(Long2IntArrayMap long2IntArrayMap) {
        return long2IntArrayMap.size--;
    }

    static int access$300(Long2IntArrayMap long2IntArrayMap, long l) {
        return long2IntArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2IntMap.Entry>
    implements Long2IntMap.FastEntrySet {
        final Long2IntArrayMap this$0;

        private EntrySet(Long2IntArrayMap long2IntArrayMap) {
            this.this$0 = long2IntArrayMap;
        }

        @Override
        public ObjectIterator<Long2IntMap.Entry> iterator() {
            return new ObjectIterator<Long2IntMap.Entry>(this){
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
                    return this.next < Long2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2IntMap.BasicEntry(Long2IntArrayMap.access$100(this.this$1.this$0)[this.curr], Long2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Long2IntMap.Entry>(this){
                int next;
                int curr;
                final AbstractLong2IntMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2IntArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2IntArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && this.this$0.get(l) == ((Integer)entry.getValue()).intValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            long l = (Long)entry.getKey();
            int n = (Integer)entry.getValue();
            int n2 = Long2IntArrayMap.access$300(this.this$0, l);
            if (n2 == -1 || n != Long2IntArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Long2IntArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Long2IntArrayMap.access$100(this.this$0), n2 + 1, Long2IntArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Long2IntArrayMap.access$200(this.this$0), n2 + 1, Long2IntArrayMap.access$200(this.this$0), n2, n3);
            Long2IntArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2IntArrayMap long2IntArrayMap, 1 var2_2) {
            this(long2IntArrayMap);
        }
    }
}

