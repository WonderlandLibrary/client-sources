/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2IntMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
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
public class Byte2IntArrayMap
extends AbstractByte2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient int[] value;
    private int size;

    public Byte2IntArrayMap(byte[] byArray, int[] nArray) {
        this.key = byArray;
        this.value = nArray;
        this.size = byArray.length;
        if (byArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + nArray.length + ")");
        }
    }

    public Byte2IntArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Byte2IntArrayMap(int n) {
        this.key = new byte[n];
        this.value = new int[n];
    }

    public Byte2IntArrayMap(Byte2IntMap byte2IntMap) {
        this(byte2IntMap.size());
        this.putAll(byte2IntMap);
    }

    public Byte2IntArrayMap(Map<? extends Byte, ? extends Integer> map) {
        this(map.size());
        this.putAll(map);
    }

    public Byte2IntArrayMap(byte[] byArray, int[] nArray, int n) {
        this.key = byArray;
        this.value = nArray;
        this.size = n;
        if (byArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + nArray.length + ")");
        }
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + byArray.length + ")");
        }
    }

    public Byte2IntMap.FastEntrySet byte2IntEntrySet() {
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
    public int get(byte by) {
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
    public boolean containsValue(int n) {
        int n2 = this.size;
        while (n2-- != 0) {
            if (this.value[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int put(byte by, int n) {
        int n2 = this.findKey(by);
        if (n2 != -1) {
            int n3 = this.value[n2];
            this.value[n2] = n;
            return n3;
        }
        if (this.size == this.key.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n4 = this.size;
            while (n4-- != 0) {
                byArray[n4] = this.key[n4];
                nArray[n4] = this.value[n4];
            }
            this.key = byArray;
            this.value = nArray;
        }
        this.key[this.size] = by;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int remove(byte by) {
        int n = this.findKey(by);
        if (n == -1) {
            return this.defRetValue;
        }
        int n2 = this.value[n];
        int n3 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n3);
        System.arraycopy(this.value, n + 1, this.value, n, n3);
        --this.size;
        return n2;
    }

    @Override
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final Byte2IntArrayMap this$0;
            {
                this.this$0 = byte2IntArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return Byte2IntArrayMap.access$300(this.this$0, by) != -1;
            }

            @Override
            public boolean remove(byte by) {
                int n = Byte2IntArrayMap.access$300(this.this$0, by);
                if (n == -1) {
                    return true;
                }
                int n2 = Byte2IntArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Byte2IntArrayMap.access$100(this.this$0), n + 1, Byte2IntArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Byte2IntArrayMap.access$200(this.this$0), n + 1, Byte2IntArrayMap.access$200(this.this$0), n, n2);
                Byte2IntArrayMap.access$010(this.this$0);
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
                        return this.pos < Byte2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2IntArrayMap.access$100(this.this$1.this$0), this.pos, Byte2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2IntArrayMap.access$200(this.this$1.this$0), this.pos, Byte2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2IntArrayMap.access$000(this.this$0);
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
    public IntCollection values() {
        return new AbstractIntCollection(this){
            final Byte2IntArrayMap this$0;
            {
                this.this$0 = byte2IntArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsValue(n);
            }

            @Override
            public IntIterator iterator() {
                return new IntIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Byte2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2IntArrayMap.access$100(this.this$1.this$0), this.pos, Byte2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2IntArrayMap.access$200(this.this$1.this$0), this.pos, Byte2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2IntArrayMap.access$000(this.this$0);
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

    public Byte2IntArrayMap clone() {
        Byte2IntArrayMap byte2IntArrayMap;
        try {
            byte2IntArrayMap = (Byte2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2IntArrayMap.key = (byte[])this.key.clone();
        byte2IntArrayMap.value = (int[])this.value.clone();
        return byte2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readByte();
            this.value[i] = objectInputStream.readInt();
        }
    }

    public ObjectSet byte2IntEntrySet() {
        return this.byte2IntEntrySet();
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

    static int access$000(Byte2IntArrayMap byte2IntArrayMap) {
        return byte2IntArrayMap.size;
    }

    static byte[] access$100(Byte2IntArrayMap byte2IntArrayMap) {
        return byte2IntArrayMap.key;
    }

    static int[] access$200(Byte2IntArrayMap byte2IntArrayMap) {
        return byte2IntArrayMap.value;
    }

    static int access$010(Byte2IntArrayMap byte2IntArrayMap) {
        return byte2IntArrayMap.size--;
    }

    static int access$300(Byte2IntArrayMap byte2IntArrayMap, byte by) {
        return byte2IntArrayMap.findKey(by);
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2IntMap.Entry>
    implements Byte2IntMap.FastEntrySet {
        final Byte2IntArrayMap this$0;

        private EntrySet(Byte2IntArrayMap byte2IntArrayMap) {
            this.this$0 = byte2IntArrayMap;
        }

        @Override
        public ObjectIterator<Byte2IntMap.Entry> iterator() {
            return new ObjectIterator<Byte2IntMap.Entry>(this){
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
                    return this.next < Byte2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2IntMap.BasicEntry(Byte2IntArrayMap.access$100(this.this$1.this$0)[this.curr], Byte2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Byte2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Byte2IntMap.Entry>(this){
                int next;
                int curr;
                final AbstractByte2IntMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractByte2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Byte2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2IntArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Byte2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Byte2IntArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            return this.this$0.containsKey(by) && this.this$0.get(by) == ((Integer)entry.getValue()).intValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            int n = (Integer)entry.getValue();
            int n2 = Byte2IntArrayMap.access$300(this.this$0, by);
            if (n2 == -1 || n != Byte2IntArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Byte2IntArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Byte2IntArrayMap.access$100(this.this$0), n2 + 1, Byte2IntArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Byte2IntArrayMap.access$200(this.this$0), n2 + 1, Byte2IntArrayMap.access$200(this.this$0), n2, n3);
            Byte2IntArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Byte2IntArrayMap byte2IntArrayMap, 1 var2_2) {
            this(byte2IntArrayMap);
        }
    }
}

