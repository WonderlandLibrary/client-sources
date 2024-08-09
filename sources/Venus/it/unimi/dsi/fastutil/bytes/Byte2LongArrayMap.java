/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2LongMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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
public class Byte2LongArrayMap
extends AbstractByte2LongMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient long[] value;
    private int size;

    public Byte2LongArrayMap(byte[] byArray, long[] lArray) {
        this.key = byArray;
        this.value = lArray;
        this.size = byArray.length;
        if (byArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + lArray.length + ")");
        }
    }

    public Byte2LongArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Byte2LongArrayMap(int n) {
        this.key = new byte[n];
        this.value = new long[n];
    }

    public Byte2LongArrayMap(Byte2LongMap byte2LongMap) {
        this(byte2LongMap.size());
        this.putAll(byte2LongMap);
    }

    public Byte2LongArrayMap(Map<? extends Byte, ? extends Long> map) {
        this(map.size());
        this.putAll(map);
    }

    public Byte2LongArrayMap(byte[] byArray, long[] lArray, int n) {
        this.key = byArray;
        this.value = lArray;
        this.size = n;
        if (byArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + lArray.length + ")");
        }
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + byArray.length + ")");
        }
    }

    public Byte2LongMap.FastEntrySet byte2LongEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(byte by) {
        byte[] byArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (byArray[n] != by) continue;
            return n;
        }
        return 1;
    }

    @Override
    public long get(byte by) {
        byte[] byArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (byArray[n] != by) continue;
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
    public boolean containsKey(byte by) {
        return this.findKey(by) != -1;
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
    public long put(byte by, long l) {
        int n = this.findKey(by);
        if (n != -1) {
            long l2 = this.value[n];
            this.value[n] = l;
            return l2;
        }
        if (this.size == this.key.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                byArray[n2] = this.key[n2];
                lArray[n2] = this.value[n2];
            }
            this.key = byArray;
            this.value = lArray;
        }
        this.key[this.size] = by;
        this.value[this.size] = l;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public long remove(byte by) {
        int n = this.findKey(by);
        if (n == -1) {
            return this.defRetValue;
        }
        long l = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return l;
    }

    @Override
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final Byte2LongArrayMap this$0;
            {
                this.this$0 = byte2LongArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return Byte2LongArrayMap.access$300(this.this$0, by) != -1;
            }

            @Override
            public boolean remove(byte by) {
                int n = Byte2LongArrayMap.access$300(this.this$0, by);
                if (n == -1) {
                    return true;
                }
                int n2 = Byte2LongArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Byte2LongArrayMap.access$100(this.this$0), n + 1, Byte2LongArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Byte2LongArrayMap.access$200(this.this$0), n + 1, Byte2LongArrayMap.access$200(this.this$0), n, n2);
                Byte2LongArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public ByteIterator iterator() {
                return new ByteIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Byte2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2LongArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2LongArrayMap.access$100(this.this$1.this$0), this.pos, Byte2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2LongArrayMap.access$200(this.this$1.this$0), this.pos, Byte2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2LongArrayMap.access$000(this.this$0);
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
            final Byte2LongArrayMap this$0;
            {
                this.this$0 = byte2LongArrayMap;
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
                        return this.pos < Byte2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2LongArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2LongArrayMap.access$100(this.this$1.this$0), this.pos, Byte2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2LongArrayMap.access$200(this.this$1.this$0), this.pos, Byte2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2LongArrayMap.access$000(this.this$0);
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

    public Byte2LongArrayMap clone() {
        Byte2LongArrayMap byte2LongArrayMap;
        try {
            byte2LongArrayMap = (Byte2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2LongArrayMap.key = (byte[])this.key.clone();
        byte2LongArrayMap.value = (long[])this.value.clone();
        return byte2LongArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.key[i]);
            objectOutputStream.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readByte();
            this.value[i] = objectInputStream.readLong();
        }
    }

    public ObjectSet byte2LongEntrySet() {
        return this.byte2LongEntrySet();
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

    static int access$000(Byte2LongArrayMap byte2LongArrayMap) {
        return byte2LongArrayMap.size;
    }

    static byte[] access$100(Byte2LongArrayMap byte2LongArrayMap) {
        return byte2LongArrayMap.key;
    }

    static long[] access$200(Byte2LongArrayMap byte2LongArrayMap) {
        return byte2LongArrayMap.value;
    }

    static int access$010(Byte2LongArrayMap byte2LongArrayMap) {
        return byte2LongArrayMap.size--;
    }

    static int access$300(Byte2LongArrayMap byte2LongArrayMap, byte by) {
        return byte2LongArrayMap.findKey(by);
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2LongMap.Entry>
    implements Byte2LongMap.FastEntrySet {
        final Byte2LongArrayMap this$0;

        private EntrySet(Byte2LongArrayMap byte2LongArrayMap) {
            this.this$0 = byte2LongArrayMap;
        }

        @Override
        public ObjectIterator<Byte2LongMap.Entry> iterator() {
            return new ObjectIterator<Byte2LongMap.Entry>(this){
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
                    return this.next < Byte2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2LongMap.BasicEntry(Byte2LongArrayMap.access$100(this.this$1.this$0)[this.curr], Byte2LongArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Byte2LongMap.Entry> fastIterator() {
            return new ObjectIterator<Byte2LongMap.Entry>(this){
                int next;
                int curr;
                final AbstractByte2LongMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractByte2LongMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Byte2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2LongArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Byte2LongArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Byte2LongArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            return this.this$0.containsKey(by) && this.this$0.get(by) == ((Long)entry.getValue()).longValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            long l = (Long)entry.getValue();
            int n = Byte2LongArrayMap.access$300(this.this$0, by);
            if (n == -1 || l != Byte2LongArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Byte2LongArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Byte2LongArrayMap.access$100(this.this$0), n + 1, Byte2LongArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Byte2LongArrayMap.access$200(this.this$0), n + 1, Byte2LongArrayMap.access$200(this.this$0), n, n2);
            Byte2LongArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Byte2LongArrayMap byte2LongArrayMap, 1 var2_2) {
            this(byte2LongArrayMap);
        }
    }
}

