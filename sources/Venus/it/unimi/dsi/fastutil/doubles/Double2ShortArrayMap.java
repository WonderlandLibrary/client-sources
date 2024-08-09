/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2ShortMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
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
public class Double2ShortArrayMap
extends AbstractDouble2ShortMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient short[] value;
    private int size;

    public Double2ShortArrayMap(double[] dArray, short[] sArray) {
        this.key = dArray;
        this.value = sArray;
        this.size = dArray.length;
        if (dArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + sArray.length + ")");
        }
    }

    public Double2ShortArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = ShortArrays.EMPTY_ARRAY;
    }

    public Double2ShortArrayMap(int n) {
        this.key = new double[n];
        this.value = new short[n];
    }

    public Double2ShortArrayMap(Double2ShortMap double2ShortMap) {
        this(double2ShortMap.size());
        this.putAll(double2ShortMap);
    }

    public Double2ShortArrayMap(Map<? extends Double, ? extends Short> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2ShortArrayMap(double[] dArray, short[] sArray, int n) {
        this.key = dArray;
        this.value = sArray;
        this.size = n;
        if (dArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + sArray.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2ShortMap.FastEntrySet double2ShortEntrySet() {
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
    public short get(double d) {
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
    public short put(double d, short s) {
        int n = this.findKey(d);
        if (n != -1) {
            short s2 = this.value[n];
            this.value[n] = s;
            return s2;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.key[n2];
                sArray[n2] = this.value[n2];
            }
            this.key = dArray;
            this.value = sArray;
        }
        this.key[this.size] = d;
        this.value[this.size] = s;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public short remove(double d) {
        int n = this.findKey(d);
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
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2ShortArrayMap this$0;
            {
                this.this$0 = double2ShortArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2ShortArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2ShortArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2ShortArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2ShortArrayMap.access$100(this.this$0), n + 1, Double2ShortArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2ShortArrayMap.access$200(this.this$0), n + 1, Double2ShortArrayMap.access$200(this.this$0), n, n2);
                Double2ShortArrayMap.access$010(this.this$0);
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
                        return this.pos < Double2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2ShortArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Double2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Double2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2ShortArrayMap.access$000(this.this$0);
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
            final Double2ShortArrayMap this$0;
            {
                this.this$0 = double2ShortArrayMap;
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
                        return this.pos < Double2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2ShortArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Double2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Double2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2ShortArrayMap.access$000(this.this$0);
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

    public Double2ShortArrayMap clone() {
        Double2ShortArrayMap double2ShortArrayMap;
        try {
            double2ShortArrayMap = (Double2ShortArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2ShortArrayMap.key = (double[])this.key.clone();
        double2ShortArrayMap.value = (short[])this.value.clone();
        return double2ShortArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeShort(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readShort();
        }
    }

    public ObjectSet double2ShortEntrySet() {
        return this.double2ShortEntrySet();
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

    static int access$000(Double2ShortArrayMap double2ShortArrayMap) {
        return double2ShortArrayMap.size;
    }

    static double[] access$100(Double2ShortArrayMap double2ShortArrayMap) {
        return double2ShortArrayMap.key;
    }

    static short[] access$200(Double2ShortArrayMap double2ShortArrayMap) {
        return double2ShortArrayMap.value;
    }

    static int access$010(Double2ShortArrayMap double2ShortArrayMap) {
        return double2ShortArrayMap.size--;
    }

    static int access$300(Double2ShortArrayMap double2ShortArrayMap, double d) {
        return double2ShortArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2ShortMap.Entry>
    implements Double2ShortMap.FastEntrySet {
        final Double2ShortArrayMap this$0;

        private EntrySet(Double2ShortArrayMap double2ShortArrayMap) {
            this.this$0 = double2ShortArrayMap;
        }

        @Override
        public ObjectIterator<Double2ShortMap.Entry> iterator() {
            return new ObjectIterator<Double2ShortMap.Entry>(this){
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
                    return this.next < Double2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2ShortMap.BasicEntry(Double2ShortArrayMap.access$100(this.this$1.this$0)[this.curr], Double2ShortArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2ShortMap.Entry> fastIterator() {
            return new ObjectIterator<Double2ShortMap.Entry>(this){
                int next;
                int curr;
                final AbstractDouble2ShortMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2ShortMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2ShortArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2ShortArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2ShortArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && this.this$0.get(d) == ((Short)entry.getValue()).shortValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            double d = (Double)entry.getKey();
            short s = (Short)entry.getValue();
            int n = Double2ShortArrayMap.access$300(this.this$0, d);
            if (n == -1 || s != Double2ShortArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Double2ShortArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Double2ShortArrayMap.access$100(this.this$0), n + 1, Double2ShortArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Double2ShortArrayMap.access$200(this.this$0), n + 1, Double2ShortArrayMap.access$200(this.this$0), n, n2);
            Double2ShortArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2ShortArrayMap double2ShortArrayMap, 1 var2_2) {
            this(double2ShortArrayMap);
        }
    }
}

