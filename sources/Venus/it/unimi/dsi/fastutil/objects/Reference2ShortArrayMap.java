/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2ShortMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2ShortMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
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
public class Reference2ShortArrayMap<K>
extends AbstractReference2ShortMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient short[] value;
    private int size;

    public Reference2ShortArrayMap(Object[] objectArray, short[] sArray) {
        this.key = objectArray;
        this.value = sArray;
        this.size = objectArray.length;
        if (objectArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + sArray.length + ")");
        }
    }

    public Reference2ShortArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = ShortArrays.EMPTY_ARRAY;
    }

    public Reference2ShortArrayMap(int n) {
        this.key = new Object[n];
        this.value = new short[n];
    }

    public Reference2ShortArrayMap(Reference2ShortMap<K> reference2ShortMap) {
        this(reference2ShortMap.size());
        this.putAll(reference2ShortMap);
    }

    public Reference2ShortArrayMap(Map<? extends K, ? extends Short> map) {
        this(map.size());
        this.putAll(map);
    }

    public Reference2ShortArrayMap(Object[] objectArray, short[] sArray, int n) {
        this.key = objectArray;
        this.value = sArray;
        this.size = n;
        if (objectArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + sArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Reference2ShortMap.FastEntrySet<K> reference2ShortEntrySet() {
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
    public short getShort(Object object) {
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
    public short put(K k, short s) {
        int n = this.findKey(k);
        if (n != -1) {
            short s2 = this.value[n];
            this.value[n] = s;
            return s2;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.key[n2];
                sArray[n2] = this.value[n2];
            }
            this.key = objectArray;
            this.value = sArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = s;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public short removeShort(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        short s = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.key[this.size] = null;
        return s;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final Reference2ShortArrayMap this$0;
            {
                this.this$0 = reference2ShortArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Reference2ShortArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Reference2ShortArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Reference2ShortArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Reference2ShortArrayMap.access$100(this.this$0), n + 1, Reference2ShortArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Reference2ShortArrayMap.access$200(this.this$0), n + 1, Reference2ShortArrayMap.access$200(this.this$0), n, n2);
                Reference2ShortArrayMap.access$010(this.this$0);
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
                        return this.pos < Reference2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2ShortArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Reference2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Reference2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2ShortArrayMap.access$000(this.this$0);
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
            final Reference2ShortArrayMap this$0;
            {
                this.this$0 = reference2ShortArrayMap;
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
                        return this.pos < Reference2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2ShortArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Reference2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Reference2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2ShortArrayMap.access$000(this.this$0);
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

    public Reference2ShortArrayMap<K> clone() {
        Reference2ShortArrayMap reference2ShortArrayMap;
        try {
            reference2ShortArrayMap = (Reference2ShortArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2ShortArrayMap.key = (Object[])this.key.clone();
        reference2ShortArrayMap.value = (short[])this.value.clone();
        return reference2ShortArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeShort(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readShort();
        }
    }

    @Override
    public ObjectSet reference2ShortEntrySet() {
        return this.reference2ShortEntrySet();
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

    static int access$000(Reference2ShortArrayMap reference2ShortArrayMap) {
        return reference2ShortArrayMap.size;
    }

    static Object[] access$100(Reference2ShortArrayMap reference2ShortArrayMap) {
        return reference2ShortArrayMap.key;
    }

    static short[] access$200(Reference2ShortArrayMap reference2ShortArrayMap) {
        return reference2ShortArrayMap.value;
    }

    static int access$010(Reference2ShortArrayMap reference2ShortArrayMap) {
        return reference2ShortArrayMap.size--;
    }

    static int access$300(Reference2ShortArrayMap reference2ShortArrayMap, Object object) {
        return reference2ShortArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2ShortMap.Entry<K>>
    implements Reference2ShortMap.FastEntrySet<K> {
        final Reference2ShortArrayMap this$0;

        private EntrySet(Reference2ShortArrayMap reference2ShortArrayMap) {
            this.this$0 = reference2ShortArrayMap;
        }

        @Override
        public ObjectIterator<Reference2ShortMap.Entry<K>> iterator() {
            return new ObjectIterator<Reference2ShortMap.Entry<K>>(this){
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
                    return this.next < Reference2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2ShortMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2ShortMap.BasicEntry<Object>(Reference2ShortArrayMap.access$100(this.this$1.this$0)[this.curr], Reference2ShortArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2ShortArrayMap.access$100((Reference2ShortArrayMap)this.this$1.this$0)[Reference2ShortArrayMap.access$000((Reference2ShortArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Reference2ShortMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Reference2ShortMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractReference2ShortMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractReference2ShortMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Reference2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2ShortMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2ShortArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Reference2ShortArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2ShortArrayMap.access$100((Reference2ShortArrayMap)this.this$1.this$0)[Reference2ShortArrayMap.access$000((Reference2ShortArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Reference2ShortArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && this.this$0.getShort(k) == ((Short)entry.getValue()).shortValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            Object k = entry.getKey();
            short s = (Short)entry.getValue();
            int n = Reference2ShortArrayMap.access$300(this.this$0, k);
            if (n == -1 || s != Reference2ShortArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Reference2ShortArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Reference2ShortArrayMap.access$100(this.this$0), n + 1, Reference2ShortArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Reference2ShortArrayMap.access$200(this.this$0), n + 1, Reference2ShortArrayMap.access$200(this.this$0), n, n2);
            Reference2ShortArrayMap.access$010(this.this$0);
            Reference2ShortArrayMap.access$100((Reference2ShortArrayMap)this.this$0)[Reference2ShortArrayMap.access$000((Reference2ShortArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Reference2ShortArrayMap reference2ShortArrayMap, 1 var2_2) {
            this(reference2ShortArrayMap);
        }
    }
}

