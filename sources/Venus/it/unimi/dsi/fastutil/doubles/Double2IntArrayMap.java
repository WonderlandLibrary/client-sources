/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2IntMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
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
public class Double2IntArrayMap
extends AbstractDouble2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient int[] value;
    private int size;

    public Double2IntArrayMap(double[] dArray, int[] nArray) {
        this.key = dArray;
        this.value = nArray;
        this.size = dArray.length;
        if (dArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + nArray.length + ")");
        }
    }

    public Double2IntArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Double2IntArrayMap(int n) {
        this.key = new double[n];
        this.value = new int[n];
    }

    public Double2IntArrayMap(Double2IntMap double2IntMap) {
        this(double2IntMap.size());
        this.putAll(double2IntMap);
    }

    public Double2IntArrayMap(Map<? extends Double, ? extends Integer> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2IntArrayMap(double[] dArray, int[] nArray, int n) {
        this.key = dArray;
        this.value = nArray;
        this.size = n;
        if (dArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + nArray.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2IntMap.FastEntrySet double2IntEntrySet() {
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
    public int get(double d) {
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
    public int put(double d, int n) {
        int n2 = this.findKey(d);
        if (n2 != -1) {
            int n3 = this.value[n2];
            this.value[n2] = n;
            return n3;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n4 = this.size;
            while (n4-- != 0) {
                dArray[n4] = this.key[n4];
                nArray[n4] = this.value[n4];
            }
            this.key = dArray;
            this.value = nArray;
        }
        this.key[this.size] = d;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int remove(double d) {
        int n = this.findKey(d);
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
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2IntArrayMap this$0;
            {
                this.this$0 = double2IntArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2IntArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2IntArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2IntArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2IntArrayMap.access$100(this.this$0), n + 1, Double2IntArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2IntArrayMap.access$200(this.this$0), n + 1, Double2IntArrayMap.access$200(this.this$0), n, n2);
                Double2IntArrayMap.access$010(this.this$0);
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
                        return this.pos < Double2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2IntArrayMap.access$100(this.this$1.this$0), this.pos, Double2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2IntArrayMap.access$200(this.this$1.this$0), this.pos, Double2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2IntArrayMap.access$000(this.this$0);
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
            final Double2IntArrayMap this$0;
            {
                this.this$0 = double2IntArrayMap;
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
                        return this.pos < Double2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2IntArrayMap.access$100(this.this$1.this$0), this.pos, Double2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2IntArrayMap.access$200(this.this$1.this$0), this.pos, Double2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2IntArrayMap.access$000(this.this$0);
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

    public Double2IntArrayMap clone() {
        Double2IntArrayMap double2IntArrayMap;
        try {
            double2IntArrayMap = (Double2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2IntArrayMap.key = (double[])this.key.clone();
        double2IntArrayMap.value = (int[])this.value.clone();
        return double2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readInt();
        }
    }

    public ObjectSet double2IntEntrySet() {
        return this.double2IntEntrySet();
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

    static int access$000(Double2IntArrayMap double2IntArrayMap) {
        return double2IntArrayMap.size;
    }

    static double[] access$100(Double2IntArrayMap double2IntArrayMap) {
        return double2IntArrayMap.key;
    }

    static int[] access$200(Double2IntArrayMap double2IntArrayMap) {
        return double2IntArrayMap.value;
    }

    static int access$010(Double2IntArrayMap double2IntArrayMap) {
        return double2IntArrayMap.size--;
    }

    static int access$300(Double2IntArrayMap double2IntArrayMap, double d) {
        return double2IntArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2IntMap.Entry>
    implements Double2IntMap.FastEntrySet {
        final Double2IntArrayMap this$0;

        private EntrySet(Double2IntArrayMap double2IntArrayMap) {
            this.this$0 = double2IntArrayMap;
        }

        @Override
        public ObjectIterator<Double2IntMap.Entry> iterator() {
            return new ObjectIterator<Double2IntMap.Entry>(this){
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
                    return this.next < Double2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2IntMap.BasicEntry(Double2IntArrayMap.access$100(this.this$1.this$0)[this.curr], Double2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Double2IntMap.Entry>(this){
                int next;
                int curr;
                final AbstractDouble2IntMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2IntArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2IntArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && this.this$0.get(d) == ((Integer)entry.getValue()).intValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            double d = (Double)entry.getKey();
            int n = (Integer)entry.getValue();
            int n2 = Double2IntArrayMap.access$300(this.this$0, d);
            if (n2 == -1 || n != Double2IntArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Double2IntArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Double2IntArrayMap.access$100(this.this$0), n2 + 1, Double2IntArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Double2IntArrayMap.access$200(this.this$0), n2 + 1, Double2IntArrayMap.access$200(this.this$0), n2, n3);
            Double2IntArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2IntArrayMap double2IntArrayMap, 1 var2_2) {
            this(double2IntArrayMap);
        }
    }
}

