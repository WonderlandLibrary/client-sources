/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2ByteMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2ByteMap;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
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
public class Float2ByteArrayMap
extends AbstractFloat2ByteMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient byte[] value;
    private int size;

    public Float2ByteArrayMap(float[] fArray, byte[] byArray) {
        this.key = fArray;
        this.value = byArray;
        this.size = fArray.length;
        if (fArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + byArray.length + ")");
        }
    }

    public Float2ByteArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = ByteArrays.EMPTY_ARRAY;
    }

    public Float2ByteArrayMap(int n) {
        this.key = new float[n];
        this.value = new byte[n];
    }

    public Float2ByteArrayMap(Float2ByteMap float2ByteMap) {
        this(float2ByteMap.size());
        this.putAll(float2ByteMap);
    }

    public Float2ByteArrayMap(Map<? extends Float, ? extends Byte> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2ByteArrayMap(float[] fArray, byte[] byArray, int n) {
        this.key = fArray;
        this.value = byArray;
        this.size = n;
        if (fArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + byArray.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2ByteMap.FastEntrySet float2ByteEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(float f) {
        float[] fArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public byte get(float f) {
        float[] fArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
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
    public boolean containsKey(float f) {
        return this.findKey(f) != -1;
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
    public byte put(float f, byte by) {
        int n = this.findKey(f);
        if (n != -1) {
            byte by2 = this.value[n];
            this.value[n] = by;
            return by2;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.key[n2];
                byArray[n2] = this.value[n2];
            }
            this.key = fArray;
            this.value = byArray;
        }
        this.key[this.size] = f;
        this.value[this.size] = by;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public byte remove(float f) {
        int n = this.findKey(f);
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
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2ByteArrayMap this$0;
            {
                this.this$0 = float2ByteArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2ByteArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2ByteArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2ByteArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2ByteArrayMap.access$100(this.this$0), n + 1, Float2ByteArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2ByteArrayMap.access$200(this.this$0), n + 1, Float2ByteArrayMap.access$200(this.this$0), n, n2);
                Float2ByteArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Float2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2ByteArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Float2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Float2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2ByteArrayMap.access$000(this.this$0);
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
            final Float2ByteArrayMap this$0;
            {
                this.this$0 = float2ByteArrayMap;
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
                        return this.pos < Float2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2ByteArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Float2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Float2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2ByteArrayMap.access$000(this.this$0);
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

    public Float2ByteArrayMap clone() {
        Float2ByteArrayMap float2ByteArrayMap;
        try {
            float2ByteArrayMap = (Float2ByteArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2ByteArrayMap.key = (float[])this.key.clone();
        float2ByteArrayMap.value = (byte[])this.value.clone();
        return float2ByteArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeByte(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new byte[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readByte();
        }
    }

    public ObjectSet float2ByteEntrySet() {
        return this.float2ByteEntrySet();
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

    static int access$000(Float2ByteArrayMap float2ByteArrayMap) {
        return float2ByteArrayMap.size;
    }

    static float[] access$100(Float2ByteArrayMap float2ByteArrayMap) {
        return float2ByteArrayMap.key;
    }

    static byte[] access$200(Float2ByteArrayMap float2ByteArrayMap) {
        return float2ByteArrayMap.value;
    }

    static int access$010(Float2ByteArrayMap float2ByteArrayMap) {
        return float2ByteArrayMap.size--;
    }

    static int access$300(Float2ByteArrayMap float2ByteArrayMap, float f) {
        return float2ByteArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2ByteMap.Entry>
    implements Float2ByteMap.FastEntrySet {
        final Float2ByteArrayMap this$0;

        private EntrySet(Float2ByteArrayMap float2ByteArrayMap) {
            this.this$0 = float2ByteArrayMap;
        }

        @Override
        public ObjectIterator<Float2ByteMap.Entry> iterator() {
            return new ObjectIterator<Float2ByteMap.Entry>(this){
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
                    return this.next < Float2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2ByteMap.BasicEntry(Float2ByteArrayMap.access$100(this.this$1.this$0)[this.curr], Float2ByteArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2ByteMap.Entry> fastIterator() {
            return new ObjectIterator<Float2ByteMap.Entry>(this){
                int next;
                int curr;
                final AbstractFloat2ByteMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2ByteMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2ByteArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2ByteArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2ByteArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && this.this$0.get(f) == ((Byte)entry.getValue()).byteValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            byte by = (Byte)entry.getValue();
            int n = Float2ByteArrayMap.access$300(this.this$0, f);
            if (n == -1 || by != Float2ByteArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Float2ByteArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Float2ByteArrayMap.access$100(this.this$0), n + 1, Float2ByteArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Float2ByteArrayMap.access$200(this.this$0), n + 1, Float2ByteArrayMap.access$200(this.this$0), n, n2);
            Float2ByteArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2ByteArrayMap float2ByteArrayMap, 1 var2_2) {
            this(float2ByteArrayMap);
        }
    }
}

