/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2ObjectMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
public class Long2ObjectArrayMap<V>
extends AbstractLong2ObjectMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient Object[] value;
    private int size;

    public Long2ObjectArrayMap(long[] lArray, Object[] objectArray) {
        this.key = lArray;
        this.value = objectArray;
        this.size = lArray.length;
        if (lArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + objectArray.length + ")");
        }
    }

    public Long2ObjectArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Long2ObjectArrayMap(int n) {
        this.key = new long[n];
        this.value = new Object[n];
    }

    public Long2ObjectArrayMap(Long2ObjectMap<V> long2ObjectMap) {
        this(long2ObjectMap.size());
        this.putAll(long2ObjectMap);
    }

    public Long2ObjectArrayMap(Map<? extends Long, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2ObjectArrayMap(long[] lArray, Object[] objectArray, int n) {
        this.key = lArray;
        this.value = objectArray;
        this.size = n;
        if (lArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + objectArray.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2ObjectMap.FastEntrySet<V> long2ObjectEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(long l) {
        long[] lArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (lArray[n] != l) continue;
            return n;
        }
        return 1;
    }

    @Override
    public V get(long l) {
        long[] lArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (lArray[n] != l) continue;
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
    public boolean containsKey(long l) {
        return this.findKey(l) != -1;
    }

    @Override
    public boolean containsValue(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(this.value[n], object)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public V put(long l, V v) {
        int n = this.findKey(l);
        if (n != -1) {
            Object object = this.value[n];
            this.value[n] = v;
            return (V)object;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.key[n2];
                objectArray[n2] = this.value[n2];
            }
            this.key = lArray;
            this.value = objectArray;
        }
        this.key[this.size] = l;
        this.value[this.size] = v;
        ++this.size;
        return (V)this.defRetValue;
    }

    @Override
    public V remove(long l) {
        int n = this.findKey(l);
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
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2ObjectArrayMap this$0;
            {
                this.this$0 = long2ObjectArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2ObjectArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2ObjectArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2ObjectArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2ObjectArrayMap.access$100(this.this$0), n + 1, Long2ObjectArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2ObjectArrayMap.access$200(this.this$0), n + 1, Long2ObjectArrayMap.access$200(this.this$0), n, n2);
                Long2ObjectArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public LongIterator iterator() {
                return new LongIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Long2ObjectArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Long2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Long2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2ObjectArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2ObjectArrayMap.access$000(this.this$0);
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
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(this){
            final Long2ObjectArrayMap this$0;
            {
                this.this$0 = long2ObjectArrayMap;
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
                        return this.pos < Long2ObjectArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2ObjectArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Long2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Long2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2ObjectArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2ObjectArrayMap.access$000(this.this$0);
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

    public Long2ObjectArrayMap<V> clone() {
        Long2ObjectArrayMap long2ObjectArrayMap;
        try {
            long2ObjectArrayMap = (Long2ObjectArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2ObjectArrayMap.key = (long[])this.key.clone();
        long2ObjectArrayMap.value = (Object[])this.value.clone();
        return long2ObjectArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readObject();
        }
    }

    @Override
    public ObjectSet long2ObjectEntrySet() {
        return this.long2ObjectEntrySet();
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

    static int access$000(Long2ObjectArrayMap long2ObjectArrayMap) {
        return long2ObjectArrayMap.size;
    }

    static long[] access$100(Long2ObjectArrayMap long2ObjectArrayMap) {
        return long2ObjectArrayMap.key;
    }

    static Object[] access$200(Long2ObjectArrayMap long2ObjectArrayMap) {
        return long2ObjectArrayMap.value;
    }

    static int access$010(Long2ObjectArrayMap long2ObjectArrayMap) {
        return long2ObjectArrayMap.size--;
    }

    static int access$300(Long2ObjectArrayMap long2ObjectArrayMap, long l) {
        return long2ObjectArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2ObjectMap.Entry<V>>
    implements Long2ObjectMap.FastEntrySet<V> {
        final Long2ObjectArrayMap this$0;

        private EntrySet(Long2ObjectArrayMap long2ObjectArrayMap) {
            this.this$0 = long2ObjectArrayMap;
        }

        @Override
        public ObjectIterator<Long2ObjectMap.Entry<V>> iterator() {
            return new ObjectIterator<Long2ObjectMap.Entry<V>>(this){
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
                    return this.next < Long2ObjectArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2ObjectMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2ObjectMap.BasicEntry<Object>(Long2ObjectArrayMap.access$100(this.this$1.this$0)[this.curr], Long2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Long2ObjectArrayMap.access$200((Long2ObjectArrayMap)this.this$1.this$0)[Long2ObjectArrayMap.access$000((Long2ObjectArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2ObjectMap.Entry<V>> fastIterator() {
            return new ObjectIterator<Long2ObjectMap.Entry<V>>(this){
                int next;
                int curr;
                final AbstractLong2ObjectMap.BasicEntry<V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2ObjectMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2ObjectArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2ObjectMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2ObjectArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Long2ObjectArrayMap.access$200((Long2ObjectArrayMap)this.this$1.this$0)[Long2ObjectArrayMap.access$000((Long2ObjectArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2ObjectArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && Objects.equals(this.this$0.get(l), entry.getValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            long l = (Long)entry.getKey();
            Object v = entry.getValue();
            int n = Long2ObjectArrayMap.access$300(this.this$0, l);
            if (n == -1 || !Objects.equals(v, Long2ObjectArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Long2ObjectArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2ObjectArrayMap.access$100(this.this$0), n + 1, Long2ObjectArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2ObjectArrayMap.access$200(this.this$0), n + 1, Long2ObjectArrayMap.access$200(this.this$0), n, n2);
            Long2ObjectArrayMap.access$010(this.this$0);
            Long2ObjectArrayMap.access$200((Long2ObjectArrayMap)this.this$0)[Long2ObjectArrayMap.access$000((Long2ObjectArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2ObjectArrayMap long2ObjectArrayMap, 1 var2_2) {
            this(long2ObjectArrayMap);
        }
    }
}

