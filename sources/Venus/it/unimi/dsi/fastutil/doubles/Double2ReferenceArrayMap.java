/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2ReferenceMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
public class Double2ReferenceArrayMap<V>
extends AbstractDouble2ReferenceMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient Object[] value;
    private int size;

    public Double2ReferenceArrayMap(double[] dArray, Object[] objectArray) {
        this.key = dArray;
        this.value = objectArray;
        this.size = dArray.length;
        if (dArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + objectArray.length + ")");
        }
    }

    public Double2ReferenceArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Double2ReferenceArrayMap(int n) {
        this.key = new double[n];
        this.value = new Object[n];
    }

    public Double2ReferenceArrayMap(Double2ReferenceMap<V> double2ReferenceMap) {
        this(double2ReferenceMap.size());
        this.putAll(double2ReferenceMap);
    }

    public Double2ReferenceArrayMap(Map<? extends Double, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2ReferenceArrayMap(double[] dArray, Object[] objectArray, int n) {
        this.key = dArray;
        this.value = objectArray;
        this.size = n;
        if (dArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + objectArray.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2ReferenceMap.FastEntrySet<V> double2ReferenceEntrySet() {
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
    public V get(double d) {
        double[] dArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray[n]) != Double.doubleToLongBits(d)) continue;
            return (V)this.value[n];
        }
        return (V)this.defRetValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        int n = this.size;
        while (n-- != 0) {
            this.value[n] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(double d) {
        return this.findKey(d) != -1;
    }

    @Override
    public boolean containsValue(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public V put(double d, V v) {
        int n = this.findKey(d);
        if (n != -1) {
            Object object = this.value[n];
            this.value[n] = v;
            return (V)object;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.key[n2];
                objectArray[n2] = this.value[n2];
            }
            this.key = dArray;
            this.value = objectArray;
        }
        this.key[this.size] = d;
        this.value[this.size] = v;
        ++this.size;
        return (V)this.defRetValue;
    }

    @Override
    public V remove(double d) {
        int n = this.findKey(d);
        if (n == -1) {
            return (V)this.defRetValue;
        }
        Object object = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.value[this.size] = null;
        return (V)object;
    }

    @Override
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2ReferenceArrayMap this$0;
            {
                this.this$0 = double2ReferenceArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2ReferenceArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2ReferenceArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2ReferenceArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2ReferenceArrayMap.access$100(this.this$0), n + 1, Double2ReferenceArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2ReferenceArrayMap.access$200(this.this$0), n + 1, Double2ReferenceArrayMap.access$200(this.this$0), n, n2);
                Double2ReferenceArrayMap.access$010(this.this$0);
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
                        return this.pos < Double2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2ReferenceArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Double2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Double2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2ReferenceArrayMap.access$000(this.this$0);
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
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(this){
            final Double2ReferenceArrayMap this$0;
            {
                this.this$0 = double2ReferenceArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsValue(object);
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Double2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2ReferenceArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Double2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Double2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2ReferenceArrayMap.access$000(this.this$0);
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

    public Double2ReferenceArrayMap<V> clone() {
        Double2ReferenceArrayMap double2ReferenceArrayMap;
        try {
            double2ReferenceArrayMap = (Double2ReferenceArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2ReferenceArrayMap.key = (double[])this.key.clone();
        double2ReferenceArrayMap.value = (Object[])this.value.clone();
        return double2ReferenceArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readObject();
        }
    }

    @Override
    public ObjectSet double2ReferenceEntrySet() {
        return this.double2ReferenceEntrySet();
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

    static int access$000(Double2ReferenceArrayMap double2ReferenceArrayMap) {
        return double2ReferenceArrayMap.size;
    }

    static double[] access$100(Double2ReferenceArrayMap double2ReferenceArrayMap) {
        return double2ReferenceArrayMap.key;
    }

    static Object[] access$200(Double2ReferenceArrayMap double2ReferenceArrayMap) {
        return double2ReferenceArrayMap.value;
    }

    static int access$010(Double2ReferenceArrayMap double2ReferenceArrayMap) {
        return double2ReferenceArrayMap.size--;
    }

    static int access$300(Double2ReferenceArrayMap double2ReferenceArrayMap, double d) {
        return double2ReferenceArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2ReferenceMap.Entry<V>>
    implements Double2ReferenceMap.FastEntrySet<V> {
        final Double2ReferenceArrayMap this$0;

        private EntrySet(Double2ReferenceArrayMap double2ReferenceArrayMap) {
            this.this$0 = double2ReferenceArrayMap;
        }

        @Override
        public ObjectIterator<Double2ReferenceMap.Entry<V>> iterator() {
            return new ObjectIterator<Double2ReferenceMap.Entry<V>>(this){
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
                    return this.next < Double2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2ReferenceMap.BasicEntry<Object>(Double2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr], Double2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Double2ReferenceArrayMap.access$200((Double2ReferenceArrayMap)this.this$1.this$0)[Double2ReferenceArrayMap.access$000((Double2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2ReferenceMap.Entry<V>> fastIterator() {
            return new ObjectIterator<Double2ReferenceMap.Entry<V>>(this){
                int next;
                int curr;
                final AbstractDouble2ReferenceMap.BasicEntry<V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2ReferenceMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Double2ReferenceArrayMap.access$200((Double2ReferenceArrayMap)this.this$1.this$0)[Double2ReferenceArrayMap.access$000((Double2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2ReferenceArrayMap.access$000(this.this$0);
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
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && this.this$0.get(d) == entry.getValue();
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
            double d = (Double)entry.getKey();
            Object v = entry.getValue();
            int n = Double2ReferenceArrayMap.access$300(this.this$0, d);
            if (n == -1 || v != Double2ReferenceArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Double2ReferenceArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Double2ReferenceArrayMap.access$100(this.this$0), n + 1, Double2ReferenceArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Double2ReferenceArrayMap.access$200(this.this$0), n + 1, Double2ReferenceArrayMap.access$200(this.this$0), n, n2);
            Double2ReferenceArrayMap.access$010(this.this$0);
            Double2ReferenceArrayMap.access$200((Double2ReferenceArrayMap)this.this$0)[Double2ReferenceArrayMap.access$000((Double2ReferenceArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2ReferenceArrayMap double2ReferenceArrayMap, 1 var2_2) {
            this(double2ReferenceArrayMap);
        }
    }
}

