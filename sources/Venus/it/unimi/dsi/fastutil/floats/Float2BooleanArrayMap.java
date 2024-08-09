/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap;
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
public class Float2BooleanArrayMap
extends AbstractFloat2BooleanMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient boolean[] value;
    private int size;

    public Float2BooleanArrayMap(float[] fArray, boolean[] blArray) {
        this.key = fArray;
        this.value = blArray;
        this.size = fArray.length;
        if (fArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + blArray.length + ")");
        }
    }

    public Float2BooleanArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Float2BooleanArrayMap(int n) {
        this.key = new float[n];
        this.value = new boolean[n];
    }

    public Float2BooleanArrayMap(Float2BooleanMap float2BooleanMap) {
        this(float2BooleanMap.size());
        this.putAll(float2BooleanMap);
    }

    public Float2BooleanArrayMap(Map<? extends Float, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2BooleanArrayMap(float[] fArray, boolean[] blArray, int n) {
        this.key = fArray;
        this.value = blArray;
        this.size = n;
        if (fArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + blArray.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2BooleanMap.FastEntrySet float2BooleanEntrySet() {
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
    public boolean get(float f) {
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
    public boolean put(float f, boolean bl) {
        int n = this.findKey(f);
        if (n != -1) {
            boolean bl2 = this.value[n];
            this.value[n] = bl;
            return bl2;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.key[n2];
                blArray[n2] = this.value[n2];
            }
            this.key = fArray;
            this.value = blArray;
        }
        this.key[this.size] = f;
        this.value[this.size] = bl;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public boolean remove(float f) {
        int n = this.findKey(f);
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
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2BooleanArrayMap this$0;
            {
                this.this$0 = float2BooleanArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2BooleanArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2BooleanArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2BooleanArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2BooleanArrayMap.access$100(this.this$0), n + 1, Float2BooleanArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2BooleanArrayMap.access$200(this.this$0), n + 1, Float2BooleanArrayMap.access$200(this.this$0), n, n2);
                Float2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Float2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Float2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Float2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2BooleanArrayMap.access$000(this.this$0);
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
            final Float2BooleanArrayMap this$0;
            {
                this.this$0 = float2BooleanArrayMap;
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
                        return this.pos < Float2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Float2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Float2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2BooleanArrayMap.access$000(this.this$0);
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

    public Float2BooleanArrayMap clone() {
        Float2BooleanArrayMap float2BooleanArrayMap;
        try {
            float2BooleanArrayMap = (Float2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2BooleanArrayMap.key = (float[])this.key.clone();
        float2BooleanArrayMap.value = (boolean[])this.value.clone();
        return float2BooleanArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeBoolean(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readBoolean();
        }
    }

    public ObjectSet float2BooleanEntrySet() {
        return this.float2BooleanEntrySet();
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

    static int access$000(Float2BooleanArrayMap float2BooleanArrayMap) {
        return float2BooleanArrayMap.size;
    }

    static float[] access$100(Float2BooleanArrayMap float2BooleanArrayMap) {
        return float2BooleanArrayMap.key;
    }

    static boolean[] access$200(Float2BooleanArrayMap float2BooleanArrayMap) {
        return float2BooleanArrayMap.value;
    }

    static int access$010(Float2BooleanArrayMap float2BooleanArrayMap) {
        return float2BooleanArrayMap.size--;
    }

    static int access$300(Float2BooleanArrayMap float2BooleanArrayMap, float f) {
        return float2BooleanArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2BooleanMap.Entry>
    implements Float2BooleanMap.FastEntrySet {
        final Float2BooleanArrayMap this$0;

        private EntrySet(Float2BooleanArrayMap float2BooleanArrayMap) {
            this.this$0 = float2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Float2BooleanMap.Entry> iterator() {
            return new ObjectIterator<Float2BooleanMap.Entry>(this){
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
                    return this.next < Float2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2BooleanMap.BasicEntry(Float2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Float2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2BooleanMap.Entry> fastIterator() {
            return new ObjectIterator<Float2BooleanMap.Entry>(this){
                int next;
                int curr;
                final AbstractFloat2BooleanMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2BooleanArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && this.this$0.get(f) == ((Boolean)entry.getValue()).booleanValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            boolean bl = (Boolean)entry.getValue();
            int n = Float2BooleanArrayMap.access$300(this.this$0, f);
            if (n == -1 || bl != Float2BooleanArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Float2BooleanArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Float2BooleanArrayMap.access$100(this.this$0), n + 1, Float2BooleanArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Float2BooleanArrayMap.access$200(this.this$0), n + 1, Float2BooleanArrayMap.access$200(this.this$0), n, n2);
            Float2BooleanArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2BooleanArrayMap float2BooleanArrayMap, 1 var2_2) {
            this(float2BooleanArrayMap);
        }
    }
}

