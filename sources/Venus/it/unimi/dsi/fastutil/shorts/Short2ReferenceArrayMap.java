/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ReferenceMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
public class Short2ReferenceArrayMap<V>
extends AbstractShort2ReferenceMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] key;
    private transient Object[] value;
    private int size;

    public Short2ReferenceArrayMap(short[] sArray, Object[] objectArray) {
        this.key = sArray;
        this.value = objectArray;
        this.size = sArray.length;
        if (sArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + objectArray.length + ")");
        }
    }

    public Short2ReferenceArrayMap() {
        this.key = ShortArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Short2ReferenceArrayMap(int n) {
        this.key = new short[n];
        this.value = new Object[n];
    }

    public Short2ReferenceArrayMap(Short2ReferenceMap<V> short2ReferenceMap) {
        this(short2ReferenceMap.size());
        this.putAll(short2ReferenceMap);
    }

    public Short2ReferenceArrayMap(Map<? extends Short, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }

    public Short2ReferenceArrayMap(short[] sArray, Object[] objectArray, int n) {
        this.key = sArray;
        this.value = objectArray;
        this.size = n;
        if (sArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + objectArray.length + ")");
        }
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + sArray.length + ")");
        }
    }

    public Short2ReferenceMap.FastEntrySet<V> short2ReferenceEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(short s) {
        short[] sArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (sArray[n] != s) continue;
            return n;
        }
        return 1;
    }

    @Override
    public V get(short s) {
        short[] sArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (sArray[n] != s) continue;
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
    public boolean containsKey(short s) {
        return this.findKey(s) != -1;
    }

    @Override
    public boolean containsValue(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public V put(short s, V v) {
        int n = this.findKey(s);
        if (n != -1) {
            Object object = this.value[n];
            this.value[n] = v;
            return (V)object;
        }
        if (this.size == this.key.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                sArray[n2] = this.key[n2];
                objectArray[n2] = this.value[n2];
            }
            this.key = sArray;
            this.value = objectArray;
        }
        this.key[this.size] = s;
        this.value[this.size] = v;
        ++this.size;
        return (V)this.defRetValue;
    }

    @Override
    public V remove(short s) {
        int n = this.findKey(s);
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
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final Short2ReferenceArrayMap this$0;
            {
                this.this$0 = short2ReferenceArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return Short2ReferenceArrayMap.access$300(this.this$0, s) != -1;
            }

            @Override
            public boolean remove(short s) {
                int n = Short2ReferenceArrayMap.access$300(this.this$0, s);
                if (n == -1) {
                    return true;
                }
                int n2 = Short2ReferenceArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Short2ReferenceArrayMap.access$100(this.this$0), n + 1, Short2ReferenceArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Short2ReferenceArrayMap.access$200(this.this$0), n + 1, Short2ReferenceArrayMap.access$200(this.this$0), n, n2);
                Short2ReferenceArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public ShortIterator iterator() {
                return new ShortIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Short2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2ReferenceArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Short2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Short2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2ReferenceArrayMap.access$000(this.this$0);
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
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(this){
            final Short2ReferenceArrayMap this$0;
            {
                this.this$0 = short2ReferenceArrayMap;
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
                        return this.pos < Short2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2ReferenceArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Short2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Short2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2ReferenceArrayMap.access$000(this.this$0);
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

    public Short2ReferenceArrayMap<V> clone() {
        Short2ReferenceArrayMap short2ReferenceArrayMap;
        try {
            short2ReferenceArrayMap = (Short2ReferenceArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2ReferenceArrayMap.key = (short[])this.key.clone();
        short2ReferenceArrayMap.value = (Object[])this.value.clone();
        return short2ReferenceArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.key[i]);
            objectOutputStream.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new short[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readShort();
            this.value[i] = objectInputStream.readObject();
        }
    }

    @Override
    public ObjectSet short2ReferenceEntrySet() {
        return this.short2ReferenceEntrySet();
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

    static int access$000(Short2ReferenceArrayMap short2ReferenceArrayMap) {
        return short2ReferenceArrayMap.size;
    }

    static short[] access$100(Short2ReferenceArrayMap short2ReferenceArrayMap) {
        return short2ReferenceArrayMap.key;
    }

    static Object[] access$200(Short2ReferenceArrayMap short2ReferenceArrayMap) {
        return short2ReferenceArrayMap.value;
    }

    static int access$010(Short2ReferenceArrayMap short2ReferenceArrayMap) {
        return short2ReferenceArrayMap.size--;
    }

    static int access$300(Short2ReferenceArrayMap short2ReferenceArrayMap, short s) {
        return short2ReferenceArrayMap.findKey(s);
    }

    private final class EntrySet
    extends AbstractObjectSet<Short2ReferenceMap.Entry<V>>
    implements Short2ReferenceMap.FastEntrySet<V> {
        final Short2ReferenceArrayMap this$0;

        private EntrySet(Short2ReferenceArrayMap short2ReferenceArrayMap) {
            this.this$0 = short2ReferenceArrayMap;
        }

        @Override
        public ObjectIterator<Short2ReferenceMap.Entry<V>> iterator() {
            return new ObjectIterator<Short2ReferenceMap.Entry<V>>(this){
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
                    return this.next < Short2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractShort2ReferenceMap.BasicEntry<Object>(Short2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr], Short2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Short2ReferenceArrayMap.access$200((Short2ReferenceArrayMap)this.this$1.this$0)[Short2ReferenceArrayMap.access$000((Short2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Short2ReferenceMap.Entry<V>> fastIterator() {
            return new ObjectIterator<Short2ReferenceMap.Entry<V>>(this){
                int next;
                int curr;
                final AbstractShort2ReferenceMap.BasicEntry<V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractShort2ReferenceMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Short2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Short2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Short2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Short2ReferenceArrayMap.access$200((Short2ReferenceArrayMap)this.this$1.this$0)[Short2ReferenceArrayMap.access$000((Short2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Short2ReferenceArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Short)) {
                return true;
            }
            short s = (Short)entry.getKey();
            return this.this$0.containsKey(s) && this.this$0.get(s) == entry.getValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Short)) {
                return true;
            }
            short s = (Short)entry.getKey();
            Object v = entry.getValue();
            int n = Short2ReferenceArrayMap.access$300(this.this$0, s);
            if (n == -1 || v != Short2ReferenceArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Short2ReferenceArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Short2ReferenceArrayMap.access$100(this.this$0), n + 1, Short2ReferenceArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Short2ReferenceArrayMap.access$200(this.this$0), n + 1, Short2ReferenceArrayMap.access$200(this.this$0), n, n2);
            Short2ReferenceArrayMap.access$010(this.this$0);
            Short2ReferenceArrayMap.access$200((Short2ReferenceArrayMap)this.this$0)[Short2ReferenceArrayMap.access$000((Short2ReferenceArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Short2ReferenceArrayMap short2ReferenceArrayMap, 1 var2_2) {
            this(short2ReferenceArrayMap);
        }
    }
}

