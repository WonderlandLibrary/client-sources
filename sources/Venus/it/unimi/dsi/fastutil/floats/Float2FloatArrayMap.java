/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
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
public class Float2FloatArrayMap
extends AbstractFloat2FloatMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient float[] value;
    private int size;

    public Float2FloatArrayMap(float[] fArray, float[] fArray2) {
        this.key = fArray;
        this.value = fArray2;
        this.size = fArray.length;
        if (fArray.length != fArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + fArray2.length + ")");
        }
    }

    public Float2FloatArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Float2FloatArrayMap(int n) {
        this.key = new float[n];
        this.value = new float[n];
    }

    public Float2FloatArrayMap(Float2FloatMap float2FloatMap) {
        this(float2FloatMap.size());
        this.putAll(float2FloatMap);
    }

    public Float2FloatArrayMap(Map<? extends Float, ? extends Float> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2FloatArrayMap(float[] fArray, float[] fArray2, int n) {
        this.key = fArray;
        this.value = fArray2;
        this.size = n;
        if (fArray.length != fArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + fArray2.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2FloatMap.FastEntrySet float2FloatEntrySet() {
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
    public float get(float f) {
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
    public float put(float f, float f2) {
        int n = this.findKey(f);
        if (n != -1) {
            float f3 = this.value[n];
            this.value[n] = f2;
            return f3;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            float[] fArray2 = new float[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.key[n2];
                fArray2[n2] = this.value[n2];
            }
            this.key = fArray;
            this.value = fArray2;
        }
        this.key[this.size] = f;
        this.value[this.size] = f2;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public float remove(float f) {
        int n = this.findKey(f);
        if (n == -1) {
            return this.defRetValue;
        }
        float f2 = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return f2;
    }

    @Override
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2FloatArrayMap this$0;
            {
                this.this$0 = float2FloatArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2FloatArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2FloatArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2FloatArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2FloatArrayMap.access$100(this.this$0), n + 1, Float2FloatArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2FloatArrayMap.access$200(this.this$0), n + 1, Float2FloatArrayMap.access$200(this.this$0), n, n2);
                Float2FloatArrayMap.access$010(this.this$0);
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
                        return this.pos < Float2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2FloatArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Float2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Float2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2FloatArrayMap.access$000(this.this$0);
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
            final Float2FloatArrayMap this$0;
            {
                this.this$0 = float2FloatArrayMap;
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
                        return this.pos < Float2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2FloatArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Float2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Float2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2FloatArrayMap.access$000(this.this$0);
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

    public Float2FloatArrayMap clone() {
        Float2FloatArrayMap float2FloatArrayMap;
        try {
            float2FloatArrayMap = (Float2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2FloatArrayMap.key = (float[])this.key.clone();
        float2FloatArrayMap.value = (float[])this.value.clone();
        return float2FloatArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readFloat();
        }
    }

    public ObjectSet float2FloatEntrySet() {
        return this.float2FloatEntrySet();
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

    static int access$000(Float2FloatArrayMap float2FloatArrayMap) {
        return float2FloatArrayMap.size;
    }

    static float[] access$100(Float2FloatArrayMap float2FloatArrayMap) {
        return float2FloatArrayMap.key;
    }

    static float[] access$200(Float2FloatArrayMap float2FloatArrayMap) {
        return float2FloatArrayMap.value;
    }

    static int access$010(Float2FloatArrayMap float2FloatArrayMap) {
        return float2FloatArrayMap.size--;
    }

    static int access$300(Float2FloatArrayMap float2FloatArrayMap, float f) {
        return float2FloatArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2FloatMap.Entry>
    implements Float2FloatMap.FastEntrySet {
        final Float2FloatArrayMap this$0;

        private EntrySet(Float2FloatArrayMap float2FloatArrayMap) {
            this.this$0 = float2FloatArrayMap;
        }

        @Override
        public ObjectIterator<Float2FloatMap.Entry> iterator() {
            return new ObjectIterator<Float2FloatMap.Entry>(this){
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
                    return this.next < Float2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2FloatMap.BasicEntry(Float2FloatArrayMap.access$100(this.this$1.this$0)[this.curr], Float2FloatArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2FloatMap.Entry> fastIterator() {
            return new ObjectIterator<Float2FloatMap.Entry>(this){
                int next;
                int curr;
                final AbstractFloat2FloatMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2FloatMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2FloatArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2FloatArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2FloatArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && Float.floatToIntBits(this.this$0.get(f)) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            float f2 = ((Float)entry.getValue()).floatValue();
            int n = Float2FloatArrayMap.access$300(this.this$0, f);
            if (n == -1 || Float.floatToIntBits(f2) != Float.floatToIntBits(Float2FloatArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Float2FloatArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Float2FloatArrayMap.access$100(this.this$0), n + 1, Float2FloatArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Float2FloatArrayMap.access$200(this.this$0), n + 1, Float2FloatArrayMap.access$200(this.this$0), n, n2);
            Float2FloatArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2FloatArrayMap float2FloatArrayMap, 1 var2_2) {
            this(float2FloatArrayMap);
        }
    }
}

