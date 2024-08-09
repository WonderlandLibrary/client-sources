/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2IntMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
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
public class Reference2IntArrayMap<K>
extends AbstractReference2IntMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient int[] value;
    private int size;

    public Reference2IntArrayMap(Object[] objectArray, int[] nArray) {
        this.key = objectArray;
        this.value = nArray;
        this.size = objectArray.length;
        if (objectArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + nArray.length + ")");
        }
    }

    public Reference2IntArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Reference2IntArrayMap(int n) {
        this.key = new Object[n];
        this.value = new int[n];
    }

    public Reference2IntArrayMap(Reference2IntMap<K> reference2IntMap) {
        this(reference2IntMap.size());
        this.putAll(reference2IntMap);
    }

    public Reference2IntArrayMap(Map<? extends K, ? extends Integer> map) {
        this(map.size());
        this.putAll(map);
    }

    public Reference2IntArrayMap(Object[] objectArray, int[] nArray, int n) {
        this.key = objectArray;
        this.value = nArray;
        this.size = n;
        if (objectArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + nArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Reference2IntMap.FastEntrySet<K> reference2IntEntrySet() {
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
    public int getInt(Object object) {
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
    public int put(K k, int n) {
        int n2 = this.findKey(k);
        if (n2 != -1) {
            int n3 = this.value[n2];
            this.value[n2] = n;
            return n3;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n4 = this.size;
            while (n4-- != 0) {
                objectArray[n4] = this.key[n4];
                nArray[n4] = this.value[n4];
            }
            this.key = objectArray;
            this.value = nArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int removeInt(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        int n2 = this.value[n];
        int n3 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n3);
        System.arraycopy(this.value, n + 1, this.value, n, n3);
        --this.size;
        this.key[this.size] = null;
        return n2;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final Reference2IntArrayMap this$0;
            {
                this.this$0 = reference2IntArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Reference2IntArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Reference2IntArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Reference2IntArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Reference2IntArrayMap.access$100(this.this$0), n + 1, Reference2IntArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Reference2IntArrayMap.access$200(this.this$0), n + 1, Reference2IntArrayMap.access$200(this.this$0), n, n2);
                Reference2IntArrayMap.access$010(this.this$0);
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
                        return this.pos < Reference2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2IntArrayMap.access$100(this.this$1.this$0), this.pos, Reference2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2IntArrayMap.access$200(this.this$1.this$0), this.pos, Reference2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2IntArrayMap.access$000(this.this$0);
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
            final Reference2IntArrayMap this$0;
            {
                this.this$0 = reference2IntArrayMap;
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
                        return this.pos < Reference2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2IntArrayMap.access$100(this.this$1.this$0), this.pos, Reference2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2IntArrayMap.access$200(this.this$1.this$0), this.pos, Reference2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2IntArrayMap.access$000(this.this$0);
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

    public Reference2IntArrayMap<K> clone() {
        Reference2IntArrayMap reference2IntArrayMap;
        try {
            reference2IntArrayMap = (Reference2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2IntArrayMap.key = (Object[])this.key.clone();
        reference2IntArrayMap.value = (int[])this.value.clone();
        return reference2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readInt();
        }
    }

    @Override
    public ObjectSet reference2IntEntrySet() {
        return this.reference2IntEntrySet();
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

    static int access$000(Reference2IntArrayMap reference2IntArrayMap) {
        return reference2IntArrayMap.size;
    }

    static Object[] access$100(Reference2IntArrayMap reference2IntArrayMap) {
        return reference2IntArrayMap.key;
    }

    static int[] access$200(Reference2IntArrayMap reference2IntArrayMap) {
        return reference2IntArrayMap.value;
    }

    static int access$010(Reference2IntArrayMap reference2IntArrayMap) {
        return reference2IntArrayMap.size--;
    }

    static int access$300(Reference2IntArrayMap reference2IntArrayMap, Object object) {
        return reference2IntArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2IntMap.Entry<K>>
    implements Reference2IntMap.FastEntrySet<K> {
        final Reference2IntArrayMap this$0;

        private EntrySet(Reference2IntArrayMap reference2IntArrayMap) {
            this.this$0 = reference2IntArrayMap;
        }

        @Override
        public ObjectIterator<Reference2IntMap.Entry<K>> iterator() {
            return new ObjectIterator<Reference2IntMap.Entry<K>>(this){
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
                    return this.next < Reference2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2IntMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2IntMap.BasicEntry<Object>(Reference2IntArrayMap.access$100(this.this$1.this$0)[this.curr], Reference2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2IntArrayMap.access$100((Reference2IntArrayMap)this.this$1.this$0)[Reference2IntArrayMap.access$000((Reference2IntArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Reference2IntMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Reference2IntMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractReference2IntMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractReference2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Reference2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2IntMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2IntArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Reference2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2IntArrayMap.access$100((Reference2IntArrayMap)this.this$1.this$0)[Reference2IntArrayMap.access$000((Reference2IntArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Reference2IntArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && this.this$0.getInt(k) == ((Integer)entry.getValue()).intValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            Object k = entry.getKey();
            int n = (Integer)entry.getValue();
            int n2 = Reference2IntArrayMap.access$300(this.this$0, k);
            if (n2 == -1 || n != Reference2IntArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Reference2IntArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Reference2IntArrayMap.access$100(this.this$0), n2 + 1, Reference2IntArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Reference2IntArrayMap.access$200(this.this$0), n2 + 1, Reference2IntArrayMap.access$200(this.this$0), n2, n3);
            Reference2IntArrayMap.access$010(this.this$0);
            Reference2IntArrayMap.access$100((Reference2IntArrayMap)this.this$0)[Reference2IntArrayMap.access$000((Reference2IntArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Reference2IntArrayMap reference2IntArrayMap, 1 var2_2) {
            this(reference2IntArrayMap);
        }
    }
}

