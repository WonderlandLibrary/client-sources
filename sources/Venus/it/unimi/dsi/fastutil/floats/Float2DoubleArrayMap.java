/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2DoubleMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
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
public class Float2DoubleArrayMap
extends AbstractFloat2DoubleMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient double[] value;
    private int size;

    public Float2DoubleArrayMap(float[] fArray, double[] dArray) {
        this.key = fArray;
        this.value = dArray;
        this.size = fArray.length;
        if (fArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + dArray.length + ")");
        }
    }

    public Float2DoubleArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Float2DoubleArrayMap(int n) {
        this.key = new float[n];
        this.value = new double[n];
    }

    public Float2DoubleArrayMap(Float2DoubleMap float2DoubleMap) {
        this(float2DoubleMap.size());
        this.putAll(float2DoubleMap);
    }

    public Float2DoubleArrayMap(Map<? extends Float, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2DoubleArrayMap(float[] fArray, double[] dArray, int n) {
        this.key = fArray;
        this.value = dArray;
        this.size = n;
        if (fArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + dArray.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2DoubleMap.FastEntrySet float2DoubleEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(float f) {
        float[] fArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public double get(float f) {
        float[] fArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
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
    public boolean containsKey(float f) {
        return this.findKey(f) != -1;
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
    public double put(float f, double d) {
        int n = this.findKey(f);
        if (n != -1) {
            double d2 = this.value[n];
            this.value[n] = d;
            return d2;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.key[n2];
                dArray[n2] = this.value[n2];
            }
            this.key = fArray;
            this.value = dArray;
        }
        this.key[this.size] = f;
        this.value[this.size] = d;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double remove(float f) {
        int n = this.findKey(f);
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
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2DoubleArrayMap this$0;
            {
                this.this$0 = float2DoubleArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2DoubleArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2DoubleArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2DoubleArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2DoubleArrayMap.access$100(this.this$0), n + 1, Float2DoubleArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2DoubleArrayMap.access$200(this.this$0), n + 1, Float2DoubleArrayMap.access$200(this.this$0), n, n2);
                Float2DoubleArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Float2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Float2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Float2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2DoubleArrayMap.access$000(this.this$0);
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
            final Float2DoubleArrayMap this$0;
            {
                this.this$0 = float2DoubleArrayMap;
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
                        return this.pos < Float2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Float2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Float2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2DoubleArrayMap.access$000(this.this$0);
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

    public Float2DoubleArrayMap clone() {
        Float2DoubleArrayMap float2DoubleArrayMap;
        try {
            float2DoubleArrayMap = (Float2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2DoubleArrayMap.key = (float[])this.key.clone();
        float2DoubleArrayMap.value = (double[])this.value.clone();
        return float2DoubleArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readDouble();
        }
    }

    public ObjectSet float2DoubleEntrySet() {
        return this.float2DoubleEntrySet();
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

    static int access$000(Float2DoubleArrayMap float2DoubleArrayMap) {
        return float2DoubleArrayMap.size;
    }

    static float[] access$100(Float2DoubleArrayMap float2DoubleArrayMap) {
        return float2DoubleArrayMap.key;
    }

    static double[] access$200(Float2DoubleArrayMap float2DoubleArrayMap) {
        return float2DoubleArrayMap.value;
    }

    static int access$010(Float2DoubleArrayMap float2DoubleArrayMap) {
        return float2DoubleArrayMap.size--;
    }

    static int access$300(Float2DoubleArrayMap float2DoubleArrayMap, float f) {
        return float2DoubleArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2DoubleMap.Entry>
    implements Float2DoubleMap.FastEntrySet {
        final Float2DoubleArrayMap this$0;

        private EntrySet(Float2DoubleArrayMap float2DoubleArrayMap) {
            this.this$0 = float2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Float2DoubleMap.Entry> iterator() {
            return new ObjectIterator<Float2DoubleMap.Entry>(this){
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
                    return this.next < Float2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Float2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2DoubleMap.Entry> fastIterator() {
            return new ObjectIterator<Float2DoubleMap.Entry>(this){
                int next;
                int curr;
                final AbstractFloat2DoubleMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2DoubleArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && Double.doubleToLongBits(this.this$0.get(f)) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            double d = (Double)entry.getValue();
            int n = Float2DoubleArrayMap.access$300(this.this$0, f);
            if (n == -1 || Double.doubleToLongBits(d) != Double.doubleToLongBits(Float2DoubleArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Float2DoubleArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Float2DoubleArrayMap.access$100(this.this$0), n + 1, Float2DoubleArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Float2DoubleArrayMap.access$200(this.this$0), n + 1, Float2DoubleArrayMap.access$200(this.this$0), n, n2);
            Float2DoubleArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2DoubleArrayMap float2DoubleArrayMap, 1 var2_2) {
            this(float2DoubleArrayMap);
        }
    }
}

