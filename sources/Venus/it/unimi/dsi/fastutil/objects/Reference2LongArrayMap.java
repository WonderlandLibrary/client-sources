/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2LongMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2LongMap;
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
public class Reference2LongArrayMap<K>
extends AbstractReference2LongMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient long[] value;
    private int size;

    public Reference2LongArrayMap(Object[] objectArray, long[] lArray) {
        this.key = objectArray;
        this.value = lArray;
        this.size = objectArray.length;
        if (objectArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + lArray.length + ")");
        }
    }

    public Reference2LongArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Reference2LongArrayMap(int n) {
        this.key = new Object[n];
        this.value = new long[n];
    }

    public Reference2LongArrayMap(Reference2LongMap<K> reference2LongMap) {
        this(reference2LongMap.size());
        this.putAll(reference2LongMap);
    }

    public Reference2LongArrayMap(Map<? extends K, ? extends Long> map) {
        this(map.size());
        this.putAll(map);
    }

    public Reference2LongArrayMap(Object[] objectArray, long[] lArray, int n) {
        this.key = objectArray;
        this.value = lArray;
        this.size = n;
        if (objectArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + lArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Reference2LongMap.FastEntrySet<K> reference2LongEntrySet() {
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
    public long getLong(Object object) {
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
    public boolean containsValue(long l) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != l) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public long put(K k, long l) {
        int n = this.findKey(k);
        if (n != -1) {
            long l2 = this.value[n];
            this.value[n] = l;
            return l2;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.key[n2];
                lArray[n2] = this.value[n2];
            }
            this.key = objectArray;
            this.value = lArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = l;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public long removeLong(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        long l = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.key[this.size] = null;
        return l;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final Reference2LongArrayMap this$0;
            {
                this.this$0 = reference2LongArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Reference2LongArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Reference2LongArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Reference2LongArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Reference2LongArrayMap.access$100(this.this$0), n + 1, Reference2LongArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Reference2LongArrayMap.access$200(this.this$0), n + 1, Reference2LongArrayMap.access$200(this.this$0), n, n2);
                Reference2LongArrayMap.access$010(this.this$0);
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
                        return this.pos < Reference2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2LongArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2LongArrayMap.access$100(this.this$1.this$0), this.pos, Reference2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2LongArrayMap.access$200(this.this$1.this$0), this.pos, Reference2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2LongArrayMap.access$000(this.this$0);
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
    public LongCollection values() {
        return new AbstractLongCollection(this){
            final Reference2LongArrayMap this$0;
            {
                this.this$0 = reference2LongArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return this.this$0.containsValue(l);
            }

            @Override
            public LongIterator iterator() {
                return new LongIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Reference2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2LongArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2LongArrayMap.access$100(this.this$1.this$0), this.pos, Reference2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2LongArrayMap.access$200(this.this$1.this$0), this.pos, Reference2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2LongArrayMap.access$000(this.this$0);
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

    public Reference2LongArrayMap<K> clone() {
        Reference2LongArrayMap reference2LongArrayMap;
        try {
            reference2LongArrayMap = (Reference2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2LongArrayMap.key = (Object[])this.key.clone();
        reference2LongArrayMap.value = (long[])this.value.clone();
        return reference2LongArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readLong();
        }
    }

    @Override
    public ObjectSet reference2LongEntrySet() {
        return this.reference2LongEntrySet();
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

    static int access$000(Reference2LongArrayMap reference2LongArrayMap) {
        return reference2LongArrayMap.size;
    }

    static Object[] access$100(Reference2LongArrayMap reference2LongArrayMap) {
        return reference2LongArrayMap.key;
    }

    static long[] access$200(Reference2LongArrayMap reference2LongArrayMap) {
        return reference2LongArrayMap.value;
    }

    static int access$010(Reference2LongArrayMap reference2LongArrayMap) {
        return reference2LongArrayMap.size--;
    }

    static int access$300(Reference2LongArrayMap reference2LongArrayMap, Object object) {
        return reference2LongArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2LongMap.Entry<K>>
    implements Reference2LongMap.FastEntrySet<K> {
        final Reference2LongArrayMap this$0;

        private EntrySet(Reference2LongArrayMap reference2LongArrayMap) {
            this.this$0 = reference2LongArrayMap;
        }

        @Override
        public ObjectIterator<Reference2LongMap.Entry<K>> iterator() {
            return new ObjectIterator<Reference2LongMap.Entry<K>>(this){
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
                    return this.next < Reference2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2LongMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2LongMap.BasicEntry<Object>(Reference2LongArrayMap.access$100(this.this$1.this$0)[this.curr], Reference2LongArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2LongArrayMap.access$100((Reference2LongArrayMap)this.this$1.this$0)[Reference2LongArrayMap.access$000((Reference2LongArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Reference2LongMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Reference2LongMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractReference2LongMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractReference2LongMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Reference2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2LongMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2LongArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Reference2LongArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2LongArrayMap.access$100((Reference2LongArrayMap)this.this$1.this$0)[Reference2LongArrayMap.access$000((Reference2LongArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Reference2LongArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && this.this$0.getLong(k) == ((Long)entry.getValue()).longValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            Object k = entry.getKey();
            long l = (Long)entry.getValue();
            int n = Reference2LongArrayMap.access$300(this.this$0, k);
            if (n == -1 || l != Reference2LongArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Reference2LongArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Reference2LongArrayMap.access$100(this.this$0), n + 1, Reference2LongArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Reference2LongArrayMap.access$200(this.this$0), n + 1, Reference2LongArrayMap.access$200(this.this$0), n, n2);
            Reference2LongArrayMap.access$010(this.this$0);
            Reference2LongArrayMap.access$100((Reference2LongArrayMap)this.this$0)[Reference2LongArrayMap.access$000((Reference2LongArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Reference2LongArrayMap reference2LongArrayMap, 1 var2_2) {
            this(reference2LongArrayMap);
        }
    }
}

