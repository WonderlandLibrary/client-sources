/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2CharMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2CharMap;
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
public class Short2CharArrayMap
extends AbstractShort2CharMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient short[] key;
    private transient char[] value;
    private int size;

    public Short2CharArrayMap(short[] sArray, char[] cArray) {
        this.key = sArray;
        this.value = cArray;
        this.size = sArray.length;
        if (sArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + cArray.length + ")");
        }
    }

    public Short2CharArrayMap() {
        this.key = ShortArrays.EMPTY_ARRAY;
        this.value = CharArrays.EMPTY_ARRAY;
    }

    public Short2CharArrayMap(int n) {
        this.key = new short[n];
        this.value = new char[n];
    }

    public Short2CharArrayMap(Short2CharMap short2CharMap) {
        this(short2CharMap.size());
        this.putAll(short2CharMap);
    }

    public Short2CharArrayMap(Map<? extends Short, ? extends Character> map) {
        this(map.size());
        this.putAll(map);
    }

    public Short2CharArrayMap(short[] sArray, char[] cArray, int n) {
        this.key = sArray;
        this.value = cArray;
        this.size = n;
        if (sArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + sArray.length + ", " + cArray.length + ")");
        }
        if (n > sArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + sArray.length + ")");
        }
    }

    public Short2CharMap.FastEntrySet short2CharEntrySet() {
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
    public char get(short s) {
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
    public boolean containsValue(char c) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != c) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public char put(short s, char c) {
        int n = this.findKey(s);
        if (n != -1) {
            char c2 = this.value[n];
            this.value[n] = c;
            return c2;
        }
        if (this.size == this.key.length) {
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                sArray[n2] = this.key[n2];
                cArray[n2] = this.value[n2];
            }
            this.key = sArray;
            this.value = cArray;
        }
        this.key[this.size] = s;
        this.value[this.size] = c;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public char remove(short s) {
        int n = this.findKey(s);
        if (n == -1) {
            return this.defRetValue;
        }
        char c = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return c;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final Short2CharArrayMap this$0;
            {
                this.this$0 = short2CharArrayMap;
            }

            @Override
            public boolean contains(short s) {
                return Short2CharArrayMap.access$300(this.this$0, s) != -1;
            }

            @Override
            public boolean remove(short s) {
                int n = Short2CharArrayMap.access$300(this.this$0, s);
                if (n == -1) {
                    return true;
                }
                int n2 = Short2CharArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Short2CharArrayMap.access$100(this.this$0), n + 1, Short2CharArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Short2CharArrayMap.access$200(this.this$0), n + 1, Short2CharArrayMap.access$200(this.this$0), n, n2);
                Short2CharArrayMap.access$010(this.this$0);
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
                        return this.pos < Short2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2CharArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2CharArrayMap.access$100(this.this$1.this$0), this.pos, Short2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2CharArrayMap.access$200(this.this$1.this$0), this.pos, Short2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2CharArrayMap.access$000(this.this$0);
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
    public CharCollection values() {
        return new AbstractCharCollection(this){
            final Short2CharArrayMap this$0;
            {
                this.this$0 = short2CharArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return this.this$0.containsValue(c);
            }

            @Override
            public CharIterator iterator() {
                return new CharIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Short2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Short2CharArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Short2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Short2CharArrayMap.access$100(this.this$1.this$0), this.pos, Short2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Short2CharArrayMap.access$200(this.this$1.this$0), this.pos, Short2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Short2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Short2CharArrayMap.access$000(this.this$0);
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

    public Short2CharArrayMap clone() {
        Short2CharArrayMap short2CharArrayMap;
        try {
            short2CharArrayMap = (Short2CharArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        short2CharArrayMap.key = (short[])this.key.clone();
        short2CharArrayMap.value = (char[])this.value.clone();
        return short2CharArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.key[i]);
            objectOutputStream.writeChar(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new short[this.size];
        this.value = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readShort();
            this.value[i] = objectInputStream.readChar();
        }
    }

    public ObjectSet short2CharEntrySet() {
        return this.short2CharEntrySet();
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

    static int access$000(Short2CharArrayMap short2CharArrayMap) {
        return short2CharArrayMap.size;
    }

    static short[] access$100(Short2CharArrayMap short2CharArrayMap) {
        return short2CharArrayMap.key;
    }

    static char[] access$200(Short2CharArrayMap short2CharArrayMap) {
        return short2CharArrayMap.value;
    }

    static int access$010(Short2CharArrayMap short2CharArrayMap) {
        return short2CharArrayMap.size--;
    }

    static int access$300(Short2CharArrayMap short2CharArrayMap, short s) {
        return short2CharArrayMap.findKey(s);
    }

    private final class EntrySet
    extends AbstractObjectSet<Short2CharMap.Entry>
    implements Short2CharMap.FastEntrySet {
        final Short2CharArrayMap this$0;

        private EntrySet(Short2CharArrayMap short2CharArrayMap) {
            this.this$0 = short2CharArrayMap;
        }

        @Override
        public ObjectIterator<Short2CharMap.Entry> iterator() {
            return new ObjectIterator<Short2CharMap.Entry>(this){
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
                    return this.next < Short2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractShort2CharMap.BasicEntry(Short2CharArrayMap.access$100(this.this$1.this$0)[this.curr], Short2CharArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Short2CharMap.Entry> fastIterator() {
            return new ObjectIterator<Short2CharMap.Entry>(this){
                int next;
                int curr;
                final AbstractShort2CharMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractShort2CharMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Short2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Short2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Short2CharArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Short2CharArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Short2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Short2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Short2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Short2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Short2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Short2CharArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            short s = (Short)entry.getKey();
            return this.this$0.containsKey(s) && this.this$0.get(s) == ((Character)entry.getValue()).charValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            short s = (Short)entry.getKey();
            char c = ((Character)entry.getValue()).charValue();
            int n = Short2CharArrayMap.access$300(this.this$0, s);
            if (n == -1 || c != Short2CharArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Short2CharArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Short2CharArrayMap.access$100(this.this$0), n + 1, Short2CharArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Short2CharArrayMap.access$200(this.this$0), n + 1, Short2CharArrayMap.access$200(this.this$0), n, n2);
            Short2CharArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Short2CharArrayMap short2CharArrayMap, 1 var2_2) {
            this(short2CharArrayMap);
        }
    }
}

