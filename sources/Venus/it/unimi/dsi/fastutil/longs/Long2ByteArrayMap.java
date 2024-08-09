/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2ByteMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
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
public class Long2ByteArrayMap
extends AbstractLong2ByteMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient byte[] value;
    private int size;

    public Long2ByteArrayMap(long[] lArray, byte[] byArray) {
        this.key = lArray;
        this.value = byArray;
        this.size = lArray.length;
        if (lArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + byArray.length + ")");
        }
    }

    public Long2ByteArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = ByteArrays.EMPTY_ARRAY;
    }

    public Long2ByteArrayMap(int n) {
        this.key = new long[n];
        this.value = new byte[n];
    }

    public Long2ByteArrayMap(Long2ByteMap long2ByteMap) {
        this(long2ByteMap.size());
        this.putAll(long2ByteMap);
    }

    public Long2ByteArrayMap(Map<? extends Long, ? extends Byte> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2ByteArrayMap(long[] lArray, byte[] byArray, int n) {
        this.key = lArray;
        this.value = byArray;
        this.size = n;
        if (lArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + byArray.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2ByteMap.FastEntrySet long2ByteEntrySet() {
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
    public byte get(long l) {
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
    public boolean containsValue(byte by) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != by) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public byte put(long l, byte by) {
        int n = this.findKey(l);
        if (n != -1) {
            byte by2 = this.value[n];
            this.value[n] = by;
            return by2;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.key[n2];
                byArray[n2] = this.value[n2];
            }
            this.key = lArray;
            this.value = byArray;
        }
        this.key[this.size] = l;
        this.value[this.size] = by;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public byte remove(long l) {
        int n = this.findKey(l);
        if (n == -1) {
            return this.defRetValue;
        }
        byte by = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return by;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2ByteArrayMap this$0;
            {
                this.this$0 = long2ByteArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2ByteArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2ByteArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2ByteArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2ByteArrayMap.access$100(this.this$0), n + 1, Long2ByteArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2ByteArrayMap.access$200(this.this$0), n + 1, Long2ByteArrayMap.access$200(this.this$0), n, n2);
                Long2ByteArrayMap.access$010(this.this$0);
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
                        return this.pos < Long2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2ByteArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Long2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Long2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2ByteArrayMap.access$000(this.this$0);
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
    public ByteCollection values() {
        return new AbstractByteCollection(this){
            final Long2ByteArrayMap this$0;
            {
                this.this$0 = long2ByteArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return this.this$0.containsValue(by);
            }

            @Override
            public ByteIterator iterator() {
                return new ByteIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Long2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2ByteArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Long2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Long2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2ByteArrayMap.access$000(this.this$0);
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

    public Long2ByteArrayMap clone() {
        Long2ByteArrayMap long2ByteArrayMap;
        try {
            long2ByteArrayMap = (Long2ByteArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2ByteArrayMap.key = (long[])this.key.clone();
        long2ByteArrayMap.value = (byte[])this.value.clone();
        return long2ByteArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeByte(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new byte[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readByte();
        }
    }

    public ObjectSet long2ByteEntrySet() {
        return this.long2ByteEntrySet();
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

    static int access$000(Long2ByteArrayMap long2ByteArrayMap) {
        return long2ByteArrayMap.size;
    }

    static long[] access$100(Long2ByteArrayMap long2ByteArrayMap) {
        return long2ByteArrayMap.key;
    }

    static byte[] access$200(Long2ByteArrayMap long2ByteArrayMap) {
        return long2ByteArrayMap.value;
    }

    static int access$010(Long2ByteArrayMap long2ByteArrayMap) {
        return long2ByteArrayMap.size--;
    }

    static int access$300(Long2ByteArrayMap long2ByteArrayMap, long l) {
        return long2ByteArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2ByteMap.Entry>
    implements Long2ByteMap.FastEntrySet {
        final Long2ByteArrayMap this$0;

        private EntrySet(Long2ByteArrayMap long2ByteArrayMap) {
            this.this$0 = long2ByteArrayMap;
        }

        @Override
        public ObjectIterator<Long2ByteMap.Entry> iterator() {
            return new ObjectIterator<Long2ByteMap.Entry>(this){
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
                    return this.next < Long2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2ByteMap.BasicEntry(Long2ByteArrayMap.access$100(this.this$1.this$0)[this.curr], Long2ByteArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2ByteMap.Entry> fastIterator() {
            return new ObjectIterator<Long2ByteMap.Entry>(this){
                int next;
                int curr;
                final AbstractLong2ByteMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2ByteMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2ByteArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2ByteArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2ByteArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && this.this$0.get(l) == ((Byte)entry.getValue()).byteValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            long l = (Long)entry.getKey();
            byte by = (Byte)entry.getValue();
            int n = Long2ByteArrayMap.access$300(this.this$0, l);
            if (n == -1 || by != Long2ByteArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Long2ByteArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2ByteArrayMap.access$100(this.this$0), n + 1, Long2ByteArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2ByteArrayMap.access$200(this.this$0), n + 1, Long2ByteArrayMap.access$200(this.this$0), n, n2);
            Long2ByteArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2ByteArrayMap long2ByteArrayMap, 1 var2_2) {
            this(long2ByteArrayMap);
        }
    }
}

