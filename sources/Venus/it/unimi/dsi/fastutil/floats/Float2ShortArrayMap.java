/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2ShortMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2ShortMap;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
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
public class Float2ShortArrayMap
extends AbstractFloat2ShortMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient short[] value;
    private int size;

    public Float2ShortArrayMap(float[] fArray, short[] sArray) {
        this.key = fArray;
        this.value = sArray;
        this.size = fArray.length;
        if (fArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + sArray.length + ")");
        }
    }

    public Float2ShortArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = ShortArrays.EMPTY_ARRAY;
    }

    public Float2ShortArrayMap(int n) {
        this.key = new float[n];
        this.value = new short[n];
    }

    public Float2ShortArrayMap(Float2ShortMap float2ShortMap) {
        this(float2ShortMap.size());
        this.putAll(float2ShortMap);
    }

    public Float2ShortArrayMap(Map<? extends Float, ? extends Short> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2ShortArrayMap(float[] fArray, short[] sArray, int n) {
        this.key = fArray;
        this.value = sArray;
        this.size = n;
        if (fArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + sArray.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2ShortMap.FastEntrySet float2ShortEntrySet() {
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
    public short get(float f) {
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
    public short put(float f, short s) {
        int n = this.findKey(f);
        if (n != -1) {
            short s2 = this.value[n];
            this.value[n] = s;
            return s2;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.key[n2];
                sArray[n2] = this.value[n2];
            }
            this.key = fArray;
            this.value = sArray;
        }
        this.key[this.size] = f;
        this.value[this.size] = s;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public short remove(float f) {
        int n = this.findKey(f);
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
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2ShortArrayMap this$0;
            {
                this.this$0 = float2ShortArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2ShortArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2ShortArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2ShortArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2ShortArrayMap.access$100(this.this$0), n + 1, Float2ShortArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2ShortArrayMap.access$200(this.this$0), n + 1, Float2ShortArrayMap.access$200(this.this$0), n, n2);
                Float2ShortArrayMap.access$010(this.this$0);
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
                        return this.pos < Float2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2ShortArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Float2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Float2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2ShortArrayMap.access$000(this.this$0);
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
            final Float2ShortArrayMap this$0;
            {
                this.this$0 = float2ShortArrayMap;
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
                        return this.pos < Float2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2ShortArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Float2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Float2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2ShortArrayMap.access$000(this.this$0);
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

    public Float2ShortArrayMap clone() {
        Float2ShortArrayMap float2ShortArrayMap;
        try {
            float2ShortArrayMap = (Float2ShortArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2ShortArrayMap.key = (float[])this.key.clone();
        float2ShortArrayMap.value = (short[])this.value.clone();
        return float2ShortArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeShort(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readShort();
        }
    }

    public ObjectSet float2ShortEntrySet() {
        return this.float2ShortEntrySet();
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

    static int access$000(Float2ShortArrayMap float2ShortArrayMap) {
        return float2ShortArrayMap.size;
    }

    static float[] access$100(Float2ShortArrayMap float2ShortArrayMap) {
        return float2ShortArrayMap.key;
    }

    static short[] access$200(Float2ShortArrayMap float2ShortArrayMap) {
        return float2ShortArrayMap.value;
    }

    static int access$010(Float2ShortArrayMap float2ShortArrayMap) {
        return float2ShortArrayMap.size--;
    }

    static int access$300(Float2ShortArrayMap float2ShortArrayMap, float f) {
        return float2ShortArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2ShortMap.Entry>
    implements Float2ShortMap.FastEntrySet {
        final Float2ShortArrayMap this$0;

        private EntrySet(Float2ShortArrayMap float2ShortArrayMap) {
            this.this$0 = float2ShortArrayMap;
        }

        @Override
        public ObjectIterator<Float2ShortMap.Entry> iterator() {
            return new ObjectIterator<Float2ShortMap.Entry>(this){
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
                    return this.next < Float2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2ShortMap.BasicEntry(Float2ShortArrayMap.access$100(this.this$1.this$0)[this.curr], Float2ShortArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2ShortMap.Entry> fastIterator() {
            return new ObjectIterator<Float2ShortMap.Entry>(this){
                int next;
                int curr;
                final AbstractFloat2ShortMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2ShortMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2ShortArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2ShortArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2ShortArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && this.this$0.get(f) == ((Short)entry.getValue()).shortValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            short s = (Short)entry.getValue();
            int n = Float2ShortArrayMap.access$300(this.this$0, f);
            if (n == -1 || s != Float2ShortArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Float2ShortArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Float2ShortArrayMap.access$100(this.this$0), n + 1, Float2ShortArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Float2ShortArrayMap.access$200(this.this$0), n + 1, Float2ShortArrayMap.access$200(this.this$0), n, n2);
            Float2ShortArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2ShortArrayMap float2ShortArrayMap, 1 var2_2) {
            this(float2ShortArrayMap);
        }
    }
}

