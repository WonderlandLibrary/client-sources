/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2DoubleMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
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
public class Long2DoubleArrayMap
extends AbstractLong2DoubleMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient double[] value;
    private int size;

    public Long2DoubleArrayMap(long[] lArray, double[] dArray) {
        this.key = lArray;
        this.value = dArray;
        this.size = lArray.length;
        if (lArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + dArray.length + ")");
        }
    }

    public Long2DoubleArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Long2DoubleArrayMap(int n) {
        this.key = new long[n];
        this.value = new double[n];
    }

    public Long2DoubleArrayMap(Long2DoubleMap long2DoubleMap) {
        this(long2DoubleMap.size());
        this.putAll(long2DoubleMap);
    }

    public Long2DoubleArrayMap(Map<? extends Long, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2DoubleArrayMap(long[] lArray, double[] dArray, int n) {
        this.key = lArray;
        this.value = dArray;
        this.size = n;
        if (lArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + dArray.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2DoubleMap.FastEntrySet long2DoubleEntrySet() {
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
    public double get(long l) {
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
    public boolean containsValue(double d) {
        int n = this.size;
        while (n-- != 0) {
            if (Double.doubleToLongBits(this.value[n]) != Double.doubleToLongBits(d)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public double put(long l, double d) {
        int n = this.findKey(l);
        if (n != -1) {
            double d2 = this.value[n];
            this.value[n] = d;
            return d2;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.key[n2];
                dArray[n2] = this.value[n2];
            }
            this.key = lArray;
            this.value = dArray;
        }
        this.key[this.size] = l;
        this.value[this.size] = d;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double remove(long l) {
        int n = this.findKey(l);
        if (n == -1) {
            return this.defRetValue;
        }
        double d = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return d;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2DoubleArrayMap this$0;
            {
                this.this$0 = long2DoubleArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2DoubleArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2DoubleArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2DoubleArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2DoubleArrayMap.access$100(this.this$0), n + 1, Long2DoubleArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2DoubleArrayMap.access$200(this.this$0), n + 1, Long2DoubleArrayMap.access$200(this.this$0), n, n2);
                Long2DoubleArrayMap.access$010(this.this$0);
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
                        return this.pos < Long2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Long2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Long2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2DoubleArrayMap.access$000(this.this$0);
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
    public DoubleCollection values() {
        return new AbstractDoubleCollection(this){
            final Long2DoubleArrayMap this$0;
            {
                this.this$0 = long2DoubleArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return this.this$0.containsValue(d);
            }

            @Override
            public DoubleIterator iterator() {
                return new DoubleIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Long2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Long2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Long2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2DoubleArrayMap.access$000(this.this$0);
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

    public Long2DoubleArrayMap clone() {
        Long2DoubleArrayMap long2DoubleArrayMap;
        try {
            long2DoubleArrayMap = (Long2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2DoubleArrayMap.key = (long[])this.key.clone();
        long2DoubleArrayMap.value = (double[])this.value.clone();
        return long2DoubleArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readDouble();
        }
    }

    public ObjectSet long2DoubleEntrySet() {
        return this.long2DoubleEntrySet();
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

    static int access$000(Long2DoubleArrayMap long2DoubleArrayMap) {
        return long2DoubleArrayMap.size;
    }

    static long[] access$100(Long2DoubleArrayMap long2DoubleArrayMap) {
        return long2DoubleArrayMap.key;
    }

    static double[] access$200(Long2DoubleArrayMap long2DoubleArrayMap) {
        return long2DoubleArrayMap.value;
    }

    static int access$010(Long2DoubleArrayMap long2DoubleArrayMap) {
        return long2DoubleArrayMap.size--;
    }

    static int access$300(Long2DoubleArrayMap long2DoubleArrayMap, long l) {
        return long2DoubleArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2DoubleMap.Entry>
    implements Long2DoubleMap.FastEntrySet {
        final Long2DoubleArrayMap this$0;

        private EntrySet(Long2DoubleArrayMap long2DoubleArrayMap) {
            this.this$0 = long2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Long2DoubleMap.Entry> iterator() {
            return new ObjectIterator<Long2DoubleMap.Entry>(this){
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
                    return this.next < Long2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2DoubleMap.BasicEntry(Long2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Long2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2DoubleMap.Entry> fastIterator() {
            return new ObjectIterator<Long2DoubleMap.Entry>(this){
                int next;
                int curr;
                final AbstractLong2DoubleMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2DoubleArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && Double.doubleToLongBits(this.this$0.get(l)) == Double.doubleToLongBits((Double)entry.getValue());
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            long l = (Long)entry.getKey();
            double d = (Double)entry.getValue();
            int n = Long2DoubleArrayMap.access$300(this.this$0, l);
            if (n == -1 || Double.doubleToLongBits(d) != Double.doubleToLongBits(Long2DoubleArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Long2DoubleArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2DoubleArrayMap.access$100(this.this$0), n + 1, Long2DoubleArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2DoubleArrayMap.access$200(this.this$0), n + 1, Long2DoubleArrayMap.access$200(this.this$0), n, n2);
            Long2DoubleArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2DoubleArrayMap long2DoubleArrayMap, 1 var2_2) {
            this(long2DoubleArrayMap);
        }
    }
}

