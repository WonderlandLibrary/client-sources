/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2DoubleMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMap;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
public class Short2DoubleArrayMap
extends AbstractShort2DoubleMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] key;
    private transient double[] value;
    private int size;

    public Short2DoubleArrayMap(short[] sArray, double[] dArray) {
        this.key = sArray;
        this.value = dArray;
        this.size = sArray.length;
        if (sArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + dArray.length + ")");
        }
    }

    public Short2DoubleArrayMap() {
        this.key = ShortArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Short2DoubleArrayMap(int n) {
        this.key = new short[n];
        this.value = new double[n];
    }

    public Short2DoubleArrayMap(Short2DoubleMap short2DoubleMap) {
        this(short2DoubleMap.size());
        this.putAll(short2DoubleMap);
    }

    public Short2DoubleArrayMap(Map<? extends Short, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Short2DoubleArrayMap(short[] sArray, double[] dArray, int n) {
        this.key = sArray;
        this.value = dArray;
        this.size = n;
        if (sArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + dArray.length + ")");
        }
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + sArray.length + ")");
        }
    }

    public Short2DoubleMap.FastEntrySet short2DoubleEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(short s) {
        short[] sArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (sArray[n] != s) continue;
            return n;
        }
        return 1;
    }

    @Override
    public double get(short s) {
        short[] sArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (sArray[n] != s) continue;
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
    public boolean containsKey(short s) {
        return this.findKey(s) != -1;
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
    public double put(short s, double d) {
        int n = this.findKey(s);
        if (n != -1) {
            double d2 = this.value[n];
            this.value[n] = d;
            return d2;
        }
        if (this.size == this.key.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                sArray[n2] = this.key[n2];
                dArray[n2] = this.value[n2];
            }
            this.key = sArray;
            this.value = dArray;
        }
        this.key[this.size] = s;
        this.value[this.size] = d;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double remove(short s) {
        int n = this.findKey(s);
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
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final Short2DoubleArrayMap this$0;
            {
                this.this$0 = short2DoubleArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return Short2DoubleArrayMap.access$300(this.this$0, s) != -1;
            }

            @Override
            public boolean remove(short s) {
                int n = Short2DoubleArrayMap.access$300(this.this$0, s);
                if (n == -1) {
                    return true;
                }
                int n2 = Short2DoubleArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Short2DoubleArrayMap.access$100(this.this$0), n + 1, Short2DoubleArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Short2DoubleArrayMap.access$200(this.this$0), n + 1, Short2DoubleArrayMap.access$200(this.this$0), n, n2);
                Short2DoubleArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public ShortIterator iterator() {
                return new ShortIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Short2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Short2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Short2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2DoubleArrayMap.access$000(this.this$0);
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
            final Short2DoubleArrayMap this$0;
            {
                this.this$0 = short2DoubleArrayMap;
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
                        return this.pos < Short2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Short2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Short2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2DoubleArrayMap.access$000(this.this$0);
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

    public Short2DoubleArrayMap clone() {
        Short2DoubleArrayMap short2DoubleArrayMap;
        try {
            short2DoubleArrayMap = (Short2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2DoubleArrayMap.key = (short[])this.key.clone();
        short2DoubleArrayMap.value = (double[])this.value.clone();
        return short2DoubleArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.key[i]);
            objectOutputStream.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new short[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readShort();
            this.value[i] = objectInputStream.readDouble();
        }
    }

    public ObjectSet short2DoubleEntrySet() {
        return this.short2DoubleEntrySet();
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

    static int access$000(Short2DoubleArrayMap short2DoubleArrayMap) {
        return short2DoubleArrayMap.size;
    }

    static short[] access$100(Short2DoubleArrayMap short2DoubleArrayMap) {
        return short2DoubleArrayMap.key;
    }

    static double[] access$200(Short2DoubleArrayMap short2DoubleArrayMap) {
        return short2DoubleArrayMap.value;
    }

    static int access$010(Short2DoubleArrayMap short2DoubleArrayMap) {
        return short2DoubleArrayMap.size--;
    }

    static int access$300(Short2DoubleArrayMap short2DoubleArrayMap, short s) {
        return short2DoubleArrayMap.findKey(s);
    }

    private final class EntrySet
    extends AbstractObjectSet<Short2DoubleMap.Entry>
    implements Short2DoubleMap.FastEntrySet {
        final Short2DoubleArrayMap this$0;

        private EntrySet(Short2DoubleArrayMap short2DoubleArrayMap) {
            this.this$0 = short2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Short2DoubleMap.Entry> iterator() {
            return new ObjectIterator<Short2DoubleMap.Entry>(this){
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
                    return this.next < Short2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractShort2DoubleMap.BasicEntry(Short2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Short2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Short2DoubleMap.Entry> fastIterator() {
            return new ObjectIterator<Short2DoubleMap.Entry>(this){
                int next;
                int curr;
                final AbstractShort2DoubleMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractShort2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Short2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Short2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Short2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Short2DoubleArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Short)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            short s = (Short)entry.getKey();
            return this.this$0.containsKey(s) && Double.doubleToLongBits(this.this$0.get(s)) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Short)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            short s = (Short)entry.getKey();
            double d = (Double)entry.getValue();
            int n = Short2DoubleArrayMap.access$300(this.this$0, s);
            if (n == -1 || Double.doubleToLongBits(d) != Double.doubleToLongBits(Short2DoubleArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Short2DoubleArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Short2DoubleArrayMap.access$100(this.this$0), n + 1, Short2DoubleArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Short2DoubleArrayMap.access$200(this.this$0), n + 1, Short2DoubleArrayMap.access$200(this.this$0), n, n2);
            Short2DoubleArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Short2DoubleArrayMap short2DoubleArrayMap, 1 var2_2) {
            this(short2DoubleArrayMap);
        }
    }
}

