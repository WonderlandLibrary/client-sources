/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2IntMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
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
public class Short2IntArrayMap
extends AbstractShort2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] key;
    private transient int[] value;
    private int size;

    public Short2IntArrayMap(short[] sArray, int[] nArray) {
        this.key = sArray;
        this.value = nArray;
        this.size = sArray.length;
        if (sArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + nArray.length + ")");
        }
    }

    public Short2IntArrayMap() {
        this.key = ShortArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Short2IntArrayMap(int n) {
        this.key = new short[n];
        this.value = new int[n];
    }

    public Short2IntArrayMap(Short2IntMap short2IntMap) {
        this(short2IntMap.size());
        this.putAll(short2IntMap);
    }

    public Short2IntArrayMap(Map<? extends Short, ? extends Integer> map) {
        this(map.size());
        this.putAll(map);
    }

    public Short2IntArrayMap(short[] sArray, int[] nArray, int n) {
        this.key = sArray;
        this.value = nArray;
        this.size = n;
        if (sArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + nArray.length + ")");
        }
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + sArray.length + ")");
        }
    }

    public Short2IntMap.FastEntrySet short2IntEntrySet() {
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
    public int get(short s) {
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
    public int put(short s, int n) {
        int n2 = this.findKey(s);
        if (n2 != -1) {
            int n3 = this.value[n2];
            this.value[n2] = n;
            return n3;
        }
        if (this.size == this.key.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n4 = this.size;
            while (n4-- != 0) {
                sArray[n4] = this.key[n4];
                nArray[n4] = this.value[n4];
            }
            this.key = sArray;
            this.value = nArray;
        }
        this.key[this.size] = s;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int remove(short s) {
        int n = this.findKey(s);
        if (n == -1) {
            return this.defRetValue;
        }
        int n2 = this.value[n];
        int n3 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n3);
        System.arraycopy(this.value, n + 1, this.value, n, n3);
        --this.size;
        return n2;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final Short2IntArrayMap this$0;
            {
                this.this$0 = short2IntArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return Short2IntArrayMap.access$300(this.this$0, s) != -1;
            }

            @Override
            public boolean remove(short s) {
                int n = Short2IntArrayMap.access$300(this.this$0, s);
                if (n == -1) {
                    return true;
                }
                int n2 = Short2IntArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Short2IntArrayMap.access$100(this.this$0), n + 1, Short2IntArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Short2IntArrayMap.access$200(this.this$0), n + 1, Short2IntArrayMap.access$200(this.this$0), n, n2);
                Short2IntArrayMap.access$010(this.this$0);
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
                        return this.pos < Short2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2IntArrayMap.access$100(this.this$1.this$0), this.pos, Short2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2IntArrayMap.access$200(this.this$1.this$0), this.pos, Short2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2IntArrayMap.access$000(this.this$0);
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
            final Short2IntArrayMap this$0;
            {
                this.this$0 = short2IntArrayMap;
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
                        return this.pos < Short2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2IntArrayMap.access$100(this.this$1.this$0), this.pos, Short2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2IntArrayMap.access$200(this.this$1.this$0), this.pos, Short2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2IntArrayMap.access$000(this.this$0);
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

    public Short2IntArrayMap clone() {
        Short2IntArrayMap short2IntArrayMap;
        try {
            short2IntArrayMap = (Short2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2IntArrayMap.key = (short[])this.key.clone();
        short2IntArrayMap.value = (int[])this.value.clone();
        return short2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new short[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readShort();
            this.value[i] = objectInputStream.readInt();
        }
    }

    public ObjectSet short2IntEntrySet() {
        return this.short2IntEntrySet();
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

    static int access$000(Short2IntArrayMap short2IntArrayMap) {
        return short2IntArrayMap.size;
    }

    static short[] access$100(Short2IntArrayMap short2IntArrayMap) {
        return short2IntArrayMap.key;
    }

    static int[] access$200(Short2IntArrayMap short2IntArrayMap) {
        return short2IntArrayMap.value;
    }

    static int access$010(Short2IntArrayMap short2IntArrayMap) {
        return short2IntArrayMap.size--;
    }

    static int access$300(Short2IntArrayMap short2IntArrayMap, short s) {
        return short2IntArrayMap.findKey(s);
    }

    private final class EntrySet
    extends AbstractObjectSet<Short2IntMap.Entry>
    implements Short2IntMap.FastEntrySet {
        final Short2IntArrayMap this$0;

        private EntrySet(Short2IntArrayMap short2IntArrayMap) {
            this.this$0 = short2IntArrayMap;
        }

        @Override
        public ObjectIterator<Short2IntMap.Entry> iterator() {
            return new ObjectIterator<Short2IntMap.Entry>(this){
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
                    return this.next < Short2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractShort2IntMap.BasicEntry(Short2IntArrayMap.access$100(this.this$1.this$0)[this.curr], Short2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Short2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Short2IntMap.Entry>(this){
                int next;
                int curr;
                final AbstractShort2IntMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractShort2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Short2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Short2IntArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Short2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Short2IntArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            short s = (Short)entry.getKey();
            return this.this$0.containsKey(s) && this.this$0.get(s) == ((Integer)entry.getValue()).intValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            short s = (Short)entry.getKey();
            int n = (Integer)entry.getValue();
            int n2 = Short2IntArrayMap.access$300(this.this$0, s);
            if (n2 == -1 || n != Short2IntArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Short2IntArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Short2IntArrayMap.access$100(this.this$0), n2 + 1, Short2IntArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Short2IntArrayMap.access$200(this.this$0), n2 + 1, Short2IntArrayMap.access$200(this.this$0), n2, n3);
            Short2IntArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Short2IntArrayMap short2IntArrayMap, 1 var2_2) {
            this(short2IntArrayMap);
        }
    }
}

