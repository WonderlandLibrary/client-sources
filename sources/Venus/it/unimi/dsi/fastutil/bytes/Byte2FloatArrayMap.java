/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2FloatMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2FloatMap;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
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
public class Byte2FloatArrayMap
extends AbstractByte2FloatMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient float[] value;
    private int size;

    public Byte2FloatArrayMap(byte[] byArray, float[] fArray) {
        this.key = byArray;
        this.value = fArray;
        this.size = byArray.length;
        if (byArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + fArray.length + ")");
        }
    }

    public Byte2FloatArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Byte2FloatArrayMap(int n) {
        this.key = new byte[n];
        this.value = new float[n];
    }

    public Byte2FloatArrayMap(Byte2FloatMap byte2FloatMap) {
        this(byte2FloatMap.size());
        this.putAll(byte2FloatMap);
    }

    public Byte2FloatArrayMap(Map<? extends Byte, ? extends Float> map) {
        this(map.size());
        this.putAll(map);
    }

    public Byte2FloatArrayMap(byte[] byArray, float[] fArray, int n) {
        this.key = byArray;
        this.value = fArray;
        this.size = n;
        if (byArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + fArray.length + ")");
        }
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + byArray.length + ")");
        }
    }

    public Byte2FloatMap.FastEntrySet byte2FloatEntrySet() {
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
    public float get(byte by) {
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
    public boolean containsValue(float f) {
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(this.value[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public float put(byte by, float f) {
        int n = this.findKey(by);
        if (n != -1) {
            float f2 = this.value[n];
            this.value[n] = f;
            return f2;
        }
        if (this.size == this.key.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                byArray[n2] = this.key[n2];
                fArray[n2] = this.value[n2];
            }
            this.key = byArray;
            this.value = fArray;
        }
        this.key[this.size] = by;
        this.value[this.size] = f;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public float remove(byte by) {
        int n = this.findKey(by);
        if (n == -1) {
            return this.defRetValue;
        }
        float f = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return f;
    }

    @Override
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final Byte2FloatArrayMap this$0;
            {
                this.this$0 = byte2FloatArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return Byte2FloatArrayMap.access$300(this.this$0, by) != -1;
            }

            @Override
            public boolean remove(byte by) {
                int n = Byte2FloatArrayMap.access$300(this.this$0, by);
                if (n == -1) {
                    return true;
                }
                int n2 = Byte2FloatArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Byte2FloatArrayMap.access$100(this.this$0), n + 1, Byte2FloatArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Byte2FloatArrayMap.access$200(this.this$0), n + 1, Byte2FloatArrayMap.access$200(this.this$0), n, n2);
                Byte2FloatArrayMap.access$010(this.this$0);
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
                        return this.pos < Byte2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2FloatArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Byte2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Byte2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2FloatArrayMap.access$000(this.this$0);
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
    public FloatCollection values() {
        return new AbstractFloatCollection(this){
            final Byte2FloatArrayMap this$0;
            {
                this.this$0 = byte2FloatArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return this.this$0.containsValue(f);
            }

            @Override
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Byte2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2FloatArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Byte2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Byte2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2FloatArrayMap.access$000(this.this$0);
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

    public Byte2FloatArrayMap clone() {
        Byte2FloatArrayMap byte2FloatArrayMap;
        try {
            byte2FloatArrayMap = (Byte2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2FloatArrayMap.key = (byte[])this.key.clone();
        byte2FloatArrayMap.value = (float[])this.value.clone();
        return byte2FloatArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.key[i]);
            objectOutputStream.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readByte();
            this.value[i] = objectInputStream.readFloat();
        }
    }

    public ObjectSet byte2FloatEntrySet() {
        return this.byte2FloatEntrySet();
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

    static int access$000(Byte2FloatArrayMap byte2FloatArrayMap) {
        return byte2FloatArrayMap.size;
    }

    static byte[] access$100(Byte2FloatArrayMap byte2FloatArrayMap) {
        return byte2FloatArrayMap.key;
    }

    static float[] access$200(Byte2FloatArrayMap byte2FloatArrayMap) {
        return byte2FloatArrayMap.value;
    }

    static int access$010(Byte2FloatArrayMap byte2FloatArrayMap) {
        return byte2FloatArrayMap.size--;
    }

    static int access$300(Byte2FloatArrayMap byte2FloatArrayMap, byte by) {
        return byte2FloatArrayMap.findKey(by);
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2FloatMap.Entry>
    implements Byte2FloatMap.FastEntrySet {
        final Byte2FloatArrayMap this$0;

        private EntrySet(Byte2FloatArrayMap byte2FloatArrayMap) {
            this.this$0 = byte2FloatArrayMap;
        }

        @Override
        public ObjectIterator<Byte2FloatMap.Entry> iterator() {
            return new ObjectIterator<Byte2FloatMap.Entry>(this){
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
                    return this.next < Byte2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2FloatMap.BasicEntry(Byte2FloatArrayMap.access$100(this.this$1.this$0)[this.curr], Byte2FloatArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Byte2FloatMap.Entry> fastIterator() {
            return new ObjectIterator<Byte2FloatMap.Entry>(this){
                int next;
                int curr;
                final AbstractByte2FloatMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractByte2FloatMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Byte2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2FloatArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Byte2FloatArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Byte2FloatArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            return this.this$0.containsKey(by) && Float.floatToIntBits(this.this$0.get(by)) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            int n = Byte2FloatArrayMap.access$300(this.this$0, by);
            if (n == -1 || Float.floatToIntBits(f) != Float.floatToIntBits(Byte2FloatArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Byte2FloatArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Byte2FloatArrayMap.access$100(this.this$0), n + 1, Byte2FloatArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Byte2FloatArrayMap.access$200(this.this$0), n + 1, Byte2FloatArrayMap.access$200(this.this$0), n, n2);
            Byte2FloatArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Byte2FloatArrayMap byte2FloatArrayMap, 1 var2_2) {
            this(byte2FloatArrayMap);
        }
    }
}

