/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ShortMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ShortMap;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
public class Char2ShortArrayMap
extends AbstractChar2ShortMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient short[] value;
    private int size;

    public Char2ShortArrayMap(char[] cArray, short[] sArray) {
        this.key = cArray;
        this.value = sArray;
        this.size = cArray.length;
        if (cArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + sArray.length + ")");
        }
    }

    public Char2ShortArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = ShortArrays.EMPTY_ARRAY;
    }

    public Char2ShortArrayMap(int n) {
        this.key = new char[n];
        this.value = new short[n];
    }

    public Char2ShortArrayMap(Char2ShortMap char2ShortMap) {
        this(char2ShortMap.size());
        this.putAll(char2ShortMap);
    }

    public Char2ShortArrayMap(Map<? extends Character, ? extends Short> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2ShortArrayMap(char[] cArray, short[] sArray, int n) {
        this.key = cArray;
        this.value = sArray;
        this.size = n;
        if (cArray.length != sArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + sArray.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2ShortMap.FastEntrySet char2ShortEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(char c) {
        char[] cArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (cArray[n] != c) continue;
            return n;
        }
        return 1;
    }

    @Override
    public short get(char c) {
        char[] cArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (cArray[n] != c) continue;
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
    public boolean containsKey(char c) {
        return this.findKey(c) != -1;
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
    public short put(char c, short s) {
        int n = this.findKey(c);
        if (n != -1) {
            short s2 = this.value[n];
            this.value[n] = s;
            return s2;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            short[] sArray = new short[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.key[n2];
                sArray[n2] = this.value[n2];
            }
            this.key = cArray;
            this.value = sArray;
        }
        this.key[this.size] = c;
        this.value[this.size] = s;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public short remove(char c) {
        int n = this.findKey(c);
        if (n == -1) {
            return this.defRetValue;
        }
        short s = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return s;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2ShortArrayMap this$0;
            {
                this.this$0 = char2ShortArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2ShortArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2ShortArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2ShortArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2ShortArrayMap.access$100(this.this$0), n + 1, Char2ShortArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2ShortArrayMap.access$200(this.this$0), n + 1, Char2ShortArrayMap.access$200(this.this$0), n, n2);
                Char2ShortArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public CharIterator iterator() {
                return new CharIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Char2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2ShortArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Char2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Char2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2ShortArrayMap.access$000(this.this$0);
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
            final Char2ShortArrayMap this$0;
            {
                this.this$0 = char2ShortArrayMap;
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
                        return this.pos < Char2ShortArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2ShortArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2ShortArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2ShortArrayMap.access$100(this.this$1.this$0), this.pos, Char2ShortArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2ShortArrayMap.access$200(this.this$1.this$0), this.pos, Char2ShortArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2ShortArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2ShortArrayMap.access$000(this.this$0);
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

    public Char2ShortArrayMap clone() {
        Char2ShortArrayMap char2ShortArrayMap;
        try {
            char2ShortArrayMap = (Char2ShortArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2ShortArrayMap.key = (char[])this.key.clone();
        char2ShortArrayMap.value = (short[])this.value.clone();
        return char2ShortArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeShort(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readShort();
        }
    }

    public ObjectSet char2ShortEntrySet() {
        return this.char2ShortEntrySet();
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

    static int access$000(Char2ShortArrayMap char2ShortArrayMap) {
        return char2ShortArrayMap.size;
    }

    static char[] access$100(Char2ShortArrayMap char2ShortArrayMap) {
        return char2ShortArrayMap.key;
    }

    static short[] access$200(Char2ShortArrayMap char2ShortArrayMap) {
        return char2ShortArrayMap.value;
    }

    static int access$010(Char2ShortArrayMap char2ShortArrayMap) {
        return char2ShortArrayMap.size--;
    }

    static int access$300(Char2ShortArrayMap char2ShortArrayMap, char c) {
        return char2ShortArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2ShortMap.Entry>
    implements Char2ShortMap.FastEntrySet {
        final Char2ShortArrayMap this$0;

        private EntrySet(Char2ShortArrayMap char2ShortArrayMap) {
            this.this$0 = char2ShortArrayMap;
        }

        @Override
        public ObjectIterator<Char2ShortMap.Entry> iterator() {
            return new ObjectIterator<Char2ShortMap.Entry>(this){
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
                    return this.next < Char2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2ShortMap.BasicEntry(Char2ShortArrayMap.access$100(this.this$1.this$0)[this.curr], Char2ShortArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2ShortMap.Entry> fastIterator() {
            return new ObjectIterator<Char2ShortMap.Entry>(this){
                int next;
                int curr;
                final AbstractChar2ShortMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2ShortMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2ShortArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2ShortMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2ShortArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2ShortArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2ShortArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2ShortArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2ShortArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2ShortArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2ShortArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2ShortArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Character)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && this.this$0.get(c) == ((Short)entry.getValue()).shortValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Character)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Short)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            short s = (Short)entry.getValue();
            int n = Char2ShortArrayMap.access$300(this.this$0, c);
            if (n == -1 || s != Char2ShortArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Char2ShortArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Char2ShortArrayMap.access$100(this.this$0), n + 1, Char2ShortArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Char2ShortArrayMap.access$200(this.this$0), n + 1, Char2ShortArrayMap.access$200(this.this$0), n, n2);
            Char2ShortArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2ShortArrayMap char2ShortArrayMap, 1 var2_2) {
            this(char2ShortArrayMap);
        }
    }
}

