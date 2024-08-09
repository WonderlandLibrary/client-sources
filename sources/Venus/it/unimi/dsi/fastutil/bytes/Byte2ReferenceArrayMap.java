/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ReferenceMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
public class Byte2ReferenceArrayMap<V>
extends AbstractByte2ReferenceMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient Object[] value;
    private int size;

    public Byte2ReferenceArrayMap(byte[] byArray, Object[] objectArray) {
        this.key = byArray;
        this.value = objectArray;
        this.size = byArray.length;
        if (byArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + objectArray.length + ")");
        }
    }

    public Byte2ReferenceArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Byte2ReferenceArrayMap(int n) {
        this.key = new byte[n];
        this.value = new Object[n];
    }

    public Byte2ReferenceArrayMap(Byte2ReferenceMap<V> byte2ReferenceMap) {
        this(byte2ReferenceMap.size());
        this.putAll(byte2ReferenceMap);
    }

    public Byte2ReferenceArrayMap(Map<? extends Byte, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }

    public Byte2ReferenceArrayMap(byte[] byArray, Object[] objectArray, int n) {
        this.key = byArray;
        this.value = objectArray;
        this.size = n;
        if (byArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + objectArray.length + ")");
        }
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + byArray.length + ")");
        }
    }

    public Byte2ReferenceMap.FastEntrySet<V> byte2ReferenceEntrySet() {
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
    public V get(byte by) {
        byte[] byArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (byArray[n] != by) continue;
            return (V)this.value[n];
        }
        return (V)this.defRetValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        int n = this.size;
        while (n-- != 0) {
            this.value[n] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(byte by) {
        return this.findKey(by) != -1;
    }

    @Override
    public boolean containsValue(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public V put(byte by, V v) {
        int n = this.findKey(by);
        if (n != -1) {
            Object object = this.value[n];
            this.value[n] = v;
            return (V)object;
        }
        if (this.size == this.key.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                byArray[n2] = this.key[n2];
                objectArray[n2] = this.value[n2];
            }
            this.key = byArray;
            this.value = objectArray;
        }
        this.key[this.size] = by;
        this.value[this.size] = v;
        ++this.size;
        return (V)this.defRetValue;
    }

    @Override
    public V remove(byte by) {
        int n = this.findKey(by);
        if (n == -1) {
            return (V)this.defRetValue;
        }
        Object object = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.value[this.size] = null;
        return (V)object;
    }

    @Override
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final Byte2ReferenceArrayMap this$0;
            {
                this.this$0 = byte2ReferenceArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return Byte2ReferenceArrayMap.access$300(this.this$0, by) != -1;
            }

            @Override
            public boolean remove(byte by) {
                int n = Byte2ReferenceArrayMap.access$300(this.this$0, by);
                if (n == -1) {
                    return true;
                }
                int n2 = Byte2ReferenceArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Byte2ReferenceArrayMap.access$100(this.this$0), n + 1, Byte2ReferenceArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Byte2ReferenceArrayMap.access$200(this.this$0), n + 1, Byte2ReferenceArrayMap.access$200(this.this$0), n, n2);
                Byte2ReferenceArrayMap.access$010(this.this$0);
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
                        return this.pos < Byte2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2ReferenceArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Byte2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Byte2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2ReferenceArrayMap.access$000(this.this$0);
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
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(this){
            final Byte2ReferenceArrayMap this$0;
            {
                this.this$0 = byte2ReferenceArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsValue(object);
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Byte2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2ReferenceArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Byte2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Byte2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2ReferenceArrayMap.access$000(this.this$0);
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

    public Byte2ReferenceArrayMap<V> clone() {
        Byte2ReferenceArrayMap byte2ReferenceArrayMap;
        try {
            byte2ReferenceArrayMap = (Byte2ReferenceArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2ReferenceArrayMap.key = (byte[])this.key.clone();
        byte2ReferenceArrayMap.value = (Object[])this.value.clone();
        return byte2ReferenceArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.key[i]);
            objectOutputStream.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readByte();
            this.value[i] = objectInputStream.readObject();
        }
    }

    @Override
    public ObjectSet byte2ReferenceEntrySet() {
        return this.byte2ReferenceEntrySet();
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

    static int access$000(Byte2ReferenceArrayMap byte2ReferenceArrayMap) {
        return byte2ReferenceArrayMap.size;
    }

    static byte[] access$100(Byte2ReferenceArrayMap byte2ReferenceArrayMap) {
        return byte2ReferenceArrayMap.key;
    }

    static Object[] access$200(Byte2ReferenceArrayMap byte2ReferenceArrayMap) {
        return byte2ReferenceArrayMap.value;
    }

    static int access$010(Byte2ReferenceArrayMap byte2ReferenceArrayMap) {
        return byte2ReferenceArrayMap.size--;
    }

    static int access$300(Byte2ReferenceArrayMap byte2ReferenceArrayMap, byte by) {
        return byte2ReferenceArrayMap.findKey(by);
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2ReferenceMap.Entry<V>>
    implements Byte2ReferenceMap.FastEntrySet<V> {
        final Byte2ReferenceArrayMap this$0;

        private EntrySet(Byte2ReferenceArrayMap byte2ReferenceArrayMap) {
            this.this$0 = byte2ReferenceArrayMap;
        }

        @Override
        public ObjectIterator<Byte2ReferenceMap.Entry<V>> iterator() {
            return new ObjectIterator<Byte2ReferenceMap.Entry<V>>(this){
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
                    return this.next < Byte2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2ReferenceMap.BasicEntry<Object>(Byte2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr], Byte2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Byte2ReferenceArrayMap.access$200((Byte2ReferenceArrayMap)this.this$1.this$0)[Byte2ReferenceArrayMap.access$000((Byte2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Byte2ReferenceMap.Entry<V>> fastIterator() {
            return new ObjectIterator<Byte2ReferenceMap.Entry<V>>(this){
                int next;
                int curr;
                final AbstractByte2ReferenceMap.BasicEntry<V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractByte2ReferenceMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Byte2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Byte2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Byte2ReferenceArrayMap.access$200((Byte2ReferenceArrayMap)this.this$1.this$0)[Byte2ReferenceArrayMap.access$000((Byte2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Byte2ReferenceArrayMap.access$000(this.this$0);
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
            byte by = (Byte)entry.getKey();
            return this.this$0.containsKey(by) && this.this$0.get(by) == entry.getValue();
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
            byte by = (Byte)entry.getKey();
            Object v = entry.getValue();
            int n = Byte2ReferenceArrayMap.access$300(this.this$0, by);
            if (n == -1 || v != Byte2ReferenceArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Byte2ReferenceArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Byte2ReferenceArrayMap.access$100(this.this$0), n + 1, Byte2ReferenceArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Byte2ReferenceArrayMap.access$200(this.this$0), n + 1, Byte2ReferenceArrayMap.access$200(this.this$0), n, n2);
            Byte2ReferenceArrayMap.access$010(this.this$0);
            Byte2ReferenceArrayMap.access$200((Byte2ReferenceArrayMap)this.this$0)[Byte2ReferenceArrayMap.access$000((Byte2ReferenceArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Byte2ReferenceArrayMap byte2ReferenceArrayMap, 1 var2_2) {
            this(byte2ReferenceArrayMap);
        }
    }
}

