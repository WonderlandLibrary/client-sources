/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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
public class Byte2BooleanArrayMap
extends AbstractByte2BooleanMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient boolean[] value;
    private int size;

    public Byte2BooleanArrayMap(byte[] byArray, boolean[] blArray) {
        this.key = byArray;
        this.value = blArray;
        this.size = byArray.length;
        if (byArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + blArray.length + ")");
        }
    }

    public Byte2BooleanArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Byte2BooleanArrayMap(int n) {
        this.key = new byte[n];
        this.value = new boolean[n];
    }

    public Byte2BooleanArrayMap(Byte2BooleanMap byte2BooleanMap) {
        this(byte2BooleanMap.size());
        this.putAll(byte2BooleanMap);
    }

    public Byte2BooleanArrayMap(Map<? extends Byte, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Byte2BooleanArrayMap(byte[] byArray, boolean[] blArray, int n) {
        this.key = byArray;
        this.value = blArray;
        this.size = n;
        if (byArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + blArray.length + ")");
        }
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + byArray.length + ")");
        }
    }

    public Byte2BooleanMap.FastEntrySet byte2BooleanEntrySet() {
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
    public boolean get(byte by) {
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
    public boolean containsValue(boolean bl) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != bl) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean put(byte by, boolean bl) {
        int n = this.findKey(by);
        if (n != -1) {
            boolean bl2 = this.value[n];
            this.value[n] = bl;
            return bl2;
        }
        if (this.size == this.key.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                byArray[n2] = this.key[n2];
                blArray[n2] = this.value[n2];
            }
            this.key = byArray;
            this.value = blArray;
        }
        this.key[this.size] = by;
        this.value[this.size] = bl;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public boolean remove(byte by) {
        int n = this.findKey(by);
        if (n == -1) {
            return this.defRetValue;
        }
        boolean bl = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return bl;
    }

    @Override
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final Byte2BooleanArrayMap this$0;
            {
                this.this$0 = byte2BooleanArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return Byte2BooleanArrayMap.access$300(this.this$0, by) != -1;
            }

            @Override
            public boolean remove(byte by) {
                int n = Byte2BooleanArrayMap.access$300(this.this$0, by);
                if (n == -1) {
                    return true;
                }
                int n2 = Byte2BooleanArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Byte2BooleanArrayMap.access$100(this.this$0), n + 1, Byte2BooleanArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Byte2BooleanArrayMap.access$200(this.this$0), n + 1, Byte2BooleanArrayMap.access$200(this.this$0), n, n2);
                Byte2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Byte2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Byte2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Byte2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2BooleanArrayMap.access$000(this.this$0);
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
    public BooleanCollection values() {
        return new AbstractBooleanCollection(this){
            final Byte2BooleanArrayMap this$0;
            {
                this.this$0 = byte2BooleanArrayMap;
            }

            @Override
            public boolean contains(boolean bl) {
                return this.this$0.containsValue(bl);
            }

            @Override
            public BooleanIterator iterator() {
                return new BooleanIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Byte2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Byte2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Byte2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2BooleanArrayMap.access$000(this.this$0);
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

    public Byte2BooleanArrayMap clone() {
        Byte2BooleanArrayMap byte2BooleanArrayMap;
        try {
            byte2BooleanArrayMap = (Byte2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2BooleanArrayMap.key = (byte[])this.key.clone();
        byte2BooleanArrayMap.value = (boolean[])this.value.clone();
        return byte2BooleanArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.key[i]);
            objectOutputStream.writeBoolean(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readByte();
            this.value[i] = objectInputStream.readBoolean();
        }
    }

    public ObjectSet byte2BooleanEntrySet() {
        return this.byte2BooleanEntrySet();
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

    static int access$000(Byte2BooleanArrayMap byte2BooleanArrayMap) {
        return byte2BooleanArrayMap.size;
    }

    static byte[] access$100(Byte2BooleanArrayMap byte2BooleanArrayMap) {
        return byte2BooleanArrayMap.key;
    }

    static boolean[] access$200(Byte2BooleanArrayMap byte2BooleanArrayMap) {
        return byte2BooleanArrayMap.value;
    }

    static int access$010(Byte2BooleanArrayMap byte2BooleanArrayMap) {
        return byte2BooleanArrayMap.size--;
    }

    static int access$300(Byte2BooleanArrayMap byte2BooleanArrayMap, byte by) {
        return byte2BooleanArrayMap.findKey(by);
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2BooleanMap.Entry>
    implements Byte2BooleanMap.FastEntrySet {
        final Byte2BooleanArrayMap this$0;

        private EntrySet(Byte2BooleanArrayMap byte2BooleanArrayMap) {
            this.this$0 = byte2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Byte2BooleanMap.Entry> iterator() {
            return new ObjectIterator<Byte2BooleanMap.Entry>(this){
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
                    return this.next < Byte2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2BooleanMap.BasicEntry(Byte2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Byte2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Byte2BooleanMap.Entry> fastIterator() {
            return new ObjectIterator<Byte2BooleanMap.Entry>(this){
                int next;
                int curr;
                final AbstractByte2BooleanMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractByte2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Byte2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Byte2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Byte2BooleanArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            return this.this$0.containsKey(by) && this.this$0.get(by) == ((Boolean)entry.getValue()).booleanValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            boolean bl = (Boolean)entry.getValue();
            int n = Byte2BooleanArrayMap.access$300(this.this$0, by);
            if (n == -1 || bl != Byte2BooleanArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Byte2BooleanArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Byte2BooleanArrayMap.access$100(this.this$0), n + 1, Byte2BooleanArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Byte2BooleanArrayMap.access$200(this.this$0), n + 1, Byte2BooleanArrayMap.access$200(this.this$0), n, n2);
            Byte2BooleanArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Byte2BooleanArrayMap byte2BooleanArrayMap, 1 var2_2) {
            this(byte2BooleanArrayMap);
        }
    }
}

