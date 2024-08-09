/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ShortMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
public class Int2ShortArrayMap
extends AbstractInt2ShortMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient short[] value;
    private int size;

    public Int2ShortArrayMap(int[] nArray, short[] sArray) {
        this.key = nArray;
        this.value = sArray;
        this.size = nArray.length;
        if (nArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + sArray.length + ")");
        }
    }

    public Int2ShortArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = ShortArrays.EMPTY_ARRAY;
    }

    public Int2ShortArrayMap(int n) {
        this.key = new int[n];
        this.value = new short[n];
    }

    public Int2ShortArrayMap(Int2ShortMap int2ShortMap) {
        this(int2ShortMap.size());
        this.putAll(int2ShortMap);
    }

    public Int2ShortArrayMap(Map<? extends Integer, ? extends Short> map) {
        this(map.size());
        this.putAll(map);
    }

    public Int2ShortArrayMap(int[] nArray, short[] sArray, int n) {
        this.key = nArray;
        this.value = sArray;
        this.size = n;
        if (nArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + sArray.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2ShortMap.FastEntrySet int2ShortEntrySet() {
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
    public short get(int n) {
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
    public boolean containsValue(short s) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public short put(int n, short s) {
        int n2 = this.findKey(n);
        if (n2 != -1) {
            short s2 = this.value[n2];
            this.value[n2] = s;
            return s2;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int n3 = this.size;
            while (n3-- != 0) {
                nArray[n3] = this.key[n3];
                sArray[n3] = this.value[n3];
            }
            this.key = nArray;
            this.value = sArray;
        }
        this.key[this.size] = n;
        this.value[this.size] = s;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public short remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        short s = this.value[n2];
        int n3 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n3);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n3);
        --this.size;
        return s;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final Int2ShortArrayMap this$0;
            {
                this.this$0 = int2ShortArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return Int2ShortArrayMap.access$300(this.this$0, n) != -1;
            }

            @Override
            public boolean remove(int n) {
                int n2 = Int2ShortArrayMap.access$300(this.this$0, n);
                if (n2 == -1) {
                    return true;
                }
                int n3 = Int2ShortArrayMap.access$000(this.this$0) - n2 - 1;
                System.arraycopy(Int2ShortArrayMap.access$100(this.this$0), n2 + 1, Int2ShortArrayMap.access$100(this.this$0), n2, n3);
                System.arraycopy(Int2ShortArrayMap.access$200(this.this$0), n2 + 1, Int2ShortArrayMap.access$200(this.this$0), n2, n3);
                Int2ShortArrayMap.access$010(this.this$0);
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
                        return this.pos < Int2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2ShortArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Int2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Int2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2ShortArrayMap.access$000(this.this$0);
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
    public ShortCollection values() {
        return new AbstractShortCollection(this){
            final Int2ShortArrayMap this$0;
            {
                this.this$0 = int2ShortArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return this.this$0.containsValue(s);
            }

            @Override
            public ShortIterator iterator() {
                return new ShortIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Int2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2ShortArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Int2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Int2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2ShortArrayMap.access$000(this.this$0);
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

    public Int2ShortArrayMap clone() {
        Int2ShortArrayMap int2ShortArrayMap;
        try {
            int2ShortArrayMap = (Int2ShortArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2ShortArrayMap.key = (int[])this.key.clone();
        int2ShortArrayMap.value = (short[])this.value.clone();
        return int2ShortArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeShort(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readShort();
        }
    }

    public ObjectSet int2ShortEntrySet() {
        return this.int2ShortEntrySet();
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

    static int access$000(Int2ShortArrayMap int2ShortArrayMap) {
        return int2ShortArrayMap.size;
    }

    static int[] access$100(Int2ShortArrayMap int2ShortArrayMap) {
        return int2ShortArrayMap.key;
    }

    static short[] access$200(Int2ShortArrayMap int2ShortArrayMap) {
        return int2ShortArrayMap.value;
    }

    static int access$010(Int2ShortArrayMap int2ShortArrayMap) {
        return int2ShortArrayMap.size--;
    }

    static int access$300(Int2ShortArrayMap int2ShortArrayMap, int n) {
        return int2ShortArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2ShortMap.Entry>
    implements Int2ShortMap.FastEntrySet {
        final Int2ShortArrayMap this$0;

        private EntrySet(Int2ShortArrayMap int2ShortArrayMap) {
            this.this$0 = int2ShortArrayMap;
        }

        @Override
        public ObjectIterator<Int2ShortMap.Entry> iterator() {
            return new ObjectIterator<Int2ShortMap.Entry>(this){
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
                    return this.next < Int2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2ShortMap.BasicEntry(Int2ShortArrayMap.access$100(this.this$1.this$0)[this.curr], Int2ShortArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2ShortMap.Entry> fastIterator() {
            return new ObjectIterator<Int2ShortMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2ShortMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2ShortMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Int2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2ShortArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Int2ShortArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Int2ShortArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && this.this$0.get(n) == ((Short)entry.getValue()).shortValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            short s = (Short)entry.getValue();
            int n2 = Int2ShortArrayMap.access$300(this.this$0, n);
            if (n2 == -1 || s != Int2ShortArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Int2ShortArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Int2ShortArrayMap.access$100(this.this$0), n2 + 1, Int2ShortArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Int2ShortArrayMap.access$200(this.this$0), n2 + 1, Int2ShortArrayMap.access$200(this.this$0), n2, n3);
            Int2ShortArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Int2ShortArrayMap int2ShortArrayMap, 1 var2_2) {
            this(int2ShortArrayMap);
        }
    }
}

