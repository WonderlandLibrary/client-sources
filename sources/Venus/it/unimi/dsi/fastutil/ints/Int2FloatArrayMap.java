/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2FloatMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
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
public class Int2FloatArrayMap
extends AbstractInt2FloatMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient float[] value;
    private int size;

    public Int2FloatArrayMap(int[] nArray, float[] fArray) {
        this.key = nArray;
        this.value = fArray;
        this.size = nArray.length;
        if (nArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + fArray.length + ")");
        }
    }

    public Int2FloatArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Int2FloatArrayMap(int n) {
        this.key = new int[n];
        this.value = new float[n];
    }

    public Int2FloatArrayMap(Int2FloatMap int2FloatMap) {
        this(int2FloatMap.size());
        this.putAll(int2FloatMap);
    }

    public Int2FloatArrayMap(Map<? extends Integer, ? extends Float> map) {
        this(map.size());
        this.putAll(map);
    }

    public Int2FloatArrayMap(int[] nArray, float[] fArray, int n) {
        this.key = nArray;
        this.value = fArray;
        this.size = n;
        if (nArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + fArray.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2FloatMap.FastEntrySet int2FloatEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(int n) {
        int[] nArray = this.key;
        int n2 = this.size;
        while (n2-- != 0) {
            if (nArray[n2] != n) continue;
            return n2;
        }
        return 1;
    }

    @Override
    public float get(int n) {
        int[] nArray = this.key;
        int n2 = this.size;
        while (n2-- != 0) {
            if (nArray[n2] != n) continue;
            return this.value[n2];
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
    public boolean containsKey(int n) {
        return this.findKey(n) != -1;
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
    public float put(int n, float f) {
        int n2 = this.findKey(n);
        if (n2 != -1) {
            float f2 = this.value[n2];
            this.value[n2] = f;
            return f2;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            int n3 = this.size;
            while (n3-- != 0) {
                nArray[n3] = this.key[n3];
                fArray[n3] = this.value[n3];
            }
            this.key = nArray;
            this.value = fArray;
        }
        this.key[this.size] = n;
        this.value[this.size] = f;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public float remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        float f = this.value[n2];
        int n3 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n3);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n3);
        --this.size;
        return f;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final Int2FloatArrayMap this$0;
            {
                this.this$0 = int2FloatArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return Int2FloatArrayMap.access$300(this.this$0, n) != -1;
            }

            @Override
            public boolean remove(int n) {
                int n2 = Int2FloatArrayMap.access$300(this.this$0, n);
                if (n2 == -1) {
                    return true;
                }
                int n3 = Int2FloatArrayMap.access$000(this.this$0) - n2 - 1;
                System.arraycopy(Int2FloatArrayMap.access$100(this.this$0), n2 + 1, Int2FloatArrayMap.access$100(this.this$0), n2, n3);
                System.arraycopy(Int2FloatArrayMap.access$200(this.this$0), n2 + 1, Int2FloatArrayMap.access$200(this.this$0), n2, n3);
                Int2FloatArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public IntIterator iterator() {
                return new IntIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Int2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2FloatArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Int2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Int2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2FloatArrayMap.access$000(this.this$0);
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
            final Int2FloatArrayMap this$0;
            {
                this.this$0 = int2FloatArrayMap;
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
                        return this.pos < Int2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2FloatArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Int2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Int2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2FloatArrayMap.access$000(this.this$0);
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

    public Int2FloatArrayMap clone() {
        Int2FloatArrayMap int2FloatArrayMap;
        try {
            int2FloatArrayMap = (Int2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2FloatArrayMap.key = (int[])this.key.clone();
        int2FloatArrayMap.value = (float[])this.value.clone();
        return int2FloatArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readFloat();
        }
    }

    public ObjectSet int2FloatEntrySet() {
        return this.int2FloatEntrySet();
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

    static int access$000(Int2FloatArrayMap int2FloatArrayMap) {
        return int2FloatArrayMap.size;
    }

    static int[] access$100(Int2FloatArrayMap int2FloatArrayMap) {
        return int2FloatArrayMap.key;
    }

    static float[] access$200(Int2FloatArrayMap int2FloatArrayMap) {
        return int2FloatArrayMap.value;
    }

    static int access$010(Int2FloatArrayMap int2FloatArrayMap) {
        return int2FloatArrayMap.size--;
    }

    static int access$300(Int2FloatArrayMap int2FloatArrayMap, int n) {
        return int2FloatArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2FloatMap.Entry>
    implements Int2FloatMap.FastEntrySet {
        final Int2FloatArrayMap this$0;

        private EntrySet(Int2FloatArrayMap int2FloatArrayMap) {
            this.this$0 = int2FloatArrayMap;
        }

        @Override
        public ObjectIterator<Int2FloatMap.Entry> iterator() {
            return new ObjectIterator<Int2FloatMap.Entry>(this){
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
                    return this.next < Int2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2FloatMap.BasicEntry(Int2FloatArrayMap.access$100(this.this$1.this$0)[this.curr], Int2FloatArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2FloatMap.Entry> fastIterator() {
            return new ObjectIterator<Int2FloatMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2FloatMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2FloatMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Int2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2FloatArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Int2FloatArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Int2FloatArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && Float.floatToIntBits(this.this$0.get(n)) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            float f = ((Float)entry.getValue()).floatValue();
            int n2 = Int2FloatArrayMap.access$300(this.this$0, n);
            if (n2 == -1 || Float.floatToIntBits(f) != Float.floatToIntBits(Int2FloatArrayMap.access$200(this.this$0)[n2])) {
                return true;
            }
            int n3 = Int2FloatArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Int2FloatArrayMap.access$100(this.this$0), n2 + 1, Int2FloatArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Int2FloatArrayMap.access$200(this.this$0), n2 + 1, Int2FloatArrayMap.access$200(this.this$0), n2, n3);
            Int2FloatArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Int2FloatArrayMap int2FloatArrayMap, 1 var2_2) {
            this(int2FloatArrayMap);
        }
    }
}

