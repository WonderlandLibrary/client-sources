/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2CharMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.Double2CharMap;
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
public class Double2CharArrayMap
extends AbstractDouble2CharMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient double[] key;
    private transient char[] value;
    private int size;

    public Double2CharArrayMap(double[] dArray, char[] cArray) {
        this.key = dArray;
        this.value = cArray;
        this.size = dArray.length;
        if (dArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + cArray.length + ")");
        }
    }

    public Double2CharArrayMap() {
        this.key = DoubleArrays.EMPTY_ARRAY;
        this.value = CharArrays.EMPTY_ARRAY;
    }

    public Double2CharArrayMap(int n) {
        this.key = new double[n];
        this.value = new char[n];
    }

    public Double2CharArrayMap(Double2CharMap double2CharMap) {
        this(double2CharMap.size());
        this.putAll(double2CharMap);
    }

    public Double2CharArrayMap(Map<? extends Double, ? extends Character> map) {
        this(map.size());
        this.putAll(map);
    }

    public Double2CharArrayMap(double[] dArray, char[] cArray, int n) {
        this.key = dArray;
        this.value = cArray;
        this.size = n;
        if (dArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + dArray.length + ", " + cArray.length + ")");
        }
        if (n > dArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + dArray.length + ")");
        }
    }

    public Double2CharMap.FastEntrySet double2CharEntrySet() {
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
    public char get(double d) {
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
    public boolean containsValue(char c) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != c) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public char put(double d, char c) {
        int n = this.findKey(d);
        if (n != -1) {
            char c2 = this.value[n];
            this.value[n] = c;
            return c2;
        }
        if (this.size == this.key.length) {
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                dArray[n2] = this.key[n2];
                cArray[n2] = this.value[n2];
            }
            this.key = dArray;
            this.value = cArray;
        }
        this.key[this.size] = d;
        this.value[this.size] = c;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public char remove(double d) {
        int n = this.findKey(d);
        if (n == -1) {
            return this.defRetValue;
        }
        char c = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return c;
    }

    @Override
    public DoubleSet keySet() {
        return new AbstractDoubleSet(this){
            final Double2CharArrayMap this$0;
            {
                this.this$0 = double2CharArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return Double2CharArrayMap.access$300(this.this$0, d) != -1;
            }

            @Override
            public boolean remove(double d) {
                int n = Double2CharArrayMap.access$300(this.this$0, d);
                if (n == -1) {
                    return true;
                }
                int n2 = Double2CharArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Double2CharArrayMap.access$100(this.this$0), n + 1, Double2CharArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Double2CharArrayMap.access$200(this.this$0), n + 1, Double2CharArrayMap.access$200(this.this$0), n, n2);
                Double2CharArrayMap.access$010(this.this$0);
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
                        return this.pos < Double2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2CharArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2CharArrayMap.access$100(this.this$1.this$0), this.pos, Double2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2CharArrayMap.access$200(this.this$1.this$0), this.pos, Double2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2CharArrayMap.access$000(this.this$0);
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
    public CharCollection values() {
        return new AbstractCharCollection(this){
            final Double2CharArrayMap this$0;
            {
                this.this$0 = double2CharArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return this.this$0.containsValue(c);
            }

            @Override
            public CharIterator iterator() {
                return new CharIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Double2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Double2CharArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Double2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Double2CharArrayMap.access$100(this.this$1.this$0), this.pos, Double2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Double2CharArrayMap.access$200(this.this$1.this$0), this.pos, Double2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Double2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Double2CharArrayMap.access$000(this.this$0);
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

    public Double2CharArrayMap clone() {
        Double2CharArrayMap double2CharArrayMap;
        try {
            double2CharArrayMap = (Double2CharArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        double2CharArrayMap.key = (double[])this.key.clone();
        double2CharArrayMap.value = (char[])this.value.clone();
        return double2CharArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeDouble(this.key[i]);
            objectOutputStream.writeChar(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new double[this.size];
        this.value = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readDouble();
            this.value[i] = objectInputStream.readChar();
        }
    }

    public ObjectSet double2CharEntrySet() {
        return this.double2CharEntrySet();
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

    static int access$000(Double2CharArrayMap double2CharArrayMap) {
        return double2CharArrayMap.size;
    }

    static double[] access$100(Double2CharArrayMap double2CharArrayMap) {
        return double2CharArrayMap.key;
    }

    static char[] access$200(Double2CharArrayMap double2CharArrayMap) {
        return double2CharArrayMap.value;
    }

    static int access$010(Double2CharArrayMap double2CharArrayMap) {
        return double2CharArrayMap.size--;
    }

    static int access$300(Double2CharArrayMap double2CharArrayMap, double d) {
        return double2CharArrayMap.findKey(d);
    }

    private final class EntrySet
    extends AbstractObjectSet<Double2CharMap.Entry>
    implements Double2CharMap.FastEntrySet {
        final Double2CharArrayMap this$0;

        private EntrySet(Double2CharArrayMap double2CharArrayMap) {
            this.this$0 = double2CharArrayMap;
        }

        @Override
        public ObjectIterator<Double2CharMap.Entry> iterator() {
            return new ObjectIterator<Double2CharMap.Entry>(this){
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
                    return this.next < Double2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractDouble2CharMap.BasicEntry(Double2CharArrayMap.access$100(this.this$1.this$0)[this.curr], Double2CharArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Double2CharMap.Entry> fastIterator() {
            return new ObjectIterator<Double2CharMap.Entry>(this){
                int next;
                int curr;
                final AbstractDouble2CharMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractDouble2CharMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Double2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Double2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Double2CharArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Double2CharArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Double2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Double2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Double2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Double2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Double2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Double2CharArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            double d = (Double)entry.getKey();
            return this.this$0.containsKey(d) && this.this$0.get(d) == ((Character)entry.getValue()).charValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            double d = (Double)entry.getKey();
            char c = ((Character)entry.getValue()).charValue();
            int n = Double2CharArrayMap.access$300(this.this$0, d);
            if (n == -1 || c != Double2CharArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Double2CharArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Double2CharArrayMap.access$100(this.this$0), n + 1, Double2CharArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Double2CharArrayMap.access$200(this.this$0), n + 1, Double2CharArrayMap.access$200(this.this$0), n, n2);
            Double2CharArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Double2CharArrayMap double2CharArrayMap, 1 var2_2) {
            this(double2CharArrayMap);
        }
    }
}

