/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ByteMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
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
public class Short2ByteArrayMap
extends AbstractShort2ByteMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] key;
    private transient byte[] value;
    private int size;

    public Short2ByteArrayMap(short[] sArray, byte[] byArray) {
        this.key = sArray;
        this.value = byArray;
        this.size = sArray.length;
        if (sArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + byArray.length + ")");
        }
    }

    public Short2ByteArrayMap() {
        this.key = ShortArrays.EMPTY_ARRAY;
        this.value = ByteArrays.EMPTY_ARRAY;
    }

    public Short2ByteArrayMap(int n) {
        this.key = new short[n];
        this.value = new byte[n];
    }

    public Short2ByteArrayMap(Short2ByteMap short2ByteMap) {
        this(short2ByteMap.size());
        this.putAll(short2ByteMap);
    }

    public Short2ByteArrayMap(Map<? extends Short, ? extends Byte> map) {
        this(map.size());
        this.putAll(map);
    }

    public Short2ByteArrayMap(short[] sArray, byte[] byArray, int n) {
        this.key = sArray;
        this.value = byArray;
        this.size = n;
        if (sArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + byArray.length + ")");
        }
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + sArray.length + ")");
        }
    }

    public Short2ByteMap.FastEntrySet short2ByteEntrySet() {
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
    public byte get(short s) {
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
    public byte put(short s, byte by) {
        int n = this.findKey(s);
        if (n != -1) {
            byte by2 = this.value[n];
            this.value[n] = by;
            return by2;
        }
        if (this.size == this.key.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                sArray[n2] = this.key[n2];
                byArray[n2] = this.value[n2];
            }
            this.key = sArray;
            this.value = byArray;
        }
        this.key[this.size] = s;
        this.value[this.size] = by;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public byte remove(short s) {
        int n = this.findKey(s);
        if (n == -1) {
            return this.defRetValue;
        }
        byte by = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return by;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final Short2ByteArrayMap this$0;
            {
                this.this$0 = short2ByteArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return Short2ByteArrayMap.access$300(this.this$0, s) != -1;
            }

            @Override
            public boolean remove(short s) {
                int n = Short2ByteArrayMap.access$300(this.this$0, s);
                if (n == -1) {
                    return true;
                }
                int n2 = Short2ByteArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Short2ByteArrayMap.access$100(this.this$0), n + 1, Short2ByteArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Short2ByteArrayMap.access$200(this.this$0), n + 1, Short2ByteArrayMap.access$200(this.this$0), n, n2);
                Short2ByteArrayMap.access$010(this.this$0);
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
                        return this.pos < Short2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2ByteArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Short2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Short2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2ByteArrayMap.access$000(this.this$0);
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
            final Short2ByteArrayMap this$0;
            {
                this.this$0 = short2ByteArrayMap;
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
                        return this.pos < Short2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2ByteArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Short2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Short2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2ByteArrayMap.access$000(this.this$0);
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

    public Short2ByteArrayMap clone() {
        Short2ByteArrayMap short2ByteArrayMap;
        try {
            short2ByteArrayMap = (Short2ByteArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2ByteArrayMap.key = (short[])this.key.clone();
        short2ByteArrayMap.value = (byte[])this.value.clone();
        return short2ByteArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.key[i]);
            objectOutputStream.writeByte(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new short[this.size];
        this.value = new byte[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readShort();
            this.value[i] = objectInputStream.readByte();
        }
    }

    public ObjectSet short2ByteEntrySet() {
        return this.short2ByteEntrySet();
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

    static int access$000(Short2ByteArrayMap short2ByteArrayMap) {
        return short2ByteArrayMap.size;
    }

    static short[] access$100(Short2ByteArrayMap short2ByteArrayMap) {
        return short2ByteArrayMap.key;
    }

    static byte[] access$200(Short2ByteArrayMap short2ByteArrayMap) {
        return short2ByteArrayMap.value;
    }

    static int access$010(Short2ByteArrayMap short2ByteArrayMap) {
        return short2ByteArrayMap.size--;
    }

    static int access$300(Short2ByteArrayMap short2ByteArrayMap, short s) {
        return short2ByteArrayMap.findKey(s);
    }

    private final class EntrySet
    extends AbstractObjectSet<Short2ByteMap.Entry>
    implements Short2ByteMap.FastEntrySet {
        final Short2ByteArrayMap this$0;

        private EntrySet(Short2ByteArrayMap short2ByteArrayMap) {
            this.this$0 = short2ByteArrayMap;
        }

        @Override
        public ObjectIterator<Short2ByteMap.Entry> iterator() {
            return new ObjectIterator<Short2ByteMap.Entry>(this){
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
                    return this.next < Short2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractShort2ByteMap.BasicEntry(Short2ByteArrayMap.access$100(this.this$1.this$0)[this.curr], Short2ByteArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Short2ByteMap.Entry> fastIterator() {
            return new ObjectIterator<Short2ByteMap.Entry>(this){
                int next;
                int curr;
                final AbstractShort2ByteMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractShort2ByteMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Short2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Short2ByteArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Short2ByteArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Short2ByteArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            short s = (Short)entry.getKey();
            return this.this$0.containsKey(s) && this.this$0.get(s) == ((Byte)entry.getValue()).byteValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            short s = (Short)entry.getKey();
            byte by = (Byte)entry.getValue();
            int n = Short2ByteArrayMap.access$300(this.this$0, s);
            if (n == -1 || by != Short2ByteArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Short2ByteArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Short2ByteArrayMap.access$100(this.this$0), n + 1, Short2ByteArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Short2ByteArrayMap.access$200(this.this$0), n + 1, Short2ByteArrayMap.access$200(this.this$0), n, n2);
            Short2ByteArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Short2ByteArrayMap short2ByteArrayMap, 1 var2_2) {
            this(short2ByteArrayMap);
        }
    }
}

