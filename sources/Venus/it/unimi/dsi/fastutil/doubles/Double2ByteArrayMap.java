/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2ByteMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2ByteMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
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
public class Double2ByteArrayMap
extends AbstractDouble2ByteMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient byte[] value;
    private int size;

    public Double2ByteArrayMap(double[] dArray, byte[] byArray) {
        this.key = dArray;
        this.value = byArray;
        this.size = dArray.length;
        if (dArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + byArray.length + ")");
        }
    }

    public Double2ByteArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = ByteArrays.EMPTY_ARRAY;
    }

    public Double2ByteArrayMap(int n) {
        this.key = new double[n];
        this.value = new byte[n];
    }

    public Double2ByteArrayMap(Double2ByteMap double2ByteMap) {
        this(double2ByteMap.size());
        this.putAll(double2ByteMap);
    }

    public Double2ByteArrayMap(Map<? extends Double, ? extends Byte> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2ByteArrayMap(double[] dArray, byte[] byArray, int n) {
        this.key = dArray;
        this.value = byArray;
        this.size = n;
        if (dArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + byArray.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2ByteMap.FastEntrySet double2ByteEntrySet() {
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
    public byte get(double d) {
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
    public byte put(double d, byte by) {
        int n = this.findKey(d);
        if (n != -1) {
            byte by2 = this.value[n];
            this.value[n] = by;
            return by2;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.key[n2];
                byArray[n2] = this.value[n2];
            }
            this.key = dArray;
            this.value = byArray;
        }
        this.key[this.size] = d;
        this.value[this.size] = by;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public byte remove(double d) {
        int n = this.findKey(d);
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
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2ByteArrayMap this$0;
            {
                this.this$0 = double2ByteArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2ByteArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2ByteArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2ByteArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2ByteArrayMap.access$100(this.this$0), n + 1, Double2ByteArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2ByteArrayMap.access$200(this.this$0), n + 1, Double2ByteArrayMap.access$200(this.this$0), n, n2);
                Double2ByteArrayMap.access$010(this.this$0);
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
                        return this.pos < Double2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2ByteArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Double2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Double2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2ByteArrayMap.access$000(this.this$0);
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
            final Double2ByteArrayMap this$0;
            {
                this.this$0 = double2ByteArrayMap;
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
                        return this.pos < Double2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2ByteArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Double2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Double2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2ByteArrayMap.access$000(this.this$0);
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

    public Double2ByteArrayMap clone() {
        Double2ByteArrayMap double2ByteArrayMap;
        try {
            double2ByteArrayMap = (Double2ByteArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2ByteArrayMap.key = (double[])this.key.clone();
        double2ByteArrayMap.value = (byte[])this.value.clone();
        return double2ByteArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeByte(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new byte[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readByte();
        }
    }

    public ObjectSet double2ByteEntrySet() {
        return this.double2ByteEntrySet();
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

    static int access$000(Double2ByteArrayMap double2ByteArrayMap) {
        return double2ByteArrayMap.size;
    }

    static double[] access$100(Double2ByteArrayMap double2ByteArrayMap) {
        return double2ByteArrayMap.key;
    }

    static byte[] access$200(Double2ByteArrayMap double2ByteArrayMap) {
        return double2ByteArrayMap.value;
    }

    static int access$010(Double2ByteArrayMap double2ByteArrayMap) {
        return double2ByteArrayMap.size--;
    }

    static int access$300(Double2ByteArrayMap double2ByteArrayMap, double d) {
        return double2ByteArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2ByteMap.Entry>
    implements Double2ByteMap.FastEntrySet {
        final Double2ByteArrayMap this$0;

        private EntrySet(Double2ByteArrayMap double2ByteArrayMap) {
            this.this$0 = double2ByteArrayMap;
        }

        @Override
        public ObjectIterator<Double2ByteMap.Entry> iterator() {
            return new ObjectIterator<Double2ByteMap.Entry>(this){
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
                    return this.next < Double2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2ByteMap.BasicEntry(Double2ByteArrayMap.access$100(this.this$1.this$0)[this.curr], Double2ByteArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2ByteMap.Entry> fastIterator() {
            return new ObjectIterator<Double2ByteMap.Entry>(this){
                int next;
                int curr;
                final AbstractDouble2ByteMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2ByteMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2ByteArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2ByteArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2ByteArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && this.this$0.get(d) == ((Byte)entry.getValue()).byteValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            double d = (Double)entry.getKey();
            byte by = (Byte)entry.getValue();
            int n = Double2ByteArrayMap.access$300(this.this$0, d);
            if (n == -1 || by != Double2ByteArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Double2ByteArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Double2ByteArrayMap.access$100(this.this$0), n + 1, Double2ByteArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Double2ByteArrayMap.access$200(this.this$0), n + 1, Double2ByteArrayMap.access$200(this.this$0), n, n2);
            Double2ByteArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2ByteArrayMap double2ByteArrayMap, 1 var2_2) {
            this(double2ByteArrayMap);
        }
    }
}

