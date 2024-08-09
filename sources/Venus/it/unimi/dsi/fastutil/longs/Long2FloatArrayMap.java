/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2FloatMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
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
public class Long2FloatArrayMap
extends AbstractLong2FloatMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient float[] value;
    private int size;

    public Long2FloatArrayMap(long[] lArray, float[] fArray) {
        this.key = lArray;
        this.value = fArray;
        this.size = lArray.length;
        if (lArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + fArray.length + ")");
        }
    }

    public Long2FloatArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Long2FloatArrayMap(int n) {
        this.key = new long[n];
        this.value = new float[n];
    }

    public Long2FloatArrayMap(Long2FloatMap long2FloatMap) {
        this(long2FloatMap.size());
        this.putAll(long2FloatMap);
    }

    public Long2FloatArrayMap(Map<? extends Long, ? extends Float> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2FloatArrayMap(long[] lArray, float[] fArray, int n) {
        this.key = lArray;
        this.value = fArray;
        this.size = n;
        if (lArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + fArray.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2FloatMap.FastEntrySet long2FloatEntrySet() {
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
    public float get(long l) {
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
    public boolean containsValue(float f) {
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(this.value[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public float put(long l, float f) {
        int n = this.findKey(l);
        if (n != -1) {
            float f2 = this.value[n];
            this.value[n] = f;
            return f2;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.key[n2];
                fArray[n2] = this.value[n2];
            }
            this.key = lArray;
            this.value = fArray;
        }
        this.key[this.size] = l;
        this.value[this.size] = f;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public float remove(long l) {
        int n = this.findKey(l);
        if (n == -1) {
            return this.defRetValue;
        }
        float f = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return f;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2FloatArrayMap this$0;
            {
                this.this$0 = long2FloatArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2FloatArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2FloatArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2FloatArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2FloatArrayMap.access$100(this.this$0), n + 1, Long2FloatArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2FloatArrayMap.access$200(this.this$0), n + 1, Long2FloatArrayMap.access$200(this.this$0), n, n2);
                Long2FloatArrayMap.access$010(this.this$0);
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
                        return this.pos < Long2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2FloatArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Long2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Long2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2FloatArrayMap.access$000(this.this$0);
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
    public FloatCollection values() {
        return new AbstractFloatCollection(this){
            final Long2FloatArrayMap this$0;
            {
                this.this$0 = long2FloatArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return this.this$0.containsValue(f);
            }

            @Override
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Long2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2FloatArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Long2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Long2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2FloatArrayMap.access$000(this.this$0);
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

    public Long2FloatArrayMap clone() {
        Long2FloatArrayMap long2FloatArrayMap;
        try {
            long2FloatArrayMap = (Long2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2FloatArrayMap.key = (long[])this.key.clone();
        long2FloatArrayMap.value = (float[])this.value.clone();
        return long2FloatArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readFloat();
        }
    }

    public ObjectSet long2FloatEntrySet() {
        return this.long2FloatEntrySet();
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

    static int access$000(Long2FloatArrayMap long2FloatArrayMap) {
        return long2FloatArrayMap.size;
    }

    static long[] access$100(Long2FloatArrayMap long2FloatArrayMap) {
        return long2FloatArrayMap.key;
    }

    static float[] access$200(Long2FloatArrayMap long2FloatArrayMap) {
        return long2FloatArrayMap.value;
    }

    static int access$010(Long2FloatArrayMap long2FloatArrayMap) {
        return long2FloatArrayMap.size--;
    }

    static int access$300(Long2FloatArrayMap long2FloatArrayMap, long l) {
        return long2FloatArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2FloatMap.Entry>
    implements Long2FloatMap.FastEntrySet {
        final Long2FloatArrayMap this$0;

        private EntrySet(Long2FloatArrayMap long2FloatArrayMap) {
            this.this$0 = long2FloatArrayMap;
        }

        @Override
        public ObjectIterator<Long2FloatMap.Entry> iterator() {
            return new ObjectIterator<Long2FloatMap.Entry>(this){
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
                    return this.next < Long2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2FloatMap.BasicEntry(Long2FloatArrayMap.access$100(this.this$1.this$0)[this.curr], Long2FloatArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2FloatMap.Entry> fastIterator() {
            return new ObjectIterator<Long2FloatMap.Entry>(this){
                int next;
                int curr;
                final AbstractLong2FloatMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2FloatMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2FloatArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2FloatArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2FloatArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && Float.floatToIntBits(this.this$0.get(l)) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            long l = (Long)entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            int n = Long2FloatArrayMap.access$300(this.this$0, l);
            if (n == -1 || Float.floatToIntBits(f) != Float.floatToIntBits(Long2FloatArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Long2FloatArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2FloatArrayMap.access$100(this.this$0), n + 1, Long2FloatArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2FloatArrayMap.access$200(this.this$0), n + 1, Long2FloatArrayMap.access$200(this.this$0), n, n2);
            Long2FloatArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2FloatArrayMap long2FloatArrayMap, 1 var2_2) {
            this(long2FloatArrayMap);
        }
    }
}

