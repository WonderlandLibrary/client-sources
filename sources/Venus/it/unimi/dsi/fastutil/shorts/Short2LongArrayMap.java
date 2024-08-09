/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2LongMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
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
public class Short2LongArrayMap
extends AbstractShort2LongMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] key;
    private transient long[] value;
    private int size;

    public Short2LongArrayMap(short[] sArray, long[] lArray) {
        this.key = sArray;
        this.value = lArray;
        this.size = sArray.length;
        if (sArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + lArray.length + ")");
        }
    }

    public Short2LongArrayMap() {
        this.key = ShortArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Short2LongArrayMap(int n) {
        this.key = new short[n];
        this.value = new long[n];
    }

    public Short2LongArrayMap(Short2LongMap short2LongMap) {
        this(short2LongMap.size());
        this.putAll(short2LongMap);
    }

    public Short2LongArrayMap(Map<? extends Short, ? extends Long> map) {
        this(map.size());
        this.putAll(map);
    }

    public Short2LongArrayMap(short[] sArray, long[] lArray, int n) {
        this.key = sArray;
        this.value = lArray;
        this.size = n;
        if (sArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + lArray.length + ")");
        }
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + sArray.length + ")");
        }
    }

    public Short2LongMap.FastEntrySet short2LongEntrySet() {
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
    public long get(short s) {
        short[] sArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (sArray[n] != s) continue;
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
    public boolean containsKey(short s) {
        return this.findKey(s) != -1;
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
    public long put(short s, long l) {
        int n = this.findKey(s);
        if (n != -1) {
            long l2 = this.value[n];
            this.value[n] = l;
            return l2;
        }
        if (this.size == this.key.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                sArray[n2] = this.key[n2];
                lArray[n2] = this.value[n2];
            }
            this.key = sArray;
            this.value = lArray;
        }
        this.key[this.size] = s;
        this.value[this.size] = l;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public long remove(short s) {
        int n = this.findKey(s);
        if (n == -1) {
            return this.defRetValue;
        }
        long l = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return l;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final Short2LongArrayMap this$0;
            {
                this.this$0 = short2LongArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return Short2LongArrayMap.access$300(this.this$0, s) != -1;
            }

            @Override
            public boolean remove(short s) {
                int n = Short2LongArrayMap.access$300(this.this$0, s);
                if (n == -1) {
                    return true;
                }
                int n2 = Short2LongArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Short2LongArrayMap.access$100(this.this$0), n + 1, Short2LongArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Short2LongArrayMap.access$200(this.this$0), n + 1, Short2LongArrayMap.access$200(this.this$0), n, n2);
                Short2LongArrayMap.access$010(this.this$0);
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
                        return this.pos < Short2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2LongArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2LongArrayMap.access$100(this.this$1.this$0), this.pos, Short2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2LongArrayMap.access$200(this.this$1.this$0), this.pos, Short2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2LongArrayMap.access$000(this.this$0);
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
            final Short2LongArrayMap this$0;
            {
                this.this$0 = short2LongArrayMap;
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
                        return this.pos < Short2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2LongArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2LongArrayMap.access$100(this.this$1.this$0), this.pos, Short2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2LongArrayMap.access$200(this.this$1.this$0), this.pos, Short2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2LongArrayMap.access$000(this.this$0);
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

    public Short2LongArrayMap clone() {
        Short2LongArrayMap short2LongArrayMap;
        try {
            short2LongArrayMap = (Short2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2LongArrayMap.key = (short[])this.key.clone();
        short2LongArrayMap.value = (long[])this.value.clone();
        return short2LongArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.key[i]);
            objectOutputStream.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new short[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readShort();
            this.value[i] = objectInputStream.readLong();
        }
    }

    public ObjectSet short2LongEntrySet() {
        return this.short2LongEntrySet();
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

    static int access$000(Short2LongArrayMap short2LongArrayMap) {
        return short2LongArrayMap.size;
    }

    static short[] access$100(Short2LongArrayMap short2LongArrayMap) {
        return short2LongArrayMap.key;
    }

    static long[] access$200(Short2LongArrayMap short2LongArrayMap) {
        return short2LongArrayMap.value;
    }

    static int access$010(Short2LongArrayMap short2LongArrayMap) {
        return short2LongArrayMap.size--;
    }

    static int access$300(Short2LongArrayMap short2LongArrayMap, short s) {
        return short2LongArrayMap.findKey(s);
    }

    private final class EntrySet
    extends AbstractObjectSet<Short2LongMap.Entry>
    implements Short2LongMap.FastEntrySet {
        final Short2LongArrayMap this$0;

        private EntrySet(Short2LongArrayMap short2LongArrayMap) {
            this.this$0 = short2LongArrayMap;
        }

        @Override
        public ObjectIterator<Short2LongMap.Entry> iterator() {
            return new ObjectIterator<Short2LongMap.Entry>(this){
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
                    return this.next < Short2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractShort2LongMap.BasicEntry(Short2LongArrayMap.access$100(this.this$1.this$0)[this.curr], Short2LongArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Short2LongMap.Entry> fastIterator() {
            return new ObjectIterator<Short2LongMap.Entry>(this){
                int next;
                int curr;
                final AbstractShort2LongMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractShort2LongMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Short2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Short2LongArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Short2LongArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Short2LongArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            short s = (Short)entry.getKey();
            return this.this$0.containsKey(s) && this.this$0.get(s) == ((Long)entry.getValue()).longValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            short s = (Short)entry.getKey();
            long l = (Long)entry.getValue();
            int n = Short2LongArrayMap.access$300(this.this$0, s);
            if (n == -1 || l != Short2LongArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Short2LongArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Short2LongArrayMap.access$100(this.this$0), n + 1, Short2LongArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Short2LongArrayMap.access$200(this.this$0), n + 1, Short2LongArrayMap.access$200(this.this$0), n, n2);
            Short2LongArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Short2LongArrayMap short2LongArrayMap, 1 var2_2) {
            this(short2LongArrayMap);
        }
    }
}

