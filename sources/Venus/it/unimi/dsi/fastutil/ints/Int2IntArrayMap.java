/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2IntMap;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
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
public class Int2IntArrayMap
extends AbstractInt2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient int[] value;
    private int size;

    public Int2IntArrayMap(int[] nArray, int[] nArray2) {
        this.key = nArray;
        this.value = nArray2;
        this.size = nArray.length;
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + nArray2.length + ")");
        }
    }

    public Int2IntArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Int2IntArrayMap(int n) {
        this.key = new int[n];
        this.value = new int[n];
    }

    public Int2IntArrayMap(Int2IntMap int2IntMap) {
        this(int2IntMap.size());
        this.putAll(int2IntMap);
    }

    public Int2IntArrayMap(Map<? extends Integer, ? extends Integer> map) {
        this(map.size());
        this.putAll(map);
    }

    public Int2IntArrayMap(int[] nArray, int[] nArray2, int n) {
        this.key = nArray;
        this.value = nArray2;
        this.size = n;
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + nArray2.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2IntMap.FastEntrySet int2IntEntrySet() {
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
    public int get(int n) {
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
    public boolean containsValue(int n) {
        int n2 = this.size;
        while (n2-- != 0) {
            if (this.value[n2] != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int put(int n, int n2) {
        int n3 = this.findKey(n);
        if (n3 != -1) {
            int n4 = this.value[n3];
            this.value[n3] = n2;
            return n4;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int[] nArray2 = new int[this.size == 0 ? 2 : this.size * 2];
            int n5 = this.size;
            while (n5-- != 0) {
                nArray[n5] = this.key[n5];
                nArray2[n5] = this.value[n5];
            }
            this.key = nArray;
            this.value = nArray2;
        }
        this.key[this.size] = n;
        this.value[this.size] = n2;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        int n3 = this.value[n2];
        int n4 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n4);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n4);
        --this.size;
        return n3;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final Int2IntArrayMap this$0;
            {
                this.this$0 = int2IntArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return Int2IntArrayMap.access$300(this.this$0, n) != -1;
            }

            @Override
            public boolean remove(int n) {
                int n2 = Int2IntArrayMap.access$300(this.this$0, n);
                if (n2 == -1) {
                    return true;
                }
                int n3 = Int2IntArrayMap.access$000(this.this$0) - n2 - 1;
                System.arraycopy(Int2IntArrayMap.access$100(this.this$0), n2 + 1, Int2IntArrayMap.access$100(this.this$0), n2, n3);
                System.arraycopy(Int2IntArrayMap.access$200(this.this$0), n2 + 1, Int2IntArrayMap.access$200(this.this$0), n2, n3);
                Int2IntArrayMap.access$010(this.this$0);
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
                        return this.pos < Int2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2IntArrayMap.access$100(this.this$1.this$0), this.pos, Int2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2IntArrayMap.access$200(this.this$1.this$0), this.pos, Int2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2IntArrayMap.access$000(this.this$0);
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
    public IntCollection values() {
        return new AbstractIntCollection(this){
            final Int2IntArrayMap this$0;
            {
                this.this$0 = int2IntArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsValue(n);
            }

            @Override
            public IntIterator iterator() {
                return new IntIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Int2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2IntArrayMap.access$100(this.this$1.this$0), this.pos, Int2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2IntArrayMap.access$200(this.this$1.this$0), this.pos, Int2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2IntArrayMap.access$000(this.this$0);
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

    public Int2IntArrayMap clone() {
        Int2IntArrayMap int2IntArrayMap;
        try {
            int2IntArrayMap = (Int2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2IntArrayMap.key = (int[])this.key.clone();
        int2IntArrayMap.value = (int[])this.value.clone();
        return int2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readInt();
        }
    }

    public ObjectSet int2IntEntrySet() {
        return this.int2IntEntrySet();
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

    static int access$000(Int2IntArrayMap int2IntArrayMap) {
        return int2IntArrayMap.size;
    }

    static int[] access$100(Int2IntArrayMap int2IntArrayMap) {
        return int2IntArrayMap.key;
    }

    static int[] access$200(Int2IntArrayMap int2IntArrayMap) {
        return int2IntArrayMap.value;
    }

    static int access$010(Int2IntArrayMap int2IntArrayMap) {
        return int2IntArrayMap.size--;
    }

    static int access$300(Int2IntArrayMap int2IntArrayMap, int n) {
        return int2IntArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2IntMap.Entry>
    implements Int2IntMap.FastEntrySet {
        final Int2IntArrayMap this$0;

        private EntrySet(Int2IntArrayMap int2IntArrayMap) {
            this.this$0 = int2IntArrayMap;
        }

        @Override
        public ObjectIterator<Int2IntMap.Entry> iterator() {
            return new ObjectIterator<Int2IntMap.Entry>(this){
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
                    return this.next < Int2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.access$100(this.this$1.this$0)[this.curr], Int2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Int2IntMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2IntMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Int2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2IntArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Int2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Int2IntArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && this.this$0.get(n) == ((Integer)entry.getValue()).intValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            int n2 = (Integer)entry.getValue();
            int n3 = Int2IntArrayMap.access$300(this.this$0, n);
            if (n3 == -1 || n2 != Int2IntArrayMap.access$200(this.this$0)[n3]) {
                return true;
            }
            int n4 = Int2IntArrayMap.access$000(this.this$0) - n3 - 1;
            System.arraycopy(Int2IntArrayMap.access$100(this.this$0), n3 + 1, Int2IntArrayMap.access$100(this.this$0), n3, n4);
            System.arraycopy(Int2IntArrayMap.access$200(this.this$0), n3 + 1, Int2IntArrayMap.access$200(this.this$0), n3, n4);
            Int2IntArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Int2IntArrayMap int2IntArrayMap, 1 var2_2) {
            this(int2IntArrayMap);
        }
    }
}

