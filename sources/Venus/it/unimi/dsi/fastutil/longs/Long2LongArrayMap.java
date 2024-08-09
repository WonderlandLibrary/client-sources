/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2LongMap;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
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
public class Long2LongArrayMap
extends AbstractLong2LongMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient long[] value;
    private int size;

    public Long2LongArrayMap(long[] lArray, long[] lArray2) {
        this.key = lArray;
        this.value = lArray2;
        this.size = lArray.length;
        if (lArray.length != lArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + lArray2.length + ")");
        }
    }

    public Long2LongArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Long2LongArrayMap(int n) {
        this.key = new long[n];
        this.value = new long[n];
    }

    public Long2LongArrayMap(Long2LongMap long2LongMap) {
        this(long2LongMap.size());
        this.putAll(long2LongMap);
    }

    public Long2LongArrayMap(Map<? extends Long, ? extends Long> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2LongArrayMap(long[] lArray, long[] lArray2, int n) {
        this.key = lArray;
        this.value = lArray2;
        this.size = n;
        if (lArray.length != lArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + lArray2.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2LongMap.FastEntrySet long2LongEntrySet() {
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
    public long get(long l) {
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
    public long put(long l, long l2) {
        int n = this.findKey(l);
        if (n != -1) {
            long l3 = this.value[n];
            this.value[n] = l2;
            return l3;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            long[] lArray2 = new long[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.key[n2];
                lArray2[n2] = this.value[n2];
            }
            this.key = lArray;
            this.value = lArray2;
        }
        this.key[this.size] = l;
        this.value[this.size] = l2;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public long remove(long l) {
        int n = this.findKey(l);
        if (n == -1) {
            return this.defRetValue;
        }
        long l2 = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return l2;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2LongArrayMap this$0;
            {
                this.this$0 = long2LongArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2LongArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2LongArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2LongArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2LongArrayMap.access$100(this.this$0), n + 1, Long2LongArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2LongArrayMap.access$200(this.this$0), n + 1, Long2LongArrayMap.access$200(this.this$0), n, n2);
                Long2LongArrayMap.access$010(this.this$0);
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
                        return this.pos < Long2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2LongArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2LongArrayMap.access$100(this.this$1.this$0), this.pos, Long2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2LongArrayMap.access$200(this.this$1.this$0), this.pos, Long2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2LongArrayMap.access$000(this.this$0);
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
            final Long2LongArrayMap this$0;
            {
                this.this$0 = long2LongArrayMap;
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
                        return this.pos < Long2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2LongArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2LongArrayMap.access$100(this.this$1.this$0), this.pos, Long2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2LongArrayMap.access$200(this.this$1.this$0), this.pos, Long2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2LongArrayMap.access$000(this.this$0);
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

    public Long2LongArrayMap clone() {
        Long2LongArrayMap long2LongArrayMap;
        try {
            long2LongArrayMap = (Long2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2LongArrayMap.key = (long[])this.key.clone();
        long2LongArrayMap.value = (long[])this.value.clone();
        return long2LongArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readLong();
        }
    }

    public ObjectSet long2LongEntrySet() {
        return this.long2LongEntrySet();
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

    static int access$000(Long2LongArrayMap long2LongArrayMap) {
        return long2LongArrayMap.size;
    }

    static long[] access$100(Long2LongArrayMap long2LongArrayMap) {
        return long2LongArrayMap.key;
    }

    static long[] access$200(Long2LongArrayMap long2LongArrayMap) {
        return long2LongArrayMap.value;
    }

    static int access$010(Long2LongArrayMap long2LongArrayMap) {
        return long2LongArrayMap.size--;
    }

    static int access$300(Long2LongArrayMap long2LongArrayMap, long l) {
        return long2LongArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2LongMap.Entry>
    implements Long2LongMap.FastEntrySet {
        final Long2LongArrayMap this$0;

        private EntrySet(Long2LongArrayMap long2LongArrayMap) {
            this.this$0 = long2LongArrayMap;
        }

        @Override
        public ObjectIterator<Long2LongMap.Entry> iterator() {
            return new ObjectIterator<Long2LongMap.Entry>(this){
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
                    return this.next < Long2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2LongMap.BasicEntry(Long2LongArrayMap.access$100(this.this$1.this$0)[this.curr], Long2LongArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2LongMap.Entry> fastIterator() {
            return new ObjectIterator<Long2LongMap.Entry>(this){
                int next;
                int curr;
                final AbstractLong2LongMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2LongMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2LongArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2LongArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2LongArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && this.this$0.get(l) == ((Long)entry.getValue()).longValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            long l = (Long)entry.getKey();
            long l2 = (Long)entry.getValue();
            int n = Long2LongArrayMap.access$300(this.this$0, l);
            if (n == -1 || l2 != Long2LongArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Long2LongArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2LongArrayMap.access$100(this.this$0), n + 1, Long2LongArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2LongArrayMap.access$200(this.this$0), n + 1, Long2LongArrayMap.access$200(this.this$0), n, n2);
            Long2LongArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2LongArrayMap long2LongArrayMap, 1 var2_2) {
            this(long2LongArrayMap);
        }
    }
}

