/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2DoubleMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2DoubleMap;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
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
public class Char2DoubleArrayMap
extends AbstractChar2DoubleMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient double[] value;
    private int size;

    public Char2DoubleArrayMap(char[] cArray, double[] dArray) {
        this.key = cArray;
        this.value = dArray;
        this.size = cArray.length;
        if (cArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + dArray.length + ")");
        }
    }

    public Char2DoubleArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Char2DoubleArrayMap(int n) {
        this.key = new char[n];
        this.value = new double[n];
    }

    public Char2DoubleArrayMap(Char2DoubleMap char2DoubleMap) {
        this(char2DoubleMap.size());
        this.putAll(char2DoubleMap);
    }

    public Char2DoubleArrayMap(Map<? extends Character, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2DoubleArrayMap(char[] cArray, double[] dArray, int n) {
        this.key = cArray;
        this.value = dArray;
        this.size = n;
        if (cArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + dArray.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2DoubleMap.FastEntrySet char2DoubleEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(char c) {
        char[] cArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (cArray[n] != c) continue;
            return n;
        }
        return 1;
    }

    @Override
    public double get(char c) {
        char[] cArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (cArray[n] != c) continue;
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
    public boolean containsKey(char c) {
        return this.findKey(c) != -1;
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
    public double put(char c, double d) {
        int n = this.findKey(c);
        if (n != -1) {
            double d2 = this.value[n];
            this.value[n] = d;
            return d2;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.key[n2];
                dArray[n2] = this.value[n2];
            }
            this.key = cArray;
            this.value = dArray;
        }
        this.key[this.size] = c;
        this.value[this.size] = d;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double remove(char c) {
        int n = this.findKey(c);
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
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2DoubleArrayMap this$0;
            {
                this.this$0 = char2DoubleArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2DoubleArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2DoubleArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2DoubleArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2DoubleArrayMap.access$100(this.this$0), n + 1, Char2DoubleArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2DoubleArrayMap.access$200(this.this$0), n + 1, Char2DoubleArrayMap.access$200(this.this$0), n, n2);
                Char2DoubleArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public CharIterator iterator() {
                return new CharIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Char2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Char2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Char2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2DoubleArrayMap.access$000(this.this$0);
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
            final Char2DoubleArrayMap this$0;
            {
                this.this$0 = char2DoubleArrayMap;
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
                        return this.pos < Char2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Char2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Char2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2DoubleArrayMap.access$000(this.this$0);
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

    public Char2DoubleArrayMap clone() {
        Char2DoubleArrayMap char2DoubleArrayMap;
        try {
            char2DoubleArrayMap = (Char2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2DoubleArrayMap.key = (char[])this.key.clone();
        char2DoubleArrayMap.value = (double[])this.value.clone();
        return char2DoubleArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readDouble();
        }
    }

    public ObjectSet char2DoubleEntrySet() {
        return this.char2DoubleEntrySet();
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

    static int access$000(Char2DoubleArrayMap char2DoubleArrayMap) {
        return char2DoubleArrayMap.size;
    }

    static char[] access$100(Char2DoubleArrayMap char2DoubleArrayMap) {
        return char2DoubleArrayMap.key;
    }

    static double[] access$200(Char2DoubleArrayMap char2DoubleArrayMap) {
        return char2DoubleArrayMap.value;
    }

    static int access$010(Char2DoubleArrayMap char2DoubleArrayMap) {
        return char2DoubleArrayMap.size--;
    }

    static int access$300(Char2DoubleArrayMap char2DoubleArrayMap, char c) {
        return char2DoubleArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2DoubleMap.Entry>
    implements Char2DoubleMap.FastEntrySet {
        final Char2DoubleArrayMap this$0;

        private EntrySet(Char2DoubleArrayMap char2DoubleArrayMap) {
            this.this$0 = char2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Char2DoubleMap.Entry> iterator() {
            return new ObjectIterator<Char2DoubleMap.Entry>(this){
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
                    return this.next < Char2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2DoubleMap.BasicEntry(Char2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Char2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2DoubleMap.Entry> fastIterator() {
            return new ObjectIterator<Char2DoubleMap.Entry>(this){
                int next;
                int curr;
                final AbstractChar2DoubleMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2DoubleMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2DoubleArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Character)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && Double.doubleToLongBits(this.this$0.get(c)) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Character)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            double d = (Double)entry.getValue();
            int n = Char2DoubleArrayMap.access$300(this.this$0, c);
            if (n == -1 || Double.doubleToLongBits(d) != Double.doubleToLongBits(Char2DoubleArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Char2DoubleArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Char2DoubleArrayMap.access$100(this.this$0), n + 1, Char2DoubleArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Char2DoubleArrayMap.access$200(this.this$0), n + 1, Char2DoubleArrayMap.access$200(this.this$0), n, n2);
            Char2DoubleArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2DoubleArrayMap char2DoubleArrayMap, 1 var2_2) {
            this(char2DoubleArrayMap);
        }
    }
}

