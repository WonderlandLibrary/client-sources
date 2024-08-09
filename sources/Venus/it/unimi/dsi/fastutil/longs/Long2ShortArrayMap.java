/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2ShortMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ShortMap;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
public class Long2ShortArrayMap
extends AbstractLong2ShortMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient short[] value;
    private int size;

    public Long2ShortArrayMap(long[] lArray, short[] sArray) {
        this.key = lArray;
        this.value = sArray;
        this.size = lArray.length;
        if (lArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + sArray.length + ")");
        }
    }

    public Long2ShortArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = ShortArrays.EMPTY_ARRAY;
    }

    public Long2ShortArrayMap(int n) {
        this.key = new long[n];
        this.value = new short[n];
    }

    public Long2ShortArrayMap(Long2ShortMap long2ShortMap) {
        this(long2ShortMap.size());
        this.putAll(long2ShortMap);
    }

    public Long2ShortArrayMap(Map<? extends Long, ? extends Short> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2ShortArrayMap(long[] lArray, short[] sArray, int n) {
        this.key = lArray;
        this.value = sArray;
        this.size = n;
        if (lArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + sArray.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2ShortMap.FastEntrySet long2ShortEntrySet() {
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
    public short get(long l) {
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
    public boolean containsValue(short s) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public short put(long l, short s) {
        int n = this.findKey(l);
        if (n != -1) {
            short s2 = this.value[n];
            this.value[n] = s;
            return s2;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.key[n2];
                sArray[n2] = this.value[n2];
            }
            this.key = lArray;
            this.value = sArray;
        }
        this.key[this.size] = l;
        this.value[this.size] = s;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public short remove(long l) {
        int n = this.findKey(l);
        if (n == -1) {
            return this.defRetValue;
        }
        short s = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return s;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2ShortArrayMap this$0;
            {
                this.this$0 = long2ShortArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2ShortArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2ShortArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2ShortArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2ShortArrayMap.access$100(this.this$0), n + 1, Long2ShortArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2ShortArrayMap.access$200(this.this$0), n + 1, Long2ShortArrayMap.access$200(this.this$0), n, n2);
                Long2ShortArrayMap.access$010(this.this$0);
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
                        return this.pos < Long2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2ShortArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Long2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Long2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2ShortArrayMap.access$000(this.this$0);
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
    public ShortCollection values() {
        return new AbstractShortCollection(this){
            final Long2ShortArrayMap this$0;
            {
                this.this$0 = long2ShortArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return this.this$0.containsValue(s);
            }

            @Override
            public ShortIterator iterator() {
                return new ShortIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Long2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2ShortArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Long2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Long2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2ShortArrayMap.access$000(this.this$0);
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

    public Long2ShortArrayMap clone() {
        Long2ShortArrayMap long2ShortArrayMap;
        try {
            long2ShortArrayMap = (Long2ShortArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2ShortArrayMap.key = (long[])this.key.clone();
        long2ShortArrayMap.value = (short[])this.value.clone();
        return long2ShortArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeShort(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readShort();
        }
    }

    public ObjectSet long2ShortEntrySet() {
        return this.long2ShortEntrySet();
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

    static int access$000(Long2ShortArrayMap long2ShortArrayMap) {
        return long2ShortArrayMap.size;
    }

    static long[] access$100(Long2ShortArrayMap long2ShortArrayMap) {
        return long2ShortArrayMap.key;
    }

    static short[] access$200(Long2ShortArrayMap long2ShortArrayMap) {
        return long2ShortArrayMap.value;
    }

    static int access$010(Long2ShortArrayMap long2ShortArrayMap) {
        return long2ShortArrayMap.size--;
    }

    static int access$300(Long2ShortArrayMap long2ShortArrayMap, long l) {
        return long2ShortArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2ShortMap.Entry>
    implements Long2ShortMap.FastEntrySet {
        final Long2ShortArrayMap this$0;

        private EntrySet(Long2ShortArrayMap long2ShortArrayMap) {
            this.this$0 = long2ShortArrayMap;
        }

        @Override
        public ObjectIterator<Long2ShortMap.Entry> iterator() {
            return new ObjectIterator<Long2ShortMap.Entry>(this){
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
                    return this.next < Long2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2ShortMap.BasicEntry(Long2ShortArrayMap.access$100(this.this$1.this$0)[this.curr], Long2ShortArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2ShortMap.Entry> fastIterator() {
            return new ObjectIterator<Long2ShortMap.Entry>(this){
                int next;
                int curr;
                final AbstractLong2ShortMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2ShortMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2ShortArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2ShortArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2ShortArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && this.this$0.get(l) == ((Short)entry.getValue()).shortValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            long l = (Long)entry.getKey();
            short s = (Short)entry.getValue();
            int n = Long2ShortArrayMap.access$300(this.this$0, l);
            if (n == -1 || s != Long2ShortArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Long2ShortArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2ShortArrayMap.access$100(this.this$0), n + 1, Long2ShortArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2ShortArrayMap.access$200(this.this$0), n + 1, Long2ShortArrayMap.access$200(this.this$0), n, n2);
            Long2ShortArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2ShortArrayMap long2ShortArrayMap, 1 var2_2) {
            this(long2ShortArrayMap);
        }
    }
}

