/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
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
public class Double2FloatArrayMap
extends AbstractDouble2FloatMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient float[] value;
    private int size;

    public Double2FloatArrayMap(double[] dArray, float[] fArray) {
        this.key = dArray;
        this.value = fArray;
        this.size = dArray.length;
        if (dArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + fArray.length + ")");
        }
    }

    public Double2FloatArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Double2FloatArrayMap(int n) {
        this.key = new double[n];
        this.value = new float[n];
    }

    public Double2FloatArrayMap(Double2FloatMap double2FloatMap) {
        this(double2FloatMap.size());
        this.putAll(double2FloatMap);
    }

    public Double2FloatArrayMap(Map<? extends Double, ? extends Float> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2FloatArrayMap(double[] dArray, float[] fArray, int n) {
        this.key = dArray;
        this.value = fArray;
        this.size = n;
        if (dArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + fArray.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2FloatMap.FastEntrySet double2FloatEntrySet() {
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
    public float get(double d) {
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
    public boolean containsValue(float f) {
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(this.value[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public float put(double d, float f) {
        int n = this.findKey(d);
        if (n != -1) {
            float f2 = this.value[n];
            this.value[n] = f;
            return f2;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.key[n2];
                fArray[n2] = this.value[n2];
            }
            this.key = dArray;
            this.value = fArray;
        }
        this.key[this.size] = d;
        this.value[this.size] = f;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public float remove(double d) {
        int n = this.findKey(d);
        if (n == -1) {
            return this.defRetValue;
        }
        float f = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return f;
    }

    @Override
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2FloatArrayMap this$0;
            {
                this.this$0 = double2FloatArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2FloatArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2FloatArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2FloatArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2FloatArrayMap.access$100(this.this$0), n + 1, Double2FloatArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2FloatArrayMap.access$200(this.this$0), n + 1, Double2FloatArrayMap.access$200(this.this$0), n, n2);
                Double2FloatArrayMap.access$010(this.this$0);
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
                        return this.pos < Double2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2FloatArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Double2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Double2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2FloatArrayMap.access$000(this.this$0);
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
    public FloatCollection values() {
        return new AbstractFloatCollection(this){
            final Double2FloatArrayMap this$0;
            {
                this.this$0 = double2FloatArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return this.this$0.containsValue(f);
            }

            @Override
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Double2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2FloatArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Double2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Double2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2FloatArrayMap.access$000(this.this$0);
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

    public Double2FloatArrayMap clone() {
        Double2FloatArrayMap double2FloatArrayMap;
        try {
            double2FloatArrayMap = (Double2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2FloatArrayMap.key = (double[])this.key.clone();
        double2FloatArrayMap.value = (float[])this.value.clone();
        return double2FloatArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readFloat();
        }
    }

    public ObjectSet double2FloatEntrySet() {
        return this.double2FloatEntrySet();
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

    static int access$000(Double2FloatArrayMap double2FloatArrayMap) {
        return double2FloatArrayMap.size;
    }

    static double[] access$100(Double2FloatArrayMap double2FloatArrayMap) {
        return double2FloatArrayMap.key;
    }

    static float[] access$200(Double2FloatArrayMap double2FloatArrayMap) {
        return double2FloatArrayMap.value;
    }

    static int access$010(Double2FloatArrayMap double2FloatArrayMap) {
        return double2FloatArrayMap.size--;
    }

    static int access$300(Double2FloatArrayMap double2FloatArrayMap, double d) {
        return double2FloatArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2FloatMap.Entry>
    implements Double2FloatMap.FastEntrySet {
        final Double2FloatArrayMap this$0;

        private EntrySet(Double2FloatArrayMap double2FloatArrayMap) {
            this.this$0 = double2FloatArrayMap;
        }

        @Override
        public ObjectIterator<Double2FloatMap.Entry> iterator() {
            return new ObjectIterator<Double2FloatMap.Entry>(this){
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
                    return this.next < Double2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2FloatMap.BasicEntry(Double2FloatArrayMap.access$100(this.this$1.this$0)[this.curr], Double2FloatArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2FloatMap.Entry> fastIterator() {
            return new ObjectIterator<Double2FloatMap.Entry>(this){
                int next;
                int curr;
                final AbstractDouble2FloatMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2FloatMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2FloatArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2FloatArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2FloatArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && Float.floatToIntBits(this.this$0.get(d)) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            double d = (Double)entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            int n = Double2FloatArrayMap.access$300(this.this$0, d);
            if (n == -1 || Float.floatToIntBits(f) != Float.floatToIntBits(Double2FloatArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Double2FloatArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Double2FloatArrayMap.access$100(this.this$0), n + 1, Double2FloatArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Double2FloatArrayMap.access$200(this.this$0), n + 1, Double2FloatArrayMap.access$200(this.this$0), n, n2);
            Double2FloatArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2FloatArrayMap double2FloatArrayMap, 1 var2_2) {
            this(double2FloatArrayMap);
        }
    }
}

