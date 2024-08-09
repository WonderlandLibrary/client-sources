/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
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
public class Double2DoubleArrayMap
extends AbstractDouble2DoubleMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient double[] value;
    private int size;

    public Double2DoubleArrayMap(double[] dArray, double[] dArray2) {
        this.key = dArray;
        this.value = dArray2;
        this.size = dArray.length;
        if (dArray.length != dArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + dArray2.length + ")");
        }
    }

    public Double2DoubleArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Double2DoubleArrayMap(int n) {
        this.key = new double[n];
        this.value = new double[n];
    }

    public Double2DoubleArrayMap(Double2DoubleMap double2DoubleMap) {
        this(double2DoubleMap.size());
        this.putAll(double2DoubleMap);
    }

    public Double2DoubleArrayMap(Map<? extends Double, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2DoubleArrayMap(double[] dArray, double[] dArray2, int n) {
        this.key = dArray;
        this.value = dArray2;
        this.size = n;
        if (dArray.length != dArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + dArray2.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2DoubleMap.FastEntrySet double2DoubleEntrySet() {
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
    public double get(double d) {
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
    public double put(double d, double d2) {
        int n = this.findKey(d);
        if (n != -1) {
            double d3 = this.value[n];
            this.value[n] = d2;
            return d3;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            double[] dArray2 = new double[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.key[n2];
                dArray2[n2] = this.value[n2];
            }
            this.key = dArray;
            this.value = dArray2;
        }
        this.key[this.size] = d;
        this.value[this.size] = d2;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double remove(double d) {
        int n = this.findKey(d);
        if (n == -1) {
            return this.defRetValue;
        }
        double d2 = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return d2;
    }

    @Override
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2DoubleArrayMap this$0;
            {
                this.this$0 = double2DoubleArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2DoubleArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2DoubleArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2DoubleArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2DoubleArrayMap.access$100(this.this$0), n + 1, Double2DoubleArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2DoubleArrayMap.access$200(this.this$0), n + 1, Double2DoubleArrayMap.access$200(this.this$0), n, n2);
                Double2DoubleArrayMap.access$010(this.this$0);
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
                        return this.pos < Double2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Double2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Double2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2DoubleArrayMap.access$000(this.this$0);
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
            final Double2DoubleArrayMap this$0;
            {
                this.this$0 = double2DoubleArrayMap;
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
                        return this.pos < Double2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Double2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Double2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2DoubleArrayMap.access$000(this.this$0);
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

    public Double2DoubleArrayMap clone() {
        Double2DoubleArrayMap double2DoubleArrayMap;
        try {
            double2DoubleArrayMap = (Double2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2DoubleArrayMap.key = (double[])this.key.clone();
        double2DoubleArrayMap.value = (double[])this.value.clone();
        return double2DoubleArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readDouble();
        }
    }

    public ObjectSet double2DoubleEntrySet() {
        return this.double2DoubleEntrySet();
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

    static int access$000(Double2DoubleArrayMap double2DoubleArrayMap) {
        return double2DoubleArrayMap.size;
    }

    static double[] access$100(Double2DoubleArrayMap double2DoubleArrayMap) {
        return double2DoubleArrayMap.key;
    }

    static double[] access$200(Double2DoubleArrayMap double2DoubleArrayMap) {
        return double2DoubleArrayMap.value;
    }

    static int access$010(Double2DoubleArrayMap double2DoubleArrayMap) {
        return double2DoubleArrayMap.size--;
    }

    static int access$300(Double2DoubleArrayMap double2DoubleArrayMap, double d) {
        return double2DoubleArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2DoubleMap.Entry>
    implements Double2DoubleMap.FastEntrySet {
        final Double2DoubleArrayMap this$0;

        private EntrySet(Double2DoubleArrayMap double2DoubleArrayMap) {
            this.this$0 = double2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Double2DoubleMap.Entry> iterator() {
            return new ObjectIterator<Double2DoubleMap.Entry>(this){
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
                    return this.next < Double2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Double2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2DoubleMap.Entry> fastIterator() {
            return new ObjectIterator<Double2DoubleMap.Entry>(this){
                int next;
                int curr;
                final AbstractDouble2DoubleMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2DoubleArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && Double.doubleToLongBits(this.this$0.get(d)) == Double.doubleToLongBits((Double)entry.getValue());
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            double d = (Double)entry.getKey();
            double d2 = (Double)entry.getValue();
            int n = Double2DoubleArrayMap.access$300(this.this$0, d);
            if (n == -1 || Double.doubleToLongBits(d2) != Double.doubleToLongBits(Double2DoubleArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Double2DoubleArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Double2DoubleArrayMap.access$100(this.this$0), n + 1, Double2DoubleArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Double2DoubleArrayMap.access$200(this.this$0), n + 1, Double2DoubleArrayMap.access$200(this.this$0), n, n2);
            Double2DoubleArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2DoubleArrayMap double2DoubleArrayMap, 1 var2_2) {
            this(double2DoubleArrayMap);
        }
    }
}

