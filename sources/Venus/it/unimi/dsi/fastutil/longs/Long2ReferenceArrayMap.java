/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
public class Long2ReferenceArrayMap<V>
extends AbstractLong2ReferenceMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient Object[] value;
    private int size;

    public Long2ReferenceArrayMap(long[] lArray, Object[] objectArray) {
        this.key = lArray;
        this.value = objectArray;
        this.size = lArray.length;
        if (lArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + objectArray.length + ")");
        }
    }

    public Long2ReferenceArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Long2ReferenceArrayMap(int n) {
        this.key = new long[n];
        this.value = new Object[n];
    }

    public Long2ReferenceArrayMap(Long2ReferenceMap<V> long2ReferenceMap) {
        this(long2ReferenceMap.size());
        this.putAll(long2ReferenceMap);
    }

    public Long2ReferenceArrayMap(Map<? extends Long, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2ReferenceArrayMap(long[] lArray, Object[] objectArray, int n) {
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

    public Long2ReferenceMap.FastEntrySet<V> long2ReferenceEntrySet() {
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
            final Long2ReferenceArrayMap this$0;
            {
                this.this$0 = long2ReferenceArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2ReferenceArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2ReferenceArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2ReferenceArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2ReferenceArrayMap.access$100(this.this$0), n + 1, Long2ReferenceArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2ReferenceArrayMap.access$200(this.this$0), n + 1, Long2ReferenceArrayMap.access$200(this.this$0), n, n2);
                Long2ReferenceArrayMap.access$010(this.this$0);
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
                        return this.pos < Long2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2ReferenceArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Long2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Long2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2ReferenceArrayMap.access$000(this.this$0);
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
            final Long2ReferenceArrayMap this$0;
            {
                this.this$0 = long2ReferenceArrayMap;
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
                        return this.pos < Long2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2ReferenceArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Long2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Long2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2ReferenceArrayMap.access$000(this.this$0);
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

    public Long2ReferenceArrayMap<V> clone() {
        Long2ReferenceArrayMap long2ReferenceArrayMap;
        try {
            long2ReferenceArrayMap = (Long2ReferenceArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2ReferenceArrayMap.key = (long[])this.key.clone();
        long2ReferenceArrayMap.value = (Object[])this.value.clone();
        return long2ReferenceArrayMap;
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
    public ObjectSet long2ReferenceEntrySet() {
        return this.long2ReferenceEntrySet();
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

    static int access$000(Long2ReferenceArrayMap long2ReferenceArrayMap) {
        return long2ReferenceArrayMap.size;
    }

    static long[] access$100(Long2ReferenceArrayMap long2ReferenceArrayMap) {
        return long2ReferenceArrayMap.key;
    }

    static Object[] access$200(Long2ReferenceArrayMap long2ReferenceArrayMap) {
        return long2ReferenceArrayMap.value;
    }

    static int access$010(Long2ReferenceArrayMap long2ReferenceArrayMap) {
        return long2ReferenceArrayMap.size--;
    }

    static int access$300(Long2ReferenceArrayMap long2ReferenceArrayMap, long l) {
        return long2ReferenceArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2ReferenceMap.Entry<V>>
    implements Long2ReferenceMap.FastEntrySet<V> {
        final Long2ReferenceArrayMap this$0;

        private EntrySet(Long2ReferenceArrayMap long2ReferenceArrayMap) {
            this.this$0 = long2ReferenceArrayMap;
        }

        @Override
        public ObjectIterator<Long2ReferenceMap.Entry<V>> iterator() {
            return new ObjectIterator<Long2ReferenceMap.Entry<V>>(this){
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
                    return this.next < Long2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2ReferenceMap.BasicEntry<Object>(Long2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr], Long2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Long2ReferenceArrayMap.access$200((Long2ReferenceArrayMap)this.this$1.this$0)[Long2ReferenceArrayMap.access$000((Long2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2ReferenceMap.Entry<V>> fastIterator() {
            return new ObjectIterator<Long2ReferenceMap.Entry<V>>(this){
                int next;
                int curr;
                final AbstractLong2ReferenceMap.BasicEntry<V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2ReferenceMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Long2ReferenceArrayMap.access$200((Long2ReferenceArrayMap)this.this$1.this$0)[Long2ReferenceArrayMap.access$000((Long2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2ReferenceArrayMap.access$000(this.this$0);
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
            return this.this$0.containsKey(l) && this.this$0.get(l) == entry.getValue();
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
            int n = Long2ReferenceArrayMap.access$300(this.this$0, l);
            if (n == -1 || v != Long2ReferenceArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Long2ReferenceArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2ReferenceArrayMap.access$100(this.this$0), n + 1, Long2ReferenceArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2ReferenceArrayMap.access$200(this.this$0), n + 1, Long2ReferenceArrayMap.access$200(this.this$0), n, n2);
            Long2ReferenceArrayMap.access$010(this.this$0);
            Long2ReferenceArrayMap.access$200((Long2ReferenceArrayMap)this.this$0)[Long2ReferenceArrayMap.access$000((Long2ReferenceArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2ReferenceArrayMap long2ReferenceArrayMap, 1 var2_2) {
            this(long2ReferenceArrayMap);
        }
    }
}

