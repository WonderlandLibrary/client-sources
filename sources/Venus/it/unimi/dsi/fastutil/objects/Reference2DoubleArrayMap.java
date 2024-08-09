/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2DoubleMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
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
public class Reference2DoubleArrayMap<K>
extends AbstractReference2DoubleMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient double[] value;
    private int size;

    public Reference2DoubleArrayMap(Object[] objectArray, double[] dArray) {
        this.key = objectArray;
        this.value = dArray;
        this.size = objectArray.length;
        if (objectArray.length != dArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + dArray.length + ")");
        }
    }

    public Reference2DoubleArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = DoubleArrays.EMPTY_ARRAY;
    }

    public Reference2DoubleArrayMap(int n) {
        this.key = new Object[n];
        this.value = new double[n];
    }

    public Reference2DoubleArrayMap(Reference2DoubleMap<K> reference2DoubleMap) {
        this(reference2DoubleMap.size());
        this.putAll(reference2DoubleMap);
    }

    public Reference2DoubleArrayMap(Map<? extends K, ? extends Double> map) {
        this(map.size());
        this.putAll(map);
    }

    public Reference2DoubleArrayMap(Object[] objectArray, double[] dArray, int n) {
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

    public Reference2DoubleMap.FastEntrySet<K> reference2DoubleEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (objectArray[n] != object) continue;
            return n;
        }
        return 1;
    }

    @Override
    public double getDouble(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (objectArray[n] != object) continue;
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
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final Reference2DoubleArrayMap this$0;
            {
                this.this$0 = reference2DoubleArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Reference2DoubleArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Reference2DoubleArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Reference2DoubleArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Reference2DoubleArrayMap.access$100(this.this$0), n + 1, Reference2DoubleArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Reference2DoubleArrayMap.access$200(this.this$0), n + 1, Reference2DoubleArrayMap.access$200(this.this$0), n, n2);
                Reference2DoubleArrayMap.access$010(this.this$0);
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
                        return this.pos < Reference2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2DoubleArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Reference2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Reference2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2DoubleArrayMap.access$000(this.this$0);
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
            final Reference2DoubleArrayMap this$0;
            {
                this.this$0 = reference2DoubleArrayMap;
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
                        return this.pos < Reference2DoubleArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2DoubleArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2DoubleArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2DoubleArrayMap.access$100(this.this$1.this$0), this.pos, Reference2DoubleArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2DoubleArrayMap.access$200(this.this$1.this$0), this.pos, Reference2DoubleArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2DoubleArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2DoubleArrayMap.access$000(this.this$0);
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

    public Reference2DoubleArrayMap<K> clone() {
        Reference2DoubleArrayMap reference2DoubleArrayMap;
        try {
            reference2DoubleArrayMap = (Reference2DoubleArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2DoubleArrayMap.key = (Object[])this.key.clone();
        reference2DoubleArrayMap.value = (double[])this.value.clone();
        return reference2DoubleArrayMap;
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
    public ObjectSet reference2DoubleEntrySet() {
        return this.reference2DoubleEntrySet();
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

    static int access$000(Reference2DoubleArrayMap reference2DoubleArrayMap) {
        return reference2DoubleArrayMap.size;
    }

    static Object[] access$100(Reference2DoubleArrayMap reference2DoubleArrayMap) {
        return reference2DoubleArrayMap.key;
    }

    static double[] access$200(Reference2DoubleArrayMap reference2DoubleArrayMap) {
        return reference2DoubleArrayMap.value;
    }

    static int access$010(Reference2DoubleArrayMap reference2DoubleArrayMap) {
        return reference2DoubleArrayMap.size--;
    }

    static int access$300(Reference2DoubleArrayMap reference2DoubleArrayMap, Object object) {
        return reference2DoubleArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2DoubleMap.Entry<K>>
    implements Reference2DoubleMap.FastEntrySet<K> {
        final Reference2DoubleArrayMap this$0;

        private EntrySet(Reference2DoubleArrayMap reference2DoubleArrayMap) {
            this.this$0 = reference2DoubleArrayMap;
        }

        @Override
        public ObjectIterator<Reference2DoubleMap.Entry<K>> iterator() {
            return new ObjectIterator<Reference2DoubleMap.Entry<K>>(this){
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
                    return this.next < Reference2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2DoubleMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2DoubleMap.BasicEntry<Object>(Reference2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr], Reference2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2DoubleArrayMap.access$100((Reference2DoubleArrayMap)this.this$1.this$0)[Reference2DoubleArrayMap.access$000((Reference2DoubleArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Reference2DoubleMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractReference2DoubleMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractReference2DoubleMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Reference2DoubleArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2DoubleMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2DoubleArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Reference2DoubleArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2DoubleArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2DoubleArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2DoubleArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2DoubleArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2DoubleArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2DoubleArrayMap.access$100((Reference2DoubleArrayMap)this.this$1.this$0)[Reference2DoubleArrayMap.access$000((Reference2DoubleArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Reference2DoubleArrayMap.access$000(this.this$0);
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
            int n = Reference2DoubleArrayMap.access$300(this.this$0, k);
            if (n == -1 || Double.doubleToLongBits(d) != Double.doubleToLongBits(Reference2DoubleArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Reference2DoubleArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Reference2DoubleArrayMap.access$100(this.this$0), n + 1, Reference2DoubleArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Reference2DoubleArrayMap.access$200(this.this$0), n + 1, Reference2DoubleArrayMap.access$200(this.this$0), n, n2);
            Reference2DoubleArrayMap.access$010(this.this$0);
            Reference2DoubleArrayMap.access$100((Reference2DoubleArrayMap)this.this$0)[Reference2DoubleArrayMap.access$000((Reference2DoubleArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Reference2DoubleArrayMap reference2DoubleArrayMap, 1 var2_2) {
            this(reference2DoubleArrayMap);
        }
    }
}

