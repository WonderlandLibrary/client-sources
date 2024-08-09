/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2DoubleMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
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
public class Object2DoubleArrayMap<K>
extends AbstractObject2DoubleMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient double[] value;
    private int size;

    public Object2DoubleArrayMap(Object[] objectArray, double[] dArray) {
        this.key = objectArray;
        this.value = dArray;
        this.size = objectArray.length;
        if (objectArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + dArray.length + ")");
        }
    }

    public Object2DoubleArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Object2DoubleArrayMap(int n) {
        this.key = new Object[n];
        this.value = new double[n];
    }

    public Object2DoubleArrayMap(Object2DoubleMap<K> object2DoubleMap) {
        this(object2DoubleMap.size());
        this.putAll(object2DoubleMap);
    }

    public Object2DoubleArrayMap(Map<? extends K, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Object2DoubleArrayMap(Object[] objectArray, double[] dArray, int n) {
        this.key = objectArray;
        this.value = dArray;
        this.size = n;
        if (objectArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + dArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Object2DoubleMap.FastEntrySet<K> object2DoubleEntrySet() {
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
    public double getDouble(Object object) {
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
    public boolean containsValue(double d) {
        int n = this.size;
        while (n-- != 0) {
            if (Double.doubleToLongBits(this.value[n]) != Double.doubleToLongBits(d)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public double put(K k, double d) {
        int n = this.findKey(k);
        if (n != -1) {
            double d2 = this.value[n];
            this.value[n] = d;
            return d2;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            double[] dArray = new double[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.key[n2];
                dArray[n2] = this.value[n2];
            }
            this.key = objectArray;
            this.value = dArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = d;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public double removeDouble(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        double d = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.key[this.size] = null;
        return d;
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(this){
            final Object2DoubleArrayMap this$0;
            {
                this.this$0 = object2DoubleArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Object2DoubleArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Object2DoubleArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Object2DoubleArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Object2DoubleArrayMap.access$100(this.this$0), n + 1, Object2DoubleArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Object2DoubleArrayMap.access$200(this.this$0), n + 1, Object2DoubleArrayMap.access$200(this.this$0), n, n2);
                Object2DoubleArrayMap.access$010(this.this$0);
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
                        return this.pos < Object2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Object2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Object2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Object2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Object2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Object2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Object2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Object2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Object2DoubleArrayMap.access$000(this.this$0);
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
    public DoubleCollection values() {
        return new AbstractDoubleCollection(this){
            final Object2DoubleArrayMap this$0;
            {
                this.this$0 = object2DoubleArrayMap;
            }

            @Override
            public boolean contains(double d) {
                return this.this$0.containsValue(d);
            }

            @Override
            public DoubleIterator iterator() {
                return new DoubleIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Object2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Object2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Object2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Object2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Object2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Object2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Object2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Object2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Object2DoubleArrayMap.access$000(this.this$0);
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

    public Object2DoubleArrayMap<K> clone() {
        Object2DoubleArrayMap object2DoubleArrayMap;
        try {
            object2DoubleArrayMap = (Object2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2DoubleArrayMap.key = (Object[])this.key.clone();
        object2DoubleArrayMap.value = (double[])this.value.clone();
        return object2DoubleArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeDouble(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new double[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readDouble();
        }
    }

    @Override
    public ObjectSet object2DoubleEntrySet() {
        return this.object2DoubleEntrySet();
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

    static int access$000(Object2DoubleArrayMap object2DoubleArrayMap) {
        return object2DoubleArrayMap.size;
    }

    static Object[] access$100(Object2DoubleArrayMap object2DoubleArrayMap) {
        return object2DoubleArrayMap.key;
    }

    static double[] access$200(Object2DoubleArrayMap object2DoubleArrayMap) {
        return object2DoubleArrayMap.value;
    }

    static int access$010(Object2DoubleArrayMap object2DoubleArrayMap) {
        return object2DoubleArrayMap.size--;
    }

    static int access$300(Object2DoubleArrayMap object2DoubleArrayMap, Object object) {
        return object2DoubleArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Object2DoubleMap.Entry<K>>
    implements Object2DoubleMap.FastEntrySet<K> {
        final Object2DoubleArrayMap this$0;

        private EntrySet(Object2DoubleArrayMap object2DoubleArrayMap) {
            this.this$0 = object2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Object2DoubleMap.Entry<K>> iterator() {
            return new ObjectIterator<Object2DoubleMap.Entry<K>>(this){
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
                    return this.next < Object2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Object2DoubleMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractObject2DoubleMap.BasicEntry<Object>(Object2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Object2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Object2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2DoubleArrayMap.access$100((Object2DoubleArrayMap)this.this$1.this$0)[Object2DoubleArrayMap.access$000((Object2DoubleArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Object2DoubleMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Object2DoubleMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractObject2DoubleMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractObject2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Object2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Object2DoubleMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Object2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Object2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Object2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2DoubleArrayMap.access$100((Object2DoubleArrayMap)this.this$1.this$0)[Object2DoubleArrayMap.access$000((Object2DoubleArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Object2DoubleArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && Double.doubleToLongBits(this.this$0.getDouble(k)) == Double.doubleToLongBits((Double)entry.getValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Double)) {
                return true;
            }
            Object k = entry.getKey();
            double d = (Double)entry.getValue();
            int n = Object2DoubleArrayMap.access$300(this.this$0, k);
            if (n == -1 || Double.doubleToLongBits(d) != Double.doubleToLongBits(Object2DoubleArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Object2DoubleArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Object2DoubleArrayMap.access$100(this.this$0), n + 1, Object2DoubleArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Object2DoubleArrayMap.access$200(this.this$0), n + 1, Object2DoubleArrayMap.access$200(this.this$0), n, n2);
            Object2DoubleArrayMap.access$010(this.this$0);
            Object2DoubleArrayMap.access$100((Object2DoubleArrayMap)this.this$0)[Object2DoubleArrayMap.access$000((Object2DoubleArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Object2DoubleArrayMap object2DoubleArrayMap, 1 var2_2) {
            this(object2DoubleArrayMap);
        }
    }
}

