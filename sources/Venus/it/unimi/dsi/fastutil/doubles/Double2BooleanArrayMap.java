/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
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
public class Double2BooleanArrayMap
extends AbstractDouble2BooleanMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient boolean[] value;
    private int size;

    public Double2BooleanArrayMap(double[] dArray, boolean[] blArray) {
        this.key = dArray;
        this.value = blArray;
        this.size = dArray.length;
        if (dArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + blArray.length + ")");
        }
    }

    public Double2BooleanArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Double2BooleanArrayMap(int n) {
        this.key = new double[n];
        this.value = new boolean[n];
    }

    public Double2BooleanArrayMap(Double2BooleanMap double2BooleanMap) {
        this(double2BooleanMap.size());
        this.putAll(double2BooleanMap);
    }

    public Double2BooleanArrayMap(Map<? extends Double, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2BooleanArrayMap(double[] dArray, boolean[] blArray, int n) {
        this.key = dArray;
        this.value = blArray;
        this.size = n;
        if (dArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + blArray.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2BooleanMap.FastEntrySet double2BooleanEntrySet() {
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
    public boolean get(double d) {
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
    public boolean containsValue(boolean bl) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != bl) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean put(double d, boolean bl) {
        int n = this.findKey(d);
        if (n != -1) {
            boolean bl2 = this.value[n];
            this.value[n] = bl;
            return bl2;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.key[n2];
                blArray[n2] = this.value[n2];
            }
            this.key = dArray;
            this.value = blArray;
        }
        this.key[this.size] = d;
        this.value[this.size] = bl;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public boolean remove(double d) {
        int n = this.findKey(d);
        if (n == -1) {
            return this.defRetValue;
        }
        boolean bl = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return bl;
    }

    @Override
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2BooleanArrayMap this$0;
            {
                this.this$0 = double2BooleanArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2BooleanArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2BooleanArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2BooleanArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2BooleanArrayMap.access$100(this.this$0), n + 1, Double2BooleanArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2BooleanArrayMap.access$200(this.this$0), n + 1, Double2BooleanArrayMap.access$200(this.this$0), n, n2);
                Double2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Double2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Double2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Double2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2BooleanArrayMap.access$000(this.this$0);
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
    public BooleanCollection values() {
        return new AbstractBooleanCollection(this){
            final Double2BooleanArrayMap this$0;
            {
                this.this$0 = double2BooleanArrayMap;
            }

            @Override
            public boolean contains(boolean bl) {
                return this.this$0.containsValue(bl);
            }

            @Override
            public BooleanIterator iterator() {
                return new BooleanIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Double2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Double2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Double2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2BooleanArrayMap.access$000(this.this$0);
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

    public Double2BooleanArrayMap clone() {
        Double2BooleanArrayMap double2BooleanArrayMap;
        try {
            double2BooleanArrayMap = (Double2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2BooleanArrayMap.key = (double[])this.key.clone();
        double2BooleanArrayMap.value = (boolean[])this.value.clone();
        return double2BooleanArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeBoolean(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readBoolean();
        }
    }

    public ObjectSet double2BooleanEntrySet() {
        return this.double2BooleanEntrySet();
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

    static int access$000(Double2BooleanArrayMap double2BooleanArrayMap) {
        return double2BooleanArrayMap.size;
    }

    static double[] access$100(Double2BooleanArrayMap double2BooleanArrayMap) {
        return double2BooleanArrayMap.key;
    }

    static boolean[] access$200(Double2BooleanArrayMap double2BooleanArrayMap) {
        return double2BooleanArrayMap.value;
    }

    static int access$010(Double2BooleanArrayMap double2BooleanArrayMap) {
        return double2BooleanArrayMap.size--;
    }

    static int access$300(Double2BooleanArrayMap double2BooleanArrayMap, double d) {
        return double2BooleanArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2BooleanMap.Entry>
    implements Double2BooleanMap.FastEntrySet {
        final Double2BooleanArrayMap this$0;

        private EntrySet(Double2BooleanArrayMap double2BooleanArrayMap) {
            this.this$0 = double2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Double2BooleanMap.Entry> iterator() {
            return new ObjectIterator<Double2BooleanMap.Entry>(this){
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
                    return this.next < Double2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2BooleanMap.BasicEntry(Double2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Double2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2BooleanMap.Entry> fastIterator() {
            return new ObjectIterator<Double2BooleanMap.Entry>(this){
                int next;
                int curr;
                final AbstractDouble2BooleanMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2BooleanArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && this.this$0.get(d) == ((Boolean)entry.getValue()).booleanValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            double d = (Double)entry.getKey();
            boolean bl = (Boolean)entry.getValue();
            int n = Double2BooleanArrayMap.access$300(this.this$0, d);
            if (n == -1 || bl != Double2BooleanArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Double2BooleanArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Double2BooleanArrayMap.access$100(this.this$0), n + 1, Double2BooleanArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Double2BooleanArrayMap.access$200(this.this$0), n + 1, Double2BooleanArrayMap.access$200(this.this$0), n, n2);
            Double2BooleanArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2BooleanArrayMap double2BooleanArrayMap, 1 var2_2) {
            this(double2BooleanArrayMap);
        }
    }
}

