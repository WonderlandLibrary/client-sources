/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
import java.util.Objects;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Float2ObjectArrayMap<V>
extends AbstractFloat2ObjectMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient Object[] value;
    private int size;

    public Float2ObjectArrayMap(float[] fArray, Object[] objectArray) {
        this.key = fArray;
        this.value = objectArray;
        this.size = fArray.length;
        if (fArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + objectArray.length + ")");
        }
    }

    public Float2ObjectArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Float2ObjectArrayMap(int n) {
        this.key = new float[n];
        this.value = new Object[n];
    }

    public Float2ObjectArrayMap(Float2ObjectMap<V> float2ObjectMap) {
        this(float2ObjectMap.size());
        this.putAll(float2ObjectMap);
    }

    public Float2ObjectArrayMap(Map<? extends Float, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2ObjectArrayMap(float[] fArray, Object[] objectArray, int n) {
        this.key = fArray;
        this.value = objectArray;
        this.size = n;
        if (fArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + objectArray.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2ObjectMap.FastEntrySet<V> float2ObjectEntrySet() {
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
    public V get(float f) {
        float[] fArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
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
    public boolean containsKey(float f) {
        return this.findKey(f) != -1;
    }

    @Override
    public boolean containsValue(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(this.value[n], object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public V put(float f, V v) {
        int n = this.findKey(f);
        if (n != -1) {
            Object object = this.value[n];
            this.value[n] = v;
            return (V)object;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.key[n2];
                objectArray[n2] = this.value[n2];
            }
            this.key = fArray;
            this.value = objectArray;
        }
        this.key[this.size] = f;
        this.value[this.size] = v;
        ++this.size;
        return (V)this.defRetValue;
    }

    @Override
    public V remove(float f) {
        int n = this.findKey(f);
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
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2ObjectArrayMap this$0;
            {
                this.this$0 = float2ObjectArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2ObjectArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2ObjectArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2ObjectArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2ObjectArrayMap.access$100(this.this$0), n + 1, Float2ObjectArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2ObjectArrayMap.access$200(this.this$0), n + 1, Float2ObjectArrayMap.access$200(this.this$0), n, n2);
                Float2ObjectArrayMap.access$010(this.this$0);
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
                        return this.pos < Float2ObjectArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Float2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Float2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2ObjectArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2ObjectArrayMap.access$000(this.this$0);
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
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(this){
            final Float2ObjectArrayMap this$0;
            {
                this.this$0 = float2ObjectArrayMap;
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
                        return this.pos < Float2ObjectArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2ObjectArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Float2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Float2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2ObjectArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2ObjectArrayMap.access$000(this.this$0);
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

    public Float2ObjectArrayMap<V> clone() {
        Float2ObjectArrayMap float2ObjectArrayMap;
        try {
            float2ObjectArrayMap = (Float2ObjectArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2ObjectArrayMap.key = (float[])this.key.clone();
        float2ObjectArrayMap.value = (Object[])this.value.clone();
        return float2ObjectArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readObject();
        }
    }

    @Override
    public ObjectSet float2ObjectEntrySet() {
        return this.float2ObjectEntrySet();
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

    static int access$000(Float2ObjectArrayMap float2ObjectArrayMap) {
        return float2ObjectArrayMap.size;
    }

    static float[] access$100(Float2ObjectArrayMap float2ObjectArrayMap) {
        return float2ObjectArrayMap.key;
    }

    static Object[] access$200(Float2ObjectArrayMap float2ObjectArrayMap) {
        return float2ObjectArrayMap.value;
    }

    static int access$010(Float2ObjectArrayMap float2ObjectArrayMap) {
        return float2ObjectArrayMap.size--;
    }

    static int access$300(Float2ObjectArrayMap float2ObjectArrayMap, float f) {
        return float2ObjectArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2ObjectMap.Entry<V>>
    implements Float2ObjectMap.FastEntrySet<V> {
        final Float2ObjectArrayMap this$0;

        private EntrySet(Float2ObjectArrayMap float2ObjectArrayMap) {
            this.this$0 = float2ObjectArrayMap;
        }

        @Override
        public ObjectIterator<Float2ObjectMap.Entry<V>> iterator() {
            return new ObjectIterator<Float2ObjectMap.Entry<V>>(this){
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
                    return this.next < Float2ObjectArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2ObjectMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2ObjectMap.BasicEntry<Object>(Float2ObjectArrayMap.access$100(this.this$1.this$0)[this.curr], Float2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Float2ObjectArrayMap.access$200((Float2ObjectArrayMap)this.this$1.this$0)[Float2ObjectArrayMap.access$000((Float2ObjectArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2ObjectMap.Entry<V>> fastIterator() {
            return new ObjectIterator<Float2ObjectMap.Entry<V>>(this){
                int next;
                int curr;
                final AbstractFloat2ObjectMap.BasicEntry<V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2ObjectMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2ObjectArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2ObjectMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2ObjectArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Float2ObjectArrayMap.access$200((Float2ObjectArrayMap)this.this$1.this$0)[Float2ObjectArrayMap.access$000((Float2ObjectArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2ObjectArrayMap.access$000(this.this$0);
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
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && Objects.equals(this.this$0.get(f), entry.getValue());
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
            float f = ((Float)entry.getKey()).floatValue();
            Object v = entry.getValue();
            int n = Float2ObjectArrayMap.access$300(this.this$0, f);
            if (n == -1 || !Objects.equals(v, Float2ObjectArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Float2ObjectArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Float2ObjectArrayMap.access$100(this.this$0), n + 1, Float2ObjectArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Float2ObjectArrayMap.access$200(this.this$0), n + 1, Float2ObjectArrayMap.access$200(this.this$0), n, n2);
            Float2ObjectArrayMap.access$010(this.this$0);
            Float2ObjectArrayMap.access$200((Float2ObjectArrayMap)this.this$0)[Float2ObjectArrayMap.access$000((Float2ObjectArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2ObjectArrayMap float2ObjectArrayMap, 1 var2_2) {
            this(float2ObjectArrayMap);
        }
    }
}

