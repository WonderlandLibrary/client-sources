/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
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
public class Short2BooleanArrayMap
extends AbstractShort2BooleanMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] key;
    private transient boolean[] value;
    private int size;

    public Short2BooleanArrayMap(short[] sArray, boolean[] blArray) {
        this.key = sArray;
        this.value = blArray;
        this.size = sArray.length;
        if (sArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + blArray.length + ")");
        }
    }

    public Short2BooleanArrayMap() {
        this.key = ShortArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Short2BooleanArrayMap(int n) {
        this.key = new short[n];
        this.value = new boolean[n];
    }

    public Short2BooleanArrayMap(Short2BooleanMap short2BooleanMap) {
        this(short2BooleanMap.size());
        this.putAll(short2BooleanMap);
    }

    public Short2BooleanArrayMap(Map<? extends Short, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Short2BooleanArrayMap(short[] sArray, boolean[] blArray, int n) {
        this.key = sArray;
        this.value = blArray;
        this.size = n;
        if (sArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + blArray.length + ")");
        }
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + sArray.length + ")");
        }
    }

    public Short2BooleanMap.FastEntrySet short2BooleanEntrySet() {
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
    public boolean get(short s) {
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
    public boolean put(short s, boolean bl) {
        int n = this.findKey(s);
        if (n != -1) {
            boolean bl2 = this.value[n];
            this.value[n] = bl;
            return bl2;
        }
        if (this.size == this.key.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                sArray[n2] = this.key[n2];
                blArray[n2] = this.value[n2];
            }
            this.key = sArray;
            this.value = blArray;
        }
        this.key[this.size] = s;
        this.value[this.size] = bl;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public boolean remove(short s) {
        int n = this.findKey(s);
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
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final Short2BooleanArrayMap this$0;
            {
                this.this$0 = short2BooleanArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return Short2BooleanArrayMap.access$300(this.this$0, s) != -1;
            }

            @Override
            public boolean remove(short s) {
                int n = Short2BooleanArrayMap.access$300(this.this$0, s);
                if (n == -1) {
                    return true;
                }
                int n2 = Short2BooleanArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Short2BooleanArrayMap.access$100(this.this$0), n + 1, Short2BooleanArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Short2BooleanArrayMap.access$200(this.this$0), n + 1, Short2BooleanArrayMap.access$200(this.this$0), n, n2);
                Short2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Short2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Short2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Short2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2BooleanArrayMap.access$000(this.this$0);
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
            final Short2BooleanArrayMap this$0;
            {
                this.this$0 = short2BooleanArrayMap;
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
                        return this.pos < Short2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Short2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Short2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2BooleanArrayMap.access$000(this.this$0);
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

    public Short2BooleanArrayMap clone() {
        Short2BooleanArrayMap short2BooleanArrayMap;
        try {
            short2BooleanArrayMap = (Short2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2BooleanArrayMap.key = (short[])this.key.clone();
        short2BooleanArrayMap.value = (boolean[])this.value.clone();
        return short2BooleanArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.key[i]);
            objectOutputStream.writeBoolean(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new short[this.size];
        this.value = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readShort();
            this.value[i] = objectInputStream.readBoolean();
        }
    }

    public ObjectSet short2BooleanEntrySet() {
        return this.short2BooleanEntrySet();
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

    static int access$000(Short2BooleanArrayMap short2BooleanArrayMap) {
        return short2BooleanArrayMap.size;
    }

    static short[] access$100(Short2BooleanArrayMap short2BooleanArrayMap) {
        return short2BooleanArrayMap.key;
    }

    static boolean[] access$200(Short2BooleanArrayMap short2BooleanArrayMap) {
        return short2BooleanArrayMap.value;
    }

    static int access$010(Short2BooleanArrayMap short2BooleanArrayMap) {
        return short2BooleanArrayMap.size--;
    }

    static int access$300(Short2BooleanArrayMap short2BooleanArrayMap, short s) {
        return short2BooleanArrayMap.findKey(s);
    }

    private final class EntrySet
    extends AbstractObjectSet<Short2BooleanMap.Entry>
    implements Short2BooleanMap.FastEntrySet {
        final Short2BooleanArrayMap this$0;

        private EntrySet(Short2BooleanArrayMap short2BooleanArrayMap) {
            this.this$0 = short2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Short2BooleanMap.Entry> iterator() {
            return new ObjectIterator<Short2BooleanMap.Entry>(this){
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
                    return this.next < Short2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractShort2BooleanMap.BasicEntry(Short2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Short2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Short2BooleanMap.Entry> fastIterator() {
            return new ObjectIterator<Short2BooleanMap.Entry>(this){
                int next;
                int curr;
                final AbstractShort2BooleanMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractShort2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Short2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Short2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Short2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Short2BooleanArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            short s = (Short)entry.getKey();
            return this.this$0.containsKey(s) && this.this$0.get(s) == ((Boolean)entry.getValue()).booleanValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            short s = (Short)entry.getKey();
            boolean bl = (Boolean)entry.getValue();
            int n = Short2BooleanArrayMap.access$300(this.this$0, s);
            if (n == -1 || bl != Short2BooleanArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Short2BooleanArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Short2BooleanArrayMap.access$100(this.this$0), n + 1, Short2BooleanArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Short2BooleanArrayMap.access$200(this.this$0), n + 1, Short2BooleanArrayMap.access$200(this.this$0), n, n2);
            Short2BooleanArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Short2BooleanArrayMap short2BooleanArrayMap, 1 var2_2) {
            this(short2BooleanArrayMap);
        }
    }
}

