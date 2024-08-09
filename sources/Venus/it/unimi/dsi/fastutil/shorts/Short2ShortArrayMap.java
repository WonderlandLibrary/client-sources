/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ShortMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
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
public class Short2ShortArrayMap
extends AbstractShort2ShortMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] key;
    private transient short[] value;
    private int size;

    public Short2ShortArrayMap(short[] sArray, short[] sArray2) {
        this.key = sArray;
        this.value = sArray2;
        this.size = sArray.length;
        if (sArray.length != sArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + sArray2.length + ")");
        }
    }

    public Short2ShortArrayMap() {
        this.key = ShortArrays.EMPTY_ARRAY;
        this.value = ShortArrays.EMPTY_ARRAY;
    }

    public Short2ShortArrayMap(int n) {
        this.key = new short[n];
        this.value = new short[n];
    }

    public Short2ShortArrayMap(Short2ShortMap short2ShortMap) {
        this(short2ShortMap.size());
        this.putAll(short2ShortMap);
    }

    public Short2ShortArrayMap(Map<? extends Short, ? extends Short> map) {
        this(map.size());
        this.putAll(map);
    }

    public Short2ShortArrayMap(short[] sArray, short[] sArray2, int n) {
        this.key = sArray;
        this.value = sArray2;
        this.size = n;
        if (sArray.length != sArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + sArray2.length + ")");
        }
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + sArray.length + ")");
        }
    }

    public Short2ShortMap.FastEntrySet short2ShortEntrySet() {
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
    public short get(short s) {
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
    public boolean containsValue(short s) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public short put(short s, short s2) {
        int n = this.findKey(s);
        if (n != -1) {
            short s3 = this.value[n];
            this.value[n] = s2;
            return s3;
        }
        if (this.size == this.key.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            short[] sArray2 = new short[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                sArray[n2] = this.key[n2];
                sArray2[n2] = this.value[n2];
            }
            this.key = sArray;
            this.value = sArray2;
        }
        this.key[this.size] = s;
        this.value[this.size] = s2;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public short remove(short s) {
        int n = this.findKey(s);
        if (n == -1) {
            return this.defRetValue;
        }
        short s2 = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return s2;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final Short2ShortArrayMap this$0;
            {
                this.this$0 = short2ShortArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return Short2ShortArrayMap.access$300(this.this$0, s) != -1;
            }

            @Override
            public boolean remove(short s) {
                int n = Short2ShortArrayMap.access$300(this.this$0, s);
                if (n == -1) {
                    return true;
                }
                int n2 = Short2ShortArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Short2ShortArrayMap.access$100(this.this$0), n + 1, Short2ShortArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Short2ShortArrayMap.access$200(this.this$0), n + 1, Short2ShortArrayMap.access$200(this.this$0), n, n2);
                Short2ShortArrayMap.access$010(this.this$0);
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
                        return this.pos < Short2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2ShortArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Short2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Short2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2ShortArrayMap.access$000(this.this$0);
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
    public ShortCollection values() {
        return new AbstractShortCollection(this){
            final Short2ShortArrayMap this$0;
            {
                this.this$0 = short2ShortArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return this.this$0.containsValue(s);
            }

            @Override
            public ShortIterator iterator() {
                return new ShortIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Short2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2ShortArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Short2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Short2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2ShortArrayMap.access$000(this.this$0);
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

    public Short2ShortArrayMap clone() {
        Short2ShortArrayMap short2ShortArrayMap;
        try {
            short2ShortArrayMap = (Short2ShortArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2ShortArrayMap.key = (short[])this.key.clone();
        short2ShortArrayMap.value = (short[])this.value.clone();
        return short2ShortArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.key[i]);
            objectOutputStream.writeShort(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new short[this.size];
        this.value = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readShort();
            this.value[i] = objectInputStream.readShort();
        }
    }

    public ObjectSet short2ShortEntrySet() {
        return this.short2ShortEntrySet();
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

    static int access$000(Short2ShortArrayMap short2ShortArrayMap) {
        return short2ShortArrayMap.size;
    }

    static short[] access$100(Short2ShortArrayMap short2ShortArrayMap) {
        return short2ShortArrayMap.key;
    }

    static short[] access$200(Short2ShortArrayMap short2ShortArrayMap) {
        return short2ShortArrayMap.value;
    }

    static int access$010(Short2ShortArrayMap short2ShortArrayMap) {
        return short2ShortArrayMap.size--;
    }

    static int access$300(Short2ShortArrayMap short2ShortArrayMap, short s) {
        return short2ShortArrayMap.findKey(s);
    }

    private final class EntrySet
    extends AbstractObjectSet<Short2ShortMap.Entry>
    implements Short2ShortMap.FastEntrySet {
        final Short2ShortArrayMap this$0;

        private EntrySet(Short2ShortArrayMap short2ShortArrayMap) {
            this.this$0 = short2ShortArrayMap;
        }

        @Override
        public ObjectIterator<Short2ShortMap.Entry> iterator() {
            return new ObjectIterator<Short2ShortMap.Entry>(this){
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
                    return this.next < Short2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractShort2ShortMap.BasicEntry(Short2ShortArrayMap.access$100(this.this$1.this$0)[this.curr], Short2ShortArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Short2ShortMap.Entry> fastIterator() {
            return new ObjectIterator<Short2ShortMap.Entry>(this){
                int next;
                int curr;
                final AbstractShort2ShortMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractShort2ShortMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Short2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Short2ShortArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Short2ShortArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Short2ShortArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            short s = (Short)entry.getKey();
            return this.this$0.containsKey(s) && this.this$0.get(s) == ((Short)entry.getValue()).shortValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            short s = (Short)entry.getKey();
            short s2 = (Short)entry.getValue();
            int n = Short2ShortArrayMap.access$300(this.this$0, s);
            if (n == -1 || s2 != Short2ShortArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Short2ShortArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Short2ShortArrayMap.access$100(this.this$0), n + 1, Short2ShortArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Short2ShortArrayMap.access$200(this.this$0), n + 1, Short2ShortArrayMap.access$200(this.this$0), n, n2);
            Short2ShortArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Short2ShortArrayMap short2ShortArrayMap, 1 var2_2) {
            this(short2ShortArrayMap);
        }
    }
}

