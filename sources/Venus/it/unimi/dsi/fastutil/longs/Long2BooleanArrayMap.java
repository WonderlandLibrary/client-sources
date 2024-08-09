/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2BooleanMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
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
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Long2BooleanArrayMap
extends AbstractLong2BooleanMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient boolean[] value;
    private int size;

    public Long2BooleanArrayMap(long[] lArray, boolean[] blArray) {
        this.key = lArray;
        this.value = blArray;
        this.size = lArray.length;
        if (lArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + blArray.length + ")");
        }
    }

    public Long2BooleanArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Long2BooleanArrayMap(int n) {
        this.key = new long[n];
        this.value = new boolean[n];
    }

    public Long2BooleanArrayMap(Long2BooleanMap long2BooleanMap) {
        this(long2BooleanMap.size());
        this.putAll(long2BooleanMap);
    }

    public Long2BooleanArrayMap(Map<? extends Long, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2BooleanArrayMap(long[] lArray, boolean[] blArray, int n) {
        this.key = lArray;
        this.value = blArray;
        this.size = n;
        if (lArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + blArray.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2BooleanMap.FastEntrySet long2BooleanEntrySet() {
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
    public boolean get(long l) {
        long[] lArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (lArray[n] != l) continue;
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
        this.size = 0;
    }

    @Override
    public boolean containsKey(long l) {
        return this.findKey(l) != -1;
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
    public boolean put(long l, boolean bl) {
        int n = this.findKey(l);
        if (n != -1) {
            boolean bl2 = this.value[n];
            this.value[n] = bl;
            return bl2;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.key[n2];
                blArray[n2] = this.value[n2];
            }
            this.key = lArray;
            this.value = blArray;
        }
        this.key[this.size] = l;
        this.value[this.size] = bl;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public boolean remove(long l) {
        int n = this.findKey(l);
        if (n == -1) {
            return this.defRetValue;
        }
        boolean bl = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return bl;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2BooleanArrayMap this$0;
            {
                this.this$0 = long2BooleanArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2BooleanArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2BooleanArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2BooleanArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2BooleanArrayMap.access$100(this.this$0), n + 1, Long2BooleanArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2BooleanArrayMap.access$200(this.this$0), n + 1, Long2BooleanArrayMap.access$200(this.this$0), n, n2);
                Long2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Long2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Long2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Long2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2BooleanArrayMap.access$000(this.this$0);
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
            final Long2BooleanArrayMap this$0;
            {
                this.this$0 = long2BooleanArrayMap;
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
                        return this.pos < Long2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Long2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Long2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2BooleanArrayMap.access$000(this.this$0);
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

    public Long2BooleanArrayMap clone() {
        Long2BooleanArrayMap long2BooleanArrayMap;
        try {
            long2BooleanArrayMap = (Long2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2BooleanArrayMap.key = (long[])this.key.clone();
        long2BooleanArrayMap.value = (boolean[])this.value.clone();
        return long2BooleanArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeBoolean(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readBoolean();
        }
    }

    public ObjectSet long2BooleanEntrySet() {
        return this.long2BooleanEntrySet();
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

    static int access$000(Long2BooleanArrayMap long2BooleanArrayMap) {
        return long2BooleanArrayMap.size;
    }

    static long[] access$100(Long2BooleanArrayMap long2BooleanArrayMap) {
        return long2BooleanArrayMap.key;
    }

    static boolean[] access$200(Long2BooleanArrayMap long2BooleanArrayMap) {
        return long2BooleanArrayMap.value;
    }

    static int access$010(Long2BooleanArrayMap long2BooleanArrayMap) {
        return long2BooleanArrayMap.size--;
    }

    static int access$300(Long2BooleanArrayMap long2BooleanArrayMap, long l) {
        return long2BooleanArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2BooleanMap.Entry>
    implements Long2BooleanMap.FastEntrySet {
        final Long2BooleanArrayMap this$0;

        private EntrySet(Long2BooleanArrayMap long2BooleanArrayMap) {
            this.this$0 = long2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Long2BooleanMap.Entry> iterator() {
            return new ObjectIterator<Long2BooleanMap.Entry>(this){
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
                    return this.next < Long2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2BooleanMap.BasicEntry(Long2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Long2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2BooleanMap.Entry> fastIterator() {
            return new ObjectIterator<Long2BooleanMap.Entry>(this){
                int next;
                int curr;
                final AbstractLong2BooleanMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2BooleanArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && this.this$0.get(l) == ((Boolean)entry.getValue()).booleanValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            long l = (Long)entry.getKey();
            boolean bl = (Boolean)entry.getValue();
            int n = Long2BooleanArrayMap.access$300(this.this$0, l);
            if (n == -1 || bl != Long2BooleanArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Long2BooleanArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2BooleanArrayMap.access$100(this.this$0), n + 1, Long2BooleanArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2BooleanArrayMap.access$200(this.this$0), n + 1, Long2BooleanArrayMap.access$200(this.this$0), n, n2);
            Long2BooleanArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2BooleanArrayMap long2BooleanArrayMap, 1 var2_2) {
            this(long2BooleanArrayMap);
        }
    }
}

