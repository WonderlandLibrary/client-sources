/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ShortMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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
public class Byte2ShortArrayMap
extends AbstractByte2ShortMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient short[] value;
    private int size;

    public Byte2ShortArrayMap(byte[] byArray, short[] sArray) {
        this.key = byArray;
        this.value = sArray;
        this.size = byArray.length;
        if (byArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + sArray.length + ")");
        }
    }

    public Byte2ShortArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = ShortArrays.EMPTY_ARRAY;
    }

    public Byte2ShortArrayMap(int n) {
        this.key = new byte[n];
        this.value = new short[n];
    }

    public Byte2ShortArrayMap(Byte2ShortMap byte2ShortMap) {
        this(byte2ShortMap.size());
        this.putAll(byte2ShortMap);
    }

    public Byte2ShortArrayMap(Map<? extends Byte, ? extends Short> map) {
        this(map.size());
        this.putAll(map);
    }

    public Byte2ShortArrayMap(byte[] byArray, short[] sArray, int n) {
        this.key = byArray;
        this.value = sArray;
        this.size = n;
        if (byArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + sArray.length + ")");
        }
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + byArray.length + ")");
        }
    }

    public Byte2ShortMap.FastEntrySet byte2ShortEntrySet() {
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
    public short get(byte by) {
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
    public short put(byte by, short s) {
        int n = this.findKey(by);
        if (n != -1) {
            short s2 = this.value[n];
            this.value[n] = s;
            return s2;
        }
        if (this.size == this.key.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                byArray[n2] = this.key[n2];
                sArray[n2] = this.value[n2];
            }
            this.key = byArray;
            this.value = sArray;
        }
        this.key[this.size] = by;
        this.value[this.size] = s;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public short remove(byte by) {
        int n = this.findKey(by);
        if (n == -1) {
            return this.defRetValue;
        }
        short s = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return s;
    }

    @Override
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final Byte2ShortArrayMap this$0;
            {
                this.this$0 = byte2ShortArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return Byte2ShortArrayMap.access$300(this.this$0, by) != -1;
            }

            @Override
            public boolean remove(byte by) {
                int n = Byte2ShortArrayMap.access$300(this.this$0, by);
                if (n == -1) {
                    return true;
                }
                int n2 = Byte2ShortArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Byte2ShortArrayMap.access$100(this.this$0), n + 1, Byte2ShortArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Byte2ShortArrayMap.access$200(this.this$0), n + 1, Byte2ShortArrayMap.access$200(this.this$0), n, n2);
                Byte2ShortArrayMap.access$010(this.this$0);
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
                        return this.pos < Byte2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2ShortArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Byte2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Byte2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2ShortArrayMap.access$000(this.this$0);
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
            final Byte2ShortArrayMap this$0;
            {
                this.this$0 = byte2ShortArrayMap;
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
                        return this.pos < Byte2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2ShortArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Byte2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Byte2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2ShortArrayMap.access$000(this.this$0);
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

    public Byte2ShortArrayMap clone() {
        Byte2ShortArrayMap byte2ShortArrayMap;
        try {
            byte2ShortArrayMap = (Byte2ShortArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2ShortArrayMap.key = (byte[])this.key.clone();
        byte2ShortArrayMap.value = (short[])this.value.clone();
        return byte2ShortArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.key[i]);
            objectOutputStream.writeShort(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readByte();
            this.value[i] = objectInputStream.readShort();
        }
    }

    public ObjectSet byte2ShortEntrySet() {
        return this.byte2ShortEntrySet();
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

    static int access$000(Byte2ShortArrayMap byte2ShortArrayMap) {
        return byte2ShortArrayMap.size;
    }

    static byte[] access$100(Byte2ShortArrayMap byte2ShortArrayMap) {
        return byte2ShortArrayMap.key;
    }

    static short[] access$200(Byte2ShortArrayMap byte2ShortArrayMap) {
        return byte2ShortArrayMap.value;
    }

    static int access$010(Byte2ShortArrayMap byte2ShortArrayMap) {
        return byte2ShortArrayMap.size--;
    }

    static int access$300(Byte2ShortArrayMap byte2ShortArrayMap, byte by) {
        return byte2ShortArrayMap.findKey(by);
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2ShortMap.Entry>
    implements Byte2ShortMap.FastEntrySet {
        final Byte2ShortArrayMap this$0;

        private EntrySet(Byte2ShortArrayMap byte2ShortArrayMap) {
            this.this$0 = byte2ShortArrayMap;
        }

        @Override
        public ObjectIterator<Byte2ShortMap.Entry> iterator() {
            return new ObjectIterator<Byte2ShortMap.Entry>(this){
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
                    return this.next < Byte2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2ShortMap.BasicEntry(Byte2ShortArrayMap.access$100(this.this$1.this$0)[this.curr], Byte2ShortArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Byte2ShortMap.Entry> fastIterator() {
            return new ObjectIterator<Byte2ShortMap.Entry>(this){
                int next;
                int curr;
                final AbstractByte2ShortMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractByte2ShortMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Byte2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2ShortArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Byte2ShortArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Byte2ShortArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            return this.this$0.containsKey(by) && this.this$0.get(by) == ((Short)entry.getValue()).shortValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            short s = (Short)entry.getValue();
            int n = Byte2ShortArrayMap.access$300(this.this$0, by);
            if (n == -1 || s != Byte2ShortArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Byte2ShortArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Byte2ShortArrayMap.access$100(this.this$0), n + 1, Byte2ShortArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Byte2ShortArrayMap.access$200(this.this$0), n + 1, Byte2ShortArrayMap.access$200(this.this$0), n, n2);
            Byte2ShortArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Byte2ShortArrayMap byte2ShortArrayMap, 1 var2_2) {
            this(byte2ShortArrayMap);
        }
    }
}

