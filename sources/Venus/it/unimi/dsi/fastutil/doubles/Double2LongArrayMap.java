/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2LongMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2LongMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
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
public class Double2LongArrayMap
extends AbstractDouble2LongMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient long[] value;
    private int size;

    public Double2LongArrayMap(double[] dArray, long[] lArray) {
        this.key = dArray;
        this.value = lArray;
        this.size = dArray.length;
        if (dArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + lArray.length + ")");
        }
    }

    public Double2LongArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Double2LongArrayMap(int n) {
        this.key = new double[n];
        this.value = new long[n];
    }

    public Double2LongArrayMap(Double2LongMap double2LongMap) {
        this(double2LongMap.size());
        this.putAll(double2LongMap);
    }

    public Double2LongArrayMap(Map<? extends Double, ? extends Long> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2LongArrayMap(double[] dArray, long[] lArray, int n) {
        this.key = dArray;
        this.value = lArray;
        this.size = n;
        if (dArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + lArray.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2LongMap.FastEntrySet double2LongEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(double d) {
        double[] dArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray[n]) != Double.doubleToLongBits(d)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public long get(double d) {
        double[] dArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray[n]) != Double.doubleToLongBits(d)) continue;
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
    public boolean containsKey(double d) {
        return this.findKey(d) != -1;
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
    public long put(double d, long l) {
        int n = this.findKey(d);
        if (n != -1) {
            long l2 = this.value[n];
            this.value[n] = l;
            return l2;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.key[n2];
                lArray[n2] = this.value[n2];
            }
            this.key = dArray;
            this.value = lArray;
        }
        this.key[this.size] = d;
        this.value[this.size] = l;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public long remove(double d) {
        int n = this.findKey(d);
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
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2LongArrayMap this$0;
            {
                this.this$0 = double2LongArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2LongArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2LongArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2LongArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2LongArrayMap.access$100(this.this$0), n + 1, Double2LongArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2LongArrayMap.access$200(this.this$0), n + 1, Double2LongArrayMap.access$200(this.this$0), n, n2);
                Double2LongArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public DoubleIterator iterator() {
                return new DoubleIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Double2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2LongArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2LongArrayMap.access$100(this.this$1.this$0), this.pos, Double2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2LongArrayMap.access$200(this.this$1.this$0), this.pos, Double2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2LongArrayMap.access$000(this.this$0);
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
            final Double2LongArrayMap this$0;
            {
                this.this$0 = double2LongArrayMap;
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
                        return this.pos < Double2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2LongArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2LongArrayMap.access$100(this.this$1.this$0), this.pos, Double2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2LongArrayMap.access$200(this.this$1.this$0), this.pos, Double2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2LongArrayMap.access$000(this.this$0);
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

    public Double2LongArrayMap clone() {
        Double2LongArrayMap double2LongArrayMap;
        try {
            double2LongArrayMap = (Double2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2LongArrayMap.key = (double[])this.key.clone();
        double2LongArrayMap.value = (long[])this.value.clone();
        return double2LongArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readLong();
        }
    }

    public ObjectSet double2LongEntrySet() {
        return this.double2LongEntrySet();
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

    static int access$000(Double2LongArrayMap double2LongArrayMap) {
        return double2LongArrayMap.size;
    }

    static double[] access$100(Double2LongArrayMap double2LongArrayMap) {
        return double2LongArrayMap.key;
    }

    static long[] access$200(Double2LongArrayMap double2LongArrayMap) {
        return double2LongArrayMap.value;
    }

    static int access$010(Double2LongArrayMap double2LongArrayMap) {
        return double2LongArrayMap.size--;
    }

    static int access$300(Double2LongArrayMap double2LongArrayMap, double d) {
        return double2LongArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2LongMap.Entry>
    implements Double2LongMap.FastEntrySet {
        final Double2LongArrayMap this$0;

        private EntrySet(Double2LongArrayMap double2LongArrayMap) {
            this.this$0 = double2LongArrayMap;
        }

        @Override
        public ObjectIterator<Double2LongMap.Entry> iterator() {
            return new ObjectIterator<Double2LongMap.Entry>(this){
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
                    return this.next < Double2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2LongMap.BasicEntry(Double2LongArrayMap.access$100(this.this$1.this$0)[this.curr], Double2LongArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2LongMap.Entry> fastIterator() {
            return new ObjectIterator<Double2LongMap.Entry>(this){
                int next;
                int curr;
                final AbstractDouble2LongMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2LongMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2LongArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2LongArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2LongArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Double)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && this.this$0.get(d) == ((Long)entry.getValue()).longValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Double)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            double d = (Double)entry.getKey();
            long l = (Long)entry.getValue();
            int n = Double2LongArrayMap.access$300(this.this$0, d);
            if (n == -1 || l != Double2LongArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Double2LongArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Double2LongArrayMap.access$100(this.this$0), n + 1, Double2LongArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Double2LongArrayMap.access$200(this.this$0), n + 1, Double2LongArrayMap.access$200(this.this$0), n, n2);
            Double2LongArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2LongArrayMap double2LongArrayMap, 1 var2_2) {
            this(double2LongArrayMap);
        }
    }
}

