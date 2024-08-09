/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2ByteMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2ByteMap;
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
public class Reference2ByteArrayMap<K>
extends AbstractReference2ByteMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient byte[] value;
    private int size;

    public Reference2ByteArrayMap(Object[] objectArray, byte[] byArray) {
        this.key = objectArray;
        this.value = byArray;
        this.size = objectArray.length;
        if (objectArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + byArray.length + ")");
        }
    }

    public Reference2ByteArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = ByteArrays.EMPTY_ARRAY;
    }

    public Reference2ByteArrayMap(int n) {
        this.key = new Object[n];
        this.value = new byte[n];
    }

    public Reference2ByteArrayMap(Reference2ByteMap<K> reference2ByteMap) {
        this(reference2ByteMap.size());
        this.putAll(reference2ByteMap);
    }

    public Reference2ByteArrayMap(Map<? extends K, ? extends Byte> map) {
        this(map.size());
        this.putAll(map);
    }

    public Reference2ByteArrayMap(Object[] objectArray, byte[] byArray, int n) {
        this.key = objectArray;
        this.value = byArray;
        this.size = n;
        if (objectArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + byArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Reference2ByteMap.FastEntrySet<K> reference2ByteEntrySet() {
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
    public byte getByte(Object object) {
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
    public boolean containsValue(byte by) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != by) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public byte put(K k, byte by) {
        int n = this.findKey(k);
        if (n != -1) {
            byte by2 = this.value[n];
            this.value[n] = by;
            return by2;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.key[n2];
                byArray[n2] = this.value[n2];
            }
            this.key = objectArray;
            this.value = byArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = by;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public byte removeByte(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        byte by = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.key[this.size] = null;
        return by;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final Reference2ByteArrayMap this$0;
            {
                this.this$0 = reference2ByteArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Reference2ByteArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Reference2ByteArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Reference2ByteArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Reference2ByteArrayMap.access$100(this.this$0), n + 1, Reference2ByteArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Reference2ByteArrayMap.access$200(this.this$0), n + 1, Reference2ByteArrayMap.access$200(this.this$0), n, n2);
                Reference2ByteArrayMap.access$010(this.this$0);
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
                        return this.pos < Reference2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2ByteArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Reference2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Reference2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2ByteArrayMap.access$000(this.this$0);
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
    public ByteCollection values() {
        return new AbstractByteCollection(this){
            final Reference2ByteArrayMap this$0;
            {
                this.this$0 = reference2ByteArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return this.this$0.containsValue(by);
            }

            @Override
            public ByteIterator iterator() {
                return new ByteIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Reference2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2ByteArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Reference2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Reference2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2ByteArrayMap.access$000(this.this$0);
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

    public Reference2ByteArrayMap<K> clone() {
        Reference2ByteArrayMap reference2ByteArrayMap;
        try {
            reference2ByteArrayMap = (Reference2ByteArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2ByteArrayMap.key = (Object[])this.key.clone();
        reference2ByteArrayMap.value = (byte[])this.value.clone();
        return reference2ByteArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeByte(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new byte[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readByte();
        }
    }

    @Override
    public ObjectSet reference2ByteEntrySet() {
        return this.reference2ByteEntrySet();
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

    static int access$000(Reference2ByteArrayMap reference2ByteArrayMap) {
        return reference2ByteArrayMap.size;
    }

    static Object[] access$100(Reference2ByteArrayMap reference2ByteArrayMap) {
        return reference2ByteArrayMap.key;
    }

    static byte[] access$200(Reference2ByteArrayMap reference2ByteArrayMap) {
        return reference2ByteArrayMap.value;
    }

    static int access$010(Reference2ByteArrayMap reference2ByteArrayMap) {
        return reference2ByteArrayMap.size--;
    }

    static int access$300(Reference2ByteArrayMap reference2ByteArrayMap, Object object) {
        return reference2ByteArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2ByteMap.Entry<K>>
    implements Reference2ByteMap.FastEntrySet<K> {
        final Reference2ByteArrayMap this$0;

        private EntrySet(Reference2ByteArrayMap reference2ByteArrayMap) {
            this.this$0 = reference2ByteArrayMap;
        }

        @Override
        public ObjectIterator<Reference2ByteMap.Entry<K>> iterator() {
            return new ObjectIterator<Reference2ByteMap.Entry<K>>(this){
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
                    return this.next < Reference2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2ByteMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2ByteMap.BasicEntry<Object>(Reference2ByteArrayMap.access$100(this.this$1.this$0)[this.curr], Reference2ByteArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2ByteArrayMap.access$100((Reference2ByteArrayMap)this.this$1.this$0)[Reference2ByteArrayMap.access$000((Reference2ByteArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Reference2ByteMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Reference2ByteMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractReference2ByteMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractReference2ByteMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Reference2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2ByteMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2ByteArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Reference2ByteArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2ByteArrayMap.access$100((Reference2ByteArrayMap)this.this$1.this$0)[Reference2ByteArrayMap.access$000((Reference2ByteArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Reference2ByteArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && this.this$0.getByte(k) == ((Byte)entry.getValue()).byteValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            Object k = entry.getKey();
            byte by = (Byte)entry.getValue();
            int n = Reference2ByteArrayMap.access$300(this.this$0, k);
            if (n == -1 || by != Reference2ByteArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Reference2ByteArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Reference2ByteArrayMap.access$100(this.this$0), n + 1, Reference2ByteArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Reference2ByteArrayMap.access$200(this.this$0), n + 1, Reference2ByteArrayMap.access$200(this.this$0), n, n2);
            Reference2ByteArrayMap.access$010(this.this$0);
            Reference2ByteArrayMap.access$100((Reference2ByteArrayMap)this.this$0)[Reference2ByteArrayMap.access$000((Reference2ByteArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Reference2ByteArrayMap reference2ByteArrayMap, 1 var2_2) {
            this(reference2ByteArrayMap);
        }
    }
}

