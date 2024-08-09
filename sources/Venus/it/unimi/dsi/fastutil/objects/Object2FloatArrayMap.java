/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2FloatMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
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
public class Object2FloatArrayMap<K>
extends AbstractObject2FloatMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient float[] value;
    private int size;

    public Object2FloatArrayMap(Object[] objectArray, float[] fArray) {
        this.key = objectArray;
        this.value = fArray;
        this.size = objectArray.length;
        if (objectArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + fArray.length + ")");
        }
    }

    public Object2FloatArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Object2FloatArrayMap(int n) {
        this.key = new Object[n];
        this.value = new float[n];
    }

    public Object2FloatArrayMap(Object2FloatMap<K> object2FloatMap) {
        this(object2FloatMap.size());
        this.putAll(object2FloatMap);
    }

    public Object2FloatArrayMap(Map<? extends K, ? extends Float> map) {
        this(map.size());
        this.putAll(map);
    }

    public Object2FloatArrayMap(Object[] objectArray, float[] fArray, int n) {
        this.key = objectArray;
        this.value = fArray;
        this.size = n;
        if (objectArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + fArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Object2FloatMap.FastEntrySet<K> object2FloatEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(objectArray[n], object)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public float getFloat(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(objectArray[n], object)) continue;
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
        int n = this.size;
        while (n-- != 0) {
            this.key[n] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.findKey(object) != -1;
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
    public float put(K k, float f) {
        int n = this.findKey(k);
        if (n != -1) {
            float f2 = this.value[n];
            this.value[n] = f;
            return f2;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.key[n2];
                fArray[n2] = this.value[n2];
            }
            this.key = objectArray;
            this.value = fArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = f;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public float removeFloat(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        float f = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.key[this.size] = null;
        return f;
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(this){
            final Object2FloatArrayMap this$0;
            {
                this.this$0 = object2FloatArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Object2FloatArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Object2FloatArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Object2FloatArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Object2FloatArrayMap.access$100(this.this$0), n + 1, Object2FloatArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Object2FloatArrayMap.access$200(this.this$0), n + 1, Object2FloatArrayMap.access$200(this.this$0), n, n2);
                Object2FloatArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Object2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Object2FloatArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Object2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Object2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Object2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Object2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Object2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Object2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Object2FloatArrayMap.access$000(this.this$0);
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
            final Object2FloatArrayMap this$0;
            {
                this.this$0 = object2FloatArrayMap;
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
                        return this.pos < Object2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Object2FloatArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Object2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Object2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Object2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Object2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Object2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Object2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Object2FloatArrayMap.access$000(this.this$0);
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

    public Object2FloatArrayMap<K> clone() {
        Object2FloatArrayMap object2FloatArrayMap;
        try {
            object2FloatArrayMap = (Object2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2FloatArrayMap.key = (Object[])this.key.clone();
        object2FloatArrayMap.value = (float[])this.value.clone();
        return object2FloatArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readFloat();
        }
    }

    @Override
    public ObjectSet object2FloatEntrySet() {
        return this.object2FloatEntrySet();
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

    static int access$000(Object2FloatArrayMap object2FloatArrayMap) {
        return object2FloatArrayMap.size;
    }

    static Object[] access$100(Object2FloatArrayMap object2FloatArrayMap) {
        return object2FloatArrayMap.key;
    }

    static float[] access$200(Object2FloatArrayMap object2FloatArrayMap) {
        return object2FloatArrayMap.value;
    }

    static int access$010(Object2FloatArrayMap object2FloatArrayMap) {
        return object2FloatArrayMap.size--;
    }

    static int access$300(Object2FloatArrayMap object2FloatArrayMap, Object object) {
        return object2FloatArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Object2FloatMap.Entry<K>>
    implements Object2FloatMap.FastEntrySet<K> {
        final Object2FloatArrayMap this$0;

        private EntrySet(Object2FloatArrayMap object2FloatArrayMap) {
            this.this$0 = object2FloatArrayMap;
        }

        @Override
        public ObjectIterator<Object2FloatMap.Entry<K>> iterator() {
            return new ObjectIterator<Object2FloatMap.Entry<K>>(this){
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
                    return this.next < Object2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Object2FloatMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractObject2FloatMap.BasicEntry<Object>(Object2FloatArrayMap.access$100(this.this$1.this$0)[this.curr], Object2FloatArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Object2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2FloatArrayMap.access$100((Object2FloatArrayMap)this.this$1.this$0)[Object2FloatArrayMap.access$000((Object2FloatArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Object2FloatMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Object2FloatMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractObject2FloatMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractObject2FloatMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Object2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Object2FloatMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Object2FloatArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Object2FloatArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Object2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2FloatArrayMap.access$100((Object2FloatArrayMap)this.this$1.this$0)[Object2FloatArrayMap.access$000((Object2FloatArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Object2FloatArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && Float.floatToIntBits(this.this$0.getFloat(k)) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            Object k = entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            int n = Object2FloatArrayMap.access$300(this.this$0, k);
            if (n == -1 || Float.floatToIntBits(f) != Float.floatToIntBits(Object2FloatArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Object2FloatArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Object2FloatArrayMap.access$100(this.this$0), n + 1, Object2FloatArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Object2FloatArrayMap.access$200(this.this$0), n + 1, Object2FloatArrayMap.access$200(this.this$0), n, n2);
            Object2FloatArrayMap.access$010(this.this$0);
            Object2FloatArrayMap.access$100((Object2FloatArrayMap)this.this$0)[Object2FloatArrayMap.access$000((Object2FloatArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Object2FloatArrayMap object2FloatArrayMap, 1 var2_2) {
            this(object2FloatArrayMap);
        }
    }
}

