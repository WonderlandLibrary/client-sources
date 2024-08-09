/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2IntMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
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
public class Float2IntArrayMap
extends AbstractFloat2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient int[] value;
    private int size;

    public Float2IntArrayMap(float[] fArray, int[] nArray) {
        this.key = fArray;
        this.value = nArray;
        this.size = fArray.length;
        if (fArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + nArray.length + ")");
        }
    }

    public Float2IntArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Float2IntArrayMap(int n) {
        this.key = new float[n];
        this.value = new int[n];
    }

    public Float2IntArrayMap(Float2IntMap float2IntMap) {
        this(float2IntMap.size());
        this.putAll(float2IntMap);
    }

    public Float2IntArrayMap(Map<? extends Float, ? extends Integer> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2IntArrayMap(float[] fArray, int[] nArray, int n) {
        this.key = fArray;
        this.value = nArray;
        this.size = n;
        if (fArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + nArray.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2IntMap.FastEntrySet float2IntEntrySet() {
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
    public int get(float f) {
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
    public int put(float f, int n) {
        int n2 = this.findKey(f);
        if (n2 != -1) {
            int n3 = this.value[n2];
            this.value[n2] = n;
            return n3;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n4 = this.size;
            while (n4-- != 0) {
                fArray[n4] = this.key[n4];
                nArray[n4] = this.value[n4];
            }
            this.key = fArray;
            this.value = nArray;
        }
        this.key[this.size] = f;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int remove(float f) {
        int n = this.findKey(f);
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
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2IntArrayMap this$0;
            {
                this.this$0 = float2IntArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2IntArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2IntArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2IntArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2IntArrayMap.access$100(this.this$0), n + 1, Float2IntArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2IntArrayMap.access$200(this.this$0), n + 1, Float2IntArrayMap.access$200(this.this$0), n, n2);
                Float2IntArrayMap.access$010(this.this$0);
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
                        return this.pos < Float2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2IntArrayMap.access$100(this.this$1.this$0), this.pos, Float2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2IntArrayMap.access$200(this.this$1.this$0), this.pos, Float2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2IntArrayMap.access$000(this.this$0);
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
            final Float2IntArrayMap this$0;
            {
                this.this$0 = float2IntArrayMap;
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
                        return this.pos < Float2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2IntArrayMap.access$100(this.this$1.this$0), this.pos, Float2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2IntArrayMap.access$200(this.this$1.this$0), this.pos, Float2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2IntArrayMap.access$000(this.this$0);
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

    public Float2IntArrayMap clone() {
        Float2IntArrayMap float2IntArrayMap;
        try {
            float2IntArrayMap = (Float2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2IntArrayMap.key = (float[])this.key.clone();
        float2IntArrayMap.value = (int[])this.value.clone();
        return float2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readInt();
        }
    }

    public ObjectSet float2IntEntrySet() {
        return this.float2IntEntrySet();
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

    static int access$000(Float2IntArrayMap float2IntArrayMap) {
        return float2IntArrayMap.size;
    }

    static float[] access$100(Float2IntArrayMap float2IntArrayMap) {
        return float2IntArrayMap.key;
    }

    static int[] access$200(Float2IntArrayMap float2IntArrayMap) {
        return float2IntArrayMap.value;
    }

    static int access$010(Float2IntArrayMap float2IntArrayMap) {
        return float2IntArrayMap.size--;
    }

    static int access$300(Float2IntArrayMap float2IntArrayMap, float f) {
        return float2IntArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2IntMap.Entry>
    implements Float2IntMap.FastEntrySet {
        final Float2IntArrayMap this$0;

        private EntrySet(Float2IntArrayMap float2IntArrayMap) {
            this.this$0 = float2IntArrayMap;
        }

        @Override
        public ObjectIterator<Float2IntMap.Entry> iterator() {
            return new ObjectIterator<Float2IntMap.Entry>(this){
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
                    return this.next < Float2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2IntMap.BasicEntry(Float2IntArrayMap.access$100(this.this$1.this$0)[this.curr], Float2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Float2IntMap.Entry>(this){
                int next;
                int curr;
                final AbstractFloat2IntMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2IntArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2IntArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && this.this$0.get(f) == ((Integer)entry.getValue()).intValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            int n = (Integer)entry.getValue();
            int n2 = Float2IntArrayMap.access$300(this.this$0, f);
            if (n2 == -1 || n != Float2IntArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Float2IntArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Float2IntArrayMap.access$100(this.this$0), n2 + 1, Float2IntArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Float2IntArrayMap.access$200(this.this$0), n2 + 1, Float2IntArrayMap.access$200(this.this$0), n2, n3);
            Float2IntArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2IntArrayMap float2IntArrayMap, 1 var2_2) {
            this(float2IntArrayMap);
        }
    }
}

