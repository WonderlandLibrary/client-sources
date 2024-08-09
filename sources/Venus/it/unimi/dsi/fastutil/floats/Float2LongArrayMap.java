/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2LongMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2LongMap;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
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
public class Float2LongArrayMap
extends AbstractFloat2LongMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient long[] value;
    private int size;

    public Float2LongArrayMap(float[] fArray, long[] lArray) {
        this.key = fArray;
        this.value = lArray;
        this.size = fArray.length;
        if (fArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + lArray.length + ")");
        }
    }

    public Float2LongArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Float2LongArrayMap(int n) {
        this.key = new float[n];
        this.value = new long[n];
    }

    public Float2LongArrayMap(Float2LongMap float2LongMap) {
        this(float2LongMap.size());
        this.putAll(float2LongMap);
    }

    public Float2LongArrayMap(Map<? extends Float, ? extends Long> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2LongArrayMap(float[] fArray, long[] lArray, int n) {
        this.key = fArray;
        this.value = lArray;
        this.size = n;
        if (fArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + lArray.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2LongMap.FastEntrySet float2LongEntrySet() {
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
    public long get(float f) {
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
    public boolean containsValue(long l) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != l) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public long put(float f, long l) {
        int n = this.findKey(f);
        if (n != -1) {
            long l2 = this.value[n];
            this.value[n] = l;
            return l2;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.key[n2];
                lArray[n2] = this.value[n2];
            }
            this.key = fArray;
            this.value = lArray;
        }
        this.key[this.size] = f;
        this.value[this.size] = l;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public long remove(float f) {
        int n = this.findKey(f);
        if (n == -1) {
            return this.defRetValue;
        }
        long l = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return l;
    }

    @Override
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2LongArrayMap this$0;
            {
                this.this$0 = float2LongArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2LongArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2LongArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2LongArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2LongArrayMap.access$100(this.this$0), n + 1, Float2LongArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2LongArrayMap.access$200(this.this$0), n + 1, Float2LongArrayMap.access$200(this.this$0), n, n2);
                Float2LongArrayMap.access$010(this.this$0);
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
                        return this.pos < Float2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2LongArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2LongArrayMap.access$100(this.this$1.this$0), this.pos, Float2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2LongArrayMap.access$200(this.this$1.this$0), this.pos, Float2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2LongArrayMap.access$000(this.this$0);
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
    public LongCollection values() {
        return new AbstractLongCollection(this){
            final Float2LongArrayMap this$0;
            {
                this.this$0 = float2LongArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return this.this$0.containsValue(l);
            }

            @Override
            public LongIterator iterator() {
                return new LongIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Float2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2LongArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2LongArrayMap.access$100(this.this$1.this$0), this.pos, Float2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2LongArrayMap.access$200(this.this$1.this$0), this.pos, Float2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2LongArrayMap.access$000(this.this$0);
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

    public Float2LongArrayMap clone() {
        Float2LongArrayMap float2LongArrayMap;
        try {
            float2LongArrayMap = (Float2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2LongArrayMap.key = (float[])this.key.clone();
        float2LongArrayMap.value = (long[])this.value.clone();
        return float2LongArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readLong();
        }
    }

    public ObjectSet float2LongEntrySet() {
        return this.float2LongEntrySet();
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

    static int access$000(Float2LongArrayMap float2LongArrayMap) {
        return float2LongArrayMap.size;
    }

    static float[] access$100(Float2LongArrayMap float2LongArrayMap) {
        return float2LongArrayMap.key;
    }

    static long[] access$200(Float2LongArrayMap float2LongArrayMap) {
        return float2LongArrayMap.value;
    }

    static int access$010(Float2LongArrayMap float2LongArrayMap) {
        return float2LongArrayMap.size--;
    }

    static int access$300(Float2LongArrayMap float2LongArrayMap, float f) {
        return float2LongArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2LongMap.Entry>
    implements Float2LongMap.FastEntrySet {
        final Float2LongArrayMap this$0;

        private EntrySet(Float2LongArrayMap float2LongArrayMap) {
            this.this$0 = float2LongArrayMap;
        }

        @Override
        public ObjectIterator<Float2LongMap.Entry> iterator() {
            return new ObjectIterator<Float2LongMap.Entry>(this){
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
                    return this.next < Float2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2LongMap.BasicEntry(Float2LongArrayMap.access$100(this.this$1.this$0)[this.curr], Float2LongArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2LongMap.Entry> fastIterator() {
            return new ObjectIterator<Float2LongMap.Entry>(this){
                int next;
                int curr;
                final AbstractFloat2LongMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2LongMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2LongArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2LongArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2LongArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && this.this$0.get(f) == ((Long)entry.getValue()).longValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            long l = (Long)entry.getValue();
            int n = Float2LongArrayMap.access$300(this.this$0, f);
            if (n == -1 || l != Float2LongArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Float2LongArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Float2LongArrayMap.access$100(this.this$0), n + 1, Float2LongArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Float2LongArrayMap.access$200(this.this$0), n + 1, Float2LongArrayMap.access$200(this.this$0), n, n2);
            Float2LongArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2LongArrayMap float2LongArrayMap, 1 var2_2) {
            this(float2LongArrayMap);
        }
    }
}

