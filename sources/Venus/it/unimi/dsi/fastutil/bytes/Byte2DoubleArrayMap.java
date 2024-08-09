/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
public class Byte2DoubleArrayMap
extends AbstractByte2DoubleMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient double[] value;
    private int size;

    public Byte2DoubleArrayMap(byte[] byArray, double[] dArray) {
        this.key = byArray;
        this.value = dArray;
        this.size = byArray.length;
        if (byArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + dArray.length + ")");
        }
    }

    public Byte2DoubleArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Byte2DoubleArrayMap(int n) {
        this.key = new byte[n];
        this.value = new double[n];
    }

    public Byte2DoubleArrayMap(Byte2DoubleMap byte2DoubleMap) {
        this(byte2DoubleMap.size());
        this.putAll(byte2DoubleMap);
    }

    public Byte2DoubleArrayMap(Map<? extends Byte, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Byte2DoubleArrayMap(byte[] byArray, double[] dArray, int n) {
        this.key = byArray;
        this.value = dArray;
        this.size = n;
        if (byArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + dArray.length + ")");
        }
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + byArray.length + ")");
        }
    }

    public Byte2DoubleMap.FastEntrySet byte2DoubleEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(byte by) {
        byte[] byArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (byArray[n] != by) continue;
            return n;
        }
        return 1;
    }

    @Override
    public double get(byte by) {
        byte[] byArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (byArray[n] != by) continue;
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
    public boolean containsKey(byte by) {
        return this.findKey(by) != -1;
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
    public double put(byte by, double d) {
        int n = this.findKey(by);
        if (n != -1) {
            double d2 = this.value[n];
            this.value[n] = d;
            return d2;
        }
        if (this.size == this.key.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                byArray[n2] = this.key[n2];
                dArray[n2] = this.value[n2];
            }
            this.key = byArray;
            this.value = dArray;
        }
        this.key[this.size] = by;
        this.value[this.size] = d;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double remove(byte by) {
        int n = this.findKey(by);
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
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final Byte2DoubleArrayMap this$0;
            {
                this.this$0 = byte2DoubleArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return Byte2DoubleArrayMap.access$300(this.this$0, by) != -1;
            }

            @Override
            public boolean remove(byte by) {
                int n = Byte2DoubleArrayMap.access$300(this.this$0, by);
                if (n == -1) {
                    return true;
                }
                int n2 = Byte2DoubleArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Byte2DoubleArrayMap.access$100(this.this$0), n + 1, Byte2DoubleArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Byte2DoubleArrayMap.access$200(this.this$0), n + 1, Byte2DoubleArrayMap.access$200(this.this$0), n, n2);
                Byte2DoubleArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public ByteIterator iterator() {
                return new ByteIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Byte2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Byte2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Byte2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2DoubleArrayMap.access$000(this.this$0);
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
            final Byte2DoubleArrayMap this$0;
            {
                this.this$0 = byte2DoubleArrayMap;
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
                        return this.pos < Byte2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Byte2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Byte2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2DoubleArrayMap.access$000(this.this$0);
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

    public Byte2DoubleArrayMap clone() {
        Byte2DoubleArrayMap byte2DoubleArrayMap;
        try {
            byte2DoubleArrayMap = (Byte2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2DoubleArrayMap.key = (byte[])this.key.clone();
        byte2DoubleArrayMap.value = (double[])this.value.clone();
        return byte2DoubleArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.key[i]);
            objectOutputStream.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readByte();
            this.value[i] = objectInputStream.readDouble();
        }
    }

    public ObjectSet byte2DoubleEntrySet() {
        return this.byte2DoubleEntrySet();
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

    static int access$000(Byte2DoubleArrayMap byte2DoubleArrayMap) {
        return byte2DoubleArrayMap.size;
    }

    static byte[] access$100(Byte2DoubleArrayMap byte2DoubleArrayMap) {
        return byte2DoubleArrayMap.key;
    }

    static double[] access$200(Byte2DoubleArrayMap byte2DoubleArrayMap) {
        return byte2DoubleArrayMap.value;
    }

    static int access$010(Byte2DoubleArrayMap byte2DoubleArrayMap) {
        return byte2DoubleArrayMap.size--;
    }

    static int access$300(Byte2DoubleArrayMap byte2DoubleArrayMap, byte by) {
        return byte2DoubleArrayMap.findKey(by);
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2DoubleMap.Entry>
    implements Byte2DoubleMap.FastEntrySet {
        final Byte2DoubleArrayMap this$0;

        private EntrySet(Byte2DoubleArrayMap byte2DoubleArrayMap) {
            this.this$0 = byte2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Byte2DoubleMap.Entry> iterator() {
            return new ObjectIterator<Byte2DoubleMap.Entry>(this){
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
                    return this.next < Byte2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2DoubleMap.BasicEntry(Byte2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Byte2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Byte2DoubleMap.Entry> fastIterator() {
            return new ObjectIterator<Byte2DoubleMap.Entry>(this){
                int next;
                int curr;
                final AbstractByte2DoubleMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractByte2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Byte2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Byte2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Byte2DoubleArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            return this.this$0.containsKey(by) && Double.doubleToLongBits(this.this$0.get(by)) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            double d = (Double)entry.getValue();
            int n = Byte2DoubleArrayMap.access$300(this.this$0, by);
            if (n == -1 || Double.doubleToLongBits(d) != Double.doubleToLongBits(Byte2DoubleArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Byte2DoubleArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Byte2DoubleArrayMap.access$100(this.this$0), n + 1, Byte2DoubleArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Byte2DoubleArrayMap.access$200(this.this$0), n + 1, Byte2DoubleArrayMap.access$200(this.this$0), n, n2);
            Byte2DoubleArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Byte2DoubleArrayMap byte2DoubleArrayMap, 1 var2_2) {
            this(byte2DoubleArrayMap);
        }
    }
}

