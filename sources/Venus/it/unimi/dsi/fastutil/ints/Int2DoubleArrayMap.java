/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
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
public class Int2DoubleArrayMap
extends AbstractInt2DoubleMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient double[] value;
    private int size;

    public Int2DoubleArrayMap(int[] nArray, double[] dArray) {
        this.key = nArray;
        this.value = dArray;
        this.size = nArray.length;
        if (nArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + dArray.length + ")");
        }
    }

    public Int2DoubleArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Int2DoubleArrayMap(int n) {
        this.key = new int[n];
        this.value = new double[n];
    }

    public Int2DoubleArrayMap(Int2DoubleMap int2DoubleMap) {
        this(int2DoubleMap.size());
        this.putAll(int2DoubleMap);
    }

    public Int2DoubleArrayMap(Map<? extends Integer, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Int2DoubleArrayMap(int[] nArray, double[] dArray, int n) {
        this.key = nArray;
        this.value = dArray;
        this.size = n;
        if (nArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + dArray.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2DoubleMap.FastEntrySet int2DoubleEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(int n) {
        int[] nArray = this.key;
        int n2 = this.size;
        while (n2-- != 0) {
            if (nArray[n2] != n) continue;
            return n2;
        }
        return 1;
    }

    @Override
    public double get(int n) {
        int[] nArray = this.key;
        int n2 = this.size;
        while (n2-- != 0) {
            if (nArray[n2] != n) continue;
            return this.value[n2];
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
    public boolean containsKey(int n) {
        return this.findKey(n) != -1;
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
    public double put(int n, double d) {
        int n2 = this.findKey(n);
        if (n2 != -1) {
            double d2 = this.value[n2];
            this.value[n2] = d;
            return d2;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int n3 = this.size;
            while (n3-- != 0) {
                nArray[n3] = this.key[n3];
                dArray[n3] = this.value[n3];
            }
            this.key = nArray;
            this.value = dArray;
        }
        this.key[this.size] = n;
        this.value[this.size] = d;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        double d = this.value[n2];
        int n3 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n3);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n3);
        --this.size;
        return d;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final Int2DoubleArrayMap this$0;
            {
                this.this$0 = int2DoubleArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return Int2DoubleArrayMap.access$300(this.this$0, n) != -1;
            }

            @Override
            public boolean remove(int n) {
                int n2 = Int2DoubleArrayMap.access$300(this.this$0, n);
                if (n2 == -1) {
                    return true;
                }
                int n3 = Int2DoubleArrayMap.access$000(this.this$0) - n2 - 1;
                System.arraycopy(Int2DoubleArrayMap.access$100(this.this$0), n2 + 1, Int2DoubleArrayMap.access$100(this.this$0), n2, n3);
                System.arraycopy(Int2DoubleArrayMap.access$200(this.this$0), n2 + 1, Int2DoubleArrayMap.access$200(this.this$0), n2, n3);
                Int2DoubleArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public IntIterator iterator() {
                return new IntIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Int2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Int2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Int2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2DoubleArrayMap.access$000(this.this$0);
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
            final Int2DoubleArrayMap this$0;
            {
                this.this$0 = int2DoubleArrayMap;
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
                        return this.pos < Int2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Int2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Int2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2DoubleArrayMap.access$000(this.this$0);
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

    public Int2DoubleArrayMap clone() {
        Int2DoubleArrayMap int2DoubleArrayMap;
        try {
            int2DoubleArrayMap = (Int2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2DoubleArrayMap.key = (int[])this.key.clone();
        int2DoubleArrayMap.value = (double[])this.value.clone();
        return int2DoubleArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readDouble();
        }
    }

    public ObjectSet int2DoubleEntrySet() {
        return this.int2DoubleEntrySet();
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

    static int access$000(Int2DoubleArrayMap int2DoubleArrayMap) {
        return int2DoubleArrayMap.size;
    }

    static int[] access$100(Int2DoubleArrayMap int2DoubleArrayMap) {
        return int2DoubleArrayMap.key;
    }

    static double[] access$200(Int2DoubleArrayMap int2DoubleArrayMap) {
        return int2DoubleArrayMap.value;
    }

    static int access$010(Int2DoubleArrayMap int2DoubleArrayMap) {
        return int2DoubleArrayMap.size--;
    }

    static int access$300(Int2DoubleArrayMap int2DoubleArrayMap, int n) {
        return int2DoubleArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2DoubleMap.Entry>
    implements Int2DoubleMap.FastEntrySet {
        final Int2DoubleArrayMap this$0;

        private EntrySet(Int2DoubleArrayMap int2DoubleArrayMap) {
            this.this$0 = int2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Int2DoubleMap.Entry> iterator() {
            return new ObjectIterator<Int2DoubleMap.Entry>(this){
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
                    return this.next < Int2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2DoubleMap.BasicEntry(Int2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Int2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2DoubleMap.Entry> fastIterator() {
            return new ObjectIterator<Int2DoubleMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2DoubleMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Int2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Int2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Int2DoubleArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && Double.doubleToLongBits(this.this$0.get(n)) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            double d = (Double)entry.getValue();
            int n2 = Int2DoubleArrayMap.access$300(this.this$0, n);
            if (n2 == -1 || Double.doubleToLongBits(d) != Double.doubleToLongBits(Int2DoubleArrayMap.access$200(this.this$0)[n2])) {
                return true;
            }
            int n3 = Int2DoubleArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Int2DoubleArrayMap.access$100(this.this$0), n2 + 1, Int2DoubleArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Int2DoubleArrayMap.access$200(this.this$0), n2 + 1, Int2DoubleArrayMap.access$200(this.this$0), n2, n3);
            Int2DoubleArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Int2DoubleArrayMap int2DoubleArrayMap, 1 var2_2) {
            this(int2DoubleArrayMap);
        }
    }
}

