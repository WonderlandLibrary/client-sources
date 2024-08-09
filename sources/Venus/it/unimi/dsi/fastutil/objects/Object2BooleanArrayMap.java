/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2BooleanMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
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
public class Object2BooleanArrayMap<K>
extends AbstractObject2BooleanMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient boolean[] value;
    private int size;

    public Object2BooleanArrayMap(Object[] objectArray, boolean[] blArray) {
        this.key = objectArray;
        this.value = blArray;
        this.size = objectArray.length;
        if (objectArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + blArray.length + ")");
        }
    }

    public Object2BooleanArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Object2BooleanArrayMap(int n) {
        this.key = new Object[n];
        this.value = new boolean[n];
    }

    public Object2BooleanArrayMap(Object2BooleanMap<K> object2BooleanMap) {
        this(object2BooleanMap.size());
        this.putAll(object2BooleanMap);
    }

    public Object2BooleanArrayMap(Map<? extends K, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Object2BooleanArrayMap(Object[] objectArray, boolean[] blArray, int n) {
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

    public Object2BooleanMap.FastEntrySet<K> object2BooleanEntrySet() {
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
    public boolean getBoolean(Object object) {
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
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(this){
            final Object2BooleanArrayMap this$0;
            {
                this.this$0 = object2BooleanArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Object2BooleanArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Object2BooleanArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Object2BooleanArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Object2BooleanArrayMap.access$100(this.this$0), n + 1, Object2BooleanArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Object2BooleanArrayMap.access$200(this.this$0), n + 1, Object2BooleanArrayMap.access$200(this.this$0), n, n2);
                Object2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Object2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Object2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Object2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Object2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Object2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Object2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Object2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Object2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Object2BooleanArrayMap.access$000(this.this$0);
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
            final Object2BooleanArrayMap this$0;
            {
                this.this$0 = object2BooleanArrayMap;
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
                        return this.pos < Object2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Object2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Object2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Object2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Object2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Object2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Object2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Object2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Object2BooleanArrayMap.access$000(this.this$0);
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

    public Object2BooleanArrayMap<K> clone() {
        Object2BooleanArrayMap object2BooleanArrayMap;
        try {
            object2BooleanArrayMap = (Object2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2BooleanArrayMap.key = (Object[])this.key.clone();
        object2BooleanArrayMap.value = (boolean[])this.value.clone();
        return object2BooleanArrayMap;
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
    public ObjectSet object2BooleanEntrySet() {
        return this.object2BooleanEntrySet();
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

    static int access$000(Object2BooleanArrayMap object2BooleanArrayMap) {
        return object2BooleanArrayMap.size;
    }

    static Object[] access$100(Object2BooleanArrayMap object2BooleanArrayMap) {
        return object2BooleanArrayMap.key;
    }

    static boolean[] access$200(Object2BooleanArrayMap object2BooleanArrayMap) {
        return object2BooleanArrayMap.value;
    }

    static int access$010(Object2BooleanArrayMap object2BooleanArrayMap) {
        return object2BooleanArrayMap.size--;
    }

    static int access$300(Object2BooleanArrayMap object2BooleanArrayMap, Object object) {
        return object2BooleanArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Object2BooleanMap.Entry<K>>
    implements Object2BooleanMap.FastEntrySet<K> {
        final Object2BooleanArrayMap this$0;

        private EntrySet(Object2BooleanArrayMap object2BooleanArrayMap) {
            this.this$0 = object2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Object2BooleanMap.Entry<K>> iterator() {
            return new ObjectIterator<Object2BooleanMap.Entry<K>>(this){
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
                    return this.next < Object2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Object2BooleanMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractObject2BooleanMap.BasicEntry<Object>(Object2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Object2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Object2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2BooleanArrayMap.access$100((Object2BooleanArrayMap)this.this$1.this$0)[Object2BooleanArrayMap.access$000((Object2BooleanArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Object2BooleanMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Object2BooleanMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractObject2BooleanMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractObject2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Object2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Object2BooleanMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Object2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Object2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Object2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2BooleanArrayMap.access$100((Object2BooleanArrayMap)this.this$1.this$0)[Object2BooleanArrayMap.access$000((Object2BooleanArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Object2BooleanArrayMap.access$000(this.this$0);
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
            int n = Object2BooleanArrayMap.access$300(this.this$0, k);
            if (n == -1 || bl != Object2BooleanArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Object2BooleanArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Object2BooleanArrayMap.access$100(this.this$0), n + 1, Object2BooleanArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Object2BooleanArrayMap.access$200(this.this$0), n + 1, Object2BooleanArrayMap.access$200(this.this$0), n, n2);
            Object2BooleanArrayMap.access$010(this.this$0);
            Object2BooleanArrayMap.access$100((Object2BooleanArrayMap)this.this$0)[Object2BooleanArrayMap.access$000((Object2BooleanArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Object2BooleanArrayMap object2BooleanArrayMap, 1 var2_2) {
            this(object2BooleanArrayMap);
        }
    }
}

