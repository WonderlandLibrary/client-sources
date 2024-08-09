/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2BooleanMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
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
public class Int2BooleanArrayMap
extends AbstractInt2BooleanMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient boolean[] value;
    private int size;

    public Int2BooleanArrayMap(int[] nArray, boolean[] blArray) {
        this.key = nArray;
        this.value = blArray;
        this.size = nArray.length;
        if (nArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + blArray.length + ")");
        }
    }

    public Int2BooleanArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Int2BooleanArrayMap(int n) {
        this.key = new int[n];
        this.value = new boolean[n];
    }

    public Int2BooleanArrayMap(Int2BooleanMap int2BooleanMap) {
        this(int2BooleanMap.size());
        this.putAll(int2BooleanMap);
    }

    public Int2BooleanArrayMap(Map<? extends Integer, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Int2BooleanArrayMap(int[] nArray, boolean[] blArray, int n) {
        this.key = nArray;
        this.value = blArray;
        this.size = n;
        if (nArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + blArray.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2BooleanMap.FastEntrySet int2BooleanEntrySet() {
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
    public boolean get(int n) {
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
    public boolean put(int n, boolean bl) {
        int n2 = this.findKey(n);
        if (n2 != -1) {
            boolean bl2 = this.value[n2];
            this.value[n2] = bl;
            return bl2;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n3 = this.size;
            while (n3-- != 0) {
                nArray[n3] = this.key[n3];
                blArray[n3] = this.value[n3];
            }
            this.key = nArray;
            this.value = blArray;
        }
        this.key[this.size] = n;
        this.value[this.size] = bl;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public boolean remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        boolean bl = this.value[n2];
        int n3 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n3);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n3);
        --this.size;
        return bl;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final Int2BooleanArrayMap this$0;
            {
                this.this$0 = int2BooleanArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return Int2BooleanArrayMap.access$300(this.this$0, n) != -1;
            }

            @Override
            public boolean remove(int n) {
                int n2 = Int2BooleanArrayMap.access$300(this.this$0, n);
                if (n2 == -1) {
                    return true;
                }
                int n3 = Int2BooleanArrayMap.access$000(this.this$0) - n2 - 1;
                System.arraycopy(Int2BooleanArrayMap.access$100(this.this$0), n2 + 1, Int2BooleanArrayMap.access$100(this.this$0), n2, n3);
                System.arraycopy(Int2BooleanArrayMap.access$200(this.this$0), n2 + 1, Int2BooleanArrayMap.access$200(this.this$0), n2, n3);
                Int2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Int2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Int2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Int2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2BooleanArrayMap.access$000(this.this$0);
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
            final Int2BooleanArrayMap this$0;
            {
                this.this$0 = int2BooleanArrayMap;
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
                        return this.pos < Int2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Int2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Int2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2BooleanArrayMap.access$000(this.this$0);
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

    public Int2BooleanArrayMap clone() {
        Int2BooleanArrayMap int2BooleanArrayMap;
        try {
            int2BooleanArrayMap = (Int2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2BooleanArrayMap.key = (int[])this.key.clone();
        int2BooleanArrayMap.value = (boolean[])this.value.clone();
        return int2BooleanArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeBoolean(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readBoolean();
        }
    }

    public ObjectSet int2BooleanEntrySet() {
        return this.int2BooleanEntrySet();
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

    static int access$000(Int2BooleanArrayMap int2BooleanArrayMap) {
        return int2BooleanArrayMap.size;
    }

    static int[] access$100(Int2BooleanArrayMap int2BooleanArrayMap) {
        return int2BooleanArrayMap.key;
    }

    static boolean[] access$200(Int2BooleanArrayMap int2BooleanArrayMap) {
        return int2BooleanArrayMap.value;
    }

    static int access$010(Int2BooleanArrayMap int2BooleanArrayMap) {
        return int2BooleanArrayMap.size--;
    }

    static int access$300(Int2BooleanArrayMap int2BooleanArrayMap, int n) {
        return int2BooleanArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2BooleanMap.Entry>
    implements Int2BooleanMap.FastEntrySet {
        final Int2BooleanArrayMap this$0;

        private EntrySet(Int2BooleanArrayMap int2BooleanArrayMap) {
            this.this$0 = int2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Int2BooleanMap.Entry> iterator() {
            return new ObjectIterator<Int2BooleanMap.Entry>(this){
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
                    return this.next < Int2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2BooleanMap.BasicEntry(Int2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Int2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2BooleanMap.Entry> fastIterator() {
            return new ObjectIterator<Int2BooleanMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2BooleanMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Int2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Int2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Int2BooleanArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && this.this$0.get(n) == ((Boolean)entry.getValue()).booleanValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            boolean bl = (Boolean)entry.getValue();
            int n2 = Int2BooleanArrayMap.access$300(this.this$0, n);
            if (n2 == -1 || bl != Int2BooleanArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Int2BooleanArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Int2BooleanArrayMap.access$100(this.this$0), n2 + 1, Int2BooleanArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Int2BooleanArrayMap.access$200(this.this$0), n2 + 1, Int2BooleanArrayMap.access$200(this.this$0), n2, n3);
            Int2BooleanArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Int2BooleanArrayMap int2BooleanArrayMap, 1 var2_2) {
            this(int2BooleanArrayMap);
        }
    }
}

