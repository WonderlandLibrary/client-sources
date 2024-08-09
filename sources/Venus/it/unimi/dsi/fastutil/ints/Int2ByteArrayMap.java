/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2ByteMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2ByteMap;
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
public class Int2ByteArrayMap
extends AbstractInt2ByteMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient byte[] value;
    private int size;

    public Int2ByteArrayMap(int[] nArray, byte[] byArray) {
        this.key = nArray;
        this.value = byArray;
        this.size = nArray.length;
        if (nArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + byArray.length + ")");
        }
    }

    public Int2ByteArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = ByteArrays.EMPTY_ARRAY;
    }

    public Int2ByteArrayMap(int n) {
        this.key = new int[n];
        this.value = new byte[n];
    }

    public Int2ByteArrayMap(Int2ByteMap int2ByteMap) {
        this(int2ByteMap.size());
        this.putAll(int2ByteMap);
    }

    public Int2ByteArrayMap(Map<? extends Integer, ? extends Byte> map) {
        this(map.size());
        this.putAll(map);
    }

    public Int2ByteArrayMap(int[] nArray, byte[] byArray, int n) {
        this.key = nArray;
        this.value = byArray;
        this.size = n;
        if (nArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + byArray.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2ByteMap.FastEntrySet int2ByteEntrySet() {
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
    public byte get(int n) {
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
    public boolean containsValue(byte by) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != by) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public byte put(int n, byte by) {
        int n2 = this.findKey(n);
        if (n2 != -1) {
            byte by2 = this.value[n2];
            this.value[n2] = by;
            return by2;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int n3 = this.size;
            while (n3-- != 0) {
                nArray[n3] = this.key[n3];
                byArray[n3] = this.value[n3];
            }
            this.key = nArray;
            this.value = byArray;
        }
        this.key[this.size] = n;
        this.value[this.size] = by;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public byte remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        byte by = this.value[n2];
        int n3 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n3);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n3);
        --this.size;
        return by;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final Int2ByteArrayMap this$0;
            {
                this.this$0 = int2ByteArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return Int2ByteArrayMap.access$300(this.this$0, n) != -1;
            }

            @Override
            public boolean remove(int n) {
                int n2 = Int2ByteArrayMap.access$300(this.this$0, n);
                if (n2 == -1) {
                    return true;
                }
                int n3 = Int2ByteArrayMap.access$000(this.this$0) - n2 - 1;
                System.arraycopy(Int2ByteArrayMap.access$100(this.this$0), n2 + 1, Int2ByteArrayMap.access$100(this.this$0), n2, n3);
                System.arraycopy(Int2ByteArrayMap.access$200(this.this$0), n2 + 1, Int2ByteArrayMap.access$200(this.this$0), n2, n3);
                Int2ByteArrayMap.access$010(this.this$0);
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
                        return this.pos < Int2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2ByteArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Int2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Int2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2ByteArrayMap.access$000(this.this$0);
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
    public ByteCollection values() {
        return new AbstractByteCollection(this){
            final Int2ByteArrayMap this$0;
            {
                this.this$0 = int2ByteArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return this.this$0.containsValue(by);
            }

            @Override
            public ByteIterator iterator() {
                return new ByteIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Int2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2ByteArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Int2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Int2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2ByteArrayMap.access$000(this.this$0);
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

    public Int2ByteArrayMap clone() {
        Int2ByteArrayMap int2ByteArrayMap;
        try {
            int2ByteArrayMap = (Int2ByteArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2ByteArrayMap.key = (int[])this.key.clone();
        int2ByteArrayMap.value = (byte[])this.value.clone();
        return int2ByteArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeByte(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new byte[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readByte();
        }
    }

    public ObjectSet int2ByteEntrySet() {
        return this.int2ByteEntrySet();
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

    static int access$000(Int2ByteArrayMap int2ByteArrayMap) {
        return int2ByteArrayMap.size;
    }

    static int[] access$100(Int2ByteArrayMap int2ByteArrayMap) {
        return int2ByteArrayMap.key;
    }

    static byte[] access$200(Int2ByteArrayMap int2ByteArrayMap) {
        return int2ByteArrayMap.value;
    }

    static int access$010(Int2ByteArrayMap int2ByteArrayMap) {
        return int2ByteArrayMap.size--;
    }

    static int access$300(Int2ByteArrayMap int2ByteArrayMap, int n) {
        return int2ByteArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2ByteMap.Entry>
    implements Int2ByteMap.FastEntrySet {
        final Int2ByteArrayMap this$0;

        private EntrySet(Int2ByteArrayMap int2ByteArrayMap) {
            this.this$0 = int2ByteArrayMap;
        }

        @Override
        public ObjectIterator<Int2ByteMap.Entry> iterator() {
            return new ObjectIterator<Int2ByteMap.Entry>(this){
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
                    return this.next < Int2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2ByteMap.BasicEntry(Int2ByteArrayMap.access$100(this.this$1.this$0)[this.curr], Int2ByteArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2ByteMap.Entry> fastIterator() {
            return new ObjectIterator<Int2ByteMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2ByteMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2ByteMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Int2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2ByteArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Int2ByteArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Int2ByteArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && this.this$0.get(n) == ((Byte)entry.getValue()).byteValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            byte by = (Byte)entry.getValue();
            int n2 = Int2ByteArrayMap.access$300(this.this$0, n);
            if (n2 == -1 || by != Int2ByteArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Int2ByteArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Int2ByteArrayMap.access$100(this.this$0), n2 + 1, Int2ByteArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Int2ByteArrayMap.access$200(this.this$0), n2 + 1, Int2ByteArrayMap.access$200(this.this$0), n2, n3);
            Int2ByteArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Int2ByteArrayMap int2ByteArrayMap, 1 var2_2) {
            this(int2ByteArrayMap);
        }
    }
}

