/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2BooleanMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap;
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
public class Reference2BooleanArrayMap<K>
extends AbstractReference2BooleanMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient boolean[] value;
    private int size;

    public Reference2BooleanArrayMap(Object[] objectArray, boolean[] blArray) {
        this.key = objectArray;
        this.value = blArray;
        this.size = objectArray.length;
        if (objectArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + blArray.length + ")");
        }
    }

    public Reference2BooleanArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Reference2BooleanArrayMap(int n) {
        this.key = new Object[n];
        this.value = new boolean[n];
    }

    public Reference2BooleanArrayMap(Reference2BooleanMap<K> reference2BooleanMap) {
        this(reference2BooleanMap.size());
        this.putAll(reference2BooleanMap);
    }

    public Reference2BooleanArrayMap(Map<? extends K, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Reference2BooleanArrayMap(Object[] objectArray, boolean[] blArray, int n) {
        this.key = objectArray;
        this.value = blArray;
        this.size = n;
        if (objectArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + blArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Reference2BooleanMap.FastEntrySet<K> reference2BooleanEntrySet() {
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
    public boolean getBoolean(Object object) {
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
    public boolean put(K k, boolean bl) {
        int n = this.findKey(k);
        if (n != -1) {
            boolean bl2 = this.value[n];
            this.value[n] = bl;
            return bl2;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.key[n2];
                blArray[n2] = this.value[n2];
            }
            this.key = objectArray;
            this.value = blArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = bl;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public boolean removeBoolean(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        boolean bl = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.key[this.size] = null;
        return bl;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final Reference2BooleanArrayMap this$0;
            {
                this.this$0 = reference2BooleanArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Reference2BooleanArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Reference2BooleanArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Reference2BooleanArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Reference2BooleanArrayMap.access$100(this.this$0), n + 1, Reference2BooleanArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Reference2BooleanArrayMap.access$200(this.this$0), n + 1, Reference2BooleanArrayMap.access$200(this.this$0), n, n2);
                Reference2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Reference2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Reference2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Reference2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2BooleanArrayMap.access$000(this.this$0);
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
            final Reference2BooleanArrayMap this$0;
            {
                this.this$0 = reference2BooleanArrayMap;
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
                        return this.pos < Reference2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Reference2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Reference2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Reference2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Reference2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Reference2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Reference2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Reference2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Reference2BooleanArrayMap.access$000(this.this$0);
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

    public Reference2BooleanArrayMap<K> clone() {
        Reference2BooleanArrayMap reference2BooleanArrayMap;
        try {
            reference2BooleanArrayMap = (Reference2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        reference2BooleanArrayMap.key = (Object[])this.key.clone();
        reference2BooleanArrayMap.value = (boolean[])this.value.clone();
        return reference2BooleanArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeBoolean(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readBoolean();
        }
    }

    @Override
    public ObjectSet reference2BooleanEntrySet() {
        return this.reference2BooleanEntrySet();
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

    static int access$000(Reference2BooleanArrayMap reference2BooleanArrayMap) {
        return reference2BooleanArrayMap.size;
    }

    static Object[] access$100(Reference2BooleanArrayMap reference2BooleanArrayMap) {
        return reference2BooleanArrayMap.key;
    }

    static boolean[] access$200(Reference2BooleanArrayMap reference2BooleanArrayMap) {
        return reference2BooleanArrayMap.value;
    }

    static int access$010(Reference2BooleanArrayMap reference2BooleanArrayMap) {
        return reference2BooleanArrayMap.size--;
    }

    static int access$300(Reference2BooleanArrayMap reference2BooleanArrayMap, Object object) {
        return reference2BooleanArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Reference2BooleanMap.Entry<K>>
    implements Reference2BooleanMap.FastEntrySet<K> {
        final Reference2BooleanArrayMap this$0;

        private EntrySet(Reference2BooleanArrayMap reference2BooleanArrayMap) {
            this.this$0 = reference2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Reference2BooleanMap.Entry<K>> iterator() {
            return new ObjectIterator<Reference2BooleanMap.Entry<K>>(this){
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
                    return this.next < Reference2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2BooleanMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractReference2BooleanMap.BasicEntry<Object>(Reference2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Reference2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2BooleanArrayMap.access$100((Reference2BooleanArrayMap)this.this$1.this$0)[Reference2BooleanArrayMap.access$000((Reference2BooleanArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Reference2BooleanMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Reference2BooleanMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractReference2BooleanMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractReference2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Reference2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Reference2BooleanMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Reference2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Reference2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Reference2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Reference2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Reference2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Reference2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Reference2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Reference2BooleanArrayMap.access$100((Reference2BooleanArrayMap)this.this$1.this$0)[Reference2BooleanArrayMap.access$000((Reference2BooleanArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Reference2BooleanArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && this.this$0.getBoolean(k) == ((Boolean)entry.getValue()).booleanValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            Object k = entry.getKey();
            boolean bl = (Boolean)entry.getValue();
            int n = Reference2BooleanArrayMap.access$300(this.this$0, k);
            if (n == -1 || bl != Reference2BooleanArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Reference2BooleanArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Reference2BooleanArrayMap.access$100(this.this$0), n + 1, Reference2BooleanArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Reference2BooleanArrayMap.access$200(this.this$0), n + 1, Reference2BooleanArrayMap.access$200(this.this$0), n, n2);
            Reference2BooleanArrayMap.access$010(this.this$0);
            Reference2BooleanArrayMap.access$100((Reference2BooleanArrayMap)this.this$0)[Reference2BooleanArrayMap.access$000((Reference2BooleanArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Reference2BooleanArrayMap reference2BooleanArrayMap, 1 var2_2) {
            this(reference2BooleanArrayMap);
        }
    }
}

